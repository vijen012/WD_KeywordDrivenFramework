package com.driverClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.actionKeywords.ActionKeywords;
import com.factory.DriverFactory;
import com.utilities.ExcelUtil;
import com.variables.Constant;

public class Driver 
{	
	private DriverFactory factory;
	private ExcelUtil excelUtil;
	private WebDriver driver = null;
	private ActionKeywords actionKeywords;
	
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
		//actionKeywords = new ActionKeywords();
	}
	
	@AfterTest
	public void afterTest()
	{
		//driver.close();
		//driver.quit();
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
				
				for( ; iTestCaseStartRowNum <= iTestCaseLastRowNum; iTestCaseStartRowNum++)
				{
					String testStepId = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.TESTSTEP_ID);
					String actionKeyword = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.ACTION_KEYWORD);
					String element = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.PAGE_OBJECT);
					String dataSet = excelUtil.getCellData(Constant.SHEET_TESTSTEPS, iTestCaseStartRowNum, Constant.DATA_SET);
					//System.out.println("TestCase Name : "+testCaseName+" TestStep Id : "+testStepId+" ActionKeyword : "+actionKeyword);
					executeAction(actionKeyword, element, dataSet);
				}
			}
		}		
	}//End executeTestCases()
	
	
	private void executeAction(String actionkeyword, String element, String dataSet)
	{
			try 
			{
				Method method = actionKeywords.getClass().getMethod(actionkeyword, String.class, String.class);
				method.invoke(actionKeywords, element, dataSet);
			} 
			catch (NoSuchMethodException | SecurityException |InvocationTargetException | IllegalAccessException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
