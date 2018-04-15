/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import com.opencsv.CSVReader;
import static com.gamedevelopermf.controller.RowExcel.addSheet2Excel;
import static com.gamedevelopermf.controller.RowExcel.modifySheet2Excel;
import static com.gamedevelopermf.controller.TickerController.getRowTickerArray;//??
import static com.gamedevelopermf.controller.TickerController.getAnnualTicker;
import static com.gamedevelopermf.controller.TickerController.getQuarterlyTicker;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * @author emanuele.calabro
 */
public class ManageExcel {

    private static String inputFile;

    public static void setInputFile(String inputFile) {
        ManageExcel.inputFile = inputFile;
    }

    public static int getColNumFromTxt(String headerName, ArrayList<ArrayList<String>> datas) {
        //Return the index of the column whose header name is "headerName"

        int i = 0;
        for (i = 0; i < datas.get(0).size(); i++) {
            //System.out.println(datas.get(0).get(i));
            if (datas.get(0).get(i).toLowerCase().equals(headerName.toLowerCase())) {
                return i;
            }
        }
        return 9999;
    }

    public static HashSet<String> getListOfValue(int colSel, ArrayList<ArrayList<String>> datas) {
        // Return all values of a column (colSel) without repetitions

        HashSet<String> typeOf = new HashSet<>();
        for (int i = 1; datas.get(i).get(colSel) != null; i++) {
            //System.out.println(datas.get(i).get(colSel));
            typeOf.add(datas.get(i).get(colSel));
            try {
                datas.get(i + 1).get(colSel);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                return typeOf;
            }
        }
        return typeOf;
    }

    public static int getRowsData(ArrayList<ArrayList<String>> datas) {
        // Get number of rows in "datas" (header included)

        int i = 0;
        int retVal = 0;
        while ((datas.get(i).get(0) != null) && (!"".equals(datas.get(i).get(0)))) {
            retVal += 1;
        }
        return retVal;
    }

    public static ArrayList<ArrayList<String>> getAllDataFromFile(char sep) {
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        // Get datas from csv file to ArrayList of ArrayList
        try (CSVReader reader = new CSVReader(new FileReader(inputFile), sep);) {
            String[] nextLine;
            int numRow = 0;
            while ((nextLine = reader.readNext()) != null && numRow < 8000) {
                ArrayList<String> allRow = new ArrayList<>();
                allRow.addAll(Arrays.asList(nextLine));
                allData.add(allRow);
                numRow += 1;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return allData;
    }

    public static ArrayList<ArrayList<String>> getAllDataFromFile(String csvInputPath, char sep) {
        // Get datas from csv file to ArrayList of ArrayList
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csvInputPath)), sep, '"', '|');) {
            String[] nextLine;
            int numRow = 0;
            while ((nextLine = reader.readNext()) != null && numRow < 8000) {
                ArrayList<String> allRow = new ArrayList<>();
                allRow.addAll(Arrays.asList(nextLine));
                allData.add(allRow);
                numRow += 1;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return allData;
    }

    public static ArrayList<ArrayList<String>> getAllDataFromTKFile(String csvInputName, char sep) {
        String csvInputPath = TickerController.getInsideFullPath() + csvInputName + ".csv";
        // Get datas from csv file to ArrayList of ArrayList
        ArrayList<ArrayList<String>> allData = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvInputPath), sep);) {
            String[] nextLine;
            int numRow = 0;
            while ((nextLine = reader.readNext()) != null && numRow < 8000) {
                ArrayList<String> allRow = new ArrayList<>();
                allRow.addAll(Arrays.asList(nextLine));
                allData.add(allRow);
                numRow += 1;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(ManageExcel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return allData;
    }

    public static ArrayList<String> getHeaderList(ArrayList<ArrayList<String>> allData) {
        // Return an array storing each header (column name) of "allData"

        ArrayList<String> myHeader = new ArrayList<>();
        for (int i = 0; i < allData.get(0).size(); i++) {
            if ((allData.get(0).get(i) != null) && (!"".equals(allData.get(0).get(i)))) {
                myHeader.add(allData.get(0).get(i).toLowerCase().trim());
                // Comtempla caso più spazi tra "adj close"
            }
        }
        return myHeader;
    }

    public static String getColNameFromIndex(int index, ArrayList<ArrayList<String>> datas) {
        //Return the name of the column whose column index is "index"

        String columnHeaderName = datas.get(0).get(index);
        return columnHeaderName;
    }

    public void save(ArrayList<String[]> list, String pathSaveDwlCSV) {
        File file;
        String pathFileName = pathSaveDwlCSV; // Add TK name
        file = new File(pathFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create File " + e);
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < list.size(); i++) {
                String[] row = list.get(i);
                for (int j = 0; j < row.length; j++) {
                    writer.write(row[j]);
                    if (j != (row.length - 1)) {
                        writer.write(',');
                    } else {
                        writer.write('\n');
                    }
                }
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write to File " + e);
        }
    }

    public static void createExcel(ArrayList<RowTicker> myTickerDaily, ArrayList<RowTicker> myTickerWeekly, ArrayList<RowTicker> myTicker, String userSavePath, String fileName, JTextField txtField) {
        Workbook myWb = new XSSFWorkbook();
        CreationHelper myCreateHelper = myWb.getCreationHelper();

        String myDailiySheetName = "Giornaliero";
        addSheet2Excel(myWb, myCreateHelper, myDailiySheetName, myTickerDaily);

        String myWeeklySheetName = "Settimanale";
        addSheet2Excel(myWb, myCreateHelper, myWeeklySheetName, myTickerWeekly);

        String mySheetName = "Mensile";
        addSheet2Excel(myWb, myCreateHelper, mySheetName, myTicker);

        ArrayList<RowTicker> myQuarterTicker = getQuarterlyTicker(myTicker);
        String myQuarterSheetName = "Trimestrale";
        addSheet2Excel(myWb, myCreateHelper, myQuarterSheetName, myQuarterTicker);

        ArrayList<RowTicker> myAnnualTicker = getAnnualTicker(myTicker);
        String myAnnualSheetName = "Annuale";
        addSheet2Excel(myWb, myCreateHelper, myAnnualSheetName, myAnnualTicker);

        // Write the output to a file
        String outputFilePath = userSavePath + File.separator + fileName + ".xlsx";
        // System.out.println(outputFilePath);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(outputFilePath);
            myWb.write(fileOut);
            fileOut.close();
            OutputMessage.setOutputText("\'" + fileName + "\' salvato correttamente", txtField);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(ManageExcel.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Unable to write the file");
            String outMessage = "Impossibile scrivere il file: \'" + fileName + "\'";
            OutputMessage.setOutputText(outMessage, txtField, 2);
        } catch (IOException ex) {
            //Logger.getLogger(ManageExcel.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Unable to write the file I/O Ex");
            String outMessage = "Impossibile scrivere il file: \'" + fileName + "\'";
            OutputMessage.setOutputText(outMessage, txtField, 2);
        }

    }

    public static boolean checkIfExists(String fileName, String filePath) {
        boolean exists = false;

        String outputFilePath = filePath + File.separator + fileName + ".xlsx";

        // Create an abstract definition of configuration file to be read.
        File file = new File(outputFilePath);

        // If configuration file fileName does exist in the current returns true
        if (file.exists()) {
            exists = true;
        }

        return exists;
    }

    public static void modifyExcel(ArrayList<RowTicker> myTickerDaily, ArrayList<RowTicker> myTickerWeekly, ArrayList<RowTicker> myTicker, String userSavePath, String fileName, JTextField txtField) {

        String inputFilePath = userSavePath + File.separator + fileName + ".xlsx";

        FileInputStream file = null;
        XSSFWorkbook workbook = new XSSFWorkbook();
        //--//        
        ZipSecureFile.setMinInflateRatio(0);
        try {
            file = new FileInputStream(inputFilePath);
            workbook = new XSSFWorkbook(file);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found in ManageExcel - modifyExcel ");
            String outMessage = "Impossibile leggere il file: \'" + fileName + "\'";
            OutputMessage.setOutputText(outMessage, txtField, 2);
        } catch (IOException ex) {
            System.out.println("Workbook not found in ManageExcel - modifyExcel ");
            String outMessage = "Impossibile leggere il file: \'" + fileName + "\'";
            OutputMessage.setOutputText(outMessage, txtField, 2);
            Logger.getLogger(ManageExcel.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (EncryptedDocumentException ex) {
            Logger.getLogger(ManageExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CreationHelper myCrHelper = workbook.getCreationHelper();

        System.out.println("--> " + String.valueOf(workbook.getNumberOfSheets()));
        System.out.println("--> " + userSavePath + fileName);

        Sheet mySheet0 = workbook.getSheet("Giornaliero");
        modifySheet2Excel(workbook, myCrHelper, mySheet0, myTickerDaily);

        Sheet mySheet1 = workbook.getSheet("Settimanale");
        modifySheet2Excel(workbook, myCrHelper, mySheet1, myTickerWeekly);

        Sheet mySheet2 = workbook.getSheet("Mensile");
        modifySheet2Excel(workbook, myCrHelper, mySheet2, myTicker);

        Sheet mySheet3 = workbook.getSheet("Trimestrale");
        ArrayList<RowTicker> myQuarterTicker = getQuarterlyTicker(myTicker);
        modifySheet2Excel(workbook, myCrHelper, mySheet3, myQuarterTicker);

        Sheet mySheet4 = workbook.getSheet("Annuale");
        ArrayList<RowTicker> myAnnualTicker = getAnnualTicker(myTicker);
        modifySheet2Excel(workbook, myCrHelper, mySheet4, myAnnualTicker);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            
        try {
            file.close();
        } catch (IOException ex) {
            System.out.println("File not found in ManageExcel - modifyExcel - during Close ");
        }
         
        //
        File myFile = new File(inputFilePath);

            try (FileOutputStream outFile = new FileOutputStream(myFile)) {
                workbook.write(outFile);
                OutputMessage.setOutputText("\'" + fileName + "\' modificato correttamente", txtField);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write the file");
                String outMessage = "Impossibile modificare il file: \'" + fileName + "\'. Controllare che non sia aperto da un altro programma";
                OutputMessage.setOutputText(outMessage, txtField, 2);
            } catch (IOException e) {
                System.out.println("Unable to write the file I/O Ex");
                String outMessage = "Impossibile modificare il file: \'" + fileName + "\'. Controllare che non sia aperto da un altro programma";
                OutputMessage.setOutputText(outMessage, txtField, 2);
            }

    }

}
