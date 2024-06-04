package meaty.home;

import javax.swing.*;
import java.awt.*;

import meaty.MainFrame;


public class AddPost extends JPanel {
    private MainFrame mainFrame;
    
    public AddPost(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Example content for the add post page
        JLabel addPostLabel = new JLabel("Add Post Page", SwingConstants.CENTER);
        add(addPostLabel, BorderLayout.CENTER);
    }
}
