package model;

import config.TempMetricConverter;
import java.util.*;

public class WeatherData {

    public String location, description, icon;
    public float currentTemp, maxTemp, minTemp, humidity;
    public java.util.List<HourlyData> hourly;
    public java.util.List<DailyData> daily;
    public int airQualityIndex;
    public Date sunrise, sunset;
    
    public WeatherData() {
        
    }
    
    public WeatherData(String icon, String location, String description, float currentTemp, float maxTemp, float minTemp, List<HourlyData> hourly, List<DailyData> daily, int airQualityIndex, float humidity, Date sunrise, Date sunset) {
        this.icon = icon;
        this.location = location;
        this.description = description;
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.hourly = hourly;
        this.daily = daily;
        this.airQualityIndex = airQualityIndex;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
    
    public void convertTemp_K_to_C() {
        this.currentTemp = TempMetricConverter.convert_K_to_C(this.currentTemp);
        this.minTemp = TempMetricConverter.convert_K_to_C(this.minTemp);
        this.maxTemp = TempMetricConverter.convert_K_to_C(this.maxTemp);
        for (HourlyData h : this.hourly) {
            h.temp = TempMetricConverter.convert_K_to_C(h.temp);
        }
        for (DailyData d : this.daily) {
            d.maxTemp = TempMetricConverter.convert_K_to_C(d.maxTemp);
            d.minTemp = TempMetricConverter.convert_K_to_C(d.minTemp);
        }
    }
}
