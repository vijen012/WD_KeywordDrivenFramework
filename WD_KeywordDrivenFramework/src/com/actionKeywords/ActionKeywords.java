package com.actionKeywords;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ActionKeywords 
{
	private WebDriver driver;
	
	
	public ActionKeywords() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public ActionKeywords(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void launchBrowser(WebElement webElement, String dataSet)
	{
		
		System.out.println("launchBrowser");
		driver.get(dataSet);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void verifyPage(WebElement webElement, String dataSet)
	{
		System.out.println("verifyPage");
	}
	
	public void enterText(WebElement webElement, String dataSet)
	{
		System.out.println("enterText");
	}
	
	public void click(WebElement webElement, String dataSet)
	{
		System.out.println("click");
	}
	
}
