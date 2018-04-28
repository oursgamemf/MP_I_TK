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
public class RowTicker {
    private Date dateTk;
    private Double openTk;
    private Double highTk;
    private Double lowTk;
    private Double closeTk;
    private Double volumeTk;
    private Double adjCloseTk;
    private Double dividends;

    public Date getDateTk() {
        return dateTk;
    }

    public void setDateTk(Date dateTk) {
        this.dateTk = dateTk;
    }

    public Double getOpenTk() {
        return openTk;
    }

    public void setOpenTk(Double openTk) {
        this.openTk = openTk;
    }

    public Double getHighTk() {
        return highTk;
    }

    public void setHighTk(Double highTk) {
        this.highTk = highTk;
    }

    public Double getLowTk() {
        return lowTk;
    }

    public void setLowTk(Double lowTk) {
        this.lowTk = lowTk;
    }

    public Double getCloseTk() {
        return closeTk;
    }

    public void setCloseTk(Double closeTk) {
        this.closeTk = closeTk;
    }

    public Double getVolumeTk() {
        return volumeTk;
    }

    public void setVolumeTk(Double volumeTk) {
        this.volumeTk = volumeTk;
    }

    public Double getAdjCloseTk() {
        return adjCloseTk;
    }

    public void setAdjCloseTk(Double adjCloseTk) {
        this.adjCloseTk = adjCloseTk;
    }
    
    public Double getDividendsTk() {
        return dividends;
    }

    public void setDividendsTk(Double divids) {
        this.dividends = divids;
    }
}
