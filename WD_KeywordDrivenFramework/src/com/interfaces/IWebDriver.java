package com.interfaces;

import org.openqa.selenium.WebDriver;

public interface IWebDriver 
{
	public static final String firFox = "Firefox";
	public static final String googleChrome = "Chrome";
	public static final String internetExplorer = "InternetExplorer";
	public WebDriver getDriver();
}
