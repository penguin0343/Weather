package app;

import javax.swing.*;
import java.awt.*;
import components.*; // Import tất cả các components đã tách

public class SettingsUI extends JFrame implements SettingsConstants {

    public SettingsUI() {
        setTitle("Settings UI");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Nên dùng DISPOSE_ON_CLOSE cho cửa sổ phụ
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // Sử dụng hằng số
        setResizable(false);
        setLayout(new BorderLayout());

        // Thiết lập màu nền chung  
        getContentPane().setBackground(PRIMARY_COLOR);

        // --- 1. Tạo thanh điều hướng bên trái (Sử dụng class đã tách) ---
        JPanel navPanel = new SettingsSidebar(); 
        
        // --- 2. Tạo nội dung cài đặt chính (Sử dụng class đã tách) ---
        JPanel contentPanel = new SettingsContentPanel();

        // Thêm các phần vào JFrame
        add(navPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SettingsUI().setVisible(true);
        });
    }
}