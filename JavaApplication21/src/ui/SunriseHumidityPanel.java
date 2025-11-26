package ui;

import components.RoundedPanel;
import config.ConfigManager;
import config.TimeDisplayConverter;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.border.EmptyBorder;

public class SunriseHumidityPanel extends JPanel {
    private JLabel sunLabel = new JLabel();
    private JLabel humLabel = new JLabel();

    public SunriseHumidityPanel() {
        setOpaque(false);
        setLayout(new GridLayout(1, 2, 12, 12));
        add(makeCard(sunLabel));
        add(makeCard(humLabel));
    }

    private RoundedPanel makeCard(JLabel l) {
        RoundedPanel p = new RoundedPanel(12, new Color(255, 255, 255, 20));
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(22, 26, 22, 26));
        l.setForeground(Color.WHITE);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    private String convertDateToTimeString(Date date) {
        // Chuyển Date -> LocalDateTime
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        // Convert theo setting 12h/24h
        return TimeDisplayConverter.convertFromDefault(ldt, ConfigManager.defaultTimeDisplay);
    }

    public void update(Date sunrise, Date sunset, float humidity) {
        String sunriseStr = convertDateToTimeString(sunrise);
        String sunsetStr  = convertDateToTimeString(sunset);

        sunLabel.setText(String.format("<html><b>Sunrise:</b> %s<br><b>Sunset:</b> %s</html>", sunriseStr, sunsetStr));
        humLabel.setText(String.format("<html><b>Humidity:</b> %.0f%%</html>", humidity));
    }
}
