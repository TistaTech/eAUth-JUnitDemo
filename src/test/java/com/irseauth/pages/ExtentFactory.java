package com.irseauth.pages;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
	public static ExtentReports getInstance() {
		ExtentReports extent = new ExtentReports("C:/Users/Marat Metoff/Desktop/logintest.html", false);
		extent
			.addSystemInfo("Selenium Version", "3.4.0")
		    .addSystemInfo("Chrome Version", "61");
		return extent;
	}
}
