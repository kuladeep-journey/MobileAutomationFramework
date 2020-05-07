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
			System.out.println(e.toString());
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

	/*
	 * Method: waitForElementVisible() : This method will set the driver wait until
	 * the element is visible or timeout Input : id : element id for android,
	 * accessibility id for iOS xPath: If id is provided, this element is not
	 * considered. If id is null, then element will be searched based on xPath
	 * Return: This method will return Mobile element if the element with id/xPath
	 * is visible on the screen, else returns null. NOTE: If the id is mentioned,
	 * xPath is ignored. If user wants to use xPath then id should be null.
	 * 
	 */
	public static MobileElement waitForElementVisible(String id, String xPath, int maxWaitSeconds) {

		WebDriverWait wait = new WebDriverWait(TestManager.driver, maxWaitSeconds);

		MobileElement element = null;

		if (maxWaitSeconds == 0) {
			maxWaitSeconds = FrameworkUtility.getDefaultFWSleepTime();
		}

		if (id != null) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));

			element = TestManager.driver.findElement(By.id(id));
		} else if (xPath != null) {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));

			element = TestManager.driver.findElement(By.xpath(xPath));
		}

		return element;
	}

	/*
	 * Method: AddDelay() : This method is to add execution delay to the current
	 * execution thread Input : milliSec: duration of sleep time /delay in
	 * milliseconds Return: This method will return nothing. NOTE: If the delay in
	 * milliseconds is zero, then default framework sleep time is applied i.e
	 * usually 2 seconds
	 */
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

	/*
	 * Method: findButtonWithText() : This method is to identify button element with
	 * visible text matching btnText Input : btnText: Visible text on element of
	 * type button in iOS/Android Return: Element of type MobileElement. If there
	 * are multiple buttons with same text in same page, then first identified
	 * element will be returned
	 * 
	 */
	public static MobileElement findButtonWithText(String btnText) {
		if (TestManager.platform.equalsIgnoreCase("android")) {
			btnText = "//android.widget.Button[@text= \"" + btnText + "\"]";
		} else if (TestManager.platform.equalsIgnoreCase("ios")) {
			btnText = "//XCUIElementTypeButton[@name= \"" + btnText + "\"]";
		}
		return findElementByXpath(btnText);
	}

	/*
	 * Method: findElementByXpath() : This method is to identify element matching
	 * the xPath Input : xPath of the element Return: Element of type MobileElement
	 * 
	 */
	public static MobileElement findElementByXpath(String xPath) {
		return TestManager.driver.findElement(By.xpath(xPath));
	}

	/*
	 * Method: findElementById() : This method is to identify element matching the
	 * element id for Android and accessibility id for iOS Input : id : For Android
	 * id is element id. For iOS, id is accessibilityId. Return: Element of type
	 * MobileElement
	 * 
	 */
	public static MobileElement findElementById(String id) {
		System.out.println("In find element by id/accessibility ID ............" + id);
		return (MobileElement) TestManager.driver.findElement(By.id(id));
	}

	/*
	 * Method: findElementsWithClassNameAndText() : This method is to find all
	 * elements matching the class and visible text Input: elemText: Input parameter
	 * with visible element text elemClass: Class of element, based on the platform
	 * Return: List with all elements whose visible text is elemText and class
	 * matching to elemClass
	 */
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

	/*
	 * Method: findAllElementsWithText() : This method is to find all elements
	 * matching the visible text Input: elemText: Input parameter with visible
	 * element text caseSensitive: boolean Flag, if user wants or not, to ignore the
	 * case sensitivity Return: List with all elements whose visible text is
	 * elemText
	 */

	public static List<MobileElement> findAllElementsWithText(String elemText, boolean caseSensitive) {
		List<MobileElement> elemList = TestManager.driver
				.findElements(By.xpath("//*[contains(@text, " + "\'" + elemText + "\'"));
		for (int i = 0; i < elemList.size(); i++) {
			if (caseSensitive) {
				if (!elemText.equals(elemList.get(i).getText())) {
					elemList.remove(i);
				}
			} /*
				 * else { if (!elemText.equalsIgnoreCase(elemList.get(i).getText())) {
				 * elemList.remove(i); } }
				 */
		}
		return elemList;
	}

	/*
	 * findElementWithValue() : This method is to find element provided below
	 * parameters Input: elementType: Type of element as per the platform strings
	 * value: Visible element text/value
	 * 
	 * Based on the platform value is treated as visible text for Android/iOS
	 * Return: MobileElement matching the elementType and value
	 */
	public static MobileElement findElementWithValue(String elementType, String value) {
		String path = null;

		if (TestManager.platform.equalsIgnoreCase("ios")) {
			path = "//" + elementType + "[@value=\"" + value + "\"]";

		} else if (TestManager.platform.equalsIgnoreCase("android")) {
			path = "//" + elementType + "[@text=\"" + value + "\"]";
		}

		return TestManager.driver.findElement(By.xpath(path));
	}

}
