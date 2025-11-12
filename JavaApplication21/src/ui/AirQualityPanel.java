package ui;

import components.RoundedPanel;
import model.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class AirQualityPanel extends RoundedPanel {
        private JLabel titleLabel, aqiLabel;

        public AirQualityPanel() {
            super(20, new Color(255, 255, 255, 25));
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(12, 16, 12, 16));

            titleLabel = new JLabel("AIR QUALITY");
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            aqiLabel = new JLabel("2 - Moderate (AQI: 80)");
            aqiLabel.setForeground(Color.WHITE);
            aqiLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            aqiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(titleLabel);
            add(Box.createVerticalStrut(6));
            add(aqiLabel);
        }

        public void update(int aqi) {
            String risk;
            if (aqi <= 50) risk = "1 - Good";
            else if (aqi <= 100) risk = "2 - Moderate";
            else if (aqi <= 150) risk = "3 - Low Health Risk";
            else if (aqi <= 200) risk = "4 - Unhealthy";
            else risk = "5 - Very Unhealthy";
            aqiLabel.setText(risk + " (AQI: " + aqi + ")");
        }
    }
