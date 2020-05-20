package journeyai.MobileTestAutomation.k2reactapp.pages.HomePage;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

public class MenuPage {

	public MenuPage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Menu\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement pageTitle;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Manage Accounts\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement manageAccounts;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Notifications\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement notifications;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Login Preferences\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement loginPreferences;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Test Info\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement testInfo;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Feedback\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement feedback;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Log Out\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement logOut;
	
	
	
	@AndroidFindBy(className = "android.widget.ScrollView")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement scrollMenu;
	
	
	public boolean isMenuPage(){
		if (pageTitle.isDisplayed()) {
			return true;
		}
		return false;
	}
	
	public void scrollMenuDown(){
		FrameworkUtility.pageDown(scrollMenu);
	}
	
	public void scrollMenuUp(){
		FrameworkUtility.pageUp(scrollMenu);
	}
	
	public MobileElement getManageAccountsOption() {
		return manageAccounts;
	}
	
	public MobileElement getNotificationsOption() {
		return manageAccounts;
	}
	public MobileElement getLoginPreferencesOption() {
		return loginPreferences;
	}
	public MobileElement getTestInfoOption() {
		return manageAccounts;
	}
	public MobileElement getFeedbackOption() {
		scrollMenuDown();
		return manageAccounts;
	}
	public MobileElement getLogOutOption() {
//		scrollMenuDown();
		FrameworkUtility.pageDown(scrollMenu);
		return logOut;
	}

}
