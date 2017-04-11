package com.actionKeywords;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.logging.LoggerUtility;

public class ActionKeywords 
{
	private WebDriver driver;
	private Logger logger = LoggerUtility.getLogger();
	
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
		try
		{
			logger.info("Launch Browser");
			System.out.println("launchBrowser");
			driver.get(dataSet);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			logger.error("Error- Unable to open browser");
		}
		
	}
	
	public void verifyPage(WebElement webElement, String dataSet)
	{
		try
		{
			logger.info("Verify Page");
			System.out.println("verifyPage");				
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			logger.error("Error - unable to verify page");
			ex.printStackTrace();
		}
	}
	
	public void enterText(WebElement webElement, String dataSet)
	{
		try
		{
			logger.info("enter text");
			System.out.println("enterText");			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			logger.error("Error - unable to enter text");
			ex.printStackTrace();
		}
	}
	
	public void click(WebElement webElement, String dataSet)
	{
		try
		{
			logger.info("click");
			System.out.println("click");			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			logger.error("Error - unable to click on webelement");
			ex.printStackTrace();
		}
	}
	
}
