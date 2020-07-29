package pojo_serialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderAddPlace {

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
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
		
		
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON)
		.build();               //build() is important
		
		ResponseSpecification resSpec= new ResponseSpecBuilder()
		.expectContentType(ContentType.JSON)
		.expectStatusCode(200)
		.build();
		
		RequestSpecification req=given().log().all().spec(reqSpec).body(jp);
		
		Response response=req.when().post("/maps/api/place/add/json")
		.then().log().all().spec(resSpec).extract().response();
		
		String responseString=response.asString();
		System.out.println(responseString);
		
	
	}

}
