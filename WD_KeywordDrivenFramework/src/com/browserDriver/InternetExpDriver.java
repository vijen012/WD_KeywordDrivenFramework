package com.browserDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.interfaces.IWebDriver;
import com.variables.GlobalVariables;

public class InternetExpDriver implements IWebDriver 
{
	private WebDriver driver = null;
	
	public WebDriver getDriver()
	{
		System.setProperty("webdriver.ie.driver", GlobalVariables.getDriverServerDirPath() +"\\IEDriverServer.exe");
		DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);	
		cap.setCapability("ignoreProtectedModeSettings", true);
		driver = new InternetExplorerDriver(cap); 
		return driver;
	}
}
