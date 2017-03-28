package com.objectRepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.variables.GlobalVariables;

public class ReadObjects 
{
	private Properties properties = new Properties();
	
	public Properties getObjectRepository()
	{
		try
		{
			File file = new File(GlobalVariables.getObjectRepoDirPath() + "\\or.properties");
			FileInputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
		}
		catch (IOException ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		
		return properties;
	}
}
