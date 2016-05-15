package runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;
import control.Handler;
import control.PropertyFileManager;

/**
 * 
 * @author TWijayawardhana
 *
 */
public class ZipRunner implements Runnable {
	private List<File> fileList;
	private File sourceFolder;

	public ZipRunner(List<File> fileList, File sourceFolder) {

		this.fileList = fileList;
		this.sourceFolder = sourceFolder;
	}

	@Override
	public void run() {

		try {
			zipFiles();
			Handler.getInstance().getMainFrame().getFlowsPanel().enableZip(true);
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "CUTS files have been zipped");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "File missing " + e, "Error",
					JOptionPane.ERROR_MESSAGE);

			e.printStackTrace();
			Handler.getInstance().getMainFrame().getFlowsPanel().enableZip(true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Zip failed " + e, "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			Handler.getInstance().getMainFrame().getFlowsPanel().enableZip(true);
		}

	}

	private void zipFiles() throws FileNotFoundException, IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(
				PropertyFileManager.getInstance().getProperty_Path("zip_outputDir")
						+ Calendar.getInstance().getTime().toString().replaceAll(":", "-") + "_CUTS.zip");
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

		for (File file : fileList) {
			if (!file.isDirectory()) {
				addToZip(file, zipOutputStream);
			}

		}

		zipOutputStream.close();
		fileOutputStream.close();

	}

	private void addToZip(File file, ZipOutputStream zipOutputStream) throws FileNotFoundException, IOException {

		FileInputStream fileInputStream = new FileInputStream(file);

		String zipFilePath = file.getCanonicalPath().substring(sourceFolder.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zipOutputStream.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fileInputStream.read(bytes)) >= 0) {
			zipOutputStream.write(bytes, 0, length);
		}

		fileInputStream.close();
	}
}
