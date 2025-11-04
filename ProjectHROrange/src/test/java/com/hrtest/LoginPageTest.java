package com.hrtest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;
import com.demoproj.pages.HomePage;
import com.demoproj.pages.LoginPage;

public class LoginPageTest extends BaseClass {
	
	private LoginPage loginpage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginpage=new LoginPage(driver);
		homePage=new HomePage(driver);
	}
	@Test
	public void verfyValidityLoginTest() {
		loginpage.login("Admin","admin123");

		
		//ssert.assertTrue(homePage.visibleAdminTab(), "Adminshd be visible after login");
		//
		Assert.assertTrue(homePage.visibleAdminTab());
		homePage.logout();
	}
}
