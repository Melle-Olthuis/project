# Java Selenium Headless Test Project

This project demonstrates how to set up a Java application using Selenium WebDriver with Chrome in headless mode for automated testing.

## Project Structure

```
java-selenium-test
├── src
│   ├── main
│   │   └── java
│   │       └── App.java
│   └── test
│       └── java
│           └── ChromeHeadlessTest.java
├── pom.xml
└── README.md
```

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven
- Chrome browser installed on your machine

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repository-url>
   cd java-selenium-test
   ```

2. Build the project using Maven:
   ```
   mvn clean install
   ```

3. Ensure that the ChromeDriver is compatible with your installed version of Chrome. You can download it from [ChromeDriver](https://sites.google.com/chromium.org/driver/).

## Running the Tests

To run the Selenium tests in headless mode, execute the following command:
```
mvn test
```

This will run the tests defined in `ChromeHeadlessTest.java` using the Chrome browser in headless mode.

## Additional Information

- The main application logic can be found in `App.java`.
- The test cases are located in `ChromeHeadlessTest.java`.
- The project dependencies and build configurations are specified in `pom.xml`. 

Feel free to modify the tests and application logic as needed for your specific use case.