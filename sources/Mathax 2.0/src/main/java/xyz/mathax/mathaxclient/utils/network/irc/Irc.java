package xyz.mathax.mathaxclient.utils.network.irc;

import org.json.JSONObject;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.GameJoinedEvent;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.init.PostInit;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

// TODO: Make IRC MatHax account based.
// TODO: Remove settings saving and loading, won't be needed with accounts.

public class Irc {
    protected static String username = "";
    protected static String password = "";

    public static Client client = null;

    public static boolean enabled = false;

    @PostInit
    public static void init() {
        File file = new File(MatHax.FOLDER, "IRC.json");
        if (file.exists()) {
            JSONObject json = JSONUtils.loadJSON(file);
            if (json.has("username") && json.has("password")) {
                username = json.getString("username");
                password = json.getString("password");
            }
        }

        MatHax.EVENT_BUS.subscribe(Irc.class);
    }

    @EventHandler
    private static void onGameJoined(GameJoinedEvent event) {
        if (enabled && client == null) {
            join();
        }
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        if (!Utils.canUpdate()) {
            return;
        }

        if (enabled) {
            if (client == null) {
                join();
            }
        } else if (client != null) {
            leave();
        }
    }

    @EventHandler
    private static void onGameLeft(GameLeftEvent event) {
        if (client != null) {
            leave();
        }
    }

    private static void updateAuth(String username, String password) {
        Irc.username = username;

        File file = new File(MatHax.FOLDER, "IRC.json");
        file.getParentFile().mkdir();

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        JSONUtils.saveJSON(json, file);
    }

    public static void setAuth(String username, String password) {
        if (client != null){
            if (username.isBlank() && password.isBlank()) {
                ChatUtils.error("IRC", "You can't clear your username and password while connected.");
            } else {
                ChatUtils.error("IRC", "You can't change your username or password while connected.");
            }
        } else if (username.isEmpty() || password.isEmpty()) {
            ChatUtils.error("IRC", "Username and password can't be empty.");
        } else {
            updateAuth(username, password);

            ChatUtils.info("IRC", "Username and password updated.");
        }
    }

    public static void join() {
        if (username.isEmpty() || password.isEmpty()) {
            ChatUtils.error("IRC", "Username and password can't be empty. Use .irc auth <username> <password> to set them.");
        } else if (client == null) {
            try {
                /*String uri = MatHax.API_URL.replace("https", "ws");
                uri = uri.replace("http", "ws");*/
                String uri = "ws://51.161.192.31:8107/irc";
                client = new Client(new URI(uri + "/irc"));
                client.connect();

                enabled = true;
            } catch (URISyntaxException exception) {
                exception.printStackTrace();
            }
        } else {
            ChatUtils.error("IRC", "You are already connected.");
        }
    }

    public static void leave() {
        if (client != null) {
            client.close();
            client = null;
        } else {
            ChatUtils.error("IRC", "You are not connected.");
        }
    }

    public static void send(String message) {
        if (client != null) {
            client.sendBroadcast(username, message);
        } else {
            ChatUtils.error("IRC", "You are not connected.");
        }
    }

    public static void sendDirect(String user, String message) {
        if (client != null) {
            if (user.equals(username)) {
                ChatUtils.error("IRC", "You can't direct message yourself.");

                return;
            }

            client.sendDirect(username, user, message);

            ChatUtils.info("IRC", "To (highlight)%s(default): %s", user, message);
        } else {
            ChatUtils.error("IRC", "You are not connected.");
        }
    }
}