package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(id = "error-msg")
    private WebElement errorMessageElement;

    @FindBy(id = "logout-msg")
    private WebElement logoutMessageElement;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        this.usernameField.sendKeys(username);
        this.passwordField.sendKeys(password);
        this.submitButton.submit();
    }

    public WebElement getErrorMessageElement() {
        return this.errorMessageElement;
    }

    public WebElement getLogoutMessageElement() {
        return this.logoutMessageElement;
    }
}
