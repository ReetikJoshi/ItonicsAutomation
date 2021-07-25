package pages.facebook;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Header {

	private WebDriver driver;

	public Header(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * click the profile icon at the top right section
	 */
	public void clickProfileIcon() {
		// getting the first matching element
		WebElement profileIcon = driver.findElements(By.cssSelector(".ehxjyohh.kr520xx4 .du4w35lb .oajrlxb2")).get(0);
		profileIcon.click();
	}

	public void clickLogoutBtn() {
		WebElement logoutBtn = driver.findElement(By.xpath("//span[contains(text(),'Log Out')]"));
		Assert.assertTrue(logoutBtn.isDisplayed());
		Assert.assertEquals(logoutBtn.getText(), "Log Out");
		logoutBtn.click();
	}
}
