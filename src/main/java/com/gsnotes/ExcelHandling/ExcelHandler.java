package com.gsnotes.ExcelHandling;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements export and import features from Excel
 * 
 * @author Tarik BOUDAA
 *
 */
public class ExcelHandler {

	/** the unique instance of this class */
	private static final ExcelHandler instance = new ExcelHandler();

	/**
	 * private constructor (singleton DP)
	 */
	private ExcelHandler() {
	}

	/**
	 * returns the unique instance of this class
	 */
	public static final ExcelHandler getInstance() {
		return instance;
	}

	/**
	 * Exports data to an excel file
	 * @param pFileName the file name
	 * @param sheetName the Excel sheet name to export
	 * @param pDataAndHeader the data to export (each line is a list of objects)
	 * @throws ExcelHandlerException
	 */
	public static void export(String pFileName, String sheetName, List<ArrayList<Object>> pDataAndHeader)
			throws ExcelHandlerException {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(sheetName);

		int rowNum = 0;

		for (ArrayList<Object> datatype : pDataAndHeader) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (Object field : datatype) {
				Cell cell = row.createCell(colNum++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				} else if (field instanceof Long) {
					cell.setCellValue((Long) field);
				}
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(pFileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception ex) {
			throw new ExcelHandlerException("Error while exporting and excel file", ex);
		}

	}


	/**
	 * Imports data from an excel file
	 * @param pFileName the file name
	 * @param sheet name the excel sheet name to create
	 * @return the data of the excel file as a list of list  (each line is a list of objects)
	 * @throws ExcelHandlerException
	 */
	public static List<ArrayList<Object>> readFromExcel(String pFileName, int pSheet) throws ExcelHandlerException {

		List<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

		try {
			Workbook workbook = null;
			try {
				FileInputStream excelFile = new FileInputStream(new File(pFileName));
				workbook = new XSSFWorkbook(excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(pSheet);
				Iterator<Row> iterator = datatypeSheet.iterator();

				while (iterator.hasNext()) {

					ArrayList<Object> rowValues = new ArrayList<Object>();

					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();

					while (cellIterator.hasNext()) {

						Cell currentCell = cellIterator.next();

						if (currentCell.getCellType() == CellType.STRING) {

							rowValues.add(currentCell.getStringCellValue());

						} else if (currentCell.getCellType() == CellType.NUMERIC) {
							rowValues.add(currentCell.getNumericCellValue());
						} else if (currentCell.getCellType() == CellType.FORMULA) {
							rowValues.add(currentCell.getNumericCellValue());
						}

					}

					data.add(rowValues);

				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception ex) {
			throw new ExcelHandlerException("Error while importing an excel file", ex);
		}

		return data;

	}

}
