package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;
import data.Flow;
import data.Model;
import data.Script;
import data.Status;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author TWijayawardhana
 *
 */
public class FileManager {
	private static FileManager fileManager;
	private File folder;
	private Map<String, Image> images;
	public static final String dd = "";

	protected FileManager() {
	}

	public static FileManager getInstance() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		return fileManager;
	}

	public void init() throws FileNotFoundException, IOException, NullPointerException {
		images = new HashMap<>();
		readImages();
		PropertyFileManager.getInstance().init();

	}

	private void readImages() throws IOException {
		images.put("up", ImageIO.read(new File("icons/up.png")));
		images.put("down", ImageIO.read(new File("icons/down.png")));
		images.put("icon", ImageIO.read(new File("icons/icon.png")));

	}

	public Image getImage(String name) {
		return images.get(name);
	}

	public Model[] getScripts() {
		folder = new File(PropertyFileManager.getInstance().getProperty_Path("sikuliScriptDir"));

		String[] scripts = folder.list();
		if (scripts == null) {
			throw new NullPointerException(
					"\nScripts are not found in: " + PropertyFileManager.getInstance().getProperty_Path("sikuliScriptDir")
							+ " (The system cannot find path specified)");
		}
		Model[] rtnArray = new Model[scripts.length];
		for (int x = 0; x < scripts.length; x++) {
			rtnArray[x] = new Script(scripts[x], Status.NOTRUNNING);
		}
		return rtnArray;
	}

	public ArrayList<Model> readList(ArrayList<Model> list, String file) throws FileNotFoundException {

		Scanner fileScanner = new Scanner(
				new File(PropertyFileManager.getInstance().getProperty_Path("sikuliFlowsDir") + file));
		while (fileScanner.hasNext()) {

			list.add(new Flow(fileScanner.nextLine(), Status.NOTRUNNING));
		}
		fileScanner.close();
		return list;
	}

	public void writeList(ArrayList<Model> list, String file)
			throws UnsupportedEncodingException, FileNotFoundException {

		PrintWriter writer = new PrintWriter(PropertyFileManager.getInstance().getProperty_Path("sikuliFlowsDir") + file,
				"UTF-8");
		for (Model s : list) {
			writer.println(s.getName());
		}
		writer.close();

	}

	public void saveFlow(ArrayList<Model> list, String fileName)
			throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(PropertyFileManager.getInstance().getProperty_Path("sikuliFlowsDir") + fileName,
				"UTF-8");
		for (Model s : list) {
			writer.println(s.getName());
		}
		writer.close();

	}

	public void writeLog(ArrayList<String> log, String logFile)
			throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(PropertyFileManager.getInstance().getProperty_Path("sikuliLogDir") + logFile,
				"UTF-8");
		for (String s : log) {
			writer.println(s);
		}
		writer.close();

	}

}
