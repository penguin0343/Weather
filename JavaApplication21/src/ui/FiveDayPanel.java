package ui;

import components.RoundedPanel;
import model.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class FiveDayPanel extends RoundedPanel {
        private JPanel listPanel;

        public FiveDayPanel() {
            super(20, new Color(255, 255, 255, 25));
            setLayout(new BorderLayout());
            setOpaque(false);
            setBorder(new EmptyBorder(12, 12, 12, 12));

            JLabel title = new JLabel("5-Days Forecast", SwingConstants.LEFT);
            title.setForeground(Color.WHITE);
            title.setFont(new Font("SansSerif", Font.BOLD, 14));
            add(title, BorderLayout.NORTH);

            listPanel = new JPanel(new GridLayout(1, 5, 10, 0));
            listPanel.setOpaque(false);
            listPanel.setBorder(new EmptyBorder(8, 0, 0, 0));
            add(listPanel, BorderLayout.CENTER);
        }

        public void updateForecast(List<DailyData> forecasts) {
            listPanel.removeAll();
            for (DailyData f : forecasts)
                listPanel.add(createDayCard(f));
            revalidate();
            repaint();
        }

        private JPanel createDayCard(DailyData f) {
            JPanel card = new JPanel();
            card.setOpaque(false);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new EmptyBorder(4, 4, 4, 4));

            JLabel dayLabel = new JLabel(f.dayOfWeek, SwingConstants.CENTER);
            dayLabel.setForeground(Color.WHITE);
            dayLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel iconLabel = new JLabel(f.icon, SwingConstants.CENTER);
            iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 32)); // to hơn
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel tempLabel = new JLabel((int) f.maxTemp + "° / " + (int) f.minTemp + "°", SwingConstants.CENTER);
            tempLabel.setForeground(Color.WHITE);
            tempLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(dayLabel);
            card.add(Box.createVerticalStrut(6));
            card.add(iconLabel);
            card.add(Box.createVerticalStrut(6));
            card.add(tempLabel);
            return card;
        }
    }
