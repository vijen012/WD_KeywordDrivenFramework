package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.interfaces.IExcelUtil;
import com.variables.Constant;
import com.variables.GlobalVariables;

public class ExcelUtil implements IExcelUtil
{
	private static XSSFWorkbook ExcelWorkbook;
	private static XSSFSheet ExcelSheet;
	private static XSSFRow Row;
	private static XSSFCell Cell;

	@Override
	public void setExcelFile(String fileName) 
	{
		String excelFilePath = GlobalVariables.getProjectDirPath()+"\\src\\com\\testscript\\" + fileName;
		GlobalVariables.setExcelFilePath(excelFilePath);
		try 
		{
			File file = new File(excelFilePath);
			FileInputStream fileInputStream = new FileInputStream(file);			
			ExcelWorkbook = new XSSFWorkbook(fileInputStream);			
		} 
		catch (IOException ex) 
		{		
			ex.printStackTrace();
		}
	}
	
	@Override
	public void closeExcel()
	{
		try
		{
			if(ExcelWorkbook != null)
			{
				ExcelWorkbook.close();
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void setExcelSheet(String sheetName) 
	{
		try
		{
			ExcelSheet = ExcelWorkbook.getSheet(sheetName);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public XSSFSheet getExcelSheet(String sheetName) 
	{
		try
		{
			ExcelSheet = ExcelWorkbook.getSheet(sheetName);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return ExcelSheet;
	}

	@Override
	public String getCellData(int rowNum, int columNum) 
	{
		String cellData="";
		boolean isCellEmptyORNull = false;
		Cell = ExcelSheet.getRow(rowNum).getCell(columNum);
		isCellEmptyORNull = isCellEmpty(Cell);
		if(!isCellEmptyORNull)
		{
			cellData = Cell.getStringCellValue().trim();
		}
		return cellData;
	}
	
	@Override
	public String getCellData(String excelSheetName, int rowNum, int columNum)
	{
		String cellData="";
		boolean isCellEmptyORNull = false;
		ExcelSheet = getExcelSheet(excelSheetName);
		Cell = ExcelSheet.getRow(rowNum).getCell(columNum);
		isCellEmptyORNull = isCellEmpty(Cell);
		if(!isCellEmptyORNull)
		{
			cellData = Cell.getStringCellValue().trim();
		}
		return cellData;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean isCellEmpty(XSSFCell cell)
	{
		boolean isCellNull = false;
		if(cell == null || cell.getCellTypeEnum() == CellType.BLANK)
		{
			isCellNull = true;
		}
		return isCellNull;		
	}

	@Override
	public int getRowCount() 
	{
		//int rowCount = ExcelSheet.getLastRowNum() - ExcelSheet.getFirstRowNum();
		int rowCount = ExcelSheet.getPhysicalNumberOfRows();
		return rowCount;
	}

	@Override
	public int getRowCount(String excelSheetName) 
	{
		//int rowCount = excelSheet.getLastRowNum() - ExcelSheet.getFirstRowNum();
		ExcelSheet = getExcelSheet(excelSheetName);
		int rowCount = ExcelSheet.getPhysicalNumberOfRows();		
		return rowCount;
	}

	@Override
	public int getColumnCount() 
	{
		//int columnCount = ExcelSheet.getRow(0).getLastCellNum();
		int columnCount = ExcelSheet.getRow(0).getPhysicalNumberOfCells();
		return columnCount;
	}

	@Override
	public int getColumnCount(String excelSheetName) 
	{
		//int columnCount = excelSheet.getRow(0).getLastCellNum();
		ExcelSheet = getExcelSheet(excelSheetName);
		int columnCount = ExcelSheet.getRow(0).getPhysicalNumberOfCells();
		return columnCount;
	}
	
	//This method is to get the Row number of the test case
	//This methods takes three arguments(Test Case name , Column Number & Sheet name)
	@Override
	public int getTestCaseRowNumber(String testCaseName, int colNumber, String excelSheetName)
	{
		int testCaseRowNum = 0;
		for(int iRow = 1; iRow < getRowCount(excelSheetName); iRow++)
		{
			if(getCellData(excelSheetName, iRow, colNumber).equalsIgnoreCase(testCaseName))
			{
				testCaseRowNum = iRow;
				break;
			}
		}
		return testCaseRowNum;
	}
	
	
	//This method is to get the count of the test steps of test case
	//This method takes three arguments (Sheet name, Test Case Id & Test case row number)	
	@Override
	public int getTestStepsCount(String testCaseName, int iTestCaseStartRowNum, String excelSheetName)
	{
		int iTestCaseLastRowNum = 0;
		for( ; iTestCaseStartRowNum < getRowCount(excelSheetName); iTestCaseStartRowNum++)
		{
			if(!testCaseName.equalsIgnoreCase(getCellData(excelSheetName, iTestCaseStartRowNum, Constant.TESTCASE_ID)))
			{
				iTestCaseLastRowNum = iTestCaseStartRowNum - 1;
				break;
			}
		}
		return iTestCaseLastRowNum;
	}
		
}
