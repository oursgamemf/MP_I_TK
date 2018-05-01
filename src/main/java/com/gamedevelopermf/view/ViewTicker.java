/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.view;

import com.gamedevelopermf.controller.ManageExcel;
import static com.gamedevelopermf.controller.ManageExcel.checkIfExists;
import com.gamedevelopermf.controller.RowTicker;
import com.gamedevelopermf.controller.TickerController;
import static com.gamedevelopermf.controller.TickerController.getRowTickerArray;
import static com.gamedevelopermf.controller.TickerController.runMeAtStart;
import java.io.IOException;
import java.util.ArrayList;
import com.gamedevelopermf.model.DBtkEvo;
import static com.gamedevelopermf.controller.ManageExcel.getAllDataFromTKFile;
import com.gamedevelopermf.controller.OutputMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import com.gamedevelopermf.controller.RowChoosenTks;
import com.gamedevelopermf.controller.SelfDownloadCaller;
import static com.gamedevelopermf.controller.TickerController.getRowTickerArray_DIV;
import static com.gamedevelopermf.controller.TickerController.sortTicker;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.sql.Date;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author virginia
 */
public class ViewTicker extends javax.swing.JFrame {

    public static TickerController tkC = new TickerController();
    public static String outputExcelFile = "none";
    public static String choosedTKTable = "";
    public static DBtkEvo myStmtDB = null;
    public static String queryToInsChTK = "";
    public static JTable myTable;

    /**
     * Creates new form ViewTicker
     */
    public ViewTicker() {
        // UI setting at Start

        // Inizialize data from config file: Create or COnn DB - Create two tables return the list of config data.
        myStmtDB = (DBtkEvo) runMeAtStart().get(0);
        ArrayList<String> setList = new ArrayList<>();
        ArrayList<Object> subSetList = runMeAtStart();
        for (int ii = 1; ii < runMeAtStart().size(); ii++) {
            setList.add((String) subSetList.get(ii));
        } // From here setList has all config data
        outputExcelFile = setList.get(8);
        //RowTicker rrt = myStmtDB.getAllFromDBData(); // get data from DB

        // Set source/target table for choosed TK
        choosedTKTable = setList.get(4);

        // Query Insert data in CH TK table
        queryToInsChTK = setList.get(6);

        // Initialize the UI
        initComponents();

        // Enable or disable the search Button
        // String pathTKsaved = setList.get(8);
        //buttonEnabling(pathTKsaved);
        buttonEnabling();

        // set Table Model
        myTable = setTableAtStart();
        myTable.getDefaultEditor(String.class).addCellEditorListener(ChangeNotification);

        // fill Table Model
        fillTableFromDB(choosedTKTable, myStmtDB, myTable);

        SelfDownloadCaller sdc = new SelfDownloadCaller("Update Called", 100, choosedTKTable, myStmtDB, this);

    }

    public void write2configFile(String selectedPath) throws FileNotFoundException, IOException {
        File inputFile = new File(TickerController.getConfigFullPath());
        File tempFile = new File(TickerController.getConfigTempFullPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8")) //BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                ) {

            String lineToReplace = "savedTickerPath=";
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                String[] rowElement = trimmedLine.split(";");
                if (rowElement[0].equals(lineToReplace)) {
                    writer.write(rowElement[0] + ";" + selectedPath + ";" + System.getProperty("line.separator"));
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }
        }
        //BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        System.out.println(successful);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane4 = new javax.swing.JSplitPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jSplitPane5 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jSplitPane6 = new javax.swing.JSplitPane();
        jButton4 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jButton1.setText("Sel. Cartella Destinazione");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jSplitPane4.setLeftComponent(jButton1);

        jButton2.setText("Avvia scaricamento");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jSplitPane4.setRightComponent(jButton2);
        jButton2.getAccessibleContext().setAccessibleName("Avvia_scaricamento");

        jSplitPane3.setRightComponent(jSplitPane4);

        jTextField1.setText("e.g.PHAU.MI");
        jSplitPane3.setLeftComponent(jTextField1);

        jSplitPane2.setTopComponent(jSplitPane3);

        jSplitPane5.setDividerLocation(200);
        jSplitPane5.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jButton3.setText("Scarica Ticker Selezionato nella Tabella");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jSplitPane1.setTopComponent(jButton3);
        jSplitPane1.setRightComponent(jScrollPane2);

        jScrollPane1.setViewportView(jSplitPane1);

        jSplitPane5.setTopComponent(jScrollPane1);

        jSplitPane6.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane6.setMaximumSize(new java.awt.Dimension(2147483647, 80));
        jSplitPane6.setMinimumSize(new java.awt.Dimension(700, 80));
        jSplitPane6.setPreferredSize(new java.awt.Dimension(700, 80));

        jButton4.setText("Rimuovi Ticker Selezionato");
        jButton4.setAlignmentY(0.0F);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jSplitPane6.setTopComponent(jButton4);

        jTextField3.setEditable(false);
        jTextField3.setAlignmentY(0.0F);
        jTextField3.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        jTextField3.setMinimumSize(new java.awt.Dimension(700, 30));
        jTextField3.setPreferredSize(new java.awt.Dimension(700, 30));
        jSplitPane6.setRightComponent(jTextField3);

        jSplitPane5.setRightComponent(jSplitPane6);

        jSplitPane2.setRightComponent(jSplitPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        OutputMessage.setOutputText("", jTextField3);
        // TODO add your handling code here:
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //In response to a button click:
        int result = fc.showOpenDialog(jButton1);

        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fc.getSelectedFile().getAbsolutePath();
            System.out.println("Open was selected: " + selectedPath);
            try {
                write2configFile(selectedPath);
                outputExcelFile = selectedPath;

            } catch (IOException ex) {
                Logger.getLogger(ViewTicker.class.getName()).log(Level.SEVERE, null, ex);
            }
            jButton2.setEnabled(true);
        } else if (result == JFileChooser.CANCEL_OPTION) {
            String selectedPath = fc.getCurrentDirectory().getAbsolutePath();
            System.out.println("Cancel was selected: " + "none");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private JTable setTableAtStart() {
        String[] columnNames = {"Codice", "Ultimo Download", "Auto-Download", "Aggiorna ogni: [gg]"};
        Object[][] data = {};
//            {null, null, null, null}
//        };
        JTable table = new javax.swing.JTable();
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(column == 0 || column == 1);
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setVisible(true);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.getViewport().add(table);
        table.setFillsViewportHeight(true);

        table.putClientProperty("terminateEditOnFocusLost", true);
        return table;
    }

//    private JTable recreateUpdatedTable(String tableDBName, DBtkEvo myDB) {
//        ResultSet rs = myDB.getAllRowChoosenDBDataRS(tableDBName);
//        JTable table = new JTable();
//        try {
//            table = new JTable(myDB.buildTableModel(rs));
//        } catch (SQLException ex) {
//            System.out.println("Unable to reach the DB");
//        }
//        return table;
//    }
    private void fillTableFromDB(String tableDBName, DBtkEvo myDB, JTable tableUI) {
        ArrayList<RowChoosenTks> allChosenTKs = myDB.getAllRowChoosenDBData(tableDBName);
        DefaultTableModel modelDef = (DefaultTableModel) tableUI.getModel();
        int rowCount = modelDef.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            modelDef.removeRow(i);
        }
        int ii = 0;
        for (RowChoosenTks tcTK : allChosenTKs) {
            Object[][] data = {
                {null, null, null, null, null}
            };
            modelDef.addRow(data);

            tableUI.getModel().setValueAt(tcTK.getTickerName(), ii, 0);
            tableUI.getModel().setValueAt(tcTK.getLastDownloadDate(), ii, 1);
            tableUI.getModel().setValueAt(tcTK.getAutomaticRefresh(), ii, 2);
            tableUI.getModel().setValueAt(tcTK.getRefreshPeriod(), ii, 3);

            ii += 1;

        }

        //column = tableUI.getColumnModel().getColumn(i);
    }


    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // avvia scaricamento
        // TODO add your handling code here:
        OutputMessage.setOutputText("", jTextField3);
        String tickerName = jTextField1.getText().trim().toUpperCase();
        downloadTicker(tickerName);
        
        // elabAllCSVinFolder();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        // TODO add your handling code here:
        String[] Tks2Remove = removeRowFromTable(myTable);
        for (String tk : Tks2Remove) {
            myStmtDB.delChoosenTKrow(choosedTKTable, tk);
        }
        OutputMessage.setOutputText("", jTextField3);
        fillTableFromDB(choosedTKTable, myStmtDB, myTable);


    }//GEN-LAST:event_jButton4ActionPerformed

    private void elabAllCSVinFolder() {
        String path = TickerController.getInsideFullPath();
        System.out.println(path);
        File dir = new File(path);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String temp = name.toLowerCase();
                if (temp.endsWith("_w.csv")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        for (File f : files) {
            String tkName = f.getName().substring(0, f.getName().length()-6);
            System.out.println(tkName);
            // get Ticker object with daily datas
            ArrayList<ArrayList<String>> data_D = getAllDataFromTKFile(tkName + "_D", ',');
            System.out.println(tkName + "_D");
            ArrayList<RowTicker> myTicker_D = getRowTickerArray(data_D);
            ArrayList<RowTicker> mySortedTicker_D = sortTicker(myTicker_D);
            // get Ticker object with weekly datas
            ArrayList<ArrayList<String>> data_W = getAllDataFromTKFile(tkName + "_W", ',');
            ArrayList<RowTicker> myTicker_W = getRowTickerArray(data_W);
            ArrayList<RowTicker> mySortedTicker_W = sortTicker(myTicker_W);
            // get Ticker object with monthly datas
            ArrayList<ArrayList<String>> data = getAllDataFromTKFile(tkName, ',');
            ArrayList<RowTicker> myTicker = getRowTickerArray(data);
            ArrayList<RowTicker> mySortedTicker = sortTicker(myTicker);
            // get Ticker object with dividends datas
            ArrayList<RowTicker> mySortedTicker_DIV = null;
            if(TickerController.getTCDividend()==true){
                ArrayList<ArrayList<String>> data_DIV = getAllDataFromTKFile(tkName + "_DIV", ',');
                ArrayList<RowTicker> myTicker_DIV = getRowTickerArray_DIV(data_DIV);
                mySortedTicker_DIV = sortTicker(myTicker_DIV);
            }
            
            // Add Choosen TK
            ArrayList<RowChoosenTks> information = new ArrayList<>();
            RowChoosenTks myRowCh = TickerController.addTkChoosenInOBJ(myStmtDB, choosedTKTable, tkName);
            information.add(myRowCh);
            Boolean allIn = myStmtDB.insRowChoosenTKinDB(information, queryToInsChTK, choosedTKTable);
            if (allIn) {
                // If data are correctly setted in the DB add the Row Into the table
            }
            boolean fileAlreadyExists = checkIfExists(tkName, outputExcelFile);
            if (fileAlreadyExists) {
                ManageExcel.modifyExcel(mySortedTicker_DIV, mySortedTicker_D, mySortedTicker_W, mySortedTicker, outputExcelFile, tkName, jTextField3);
            } else {
                ManageExcel.createExcel(mySortedTicker_DIV, mySortedTicker_D, mySortedTicker_W, mySortedTicker, outputExcelFile, tkName, jTextField3);
                //TickerController.addTkChoosenInOBJ();
            }
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        OutputMessage.setOutputText("", jTextField3);
        String[] selTks = getTickersFromTable(myTable);
        for (String tks : selTks) {
            downloadTicker(tks);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void buttonEnabling() {
        if (outputExcelFile.equals("none")) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
    }

    private void buttonEnabling(String pathConfigFile) {
        if (pathConfigFile.equals("none")) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
    }

    public void downloadTicker(String tkName) {
        Boolean isWebConn = TickerController.getWebConnection();
        if (isWebConn) {
            String myTKs = TickerController.makeURL(tkName);
            TickerController.searchSaveTK(myTKs, tkName, jTextField3);
            // get Ticker object with daily datas
            ArrayList<ArrayList<String>> data_D = getAllDataFromTKFile(tkName + "_D", ',');
            System.out.println(tkName + "_D");
            ArrayList<RowTicker> myTicker_D = getRowTickerArray(data_D);
            ArrayList<RowTicker> mySortedTicker_D = sortTicker(myTicker_D);
            // get Ticker object with weekly datas
            ArrayList<ArrayList<String>> data_W = getAllDataFromTKFile(tkName + "_W", ',');
            ArrayList<RowTicker> myTicker_W = getRowTickerArray(data_W);
            ArrayList<RowTicker> mySortedTicker_W = sortTicker(myTicker_W);
            // get Ticker object with monthly datas
            ArrayList<ArrayList<String>> data = getAllDataFromTKFile(tkName, ',');
            ArrayList<RowTicker> myTicker = getRowTickerArray(data);
            ArrayList<RowTicker> mySortedTicker = sortTicker(myTicker);
            // get Ticker object with dividends datas
            ArrayList<RowTicker> mySortedTicker_DIV = null;
            if(TickerController.getTCDividend()==true){
                ArrayList<ArrayList<String>> data_DIV = getAllDataFromTKFile(tkName + "_DIV", ',');
                ArrayList<RowTicker> myTicker_DIV = getRowTickerArray_DIV(data_DIV);
                mySortedTicker_DIV = sortTicker(myTicker_DIV);
            }
            // Add Choosen TK
            ArrayList<RowChoosenTks> information = new ArrayList<>();
            RowChoosenTks myRowCh = TickerController.addTkChoosenInOBJ(myStmtDB, choosedTKTable, tkName);
            information.add(myRowCh);
            Boolean allIn = myStmtDB.insRowChoosenTKinDB(information, queryToInsChTK, choosedTKTable);
            if (allIn) {
                // If data are correctly setted in the DB add the Row Into the table
            }
            // end
//            ManageExcel.createExcel(myTicker, outputExcelFile, tickerName);
            boolean fileAlreadyExists = checkIfExists(tkName, outputExcelFile);
            if (fileAlreadyExists) {
                ManageExcel.modifyExcel(mySortedTicker_DIV, mySortedTicker_D, mySortedTicker_W, mySortedTicker, outputExcelFile, tkName, jTextField3);
            } else {
                ManageExcel.createExcel(mySortedTicker_DIV, mySortedTicker_D, mySortedTicker_W, mySortedTicker, outputExcelFile, tkName, jTextField3);
                //TickerController.addTkChoosenInOBJ();
            }

        } else {
            System.out.println("Controllare la connessione ad internet");
            String outMsg = "Impossibile scaricare il file: \'" + tkName + "\' . Controllare la connessione ad internet";
            OutputMessage.setOutputText(outMsg, jTextField3, 2);
        }
        fillTableFromDB(choosedTKTable, myStmtDB, myTable); // Da spostare!!!
    }

    public void downloadTickerForUpdate(String tkName) {
        Boolean isWebConn = TickerController.getWebConnection();
        if (isWebConn) {
            String myTKs = TickerController.makeURL(tkName);
            TickerController.searchSaveTK(myTKs, tkName, jTextField3);
            // GEt ticker with daily data
            ArrayList<ArrayList<String>> data_D = getAllDataFromTKFile(tkName + "_D", ',');
            ArrayList<RowTicker> myTicker_D = getRowTickerArray(data_D);
            ArrayList<RowTicker> mySortedTicker_D = sortTicker(myTicker_D);
            // GEt ticker with weekly data
            ArrayList<ArrayList<String>> data_W = getAllDataFromTKFile(tkName + "_W", ',');
            ArrayList<RowTicker> myTicker_W = getRowTickerArray(data_W);
            ArrayList<RowTicker> mySortedTicker_W = sortTicker(myTicker_W);
            // Get ticker with monthly data
            ArrayList<ArrayList<String>> data = getAllDataFromTKFile(tkName, ',');
            ArrayList<RowTicker> myTicker = getRowTickerArray(data);
            ArrayList<RowTicker> mySortedTicker = sortTicker(myTicker);
            // GEt ticker with weekly data
            ArrayList<RowTicker> mySortedTicker_DIV = null;
            if(TickerController.getTCDividend()==true){
                ArrayList<ArrayList<String>> data_DIV = getAllDataFromTKFile(tkName + "_DIV", ',');
                ArrayList<RowTicker> myTicker_DIV = getRowTickerArray_DIV(data_DIV);
                mySortedTicker_DIV = sortTicker(myTicker_DIV);
            }
            // Add Choosen TK
            //get il TK
            RowChoosenTks rct = myStmtDB.getRowChoosenDBData(choosedTKTable, tkName);
            // Set new date
            RowChoosenTks myRowCh = TickerController.updateTkChoosenInOBJ(rct);
            // Save TK in DB
            ArrayList<RowChoosenTks> information = new ArrayList<>();
            information.add(myRowCh);
            Boolean allIn = myStmtDB.insRowChoosenTKinDB(information, queryToInsChTK, choosedTKTable);
            // Update last download
            boolean fileAlreadyExists = checkIfExists(tkName, outputExcelFile);
            if (fileAlreadyExists) {
                ManageExcel.modifyExcel(mySortedTicker_DIV, mySortedTicker_D, mySortedTicker_W, mySortedTicker, outputExcelFile, tkName, jTextField3);
            } else {
                ManageExcel.createExcel(mySortedTicker_DIV, mySortedTicker_D, mySortedTicker_W, mySortedTicker, outputExcelFile, tkName, jTextField3);
                //TickerController.addTkChoosenInOBJ();
            }

        } else {
            System.out.println("Controllare la connessione ad internet");
            String outMsg = "Impossibile scaricare il file: \'" + tkName + "\' . Controllare la connessione ad internet";
            OutputMessage.setOutputText(outMsg, jTextField3, 2);
        }
        fillTableFromDB(choosedTKTable, myStmtDB, myTable); // Da spostare!!!
    }

    public static String[] getTickersFromTable(JTable tableUI) {

        int selRows[] = tableUI.getSelectedRows();
        String tkName[] = new String[selRows.length];
        int index = 0;
        for (int row : selRows) {
            DefaultTableModel model = (DefaultTableModel) tableUI.getModel();
            int tkCol = tableUI.getColumnModel().getColumnIndex("Codice");
            tkName[index] = model.getValueAt(row, tkCol).toString();
            index = index + 1;
        }
        return tkName;
    }

    public String[] removeRowFromTable(JTable tableUI) {
        int selRows[] = tableUI.getSelectedRows();
        String tks2Remove[] = new String[selRows.length];
        int tkCol = tableUI.getColumnModel().getColumnIndex("Codice");

        int invCounter = 0;
        for (int row : selRows) {
            row -= invCounter;
            DefaultTableModel model = (DefaultTableModel) tableUI.getModel();
            tks2Remove[invCounter] = model.getValueAt(row, tkCol).toString();
            model.removeRow(row);
            invCounter += 1;
        }
        return tks2Remove;
    }

    CellEditorListener ChangeNotification = new CellEditorListener() {
        public void editingCanceled(ChangeEvent e) {
            fillTableFromDB(choosedTKTable, myStmtDB, myTable);
            OutputMessage.setOutputText("The user canceled editing", jTextField3);
        }

        public void editingStopped(ChangeEvent e) {
            int[] cols = myTable.getSelectedColumns();
            int[] rows = myTable.getSelectedRows();
            if ((cols.length > 1) || (rows.length > 1)) {
                OutputMessage.setOutputText("Selezionare una sola riga durante l'editing", jTextField3, 1);
                fillTableFromDB(choosedTKTable, myStmtDB, myTable);
                //System.out.println("Selezionare una sola riga durante l'editing");
            } else {
                int col = cols[0];
                int row = rows[0];
                // Inserite i dati della riga nell'oggetto
                RowChoosenTks newRowChoosTks = createRCTfromRowint(row, col);
                if (newRowChoosTks == null) {
                    fillTableFromDB(choosedTKTable, myStmtDB, myTable);
                    return;
                }
                // Cancellare la vecchia riga
                int tkCol = myTable.getColumnModel().getColumnIndex("Codice");
                String tks2Remove = myTable.getModel().getValueAt(row, tkCol).toString();
                DefaultTableModel model = (DefaultTableModel) myTable.getModel();
                model.removeRow(row);
                myStmtDB.delChoosenTKrow(choosedTKTable, tks2Remove);
                // Inserire quella nuova
                ArrayList<RowChoosenTks> information = new ArrayList<>();
                information.add(newRowChoosTks);
                Boolean allIn = myStmtDB.insRowChoosenTKinDB(information, queryToInsChTK, choosedTKTable);
                fillTableFromDB(choosedTKTable, myStmtDB, myTable);
            }
        }
    };

    private RowChoosenTks createRCTfromRowint(int modifiedRow, int modifiedCol) {
        String colName = myTable.getColumnModel().getColumn(modifiedCol).toString();
        //System.out.println("riga: " + modifiedCol);
        //System.out.println("colonna: " + modifiedRow);

        // Creare un nuovo oggetto RowChoosenTks
        RowChoosenTks newRowChoosTks = new RowChoosenTks();

        // Inserite i dati della riga nell'oggetto
        String nameTk = myTable.getModel().getValueAt(modifiedRow, 0).toString();
        newRowChoosTks.setTickerName(nameTk);

        String dateTkSt = myTable.getModel().getValueAt(modifiedRow, 1).toString();
        Date dateTk = Date.valueOf(dateTkSt);
        newRowChoosTks.setLastDownloadDate(dateTk);

        String refTkSt = myTable.getModel().getValueAt(modifiedRow, 2).toString().toLowerCase();
        Boolean autRef = false;
        if (refTkSt.trim().equals("true")) {
            autRef = true;
            newRowChoosTks.setAutomaticRefresh(autRef);
        } else if (refTkSt.trim().equals("false")) {
            autRef = false;
            newRowChoosTks.setAutomaticRefresh(autRef);
        } else {
            String outMsg2 = "Update annullato: nella colonna \'Auto-Download\' è possibile inserire solo i valori \'true\' o \'false\'";
            OutputMessage.setOutputText(outMsg2, jTextField3, 1);
            //System.out.println(outMsg2);
            return null;
        }
        String perTkSt = myTable.getModel().getValueAt(modifiedRow, 3).toString().toLowerCase().trim();
        try {
            int perTk = Integer.parseInt(perTkSt);
            newRowChoosTks.setRefreshPeriod(perTk);
        } catch (NumberFormatException notNumber) {
            String outMsg3 = "Update annullato: nella colonna \'Periodo\' è possibile inserire solo cifre da 0 a 9";
            OutputMessage.setOutputText(outMsg3, jTextField3, 1);
            //System.out.println(outMsg3);
            return null;
        }
        OutputMessage.setOutputText("Update dei parametri relativi a " + nameTk + " eseguito correttamente", jTextField3);
        return newRowChoosTks;
    }

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
    //new SelfDownloadCaller("Update Called", 10, tableDBName,  myStmtDB, this);
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     *//*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTicker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //ArrayList<RowTicker> myTicker = getRowTickerArray(data);
        //myStmtDB.insertRowTKinDB(myTicker, myStmtDB.getQuery());// Use default query -NEED override this method!!
        /*
        java.awt.EventQueue.invokeLater(() -> {
            new ViewTicker().setVisible(true);
        });
        
    }
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JSplitPane jSplitPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
