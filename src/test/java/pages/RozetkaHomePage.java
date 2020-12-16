package pages;

import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
		WebElement languageReferenceUA = getWait(10L).until(ExpectedConditions.elementToBeClickable((By.linkText("UA"))));
		languageReferenceUA.click();
	}

	public void clickOnChangeLanguageRU() {
		WebElement languageReferenceRU = getWait(10L).until(ExpectedConditions.elementToBeClickable(By.linkText("RU")));
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
		WebElement contactPhoneButton = getWait(10L).until(ExpectedConditions.elementToBeClickable((By.xpath(phoneButton))));
		contactPhoneButton.click();
	}

	public boolean isOpenModalWindow() {
		String modalWindow = propertiesReader.getProperties("modalWindow.title.xpath");
		WebElement modalWindowTitle = getWait(12L).until(ExpectedConditions.elementToBeClickable((By.xpath(modalWindow))));
		String title = modalWindowTitle.getText();
		return title.contains(propertiesReader.getProperties("modalWindow.RUTitle"));
	}

	private WebDriverWait getWait(long sec) {
		return new WebDriverWait(driver, Duration.ofSeconds(sec));
	}

	public void searchProduct() {
		String searchLocator = propertiesReader.getProperties("searchBar.name");
		WebElement searchBar = getWait(50).until(ExpectedConditions.elementToBeClickable(By.name(searchLocator)));
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
}