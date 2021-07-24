package testCase;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import helper.HelperMethods;

public class Facebook {

	private WebDriver driver;

	@BeforeClass
	public void visitFacebookSite() throws IOException, ParseException {
		driver = HelperMethods.initializeDriver();
		// get the facebook URL from environment using helper methods
		String facebookURL = HelperMethods.getSpecificFrontendURL("facebook");
		// visit the URL
		driver.get(facebookURL);
	}

	@Test
	public void tetst() {

	}
}
