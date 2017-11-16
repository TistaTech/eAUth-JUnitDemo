package com.irseauth.pages;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.irseauth.utilities.ConfigurationReader;
import com.irseauth.utilities.Driver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.irseauth.pages.ExtentFactory;

public class BasePage {

	public WebDriver driver;
	JavascriptExecutor js;
	ExtentReports report;
	ExtentTest test;

	private static final String BAT_PATH = "C:/Bat_Files/ArchiveReport.bat";
	private static String cmd = "cmd /c start " + BAT_PATH;

	public BasePage() {
		this.driver = Driver.getInstance();
	}


	public void navigateToPage(String URL, String title) throws IOException {									// Navigates to URL, verifies the title and logs the result
		driver.get(ConfigurationReader.getProperty(URL));
		if ((driver.getTitle().equals(ConfigurationReader.getProperty(title)))) {
			logData(LogStatus.INFO, "Successfully navigated to " + ConfigurationReader.getProperty(URL));
		} else {
			logData(LogStatus.WARNING, "Couldn't navigated to " + ConfigurationReader.getProperty(URL));
		}
	}
																												
	public void js_click(By locator) {																			// This method uses javascript to click on a WebElement based on it's locator:xpath, cssclass, class, id etc.
		JavascriptExecutor js_executor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(locator);
		js_executor.executeScript("var elem=arguments[0]; setTimeout(function() { elem.click();}, 100)", element);
		implicitWait(20);
	}

	public void pressTabOn(By locator) {																		// This method presses tab on the selected element
		WebElement element = driver.findElement(locator);
		element.sendKeys(Keys.TAB);
		implicitWait(10);
	}

	public void pressReturnOn(By locator) {																		// This method presses return on the selected element
		WebElement element = driver.findElement(locator);
		element.sendKeys(Keys.RETURN);
	}

	public void pressSpaceOn(By locator) {																		// This method presses space on the selected element
		WebElement element = driver.findElement(locator);
		element.sendKeys(Keys.SPACE);
	}

	public void selectMultiple(By locator) {																	// This method selects multiple values by holding down the control key
		driver.findElement(locator).sendKeys(Keys.CONTROL);
	}

	public void type(String inputText, WebElement element) {													// This method clears the field and types in text in a WebElement based on it's locator
		element.clear();
		element.sendKeys(inputText);
	}

	public Boolean isDisplayed(WebElement element) {															// This method validates an item is currently displayed based on it's locator
		try {
			return element.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException exception) {
			return false;
		}
	}

	public void ValidateElementExists(WebElement element) {														// This method validates the existence of an element on a page
		Assert.assertTrue(isDisplayed(element));
	}

	public void ValidateNoElementExists(WebElement element) {													// This method validates that an element that should not be on the page does not show up 
		Assert.assertFalse(isDisplayed(element));
	}

	public Boolean waitForTheElementToBeDisplayed(WebElement element, Integer... timeout) {						// This method waits for an element to be displayed for the defined timeout amount
		try {
			waitFor(ExpectedConditions.visibilityOf(element), (timeout.length > 0 ? timeout[0] : null));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	public void waitUntilClickable(By locator) {																// This method waits for an element to become clickable
		WebDriverWait wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void switch_window_focus_to(String value) {															// This method switches the test window focus to a specific window.
		driver.switchTo().window(value);
	}

	public void switchToDefaultContent() {																		// This method switched the screen to the default frame
		driver.switchTo().defaultContent();
	}

	public Boolean waitAndSwitchToFrame(By locator, Integer... timeout) {										// This method waits for a page frame to be displayed for the defined timeout amount
		WebDriverWait wait = new WebDriverWait(driver, 180);
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	public Boolean waitForAlert(Integer... timeout) {															// This method waits for a page frame to be displayed for the defined timeout amount
		WebDriverWait wait = new WebDriverWait(driver, 180);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeout) {							// This method waits for an element to be in an expected state.
		timeout = timeout != null ? timeout : 5;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(condition);
	}

	public void script_stand_by(Integer timeout) throws InterruptedException {									// Puts thread to sleep
		Thread.sleep(timeout);
	}

	public void implicitWait(Integer timeout) {																	// This is an implicit Wait based on an amount preferred
		timeout = timeout != null ? timeout : 5;
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public boolean isAlertPresent(WebDriver driver) {															// This method waits for a JS alert, if present the driver will switch to the alert for further interaction
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public void AcceptAlert() {																					// This method switch focus to a Javascript alert and accepts it
		WebDriverWait wait = new WebDriverWait(driver, 600);
		if (isAlertPresent(driver)) {
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
	}

	public void dropdownMenuSelect(WebElement element, Integer index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	public void dropdownMenuSelect(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}

	public void startLogging(String testName) {																	// Starts ExtentReport logging with the specified test case name 
		report = ExtentFactory.getInstance();
		test = report.startTest(testName);
	}
	
	public void logData(LogStatus status, String message) throws IOException {									// Main logic of logging mechanism: takes screenshot, names it using random String and logs the data with the screenshot attached
		String name = getRandomString(10);
		File srcImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcImage, new File("./target/extentreports/images/" + name + ".png"));
		test.log(status, message + test.addScreenCapture("./images/" + name + ".png"));
	}

	public void finishLogging() {																				// Finishes ExtentReport logging for the specified test 
		report.endTest(test);
		report.flush();
	}

	public static String getRandomString(int length) {															// Random String generator
		StringBuilder sb = new StringBuilder();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}

	public static void archiveReport() throws Exception {														// Archives report: starts cmd and navigates to a .bat file where the logic is stored
		Runtime.getRuntime().exec(cmd);
		Thread.sleep(2000);
	}

	public static void highlightAreaWithJavascript(WebElement element) throws InterruptedException {			// Highlights and unhighlights the area with JS

		JavascriptExecutor js = (JavascriptExecutor)Driver.getInstance();
		js.executeScript("arguments[0].style.border= '3px solid red'", element);
		Thread.sleep(2000);
		js.executeScript("arguments[0].style.border= '3px solid white'", element);
	}

}