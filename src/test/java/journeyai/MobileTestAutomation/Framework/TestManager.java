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
	public static String appFolderName;
	public static String apkIpaName;
	public static Properties globalParams = new Properties();
	public static Properties appProperties = new Properties();

	public static void setFWGlobalVariable(String key, String value) {
		
		TestManager.globalParams.setProperty(key, value);
		
		System.out.println(
				"\n Property key : " + key + " - \t property value: " + TestManager.globalParams.getProperty(key));
	}

	public static void setAppGlobalVariable(String propName, String propValue) {
		
		TestManager.appProperties.setProperty(propName, propValue);
		
		System.out.println("\n Property key : " + propName + " - \t property value: "
				+ TestManager.appProperties.getProperty(propName));
	}

	public void CreateDriver() throws IOException {
		
		readGlobalValues();
		
		System.out.println("Create Driver ..................... : START");
		
		if (driver == null) {

			// check platform and create corresponding driver

			if (platform.equalsIgnoreCase("android")) {
				
				createAndroidDriver();
				
			} else if (platform.equalsIgnoreCase("ios")) {
				
				createiosDriver();
				
			}

			System.out.println("Create Driver ..................... : END");

		}
		
		return;
	}

	public void createAndroidDriver() throws IOException {
		
		driver = CreateTestDriver.AndroidCapabilities();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}

	public void createiosDriver() throws IOException {

		System.out.println("This part of iOS automation is pending ...!!!");

		driver = CreateTestDriver.IOSCapabilities();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	// read from global.properties and store in key value pairs /define variables

	public void readGlobalValues() {

		if (globalValsInitialized) {
			return;
		}

		// This is intentionally hard coded***
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

		globalParams.setProperty("rootPath", automationPath);
		
		globalParams.setProperty("appUnderTestPath", automationPath + TestManager.appFolderName);
		
		globalParams.setProperty("apkPath", automationPath + TestManager.appFolderName + "/appsUnderTest/");

		globalValsInitialized = true;
		
		ReadAppProperties();
		
	}

	public void ReadAppProperties() {
		
		String propFile = globalParams.getProperty("appUnderTestPath") + "/" + platform + "_resources.properties";
		
		System.out.println("app properties file : " + propFile);
		// Now add the app properties to Global framework properties
		File readAppProps = new File(propFile);
		
		FileInputStream fileInput = null;
		
		try {
			
			fileInput = new FileInputStream(readAppProps);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			
			appProperties.load(fileInput);
			
			fileInput.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * This method will set the execution platform, this is mandatory to create
	 * platform specific driver
	 */
	public static void setPlatform(String execPlatform) {
		
		platform = execPlatform;
	}

	public static String getPlatform() {
		
		return platform;
	}

	/*
	 * This method will set the App under test automation folder
	 * 
	 */
	public static void setAppUnderTestFolderName(String appFolder) {

		appFolderName = appFolder;
	}

	public static String getAppUnderTestFolderName(String appFolder) {

		return appFolderName;
	}
	
	/*
	 * This method will set the Apk or ipa file name to install and test on mobile
	 * 
	 */
	public static void setapkIpaName(String fileName) {

		apkIpaName = fileName;
	}

	public static String getapkIpaName() {

		return apkIpaName;
	}

	public static void printglobalValues() {
		// Print all the key values to console
		System.out.println(
				"----------------------------------------------------------------------------------------------\n");
		System.out.println(
				"-----------------------------GLOBAL VARIABLES / CONFIG PARAMS---------------------------------\n");
		System.out.println(
				"----------------------------------------------------------------------------------------------\n");
		System.out.println(
				"==================================FW GLOBAL PROPERTIES=========================================\n");
		
		Enumeration KeyValues = globalParams.keys();
		
		while (KeyValues.hasMoreElements()) {
			
			String key = (String) KeyValues.nextElement();
			String value = globalParams.getProperty(key);
			System.out.println(key + "   -    " + value);
			
		}

		System.out.println("==========================APP GLOBAL PROPERTIES=================================");

		KeyValues = appProperties.keys();
		while (KeyValues.hasMoreElements()) {
			String key = (String) KeyValues.nextElement();
			String value = appProperties.getProperty(key);
			System.out.println(key + "   -    " + value);
		}
		
		System.out.println("================================================================================");
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
