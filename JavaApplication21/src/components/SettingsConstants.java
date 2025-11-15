package components;

import java.awt.Color;

public interface SettingsConstants {
    // Kích thước Frame
    int FRAME_WIDTH = 1200;
    int FRAME_HEIGHT = 720;
    
    // Kích thước Sidebar
    int SIDEBAR_WIDTH = 96;
    
    // Màu sắc của nền
    // Màu nền chính, hơi gradient xanh tím
    Color PRIMARY_COLOR = new Color(50, 70, 200); 
    // Màu cho thanh điều hướng bên trái, đậm hơn
    Color NAV_COLOR = new Color(70, 90, 220); 
    // Màu cho công tắc gạt (màu xám khi tắt)
    Color TOGGLE_OFF_COLOR = new Color(150, 150, 150); 
    // Màu cho công tắc gạt (màu xanh khi bật)
    Color TOGGLE_ON_COLOR = new Color(255, 255, 255); 
    
    // Màu chữ
    Color TEXT_COLOR = Color.WHITE;
}