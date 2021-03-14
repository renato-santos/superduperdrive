package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class HomePage {

    private static WebDriver driver;

    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTab;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    @FindBy(css = "#create-note-button")
    private WebElement createNoteButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitleTextField;

    @FindBy(css = "#note-description")
    private WebElement noteDescriptionTextAreaField;

    @FindBy(css = "#noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(css = "#noteTitle")
    private List<WebElement> noteTitleList;

    @FindBy(css = "#noteDescription")
    private List<WebElement> noteDescriptionList;

    @FindBy(css = "#edit-note-button")
    private List<WebElement> editNoteButtonList;

    @FindBy(css = "#delete-note-button")
    private List<WebElement> deleteNoteButtonList;

    @FindBy(css = "#create-credential-button")
    private WebElement createCredentialButton;

    @FindBy(css = "#credential-url")
    private WebElement credentialUrlTextField;

    @FindBy(css = "#credential-username")
    private WebElement credentialUsernameTextField;

    @FindBy(css = "#credential-password")
    private WebElement credentialPasswordTextField;

    @FindBy(css = "#credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(css = "#credentialUrl")
    private List<WebElement> credentialUrlList;

    @FindBy(css = "#credentialUsername")
    private List<WebElement> credentialUsernameList;

    @FindBy(css = "#credentialPassword")
    private List<WebElement> credentialPasswordList;

    @FindBy(css = "#edit-credential-button")
    private List<WebElement> editCredentialButtonList;

    @FindBy(css = "#delete-credential-button")
    private List<WebElement> deleteCredentialButtonList;



    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        driver = webDriver;
    }

    public void createCredential(String url, String username, String password){

        buttonCLick(credentialsTab);
        buttonCLick(createCredentialButton);
        setValue(credentialUrlTextField, url);
        setValue(credentialUsernameTextField, username);
        setValue(credentialPasswordTextField, password);
        buttonCLick(credentialSubmitButton);

    }

    public void editCredential(String url, String username, String password, Integer position){

        buttonCLick(credentialsTab);
        buttonCLick(editCredentialButtonList.get(position));
        setValue(credentialUrlTextField, url);
        setValue(credentialUsernameTextField, username);
        setValue(credentialPasswordTextField, password);
        buttonCLick(credentialSubmitButton);

    }

    public void deleteCredential(Integer position){

        buttonCLick(credentialsTab);
        buttonCLick(deleteCredentialButtonList.get(position));
    }

    public void redirectToCredentialPage(){
        buttonCLick(credentialsTab);
    }


    public void createNote(String title, String description){

        buttonCLick(notesTab);
        buttonCLick(createNoteButton);
        setValue(noteTitleTextField, title);
        setValue(noteDescriptionTextAreaField, description);
        buttonCLick(noteSubmitButton);

    }

    public void editNote(String title, String description, Integer position){

        buttonCLick(notesTab);
        buttonCLick(editNoteButtonList.get(position));
        setValue(noteTitleTextField, title);
        setValue(noteDescriptionTextAreaField, description);
        buttonCLick(noteSubmitButton);

    }

    public void deleteNote(Integer position){

        buttonCLick(notesTab);
        buttonCLick(deleteNoteButtonList.get(position));
    }

    public void redirectToNotePage(){
        buttonCLick(notesTab);
    }


    public void logout() {
        this.logoutButton.click();
    }

    public void buttonCLick(WebElement we){

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", we);
    }

    public void setValue(WebElement we, String info){

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].value='" + info + "';" , we);
    }


    public List<String> getCredentialUrlList() {
        List<String> credentialList = new ArrayList<String>();
        for (WebElement ele : credentialUrlList) {
            credentialList.add(ele.getAttribute("innerHTML"));
        }
        return credentialList;
    }

    public List<String> getCredentialUsernameList() {
        List<String> credentialList = new ArrayList<String>();
        for (WebElement ele : credentialUsernameList) {
            credentialList.add(ele.getAttribute("innerHTML"));
        }
        return credentialList;
    }

    public List<String> getCredentialPasswordList() {
        List<String> credentialList = new ArrayList<String>();
        for (WebElement ele : credentialPasswordList) {
            credentialList.add(ele.getAttribute("innerHTML"));
        }
        return credentialList;
    }

    public List<String> getNoteTitleList() {
        List<String> noteList = new ArrayList<String>();
        for (WebElement ele : noteTitleList) {
            noteList.add(ele.getAttribute("innerHTML"));
        }
        return noteList;
    }

    public List<String> getNoteDescriptionList() {
        List<String> noteList = new ArrayList<String>();
        for (WebElement ele : noteDescriptionList) {
            noteList.add(ele.getAttribute("innerHTML"));
        }
        return noteList;
    }

}
