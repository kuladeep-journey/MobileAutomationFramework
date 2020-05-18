package journeyai.MobileTestAutomation.k2reactapp.pages.OnBoardingPages;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;
import journeyai.MobileTestAutomation.Framework.TestManager;

public class OnBoardingPage {

	public OnBoardingPage(AppiumDriver driver) {
		// Initialize all PageFactory elements with appium driver as field decorator
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

//All elements are defined here ......
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Sign Up\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement signUpBtn;


	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Get Started\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement getStartedBtn;

//	All methods related to elements are defined here ......

	public boolean isSignUpPage() {
		try {
			signUpBtn.isDisplayed();
		} catch (Exception e) {
			System.out.println("SignUp Page is not the current screen on the device...!!!");
			return false;
		}
		return true;
	}

	/*
	 * this method returns true if the signup button click navigates to get started page
	 * else returns false
	 */
	public void clickSignupButton() {

		// check if the signup button is shown on current screen or not
		if (isSignUpPage()) {
			// If button is shown, then click
			signUpBtn.click();

			FrameworkUtility.AddDelay(500);
			// Now verify that clicking on button worked successfully and get started page
			// is shown
		}else {
//			System.out.println("ERROR: Unable to find Signup Button in current page...!!!");
			FrameworkUtility.failTest("ERROR: Unable to find Signup Button in current page...!!!");
		}
	}
	
	
	
	public boolean isGetStartedPage() {

		if (getStartedBtn.isDisplayed()) {

			System.out.println("SUCCESS : GetStarted page is displayed now...!!!");
			return true;
		}

		System.out.println("ERROR : GetStarted page is not current Page, please check the flow...!!!");
		return false;
	}

	public void clickGetStartedButton() {

		if (isGetStartedPage()) {

			getStartedBtn.click();

			FrameworkUtility.AddDelay(500);
			System.out.println("getStartedButton is clicked...!!!");
		} else {
			
//			System.out.println("ERROR: Unable to find Signup Button in current page...!!!");
			FrameworkUtility.failTest("ERROR: Unable to find GetStarted Button in current page...!!!");
		}
	}

	public void startOnBoardingProcess() {
		clickSignupButton();
		clickGetStartedButton();
		
	}

}