package org.ektorp.sample;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

public class TrafficRepository extends CouchDbRepositorySupport<JsonNode>{
	
	protected TrafficRepository(CouchDbConnector db) {
		super(JsonNode.class, db);
		// TODO Auto-generated constructor stub
	}
	
	@View(name="mel_traffic")
	public List<JsonNode> getMelTraffic() {
        return db.queryView(createQuery("mel_traffic").includeDocs(true),
        		JsonNode.class);
    }

}
