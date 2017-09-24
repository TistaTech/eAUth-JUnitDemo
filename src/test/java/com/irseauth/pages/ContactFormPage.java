package com.irseauth.pages;

import static com.irseauth.utilities.ExcelUtils.getCellData;
import static com.irseauth.utilities.ExcelUtils.getUsedRowsCount;
import static com.irseauth.utilities.ExcelUtils.openExcelFile;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.irseauth.utilities.ConfigurationReader;
import com.irseauth.utilities.Driver;
import com.relevantcodes.extentreports.LogStatus;;

public class ContactFormPage extends BasePage {

	public ContactFormPage() {
		super();
		PageFactory.initElements(Driver.getInstance(), this);
	}

	@FindBy(id = "wpforms-22-field_0")
	public WebElement firstName;

	@FindBy(id = "wpforms-22-field_0-last")
	public WebElement lastName;

	@FindBy(id = "wpforms-22-field_1")
	public WebElement email;

	@FindBy(id = "wpforms-22-field_7")
	public WebElement phoneNumber;

	@FindBy(id = "wpforms-22-field_8_1")
	public WebElement checkbox1;

	@FindBy(id = "wpforms-22-field_8_2")
	public WebElement checkbox2;

	@FindBy(id = "wpforms-22-field_8_3")
	public WebElement checkbox3;

	@FindBy(id = "wpforms-22-field_9_1")
	public WebElement radioButton1;

	@FindBy(id = "wpforms-22-field_9_2")
	public WebElement radioButton2;

	@FindBy(id = "wpforms-22-field_9_3")
	public WebElement radioButton3;

	@FindBy(id = "wpforms-22-field_4")
	public WebElement dropdownMenu;

	@FindBy(id = "wpforms-22-field_2")
	public WebElement commentSection;

	@FindBy(id = "wpforms-submit-22")
	public WebElement submitButton;

	@FindBy(id = "wpforms-confirmation-22")
	public WebElement confirmation;

	int i;

	public void inputDataContactForm_PO()
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {

		waitForTheElementToBeDisplayed(firstName, 2);

		type(getCellData(i, 0), firstName);
		logData(LogStatus.INFO, "First Name Entered");
		type(getCellData(i, 1), lastName);
		logData(LogStatus.INFO, "Last Name Entered");
		type(getCellData(i, 2), email);
		logData(LogStatus.INFO, "Email Entered");
		type(getCellData(i, 3), phoneNumber);
		logData(LogStatus.INFO, "Phone Number Entered");
		click(checkbox2);
		logData(LogStatus.INFO, "Second checkbox selected");
		click(radioButton3);
		logData(LogStatus.INFO, "Third radio button selected");
		dropdownMenuSelect(dropdownMenu, getCellData(i, 4));
		logData(LogStatus.INFO, "Dropdown option selected");
		type(getCellData(i, 5), commentSection);
		logData(LogStatus.INFO, "Message entered");

		click(submitButton);
		logData(LogStatus.INFO, "Submit button clicked");

	}

	public void verifyConfirmationMessage_PO() {
		try {
			waitForTheElementToBeDisplayed(confirmation, 2);
			if (isDisplayed(confirmation))
				logData(LogStatus.PASS, "Test case PASSED - " + getCellData(i, 0) + " " + getCellData(i, 1));
		} catch (Exception e) {
			logData(LogStatus.FAIL,
					"Test case FAILED - " + getCellData(i, 0) + " " + getCellData(i, 1) + ": " + e.toString());
		} finally {
			if (!isDisplayed(confirmation)) {
				logData(LogStatus.FAIL, "Test case FAILED - " + getCellData(i, 0) + " " + getCellData(i, 1));
				finishLogging();
			}
		}
	}

	public void dataEntryContactForm_PO()
			throws EncryptedDocumentException, InvalidFormatException, InterruptedException, IOException {

		openExcelFile("./src/test/resources/test_data/Book1.xlsx", "Sheet1");

		for (i = 1; i < getUsedRowsCount(); i++) {
			try {
				navigateToPage("baseURL", "baseTitle");
				inputDataContactForm_PO();
				verifyConfirmationMessage_PO();
			} catch (Exception e) {
				logData(LogStatus.FAIL,
						"Test case FAILED - " + getCellData(i, 0) + " " + getCellData(i, 1) + ": " + e.toString());
				continue;
			} finally {
				// if
				// (!driver.getTitle().equals(ConfigurationReader.getProperty("baseTitle")))
				// {
				// test.log(LogStatus.FAIL, "Test case 'Contact Form' FAILED");
				// finishLogging();
				// }
			}
		}
	}

}
