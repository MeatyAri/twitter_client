import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DynamicResizeExample extends JFrame {

    private JLabel sizeLabel;
    private ComponentListener resizeListener;
    private JPanel panel;

    public DynamicResizeExample() {
        // Initialize the JFrame
        setTitle("Dynamic Resize Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the JLabel
        sizeLabel = new JLabel("Resize the component to see changes", JLabel.CENTER);
        add(sizeLabel, BorderLayout.NORTH);

        // Initialize the resizeListener
        resizeListener = new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component component = e.getComponent();
                int width = component.getWidth();
                int height = component.getHeight();
                sizeLabel.setText("Width: " + width + ", Height: " + height);
            }
        };

        // Initialize and add a JPanel
        panel = new JPanel();
        panel.setBackground(Color.BLUE);
        panel.setPreferredSize(new Dimension(200, 200));
        add(panel, BorderLayout.CENTER);

        // Attach the listener to the panel
        attachListener(panel);

        // Add a button to change the component being listened to
        JButton changeComponentButton = new JButton("Change Component");
        changeComponentButton.addActionListener(e -> {
            // Create a new component
            JPanel newPanel = new JPanel();
            newPanel.setBackground(Color.RED);
            newPanel.setPreferredSize(new Dimension(300, 300));

            // Remove the old component and listener
            removeListener(panel);
            remove(panel);

            // Add the new component and attach the listener
            panel = newPanel;
            add(panel, BorderLayout.CENTER);
            attachListener(panel);

            // Refresh the JFrame
            revalidate();
            repaint();
        });
        add(changeComponentButton, BorderLayout.SOUTH);
    }

    private void attachListener(Component component) {
        component.addComponentListener(resizeListener);
    }

    private void removeListener(Component component) {
        component.removeComponentListener(resizeListener);
    }

    public static void main(String[] args) {
        // Create and display the JFrame
        SwingUtilities.invokeLater(() -> {
            DynamicResizeExample example = new DynamicResizeExample();
            example.setVisible(true);
        });
    }
}
