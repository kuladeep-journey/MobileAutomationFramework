package journeyai.MobileTestAutomation.K2QaApp;

import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;

import org.testng.annotations.Test;

import org.testng.Assert;

import journeyai.MobileTestAutomation.Framework.TestManager;
import journeyai.MobileTestAutomation.Framework.FrameworkUtility;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;

public class FunctionalTestSuite_android {
	
	boolean registered = false;

	@Parameters({ "platform" })
	public FunctionalTestSuite_android(@Optional("android") String platform) {
		TestManager.platform = platform;
		
		FrameworkUtility.initAppiumDriver(platform);
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
		FrameworkUtility.findElementById("ai.journey.k2bank:id/registerPhone").click();

		FrameworkUtility.AddDelay(2000);

		
		MobileElement element = FrameworkUtility.findElementWithValue("android.widget.EditText", "Enter phone number");

		element.sendKeys(TestManager.globalParams.getProperty("phonenumber").toString());

		FrameworkUtility.findElementById("ai.journey.k2bank:id/register").click();

		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);

		String toastText = AppCommon.getToastText(toast);

		Assert.assertEquals(toastText.equalsIgnoreCase(GlobalValues.ToastSuccess), true);

		registered = true;
		// System.out.printf("\n Register mobile number Success ? %b \n", registered);
		// System.out.println("In Register Mobile Number ............................. : END");
//		 add delay to let toast get disappeared.
		FrameworkUtility.AddDelay(2000);
	}

	@Test
	public void verifyAddress() throws InterruptedException {
		System.out.println("In ADDRESS Verification............................. : START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText(GlobalValues.ADDRESS_VERIFICATION_BTN).click();
		
		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {
			Assert.assertEquals(AppCommon.waitForPopUpwithID(GlobalValues.PopUpDescId, 0), true);
			FrameworkUtility.AddDelay(1000);
			FrameworkUtility.findElementById("ai.journey.k2bank:id/inputCard").sendKeys("United States Of America");
			FrameworkUtility.AddDelay(1000);
			FrameworkUtility.findElementById("ai.journey.k2bank:id/verify").click();
			FrameworkUtility.AddDelay(1000);
			Assert.assertEquals(
					FrameworkUtility.verifyToastText(GlobalValues.ToastSuccess, GlobalValues.TOAST_MAX_WAIT_DURATION),
					true);
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

		FrameworkUtility.findButtonWithText(GlobalValues.SSN_VERIFICATION_BTN).click();
		
		MobileElement toast = AppCommon.waitForToast(null, GlobalValues.TOAST_MAX_WAIT_DURATION);
		String toastText = toast.getText();

		if (toastText.toLowerCase().contains("error")) {
			// If there is no error, then wait for pop-up menu.

			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed ADDRESS Verification: AgentDesktop API returned Error, Status........ : FAILED");
			Assert.assertTrue(false, "Error: Desktop Agent returned :" + toastText);

		} else {
			Assert.assertEquals(AppCommon.waitForPopUpwithID(GlobalValues.PopUpDescId, 0), true);
			FrameworkUtility.AddDelay(1000);
			FrameworkUtility.findElementById("ai.journey.k2bank:id/editText").sendKeys("1234");
			FrameworkUtility.AddDelay(1000);
			FrameworkUtility.findElementById("ai.journey.k2bank:id/verify").click();
			FrameworkUtility.AddDelay(1000);
			Assert.assertEquals(
					FrameworkUtility.verifyToastText(GlobalValues.ToastSuccess, GlobalValues.TOAST_MAX_WAIT_DURATION),
					true);
			FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);
		}
		Thread.sleep(3000);
		System.out.println("Completed SSN Verification.......................... SUCCESS : END");

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	@Test
	public void verifyDob() throws InterruptedException {
		System.out.println("In DOB Verification..........................: START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText(GlobalValues.DOB_VERIFICATION_BTN).click();
		errorToast = FrameworkUtility.verifyToastText(GlobalValues.ToastError, GlobalValues.TOAST_MAX_WAIT_DURATION);
		if (errorToast == false) {
			// If there is no error, then wait for pop-up menu.
			Assert.assertEquals(AppCommon.waitForPopUpwithID(GlobalValues.PopUpDescId, 0), true);
			FrameworkUtility.findElementById("ai.journey.k2bank:id/inputCard").sendKeys("01011985");
			FrameworkUtility.findElementById("ai.journey.k2bank:id/verify").click();
			Assert.assertEquals(
					FrameworkUtility.verifyToastText(GlobalValues.ToastSuccess, GlobalValues.TOAST_MAX_WAIT_DURATION),
					true);
			FrameworkUtility.AddDelay(GlobalValues.DEFAULT_DELAY_MILLISEC);
		} else {
			// asserting again to false just to inform testng that this case is failed.
			System.out.println("Completed DOB Verification: AgentDesktop API ereturned Error, Status........ : FAILED");
			Assert.assertEquals(errorToast, false);
		}

		// Remove below sleep time once the validation is added.
		Thread.sleep(3000);
		System.out.println("Completed DOB Verification.......................... SUCCESS : END");

	}

//	@Test
	public void verifyFraudNotification() throws InterruptedException {
		System.out.println("In FRAUD NOTIFICATION Verification..........................: START");
		boolean errorToast = false;
		registerMobilenumber();
		AppCommon.switchToAgentView();

		FrameworkUtility.findButtonWithText(GlobalValues.FRAUD_NOTIFICATION_BTN).click();
		errorToast = FrameworkUtility.verifyToastText(GlobalValues.ToastError, GlobalValues.TOAST_MAX_WAIT_DURATION);
		if (errorToast == false) {
	
		} else {
			// asserting again to false just to inform testng that this case is failed.
			System.out.println(
					"Completed FRAUD NOTIFICATION Verification: AgentDesktop API ereturned Error, Status........ : FAILED");
			Assert.assertEquals(errorToast, false);
		}

		// Remove below sleep time once the validation is added.

		System.out.println("Completed FRAUD NOTIFICATION Verification.......................... SUCCESS : END");

	}

//	@Test
	public void verifyOutBoundCall() throws InterruptedException {
//		WebDriverWait wait = new WebDriverWait(TestManager.driver, 120);
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.dialer:id/op_name")));
//		TestManager.driver.findElement(By.id("com.android.dialer:id/touch_view")).sendKeys("0.1");
	}
}