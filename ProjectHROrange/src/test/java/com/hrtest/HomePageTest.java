package com.hrtest;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;
import com.demoproj.pages.HomePage;
import com.demoproj.pages.LoginPage;

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
		loginpage.login("Admin", "admin123");
		homePage.visibleLogo();
		homePage.logout();
	}
}
