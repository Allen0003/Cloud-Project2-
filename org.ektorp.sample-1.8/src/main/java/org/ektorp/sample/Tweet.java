package org.ektorp.sample;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.ektorp.*;
import org.codehaus.jackson.*;
import org.ektorp.sample.ConnectorDb;;

public class Tweet {
	private static List<String> doc_ids = new ArrayList<String>();
	//private static List<JsonNode> doc_all = new ArrayList<JsonNode>();
	
	private static CouchDbConnector tweet_test; 

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
			tweet_test=ConnectorDb.createCouchDbConnector();
			//tweet_test = dbInstance.createConnector("tweet_test", true);
			System.out.println("****Test0****");
			doc_ids = tweet_test.getAllDocIds();
			/*for (String doc_id : doc_ids) {
				JsonNode doc_s = tweet_test.get(JsonNode.class, doc_id);
				System.out.println(doc_s.get("ccc_city").toString().replaceAll("\"", ""));
			}*/
			System.out.println("****Test1****");
			List<JsonNode> doc_mel=getAllMelbourneTweet();
			for (int i = 0; i < doc_mel.size(); i++) {
				String ccc_city=doc_mel.get(i).get("ccc_city").toString().replaceAll("\"", "");
				String text = doc_mel.get(i).get("text").toString().replaceAll("\"", "").toLowerCase();
				System.out.println("ccc_city:"+ccc_city);
				System.out.println("text:"+text);
			}
			System.out.println("****Success****");
	}
	
	public static List<JsonNode> getAllMelbourneTweet(){
		List<JsonNode> doc_mel = new ArrayList<JsonNode>();
		String[] cycle = {"cyclist","cycling","cycle","helmet","bike","riding","ride","rider","bicycle","pedaling","pedal","road ride","mtb","push bike"};
		//doc_ids = tweet_test.getAllDocIds();
		for (String doc_id : doc_ids) {
			JsonNode doc_s = tweet_test.get(JsonNode.class, doc_id);
			String ccc_city=doc_s.get("ccc_city").toString().replaceAll("\"", "");
			String text = doc_s.get("text").toString().replaceAll("\"", "").toLowerCase();
			if(ccc_city.equals("Melbourne")){
				for (String c: cycle) {           
			        //Do your stuff here
			        if(text.indexOf(c)>=0){
			        	doc_mel.add(doc_s);
			        	break;
			        } 
			    }
				//doc_mel.add(doc_s);
			}
		}
		return doc_mel;
	}
	
	

}
