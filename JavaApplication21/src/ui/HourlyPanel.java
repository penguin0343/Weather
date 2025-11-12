package ui;

import components.RoundedPanel;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class HourlyPanel extends JPanel {
        public void update(List<HourlyData> list) {
            removeAll();
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            if (list == null) list = Arrays.asList(new HourlyData("Now", 19, "☁"));
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

            JLabel time = new JLabel(h.time, SwingConstants.CENTER);
            JLabel icon = new JLabel(h.icon, SwingConstants.CENTER);
            JLabel temp = new JLabel(Math.round(h.temp) + "°C", SwingConstants.CENTER);
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