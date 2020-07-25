package oauth;
import static io.restassured.RestAssured.*;


import groovy.util.logging.Log;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.getCourses;
public class OauthUsingGoogle {
	
	public static void main(String[] args) {
		
		//url can be generated using
		//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		 
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F2AG6nRIkZ2NrGifYiqor3Qyk3X_rjmTUm8nMckeb1WSmXNBv1BX5rOKkTfOOQ5Opu0eiRVvc4FnzIdIOk0xzigg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode=url.split("code=")[1];
		
		String code=partialCode.split("&scope")[0];
				
		System.out.println("code ----> "+code);
		
		String Response=given()
		.urlEncodingEnabled(false)
		.queryParams("code", code)
		 .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		System.out.println("Access token Response ----> " + Response );
		
		JsonPath js=new JsonPath(Response);
		
		String access_token=js.get("access_token").toString() ;
		
		System.out.println("access_token ---->" +access_token);
		
//before implementing POJO		
//		String getCources=given().queryParam("access_token", access_token)
//		.when().get("https://rahulshettyacademy.com/getCourse.php")
//		.then().log().all().assertThat().statusCode(200).extract().response().asString();
//		System.out.println(getCources);
		
		
		getCourses gt= given().queryParam("access_token", access_token).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(getCourses.class);
		
	System.out.println(gt.getInstructor());
	
	int webAutomationCount =gt.getCourses().getWebAutomation().size();
	for(int i=0; i< webAutomationCount;i++) {
		System.out.println(gt.getCourses().getWebAutomation().get(i).getCourseTitle());
	}
	int apiCount=gt.getCourses().getApi().size();
	for(int i=0; i< apiCount;i++) {
		if(gt.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
			System.out.println(gt.getCourses().getWebAutomation().get(i).getPrice());
					
		}
	}
	
	}
	
}
