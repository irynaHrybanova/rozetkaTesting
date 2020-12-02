package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RozetkaHomePage;

public class CheckLanguageChangeTest extends TestBase {

	@Test
	public void checkLanguageChange() {
		RozetkaHomePage rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		rozetkaHomePage.clickOnChangeLanguageUA();
		Assert.assertTrue(rozetkaHomePage.isUALanguage());

		rozetkaHomePage.clickOnChangeLanguageRU();
		Assert.assertTrue(rozetkaHomePage.isRULanguage());
	}
}