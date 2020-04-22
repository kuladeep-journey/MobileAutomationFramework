package journeyai.MobileTestAutomation.Framework;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import journeyai.MobileTestAutomation.K2QaApp.GlobalValues;

import org.openqa.selenium.remote.DesiredCapabilities;

public class CreateTestDriver {

	// add constructor to check platform to validate and call corresponding driver
	// creation
	// move the below code to conditional creation
	// Creating android driver here
	public static AppiumDriver<MobileElement> AndroidCapabilities() throws IOException {

		System.out.printf("creating driver for platform : ANDROID");

		File currentRoot = new File(System.getProperty("user.dir"));

		File androidAppLocation = new File(currentRoot, TestManager.globalParams.getProperty("appsUnderTest"));
		System.out.printf("\n\n.........APK file location : %s .......... \n\n", androidAppLocation.getAbsolutePath());
		File apkFile = new File(androidAppLocation, TestManager.globalParams.getProperty("androidapp"));
		System.out.printf("\n\n !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! APK Absolute path :   %s !!!!!!!!!!! \n \n",
				apkFile.getAbsolutePath());

		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability(MobileCapabilityType.DEVICE_NAME, TestManager.globalParams.get("androiddevicename"));
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, TestManager.globalParams.get("androidautomationname"));
		cap.setCapability(MobileCapabilityType.APP, apkFile.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, TestManager.globalParams.get("appiumcommandtimeout"));
		AndroidDriver<MobileElement> driver = new AndroidDriver<MobileElement>(
				new URL(TestManager.globalParams.getProperty("appiumserverurl")), cap);

		return driver;
	}

	public static IOSDriver<MobileElement> IOSCapabilities() throws IOException {

		
		System.out.printf("creating driver for platform : IOS");

		File currentRoot = new File(System.getProperty("user.dir"));

		File iosAppLocation = new File(currentRoot, TestManager.globalParams.getProperty("appsUnderTest"));
		System.out.printf("\n\n.........APK file location : %s .......... \n\n", iosAppLocation.getAbsolutePath());
		File iosAppFile = new File(iosAppLocation, TestManager.globalParams.getProperty("iosapp"));
		System.out.printf("\n\n !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! APK Absolute path :   %s !!!!!!!!!!! \n \n",
				iosAppFile.getAbsolutePath());

		DesiredCapabilities d = new DesiredCapabilities();
		d.setCapability(MobileCapabilityType.DEVICE_NAME, TestManager.globalParams.getProperty("iosdevicename"));
		d.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		d.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		d.setCapability(MobileCapabilityType.APP, "/Users/kuladeep/Downloads/Journey_Mobile_Automation/MobileTestAutomation/src/test/java/journeyai/MobileTestAutomation/K2QaApp/appsUnderTest/JourneyQA_app.zip");

		IOSDriver<MobileElement> driver = new IOSDriver <MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),d);

		
		System.out.println("------------------------------ Automation Name : " + driver.getCapabilities());
	
			return driver;

	}

}
