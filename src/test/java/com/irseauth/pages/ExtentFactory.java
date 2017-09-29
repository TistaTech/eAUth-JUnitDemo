package com.irseauth.pages;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
	public static ExtentReports getInstance() {
		ExtentReports extent = new ExtentReports("./target/ExtentReport.html", false);
		extent
			.addSystemInfo("Selenium Version", "2.5.0")
		    .addSystemInfo("Chrome Version", "61");
		return extent;
	}
}
