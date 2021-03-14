package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	private static final String USERNAME = "johnlennon";
	private static final String PASSWORD = "imagine123@";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Lennon";
	private static final String NOTE_TITLE = "My Birthday";
	private static final String NOTE_DESCRIPTION = "October 9, 1940";
	private static final String NOTE_TITLE_EDIT = "My 10th Birthday";
	private static final String NOTE_DESCRIPTION_EDIT = "October 9, 1950";
	private static final String CREDENTIAL_URL = "beatles.com";
	private static final String CREDENTIAL_USERNAME = "beatles";
	private static final String CREDENTIAL_PASSWORD = "beatles123";
	private static final String CREDENTIAL_URL_EDIT = "johnlennon.com";
	private static final String CREDENTIAL_USERNAME_EDIT = "johnlennon";
	private static final String CREDENTIAL_PASSWORD_EDIT = "j0hnl3nn0n";

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}

	@Test
	public void testLoginSuccess() {

		performLogin();

		assertEquals("Home", driver.getTitle());
	}

	@Test
	public void testSignupSuccess() {

		performSignup();

		assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginSuccess() {

		performSignup();

		performLogin();

		assertEquals("Home", driver.getTitle());
	}

	@Test
	public void redirectToLogin() {

		performLogout();

		driver.get(baseURL + "/file");

		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupAlreadyExists() {

		performSignup();

		performSignup();

		WebElement errorMessage = driver.findElement(By.id("signup-error"));

		assertEquals("The username already exists.", errorMessage.getText());
	}

	@Test
	public void testUserLogoutAndRedirect(){

		performSignup();

		performLogin();

		performLogout();

		driver.get(baseURL + "/file");

		assertEquals("Login", driver.getTitle());

	}


	@Test
	public void testCreateCredential(){

		performSignup();

		performLogin();

		HomePage homePage = new HomePage(driver);
		homePage.createCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD);

		driver.get(baseURL + "/home");
		homePage.redirectToCredentialPage();

		List<String> credentialUrlList = homePage.getCredentialUrlList();
		List<String> credentialUsernameList = homePage.getCredentialUsernameList();
		List<String> credentialPasswordList = homePage.getCredentialPasswordList();

		assertEquals(CREDENTIAL_URL, credentialUrlList.stream().findFirst().get());
		assertEquals(CREDENTIAL_USERNAME, credentialUsernameList.stream().findFirst().get());
		assertNotEquals(CREDENTIAL_PASSWORD, credentialPasswordList.stream().findFirst().get());

		homePage.deleteCredential(0);

	}

	@Test
	public void testEditCredential(){

		performSignup();

		performLogin();

		HomePage homePage = new HomePage(driver);
		homePage.createCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD);

		driver.get(baseURL + "/home");
		homePage.redirectToCredentialPage();

		homePage.editCredential(CREDENTIAL_URL_EDIT, CREDENTIAL_USERNAME_EDIT, CREDENTIAL_PASSWORD_EDIT, 0);

		driver.get(baseURL + "/home");
		homePage.redirectToCredentialPage();

		List<String> credentialUrlList = homePage.getCredentialUrlList();
		List<String> credentialUsernameList = homePage.getCredentialUsernameList();
		List<String> credentialPasswordList = homePage.getCredentialPasswordList();

		assertEquals(CREDENTIAL_URL_EDIT, credentialUrlList.stream().findFirst().get());
		assertEquals(CREDENTIAL_USERNAME_EDIT, credentialUsernameList.stream().findFirst().get());
		assertNotEquals(CREDENTIAL_PASSWORD_EDIT, credentialPasswordList.stream().findFirst().get());

		homePage.deleteCredential(0);

	}

	@Test
	public void testDeleteCredential(){

		performSignup();

		performLogin();

		HomePage homePage = new HomePage(driver);
		homePage.createCredential(CREDENTIAL_URL, CREDENTIAL_USERNAME, CREDENTIAL_PASSWORD);

		driver.get(baseURL + "/home");
		homePage.redirectToCredentialPage();

		homePage.deleteCredential(0);

		driver.get(baseURL + "/home");
		homePage.redirectToCredentialPage();

		List<String> credentialUrlList = homePage.getCredentialUrlList();
		List<String> credentialUsernameList = homePage.getCredentialUsernameList();
		List<String> credentialPasswordList = homePage.getCredentialPasswordList();

		assertEquals(Collections.EMPTY_LIST, credentialUrlList);
		assertEquals(Collections.EMPTY_LIST, credentialUsernameList);
		assertEquals(Collections.EMPTY_LIST, credentialPasswordList);

	}

	@Test
	public void testCreateNote(){

		performSignup();

		performLogin();

		HomePage homePage = new HomePage(driver);
		homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

		driver.get(baseURL + "/home");
		homePage.redirectToNotePage();

		List<String> noteTitleList = homePage.getNoteTitleList();
		List<String> noteDescriptionList = homePage.getNoteDescriptionList();

		assertEquals(NOTE_TITLE, noteTitleList.stream().findFirst().get());
		assertEquals(NOTE_DESCRIPTION, noteDescriptionList.stream().findFirst().get());

		homePage.deleteNote(0);

	}

	@Test
	public void testEditNote(){

		performSignup();

		performLogin();

		HomePage homePage = new HomePage(driver);
		homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

		driver.get(baseURL + "/home");
		homePage.redirectToNotePage();

		homePage.editNote(NOTE_TITLE_EDIT, NOTE_DESCRIPTION_EDIT, 0);

		driver.get(baseURL + "/home");
		homePage.redirectToNotePage();

		List<String> noteTitleList = homePage.getNoteTitleList();
		List<String> noteDescriptionList = homePage.getNoteDescriptionList();

		assertEquals(NOTE_TITLE_EDIT, noteTitleList.stream().findFirst().get());
		assertEquals(NOTE_DESCRIPTION_EDIT, noteDescriptionList.stream().findFirst().get());

		homePage.deleteNote(0);

	}

	@Test
	public void testDeleteNote(){

		performSignup();

		performLogin();

		HomePage homePage = new HomePage(driver);
		homePage.createNote(NOTE_TITLE, NOTE_DESCRIPTION);

		driver.get(baseURL + "/home");
		homePage.redirectToNotePage();

		homePage.deleteNote(0);

		driver.get(baseURL + "/home");
		homePage.redirectToNotePage();

		List<String> noteTitleList = homePage.getNoteTitleList();
		List<String> noteDescriptionList = homePage.getNoteDescriptionList();

		assertEquals(Collections.EMPTY_LIST, noteTitleList);
		assertEquals(Collections.EMPTY_LIST, noteDescriptionList);

	}


	public void performLogin(){

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);
	}

	public void performSignup(){

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
	}

	public void performLogout(){

		driver.get(baseURL + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.logout();
	}

}
