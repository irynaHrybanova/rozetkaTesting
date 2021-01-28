package tests;

import config.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {
	private PropertiesReader propertiesReader;
	private WebDriver driver;

	@BeforeSuite
	public void setUpWebDriver() {
		propertiesReader = new PropertiesReader("webdriver.properties");
		driver = initDriver();
	}

	public WebDriver getDriver() {
		driver.navigate().refresh();
		return driver;
	}

	private WebDriver initDriver() {
		String driverName = getProperty("driver.name");
		String driverPath = getProperty("driver.path");
		System.setProperty(driverName, driverPath);
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get(getProperty("site.url"));
		return driver;
	}

	protected String getProperty(String name) {
		return propertiesReader.getProperties(name);
	}

	//@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.close();
		}
	}
}