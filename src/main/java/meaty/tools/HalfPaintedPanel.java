package meaty.tools;

import javax.swing.*;
import java.awt.*;

public class HalfPaintedPanel extends JPanel {
    private int radius = 50;
    private int top = 0;
    private int left = 0;
    private int bottom = 0;
    private int right = 0;

    public HalfPaintedPanel(int top, int left, int bottom, int right) {
        super();
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        setOpaque(false); // make the panel transparent
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(left, top, getWidth()-left-right, getHeight()-top-bottom, radius, radius);

        // draw the content of the panel
        super.paintComponent(g);

        g2.dispose();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }
}
