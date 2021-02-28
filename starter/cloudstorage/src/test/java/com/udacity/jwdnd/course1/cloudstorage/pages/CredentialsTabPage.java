package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CredentialsTabPage extends HomePage {
    public static final String CREDENTIAL_TAB_ID = "nav-credentials-tab";
    public static final String ADD_CREDENTIAL_BUTTON_ID = "add-credential-button";
    public static final String CREDENTIAL_MODAL_ID = "credentialModal";
    public static final String CREDENTIAL_TABLE_ID = "credentialTable";
    public static final String URL_FIELD_NAME = "url";
    public static final String USERNAME_FIELD_NAME = "username";
    public static final String PASSWORD_FIELD_NAME = "password";
    public static final String CREDENTIAL_ROW_CLASS = "credential-row";
    public static final String CREDENTIAL_URL_CELL_CLASS = "credential-url";
    public static final String CREDENTIAL_USERNAME_CELL_CLASS = "credential-username";
    public static final String CREDENTIAL_PASSWORD_CELL_CLASS = "credential-password";

    public CredentialsTabPage(WebDriver driver) {
        super(driver);
    }

    public void openCredentialsTab() {
        this.openTab(CREDENTIAL_TAB_ID);
    }

    public void addCredential(String url, String username, String password) {
        this.openCredentialsTab();

        WebElement addButton = this.waitUntilClickable(By.id(ADD_CREDENTIAL_BUTTON_ID));
        addButton.click();

        WebElement credentialModal = this.getCredentialModal();
        WebElement urlField = credentialModal.findElement(By.name(URL_FIELD_NAME));
        urlField.sendKeys(url);

        WebElement usernameField = credentialModal.findElement(By.name(USERNAME_FIELD_NAME));
        usernameField.sendKeys(username);

        WebElement passwordField = credentialModal.findElement(By.name(PASSWORD_FIELD_NAME));
        passwordField.sendKeys(password);
        passwordField.submit();
    }

    public void editCredentials(String url, String username, String password) {
        this.openCredentialsTab();

        WebElement firstRow = this.getTableRow();
        WebElement editButton = firstRow.findElement(By.tagName("button"));
        editButton.click();

        WebElement credentialModal = this.getCredentialModal();
        WebElement urlField = credentialModal.findElement(By.name(URL_FIELD_NAME));
        urlField.clear();
        urlField.sendKeys(url);

        WebElement usernameField = credentialModal.findElement(By.name(USERNAME_FIELD_NAME));
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = credentialModal.findElement(By.name(PASSWORD_FIELD_NAME));
        passwordField.clear();
        passwordField.sendKeys(password);
        passwordField.submit();
    }

    public Credential getEditFieldsCredentials() {
        this.openCredentialsTab();

        WebElement firstRow = this.getTableRow();
        WebElement editButton = firstRow.findElement(By.tagName("button"));
        editButton.click();

        WebElement credentialModal = this.getCredentialModal();
        String url = credentialModal.findElement(By.name(URL_FIELD_NAME)).getAttribute("value");
        String username = credentialModal.findElement(By.name(USERNAME_FIELD_NAME)).getAttribute("value");
        String password = credentialModal.findElement(By.name(PASSWORD_FIELD_NAME)).getAttribute("value");

        return new Credential(null, url, username, null, password, null);
    }

    public Credential getFirstCredential() {
        this.openCredentialsTab();

        WebElement row = this.getTableRow();
        WebElement url = row.findElement(By.className(CREDENTIAL_URL_CELL_CLASS));
        WebElement username = row.findElement(By.className(CREDENTIAL_USERNAME_CELL_CLASS));
        WebElement password = row.findElement(By.className(CREDENTIAL_PASSWORD_CELL_CLASS));

        return new Credential(null, url.getText(), username.getText(), null, password.getText(), null);
    }

    public void deleteFirstCredential() {
        this.openCredentialsTab();

        WebElement row = this.getTableRow();
        WebElement deleteButton = row.findElement(By.tagName("a"));
        deleteButton.click();
    }

    private WebElement getTableRow() {
        return waitUntilVisible(By.id(CREDENTIAL_TABLE_ID)).findElement(By.className(CREDENTIAL_ROW_CLASS));
    }

    private WebElement getCredentialModal() {
        return this.getModal(CREDENTIAL_MODAL_ID);
    }
}
