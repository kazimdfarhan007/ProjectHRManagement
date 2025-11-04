package com.demoproj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.demoproj.actiondriver.ActionDriver;

public class LoginPage {

	private ActionDriver actionDriver;
	private By usernameField = By.name("username");

    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[text()=' Login ']");
    private By errorMsg = By.linkText("//p[text()='Invalid credentials']");
    
	public LoginPage(WebDriver driver) {
		ActionDriver actionDriver = new ActionDriver(driver);
    	this.actionDriver = actionDriver;
    }

    public void login(String username,String password) {
   
		actionDriver.type(usernameField, username);
		actionDriver.type(passwordField, password);
		actionDriver.click(loginButton);
    }

	public void geterrorMsg() {
		actionDriver.isDisplayed(errorMsg);
	}

	public void geterrorText(String expected) {
		actionDriver.getText(errorMsg);
		actionDriver.compareText(errorMsg, expected);
	}

    
}