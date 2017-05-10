package com.crunchify.restjersey.dao;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

public class TweetDao extends CouchDbRepositorySupport<JsonNode> {

	public TweetDao(CouchDbConnector db) {
		super(JsonNode.class, db);
	}

	@View(name = "mel_cycle_1")
	public List<JsonNode> getMelCycleList() {
		return db.queryView(createQuery("mel_cycle_1").includeDocs(true).limit(500), JsonNode.class);
	}

	@View(name = "mel_weekday")
	public List<JsonNode> getMelWeekday() {
		ViewQuery query = new ViewQuery().designDocId("_design/JsonNode").viewName("mel_weekday").includeDocs(true)
				.limit(500);
		return db.queryView(query, JsonNode.class);
	}

	@View(name = "mel_weekend")
	public List<JsonNode> getMelWeekend() {
		ViewQuery query = new ViewQuery().designDocId("_design/JsonNode").viewName("mel_weekend").includeDocs(true)
				.limit(500);
		return db.queryView(query, JsonNode.class);
	}

}