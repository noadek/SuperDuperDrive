package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    public static final String NOTE_TAB_ID = "nav-notes-tab";
    public static final String ADD_NOTE_BUTTON = "add-note-button";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_DESCRIPTION = "noteDescription";
    public static final String NOTE_MODAL = "noteModal";
    public static final String NOTE_TABLE_ID = "userTable";
    public static final String NOTE_TITLE_CELL = "note-title";
    public static final String NOTE_DESCRIPTION_CELL = "note-description";
    public static final String LOGOUT_BUTTON_ID = "logout-button";

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void logout() {
        WebElement logoutButton = this.waitUntilClickable(By.id(LOGOUT_BUTTON_ID));
        logoutButton.click();
    }

    public void openNotesTab() {
        WebElement notesTab = this.waitUntilClickable(By.id(NOTE_TAB_ID));
        notesTab.click();
    }

    public void addNote(String noteTitle, String noteDescription) {
        this.openNotesTab();

        WebElement addNoteButton = this.waitUntilClickable(By.id(ADD_NOTE_BUTTON));
        addNoteButton.click();

        WebElement noteModal = this.getNoteModal();
        WebElement titleField = noteModal.findElement(By.name(NOTE_TITLE));
        titleField.sendKeys(noteTitle);

        WebElement descriptionField = noteModal.findElement(By.name(NOTE_DESCRIPTION));
        descriptionField.sendKeys(noteDescription);
        descriptionField.submit();
    }

    public void editNote(String noteTitle, String noteDescription) {
        this.openNotesTab();

        WebElement noteRow = this.getNoteTable();
        WebElement editButton = noteRow.findElement(By.tagName("button"));
        editButton.click();

        WebElement noteModal = this.getNoteModal();
        WebElement titleField = noteModal.findElement(By.name(NOTE_TITLE));
        titleField.clear();
        titleField.sendKeys(noteTitle);

        WebElement descriptionField = noteModal.findElement(By.name(NOTE_DESCRIPTION));
        descriptionField.clear();
        descriptionField.sendKeys(noteDescription);
        descriptionField.submit();
    }

    public Note getFirstNote() {
        this.openNotesTab();

        WebElement noteRow = this.getNoteTable();
        WebElement title = noteRow.findElement(By.className(NOTE_TITLE_CELL));
        WebElement description = noteRow.findElement(By.className(NOTE_DESCRIPTION_CELL));

        return new Note(null, title.getText(), description.getText(), null);
    }

    public void deleteFirstNote() {
        this.openNotesTab();

        WebElement noteRow = this.getNoteTable();
        WebElement deleteNoteButton = noteRow.findElement(By.tagName("a"));
        deleteNoteButton.click();
    }

    private WebElement getNoteTable() {
        return waitUntilVisible(By.id(NOTE_TABLE_ID));
    }

    private WebElement getNoteModal() {
        return this.waitUntilVisible(By.id(NOTE_MODAL));
    }

    private WebElement waitUntilClickable(By by) {
        return this.wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    private WebElement waitUntilVisible(By by) {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
