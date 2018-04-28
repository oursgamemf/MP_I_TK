/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;

/**
 *
 * @author emanuele
 */
public class DwnldDataSourceOne {
    
    public static Stock yahooAPI_MONTH(String nameTk){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(1800, 01, 01);
        
        Stock google = null;
        try {
            google = YahooFinance.get(nameTk, from, to, Interval.MONTHLY);
            return google;
        } catch (IOException ex) {
            Logger.getLogger(DwnldDataSourceOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        return google;
    }
    
    public static Stock yahooAPI_WEEK(String nameTk){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(1800, 01, 01);

        Stock google = null;
        try {
            google = YahooFinance.get(nameTk, from, to, Interval.WEEKLY);
            return google;
        } catch (IOException ex) {
            Logger.getLogger(DwnldDataSourceOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        return google;
    }
    
    public static Stock yahooAPI_DAY(String nameTk){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(1800, 01, 01);
        
        Stock google = null;
        try {
            google = YahooFinance.get(nameTk, from, to, Interval.DAILY);
            return google;
        } catch (IOException ex) {
            Logger.getLogger(DwnldDataSourceOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        return google;
    }
    
    public static List<HistoricalDividend> getListOfDividends(String nameTk) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.set(1800, 01, 01);
        Stock google;
        List<HistoricalDividend> dividend = null;
        try {
            google = YahooFinance.get(nameTk, from, to, Interval.DAILY);
            dividend = google.getDividendHistory(from, to);
        } catch (IOException ex) {
            Logger.getLogger(DwnldDataSourceOne.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dividend;
    }
    
}
