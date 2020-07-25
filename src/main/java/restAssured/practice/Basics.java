package restAssured.practice;


import static org.hamcrest.Matchers.*; //requires for equalTo

import org.testng.Assert;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// given= all input details
		// when = submit the API (Resources and http method)
		// then= validate the response

		RestAssured.baseURI = "https://rahulshettyacademy.com";

	 String response=given().log().all().queryParam("Key", "qaclick123").header("content-type", "application/json")
				.body(PayLoad.addPlace())
				.when().post("/maps/api/place/add/json")
				.then().assertThat()        //log().all().
				.statusCode(200).body("scope", equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
	 
	 System.out.println(response);
	 
	 JsonPath js=new JsonPath(response);
	 
	 String place_id=js.getString("place_id");

	 System.out.println(place_id);
	 
		// update by capturing place_id and then get updated address

	 String newAddress="Pune, Maharashtra 411017";
	 
	 
	 given().log().all().queryParam("key", "qaclick123").header("content-type","application/json")
	 .body("{\r\n" + 
	 		"\"place_id\":\""+place_id+"\",\r\n" + 
	 		"\"address\":\""+newAddress+"\",\r\n" + 
	 		"\"key\":\"qaclick123\"\r\n" + 
	 		"}\r\n" + 
	 		"")
	 	.when().put("/maps/api/place/update/json") 	
	 	.then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
	 
	 //get updated place_id
	 
	 String extractedAddress=given().log().all().queryParam("key","qaclick123").queryParam("place_id", place_id)
	 .when().get("/maps/api/place/get/json")
	 .then().log().all().assertThat().statusCode(200).extract().response().asString();
	 
	 JsonPath js1=new JsonPath(extractedAddress);
	 String ActualAddress=js1.getString("address");
	 
	 Assert.assertEquals(newAddress, ActualAddress);
	 
	 System.out.println();
	}
	

}
