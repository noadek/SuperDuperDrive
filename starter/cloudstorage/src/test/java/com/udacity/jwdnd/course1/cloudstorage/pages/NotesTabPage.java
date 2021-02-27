package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NotesTabPage extends HomePage {
    public static final String NOTE_TAB_ID = "nav-notes-tab";
    public static final String ADD_NOTE_BUTTON_ID = "add-note-button";
    public static final String NOTE_TITLE_FIELD_NAME = "noteTitle";
    public static final String NOTE_DESCRIPTION_FIELD_NAME = "noteDescription";
    public static final String NOTE_MODAL_ID = "noteModal";
    public static final String NOTE_TABLE_ID = "noteTable";
    public static final String NOTE_ROW_CLASS = "note-row";
    public static final String NOTE_TITLE_CELL_CLASS = "note-title";
    public static final String NOTE_DESCRIPTION_CELL_CLASS = "note-description";

    public NotesTabPage(WebDriver driver) {
        super(driver);
    }

    public void openNotesTab() {
        this.openTab(NOTE_TAB_ID);
    }

    public void addNote(String noteTitle, String noteDescription) {
        this.openNotesTab();

        WebElement addButton = this.waitUntilClickable(By.id(ADD_NOTE_BUTTON_ID));
        addButton.click();

        WebElement noteModal = this.getNoteModal();
        WebElement titleField = noteModal.findElement(By.name(NOTE_TITLE_FIELD_NAME));
        titleField.sendKeys(noteTitle);

        WebElement descriptionField = noteModal.findElement(By.name(NOTE_DESCRIPTION_FIELD_NAME));
        descriptionField.sendKeys(noteDescription);
        descriptionField.submit();
    }

    public void editNote(String noteTitle, String noteDescription) {
        this.openNotesTab();

        WebElement noteRow = this.getTableRow();
        WebElement editButton = noteRow.findElement(By.tagName("button"));
        editButton.click();

        WebElement noteModal = this.getNoteModal();
        WebElement titleField = noteModal.findElement(By.name(NOTE_TITLE_FIELD_NAME));
        titleField.clear();
        titleField.sendKeys(noteTitle);

        WebElement descriptionField = noteModal.findElement(By.name(NOTE_DESCRIPTION_FIELD_NAME));
        descriptionField.clear();
        descriptionField.sendKeys(noteDescription);
        descriptionField.submit();
    }

    public Note getFirstNote() {
        this.openNotesTab();

        WebElement row = this.getTableRow();
        WebElement title = row.findElement(By.className(NOTE_TITLE_CELL_CLASS));
        WebElement description = row.findElement(By.className(NOTE_DESCRIPTION_CELL_CLASS));

        return new Note(null, title.getText(), description.getText(), null);
    }

    public void deleteFirstNote() {
        this.openNotesTab();

        WebElement row = this.getTableRow();
        WebElement deleteNoteButton = row.findElement(By.tagName("a"));
        deleteNoteButton.click();
    }

    private WebElement getTableRow() {
        return waitUntilVisible(By.id(NOTE_TABLE_ID)).findElement(By.className(NOTE_ROW_CLASS));
    }

    private WebElement getNoteModal() {
        return this.getModal(NOTE_MODAL_ID);
    }
}
