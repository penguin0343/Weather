package model;

public class DailyData {
    public String dayOfWeek, icon;
    public double maxTemp, minTemp;

    public DailyData(String d, double max, double min, String ic) {
        dayOfWeek = d;
        maxTemp = max;
        minTemp = min;
        icon = ic;
    }
}
