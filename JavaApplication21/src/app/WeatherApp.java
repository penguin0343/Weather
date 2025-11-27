package app;

import javax.swing.*;
import java.awt.*;
import components.*; 
import config.ConfigManager;
import java.util.HashMap;
import java.util.Map;
import ui.*;
import model.*;
import model.WeatherData;

public class WeatherApp implements SettingsConstants { 

    private JFrame frame;
    private CardLayout mainCards;
    private JPanel mainCardPanel;
    private MainWeatherPanel mainWeatherPanel;
    private Component searchPanel;
    private static WeatherApp instance;
    private SettingsContentPanel settingsPanel;
    private Map<String, WeatherData> wm;
    private WeatherData cwd;
    private WeatherData rwd;

    public WeatherApp(Map<String, WeatherData> wm) {
        String defaultLocation = ConfigManager.defaultLocation;
        cwd = wm.get(defaultLocation);
        String defaultTempMetric = ConfigManager.getCurentTempMetric();
        cwd.converttempToDefault(defaultTempMetric);
        rwd = cwd;
        instance = this;
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Weather App - OOP Assignment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); 
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
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
            System.out.println("Search city: " + city);});
        mainCardPanel.add(searchPanel, "SEARCH");
        settingsPanel = new SettingsContentPanel();
        mainCardPanel.add(settingsPanel, "SETTING");
        root.add(mainCardPanel, BorderLayout.CENTER);
        frame.add(root, BorderLayout.CENTER);
        frame.setVisible(true);
        mainCards.show(mainCardPanel, "MAIN");
    }

    private JPanel createSidebar() {
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
        btnSetting.addActionListener(e -> mainCards.show(mainCardPanel, "SETTING"));

        bar.add(btnMain);
        bar.add(btnSearch);
        bar.add(btnSetting);

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

    public void updateWeather(Map<String, WeatherData> weatherMap) {
        wm = new HashMap<String, WeatherData>();
//        Copy sang wm
        for (Map.Entry<String, WeatherData> entry : weatherMap.entrySet()) {
            wm.put(entry.getKey(), entry.getValue().clone());
        }
        
        String defaultLocation = ConfigManager.defaultLocation;
        String defaultTempMetric = ConfigManager.getCurentTempMetric();
        cwd = weatherMap.get(defaultLocation);
        cwd.converttempToDefault(defaultTempMetric);
        if (mainWeatherPanel != null) {
            mainWeatherPanel.updateWeather(cwd);
        }
    }

    public Map<String, WeatherData> getWeatherMap() {
        return wm;
    }
        
    public boolean changeWeatherLocation(String location) {
        Map<String, WeatherData> new_wm = new HashMap<String, WeatherData>();
//        Copy sang wm
        for (Map.Entry<String, WeatherData> entry : wm.entrySet()) {
            new_wm.put(entry.getKey(), entry.getValue().clone());
        }
        
        rwd = cwd;
        cwd = new_wm.get(location);
        String defaultTempMetric = ConfigManager.getCurentTempMetric();
        cwd.converttempToDefault(defaultTempMetric);
        if (rwd.location.trim().equalsIgnoreCase(location.trim())) {
            return false;
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
