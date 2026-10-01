package com.bank.tests;

import com.bank.pages.FundTransferPage;
import com.bank.pages.LoginPage;
import com.bank.utils.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FundTransferTest extends BaseTest {
    @Test(enabled = false)
    public void transferFundsFromOneAccountToAnother() {
        WebDriver driver = WebDriverManager.createDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open(config.getString("base.url"));
            loginPage.login(config.getString("username"), config.getString("password"));

            FundTransferPage transferPage = new FundTransferPage(driver);
            transferPage.open(config.getString("base.url"));
            transferPage.transferFunds(13344, 13899, 1.00);
            Assert.assertTrue(transferPage.getConfirmationMessage().contains("Successfully transferred"));
        } finally {
            driver.quit();
        }
    }
}
