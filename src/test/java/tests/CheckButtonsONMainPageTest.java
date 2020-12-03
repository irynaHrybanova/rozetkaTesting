package tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RozetkaHomePage;

public class CheckButtonsONMainPageTest extends TestBase {

	@Test
	void checkLanguageChange() {
		RozetkaHomePage rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		rozetkaHomePage.clickOnChangeLanguageUA();
		Assert.assertTrue(rozetkaHomePage.isUALanguage());

		rozetkaHomePage.clickOnChangeLanguageRU();
		Assert.assertTrue(rozetkaHomePage.isRULanguage());
	}

	@Test(priority = 1)
	void checkContactsPhoneNumber() {
		RozetkaHomePage rozetkaHomePage = PageFactory.initElements(getDriver(), RozetkaHomePage.class);
		rozetkaHomePage.clickOnContactPhoneNumber();
		Assert.assertTrue(rozetkaHomePage.isOpenModalWindow());
	}
}