package components;

import javax.swing.*;
import java.awt.*;
//Chuyển đổi giữa các chế độ cài đặt
public class ToggleSwitch extends JToggleButton implements SettingsConstants {
    
    public ToggleSwitch() {
        setPreferredSize(new Dimension(60, 30));
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setSelected(true); // Mặc định bật
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        // Vẽ thanh nền (màu xám hoặc màu sáng)
        Color bgColor = isSelected() ? NAV_COLOR.brighter() : TOGGLE_OFF_COLOR;
        g2.setColor(bgColor);
        g2.fillRoundRect(0, (int)(h * 0.25), w, (int)(h * 0.5), h / 2, h / 2);
        
        // Vẽ nút gạt (màu trắng)
        int knobSize = (int)(h * 0.8);
        g2.setColor(TOGGLE_ON_COLOR);
        int x = isSelected() ? w - knobSize - 2 : 2; // Vị trí (bên phải nếu bật)
        g2.fillOval(x, (h - knobSize) / 2, knobSize, knobSize);
        
        g2.dispose();
    }
}