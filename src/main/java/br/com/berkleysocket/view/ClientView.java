package br.com.berkleysocket.view;

import javax.swing.*;

public class ClientView extends JFrame {
    private JPanel rootPanel;
    private JPanel chatPanel;
    private JPanel messagePanel;
    private JTextPane textPane1;
    private JTextArea txtMessage;
    private JButton enviarButton;

    public ClientView() {
        add(rootPanel);
        setTitle("Chat");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        ClientView view = new ClientView();
        view.setVisible(true);
        view.txtMessage.requestFocus();
    }
}
