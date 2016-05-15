package util;

import org.sikuli.script.Runner;

import control.Handler;

public class CambioRunner extends Runner implements Runnable {

	private void initialize() {
		initpy();
	}

	@Override
	public void run() {
		
		Handler.getInstance().getMainFrame().getFlowsPanel().setStatus("Setting up environment...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
		Handler.getInstance().getMainFrame().getFlowsPanel().enableRun(true);
		Handler.getInstance().getMainFrame().getFlowsPanel().setStatus("Done");
	}

}
