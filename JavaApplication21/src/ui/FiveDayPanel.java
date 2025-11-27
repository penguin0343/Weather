package ui;

import components.RoundedPanel;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.Locale;
import db_connect.DbQuery;
import tools.ImageUtils;

public class FiveDayPanel extends RoundedPanel {

    private JPanel listPanel;

    public FiveDayPanel() {
        super(20, new Color(255, 255, 255, 25));
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(30, 11, 30, 11));

        JLabel title = new JLabel("5-Days Forecast", SwingConstants.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(title, BorderLayout.NORTH);

        listPanel = new JPanel(new GridLayout(1, 5, 5, 0));
        listPanel.setOpaque(false);
        listPanel.setBorder(new EmptyBorder(8, 2, 2, 2));
        add(listPanel, BorderLayout.CENTER);
    }
//

    public void updateForecast(List<DailyData> forecasts) {
        listPanel.removeAll();
        for (DailyData f : forecasts) {
            listPanel.add(createDayCard(f));
        }
        revalidate();
        repaint();
    }

    private JPanel createDayCard(DailyData f) {
        JPanel card = new JPanel();
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(4, 4, 4, 4));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextday = now.plusDays(f.dayOfWeek);
        DayOfWeek nextdayofweek = nextday.getDayOfWeek();
        JLabel dayLabel = new JLabel(nextdayofweek.getDisplayName(TextStyle.FULL, Locale.ENGLISH), SwingConstants.CENTER);
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //JLabel iconLabel = new JLabel(f.icon, SwingConstants.CENTER);
        JLabel iconLabel = new JLabel("", SwingConstants.CENTER); // tạo JLabel rỗng
        try {
            ImageIcon imgIcon = ImageUtils.loadPngIcon("/assets/" + f.icon + "_t@4x.png", 60, 60);
            iconLabel.setIcon(imgIcon);
        } catch (Exception e) {
            iconLabel.setText("☁"); // fallback nếu URL lỗi
            e.printStackTrace();
        }

        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 32));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tempLabel = new JLabel((int) f.maxTemp + "° / " + (int) f.minTemp + "°", SwingConstants.CENTER);
        tempLabel.setForeground(Color.WHITE);
        tempLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(dayLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(4));
        card.add(tempLabel);
        return card;
    }
}
