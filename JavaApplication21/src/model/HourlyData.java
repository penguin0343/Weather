package model;

public class HourlyData {
    public int time;
    public String icon;
    public float temp;

    public HourlyData(int t, float tmp, String ic) {
        time = t;
        temp = tmp;
        icon = ic;
    }
}
