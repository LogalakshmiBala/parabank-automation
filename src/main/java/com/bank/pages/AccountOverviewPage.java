package com.bank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AccountOverviewPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public AccountOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<String> getAccountLinks() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table#accountTable")));
        List<String> accountIds = new ArrayList<>();
        for (var row : driver.findElements(By.cssSelector("table#accountTable tbody tr"))) {
            var cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                accountIds.add(cells.get(0).getText());
            }
        }
        return accountIds;
    }
}
