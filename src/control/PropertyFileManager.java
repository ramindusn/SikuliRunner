package control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class PropertyFileManager {
	private static PropertyFileManager propertyFileManager;
	private Properties properties;
	ArrayList<String> propertyList;

	private PropertyFileManager() {
		initList();
	}

	public static PropertyFileManager getInstance() {

		if (propertyFileManager == null) {
			propertyFileManager = new PropertyFileManager();
		}
		return propertyFileManager;
	}

	public void init() throws FileNotFoundException, IOException, NullPointerException {

		properties = new Properties();
		properties.load(new FileInputStream("properties.properties"));
		for (String property : propertyList) {

			if (!properties.containsKey(property)) {
				throw new NullPointerException("Property: " + property + " is missing from the file");
			}
		}
	}

	private void initList() {

		if (propertyList == null) {
			propertyList = new ArrayList<>();
		} else {
			propertyList.clear();
		}

		propertyList.add("sikuliScriptDir");
		propertyList.add("sikuliFlowsDir");
		propertyList.add("sikuliLogDir");
		propertyList.add("open_ColdCUTS_fileName");
		propertyList.add("open_HotCUTS_fileName");
		propertyList.add("ExcelFile");
		propertyList.add("zip_inputDir");
		propertyList.add("zip_outputDir");
		propertyList.add("CUTS_selection");
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty_Path(String key) {
		return properties.getProperty(key).replaceAll("\\\\", "/") + "/";
	}
}
