package br.com.berkleysocket.view;

import br.com.berkleysocket.utils.Validator;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConnectorView extends JFrame {
    private JPanel rootPanel;
    private JButton btnConnect;
    private JTextField txtPort;
    private JTextField txtName;
    private JLabel txtValidationMsg;
    private int port;

    private ConnectorView() {
        initComponents();
        listeners();
    }

    private void initComponents() {
        add(rootPanel);
        setTitle("Conectar");
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void listeners() {
        btnConnect.addActionListener(e -> {
            connect();
        });
        txtName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    connect();
                }
            }
        });
    }

    private void connect() {
        String validation = Validator.isValidPort(txtPort.getText());
        if (validation == null) {
            ClientView clientView = new ClientView(port, txtName.getText());
            this.dispose();
            clientView.setVisible(true);
            clientView.setFocus();
        } else {
            txtValidationMsg.setText(validation);
        }
    }

    public static void main(String[] args) {
        ConnectorView view = new ConnectorView();
        view.setVisible(true);
    }
}