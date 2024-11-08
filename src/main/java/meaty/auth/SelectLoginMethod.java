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
        loginPanel.setLayout(new BorderLayout());
        loginPanel.setBackground(new Color(50, 50, 50));

        // back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        RButton backButton = new RButton(imageEdits.getResizeIcon("static/imgs/back.png", 15, 15));
        backButton.setNormalColor(new Color(70, 70, 70));
        backButton.setHoverColor(new Color(90, 90, 90));
        backButton.addActionListener(e -> {
            cardLayout.show(rightPanel, "InitialPanel");
        });
        topPanel.add(backButton);
        loginPanel.add(topPanel, BorderLayout.NORTH);

        // content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();

        // Twitter logo
        c.gridy = 0;
        c.insets = new Insets(10, 20, 20, 20);
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel logoLabel = new JLabel(new ImageIcon("static/imgs/twitter_logo.png"));
        contentPanel.add(logoLabel, c);

        // Username field
        c.gridy = 1;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(new Color(200, 200, 200));
        JTextField usernameField = new RTextField(20);
        usernameField.setPreferredSize(new Dimension(0, 35));
        contentPanel.add(usernameLabel, c);
        c.gridy = 2;
        contentPanel.add(usernameField, c);

        // Password field
        c.gridy = 3;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(200, 200, 200));
        JPasswordField passwordField = new RPasswordField(20);
        passwordField.setPreferredSize(new Dimension(0, 35));
        contentPanel.add(passwordLabel, c);
        c.gridy = 4;
        contentPanel.add(passwordField, c);

        // Login button
        c.gridy = 5;
        c.insets = new Insets(20, 20, 10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        RButton loginButton = new RButton("Log in");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setNormalColor(new Color(50, 150, 255));
        loginButton.setHoverColor(new Color(70, 170, 255));
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(loginButton, c);

        loginButton.addActionListener(e -> sendLoginRequest(usernameField, passwordField));

        loginPanel.add(contentPanel, BorderLayout.CENTER);

        return loginPanel;
    }

    private JPanel createSignUpPanel() {
        JPanel signUpPanel = new RPanel();
        signUpPanel.setLayout(new BorderLayout());
        signUpPanel.setBackground(new Color(50, 50, 50));

        // back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        RButton backButton = new RButton(imageEdits.getResizeIcon("static/imgs/back.png", 15, 15));
        backButton.setNormalColor(new Color(70, 70, 70));
        backButton.setHoverColor(new Color(90, 90, 90));
        backButton.addActionListener(e -> {
            cardLayout.show(rightPanel, "InitialPanel");
        });
        topPanel.add(backButton);
        signUpPanel.add(topPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new RPanel();
        contentPanel.setBackground(new Color(50, 50, 50));
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // Twitter logo
        JLabel logoLabel = imageEdits.getResizeImage("static/imgs/twitter_logo.png", 62, 51);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.EAST;
        c.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(logoLabel, c);

        // Username field
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel usernameLabel = new JLabel("Username*:");
        usernameLabel.setForeground(new Color(200, 200, 200));
        JTextField usernameField = new RTextField(20);
        usernameField.setPreferredSize(new Dimension(0, 35));
        contentPanel.add(usernameLabel, c);
        c.gridy = 2;
        contentPanel.add(usernameField, c);

        // Password field
        c.gridy = 3;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel passwordLabel = new JLabel("Password*:");
        passwordLabel.setForeground(new Color(200, 200, 200));
        JPasswordField passwordField = new RPasswordField(20);
        passwordField.setPreferredSize(new Dimension(0, 35));
        contentPanel.add(passwordLabel, c);
        c.gridy = 4;
        contentPanel.add(passwordField, c);

        // Email field
        c.gridy = 5;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(200, 200, 200));
        JTextField emailField = new RTextField(20);
        emailField.setPreferredSize(new Dimension(0, 35));
        contentPanel.add(emailLabel, c);
        c.gridy = 6;
        contentPanel.add(emailField, c);

        // Phone field
        c.gridy = 7;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(new Color(200, 200, 200));
        JTextField phoneField = new RTextField(20);
        phoneField.setPreferredSize(new Dimension(0, 35));
        contentPanel.add(phoneLabel, c);
        c.gridy = 8;
        contentPanel.add(phoneField, c);

        // Birth date field
        c.gridy = 9;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel birthDateLabel = new JLabel("Birthday*:");
        birthDateLabel.setForeground(new Color(200, 200, 200));

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        contentPanel.add(birthDateLabel, c);
        c.gridy = 10;
        contentPanel.add(datePicker, c);

        // Bio field
        c.gridy = 11;
        c.insets = new Insets(5, 20, 5, 20);
        JLabel bioLabel = new JLabel("Bio:");
        bioLabel.setForeground(new Color(200, 200, 200));
        JTextArea bioField = new RTextArea(5, 20);
        bioField.setLineWrap(true);
        bioField.setWrapStyleWord(true);
        bioField.setPreferredSize(new Dimension(0, 75));
        JScrollPane bioScrollPane = new RScrollPane(bioField);
        bioScrollPane.setPreferredSize(new Dimension(0, 75));
        contentPanel.add(bioLabel, c);
        c.gridy = 12;
        contentPanel.add(bioScrollPane, c);

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
        contentPanel.add(signUpButton, c);

        signUpButton.addActionListener(e -> {
            sendSignUpRequest(usernameField, passwordField, emailField, phoneField, datePicker, bioField);
        });

        signUpPanel.add(contentPanel, BorderLayout.CENTER);

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

            if (response.getStatus() != 200) {
                JOptionPane.showMessageDialog(null, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String token = response.getData().get("token").getAsString();
            ConnectionAPI.saveToken(token);

            String responseStr = ConnectionAPI.getResponseStr(response);
            JOptionPane.showMessageDialog(null, responseStr);

            // Login successful, redirect to home page
            mainFrame.showPage("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
