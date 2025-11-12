package ui;

import components.RoundedPanel;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
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
            p.setBorder(new EmptyBorder(12, 12, 12, 12));
            l.setForeground(Color.WHITE);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            p.add(l, BorderLayout.CENTER);
            return p;
        }

        public void update(Date sunrise, Date sunset, int humidity) {
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
            sunLabel.setText(String.format("<html><b>Sunrise:</b> %s<br><b>Sunset:</b> %s</html>", fmt.format(sunrise), fmt.format(sunset)));
            humLabel.setText(String.format("<html><b>Humidity:</b> %d%%</html>", humidity));
        }
    }
