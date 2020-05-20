package journeyai.MobileTestAutomation.k2reactapp.pages.HomePage;

import java.util.List;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class HomePage {

	public HomePage(AppiumDriver driver) {
		
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	
	@AndroidFindBy(className = "android.widget.Button")
	@iOSXCUITFindBy(xpath = "")
	public List<MobileElement> homePageTabButtons;
	
	
	

	public MobileElement getAccountsTabButton() {
		return homePageTabButtons.get(0);
	}

	public MobileElement getVerifyTabButton() {
		return homePageTabButtons.get(1);
	}

	public MobileElement getSupportTabButton() {
		return homePageTabButtons.get(2);
	}

	public MobileElement getMenuTabButton() {
		return homePageTabButtons.get(3);
	}
	
	public boolean isHomePage() {
		System.out.println("********************    Total buttons displayed on screen : "+homePageTabButtons.size());
		if (homePageTabButtons.get(0).isDisplayed()){
			return true;
		}
		
		return false;
	}
}
