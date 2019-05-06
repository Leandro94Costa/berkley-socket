package br.com.berkleysocket.view;

import br.com.berkleysocket.utils.Time;

import javax.swing.*;

public class ClientView extends JFrame {
    private JPanel rootPanel;
    private JPanel chatPanel;
    private JPanel messagePanel;
    private JTextArea txtChat;
    private JTextArea txtMessage;
    private JButton sendButton;
    private JScrollPane chatScroll;
    private Integer port;
    private String name;

    ClientView(Integer port, String name) {
        this.port = port;
        this.name = name;
        initComponents();
        listeners();
    }

    private void initComponents() {
        add(rootPanel);
        setTitle("Chat");
        setSize(800, 600);
        setLocationRelativeTo(null);
        txtMessage.setBorder(BorderFactory.createEtchedBorder());
        addMessage("Conectado a porta: " + port, "Server");
        addMessage("Seja bem-vindo(a) " + name, "Server");
    }

    private void listeners() {
        sendButton.addActionListener(e -> sendMessage());

    }

    private void sendMessage() {
        addMessage(txtMessage.getText(), name);
    }

    private void addMessage(String message, String origin) {
        if ("".equals(txtChat.getText())) {
            txtChat.setText(Time.getTime() + "\n" + origin + ": " + message + "\n");
        } else {
            txtChat.append("\n" + Time.getTime() + "\n" + origin + ": " + message + "\n");
        }
    }

    void setFocus() {
        txtMessage.requestFocus();
    }
}
