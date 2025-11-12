package model;

import java.util.*;

public class WeatherData {
    public String location, description;
    public double currentTemp, maxTemp, minTemp;
    public java.util.List<HourlyData> hourly;
    public java.util.List<DailyData> daily;
    public int airQualityIndex, humidity;
    public Date sunrise, sunset;
}
