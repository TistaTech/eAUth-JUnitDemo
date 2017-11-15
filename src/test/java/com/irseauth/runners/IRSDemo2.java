package com.irseauth.runners;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.irseauth.pages.BasePage;
import com.irseauth.pages.ContactFormPage;
import com.irseauth.pages.LoginPage;
import com.irseauth.utilities.Driver;

public class IRSDemo2 {

	ContactFormPage contactForm = new ContactFormPage();
	LoginPage login = new LoginPage();
	
	@Test(priority = 2, enabled = false)
	public void contactForm() throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
		contactForm.startLogging("Contact Form");
		contactForm.dataEntryContactForm_PO();
		contactForm.finishLogging();
	}

	@Test(priority = 3, enabled = false)
	public void loginTest() throws EncryptedDocumentException, InvalidFormatException, InterruptedException, IOException {
		login.startLogging("Login test");
		login.dataEntryLogin_PO();
		login.finishLogging();
	}


}
