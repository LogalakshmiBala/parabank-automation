package com.bank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FundTransferPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public FundTransferPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/transfer.htm");
    }

    public void transferFunds(int fromAccountId, int toAccountId, double amount) {
        WebElement amountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amount")));
        amountInput.clear();
        amountInput.sendKeys(String.valueOf(amount));

        Select from = new Select(driver.findElement(By.id("fromAccountId")));
        from.selectByValue(String.valueOf(fromAccountId));

        Select to = new Select(driver.findElement(By.id("toAccountId")));
        to.selectByValue(String.valueOf(toAccountId));

        driver.findElement(By.cssSelector("input[value='Transfer']")).click();
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amountResult"))).getText();
    }
}
