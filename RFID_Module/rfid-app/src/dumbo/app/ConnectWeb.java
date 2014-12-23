package dumbo.app;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectWeb {
	private String ipServer;
	private String room;
	private static Map<String, String> settings = new HashMap<String, String>();
	public ConnectWeb() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("settings.properties"));

			for (String key : props.stringPropertyNames()) {
				String value = props.getProperty(key);
				settings.put(key, value);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		ipServer = settings.get("ipServer");
		room = settings.get("room");
	}

	public JSONObject checkRFID(String rfid) throws ClientProtocolException,
			IOException {
		// Request To Server
		JSONObject o = new JSONObject();
//		String result = Request
//				.Post(ipServer+"/api/verify_rfid").setHeader("Content-Type", "application/json")
//				.bodyForm(
//						Form.form().add("rfid", rfid)
//				.build()).execute().returnContent().asString();
		  try {
	            HttpClient c = new DefaultHttpClient();        
	            HttpPost p = new HttpPost(ipServer+"/api/verify_rfid");        
	 
	            p.setEntity(new StringEntity("{\"rfid\":\"" + rfid + "\",\"room\":\""+ room + "\"}", 
	                             ContentType.create("application/json")));
//	            p.setEntity(new StringEntity("{\"rfid\":\"" + rfid + "\"}", 
//                        ContentType.create("application/json")));
	    		//System.out.println("{\"rfid\":\"" + rfid + "\",\"room\":\""+ room + "\"}");
	 
	            HttpResponse r = c.execute(p);
	 
	            BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
	            String line = "";
	            while ((line = rd.readLine()) != null) {
	               //Parse our JSON response               
	               JSONParser j = new JSONParser();
	               o = (JSONObject)j.parse(line);
	               
	       		   System.out.println(o.get("StdID"));
	               System.out.println(line);
	            }
	        }
	        catch(ParseException e) {
	            System.out.println(e);
	        }
	        catch(IOException e) {
	            System.out.println(e);
	        }                     
							
		
		// Response From Server
		//JSONObject obj = (JSONObject) JSONValue.parse(result);
		//System.out.println(obj.get("message"));
		//System.out.println(obj.get("names"));
		// name = obj.get("studentName").toString();
		//listener.nameChanged();
		return o;
	}
}