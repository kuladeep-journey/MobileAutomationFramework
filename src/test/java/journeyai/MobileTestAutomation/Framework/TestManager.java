package journeyai.MobileTestAutomation.Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class TestManager {
//	public static AndroidDriver<AndroidElement> andydriver;
//	public static IOSDriver<IOSElement> iosdriver;
	public static AppiumDriver<MobileElement> driver;
	boolean globalValsInitialized = false;
	public static String platform;
	

	public void CreateDriver() throws IOException {
		readGlobalValues();
		
//		String platform = "Android";
		if (driver == null) {
			System.out.println("Create Driver ..................... : START");
			
			// check platform and create corresponding driver
			

			if (platform.equalsIgnoreCase("android")) {
				createAndroidDriver();
			}else if (platform.equalsIgnoreCase("ios")) {
				createiosDriver();
			}
//			else if (platform.equals("")) {
//					if (this.globalParams.getProperty("platform_under_test").equalsIgnoreCase("android")) {
//						platform = "android"; 
//					} else if (this.globalParams.getProperty("platform_under_test").equalsIgnoreCase("ios")) {
//						platform = "ios"; 
//					}	
//			}
			System.out.println("Create Driver ..................... : END");
		} 
		return;
	}

	public void createAndroidDriver() throws IOException {
		driver = CreateTestDriver.AndroidCapabilities();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver = andydriver;
	}

	public void createiosDriver() throws IOException {
		// Create IOS driver using Appium
		System.out.println("This part of iOS automation is pending ...!!!");
		driver = CreateTestDriver.IOSCapabilities();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver = iosdriver;
	}

	public static Properties globalParams = new Properties();

	public void readGlobalValues() {
		// read from global.properties and store in key value pairs /define variables
		// for corresponding key/values
		// fill value for K2BankUrl/CentraalURL/K2CMURL
		if (globalValsInitialized) {
			return;
		}
//		
//		globalParams.put("workingdir", System.getProperty("user.dir"));
//		globalParams.put("automationdir", System.getProperty("user.dir") + "/src/test/java/journeyai/MobileTestAutomation/");
//		globalParams.put("automationappdir", globalParams.getProperty("automationdir")+globalParams.getProperty("AppUnderTest"));
//		globalParams.put("appsundertest", globalParams.getProperty("automationdir")+globalParams.getProperty("AppUnderTest")+"/appsUnderTest");
		System.out.println("***************************************"+TestManager.platform);
		
		String automationPath = "src/test/java/journeyai/MobileTestAutomation/";
		
		File readGlobalValuesFile = new File(automationPath + "Framework/FrameworkGlobal.properties");
		
		System.out.printf("\n\n!!!!!!!All global values are read from path Below!!!!!!!\n",
				readGlobalValuesFile.getAbsolutePath());
		
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(readGlobalValuesFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			globalParams.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String appName = globalParams.getProperty("AppUnderTest");
		
		// Now add the app properties to Global framework properties
		File readAppGlobalValues = new File(automationPath + appName + "/AppGlobal.properties");
		try {
			System.out.println("App Global values file : " + automationPath + appName + "AppGlobal.properties");
			fileInput =  new FileInputStream(readAppGlobalValues);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			globalParams.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

		// Print all the key values to console
		System.out.println(
				"----------------------------------------------------------------------------------------------\n");
		System.out.println(
				"-----------------------------GLOBAL VARIABLES / CONFIG PARAMS---------------------------------\n");
		System.out.println(
				"----------------------------------------------------------------------------------------------\n");
		Enumeration KeyValues = globalParams.keys();
		while (KeyValues.hasMoreElements()) {
			String key = (String) KeyValues.nextElement();
			String value = globalParams.getProperty(key);
			System.out.println(key + "   -    " + value);
		}
		System.out.println(
				"----------------------------------------------------------------------------------------------\n");
//		System.out.printf("!!!!!!!     %s   !!!!!", readGlobalValues.getAbsolutePath());
		globalValsInitialized = true;
	}

	@Parameters({ "platform" })
	@BeforeSuite
	public void InitDriver(String testPlatform) throws IOException {
		System.out.println("\n Before Suite: .... : START");
		platform = testPlatform;
		CreateDriver();
		System.out.println("\n Before Suite: .... : END");
	}

	@AfterSuite
	public void closeDriver() {
		System.out.println("After Suite: ....!!!!");
		System.out.println("Completed the test suite, thus closing app and driver");
		driver.close();
	}

}
