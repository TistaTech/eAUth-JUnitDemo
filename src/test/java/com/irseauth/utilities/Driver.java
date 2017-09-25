package com.irseauth.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;



public class Driver {

	private static WebDriver driver;

	public static WebDriver getInstance() {
		if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
			switch (ConfigurationReader.getProperty("browser")) {
			case "firefox":
				FirefoxDriverManager.getInstance().setup();
				driver = new FirefoxDriver();
				break;
			case "chrome":
				ChromeDriverManager.getInstance().setup();
				driver = new ChromeDriver();
				break;
			case "ie":
				InternetExplorerDriverManager.getInstance().setup();
				driver = new InternetExplorerDriver();
				break;
			
			case "safari":
				driver = new SafariDriver();
				break;
			
			default:
				ChromeDriverManager.getInstance().setup();
				driver = new ChromeDriver();
			}
		}
		return driver;
	}

	public static void closeDriver() {
		
		if (driver != null) {
			driver.quit();
			driver = null;
		}

	}

}