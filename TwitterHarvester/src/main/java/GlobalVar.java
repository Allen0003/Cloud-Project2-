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

public class GlobalVar {
    // endpoint
    public static final String searchTweets = "/search/tweets";
    public static final String statusesUser_timeline = "/statuses/user_timeline";
    public static final String followersList = "/followers/list";

    // Rate Limit : 15 minutes window
    public static final int maxResetTime = 15 * 60;

    // harvesting mode
    public static final int STREAM_API = 1;
    public static final int SEARCH_API = 2;

    // positive situations
    public static final int IN_BOUNDING_BOX = 10000;
    public static final int WRITE_DB_SUCCESS = IN_BOUNDING_BOX + 2;

    // negative situations
    public static final int NO_GEO_LOCATION = -10000;
    public static final int NOT_IN_BOUNDING_BOX = NO_GEO_LOCATION - 1;
    public static final int NO_RAW_JSON = NO_GEO_LOCATION - 2;
    public static final int TWEET_ALREADY_EXIST = NO_GEO_LOCATION - 3;
}
