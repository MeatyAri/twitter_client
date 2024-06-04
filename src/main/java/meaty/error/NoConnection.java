package meaty.error;

import javax.swing.*;
import java.awt.*;

import meaty.MainFrame;


public class NoConnection extends JPanel {
    private MainFrame mainFrame;

    public NoConnection(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        JLabel noConnectionLabel = new JLabel("No Connection", SwingConstants.CENTER);
        add(noConnectionLabel, BorderLayout.CENTER);
    }
}
