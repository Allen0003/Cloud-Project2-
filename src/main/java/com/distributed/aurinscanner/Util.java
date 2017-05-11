/* 
 * This file defines utility functions.
 * 
 * It defines log and flog to print out debug message onto the screen.
 *      - log : print out message if debug mode is turned on in settings.
 *      - flog : force print out message
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package com.distributed.aurinscanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class Util {
    public static void log(String message) {
        if (Settings.getDebugMode())
            System.out.printf(message);
    }

    private static void writeToDB(Long id, int city, String rawJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode object = mapper.readTree(rawJson);
            ((ObjectNode) object).put("ccc_city", GlobalVar.cityString[city]);

            MyCouchDbConnector.getCouchDbConnector().create(Long.toString(id), object);

            log(String.format("==========> writeToDB() : %d, %d\n", id, city));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public static boolean saveNewData(Object json, int city) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode object = mapper.createObjectNode();
            Long dataId = object.get("").asLong();

            // with get tag
//            if (Settings.getGeoTagOnly()) {
//                if (object.getGeoLocation() == null)
//                    return false;
//            }

            // only new one
//            if (MyCouchDbConnector.getCouchDbConnector().find(JsonNode.class, Long.toString(tweetId)) == null) {
//                String rawJson = TwitterObjectFactory.getRawJSON(status);
//                if (rawJson == null) {
//                    log(String.format("==========> saveNewData() : %d is NULL\n", tweetId));
//                    return false;
//                }
//                writeToDB(tweetId, city, rawJson);
//                return true;
//            } else {
//                log(String.format("==========> saveNewData() : %d is already in DB\n", tweetId));
//                return false;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

//    public static int getBoundingBox(GeoLocation geo) {
//        if (geo == null)
//            return -1;
//
//        for (int i = 0; i < GlobalVar.boundingBox.length; i++) {
//            if (isInBoundingBox(i, geo))
//                return i;
//        }
//
//        return -1;
//    }

//    public static boolean isInBoundingBox(int city, GeoLocation geo) {
//        if (geo == null)
//            return false;
//
//        double minLong = GlobalVar.boundingBox[city][0];
//        double minLat = GlobalVar.boundingBox[city][1];
//        double maxLong = GlobalVar.boundingBox[city][2];
//        double maxLat = GlobalVar.boundingBox[city][3];
//
//        double longitude = geo.getLongitude();
//        double latitude = geo.getLatitude();
//
//        if (minLong <= longitude && longitude <= maxLong && minLat <= latitude && latitude <= maxLat) {
//            return true;
//        }
//
//        return false;
//    }
}
