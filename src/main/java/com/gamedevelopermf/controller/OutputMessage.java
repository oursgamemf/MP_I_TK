/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamedevelopermf.controller;

import com.sun.prism.paint.Color;
import javax.swing.JTextField;


/**
 *
 * @author virginia
 */
public class OutputMessage {
    
    public static void setOutputText(String toShow, JTextField txtField){
        txtField.setForeground(java.awt.Color.BLACK);
        txtField.setText(toShow);
    }
    
    public static void setOutputText(String toShow, JTextField txtField, int severity){
        switch (severity){
            case 0: txtField.setForeground(java.awt.Color.BLACK);
                    break;
            case 1: txtField.setForeground(java.awt.Color.BLUE);
                    break;
            case 2: txtField.setForeground(java.awt.Color.RED);
                    break;
        }
        txtField.setText(toShow);
    }
    
    
}
