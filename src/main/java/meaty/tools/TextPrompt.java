package meaty.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class TextPrompt extends JLabel implements FocusListener, DocumentListener {
    private Document document;

    public TextPrompt(String text, JTextComponent component) {
        document = component.getDocument();

        setText(text);
        setFont(component.getFont());
        setForeground(component.getForeground().brighter().brighter());
        setHorizontalAlignment(JLabel.LEADING);

        component.addFocusListener(this);
        document.addDocumentListener(this);

        component.setLayout(new BorderLayout());
        component.add(this);
        checkForPrompt();
    }

    public void changeAlpha(float alpha) {
        setForeground(new Color(getForeground().getRed(),
                getForeground().getGreen(),
                getForeground().getBlue(),
                (int)(alpha * 255)));
    }

    private void checkForPrompt() {
        if (document.getLength() > 0) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        checkForPrompt();
    }

    @Override
    public void focusLost(FocusEvent e) {
        checkForPrompt();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        checkForPrompt();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        checkForPrompt();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}