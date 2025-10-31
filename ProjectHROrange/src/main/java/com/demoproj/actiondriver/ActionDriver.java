package com.demoproj.actiondriver;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionDriver{
	private WebDriver driver;
	private WebDriverWait wait;
	
	public ActionDriver(WebDriver driver) {
		this.driver=driver;
		this.wait=new WebDriverWait(driver,Duration.ofSeconds(30));
		
	}
	public void click(By by) {
		try {
			driver.findElement(by).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to click element"+e.getMessage());
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