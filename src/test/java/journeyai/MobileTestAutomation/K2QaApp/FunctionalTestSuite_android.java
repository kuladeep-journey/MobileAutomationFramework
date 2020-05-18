package journeyai.MobileTestAutomation.K2QaApp;

import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.Test;

import org.testng.Assert;

import journeyai.MobileTestAutomation.Framework.TestManager;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionalTestSuite_android {

	boolean registered = false;

	@Parameters({ "platform", "appFolderName", "phoneNumber", "apkIpaName" })
	public FunctionalTestSuite_android(@Optional("android") String platform, String appFolderName, String phoneNumber,
			String apkIpaName) {

		TestManager.setPlatform(platform);

		TestManager.setAppUnderTestFolderName(appFolderName);

		TestManager.setapkIpaName(apkIpaName);

		FrameworkUtility.initAppiumDriver(platform);

		TestManager.setAppGlobalVariable("phonenumber", phoneNumber);

		TestManager.printglobalValues();

		FrameworkUtility.AddDelay(2000);
	}

	public void failTest(String message) {
		Assert.assertTrue(false, message);
	}

	public void answerIncomingCall() {

//		com.android.dialer:id/action2
		((AndroidDriver) TestManager.driver).openNotifications();

		WebDriverWait wait = new WebDriverWait(TestManager.driver, 20);
		MobileElement element = null;
		try {
			element = (MobileElement) wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.dialer:id/action2")));
		} catch (Exception e) {
			System.out.println("No incoming call received within 20 seconds");
		}
		Assert.assertNotEquals(element, null, "Incoming call isnot received");

		element.click();

	}

	public void disconnectOngoingCall() {
		System.out.println("In disconnect ongoign call, check for completed message !!!");
		MobileElement element = null;

		WebDriverWait wait = new WebDriverWait(TestManager.driver, 20);

		try {

			element = (MobileElement) wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("com.android.dialer:id/op_call_card_container")));

		} catch (Exception e) {
			System.out.println("Error : Ongoing call screen is not in focus");
		}
		FrameworkUtility.findElementById("com.android.dialer:id/floating_end_call_action_button").click();

		System.out.println("In disconnect ongoign call : Completed disconnect call");

	}

	public void rejectIncomingCall() {

		((AndroidDriver) TestManager.driver).openNotifications();

		WebDriverWait wait = new WebDriverWait(TestManager.driver, 20);
		MobileElement element = null;
		try {
			element = (MobileElement) wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.dialer:id/action0")));
		} catch (Exception e) {
			System.out.println("No incoming call received within 20 seconds");
		}
		Assert.assertNotEquals(element, null, "Incoming call isnot received");

		element.click();
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

		MobileElement element = FrameworkUtility.findElementWithValue(
				TestManager.appProperties.getProperty("register_phone_popup_text_input_type"),
				TestManager.appProperties.getProperty("register_phone_popup_text_input_deault_text"));

		element.sendKeys(TestManager.appProperties.getProperty("phonenumber").toString());

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("register_phone_popup_register_btn_id"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = AppCommon.getToastText(toast);

		Assert.assertEquals(toastText.equalsIgnoreCase(GlobalValues.ToastSuccess), true);

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

		MobileElement temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);
		if (temp != null) {

			failTest("Error: In address Verification - DesktopAgent returned :" + temp.getText());
		} else {
			System.out.println(
					"There was no error from desktop agent ... !!! clicked Adddress verification waiting for popup");
		}

		temp = FrameworkUtility.waitForElementVisible(
				TestManager.appProperties.getProperty("push_notification_address_input_id"), null, 10);
		if (temp == null) {

			failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
		}

		temp.sendKeys(TestManager.appProperties.getProperty("push_notification_address_to_enter"));

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("push_notification_verify_btn_id"))
				.click();

		Assert.assertEquals(
				FrameworkUtility.verifyToastText(TestManager.appProperties.getProperty("toast_text_success"),
						GlobalValues.TOAST_MAX_WAIT_DURATION),
				true);

		FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);

		System.out.println("Completed ADDRESS Verification............................. SUCCESS : END");

	}

//	@Test
	public void verifySsn() throws InterruptedException {

		System.out.println("In SSN Verification..........................: START");

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_ssn_verification_btn_text"))
				.click();

		MobileElement temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);
		if (temp != null) {

			failTest("Error: In SSN Verification - DesktopAgent returned :" + temp.getText());
		} else {
			System.out.println(
					"There was no error from desktop agent ... !!! clicked SSN verification, waiting for popup");
		}

		temp = FrameworkUtility.waitForElementVisible(
				TestManager.appProperties.getProperty("push_notification_ssn_input_id"), null, 10);
		if (temp == null) {

			failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
		}

		temp.sendKeys(TestManager.appProperties.getProperty("push_notification_ssn_to_enter"));

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("push_notification_verify_btn_id"))
				.click();

		Assert.assertEquals(
				FrameworkUtility.verifyToastText(TestManager.appProperties.getProperty("toast_text_success"),
						GlobalValues.TOAST_MAX_WAIT_DURATION),
				true);

		FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);

		System.out.println("Completed SSN Verification............................. SUCCESS : END");
	}

//	@Test
	public void verifyDob() throws InterruptedException {
		System.out.println("In DOB Verification..........................: START");

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_dob_verification_btn_text"))
				.click();

		MobileElement temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);
		if (temp != null) {

			failTest("Error: In DOB Verification - DesktopAgent returned :" + temp.getText());
		} else {
			System.out.println(
					"There was no error from desktop agent ... !!! clicked DOB verification, waiting for popup");
		}

		temp = FrameworkUtility.waitForElementVisible(
				TestManager.appProperties.getProperty("push_notification_dob_input_id"), null, 10);
		if (temp == null) {

			failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
		}

		temp.sendKeys(TestManager.appProperties.getProperty("push_notification_dob_to_enter"));

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("push_notification_verify_btn_id"))
				.click();

		Assert.assertEquals(
				FrameworkUtility.verifyToastText(TestManager.appProperties.getProperty("toast_text_success"),
						GlobalValues.TOAST_MAX_WAIT_DURATION),
				true);

		FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);

		System.out.println("Completed DOB Verification.......................... SUCCESS : END");
	}

//	@Test
	public void verifyFraudNotificationDontCall() throws InterruptedException {
		System.out.println("In FRAUD NOTIFICATION Verification: Don't Call ..........................: START");

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_fraud_verification_btn_text"))
				.click();

		MobileElement temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);
		if (temp != null) {

			failTest("Error: In DOB Verification - DesktopAgent returned :" + temp.getText());
		} else {
			System.out.println(
					"There was no error from desktop agent ... !!! clicked FRAUD verification, waiting for popup");
		}

		temp = FrameworkUtility.waitForElementVisible(
				TestManager.appProperties.getProperty("push_notification_fraud_radio_dont_call_id"), null, 10);

		if (temp == null) {
			failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
		}
		temp.click();

		System.out.println(
				"Completed FRAUD NOTIFICATION Verification : Don't Call .......................... SUCCESS : END");
	}

	@Test
	public void verifyFraudNotificationCallNow() throws InterruptedException {
		System.out.println("In FRAUD NOTIFICATION Verification: Call Now ..........................: START");

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_fraud_verification_btn_text"))
				.click();

		MobileElement temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);
		if (temp != null) {

			failTest("Error: In DOB Verification - DesktopAgent returned :" + temp.getText());
		} else {
			System.out.println(
					"There was no error from desktop agent ... !!! clicked FRAUD verification, waiting for popup");
		}

		temp = FrameworkUtility.waitForElementVisible(
				TestManager.appProperties.getProperty("push_notification_fraud_radio_call_now_id"), null, 10);

		if (temp == null) {
			failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
		}

		temp.click();

		temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);

		if (temp != null) {
			if (temp.getText().equalsIgnoreCase("SUCCESS")) {
				System.out.println(
						"There was no error from desktop agent ... !!! clicked Adddress verification waiting for popup");
			} else {
				failTest("Error: In address Verification - DesktopAgent returned :" + temp.getText());
			}
		}
		answerIncomingCall();

		FrameworkUtility.AddDelay(500);

		disconnectOngoingCall();

		System.out.println(
				"Completed FRAUD NOTIFICATION Verification : Call Now .......................... SUCCESS : END");
	}

//	@DataProvider
	public Object[][] getFraudCallTestType() {
		Object[][] data = new Object[4][2];
		data[0][0] = "DONTCALL";
		data[0][1] = null;

		data[1][0] = "CALL";
		data[1][1] = "REJECT";

		data[2][0] = "CALL";
		data[2][1] = "ANSWER";

		data[3][0] = "LATER";
		data[3][1] = null;

		return data;
	}

//	@Test(dataProvider="getFraudCallTestType")
	public void verifyFraudNotification(String option, String action) throws InterruptedException {
		System.out.println("In FRAUD NOTIFICATION Verification: ..........................: START");

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		System.out.println("clicking on Agent PAGE Fraud notification button: ..........................");
		FrameworkUtility
				.findButtonWithText(TestManager.appProperties.getProperty("agent_page_fraud_verification_btn_text"))
				.click();
		MobileElement temp = null;
		temp = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);

		if (temp != null) {
			if (temp.getText().equalsIgnoreCase("success")) {
				System.out.println("On selection of fraud notification button, toast is displayed");
			} else {
				failTest("Error: In FRAUD Verification - DesktopAgent returned :" + temp.getText());
			}
		} else {
			System.out.println("There was no no toast to wait for, waiting for popup");
		}
		MobileElement callOption = null;

		if (option.equalsIgnoreCase("DONTCALL")) {
			System.out.println("In DONTCALL : ..........................:START");
			callOption = FrameworkUtility.waitForElementVisible(
					TestManager.appProperties.getProperty("ai.journey.k2bank:id/dontCall"), null, 10);

			if (callOption == null) {
				failTest("Error: In Fraud Verification - Push Notification is not received, waited for 10 seconds");
			}

			callOption.click();
			System.out.println("In DONTCALL : ..........................:END");

		} else if (option.equalsIgnoreCase("CALL")) {
			System.out.println("In CALLNOW : ..........................:START");
			callOption = FrameworkUtility.waitForElementVisible(
					TestManager.appProperties.getProperty("push_notification_fraud_radio_call_now_id"), null, 10);

			if (callOption == null) {
				failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
			}

			callOption.click();

			MobileElement temp1 = null;
			temp1 = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);

			if (temp1 != null) {
				if (temp1.getText().equalsIgnoreCase("SUCCESS")) {
					System.out.println(
							"There was no error from desktop agent ... !!! clicked Adddress verification waiting for popup");
				} else {
					failTest("Error: In address Verification - DesktopAgent returned :" + temp1.getText());
				}
			}

			if (action.equalsIgnoreCase("REJECT")) {

				rejectIncomingCall();

			} else if (action.equalsIgnoreCase("ANSWER")) {

				answerIncomingCall();

				FrameworkUtility.AddDelay(500);

				disconnectOngoingCall();
			}

			System.out.println("In CALLNOW : ..........................:END");

		} else if (option.equalsIgnoreCase("LATER")) {
			// This option will be implemented once support in backend is available
			System.out.println("In CALL LATER : ..........................:START");
			callOption = FrameworkUtility.waitForElementVisible(
					TestManager.appProperties.getProperty("ai.journey.k2bank:id/callLater"), null, 10);

			if (callOption == null) {
				failTest("Error: In address Verification - Push Notification is not received, waited for 10 seconds");
			}

			callOption.click();

			MobileElement temp2 = null;

			temp2 = FrameworkUtility.waitForElementVisible(null, "//android.widget.Toast", 3);

			if (temp2 != null) {
				if (temp2.getText().equalsIgnoreCase("SUCCESS")) {
					System.out.println(
							"There was no error from desktop agent ... !!! clicked Adddress verification waiting for popup");
				} else {
					failTest("Error: In address Verification - DesktopAgent returned :" + temp2.getText());
				}
			}

			rejectIncomingCall();
			System.out.println("In CALL LATER : ..........................:END");
		}

		FrameworkUtility.AddDelay(2000);

		System.out.println(
				"Completed FRAUD NOTIFICATION Verification : Call Now .......................... SUCCESS : END");
	}

//	@Test
	public void verifyOutBoundCall() throws InterruptedException {

//		Register mobile number if not registered already (this methods works like a singleton)
		registerMobilenumber();

		AppCommon.switchToAgentView();

		System.out.println("In outbound call................");

		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_outboundcall_verification_btn_text")).click();

		System.out.println("waiting to receive incoming call for 120 secs................");

		((AndroidDriver) TestManager.driver).openNotifications();

		WebDriverWait wait = new WebDriverWait(TestManager.driver, 20);
		MobileElement element = null;
		try {
			element = (MobileElement) wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.dialer:id/action0")));
		} catch (Exception e) {
			System.out.println("No incoming call received within 20 seconds");
		}
		Assert.assertNotEquals(element, null, "Incoming call isnot received");

		element.click();

	}

	@AfterSuite
	public void verifyzResetDesktopAgent() throws InterruptedException {

		System.out.println("Reset Desktop Agent and Register mobile again............................. : START");

		AppCommon.switchToAgentView();

		FrameworkUtility.AddDelay(500);

		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("agent_page_reset_agent_btn_id"))
				.click();

//			registerMobilenumber();

		System.out.println("Reset Desktop Agent and Register mobile again............................. : END");

	}
}