package dumbo.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PostFile {
	private String picsPath;
	private String urlPostPic;
	private static Map<String, String> settings = new HashMap<String, String>();
	
	public PostFile() {
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
		picsPath = settings.get("picsPath");
		urlPostPic = settings.get("urlPostPic");
		
	}

	public JSONObject SendFile(String fileName) throws ClientProtocolException,IOException {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost(urlPostPic);
		// String picPath = picsPath + fileName;
		// String picPath = "pics/";
		//File  file = new File(picPath);
		MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
		//mpEntity.addBinaryBody("file", file, ContentType.create("image/jpeg"),fileName);
		httppost.setEntity(mpEntity.build());

		System.out.println("executing request " + httppost.getRequestLine());

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();
		JSONObject obj = (JSONObject) JSONValue.parse(EntityUtils.toString(resEntity));
		//System.out.println(obj.get("message"));
		return obj;
	}
}