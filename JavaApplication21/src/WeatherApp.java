import javax.swing.*;
import java.awt.*;
import components.*;
import ui.*;
import model.*;

public class WeatherApp {
    private JFrame frame;
    private CardLayout mainCards;
    private JPanel mainCardPanel;
    private MainWeatherPanel mainWeatherPanel;

    public WeatherApp() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Weather App - OOP Assignment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        //Co dinh size
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
        mainCardPanel.add(createPlaceholderPanel("Search (to implement)"), "SEARCH");
        mainCardPanel.add(createPlaceholderPanel("Setting (to implement)"), "SETTING");

        root.add(mainCardPanel, BorderLayout.CENTER);
        frame.add(root, BorderLayout.CENTER);
        frame.setVisible(true);

        mainCards.show(mainCardPanel, "MAIN");
    }

    private JPanel createSidebar() {
        JPanel bar = new JPanel();
        bar.setPreferredSize(new Dimension(96, 0));
        bar.setOpaque(false);
        bar.setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnMain = new IconMenuButton("☰", "Main");
        JButton btnSearch = new IconMenuButton("🔎", "Search");
        JButton btnSetting = new IconMenuButton("⚙", "Setting");

        btnMain.addActionListener(e -> mainCards.show(mainCardPanel, "MAIN"));
        btnSearch.addActionListener(e -> mainCards.show(mainCardPanel, "SEARCH"));
        btnSetting.addActionListener(e -> mainCards.show(mainCardPanel, "SETTING"));

        bar.add(btnMain);
        bar.add(btnSearch);
        bar.add(btnSetting);

        return bar;
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setFont(new Font("SansSerif", Font.PLAIN, 24));
        l.setForeground(Color.WHITE);
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    public void updateWeather(WeatherData data) {
        mainWeatherPanel.updateWeather(data);
    }
}
