package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesTabPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class NotesTest extends BaseTest {

    private NotesTabPage notesTabPage;

    @Test
    public void testCreateDeleteEditFlow() {
        this.createUserAndLogin();

        this.notesTabPage = new NotesTabPage(this.driver);

        String noteTitle = "My note title";
        String noteDescription = "This is a test note description";

        this.addNote(noteTitle, noteDescription);
        this.getPage("/");
        Note note = this.notesTabPage.getFirstNote();

        assertEquals(note.getNoteTitle(), noteTitle);
        assertEquals(note.getNoteDescription(), noteDescription);

        this.notesTabPage.deleteFirstNote();
        this.getPage("/");
        this.notesTabPage.openNotesTab();

        assertThrows(NoSuchElementException.class, () -> this.notesTabPage.getFirstNote());

        this.addNote(noteTitle, noteDescription);

        String updateNoteTitle = "My updated title";
        String updateNoteDescription = "This is an updated note description";

        this.notesTabPage.editNote(updateNoteTitle, updateNoteDescription);
        this.getPage("/");
        this.notesTabPage.openNotesTab();

        note = this.notesTabPage.getFirstNote();

        assertEquals(note.getNoteTitle(), updateNoteTitle);
        assertEquals(note.getNoteDescription(), updateNoteDescription);
    }

    private void addNote(String title, String description) {
        this.notesTabPage.addNote(title, description);
        this.getPage("/");
        this.notesTabPage.openNotesTab();
    }

}
