package com.browserDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.interfaces.IWebDriver;
import com.variables.GlobalVariables;

public class GoogleChromeDriver implements IWebDriver 
{
	private WebDriver driver = null;
	public WebDriver getDriver()
	{
		System.setProperty("webdriver.chrome.driver", GlobalVariables.getDriverServerDirPath() +"\\chromedriver.exe");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);		
		driver = new ChromeDriver(cap);
		return driver;
	}
	
}
