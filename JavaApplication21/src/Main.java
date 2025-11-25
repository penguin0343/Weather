
import app.WeatherApp;
import config.ConfigManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import model.DailyData;
import model.HourlyData;
import model.WeatherData;
import db_connect.NgrokConnector;
import db_connect.DatabaseConnector;
import db_connect.DbQuery;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        NgrokConnector ngrokconnector = new NgrokConnector();
        String publicUrl = ngrokconnector.getTunnelPublicUrl();

        Map<String, String> info = ngrokconnector.decodePublicUrl(publicUrl);
        String host = info.get("ip");
        String port = info.get("port");

        DatabaseConnector dbconnector = new DatabaseConnector(host, port);

        DbQuery dbquery = new DbQuery(dbconnector.getConn(), dbconnector.getStmt(), dbconnector.getRs());

        ConfigManager cfm = new ConfigManager();
//        for(Map.Entry<String, WeatherData> entry : weather_map.entrySet()){
//            System.out.println(entry.getKey());
//            WeatherData wd = entry.getValue();
//            System.out.println(wd.airQualityIndex);
//        }
        WeatherApp app = new WeatherApp();
        Map<String, WeatherData> weather_map = dbquery.getWeather();
        app.updateWeather(weather_map);
        javax.swing.Timer t = new javax.swing.Timer(600, e -> {
            
            Map<String, WeatherData> new_weather_map = dbquery.getWeather();
            app.updateWeather(new_weather_map);
            
            ((javax.swing.Timer) e.getSource()).stop();
        });

        t.start();

    }
}
