/* 
 * This file defines the interface to mapreduce function in tweet_db database
 *
 * Authers: Team 10
 *      Ziren Wang, zirenw@student.unimelb.edu.au, 720128
 *      Xiang Xiang, xxiang2@student.unimelb.edu.au, 720138
 *      Yongchul Kim, yongchulk@student.unimelb.edu.au, 750659
 *      Own Daghagheleh, odaghagheleh@student.unimelb.edu.au, 816273
 *      Wen Pin Wu, w.wu47@student.unimelb.edu.au, 871702
 */

package twitteranalyzer;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.ektorp.*;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;


public class TweetRepository extends CouchDbRepositorySupport<JsonNode>{

	protected TweetRepository(CouchDbConnector db) {
		super(JsonNode.class, db);
	}
	

	@View(name = "cycle_b1")
    public int getCycleB1() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/CycleB")
        .viewName("cycle_b1");
        ViewResult r = db.queryView(query);
        
        List<Row> rows = r.getRows();
        if(rows.isEmpty()){
        	return 0;
        }
        return r.getRows().get(0).getValueAsInt();
    }
	
	@View(name = "all_b1")
    public int getB1() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/AreaAllTweet")
        .viewName("all_b1");
        ViewResult r = db.queryView(query);
        
        List<Row> rows = r.getRows();
        if(rows.isEmpty()){
        	return 0;
        }
        return r.getRows().get(0).getValueAsInt();
    }
	
	
	@View(name="mel_weekday")
	public List<JsonNode> getMelWeekday() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/JsonNode")
        .viewName("mel_weekday")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }
	
	@View(name="mel_weekend")
	public List<JsonNode> getMelWeekend() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/JsonNode")
        .viewName("mel_weekend")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }
	
	@View(name="mel_mon")
	public List<JsonNode> getMelMon() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/Sentiment")
        .viewName("mel_mon")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }
	
	@View(name="mel_tue")
	public List<JsonNode> getMelTue() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/Sentiment")
        .viewName("mel_tue")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }
	
	@View(name="mel_wed")
	public List<JsonNode> getMelWed() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/Sentiment")
        .viewName("mel_wed")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }
	
	@View(name="mel_fri")
	public List<JsonNode> getMelFri() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/Sentiment")
        .viewName("mel_fri")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }
	
	@View(name="mel_sat")
	public List<JsonNode> getMelSat() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/Sentiment")
        .viewName("mel_sat")
        .includeDocs(true).limit(500);
        return db.queryView(query,JsonNode.class);
    }

}

