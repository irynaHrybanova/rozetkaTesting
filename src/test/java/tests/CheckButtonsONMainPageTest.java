package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.RozetkaHomePage;

import java.util.List;

public class CheckButtonsONMainPageTest extends TestBase {

	private static ExtentReports report;
	private static ExtentTest test;
	private RozetkaHomePage rozetkaHomePage;
	private PropertiesReader propertiesReader;

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
		propertiesReader = new PropertiesReader();
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
			test.log(Status.PASS, "Language was changed. Test successfully completed");
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
			test.log(Status.PASS, "Contact phones were shown. Test successfully completed");
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
			test.log(Status.PASS, "All goods correspond to search name. Test successfully completed");
			rozetkaHomePage.goToHomePage();
		} else {
			test.log(Status.FAIL, "Fail");
		}
		rozetkaHomePage.goToHomePage();
	}

	@Test(priority = 2)
	void addProductToShoppingCart() {
		test = report.createTest("Add product to shopping cart");
		rozetkaHomePage.addProductToCart();
		test.pass("Product added to shopping cart");
		rozetkaHomePage.clickOnOrderProductButton();
		test.pass("Clicked on order button");
		if (rozetkaHomePage.isOrderPageOpen()) {
			test.log(Status.PASS, "Order page is open. Test successfully completed");
			rozetkaHomePage.goToHomePage();
		} else {
			test.log(Status.FAIL, "Fail");
		}
		rozetkaHomePage.goToHomePage();
	}

	@Test(priority = 2)
	void checkShopsAddress() {
		test = report.createTest("Check Shops address");
		test.log(Status.INFO, "Start test");
		rozetkaHomePage.clickOnFirstAddress();
		List<WebElement> modalAddressButtons = rozetkaHomePage.getAddressesWebElements();
		test.log(Status.INFO, "Get address buttons");
		for (WebElement button : modalAddressButtons) {
			String text = button.findElement(By.className(getProperty("addressName.className"))).getText();
			test.pass("Click on shop address " + text);
			if (rozetkaHomePage.isAddressRight(button, test)) {
				test.log(Status.PASS, "Address is the same " + text);
			} else {
				test.log(Status.FAIL, "Address NOT the same " + text);
			}
		}
	}

	@Test
	void selectProductCharacteristics() {
		test = report.createTest("Select product characteristics");
		test.log(Status.INFO, "Start test");
		rozetkaHomePage.getPageWithVacuumCleaners();
		test.log(Status.INFO, "Open page with vacuum cleaners");
		rozetkaHomePage.selectBrand();
		test.log(Status.INFO, "Sort vacuum cleaners by brand: " + propertiesReader.getProperties("brand.name"));
		if (rozetkaHomePage.isBrandCorrect()) {
			test.pass("Correct output value: brand.");
		} else {
			test.fail("Sort by brand incorrect");
		}
		rozetkaHomePage.selectInterval();
		test.log(Status.INFO, "Select Max price: " + propertiesReader.getProperties("priceLimit"));
		if (rozetkaHomePage.isPriceCorrect()) {
			test.log(Status.PASS, "Correct filtration by price. Test successfully completed");
		} else {
			test.fail("Incorrect filtration by price");
		}
	}

	@AfterClass
	static void endTestCase() {
		report.flush();
	}
}