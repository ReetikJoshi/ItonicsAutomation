package pages.amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import helper.HelperMethods;

public class Header {

	protected WebDriver driver;

	public Header(WebDriver driver) {
		this.driver = driver;
	}

	By allDropdownOptions = By.cssSelector(".searchSelect option");
	By departmentDropdown = By.className("searchSelect");
	By amazonLogo = By.className("nav-logo-link");

	/**
	 * grab the count of the dropdown options
	 * 
	 * @return count of dropdown options
	 */
	public int getDropdownOptionsCount() {
		// defining explicit wait
		HelperMethods.explicitWait(allDropdownOptions);
		return driver.findElements(allDropdownOptions).size();
	}

	public void selectDropdown(int index) {
		Select dropdown = new Select(driver.findElement(departmentDropdown));
		dropdown.selectByIndex(index);
	}

	/**
	 * Get the text of the selected dropdown option
	 */
	public String getSelectedOptionText() {
		Select dropdown = new Select(driver.findElement(departmentDropdown));
		return dropdown.getFirstSelectedOption().getText();
	}

	/**
	 * validate if the logo is visible and has the specified href attribute value
	 */
	public void checkLogo() {
		WebElement logo = driver.findElement(amazonLogo);
		Assert.assertTrue(logo.isDisplayed());
		Assert.assertTrue(logo.getAttribute("href").contains("/ref=nav_logo"));
	}
}
