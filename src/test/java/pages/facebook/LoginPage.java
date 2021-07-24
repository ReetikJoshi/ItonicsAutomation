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
		// enter email
		emailField.sendKeys(email);
		// checking placeholder value
		Assert.assertEquals(emailField.getAttribute("placeholder"), "Email or Phone Number");
		// validate the input field has the currently entered value or not
		Assert.assertEquals(emailField.getAttribute("value"), email);
	}

	public void enterPassword(String password) {
		WebElement passwordField = driver.findElement(By.id("pass"));
		// enter password
		passwordField.sendKeys(password);
		// checking placeholder value
		Assert.assertEquals(passwordField.getAttribute("placeholder"), "Password");
		// validate the input field has the currently entered value or not
		Assert.assertEquals(passwordField.getAttribute("value"), password);
	}

	/**
	 * click the login button and validate the url
	 * 
	 * @throws InterruptedException
	 */
	public void clickLoginBtn() throws InterruptedException {
		driver.findElement(loginBtn).click();
		Thread.sleep(4000);
		// check the url
		Assert.assertTrue(driver.getCurrentUrl().contains("?sk=welcome"));
	}

}
