package com.crunchify.restjersey.app;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.crunchify.restjersey.bo.TweetBo;
import com.crunchify.restjersey.util.Const;

import twittersentiment.WekaML;

@Path("/getCycle")
public class TweetService {
	@SuppressWarnings("unchecked")
	public Response getData(List<JsonNode> rawDatas, int length) throws Exception {
		WekaML util = new WekaML();

		JSONArray ja = new JSONArray();
		JSONObject json = null;
		length = length == Const.showAll ? rawDatas.size() : length;
		for (int i = 0; i < length; i++) {
			JsonNode rawData = rawDatas.get(i);
			JsonNode arrNode = rawData.get("coordinates").get("coordinates");

			String address = "";
			ArrayList<String> tempAddress = new ArrayList<String>();
			if (arrNode.isArray()) {
				for (JsonNode objNode : arrNode) {
					tempAddress.add(objNode.toString());
				}
			}
			// reverse the array
			if (tempAddress.size() != 0) {
				address += tempAddress.get(1) + "," + tempAddress.get(0);
			}
			json = new JSONObject();
			json.put("address", address);
			json.put("class", getEmotion(util.predict(rawData.get("text").toString(), false)));
			ja.add(json);
		}

		return Response.status(200).entity(ja.toJSONString()).build();
	}

	public Response getData(List<JsonNode> rawDatas) throws Exception {
		return getData(rawDatas, Const.showAll);
	}

	// FIXME GG
	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response getCycle(@PathParam("f") int f) throws Exception {
		TweetBo bo = new TweetBo(Const.Tweet);
		return new TweetService().getData(bo.getMelCycleList(), f);
	}

	public String getEmotion(String input) {
		if (input.equals("0")) {
			return "bad";
		} else if (input.equals("4")) {
			return "happy";
		} else { // 2
			return "soso";
		}
	}
}