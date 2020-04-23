package journeyai.MobileTestAutomation.Framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;


public class FrameworkUtility {

	public static void initAppiumDriver(String pltform) {
		TestManager tm = new TestManager();
		try {
			tm.InitDriver(pltform);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getDefaultFWSleepTime() {
		return Integer.valueOf(TestManager.globalParams.getProperty("defaultsleeptime"));
	}

	public static boolean verifyToastText(String expectedText, int maxWaitSeconds) {

		WebDriverWait wait = new WebDriverWait(TestManager.driver, maxWaitSeconds);

		MobileElement element;

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Toast")));
		String txt = TestManager.driver.findElement(By.xpath("//android.widget.Toast")).getText().toLowerCase();
		System.out.printf(" \n Expected Text : %s \n", expectedText);
		System.out.printf("\n Actual Text : %s \n", txt);

		if (txt.contains(expectedText.toLowerCase())) {
			return true;
		}

		return false;
	}
	
		
	// if User wants default delay it is 2 seconds
	public static void AddDelay(int milliSec) {
		if (milliSec == 0) {
			milliSec = getDefaultFWSleepTime();
		}
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}


	public static MobileElement findButtonWithText(String txt) {
		if (TestManager.platform.equalsIgnoreCase("android")) {
			txt = "//android.widget.Button[@text= \"" + txt + "\"]";
		} else if (TestManager.platform.equalsIgnoreCase("ios")) {
			txt = "//XCUIElementTypeButton[@name= \"" + txt + "\"]";
		}
		return findelementByXpath(txt);
	}

	
	public static MobileElement findelementByXpath(String path) {
		return TestManager.driver.findElement(By.xpath(path));
	}


/*
 * findElementById: For Android id is element id
 *                  For iOS, id is accessibilityId
 *                  returns element of type MobileElement  
 * 
 */
	public static MobileElement findElementById(String id) {
			System.out.println("In find element by id/accessibility ID ............"+id);
			return (MobileElement) TestManager.driver.findElement(By.id(id));
	}



	public static List<MobileElement> findElementsWithClassNameAndText(String elemText, String elemClass,
			boolean caseSensitive) {
		List<MobileElement> elemList = TestManager.driver.findElements(By.className(elemClass));
		for (int i = 0; i < elemList.size(); i++) {
			if (caseSensitive) {
				if (!elemText.equals(elemList.get(i).getText())) {
					elemList.remove(i);
				}
			} else {
				if (!elemText.equalsIgnoreCase(elemList.get(i).getText())) {
					elemList.remove(i);
				}
			}
		}
		return elemList;
	}
	
	public static List<MobileElement> findAllElementsWithText(String elemText, boolean caseSensitive) {
		List<MobileElement> elemList = TestManager.driver.findElements(By.xpath("//*[contains(@text, " + "\'" + elemText + "\'"));
		for (int i = 0; i < elemList.size(); i++) {
			if (caseSensitive) {
				if (!elemText.equals(elemList.get(i).getText())) {
					elemList.remove(i);
				}
			} else {
				if (!elemText.equalsIgnoreCase(elemList.get(i).getText())) {
					elemList.remove(i);
				}
			}
		}
		return elemList;
	}


	public static MobileElement findElementWithValue(String elementType, String value) {
		String path = null;
		System.out.println("PUT ----------------------------------: "+TestManager.platform);
		System.out.println("PUT ----------------------------------: "+elementType);
		System.out.println("PUT ----------------------------------: "+value);
		if (TestManager.platform.equalsIgnoreCase("ios")) {
			 path = "//" + elementType + "[@value=\"" + value +"\"]";
			
		}else if (TestManager.platform.equalsIgnoreCase("android")) {
			path = "//" + elementType + "[@text=\"" + value +"\"]";
			System.out.println("PUT ----------------------------------: "+path);
		}
		
		System.out.println("xpath for entering phone number: "+path);
		return TestManager.driver.findElement(By.xpath(path));
	}
	
	

}
