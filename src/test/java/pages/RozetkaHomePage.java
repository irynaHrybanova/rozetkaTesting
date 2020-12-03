package pages;

import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RozetkaHomePage {
	private final WebDriver driver;
	PropertiesReader propertiesReader;
	@FindBy(linkText = "UA")//todo
	private WebElement languageReferenceUA;
	@FindBy(linkText = "RU")
	private WebElement languageReferenceRU;

	public RozetkaHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		propertiesReader = new PropertiesReader();
	}

	public void clickOnChangeLanguageUA() {
		languageReferenceUA.click();
	}

	public void clickOnChangeLanguageRU() {
		languageReferenceRU.click();
	}

	public boolean isUALanguage() {
		return driver.getTitle().contains(propertiesReader.getProperties("uaLanguage"));
	}

	public boolean isRULanguage() {
		return driver.getTitle().contains(propertiesReader.getProperties("ruLanguage"));
	}

	public void clickOnContactPhoneNumber() {
		WebElement contactPhoneButton = driver.findElement(By.xpath(propertiesReader.getProperties("contactPhoneButton.xpath")));
		contactPhoneButton.click();

	}

	public boolean isOpenModalWindow() {
		WebElement modalWindowTitle = driver.findElement(By.xpath(propertiesReader.getProperties("modalWindow.title.xpath")));
		String title = modalWindowTitle.getText();
		return title.contains(propertiesReader.getProperties("modalWindow.RUTitle"));
	}
}