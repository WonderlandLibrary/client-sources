package mathax.client.utils.irc;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Logger;

import static mathax.client.MatHax.mc;

public class Client {
    protected static String username;
    protected static String password;

    public static Gson gson = new Gson();
    public static MutableText prefix = Text.literal("[IRC] ").formatted(Formatting.BLUE);
    public static Logger logger = Logger.getLogger("IRC");
    public static ClientEndpoint endpoint = null;

    static {
        try {
            File file = FabricLoader.getInstance().getConfigDir().resolve("mathax").resolve("irc.json").toFile();
            if (file.exists()) {
                HashMap data = gson.fromJson(new FileReader(file), HashMap.class);
                username = (String) data.get("username");
                password = (String) data.get("password");
            }else{
                updateAuth("", "");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendToChat(Text text) {
        mc.inGameHud.getChatHud().addMessage(prefix.copy().append(text));
    }

    private static void updateAuth(String username, String password) {
        Client.username = username;
        Client.password = password;
        try {
            File file = FabricLoader.getInstance().getConfigDir().resolve("mathax").resolve("irc.json").toFile();
            file.getParentFile().mkdir();
            FileWriter fileWriter = new FileWriter(file);
            HashMap<String, String> data = new HashMap<>();
            data.put("username", username);
            data.put("password", password);
            fileWriter.write(gson.toJson(data));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setAuth(String username, String password) {
        if (endpoint != null){
            MutableText text = Text.literal("You can't change your username or password while connected to the IRC server.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        } else if (username.isEmpty() || password.isEmpty()) {
            MutableText text = Text.literal("Username and password can't be empty.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        } else {
            updateAuth(username, password);
            MutableText text = Text.literal("Username and password updated.");
            text.setStyle(text.getStyle().withColor(0x00FF00));
            sendToChat(text);
        }
    }

    public static void connect() throws URISyntaxException, IOException {
        if (username.isEmpty() || password.isEmpty()) {
            MutableText text = Text.literal("Username and password can't be empty. Use .irc auth <username> <password> to set them.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        } else if (endpoint == null) {
            endpoint = new ClientEndpoint(new URI("ws://51.161.192.31:8107/irc"));
            endpoint.connect();
        } else {
            MutableText text = Text.literal("You are already connected to the IRC server.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        }
    }

    public static void disconnect() {
        if (endpoint != null) {
            endpoint.close();
            endpoint = null;
        }else {
            MutableText text = Text.literal("You are not connected to the IRC server.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        }
    }

    public static void send(String message) throws Exception {
        if (endpoint != null) {
            endpoint.sendBroadcast(message);
        }else {
            MutableText text = Text.literal(" You are not connected to the IRC server.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        }
    }

    public static void sendDirect(String user, String message) throws Exception {
        if (endpoint != null) {
            endpoint.sendDirect(user, message);
            sendToChat(Text.literal("To " + user + ": " + message).formatted(Formatting.RED));
        }else {
            MutableText text = Text.literal("You are not connected to the IRC server.");
            text.setStyle(text.getStyle().withColor(0xFF0000));
            sendToChat(text);
        }
    }
}
