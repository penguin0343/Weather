package ui;

import config.ConfigManager;
import config.TempMetricConverter;
import model.*;
import javax.swing.*;
import java.awt.*;
import tools.ImageUtils;

public class HeaderPanel extends JPanel {

    JLabel locationLabel = new JLabel();
    JLabel statusLabel = new JLabel();
    JLabel tempLabel = new JLabel();
    JLabel minMaxLabel = new JLabel();
    JLabel weatherIcon = new JLabel();

    public HeaderPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (JLabel l : new JLabel[]{locationLabel, statusLabel, tempLabel, minMaxLabel, weatherIcon}) {
            l.setAlignmentX(CENTER_ALIGNMENT);
        }
        locationLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        tempLabel.setFont(new Font("SansSerif", Font.BOLD, 72));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        minMaxLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        weatherIcon.setFont(new Font("SansSerif", Font.PLAIN, 70));
        for (JLabel l : new JLabel[]{locationLabel, statusLabel, tempLabel, minMaxLabel, weatherIcon}) {
            l.setForeground(Color.WHITE);
        }

        add(Box.createVerticalStrut(35));
        add(locationLabel);
        add(Box.createVerticalStrut(8));
        add(statusLabel);
        add(Box.createVerticalStrut(12));
        add(tempLabel);
        add(Box.createVerticalStrut(8));
        add(minMaxLabel);
        add(weatherIcon);
        add(Box.createVerticalStrut(12));

    }

    public void update(WeatherData d) {
        locationLabel.setText(d.location);
        statusLabel.setText(d.description);

        tempLabel.setText(ConfigManager.formatTemperature(d.currentTemp));
        minMaxLabel.setText("Max: " + ConfigManager.formatTemperature(d.maxTemp) + "   Min: " + ConfigManager.formatTemperature(d.minTemp));
//        String desc = d.description.toLowerCase();
//        if (desc.contains("sun")) weatherIcon.setText("☀");
//        else if (desc.contains("cloud")) weatherIcon.setText("☁");
//        else if (desc.contains("rain")) weatherIcon.setText("🌧");
//        else if (desc.contains("storm")) weatherIcon.setText("⛈");
        //weatherIcon.setText(d.icon);
        try {
            ImageIcon imgIcon = ImageUtils.loadPngIcon("/assets/" + d.icon + "_t@4x.png", 120, 120);
            weatherIcon.setIcon(imgIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
