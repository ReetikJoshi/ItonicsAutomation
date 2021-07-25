package testCase;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import helper.HelperMethods;
import pages.facebook.Header;
import pages.facebook.Homepage;
import pages.facebook.LoginPage;

public class Facebook {

	// Global Variables
	private WebDriver driver;
	private JSONObject loginCredentialsObj;
	private ArrayList<String> excelLoginData;
	private String loginType;
	// class object instances
	LoginPage loginP;
	Homepage homeP;
	Boolean loginStatus;

	@BeforeClass
	public void visitFacebookSite() throws IOException, ParseException {
		driver = HelperMethods.initializeDriver();
		// get the facebook URL from environment using helper methods
		String facebookURL = HelperMethods.getSpecificFrontendURL("facebook");
		// visit the URL
		driver.get(facebookURL);
		// validate the login page
		validateLoginPage();
	}

	/**
	 * Check the following:<br>
	 * - page title<br>
	 * - logo src<br>
	 * - login button visibility
	 */
	public void validateLoginPage() {
		loginP = new LoginPage(driver);
		// check title
		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Facebook - Log In or Sign Up");
		// check logo
		loginP.checkFacebookLogoVisibility();
		// check login button visibility
		loginP.checkLoginBtnVisibility();
	}

	/**
	 * 1. Get the loginStatus and loginType from the environment.<br>
	 * 2. Login the user according to these values extracted. <br>
	 * 3. Note: User will either get logged in from the details passed in json or
	 * xlsx file inside resources folder
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	@Test
	public void validateLoginUser() throws IOException, ParseException, InterruptedException {
		JSONObject loginDetails = HelperMethods.getFacebookLoginDetails();
		loginStatus = (Boolean) loginDetails.get("loginStatus");
		loginType = (String) loginDetails.get("type");
		// Guard point to not continue if the login status is set to false
		if (!loginStatus)
			return;
		if (loginType.equalsIgnoreCase("xlsx")) {
			excelLoginData = HelperMethods.getExcelData();
			loginUser(excelLoginData.get(0), excelLoginData.get(1));
		} else {
			loginCredentialsObj = HelperMethods.readJSONFile("/src/test/resources/testData/fbLoginCredentials.json");
			loginUser((String) loginCredentialsObj.get("email"), (String) loginCredentialsObj.get("password"));
		}
	}

	/**
	 * Reusable method to login user according to the credentials passed
	 * 
	 * @param email
	 * @param password
	 * @throws InterruptedException
	 */
	public void loginUser(String email, String password) throws InterruptedException {
		loginP.enterEmail(email);
		Thread.sleep(1000);
		loginP.enterPassword(password);
		Thread.sleep(1000);
		loginP.clickLoginBtn();
	}

	/**
	 * validate the homepage after login <br>
	 * - fullname <br>
	 * - welcome text <br>
	 * - current homepage Title
	 */
	@Test(dependsOnMethods = "validateLoginUser")
	public void validateHomepage() {
		// Checking the login status set in the env file
		if (!loginStatus)
			return;
		homeP = new Homepage(driver);
		String firstName, lastName;
		if (loginType.equalsIgnoreCase("xlsx")) {
			firstName = excelLoginData.get(2);
			lastName = excelLoginData.get(3);
		} else {
			firstName = (String) loginCredentialsObj.get("firstName");
			lastName = (String) loginCredentialsObj.get("lastName");
		}
		String fullName = firstName + " " + lastName;
		// validate fullname and welcome text
		homeP.checkFullName(fullName);
		homeP.checkWelcomeText(firstName);
		// validate homepage title
		String homepageTitle = driver.getTitle();
		Assert.assertTrue(homepageTitle.contains("Facebook"));
		Assert.assertFalse(homepageTitle.contains("Facebook - Log In or Sign Up"));
	}

	/**
	 * 1. Logout user.<br>
	 * 2. Validate the user can't login with valid email and invalid password. <br>
	 * 3. validate the user can't login with invalid email and valid password. <br>
	 * 4. validate the user can't login with invalid email and invalid password.<br>
	 * 5. validate the page on logging with blank credentials.
	 * 
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods = "validateHomepage")
	public void validateLoginNegativeScenarios() throws InterruptedException {
		// logout user
		Header header = new Header(driver);
		header.clickProfileIcon();
		header.clickLogoutBtn();
		Thread.sleep(2000);
		// validate the user can't login with valid email and invalid password
		String email, password;
		if (loginType.equalsIgnoreCase("xlsx")) {
			email = excelLoginData.get(0);
			password = excelLoginData.get(1);
		} else {
			email = (String) loginCredentialsObj.get("email");
			password = (String) loginCredentialsObj.get("password");
		}
		// login user
		loginUser(email, "random");
		Thread.sleep(1000);
		loginP.checkPasswordIncorrectMsg();
		Assert.assertFalse(driver.getCurrentUrl().contains("?sk=welcome"));
		// go back
		driver.navigate().back();
		// validate the user can't login with invalid email and valid password
		loginUser("sdkfjsdk@gmail.com", password);
		Thread.sleep(1000);
		loginP.checkAccountNotFoundMsg();
		Assert.assertFalse(driver.getCurrentUrl().contains("?sk=welcome"));
		// go back
		driver.navigate().back();
		// validate the user can't login with invalid email and invalid password
		loginUser("sdfsadf@mailinator.com", "kjdfsjda");
		Thread.sleep(1000);
		loginP.checkEmailNotConnectedMsg();
		Assert.assertFalse(driver.getCurrentUrl().contains("?sk=welcome"));
		// go back
		driver.navigate().back();
		Thread.sleep(1000);
		driver.navigate().refresh();
		// validate the page on logging with blank credentials.
		loginP.clickLoginBtn();
		Thread.sleep(1000);
		loginP.checkEmailOrMobileNotConnectedMsg();
		Assert.assertFalse(driver.getCurrentUrl().contains("?sk=welcome"));
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		driver = null;
	}
}
