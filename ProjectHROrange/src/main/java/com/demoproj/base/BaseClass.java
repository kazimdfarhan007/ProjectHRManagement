package com.demoproj.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.demoproj.actiondriver.ActionDriver;
import com.demoproj.utilities.ExtentManager;
import com.demoproj.utilities.LoggerManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static Properties prop;
//	public WebDriver driver;
//	private static ActionDriver actionDriver;

	// for thread safety
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<ActionDriver>();

	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	FileReader fr;

	@BeforeSuite
	public void loadconfig() throws IOException {
		prop = new Properties();
		fr = new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\cf.properties");
		prop.load(fr);
		logger.info("cf.properties file loaded");

	}

	@BeforeMethod
	public void setUp() throws IOException {

		System.out.println("Seeting up webdriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(3);
		logger.info("Browser launched successfully");
		
		// intialize action driver once
//		if (actionDriver == null) {
//			actionDriver = new ActionDriver(driver);
//			logger.info("Action Driver initialized successfully"+Thread.currentThread().getName());
//		}
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("Action Driver initialized successfully for thread: " + Thread.currentThread().getName());

	}

	public void launchBrowser() {
		if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();

//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--remote-allow-origins=*");
//			driver = new ChromeDriver(options);
			driver.set(new ChromeDriver());// new changes as per thread local
			ExtentManager.registerDriver(getDriver());
			logger.info("Chrome Browser launched");

		} else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver();
			driver.set(new FirefoxDriver()); // new changes as per thread local
			ExtentManager.registerDriver(getDriver());
			logger.info("Firefox Browser launched");

		} else if (prop.getProperty("browser").equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			// driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver(getDriver());
		}
	}

	public void configureBrowser() {
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		driver.get().manage().window().maximize();
		try {
			driver.get().get(prop.getProperty("url"));
			logger.info("Navigated to URL: " + prop.getProperty("url"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to navigate to URL: " + e.getMessage());
		}
	}

	@AfterMethod
	public void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
				logger.info("Browser closed successfully");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("unable to quit the driver:" + e.getMessage());
			}
		}
//		driver=null;
//		actionDriver=null;
		driver.remove();
		actionDriver.remove();
		System.out.println("Driver and ActionDriver set to null");
		logger.info("Browser closed and resources cleaned up");
	}
	@AfterSuite
	public void tearDownlast() {
	    ExtentManager.endTest();
	    System.out.println("Extent Report flushed — file created successfully.");
	}


	public static Properties getProp() {
		return prop;
	}

	// getter for driver
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			System.out.println("Driver is null");
			throw new IllegalStateException(
					"WebDriver has not been initialized. Call setUp() before using getDriver().");

		}
		return driver.get();
	}

	public static ActionDriver getActionDriver() {
		if (actionDriver.get() == null) {
			System.out.println("Driver is null in getActionDriver");
			throw new IllegalStateException(
					"WebDriver has not been initialized. Call setUp() before using getActionDriver().");
		}
		return actionDriver.get();
	}

	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

	public void staticWait(int sec) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(sec));
	}
}
