
/* 
 * This file defines harvesting mode using search API.
 * 
 * Each city has one harvester with search API. The setGeoCode() API was used to
 * limit tweet locations with each city.
 * 
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MySearchCollector extends Thread {
    Twitter twitter = null;

    private boolean term = false;
    private long lowestStatusId = Long.MAX_VALUE;

    private int city = 0;
    Query query = null;

    public MySearchCollector(int city) {
        this.city = city;
        query = getSearchQuery(city);
    }

    public void startExecuting() {
        start();
    }

    public void stopExecuting() {
        term = true;
    }

    public static Query getSearchQuery(int city) {
        Query query = new Query();

        // calculate center of the city
        Double[] box = Settings.getCityBoundingBox().get(city);
        double latitude = (box[1] + box[3]) / 2.0;
        double longitude = (box[0] + box[2]) / 2.0;
        GeoLocation location = new GeoLocation(latitude, longitude);

        query.setGeoCode(location, Settings.getSearchAPIRadiusKm(), Query.KILOMETERS);

        Util.flog(String.format("getSecrchQuery() : %s => latitude = %f, longitude = %f\n",
                Settings.getCityString().get(city), latitude, longitude));

        return query;
    }

    public void run() {
        try {
            while (!term) {
                // get twitter connector
                while (true) {
                    twitter = MyTwitterConnectionPool.getTwitterConnector(GlobalVar.searchTweets);
                    if (twitter == null) {
                        Thread.sleep(Settings.getThreadWaitMiliSeconds());
                        continue;
                    }

                    collect();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //
    // ref:
    // http://stackoverflow.com/questions/18800610/how-to-retrieve-more-than-100-results-using-twitter4j
    //
    public void collect() {
        Util.log(String.format("collect() : [%02d]\n", city));

        boolean finished = false;
        while (!finished) {
            QueryResult result = null;

            try {
                result = twitter.search(query);
            } catch (TwitterException e) {
                if (e.exceededRateLimitation()) {
                    Util.log(String.format("collect() : [%02d] ==========> RATE LIMIT EXCEEDED <==========\n", city));
                }
            }

            if (result == null) {
                return;
            }

            List<Status> statuses = result.getTweets();

            for (Status status : statuses) {
                // in the city or not
                int ret = Util.isInCityBoundingBox(city, status);

                if (ret == GlobalVar.NO_GEO_LOCATION) {
                    continue;
                }

                // if we want to collect other cities as well
                if (ret == GlobalVar.NOT_IN_BOUNDING_BOX) {
                    if (Settings.getCollectOtherCities() == false) {
                        continue;
                    }
                }

                // add user id in a QUEUE
                MyHarvesterInfo.addUserIdQueue(status.getUser().getId());

                // capture the lowest (earliest) Status id
                lowestStatusId = Math.min(status.getId(), lowestStatusId);
            }

            // subtracting one here because 'max_id' is inclusive
            query.setMaxId(lowestStatusId - 1);
        }
    }
}
