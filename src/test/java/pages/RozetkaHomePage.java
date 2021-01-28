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
		return driver.getTitle().contains(propertiesReader.getProperties("uaLanguage"));
	}

	public boolean isRULanguage() {
		return driver.getTitle().contains(propertiesReader.getProperties("ruLanguage"));
	}

	public void clickOnContactPhoneNumber() {
		String phoneButton = propertiesReader.getProperties("contactPhoneButton.className");
		WebElement contactPhoneButton = waitWebElement(10, By.className(phoneButton));
		contactPhoneButton.click();
	}

	public boolean isOpenModalWindow() {
		String modalWindow = propertiesReader.getProperties("modalWindow.className");
		WebElement modalWindowTitle = waitWebElement(20, By.className(modalWindow));
		String title = modalWindowTitle.getText();
		return title.contains(propertiesReader.getProperties("modalWindow.UATitle"));
	}

	public void searchProduct() {
		String searchLocator = propertiesReader.getProperties("searchBar.name");
		WebElement searchBar = waitWebElement(20, By.name(searchLocator));
		searchBar.sendKeys(propertiesReader.getProperties("searchProduct.name"));

		String searchButtonPath = propertiesReader.getProperties("searchButton.className");
		WebElement searchButton = driver.findElement(By.className(searchButtonPath));
		searchButton.click();
	}

	public boolean isProductName() {
		List<WebElement> products = driver.findElements(By.className(propertiesReader.getProperties("products.classNames")));
		sleep(1000);
		for (WebElement product : products) {
			if (!product.getText().toLowerCase().contains(propertiesReader.getProperties("searchProduct.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public void addProductToCart() {
		driver.navigate().to(propertiesReader.getProperties("productRefer"));
		String buyButtonLocator = propertiesReader.getProperties("buyButton.className");
		WebElement buyButton = waitWebElement(20, By.className(buyButtonLocator));
		buyButton.click();
	}

	public void clickOnOrderProductButton() {
		String orderProductButton = propertiesReader.getProperties("orderProductButton.className");
		WebElement orderButton = waitWebElement(20, By.className(orderProductButton));
		orderButton.click();
	}

	public boolean isOrderPageOpen() {
		String headerLocator = propertiesReader.getProperties("orderHeading.className");
		WebElement header = waitWebElement(20, By.className(headerLocator));
		return header.getText().contains(propertiesReader.getProperties("orderHeading.name"));
	}

	public void getPageWithProducts(String menuCategoryLocator, String productLocator) {
		Actions actions = new Actions(driver);
		actions.moveToElement(waitWebElement(10, By.linkText(menuCategoryLocator))).build().perform();
		waitWebElement(35, By.linkText(productLocator)).click();
	}

	public void selectBrand() {
		String brand = propertiesReader.getProperties("brand.id");
		driver.findElement(By.id(brand)).findElement(By.xpath("./..")).click();
	}

	public void selectInterval() {
		String maxLimitLocator = propertiesReader.getProperties("maxLimit");
		WebElement maxLimit = waitWebElement(10, By.xpath(maxLimitLocator));
		maxLimit.clear();
		maxLimit.sendKeys(propertiesReader.getProperties("priceLimit"));
		maxLimit.submit();
	}

	public boolean isBrandCorrect() {
		sleep(1000);
		List<WebElement> vacuumCleaners = driver.findElements(By.className(propertiesReader.getProperties("cleanerTitle.className")));
		for (WebElement element : vacuumCleaners) {
			if (!element.getText().toLowerCase().contains(propertiesReader.getProperties("brand.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public boolean isPriceCorrect() {
		sleep(1000);
		List<WebElement> vacuumCleanersPrices = driver.findElements(By.className(propertiesReader.getProperties("cleanerPrice.className")));
		int priceLimit = Integer.parseInt(propertiesReader.getProperties("priceLimit"));
		return vacuumCleanersPrices.stream()
				.map(element -> element.getText().replaceAll(" ", ""))
				.map(Integer::parseInt)
				.noneMatch(price -> price > priceLimit);
	}
}