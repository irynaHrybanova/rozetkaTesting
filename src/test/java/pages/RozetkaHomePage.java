package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class RozetkaHomePage extends AbstractPage {

	public RozetkaHomePage(WebDriver driver) {
		super(driver);
	}

	public void clickOnChangeLanguageUA() {
		WebElement languageReferenceUA = waitWebElement(10, By.linkText("UA"));
		languageReferenceUA.click();
	}

	public void clickOnChangeLanguageRU() {
		WebElement languageReferenceRU = waitWebElement(12, By.linkText("RU"));
		languageReferenceRU.click();
	}

	public boolean isUALanguage() {
		return driver.getTitle().contains(getProperty("01_uaLanguage"));
	}

	public boolean isRULanguage() {
		return driver.getTitle().contains(getProperty("01_ruLanguage"));
	}

	public void clickOnContactPhoneNumber() {
		String phoneButton = getProperty("02_contactPhoneButton.className");
		WebElement contactPhoneButton = waitWebElement(10, By.className(phoneButton));
		contactPhoneButton.click();
	}

	public boolean isOpenModalWindow() {
		String modalWindow = getProperty("02_modalWindow.className");
		WebElement modalWindowTitle = waitWebElement(20, By.className(modalWindow));
		String title = modalWindowTitle.getText();
		return title.contains(getProperty("02_modalWindow.UATitle"));
	}

	public void searchProduct() {
		String searchLocator = getProperty("03_searchBar.name");
		WebElement searchBar = waitWebElement(20, By.name(searchLocator));
		searchBar.sendKeys(getProperty("03_searchProduct.name"));

		String searchButtonPath = getProperty("03_searchButton.className");
		WebElement searchButton = driver.findElement(By.className(searchButtonPath));
		searchButton.click();
	}

	public boolean isProductName() {
		List<WebElement> products = driver.findElements(By.className(getProperty("03_products.classNames")));
		sleep(1000);
		for (WebElement product : products) {
			if (!product.getText().toLowerCase().contains(getProperty("03_searchProduct.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public void addProductToCart() {
		driver.navigate().to(getProperty("04_productRefer"));
		String buyButtonLocator = getProperty("04_buyButton.className");
		WebElement buyButton = waitWebElement(20, By.className(buyButtonLocator));
		buyButton.click();
	}

	public void clickOnOrderProductButton() {
		String orderProductButton = getProperty("04_orderProductButton.className");
		WebElement orderButton = waitWebElement(20, By.className(orderProductButton));
		orderButton.click();
	}

	public boolean isOrderPageOpen() {
		String headerLocator = getProperty("04_orderHeading.className");
		WebElement header = waitWebElement(20, By.className(headerLocator));
		return header.getText().contains(getProperty("04_orderHeading.name"));
	}

	public void getPageWithProducts(String menuCategoryLocator, String productLocator) {
		Actions actions = new Actions(driver);
		actions.moveToElement(waitWebElement(20, By.linkText(menuCategoryLocator))).build().perform();
		waitWebElement(40, By.linkText(productLocator)).click();
	}

	public void selectBrand() {
		String brand = getProperty("06_brand.id");
		driver.findElement(By.id(brand)).findElement(By.xpath("./..")).click();
	}

	public void selectInterval() {
		String maxLimitLocator = getProperty("06_maxLimit");
		WebElement maxLimit = waitWebElement(10, By.xpath(maxLimitLocator));
		maxLimit.clear();
		maxLimit.sendKeys(getProperty("06_priceLimit"));
		maxLimit.submit();
	}

	public boolean isBrandCorrect() {
		sleep(1000);
		List<WebElement> vacuumCleaners = driver.findElements(By.className(getProperty("06_cleanerTitle.className")));
		for (WebElement element : vacuumCleaners) {
			if (!element.getText().toLowerCase().contains(getProperty("06_brand.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public boolean isPriceCorrect() {
		sleep(1000);
		List<WebElement> vacuumCleanersPrices = driver.findElements(By.className(getProperty("06_cleanerPrice.className")));
		int priceLimit = Integer.parseInt(getProperty("06_priceLimit"));
		return vacuumCleanersPrices.stream()
				.map(element -> element.getText().replaceAll(" ", ""))
				.map(Integer::parseInt)
				.noneMatch(price -> price > priceLimit);
	}
}