package br.com.berkleysocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// SERVER : Multi Server3
// TIPE : One-Way Communication (Client to Server3)
// DESCRIPTION :
// A simple server that will accept multi client connection and display everything the client says on the screen.
// The Server3 can handle multiple clients simultaneously.
// If the client user types "exit", the client will quit.
public class Server3 implements Runnable {

    private int port = 7777;
    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private ChatServerThread client = null;

    public Server3() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server3 started on port " + serverSocket.getLocalPort() + "...");
            System.out.println("Waiting for client...");
            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            System.out.println("Error : " + e);
        }
    }

    @Override
    public void run() {
        while (thread != null) {
            try {
                // wait until client socket connecting, then add new thread
                addThreadClient(serverSocket.accept());
            } catch (IOException e) {
                System.out.println("Error : " + e);
            }
        }
    }

    public void addThreadClient(Socket socket) {
        client = new ChatServerThread(this, socket);
        client.start();
    }

    public static void main(String args[]) {
        Server3 server = new Server3();
    }
}