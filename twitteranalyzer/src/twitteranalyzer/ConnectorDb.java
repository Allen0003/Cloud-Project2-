/* 
 * This file defines the connector to the database in couchdb
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package twitteranalyzer;
import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
public class ConnectorDb {
	
	 public static CouchDbConnector createCouchDbConnectorTweet() throws MalformedURLException{
			HttpClient authenticatedHttpClient = new StdHttpClient.Builder().url("http://130.56.253.207:5984").username("admin").password("cloudProject").socketTimeout(0).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
			CouchDbConnector tweet_test = null;
			tweet_test = dbInstance.createConnector("tweet_db", true);
			return tweet_test;
	 }
	 
	 public static CouchDbConnector createCouchDbConnectorAccident() throws MalformedURLException{
			HttpClient authenticatedHttpClient = new StdHttpClient.Builder().url("http://130.56.253.207:5984").username("admin").password("cloudProject").socketTimeout(0).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
			CouchDbConnector accident_connect = null;
			accident_connect = dbInstance.createConnector("black_spots_2007-2012_vic", true);
			return accident_connect;
	 }
	 
	 public static CouchDbConnector createCouchDbConnectorVolume() throws MalformedURLException{
			HttpClient authenticatedHttpClient = new StdHttpClient.Builder().url("http://130.56.253.207:5984").username("admin").password("cloudProject").socketTimeout(0).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
			CouchDbConnector volume_connect = null;
			volume_connect = dbInstance.createConnector("victorian_road_traffic_volumes", true);
			return volume_connect;
	 }
}
