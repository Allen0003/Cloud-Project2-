/* 
 * This file defines global variables.
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package com.distributed.aurinscanner;


public class GlobalVar {
    public static final int MELBOURNE = 0;
    public static final int SYDNEY = 1;
    public static final int ADELAIDE = 2;
    public static final int PERTH = 3;
    public static final int BRISBANE = 4;
    public static final int HOBART = 5;
    public static final int DARWIN = 6;

    public static final String[] cityString = { "Melbourne", "Sydney", "Adelaide", "Perth", "Brisbane", "Hobart",
            "Darwin" };

    public static final double[][] boundingBox = {
            // Melbourne
            { 146.810512, -42.076189, 147.066417, -41.895063 },
            // Sydney
            { 150.520929, -34.118347, 151.343021, -33.578141 },
            // Adelaide
            { 138.44213, -35.34897, 138.78019, -34.652564 },
            // Perth
            { 115.617614, -32.675715, 116.239023, -31.624486 },
            // Brisbane
            { 152.668523, -27.767441, 153.31787, -26.996845 },
            // Hobart
            { 147.13366, -43.014123, 147.613383, -42.655376 },
            // Darwin
            { 130.815117, -12.521742, 131.0515, -12.33006 } };

    public static final String searchTweets = "/search/tweets";
    public static final String statusesUser_timeline = "/statuses/user_timeline";
    public static final String followersList = "/followers/list";

    // Rate Limit Exceed : 15 minutes window
    public static final int maxResetTime = 15 * 60;
}
