package config;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class ConfigManager {

    public static String defaultLocation = "Hanoi";
    public ArrayList<String> locationList;
    private enum tempMetric {C, F, K};
    private tempMetric curentTempMetric;
            
            
            
            
            
            
    public ConfigManager() {
        String[] arr = {"Hanoi", "Danang", "HCM"};
        locationList = new ArrayList<>(Arrays.asList(arr));
//        Mặc định hiển thị là độ C
        curentTempMetric = tempMetric.C;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public String getCurentTempMetric() {
        return ""+curentTempMetric+"";
    }


    
}
