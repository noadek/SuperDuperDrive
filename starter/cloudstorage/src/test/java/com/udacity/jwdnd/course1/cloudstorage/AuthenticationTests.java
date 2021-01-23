package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.*;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import org.junit.jupiter.api.Test;

public class AuthenticationTests extends BaseTest {
    @Test
    public void testUnauthorizedUserCanOnlyAccessAuthPages() {
        this.getPage("/login");
        assertEquals(baseURL + "/login" , this.driver.getCurrentUrl());

        this.getPage("/signup");
        assertEquals(baseURL + "/signup" , this.driver.getCurrentUrl());

        this.getPage("/");
        assertEquals(baseURL + "/login" , this.driver.getCurrentUrl());
    }

    @Test
    public void testWrongUsernameOrPasswordShowsErrorMessage() {
        String username = "pzastoup";
        String password = "whatabadpassword";

        this.getPage("/login");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login(username, password);

        this.getPage("/login?error");
        loginPage = new LoginPage(this.driver);
        assertTrue(loginPage.getErrorMessageElement().isDisplayed());
    }

    @Test
    public void testUserCanSignUpLoginAndLogout() {
        String username = "pzastoup";
        String password = "whatabadpassword";

        this.getPage("/signup");
        SignUpPage signupPage = new SignUpPage(this.driver);
        signupPage.signup("Peter", "Zastoupil", username, password);
        assertTrue(signupPage.getSuccessMessageElement().isDisplayed());

        this.getPage("/login");
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.login(username, password);

        this.getPage("/");
        HomePage homePage = new HomePage(this.driver);
        homePage.logout();

        this.getPage("/login");

        // attempt to access home page after logout
        this.getPage("/");
        this.getPage("/login");
    }
}