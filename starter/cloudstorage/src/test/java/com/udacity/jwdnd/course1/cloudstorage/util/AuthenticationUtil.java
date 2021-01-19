package com.udacity.jwdnd.course1.cloudstorage.util;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import org.openqa.selenium.WebDriver;

public class AuthenticationUtil {

    private WebDriver driver;

    private String baseURL;

    public AuthenticationUtil(WebDriver driver, String baseURL) {
        this.driver = driver;
        this.baseURL = baseURL;
    }

    public void createUserAndLogin() {
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
