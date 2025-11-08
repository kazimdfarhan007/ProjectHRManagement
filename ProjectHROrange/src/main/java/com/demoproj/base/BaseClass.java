package com.demoproj.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.demoproj.actiondriver.ActionDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {

	public static Properties prop;
	public WebDriver driver;
	private static ActionDriver actionDriver;
	FileReader fr;
	@BeforeSuite
	public void loadconfig() throws IOException {
		prop=new Properties();
		if (driver == null) {
			fr = new FileReader(System.getProperty("user.dir")+"\\src\\main\\resources\\cf.properties");
			prop.load(fr);
		}
		
	}
	@BeforeMethod
	public void setUp() throws IOException {
		
		System.out.println("Seeting up webdriver for:"+this.getClass().getSimpleName());
		launchBrowser();
		staticWait(3);
		
		//intialize action driver once
		if (actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			System.out.println("Action driver initialized");
		}
		
	}
	public void launchBrowser() {
if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
			
			WebDriverManager.chromedriver().setup();
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.get(prop.getProperty("url"));
		}
		else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.get(prop.getProperty("url"));
		} else if (prop.getProperty("browser").equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.get(prop.getProperty("url"));
		} 
	}
	@AfterMethod
	public void tearDown() {
		driver.close();
		System.out.println("Browser closed successfully");
		driver=null;
		actionDriver=null;
		System.out.println("Driver and ActionDriver set to null");
	}

	
	public static Properties getProp() {
		return prop;
	}
	
	public WebDriver getDriver() {
		if (driver == null) {
			System.out.println("Driver is null");
			throw new IllegalStateException("WebDriver has not been initialized. Call setUp() before using getDriver().");
			
		}
		return driver;
	}

	public static ActionDriver getActionDriver() {
		if (actionDriver == null) {
			System.out.println("Driver is null in getActionDriver");
			throw new IllegalStateException(
					"WebDriver has not been initialized. Call setUp() before using getActionDriver().");
		}
		return actionDriver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public void staticWait(int sec){
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(sec));
	}
}
