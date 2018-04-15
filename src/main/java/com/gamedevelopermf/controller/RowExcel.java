/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import java.util.ArrayList;
import java.util.Calendar;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author virginia
 */
public class RowExcel {

    public static void setExcelHeader(Row myHeaderRow) {
        myHeaderRow.createCell(0).setCellValue("Date");
        myHeaderRow.createCell(1).setCellValue("Open");
        myHeaderRow.createCell(2).setCellValue("High");
        myHeaderRow.createCell(3).setCellValue("Low");
        myHeaderRow.createCell(4).setCellValue("Adj Close");

    }

    public static void addRow2Excel(Workbook myWb, CreationHelper myCreateHelper, Row myRow, RowTicker myRowTk, boolean isLastRow) {
        Cell myCell = myRow.createCell(0);
        CellStyle cellStyle = myWb.createCellStyle();
        cellStyle.setDataFormat(myCreateHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        if (isLastRow) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(myRowTk.getDateTk());
            if (cal.get(Calendar.DAY_OF_MONTH) != cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            } else {
                myCell.setCellValue(myRowTk.getDateTk());
                myCell.setCellStyle(cellStyle);
                myRow.createCell(1).setCellValue(myRowTk.getOpenTk());
                myRow.createCell(2).setCellValue(myRowTk.getHighTk());
                myRow.createCell(3).setCellValue(myRowTk.getLowTk());
                myRow.createCell(4).setCellValue(myRowTk.getAdjCloseTk());
            }

        } else {
            myCell.setCellValue(myRowTk.getDateTk());
            myCell.setCellStyle(cellStyle);
            myRow.createCell(1).setCellValue(myRowTk.getOpenTk());
            myRow.createCell(2).setCellValue(myRowTk.getHighTk());
            myRow.createCell(3).setCellValue(myRowTk.getLowTk());
            myRow.createCell(4).setCellValue(myRowTk.getAdjCloseTk());
        }

    }

    public static void addSheet2Excel(Workbook myWb, CreationHelper myCrHelper, String sheetName, ArrayList<RowTicker> myTicker) {
        Sheet mySheet = myWb.createSheet(sheetName);
        Row myHeaderRow = mySheet.createRow(0);
        setExcelHeader(myHeaderRow);
        int lastRow = 1;
        int rowsNum = myTicker.size();
        boolean isLast;
        for (RowTicker myRowTk : myTicker) {
            isLast = lastRow == rowsNum;
            Row myRow = mySheet.createRow(lastRow);
            addRow2Excel(myWb, myCrHelper, myRow, myRowTk, isLast);
            lastRow = lastRow + 1;
        }
        mySheet.autoSizeColumn(0, false);
    }

    public static void modifyRow2Excel(Workbook myWb, CreationHelper myCrHelper, RowTicker myRowTk, Row myRow, boolean isLastRow) {
        Cell myCell;

        CellStyle cellStyle = myWb.createCellStyle();
        cellStyle.setDataFormat(myCrHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        if (isLastRow) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(myRowTk.getDateTk());
            if (cal.get(Calendar.DAY_OF_MONTH) != cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            } else {
                myCell = myRow.createCell(0);
                myCell.setCellValue(myRowTk.getDateTk());
                myCell.setCellStyle(cellStyle);

                myCell = myRow.createCell(1);
                myCell.setCellValue(myRowTk.getOpenTk());

                myCell = myRow.createCell(2);
                myCell.setCellValue(myRowTk.getHighTk());

                myCell = myRow.createCell(3);
                myCell.setCellValue(myRowTk.getLowTk());

                myCell = myRow.createCell(4);
                myCell.setCellValue(myRowTk.getAdjCloseTk());
            }
        } else {
            myCell = myRow.createCell(0);
            myCell.setCellValue(myRowTk.getDateTk());
            myCell.setCellStyle(cellStyle);

            myCell = myRow.createCell(1);
            myCell.setCellValue(myRowTk.getOpenTk());

            myCell = myRow.createCell(2);
            myCell.setCellValue(myRowTk.getHighTk());

            myCell = myRow.createCell(3);
            myCell.setCellValue(myRowTk.getLowTk());

            myCell = myRow.createCell(4);
            myCell.setCellValue(myRowTk.getAdjCloseTk());
        }
    }

    public static void modifySheet2Excel(Workbook myWb, CreationHelper myCrHelper, Sheet mySheet, ArrayList<RowTicker> myTicker) {

        int row = 1;
        int rowsNum = myTicker.size();
        //System.out.println("--> " + String.valueOf(rowsNum));
        //System.out.println("--> " + mySheet.getSheetName());
        //System.out.println("--> " +mySheet.getLastRowNum());
        boolean isLast;
        for (RowTicker myRowTk : myTicker) {
            isLast = row == rowsNum;
            Row myRow = mySheet.getRow(row);
            if (myRow == null || myRow.getCell(0) == null || myRow.getCell(1) == null || myRow.getCell(2) == null || myRow.getCell(3) == null) {
                myRow = mySheet.createRow(row);
                addRow2Excel(myWb, myCrHelper, myRow, myRowTk, isLast);
            } else {
                modifyRow2Excel(myWb, myCrHelper, myRowTk, myRow, isLast);
            }
            row += 1;
        }
        // Test
        while ((mySheet.getRow(row) != null)) {
            if ((mySheet.getRow(row).getCell(0)) == null) {
                break;
            } else {
                mySheet.getRow(row).getCell(0).setCellValue("");
                mySheet.getRow(row).getCell(1).setCellValue("");
                mySheet.getRow(row).getCell(2).setCellValue("");
                mySheet.getRow(row).getCell(3).setCellValue("");
                row += 1;
            }
        }
        // end test
        mySheet.autoSizeColumn(0, false);
    }

}
