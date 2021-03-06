package br.com.berkleysocket.server;

import br.com.berkleysocket.view.ServerView;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ChatServerThread extends Thread {

    private Server server;
    private Socket socket;
    private SocketAddress ID;
    private BufferedInputStream bis = null;
    private DataInputStream dis = null;
    private BufferedOutputStream bos = null;
    private DataOutputStream dos = null;
    private ServerView view;

    public ChatServerThread(Server _server, Socket _socket, ServerView view) {
        super();
        server = _server;
        socket = _socket;
        ID = socket.getRemoteSocketAddress();
        this.view = view;
    }

    public SocketAddress getID() {
        return ID;
    }

    public void send(String message) {
        //try {
            /*dos.writeUTF(message);
            dos.flush();*/
            view.addMessage(message);
        /*} catch (IOException e) {
            System.out.println("Client " + socket.getRemoteSocketAddress() + " error sending : " + e.getMessage());
            server.remove(ID);
        }*/
    }

    @Override
    public void run() {
        try {
            //System.out.println("Client " + socket.getRemoteSocketAddress() + " connected to server...");
            view.addMessage("Cliente " + socket.getRemoteSocketAddress() + " conectado ao servidor...");

            bis = new BufferedInputStream(socket.getInputStream());
            dis = new DataInputStream(bis);
            bos = new BufferedOutputStream(socket.getOutputStream());
            dos = new DataOutputStream(bos);

            while (true) {
                server.handle(ID, dis.readUTF());
            }
        } catch (IOException e) {
            //System.out.println("Client " + socket.getRemoteSocketAddress() + " error reading : " + e.getMessage());
            server.remove(ID);
        }
    }

    public void close() throws IOException {
        //System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnect from server...");
        view.addMessage("Cliente " + socket.getRemoteSocketAddress() + " desconectado do servidor...");
        socket.close();
        dis.close();
        dos.close();
    }
}
