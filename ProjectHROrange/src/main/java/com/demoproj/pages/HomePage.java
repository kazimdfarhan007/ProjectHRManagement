package com.demoproj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.demoproj.actiondriver.ActionDriver;

public class HomePage {
	private ActionDriver actionDriver;

	private By adminTab = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/aside/nav/div[2]/ul/li[1]/a/span");
	private By userdButtn = By.className("oxd-userdropdown-name");
	private By logoutLink = By.linkText("Logout");
	private By logo = By.xpath("//div[@class='oxd-brand-banner']//img");

	public HomePage(WebDriver driver) {
		this.actionDriver = new ActionDriver(driver);
	}

	public boolean visibleAdminTab() {
		
		return actionDriver.isDisplayed(adminTab);
	}

	public boolean visibleLogo() {

		return actionDriver.isDisplayed(logo);
	}
	
	public void logout() {
		actionDriver.click(userdButtn);
		actionDriver.click(logoutLink);
	}
	
}