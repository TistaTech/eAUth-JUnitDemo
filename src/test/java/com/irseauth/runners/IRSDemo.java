package com.irseauth.runners;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.irseauth.pages.BasePage;
import com.irseauth.pages.ContactFormPage;
import com.irseauth.pages.LoginPage;
import com.irseauth.utilities.Driver;

public class IRSDemo {

	public WebDriver driver;
	ContactFormPage contactForm;
	LoginPage login;

	@BeforeMethod                                                                                           // Keep it as "BeforeMethod" so browser restarts for each test case. Needed due to cashing issues on IRS website; 
	public void setUp() {
		driver = Driver.getInstance();																		// Driver instance is generated inside the Driver class based on the browser parameter provided in configuration.properties file
		contactForm = PageFactory.initElements(driver, ContactFormPage.class);								// Pages have to be instantiated inside "Before Method" so constructors inside these pages instantiate the driver;  
		login = PageFactory.initElements(driver, LoginPage.class);		 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}

	@Test(priority = 0, enabled = true)
	public void contactForm()
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {  
		contactForm.startLogging("Contact Form");
		contactForm.dataEntryContactForm_PO();
		contactForm.finishLogging();
	}

	@Test(priority = 1, enabled = true)
	public void loginTest()
			throws EncryptedDocumentException, InvalidFormatException, InterruptedException, IOException {
		login.startLogging("Login test");
		login.dataEntryLogin_PO();
		login.finishLogging();
	}

//	@Test(priority = 2, enabled = true)
//	public void contactForm2()
//			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
//		contactForm.startLogging("Contact Form2");
//		contactForm.dataEntryContactForm_PO();
//		contactForm.finishLogging();
//	}
//
//	@Test(priority = 3, enabled = true)
//	public void loginTest2()
//			throws EncryptedDocumentException, InvalidFormatException, InterruptedException, IOException {
//		login.startLogging("Login test2");
//		login.dataEntryLogin_PO();
//		login.finishLogging();
//	}

	@AfterMethod                                                                                             // Keep it as "After Method" so browser closes after each test case;
	public void tearDown() throws Exception {
		Driver.closeDriver();	
	}
	
	@AfterTest																								 // Keep it as "After Test" - report needs to be archived only once;
	public void archiveReport() throws Exception {
		BasePage.archiveReport();
	}
}
