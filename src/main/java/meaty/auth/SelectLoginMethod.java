package meaty.auth;

import java.io.IOException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import meaty.MainFrame;
import meaty.tools.*;
import meaty.ServerAPIs.*;
import meaty.ServerAPIs.protocol.*;

public class SelectLoginMethod extends JPanel {
    private MainFrame mainFrame;
    private JPanel rightPanel;
    private CardLayout cardLayout;

    public SelectLoginMethod(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Left panel
        JPanel leftPanel = new RPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(15, 86, 216)); // Dark blue
        c.insets = new Insets(0, 10, 0, 5); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.7; // Take up 70% of the width
        c.fill = GridBagConstraints.BOTH;
        add(leftPanel, c);

        JLabel scaledWelcomeImage = imageEdits.getResizeImage("static/imgs/twitter-login-screen.png", 700, 650);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        leftPanel.add(scaledWelcomeImage, c);

        // Right panel with CardLayout
        rightPanel = new RPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);
        rightPanel.setBackground(new Color(50, 50, 50));
        c.insets = new Insets(0, 5, 0, 10); // Add padding
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.3; // Take up 30% of the width
        c.fill = GridBagConstraints.BOTH;
        add(rightPanel, c);

        // Add cards to the CardLayout
        JPanel initialPanel = createInitialPanel();
        JPanel loginPanel = createLoginPanel();
        JPanel signUpPanel = createSignUpPanel();

        rightPanel.add(initialPanel, "InitialPanel");
        rightPanel.add(loginPanel, "LoginPanel");
        rightPanel.add(signUpPanel, "SignUpPanel");        

        cardLayout.show(rightPanel, "InitialPanel");

        // Action listeners for buttons
        ((RButton) initialPanel.getComponent(4)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "SignUpPanel");
            }
        });

        ((RButton) initialPanel.getComponent(5)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(rightPanel, "LoginPanel");
            }
        });
    }

    private JPanel createInitialPanel() {
        JPanel initialPanel = new RPanel();
        initialPanel.setLayout(new GridBagLayout());
        initialPanel.setBackground(new Color(50, 50, 50));
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(20, 20, 20, 20); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel logoLabel = new JLabel(new ImageIcon("static/imgs/twitter_logo.png"));
        initialPanel.add(logoLabel, c);

        c.gridy = 1;
        c.insets = new Insets(20, 20, 5, 20); // Add padding
        JLabel seeLabel01 = new JLabel("See what's happening in");
        seeLabel01.setFont(new Font("Arial", Font.BOLD, 24));
        seeLabel01.setForeground(new Color(200, 200, 200));
        initialPanel.add(seeLabel01, c);

        c.gridy = 2;
        c.insets = new Insets(5, 20, 20, 20); // Add padding
        JLabel seeLabel02 = new JLabel("the world right now");
        seeLabel02.setFont(new Font("Arial", Font.BOLD, 24));
        seeLabel02.setForeground(new Color(200, 200, 200));
        initialPanel.add(seeLabel02, c);

        c.gridy = 3;
        c.insets = new Insets(10, 20, 10, 20); // Add padding
        JLabel joinTodayLabel = new JLabel("Join Twitter today.");
        joinTodayLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        joinTodayLabel.setForeground(new Color(150, 150, 150));
        initialPanel.add(joinTodayLabel, c);

        c.gridy = 4;
        c.insets = new Insets(20, 20, 10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        RButton signUpButton = new RButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(200, 40));
        signUpButton.setNormalColor(new Color(50, 150, 255));
        signUpButton.setHoverColor(new Color(70, 170, 255));
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        initialPanel.add(signUpButton, c);

        c.gridy = 5;
        c.insets = new Insets(10, 20, 20, 20);
        RButton logInButton = new RButton("Log in");
        logInButton.setPreferredSize(new Dimension(200, 40));
        logInButton.setNormalColor(new Color(70, 70, 70));
        logInButton.setHoverColor(new Color(90, 90, 90));
        logInButton.setFont(new Font("Arial", Font.BOLD, 16));
        initialPanel.add(logInButton, c);

        return initialPanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new RPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(50, 50, 50));
        GridBagConstraints c = new GridBagConstraints();

        // Twitter logo
        JLabel logoLabel = new JLabel(new ImageIcon("static/imgs/twitter_logo.png"));
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 20, 20, 20);
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        loginPanel.add(logoLabel, c);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(200, 200, 200));
        JTextField usernameField = new RTextField(20);
        usernameField.setPreferredSize(new Dimension(0, 35));
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 20, 5, 20);
        loginPanel.add(usernameLabel, c);
        c.gridy = 2;
        loginPanel.add(usernameField, c);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(200, 200, 200));
        JPasswordField passwordField = new RPasswordField(20);
        passwordField.setPreferredSize(new Dimension(0, 35));
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 20, 5, 20);
        loginPanel.add(passwordLabel, c);
        c.gridy = 4;
        loginPanel.add(passwordField, c);

        // Login button
        RButton loginButton = new RButton("Log in");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setNormalColor(new Color(50, 150, 255));
        loginButton.setHoverColor(new Color(70, 170, 255));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(20, 20, 10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        loginPanel.add(loginButton, c);

        loginButton.addActionListener(e -> sendLoginRequest(usernameField, passwordField));

        return loginPanel;
    }

    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new RPanel();
        signUpPanel.setLayout(new GridBagLayout());
        signUpPanel.setBackground(new Color(50, 50, 50));
        GridBagConstraints c = new GridBagConstraints();

        // Twitter logo
        JLabel logoLabel = imageEdits.getResizeImage("static/imgs/twitter_logo.png", 62, 51);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        signUpPanel.add(logoLabel, c);

        // Username field
        JLabel usernameLabel = new JLabel("Username*:");
        usernameLabel.setForeground(new Color(200, 200, 200));
        JTextField usernameField = new RTextField(20);
        usernameField.setPreferredSize(new Dimension(0, 35));
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 20, 5, 20);
        signUpPanel.add(usernameLabel, c);
        c.gridy = 2;
        signUpPanel.add(usernameField, c);

        // Password field
        JLabel passwordLabel = new JLabel("Password*:");
        passwordLabel.setForeground(new Color(200, 200, 200));
        JPasswordField passwordField = new RPasswordField(20);
        passwordField.setPreferredSize(new Dimension(0, 35));
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 20, 5, 20);
        signUpPanel.add(passwordLabel, c);
        c.gridy = 4;
        signUpPanel.add(passwordField, c);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(200, 200, 200));
        JTextField emailField = new RTextField(20);
        emailField.setPreferredSize(new Dimension(0, 35));
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(5, 20, 5, 20);
        signUpPanel.add(emailLabel, c);
        c.gridy = 6;
        signUpPanel.add(emailField, c);

        // Phone field
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(new Color(200, 200, 200));
        JTextField phoneField = new RTextField(20);
        phoneField.setPreferredSize(new Dimension(0, 35));
        c.gridx = 0;
        c.gridy = 7;
        c.insets = new Insets(5, 20, 5, 20);
        signUpPanel.add(phoneLabel, c);
        c.gridy = 8;
        signUpPanel.add(phoneField, c);

        // Birth date field
        JLabel birthDateLabel = new JLabel("Birth Date*:");
        birthDateLabel.setForeground(new Color(200, 200, 200));

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        c.gridx = 0;
        c.gridy = 9;
        c.insets = new Insets(5, 20, 5, 20);
        signUpPanel.add(birthDateLabel, c);
        c.gridy = 10;
        signUpPanel.add(datePicker, c);

        // Bio field
        JLabel bioLabel = new JLabel("Bio:");
        bioLabel.setForeground(new Color(200, 200, 200));
        JTextArea bioField = new RTextArea(5, 20);
        bioField.setLineWrap(true);
        bioField.setWrapStyleWord(true);
        bioField.setPreferredSize(new Dimension(0, 75));
        JScrollPane bioScrollPane = new RScrollPane(bioField);
        bioScrollPane.setPreferredSize(new Dimension(0, 75));
        c.gridx = 0;
        c.gridy = 11;
        c.insets = new Insets(5, 20, 5, 20);
        signUpPanel.add(bioLabel, c);
        c.gridy = 12;
        signUpPanel.add(bioScrollPane, c);

        // Sign Up button
        RButton signUpButton = new RButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(200, 40));
        signUpButton.setNormalColor(new Color(50, 150, 255));
        signUpButton.setHoverColor(new Color(70, 170, 255));
        signUpButton.setFont(new Font("Arial", Font.BOLD, 16));
        c.gridx = 0;
        c.gridy = 13;
        c.insets = new Insets(20, 20, 10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        signUpPanel.add(signUpButton, c);

        signUpButton.addActionListener(e -> {
            sendSignUpRequest(usernameField, passwordField, emailField, phoneField, datePicker, bioField);
        });

        return signUpPanel;
    }

    private void sendSignUpRequest(JTextField usernameField, JPasswordField passwordField, JTextField emailField, JTextField phoneField,
                                   JDatePickerImpl datePicker, JTextArea bioField) {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String phone = phoneField.getText();
        Date birthDate = (Date) datePicker.getModel().getValue();
        String bio = bioField.getText();

        if (username.isEmpty() || password.isEmpty() || birthDate == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all the *required fields.");
            return;
        }
        
        try {
            long id = Auth.sendSignUpRequest(username, password, email, phone, birthDate, bio);
            Response response = ConnectionAPI.awaitResponse(id);

            String token = response.getData().get("token").getAsString();
            ConnectionAPI.saveToken(token);

            String responseStr = ConnectionAPI.getResponseStr(response);
            JOptionPane.showMessageDialog(null, responseStr);

            if (response.getStatus() == 200) {
                // Login successful, redirect to home page
                mainFrame.showPage("Home");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendLoginRequest(JTextField usernameField, JPasswordField passwordField) {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the *required fields.");
            return;
        }

        try {
            long id = Auth.sendLoginRequest(username, password);
            Response response = ConnectionAPI.awaitResponse(id);

            String token = response.getData().get("token").getAsString();
            ConnectionAPI.saveToken(token);

            String responseStr = ConnectionAPI.getResponseStr(response);
            JOptionPane.showMessageDialog(null, responseStr);

            if (response.getStatus() == 200) {
                // Login successful, redirect to home page
                mainFrame.showPage("Home");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
