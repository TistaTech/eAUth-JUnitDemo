package com.irseauth.pages;

import static com.irseauth.utilities.ExcelUtils.getCellData;
import static com.irseauth.utilities.ExcelUtils.getUsedRowsCount;
import static com.irseauth.utilities.ExcelUtils.openExcelFile;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.irseauth.utilities.Driver;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPage extends BasePage {
	
	public LoginPage() {
		super();
		PageFactory.initElements(Driver.getInstance(), this);
	}

	@FindBy(id = "user_login")
	public WebElement username;

	@FindBy(id = "user_pass")
	public WebElement password;

	@FindBy(id = "wp-submit")
	public WebElement loginButton;

	@FindBy(css = "div.wrap>h1")
	public WebElement dashboard;

	int i;

	private void inputDataLogin_PO() throws InterruptedException, IOException {
		waitForTheElementToBeDisplayed(username, 2);
		script_stand_by(2000);
		
		highlightAreaWithJavascript(username);
		type(getCellData(i, 0), username);
		logData(LogStatus.INFO, "Username Entered");
		
		highlightAreaWithJavascript(password);
		type(getCellData(i, 1), password);
		logData(LogStatus.INFO, "Password Entered");

		highlightAreaWithJavascript(loginButton);
		loginButton.click();
		logData(LogStatus.INFO, "Login button clicked");
	}

	private void verifyLoggedIn_PO() throws IOException {

		try {
			waitForTheElementToBeDisplayed(dashboard, 2);
			if (isDisplayed(dashboard)) {
				logData(LogStatus.PASS,
						"Test case PASSED - Username: " + getCellData(i, 0) + " Password: " + getCellData(i, 1));
			} else {
				logData(LogStatus.FAIL,
						"Test case FAILED - Username: " + getCellData(i, 0) + " Password: " + getCellData(i, 1));
				finishLogging();
			}
		} catch (Exception e) {
			logData(LogStatus.FAIL,
					"Test case FAILED - " + getCellData(i, 0) + " " + getCellData(i, 1) + ": " + e.toString());
		}
	}

	public void dataEntryLogin_PO()
			throws EncryptedDocumentException, InvalidFormatException, InterruptedException, IOException {

		openExcelFile("./src/test/resources/test_data/Book2.xlsx", "Sheet1");

		for (i = 1; i < getUsedRowsCount(); i++) {
			try {
				navigateToPage("loginURL", "loginTitle");
				inputDataLogin_PO();
				verifyLoggedIn_PO();
			} catch (Exception e) {
				logData(LogStatus.FAIL, "Test case FAILED - Username: " + getCellData(i, 0) + " Password: "
						+ getCellData(i, 1) + ": " + e.toString());
				continue;
			}
		}
	}

}
