package control;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import gui.MainFrame;
import util.CambioRunner;


public class Handler {
	private static Handler handler;
	private FlowManager flowManager;
	private MainFrame mainFrame;

	public static void main(String[] args) {
		try {
			Thread initThread = new Thread(new CambioRunner());
			 
			Handler handler = Handler.getInstance();
			FileManager.getInstance().init();
			handler.initFlowManager();
			LogManager.getInstance().setLoggingEnabled(true);
			handler.initMainFrame();
			initThread.start();
			handler.getMainFrame().setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(),
					"Couldn't initialized the application. An Error has occured : " + e, "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();

		}
	}

	private Handler() {
	}

	public static Handler getInstance() {
		if (handler == null) {
			handler = new Handler();
		}
		return handler;
	}

	private void initMainFrame() throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		mainFrame = new MainFrame();
		mainFrame.init();
	}

	public void initFlowManager() throws FileNotFoundException {
		flowManager = new FlowManager();
		flowManager.init();
	}

	public FlowManager getFlowManager() {
		return flowManager;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

}
