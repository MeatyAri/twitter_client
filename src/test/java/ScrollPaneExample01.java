import javax.swing.*;
import java.awt.*;

public class ScrollPaneExample01 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Scroll Pane Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // Create a panel for content
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Add components to contentPanel
            for (int i = 1; i <= 20; i++) {
                JLabel label = new JLabel("Label " + i);
                contentPanel.add(label);
            }

            // Create JScrollPane and add contentPanel to it
            JScrollPane scrollPane = new JScrollPane(contentPanel);

            // Adjust scroll pane settings
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // Add JScrollPane to JFrame
            frame.add(scrollPane);

            // Display the frame
            frame.setVisible(true);
        });
    }
}
