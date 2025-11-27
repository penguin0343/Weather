package ui;


import components.RoundedPanel;
import config.ConfigManager;
import config.TempMetricConverter;
import config.TimeDisplayConverter;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.swing.border.EmptyBorder;
import java.util.List;
import tools.ImageUtils;

public class HourlyPanel extends JPanel {

    public HourlyPanel() {
    setOpaque(false);
    setBorder(new EmptyBorder(0, 0, 12, 0));
}

    public void update(List<HourlyData> list) {
        removeAll();
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        for (HourlyData h : list) {
            add(makeCard(h));
            add(Box.createHorizontalStrut(12));
        }
        revalidate();
        repaint();
    }

    private JComponent makeCard(HourlyData h) {
        RoundedPanel card = new RoundedPanel(12, new Color(255, 255, 255, 25));
        card.setPreferredSize(new Dimension(120, 140));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(8, 8, 8, 8));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now_plus_hours = now.plusHours(h.time);
        int defaultHourDisplay = ConfigManager.defaultTimeDisplay;
        String timedisplay = TimeDisplayConverter.convertFromDefault(now_plus_hours, defaultHourDisplay);
        JLabel time = new JLabel(timedisplay, SwingConstants.CENTER);
        JLabel icon = new JLabel("", SwingConstants.CENTER); 
        try {
            ImageIcon imgIcon = ImageUtils.loadPngIcon("/assets/" + h.icon + "_t@4x.png", 60, 60);
            icon.setIcon(imgIcon);
        } catch (Exception e) {
            icon.setText("☁"); // fallback nếu URL lỗi
            e.printStackTrace();
        }

        JLabel temp = new JLabel(ConfigManager.formatTemperature(h.temp), SwingConstants.CENTER);
        for (JLabel l : new JLabel[]{time, icon, temp}) {
            l.setForeground(Color.WHITE);
            l.setAlignmentX(CENTER_ALIGNMENT);
        }
        icon.setFont(new Font("SansSerif", Font.PLAIN, 32));
        temp.setFont(new Font("SansSerif", Font.BOLD, 18));

        card.add(time);
        card.add(Box.createVerticalStrut(8));
        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(temp);
        return card;
    }
}
