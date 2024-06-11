package meaty.tools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RScrollPane extends JScrollPane {
    private int radius = 20;

    public RScrollPane(Component view) {
        super(view);
        setOpaque(false);
        setBorder(new EmptyBorder(5, 2, 5, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g);
        
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw border
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));

        g2.dispose();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }
}
