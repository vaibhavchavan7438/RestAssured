package pojo_serialization;

import io.restassured.RestAssured;
import static  io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class AddPlace {

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
//		{
//		  "location": {
//		    "lat": -38.383494,
//		    "lng": 33.427362 
//		  },
//		  "accuracy": 50,
//		  "name": "Frontline house",
//		  "phone_number": "(+91) 983 893 3937",
//		  "address": "Devwadi, Taluka-Shirala, Maharashtra",
//		  "types": [
//		    "shoe park",
//		    "shop"
//		  ],
//		  "website": "http://google.com",
//		  "language": "French-IN"
//		}
		
		JsonParameters jp=new JsonParameters();
		jp.setAccuracy(50);
		jp.setAddress("Devwadi, Taluka-Shirala, Maharashtra");
		jp.setLanguage("Marathi");
		jp.setName("Vaibhav");
		jp.setPhone_number("6548154844");
		jp.setWebsite("www.vaibhavchavan.com");
		
		Location location=new Location();
		location .setLat(-38.383494);
		location.setLng(33.427362);
		
		jp.setLocation(location);
		List<String> typesList=new ArrayList<String>();
		typesList.add("shoe park");
		typesList.add("shop");
		
		jp.setTypes(typesList);
		
		
		String response=given().log().all().queryParam("key", "qaclick123").body(jp)
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
		
	}
	
	
}
