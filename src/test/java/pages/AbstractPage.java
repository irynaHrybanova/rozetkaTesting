package pages;

import config.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractPage {

	protected final WebDriver driver;
	protected PropertiesReader propertiesReader;

	protected AbstractPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		propertiesReader = new PropertiesReader();
	}

	protected WebDriverWait getWait(long sec) {
		return new WebDriverWait(driver, Duration.ofSeconds(sec));
	}

	protected WebElement waitWebElement(long seconds, By locator) {
		return getWait(seconds).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void goToHomePage() {
		driver.navigate().to(propertiesReader.getProperties("site.url"));
	}

	protected void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException ignored) {
		}
	}
}
