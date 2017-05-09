package org.ektorp.sample;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.*;
import org.codehaus.jackson.*;
import org.ektorp.sample.ConnectorDb;;

public class Tweet {
	//private static List<String> doc_ids = new ArrayList<String>();
	private static CouchDbConnector tweet_test; 
	private static CouchDbConnector traffic_test; 

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		
		// TODO Auto-generated method stub
			//HttpClient authenticatedHttpClient = new StdHttpClient.Builder().url("http://127.0.0.1:5984").username("admin").password("1234").socketTimeout(0).build();
			//CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
			// if the second parameter is true, the database will be created if it doesn't exists
			//CouchDbConnector tweet_test = null;
			//tweet_test = dbInstance.createConnector("tweet_test", true);
			System.out.println("****Test Tweet****");
		
		tweet_test=ConnectorDb.createCouchDbConnectorTweet();
		TweetRepository tweet_re_test= new TweetRepository(tweet_test);
		
		int n=tweet_re_test.geMelCycle();
		System.out.println(n);
		System.out.println("****Test 1 ends****");
		
		List<JsonNode> doc_all = new ArrayList<JsonNode>();
		doc_all=tweet_re_test.getMelCycleCity();
		for (JsonNode doc_s : doc_all) {
			System.out.println(doc_s.get("text").toString().replaceAll("\"", ""));
		}
		System.out.println("****Test 2 ends****");
		
		List<JsonNode> doc_weekday = new ArrayList<JsonNode>();
		doc_weekday=tweet_re_test.getMelWeekday();
		for (JsonNode doc_s : doc_weekday) {
			System.out.println(doc_s.get("text").toString().replaceAll("\"", "")+" "+doc_s.get("created_at"));
		}
		System.out.println("****Test 3 ends****");
		
		List<JsonNode> doc_weekend = new ArrayList<JsonNode>();
		doc_weekend=tweet_re_test.getMelWeekend();
		for (JsonNode doc_s : doc_weekend) {
			System.out.println(doc_s.get("text").toString().replaceAll("\"", "")+" "+doc_s.get("created_at"));
		}
		System.out.println("****Test 4 ends****");
/*			doc_ids = tweet_test.getAllDocIds();*/
			/*for (String doc_id : doc_ids) {
				JsonNode doc_s = tweet_test.get(JsonNode.class, doc_id);
				System.out.println(doc_s.get("ccc_city").toString().replaceAll("\"", ""));
			}*/
			/*System.out.println("****Test Traffic****");
			traffic_test=ConnectorDb.createCouchDbConnectorTraffic();
			TrafficRepository traffic_re_test= new TrafficRepository(traffic_test);
			List<JsonNode> doc_mel_traffic = new ArrayList<JsonNode>();
			doc_mel_traffic=traffic_re_test.getMelTraffic();
			for (JsonNode doc_s : doc_mel_traffic) {
				System.out.println(doc_s.get("accidents"));
			}*/
			
			System.out.println("****Success****");
	}

}
