package com.hrtest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;
import com.demoproj.pages.HomePage;
import com.demoproj.pages.LoginPage;
import com.demoproj.utilities.ExtentManager;

public class HomePageTest extends BaseClass {
	private HomePage homePage;
	private LoginPage loginpage;
	
	@BeforeMethod
	public void setupPages() {
		homePage = new HomePage(getDriver());
		loginpage = new LoginPage(getDriver());
	}
	
	@Test
	public void verifyAdminTabVisibilityTest() {
		ExtentManager.startTest("Home Page Verify Logo Test");
		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginpage.login("Admin", "admin123");
		ExtentManager.logStep("Verifying logo is visible or not");
		homePage.visibleLogo();
		ExtentManager.logStep("Validation Successful");
		homePage.logout();
		ExtentManager.logStep("Logged out Successfully!");
	}
}
