package journeyai.MobileTestAutomation.Framework;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

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

		System.out.printf(" \n Expected Toast : %s \n", expectedText);

		System.out.printf("\n Actual Toast : %s \n", txt);

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
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			} catch (Exception e) {
				System.out.println("Unable to find the element with id : " + e.toString());
				return null;
			}
			element = TestManager.driver.findElement(By.id(id));

		} else if (xPath != null) {

			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
			} catch (Exception e) {
				System.out.println("Unable to find the element with id : " + e.toString());
				return null;
			}

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
			}
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

	/*
	 * This method will just fool the execution of testNG and makes the test fail
	 * immediately
	 * 
	 * @message: This is the console log message if user wants to print for
	 * debugging purpose
	 * 
	 */
	public static void failTest(String message) {
		Assert.assertTrue(false, message);
	}

	public static void pressDeviceBackButton() {
		TestManager.driver.navigate().back();
	}

	public static void pageUp(MobileElement ele) {
		int margin = 100;
		int xPos = ele.getRect().x + ele.getRect().width / 2;
		int yFrom = (ele.getRect().y + ele.getRect().width) / 2 + margin;
		int yTo = ele.getRect().y + ele.getRect().height - margin;
		System.out.println("xpos : " + xPos + "startY : " + yFrom + "EndY : " + yTo);
		TouchAction actions = new TouchAction(TestManager.driver);
		actions.press(new PointOption().point(xPos, yFrom));
		actions.waitAction(new WaitOptions());
		actions.moveTo(new PointOption().point(xPos, yTo));
		actions.release();
		actions.perform();

	}

	public static void pageDown(MobileElement ele) {
		int margin = 100;

		int xPos = ele.getRect().x + ele.getRect().width / 2;
		int yFrom = ele.getRect().y + ele.getRect().height - margin;
		int yTo = (ele.getRect().y + ele.getRect().width) / 2 + margin;
		System.out.println("xpos : " + xPos + "startY : " + yFrom + "EndY : " + yTo);
		TouchAction actions = new TouchAction(TestManager.driver);
		actions.press(new PointOption().point(xPos, yFrom));
		actions.waitAction(new WaitOptions());
		actions.moveTo(new PointOption().point(xPos, yTo));
		actions.release();
		actions.perform();
	}
	
	private static MobileElement findFromVisibleListElements(MobileElement listToCheck, String childClassname,
			String elemText) {
		String xpathToFind = "//" + childClassname + "[@text= \"" + elemText + "\"]";
		try {
			MobileElement elemToFind = listToCheck.findElement(By.xpath(xpathToFind));
			
			if (elemToFind != null && elemToFind.getText().equalsIgnoreCase(elemText)) {
				System.out.println("Found in current page, will just return.... !!!!");
				return elemToFind;
			} else {
				System.out.println("Not found in current page, will scroll and find !!!!");
			}
			
		} catch (Exception e ) {
			System.out.println("Element is not visible in current list");
			return null;
		}
		
		
		return null;
	}

	public static MobileElement searchListScrollingToTop(MobileElement listElement, String childClassname, String elemText) {

		List<MobileElement> allVisibleElements = listElement.findElements(By.className(childClassname));
		String prevStart = allVisibleElements.get(0).getText();
		String currentStart = null;
		boolean reachedTop = false;
		boolean foundElement = false;
		MobileElement foundElem = null;
		System.out.println("Previous start : " + prevStart + "\n Current Start : " + currentStart);
//		check if the element is directly visible in current screen
		foundElem = findFromVisibleListElements(listElement, childClassname, elemText);
		if (foundElem != null) {
			System.out.println("!!!!!!!!!!!!!!!  element found is not null !!!!!!!!!!!!!!!!!!!!!");
			return foundElem;
		} else {
			System.out.println("!!!!!!!!!!!!!!!  element found is NULL !!!!!!!!!!!!!!!!!!!!!");
		}
//		 now perform scroll
		while (!reachedTop) {
			pageUp(listElement);
			
			foundElem = findFromVisibleListElements(listElement, childClassname, elemText);
			if (foundElem != null) {
				return foundElem;
			}
//			if element is not found in this page then continue page down
//			 This is to check whether we reached bottom or not
			allVisibleElements = listElement.findElements(By.className(childClassname));
			currentStart = allVisibleElements.get(0).getText();
			System.out.println("Previous start : " + prevStart + "\n Current Start : " + currentStart);
			if (currentStart.equals(prevStart)) {
				reachedTop = true;
			} else {
				prevStart = currentStart;
			}
		}
		return foundElem;
	}

	

	public static MobileElement searchListScrollingToBottom(MobileElement listElement, String childClassname, String elemText) {
		System.out.println("Scrolling to bottom now !!!!!!");
		List<MobileElement> allVisibleElements = listElement.findElements(By.className(childClassname));
		String prevBottom = allVisibleElements.get(allVisibleElements.size() - 1).getText();
		String currentBottom = null;
		boolean reachedBottom = false;
		boolean foundElement = false;
		MobileElement foundElem = null;
		System.out.println("Previous start : " + prevBottom + "\n Current Start : " + currentBottom);
//		check if the element is directly visible in current screen
		foundElem = findFromVisibleListElements(listElement, childClassname, elemText);
		if (foundElem != null) {
			return foundElem;
		}
//		 now perform scroll
		while (!reachedBottom) {
			pageDown(listElement);
			foundElem = findFromVisibleListElements(listElement, childClassname, elemText);
			if (foundElem != null) {
				return foundElem;
			}
//			if element is not found in this page then continue page down
//			 This is to check whether we reached bottom or not
			allVisibleElements = listElement.findElements(By.className(childClassname));
			currentBottom = allVisibleElements.get(allVisibleElements.size() - 1).getText();
			System.out.println("Previous start : " + prevBottom + "\n Current Start : " + currentBottom);
			if (currentBottom.equals(prevBottom)) {
				reachedBottom = true;
			} else {
				prevBottom = currentBottom;
			}
		}
		return foundElem;

	}

	public static void findElementinScrollableList() {

	}
}
