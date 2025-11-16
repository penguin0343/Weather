package ui; // <--- Thay đổi package

import components.SettingsSidebar;
import components.SettingsContentPanel;
import components.SettingsConstants;
import javax.swing.*;
import java.awt.*;

// Thay đổi Panel
public class SettingsPanel extends JPanel implements SettingsConstants {

    public SettingsPanel() {
        // Thiết lập kích thước và bố cục
        setLayout(new BorderLayout());

        // Sử dụng GradientPanel màu nền của WeatherApp
        setOpaque(false); 

        // Bỏ thanh điều hướng navPanel (Sidebar của Settings) vì WeatherApp đã có thanh điều hướng chính
        
        // --- Tạo nội dung cài đặt chính ---
        // Chúng ta cần bọc nó trong một container để có thể đặt màu nền chính xác
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); 
        
        // SettingsContentPanel sẽ tự vẽ phần giao diện Setting như hình
        JPanel contentPanel = new SettingsContentPanel(); 
        
        // Thêm contentPanel vào giữa wrapper
        wrapper.add(contentPanel, BorderLayout.CENTER);
        
        // Thêm wrapper vào SettingsPanel
        add(wrapper, BorderLayout.CENTER);
    }
    
    // Xóa phương thức main()
}