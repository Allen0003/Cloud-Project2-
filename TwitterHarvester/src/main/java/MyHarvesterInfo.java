
/* 
 * This file defines QUEUE of the twitter users for collecting their timeline.
 * 
 * After searching a tweet by streaming API and search API, the twitter user id and followers (20) of
 * that twitter user are recored in the QUEUE.
 * 
 * Then, each Timeline thread(MyTimelineCollector) gets user id from this QUEUE then starts to collect timeline.
 * 
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class MyHarvesterInfo {
    private static Twitter twitter = null;

    private static Queue<Long> userIdQueue = new LinkedList<Long>();

    // ----------------------------------------------------------------------------------------------------

    // SYNCHRONIZED
    synchronized static public void addUserIdQueue(Long userId) {
        userIdQueue.add(userId);

        Util.log(String.format("addUserIdQueue() : [%d] => %d\n", userId, userIdQueue.size()));

        // because the rate limit of /followers/list is so small, 15 API requests per 15
        // minutes, we add follower list if there is an available twitter connector for it.
        if (Settings.getHarvestingMode() == GlobalVar.STREAM_API && Settings.getAddFriendsFollowers()) {
            twitter = MyTwitterConnectionPool.getTwitterConnector(GlobalVar.followersList);
            if (twitter != null) {
                addFriendsFollowers(userId);
            }
        }
    }

    // SYNCHRONIZED
    synchronized static public Long removeUserIdQueue() {
        Long userId = (long) -1;
        if (userIdQueue.size() > 0) {
            userId = userIdQueue.remove();
        }

        if (userId != -1) {
            Util.log(String.format("removeUserIdQueue() : [%d] => %d\n", userId, userIdQueue.size()));
        }

        return userId;
    }

    static public int getUserIdQueueSize() {
        return userIdQueue.size();
    }

    public static void addFriendsFollowers(Long userId) {
        List<User> followersList = null;

        try {
            long cursor = -1;
            followersList = twitter.getFollowersList(userId, cursor);
        } catch (TwitterException e) {
            if (e.exceededRateLimitation()) {
                Util.log(String.format("addFriendsFollowers() : [%d] ==========> RATE LIMIT <==========\n", userId));
            }
        }

        if (followersList == null)
            return;

        Util.log(String.format("==> followers = %d\n", followersList.size()));

        for (int i = 0; i < followersList.size(); i++) {
            userIdQueue.add(followersList.get(i).getId());
        }

        Util.log(String.format("addFriendsFollowers() : [%d] => %d\n", userId, userIdQueue.size()));
    }
}
