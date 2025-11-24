/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_connect;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import model.WeatherData;

/**
 *
 * @author Admin
 */
public class DbQuery {
    Connection conn;
    Statement stmt;
    ResultSet rs;
    
    Map<String, WeatherData> current_weather_map = new HashMap<String, WeatherData>();
    Map<Integer, String> city_id_map = new HashMap<Integer, String>();
    
    public DbQuery(Connection conn, Statement stmt, ResultSet rs) {
        this.conn = conn;
        this.stmt = stmt;
        this.rs = rs;
        
        city_id_map.put(1, "Hanoi");
        city_id_map.put(2, "Danang");
        city_id_map.put(3, "HCM");

    }

    
    
    
    public Map<String, WeatherData> getCurrentWeather() {
        String sql_query  = "SELECT \n" +
        "    c.city_id,\n" +
        "    c.description,\n" +
        "    c.temperature,\n" +
        "    c.temp_max,\n" +
        "    c.temp_min,\n" +
        "    c.humidity,\n" +
        "    c.sunrise,\n" +
        "    c.sunset,\n" +
        "    a.aqi\n" +
        "FROM currentdata c\n" +
        "INNER JOIN currentaqidata a ON c.city_id = a.city_id\n" +
        "ORDER BY c.id ASC\n" +
        "LIMIT 3;";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql_query);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {        
                String location = city_id_map.get(rs.getInt("city_id"));
                Long sunrise = rs.getLong("sunrise");
                Long sunset = rs.getLong("sunset");
                   
                Date sunriseDate = new Date(sunrise * 1000);
                Date sunsetDate = new Date(sunset * 1000);

                WeatherData wd = new WeatherData(
                        location, 
                        rs.getString("description"),
                        rs.getFloat("temperature"), 
                        rs.getFloat("temp_max"), 
                        rs.getFloat("temp_min"), 
                        null, 
                        null, 
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
         
        return current_weather_map;
    }
}
