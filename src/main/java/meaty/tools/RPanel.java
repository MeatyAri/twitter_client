package meaty.tools;


import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel{
    private int radius = 50;

    public RPanel() {
        super();
        setOpaque(false); // make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // draw the content of the panel
        super.paintComponent(g);

        g2.dispose();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }
}
