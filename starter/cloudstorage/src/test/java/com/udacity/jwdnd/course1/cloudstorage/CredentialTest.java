package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialsTabPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialTest extends BaseTest {
    private CredentialsTabPage credentialsTabPage;

    @Test
    public void testCreateDeleteEditFlow() {
        this.createUserAndLogin();

        this.credentialsTabPage = new CredentialsTabPage(this.driver);

        String url = "http://example.com";
        String username = "username";
        String password = "a-password";

        this.addCredential(url, username, password);
        this.getPage("/");
        Credential credential = this.credentialsTabPage.getFirstCredential();

        assertEquals(credential.getUrl(), url);
        assertEquals(credential.getUsername(), username);
        assertNotEquals(credential.getPassword(), password);

        this.credentialsTabPage.deleteFirstCredential();
        this.getPage("/");
        this.credentialsTabPage.openCredentialsTab();

        assertThrows(NoSuchElementException.class, () -> this.credentialsTabPage.getFirstCredential());

        this.addCredential(url, username, password);
        credential = this.credentialsTabPage.getEditFieldsCredentials();

        assertEquals(credential.getUrl(), url);
        assertEquals(credential.getUsername(), username);
        assertEquals(credential.getPassword(), password);

        this.getPage("/");

        String updatedUrl = "http://test.com/";
        String updatedUsername = "updatedUsername";
        String updatedPassword = "a-new-password";

        this.credentialsTabPage.editCredentials(updatedUrl, updatedUsername, updatedPassword);
        this.getPage("/");
        this.credentialsTabPage.openCredentialsTab();

        credential = this.credentialsTabPage.getFirstCredential();

        assertEquals(credential.getUrl(), updatedUrl);
        assertEquals(credential.getUsername(), updatedUsername);
        assertNotEquals(credential.getPassword(), updatedPassword);
    }

    private void addCredential(String url, String username, String password) {
        this.credentialsTabPage.addCredential(url, username, password);
        this.getPage("/");
        this.credentialsTabPage.openCredentialsTab();
    }
}
