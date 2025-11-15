import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsUI extends JFrame {

    // Kích thước và Màu sắc cơ bản
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    // Màu nền chính, hơi gradient xanh tím
    private static final Color PRIMARY_COLOR = new Color(50, 70, 200); 
    // Màu cho thanh điều hướng bên trái, đậm hơn
    private static final Color NAV_COLOR = new Color(70, 90, 220); 
    // Màu cho công tắc gạt (màu xám khi tắt)
    private static final Color TOGGLE_OFF_COLOR = new Color(150, 150, 150); 
    // Màu cho công tắc gạt (màu xanh khi bật)
    private static final Color TOGGLE_ON_COLOR = new Color(255, 255, 255); 

    public SettingsUI() {
        setTitle("Settings UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());

        // Thiết lập màu nền chung cho toàn bộ cửa sổ
        getContentPane().setBackground(PRIMARY_COLOR);

        // --- 1. Tạo thanh điều hướng bên trái ---
        JPanel navPanel = createNavigationPanel();
        
        // --- 2. Tạo nội dung cài đặt chính ---
        JPanel contentPanel = createContentPanel();

        // Thêm các phần vào JFrame
        add(navPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); 
    }

    // --- 1. Bảng điều hướng (Navigation Panel) ---
    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(60, HEIGHT)); // Độ rộng cố định
        panel.setBackground(NAV_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 0, 20, 0)); 

        // Biểu tượng Menu (☰)
        panel.add(createIconLabel("☰", 30, NAV_COLOR));
        panel.add(Box.createVerticalStrut(HEIGHT / 6)); 
        // Biểu tượng Vị trí (📍)
        panel.add(createIconLabel("📍", 30, NAV_COLOR));
        panel.add(Box.createVerticalGlue()); // Đẩy phần dưới cùng xuống
        // Biểu tượng Cài đặt (⚙) - Thêm màu sáng hơn để đánh dấu đang ở mục này
        panel.add(createIconLabel("⚙", 30, PRIMARY_COLOR)); 

        return panel;
    }

    private JLabel createIconLabel(String icon, int size, Color bgColor) {
        JLabel label = new JLabel(icon);
        label.setFont(new Font("Arial", Font.BOLD, size));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        // Tạo panel chứa icon để mô phỏng hình tròn/vuông bo góc xung quanh icon
        JPanel iconWrapper = new JPanel();
        iconWrapper.setBackground(bgColor); 
        iconWrapper.add(label);
        
        // Thêm khoảng đệm để căn chỉnh
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(iconWrapper);
        
        return label; // Trả về JLabel để đơn giản hóa, iconWrapper chỉ để căn chỉnh
    }
    
    // --- 2. Nội dung Cài Đặt (Content Panel) ---
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30)); 

        // 2.1. Tiêu đề SETTING
        JLabel titleLabel = new JLabel("SETTING");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);

        // Đường kẻ ngang mô phỏng
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(150, 150, 255));
        separator.setBackground(PRIMARY_COLOR);
        separator.setMaximumSize(new Dimension(WIDTH - 150, 5));
        panel.add(separator);
        panel.add(Box.createVerticalStrut(30));

        // 2.2. Đơn vị Nhiệt độ
        panel.add(createSettingGroup("Temperature Unit", "Celsius/°C", "Fahrenheit/°F"));
        panel.add(Box.createVerticalStrut(40));

        // 2.3. Định dạng Thời gian
        panel.add(createSettingGroup("Time format", "12-hour", "24-hour"));
        
        panel.add(Box.createVerticalGlue()); 

        return panel;
    }
    
    // --- Hàm tạo nhóm cài đặt (Tiêu đề, Tùy chọn A, Tùy chọn B) ---
    private JPanel createSettingGroup(String title, String optionA, String optionB) {
        JPanel groupPanel = new JPanel();
        groupPanel.setOpaque(false);
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Tiêu đề nhóm
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupPanel.add(titleLabel);
        groupPanel.add(Box.createVerticalStrut(15));
        
        // Hàng tùy chọn (Label A | Toggle | Label B)
        JPanel optionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        optionRow.setOpaque(false);
        optionRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Tùy chọn A
        JLabel labelA = new JLabel(optionA);
        labelA.setFont(new Font("Arial", Font.PLAIN, 18));
        labelA.setForeground(Color.WHITE);
        
        // Mô phỏng Toggle Switch
        JToggleButton toggle = createToggleSwitchMock();
        
        // Tùy chọn B
        JLabel labelB = new JLabel(optionB);
        labelB.setFont(new Font("Arial", Font.PLAIN, 18));
        labelB.setForeground(Color.WHITE);

        optionRow.add(labelA);
        optionRow.add(Box.createHorizontalStrut(10));
        optionRow.add(toggle);
        optionRow.add(Box.createHorizontalStrut(10));
        optionRow.add(labelB);

        groupPanel.add(optionRow);
        
        return groupPanel;
    }

    // --- Hàm tạo Toggle Switch (Mô phỏng) ---
    private JToggleButton createToggleSwitchMock() {
        JToggleButton toggle = new JToggleButton() {
            // Tùy chỉnh để vẽ hình dạng công tắc gạt
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
        };

        toggle.setPreferredSize(new Dimension(60, 30));
        toggle.setOpaque(false);
        toggle.setContentAreaFilled(false);
        toggle.setBorderPainted(false);
        toggle.setFocusPainted(false);
        toggle.setSelected(true); // Mặc định bật option bên trái (Celsius/12-hour)

        return toggle;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SettingsUI().setVisible(true);
        });
    }
}