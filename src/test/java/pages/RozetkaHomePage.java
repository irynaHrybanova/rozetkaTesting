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
		WebElement languageReferenceRU = waitWebElement(15, By.linkText("RU"));
		languageReferenceRU.click();
	}

	public boolean isUALanguage() {
		WebElement uaLabel = waitWebElement(10, By.className(getProperty("label.className")));
		return uaLabel.getText().contains(getProperty("uaLanguage"));
	}

	public boolean isRULanguage() {
		WebElement ruLabel = waitWebElement(10, By.className(getProperty("label.className")));
		return ruLabel.getText().contains(getProperty("ruLanguage"));
	}

	public void searchProduct() {
		String searchLocator = getProperty("searchBar.name");
		WebElement searchBar = waitWebElement(20, By.name(searchLocator));
		searchBar.sendKeys(getProperty("searchProduct.name"));

		String searchButtonPath = getProperty("searchButton.className");
		WebElement searchButton = driver.findElement(By.className(searchButtonPath));
		searchButton.click();
	}

	public boolean isProductName() {
		List<WebElement> products = driver.findElements(By.className(getProperty("products.classNames")));
		sleep(1000);
		for (WebElement product : products) {
			if (!product.getText().toLowerCase().contains(getProperty("searchProduct.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public void addProductToCart() {
		driver.navigate().to(getProperty("productRefer"));
		String buyButtonLocator = getProperty("buyButton.className");
		WebElement buyButton = waitWebElement(20, By.className(buyButtonLocator));
		buyButton.click();
	}

	public void clickOnOrderProductButton() {
		String orderProductButton = getProperty("orderProductButton.className");
		WebElement orderButton = waitWebElement(20, By.className(orderProductButton));
		orderButton.click();
	}

	public boolean isOrderPageOpen() {
		String headerLocator = getProperty("orderHeading.className");
		WebElement header = waitWebElement(20, By.className(headerLocator));
		return header.getText().contains(getProperty("orderHeading.name"));
	}

	public void getPageWithProducts(String menuCategoryLocator, String productLocator) {
		Actions actions = new Actions(driver);
		actions.moveToElement(waitWebElement(50, By.linkText(menuCategoryLocator))).build().perform();
		waitWebElement(50, By.linkText(productLocator)).click();
	}

	public void selectBrand() {
		String brand = getProperty("brand.id");
		driver.findElement(By.id(brand)).findElement(By.xpath("./..")).click();
	}

	public void selectInterval() {
		String maxLimitLocator = getProperty("maxLimit");
		WebElement maxLimit = waitWebElement(10, By.xpath(maxLimitLocator));
		maxLimit.clear();
		maxLimit.sendKeys(getProperty("priceLimit"));
		maxLimit.submit();
	}

	public boolean isBrandCorrect() {
		sleep(1000);
		List<WebElement> vacuumCleaners = driver.findElements(By.className(getProperty("cleanerTitle.className")));
		for (WebElement element : vacuumCleaners) {
			if (!element.getText().toLowerCase().contains(getProperty("brand.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	public boolean isPriceCorrect() {
		sleep(1000);
		List<WebElement> vacuumCleanersPrices = driver.findElements(By.className(getProperty("cleanerPrice.className")));
		int priceLimit = Integer.parseInt(getProperty("priceLimit"));
		return vacuumCleanersPrices.stream()
				.map(element -> element.getText().replaceAll(" ", ""))
				.map(Integer::parseInt)
				.noneMatch(price -> price > priceLimit);
	}

	public void clickOnEditButton() {
		String button = getProperty("editButton.className");
		WebElement editButton = waitWebElement(10, By.className(button));
		editButton.click();
	}

	public void clickOnContextMenu() {
		String productMenu = getProperty("productMenu.className");
		WebElement productMenuToggle = waitWebElement(10, By.className(productMenu));
		productMenuToggle.click();
	}

	public void deleteProductFromCart() {
		String deleteProduct = getProperty("deleteButton.className");
		WebElement deleteProductButton = waitWebElement(10, By.className(deleteProduct));
		deleteProductButton.click();
	}
}