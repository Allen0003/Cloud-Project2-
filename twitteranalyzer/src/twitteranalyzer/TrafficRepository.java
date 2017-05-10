/* 
 * This file defines the interface to mapreduce function in black_spots_2007-2012_vic database and victorian_road_traffic_volumes database
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
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;

public class TrafficRepository extends CouchDbRepositorySupport<JsonNode>{
	
	protected TrafficRepository(CouchDbConnector db) {
		super(JsonNode.class, db);
	}
	
	@View(name = "mel_b1")
    public int getAccidentB1() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/MelB")
        .viewName("mel_b1");
        ViewResult r = db.queryView(query);
        
        List<Row> rows = r.getRows();
        if(rows.isEmpty()){
        	return 0;
        }
        return r.getRows().get(0).getValueAsInt();
    }
	
	@View(name = "vol_b1")
    public int getVolumeB1() {
		ViewQuery query = new ViewQuery()
        .designDocId("_design/view2")
        .viewName("vol_b1");
        ViewResult r = db.queryView(query);
        
        List<Row> rows = r.getRows();
        if(rows.isEmpty()){
        	return 0;
        }
        return r.getRows().get(0).getValueAsInt();
    }
	

}
