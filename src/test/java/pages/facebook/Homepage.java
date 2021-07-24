package pages.facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Homepage {

	private WebDriver driver;

	public Homepage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Check the full name shown in the side bar
	 */
	public void checkFullName(String fullName) {
		WebElement fullNameDiv = driver.findElement(By.xpath("//span[contains(text(),'" + fullName + "')]"));
		Assert.assertEquals(fullNameDiv.getText(), fullName);
	}

	public void checkWelcomeText(String firstName) {
		WebElement welcomeTextDiv = driver.findElement(By.cssSelector(".bcvklqu9.nzypyw8j > span"));
		Assert.assertEquals(welcomeTextDiv.getText(), "Welcome to Facebook, " + firstName);
	}
}
