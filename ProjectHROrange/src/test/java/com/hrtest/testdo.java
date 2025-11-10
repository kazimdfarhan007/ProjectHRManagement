package com.hrtest;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;

public class testdo extends BaseClass {
	@Test
	public void testmethod() {
		String title = getDriver().getCurrentUrl();
		assert title.equals("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login") : "Title does not match";
		System.out.println("Title matches: " + title);
	}
}
