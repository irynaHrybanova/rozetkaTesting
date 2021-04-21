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
		sleep(2000);
		List<WebElement> buttons = driver.findElements(By.className(getProperty("compareButton.className")));
		for (int i = 0; i < productQuantity; i++) {
			buttons.get(i).click();
		}
	}

	public int getExpectedQuantity() {
		return Integer.parseInt(getProperty("quantity"));
	}

	public boolean isQuantityCorrect() {
		WebElement actualQuantityCounter = waitWebElement(20, By.className(getProperty("actualQuantity.className")));
		int actualQuantity = Integer.parseInt(actualQuantityCounter.getText());
		int expectedQuantity = getExpectedQuantity();
		return actualQuantity == expectedQuantity;
	}
}