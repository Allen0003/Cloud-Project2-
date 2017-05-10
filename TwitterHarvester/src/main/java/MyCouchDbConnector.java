
/* 
 * This file defines CouchDB connector.
 * 
 * Each database in a CouchDB server has its own connector for data accessing.
 * 
 * There are three connectors:
 *      - tweet_db: contains tweets posted within Australia, classified as 8 main cities with "ccc_city"
 *      - last_tweet_time_db: contains last tweet time for the given twitter user
 *      - tweet_other_db: contains tweets posted within Australia but not in 8 main cities
 *
 * Connector information is stored in a separated properties file. (ex: couchdb.properties)
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
import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

public class MyCouchDbConnector {
    private static HttpClient httpClient = null;
    private static CouchDbInstance dbInstance = null;

    private static CouchDbConnector tweetDbConnector = null;
    private static CouchDbConnector lastTweetTimeDbConnector = null;
    private static CouchDbConnector tweetOtherCitiesDbConnector = null;

    // each information has five lines of information
    private static final int NUM_LINES = 5;

    // read properties then initialize couchdb connectors
    public static void initCouchDbConnector(String configFileName) {
        BufferedReader br = null;
        try {
            String line = null;
            String[] lines = new String[NUM_LINES];
            int linesIndex = 0;
            br = new BufferedReader(new FileReader(configFileName));

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                if (linesIndex == NUM_LINES) {
                    createCouchDbConnector(lines);
                    linesIndex = 0;
                }
                lines[linesIndex] = line;
                ++linesIndex;
            }
            if (linesIndex == NUM_LINES) {
                createCouchDbConnector(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // create a couchdb connector
    private static void createCouchDbConnector(String[] lines) {
        String target = null;
        String url = null;
        String username = null;
        String password = null;
        String databaseName = null;

        for (int i = 0; i < lines.length; ++i) {
            String[] input = lines[i].split("=");
            if (input[0].equalsIgnoreCase("couchdb.target")) {
                target = input[1];
            }
            if (input[0].equalsIgnoreCase("couchdb.url")) {
                url = input[1];
            }
            if (input[0].equalsIgnoreCase("couchdb.username")) {
                username = input[1];
            }
            if (input[0].equalsIgnoreCase("couchdb.password")) {
                password = input[1];
            }
            if (input[0].equalsIgnoreCase("couchdb.databaseName")) {
                databaseName = input[1];
            }
        }

        Util.flog(String.format("createCouchDbConnector() : %s %s %s %s\n", url, username, password, databaseName));

        try {
            httpClient = new StdHttpClient.Builder().url(url).username(username).password(password).build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (httpClient == null)
            return;

        dbInstance = new StdCouchDbInstance(httpClient);

        if (dbInstance.checkIfDbExists(databaseName) == false) {
            dbInstance.createDatabase(databaseName);
        }

        if (target.equals("tweet")) {
            tweetDbConnector = dbInstance.createConnector(databaseName, true);
        } else if (target.equals("lastTweetTime")) {
            lastTweetTimeDbConnector = dbInstance.createConnector(databaseName, true);
        } else if (target.equals("tweetOtherCities")) {
            tweetOtherCitiesDbConnector = dbInstance.createConnector(databaseName, true);
        }
    }

    // get couchdb connector according to its city
    public static CouchDbConnector getCouchDbConnectorForTweet(int city) {
        if (city >= 0) {
            return tweetDbConnector;
        } else {
            return tweetOtherCitiesDbConnector;
        }
    }

    // get couchdb connector for last_tweet_time_db
    public static CouchDbConnector getCouchDbConnectorForLastTweetTime() {
        return lastTweetTimeDbConnector;
    }
}
