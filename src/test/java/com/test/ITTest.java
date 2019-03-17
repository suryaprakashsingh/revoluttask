package com.test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ITTest {

	@Before
	public void setUp() {

		RestAssured.baseURI = "http://localhost:8080/api/v1";

	}

	@Test
	public void serviceChecker() {
		String myJson = "{\"accountNum\": \"1234\", \"balance\": 4000}";
		Response r = given().contentType("application/json").body(myJson).when().post("/accounts");

		String body = r.getBody().asString();
		System.out.println(body);

		when().get("/accounts/{id}", 1234).then().statusCode(200);

	}

	@Test
	public void addBalance() {
		String myJson = "{\"accountNum\": \"12345\", \"balance\": 4000}";
		Response r = given().contentType("application/json").body(myJson).when().post("/accounts");

		String body = r.getBody().asString();
		System.out.println(body);

		when().get("/accounts/{id}", 12345).then().statusCode(200).body("accountNum", equalTo("12345"));

	}

	@Test
	public void addTransferTest() {

		// Account setup
		String myJson1 = "{\"accountNum\": \"123456\", \"balance\": 4000}";
		String myJson2 = "{\"accountNum\": \"123457\", \"balance\": 4000}";
		given().contentType("application/json").body(myJson1).when().post("/accounts");
		given().contentType("application/json").body(myJson2).when().post("/accounts");

		// transfer
		String myJson = "{\r\n" + "	\"from\" : \"123456\",\r\n" + "	\"to\" : \"123457\",\r\n" + "	\"amount\": 100\r\n"
				+ "}";
		Response r = given().contentType("application/json").body(myJson).when().post("/transfers");

		String body = r.getBody().asString();
		System.out.println(body);

		when().get("/transfers/{id}", 123456).then().statusCode(200).body("from", contains("123456")).body("to",
				contains("123457"));

	}

}
