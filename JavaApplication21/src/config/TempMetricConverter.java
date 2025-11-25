package config;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class TempMetricConverter {
    public static float convert_F_to_C(float F) {
        float C = F - 32;
        return C;
    }
    
    public static float convert_K_to_C(float K) {
        float C = K - 273.15f;
        return C;
    }
}
