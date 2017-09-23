package com.irseauth.runners;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.irseauth.pages.ContactFormPage;
import com.irseauth.pages.LoginPage;
import com.irseauth.utilities.Driver;
import com.relevantcodes.extentreports.LogStatus;

public class IRSDemo {

	public static WebDriver driver;
	ContactFormPage contactForm = PageFactory.initElements(driver, ContactFormPage.class);
	LoginPage login = PageFactory.initElements(driver, LoginPage.class);

	@BeforeClass
	public static void initializeTestBaseSetup() {
		driver = Driver.getInstance();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void contactForm() throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
		contactForm.startLogging("Contact Form");
		contactForm.dataEntry();
		contactForm.finishLogging();
	}

	@Test
	public void loginTest() {
		login.startLogging("Login functionality");
		login.logData(LogStatus.WARNING, "Nothing here");
		login.finishLogging();
	}

	@AfterClass
	public static void tearDown() {
		Driver.closeDriver();
	}
}
