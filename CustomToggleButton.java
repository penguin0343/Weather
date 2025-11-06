import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

// Interface để thông báo sự thay đổi trạng thái
interface ToggleChangeListener {
    void onToggleChanged(boolean isOn);
}

public class CustomToggleButton extends JPanel {

    private boolean isOn; // Trạng thái hiện tại: true = Fahrenheit, false = Celsius
    private int knobX; // Vị trí X của nút tròn
    private int startDragX; // Vị trí X khi bắt đầu kéo
    private int knobWidth = 24; // Chiều rộng của nút tròn
    private int knobHeight = 24; // Chiều cao của nút tròn
    private int padding = 2; // Khoảng đệm từ viền
    
    private List<ToggleChangeListener> listeners = new ArrayList<>();

    public CustomToggleButton() {
        this.isOn = false; // Mặc định là Celsius (trái)
        this.knobX = padding; // Vị trí ban đầu của nút tròn
        
        // Thiết lập kích thước ưa thích cho thanh trượt
        setPreferredSize(new Dimension(50, 28)); // Chiều rộng và chiều cao của toàn bộ thanh trượt
        setOpaque(false); // Không vẽ nền JPanel này để chỉ vẽ các thành phần tùy chỉnh

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startDragX = e.getX(); // Ghi lại vị trí bắt đầu kéo
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Khi nhả chuột, xác định trạng thái cuối cùng
                int centerX = getWidth() / 2;
                if (knobX + knobWidth / 2 > centerX) {
                    setOn(true); // Nếu nút tròn vượt quá giữa, đặt là ON (Fahrenheit)
                } else {
                    setOn(false); // Ngược lại, đặt là OFF (Celsius)
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - startDragX;
                int newKnobX = knobX + deltaX;

                // Giới hạn vị trí nút tròn trong phạm vi của thanh trượt
                newKnobX = Math.max(padding, newKnobX); // Không đi quá sang trái
                newKnobX = Math.min(getWidth() - knobWidth - padding, newKnobX); // Không đi quá sang phải

                // Cập nhật vị trí nút tròn và vẽ lại
                knobX = newKnobX;
                startDragX = e.getX(); // Cập nhật vị trí bắt đầu kéo để tính delta chính xác hơn
                repaint();
            }
        });
    }

    // Phương thức vẽ tùy chỉnh
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Vẽ nền của thanh trượt (hình bầu dục)
        if (isOn) {
            g2d.setColor(new Color(100, 180, 255)); // Màu xanh sáng khi ON (Fahrenheit)
        } else {
            g2d.setColor(new Color(150, 150, 150)); // Màu xám khi OFF (Celsius)
        }
        g2d.fillRoundRect(padding, padding, width - 2 * padding, height - 2 * padding, height - 2 * padding, height - 2 * padding);

        // Vẽ nút tròn (Knob)
        g2d.setColor(Color.WHITE);
        g2d.fillOval(knobX, padding, knobWidth, knobHeight);
    }

    // Getter và Setter cho trạng thái
    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        if (this.isOn != isOn) {
            this.isOn = isOn;
            // Di chuyển nút tròn đến vị trí cuối cùng
            this.knobX = isOn ? (getWidth() - knobWidth - padding) : padding;
            repaint();
            notifyToggleChanged(); // Thông báo cho các listener
        }
    }
    
    // Thêm listener
    public void addToggleChangeListener(ToggleChangeListener listener) {
        listeners.add(listener);
    }
    
    // Thông báo sự kiện thay đổi
    private void notifyToggleChanged() {
        for (ToggleChangeListener listener : listeners) {
            listener.onToggleChanged(isOn);
        }
    }
}