package dumbo.app;


import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.cpp.opencv_objdetect;

public class View implements Listener {
	
	private JFrame frame;
	private JLabel label;
	private JLabel labelMessage;
	private JLabel labelName;
	private JLabel labelStatus;
	private Controller controller;
//	private PicsPanel picsPanel;

	public View(Controller controller) {
		this.controller = controller;
		controller.setListener(this);
		// Preload the opencv_objdetect module to work around a known bug.
		//Loader.load(opencv_objdetect.class);
		
//		picsPanel = new PicsPanel();

		frame = new JFrame("BANN-POM");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(320, 460);

		Panel panel = new Panel();
		frame.add(panel);
		panel.setLayout(new BorderLayout());

		label = new JLabel("-");
		label.setFont(label.getFont().deriveFont(25.0f));
		label.setHorizontalAlignment(JLabel.CENTER);
		labelMessage = new JLabel("-");
		labelMessage.setFont(labelMessage.getFont().deriveFont(20.0f));
		labelMessage.setHorizontalAlignment(JLabel.CENTER);
		labelName = new JLabel("-");
		labelName.setFont(labelName.getFont().deriveFont(25.0f));
		labelName.setHorizontalAlignment(JLabel.CENTER);
		labelStatus = new JLabel("-");
		labelStatus.setFont(labelName.getFont().deriveFont(20.0f));
		labelStatus.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel subPanel = new JPanel();
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		subPanel.add(label);
		subPanel.add(labelName);
		subPanel1.add(labelMessage);
		subPanel.add(labelStatus);
		
//		panel.add(picsPanel, BorderLayout.PAGE_START);
		panel.add(subPanel, BorderLayout.CENTER);
		panel.add(subPanel2, BorderLayout.PAGE_END);
		panel.add(subPanel1, BorderLayout.AFTER_LAST_LINE);
		// c.add(picsPanel, BorderLayout.LINE_START);
		// c.add(label, BorderLayout.LINE_END);
		// frame.getContentPane().add(c);
	}

//	@Override
//	public PicsPanel modelChanged() {
//		if(controller.getIsLoading()){
//			label.setText("");
//			labelMessage.setText("");
//			labelStatus.setText("");
//			labelName.setText("Loading...");
//		}else {
//			label.setText(controller.getRfid());
//			labelMessage.setText(controller.getMessage());
//			labelName.setText(controller.getName());
//			labelStatus.setText("Status : "+controller.getStatus());
//		}
//		return picsPanel;
//	}

	public void show() {
		frame.setVisible(true);
	}
}
