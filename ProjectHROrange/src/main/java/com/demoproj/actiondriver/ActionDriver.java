package com.demoproj.actiondriver;

import java.time.Duration;

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
	
	public ActionDriver(WebDriver driver) {
		this.driver=driver;
		int Intg = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
		this.wait=new WebDriverWait(driver,Duration.ofSeconds(Intg));
		
	}
	public void click(By by) {
		try {
			waitforElementtobeClikable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to click element"+e.getMessage());
		}
	}

	public void type(By by, String text) {
		try {
			waitforElementtobeVisible(by);
			driver.findElement(by).sendKeys(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to type in element" + e.getMessage());
		}
	}

	public String getText(By by) {
		String text = null;
		try {
			waitforElementtobeVisible(by);
			text = driver.findElement(by).getText();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to get text from element" + e.getMessage());
		}
		return text;
	}

	public boolean isDisplayed(By by) {
		try {
			waitforElementtobeVisible(by);
			boolean displayed = driver.findElement(by).isDisplayed();
			if (displayed) {
				System.out.println("Element is displayed");
			} else {
				System.out.println("Element is not displayed");
			}
			return displayed;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to verify element display status" + e.getMessage());
			return false;		
		}
		
	}
	public void compareText(By by, String expectedText) {
		try {
			waitforElementtobeVisible(by);
			String actualText = getText(by);
			if (actualText.equals(expectedText)) {
				System.out.println("Text matches: " + actualText);
			} else {
				System.out.println("Text does not match. Expected: " + expectedText + ", Actual: " + actualText);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to compare text" + e.getMessage());
		}
	}

	public void scrollToElement(By by) {
		try {
			waitforElementtobeVisible(by);
			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to scroll to element" + e.getMessage());
		}
	}

	public void waitforPageLoad() {
		try {
			wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
					.equals("complete"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Page did not load completely" + e.getMessage());
		}

	}

	public void scrolltoElementandClick(By by) {
		try {
			waitforElementtobeVisible(by);
			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to scroll and click element" + e.getMessage());
		}
	}
	private void waitforElementtobeClikable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("element is not clickable"+e.getMessage());
		}
		
	}
	public void waitforElementtobeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Element is not visible"+e.getMessage());
		}
		
	}
	
	
}