package model;

import config.TempMetricConverter;
import java.util.*;

public class WeatherData {

    public String location, description, icon;
    public float currentTemp, maxTemp, minTemp, humidity;
    public java.util.List<HourlyData> hourly;
    public java.util.List<DailyData> daily;
    public java.util.List<DailyData> daily_tmp;
    public int airQualityIndex;
    public Date sunrise, sunset;
    
    
    public WeatherData() {}
    
    public WeatherData(String iconUrl, String location, String description, float currentTemp, float maxTemp, float minTemp, List<HourlyData> hourly, List<DailyData> daily, int airQualityIndex, float humidity, Date sunrise, Date sunset) {
        this.daily_tmp = daily;
        this.icon = iconUrl;
        this.location = location;
        this.description = description;
        this.currentTemp = currentTemp;
        this.maxTemp = daily.getFirst().maxTemp;
        this.minTemp = daily.getFirst().minTemp;
        this.hourly = hourly;
        this.daily = new ArrayList<DailyData>(daily.subList(1, daily.size()));
        this.airQualityIndex = airQualityIndex;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
    
    
    public void converttempToDefault(String defaultMetric) {
        if (defaultMetric.equalsIgnoreCase("C")) {
            convertTemp_K_to_C();
        }
        if (defaultMetric.equalsIgnoreCase("F")) {
            convertTemp_K_to_F();
        }
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
    
    public void convertTemp_K_to_F() {
        this.currentTemp = TempMetricConverter.convert_K_to_F(this.currentTemp);
        this.minTemp = TempMetricConverter.convert_K_to_F(this.minTemp);
        this.maxTemp = TempMetricConverter.convert_K_to_F(this.maxTemp);
        for (HourlyData h : this.hourly) {
            h.temp = TempMetricConverter.convert_K_to_F(h.temp);
        }
        for (DailyData d : this.daily) {
            d.maxTemp = TempMetricConverter.convert_K_to_F(d.maxTemp);
            d.minTemp = TempMetricConverter.convert_K_to_F(d.minTemp);
        }
    }
    
    public WeatherData clone() {
        java.util.List<HourlyData> newHourly = new java.util.ArrayList<>();
        for (HourlyData hd: this.hourly) {
            HourlyData newhd = new HourlyData(hd.time, hd.temp, hd.icon);
            newHourly.add(newhd);
        }
        
        java.util.List<DailyData> newDaily = new java.util.ArrayList<>();
        for (DailyData dd: this.daily_tmp) {
            DailyData newdd = new DailyData(dd.dayOfWeek, dd.maxTemp, dd.minTemp, dd.icon);
            newDaily.add(newdd);
        }
        
        WeatherData newWD = new WeatherData(
                this.icon,
                this.location,
                this.description,
                this.currentTemp,
                this.maxTemp,
                this.minTemp,
                newHourly,
                newDaily,
                this.airQualityIndex,
                this.humidity,
                this.sunrise,
                this.sunset
        );
        return newWD;
    }
}
