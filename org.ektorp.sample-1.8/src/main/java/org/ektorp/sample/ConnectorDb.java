package org.ektorp.sample;

import java.net.MalformedURLException;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

public class ConnectorDb {
	 public static CouchDbConnector createCouchDbConnector() throws MalformedURLException{
			HttpClient authenticatedHttpClient = new StdHttpClient.Builder().url("http://127.0.0.1:5984").username("admin").password("1234").socketTimeout(0).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
			// if the second parameter is true, the database will be created if it doesn't exists
			CouchDbConnector tweet_test = null;
			tweet_test = dbInstance.createConnector("tweet_test", true);
			return tweet_test;
	 }
	 
	 public static CouchDbConnector createCouchDbConnectorTest() throws MalformedURLException{
			HttpClient authenticatedHttpClient = new StdHttpClient.Builder().url("http://130.56.253.207:5984").username("admin").password("cloudProject").socketTimeout(0).build();
			CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
			// if the second parameter is true, the database will be created if it doesn't exists
			CouchDbConnector tweet_test = null;
			tweet_test = dbInstance.createConnector("tweet_db", true);
			return tweet_test;
	 }

}
