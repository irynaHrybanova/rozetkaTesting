package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RozetkaHomePage {
	private final WebDriver driver;
	@FindBy(linkText = "UA")
	private WebElement languageReferenceUA;
	@FindBy(linkText = "RU")
	private WebElement languageReferenceRU;

	public RozetkaHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void clickOnChangeLanguageUA() {
		languageReferenceUA.click();
	}

	public void clickOnChangeLanguageRU() {
		languageReferenceRU.click();
	}

	public boolean isUALanguage() {
		return driver.getTitle().contains("Інтернет-магазин");
	}

	public boolean isRULanguage() {
		return driver.getTitle().contains("Интернет-магазин");
	}
}