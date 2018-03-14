/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import java.sql.Date;

/**
 *
 * @author virginia
 */
public class RowChoosenTks {
    
    private String tickerName;
    private Date lastDownloadDate;
    private Boolean automaticRefresh;
    private int refreshPeriod;
    
    public Date getLastDownloadDate() {
        return lastDownloadDate;
    }

    public void setLastDownloadDate(Date dateTk) {
        this.lastDownloadDate = dateTk;
    }

    public Boolean getAutomaticRefresh() {
        return automaticRefresh;
    }

    public void setAutomaticRefresh(Boolean openTk) {
        this.automaticRefresh = openTk;
    }

    public int getRefreshPeriod() {
        return refreshPeriod;
    }

    public void setRefreshPeriod(int highTk) {
        this.refreshPeriod = highTk;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String lowTk) {
        this.tickerName = lowTk;
    }
   
}
