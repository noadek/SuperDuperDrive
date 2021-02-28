package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    protected static final String LOGOUT_BUTTON_ID = "logout-button";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void logout() {
        WebElement logoutButton = this.waitUntilClickable(By.id(LOGOUT_BUTTON_ID));
        logoutButton.click();
    }

    protected void openTab(String tabId) {
        WebElement tab = this.waitUntilClickable(By.id(tabId));
        tab.click();
    }

    protected WebElement getModal(String modalId) {
        return this.waitUntilVisible(By.id(modalId));
    }

    protected WebElement waitUntilClickable(By by) {
        return this.wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected WebElement waitUntilVisible(By by) {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
