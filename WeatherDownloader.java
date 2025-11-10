import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherDownloader {

    private static final String API_KEY = "f42687ac1e1ada5c0fe5f7277df25483";

    private static final String CITY_NAME = "Hanoi";

    // Đường dẫn tuyệt đối đến thư mục lưu trữ.
    private static final String OUTPUT_FOLDER = "E:/OneDrive/2024/PTIT/Lap trinh huong doi tuong/BaiTapLon/Data/";

    // --- Cấu hình cho DỰ BÁO THỜI TIẾT (Forecast, 3 giờ/lần) ---
    private static final long FORECAST_REPEAT_INTERVAL_MS = 3 * 60 * 60 * 1000;
    private static final String FORECAST_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";

    // --- Cấu hình cho THỜI TIẾT HIỆN TẠI (Current, 5 phút/lần) ---
    private static final long CURRENT_REPEAT_INTERVAL_MS = 5 * 60 * 1000;
    private static final String CURRENT_BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {

        // 1. Kiểm tra và tạo thư mục nếu nó chưa tồn tại
        File outputDir = new File(OUTPUT_FOLDER);
        if (!outputDir.exists()) {
            if (outputDir.mkdirs()) {
                System.out.println("Đã tạo thư mục: " + OUTPUT_FOLDER);
            } else {
                System.err.println("❌ Lỗi: Không thể tạo thư mục lưu trữ!");
                return;
            }
        }

        // 2. Cấu hình URL API đầy đủ
        final String forecastApiURL = FORECAST_BASE_URL + "?q=" + CITY_NAME + "&appid=" + API_KEY + "&units=metric";
        final String currentApiURL = CURRENT_BASE_URL + "?q=" + CITY_NAME + "&appid=" + API_KEY + "&units=metric";

        // 3. Khởi tạo và chạy LUỒNG TẢI DỮ LIỆU DỰ BÁO (3 giờ)
        System.out.println("Khởi động Luồng DỰ BÁO: Tải mỗi " + (FORECAST_REPEAT_INTERVAL_MS / 3600000) + " giờ.");
        new Thread(() -> {
            startDownloader(
                forecastApiURL, 
                CITY_NAME, 
                OUTPUT_FOLDER, 
                FORECAST_REPEAT_INTERVAL_MS, 
                "DỰ BÁO (3H)", 
                "forecast"
            );
        }).start();

        // 4. Khởi tạo và chạy LUỒNG TẢI DỮ LIỆU HIỆN TẠI (5 phút)
        System.out.println("Khởi động Luồng HIỆN TẠI: Tải mỗi " + (CURRENT_REPEAT_INTERVAL_MS / 60000) + " phút.");
        new Thread(() -> {
            startDownloader(
                currentApiURL, 
                CITY_NAME, 
                OUTPUT_FOLDER, 
                CURRENT_REPEAT_INTERVAL_MS, 
                "HIỆN TẠI (5P)", 
                "current"
            );
        }).start();
        
        System.out.println("Hai luồng tải dữ liệu đã được khởi động song song.");
    }

    /**
     * Vòng lặp chính để tải dữ liệu định kỳ trong một luồng riêng biệt.
     * @param apiURL URL đầy đủ của API
     * @param city Tên thành phố
     * @param outputFolder Đường dẫn thư mục lưu trữ
     * @param intervalMS Khoảng thời gian chờ giữa các lần tải (miligiây)
     * @param logName Tên hiển thị trong console
     * @param filePrefix Tiền tố tên file (ví dụ: "forecast", "current")
     */
    private static void startDownloader(
            String apiURL, 
            String city, 
            String outputFolder, 
            long intervalMS, 
            String logName, 
            String filePrefix) {

        while (true) {
            downloadAndSaveData(apiURL, city, outputFolder, logName, filePrefix);

            try {
                // Hiển thị thời gian chờ
                long minutes = intervalMS / 60000;
                long hours = intervalMS / 3600000;

                String displayTime = (hours > 0) ? (hours + " giờ") : (minutes + " phút");

                System.out.println("\n--- [" + logName + "] Đang chờ " + displayTime + " cho lần tải tiếp theo... ---\n");
                
                // Dừng luồng theo khoảng thời gian đã cấu hình
                Thread.sleep(intervalMS);
            } catch (InterruptedException e) {
                // Xử lý khi luồng bị gián đoạn
                Thread.currentThread().interrupt(); 
                System.err.println("[" + logName + "] Chương trình tải dữ liệu bị ngắt.");
                break;
            }
        }
    }

    /**
     * Phương thức thực hiện tải dữ liệu và lưu file cho một chu kỳ.
     */
    private static void downloadAndSaveData(
            String apiURL, 
            String city, 
            String outputFolder, 
            String logName, 
            String filePrefix) {

        // 1. Tạo tên file độc nhất dựa trên thời gian tải
        LocalDateTime now = LocalDateTime.now();
        // Định dạng yyyyMMdd_HHmmss (ví dụ: 20251108_173000)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);

        // Tạo tên file: prefix_city_timestamp.json
        String fileName = filePrefix + "_" + city + "_" + timestamp + ".json";
        String fullPath = outputFolder + fileName;

        System.out.println("[" + logName + "] Bắt đầu tải dữ liệu tại: " + timestamp);

        try {
            // 2. Mở kết nối HTTP
            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 3. Đọc dữ liệu JSON
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                String jsonResponse = content.toString();

                // 4. Lưu dữ liệu
                saveDataToFile(jsonResponse, fullPath);

                System.out.println("✅ [" + logName + "] Tải dữ liệu thành công! (" + fullPath + ")");

            } else {
                // Xử lý lỗi (Đặc biệt là lỗi 401 do Invalid API Key)
                System.err.println("❌ [" + logName + "] Lỗi khi gửi yêu cầu HTTP. Mã lỗi: " + responseCode);

                BufferedReader errorIn = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorLine;
                StringBuilder errorContent = new StringBuilder();
                while ((errorLine = errorIn.readLine()) != null) {
                    errorContent.append(errorLine);
                }
                errorIn.close();
                System.err.println("Phản hồi lỗi: " + errorContent.toString());
            }

            connection.disconnect();

        } catch (IOException e) {
            System.err.println("❌ [" + logName + "] Lỗi kết nối hoặc I/O: " + e.getMessage());
        }
    }

    /**
     * Phương thức lưu chuỗi dữ liệu vào file trên ổ cứng.
     */
    private static void saveDataToFile(String data, String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(data);
        }
    }
}