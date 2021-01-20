package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    public static final String ADD_NOTE_BUTTON = "add-note-button";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_DESCRIPTION = "noteDescription";
    public static final String NOTE_MODAL = "noteModal";

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 5);
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void addNote(String noteTitle, String noteDescription) {
        this.notesTab.click();

        WebElement addNoteButton = this.waitUntilClickable(By.id(ADD_NOTE_BUTTON));
        addNoteButton.click();

        WebElement noteModal = this.getNoteModal();

        WebElement titleField = noteModal.findElement(By.name(NOTE_TITLE));
        titleField.sendKeys(noteTitle);

        WebElement descriptionField = noteModal.findElement(By.name(NOTE_DESCRIPTION));
        descriptionField.sendKeys(noteDescription);
        descriptionField.submit();
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
