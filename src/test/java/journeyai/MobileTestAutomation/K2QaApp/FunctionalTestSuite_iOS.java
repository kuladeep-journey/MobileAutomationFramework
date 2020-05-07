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

	@Parameters({ "platform", "appFolderName", "phoneNumber", "apkIpaName" })
	public FunctionalTestSuite_iOS(@Optional("ios") String platform, String appFolder, String phoneNum, String apkIpa) {

		TestManager.setPlatform(platform);

		TestManager.setAppUnderTestFolderName(appFolder);

		TestManager.setapkIpaName(apkIpa);

		FrameworkUtility.initAppiumDriver(platform);

		TestManager.setAppGlobalVariable("phonenumber", phoneNum);

		TestManager.printglobalValues();

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

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("sdk_page_register_phone_btn_id"))
				.click();

		FrameworkUtility.AddDelay(500);

		MobileElement ele = FrameworkUtility.findElementWithValue("XCUIElementTypeTextField", "Phone Number");

		ele.click();

		FrameworkUtility.AddDelay(500);

		ele.sendKeys(TestManager.appProperties.getProperty("phonenumber").toString());

		FrameworkUtility.AddDelay(500);

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("register_phone_popup_register_btn_id"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = AppCommon.getToastText(toast);

		System.out.printf("\n Toast Text \n" + toastText);

		Assert.assertEquals(toastText.equalsIgnoreCase(TestManager.appProperties.getProperty("toast_text_success")),
				true);

		registered = true;

	}

	@Test
	public void verifyAddress() throws InterruptedException {

		System.out.println("In ADDRESS Verification............................. : START");

		boolean errorToast = false;

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_address_verification_btn_text"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");

			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {
			// wait for poup
			Assert.assertEquals(AppCommon.waitForPopUpwithID(
					TestManager.appProperties.getProperty("verification_push_notification_desc_Id"), 0), true);

			FrameworkUtility.AddDelay(1000);

			FrameworkUtility.findElementById(TestManager.appProperties.getProperty("push_notification_address_input"))
					.sendKeys(TestManager.appProperties.getProperty("push_notification_address_to_enter"));

			FrameworkUtility.AddDelay(1000);

			FrameworkUtility.findElementById(TestManager.appProperties.getProperty("push_notification_verify_btn_id"))
					.click();

			FrameworkUtility.AddDelay(1000);

			Assert.assertEquals(
					FrameworkUtility.verifyToastText(TestManager.appProperties.getProperty("toast_text_success"),
							GlobalValues.TOAST_MAX_WAIT_DURATION),
					true);

			FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);

			System.out.println("ADDRESS Verification: Push notification and verification is yet to be implemented");
		}

		System.out.println("Completed ADDRESS Verification............................. SUCCESS : END");
	}

	@Test
	public void verifySsn() throws InterruptedException {

		System.out.println("In SSN Verification............................. : START");

		boolean errorToast = false;

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_ssn_verification_btn_text"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println("Completed SSN Verification: AgentDesktop API returned Error, Status........ : FAILED");

			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);
		} else {

		}

		System.out.println("Completed SSN Verification............................. SUCCESS : END");
	}

	@Test
	public void verifyDob() throws InterruptedException {

		System.out.println("In DOB Verification............................. : START");

		boolean errorToast = false;

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_dob_verification_btn_text"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println("Completed DOB Verification: AgentDesktop API returned Error, Status........ : FAILED");

			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);
		} else {

		}
		System.out.println("Completed DOB Verification............................. SUCCESS : END");
	}

	@Test
	public void verifyFraudNotification() throws InterruptedException {
		System.out.println("In FRAUD Verification............................. : START");

		boolean errorToast = false;

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_fraud_verification_btn_text"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out
					.println("Completed FRAUD Verification: AgentDesktop API returned Error, Status........ : FAILED");

			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);
		} else {

		}

		System.out.println("Completed FRAUD Verification............................. SUCCESS : END");
	}

	@Test
	public void verifyOutBoundCall() throws InterruptedException {

		System.out.println("In OUTBOUND CALL Verification............................. : START");

		boolean errorToast = false;

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_outboundcall_verification_btn_text")).click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed OUTBOUND CALL Verification: AgentDesktop API returned Error, Status........ : FAILED");

			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);
		} else {

		}

		System.out.println("Completed OUTBOUND CALL Verification............................. SUCCESS : END");
	}

	@Test
	public void verifyzResetDesktopAgent() throws InterruptedException {

		System.out.println("Reset Desktop Agent and Register mobile again............................. : START");

		AppCommon.switchToSettingsView();

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("agent_page_reset_agent_btn_id"))
				.click();

		FrameworkUtility.AddDelay(500);

		System.out.println("Reset Desktop Agent and Register mobile again............................. : END");
	}
}
