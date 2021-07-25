package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HelperMethods {

	public static WebDriver driver;
	protected static JSONObject env;
	protected static JSONObject wait;

	/***
	 * Read json file of the given path
	 * 
	 * @param filePath The relative file path
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static JSONObject readJSONFile(String filePath) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		FileReader reader = new FileReader(System.getProperty("user.dir") + filePath);
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}

	/**
	 * Initialize driver according to the passed browser name in env
	 * 
	 * @return driver instance
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public static WebDriver initializeDriver() throws IOException, ParseException {
		env = HelperMethods.readJSONFile("/Environment.json");
		// get the browser name in lower case
		String browser = env.get("browser").toString();
		// invoking the browser according to the name specified in the environment file
		if (browser.equalsIgnoreCase("chrome") && browser.length() > 0) {
			// Switching off chrome browser notifications
			ChromeOptions options = switchOffChromeBrowserNotification();
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox") && browser.length() > 0) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge") && browser.length() > 0) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.out.println("Pass a valid browser name in the environment");
		}
		// setting global implicit wait
		wait = (JSONObject) env.get("wait");
		long implicitTimeout = (Long) wait.get("implicitWait");
		driver.manage().timeouts().implicitlyWait(implicitTimeout, TimeUnit.SECONDS);
		// maximize window
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * switching off the browser notification seen after logging into facebook.<br>
	 * Note: It is only seen in chrome browser
	 * 
	 * @return The new configuration with notification turned off
	 */
	public static ChromeOptions switchOffChromeBrowserNotification() {
		// Create prefs map to store all preferences
		Map<String, Object> prefs = new HashMap<String, Object>();
		// Put this into prefs map to switch off browser notification
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		return options;
	}

	/**
	 * Close all the windows, destroy sessions and set the driver to null
	 */
	public static void tearDown() {
		driver.quit();
		driver = null;
	}

	/**
	 * Specify the explicit wait in a selector/locator with maximum time given in
	 * environment file
	 * 
	 * @param locator
	 */
	public static void explicitWait(By locator) {
		// Grab the explicit wait from the environment
		long explicitWait = (Long) wait.get("explicitWait");
		WebDriverWait wait = new WebDriverWait(driver, explicitWait);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * Capture the screenshot of test under failure
	 * 
	 * @param testName The name of the failed test which is passed through the
	 *                 TestListener class interface
	 * @throws IOException
	 */
	public static void captureScreenshotOnFailure(String testName) throws IOException {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// specifying the location to copy the screenshot in the local directory
		FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\Screenshot\\" + testName + ".png"));
	}

	/**
	 * Get the frontend URL of the passed domain
	 * 
	 * @param domain The domain of the site i.e facebook or amazon
	 * @return The frontend url of the specified domain
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getSpecificFrontendURL(String domain) throws IOException, ParseException {
		String url = null;
		if (domain.equalsIgnoreCase("amazon")) {
			url = getFrontendURL("amazon");
		} else if (domain.equalsIgnoreCase("facebook")) {
			url = getFrontendURL("facebook");
		} else {
			System.out.println("pass a valid domain name in argument while calling this method");
		}
		return url;
	}

	/**
	 * 1. Helper method to get the frontend url.<br>
	 * 2. Resusable method to get the URL;
	 * 
	 * @param domain
	 * @return The frontend url of the passed domain
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String getFrontendURL(String domain) throws IOException, ParseException {
		// get amazon json object
		JSONObject site = (JSONObject) env.get(domain);
		// get the current url in string
		String currentURL = (String) site.get("currentURL");
		// use that current url string to get the json object of url
		JSONObject currentURLObj = (JSONObject) site.get(currentURL);
		// get the frontend URL
		String frontendURL = (String) currentURLObj.get("frontend");
		return frontendURL;
	}

	/***
	 * Get the value of loginDetails key from facebook
	 * 
	 * @return The jsonObject of loginDetails
	 */
	public static JSONObject getFacebookLoginDetails() {
		JSONObject facebookObj = (JSONObject) env.get("facebook");
		String currentURL = (String) facebookObj.get("currentURL");
		JSONObject currentURLObj = (JSONObject) facebookObj.get(currentURL);
		JSONObject loginDetailsOBj = (JSONObject) currentURLObj.get("loginDetails");
		return loginDetailsOBj;
	}

	/**
	 * Get the data of the passed excel file(xlsx)
	 * 
	 * @return The array containing 2nd row data
	 * @throws IOException
	 */
	public static ArrayList<String> getExcelData() throws IOException {
		ArrayList<String> dataArr = new ArrayList<String>();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/testData/fbLoginCredentials.xlsx");
		// Read the entire excel file
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		// Get the total number of sheets
		int totalSheets = workbook.getNumberOfSheets();
		// Loop through all sheets
		for (int i = 0; i < totalSheets; i++) {
			// Check the sheetName matches or not
			if (workbook.getSheetName(i).equalsIgnoreCase("fbLoginCredentials")) {
				// Get access to sheet
				// Sheet is the collection of rows
				XSSFSheet sheet = workbook.getSheetAt(i);
				// Row is the collection of cells
				Iterator<Row> rows = sheet.iterator();
				// get the first row
				Row firstRow = rows.next();
				// get the second row
				Row secondRow = rows.next();
				Iterator<Cell> secondCell = secondRow.cellIterator();
				while (secondCell.hasNext()) {
					Cell secondCellValue = secondCell.next();
					// store all the second row values in the array and return it
					dataArr.add(secondCellValue.getStringCellValue());
				}
			}
		}
		// Returning the array containing 2nd row values
		return dataArr;
	}
}
