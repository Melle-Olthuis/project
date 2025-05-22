import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

public class ChromeHeadlessTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // Comment this line if debugging
        options.addArguments("--no-sandbox"); 
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testPageTitle() {
        driver.get("https://google.com");
        String title = driver.getTitle();
        assertEquals("Example Domain", title);
    }

    @Test
    public void testLoginSuccess() {
        driver.get("https://www.saucedemo.com/");

        // Gebruikersnaam en wachtwoord invullen
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        // Controle: zijn we op de productenpagina?
        boolean isOnProductsPage = driver.getCurrentUrl().contains("inventory.html");
        assertTrue("Gebruiker is niet op de productenpagina terechtgekomen.", isOnProductsPage);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}