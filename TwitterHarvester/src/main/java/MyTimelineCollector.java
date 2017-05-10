
/* 
 * This file defines timeline collector thread.
 * 
 * Each thread sees the QUEUE periodically.
 * if there is a user id, patch it then start to collect timeline of the user.
 * 
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MyTimelineCollector extends Thread {
    Twitter twitter = null;

    int threadId = 0;

    private boolean term = false;

    // ----------------------------------------------------------------------------------------------------

    public MyTimelineCollector(int threadId) {
        this.threadId = threadId;
    }

    public void startExecuting() {
        start();
    }

    public void stopExecuting() {
        term = true;
    }

    public void run() {
        try {
            while (!term) {
                // get userId
                Long userId = MyHarvesterInfo.removeUserIdQueue();
                if (userId == -1) {
                    Thread.sleep(Settings.getThreadWaitMiliSeconds());
                    continue;
                }

                // get twitter connector
                boolean success = false;
                while (!success) {
                    twitter = MyTwitterConnectionPool.getTwitterConnector(GlobalVar.statusesUser_timeline);
                    if (twitter == null) {
                        Thread.sleep(Settings.getThreadWaitMiliSeconds());
                        continue;
                    } else {
                        break;
                    }
                }

                success = getTweets(userId);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean getTweets(long userId) {
        Util.flog(String.format("getTweets() : [%d] Thread ID = %02d => START\n", userId, threadId));

        // for checking tweet time
        long recentTweetTime = new Date(0).getTime();
        long lastTweetTime = -1;

        JsonNode json = MyCouchDbConnector.getCouchDbConnectorForLastTweetTime().find(JsonNode.class,
                Long.toString(userId));
        if (json != null) {
            // if (MyHarvesterInfo.hasUserIdHashMap(userId)) {
            // lastTweetTime = MyHarvesterInfo.getLastDateHashMap(userId);
            lastTweetTime = json.path("last_tweet_time").asLong();
        } else {
            Util.log(String.format("getTweets() : [%d] NEW users arrived\n", userId));
        }

        int numTotalSavedTweet = 0;
        int numTotalErrorTweet = 0;
        int pageno = 1;
        int userMainCity = GlobalVar.NOT_IN_BOUNDING_BOX;

        while (true) {
            Paging page = new Paging(pageno++, Settings.getPagingCount());

            ResponseList<Status> list = null;
            try {
                list = twitter.getUserTimeline(userId, page);
            } catch (TwitterException e) {
                if (e.exceededRateLimitation()) {
                    Util.log(String.format("getTweets() : [%d] ==========> RATE LIMIT EXCEEDED <==========\n", userId));
                }
            }

            if (list == null) {
                break;
            }

            // get recent tweet time
            for (int i = 0; i < list.size(); i++) {
                Date createdDate = ((Status) list.get(i)).getUser().getCreatedAt();
                recentTweetTime = Math.max(recentTweetTime, createdDate.getTime());
            }

            // if last tweet time (saved) is greater than or equal to recent tweet time
            if (lastTweetTime != -1 && lastTweetTime >= recentTweetTime) {
                Util.flog(String.format("getTweets() : [%d] already saved\n", userId));
                // continue;
                break;
            }

            //
            // store to database
            //
            int numSavedTweet = 0;
            int numErrorTweet = 0;
            for (int i = 0; i < list.size(); i++) {
                Status status = (Status) list.get(i);

                // which city bounding box
                int city = Util.getCity(status);

                if (city == GlobalVar.NO_GEO_LOCATION) {
                    continue;
                }

                // if we want to collect other cities as well
                if (city == GlobalVar.NOT_IN_BOUNDING_BOX) {
                    if (Settings.getCollectOtherCities() == false) {
                        continue;
                    }

                    if (Util.isCountryBoundingBox(status) != GlobalVar.IN_BOUNDING_BOX) {
                        continue;
                    }
                }

                // new tweet only
                int ret = Util.saveNewTweet(status, city);
                if (ret == GlobalVar.WRITE_DB_SUCCESS) {
                    userMainCity = city;
                    numSavedTweet++;
                } else {
                    numErrorTweet++;
                }
            }

            if (numErrorTweet > 0) {
                numTotalErrorTweet += numErrorTweet;
                Util.flog(String.format("getTweets() : [%d] => error tweet(s) : %d \n", userId, numErrorTweet));
            }

            if (numSavedTweet > 0) {
                numTotalSavedTweet += numSavedTweet;
                Util.flog(String.format("getTweets() : [%d] => saved tweet(s) : %d \n", userId, numSavedTweet));
            }

            //
            // STOP CONDITIONS : We don't have enough time to collect tweets.
            //

            // if there is no tweet with geo tag in pageno == 1, stop
            // -> pageno is increased so check with 2
            if (pageno == 2 && numSavedTweet == 0) {
                Util.log(String.format("getTweets() : [%d] => no tweet is in the cities for the top %d tweets\n",
                        userId, list.size()));
                break;
            }

            // if no. of error tweets (duplication, no geo tag) is
            // greater than no. of maximum allowable and no. saved is zero, stop
            if (numTotalSavedTweet == 0 && numTotalErrorTweet > Settings.getMaxErrorTweets()) {
                Util.log(String.format("getTweets() : [%d] => no. error tweets exceed the limit : %d\n", userId,
                        Settings.getMaxErrorTweets()));
                break;
            }

            // if pageno is larger than or equal to page limit, stop
            if (Settings.getTimelinePageLimit() <= pageno) {
                Util.log(String.format("getTweets() : [%d] => page limit exceed : %d/%d\n", userId, pageno,
                        Settings.getTimelinePageLimit()));
                break;
            }

            // if no more tweet exists, stop
            if (list.size() != Settings.getPagingCount()) {
                Util.log(String.format("getTweets() : [%d] => list.size() = %d\n", userId, list.size()));
                break;
            }
        }

        // save recent tweet time to last tweet time
        if (numTotalSavedTweet > 0) {
            ObjectMapper mapper = new ObjectMapper();

            String cityString = null;
            try {
                cityString = Settings.getCityString().get(userMainCity);
            } catch (IndexOutOfBoundsException e) {
                cityString = "other";
            }

            JsonNode jsonTime = MyCouchDbConnector.getCouchDbConnectorForLastTweetTime().find(JsonNode.class,
                    Long.toString(userId));
            if (jsonTime == null) {
                String objectStr = String.format("{\"last_tweet_time\" : %d, \"ccc_city\" : \"%s\"}", recentTweetTime,
                        cityString);
                JsonNode actualObj = null;
                try {
                    actualObj = mapper.readTree(objectStr);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // create() : _id, object
                MyCouchDbConnector.getCouchDbConnectorForLastTweetTime().create(Long.toString(userId), actualObj);

                Util.flog(
                        String.format("getTweets() : [%d] => create last tweet time to %d\n", userId, recentTweetTime));
            } else {
                ((ObjectNode) jsonTime).remove("last_tweet_time");
                ((ObjectNode) jsonTime).put("last_tweet_time", recentTweetTime);
                ((ObjectNode) jsonTime).put("ccc_city", cityString);

                MyCouchDbConnector.getCouchDbConnectorForLastTweetTime().update(jsonTime);

                Util.flog(
                        String.format("getTweets() : [%d] => update last tweet time to %d\n", userId, recentTweetTime));
            }
        }

        Util.flog(String.format("getTweets() : [%d] Thread ID = %02d => END\n", userId, threadId));

        return true;
    }
}
