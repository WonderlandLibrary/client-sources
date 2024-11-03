package net.silentclient.client.emotes.socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import net.silentclient.client.Client;
import net.silentclient.client.emotes.EmoteManager;
import net.silentclient.client.utils.NotificationUtils;
import org.json.JSONObject;

import java.net.URI;

public class EmoteSocket {
    public static EmoteSocket instance;

    public static EmoteSocket get() {
        if(instance == null) {
            instance = new EmoteSocket();
            return instance;
        }
        return instance;
    }

    private Socket socket;

    public EmoteSocket() {
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[] { WebSocket.NAME };

            this.socket = IO.socket(new URI("https://emotes.silentclient.net:443"), options);
        } catch (Exception err) {
            Client.logger.catching(err);
        }

        this.socket.on(Socket.EVENT_CONNECT, (Object... arg0) -> {
            Client.logger.info("Connected to Emotes Socket");
        }).on(Socket.EVENT_DISCONNECT, (Object... arg0) -> {
            Client.logger.info("Disconnected from Emotes Socket");
        }).on("error", (Object... arg0) -> {
            SocketError error = Client.getInstance().getGson().fromJson((String)arg0[0], SocketError.class);
            NotificationUtils.showNotification("Error", error.getError());
        }).on("startEmote", (Object... arg0) -> {
            Client.logger.info("startEmote: " + (String)arg0[0]);
            SocketShowEmote data = Client.getInstance().getGson().fromJson((String)arg0[0], SocketShowEmote.class);
            EmoteManager.sendEmote(data.username, data.emoteId);
        }).on("endEmote", (Object... arg0) -> {
            Client.logger.info("endEmote: " + (String)arg0[0]);
            SocketShowEmote data = Client.getInstance().getGson().fromJson((String)arg0[0], SocketShowEmote.class);
            EmoteManager.stop(data.username);
        });;
    }

    public void startEmote(int id) {
        this.socket.emit("startEmote", new JSONObject().put("accessToken", Client.getInstance().getUserData().getAccessToken()).put("emoteId", id).toString());
    }

    public void endEmote() {
        this.socket.emit("endEmote", new JSONObject().put("accessToken", Client.getInstance().getUserData().getAccessToken()).toString());
    }

    public void connect() {
        this.socket.connect();
    }

    public void disconnect() {
        this.socket.disconnect();
    }

    public static class SocketError {
        public String error;

        public String getError() {
            return error;
        }
    }

    public static class SocketShowEmote {
        public String username;
        public int emoteId;

        public String getUsername() {
            return username;
        }

        public int getEmoteId() {
            return emoteId;
        }
    }
}
