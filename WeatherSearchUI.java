import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WeatherSearchUI extends JFrame {

    // Kích thước và Màu sắc cơ bản
    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    private static final Color PRIMARY_COLOR = new Color(50, 70, 200); // Màu xanh dương đậm
    private static final Color CARD_COLOR = new Color(100, 120, 250, 150); // Màu xanh dương nhạt trong suốt
    private static final Color SEARCH_BAR_COLOR = new Color(255, 255, 255, 50); // Màu trắng trong suốt cho thanh tìm kiếm

    public WeatherSearchUI() {
        setTitle("Weather Search UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());

        // Thiết lập màu nền chung cho toàn bộ cửa sổ
        getContentPane().setBackground(PRIMARY_COLOR);

        // Tạo phần chính của giao diện
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_COLOR);

        // --- 1. Tạo thanh điều hướng bên trái ---
        JPanel navPanel = createNavigationPanel();

        // --- 2. Tạo nội dung chính (Search bar và các thẻ) ---
        JPanel contentPanel = createContentPanel();

        // Thêm các phần vào JFrame
        add(navPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
    }

    // --- 1. Bảng điều hướng (Navigation Panel) ---
    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(60, HEIGHT)); // Độ rộng cố định
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Padding

        // Biểu tượng Menu
        panel.add(createIconLabel("☰", 30));
        panel.add(Box.createVerticalStrut(HEIGHT / 6)); // Khoảng trống
        // Biểu tượng Vị trí
        panel.add(createIconLabel("📍", 30));
        panel.add(Box.createVerticalGlue()); // Đẩy phần dưới cùng xuống
        // Biểu tượng Cài đặt
        panel.add(createIconLabel("⚙", 30));

        return panel;
    }

    private JLabel createIconLabel(String icon, int size) {
        JLabel label = new JLabel(icon);
        label.setFont(new Font("Arial", Font.BOLD, size));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Canh giữa icon
        label.setBorder(new EmptyBorder(10, 0, 10, 0)); // Padding
        return label;
    }

    // --- 2. Nội dung chính (Search Bar và Thẻ) ---
    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 20, 20, 20)); // Padding

        // 2.1. Thanh Tìm Kiếm
        panel.add(createSearchBar());
        panel.add(Box.createVerticalStrut(30));

        // 2.2. Kết quả (Result)
        panel.add(createHeaderLabel("Result:"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createWeatherCard("Ho Chi Minh", "Max: 35°C Min: 19°C", "Heavy rain", "⛈"));
        panel.add(Box.createVerticalStrut(40));

        // 2.3. Vị trí hiện tại (Current Location)
        panel.add(createHeaderLabel("Current location:"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createWeatherCard("Ha Noi", "Max: 35°C Min: 19°C", "Mostly sunny", "🌤"));

        panel.add(Box.createVerticalGlue()); // Đẩy các thẻ lên trên

        return panel;
    }

    private JPanel createSearchBar() {
        // Sử dụng JLayeredPane để xếp chồng icon, text và nền
        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.setMaximumSize(new Dimension(WIDTH - 150, 50));
        searchBar.setBackground(SEARCH_BAR_COLOR);
        // Tạo góc bo tròn (khó làm trực tiếp với Swing, ta chỉ mô phỏng nền)
        searchBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1, true), // Viền trắng mờ
            new EmptyBorder(0, 10, 0, 10)
        ));

        // Icon kính lúp (trái)
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Arial", Font.BOLD, 18));
        searchIcon.setForeground(Color.WHITE);
        searchBar.add(searchIcon, BorderLayout.WEST);

        // Text "SEARCH" (giữa)
        JLabel searchLabel = new JLabel("SEARCH");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 18));
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
        searchBar.add(searchLabel, BorderLayout.CENTER);

        // Icon mũi tên (phải)
        JLabel arrowIcon = new JLabel(">");
        arrowIcon.setFont(new Font("Arial", Font.BOLD, 18));
        arrowIcon.setForeground(Color.WHITE);
        searchBar.add(arrowIcon, BorderLayout.EAST);

        return searchBar;
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Canh lề trái
        return label;
    }

    private JPanel createWeatherCard(String city, String minMax, String condition, String icon) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setMaximumSize(new Dimension(WIDTH - 150, 100));
        card.setBackground(CARD_COLOR);
        card.setBorder(new EmptyBorder(15, 20, 15, 20)); // Padding bên trong

        // Phần thông tin bên trái (City và Min/Max)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false); // Làm cho infoPanel trong suốt để hiển thị màu nền card

        JLabel cityLabel = new JLabel(city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 22));
        cityLabel.setForeground(Color.WHITE);
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel minMaxLabel = new JLabel(minMax);
        minMaxLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        minMaxLabel.setForeground(Color.LIGHT_GRAY);
        minMaxLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(cityLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(minMaxLabel);

        card.add(infoPanel, BorderLayout.WEST);

        // Phần thông tin bên phải (Condition và Icon)
        JPanel conditionPanel = new JPanel();
        conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
        conditionPanel.setOpaque(false); // Trong suốt
        conditionPanel.setBorder(new EmptyBorder(0, 30, 0, 0)); // Tạo khoảng trống

        // Icon thời tiết
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 40));
        iconLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        iconLabel.setForeground(Color.WHITE);

        // Mô tả thời tiết
        JLabel conditionLabel = new JLabel(condition);
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        conditionLabel.setForeground(Color.WHITE);
        conditionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        conditionPanel.add(iconLabel);
        conditionPanel.add(conditionLabel);

        card.add(conditionPanel, BorderLayout.EAST);

        return card;
    }

    public static void main(String[] args) {
        // Đảm bảo UI được tạo trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new WeatherSearchUI().setVisible(true);
        });
    }
}