package model;

import java.util.*;

public class WeatherData {
    public String location, description;
    public float currentTemp, maxTemp, minTemp, humidity;
    public java.util.List<HourlyData> hourly;
    public java.util.List<DailyData> daily;
    public int airQualityIndex;
    public Date sunrise, sunset;

    public WeatherData(String location, String description, float currentTemp, float maxTemp, float minTemp, List<HourlyData> hourly, List<DailyData> daily, int airQualityIndex, float humidity, Date sunrise, Date sunset) {
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
    
    
}
