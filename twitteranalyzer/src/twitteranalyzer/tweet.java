/* 
 * This file declares how to use the interface to views in couchdb and calculate the sentiment score of tweets
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package twitteranalyzer;

import org.ektorp.CouchDbConnector;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import twitteranalyzer.ConnectorDb;
import twitteranalyzer.TweetRepository;

import twittersentiment.WekaML;

public class tweet {
	
	private static CouchDbConnector tweet_test; 
	private static CouchDbConnector accident_connect;
	private static CouchDbConnector volume_connect;

	
	public static void main(String[] args)  throws MalformedURLException{
	    
		tweet_test=ConnectorDb.createCouchDbConnectorTweet();
		TweetRepository tweet_re_test= new TweetRepository(tweet_test);
		
		accident_connect=ConnectorDb.createCouchDbConnectorAccident();
		TrafficRepository accident_conn= new TrafficRepository(accident_connect);
		
		volume_connect=ConnectorDb.createCouchDbConnectorVolume();
		TrafficRepository volume_conn= new TrafficRepository(volume_connect);
		
		//sentiment data part
		List<JsonNode> doc_weekday = new ArrayList<JsonNode>();
		doc_weekday=tweet_re_test.getMelWeekday();
		double avg_weekday=countSentiment(doc_weekday);
		System.out.println("****The average sentiment of Weekday is "+avg_weekday+"****");
		
		List<JsonNode> doc_weekend = new ArrayList<JsonNode>();
		doc_weekend=tweet_re_test.getMelWeekend();
		double avg_weekend=countSentiment(doc_weekend);
		System.out.println("****The average sentiment of Weekend is "+avg_weekend+"****");
		
		//cycle data part
		int sumCycleB1=tweet_re_test.getCycleB1();
		System.out.println("****The total number of tweets including cycle keywords in B1 is "+sumCycleB1+"****");
		int sumB1=tweet_re_test.getB1();
		System.out.println("****The total number of tweets in B1 is "+sumB1+"****");
		
		//traffic data part
		int accidentB1=accident_conn.getAccidentB1();
		System.out.println("****The total number of accidents in B1 is "+accidentB1+"****");
		int volumnB1=volume_conn.getVolumeB1();
		System.out.println("****The total volumns in B1 is "+volumnB1+"****");
		
		System.out.println("****Success****");
	}
	
	//calculate sentiment score
	public static double countSentiment(List<JsonNode> doc_week){
		WekaML weka = new WekaML();
	    int sen= 0;
		int sum_week=0,count_week=0;
		double avg_week=0.0;
		for (JsonNode doc_s : doc_week) {
			String text=doc_s.get("text").toString().replaceAll("\"", "");
			String sentiment = weka.predict(text,false);
			sen=Integer.parseInt(sentiment);
			sum_week=sum_week+sen;
			count_week=count_week+1;
		}
		avg_week=((double)sum_week)/count_week;
		return avg_week;
	}
	
    

}
