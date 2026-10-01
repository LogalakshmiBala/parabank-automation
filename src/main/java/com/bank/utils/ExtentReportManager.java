package com.bank.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentReportManager {
    private static ExtentReports extent;

    private ExtentReportManager() {
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");
            extent.attachReporter(reporter);
        }
        return extent;
    }

    public static ExtentTest createTest(String name) {
        return getInstance().createTest(name);
    }
}
