package com.hrtest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;
import com.demoproj.pages.HomePage;
import com.demoproj.pages.LoginPage;
import com.demoproj.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {
	
	private LoginPage loginpage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginpage=new LoginPage(getDriver());
		homePage=new HomePage(getDriver());
	}
	@Test
	public void verfyValidityLoginTest() {
		ExtentManager.startTest("Valid Login Test");
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginpage.login("Admin","admin123");
		ExtentManager.logStep("Verifying Admin tab is visible or not");
		
		//ssert.assertTrue(homePage.visibleAdminTab(), "Adminshd be visible after login");
		//
		Assert.assertTrue(homePage.visibleAdminTab());
		ExtentManager.logStep("Validation Successful");
		homePage.logout();
		ExtentManager.logStep("Logged out Successfully!");
		staticWait(2);
	}
@Test
	public void invalidLoginTest() {
	ExtentManager.startTest("In-valid Login Test!");
		loginpage.login("Admin", "admin12");
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginpage.geterrorMsg();
		loginpage.geterrorText("Invalid credentials");
		ExtentManager.logStep("Validation Successful");
	}
}
