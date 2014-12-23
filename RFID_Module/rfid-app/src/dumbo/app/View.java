package dumbo.app;


import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_objdetect;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class View implements Listener {
	
	private JFrame frame;
	private JLabel label;
	private JLabel labelStdID;
	private JLabel labelFName;
	private JLabel labelLName;
	private JLabel labelimage;
	private JLabel labelroom;
	private Controller controller;
	private BufferedImage image;
	private static Map<String, String> settings = new HashMap<String, String>();
	private String room;
	
//	private PicsPanel picsPanel;

	public View(Controller controller) {
		this.controller = controller;
		controller.setListener(this);
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
		// Preload the opencv_objdetect module to work around a known bug.
		//Loader.load(opencv_objdetect.class);
		room = settings.get("room");
//		picsPanel = new PicsPanel();

		frame = new JFrame("DUMBO-PROJECT");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 220);

		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		try {                
			image = ImageIO.read(new File("D:/ProjectSwdev/RFID_Module/img/coe.jpg"));
	    } catch (IOException ex) {
	            // handle exception...
	    }
		labelStdID = new JLabel("---");
		labelStdID.setFont(labelStdID.getFont().deriveFont(20.0f));
		labelStdID.setHorizontalAlignment(JLabel.CENTER);
		labelFName = new JLabel("---");
		labelFName.setFont(labelFName.getFont().deriveFont(20.0f));
		labelFName.setHorizontalAlignment(JLabel.CENTER);
		labelFName.setHorizontalAlignment(SwingConstants.CENTER);
		labelLName = new JLabel("---");
		labelLName.setFont(labelLName.getFont().deriveFont(20.0f));
		labelLName.setHorizontalAlignment(JLabel.CENTER);
		labelroom = new JLabel(room);
		labelroom.setFont(labelroom.getFont().deriveFont(30.0f));
		labelroom.setHorizontalAlignment(JLabel.CENTER);
		labelimage = new JLabel(new ImageIcon(image));
		labelimage.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		JPanel subPanel3 = new JPanel();
		JPanel subPanel4 = new JPanel();
		JPanel subPanel5 = new JPanel();
		
		subPanel1.add(labelStdID);
		subPanel2.add(labelFName);
		subPanel3.add(labelLName);
		subPanel4.add(labelimage);
		subPanel5.add(labelroom);
		
		
//		panel.add(picsPanel, BorderLayout.PAGE_START);
		panel.add(subPanel4, BorderLayout.PAGE_START);
		panel.add(subPanel1, BorderLayout.LINE_START);
		panel.add(subPanel2, BorderLayout.CENTER);
		panel.add(subPanel3, BorderLayout.LINE_END);
		panel.add(subPanel5, BorderLayout.PAGE_END);
		frame.add(panel);
	}

	@Override
	public String modelChanged() {
		if(controller.getIsLoading()){
			labelStdID.setText("");
			labelLName.setText("");
			labelFName.setText("Loading...");
		}else {
			labelStdID.setText(controller.getStdID());
			labelFName.setText(controller.getFName());
			labelLName.setText(controller.getLName());
		}
		return "";
	}

	public void show() {
		frame.setVisible(true);
	}
}
