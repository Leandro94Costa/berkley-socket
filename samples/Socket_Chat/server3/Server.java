package br.com.berkleysocket.samples.Socket_Chat.server3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// SERVER : Multi ServerView
// TIPE : One-Way Communication (Client to ServerView)
// DESCRIPTION : 
// A simple server that will accept multi client connection and display everything the client says on the screen. 
// The ServerView can handle multiple clients simultaneously.
// If the client user types "exit", the client will quit.
public class Server implements Runnable {

    private int port = 8081;
    private ServerSocket serverSocket = null;
    private Thread thread = null;
    private ChatServerThread client = null;

    public Server() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("ServerView started on port " + serverSocket.getLocalPort() + "...");
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
        Server server = new Server();
    }
}
