package com.variables;

public class GlobalVariables 
{
	//System.out.println(System.getProperty("user.dir")+"\\driverServer");
	private static String ProjectDirPath;
	private static String DriverServerDirPath;
	private static String ExcelFilePath;
	
	public static String getProjectDirPath() 
	{
		if(ProjectDirPath == null)
		{
			ProjectDirPath = System.getProperty("user.dir");
		}
		return ProjectDirPath;
	}

	public static String getDriverServerDirPath() 
	{
		if(DriverServerDirPath == null)
		{
			DriverServerDirPath = getProjectDirPath() + "\\driverServer";
		}
		return DriverServerDirPath;
	}
	
	public static String getExcelFilePath() 
	{
		return ExcelFilePath;
	}

	public static void setExcelFilePath(String excelFilePath) 
	{
		GlobalVariables.ExcelFilePath = excelFilePath;
	}	
	
	
}
