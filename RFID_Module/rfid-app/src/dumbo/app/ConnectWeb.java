package dumbo.app;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectWeb {
	private String ipServer;
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
	}

	public int checkRFID(String rfid) throws ClientProtocolException,
			IOException {
		
		// Request To Server
		
		String result = Request
				.Post(ipServer+"/rfid/attendance").setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.bodyForm(
						Form.form().add("rfidcode", rfid)
								.build()).execute().returnContent().asString();
		// Response From Server
		JSONObject obj = (JSONObject) JSONValue.parse(result);
		System.out.println(obj.get("message"));
		//System.out.println(obj.get("names"));
		// name = obj.get("studentName").toString();
		// listener.nameChanged();
		return 0;
	}
}