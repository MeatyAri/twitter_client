package meaty;

import java.io.IOException;
import javax.swing.*;

import com.google.gson.JsonObject;

import meaty.auth.SelectLoginMethod;
import meaty.home.*;
import meaty.error.NoConnection;
import meaty.ServerAPIs.*;
import meaty.ServerAPIs.protocol.*;

import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Twitter-like Application");
        ImageIcon icon = new ImageIcon("static/imgs/twitter-logo-with-bg.png");
        setIconImage(icon.getImage());
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        try {
            addPages();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set initial page
        setInitialPage();

        // try {
        //     long id =  ProfileHadler.sendGetProfileRequest(ConnectionAPI.getToken(), "asd");
        //     Response response = ConnectionAPI.awaitResponse(id);
        //     showPage("Profile", response.getData());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // Add components to the frame
        add(mainPanel, BorderLayout.CENTER);
        // add(bottomNav, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void showPage(String pageName) {
        refreshPage(pageName);
        cardLayout.show(mainPanel, pageName);
    }

    public void showPage(String pageName, JsonObject data) {
        refreshPage(pageName, data);
        cardLayout.show(mainPanel, pageName);
    }

    public void refreshPage(String pageName) {
        switch (pageName) {
            case "NoConnection":
                break;
            case "SelectLoginMethod":
                break;
            case "Home":
                mainPanel.add(new Home(this), "Home");
                break;
            case "Bookmarks":
                mainPanel.add(new Bookmarks(this), "Bookmarks");
                break;
            default:
                System.out.println("Invalid page name: " + pageName);
                break;
        }
    }

    public void refreshPage(String pageName, JsonObject data) {
        switch (pageName) {
            case "Profile":
                mainPanel.add(new Profile(this, data), "Profile");
                break;
            default:
                System.out.println("Invalid page name: " + pageName);
                break;
        }
    }

    // public void refreshPage(String pageName, Component c) {
    //     addPage(pageName, c);
    //     cardLayout.show(mainPanel, pageName);
    // }

    // public void refreshPage(Component c) {
    //     addPage(c);
    //     cardLayout.show(mainPanel, c.getClass().getSimpleName());
    // }

    // public void addPage(String pageName, Component c) {
    //     mainPanel.add(pageName, c);
    // }

    // public void addPage(Component c) {
    //     mainPanel.add(c.getClass().getSimpleName(), c);
    // }

    private void addPages() {
        // Add pages to main panel
        // Error pages
        mainPanel.add(new NoConnection(this), "NoConnection");

        // Login page
        mainPanel.add(new SelectLoginMethod(this), "SelectLoginMethod");

        // Home page
        // mainPanel.add(new Profile(this), "Profile");
        // mainPanel.add(new Home(this), "Home");
    }

    public void setInitialPage() {
        if (!ConnectionAPI.checkConnection()) {
            showPage("NoConnection");
            return;
        }

        String token = ConnectionAPI.getToken();

        if (token == null || token.isEmpty()) {
            showPage("SelectLoginMethod");
            return;
        }

        // Check if token is valid
        try {
            long id = Auth.sendLoginRequest(token);
            Response response = ConnectionAPI.awaitResponse(id);

            if (response.getStatus() == 200) {
                // Login successful, redirect to home page
                showPage("Home");
            } else {
                // Login failed, redirect to login page
                showPage("SelectLoginMethod");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
