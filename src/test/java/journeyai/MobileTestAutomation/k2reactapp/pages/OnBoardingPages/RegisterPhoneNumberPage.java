package journeyai.MobileTestAutomation.k2reactapp.pages.OnBoardingPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;
import journeyai.MobileTestAutomation.Framework.TestManager;

public class RegisterPhoneNumberPage {

	public RegisterPhoneNumberPage(AppiumDriver driver) {
		// Initialize all PageFactory elements with appium driver as field decorator
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// All elements are defined here ......

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Phone number\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement pageTitle;
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@text=\"i.e. 3031234567\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement phoneNumberInput;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Phone number\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement selectCountryImageButton;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Send verification code\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement sendVerificationCodeButton;
	
	@AndroidFindBy(className = "android.widget.EditText")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement enterVerificationCode;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Confirm verification code\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement confirmVerificationCodeButton;
	
	
	@AndroidFindBy(className = "android.widget.ImageView")
	@iOSXCUITFindBy(xpath = "")
	public List<MobileElement> imageButtons;
	
	
	@AndroidFindBy(className = "android.widget.Spinner")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement showCountryList;
	
	
	@AndroidFindBy(id = "com.k2bank:id/select_dialog_listview")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement listOfCountries;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Confirm\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement confirmCountryButton;

//		All methods related to elements are defined here ......
	
	
	public MobileElement getBackButton() {
		return imageButtons.get(0);
	}
	
	public MobileElement getSelectCountryButton() {
		return imageButtons.get(1);
	}

	public boolean isphoneNumberRegisterPage() {

		if (pageTitle.isDisplayed()) {

			System.out.println("SUCCESS : Phone number Registration page is displayed now...!!!");
			return true;
		}

		System.out.println("ERROR : GetStarted page is not current Page, please check the flow...!!!");
		return false;
	}
	
	public void enterPhoneNumber(String phoneNumber){
		if (phoneNumberInput.isDisplayed()) {
//			phoneNumberInput.click();
//			phoneNumberInput.sendKeys(phoneNumber);
//			TestManager.driver.hideKeyboard();
			phoneNumberInput.setValue(phoneNumber);
		} else {
			System.out.println("ERROR : Unable to find phonenumber input box...!!!");
		}
	}
	
	public void selectCountry() {
		selectCountryImageButton.click();
	}

	public void clickSendVerification() {
		if (sendVerificationCodeButton.isDisplayed()) {
			System.out.println("SendVerification code button is shown ...!!!");
			if (sendVerificationCodeButton.isEnabled()){
				sendVerificationCodeButton.click();
				System.out.println("SUCCESS: SendVerification code button is enabled and Clicked now...!!!");
			} else {
				System.out.println("ERROR: SendVerification code button is still disabled...!!!");
			}
		} else {
			System.out.println("ERROR: Unable to find SendVerification code button ...!!!");
		}
	}
	
	public void enterVerificationCode(String code) {
		enterVerificationCode.sendKeys(code);
	}

	public void clickConfirmVerificationCode() {
//		confirmVerificationCodeButton.click();
		if (confirmVerificationCodeButton.isDisplayed()) {
			System.out.println("SendVerification code button is shown ...!!!");
			if (confirmVerificationCodeButton.isEnabled()){
				confirmVerificationCodeButton.click();
				System.out.println("SUCCESS: SendVerification code button is enabled and Clicked now...!!!");
			} else {
				System.out.println("ERROR: SendVerification code button is still disabled...!!!");
			}
		} else {
			System.out.println("ERROR: Unable to find SendVerification code button ...!!!");
		}
	}

	public void changeCountryTo(String countryName, String countryElemClass){
		getSelectCountryButton().click();
		FrameworkUtility.AddDelay(200);
		showCountryList.click();
//		MobileElement ele = TestManager.driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.k2bank:id/select_dialog_listview\")).getChildByText("
//				+ "new UiSelector().resourceId(\"android:id/text1\"), \"India (भारत)\")"));
//		ele.click();
		MobileElement country = FrameworkUtility.searchListScrollingToTop(listOfCountries, countryElemClass, countryName);
		if (country != null) {
			country.click();
		} else {
			FrameworkUtility.failTest("REquested country is not found in the list ...!!!");
		}

	}
}
