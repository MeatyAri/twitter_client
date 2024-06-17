package meaty.home;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import meaty.MainFrame;
import meaty.tools.*;
import meaty.ServerAPIs.*;
import meaty.ServerAPIs.protocol.*;

public class Home extends JPanel {
    private MainFrame mainFrame;
    private JPanel navigationPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;

    private JPanel containerPanel;

    public Home(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        Rectangle frameBounds = mainFrame.getBounds();
        setBackground(getBackground().brighter());
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
    
        // Navigation panel
        navigationPanel = new JPanel(new GridBagLayout());
        navigationPanel.setBackground(Color.BLACK);
        c.insets = new Insets(-1, -1, -1, 1); // remove bright edge pixels
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.18;
        c.weighty = 1.0; // Take up all available vertical space
        c.fill = GridBagConstraints.BOTH;
        Dimension dims = new Dimension((int)(frameBounds.width * c.weightx), frameBounds.height);
        navigationPanel.setPreferredSize(dims);
        navigationPanel.setMinimumSize(dims);
        navigationPanel.setMaximumSize(dims);
        add(navigationPanel, c);
    
        JPanel navPanel = createNavigationPanel();
        c.insets = new Insets(0, 20, 0, 0); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        navigationPanel.add(navPanel, c);
    
        // Center panel
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.BLACK);
        c.insets = new Insets(-1, 0, -1, 0); // Add padding
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.45;
        c.weighty = 1.0; // Take up all available vertical space
        c.fill = GridBagConstraints.BOTH;
        dims = new Dimension((int)(frameBounds.width * c.weightx), frameBounds.height);
        centerPanel.setPreferredSize(dims);
        centerPanel.setMinimumSize(dims);
        centerPanel.setMaximumSize(dims);
        add(centerPanel, c);

        JScrollPane mainPanel = createCenterPanel();
        c.insets = new Insets(0, 0, 0, 0); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        centerPanel.add(mainPanel, c);
    
        // Right panel
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        c.insets = new Insets(-1, 1, -1, -1); // Add padding
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 0.27;
        c.weighty = 1.0; // Take up all available vertical space
        c.fill = GridBagConstraints.BOTH;
        dims = new Dimension((int)(frameBounds.width * c.weightx), frameBounds.height);
        rightPanel.setPreferredSize(dims);
        rightPanel.setMinimumSize(dims);
        rightPanel.setMaximumSize(dims);
        add(rightPanel, c);

        JPanel searchPanel = createRightPanel();
        c.insets = new Insets(0, 0, 0, 20); // Add padding
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(searchPanel, c);
    }

    private JPanel createNavigationPanel() {
        // Navigation panel
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new GridBagLayout());
        navPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        // Twitter logo
        JLabel logoLabel = imageEdits.getResizeImage("static/imgs/twitter_logo.png", 35, 29);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(15, 30, 10, 30); // Add padding
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(logoLabel, c);
        
        Font font = new Font("Liberation Sans", Font.BOLD, 16); // Set the font
        
        // Home button
        RButton homeButton = new RButton("Home");
        homeButton.setHorizontalAlignment(JButton.LEFT);
        homeButton.setIcon(imageEdits.getResizeIcon("static/imgs/home.png", 25, 25));
        homeButton.setFont(font);
        homeButton.setNormalColor(new Color(0, 0, 0));
        homeButton.setHoverColor(new Color(30, 30, 30));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("Home");
            }
        });
        c.insets = new Insets(0, 10, 0, 10); // Add padding
        c.gridy = 1;
        c.gridwidth = 2; // takes two columns
        c.weightx = 1.0;
        navPanel.add(homeButton, c);
        
        // Messages button
        RButton messagesButton = new RButton("Messages");
        messagesButton.setHorizontalAlignment(JButton.LEFT);
        messagesButton.setIcon(imageEdits.getResizeIcon("static/imgs/comment.png", 25, 25));
        messagesButton.setFont(font);
        messagesButton.setNormalColor(new Color(0, 0, 0));
        messagesButton.setHoverColor(new Color(30, 30, 30));
        messagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("Messages");
            }
        });
        c.gridy = 2;
        navPanel.add(messagesButton, c);
        
        // Bookmarks button
        RButton bookmarksButton = new RButton("Bookmarks");
        bookmarksButton.setHorizontalAlignment(JButton.LEFT);
        bookmarksButton.setIcon(imageEdits.getResizeIcon("static/imgs/save-add.png", 25, 25));
        bookmarksButton.setFont(font);
        bookmarksButton.setNormalColor(new Color(0, 0, 0));
        bookmarksButton.setHoverColor(new Color(30, 30, 30));
        bookmarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("Bookmarks");
            }
        });
        c.gridy = 3;
        navPanel.add(bookmarksButton, c);
        
        // Profile button
        RButton profileButton = new RButton("Profile");
        profileButton.setHorizontalAlignment(JButton.LEFT);
        profileButton.setIcon(imageEdits.getResizeIcon("static/imgs/profile.png", 25, 25));
        profileButton.setFont(font);
        profileButton.setNormalColor(new Color(0, 0, 0));
        profileButton.setHoverColor(new Color(30, 30, 30));
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPage("Profile");
            }
        });
        c.gridy = 4;
        navPanel.add(profileButton, c);
        
        // Blue tweet button
        RButton tweetButton = new RButton("Tweet");
        tweetButton.setFont(font);
        tweetButton.setPreferredSize(new Dimension(0, 40));
        tweetButton.setNormalColor(new Color(50, 150, 255));
        tweetButton.setHoverColor(new Color(70, 170, 255));
        tweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    centerPanel.requestFocusInWindow();
                    JScrollPane scrollPane = (JScrollPane) centerPanel.getComponent(0);
                    scrollPane.getVerticalScrollBar().setValue(0);

                    // Traverse to the tweetTextArea
                    Component view = scrollPane.getViewport().getView();

                    JPanel innerPanel = (JPanel) view;
                    JPanel nestedPanel = (JPanel) innerPanel.getComponent(0);
                    JPanel tweetPanel = (JPanel) nestedPanel.getComponent(2);
                    JTextArea tweetTextArea = (JTextArea) tweetPanel.getComponent(1);
                    tweetTextArea.requestFocusInWindow();

                    JButton sendTweetButton = (JButton) tweetPanel.getComponent(4);
                    sendTweetButton.getActionListeners()[0].actionPerformed(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }            
        });
        c.insets = new Insets(20, 30, 0, 30); // Add padding
        c.gridy = 5;
        navPanel.add(tweetButton, c);

        return navPanel;
    }

    private JScrollPane createCenterPanel() {
        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        // Home
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.setBackground(Color.BLACK);
        c.insets = new Insets(15, 15, 15, 15);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(homePanel, c);

        JLabel homeLabel = new JLabel("Home");
        homeLabel.setFont(new Font("Liberation Sans", Font.BOLD, 20));
        homeLabel.setForeground(Color.WHITE);
        homePanel.add(homeLabel, BorderLayout.WEST);

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

        // Tweet panel
        JPanel tweetPanel = createTweetPanel();
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.insets = new Insets(5, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        mainPanel.add(tweetPanel, c);

        // thick separator
        JPanel thickSeparator = new JPanel();
        thickSeparator.setBackground(getBackground().darker());
        thickSeparator.setPreferredSize(new Dimension(0, 10));
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(thickSeparator, c);

        // feed panel
        JPanel feedPanel = displayTweetsPanel();
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        mainPanel.add(feedPanel, c);

        // Center panel
        containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(Color.BLACK);

        int width = (int)(mainFrame.getWidth() * 0.45);
        JLabel dummyLabel = new JLabel();
        dummyLabel.setForeground(new Color(0, 0, 0, 0));
        homePanel.add(dummyLabel, BorderLayout.EAST);

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
        containerScrollPane.setBackground(Color.BLACK);
        containerScrollPane.getViewport().setBackground(Color.BLACK);
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

    private JPanel createTweetPanel() {
        // Create the tweet panel with GridBagLayout
        JPanel tweetPanel = new JPanel(new GridBagLayout());
        tweetPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

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
            c.anchor = GridBagConstraints.WEST;
            c.insets = new Insets(10, 10, 10, 10);
            tweetPanel.add(profilePic, c);
        }

        // Text area for "What's happening?"
        JTextArea tweetTextArea = new JTextArea();
        tweetTextArea.setLineWrap(true);
        tweetTextArea.setWrapStyleWord(true);
        tweetTextArea.setBackground(Color.BLACK);
        tweetTextArea.setForeground(Color.GRAY);
        tweetTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        TextPrompt placeholder = new TextPrompt("What's happening?", tweetTextArea);
        placeholder.changeAlpha(0.5f);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        c.insets = new Insets(10, 10, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        tweetPanel.add(tweetTextArea, c);

        // Separation line
        JPanel separator = new JPanel();        
        separator.setBackground(Color.GRAY);
        Dimension seperatorDims = new Dimension(0, 1);
        separator.setPreferredSize(seperatorDims);
        separator.setMinimumSize(seperatorDims);
        separator.setMaximumSize(seperatorDims);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        tweetPanel.add(separator, c);

        // Icon panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        iconPanel.setBackground(Color.BLACK);

        // Icons
        iconPanel.add(imageEdits.getResizeImage("static/imgs/comment.png", 20, 20));
        iconPanel.add(imageEdits.getResizeImage("static/imgs/comment.png", 20, 20));
        iconPanel.add(imageEdits.getResizeImage("static/imgs/comment.png", 20, 20));
        iconPanel.add(imageEdits.getResizeImage("static/imgs/comment.png", 20, 20));

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 10, 10, 10);
        tweetPanel.add(iconPanel, c);

        // Tweet button
        RButton tweetButton = new RButton("Tweet");
        Font font = new Font("Liberation Sans", Font.BOLD, 13); // Set the font
        tweetButton.setFont(font);
        Dimension btnDims = new Dimension(70, 35);
        tweetButton.setPreferredSize(btnDims);
        tweetButton.setMinimumSize(btnDims);
        tweetButton.setMaximumSize(btnDims);
        tweetButton.setNormalColor(new Color(50, 150, 255));
        tweetButton.setHoverColor(new Color(70, 170, 255));
        tweetButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        c.gridx = 2;
        c.gridy = 2;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 10, 10, 10);
        tweetPanel.add(tweetButton, c);

        tweetButton.addActionListener(e -> {
            String tweetContent = tweetTextArea.getText();
            if (!tweetContent.isEmpty()) {
                try {
                    long tweetId = tweetHandler.sendTweetRequest(tweetContent);
                    if (tweetId != -1) {
                        tweetTextArea.setText("");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to send tweet", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return tweetPanel;
    }

    private JPanel displayTweetsPanel() {
        JPanel displayTweetsPanel = new JPanel();
        displayTweetsPanel.setLayout(new GridBagLayout());
        displayTweetsPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;

        try {
            long reqId = tweetHandler.getTweetsRequest();
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
        JPanel tweetPanel = new JPanel(new GridBagLayout());
        tweetPanel.setBackground(Color.BLACK);
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
        username.setBackground(Color.BLACK);
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
        tweetTextArea.setBackground(Color.BLACK);
        tweetTextArea.setForeground(new Color(200, 200, 200));
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 0, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        tweetPanel.add(tweetTextArea, c);

        // Interaction buttons
        JPanel interactionPanel = new JPanel(new GridBagLayout());
        interactionPanel.setBackground(Color.BLACK);

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

        // separator
        JPanel separator = new JPanel();
        separator.setBackground(getBackground().brighter());
        Dimension seperatorDims = new Dimension(0, 1);
        separator.setPreferredSize(seperatorDims);
        separator.setMinimumSize(seperatorDims);
        separator.setMaximumSize(seperatorDims);
        c.insets = new Insets(0, -1, 0, -1);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1.0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        tweetPanel.add(separator, c);

        return tweetPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        // Search bar
        RTextField searchField = new RTextField(20);
        searchField.setPreferredSize(new Dimension(0, 35));
        searchField.setRadius(33);
        TextPrompt placeholder = new TextPrompt("Search Twitter", searchField);
        placeholder.changeAlpha(0.5f);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.insets = new Insets(10, 20, 10, 20);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(searchField, c);

        return rightPanel;
    }
}
