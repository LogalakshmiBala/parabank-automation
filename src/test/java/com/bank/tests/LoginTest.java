package com.bank.tests;

import com.bank.pages.LoginPage;
import com.bank.utils.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(enabled = false)
    public void loginShouldSucceed() {
        WebDriver driver = WebDriverManager.createDriver();
        try {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.open(config.getString("base.url"));
            loginPage.login(config.getString("username"), config.getString("password"));
            Assert.assertTrue(loginPage.getWelcomeText().contains("Welcome"));
        } finally {
            driver.quit();
        }
    }
}
