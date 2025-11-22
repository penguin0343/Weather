
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
            d.location = "Ha Noi, Viet Nam";
            d.description = "Mostly sunny";
            d.currentTemp = 19; d.maxTemp = 35; d.minTemp = 19; d.airQualityIndex = 80; d.humidity = 40;

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 5); c.set(Calendar.MINUTE, 28);
            d.sunrise = c.getTime();
            c.set(Calendar.HOUR_OF_DAY, 19); c.set(Calendar.MINUTE, 20);
            d.sunset = c.getTime();

d.hourly = new ArrayList<>();

Calendar now = Calendar.getInstance();
int curHour = now.get(Calendar.HOUR_OF_DAY); // giờ hiện tại

ArrayList<HourlyData> hours = new ArrayList<>();
for (int i = 0; i < 24; i++) {
    int h = (curHour + i) % 24;
    String label;
    if (i == 0) {
        // Hiển thị "Now (12:00)" nếu bạn muốn kèm giờ trong ngoặc
        label = String.format("Now (%02d:00)", curHour);
        // hoặc nếu chỉ muốn "Now" -> label = "Now";
    } else {
        label = String.format("%02d:00", h);
    }

    // Ví dụ tạo icon và nhiệt độ giả   
    String icon = (h % 2 == 0 ? "☁" : "🌧");
    int temp = 20 + (h % 3);

    hours.add(new HourlyData(label, temp, icon));
}

d.hourly = hours;
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