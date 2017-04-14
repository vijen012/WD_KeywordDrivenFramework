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
	public static boolean bResult;
	public static boolean bTestCaseNextStep;
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
		
		for(int iTestCase = 1; iTestCase < iTotalTestCases; iTestCase++)
		{
			//Setting the value of bResult variable to 'true' before starting every test case
			bResult = true;
			String testCaseName = excelUtil.getCellData(Constant.SHEET_TESTCASES, iTestCase, Constant.TESTCASE_ID);
			String testCaseRunMode = excelUtil.getCellData(Constant.SHEET_TESTCASES, iTestCase, Constant.RUNMODE);
			if(testCaseRunMode.equalsIgnoreCase("yes"))
			{
				int iTestCaseStepStartRowNum = excelUtil.getTestCaseRowNumber(testCaseName, Constant.TESTCASE_ID, Constant.SHEET_TESTSTEPS);
				int iTestCaseStepLastRowNum = excelUtil.getTestStepsCount(testCaseName, iTestCaseStepStartRowNum, Constant.SHEET_TESTSTEPS);
				//System.out.println("TestCase Name : "+testCaseName+" TestCaseStartRowNum : "+iTestCaseStartRowNum+" TestCaseLastRowNum : "+iTestCaseLastRowNum);
				logger.info(testCaseName + " execution started");
				//Setting the value of bResult variable to 'true' before starting every test step
				bResult=true;
				//setting the true for every first step of test case
				bTestCaseNextStep = true;
				for( ; iTestCaseStepStartRowNum <= iTestCaseStepLastRowNum; iTestCaseStepStartRowNum++)
				{
					//This code execute for every first step of test case but if any step failed then for subsequent steps of test case this code won't execute
					if(bTestCaseNextStep)
					{
						String testStepId = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStepStartRowNum, Constant.TESTSTEP_ID);
						String actionKeyword = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStepStartRowNum, Constant.ACTION_KEYWORD);
						String element = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStepStartRowNum, Constant.PAGE_OBJECT);
						String dataSet = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStepStartRowNum, Constant.DATA_SET);
						//System.out.println("TestCase Name : "+testCaseName+" TestStep Id : "+testStepId+" ActionKeyword : "+actionKeyword);
						executeAction(actionKeyword, element, dataSet);						
					}		
					
					//This is the result code, this code will execute after each test step
					//The execution flow will go in to this only if the value of bResult is 'false'
					
					if(bResult==true)
					{
						//If 'true' then store the test step result as Passed
						excelUtil.setCellData(Constant.SHEET_TESTSTEPS, Constant.PASS, iTestCaseStepStartRowNum, Constant.TESTSTEP_RESULT);						
					}
					else if(bResult==false && bTestCaseNextStep==true)
					{
						bTestCaseNextStep = false;
						//If 'false' then store the test step result as Failed
						excelUtil.setCellData(Constant.SHEET_TESTSTEPS, Constant.FAIL, iTestCaseStepStartRowNum, Constant.TESTSTEP_RESULT);						
												
						//If 'false' then store the test case result as Failed
						excelUtil.setCellData(Constant.SHEET_TESTCASES, Constant.FAIL, iTestCase, Constant.TESTCASE_RESULT);
						//End the test case in the logs
						logger.info("Failed - End test case "+ testCaseName);
						//By this break statement, execution flow will not execute any more test step of the failed test case
						//break;
					}
					else if(bResult==false && bTestCaseNextStep==false)
					{
						//If bResult==false && bTestCaseNextStep==false then store the test step result as Skipped
						excelUtil.setCellData(Constant.SHEET_TESTSTEPS, Constant.SKIP, iTestCaseStepStartRowNum, Constant.TESTSTEP_RESULT);												
					}
				}
				
				//This will only execute after the last step of the test case, if value is not 'false' at any step	
				if(bResult==true)
				{
					//If 'true' then store the test case result as Passed
					excelUtil.setCellData(Constant.SHEET_TESTCASES, Constant.PASS, iTestCase, Constant.TESTCASE_RESULT);
					//End the test case in the logs
					logger.info("Passed - End test case "+ testCaseName);								
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
