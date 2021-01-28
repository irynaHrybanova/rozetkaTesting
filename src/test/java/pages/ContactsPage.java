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
		String contactsButtonLocator = getProperty("05_contacts");
		WebElement contactsButton = waitWebElement(20, By.linkText(contactsButtonLocator));
		contactsButton.click();
	}

	public void clickOnFirstAddress() {
		String addressLocator = getProperty("05_firstAddress.className");
		WebElement address = waitWebElement(20, By.className(addressLocator));
		address.click();
	}

	public List<WebElement> getAddressesWebElements() {
		waitWebElement(20, By.className(getProperty("05_firstAddress.className")));
		String addressesLocator = getProperty("05_address.className");
		return driver.findElements(By.className(addressesLocator));
	}

	public boolean isAddressRight(WebElement addressButton, ExtentTest test) {
		addressButton.click();
		String addressOnMapLocator = getProperty("05_addressOnMap.className");
		sleep(500);
		WebElement addressInModalWindow = waitWebElement(10, By.className(addressOnMapLocator));
		String text = addressButton.findElement(By.className(getProperty("05_firstAddress.className"))).getText();

		String modalAddress = addressInModalWindow.getText();
		test.log(Status.INFO, String.format("Comparing: %s and: %s", modalAddress, text));
		return modalAddress.contains(text);
	}
}
