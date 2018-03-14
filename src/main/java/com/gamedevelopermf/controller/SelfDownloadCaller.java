
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.gamedevelopermf.model.DBtkEvo;
import com.gamedevelopermf.view.ViewTicker;

/**
 *
 * @author emanuele
 */
public class SelfDownloadCaller {

    private Thread t;
    private int count;

    public SelfDownloadCaller(final String mesg, int n, final String tableDBName,final DBtkEvo myDB, final ViewTicker vt) {
        count = n;
        t = new Thread(new Runnable() {
            public void run() {
                
                while (count-- > 0) {
                    System.out.println(mesg);
                    try {
                        Boolean someOneNeedUpDate = updateChTKsetTRUE(tableDBName, myDB, vt); 
                        if (!someOneNeedUpDate)
                            System.out.println("No Ticker set to be updated");
                        Thread.sleep(10800000);     // 24 ore: 86400000
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                System.out.println(mesg + " thread all done.");
            }
        });
        t.setName(mesg + " thread running.");
        t.start();
    }

    public Boolean updateChTKsetTRUE(String tableDBName, DBtkEvo myDB, ViewTicker vt) {
        ArrayList<RowChoosenTks> allChosenTKsToBeDWL = myDB.getAllRowChoosenDownlodableDBData(tableDBName);
        Boolean someOneToUpdate = false;
        if (allChosenTKsToBeDWL.size() < 1) {
            return someOneToUpdate;
        }
        for (RowChoosenTks rct : allChosenTKsToBeDWL) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(rct.getLastDownloadDate());
            cal.add(Calendar.DATE, rct.getRefreshPeriod());
            Calendar nowDate = Calendar.getInstance();
            System.out.println("-----------");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sdf.format(cal.getTime()));
            System.out.println(sdf.format(nowDate.getTime()));
            System.out.println("-----------");
            if (cal.before(nowDate)){
                vt.downloadTickerForUpdate(rct.getTickerName()); // Deve aggiornare la data ultimo DWL + sysDate
                System.out.println("Ticker " + rct.getTickerName() +  " Updated");
            }
            else {
                System.out.println("Still not time to Update " + rct.getTickerName());             
            }
            someOneToUpdate = true;
        }
        
        return someOneToUpdate;
    }
}
