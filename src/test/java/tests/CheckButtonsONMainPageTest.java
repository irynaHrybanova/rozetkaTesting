package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.RozetkaHomePage;

public class CheckButtonsONMainPageTest extends TestBase {

	private static ExtentReports report;
	private static ExtentTest test;
	private RozetkaHomePage rozetkaHomePage;

	@BeforeClass
	static void initStatic() {
		report = new ExtentReports();
		ExtentKlovReporter klovReporter = new ExtentKlovReporter("Rozetka");
		klovReporter.initMongoDbConnection("localhost", 27017);
		report.attachReporter(klovReporter);
	}

	@BeforeTest
	private void init() {
		rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
	}

	@Test
	void checkLanguageChange() {
		test = report.createTest("Check language change");
		rozetkaHomePage.clickOnChangeLanguageUA();
		test.pass("Clicked on UA to change language");
		Assert.assertTrue(rozetkaHomePage.isUALanguage());
		test.pass("Language was changed");
		rozetkaHomePage.clickOnChangeLanguageRU();
		test.pass("Clicked on RU to change language");
		if (rozetkaHomePage.isRULanguage()) {
			test.pass("Language was changed");
			test.log(Status.PASS, "Test is complete");
		} else {
			test.log(Status.FAIL, "Fail");
		}
	}

	@Test(priority = 1)
	void checkContactsPhoneNumber() {
		test = report.createTest("Check contacts phone number");
		rozetkaHomePage.clickOnContactPhoneNumber();
		test.pass("Clicked on contact phone number");
		if (rozetkaHomePage.isOpenModalWindow()) {
			test.pass("Contact phones were shown");
			test.log(Status.PASS, "Test is complete");
		} else {
			test.log(Status.FAIL, "Fail");
		}
	}

	@Test(priority = 2)
	void searchProduct() {
		test = report.createTest("Search product");
		rozetkaHomePage.searchProduct();
		test.pass("Goods were found");
		if (rozetkaHomePage.isProductName()) {
			test.pass("All goods correspond to search name");
			test.log(Status.PASS, "Test is complete");
			rozetkaHomePage.goToHomePage();
		} else {
			test.log(Status.FAIL, "Fail");
		}
		rozetkaHomePage.goToHomePage();
	}

	@Test(priority = 2)
	void addProductToShoppingCart() {
		test = report.createTest("add product to shopping cart");
		rozetkaHomePage.addProductToCart();
		test.pass("Product added to shopping cart");
		rozetkaHomePage.clickOnOrderProductButton();
		test.pass("Clicked on order button");
		if (rozetkaHomePage.isOrderPageOpen()) {
			test.pass("Order page is open");
			test.log(Status.PASS, "Test is complete");
			rozetkaHomePage.goToHomePage();
		} else {
			test.log(Status.FAIL, "Fail");
		}
		rozetkaHomePage.goToHomePage();
	}

	@AfterClass
	static void endTestCase() {
		report.flush();
	}
}