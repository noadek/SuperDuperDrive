package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public static final String SUCCESS_MESSAGE_ID = "success-msg";
    
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

    public WebElement getSuccessMessageElement() {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SUCCESS_MESSAGE_ID)));
    }

    public WebElement getErrorMessageElement() {
        return this.errorMessageElement;
    }

    public WebElement getLogoutMessageElement() {
        return this.logoutMessageElement;
    }
}
