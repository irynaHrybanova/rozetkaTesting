package tests;

import config.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class TestBase {
	private WebDriver driver;

	@BeforeSuite
	public void setUpWebDriver() {
		String driverName = PropertiesReader.getProperties("driver.name");
		String driverPath = PropertiesReader.getProperties("driver.path");
		System.setProperty(driverName, driverPath);
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		new WebDriverWait(driver, Duration.ofSeconds(3));
		driver.get(PropertiesReader.getProperties("site.url"));
	}

	public WebDriver getDriver() {
		return driver;
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}