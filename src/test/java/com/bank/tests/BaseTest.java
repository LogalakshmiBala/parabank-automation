package com.bank.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.bank.utils.ConfigReader;
import com.bank.utils.ExtentReportManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public class BaseTest {
    protected static ExtentReports report;
    protected ExtentTest test;
    protected final ConfigReader config = new ConfigReader("config.properties");

    @BeforeSuite
    public void setUpSuite() {
        report = ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setUpMethod(Method method) {
        test = report.createTest(method.getName());
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else {
            test.skip("Test skipped");
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (report != null) {
            report.flush();
        }
    }
}
