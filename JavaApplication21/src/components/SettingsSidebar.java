import javax.swing.&;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsSidebar extends JPanel implements SettingsConstants {
    public SettingsSidebar() {
        // Kích thước cố định: 96 pixels (giống WeatherApp)
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0)); 
        setBackground(NAV_COLOR);
        // GridLayout (6 hàng, 1 cột, 10 gap) mô phỏng sidebar
        setLayout(new GridLayout(6, 1, 10, 10)); 
        setBorder(new EmptyBorder(10, 0, 10, 0)); 

        // Biểu tượng Menu (☰) - MAIN
        add(createSidebarIcon("☰", 24, TEXT_COLOR, NAV_COLOR)); 
        // Biểu tượng Vị trí (🔎) - SEARCH
        add(createSidebarIcon("🔎", 24, TEXT_COLOR, NAV_COLOR)); 
        // Biểu tượng Cài đặt (⚙) - SETTING (Đánh dấu đang chọn)
        add(createSidebarIcon("⚙", 24, TEXT_COLOR, PRIMARY_COLOR)); 
        
        // Thêm các ô trống (placeholder)
        add(new JPanel() {{ setOpaque(false); }});
        add(new JPanel() {{ setOpaque(false); }});
        add(new JPanel() {{ setOpaque(false); }});
    }

    // Hàm tạo JLabel mô phỏng IconMenuButton
    private JLabel createSidebarIcon(String icon, int size, Color fgColor, Color bgColor) {
        JLabel label = new JLabel(icon, SwingConstants.CENTER); 
        label.setFont(new Font("SansSerif", Font.PLAIN, size));
        label.setForeground(fgColor);
        label.setBackground(bgColor); 
        label.setOpaque(true); 
        label.setBorder(new EmptyBorder(5, 0, 5, 0)); 
        return label; 
    }
}