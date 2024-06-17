import javax.swing.*;
import java.awt.*;

public class TweetPanel02 {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Tweet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);

        // Create the main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        frame.add(panel);

        // Profile image (simulated with a simple label here)
        JLabel profileImage = new JLabel(new ImageIcon("profile_image_path")); // Replace with the actual image path
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(profileImage, gbc);

        // Username and handle
        JLabel username = new JLabel("Brie @Sktch_ComedyFan · 3m");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(username, gbc);

        // Tweet content
        JTextArea tweetContent = new JTextArea("Giving standup comedy a go. Open mic starts at 7, hit me up if you want ticket #heregoesnothing");
        tweetContent.setLineWrap(true);
        tweetContent.setWrapStyleWord(true);
        tweetContent.setEditable(false);
        tweetContent.setBackground(panel.getBackground());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(tweetContent, gbc);

        // Interaction buttons
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        JButton replyButton = new JButton("1");
        JButton retweetButton = new JButton("1");
        JButton likeButton = new JButton("8");

        interactionPanel.add(replyButton);
        interactionPanel.add(retweetButton);
        interactionPanel.add(likeButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(interactionPanel, gbc);

        // Show the frame
        frame.setVisible(true);
    }
}
