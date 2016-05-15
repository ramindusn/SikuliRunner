package control;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class LogManager {
	private static LogManager logManager;
	private ArrayList<String> log;
	private boolean loggingEnabled;
	private String logFileName;
	private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private LogManager() {

		log = new ArrayList<>();
	}

	private void setFileName() {

		logFileName = "SRun_" + Calendar.getInstance().getTime().toString().replaceAll(":", "-") + ".log";
	}

	private String getTime() {

		Date now = new Date();
		return timeFormat.format(now);

	}

	public static LogManager getInstance() {
		if (logManager == null) {
			logManager = new LogManager();
		}

		return logManager;
	}

	public void startLogging() throws FileNotFoundException, UnsupportedEncodingException {
		setFileName();
		try {
			endLogging();
		} catch (FileNotFoundException e) {
			loggingEnabled = false;
			throw e;
		} catch (UnsupportedEncodingException e) {
			loggingEnabled = false;
			throw e;
		}
	}

	public void log(String line) {
		if (loggingEnabled) {
			log.add("[log] " + getTime() + " " + line);
		}
	}

	public void logNewRun() {
		if (loggingEnabled) {
			log.add("======================================================================");
		}
	}

	public void endLogging() throws FileNotFoundException, UnsupportedEncodingException {
		if (loggingEnabled) {
			FileManager.getInstance().writeLog(log, logFileName);
		}
	}

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	public String getLogFileName() {
		return logFileName;
	}

}
