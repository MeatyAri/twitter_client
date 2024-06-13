import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TweetPanel {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 180);
        frame.getContentPane().setBackground(Color.BLACK);

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();

        // Profile picture
        BufferedImage profileImage = null;
        try {
            profileImage = ImageIO.read(new File("static/imgs/profile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (profileImage != null) {
            profileImage = getCircularImage(profileImage, 50);
            JLabel profilePic = new JLabel(new ImageIcon(profileImage));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 10, 10, 10);
            mainPanel.add(profilePic, gbc);
        }

        // Text area for "What's happening?"
        JTextArea tweetTextArea = new JTextArea("What's happening?");
        tweetTextArea.setLineWrap(true);
        tweetTextArea.setWrapStyleWord(true);
        tweetTextArea.setBackground(Color.BLACK);
        tweetTextArea.setForeground(Color.GRAY);
        tweetTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(tweetTextArea, gbc);

        // Separation line
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(new Color(0, 0, 0));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(separator, gbc);

        // Icon panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        iconPanel.setBackground(Color.BLACK);

        // Icons
        addIcon(iconPanel, "static/imgs/comment.png", 20, 20);
        addIcon(iconPanel, "static/imgs/comment.png", 20, 20);
        addIcon(iconPanel, "static/imgs/comment.png", 20, 20);
        addIcon(iconPanel, "static/imgs/comment.png", 20, 20);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(iconPanel, gbc);

        // Tweet button
        JButton tweetButton = new JButton("Tweet");
        tweetButton.setBackground(new Color(29, 161, 242));
        tweetButton.setForeground(Color.WHITE);
        tweetButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(tweetButton, gbc);

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void addIcon(JPanel panel, String iconPath, int width, int height) {
        Image image = new ImageIcon(iconPath).getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel imageIcon = new JLabel(new ImageIcon(scaledImage));
        panel.add(imageIcon);
    }

    private static BufferedImage getCircularImage(BufferedImage image, int diameter) {
        // Scale the image to the specified diameter
        Image scaledImage = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = bufferedScaledImage.createGraphics();
        g2.drawImage(scaledImage, 0, 0, null);
        g2.dispose();

        // Create a circular mask
        BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fill(new Ellipse2D.Double(0, 0, diameter, diameter));
        g2d.dispose();

        // Create the circular image with background
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = circularImage.createGraphics();
        applyQualityRenderingHints(g2d);

        // Set background color (change Color.WHITE to any other color as needed)
        g2d.setColor(new Color(40, 40, 40));
        g2d.fill(new Ellipse2D.Double(0, 0, diameter, diameter));

        // Draw the scaled image
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.drawImage(bufferedScaledImage, 0, 0, null);
        g2d.dispose();

        return circularImage;
    }

    private static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}