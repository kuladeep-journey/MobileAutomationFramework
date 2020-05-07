package journeyai.MobileTestAutomation.K2QaApp;

public class GlobalValues {
	static String ToastSuccess = "SUCCESS";
	static String ToastFailure = "FAILURE";
	static String ToastError = "Error";
	static String PushNotiAddr = "this is addreess verififcation string";
	static String PushNotiYOB = "1980";
	static String PushNotiSsnL4Digi = "1234";
	static String PushNotiCrediCardL4Digi = "1234";
	String K2BankUrl;
	static int TOAST_MAX_WAIT_DURATION = 30;
	static int PUSH_NOTIFICATION_MAX_WAIT_DURATION = 60;
	static int DEFAULT_DELAY_MILLISEC = 5000;

	static String ADDRESS_VERIFICATION_BTN = "ADDRESS VERIFICATION";
	static String ADDRESS_VERIFICATION_BTN_ID = "ai.journey.k2bank:id/addressVerifcation";
	static String ADDRESS_INPUT_TEXT = "Bangalore, India";
	static String SSN_VERIFICATION_BTN = "SSN VERIFICATION";
	static String SSN_VERIFICATION_BTN_ID = "ai.journey.k2bank:id/ssnVerification";
	static String SSN_INPUT_TEXT = "1234";
	static String DOB_VERIFICATION_BTN = "DOB VERIFICATION";
	static String DOB_VERIFICATION_BTN_ID = "ai.journey.k2bank:id/dobVerification";
	static String DOB_INPUT_TEXT = "01011985";
	static String OUTBOUND_CALL_BTN = "OUTBOUND CALL";
	static String OUTBOUND_CALL_BTN_ID = "ai.journey.k2bank:id/outboundCall";
	// for outbound call, we need to check incoming call and confirm whether we are
	// able to recieve calls after selecting outbound call option

	static String FRAUD_NOTIFICATION_BTN = "FRAUD NOTIFICATION";
	static String FRAUD_NOTIFICATION_BTN_ID = "ai.journey.k2bank:id/fraudVerification";

	static String PopUpDescId = "ai.journey.k2bank:id/dialogDesc";

	public static int ZeroWaitSec = 0;
}
