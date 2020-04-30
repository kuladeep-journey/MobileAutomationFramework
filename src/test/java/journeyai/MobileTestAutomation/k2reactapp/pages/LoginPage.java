package journeyai.MobileTestAutomation.k2reactapp.pages;


import io.appium.java_client.MobileElement;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

public class LoginPage {
	public MobileElement LoginButton;

	public LoginPage() {
		initElements();
	}

	public void initElements() {
		LoginButton = FrameworkUtility.findelementByXpath("//android.widget.TextView[@text=\"Login\"]");
	}
	
	public boolean isLoginPage() {
		return LoginButton.isDisplayed();
	}

}