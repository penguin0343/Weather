package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsSidebar extends JPanel implements SettingsConstants {

    public SettingsSidebar() {
        setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0)); 
        setBackground(NAV_COLOR);
        setLayout(new GridLayout(6, 1, 10, 10)); 
        setBorder(new EmptyBorder(10, 0, 10, 0)); 

        add(createSidebarIcon("☰", 24, TEXT_COLOR, NAV_COLOR)); 
        add(createSidebarIcon("🔎", 24, TEXT_COLOR, NAV_COLOR)); 
        add(createSidebarIcon("⚙", 24, TEXT_COLOR, PRIMARY_COLOR)); 
        
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