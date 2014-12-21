package dumbo.app;

import grp.dumbo.RFIDController;
import grp.dumbo.RFIDListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;


public class Controller implements RFIDListener {
	private String rfid;
	private String namePic;
	private Listener listener;
	private String comPort;
	private RFIDController rfidController;
	private ConnectWeb connectweb;
//	private PicsPanel picsPanel;
	private PostFile postfile;
	private ExecutorService executorService = Executors
			.newSingleThreadExecutor();
	private int count;
	private JSONObject responseObj;
	private boolean isLoading = false;
	private static Map<String, String> settings = new HashMap<String, String>();
	
	public Controller() {
		count = 0;
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
		comPort = settings.get("comPort");
	}

	public void begin() {
		rfidController = new RFIDController();
		try {
			rfidController.addListener(this);
			rfidController.open(comPort);
			rfidController.begin();
			connectweb = new ConnectWeb();
			postfile = new PostFile();
			// snapPics = new SnapPics();
			while (!rfidController.getClosed()) {
				// System.out.println("idle!");
				if (listener != null) {
//					picsPanel = listener.modelChanged();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			System.out.println(rfidController.getClosed());
			rfidController.close();
		}
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public String getRfid() {
		return rfid;
	}
	
	
	@Override
	public void incomingID(final String id) {
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				rfid = id;
				isLoading = true; 
				try {
//					picsPanel.takeSnap(rfid);
//					while(picsPanel.getNameReady()){
//						System.out.println("bin");
//						try {
//							Thread.sleep(10);
//						} catch (Exception e) {
//						}
//					}

//					namePic = picsPanel.getNamePic();
					
					//int tmp2 = connectweb.checkRFID(id);
					responseObj = postfile.SendFile(namePic);
					isLoading = false;
					System.out.print(responseObj.toString());
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("App(" + count++ + "): " + id);
//				try {
//					Thread.sleep(2000);
//				} catch (Exception e) {
//				}
			}
		});
	}
	public String getMessage(){
		if(responseObj == null)
			return null;
		else
			if(responseObj.containsKey("message"))
				return responseObj.get("message").toString();
			else return "N/A";
	}
	public String getName(){
		if(responseObj == null)
			return null;
		else
			if(responseObj.containsKey("name"))
				return responseObj.get("name").toString();
			else return "N/A";
	}
	public boolean getIsLoading(){
		return isLoading;
	}
	public String getStatus(){
		if(responseObj == null)
			return null;
		else{
			if(responseObj.containsKey("status"))
				return responseObj.get("status").toString();
			else
				return "N/A";
		}	
	}	
}