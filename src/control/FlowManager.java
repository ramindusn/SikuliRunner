package control;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.Flow;
import data.Model;
import data.Status;
import runner.ScriptRunner;


public class FlowManager {
	private ArrayList<Model> scriptsInOrder;
	private ArrayList<Model> flows;
	private Model[] scripts;
	public Thread sikuliRunningThread;
	private int selectedFlowIndex;

	public void init() throws FileNotFoundException {
		if (scriptsInOrder == null) {
			scriptsInOrder = new ArrayList<>();
		}
		if (flows == null) {
			flows = new ArrayList<>();
		}
		selectedFlowIndex = -1;
		flows = FileManager.getInstance().readList(flows, "order.txt");
		scripts = FileManager.getInstance().getScripts();
	}

	public ArrayList<Model> getScriptsInOrder() {
		return scriptsInOrder;
	}

	public ArrayList<Model> getFlows() {
		return flows;
	}

	public Model[] getScripts() {
		return scripts;
	}

	public String[] getScriptsArray() {
		String[] rtnArray = new String[scripts.length];
		for (int x = 0; x < scripts.length; x++) {
			rtnArray[x] = scripts[x].getName();
		}
		return rtnArray;

	}

	public void deleteFlow(int selectedIndex) throws UnsupportedEncodingException, FileNotFoundException {
		flows.remove(selectedIndex);
		saveFlow();
	}

	public void deleteScript(int selectedIndex) {
		scriptsInOrder.remove(selectedIndex);

	}

	public Model addScriptToList(int index) {
		scriptsInOrder.add(scripts[index]);
		return scripts[index];
	}

	public void clear() {
		if (scriptsInOrder != null) {
			scriptsInOrder.clear();
		}

	}

	public boolean saveScripts() throws UnsupportedEncodingException, FileNotFoundException {
		if (!scriptsInOrder.isEmpty()) {
			if (selectedFlowIndex == -1) {
				return saveNewScripts();
			} else {
				return overideScripts(flows.get(selectedFlowIndex).getName());
			}
		} else {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Please set a flow to save");
			return false;
		}
	}

	public boolean overideScripts(String flowName) throws UnsupportedEncodingException, FileNotFoundException {

		FileManager.getInstance().writeList(scriptsInOrder, flowName);
		saveFlow();
		return true;
	}

	public boolean saveNewScripts() throws UnsupportedEncodingException, FileNotFoundException {
		String flowName = JOptionPane.showInputDialog("Enter flow name :");
		if (flowName != null) {
			flowName = flowName + ".flw";
			Flow newFlow = new Flow(flowName, Status.NOTRUNNING);

			if (!flows.contains(newFlow)) {

				FileManager.getInstance().writeList(scriptsInOrder, flowName);
				flows.add(newFlow);
				saveFlow();
				return true;
			} else
				JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(),
						"A flow with the given name already exist. please choose another name");
			return saveNewScripts();
		} else {
			return false;
		}
	}

	public void saveFlow() throws UnsupportedEncodingException, FileNotFoundException {

		FileManager.getInstance().writeList(flows, "order.txt");

	}

	public void setSelectedFlow(int index) throws FileNotFoundException {

		selectedFlowIndex = index;

		if (scriptsInOrder == null) {
			scriptsInOrder = new ArrayList<>();
		}

		scriptsInOrder.clear();
		scriptsInOrder = FileManager.getInstance().readList(scriptsInOrder, flows.get(index).getName());

	}

	public int moveFlow(String move, int selectedIndex) {

		Model flow;
		if (move.equals("up") && selectedIndex > 0) {
			flow = flows.remove(selectedIndex);
			flows.add(--selectedIndex, flow);
		} else if (move.equals("down") && selectedIndex >= 0 && selectedIndex < flows.size() - 1) {
			flow = flows.remove(selectedIndex);
			flows.add(++selectedIndex, flow);
		}
		return selectedIndex;
	}

	public int moveScript(String move, int selectedIndex) {
		Model script;

		if (move.equals("up") && selectedIndex > 0) {
			resetScriptStatus();
			script = scriptsInOrder.remove(selectedIndex);
			scriptsInOrder.add(--selectedIndex, script);
		} else if (move.equals("down") && selectedIndex >= 0 && selectedIndex < scriptsInOrder.size() - 1) {
			resetScriptStatus();
			script = scriptsInOrder.remove(selectedIndex);
			scriptsInOrder.add(++selectedIndex, script);
		}
		return selectedIndex;
	}

	@SuppressWarnings("deprecation")
	public boolean run(boolean runAll, int selectedIndex) {
 
		if (sikuliRunningThread == null || !sikuliRunningThread.isAlive()) {

			sikuliRunningThread = new Thread(new ScriptRunner(runAll, selectedIndex));
			sikuliRunningThread.start();
			return true;
		} else {
			sikuliRunningThread.stop();

		}
		return false;
	}

	public void resetScriptStatus() {

		for (Model script : scriptsInOrder) {
			script.setStatus(Status.NOTRUNNING);
		}
	}

	public int getSelectedFlowIndex() {
		return selectedFlowIndex;
	}

	public void setSelectedFlowIndex(int selectedFlowIndex) {
		this.selectedFlowIndex = selectedFlowIndex;
	}

}
