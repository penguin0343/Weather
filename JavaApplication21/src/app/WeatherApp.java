package app;

import javax.swing.*;
import java.awt.*;
import components.*; // Import các components như RoundedPanel, IconMenuButton, SettingsConstants...
import config.ConfigManager;
import java.util.Map;
import ui.*;
import model.*;
import model.WeatherData;

public class WeatherApp implements SettingsConstants { // Thêm implements SettingsConstants để dùng hằng số màu sắc

    private JFrame frame;
    private CardLayout mainCards;
    private JPanel mainCardPanel;
    private MainWeatherPanel mainWeatherPanel;
    private Component searchPanel;
    private static WeatherApp instance;
    // Thêm SettingsContentPanel
    private SettingsContentPanel settingsPanel;
    private Map<String, WeatherData> wm;
    private WeatherData cwd;
    private WeatherData rwd;

    public WeatherApp(Map<String, WeatherData> wm) {
        String defaultLocation = ConfigManager.defaultLocation;
        cwd = wm.get(defaultLocation);
        rwd = cwd;
        instance = this;
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Weather App - OOP Assignment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); // Sử dụng hằng số
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        // Sử dụng GradientPanel (giả định đây là lớp đã được cung cấp)
        JPanel root = new GradientPanel();
        root.setLayout(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        root.setOpaque(false);

        JPanel sidebar = createSidebar();
        root.add(sidebar, BorderLayout.WEST);

        mainCards = new CardLayout();
        mainCardPanel = new JPanel(mainCards);
        mainCardPanel.setOpaque(false);

        mainWeatherPanel = new MainWeatherPanel();
        mainCardPanel.add(mainWeatherPanel, "MAIN");

        searchPanel = new SearchPanel(e -> {
            String city = e.getActionCommand();
            System.out.println("Search city: " + city);
            // TODO: gọi API hoặc load weather theo city
        });
        mainCardPanel.add(searchPanel, "SEARCH");

        // *** THAY ĐỔI TẠI ĐÂY: Thêm SettingsContentPanel ***  
        settingsPanel = new SettingsContentPanel();
        mainCardPanel.add(settingsPanel, "SETTING");

        root.add(mainCardPanel, BorderLayout.CENTER);
        frame.add(root, BorderLayout.CENTER);
        frame.setVisible(true);

        mainCards.show(mainCardPanel, "MAIN");
    }

    private JPanel createSidebar() {
        // Sử dụng RoundedPanel và hằng số
        RoundedPanel bar = new RoundedPanel(25, new Color(255, 255, 255, 40));
        bar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        bar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JButton btnMain = new IconMenuButton("☰", "Main");
        JButton btnSearch = new IconMenuButton("🔎", "Search");
        JButton btnSetting = new IconMenuButton("⚙", "Setting");

        Dimension btnSize = new Dimension(60, 60);
        btnMain.setPreferredSize(btnSize);
        btnSearch.setPreferredSize(btnSize);
        btnSetting.setPreferredSize(btnSize);

        btnMain.addActionListener(e -> mainCards.show(mainCardPanel, "MAIN"));
        btnSearch.addActionListener(e -> mainCards.show(mainCardPanel, "SEARCH"));
        // Liên kết nút setting với panel setting
        btnSetting.addActionListener(e -> mainCards.show(mainCardPanel, "SETTING"));

        bar.add(btnMain);
        bar.add(btnSearch);
        bar.add(btnSetting);

        // Thêm các ô trống (placeholder) để căn chỉnh các icon lên trên (giống SettingsSidebar)
        bar.add(new JPanel() {
            {
                setOpaque(false);
            }
        });
        bar.add(new JPanel() {
            {
                setOpaque(false);
            }
        });
        bar.add(new JPanel() {
            {
                setOpaque(false);
            }
        });

        return bar;
    }

    // Hàm này không còn cần thiết vì đã có SettingsContentPanel
    private JPanel createPlaceholderPanel(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.PLAIN, 24));
        l.setForeground(Color.WHITE);
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    public void updateWeather(Map<String, WeatherData> weatherMap) {
        wm = weatherMap;
        String defaultLocation = ConfigManager.defaultLocation;
        cwd = weatherMap.get(defaultLocation);
        cwd.convertTemp_K_to_C();
        if (mainWeatherPanel != null) {
            mainWeatherPanel.updateWeather(cwd);
        }
    }

    public boolean changeWeatherLocation(String location) {

        rwd = cwd;
        cwd = wm.get(location);
        if (rwd.location.trim().equalsIgnoreCase(location.trim())) {
            return false;
        }
        if (cwd.currentTemp > 200) {
            cwd.convertTemp_K_to_C();
        }
        if (mainWeatherPanel != null) {
            mainWeatherPanel.updateWeather(cwd);
        }
        return true;
    }

    public boolean changePanel() {
        
        boolean result = true;
        String location = ConfigManager.defaultLocation;

        result = changeWeatherLocation(location);
        mainCards.show(mainCardPanel, "MAIN");
        return result;
    }

    public WeatherData getCurrentWeatherData() {
        return cwd;
    }

    public WeatherData getRecentWeatherData() {
        return rwd;
    }

    public static WeatherApp getInstance() {
        return instance;
    }
}
