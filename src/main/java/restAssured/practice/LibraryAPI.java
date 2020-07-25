package restAssured.practice;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utility.Utility;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class LibraryAPI {

	
	

	@Test(dataProvider = "addBooks")
	public void addBooksToLibrary(String isbn, String aisle, String authorName, String BookName) {

		RestAssured.baseURI = "http://216.10.245.166";

		
		
		String addBooksResponse=given().log().all().header("content-type", "application/json")
		.body(PayLoad.addBooks(isbn, aisle, authorName, BookName ))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString(); //.assertThat().statusCode(200);

		
		

		 
		System.out.println("-------------------------------------------------");
System.out.println(PayLoad.addBooks(isbn, aisle, authorName, BookName));
		System.out.println(addBooksResponse);
		System.out.println("-------------------------------------------------");
	}

	

	@DataProvider(name = "addBooks")
	public Object[][] getDataFromDP() throws IOException{
		return Utility.getData();
	}
	
	@Test(dataProvider = "addBooks")
	public void getBooksFromLibrary(String isbn, String aisle, String authorName, String BookName) {

		RestAssured.baseURI = "http://216.10.245.166";

		String getBooksResponse=given().log().all()//.queryParam("AuthorName", authorName)
		.when().get("/Library/GetBook.php?AuthorName="+authorName)
		.then().log().all()//.assertThat().statusCode(200)
		.extract().response().asString();
		
		System.out.println("-------------------------------------------------");
		System.out.println(getBooksResponse);
				System.out.println("-------------------------------------------------");

		
		JsonPath js1=new JsonPath(getBooksResponse);
		String actualISBN=js1.getString("isbn[0]");
		System.out.println("actualISBN ="+actualISBN);
		
		Assert.assertEquals(actualISBN, isbn);
		System.out.println("isbn ="+ isbn);

	}
	
	
	
	
}
