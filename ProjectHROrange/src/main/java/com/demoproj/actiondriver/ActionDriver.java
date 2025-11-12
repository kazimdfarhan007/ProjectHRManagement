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
import com.demoproj.utilities.ExtentManager;

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
		String elementDesc = getElementDescribtion(by);
		try {
			waitforElementtobeClikable(by);
			driver.findElement(by).click();
			ExtentManager.logStep("clicked an element: "+elementDesc);
			logger.info("Clicked on element: " +elementDesc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to click on element: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click element:", elementDesc+"_unable to click");
		}
	}

	public void type(By by, String text) {
		try {
			waitforElementtobeVisible(by);
			driver.findElement(by).sendKeys(text);
			logger.info("Typed text "+getElementDescribtion(by)+" into element: " + text);
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
			logger.info("Element is displayed " + getElementDescribtion(by));
			ExtentManager.logStep("Element is displayed: "+getElementDescribtion(by));
			ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is displayed: ", "Element is displayed: "+getElementDescribtion(by));
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			logger.error("Element is not displayed: " + e.getMessage());
			ExtentManager.logFailure(BaseClass.getDriver(),"Element is not displayed: ","Elemenet is not displayed: "+getElementDescribtion(by));
			return false;
		}
	}
	public void compareText(By by, String expectedText) {
		try {
			waitforElementtobeVisible(by);
			String actualText = getText(by);
			if (actualText.equals(expectedText)) {
				logger.info("Text matches: " + actualText);
				ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Compare Text", "Text Verified Successfully! "+actualText+ " equals "+expectedText);
			} else {
			logger.error("Text does not match. Expected: " + expectedText + ", Actual: " + actualText);
			ExtentManager.logFailure(BaseClass.getDriver(), "Text Comparison Failed!", "Text Comparison Failed! "+actualText+ " not equals "+expectedText);}
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

	public String getElementDescribtion(By locator) {
		
		if (locator == null) {
			return "locator is null";
		}
		if (driver == null) {
			return "WebDriver is null";
		}
		WebElement element = driver.findElement(locator);
		String name=element.getDomProperty("name");
		String id=element.getDomProperty("id");
		String text=element.getText();
		String className=element.getDomProperty("class");
		String placeholder=element.getDomProperty("placeholder");
		
		try {
			if(isNotEmpty(name)) {
			    return "name="+name;
			}
			else if (isNotEmpty(id)) {
				return "id=" + id;
			} else if (isNotEmpty(text)) {
				return "text=" + truncateString(text,70);
			} else if (isNotEmpty(className)) {
				return "class=" + className;
			} else if (isNotEmpty(placeholder)) {
				return "placeholder=" + placeholder;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("unable to describe element"+e.getMessage());
		}
		
		
		
		return "Unable to fetch element describtion";
	}
	private boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

	private String truncateString(String str, int maxLength) {
		if (str.length() <= maxLength) {
			return str;
		}
		return str.substring(0, maxLength) + "...";
	}
	
	
}