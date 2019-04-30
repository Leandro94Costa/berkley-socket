package br.com.berkleysocket.server;

import br.com.berkleysocket.view.ServerView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

// SERVER : Multi ServerView
// TIPE : Two-Way Communication (Client to ServerView, ServerView to Client)
// DESCRIPTION :
// A simple server that will accept multi client connection and display everything the client says on the screen.
// The ServerView can handle multiple clients simultaneously.
// The ServerView can sends all text received from any of the connected clients to all clients,
// this means the ServerView has to receive and send, and the client has to send as well as receive.
// If the client user types "exit", the client will quit.
public class Server implements Runnable {

    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private ChatServerThread clients[] = new ChatServerThread[50];
    private int clientCount = 0;
    private ServerView view;

    public Server(ServerView view) {
        this.view = view;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            view.addMessage("Servidor inicializado na porta " + serverSocket.getLocalPort() + "...");
            view.addMessage("Esperando por cliente...");
            view.setButtonsServerUp();
            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            view.addMessage("Não foi possível se conectar a porta " + port + "\nErro: " + e);
            view.setButtonsServerDown();
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                // wait until client socket connecting, then add new thread
                if (!serverSocket.isClosed()) {
                    addThreadClient(serverSocket.accept());
                }
            } catch (IOException e) {
                view.addMessage("Erro de servidor: " + e);
                stop();
            }
        }
    }

    private void stop() {
        if (thread != null) {
            thread = null;
        }
    }

    private int findClient(SocketAddress ID) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getID() == ID) {
                return i;
            }
        }
        return -1;
    }

    synchronized void handle(SocketAddress ID, String input) {
        if (input.equals("exit")) {
            clients[findClient(ID)].send("exit");
            remove(ID);
        } else {
            for (int i = 0; i < clientCount; i++) {
                if(clients[i].getID() == ID){
                    // if this client ID is the sender, just skip it
                    continue;
                }
                clients[i].send("\n" + ID + " says : " + input);
            }
        }
    }

    synchronized void remove(SocketAddress ID) {
        int index = findClient(ID);
        if (index >= 0) {
            ChatServerThread threadToTerminate = clients[index];
            view.addMessage("Removendo cliente " + ID + " at " + index);
            if (index < clientCount - 1) {
                for (int i = index + 1; i < clientCount; i++) {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try {
                threadToTerminate.close();
            } catch (IOException e) {
                view.addMessage("Erro ao thread: " + e.getMessage());
            }
        }
    }

    private void addThreadClient(Socket socket) {
        if (clientCount < clients.length) {
            clients[clientCount] = new ChatServerThread(this, socket);
            clients[clientCount].start();
            clientCount++;
        } else {
            view.addMessage("Cliente recusado: máximo " + clients.length + " alcançado.");
        }
    }

    public void shutdown() {
        try {
            stop();
            serverSocket.close();
            view.addMessage("Servidor encerrado");
            view.setButtonsServerDown();
        } catch (IOException e) {
            view.addMessage("Erro ao encerrar servidor. \nErro: " + e.getMessage());
        }
    }
}