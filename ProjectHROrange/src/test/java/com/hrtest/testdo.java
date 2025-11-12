package com.hrtest;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;
import com.demoproj.utilities.ExtentManager;

public class testdo extends BaseClass {
	@Test
	public void testmethod() {
		ExtentManager.startTest("Verify Login Page URL");
		String title = getDriver().getCurrentUrl();
		assert title.equals("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login") : "Title does not match";
		System.out.println("Title matches: " + title);
		ExtentManager.logStep("Login Page URL verified successfully");
	}
}
