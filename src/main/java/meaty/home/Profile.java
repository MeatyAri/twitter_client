package meaty.home;

import javax.swing.*;

import meaty.MainFrame;

import java.awt.*;

public class Profile extends JPanel {
    private MainFrame mainFrame;

    public Profile(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel profileLabel = new JLabel("User Profile");
        profileLabel.setForeground(Color.WHITE);
        profileLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(profileLabel, BorderLayout.NORTH);

        // Add more profile details and functionalities here
    }
}
