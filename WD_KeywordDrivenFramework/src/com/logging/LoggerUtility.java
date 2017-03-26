package com.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtility 
{
	private static final Logger LoggerInstance = LogManager.getLogger(LoggerUtility.class.getName()); 
	
	public static Logger getLogger()
	{
		return LoggerInstance;
	}
}
