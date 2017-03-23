package com.interfaces;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface IExcelUtil 
{
	public void setExcelFile(String fileName);
	public void setExcelSheet(String sheetName);
	public XSSFSheet getExcelSheet(String sheetName);
	public String getCellData(int rowNum, int columNum);
	public String getCellData(String excelSheetName, int rowNum, int columNum);
	public boolean isCellEmpty(XSSFCell Cell);
	public int getRowCount();
	public int getRowCount(String excelSheetName);
	public int getColumnCount();
	public int getColumnCount(String excelSheetName);
	public int getTestCaseRowNumber(String testCaseName, int colNumber, String excelSheetName);
	public void closeExcel();
}
