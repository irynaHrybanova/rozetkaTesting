package pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RozetkaHomePage {
	private final WebDriver driver;
	private PropertiesReader propertiesReader;

	public RozetkaHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		propertiesReader = new PropertiesReader();
	}

	public void clickOnChangeLanguageUA() {
		WebElement languageReferenceUA = waitWebElement(10, By.linkText("UA"));
		languageReferenceUA.click();
	}

	private WebElement waitWebElement(long seconds, By locator) {
		return getWait(seconds).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void clickOnChangeLanguageRU() {
		WebElement languageReferenceRU = waitWebElement(10, By.linkText("RU"));
		languageReferenceRU.click();
	}

	public boolean isUALanguage() {
		return driver.getTitle().contains(propertiesReader.getProperties("uaLanguage"));
	}

	public boolean isRULanguage() {
		return driver.getTitle().contains(propertiesReader.getProperties("ruLanguage"));
	}

	public void clickOnContactPhoneNumber() {
		String phoneButton = propertiesReader.getProperties("contactPhoneButton.xpath");
		WebElement contactPhoneButton = waitWebElement(10, By.xpath(phoneButton));
		contactPhoneButton.click();
	}

	public boolean isOpenModalWindow() {
		String modalWindow = propertiesReader.getProperties("modalWindow.title.xpath");
		WebElement modalWindowTitle = waitWebElement(12, By.xpath(modalWindow));
		String title = modalWindowTitle.getText();
		return title.contains(propertiesReader.getProperties("modalWindow.RUTitle"));
	}

	private WebDriverWait getWait(long sec) {
		return new WebDriverWait(driver, Duration.ofSeconds(sec));
	}

	public void searchProduct() {
		String searchLocator = propertiesReader.getProperties("searchBar.name");
		WebElement searchBar = waitWebElement(20, By.name(searchLocator));
		searchBar.sendKeys(propertiesReader.getProperties("searchProduct.name"));

		String searchButtonPath = propertiesReader.getProperties("searchButton.xpath");
		WebElement searchButton = driver.findElement(By.xpath(searchButtonPath));
		searchButton.click();
	}

	public boolean isProductName() {
		List<WebElement> products = driver.findElements(By.className(propertiesReader.getProperties("products.classNames")));
		sleep(1000);
		for (WebElement product : products) {
			if (!product.getText().toLowerCase().contains(propertiesReader.getProperties("searchBar.name").toLowerCase())) {
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
		WebElement orderButton = waitWebElement(15, By.className(orderProductButton));
		orderButton.click();
	}

	public boolean isOrderPageOpen() {
		String headerLocator = propertiesReader.getProperties("orderHeading.className");
		WebElement header = waitWebElement(10, By.className(headerLocator));
		return header.getText().contains(propertiesReader.getProperties("orderHeading.name"));
	}

	public List<WebElement> getAddressesWebElements() {
		waitWebElement(10, By.className(propertiesReader.getProperties("modalWindowsAddress.className")));
		String addressesLocator = propertiesReader.getProperties("address.className");
		return driver.findElements(By.className(addressesLocator));
	}

	public void clickOnFirstAddress() {
		String addressLocator = propertiesReader.getProperties("firstAddress.className");
		WebElement address = waitWebElement(20, By.className(addressLocator));
		address.click();
	}

	public boolean isAddressRight(WebElement addressButton, ExtentTest test) {
		addressButton.click();
		String addressOnMapLocator = propertiesReader.getProperties("addressOnMap.className");
		sleep(1000);
		WebElement addressInModalWindow = waitWebElement(10, By.className(addressOnMapLocator));
		String text = addressButton.findElement(By.className(propertiesReader.getProperties("addressName.className"))).getText();

		String modalAddress = addressInModalWindow.getText();
		test.log(Status.INFO, String.format("Comparing: %s and: %s", modalAddress, text));
		return modalAddress.contains(text);
	}

	public void goToHomePage() {
		driver.navigate().to(propertiesReader.getProperties("site.url"));
	}

	public void getPageWithVacuumCleaners() {
		String menuCategory = propertiesReader.getProperties("homeDevices.linkText");
		String vacuumCleaner = propertiesReader.getProperties("vacuumCleaner.linkText");
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.linkText(menuCategory))).build().perform();
		waitWebElement(15, By.linkText(vacuumCleaner)).click();
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
		sleep(2000);
	}

	public boolean isBrandCorrect() {
		List<WebElement> vacuumCleaners = driver.findElements(By.className(propertiesReader.getProperties("cleanerTitle.className")));
		sleep(6000);
		for (WebElement element : vacuumCleaners) {
			if (!element.getText().toLowerCase().contains(propertiesReader.getProperties("brand.name").toLowerCase())) {
				return false;
			}
		}
		return true;
	}

	private void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException ignored) {
		}
	}

	public boolean isPriceCorrect() {
		List<WebElement> vacuumCleanersPrices = driver.findElements(By.className(propertiesReader.getProperties("cleanerPrice.className")));
		sleep(2000);
		int priceLimit = Integer.parseInt(propertiesReader.getProperties("priceLimit"));
		return vacuumCleanersPrices.stream()
				.map(element -> element.getText().replaceAll(" ", ""))
				.map(Integer::parseInt)
				.noneMatch(price -> price > priceLimit);
	}
}