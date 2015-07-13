package edu.uclm.tritype.http;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;


public class Proxy {	
	public static String PREFIX = "--", LINE_END = "\r\n"; 

	private static Proxy yo;
	private String urlServer;

	private Proxy() {
		this.urlServer = "alarcosj.esi.uclm.es";
	}

	public static Proxy get() {
		if (yo==null)
			yo=new Proxy();
		return yo;
	}
	
	public JSONObject postJSONOrderWithResponse(String resource, JSONObject jso) throws JSONException, InterruptedException, ExecutionException {
		PostJSONDataWithResponse pd=new PostJSONDataWithResponse(resource);
		pd.execute(jso);
		JSONObject resp = pd.get();		
		return resp;
	}

	public String getUrlServer() {
		return urlServer;
	}
}
