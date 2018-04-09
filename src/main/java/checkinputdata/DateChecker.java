/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkinputdata;

/**
 *
 * @author emanuele
 */
public class DateChecker {
    public static String checkData(String dataBefore){
        String dataAfter =  "";
        if(dataBefore.length()>10){
                           dataAfter =  dataBefore.substring(2,dataBefore.length());
        }
        else{
            dataAfter = dataBefore;
        }
        return dataAfter;
        
    }
}
