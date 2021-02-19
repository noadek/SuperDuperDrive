package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseTest {
    @LocalServerPort
    private int port;

    protected WebDriver driver;

    protected String baseURL;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromiumdriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new SafariDriver();
        this.baseURL = "http://localhost:" + this.port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected void getPage(String path) {
        this.driver.get(this.baseURL + path);
        this.waitForPageLoad();
    }

    protected void createUserAndLogin() {
        String username = "pzastoup";
        String password = "whatabadpassword";

        this.getPage("/signup");
        SignUpPage signupPage = new SignUpPage(this.driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        this.getPage("/login");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login(username, password);

        this.waitForUrlToBe("/");
    }

    protected void waitForUrlToBe(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlToBe(baseURL + url));
    }

    protected void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(webDriver ->
                ((JavascriptExecutor) this.driver).executeScript("return document.readyState")
                        .toString().equals("complete")
        );
    }
}
