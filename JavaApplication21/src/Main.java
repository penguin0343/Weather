
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import model.DailyData;
import model.HourlyData;
import model.WeatherData;

public class Main {
    public static void main(String[] args) {
        WeatherApp app = new WeatherApp();
        javax.swing.Timer t = new javax.swing.Timer(600, e -> {
            WeatherData d = new WeatherData();
            d.location = "Bac Ninh, Viet Nam";
            d.description = "Mostly cloudy";
            d.currentTemp = 20; d.maxTemp = 35; d.minTemp = 19; d.airQualityIndex = 80; d.humidity = 40;

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 5); c.set(Calendar.MINUTE, 28);
            d.sunrise = c.getTime();
            c.set(Calendar.HOUR_OF_DAY, 19); c.set(Calendar.MINUTE, 20);
            d.sunset = c.getTime();

            d.hourly = new ArrayList<>();
            d.hourly.add(new HourlyData("Now", 19, "⛅"));
            for (int i = 1; i <= 7; i++)
                d.hourly.add(new HourlyData(String.format("%02d:00", (15 + i) % 24), 20 + i % 3, i % 2 == 0 ? "🌧" : "☁"));

            d.daily = Arrays.asList(
                    new DailyData("Mon", 28, 22, "🌦"),
                    new DailyData("Tue", 36, 25, "☀"),
                    new DailyData("Wed", 30, 24, "⛅"),
                    new DailyData("Thu", 19, 16, "🌧"),
                    new DailyData("Fri", 25, 19, "⛈")
            );

            app.updateWeather(d);
            ((javax.swing.Timer) e.getSource()).stop();
        });
        t.start();
    }
}