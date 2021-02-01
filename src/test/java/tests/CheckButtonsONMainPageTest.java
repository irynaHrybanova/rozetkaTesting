package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.ContactsPage;
import pages.NotebooksPage;
import pages.RozetkaHomePage;

import java.util.List;

public class CheckButtonsONMainPageTest extends TestBase {

	private static ExtentReports report;
	private static ExtentTest test;
	private RozetkaHomePage rozetkaHomePage;
	private ContactsPage contactsPage;
	private NotebooksPage notebooksPage;

	@BeforeClass
	static void initStatic() {
		new PropertiesReader("webdriver.properties");
		report = new ExtentReports();
		ExtentKlovReporter klovReporter = new ExtentKlovReporter("Rozetka");
		PropertiesReader systemPropertiesReader = new PropertiesReader("db.properties");
		klovReporter.initMongoDbConnection(systemPropertiesReader.getProperties("mongoDbHost"), Integer.parseInt(systemPropertiesReader.getProperties("mongoDbPort")));
		klovReporter.initKlovServerConnection(systemPropertiesReader.getProperties("klovServerUrl"));
		report.attachReporter(klovReporter);
	}

	@BeforeTest
	private void init() {
		rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		contactsPage = PageFactory.initElements(getDriver(), ContactsPage.class);
		notebooksPage = PageFactory.initElements(getDriver(), NotebooksPage.class);
	}

	@Test
	void checkLanguageChange() {
		test = report.createTest("Check language change");
		rozetkaHomePage.goToHomePage();
		rozetkaHomePage.clickOnChangeLanguageRU();
		test.info("Clicked on RU to change language");
		Assert.assertTrue(rozetkaHomePage.isRULanguage());
		test.info("Language was changed");
		rozetkaHomePage.clickOnChangeLanguageUA();
		test.info("Clicked on UA to change language");
		if (rozetkaHomePage.isUALanguage()) {
			test.pass("Language was changed. Test successfully completed");
		} else {
			test.fail("Test failed", takeScreenshot());
		}
	}

	@Test(priority = 1)
	void checkContactsPhoneNumber() {
		test = report.createTest("Check contacts phone number");
		rozetkaHomePage.goToHomePage();
		rozetkaHomePage.clickOnContactPhoneNumber();
		test.info("Clicked on contact phone number");
		if (rozetkaHomePage.isOpenModalWindow()) {
			test.pass("Contact phones were shown. Test successfully completed");
		} else {
			test.fail("Test failed", takeScreenshot());
		}
	}

	@Test(priority = 2)
	void searchProduct() {
		test = report.createTest("Search product");
		rozetkaHomePage.goToHomePage();
		rozetkaHomePage.searchProduct();
		test.info("Goods were found");
		if (rozetkaHomePage.isProductName()) {
			test.pass("All goods correspond to search name. Test successfully completed");
		} else {
			test.fail("Test failed", takeScreenshot());
		}
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
		} else {
			test.fail("Test failed", takeScreenshot());
		}
	}

	@Test(priority = 2)
	void checkShopsAddress() {
		test = report.createTest("Check Shops address");
		test.info("Start test");
		rozetkaHomePage.goToHomePage();
		contactsPage.clickOnContactsButton();
		test.info("Click on Contacts button");
		contactsPage.clickOnFirstAddress();
		List<WebElement> modalAddressButtons = contactsPage.getAddressesWebElements();
		test.info("Get address buttons");

		for (WebElement button : modalAddressButtons) {
			String text = button.findElement(By.className(getProperty("05_firstAddress.className"))).getText();
			test.info("Click on shop address " + text);
			if (contactsPage.isAddressRight(button, test)) {
				test.pass("Address is the same " + text);
				test.info("Test successfully completed");
			} else {
				test.fail("Address NOT the same " + text, takeScreenshot());
			}
		}
	}

	@Test
	void selectProductCharacteristics() {
		test = report.createTest("Select product characteristics");
		test.info("Start test");
		rozetkaHomePage.goToHomePage();
		rozetkaHomePage.getPageWithProducts(getProperty("06_menuCategory.linkText"), getProperty("06_menuProduct.linkText"));
		test.info("Click -> " + getProperty("06_menuCategory.linkText") + "->" + getProperty("06_menuProduct.linkText"));
		rozetkaHomePage.selectBrand();
		test.info("Sort " + getProperty("06_menuProduct.linkText") + " by brand: " + getProperty("06_brand.name"));
		if (rozetkaHomePage.isBrandCorrect()) {
			test.pass("Correct output value: brand.");
		} else {
			test.fail("Sort by brand incorrect", takeScreenshot());
		}
		rozetkaHomePage.selectInterval();
		test.info("Select Max price: " + getProperty("06_priceLimit"));
		if (rozetkaHomePage.isPriceCorrect()) {
			test.pass("Correct filtration by price. Test successfully completed");
		} else {
			test.fail("Incorrect filtration by price", takeScreenshot());
		}
	}

	@Test
	void addProductToComparisonList() {
		test = report.createTest("07:Add product to comparison list");
		test.info("Start test");
		rozetkaHomePage.goToHomePage();
		rozetkaHomePage.getPageWithProducts(getProperty("07_menuCategory.linkText"), getProperty("07_menuProduct.linkText"));
		test.info("Clicked -> " + getProperty("07_menuCategory.linkText") + "->" + getProperty("07_menuProduct.linkText"));
		notebooksPage.addProductsToComparisonList(Integer.parseInt(getProperty("07_quantity")));
		test.info("Added: " + getProperty("07_quantity") + " products for comparisons");
		notebooksPage.clickOnComparisonButton();
		test.info("Clicked on comparison button");
		if (notebooksPage.isModalWindowOpen()) {
			test.pass("Modal window was open");
		} else {
			test.fail("Modal window was NOT open" + takeScreenshot());
		}
		notebooksPage.showProductComparisons();
		test.info("Clicked on comparison list to open comparison page");
		if (!notebooksPage.isComparisonPageOpen()) {
			test.fail("Comparison page was Not open" + takeScreenshot());
		}
	}
		public Media takeScreenshot() {
			return MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64)).build();
		}

		@AfterClass
		static void endTestCase() {
			report.flush();
		}
	}