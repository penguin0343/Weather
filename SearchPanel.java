package ui;

import javax.swing.*;
import java.awt.*;
import model.*;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel {

    private JTextField searchField;
    private JButton searchBtn;

    public SearchPanel(ActionListener onSearch) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        searchField = new JTextField(18);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        searchBtn = new JButton("🔍 Search");
        searchBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        // Khi nhấn Enter/Click sẽ gọi function từ WeatherApp
        searchBtn.addActionListener(onSearch);
        searchField.addActionListener(onSearch);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        add(searchField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(searchBtn, gbc);
    }

    public String getCityInput() {
        return searchField.getText().trim();
    }
}
