package com.driverClass;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.factory.DriverFactory;
import com.utilities.ExcelUtil;
import com.variables.Constant;

public class Driver 
{	
	DriverFactory factory;
	ExcelUtil excelUtil;
	WebDriver driver = null;
	
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
		//driver = factory.getBrowserDriver(browserName);	
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
		excelUtil.setExcelFile("Test.xlsx");
		int rowCount = excelUtil.getRowCount(Constant.SHEET_TESTCASES);
		int columnCount = excelUtil.getColumnCount(Constant.SHEET_TESTCASES);
		//System.out.println("SheetName : "+excelSheet.getSheetName() + " || " + "Row Count : " +rowCount+ " || " + "Column Count : "+columnCount);
		for(int iRow = 1; iRow < rowCount; iRow++)
		{
			String testCaseName = excelUtil.getCellData(Constant.SHEET_TESTCASES, iRow, Constant.TESTCASE_ID);
			String testCaseRunMode = excelUtil.getCellData(Constant.SHEET_TESTCASES, iRow, Constant.RUNMODE);
			int testCaseRowNum = excelUtil.getTestCaseRowNumber(testCaseName, Constant.TESTCASE_ID, Constant.SHEET_TESTSTEPS);
			System.out.println("TestCase Name : "+testCaseName+" TestCaseRowNum : "+testCaseRowNum);
		}
	}
}
