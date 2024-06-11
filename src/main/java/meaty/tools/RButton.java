package meaty.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class RButton extends JButton {
    private Color normalColor = new Color(70, 130, 180);
    private Color hoverColor = new Color(100,160,210);
    private int radius = 30;

    public RButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setForeground(Color.WHITE);
        setBackground(normalColor);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);  // Button background
        super.paintComponent(g);
        
        g2.dispose();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
        setBackground(normalColor);
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setRadius(int radius) {
        this.radius = radius;

        repaint();
    }
}