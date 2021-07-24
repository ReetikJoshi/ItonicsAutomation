package testCase;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import helper.HelperMethods;
import pages.amazon.Header;

public class Amazon {

	private WebDriver driver;
	private Header header;

	@BeforeClass
	public void visitAmazonSite() throws IOException, ParseException {
		// Setup the driver and visit amazon site
		driver = HelperMethods.initializeDriver();
		// Get the amazon URL fron environment using helper method
		String amazonURL = HelperMethods.getFrontendURL("amazon");
		// visit amazon url
		driver.get(amazonURL);
		// validate the amazon logo
		header = new Header(driver);
		header.checkLogo();
		// check current page(homepage) title
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, "Amazon.com. Spend less. Smile more.");
	}

	/**
	 * Select random options from the department dropdown of the header if available
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void selectRandomDropdown() throws InterruptedException {
		// ! Object instances
		int dropdownOptionsCount = header.getDropdownOptionsCount();
		// adding guard point to continue only if there are dropdown options
		if (dropdownOptionsCount == 0)
			return;
		// generate random number between 1 and the length of dropdown options
		// Not taking 0 as it is for 'All Departments' option
		int randomNumber = generateRandomNumber(1, dropdownOptionsCount);
		header.selectDropdown(randomNumber);
		Thread.sleep(2000);
		// validate that All Departments options is not selected in the dropdown
		// can be done by checking if the current option is not equal to all departments
		Assert.assertFalse(header.getSelectedOptionText().matches("All.*"));
	}

	/**
	 * generate random number of a given range
	 * 
	 * @param min The smallest number
	 * @param max The largest number
	 */
	public int generateRandomNumber(int min, int max) {
		float number = (float) (Math.random() * (max - min) + min);
		return (int) Math.floor(number);
	}

	@AfterClass
	public void tearDown() {
		HelperMethods.tearDown();
	}
}
