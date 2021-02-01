package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NotebooksPage extends AbstractPage {

	public NotebooksPage(WebDriver driver) {
		super(driver);
	}

	public void addProductsToComparisonList(int productQuantity) {
		sleep(1000);
		List<WebElement> buttons = driver.findElements(By.className(getProperty("07_compareButton.className")));
		for (int i = 0; i < productQuantity; i++) {
			buttons.get(i).click();
		}
	}

	public void clickOnComparisonButton() {
		WebElement comparisonButton = waitWebElement(5, By.className(getProperty("07_comparisonButton.className")));
		comparisonButton.click();
	}

	public boolean isModalWindowOpen() {
		WebElement comparisonModalLinkLocator = waitWebElement(5, By.className(getProperty("07_comparisonModalLink.className")));
		return comparisonModalLinkLocator.getText().contains(getProperty("07_menuProduct.linkText"));
	}

	public void showProductComparisons() {
		waitWebElement(5, By.className(getProperty("07_comparisonModalLink.className"))).click();
	}

	public boolean isComparisonPageOpen() {
		WebElement headerLocator = waitWebElement(5, By.className(getProperty("07_comparisonHeader.className")));
		return headerLocator.getText().contains(getProperty("07_compare"));
	}
}