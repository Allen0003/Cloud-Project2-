
/* 
 * This file defines utility functions.
 * 
 * It defines log and flog to print out debug message onto the screen.
 *      - log : print out message if debug mode is turned on in settings.
 *      - flog : force print out message
 *      
 * All tweets are saved using saveNewTweet method.
 * 
 * Geometry related methods also defined in this file.
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

public class Util {
    // print out debug message, if debug mode is on
    public static void log(String message) {
        if (Settings.getDebugMode()) {
            System.out.printf(message);
        }
    }

    // force print out message
    public static void flog(String message) {
        System.out.printf(message);
    }

    // write one tweet into couchdb
    private static void writeToDB(Long tweetId, int city, String rawJson) {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode object = null;
        try {
            object = mapper.readTree(rawJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cityString = null;
        try {
            cityString = Settings.getCityString().get(city);
        } catch (IndexOutOfBoundsException e) {
            cityString = "other";
        }
        ((ObjectNode) object).put("ccc_city", cityString);

        // create() : _id, object
        MyCouchDbConnector.getCouchDbConnectorForTweet(city).create(Long.toString(tweetId), object);
    }

    // save tweet by using writeToDB private method
    synchronized public static int saveNewTweet(Status status, int city) {
        Long tweetId = status.getId();

        // with geo tag
        if (Settings.getGeoTagOnly()) {
            if (Util.getGeoLocation(status) == null)
                return GlobalVar.NO_GEO_LOCATION;
        }

        // only new one
        // contains() : _id
        if (MyCouchDbConnector.getCouchDbConnectorForTweet(city).contains(Long.toString(tweetId)) == false) {
            String rawJson = TwitterObjectFactory.getRawJSON(status);
            if (rawJson == null) {
                return GlobalVar.NO_RAW_JSON;
            } else {
                writeToDB(tweetId, city, rawJson);
                return GlobalVar.WRITE_DB_SUCCESS;
            }
        } else {
            return GlobalVar.TWEET_ALREADY_EXIST;
        }
    }

    // get index of city
    public static int getCity(Status status) {
        for (int i = 0; i < Settings.getCityBoundingBox().size(); i++) {
            int ret = isInCityBoundingBox(i, status);
            if (ret == GlobalVar.IN_BOUNDING_BOX) {
                return i;
            } else if (ret == GlobalVar.NO_GEO_LOCATION) {
                return GlobalVar.NO_GEO_LOCATION;
            }
        }

        return GlobalVar.NOT_IN_BOUNDING_BOX;
    }

    // check whether tweet is posted in one of the cities defined in settings
    public static int isInCityBoundingBox(int city, Status status) {
        GeoLocation geo = getGeoLocation(status);
        if (geo == null) {
            return GlobalVar.NO_GEO_LOCATION;
        }

        // check for city
        Double[] boundingBox = Settings.getCityBoundingBox().get(city);

        double minLong = boundingBox[0];
        double minLat = boundingBox[1];
        double maxLong = boundingBox[2];
        double maxLat = boundingBox[3];

        double longitude = geo.getLongitude();
        double latitude = geo.getLatitude();

        if (minLong <= longitude && longitude <= maxLong && minLat <= latitude && latitude <= maxLat) {
            return GlobalVar.IN_BOUNDING_BOX;
        }

        return GlobalVar.NOT_IN_BOUNDING_BOX;
    }

    // check whether tweet is posted in the country defined in settings
    public static int isCountryBoundingBox(Status status) {
        GeoLocation geo = getGeoLocation(status);
        if (geo == null) {
            return GlobalVar.NO_GEO_LOCATION;
        }

        double[][] bBox = Settings.getCountryBoundingBox();

        double minLong = bBox[0][0];
        double minLat = bBox[0][1];
        double maxLong = bBox[1][0];
        double maxLat = bBox[1][1];

        double longitude = geo.getLongitude();
        double latitude = geo.getLatitude();

        if (minLong <= longitude && longitude <= maxLong && minLat <= latitude && latitude <= maxLat) {
            return GlobalVar.IN_BOUNDING_BOX;
        }

        return GlobalVar.NOT_IN_BOUNDING_BOX;
    }

    // get geo location from the tweet status
    private static GeoLocation getGeoLocation(Status status) {
        GeoLocation geo = status.getGeoLocation();
        if (geo != null) {
            return geo;
        } else {
            // check place field
            if (Settings.getUsePlaceTag()) {
                GeoLocation[][] bbox = null;
                try {
                    bbox = status.getPlace().getBoundingBoxCoordinates();
                } catch (NullPointerException e) {
                    return null;
                }

                // find the center of bounding box
                double maxLatitude = -1000000;
                double minLatitude = 1000000;
                double maxLongitude = -1000000;
                double minLongitude = 1000000;

                for (int i = 0; i < bbox[0].length; i++) {
                    GeoLocation box = bbox[0][i];
                    maxLatitude = Math.max(maxLatitude, box.getLatitude());
                    minLatitude = Math.min(minLatitude, box.getLatitude());
                    maxLongitude = Math.max(maxLongitude, box.getLongitude());
                    minLongitude = Math.min(minLongitude, box.getLongitude());
                }

                double latitude = (maxLatitude + minLatitude) / 2;
                double longitude = (maxLongitude + minLongitude) / 2;

                Util.log(String.format("getGeoLocation() : (%d) place => %f, %f\n", bbox[0].length, latitude,
                        longitude));

                return new GeoLocation(latitude, longitude);
            }
        }

        return null;
    }
}
