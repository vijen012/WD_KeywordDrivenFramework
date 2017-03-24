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

	//This method is to set the File path and to open the Excel file
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
	
	// Close Excel file
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

	// Set Excel Sheet based on sheet name
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

	
	// Return ExcelSheet object
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

	
	// Get Cell data based on row number and column number
	@Override
	public String getCellData(int rowNum, int columNum) 
	{
		String cellData="";
		boolean isCellEmptyORNull = false;
		try
		{
			Cell = ExcelSheet.getRow(rowNum).getCell(columNum);
			isCellEmptyORNull = isCellEmpty(Cell);
			if(!isCellEmptyORNull)
			{
				cellData = Cell.getStringCellValue().trim();
			}			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return cellData;
	}
	
	
	// Get cell data based on sheetname , row number and column number
	@Override
	public String getCellData(String excelSheetName, int rowNum, int columNum)
	{
		String cellData="";
		boolean isCellEmptyORNull = false;
		try
		{
			ExcelSheet = getExcelSheet(excelSheetName);
			Cell = ExcelSheet.getRow(rowNum).getCell(columNum);
			isCellEmptyORNull = isCellEmpty(Cell);
			if(!isCellEmptyORNull)
			{
				cellData = Cell.getStringCellValue().trim();
			}			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return cellData;
	}
	
	
	// Checking whether the cell is empty or not
	@SuppressWarnings("deprecation")
	@Override
	public boolean isCellEmpty(XSSFCell cell)
	{
		boolean isCellNull = false;
		try
		{
			if(cell == null || cell.getCellTypeEnum() == CellType.BLANK)
			{
				isCellNull = true;
			}			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return isCellNull;		
	}

	
	//Returning number of row from the specified excel sheet
	@Override
	public int getRowCount() 
	{
		int rowCount = 0;
		try
		{
			//rowCount = ExcelSheet.getLastRowNum() - ExcelSheet.getFirstRowNum();
			  rowCount = ExcelSheet.getPhysicalNumberOfRows();			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return rowCount;
	}

	//Returning number of row from the specified excel sheet	
	@Override
	public int getRowCount(String excelSheetName) 
	{
		int rowCount = 0;
		try
		{
			//rowCount = excelSheet.getLastRowNum() - ExcelSheet.getFirstRowNum();
			ExcelSheet = getExcelSheet(excelSheetName);
			rowCount = ExcelSheet.getPhysicalNumberOfRows();					
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return rowCount;
	}

	//Returning number of column from the specified excel sheet
	@Override
	public int getColumnCount() 
	{
		int columnCount = 0;
		try
		{
			//columnCount = ExcelSheet.getRow(0).getLastCellNum();
			columnCount = ExcelSheet.getRow(0).getPhysicalNumberOfCells();			
		}
		catch (Exception ex)
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return columnCount;
	}

	//Returning number of column from the specified excel sheet	
	@Override
	public int getColumnCount(String excelSheetName) 
	{
		int columnCount = 0;
		try
		{
			//columnCount = excelSheet.getRow(0).getLastCellNum();
			ExcelSheet = getExcelSheet(excelSheetName);
			columnCount = ExcelSheet.getRow(0).getPhysicalNumberOfCells();			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return columnCount;
	}
	
	//This method is to get the Row number of the test case
	//This methods takes three arguments(Test Case name , Column Number & Sheet name)
	@Override
	public int getTestCaseRowNumber(String testCaseName, int colNumber, String excelSheetName)
	{
		int testCaseRowNum = 0;
		try
		{
			for(int iRow = 1; iRow < getRowCount(excelSheetName); iRow++)
			{
				if(getCellData(excelSheetName, iRow, colNumber).equalsIgnoreCase(testCaseName))
				{
					testCaseRowNum = iRow;
					break;
				}
			}			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return testCaseRowNum;
	}
	
	
	//This method is to get the count of the test steps of test case
	//This method takes three arguments (Sheet name, Test Case Id & Test case row number)	
	@Override
	public int getTestStepsCount(String testCaseName, int iTestCaseStartRowNum, String excelSheetName)
	{
		int iTestCaseLastRowNum = 0;
		try
		{
			for( ; iTestCaseStartRowNum < getRowCount(excelSheetName); iTestCaseStartRowNum++)
			{
				if(!testCaseName.equalsIgnoreCase(getCellData(excelSheetName, iTestCaseStartRowNum, Constant.TESTCASE_ID)))
				{
					iTestCaseLastRowNum = iTestCaseStartRowNum - 1;
					break;
				}
			}			
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
		}
		return iTestCaseLastRowNum;
	}
		
}
