package pages;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ContactsPage extends AbstractPage {

	public ContactsPage(WebDriver driver) {
		super(driver);
	}

	public void clickOnContactsButton() {
		String contactsButtonLocator = getProperty("contacts");
		WebElement contactsButton = waitWebElement(20, By.linkText(contactsButtonLocator));
		contactsButton.click();
	}

	public void clickOnFirstAddress() {
		String addressLocator = getProperty("firstAddress.className");
		WebElement address = waitWebElement(30, By.className(addressLocator));
		address.click();
	}

	public List<WebElement> getAddressesWebElements() {
		waitWebElement(20, By.className(getProperty("firstAddress.className")));
		String addressesLocator = getProperty("address.className");
		return driver.findElements(By.className(addressesLocator));
	}

	public boolean isAddressRight(WebElement addressButton, ExtentTest test) {
		addressButton.click();
		String addressOnMapLocator = getProperty("addressOnMap.className");
		sleep(500);
		WebElement addressInModalWindow = waitWebElement(10, By.className(addressOnMapLocator));
		String text = addressButton.findElement(By.className(getProperty("firstAddress.className"))).getText();

		String modalAddress = addressInModalWindow.getText();
		test.log(Status.INFO, String.format("Comparing: %s AND: %s", modalAddress, text));
		return modalAddress.contains(text);
	}
}
