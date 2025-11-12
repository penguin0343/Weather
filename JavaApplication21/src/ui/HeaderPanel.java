package ui;

import model.*;
import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    JLabel locationLabel = new JLabel("Ha Noi, Viet Nam", SwingConstants.CENTER);
    JLabel statusLabel = new JLabel("Mostly sunny", SwingConstants.CENTER);
    JLabel tempLabel = new JLabel("19°C", SwingConstants.CENTER);
    JLabel minMaxLabel = new JLabel("Max: 35°C   Min: 19°C", SwingConstants.CENTER);

    public HeaderPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (JLabel l : new JLabel[]{locationLabel, statusLabel, tempLabel, minMaxLabel})
            l.setAlignmentX(CENTER_ALIGNMENT);
        locationLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        tempLabel.setFont(new Font("SansSerif", Font.BOLD, 72));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        minMaxLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (JLabel l : new JLabel[]{locationLabel, statusLabel, tempLabel, minMaxLabel})
            l.setForeground(Color.WHITE);

        add(locationLabel);
        add(Box.createVerticalStrut(8));
        add(statusLabel);
        add(Box.createVerticalStrut(8));
        add(tempLabel);
        add(Box.createVerticalStrut(8));
        add(minMaxLabel);
    }

    public void update(WeatherData d) {
        locationLabel.setText(d.location);
        statusLabel.setText(d.description);
        tempLabel.setText(Math.round(d.currentTemp) + "°C");
        minMaxLabel.setText("Max: " + Math.round(d.maxTemp) + "°C   Min: " + Math.round(d.minTemp) + "°C");
    }
}
