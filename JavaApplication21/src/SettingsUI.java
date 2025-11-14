import components.SettingsSidebar;
import components.SettingsContentPanel;
import components.SettingsConstants;
import javax.swing.*;
import java.awt.*;

public class SettingsUI extends JFrame implements SettingsConstants {

    public SettingsUI() {
        setTitle("Settings UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());

        // Thiết lập màu nền chung
        getContentPane().setBackground(PRIMARY_COLOR);

        // --- 1. Tạo thanh điều hướng bên trái ---
        JPanel navPanel = new SettingsSidebar();
        
        // --- 2. Tạo nội dung cài đặt chính ---
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