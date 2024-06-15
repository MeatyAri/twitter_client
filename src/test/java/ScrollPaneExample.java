import javax.swing.*;
import java.awt.*;

public class ScrollPaneExample {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Custom JScrollPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create a JPanel with some content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 20; i++) {
            panel.add(new JLabel("Label " + i));
        }

        // Create a JScrollPane with the JPanel as the viewport view
        JScrollPane scrollPane = new JScrollPane(panel);
        // Set scroll bar policies
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        // scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Remove borders
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create a custom scroll bar UI to make them invisible
        scrollPane.getVerticalScrollBar().setUI(new MyScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new MyScrollBarUI());

        // Add the scroll pane to the frame
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}

class MyScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(0, 0, 0, 0);
        this.trackColor = new Color(0, 0, 0, 0);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}
