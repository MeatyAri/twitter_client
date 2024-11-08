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

public class Profile extends JPanel {
    private MainFrame mainFrame;
    private JsonObject data;
    private JsonObject profileData;
    private JPanel containerPanel;

    public Profile(MainFrame mainFrame, JsonObject data) {
        this.mainFrame = mainFrame;
        this.data = data;
        this.profileData = data.get("profile").getAsJsonObject();
        Rectangle frameBounds = mainFrame.getBounds();

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints c = new GridBagConstraints();

        // Info panel
        JPanel infoPanelParent = new HalfPaintedPanel(50, 0, 0, 0);
        infoPanelParent.setLayout(new GridBagLayout());
        c.insets = new Insets(15, 15, 15, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.3;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        Dimension dims = new Dimension((int)(frameBounds.width * c.weightx), frameBounds.height);
        infoPanelParent.setPreferredSize(dims);
        infoPanelParent.setMinimumSize(dims);
        infoPanelParent.setMaximumSize(dims);
        add(infoPanelParent, c);

        JPanel infoPanel = infoPanel();
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
        infoPanelParent.add(infoPanel, c);
        
        // Main panel
        JPanel mainPanelParent = new RPanel();
        mainPanelParent.setLayout(new GridBagLayout());
        mainPanelParent.setBackground(Color.BLACK);
        c.insets = new Insets(15, 5, 15, 15);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.7;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        dims = new Dimension((int)(frameBounds.width * c.weightx), frameBounds.height);
        mainPanelParent.setPreferredSize(dims);
        mainPanelParent.setMinimumSize(dims);
        mainPanelParent.setMaximumSize(dims);
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

    private JPanel infoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        infoPanel.setOpaque(false);

        JPanel topPanel = topPanel();
        c.insets = new Insets(0, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(topPanel, c);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridBagLayout());
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(usernamePanel, c);

        // username
        JLabel username = new JLabel("@" + profileData.get("username").getAsString());
        username.setFont(username.getFont().deriveFont(20f));
        username.setForeground(Color.WHITE);
        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        usernamePanel.add(username, c);

        // follow button
        RButton followButton = new RButton("Follow");
        c.insets = new Insets(10, 10, 30, 10);
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(followButton, c);
        if (profileData.get("self").getAsBoolean()) {
            followButton.setEnabled(false);
        } else if (profileData.get("amIFollowing").getAsBoolean()) {
            followButton.setText("Unfollow");
            followButton.setNormalColor(new Color(200, 0, 0));
            followButton.setHoverColor(new Color(255, 0, 0));
        } else if (profileData.get("isFollowingBack").getAsBoolean()) {
            followButton.setText("Follow back");
        }

        followButton.addActionListener(e -> {
            if (!profileData.get("amIFollowing").getAsBoolean()) {
                try {
                    ProfileHadler.sendFollowRequest(ConnectionAPI.getToken(), profileData.get("username").getAsString());
                    profileData.addProperty("followers_count", profileData.get("followers_count").getAsInt() + 1);
                    profileData.addProperty("amIFollowing", true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    ProfileHadler.sendUnfollowRequest(ConnectionAPI.getToken(), profileData.get("username").getAsString());
                    profileData.addProperty("followers_count", profileData.get("followers_count").getAsInt() - 1);
                    profileData.addProperty("amIFollowing", false);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            JsonObject newData = data;
            newData.add("profile", profileData);
            mainFrame.showPage("Profile", newData);
        });

        // add user's birthdate
        JLabel birthdate = new JLabel("Birthday: " + profileData.get("birthDate").getAsString());
        c.insets = new Insets(5, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(birthdate, c);

        // add user's phone
        String phoneNum = profileData.get("phone").getAsString();
        JLabel phone = new JLabel("Phone: " + ((phoneNum == null) ? "Unknown" : phoneNum));
        c.insets = new Insets(5, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(phone, c);

        // add user's email
        String emailAddr = profileData.get("email").getAsString();
        JLabel email = new JLabel("Email: " + ((emailAddr == null) ? "Unknown" : emailAddr));
        c.insets = new Insets(5, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(email, c);

        // add user's bio
        JLabel bioLabel = new JLabel("Bio: ");
        c.insets = new Insets(5, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(bioLabel, c);

        JTextArea bioTextArea = new RTextArea(8, 5);
        String bioText = profileData.get("bio").getAsString();
        bioTextArea.setText(bioText == null ? "Bio not available :(" : bioText);
        bioTextArea.setEditable(false);
        bioTextArea.setLineWrap(true);
        bioTextArea.setWrapStyleWord(true);
        bioTextArea.setBackground(infoPanel.getBackground().darker());
        bioTextArea.setForeground(Color.WHITE);
        c.insets = new Insets(5, 10, 5, 10);
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 1.0;
        c.weighty = 0.2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        infoPanel.add(bioTextArea, c);

        return infoPanel;
    }

    private JPanel topPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        topPanel.setOpaque(false);

        // Profile picture
        BufferedImage profileImage = null;
        try {
            profileImage = ImageIO.read(new File("static/imgs/profile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (profileImage != null) {
            profileImage = imageEdits.getRoundedRectImage(profileImage, 200, 200, 50);
            JLabel profilePic = new JLabel(new ImageIcon(profileImage));

            c.insets = new Insets(0, 0, 0, 5);
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 0.5;
            c.weighty = 1.0;
            c.anchor = GridBagConstraints.WEST;
            topPanel.add(profilePic, c);
        }

        // Panel to hold follower and following labels and counts
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints rc = new GridBagConstraints();
        
        Font font = new Font("Liberation Sans", Font.BOLD, 21);

        // followers count
        JLabel followersCount = new JLabel(profileData.get("followers_count").getAsString());
        followersCount.setFont(font);
        rc.insets = new Insets(0, 0, 0, 0);
        rc.gridx = 0;
        rc.gridy = 0;
        rc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(followersCount, rc);

        // followers label
        JLabel followersLabel = new JLabel("Followers");
        followersLabel.setFont(font);
        rc.insets = new Insets(0, 0, 0, 0);
        rc.gridx = 0;
        rc.gridy = 1;
        rc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(followersLabel, rc);

        // following count
        JLabel followingCount = new JLabel(profileData.get("following_count").getAsString());
        followingCount.setFont(font);
        rc.insets = new Insets(15, 0, 0, 0);
        rc.gridx = 0;
        rc.gridy = 2;
        rc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(followingCount, rc);

        // following label
        JLabel followingLabel = new JLabel("Following");
        followingLabel.setFont(font);
        rc.insets = new Insets(0, 0, 0, 0);
        rc.gridx = 0;
        rc.gridy = 3;
        rc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(followingLabel, rc);
        
        // Put the right panel to the right of the profile picture
        c.insets = new Insets(50, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 1.0;
        topPanel.add(rightPanel, c);
        
        return topPanel;
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
            long reqId = tweetHandler.getTweetsRequest(profileData.get("username").getAsString());
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
