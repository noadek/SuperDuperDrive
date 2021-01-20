package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.util.AuthenticationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotesTest extends BaseTest {

    private HomePage homePage;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        AuthenticationUtil authenticationUtil = new AuthenticationUtil(this.driver, this.baseURL);
        authenticationUtil.createUserAndLogin();
        this.homePage = new HomePage(this.driver);
    }

    @Test
    public void testCreateNote() throws InterruptedException {
        String noteTitle = "a note title";
        String noteDescription = "a note description";

        this.homePage.addNote(noteTitle, noteDescription);

        Thread.sleep(5000);
    }

    @Test
    public void testViewNote() {

    }

}
