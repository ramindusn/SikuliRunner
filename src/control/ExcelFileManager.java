package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelFileManager {
	private static ExcelFileManager excelFileManager;
	private FileInputStream excelFile;
	private ArrayList<String[]> dataList;

	private ExcelFileManager() {
	}

	public static ExcelFileManager getInstance() {
		if (excelFileManager == null) {
			excelFileManager = new ExcelFileManager();
		}
		return excelFileManager;
	}

	public void init() throws FileNotFoundException {

		excelFile = new FileInputStream(new File(PropertyFileManager.getInstance().getProperty_Path("ExcelFile")));
	}

	public ArrayList<String[]> readExcel() throws IOException {
		if (dataList == null) {
			dataList = new ArrayList<>();
		} else {
			dataList.clear();
		}

		XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
		XSSFSheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String[] rowData = new String[row.getPhysicalNumberOfCells()];
			for (int count = 0; count < rowData.length; count++) {
				rowData[count] = row.getCell(count).getStringCellValue();
			}
			dataList.add(rowData);
		}
		workbook.close();
		return dataList;

	}
}