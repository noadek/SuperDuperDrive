package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.util.AuthenticationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotesTest extends BaseTest {

    private HomePage homePage;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        AuthenticationUtil authenticationUtil = new AuthenticationUtil(this.driver, this.baseURL);
        authenticationUtil.createUserAndLogin();
        this.homePage = new HomePage(this.driver);
    }

    private void addNote() {
        String title = "My note title";
        String description = "This is a test note description";

        this.addNote(title, description);
    }

    private void addNote(String title, String description) {
        this.homePage.addNote(title, description);
        this.getPage("/");
        this.homePage.openNotesTab();
    }

    @Test
    public void testCreateNote() {
        String noteTitle = "My note title";
        String noteDescription = "This is a test note description";

        this.addNote(noteTitle, noteDescription);

        Note expectedNote = this.homePage.getFirstNote();

        assertEquals(expectedNote.getNoteTitle(), noteTitle);
        assertEquals(expectedNote.getNoteDescription(), noteDescription);
    }

    @Test
    public void testEditNote() {
        this.addNote();

        String updateNoteTitle = "My updated title";
        String updateNoteDescription = "This is an updated note description";

        this.homePage.editNote(updateNoteTitle, updateNoteDescription);
        this.getPage("/");
        this.homePage.openNotesTab();

        Note expectedNote = this.homePage.getFirstNote();

        assertEquals(expectedNote.getNoteTitle(), updateNoteTitle);
        assertEquals(expectedNote.getNoteDescription(), updateNoteDescription);
    }

    @Test
    public void testDeleteNote() {

    }

}
