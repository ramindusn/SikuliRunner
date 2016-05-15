package control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import runner.ZipRunner;

public class ZipFileManager {

	private static ZipFileManager zipFileManager;
	private List<File> fileList;
	private File sourceFolder;

	private ZipFileManager() {
	}

	public static ZipFileManager getInstance() {
		if (zipFileManager == null) {
			zipFileManager = new ZipFileManager();
		}
		return zipFileManager;
	}

	public boolean zipCUTS() throws IOException, NullPointerException {

		readFiles(getSelection());
		if (!fileList.isEmpty()) {
			Thread zipRunner = new Thread(new ZipRunner(fileList, sourceFolder));
			zipRunner.start();
			return true;
		} else {
			return false;
		}
	}

	private String[] getSelection() {
		return PropertyFileManager.getInstance().getProperty("CUTS_selection").trim().split("&");

	}

	private String getPath() {

		String[] path = PropertyFileManager.getInstance().getProperty_Path("zip_inputDir").split("%");
		return path[0] + System.getProperty("user.name") + path[2];
	}

	private void readFiles(String[] selection) throws NullPointerException {

		if (fileList == null) {
			fileList = new ArrayList<File>();
		} else {
			fileList.clear();
		}
		sourceFolder = new File(getPath());
		try {

			for (File file : sourceFolder.listFiles()) {
				for (String end : selection) {

					if (file.getName().endsWith(end.trim())) {

						fileList.add(file);

					}
				}

			}
		} catch (NullPointerException e) {
			throw new NullPointerException("\n" + PropertyFileManager.getInstance().getProperty_Path("zip_inputDir")
					+ " (The system cannot find path specified)");
		}

	}

}
