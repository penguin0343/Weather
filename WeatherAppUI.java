package GiaoDienChinh;

import javax.swing.*;
import java.awt.*;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class WeatherAppUI extends JFrame {

    // Kích thước và màu sắc cơ bản
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Color PRIMARY_COLOR = new Color(59, 89, 182); // Màu xanh dương đậm

    public WeatherAppUI() {
        setTitle("Weather Application UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout()); // Sử dụng BorderLayout cho cấu trúc chính

        // Thiết lập màu nền chung
        getContentPane().setBackground(PRIMARY_COLOR);

        // Tạo 3 phần chính của giao diện
        JPanel leftPanel = createLeftPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel rightPanel = createRightPanel();

        // Thêm các phần vào JFrame
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Thiết lập khung nhìn ở giữa (phần màu trắng)
        centerPanel.add(new JPanel() {{
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(WIDTH / 2, HEIGHT)); // Chiếm không gian còn lại
        }}, BorderLayout.CENTER);


        setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
    }

    // --- 1. Phần Bảng Điều Hướng Bên Trái ---
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(150, HEIGHT)); // Độ rộng cố định
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Sắp xếp theo chiều dọc

        // Tạo các nhãn cho menu (Main, Search, Settings)
        Font menuFont = new Font("Arial", Font.BOLD, 18);
        Color menuColor = Color.WHITE;

        panel.add(Box.createVerticalStrut(HEIGHT / 4)); // Khoảng trống trên cùng
        panel.add(createMenuItem("Main", menuFont, menuColor));
        panel.add(Box.createVerticalStrut(30));
        panel.add(createMenuItem("Search", menuFont, menuColor));
        panel.add(Box.createVerticalStrut(30));
        panel.add(createMenuItem("Settings", menuFont, menuColor));
        panel.add(Box.createVerticalGlue()); // Đẩy tất cả lên trên

        return panel;
    }

    private JLabel createMenuItem(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Canh giữa theo chiều ngang trong BoxLayout
        return label;
    }

    // --- 2. Phần Chính Giữa (Thông tin thời tiết hiện tại) ---
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(PRIMARY_COLOR);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        // Tên thành phố
        JLabel cityLabel = new JLabel("Ha Noi, Viet Nam");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cityLabel.setForeground(Color.WHITE);
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nhiệt độ hiện tại
        JLabel tempLabel = new JLabel("19°C");
        tempLabel.setFont(new Font("Arial", Font.BOLD, 90));
        tempLabel.setForeground(Color.WHITE);
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Min/Max nhiệt độ
        JLabel minMaxLabel = new JLabel("Max: 35°C Min: 19°C");
        minMaxLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        minMaxLabel.setForeground(Color.YELLOW); // Màu vàng nhạt
        minMaxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Mô tả thời tiết
        JLabel descLabel = new JLabel("Mostly sunny");
        descLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        descLabel.setForeground(Color.LIGHT_GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(10)); // Khoảng trống
        infoPanel.add(cityLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(tempLabel);
        infoPanel.add(minMaxLabel);
        infoPanel.add(descLabel);

        // Tạo khung màu trắng lớn ở dưới
        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.WHITE);
        // Thêm một JProgressBar đơn giản để mô phỏng thanh cuộn
        JProgressBar progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(WIDTH / 2, 5));
        progressBar.setBorderPainted(false);
        progressBar.setBackground(PRIMARY_COLOR);
        progressBar.setForeground(new Color(100, 100, 200)); // Màu sáng hơn
        blankPanel.add(progressBar);


        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(blankPanel, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Padding trên cùng

        return panel;
    }

    // --- 3. Phần Dự Báo 5 Ngày và Thông Tin Khác Bên Phải ---
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, HEIGHT)); // Độ rộng cố định
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Tiêu đề
        JLabel titleLabel = new JLabel("5-Days Forecasts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Canh lề trái
        panel.add(Box.createVerticalStrut(15));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        // Thêm các dự báo
        panel.add(createForecastItem("Mon", "28°C"));
        panel.add(createForecastItem("Tue", "36°C"));
        panel.add(createForecastItem("Wed", "30°C"));
        panel.add(createForecastItem("Thu", "19°C"));
        panel.add(createForecastItem("Fri", "25°C"));

        // Chất lượng không khí
        panel.add(Box.createVerticalStrut(20));
        panel.add(createSmallHeader("AIR QUALITY"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createAirQualityBar()); // Thanh trống mô phỏng chỉ số AQI

        // Bình minh & Hoàng hôn
        panel.add(Box.createVerticalGlue()); // Đẩy các thành phần dưới cùng xuống
        panel.add(createSunTimePanel("05:28", "19:20"));

        // Độ ẩm
        JLabel humidityLabel = new JLabel("HUMIDITY 40%");
        humidityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        humidityLabel.setForeground(Color.WHITE);
        humidityLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(humidityLabel);
        panel.add(Box.createVerticalStrut(10));

        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding bên trong

        return panel;
    }

    private JPanel createForecastItem(String day, String temp) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        item.setBackground(PRIMARY_COLOR);
        item.setMaximumSize(new Dimension(200, 25)); // Chiều cao cố định

        JLabel dayLabel = new JLabel(day);
        dayLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setPreferredSize(new Dimension(50, 20));

        // Thanh/Button mô phỏng nhiệt độ
        JPanel tempBar = new JPanel();
        tempBar.setBackground(new Color(100, 100, 200)); // Màu xanh sáng hơn
        tempBar.setPreferredSize(new Dimension(150, 20));
        tempBar.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Canh lề phải cho nhiệt độ

        JLabel tempLabel = new JLabel(temp);
        tempLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tempLabel.setForeground(Color.WHITE);
        tempLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        tempBar.add(tempLabel);

        item.add(dayLabel);
        item.add(tempBar);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        return item;
    }

    private JLabel createSmallHeader(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.LIGHT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createAirQualityBar() {
        // Thanh trống mô phỏng chỉ số AQI
        JPanel bar = new JPanel();
        bar.setPreferredSize(new Dimension(200, 20));
        bar.setBackground(new Color(100, 100, 200));
        bar.setMaximumSize(new Dimension(200, 20));
        bar.setAlignmentX(Component.LEFT_ALIGNMENT);
        return bar;
    }

    private JPanel createSunTimePanel(String sunrise, String sunset) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(PRIMARY_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bình minh
        JLabel sunRiseTime = new JLabel(sunrise);
        sunRiseTime.setFont(new Font("Arial", Font.BOLD, 18));
        sunRiseTime.setForeground(new Color(0, 180, 255)); // Màu xanh dương sáng
        JLabel sunRiseText = new JLabel("Sunrise");
        sunRiseText.setFont(new Font("Arial", Font.PLAIN, 12));
        sunRiseText.setForeground(new Color(0, 180, 255));

        // Hoàng hôn
        JLabel sunSetTime = new JLabel(sunset);
        sunSetTime.setFont(new Font("Arial", Font.BOLD, 18));
        sunSetTime.setForeground(new Color(0, 180, 255));
        JLabel sunSetText = new JLabel("Sunset");
        sunSetText.setFont(new Font("Arial", Font.PLAIN, 12));
        sunSetText.setForeground(new Color(0, 180, 255));

        // Ghép thời gian và nhãn cho Bình minh
        JPanel sunriseCol = new JPanel();
        sunriseCol.setLayout(new BoxLayout(sunriseCol, BoxLayout.Y_AXIS));
        sunriseCol.setBackground(PRIMARY_COLOR);
        sunRiseTime.setAlignmentX(Component.LEFT_ALIGNMENT);
        sunRiseText.setAlignmentX(Component.LEFT_ALIGNMENT);
        sunriseCol.add(sunRiseTime);
        sunriseCol.add(sunRiseText);

        // Ghép thời gian và nhãn cho Hoàng hôn
        JPanel sunsetCol = new JPanel();
        sunsetCol.setLayout(new BoxLayout(sunsetCol, BoxLayout.Y_AXIS));
        sunsetCol.setBackground(PRIMARY_COLOR);
        sunSetTime.setAlignmentX(Component.LEFT_ALIGNMENT);
        sunSetText.setAlignmentX(Component.LEFT_ALIGNMENT);
        sunsetCol.add(sunSetTime);
        sunsetCol.add(sunSetText);

        panel.add(sunriseCol);
        panel.add(Box.createHorizontalStrut(50)); // Khoảng cách giữa 2 cột
        panel.add(sunsetCol);

        return panel;
    }

    public static void main(String[] args) {
        // Đảm bảo UI được tạo trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new WeatherAppUI().setVisible(true);
        });
    }
}