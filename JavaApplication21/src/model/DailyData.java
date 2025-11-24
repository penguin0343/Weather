package model;

public class DailyData {
    public int dayOfWeek;
    public String icon;
    public float maxTemp, minTemp;

    public DailyData(int d, float max, float min, String ic) {
        dayOfWeek = d;
        maxTemp = max;
        minTemp = min;
        icon = ic;
    }
}
