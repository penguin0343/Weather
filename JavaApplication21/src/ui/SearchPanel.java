package ui;

import app.WeatherApp;
import components.RoundedPanel;
import config.ConfigManager;
import app.WeatherApp;
import model.WeatherData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Stack;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jdk.jshell.SourceCodeAnalysis;
import tools.ImageUtils;

public class SearchPanel extends JPanel {

    private static final List<String> DATA = Arrays.asList(
            "Hanoi", "Danang", "HCM"
    );
    private JTextField searchField;
    private ActionListener callback;
    private String recent;
    private String current;
    JPopupMenu suggestionPopUp;
    RoundedPanel wrapper;
    private static SearchPanel instance;

    public SearchPanel(ActionListener callback) {
        this.callback = callback;

        setOpaque(false);
        setLayout(new GridBagLayout());
        instance = this;

        wrapper = new RoundedPanel(25, new Color(255, 255, 255, 25));
        wrapper.setPreferredSize(new Dimension(1000, 550));
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // ===== SEARCH BAR =====
        RoundedPanel searchBar = new RoundedPanel(20, new Color(255, 255, 255, 35));
        searchBar.setLayout(new BorderLayout());
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel searchIcon = new JLabel("🔍 ");
        searchIcon.setFont(new Font("SansSerif", Font.BOLD, 22));
        searchIcon.setForeground(Color.WHITE);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setOpaque(false);
        searchField.setForeground(Color.WHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        suggestionPopUp = new JPopupMenu();
        suggestionPopUp.setBorder(BorderFactory.createEmptyBorder());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> showSuggestions());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> showSuggestions());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void showSuggestions() {
                String input = searchField.getText().trim().toLowerCase();
                suggestionPopUp.setVisible(false);
                suggestionPopUp.removeAll();

                if (input.isEmpty()) {
                    return;
                }

                for (String s : DATA) {
                    if (s.toLowerCase().contains(input)) {
                        JMenuItem item = new JMenuItem(s);

                        item.setFont(new Font("SansSerif", Font.PLAIN, 18));
                        item.setBackground(new Color(255, 255, 255));
                        item.setForeground(Color.BLACK);

                        item.addActionListener(e -> {
                            searchField.setText(s);
                        });

                        suggestionPopUp.add(item);
                    }

                }
                if (suggestionPopUp.getComponentCount() > 0) {
                    int width = searchField.getWidth();
                    int count = suggestionPopUp.getComponentCount();
                    int basicHeight = 50;
                    int height = basicHeight * count;
                    suggestionPopUp.setPreferredSize(new Dimension(width, height));
                    suggestionPopUp.show(searchField, 0, searchField.getHeight());
                    searchField.requestFocusInWindow();
                }
            }
        });

        searchField.addActionListener(e -> {
            changeScreenFromSearch();
        });

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        iconPanel.setOpaque(false);
        iconPanel.add(searchIcon);

        searchBar.add(iconPanel, BorderLayout.WEST);
        searchBar.add(searchField, BorderLayout.CENTER);

        wrapper.add(searchBar);
        wrapper.add(Box.createVerticalStrut(20));

        // ===== SECTIONS =====
        WeatherData wd = WeatherApp.getInstance().getCurrentWeatherData();

        wrapper.add(createSection("CURRENT LOCATION",
                makeCityCard(wd.location, wd.description, wd.icon, (int) wd.maxTemp, (int) wd.minTemp)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(wrapper, gbc);
    }

    private JPanel createSection(String titleText, RoundedPanel card) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setOpaque(false);
        sectionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 5, 6, 0));

        sectionPanel.add(title);
        sectionPanel.add(card);

        return sectionPanel;
    }

    private RoundedPanel makeCityCard(String city, String status, String icon, int max, int min) {
        RoundedPanel card = new RoundedPanel(18, new Color(255, 255, 255, 45));
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        card.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel cityLabel = new JLabel(city);
        cityLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        cityLabel.setForeground(Color.WHITE);

        JLabel tempLabel = new JLabel("Max: " + ConfigManager.formatTemperature(max) + "   Min: " + ConfigManager.formatTemperature(min));
        tempLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tempLabel.setForeground(Color.WHITE);

        left.add(cityLabel);
        left.add(tempLabel);

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);

        JLabel weatherIcon = new JLabel("", SwingConstants.CENTER); // tạo JLabel rỗng
        try {
            ImageIcon imgIcon = ImageUtils.loadPngIcon("/assets/" + icon + "_t@4x.png", 60, 60);
            weatherIcon.setIcon(imgIcon);
        } catch (Exception e) {
            weatherIcon.setText("☁"); // fallback nếu URL lỗi
            e.printStackTrace();
        }
        weatherIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        right.add(weatherIcon, BorderLayout.CENTER);
        right.add(statusLabel, BorderLayout.SOUTH);

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submitSearch(city);
            }
        });

        return card;
    }

    private void submitSearch() {
        submitSearch(searchField.getText().trim());
    }

    private void submitSearch(String city) {
        if (!city.isEmpty()) {
            changeScreenFromSection(city);
        }
    }

    public String getCityInput() {
        return searchField.getText().trim();
    }

    public void changeScreenFromSearch() {
        suggestionPopUp.setVisible(false);
        String selectedValue = searchField.getText();

        boolean found = false;

        for (String i : DATA) {
            if (i.equalsIgnoreCase(selectedValue)) {
                found = true;
                break;
            }
        }

        if (!found) {
            return;
        }

        ConfigManager.defaultLocation = selectedValue;
        boolean result = WeatherApp.getInstance().changePanel();
        if (result) {
            WeatherData wd = WeatherApp.getInstance().getRecentWeatherData();
            wrapper.removeSectionsComponents();
            wrapper.add(createSection("RECENT",
                    makeCityCard(wd.location, wd.description, wd.icon, (int) wd.maxTemp, (int) wd.minTemp)
            ));

            wrapper.add(Box.createVerticalStrut(20));

            wd = WeatherApp.getInstance().getCurrentWeatherData();
            wrapper.add(createSection("CURRENT LOCATION",
                    makeCityCard(wd.location, wd.description, wd.icon, (int) wd.maxTemp, (int) wd.minTemp)
            ));

            wrapper.add(Box.createVerticalStrut(20));
        }

    }

    public void changeScreenFromSection(String location) {
        ConfigManager.defaultLocation = location;
        boolean result = WeatherApp.getInstance().changePanel();
        if (result) {
            WeatherData wd = WeatherApp.getInstance().getRecentWeatherData();
            wrapper.removeSectionsComponents();
            wrapper.add(createSection("RECENT",
                    makeCityCard(wd.location, wd.description, wd.icon, (int) wd.maxTemp, (int) wd.minTemp)
            ));

            wrapper.add(Box.createVerticalStrut(20));

            wd = WeatherApp.getInstance().getCurrentWeatherData();
            wrapper.add(createSection("CURRENT LOCATION",
                    makeCityCard(wd.location, wd.description, wd.icon, (int) wd.maxTemp, (int) wd.minTemp)
            ));

            wrapper.add(Box.createVerticalStrut(20));
        }

    }

    public void resetWrapper() {
        removeAll();    // Xóa tất cả component con
        revalidate();    // Cập nhật layout
        repaint();
        
        setOpaque(false);
        setLayout(new GridBagLayout());

        wrapper = new RoundedPanel(25, new Color(255, 255, 255, 25));
        wrapper.setPreferredSize(new Dimension(1000, 550));
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // ===== SEARCH BAR =====
        RoundedPanel searchBar = new RoundedPanel(20, new Color(255, 255, 255, 35));
        searchBar.setLayout(new BorderLayout());
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel searchIcon = new JLabel("🔍 ");
        searchIcon.setFont(new Font("SansSerif", Font.BOLD, 22));
        searchIcon.setForeground(Color.WHITE);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setOpaque(false);
        searchField.setForeground(Color.WHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        suggestionPopUp = new JPopupMenu();
        suggestionPopUp.setBorder(BorderFactory.createEmptyBorder());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> showSuggestions());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> showSuggestions());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void showSuggestions() {
                String input = searchField.getText().trim().toLowerCase();
                suggestionPopUp.setVisible(false);
                suggestionPopUp.removeAll();

                if (input.isEmpty()) {
                    return;
                }

                for (String s : DATA) {
                    if (s.toLowerCase().contains(input)) {
                        JMenuItem item = new JMenuItem(s);

                        item.setFont(new Font("SansSerif", Font.PLAIN, 18));
                        item.setBackground(new Color(255, 255, 255));
                        item.setForeground(Color.BLACK);

                        item.addActionListener(e -> {
                            searchField.setText(s);
                        });

                        suggestionPopUp.add(item);
                    }

                }
                if (suggestionPopUp.getComponentCount() > 0) {
                    int width = searchField.getWidth();
                    int count = suggestionPopUp.getComponentCount();
                    int basicHeight = 50;
                    int height = basicHeight * count;
                    suggestionPopUp.setPreferredSize(new Dimension(width, height));
                    suggestionPopUp.show(searchField, 0, searchField.getHeight());
                    searchField.requestFocusInWindow();
                }
            }
        });

        searchField.addActionListener(e -> {
            changeScreenFromSearch();
        });

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        iconPanel.setOpaque(false);
        iconPanel.add(searchIcon);

        searchBar.add(iconPanel, BorderLayout.WEST);
        searchBar.add(searchField, BorderLayout.CENTER);

        wrapper.add(searchBar);
        wrapper.add(Box.createVerticalStrut(20));

        // ===== SECTIONS =====
        WeatherData wd = WeatherApp.getInstance().getCurrentWeatherData();

        wrapper.add(createSection("CURRENT LOCATION",
                makeCityCard(wd.location, wd.description, wd.icon, (int) wd.maxTemp, (int) wd.minTemp)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(wrapper, gbc);
    }

    public static SearchPanel getInstance() {
        return instance;
    }
}
