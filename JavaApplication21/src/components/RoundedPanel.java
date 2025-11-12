package components;

import java.awt.*;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private int r;
    private Color bg;

    public RoundedPanel(int r, Color bg) {
        this.r = r;
        this.bg = bg;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
    }
}
