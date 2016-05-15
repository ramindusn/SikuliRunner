package runner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import control.ExcelFileManager;
import control.FlowManager;
import control.Handler;
import control.LogManager;
import control.PropertyFileManager;
import data.Model;
import data.Status;
import gui.MainFrame;
import util.CambioRunner;


public class ScriptRunner implements Runnable {

	private FlowManager flowManager;
	private MainFrame mainFrame;
	private ArrayList<Model> scriptsInOrder;
	private ArrayList<Model> flows;
	private int selectedIndex;
	private boolean runAll;

	public ScriptRunner(boolean runAll, int selectedIndex) {

		this.selectedIndex = selectedIndex;
		this.runAll = runAll;
		this.flowManager = Handler.getInstance().getFlowManager();
		this.mainFrame = Handler.getInstance().getMainFrame();
		scriptsInOrder = flowManager.getScriptsInOrder();
		flows = flowManager.getFlows();

	}

	public void run() {
		mainFrame.getFlowsPanel().setStatus("Running...");
		flowManager.resetScriptStatus();
		boolean result = false;
		LogManager.getInstance().logNewRun();
		LogManager.getInstance().log("Run all = " + runAll);
		if (!runAll) {
			result = runScript(flows.get(selectedIndex).getName());
		} else {
			for (int x = selectedIndex; x < flows.size(); x++) {
				LogManager.getInstance().log("started flow : " + flows.get(x).getName());
				mainFrame.getFlowsPanel().setSelectedFlow(x);
				scriptsInOrder = flowManager.getScriptsInOrder();
				result = runScript(flows.get(x).getName());
				if (!result) {
					break;
				}
			}
		}

		if (result) {
			endflow();
			JOptionPane.showMessageDialog(mainFrame, "Flow is completed");
		}
		mainFrame.getFlowsPanel().flowCompleted();
	}

	private boolean runScript(String flowName) {
		flowName = flowName.substring(0, flowName.length() - 4);

		for (int x = 0; x < scriptsInOrder.size(); x++) {
			LogManager.getInstance().log("Started : " + scriptsInOrder.get(x).getName());
			scriptsInOrder.get(x).setStatus(Status.RUNNING);
			mainFrame.getScriptsPanel().repaint();
			int out = -1;
			String[] args = new String[1];

			if (PropertyFileManager.getInstance().getProperty("open_ColdCUTS_fileName")
					.equals(scriptsInOrder.get(x).getName())
					|| PropertyFileManager.getInstance().getProperty("open_HotCUTS_fileName")
							.equals(scriptsInOrder.get(x).getName())) {
				System.out.println("in :" + flowName + ":");
				args[0] = flowName;
				out = CambioRunner.run(PropertyFileManager.getInstance().getProperty_Path("sikuliScriptDir")
						+ scriptsInOrder.get(x).getName(), args);

			} else if (isALoop(scriptsInOrder.get(x).getName())) {
				LogManager.getInstance().log("started to loop");
				out = loop(scriptsInOrder.get(x).getName());
			} else {
				out = CambioRunner.run(PropertyFileManager.getInstance().getProperty_Path("sikuliScriptDir")
						+ scriptsInOrder.get(x).getName());

			}
			if (out == -1) {
				LogManager.getInstance().log("Failed : " + scriptsInOrder.get(x).getName());
				scriptsInOrder.get(x).setStatus(Status.FAILED);
				mainFrame.getScriptsPanel().repaint();
				endflow();
				JOptionPane.showMessageDialog(mainFrame, "Script '" + scriptsInOrder.get(x).getName() + "' failed",
						"Script failed", JOptionPane.ERROR_MESSAGE);

				return false;
			}
			LogManager.getInstance().log("Success : " + scriptsInOrder.get(x).getName());
			scriptsInOrder.get(x).setStatus(Status.SUCCESS);
			mainFrame.getScriptsPanel().repaint();
		}
		return true;

	}

	private int loop(String script) {

		try {
			ExcelFileManager.getInstance().init();
			ArrayList<String[]> list = ExcelFileManager.getInstance().readExcel();
			LogManager.getInstance().log("No. of rows:" + list.size() + "No. of columns:" + list.get(0).length);

			for (String[] args : list) {
				CambioRunner.run(PropertyFileManager.getInstance().getProperty_Path("sikuliScriptDir") + script, args);
				String logString = "";
				for (String logStr : args) {
					logString += logStr + " ";
				}
				LogManager.getInstance().log("Entered:" + logString);
			}
			return 1;
		} catch (FileNotFoundException e) {
			LogManager.getInstance().log("loop failed");
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			LogManager.getInstance().log("loop failed");
			e.printStackTrace();
			return -1;
		}
	}

	private boolean isALoop(String script) {

		if (script.substring(0, 5).equalsIgnoreCase("loop_")) {
			return true;
		} else {
			return false;
		}

	}

//	 private boolean runCUTSOpen(String flowName, Model script, CUTS run) {
//	 Screen s = new Screen();
//	 flowName = flowName.substring(0, flowName.length() - 4);
//	 try {
//	 s.wait(2.0);
//	 s.type(Key.F12, Key.CTRL);
//	 String image = FileManager.getInstance().getSikuliImagesDir();
//	 s.wait(image + "CUTS_wait.png", 20);
//	 s.type(flowName + "_" + run + ".cuts");
//	 s.type(Key.TAB);
//	 s.type(Key.ENTER);
//	 LogManager.getInstance().log("Success : " + script.getName());
//	 script.setStatus(Status.SUCCESS);
//	 mainFrame.getScriptsPanel().repaint();
//	 return true;
//	
//	 } catch (FindFailed e) {
//	 LogManager.getInstance().log("Failed : " + script.getName());
//	 script.setStatus(Status.FAILED);
//	 mainFrame.getScriptsPanel().repaint();
//	 return false;
//	 }
//	
//	 }

	private void endflow() {

		mainFrame.setState(MainFrame.NORMAL);
		mainFrame.getFlowsPanel().setStatus("Completed");

	}
}
