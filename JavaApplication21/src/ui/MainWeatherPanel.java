package ui;

import components.*;
import config.ConfigManager;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWeatherPanel extends JPanel {
    private HeaderPanel header;
    private HourlyPanel hourlyPanel;
    private FiveDayPanel fiveDayPanel;
    private AirQualityPanel aqPanel;
    private SunriseHumidityPanel sunHumPanel;

    public MainWeatherPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JPanel left = createCenterLeft();
        JPanel right = createCenterRight();

        top.add(left, BorderLayout.CENTER);
        top.add(right, BorderLayout.EAST);
        add(top, BorderLayout.CENTER);
    }

    private JPanel createCenterLeft() {
        JPanel left = new RoundedPanel(20, new Color(255, 255, 255, 20));
        left.setPreferredSize(new Dimension(740, 560));
        left.setLayout(new BorderLayout());
        left.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        header = new HeaderPanel();
        left.add(header, BorderLayout.NORTH);

        hourlyPanel = new HourlyPanel();
        JScrollPane scroll = new JScrollPane(hourlyPanel,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        left.add(scroll, BorderLayout.SOUTH);
        return left;
    }

    private JPanel createCenterRight() {
        JPanel rightWrapper = new JPanel(new GridBagLayout());
        rightWrapper.setOpaque(false);
        rightWrapper.setPreferredSize(new Dimension(440, 560));

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        fiveDayPanel = new FiveDayPanel();
        aqPanel = new AirQualityPanel();
        sunHumPanel = new SunriseHumidityPanel();

        right.add(fiveDayPanel);
        right.add(Box.createVerticalStrut(24));
        right.add(aqPanel);
        right.add(Box.createVerticalStrut(24));
        right.add(sunHumPanel);
        //right.add(Box.createVerticalGlue());
        rightWrapper.add(right, new GridBagConstraints());
        return rightWrapper;
    }

    public void updateWeather(WeatherData d) {
        header.update(d);
        hourlyPanel.update(d.hourly);
        fiveDayPanel.updateForecast(d.daily);
        aqPanel.update(d.airQualityIndex);
//        sunHumPanel.update(d.sunrise, d.sunset, d.humidity);
    }
}
