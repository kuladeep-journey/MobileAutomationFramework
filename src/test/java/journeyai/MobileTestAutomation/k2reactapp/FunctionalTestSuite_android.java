package journeyai.MobileTestAutomation.k2reactapp;

import org.testng.annotations.Test;
import org.testng.annotations.Test;


import journeyai.MobileTestAutomation.Framework.TestManager;
import journeyai.MobileTestAutomation.k2reactapp.pages.LoginPage;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class FunctionalTestSuite_android {
	
	boolean registered = false;
	LoginPage lp = new LoginPage();
	@Parameters({ "platform", "appFolderName" , "phoneNumber" , "apkIpaName"})
	public FunctionalTestSuite_android(@Optional("android") String platform, String appFolder, String phoneNum, String apkIpa) {
		TestManager.platform = platform;
		TestManager.appFolderName = appFolder;
		TestManager.apkIpaName = apkIpa;
		
		FrameworkUtility.initAppiumDriver(platform);
		FrameworkUtility.AddDelay(2000);
	}

	@BeforeMethod
	public void addDelay() {
		FrameworkUtility.AddDelay(2000);
		
	}
	
	@Test
	public void login() {
		if (lp.isLoginPage()) {
			lp.LoginButton.click();
		}
		
	}

	@Test
	public void registerMobilenumber() throws InterruptedException {

	}

}