package com.crunchify.restjersey.bo;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import com.crunchify.restjersey.dao.TweetDao;
import com.crunchify.restjersey.util.Const;

public class TweetBo {

	TweetDao tweetDao = null;
	CouchDbConnector conn = null;

	public TweetBo(int db) throws Exception {
		if (db == Const.Traffic) {
			conn = getCouchDbConnector(Const.TrafficDBName);
		} else if (db == Const.Tweet) {
			conn = getCouchDbConnector(Const.TweetDBName);
		}
	}

	public List<JsonNode> getMelCycleList() {
		if (tweetDao == null) {
			tweetDao = new TweetDao(conn);
		}
		return tweetDao.getMelCycleList();
	}

	public List<JsonNode> getMelWeekday() {
		if (tweetDao == null) {
			tweetDao = new TweetDao(conn);
		}
		return tweetDao.getMelWeekday();
	}

	public List<JsonNode> getMelWeekend() {
		if (tweetDao == null) {
			tweetDao = new TweetDao(conn);
		}
		return tweetDao.getMelWeekend();
	}

	public CouchDbConnector getCouchDbConnector(String dbName) throws Exception {
		return new StdCouchDbInstance(new StdHttpClient.Builder().url(Const.DBUrl).username(Const.DBName)
				.password(Const.DBPw).socketTimeout(0).build()).createConnector(dbName, true);
	}

}
