package com.interfaces;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface IExcelUtil 
{
	public void setExcelFile(String fileName);
	public void setExcelSheet(String sheetName);
	public XSSFSheet getExcelSheet(String sheetName);
	public String getCellData(int rowNum, int columNum);
	public String getCellData(XSSFSheet excelSheet, int rowNum, int columNum);
	public boolean isCellEmpty(XSSFCell Cell);
	public int getRowCount();
	public int getRowCount(XSSFSheet excelSheet);
	public int getColumnCount();
	public int getColumnCount(XSSFSheet excelSheet);
	public void closeExcel();
}
