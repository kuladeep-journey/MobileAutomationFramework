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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionalTestSuite_android {

	boolean registered = false;

	@Parameters({ "platform", "appFolderName" , "phoneNumber" , "apkIpaName"})
	public FunctionalTestSuite_android(@Optional("android") String platform, String appFolder, String phoneNum, String apkIpa) {
		
		TestManager.platform = platform;
		TestManager.appFolderName = appFolder;
		TestManager.apkIpaName = apkIpa;

		FrameworkUtility.initAppiumDriver(platform);
		TestManager.setAppGlobalVariable("phonenumber", phoneNum);
				
		TestManager.printglobalValues();
		
		FrameworkUtility.AddDelay(2000);
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

//	@Test
	public void verifyAddress() throws InterruptedException {
		System.out.println("In ADDRESS Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_address_verification_btn_text"))
				.click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains(TestManager.appProperties.getProperty("toast_text_error"))) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {
			Assert.assertEquals(AppCommon.waitForPopUpwithID(
					TestManager.appProperties.getProperty("verification_push_notification_desc_Id"),0),
					true);
			FrameworkUtility.AddDelay(1000);
			FrameworkUtility.findElementById(
					TestManager.appProperties.getProperty("push_notification_address_input"))
					.sendKeys(TestManager.appProperties.getProperty("push_notification_address_to_enter"));
			FrameworkUtility.AddDelay(1000);
			FrameworkUtility.findElementById(
					TestManager.appProperties.getProperty("push_notification_verify_btn_id"))
					.click();
			FrameworkUtility.AddDelay(1000);
			Assert.assertEquals(
					FrameworkUtility.verifyToastText(
							TestManager.appProperties.getProperty("toast_text_success"), 
							GlobalValues.TOAST_MAX_WAIT_DURATION), true);
			
			FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);
		}
		System.out.println("Completed ADDRESS Verification............................. SUCCESS : END");

	}

//	@Test
	public void verifySsn() throws InterruptedException {
		System.out.println("In SSN Verification..........................: START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

//		FrameworkUtility.findButtonWithText(GlobalValues.SSN_VERIFICATION_BTN).click();
		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_ssn_verification_btn_text"))
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

		}
		Thread.sleep(3000);
		System.out.println("Completed SSN Verification.......................... SUCCESS : END");

	}

//	@Test
	public void verifyDob() throws InterruptedException {
		System.out.println("In DOB Verification..........................: START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

//		FrameworkUtility.findButtonWithText(GlobalValues.DOB_VERIFICATION_BTN).click();
		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_dob_verification_btn_text"))
				.click();
		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			
			System.out.println(
					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {
			
		}
		Thread.sleep(3000);
		
		System.out.println("Completed DOB Verification.......................... SUCCESS : END");

	}

//	@Test
	public void verifyFraudNotification() throws InterruptedException {
		System.out.println("In FRAUD NOTIFICATION Verification..........................: START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

//		FrameworkUtility.findButtonWithText(GlobalValues.FRAUD_NOTIFICATION_BTN).click();
		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_fraud_verification_btn_text"))
				.click();
//		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
//		String toastText = toast.getText();

//		if (toastText.toLowerCase().contains("error")) {
//			
//			System.out.println(
//					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");
//			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);
//
//		} else {
//			
//		}
//		MobileElement elem = AppCommon.waitForPopUpwithID("ai.journey.k2bank:id/radioGroup", 10);
		FrameworkUtility.AddDelay(10000);
		FrameworkUtility.findElementById(TestManager.appProperties.getProperty("fraud_notification_radio_dont_call_id")).click();
		
		

		System.out.println("Completed FRAUD NOTIFICATION Verification.......................... SUCCESS : END");

	}

	@Test
	public void verifyOutBoundCall() throws InterruptedException {

//		AppCommon.switchToAgentView();
//		System.out.println("In outbound call................");
//		FrameworkUtility.findButtonWithText(
//				TestManager.appProperties.getProperty("agent_page_outboundcall_verification_btn_text")).click();
//		System.out.println("waiting to receive incoming call for 20 secs................");
//		
//
//
////		FrameworkUtility.AddDelay(20000);
//		WebDriverWait wait = new WebDriverWait(TestManager.driver, 120);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.dialer:id/op_name")));
//		TestManager.driver.findElement(By.id("com.android.dialer:id/touch_view")).sendKeys("0.1");
		registerMobilenumber();
		AppCommon.switchToAgentView();
		System.out.println("In outbound call................");
		FrameworkUtility.findButtonWithText(
				TestManager.appProperties.getProperty("agent_page_outboundcall_verification_btn_text")).click();
		System.out.println("waiting to receive incoming call for 120 secs................");
		((AndroidDriver) TestManager.driver).openNotifications();


//		FrameworkUtility.AddDelay(20000);
		WebDriverWait wait = new WebDriverWait(TestManager.driver, 20);
		
		MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.dialer:id/action0")));
		
		Assert.assertNotEquals(element,null, "Incoming call isnot received");
		
		element.click();
		
	}

	@AfterSuite
	public void verifyzResetDesktopAgent() throws InterruptedException {
		System.out.println("Reset Desktop Agent and Register mobile again............................. : START");
		AppCommon.switchToAgentView();
		FrameworkUtility.findElementById(
				TestManager.appProperties.getProperty("agent_page_reset_agent_btn_id"))
				.click();
		
		FrameworkUtility.AddDelay(500);
//			registerMobilenumber();
		System.out.println("Reset Desktop Agent and Register mobile again............................. : END");
	}
}