package com.demoproj.actiondriver;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demoproj.base.BaseClass;

public class ActionDriver{
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = BaseClass.logger;
	
	public ActionDriver(WebDriver driver) {
		this.driver=driver;
		int Intg = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait=new WebDriverWait(driver,Duration.ofSeconds(Intg));
		System.out.println("Action Driver created");
	}
	public void click(By by) {
		try {
			waitforElementtobeClikable(by);
			driver.findElement(by).click();
			logger.info("Clicked on element: " + by.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to click on element: " + by.toString() + " - " + e.getMessage());
		}
	}

	public void type(By by, String text) {
		try {
			waitforElementtobeVisible(by);
			driver.findElement(by).sendKeys(text);
			logger.info("Typed text '" + text + "' into element: " + by.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to type text '" + text + "' into element: " + by.toString() + " - " + e.getMessage());
		}
	}

	public String getText(By by) {
		String text = null;
		try {
			waitforElementtobeVisible(by);
			text = driver.findElement(by).getText();
			logger.info("Retrieved text from element: " + by.toString() + " - Text: " + text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to get text from element" + e.getMessage());
		}
		return text;
	}

	public boolean isDisplayed(By by) {
		try {
			waitforElementtobeVisible(by);
			boolean displayed = driver.findElement(by).isDisplayed();
			if (displayed) {
				logger.info("Element is displayed");
			} else {
				logger.error("Element is not displayed");
			}
			return displayed;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to verify element display status" + e.getMessage());
			return false;		
		}
		
	}
	public void compareText(By by, String expectedText) {
		try {
			waitforElementtobeVisible(by);
			String actualText = getText(by);
			if (actualText.equals(expectedText)) {
				logger.info("Text matches: " + actualText);
			} else {
			logger.error("Text does not match. Expected: " + expectedText + ", Actual: " + actualText);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to compare text" + e.getMessage());
		}
	}

	public void scrollToElement(By by) {
		try {
			waitforElementtobeVisible(by);
			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			logger.info("Scrolled to element: " + by.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to scroll to element" + e.getMessage());
		}
	}

	public void waitforPageLoad() {
		try {
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
					.equals("complete"));
			logger.info("Page loaded completely");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Page did not load completely" + e.getMessage());
		}

	}

	public void scrolltoElementandClick(By by) {
		try {
			waitforElementtobeVisible(by);
			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("arguments[0].click();", element);
			logger.info("Scrolled to element and clicked: " + by.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to scroll and click element" + e.getMessage());
		}
	}
	private void waitforElementtobeClikable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
			logger.info("Element is clickable: " + by.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("element is not clickable"+e.getMessage());
		}
		
	}
	public void waitforElementtobeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			logger.info("Element is visible: " + by.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Element is not visible"+e.getMessage());
		}
		
	}
	
	
}