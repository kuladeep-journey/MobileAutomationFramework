package journeyai.MobileTestAutomation.K2QaApp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import journeyai.MobileTestAutomation.Framework.TestManager;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;
import journeyai.MobileTestAutomation.K2QaApp.GlobalValues;

public class AppCommon {

	
	

	// This method will switch to SDK view on mobile app
	public static void switchToSDKView() {

		if (TestManager.platform.equalsIgnoreCase("android")) {
			FrameworkUtility.findElementById("ai.journey.k2bank:id/navigation_sdkview").click();
		} else if (TestManager.platform.equalsIgnoreCase("ios")) {
			FrameworkUtility.findElementById("Bank").click();
		}
		FrameworkUtility.AddDelay(1000);

	}

	// This method will switch to Desktop Agent view on mobile app
	public static void switchToAgentView() {
//		TestManager.driver.findElement(By.id("ai.journey.k2bank:id/navigation_dashboard")).click();
		if (TestManager.platform.equalsIgnoreCase("android" )) {
			FrameworkUtility.findElementById("ai.journey.k2bank:id/navigation_dashboard").click();
		} else if (TestManager.platform.equalsIgnoreCase("ios" )) {
			FrameworkUtility.findElementById("Agent").click();
		}
		FrameworkUtility.AddDelay(1000);

	}

	// This method will switch to Settings view on mobile app
	public static void switchToSettingsView() {
//		TestManager.driver.findElement(By.id("ai.journey.k2bank:id/navigation_settings")).click();
		if (TestManager.platform.equalsIgnoreCase("android" )) {
			FrameworkUtility.findElementById("ai.journey.k2bank:id/navigation_settings").click();
		} else if (TestManager.platform.equalsIgnoreCase("ios" )) {
			FrameworkUtility.findElementById("Settings").click();
		}
		FrameworkUtility.AddDelay(1000);

	}

	public static String getToastText(MobileElement elem){
		return elem.getText();
	}

	
	public static MobileElement waitForToast(String toastXpath, int maxWaitSeconds) {
		
		if (maxWaitSeconds == 0) {
			maxWaitSeconds = FrameworkUtility.getDefaultFWSleepTime();
		}
		if (toastXpath == null ) {
			if( TestManager.platform.equalsIgnoreCase("android")) {
				toastXpath = "//android.widget.Toast";	
			} else if ( TestManager.platform.equalsIgnoreCase("ios")) {
				toastXpath = "//XCUIElementTypeStaticText[@name=\"Toast\"]"; // //XCUIElementTypeWindow[@name=\"Toast\"]";
			}
		}
		
		WebDriverWait wait = new WebDriverWait(TestManager.driver, maxWaitSeconds);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(toastXpath)));
		
		return FrameworkUtility.findelementByXpath(toastXpath);
	}


	public static boolean waitForPopUpwithID(String popupId, int maxWaitSeconds) {
		String expectedTxt = "Bank is requesting you verify";

		WebDriverWait wait = new WebDriverWait(TestManager.driver, maxWaitSeconds);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(popupId)));
		String txt = TestManager.driver.findElement(By.id("ai.journey.k2bank:id/dialogDesc")).getText().toLowerCase();
		System.out.printf(" \n Expected Text : %s \n", expectedTxt);
		System.out.printf("\n Actual Text : %s \n", txt);

		if (txt.contains(expectedTxt.toLowerCase())) {
			return true;
		}
		return false;
	}



}
