
/* 
 * This file defines twitter connector.
 * 
 * Connector information is stored in a separated properties file. (ex: twitter4j.properties)
 * 
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

// The data structure for rate limit.
// It contains time stamp when rate limit happened and time in seconds until reset.
class RateLimit {
    private long timeStamp;
    private int secondsUntilReset;

    public RateLimit(long timeStamp, int secondsUntilReset) {
        this.timeStamp = timeStamp;
        this.secondsUntilReset = secondsUntilReset;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getSecondsUntilReset() {
        return secondsUntilReset;
    }
}

// The data structure for connector status.
// It contains remaining time and time in seconds until reset.
class ConnectorStatus {
    private int remaining;
    private int secondsUntilReset;

    public ConnectorStatus() {
    }

    public ConnectorStatus(int remaining, int secondsUntilReset) {
        this.remaining = remaining;
        this.secondsUntilReset = secondsUntilReset;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getSecondsUntilReset() {
        return secondsUntilReset;
    }

    public void setSecondsUntilReset(int secondsUntilReset) {
        this.secondsUntilReset = secondsUntilReset;
    }
}

public class MyTwitterConnectionPool {
    private static HashMap<String, ArrayList<Twitter>> twitterConnectors = new HashMap<String, ArrayList<Twitter>>();
    // <EndPoint, RateLimit>
    private static HashMap<String, RateLimit> rateLimit = new HashMap<String, RateLimit>();

    private static TwitterStream twitterStream = null;

    // each information has five lines of information
    private static final int NUM_LINES = 5;

    //
    // ref:
    // http://stackoverflow.com/questions/28141330/twitter4j-how-to-solve-the-rate-limit-request-of-twitter-api
    //
    // read properties then initialize twitter connectors
    public static void initTweetConnection(String propertiesFileName) {
        BufferedReader br = null;
        try {
            String line = null;
            String[] lines = new String[NUM_LINES];
            int linesIndex = 0;
            br = new BufferedReader(new FileReader(propertiesFileName));

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                if (linesIndex == NUM_LINES) {
                    createAndAddTwitterConnector(lines);
                    linesIndex = 0;
                }
                lines[linesIndex] = line;
                ++linesIndex;
            }
            if (linesIndex == NUM_LINES) {
                createAndAddTwitterConnector(lines);
            }

            for (ArrayList<Twitter> list : twitterConnectors.values()) {
                Util.flog(String.format("MyTwitterConnectionPool = %d\n", list.size()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //
    // ref:
    // http://stackoverflow.com/questions/28141330/twitter4j-how-to-solve-the-rate-limit-request-of-twitter-api
    //
    // create a twitter connector
    private static void createAndAddTwitterConnector(String[] lines) {
        ConfigurationBuilder twitterConfigBuilder = new ConfigurationBuilder();
        String endPoint = null;

        for (int i = 0; i < lines.length; ++i) {
            String[] input = lines[i].split("=");

            if (input[0].equalsIgnoreCase("oauth.endPoint")) {
                endPoint = input[1];
            }
            if (input[0].equalsIgnoreCase("oauth.consumerKey")) {
                twitterConfigBuilder.setOAuthConsumerKey(input[1]);
            }
            if (input[0].equalsIgnoreCase("oauth.consumerSecret")) {
                twitterConfigBuilder.setOAuthConsumerSecret(input[1]);
            }
            if (input[0].equalsIgnoreCase("oauth.accessToken")) {
                twitterConfigBuilder.setOAuthAccessToken(input[1]);
            }
            if (input[0].equalsIgnoreCase("oauth.accessTokenSecret")) {
                twitterConfigBuilder.setOAuthAccessTokenSecret(input[1]);
            }
        }

        twitterConfigBuilder.setDebugEnabled(false);
        twitterConfigBuilder.setJSONStoreEnabled(true);

        if (endPoint.equals("streamAPI")) {
            twitterStream = new TwitterStreamFactory(twitterConfigBuilder.build()).getInstance();
        } else {
            ArrayList<Twitter> twitterList = null;
            if (twitterConnectors.containsKey(endPoint)) {
                twitterList = twitterConnectors.get(endPoint);
            } else {
                twitterList = new ArrayList<Twitter>();
            }

            Twitter twitter = new TwitterFactory(twitterConfigBuilder.build()).getInstance();
            twitterList.add(twitter);

            twitterConnectors.put(endPoint, twitterList);
        }
    }

    // get connector status for given endpoint
    private static ConnectorStatus getTwitterConnectorEndPoint(int index, Twitter twitter, String endPoint) {
        int remaining = 0;
        int secondsUntilReset = GlobalVar.maxResetTime;

        Map<String, RateLimitStatus> map = null;

        try {
            map = twitter.getRateLimitStatus();
        } catch (TwitterException e) {
            if (e.exceededRateLimitation()) {
                remaining = e.getRateLimitStatus().getRemaining();
                secondsUntilReset = e.getRateLimitStatus().getSecondsUntilReset();

                Util.log(String.format(
                        "getTwitterConnectorEndPoint() : (%d) ==========> RATE LIMIT EXCEEDED : %s <==========\n",
                        index, endPoint));
            }
        }

        if (map != null) {
            remaining = map.get(endPoint).getRemaining();
            secondsUntilReset = map.get(endPoint).getSecondsUntilReset();
        }

        Util.log(String.format("getTwitterConnectorEndPoint() : (%d) %s => remaining = %d, secondsUntilReset = %d\n",
                index, endPoint, remaining, secondsUntilReset));

        return new ConnectorStatus(remaining, secondsUntilReset);
    }

    public static TwitterStream getTwitterStreamConnector() {
        return twitterStream;
    }

    public static int getSecondsUntilReset(String endPoint) {
        return rateLimit.get(endPoint).getSecondsUntilReset();
    }

    // get an available twitter connector. if all connectors for endpoint have exceeded it rate limit,
    // wait until reset.
    synchronized public static Twitter getTwitterConnector(String endPoint) {
        ArrayList<Twitter> tmpList = twitterConnectors.get(endPoint);
        if (tmpList == null)
            return null;

        // if endPoint have exceeded rate limit before
        if (rateLimit.containsKey(endPoint)) {
            RateLimit rl = rateLimit.get(endPoint);
            long diff = (System.currentTimeMillis() - rl.getTimeStamp()) / 1000;
            if (diff < rl.getSecondsUntilReset()) {
                return null;
            } else {
                Util.flog(String.format("getTwitterConnector() : waiting for %s is over..!!\n", endPoint));
                rateLimit.remove(endPoint);
            }
        }

        // try to get a twitter connector, if possible
        int i = 0;
        int minResetTime = GlobalVar.maxResetTime;
        for (Twitter tc : tmpList) {
            ConnectorStatus cs = getTwitterConnectorEndPoint(i++, tc, endPoint);
            if (cs.getRemaining() > 0) {
                Util.log(String.format(
                        "getTwitterConnector() => New Twitter Connector(%02d) for %s (remaining = %d) --> ID_Q(%d)\n",
                        i - 1, endPoint, cs.getRemaining(), MyHarvesterInfo.getUserIdQueueSize()));

                return tc;
            } else {
                minResetTime = Math.min(minResetTime, cs.getSecondsUntilReset());
            }
        }

        // all connector has exceeded rate limit
        RateLimit rl = new RateLimit(System.currentTimeMillis(), minResetTime);
        rateLimit.put(endPoint, rl);

        Util.flog(String.format(
                "==========> getTwitterConnector() : reset time : %d => New Twitter Connector for %s : FAILED\n",
                minResetTime, endPoint));

        return null;
    }
}
