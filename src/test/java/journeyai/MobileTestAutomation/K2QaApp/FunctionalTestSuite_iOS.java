package journeyai.MobileTestAutomation.K2QaApp;

import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;

import org.testng.annotations.Test;

import org.testng.annotations.Test;

import org.testng.Assert;

import journeyai.MobileTestAutomation.Framework.TestManager;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionalTestSuite_iOS {

	boolean registered = false;

	@Parameters({ "platform" })
	public FunctionalTestSuite_iOS(@Optional("ios") String platform) {

		TestManager.platform = platform;
		FrameworkUtility.initAppiumDriver(platform);
		FrameworkUtility.AddDelay(2000);

		if (TestManager.platform.equalsIgnoreCase("ios")) {
			TestManager.driver.findElement(By.xpath("//*[@name=\"Allow\"]")).click();
		}
	}

	@BeforeMethod
	public void addDelay() {
		FrameworkUtility.AddDelay(2000);

	}

	@Test
	public void registerMobilenumber() throws InterruptedException {

		if (registered) {
			System.out.println("Mobile number already registered, Thus not registering again, but skipping this step ");
			return;
		}

		AppCommon.switchToSDKView();
		FrameworkUtility.findElementById("registerPhone").click();
		FrameworkUtility.AddDelay(500);
		MobileElement ele = FrameworkUtility.findElementWithValue("XCUIElementTypeTextField", "Phone Number");
		ele.click();
		FrameworkUtility.AddDelay(500);
		ele.sendKeys(TestManager.globalParams.getProperty("phonenumber").toString());
		FrameworkUtility.AddDelay(500);
		FrameworkUtility.findElementById("Register").click();
		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = AppCommon.getToastText(toast);
		System.out.printf("\n Toast Text \n" + toastText);
		Assert.assertEquals(toastText.equalsIgnoreCase(GlobalValues.ToastSuccess), true);

		registered = true;
		// System.out.printf("\n Register mobile number Success ? %b \n", registered);
		// System.out.println("In Register Mobile Number ............................. :
		// END");
//			 add delay to let toast get disappeared.
		FrameworkUtility.AddDelay(2000);
	}

	@Test
	public void verifyAddress() throws InterruptedException {

		System.out.println("In ADDRESS Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText("addressVerification").click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {

		}
		System.out.println("Completed ADDRESS Verification............................. SUCCESS : END");

	}

	@Test
	public void verifySsn() throws InterruptedException {
		System.out.println("In SSN Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText("ssnVerification").click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed SSN Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {

		}
		System.out.println("Completed SSN Verification............................. SUCCESS : END");

	}

	@Test
	public void verifyDob() throws InterruptedException {
		System.out.println("In DOB Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText("dobVerification").click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed DOB Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {

		}
		System.out.println("Completed DOB Verification............................. SUCCESS : END");

	}

	@Test
	public void verifyFraudNotification() throws InterruptedException {
		System.out.println("In FRAUD Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText("fraudVerification").click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed FRAUD Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {

		}
		System.out.println("Completed FRAUD Verification............................. SUCCESS : END");

	}

	@Test
	public void verifyOutBoundCall() throws InterruptedException {
		System.out.println("In OUTBOUND CALL Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText("outboundCall").click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed OUTBOUND CALL Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {
			Assert.assertTrue(false, "Error: Intentional failing here..., just correct it:" + toastText);
		}
		System.out.println("Completed OUTBOUND CALL Verification............................. SUCCESS : END");

	}

	@Test
	public void verifyzResetDesktopAgent() throws InterruptedException {
		System.out.println("Reset Desktop Agent and Register mobile again............................. : START");
		AppCommon.switchToSettingsView();

		FrameworkUtility.findElementById("resetContactCenter").click();
		FrameworkUtility.AddDelay(500);
//			registerMobilenumber();
		System.out.println("Reset Desktop Agent and Register mobile again............................. : END");
	}

}
