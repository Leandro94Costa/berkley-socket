package br.com.berkleysocket.view;

import br.com.berkleysocket.server.Server;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;

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

    public ServerView() {
        add(mainPanel);
        setTitle("Servidor");
        setSize(800, 600);
        setLocationRelativeTo(null);

        Server server = new Server(this);

        btnStart.addActionListener(e -> {
            if (isValidPort()) {
                server.start(port);
            }
        });
        btnStop.addActionListener(e -> server.shutdown());

        txtPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setButtonsServerDown();
                super.keyPressed(e);
            }
        });
    }

    private boolean isValidPort() {
        boolean isValid = false;
        try {
            port = Integer.parseInt(txtPort.getText());
            if (port > 0 && port <= 65535) {
                isValid = true;
            } else {
                addMessage("Porta TCP fora da faixa permitida (1 até 65535)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addMessage("Porta inválida, favor tente novamente");
        }
        return isValid;
    }

    public void addMessage(String message) {
        if ("".equals(txtMessage.getText())) {
            txtMessage.setText(getTime() + " - " + message);
        } else {
            txtMessage.setText(txtMessage.getText() + "\n" + getTime() + " - " + message);
        }
    }

    private String getTime() {
        Calendar now = Calendar.getInstance();
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String year = String.valueOf(now.get(Calendar.YEAR));
        String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String minutes = String.valueOf(now.get(Calendar.MINUTE));
        String seconds = String.valueOf(now.get(Calendar.SECOND));
        return day + "/" + month + "/" + year + " " + hour + ":" + minutes + ":" + seconds;
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