package mathax.client.utils.irc;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.crypto.SecretKey;
import java.net.URI;
import java.security.SecureRandom;

public class ClientEndpoint extends WebSocketClient {
    protected SecretKey secretKey;
    protected int iv;

    public ClientEndpoint(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Client.sendToChat(Text.literal("Connected to IRC server."));
        iv = new SecureRandom().nextInt() & Integer.MAX_VALUE;
        try {
            secretKey = CryptUtils.psk2sk(Client.password, iv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(String message) {
        Client.logger.info(message);
        Message msg = Message.fromJSON(message);
        try {
            switch (msg.type) {
                case BROADCAST -> {
                    msg = msg.decrypt(this.secretKey, this.iv);
                    Client.sendToChat(Text.literal(msg.data.get("from") + ": " + msg.data.get("message")).formatted(Formatting.WHITE));
                }
                case DIRECT_MESSAGE -> {
                    msg = msg.decrypt(this.secretKey, this.iv);
                    Client.sendToChat(Text.literal("From " + msg.data.get("from") + ": ").formatted(Formatting.RED).append(Text.literal(msg.data.get("message"))));
                }
                case PUB_KEY ->
                    send(Message.auth(msg.data.get("public_key"), this.secretKey, this.iv).toJSON());
                case PING -> send(Message.ping().toJSON());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Client.logger.info(reason);
        Client.sendToChat(Text.literal("Disconnected from IRC server").formatted(Formatting.RED));
        Client.endpoint = null;
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        Client.sendToChat(Text.literal("Error: " + ex.getMessage()).formatted(Formatting.RED));
    }

    public void sendDirect(String to, String message) throws Exception {
        send(Message.directMessage(to, message).encrypt(this.secretKey, this.iv).toJSON());
    }

    public void sendBroadcast(String message) throws Exception {
        send(Message.broadcast(message).encrypt(this.secretKey, this.iv).toJSON());
    }
}
