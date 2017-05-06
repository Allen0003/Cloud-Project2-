package org.ektorp.sample;

import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.ektorp.*;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

public class TweetRepository extends CouchDbRepositorySupport<JsonNode>{

	protected TweetRepository(CouchDbConnector db) {
		super(JsonNode.class, db);
		// TODO Auto-generated constructor stub
	}
	

	@View(name = "mel_cycle",map="function (doc) {\r\n   var str = doc.text.toLowerCase();\r\n   var cycles = [\"cyclist\",\"cycling\",\"cycle\",\"helmet\",\"bike\",\"riding\",\"ride\",\"rider\",\"bicycle\",\"pedaling\",\"pedal\",\"road ride\",\"mtb\",\"push bike\"];\r\n   var i,x;\r\n\r\n   for(i=0;i<cycles.length;i++){\r\n     x=str.indexOf(cycles[i]);\r\n     if(x!=-1 && doc.ccc_city==\"Melbourne\"){\r\n       emit(doc.ccc_city,null);\r\n     }\r\n   }\r\n}",reduce="_count")
    public int geMelCycle() {
        ViewResult r = db.queryView(createQuery("mel_cycle"));
        List<Row> rows = r.getRows();
        if(rows.isEmpty()){
        	return 0;
        }
        return r.getRows().get(0).getValueAsInt();
    }
	
	@View(name="mel_cycle_1")
	public List<JsonNode> getMelCycle1() {
        return db.queryView(createQuery("mel_cycle_1").includeDocs(true),
        		JsonNode.class);
    }

}
