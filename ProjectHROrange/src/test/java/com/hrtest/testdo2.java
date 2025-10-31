package com.hrtest;
import org.testng.annotations.Test;

import com.demoproj.base.BaseClass;

public class testdo2 extends BaseClass {
	@Test
	public void testmethod() {
		String title = driver.getTitle();
		assert title.equals("OrangeHRM") : "Title does not match";
		System.out.println("Title matches: " + title);
	}
}
