package com.bank.tests;

import com.bank.api.AuthApiHelper;
import com.bank.api.FundsTransferApiHelper;
import com.bank.utils.JsonSchemaValidatorUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class APIBankingTest extends BaseTest {
    @Test
    public void customerCanFetchAccountsAndTransferFunds() throws IOException {
        String baseUrl = config.getString("base.url");
        String username = config.getString("username");
        String password = config.getString("password");

        AuthApiHelper authApi = new AuthApiHelper(baseUrl);
        Response loginResponse = authApi.login(username, password);
        Assert.assertEquals(loginResponse.getStatusCode(), 302, "Expected a redirect after login");

        String sessionId = loginResponse.getDetailedCookie("JSESSIONID") != null
                ? loginResponse.getDetailedCookie("JSESSIONID").getValue()
                : loginResponse.getCookie("JSESSIONID");
        Assert.assertNotNull(sessionId, "Expected a session cookie after login");

        FundsTransferApiHelper fundsApi = new FundsTransferApiHelper(baseUrl);
        Response accountsResponse = fundsApi.getAccounts(sessionId);
        Assert.assertEquals(accountsResponse.getStatusCode(), 200, "Expected account retrieval to succeed");
        String accountsJson = accountsResponse.asString();
        JsonSchemaValidatorUtil.validate(accountsJson, "transfer-response-schema.json");

        List<Map<String, Object>> accounts = accountsResponse.jsonPath().getList("$");
        Assert.assertFalse(accounts.isEmpty(), "Expected at least one account for the customer");

        Map<String, Object> sourceAccount = accounts.stream()
                .filter(account -> ((Number) account.get("balance")).doubleValue() > 1.00)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No account with a positive balance was returned"));

        Map<String, Object> destinationAccount = accounts.stream()
                .filter(account -> !account.get("id").equals(sourceAccount.get("id")))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No alternate account available for transfer"));

        int fromAccountId = Integer.parseInt(sourceAccount.get("id").toString());
        int toAccountId = Integer.parseInt(destinationAccount.get("id").toString());
        double amount = Math.min(1.00, ((Number) sourceAccount.get("balance")).doubleValue() / 10.0);

        Response transferResponse = fundsApi.transfer(sessionId, fromAccountId, toAccountId, amount);
        Assert.assertEquals(transferResponse.getStatusCode(), 200, "Expected the transfer request to succeed");
        Assert.assertTrue(transferResponse.asString().contains("Successfully transferred"), "Expected a transfer success message");
    }
}
