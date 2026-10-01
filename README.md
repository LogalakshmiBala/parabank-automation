# ParaBank QA Automation

This project provides a Java + Maven automation framework for exercising the ParaBank demo banking app.

## Stack
- Java 17
- Maven 3.9+
- Selenium WebDriver
- TestNG
- REST Assured
- ExtentReports
- JSON Schema validation

## Run locally

```bash
cd /Users/charunatarajan/PROJECTS 2026/parabank-automation
export JAVA_HOME=/tmp/java/Contents/Home
export PATH=$JAVA_HOME/bin:/tmp/maven/bin:$PATH
mvn test
```

## Included tests
- API smoke test for login, account retrieval, and transfer validation
- UI page object scaffolding for login and fund transfer flows

## Notes
The framework is configured for the public ParaBank site and validates the live service contracts with a JSON schema check for account responses.
