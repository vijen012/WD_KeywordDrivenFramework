package com.browserDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import com.interfaces.IWebDriver;

public class MozilaFirefoxDriver implements IWebDriver
{
	private WebDriver driver = null;
	
	public WebDriver getDriver()
	{		
		FirefoxProfile profile = new FirefoxProfile();
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);
		driver = new FirefoxDriver(profile);
		return driver;
	}
}
