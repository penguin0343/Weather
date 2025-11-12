package components;

import java.awt.*;
import javax.swing.*;

public class IconMenuButton extends JButton {
    public IconMenuButton(String text, String tip) {
        super(text);
        setToolTipText(tip);
        setFocusPainted(false);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorder(null);
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.PLAIN, 24));
    }
}
