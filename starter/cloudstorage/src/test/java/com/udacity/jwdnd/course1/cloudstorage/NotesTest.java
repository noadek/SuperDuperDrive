package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class NotesTest extends BaseTest {

    private HomePage homePage;

    @Test
    public void testCreateDeleteEditNote() {
        this.createUserAndLogin();

        this.homePage = new HomePage(this.driver);

        String noteTitle = "My note title";
        String noteDescription = "This is a test note description";

        this.addNote(noteTitle, noteDescription);
        this.getPage("/");
        Note expectedNote = this.homePage.getFirstNote();

        assertEquals(expectedNote.getNoteTitle(), noteTitle);
        assertEquals(expectedNote.getNoteDescription(), noteDescription);

        this.homePage.deleteFirstNote();
        this.getPage("/");
        this.homePage.openNotesTab();

        assertThrows(NoSuchElementException.class, () -> this.homePage.getFirstNote());

        this.addNote();

        String updateNoteTitle = "My updated title";
        String updateNoteDescription = "This is an updated note description";

        this.homePage.editNote(updateNoteTitle, updateNoteDescription);
        this.getPage("/");
        this.homePage.openNotesTab();

        expectedNote = this.homePage.getFirstNote();

        assertEquals(expectedNote.getNoteTitle(), updateNoteTitle);
        assertEquals(expectedNote.getNoteDescription(), updateNoteDescription);
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

}
