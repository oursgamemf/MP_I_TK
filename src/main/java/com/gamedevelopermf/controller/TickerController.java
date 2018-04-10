/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import checkinputdata.DateChecker;
import com.sun.org.apache.xpath.internal.operations.Equals;
import static com.gamedevelopermf.controller.ManageExcel.getColNumFromTxt;
import static com.gamedevelopermf.controller.ManageExcel.getHeaderList;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import com.gamedevelopermf.model.DBtkEvo;
import org.apache.commons.io.FileUtils;
import static com.gamedevelopermf.controller.ManageExcel.getAllDataFromFile;
import java.io.FileWriter;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JTextField;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;
import static com.gamedevelopermf.controller.ManageExcel.getAllDataFromFile;
import static com.gamedevelopermf.controller.ManageExcel.getAllDataFromFile;
import static com.gamedevelopermf.controller.ManageExcel.getAllDataFromFile;

/**
 * @author emanuele
 */
public class TickerController {

    private static final String URL_TEST_CONN = "https://www.google.it";
    private static final String PATH_TO_CONFIG = "i_tk.config";
    private static final String PATH_TO_TEMP_CONFIG = "myTempFile.config";
    private static final String PATH_TO_CSV = "table.csv";
    private static final String PATH_TO_DWL = "myDwlFile.csv";
    private static final String PATH_TO_XLS = "renameME.xls";
    private static final String URL_TO_SRC_DATA = "https://query1.finance.yahoo.com/v7/finance/download/";//=PHAU.MI&a=05&b=20&c=2007&d=06&e=21&f=2016&g=m&ignore=.csv";
    private static final String FULL_TEST_URL = "http://real-chart.finance.yahoo.com/table.csv?s=PHAU.MI&a=05&b=20&c=2007&d=06&e=21&f=2016&g=m&ignore=.csv";
    // https://query1.finance.yahoo.com/v7/finance/download/PHAU.MI?period1=1463691956&period2=1495227956&interval=1mo&events=history&crumb=ZaS4/cgjQp5
    private static final String opSys = System.getProperty("os.name");
    private static final Path curPath = Paths.get(System.getProperty("user.dir"));
    private static final String insideFullPath = curPath.toString() + File.separator;//curPath.getParent().toString() + File.separator
    private static final String configFullPath = insideFullPath + PATH_TO_CONFIG;
    private static final String configTempFullPath = insideFullPath + PATH_TO_TEMP_CONFIG;

//    public TickerController(){
//        try {
//         
//        } catch (IOException ex) {
//            Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static String getInsideFullPath() {
        return insideFullPath;
    }

    public static String getConfigTempFullPath() {
        return configTempFullPath;
    }
    private static final String csvFullPath = insideFullPath + PATH_TO_CSV;
    private static final String dwlFullPath = insideFullPath + PATH_TO_DWL;
    private static final String outExlFullPath = insideFullPath + PATH_TO_XLS;

    public static String getDwlFullPath() {
        return dwlFullPath;
    }

    public static String getConfigFullPath() {
        return configFullPath;
    }

    public static String getCsvFullPath() {
        return csvFullPath;
    }

    public static String getTicker(String tk) {
        // Remove space outside words

        String newTk = tk.trim();
        return newTk;

    }

    public static Stock testYahooAPI() throws IOException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -100); // from 5 years ago

        Stock google = YahooFinance.get("PHAU.MI", from, to, Interval.MONTHLY);
        return google;
    }

    public static Stock yahooAPI_MONTH(String nameTk) throws IOException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        //from.add(Calendar.YEAR, -10); // from 5 years ago
        from.set(1800, 01, 01);
        //from.set(2016, 01, 01);
        /*
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(from.getTime().toString());
        System.out.println(to.getTime().toString());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
         */
        Stock google = YahooFinance.get(nameTk, from, to, Interval.MONTHLY);
        return google;
    }

    public static Stock yahooAPI_WEEK(String nameTk) throws IOException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        //from.add(Calendar.YEAR, -10); // from 5 years ago
        from.set(1800, 01, 01);
        //from.set(2016, 01, 01);
        /*
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(from.getTime().toString());
        System.out.println(to.getTime().toString());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
         */
        Stock google = YahooFinance.get(nameTk, from, to, Interval.WEEKLY);
        return google;
    }

    public static Stock yahooAPI_DAY(String nameTk) throws IOException {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        //from.add(Calendar.YEAR, -10); // from 5 years ago
        from.set(1800, 01, 01);
        //from.set(2016, 01, 01);
        /*
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(from.getTime().toString());
        System.out.println(to.getTime().toString());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
         */
        Stock google = YahooFinance.get(nameTk, from, to, Interval.DAILY);
        return google;
    }

    public static boolean getWebConnection() {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");
            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            //trying to retrieve data from the source. If there is no connection, this line will fail
            //Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addTKToDB(RowTicker TK, DBtkEvo dbInterface) {
        // Date sysDate, int dayNextUpDate
        // Campi del DB : id, tickerName, InsertDateWithMS, 
        return false;
    }

    // ManageFile
    public static ArrayList<Object> runMeAtStart() {
        //Linux
        //.getParent()
        ArrayList<Object> loadSet = new ArrayList<>();
        ArrayList<ArrayList<String>> configData = getAllDataFromFile(configFullPath, ';');
        DBtkEvo sessionDB = new DBtkEvo();
        sessionDB.setsDBname(configData.get(0).get(1));
        sessionDB.setsTable(configData.get(1).get(1));
        sessionDB.setsFieldTableCreate(configData.get(2).get(1));
        sessionDB.setQuery(configData.get(3).get(1));

        sessionDB.connectOrCreate();
        //sessionDB.dropTable(); // Remove it!!!
        sessionDB.createTable();

        // Second table
        sessionDB.setsFieldTableCreate(configData.get(4).get(1));
        //sessionDB.dropTable(configData.get(4).get(1)); // Remove it!!!
        Boolean testSecDB = sessionDB.createTable(configData.get(4).get(1), configData.get(5).get(1));

        loadSet.add(sessionDB);
        loadSet.add(configData.get(0).get(1));
        loadSet.add(configData.get(1).get(1));
        loadSet.add(configData.get(2).get(1));
        loadSet.add(configData.get(3).get(1));
        loadSet.add(configData.get(4).get(1));
        loadSet.add(configData.get(5).get(1));
        loadSet.add(configData.get(6).get(1));
        loadSet.add(configData.get(7).get(1));
        loadSet.add(configData.get(8).get(1));
        return loadSet;
    }

    public static ArrayList<RowTicker> getRowTickerArray(ArrayList<ArrayList<String>> datas) {
        // Return an Array whose elements are instances of the class RowTicker.

        ArrayList<RowTicker> myTicker = new ArrayList<>();
        ArrayList<String> headers = getHeaderList(datas);
        Integer colNumber = headers.size();
        for (int row = 1; datas.get(row) != null; row++) {

            RowTicker myRowTicker = new RowTicker();
            ////////////////////////////////////////////////////////////////////
            // Remove the second row of each case statement (but date one)
            ////////////////////////////////////////////////////////////////////
            for (String h : headers) {
                Integer col = getColNumFromTxt(h, datas);
                switch (h.toLowerCase().trim()) {
                    case "date":
                        String tempData = DateChecker.checkData(datas.get(row).get(col).toString());
                        Date dateVal = Date.valueOf(tempData);
                        myRowTicker.setDateTk(dateVal);
                        break;
                    case "open":
                        Double openVal = Double.valueOf(datas.get(row).get(col));
                        //openVal = openVal * Math.random();
                        myRowTicker.setOpenTk(openVal);
                        break;
                    case "high":
                        Double highVal = Double.valueOf(datas.get(row).get(col));
                        //highVal = highVal * Math.random();
                        myRowTicker.setHighTk(highVal);
                        break;
                    case "low":
                        Double lowVal = Double.valueOf(datas.get(row).get(col));
                        //lowVal = lowVal * Math.random();
                        myRowTicker.setLowTk(lowVal);
                        break;
                    case "close":
                        Double closeVal = Double.valueOf(datas.get(row).get(col));
                        //closeVal = closeVal * Math.random();
                        myRowTicker.setCloseTk(closeVal);
                        break;
                    case "volume":
                        Double volumeVal = Double.valueOf(datas.get(row).get(col));
                        //volumeVal = volumeVal * Math.random();
                        myRowTicker.setVolumeTk(volumeVal);
                        break;
                    case "adj close":
                        Double adjCloseVal = Double.valueOf(datas.get(row).get(col));
                        //adjCloseVal = adjCloseVal * Math.random();
                        myRowTicker.setAdjCloseTk(adjCloseVal);
                        break;
                }
            }

            myTicker.add(myRowTicker);
            try {
                datas.get(row + 1);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                break;
            }
        }
        return myTicker;
    }

    public static String makeURL(String tk) {
        // Compose full URL
        String url = URL_TO_SRC_DATA + tk;
        url = url + "PHAU.MI?period1=1182290400&period2=4085589600&interval=1d&events=history&crumb=ol06Q2/dGli";
        return url;
    }

    public static String makeURL(String tk, Date startDate, Date stopDate) throws UnsupportedEncodingException {
        // Compose full URL
        String url = URL_TO_SRC_DATA + tk;

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        String startYear = String.valueOf(startCal.get(Calendar.YEAR));
        String startMonth = String.valueOf(startCal.get(Calendar.MONTH));
        if (startMonth.length() == 1) {
            startMonth = "0" + startMonth;
        }
        String startDay = String.valueOf(startCal.get(Calendar.DAY_OF_MONTH));
        if (startDay.length() == 1) {
            startDay = "0" + startDay;
        }

        Calendar stopCal = Calendar.getInstance();
        stopCal.setTime(stopDate);
        String stopYear = String.valueOf(stopCal.get(Calendar.YEAR));
        String stopMonth = String.valueOf(stopCal.get(Calendar.MONTH));
        if (stopMonth.length() == 1) {
            stopMonth = "0" + stopMonth;
        }
        String stopDay = String.valueOf(stopCal.get(Calendar.DAY_OF_MONTH));
        if (stopDay.length() == 1) {
            stopDay = "0" + stopDay;
        }

        url = url + "&a=" + startDay + "&b=" + startMonth + "&c=" + startYear;
        url = url + "&d=" + stopDay + "&e=" + stopMonth + "&f=" + stopYear + "&g=m&ignore=.csv";
        url = java.net.URLEncoder.encode(url, "UTF-8");

        return url;
    }

    public static RowChoosenTks addTkChoosenInOBJ(DBtkEvo usingDB, String table, String ticker) {
        RowChoosenTks rct = new RowChoosenTks();
        RowChoosenTks rctAlreadyIn = usingDB.checkIfAlreadyIn(table, ticker);
        if ((rctAlreadyIn == null) || (!rctAlreadyIn.getTickerName().equalsIgnoreCase(ticker))) {
            Date a = new Date(Calendar.getInstance().getTime().getTime());
            java.sql.Date sysDate = new java.sql.Date(a.getTime());
            rct.setTickerName(ticker.trim());
            rct.setAutomaticRefresh(false);
            rct.setLastDownloadDate(sysDate);
            rct.setRefreshPeriod(10);
            return rct;
        }
        return rctAlreadyIn;
    }

    public static RowChoosenTks updateTkChoosenInOBJ(RowChoosenTks rct) {
        if (rct != null) {
            Date a = new Date(Calendar.getInstance().getTime().getTime());
            java.sql.Date sysDate = new java.sql.Date(a.getTime());
            rct.setLastDownloadDate(sysDate);
            // System.out.println("Date Updated");
            return rct;
        }
        return null;
    }

    public static void writeLine(FileWriter w, String[] values, char separators, char endLine) {

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
            sb.append(separators);
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(endLine);
        try {
            w.append(sb.toString());
        } catch (IOException ex) {
            Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean notNullInThisRow(String[] a) {
        for (String b : a) {
            if (b.equals("null")) {
                return false;
            }

        }
        return true;
    }

    public static List<String[]> getMatrixData(Stock tkStock) {

        //String[] mtStr = null;// {"asd","d","f","ff","vv"};
        List<String[]> tkDataMatrix = new ArrayList<String[]>();
        //tkDataMatrix.add(mtStr);
        try {
            List myTKList = tkStock.getHistory();
            String[] oneDate;
            for (Object a : myTKList) {
                oneDate = a.toString().split("[-,> ():]");
                tkDataMatrix.add(oneDate);
            }

        } catch (IOException ex) {
            System.out.println("error getting data");
        }

        List<String[]> outData = new ArrayList<String[]>();
        String[] temp = null;
        boolean passEmpty = false;
        int indexTmp = 1;
        while (!passEmpty) {
            if ((tkDataMatrix.get(indexTmp).length != 0) && notNullInThisRow(tkDataMatrix.get(indexTmp))) {
                temp = tkDataMatrix.get(indexTmp);
                passEmpty = true;
                break;
            }
            indexTmp++;
        }
        /*
        for (String[] a : tkDataMatrix.subList(indexTmp, tkDataMatrix.size())){
            System.out.println("--------------");
            for (String b : a){
                System.out.print(b + " ");
            }
            System.out.println("--------------");
        }
         */
        for (String[] a : tkDataMatrix.subList(indexTmp, tkDataMatrix.size())) {
            int counter = 0;

            List<String> row = new ArrayList<String>();
            for (String b : a) {

                if (b.equals("null") && (temp[counter].equals("null") || temp.equals(null))) {
                    b = "-1";
                }

                if (b.equals("null") && !temp.equals("null")) {
                    b = temp[counter];
                }

                if (counter == 0) {
                    b = b.replaceAll("[^\\d.]", "");
                    b = b.replaceAll("[.]", "");
                }
                if (b.matches(".*\\d+.*")) {
                    row.add(b);
                }
                if (!b.equals("null") && !temp.equals("null")) {
                    temp[counter] = b;

                }
                counter++;

            }
            String[] arrayRow = new String[]{row.get(0).toString() + "-" + row.get(1).toString() + "-" + row.get(2).toString(),
                row.get(5).toString(), row.get(4).toString(), row.get(3).toString(),
                row.get(6).toString(), "0", row.get(7).toString()};
            outData.add(arrayRow);

        }

        return outData;
    }

    public static boolean writeCSV(String filename, List<String[]> sourceData) {

        char DEFAULT_SEPARATOR = ',';
        try {
            FileWriter fw = new FileWriter(filename);
            String[] header = {"Date", "Open", "High", "Low", "Close", "Volume", "Adj close"};

            TickerController.writeLine(fw, header, DEFAULT_SEPARATOR, '\n');
            for (String[] st : sourceData) {
                TickerController.writeLine(fw, st, DEFAULT_SEPARATOR, '\n');
            }
            fw.close();
            ////////////
        } catch (IOException ex) {
            Logger.getLogger(TickerController.class
                    .getName()).log(Level.SEVERE, null, ex);
            // System.out.println("Unable to write CSV.");
            String outMsg = "Impossibile scrivere il file: \'" + "\'. Ticker non valido";
        }
        return false;
    }

    // public static void 
    public static void searchSaveTK(String fileUrl, String nameTK, JTextField txtField) {
        //Code to download
        InputStream input;
        String pathNameDwlCSV_MONTH = insideFullPath + nameTK.trim() + "_M.csv";
        String pathNameDwlCSV_WEEK = insideFullPath + nameTK.trim() + "_W.csv";
        String pathNameDwlCSV_DAY = insideFullPath + nameTK.trim() + "_D.csv";
        File myFile = new File(pathNameDwlCSV_MONTH);
        try {
            Stock myTKdatas_M = yahooAPI_MONTH(nameTK);
            List<String[]> outputdata_M = getMatrixData(myTKdatas_M);
            writeCSV(pathNameDwlCSV_MONTH, outputdata_M);
            
            Stock myTKdatas_W = yahooAPI_WEEK(nameTK);
            List<String[]> outputdata_W = getMatrixData(myTKdatas_W);
            writeCSV(pathNameDwlCSV_WEEK, outputdata_W);
            
            Stock myTKdatas_D = yahooAPI_DAY(nameTK);
            List<String[]> outputdata_D = getMatrixData(myTKdatas_D);
            writeCSV(pathNameDwlCSV_DAY, outputdata_D);

        } catch (IOException ex) {
            Logger.getLogger(TickerController.class
                    .getName()).log(Level.SEVERE, null, ex);
            // ("Unable to find this Ticker, correct it please.");
            String outMsg = "Impossibile scaricare il file: \'" + nameTK + "\'. Ticker non valido";
            OutputMessage.setOutputText(outMsg, txtField, 2);
        }

        try {
            URL myUrl = new URL(fileUrl);
            //input = myUrl.openStream();
            //FileUtils.copyURLToFile(myUrl, myFile);
            //Reader reader = new InputStreamReader(input, "UTF-8");

        } catch (MalformedURLException ex) {
            // Logger.getLogger(TickerController.class.getName()).log(Level.SEVERE, null, ex);
            // System.out.println("Unable to find this Ticker, correct it please.");
            String outMsg = "Impossibile scaricare il file: \'" + nameTK + "\'. Ticker non valido";
            OutputMessage.setOutputText(outMsg, txtField, 2);

        }

    }

    //
    public static Comparator<RowTicker> orederByData() {
        Comparator comp = new Comparator<RowTicker>() {
            @Override
            public int compare(RowTicker o1, RowTicker o2) {
                return o1.getDateTk().compareTo(o2.getDateTk());
            }
        };
        return comp;
    }

    //
    public static ArrayList<RowTicker> sortTicker(ArrayList<RowTicker> myTicker) {

        //Collections.sort(myTicker, (RowTicker tk1, RowTicker tk2) -> tk1.getDateTk().compareTo(tk2.getDateTk()));
        Collections.sort(myTicker, orederByData());
        return myTicker;
    }

    public static ArrayList<RowTicker> getQuarterlyTicker(ArrayList<RowTicker> myTicker) {

        ArrayList<RowTicker> myQuartTicker = new ArrayList<>();

        for (RowTicker myRowTk : myTicker) {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(myRowTk.getDateTk());
            int month = (calDate.get(Calendar.MONTH));
            if (month == 2 || month == 5 || month == 8 || month == 11) {
                myQuartTicker.add(myRowTk);
            }
        }
        /*
        myTicker.stream().forEach((myRowTk) -> {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(myRowTk.getDateTk());
            int month = (calDate.get(Calendar.MONTH));
            if (month == 2 || month == 5 || month == 8 || month == 11) {
                myQuartTicker.add(myRowTk);
            }
        });
         */
        return myQuartTicker;
    }

    public static ArrayList<RowTicker> getAnnualTicker(ArrayList<RowTicker> myTicker) {

        ArrayList<RowTicker> myAnnualTicker = new ArrayList<>();

        for (RowTicker myRowTk : myTicker) {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(myRowTk.getDateTk());
            int month = (calDate.get(Calendar.MONTH));
            if (month == 11) {
                myAnnualTicker.add(myRowTk);
            }
        }
        /*
        myTicker.stream().forEach((myRowTk) -> {
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(myRowTk.getDateTk());
            int month = (calDate.get(Calendar.MONTH));
            if (month == 11) {
                myAnnualTicker.add(myRowTk);
            }
        });
         */
        return myAnnualTicker;
    }

}
