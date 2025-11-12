package model;

public class HourlyData {
    public String time, icon;
    public double temp;

    public HourlyData(String t, double tmp, String ic) {
        time = t;
        temp = tmp;
        icon = ic;
    }
}
