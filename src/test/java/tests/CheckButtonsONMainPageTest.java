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

	@Test(priority = 1)
	void checkLanguageChange() {
		test = report.createTest("Check language change");
		rozetkaHomePage.clickOnChangeLanguageRU();
		test.info("Clicked on RU link to change language");
		Assert.assertTrue(rozetkaHomePage.isRULanguage());
		test.info("Language was changed");
		rozetkaHomePage.clickOnChangeLanguageUA();
		test.info("Clicked on UA link to change language");
		if (rozetkaHomePage.isUALanguage()) {
			test.pass("Language was changed. Test successfully completed");
		} else {
			test.fail("Test failed", takeScreenshot());
		}
		rozetkaHomePage.goToHomePage();
		test.info("POSTCONDITION: go to home page");
	}

	@Test(priority = 2)
	void searchProduct() {
		test = report.createTest("Search product");
		rozetkaHomePage.searchProduct();
		test.info("Product with brand name: " + getProperty("searchProduct.name") + " were found");
		if (rozetkaHomePage.isProductName()) {
			test.pass("All goods correspond to search name: " + getProperty("searchProduct.name") + ". Test successfully completed");
		} else {
			test.fail("Test failed. NOT correspond to search name", takeScreenshot());
		}
		rozetkaHomePage.goToHomePage();
		test.info("POSTCONDITION: go to home page");
	}

	@Test(priority = 2)
	void addProductToShoppingCart() {
		test = report.createTest("Add product to shopping cart");
		rozetkaHomePage.addProductToCart();
		test.info("Product (" + getProperty("productRefer") + ") added to shopping cart");
		rozetkaHomePage.clickOnOrderProductButton();
		test.info("Clicked on order button");
		if (rozetkaHomePage.isOrderPageOpen()) {
			test.pass("Order page is open. Test successfully COMPLETED");
		} else {
			test.fail("Test failed. Order page is NOT open", takeScreenshot());
		}
		test.info("POSTCONDITION: delete product from shopping cart.");
		rozetkaHomePage.clickOnEditButton();
		test.info("Clicked on -> edit button");
		rozetkaHomePage.clickOnContextMenu();
		test.info("Click on -> context menu");
		rozetkaHomePage.deleteProductFromCart();
		test.info("Click on -> delete button");
		rozetkaHomePage.goToHomePage();
		test.info("go to home page");
	}

	@Test(priority = 2)
	void checkShopsAddress() {
		test = report.createTest("Check Shops address");
		test.info("Start test");
		contactsPage.clickOnContactsButton();
		test.info("Click on Contacts button");
		contactsPage.clickOnFirstAddress();
		List<WebElement> modalAddressButtons = contactsPage.getAddressesWebElements();
		test.info("Get address buttons");

		for (int i = 0; i < 3; i++) {
			WebElement button = modalAddressButtons.get(i);
			String text = button.findElement(By.className(getProperty("firstAddress.className"))).getText();
			test.info("Click on shop address " + text);
			if (contactsPage.isAddressRight(button, test)) {
				test.pass("Address is the same " + text);
			} else {
				test.fail("Address NOT the same " + text, takeScreenshot());
			}
		}
		test.info("Test successfully completed");
		rozetkaHomePage.goToHomePage();
		test.info("POSTCONDITION: go to home page");
	}

	@Test(priority = 2)
	void selectProductCharacteristics() {
		test = report.createTest("Select product characteristics");
		test.info("Start test");
		rozetkaHomePage.getPageWithProducts(getProperty("menuCategoryBt.linkText"), getProperty("menuProductVc.linkText"));
		test.info("Click -> " + getProperty("menuCategoryBt.linkText") + "->" + getProperty("menuProductVc.linkText"));
		rozetkaHomePage.selectBrand();
		test.info("Sort " + getProperty("menuProductVc.linkText") + " by brand: " + getProperty("brand.name"));
		if (rozetkaHomePage.isBrandCorrect()) {
			test.pass("Correct output value: brand.");
		} else {
			test.fail("Sort by brand incorrect", takeScreenshot());
		}
		rozetkaHomePage.selectInterval();
		test.info("Select Max price: " + getProperty("priceLimit"));
		if (rozetkaHomePage.isPriceCorrect()) {
			test.pass("Correct filtration by price.");
		} else {
			test.fail("Incorrect filtration by price", takeScreenshot());
		}
		test.info("Test successfully completed");
		rozetkaHomePage.goToHomePage();
		test.info("POSTCONDITION: go to home page");
	}

	@Test(priority = 3)
	void addProductToComparisonList() {
		test = report.createTest("Add product to comparison list");
		test.info("Start test");
		rozetkaHomePage.getPageWithProducts(getProperty("menuCategory.linkText"), getProperty("menuProduct.linkText"));
		test.info("Clicked -> " + getProperty("menuCategory.linkText") + "->" + getProperty("menuProduct.linkText"));
		notebooksPage.addProductsToComparisonList(Integer.parseInt(getProperty("quantity")));
		test.info("Added: " + getProperty("quantity") + " products for comparisons");

		if (notebooksPage.isQuantityCorrect()) {
			test.pass("Quantity of products is added correctly");
		} else {
			test.fail("Quantity of products is NOT correct");
		}
		test.info("Test successfully completed");
		rozetkaHomePage.goToHomePage();
		test.info("POSTCONDITION: go to home page");
	}

	public Media takeScreenshot() {
		return MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64)).build();
	}

	@AfterClass
	static void endTestCase() {
		report.flush();
	}
}