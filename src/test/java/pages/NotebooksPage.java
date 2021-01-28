package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NotebooksPage extends AbstractPage {

	public NotebooksPage(WebDriver driver) {
		super(driver);
	}

	public void addProductToComparisonList(int productQuantity) {
		sleep(1000);
		List<WebElement> buttons = driver.findElements(By.className(getProperty("07_compareButton.className")));
		for (int i = 0; i < productQuantity; i++) {
			buttons.get(i).click();
		}
	}
}