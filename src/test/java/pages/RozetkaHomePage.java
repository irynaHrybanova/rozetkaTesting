package pages;

import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RozetkaHomePage {
	private final WebDriver driver;
	PropertiesReader propertiesReader;

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
		List<WebElement> products = driver.findElements(By.className("products.classNames"));

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
		String addressesLocator = propertiesReader.getProperties("address.className");
		return driver.findElements(By.className(addressesLocator));
	}

	public boolean isAddressRight(WebElement electedAddress) {
		electedAddress.click();
		String addressInModalWindowLocator = propertiesReader.getProperties("addressInModalWindow");
		WebElement addressInModalWindow = waitWebElement(30, By.className(addressInModalWindowLocator));
		boolean result = addressInModalWindow.getText().contains(electedAddress.getText());
		String closeModalWindowLocator = propertiesReader.getProperties("closeModalWindow");
		WebElement closeModalWindow = waitWebElement(30, By.className(closeModalWindowLocator));
		closeModalWindow.click();
		return result;
	}

	public void goToHomePage() {
		driver.navigate().to(propertiesReader.getProperties("site.url"));
	}
}