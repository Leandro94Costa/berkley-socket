package br.com.berkleysocket.server;

import br.com.berkleysocket.client.EchoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class EchoServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private EchoClient client;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("good bye");
                    break;
                }
                out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {
        client = new EchoClient();
        client.startConnection("127.0.0.1", 4444);
    }

    @After
    public void tearDown() {
        client.stopConnection();
    }

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);
    }
}