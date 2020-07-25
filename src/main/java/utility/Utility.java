package utility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Utility {
	static Object[][] obj;
	static Workbook book;
	static Sheet sheet;
	static FileInputStream fis;
	
	public static Object[][] getData() throws IOException {
		fis = new FileInputStream(
				"G:\\github_workspace\\RestAssuredPractice\\src\\main\\java\\dataProvider\\DataProvider.xlsx");
		book = WorkbookFactory.create(fis);
		sheet = book.getSheet("Sheet1");

		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();
		obj = new Object[rowCount][colCount];
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {

				obj[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				
			}
		}
		return obj;
	}

}
