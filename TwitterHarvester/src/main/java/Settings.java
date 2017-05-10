
/* 
 * This file defines settings which is used to initialize the harvester.
 * 
 * Settings are stored in a separated file. (ex: h.config)
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

public class Settings {
    private static boolean debug = false;

    // 1 for SearchAPI, 2 for StreamAPI
    private static int harvestingMode = 1;

    private static boolean geoTagOnly = true;
    private static boolean usePlaceTag = false;
    private static boolean addFriendsFollowers = true;
    private static boolean collectOtherCities = true;

    private static int numStreamThread = 50;
    private static int numSearchThread = 50;
    private static int timelinePageLimit = 50;
    private static int threadWaitMiliSeconds = 3000;
    private static int searchAPIRadiusKm = 70;
    private static int pagingCount = 100;

    private static int maxErrorTweets = 100;

    private static int maxRateLimitResetTime = 900;

    private static String twitterProperties = "twitter4j.properties";
    private static String couchDbProperties = "couchdb.properties";

    private static ArrayList<String> cityString = new ArrayList<String>();
    private static ArrayList<Double[]> cityBoundingBox = new ArrayList<Double[]>();
    // initial value => Australia + some regions within box
    private static double[][] countryBoundingBox = { { 112.4858, -44.1527 }, { 156.4341, -9.2681 } };

    private static final int NUM_LINES = 2;

    //
    // ------------------------------------------------------------------------------------------
    //

    public static void initSettings(String configFileName) {
        BufferedReader br = null;
        try {
            String line = null;
            br = new BufferedReader(new FileReader(configFileName));

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                String[] input = line.split("=");

                if (input[0].equalsIgnoreCase("harvester.debug")) {
                    debug = Boolean.parseBoolean(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.harvestingMode")) {
                    harvestingMode = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.geoTagOnly")) {
                    geoTagOnly = Boolean.parseBoolean(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.usePlaceTag")) {
                    usePlaceTag = Boolean.parseBoolean(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.addFriendsFollowers")) {
                    addFriendsFollowers = Boolean.parseBoolean(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.collectOtherCities")) {
                    collectOtherCities = Boolean.parseBoolean(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.numStreamThread")) {
                    numStreamThread = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.numSearchThread")) {
                    numSearchThread = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.timelinePageLimit")) {
                    timelinePageLimit = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.pagingCount")) {
                    pagingCount = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.searchAPIRadiusKm")) {
                    searchAPIRadiusKm = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.maxErrorTweets")) {
                    maxErrorTweets = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.maxRateLimitResetTime")) {
                    maxRateLimitResetTime = Integer.parseInt(input[1]);
                }
                if (input[0].equalsIgnoreCase("harvester.numCity")) {
                    int numCity = Integer.parseInt(input[1]);
                    int numReadCity = setTargetCities(br, numCity);
                    if (numReadCity != numCity) {
                        Util.flog(String.format(
                                "initSettings() => ERROR reading city info : numCity = %d, numReadCity = %d\n", numCity,
                                numReadCity));
                    }
                }
                if (input[0].equalsIgnoreCase("harvester.countryBoundingBox")) {
                    Double[] bdBox = getBoundingBoxFromString(input[1]);

                    countryBoundingBox[0][0] = bdBox[0];
                    countryBoundingBox[0][1] = bdBox[1];
                    countryBoundingBox[1][0] = bdBox[2];
                    countryBoundingBox[1][1] = bdBox[3];

                    Util.flog(String.format("Country boundingBox = %f %f %f %f\n", bdBox[0], bdBox[1], bdBox[2],
                            bdBox[3]));
                }
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

        // Util.flog(String.format("boundingBox = %s\n", boundingBox.toString()));
    }

    private static int setTargetCities(BufferedReader br, int numCity) {
        int numReadCity = 0;
        try {
            String line = null;
            String[] lines = new String[NUM_LINES];
            int linesIndex = 0;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }

                if (linesIndex == NUM_LINES) {
                    setTargetCity(lines);
                    linesIndex = 0;
                    numReadCity++;
                }
                lines[linesIndex] = line;
                ++linesIndex;
            }
            if (linesIndex == NUM_LINES) {
                setTargetCity(lines);
                numReadCity++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numReadCity;
    }

    private static void setTargetCity(String[] lines) {
        for (int i = 0; i < lines.length; ++i) {
            String[] input = lines[i].split("=");

            if (input[0].equalsIgnoreCase("harvester.cityName")) {
                cityString.add(input[1]);

                Util.flog(String.format("cityName = %s\n", input[1]));
            }
            if (input[0].equalsIgnoreCase("harvester.cityBoundingBox")) {
                Double[] bdBox = getBoundingBoxFromString(input[1]);
                cityBoundingBox.add(bdBox);

                Util.flog(String.format("boundingBox = %f %f %f %f\n", bdBox[0], bdBox[1], bdBox[2], bdBox[3]));
            }
        }
    }

    private static Double[] getBoundingBoxFromString(String box) {
        String[] bBox = box.replace("{", "").replace("}", "").replace("\\s+", "").split(",");
        Double[] bdBox = new Double[4];
        for (int j = 0; j < bBox.length; j++) {
            bdBox[j] = Double.parseDouble(bBox[j]);
        }

        return bdBox;
    }

    // ----------------------------------------------------------------------------------------------------

    public static ArrayList<String> getCityString() {
        return cityString;
    }

    public static ArrayList<Double[]> getCityBoundingBox() {
        return cityBoundingBox;
    }

    public static int getMaxRateLimitResetTime() {
        return maxRateLimitResetTime;
    }

    public static void setMaxRateLimitResetTime(int maxRateLimitResetTime) {
        Settings.maxRateLimitResetTime = maxRateLimitResetTime;
    }

    public static int getHarvestingMode() {
        return harvestingMode;
    }

    public static void setHarvestingMode(int harvestingMode) {
        Settings.harvestingMode = harvestingMode;
    }

    public static int getMaxErrorTweets() {
        return maxErrorTweets;
    }

    public static void setMaxErrorTweets(int maxErrorTweets) {
        Settings.maxErrorTweets = maxErrorTweets;
    }

    public static int getPagingCount() {
        return pagingCount;
    }

    public static void setPagingCount(int pagingCount) {
        Settings.pagingCount = pagingCount;
    }

    public static int getSearchAPIRadiusKm() {
        return searchAPIRadiusKm;
    }

    public static void setSearchAPIRadiusKm(int searchAPIRadiusKm) {
        Settings.searchAPIRadiusKm = searchAPIRadiusKm;
    }

    public static String getTwitterProperties() {
        return twitterProperties;
    }

    public static void setTwitterProperties(String twitterProperties) {
        Settings.twitterProperties = twitterProperties;
    }

    public static String getCouchDbProperties() {
        return couchDbProperties;
    }

    public static void setCouchDbProperties(String couchDbProperties) {
        Settings.couchDbProperties = couchDbProperties;
    }

    public static void setDebugMode(boolean debug) {
        Settings.debug = debug;
    }

    public static boolean getDebugMode() {
        return debug;
    }

    public static void setGeoTagOnly(boolean geoTagOnly) {
        Settings.geoTagOnly = geoTagOnly;
    }

    public static boolean getGeoTagOnly() {
        return geoTagOnly;
    }

    public static void setAddFriendsFollowers(boolean friendsFollowers) {
        Settings.addFriendsFollowers = friendsFollowers;
    }

    public static boolean getAddFriendsFollowers() {
        return addFriendsFollowers;
    }

    public static double[][] getCountryBoundingBox() {
        return countryBoundingBox;
    }

    public static void setNumStreamThread(int numStreamThread) {
        if (numStreamThread <= 0)
            return;

        Settings.numStreamThread = numStreamThread;
    }

    public static int getNumStreamThread() {
        return numStreamThread;
    }

    public static void setNumSearchThread(int numSearchThread) {
        if (numSearchThread <= 0)
            return;

        Settings.numSearchThread = numSearchThread;
    }

    public static int getNumSearchThread() {
        return numSearchThread;
    }

    public static void setTimelinePageLimit(int timelinePageLimit) {
        Settings.timelinePageLimit = timelinePageLimit;
    }

    public static int getTimelinePageLimit() {
        return timelinePageLimit;
    }

    public static int getThreadWaitMiliSeconds() {
        return threadWaitMiliSeconds;
    }

    public static void setUsePlaceTag(boolean usePlaceTag) {
        Settings.usePlaceTag = usePlaceTag;
    }

    public static boolean getUsePlaceTag() {
        return usePlaceTag;
    }

    public static boolean getCollectOtherCities() {
        return collectOtherCities;
    }

    public static void setCollectOtherCities(boolean collectOtherCities) {
        Settings.collectOtherCities = collectOtherCities;
    }
}
