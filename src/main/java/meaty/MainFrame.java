package meaty;

import javax.swing.*;

import meaty.auth.SelectLoginMethod;
import meaty.home.*;
import meaty.error.NoConnection;
import meaty.ServerAPIs.ConnectionAPI;

import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Twitter-like Application");
        ImageIcon icon = new ImageIcon("static/imgs/twitter_logo.png");
        setIconImage(icon.getImage());
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add pages to main panel
        // Error pages
        mainPanel.add(new NoConnection(this), "NoConnection");

        // Login page
        mainPanel.add(new SelectLoginMethod(this), "SelectLoginMethod");

        // Home page
        mainPanel.add(new Profile(this), "Profile");
        mainPanel.add(new AddPost(this), "Add Post");
        mainPanel.add(new Home(this), "Home");

        // // Create the bottom navigation bar
        // JPanel bottomNav = new JPanel(new GridLayout(1, 4));
        // JButton homeButton = new JButton("Home");
        // JButton addPostButton = new JButton("Add Post");
        // JButton profileButton = new JButton("Profile");

        // bottomNav.add(homeButton);
        // bottomNav.add(addPostButton);
        // bottomNav.add(profileButton);

        // // Add action listeners to switch pages
        // homeButton.addActionListener(e -> showPage("Home"));
        // addPostButton.addActionListener(e -> showPage("Add Post"));
        // profileButton.addActionListener(e -> showPage("Profile"));

        // Set initial page
        showPage("SelectLoginMethod");

        // if (!ConnectionAPI.checkConnection()) {
        //     showPage("NoConnection");
        // }

        // Add components to the frame
        add(mainPanel, BorderLayout.CENTER);
        // add(bottomNav, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void showPage(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    public static void main(String[] args) {
        // Set FlatLaf theme
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
