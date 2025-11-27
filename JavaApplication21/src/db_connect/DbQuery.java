/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_connect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import model.DailyData;
import model.HourlyData;
import model.WeatherData;

/**
 *
 * @author Admin
 */
public class DbQuery {

    Connection conn;
    Statement stmt;
    ResultSet rs;

    Map<Integer, String> city_id_map = new HashMap<Integer, String>();

    public DbQuery(Connection conn, Statement stmt, ResultSet rs) {
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;

        city_id_map.put(1, "Hanoi");
        city_id_map.put(2, "Danang");
        city_id_map.put(3, "HCM");

    }

    public ArrayList<HourlyData> getHourlyWeather(String city) {
        String sql_query = "SELECT city_id, temperature, hours, icon FROM forecasthourly" + city.toLowerCase()
                + " LIMIT 12";
        ArrayList<HourlyData> hd_list = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql_query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int time = rs.getInt("hours");
                float temp = rs.getFloat("temperature");
                //String icon = rs.getString("icon");
                //HourlyData hd = new HourlyData(time, temp, icon);
                
                String iconCode = rs.getString("icon");
                String iconUrl = iconCode;
                HourlyData hd = new HourlyData(time, temp, iconUrl);
                hd_list.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hd_list;
    }

    public ArrayList<DailyData> getDailyWeather(String city) {
        String sql_query = "SELECT city_id, tempmin, tempmax, days, icon FROM forecastdaily" + city.toLowerCase() + " LIMIT 6";
        ArrayList<DailyData> hd_list = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql_query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int time = rs.getInt("days");
                float min_temp = rs.getFloat("tempmin");
                float max_temp = rs.getFloat("tempmax");
                //String icon = rs.getString("icon");
                //DailyData hd = new DailyData(time, max_temp, min_temp, icon);
                String iconCode = rs.getString("icon");
                String iconUrl = iconCode;
                DailyData hd = new DailyData(time, max_temp, min_temp, iconUrl);
                hd_list.add(hd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hd_list;
    }

    public Map<String, WeatherData> getWeather() {
        Map<String, WeatherData> current_weather_map = new HashMap<String, WeatherData>();
        for (Map.Entry<Integer, String> entry : city_id_map.entrySet()) {
            WeatherData wd = null;
            
            ArrayList<HourlyData> hd = getHourlyWeather(entry.getValue());
            ArrayList<DailyData> dd = getDailyWeather(entry.getValue());
            
            String sql_query = "SELECT \n"
                    + "    c.icon AS icon,\n"
                    + "    c.city_id AS city_id,\n"
                    + "    c.description AS description,\n"
                    + "    c.temperature AS temperature,\n"
                    + "    c.temp_max AS temp_max,\n"
                    + "    c.temp_min AS temp_min,\n"
                    + "    c.humidity AS humidity,\n"
                    + "    c.sunrise AS sunrise,\n"
                    + "    c.sunset AS sunset,\n"
                    + "    a.aqi AS aqi\n"
                    + "FROM currentdata c\n"
                    + "LEFT JOIN currentaqidata a ON c.city_id = a.city_id\n"
                    + "WHERE c.city_id=?\n"
                    + "ORDER BY c.id DESC\n"
                    + "LIMIT 1;";
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql_query);
                pstmt.setInt(1, entry.getKey());
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    String location = city_id_map.get(rs.getInt("city_id"));
                    Long sunrise = rs.getLong("sunrise");
                    Long sunset = rs.getLong("sunset");

                    Date sunriseDate = new Date(sunrise * 1000);
                    Date sunsetDate = new Date(sunset * 1000);

                    String iconCode = rs.getString("icon"); // Lấy mã icon (ví dụ "01d")
                    String iconUrl = iconCode;

                    
                    wd = new WeatherData(
                            //rs.getString("icon"),
                            iconUrl,
                            location,
                            rs.getString("description"),
                            rs.getFloat("temperature"),
                            rs.getFloat("temp_max"),
                            rs.getFloat("temp_min"),
                            hd,
                            dd,
                            rs.getInt("aqi"),
                            rs.getFloat("humidity"),
                            sunriseDate,
                            sunsetDate
                    );
                    current_weather_map.put(location, wd);
                }
                rs.close();
                pstmt.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            current_weather_map.put(entry.getValue(), wd);
        }

        return current_weather_map;
    }
}
