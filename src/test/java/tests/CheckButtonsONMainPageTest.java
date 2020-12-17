package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RozetkaHomePage;

public class CheckButtonsONMainPageTest extends TestBase {

	private static ExtentReports report;
	private static ExtentTest test;

	@BeforeClass
	static void init() {
		report = new ExtentReports();
		ExtentKlovReporter klovReporter = new ExtentKlovReporter("Rozetka");
		klovReporter.initMongoDbConnection("localhost", 27017);
		report.attachReporter(klovReporter);
	}

	@Test
	void checkLanguageChange() {
		test = report.createTest("Check language change");
		RozetkaHomePage rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		rozetkaHomePage.clickOnChangeLanguageUA();
		test.pass("Clicked on UA to change language");
		Assert.assertTrue(rozetkaHomePage.isUALanguage());
		test.pass("Language was changed");
		rozetkaHomePage.clickOnChangeLanguageRU();
		test.pass("Clicked on RU to change language");
		Assert.assertTrue(rozetkaHomePage.isRULanguage());
		test.pass("Language was changed");
		test.log(Status.PASS, "Test is complete");
	}

	@Test(priority = 1)
	void checkContactsPhoneNumber() {
		test = report.createTest("Check contacts phone number");
		RozetkaHomePage rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		rozetkaHomePage.clickOnContactPhoneNumber();
		test.pass("Clicked on contact phone number");
		Assert.assertTrue(rozetkaHomePage.isOpenModalWindow());
		test.pass("Contact phones were shown");
		test.log(Status.PASS, "Test is complete");
	}

	@Test(priority = 2)
	void searchProduct() {
		test = report.createTest("Search product");
		RozetkaHomePage rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		rozetkaHomePage.searchProduct();
		test.pass("Goods were found");
		Assert.assertTrue(rozetkaHomePage.isProductName());
		test.pass("All goods correspond to search name");
		test.log(Status.PASS, "Test is complete");
	}

	@AfterClass
	static void endTestCase() {
		report.flush();
	}
}