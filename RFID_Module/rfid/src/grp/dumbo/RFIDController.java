package grp.dumbo;

import java.util.ArrayList;
import java.util.Iterator;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * 
 * @author Student
 */
public class RFIDController {

	private boolean closed;
	ArrayList<RFIDListener> rfidListener = new ArrayList<RFIDListener>();
	private SerialPort serialPort;
	private String RFID;

	public void open(String portName) throws Exception {
		closed = false;
		// String[] portNames = SerialPortList.getPortNames();
		// for (int i = 0; i < portNames.length; i++) {
		// System.out.println(portNames[i]);
		// }

		serialPort = new SerialPort(portName);

		try {
			System.out.println("Port opened: " + serialPort.openPort());
			System.out.println("Params setted: "
					+ serialPort.setParams(9600, 8, 1, 0));
			System.out
					.println("\"Hello World!!!\" successfully writen to port: "
							+ serialPort.writeBytes("Hello World!!!".getBytes()));
		} catch (SerialPortException ex) {
			System.out.println("Error : " + ex);
		}
	}

	public void close() {
		closed = true;
		// close serial port
	}

	public boolean getClosed() {
		return closed;
	}

	public void addListener(RFIDListener listener) {
		rfidListener.add(listener);
	}

	public void removeListener(RFIDListener listener) {
		rfidListener.remove(listener);
	}

	public String getRFID() {
		return RFID;
	}

	public void begin() {
		new Worker().start();
	}

	class Worker extends Thread {
		@Override
		public void run() {
			String tmp = null;
			while (!closed) {
				try {
					while (!closed) {
						do {
							tmp = serialPort.readString(1);
						} while (tmp.charAt(0) != 2);
						if (tmp.charAt(0) == 2) {
							RFID = serialPort.readString(12);
							do {
								tmp = serialPort.readString(1);
							} while (tmp.charAt(0) != 3);
							break;
						}
					}
				} catch (SerialPortException e) {
					e.printStackTrace();
				}

				Iterator<RFIDListener> iter = rfidListener.iterator();
				while (iter.hasNext()) {
					RFIDListener listener = iter.next();
					if (listener != null)
						listener.incomingID(RFID);
				}

			}
		}
	}

}
