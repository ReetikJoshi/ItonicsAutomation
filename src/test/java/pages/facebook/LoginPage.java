package pages.facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage {

	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	By facebookLogo = By.className("fb_logo");
	By loginBtn = By.cssSelector("[name='login']");

	/**
	 * Check the logo visibility and image src
	 */
	public void checkFacebookLogoVisibility() {
		WebElement logo = driver.findElement(facebookLogo);
		Assert.assertTrue(logo.isDisplayed());
		// check src
		Assert.assertTrue(logo.getAttribute("src").contains("/rsrc.php/y8/r/dF5SId3UHWd.svg"));
	}

	/**
	 * Validate the login btn text and visibilty
	 */
	public void checkLoginBtnVisibility() {
		WebElement login = driver.findElement(loginBtn);
		Assert.assertEquals(login.getText(), "Log In");
		Assert.assertTrue(login.isDisplayed());
	}

	public void enterEmail(String email) {
		WebElement emailField = driver.findElement(By.id("email"));
		emailField.clear();
		// enter email
		emailField.sendKeys(email);
		// checking placeholder value
		Assert.assertEquals(emailField.getAttribute("placeholder"), "Email or Phone Number");
		// validate the input field has the currently entered value or not
		Assert.assertEquals(emailField.getAttribute("value"), email);
	}

	public void enterPassword(String password) {
		WebElement passwordField = driver.findElement(By.id("pass"));
		passwordField.clear();
		// enter password
		passwordField.sendKeys(password);
		// checking placeholder value
		Assert.assertEquals(passwordField.getAttribute("placeholder"), "Password");
		// validate the input field has the currently entered value or not
		Assert.assertEquals(passwordField.getAttribute("value"), password);
	}

	/**
	 * click the login button
	 * 
	 * @throws InterruptedException
	 */
	public void clickLoginBtn() throws InterruptedException {
		driver.findElement(loginBtn).click();
		Thread.sleep(4000);
	}

	/**
	 * validate the msg/alert/validation when the user logs in with incorrect
	 * password
	 */
	public void checkPasswordIncorrectMsg() {
		WebElement passwordWrongMsg = driver.findElement(By.className("_9ay7"));
		Assert.assertTrue(passwordWrongMsg.isDisplayed());
		Assert.assertEquals(passwordWrongMsg.getText(), "The password you’ve entered is incorrect. Forgot Password?");
	}

	/**
	 * validate the message seen after entering invalid email and valid password
	 */
	public void checkAccountNotFoundMsg() {
		WebElement accountNotFound = driver.findElement(By.className("_9kq2"));
		String expectedMsg = "We couldn't find an account matching the login info you entered, but found an account that closely matches based on your login history.";
		Assert.assertTrue(accountNotFound.getText().contains(expectedMsg));
	}

	/**
	 * validate the msg seen after entering invalid email and invalid password
	 */
	public void checkEmailNotConnectedMsg() {
		WebElement emailNotConnected = driver.findElement(By.cssSelector("._9ay7"));
		String expectedMsg = "The email you entered isn’t connected to an account.";
		Assert.assertTrue(emailNotConnected.getText().contains(expectedMsg));
	}

	/**
	 * validate the msg seen after logging in with blank credentials
	 */
	public void checkEmailOrMobileNotConnectedMsg() {
		WebElement emailNotConnected = driver.findElement(By.cssSelector("._9ay7"));
		String expectedMsg = "The email or mobile number you entered isn’t connected to an account.";
		Assert.assertTrue(emailNotConnected.getText().contains(expectedMsg));
	}
}
