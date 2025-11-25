/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Admin
 */
public class TimeDisplayConverter {
    public static String convert_t24(LocalDateTime time) {
        String t24 = time.format(DateTimeFormatter.ofPattern("HH"));
        return t24 + "h";
    }
    
    public static String convert_t12(LocalDateTime time) {
        String t12 = time.format(DateTimeFormatter.ofPattern("hh a"));
        return t12;
    }
    
    public static String convertFromDefault(LocalDateTime time, int defaultDisplay) {
        if (defaultDisplay == 12) {
            return convert_t12(time);
        }
        return convert_t24(time);
    }
    

}
