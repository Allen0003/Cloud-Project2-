
1.Tweet.java 
Main function, you can learn about how to call the function in this file

2.ConnectorDb.java
The interface to connect to database

3.TweetRepository.java(tweet_db database related API)
getMelCycle():The interface to get totally how many tweets that are posted in Melbourne and includes keywords about cycle
getMelCycle1():The interface to get 500 tweets that are posted in Melbourne and includes keywords about cycle
getMelWeekday():The interface to get 500 tweets that are posted in Melbourne and in the weekday
getMelWeekend():The interface to get 500 tweets that are posted in Melbourne and in the weekend


4.TrafficRepository.java(black_spots_2007-2012_vic related API)
getMelTraffic(): The interface to get all traffic accidents in Melbourne
The attribute "accidents" is useful. You can use this attribute to show the frequency of car crash on the map. 

p.s. When you doing the test, do not use tweet_db database which is the biggest database, cause we are still adding tweets to it, so we have not created view in this database yet 
We recommend you to use black_spots_2007-2012_vic to do the test, because it is not growing now and we have already created view in this database
p.s. We use mapreduce to get data from database

CouchDb address: http://130.56.253.207:5984/_utils
