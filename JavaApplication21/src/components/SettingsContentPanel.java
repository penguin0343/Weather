import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsContentPanel extends JPanel implements SettingsConstants {

    public SettingsContentPanel() {
        setBackground(PRIMARY_COLOR);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(30, 50, 30, 30)); 

        // 1. Tiêu đề SETTING
        JLabel titleLabel = new JLabel("SETTING");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titleLabel);

        // Đường kẻ ngang mô phỏng
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(150, 150, 255));
        separator.setBackground(PRIMARY_COLOR);
        separator.setMaximumSize(new Dimension(FRAME_WIDTH - 200, 5)); 
        add(separator);
        add(Box.createVerticalStrut(30));

        // 2. Đơn vị Nhiệt độ
        add(createSettingGroup("Temperature Unit", "Celsius/°C", "Fahrenheit/°F"));
        add(Box.createVerticalStrut(40));

        // 3. Định dạng Thời gian
        add(createSettingGroup("Time format", "12-hour", "24-hour"));
        
        add(Box.createVerticalGlue()); 
    }
    
    // Hàm tạo nhóm cài đặt (Tiêu đề, Tùy chọn A, Tùy chọn B)
    private JPanel createSettingGroup(String title, String optionA, String optionB) {
        JPanel groupPanel = new JPanel();
        groupPanel.setOpaque(false);
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupPanel.setMaximumSize(new Dimension(FRAME_WIDTH - 200, groupPanel.getPreferredSize().height));

        // Tiêu đề nhóm
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titleLabel.setForeground(TEXT_COLOR);
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
        labelA.setForeground(TEXT_COLOR);
        
        // Mô phỏng Toggle Switch (Sử dụng class riêng)
        ToggleSwitch toggle = new ToggleSwitch();
        
        // Tùy chọn B
        JLabel labelB = new JLabel(optionB);
        labelB.setFont(new Font("Arial", Font.PLAIN, 18));
        labelB.setForeground(TEXT_COLOR);

        optionRow.add(labelA);
        optionRow.add(Box.createHorizontalStrut(10));
        optionRow.add(toggle);
        optionRow.add(Box.createHorizontalStrut(10));
        optionRow.add(labelB);
        
        optionRow.setMaximumSize(new Dimension(optionRow.getPreferredSize().width, optionRow.getPreferredSize().height));
        groupPanel.add(optionRow);
        
        return groupPanel;
    }
}