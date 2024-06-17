package meaty.error;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import meaty.ServerAPIs.ConnectionAPI;
import meaty.MainFrame;


public class NoConnection extends JPanel {
    private MainFrame mainFrame;

    public NoConnection(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel noConnectionLabel = new JLabel("No Connection", SwingConstants.CENTER);
        noConnectionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        noConnectionLabel.setForeground(Color.WHITE);
        add(noConnectionLabel, BorderLayout.CENTER);

        JLabel retryLabel = new JLabel("Tap to retry", SwingConstants.CENTER);
        retryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        retryLabel.setForeground(Color.WHITE);
        retryLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retryLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ConnectionAPI.checkConnection()) {
                    mainFrame.setInitialPage();
                }
            }
        });
        add(retryLabel, BorderLayout.SOUTH);
    }
}
