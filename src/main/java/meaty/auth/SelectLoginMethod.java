package meaty.auth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import meaty.MainFrame;
import meaty.tools.RoundedButton;


public class SelectLoginMethod extends JPanel {
    private MainFrame mainFrame;
    
    public SelectLoginMethod(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());

        // Create GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create Sign-Up button
        RoundedButton signUpButton = new RoundedButton("Sign-Up");
        signUpButton.setNormalColor(new Color(0, 122, 255));
        signUpButton.setHoverColor(new Color(0, 150, 255));
        signUpButton.setRadius(80);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 20));
        signUpButton.setPreferredSize(new Dimension(250, 75));

        // Create Login button
        RoundedButton loginButton = new RoundedButton("Login");
        loginButton.setNormalColor(new Color(90, 90, 90));
        loginButton.setHoverColor(new Color(100, 100, 100));
        loginButton.setRadius(70);
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setPreferredSize(new Dimension(200, 60));

        add(signUpButton, gbc);
        gbc.gridy = 1;
        add(loginButton, gbc);

        // Action listeners for buttons (optional)
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Sign-Up button click
                mainFrame.showPage("SignUpPage");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Login button click
                mainFrame.showPage("LoginPage");
            }
        });

    }
}
