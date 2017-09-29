package com.irseauth.pages;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
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

	public BasePage() {
		this.driver = Driver.getInstance();

	}

	// This method finds a WebElement based on it's locator etc. xpath,
	// cssclass, class, id etc.
	public WebElement find(By locator) {
		return driver.findElement(locator);
	}

	// These methods are to generalize the url get method for the browser to
	// navigate to a page defined as the url.
	public void navigateToPage(String URL, String title) throws IOException {
		driver.get(ConfigurationReader.getProperty(URL));
		if ((driver.getTitle().equals(ConfigurationReader.getProperty(title)))) {
			logData(LogStatus.INFO, "Successfully navigated to " + ConfigurationReader.getProperty(URL));
		} else {
			logData(LogStatus.WARNING, "Couldn't navigated to " + ConfigurationReader.getProperty(URL));
		}
	}

	// This method find a locator and selects by Visible Text present.
	public void selectByText(By locator, String value) {
		new Select(driver.findElement(locator)).selectByVisibleText(value);
	}

	// This method clicks on a WebElement based on it's locator etc. xpath,
	// cssclass, class, id etc.
	public void click(WebElement element) {
		element.click();
	}

	// This method uses javascript to click on a WebElement based on it's
	// locator etc. xpath, cssclass, class, id etc.
	public void js_click(By locator) {
		JavascriptExecutor js_executor = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(locator);
		js_executor.executeScript("var elem=arguments[0]; setTimeout(function() { elem.click();}, 100)", element);
		ImplicitWait(20);
	}

	// This method presses tab on the selected element
	public void pressTabOn(By locator) {
		WebElement element = driver.findElement(locator);
		element.sendKeys(Keys.TAB);
		ImplicitWait(10);
	}

	// This method presses return on the selected element
	public void pressReturnOn(By locator) {
		WebElement element = driver.findElement(locator);
		element.sendKeys(Keys.RETURN);
	}

	// This method presses space on the selected element
	public void pressSpaceOn(By locator) {
		WebElement element = driver.findElement(locator);
		element.sendKeys(Keys.SPACE);
	}

	// This method selects multiple values by holding down the control key.
	public void selectMultiple(By locator) {
		driver.findElement(locator).sendKeys(Keys.CONTROL);
	}

	// This method types in text in a WebElement based on it's locator etc.
	// xpath, cssclass, class, id etc.
	public void type(String inputText, WebElement element) {
		element.clear();
		element.sendKeys(inputText);
	}

	// This method submits the current form.
	public void submit(By locator) {
		find(locator).submit();
	}

	// This method switch a frame focus for a web applications with frames.
	public void switch_frame(By locator) {
		driver.switchTo().frame(find(locator));
	}

	// This medhod switches to the default frame.
	public void switch_default() {
		driver.switchTo().defaultContent();
	}

	// This method retrieves text from the defined element
	public void GetElementText(WebElement element) {
		element.getText();
	}

	// This method validates an item is currently displayed based on it's
	// locator etc. xpath, cssclass, class, id etc..
	public Boolean isDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException exception) {
			return false;
		}
	}

	// This method validates the existence of an element on a page
	public void ValidateElementExists(WebElement element) {
		Assert.assertTrue(isDisplayed(element));
	}

	// This method validates that an element that should not be on the page does
	// not show up
	public void ValidateNoElementExists(WebElement element) {
		Assert.assertFalse(isDisplayed(element));
	}

	// This method waits for an element to be displayed for the defined timeout
	// amount.
	public Boolean waitForTheElementToBeDisplayed(WebElement element, Integer... timeout) {
		try {
			waitFor(ExpectedConditions.visibilityOf(element), (timeout.length > 0 ? timeout[0] : null));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	// This method waits for an element to become clickable
	public void waitUntilClickable(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// This method switches the test window focus to a specific window.
	public void switch_window_focus_to(String value) {
		driver.switchTo().window(value);
	}

	// This method closes the browser window.
	public void close_window() {
		driver.close();
	}

	// This method switched the screen to the default frame
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	// This method waits for a page frame to be displayed for the defined
	// timeout amount.
	public Boolean waitAndSwitchToFrame(By locator, Integer... timeout) {
		WebDriverWait wait = new WebDriverWait(driver, 180);
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	// This method waits for a page frame to be displayed for the defined
	// timeout amount.
	public Boolean waitForAlert(Integer... timeout) {
		WebDriverWait wait = new WebDriverWait(driver, 180);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
		} catch (org.openqa.selenium.TimeoutException exception) {
			return false;
		}
		return true;
	}

	// This method an element to be in an expected state.
	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeout) {
		timeout = timeout != null ? timeout : 5;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(condition);
	}

	public void script_stand_by(Integer timeout) throws InterruptedException {
		Thread.sleep(timeout);
	}

	// This is an implicit Wait based on an amount preferred.
	public void ImplicitWait(Integer timeout) {
		timeout = timeout != null ? timeout : 5;
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	// This method waits for a JS alert, if present the driver will switch to
	// the alert for further interaction.
	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	// This method switch focus to a Javascript alert and accepts it.
	public void AcceptAlert() {
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

	public void finishLogging() {
		report.endTest(test);
		report.flush();
	}

	public void logData(LogStatus status, String message) throws IOException {
		String name = getRandomString(10);
		File srcImage = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcImage, new File("./extentreports/" + name + ".png"));
		test.log(status, message + test.addScreenCapture("./" + name + ".png"));
	}

	public void startLogging(String testName) {
		report = ExtentFactory.getInstance();
		test = report.startTest(testName);
	}
	
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}

}