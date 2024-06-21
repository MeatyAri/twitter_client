package meaty.home;

import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import meaty.MainFrame;
import meaty.ServerAPIs.*;
import meaty.ServerAPIs.protocol.Response;
import meaty.tools.*;

public class Bookmarks extends JPanel {
    private MainFrame mainFrame;
    private JPanel containerPanel;

    public Bookmarks(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        // Main panel
        JPanel mainPanelParent = new RPanel();
        mainPanelParent.setLayout(new GridBagLayout());
        mainPanelParent.setBackground(Color.BLACK);
        c.insets = new Insets(15, 15, 15, 15);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        add(mainPanelParent, c);

        JScrollPane mainPanel = createMainPanel();
        mainPanel.setOpaque(false);
        c.insets = new Insets(0, 0, 0, 0); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        mainPanelParent.add(mainPanel, c);
    }

    private JScrollPane createMainPanel() {
        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();

        // twitter logo
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        c.insets = new Insets(15, 10, 15, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(topPanel, c);

        JLabel logoLabel = imageEdits.getResizeImage("static/imgs/twitter_logo.png", 35, 29);
        topPanel.add(logoLabel, BorderLayout.EAST);

        // back button
        RButton backButton = new RButton(imageEdits.getResizeIcon("static/imgs/back.png", 15, 15));
        backButton.setNormalColor(new Color(40, 40, 40));
        backButton.setHoverColor(new Color(60, 60, 60));
        backButton.addActionListener(e -> {
            mainFrame.showPage("Home");
        });
        topPanel.add(backButton, BorderLayout.WEST);

        // separator
        JPanel separator = new JPanel();        
        separator.setBackground(getBackground().brighter());
        Dimension seperatorDims = new Dimension(0, 1);
        separator.setPreferredSize(seperatorDims);
        separator.setMinimumSize(seperatorDims);
        separator.setMaximumSize(seperatorDims);
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(separator, c);

        // feed panel
        JPanel feedPanel = displayTweetsPanel();
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        mainPanel.add(feedPanel, c);

        // Center panel
        containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setOpaque(false);

        int width = (int)(mainFrame.getWidth() * 0.45);
        JLabel dummyLabel = new JLabel();
        dummyLabel.setForeground(new Color(0, 0, 0, 0));
        topPanel.add(dummyLabel, BorderLayout.CENTER);

        // Initialize the resizeListener
        ComponentListener resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component component = e.getComponent();
                // int width = (int)(mainFrame.getWidth() * 0.45);
                int height = component.getHeight() + 1;

                // Update the JLabel with the new size
                containerPanel.setPreferredSize(new Dimension(width, height));
                containerPanel.setMinimumSize(new Dimension(width, height));
                containerPanel.setMaximumSize(new Dimension(width, height));
                dummyLabel.setText(height + "px");
            }
        };

        mainPanel.addComponentListener(resizeListener);

        c.insets = new Insets(0, 0, 0, 0); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        containerPanel.add(mainPanel, c);

        JScrollPane containerScrollPane = new JScrollPane(containerPanel);
        containerScrollPane.setOpaque(false);
        containerScrollPane.getViewport().setOpaque(false);
        containerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        containerScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        containerScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        SwingUtilities.invokeLater(() -> {
            // Ensure the JPanel is in the view hierarchy
            if (containerScrollPane.isShowing()) {
                containerScrollPane.getVerticalScrollBar().setValue(0);
            }
        });

        return containerScrollPane;
    }

        private JPanel displayTweetsPanel() {
        JPanel displayTweetsPanel = new JPanel();
        displayTweetsPanel.setLayout(new GridBagLayout());
        displayTweetsPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 5, 0);
        c.fill = GridBagConstraints.HORIZONTAL;

        try {
            long reqId = tweetHandler.getBookmarkedTweetsRequest();
            Response response = ConnectionAPI.awaitResponse(reqId);
            if (response.getStatus() == 200) {
                JsonArray tweets = response.getData().get("tweets").getAsJsonArray();
                for (int i = 0; i < tweets.size(); i++) {
                    JsonObject tweet = tweets.get(i).getAsJsonObject();
                    displayTweetsPanel.add(createTweetPanel(tweet), c);
                    c.gridy++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return displayTweetsPanel;
    }

    private JPanel createTweetPanel(JsonObject tweet) {
        // Create the tweet panel with GridBagLayout
        JPanel tweetPanel = new RPanel();
        tweetPanel.setLayout(new GridBagLayout());
        tweetPanel.setBackground(new Color(40, 40, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;

        // Profile picture
        BufferedImage profileImage = null;
        try {
            profileImage = ImageIO.read(new File("static/imgs/profile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (profileImage != null) {
            profileImage = imageEdits.getCircularImage(profileImage, 50);
            JLabel profilePic = new JLabel(new ImageIcon(profileImage));
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = 2;
            c.anchor = GridBagConstraints.NORTH;
            c.insets = new Insets(10, 10, 10, 10);
            tweetPanel.add(profilePic, c);
        }
        // Username
        JLabel username = new JLabel("@" + tweet.get("username").getAsString());
        username.setFont(new Font("Liberation Sans", Font.BOLD, 15));
        username.setForeground(Color.GRAY);
        username.setOpaque(true);
        username.setOpaque(false);
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(15, 5, 0, 5);
        tweetPanel.add(username, c);

        // Tweet content
        JTextArea tweetTextArea = new JTextArea(tweet.get("content").getAsString());
        tweetTextArea.setEditable(false);
        tweetTextArea.setLineWrap(true);
        tweetTextArea.setWrapStyleWord(true);
        tweetTextArea.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
        tweetTextArea.setOpaque(false);
        tweetTextArea.setForeground(new Color(200, 200, 200));
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 0, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        tweetPanel.add(tweetTextArea, c);

        // Interaction buttons
        JPanel interactionPanel = new JPanel(new GridBagLayout());
        interactionPanel.setOpaque(false);

        // Reply, like and save buttons
        Icon replyIcon = imageEdits.getResizeIcon("static/imgs/comment.png", 15, 15);
        Icon likeIcon = imageEdits.getResizeIcon("static/imgs/like.png", 15, 15);
        Icon likeIconFilled = imageEdits.getResizeIcon("static/imgs/like-filled.png", 15, 15);
        Icon saveIcon = imageEdits.getResizeIcon("static/imgs/save-add.png", 15, 15);
        Icon saveIconFilled = imageEdits.getResizeIcon("static/imgs/save-add-filled.png", 15, 15);

        RButton replyButton = new RButton(replyIcon);
        replyButton.setNormalColor(new Color(0, 0, 0));
        replyButton.setHoverColor(new Color(30, 30, 30));

        RButton likeButton = new RButton(likeIcon);
        likeButton.setNormalColor(new Color(0, 0, 0));
        likeButton.setHoverColor(new Color(30, 30, 30));
        likeButton.setText("" + tweet.get("likes").getAsLong());

        if (tweet.get("likedByUser").getAsBoolean()) {
            likeButton.setIcon(likeIconFilled);
        }
        
        likeButton.addActionListener(e -> {
            try {
                long reqId = tweetHandler.LikeUnlikeTweetRequest(tweet.get("id").getAsLong());
                Response response = ConnectionAPI.awaitResponse(reqId);
                if (response.getStatus() == 200) {
                    // Update like button
                    if (likeButton.getIcon() == likeIconFilled) {
                        likeButton.setIcon(likeIcon);
                    } else {
                        likeButton.setIcon(likeIconFilled);
                    }

                    // Update like count
                    long likes = response.getData().get("likes").getAsLong();
                    likeButton.setText("" + likes);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        RButton saveButton = new RButton(saveIcon);
        saveButton.setNormalColor(new Color(0, 0, 0));
        saveButton.setHoverColor(new Color(30, 30, 30));

        if (tweet.get("savedByUser").getAsBoolean()) {
            saveButton.setIcon(saveIconFilled);
        }

        saveButton.addActionListener(e -> {
            try {
                long reqId = tweetHandler.SaveUnsaveTweetRequest(tweet.get("id").getAsLong());
                Response response = ConnectionAPI.awaitResponse(reqId);
                if (response.getStatus() == 200) {
                    if (saveButton.getIcon() == saveIconFilled) {
                        saveButton.setIcon(saveIcon);
                    } else {
                        saveButton.setIcon(saveIconFilled);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0, 5, 0, 5);
        interactionPanel.add(replyButton, c);
        c.gridx = 1;
        c.gridy = 0;
        interactionPanel.add(likeButton, c);
        c.gridx = 2;
        c.gridy = 0;
        interactionPanel.add(saveButton, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        tweetPanel.add(interactionPanel, c);

        return tweetPanel;
    }
}
