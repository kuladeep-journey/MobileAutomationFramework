package journeyai.MobileTestAutomation.k2reactapp;

import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import journeyai.MobileTestAutomation.Framework.TestManager;
import journeyai.MobileTestAutomation.k2reactapp.pages.HomePage.HomePage;
import journeyai.MobileTestAutomation.k2reactapp.pages.HomePage.MenuPage;
import journeyai.MobileTestAutomation.k2reactapp.pages.OnBoardingPages.IdentityRegistrationPage;
import journeyai.MobileTestAutomation.k2reactapp.pages.OnBoardingPages.OnBoardingPage;
import journeyai.MobileTestAutomation.k2reactapp.pages.OnBoardingPages.RegisterPhoneNumberPage;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class FunctionalTestSuite_android {
	
	boolean registered = false;
	AppiumDriver driver=null;
	
	
	@Parameters({ "platform", "appFolderName" , "phoneNumber" , "apkIpaName"})
	public FunctionalTestSuite_android(@Optional("android") String platform, String appFolder, String phoneNum, String apkIpa) {
		
		TestManager.setPlatform(platform);

		TestManager.setAppUnderTestFolderName(appFolder);

		TestManager.setapkIpaName(apkIpa);
		
		FrameworkUtility.initAppiumDriver(platform);
		
		TestManager.setAppGlobalVariable("phonenumber", phoneNum);
		
		TestManager.printglobalValues();
		
		driver = TestManager.driver;
		
		FrameworkUtility.AddDelay(2000);
	}

	@BeforeMethod
	public void addDelay() {
		
		FrameworkUtility.AddDelay(2000);	
	}
	
	@Test(priority = 0)
	public void onBoarding() {
		
		OnBoardingPage onBoard = new OnBoardingPage(driver);
		
		if (onBoard.isSignUpPage()) {
			
			onBoard.startOnBoardingProcess();
			RegisterPhoneNumberPage rp = new RegisterPhoneNumberPage(driver);
			if (rp.isphoneNumberRegisterPage()) {
				System.out.println("SUCCESS: Register phone number page is displayed");
				rp.changeCountryTo("United States", "android.widget.CheckedTextView");
				rp.confirmCountryButton.click();
				rp.enterPhoneNumber("9703724650");
//				rp.enterPhoneNumber("9538243666");
				FrameworkUtility.AddDelay(500);
				rp.clickSendVerification();

				FrameworkUtility.AddDelay(2000);
				rp.enterVerificationCode("B72104");
//				rp.enterVerificationCode("6CD7BB");
//				rp.enterVerificationCode("55b1e1");
				rp.clickConfirmVerificationCode();
				
				IdentityRegistrationPage irp = new IdentityRegistrationPage(driver);
				if (irp.isIdentityRegistrationPage()) {
					irp.driverLicenseBtn.click();
//					FrameworkUtility.AddDelay(500);
					if (irp.permissionDialog.isDisplayed()) {
						irp.permissionAllowBtn.click();
					}
					FrameworkUtility.AddDelay(2000);
					
					irp.getCemraButton().click();
					FrameworkUtility.AddDelay(2000);
					irp.confirmDocumentCaptured.click();
					FrameworkUtility.AddDelay(2000);
					irp.getCemraButton().click();
					FrameworkUtility.AddDelay(2000);
					irp.confirmDocumentCaptured.click();
					FrameworkUtility.AddDelay(5000);
					irp.getselfieCemraButton().click();
					FrameworkUtility.AddDelay(2000);
					irp.confirmSelfieCaptured.click();
					FrameworkUtility.AddDelay(4000);
					irp.continueToAccount.click();
					FrameworkUtility.AddDelay(2000);
					
//					logOut();
				} else {
					FrameworkUtility.failTest("ERROR : Failed in identity registration...!!!!");
				}
				
			} else {
				System.out.println("ERROR: Register phone number page is not displayed");
			}
			
		} else {
			
			FrameworkUtility.failTest("Current page on the screen is not Signup Page...!!!!");
		}
	}
	
	@Test(priority = 1)
	public void logOut() {
// This test case will work iff the user logged in and Menu button is visible
		
		HomePage appHome = new HomePage(driver);

		if (appHome.isHomePage()) {
			appHome.getMenuTabButton().click();
			MenuPage mp = new MenuPage(driver);

			if (mp.isMenuPage()) {
				System.out.println("Entered menu page ...!!!");
				mp.getLogOutOption().click();
				
			} else {
				System.out.println("Menu page not available ...!!!");
			}
			
			FrameworkUtility.AddDelay(5000);
			OnBoardingPage obp = new OnBoardingPage(driver);
			
			if (!obp.isSignUpPage()) {
				
				FrameworkUtility.failTest("SignUp page is not shown to user !!!!");
			}
		} else {
			FrameworkUtility.failTest("Home page is not shown yet ...!!!!");
		}
	}

	@AfterSuite
	public void closeApp() {
		driver.closeApp();
	}
}