
/* 
 * This file defines main function to collect tweets by using streaming API and search API.
 * 
 * It needs three files which is provided by command line options.
 *      - h.config: contains settings to initialize harvesting mode
 *      - couchdb.properties: contains couchdb connection information
 *      - twitter4j.properties: contains twitter connection information
 *      
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;

public class TwitterHarvester {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.printf("harvester.jar h.config couchdb.properties twitter4j.properties\n");
            return;
        }

        Settings.initSettings(args[0]);
        Settings.setCouchDbProperties(args[1]);
        Settings.setTwitterProperties(args[2]);

        collectTweets();
    }

    public static void collectTweets() {
        //
        // init
        //

        // twitter connection pool
        MyTwitterConnectionPool.initTweetConnection(Settings.getTwitterProperties());

        // couch db
        MyCouchDbConnector.initCouchDbConnector(Settings.getCouchDbProperties());

        //
        // start harvesting
        //

        int mode = Settings.getHarvestingMode();
        if (mode == GlobalVar.STREAM_API) {
            streamAPI();
        } else if (mode == GlobalVar.SEARCH_API) {
            searchAPI();
        }
    }

    public static void searchAPI() {
        for (int i = 0; i < Settings.getCityString().size(); i++) {
            MySearchCollector collector = new MySearchCollector(i);

            collector.startExecuting();
        }

        // collector thread
        MyTimelineCollector[] tlcollector = new MyTimelineCollector[Settings.getNumSearchThread()];
        for (int i = 0; i < Settings.getNumSearchThread(); i++) {
            tlcollector[i] = new MyTimelineCollector(i);
            tlcollector[i].startExecuting();
        }
    }

    public static void streamAPI() {
        // listener
        MyStatusListener listener = new MyStatusListener();

        // collector thread
        MyTimelineCollector[] tlcollector = new MyTimelineCollector[Settings.getNumStreamThread()];
        for (int i = 0; i < Settings.getNumStreamThread(); i++) {
            tlcollector[i] = new MyTimelineCollector(i);
            tlcollector[i].startExecuting();
        }

        // query - in Australia
        FilterQuery filter = new FilterQuery();
        filter.locations(Settings.getCountryBoundingBox());

        // TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        TwitterStream twitterStream = MyTwitterConnectionPool.getTwitterStreamConnector();
        twitterStream.addListener(listener);
        twitterStream.filter(filter);
    }
}
