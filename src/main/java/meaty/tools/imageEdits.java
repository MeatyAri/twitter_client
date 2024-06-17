package meaty.tools;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class imageEdits {

    public static Icon getResizeIcon(String iconPath, int width, int height) {
        Image image = new ImageIcon(iconPath).getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        return imageIcon;
    }

    public static JLabel getResizeImage(String iconPath, int width, int height) {
        JLabel imageIcon = new JLabel(getResizeIcon(iconPath, width, height));
        return imageIcon;
    }

    public static BufferedImage getCircularImage(BufferedImage image, int diameter) {
        // Scale the image to the specified diameter
        Image scaledImage = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
        BufferedImage bufferedScaledImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = bufferedScaledImage.createGraphics();
        g2.drawImage(scaledImage, 0, 0, null);
        g2.dispose();

        // Create a circular mask
        BufferedImage mask = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fill(new Ellipse2D.Double(0, 0, diameter, diameter));
        g2d.dispose();

        // Create the circular image with background
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = circularImage.createGraphics();
        applyQualityRenderingHints(g2d);

        // Set background color (change Color.WHITE to any other color as needed)
        g2d.setColor(new Color(40, 40, 40));
        g2d.fill(new Ellipse2D.Double(0, 0, diameter, diameter));

        // Draw the scaled image
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.drawImage(bufferedScaledImage, 0, 0, null);
        g2d.dispose();

        return circularImage;
    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }
}
