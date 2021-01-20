package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import config.PropertiesReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
	/*private ContactsPage contactsPage;*/
	private PropertiesReader propertiesReader;

	@BeforeClass
	static void initStatic() {
		report = new ExtentReports();
		ExtentKlovReporter klovReporter = new ExtentKlovReporter("Rozetka");
		klovReporter.initMongoDbConnection("localhost", 27017);
		klovReporter.initKlovServerConnection("http://localhost");
		report.attachReporter(klovReporter);
	}

	@BeforeTest
	private void init() {
		rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		/*contactsPage = PageFactory.initElements(getDriver(), ContactsPage.class);*/
		propertiesReader = new PropertiesReader();
	}

	@Test
	void checkLanguageChange() {
		rozetkaHomePage.clickOnChangeLanguageUA();
		test.info("Clicked on UA to change language");
		Assert.assertTrue(rozetkaHomePage.isUALanguage());
		test.info("Language was changed");
		rozetkaHomePage.clickOnChangeLanguageRU();
		test.info("Clicked on RU to change language");
		if (rozetkaHomePage.isRULanguage()) {
			test.pass("Language was changed. Test successfully completed");
		} else {
			test.fail("Test failed");
		}
	}

	@Test(priority = 1)
	void checkContactsPhoneNumber() {
		test = report.createTest("Check contacts phone number");
		rozetkaHomePage.clickOnContactPhoneNumber();
		test.info("Clicked on contact phone number");
		if (rozetkaHomePage.isOpenModalWindow()) {
			test.pass("Contact phones were shown. Test successfully completed");
		} else {
			test.fail("Test failed");
		}
	}

	@Test(priority = 2)
	void searchProduct() {
		test = report.createTest("Search product");
		rozetkaHomePage.searchProduct();
		test.info("Goods were found");
		if (rozetkaHomePage.isProductName()) {
			test.pass("All goods correspond to search name. Test successfully completed");
			rozetkaHomePage.goToHomePage();
		} else {
			test.fail("Test failed");
		}
		rozetkaHomePage.goToHomePage();
	}

	@Test(priority = 2)
	void addProductToShoppingCart() {
		test = report.createTest("Add product to shopping cart");
		rozetkaHomePage.addProductToCart();
		test.info("Product added to shopping cart");
		rozetkaHomePage.clickOnOrderProductButton();
		test.info("Clicked on order button");
		if (rozetkaHomePage.isOrderPageOpen()) {
			test.pass("Order page is open. Test successfully completed");
			rozetkaHomePage.goToHomePage();
		} else {
			test.fail("Test failed");
		}
		rozetkaHomePage.goToHomePage();
	}

	@Test(priority = 2)
	void checkShopsAddress() {
		/*test = report.createTest("Check Shops address");
		test.info("Start test");
		contactsPage.clickOnContactsButton();
		test.info("Click on Contacts button");
		contactsPage.clickOnFirstAddress();
		List<WebElement> modalAddressButtons = contactsPage.getAddressesWebElements();
		test.info("Get address buttons");

		for (WebElement button : modalAddressButtons) {
			String text = button.findElement(By.className(getProperty("addressName.className"))).getText();
			test.info("Click on shop address " + text);
			if (contactsPage.isAddressRight(button, test)) {
				test.pass("Address is the same " + text);

			} else {
				test.fail("Address NOT the same " + text);
			}
		}*/
	}

	@Test
	void selectProductCharacteristics() {
		test = report.createTest("Select product characteristics");
		test.info("Start test");
		rozetkaHomePage.getPageWithVacuumCleaners();
		test.info("Open page with vacuum cleaners");
		rozetkaHomePage.selectBrand();
		test.info("Sort vacuum cleaners by brand: " + propertiesReader.getProperties("brand.name"));
		if (rozetkaHomePage.isBrandCorrect()) {
			test.pass("Correct output value: brand.");
		} else {
			test.fail("Sort by brand incorrect");

		}
		rozetkaHomePage.selectInterval();
		test.info("Select Max price: " + propertiesReader.getProperties("priceLimit"));
		if (rozetkaHomePage.isPriceCorrect()) {
			test.pass("Correct filtration by price. Test successfully completed");
		} else {
			test.fail("Incorrect filtration by price");
		}
	}



	@AfterClass
	static void endTestCase() {
		report.flush();
	}
}