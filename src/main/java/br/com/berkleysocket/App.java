package br.com.berkleysocket;

import br.com.berkleysocket.client.GreetClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Hello world!
 *
 */
public class App 
{
    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }
}
