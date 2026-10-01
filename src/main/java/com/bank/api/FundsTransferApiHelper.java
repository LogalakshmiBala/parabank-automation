package com.bank.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class FundsTransferApiHelper {
    private final String baseUrl;

    public FundsTransferApiHelper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response getAccounts(String sessionId) {
        return given()
                .baseUri(baseUrl)
                .cookie("JSESSIONID", sessionId)
                .accept(ContentType.JSON)
                .when()
                .get("/services_proxy/bank/customers/12212/accounts");
    }

    public Response transfer(String sessionId, int fromAccountId, int toAccountId, double amount) {
        return given()
                .baseUri(baseUrl)
                .cookie("JSESSIONID", sessionId)
                .queryParam("fromAccountId", fromAccountId)
                .queryParam("toAccountId", toAccountId)
                .queryParam("amount", amount)
                .when()
                .post("/services_proxy/bank/transfer");
    }
}
