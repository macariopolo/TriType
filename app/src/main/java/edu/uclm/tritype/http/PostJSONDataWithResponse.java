package edu.uclm.tritype.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class PostJSONDataWithResponse extends AsyncTask<JSONObject, Void, JSONObject> {

	private String resourceURL;

	public PostJSONDataWithResponse(String resourceURL) {
		this.resourceURL=resourceURL;
	}

	@Override
	protected JSONObject doInBackground(JSONObject... jso) {
        InputStream inputStream = null;
        JSONObject result=null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            URI uri=new URI("http", 
            		Proxy.get().getUrlServer(),
            		"/tritype/" + this.resourceURL, 
            		"command=" + jso[0].toString(),
            		null);
            String sURI=uri.toASCIIString();

            HttpPost httpPost = new HttpPost(sURI);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = getJSONObject(inputStream);
            else
                result = new JSONObject();
 
        } catch (Exception e) {
        	Log.e("MACOMACO", e.toString());
            result=new JSONObject();
            try {
            	result.put("result", "Failure");
				result.put("msg", e.getMessage());
			} catch (JSONException e1) {
				Log.e("MACOMACO", e1.toString());
			}
        }
        return result;
	}

	private JSONObject getJSONObject(InputStream inputStream) throws IOException, JSONException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String sResult = "";
		while((line = bufferedReader.readLine()) != null)
			sResult += line;

		inputStream.close();
		JSONObject result=new JSONObject(sResult);
		return result;
	}
}