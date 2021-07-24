package testCase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Testing {

	public static void main(String[] args) throws IOException {
		ArrayList<String> value = getdata();
		System.out.println(value);
	}

	public static ArrayList<String> getdata() throws IOException {
		ArrayList<String> a = new ArrayList<String>();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/testData/fbLoginCredentials.xlsx");
		// Read the entire excel file
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		// Get the total number of sheets
		int totalSheets = workbook.getNumberOfSheets();
		// Loop through all sheets
		for (int i = 0; i < totalSheets; i++) {
			// Check the sheetName matches or not
			String sheetName = workbook.getSheetName(i);
			if (workbook.getSheetName(i).equalsIgnoreCase("fbLoginCredentials")) {
				// Get access to sheet
				// Sheet is the collection of rows
				XSSFSheet sheet = workbook.getSheetAt(i);
				// Row is the collection of cells
				Iterator<Row> rows = sheet.iterator();
				// get the first row
				Row firstRow = rows.next();

				Row secondRow = rows.next();

				Iterator<Cell> secondCell = secondRow.cellIterator();
				while (secondCell.hasNext()) {
					Cell secondCellValue = secondCell.next();
					a.add(secondCellValue.getStringCellValue());
				}
			}
		}
		return a;
	}
}
