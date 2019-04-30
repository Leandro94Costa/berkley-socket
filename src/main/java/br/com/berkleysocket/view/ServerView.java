package br.com.berkleysocket.view;

import javax.swing.*;

public class ServerView extends JFrame {
    private JTextArea txtMessage;
    private JButton btnStop;
    private JPanel btnPanel;
    private JPanel txtPanel;
    private JPanel mainPanel;
    private JButton btnStart;

    public ServerView() {
        add(mainPanel);
        setTitle("Servidor");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public void addMessage(String message) {
        if ("".equals(txtMessage.getText())) {
            txtMessage.setText(message);
        } else {
            txtMessage.setText(txtMessage.getText() + "\n" + message);
        }
    }

    public void setButtonsServerUp() {
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }

    public void setButtonServerDown() {
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
    }
}
