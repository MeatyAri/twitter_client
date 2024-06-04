package meaty.auth;

import javax.swing.*;
import java.awt.*;

import meaty.MainFrame;
import meaty.tools.RoundedButton;

public class SignUpPage extends JPanel {
        private MainFrame mainFrame;

    public SignUpPage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);

    }
}
