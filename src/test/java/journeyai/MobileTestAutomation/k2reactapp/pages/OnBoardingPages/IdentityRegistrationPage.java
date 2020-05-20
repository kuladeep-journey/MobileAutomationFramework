package journeyai.MobileTestAutomation.k2reactapp.pages.OnBoardingPages;

import java.util.List;

import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class IdentityRegistrationPage {

	public IdentityRegistrationPage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

//	All elements are defined here ......

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Identity Verification\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement pageTitle;


	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Driver's License\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement driverLicenseBtn;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Passport\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement passportBtn;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"National Identity Card\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement nationalIdBtn;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Residence Permit Card\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement residencePermitCardBtn;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Need Help?\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement needHelpBtn;
	
	
//	
	@AndroidFindBy(id = "com.android.permissioncontroller:id/content_container")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement permissionDialog;
	
	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement permissionAllowBtn;
	
	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement permissionDenyBtn;
	
	
	@AndroidFindBy(className = "android.widget.ImageView")
	@iOSXCUITFindBy(xpath = "")
	public List<MobileElement> allDocCaptureImageButtons;
	
//	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"My Document is Readable\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement confirmDocumentCaptured;
	
	@AndroidFindBy(className = "android.widget.ImageView")
	@iOSXCUITFindBy(xpath = "")
	public List<MobileElement> allselfieImageButtons;
	
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"My Photo is in Focus\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement confirmSelfieCaptured;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Continue to Account\"]")
	@iOSXCUITFindBy(xpath = "")
	public MobileElement continueToAccount;
	
	
	
	
//	All methods related to elements are defined here ......
	public boolean isIdentityRegistrationPage() {
		
		if (pageTitle.isDisplayed()) {

			System.out.println("SUCCESS : Phone number Registration page is displayed now...!!!");
			return true;
		}

		System.out.println("ERROR : GetStarted page is not current Page, please check the flow...!!!");
		return false;
	}
	
	public void acceptPermissions(){
		if (permissionDialog.isDisplayed()) {
			permissionAllowBtn.click();
		}
	}
	
	public MobileElement getCemraButton() {
		return allDocCaptureImageButtons.get(3);
	}

	public MobileElement getselfieCemraButton() {
		return allselfieImageButtons.get(2);
	}
	
	
}
