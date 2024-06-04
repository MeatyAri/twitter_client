package meaty.home;

import javax.swing.*;

import meaty.MainFrame;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Home extends JPanel {
    private MainFrame mainFrame;
    private JTextArea messageArea;
    private JTextField messageField;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public Home(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(Color.BLACK);
        messageArea.setForeground(Color.WHITE);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.setBackground(Color.BLACK);
        messageField.setForeground(Color.WHITE);
        messageField.addActionListener(e -> sendMessage());
        add(messageField, BorderLayout.SOUTH);

        // Initialize socket connection to the server
        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(new IncomingReader()).start();
        } catch (IOException e) {
            mainFrame.showPage("NoConnection");
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        out.println(message);
        messageField.setText("");
    }

    private class IncomingReader implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    messageArea.append(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
