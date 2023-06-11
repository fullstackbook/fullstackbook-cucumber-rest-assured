package org.example;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ToDoStepDefinitions {
    @Given("I have created a to do")
    public void i_have_created_a_to_do() {
        get("http://localhost:8000/v1/todos/3").then().body("name", equalTo("foo"));
    }
    @When("I update the to do")
    public void i_update_the_to_do() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "bar");
        jsonAsMap.put("completed", true);

        given().
                contentType(JSON).
                body(jsonAsMap).
                when().
                put("http://localhost:8000/v1/todos/3").
                then().
                statusCode(200);
    }
    @Then("The To Do should be updated")
    public void the_to_do_should_be_updated() {
        get("http://localhost:8000/v1/todos/3").then().body("name", equalTo("bar"));
    }
    @After()
    public void cleanup() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "foo");
        jsonAsMap.put("completed", true);

        given().
                contentType(JSON).
                body(jsonAsMap).
                when().
                put("http://localhost:8000/v1/todos/3").
                then().
                statusCode(200);
    }
}
