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

package com.distributed.aurinscanner;

public class Settings {
    
    private static boolean debug = true;
    private static boolean geoTagOnly = true;
    private static String couchDbProperties = "couchdb.properties";

    public static double[][] getAustraliaBoundingBox() {
        // from portal.aurin.org.au
        // Australia = {{112.4858, -44.1527}, {156.4341, -9.2681}}
        double[][] boundingBox = { { 112.4858, -44.1527 }, { 156.4341, -9.2681 } };

        return boundingBox;
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
    public static String getCouchDbProperties() {
        return couchDbProperties;
    }

    public static void setCouchDbProperties(String couchDbProperties) {
        Settings.couchDbProperties = couchDbProperties;
    }
}
