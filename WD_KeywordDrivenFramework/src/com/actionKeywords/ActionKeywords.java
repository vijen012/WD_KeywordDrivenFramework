package com.actionKeywords;

import org.openqa.selenium.WebDriver;

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
	
	public void launchBrowser(String element, String dataSet)
	{
		System.out.println("launchBrowser");
	}
	
	public void verifyPage(String element, String dataSet)
	{
		System.out.println("verifyPage");
	}
	
	public void enterText(String element, String dataSet)
	{
		System.out.println("enterText");
	}
	
	public void click(String element, String dataSet)
	{
		System.out.println("click");
	}
	
}
