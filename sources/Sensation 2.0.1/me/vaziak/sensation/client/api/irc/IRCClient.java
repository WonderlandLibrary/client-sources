package me.vaziak.sensation.client.api.irc;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.util.Base64;

import java.io.IOException;
import java.net.URI;

public class IRCClient extends WebSocketClient {
    private static char SPLIT = '\u0000';
    private String username;

    public IRCClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");

    }

    @Override
    public void onMessage(String message) {
        if (message.contains(Character.toString(SPLIT))) {
            try {
                String[] split = message.split(Character.toString(SPLIT));
                String username = split[0];
                String msg = new String(Base64.decode(split[1]));
                System.out.println("[Chat] " + username + " -> " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("[Server] " + message);
        }

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnect from IRC");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String msg) {
        send(username + SPLIT + Base64.encodeBytes(msg.getBytes()));
    }
}