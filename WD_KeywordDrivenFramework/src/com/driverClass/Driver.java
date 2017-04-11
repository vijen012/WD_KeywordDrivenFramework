package com.driverClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.actionKeywords.ActionKeywords;
import com.factory.DriverFactory;
import com.logging.LoggerUtility;
import com.objectRepo.ReadObjects;
import com.utilities.ExcelUtil;
import com.variables.Constant;

public class Driver 
{	
	public static Properties OR;
	private DriverFactory factory;
	private ExcelUtil excelUtil;
	private WebDriver driver = null;
	private ActionKeywords actionKeywords;
	private Logger logger =  LoggerUtility.getLogger();
	
	@BeforeSuite
	public void beforeSuite()
	{
		factory = new DriverFactory();
		excelUtil = new ExcelUtil();
	}
	
	@AfterSuite
	public void afterSuite()
	{
		excelUtil.closeExcel();
		excelUtil = null;
		factory = null;		
	}
	
	@BeforeTest
	@Parameters("browserName")
	public void beforeTest(String browserName)
	{
		factory = new DriverFactory();
		driver = factory.getBrowserDriver(browserName);
		actionKeywords = new ActionKeywords(driver);
		OR = new Properties(System.getProperties());
		OR = ReadObjects.getObjectRepository();
		//actionKeywords = new ActionKeywords();
		logger.info("***********************BeforeTest****************************");
	}
	
	@AfterTest
	public void afterTest()
	{
		driver.close();
		driver.quit();
		logger.info("***********************AfterTest****************************");
	}	
	
	@Test
	public void test()
	{		
		//driver.get("http://www.google.com");
		//driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		executeTestCases();
	}
	
	
	private void executeTestCases()
	{
		excelUtil.setExcelFile("Test.xlsx");
		int iTotalTestCases = excelUtil.getRowCount(Constant.SHEET_TESTCASES);
		//int columnCount = excelUtil.getColumnCount(Constant.SHEET_TESTCASES);

		//System.out.println("SheetName : "+excelSheet.getSheetName() + " || " + "Row Count : " +rowCount+ " || " + "Column Count : "+columnCount);
		
		for(int iRow = 1; iRow < iTotalTestCases; iRow++)
		{
			String testCaseName = excelUtil.getCellData(Constant.SHEET_TESTCASES, iRow, Constant.TESTCASE_ID);
			String testCaseRunMode = excelUtil.getCellData(Constant.SHEET_TESTCASES, iRow, Constant.RUNMODE);
			if(testCaseRunMode.equalsIgnoreCase("yes"))
			{
				int iTestCaseStartRowNum = excelUtil.getTestCaseRowNumber(testCaseName, Constant.TESTCASE_ID, Constant.SHEET_TESTSTEPS);
				int iTestCaseLastRowNum = excelUtil.getTestStepsCount(testCaseName, iTestCaseStartRowNum, Constant.SHEET_TESTSTEPS);
				//System.out.println("TestCase Name : "+testCaseName+" TestCaseStartRowNum : "+iTestCaseStartRowNum+" TestCaseLastRowNum : "+iTestCaseLastRowNum);
				logger.info(testCaseName + " execution started");
				for( ; iTestCaseStartRowNum <= iTestCaseLastRowNum; iTestCaseStartRowNum++)
				{
					String testStepId = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.TESTSTEP_ID);
					String actionKeyword = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.ACTION_KEYWORD);
					String element = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.PAGE_OBJECT);
					String dataSet = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.DATA_SET);
					//System.out.println("TestCase Name : "+testCaseName+" TestStep Id : "+testStepId+" ActionKeyword : "+actionKeyword);
					executeAction(actionKeyword, element, dataSet);
				}
				logger.info(testCaseName + " execution completed");
			}
		}		
	}//End executeTestCases()
	
	
	private void executeAction(String actionkeyword, String element, String dataSet)
	{
			try 
			{
				WebElement webElement = null;
				Method method = actionKeywords.getClass().getMethod(actionkeyword, WebElement.class, String.class);
				if(!(element.isEmpty() || element.equals("")))
				{
					webElement = driver.findElement(By.xpath(OR.getProperty(element)));
				}
				method.invoke(actionKeywords, webElement, dataSet);
			} 
			catch (NoSuchMethodException | SecurityException |InvocationTargetException | IllegalAccessException e) 
			{
				// TODO Auto-generated catch block
				logger.error("Error In ExecuteAction Method");
				e.printStackTrace();
			}
	}
}
