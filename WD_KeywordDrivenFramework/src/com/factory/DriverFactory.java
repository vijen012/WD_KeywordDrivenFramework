package com.factory;

import org.openqa.selenium.WebDriver;
import com.browserDriver.MozilaFirefoxDriver;
import com.browserDriver.GoogleChromeDriver;
import com.browserDriver.InternetExpDriver;
import com.interfaces.IWebDriver;

public class DriverFactory 
{
	
	public WebDriver getBrowserDriver(String browserName)
	{
		WebDriver driver = null;;
		if(browserName.equalsIgnoreCase(IWebDriver.internetExplorer))
		{
			driver = new InternetExpDriver().getDriver();
		}
		else if(browserName.equalsIgnoreCase(IWebDriver.googleChrome))
		{
			driver = new GoogleChromeDriver().getDriver();
		}
		else if(browserName.equalsIgnoreCase(IWebDriver.firFox))
		{
			driver = new MozilaFirefoxDriver().getDriver();
		}
		return driver;
	}
}
