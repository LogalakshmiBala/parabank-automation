package com.bank.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthApiHelper {
    private final String baseUrl;

    public AuthApiHelper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response login(String username, String password) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.URLENC)
                .formParam("username", username)
                .formParam("password", password)
                .redirects()
                .follow(false)
                .when()
                .post("/login.htm");
    }
}
