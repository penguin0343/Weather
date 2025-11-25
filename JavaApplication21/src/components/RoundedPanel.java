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

    public void removeSectionsComponents() {
        if (this.getComponentCount() > 0) {
            // Lấy component cuối cùng
            int n_components = this.getComponentCount();
            for (int i = n_components - 1; i >= 2; i--) {
                Component lastComponent = this.getComponent(i);
                // Xóa component đó
                this.remove(lastComponent);
                // Cập nhật lại giao diện
            }
            this.revalidate();
            this.repaint();
        }
    }
}
