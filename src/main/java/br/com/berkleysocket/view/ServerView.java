package br.com.berkleysocket.view;

import br.com.berkleysocket.server.Server;
import br.com.berkleysocket.utils.Time;
import br.com.berkleysocket.utils.Validator;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ServerView extends JFrame {
    private JTextArea txtMessage;
    private JButton btnStop;
    private JPanel headerPanel;
    private JPanel messagePanel;
    private JPanel mainPanel;
    private JButton btnStart;
    private JTextPane txtPort;
    private JPanel portPanel;
    private JLabel lblPort;
    private JScrollPane messageScrollPane;
    private int port;

    private ServerView() {
        Server server = new Server(this);
        initComponents();
        listeners(server);
    }

    private void initComponents() {
        add(mainPanel);
        setTitle("Servidor");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void listeners(Server server) {
        btnStart.addActionListener(e -> {
            String validation = Validator.isValidPort(txtPort.getText());
            if (validation == null) {
                server.start(port);
            } else {
                addMessage(validation);
            }
        });
        btnStop.addActionListener(e -> server.shutdown());
        txtPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setButtonsServerDown();
            }
        });
    }

    public void addMessage(String message) {
        if ("".equals(txtMessage.getText())) {
            txtMessage.setText(Time.getTime() + " - " + message);
        } else {
            txtMessage.append("\n" + Time.getTime() + " - " + message);
        }
    }

    public void setButtonsServerUp() {
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        txtPort.setEnabled(false);
    }

    public void setButtonsServerDown() {
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        txtPort.setEnabled(true);
    }

    public static void main(String[] args) {
        ServerView view = new ServerView();
        view.setVisible(true);
    }
}