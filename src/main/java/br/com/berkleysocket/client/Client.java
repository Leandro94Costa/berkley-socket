package br.com.berkleysocket.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

// Client for Server1, Server2, Server3
public class Client {

    private String serverName = "localhost";
    private int serverPort = 7777;  // ServerView 1: 5555, ServerView 2: 6666, ServerView 3: 7777
    private Socket socket = null;
    private DataOutputStream dos = null;

    public Client() {
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Client started on port " + socket.getLocalPort()+"...");
            System.out.println("Connected to server " + socket.getRemoteSocketAddress());

            dos = new DataOutputStream(socket.getOutputStream());

            Scanner scan = new Scanner(System.in);
            while (true) {
                try {
                    System.out.print("Message to server: ");
                    String messageToServer = scan.nextLine();
                    if(messageToServer.equals("exit")){
                        break;
                    }
                    dos.writeUTF(messageToServer);
                    dos.flush();
                } catch (IOException e) {
                    break;
                }
            }
            dos.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        Client client = new Client();
    }
}