package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.irc.ChatType;
import me.vaziak.sensation.client.api.irc.IRCClient;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.java_websocket.WebSocketImpl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class IRC extends Module {
    public IRC() {
        super("IRC", Category.MISC);
    }

    private IRCClient chatClient;

    @Override
    protected void onEnable() {
        try {
            chatClient = new IRCClient(new URI("wss://sensation-irc.herokuapp.com:1337"));
            WebSocketImpl.DEBUG = false;

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            SSLSocketFactory factory = sslContext.getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();
            chatClient.setSocket(factory.createSocket());

            chatClient.connectBlocking();

            chatClient.setUsername(Sensation.instance.username);
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Collect
    public void onEvent(SendPacketEvent event) {
        if (event.getPacket() instanceof C01PacketChatMessage) {
            C01PacketChatMessage c01PacketChatMessage = (C01PacketChatMessage) event.getPacket();

            if (Sensation.instance.chatType == ChatType.IRC && chatClient != null) {
                chatClient.sendMessage(c01PacketChatMessage.getMessage());
                event.setCancelled(true);
            }
        }
    }

    @Override
    protected void onDisable() {
        if (Objects.nonNull(chatClient)) {
            chatClient.close();
        }
    }
}
