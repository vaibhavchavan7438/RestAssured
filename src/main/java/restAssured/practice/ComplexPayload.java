package restAssured.practice;

import org.testng.Assert;

import files.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexPayload {

	public static void main(String[] args) {

		/*
		 * 1. Print No of courses returned by API
		 * 
		 * 2.Print Purchase Amount
		 * 
		 * 3. Print Title of the first course
		 * 
		 * 4. Print All course titles and their respective Prices
		 * 
		 * 5. Print no of copies sold by RPA Course
		 * 
		 * 6. Verify if Sum of all Course prices matches with Purchase Amount
		 */

		JsonPath js = new JsonPath(PayLoad.coursesInformation());
		// 1.
		int courseCount = js.getInt("courses.size()");
		System.out.println("courseCount = " + courseCount);

		// 2.
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("purchaseAmount =" + purchaseAmount);

		// 3.
		String firstTitle = js.getString("courses[0].title");
		System.out.println("firstTitle =" + firstTitle);

		// 4.
		
		for (int i = 0; i < courseCount; i++) {
			String title = js.getString("courses[" + i + "].title");
			int price = js.getInt("courses[" + i + "].price");

			System.out.println(title + " price is : " + price);

		}
		// 5.

		String copiesOfRPA = js.getString("courses[2].copies");
		System.out.println("copies of RPA : " + copiesOfRPA);

		// 6.
		int sum = 0;
		for (int i = 0; i < courseCount; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies= js.getInt("courses[" + i + "].copies");
			int totalPrice=price*copies;
			sum+=totalPrice;

		}
		Assert.assertEquals(sum, purchaseAmount,"Successful");

	}

}
