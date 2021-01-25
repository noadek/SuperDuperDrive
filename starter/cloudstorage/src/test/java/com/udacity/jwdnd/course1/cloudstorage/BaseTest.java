package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.baseURL = "http://localhost:" + this.port;
        this.createUserAndLogin();
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
    }

    protected void createUserAndLogin() {
        String username = "pzastoup";
        String password = "whatabadpassword";

        this.driver.get(this.baseURL + "/signup");
        SignUpPage signupPage = new SignUpPage(this.driver);
        signupPage.signup("Peter", "Zastoupil", username, password);

        this.driver.get(this.baseURL + "/login");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login(username, password);

        this.driver.get(this.baseURL + "/");
    }
}
