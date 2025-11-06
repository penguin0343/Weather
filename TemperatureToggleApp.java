import javax.swing.*;
import java.awt.*;

public class TemperatureToggleApp {

    private JFrame frame;
    private JLabel celsiusLabel;
    private JLabel fahrenheitLabel;
    private CustomToggleButton toggleButton;
    private JLabel statusLabel; // Nhãn để hiển thị trạng thái hiện tại

    public TemperatureToggleApp() {
        // 1. Khởi tạo Frame
        frame = new JFrame("Chuyển đổi Đơn vị Nhiệt độ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200); // Tăng chiều cao để chứa dòng Setting
        
        // Thiết lập BorderLayout làm Layout Manager chính cho Frame
        frame.setLayout(new BorderLayout());

        // --- BỔ SUNG: THÊM NHÃN "SETTING" Ở GÓC TRÊN BÊN TRÁI ---
        
        // Tạo Panel Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); // Căn trái, có padding
        headerPanel.setBackground(new Color(40, 90, 190));
        
        // Tạo Nhãn Setting
        JLabel settingLabel = new JLabel("Setting");
        settingLabel.setForeground(Color.WHITE);
        settingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        headerPanel.add(settingLabel);
        
        // Thêm Header Panel vào vị trí NORTH của Frame
        frame.add(headerPanel, BorderLayout.NORTH);

        // --- NỘI DUNG CHÍNH (Thanh trượt) ---
        
        // Sử dụng JPanel chính để chứa thanh trượt, nằm ở giữa khung
        JPanel contentPanel = new JPanel();
        // Giữ FlowLayout để căn giữa các thành phần C/F/Toggle
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        contentPanel.setBackground(new Color(40, 90, 190)); // Màu xanh dương đậm
        
        // 2. Tạo các thành phần Label
        celsiusLabel = new JLabel("Celsius/°C");
        celsiusLabel.setForeground(Color.WHITE);
        celsiusLabel.setFont(new Font("Arial", Font.BOLD, 18));

        fahrenheitLabel = new JLabel("Fahrenheit/°F");
        fahrenheitLabel.setForeground(Color.WHITE);
        fahrenheitLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // 3. Tạo Custom Toggle Button
        // Giả định CustomToggleButton đã được khởi tạo
        toggleButton = new CustomToggleButton();
        
        // 4. Thêm listener cho Custom Toggle Button
        // Giả định ToggleChangeListener đã được định nghĩa
        toggleButton.addToggleChangeListener(new ToggleChangeListener() {
            @Override
            public void onToggleChanged(boolean isOn) {
                if (isOn) {
                    statusLabel.setText("Đơn vị hiện tại: Fahrenheit (°F)");
                } else {
                    statusLabel.setText("Đơn vị hiện tại: Celsius (°C)");
                }
            }
        });

        // 5. Thêm các thành phần vào contentPanel
        contentPanel.add(celsiusLabel);
        contentPanel.add(toggleButton);
        contentPanel.add(fahrenheitLabel);
        
        // Thêm contentPanel vào vị trí CENTER của Frame
        frame.add(contentPanel, BorderLayout.CENTER);
        
        // 6. Tạo và thêm statusLabel
        statusLabel = new JLabel("Đơn vị hiện tại: Celsius (°C)", SwingConstants.CENTER);
        statusLabel.setForeground(Color.BLACK);
        frame.add(statusLabel, BorderLayout.SOUTH); // Đặt nhãn trạng thái ở dưới

        // 7. Hiển thị Frame
        frame.setVisible(true);
    }

    // Phương thức main để chạy ứng dụng
    public static void main(String[] args) {
        // Đảm bảo giao diện người dùng được tạo trên Event Dispatch Thread (luồng EDT)
        SwingUtilities.invokeLater(() -> new TemperatureToggleApp());
    }
}