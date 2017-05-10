
/* 
 * This file defines harvesting mode using streaming API.
 * 
 * It simply add a user id whenever it sees the tweet with a geo location.
 * 
 * 
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class MyStatusListener implements StatusListener {
    public MyStatusListener() {
    }

    public void onStatus(Status status) {
        // add user id into a QUEUE
        MyHarvesterInfo.addUserIdQueue(status.getUser().getId());
    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    }

    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

    public void onException(Exception ex) {
        ex.printStackTrace();
    }

    public void onScrubGeo(long userId, long upToStatusId) {
    }

    public void onStallWarning(StallWarning warning) {
    }
}
