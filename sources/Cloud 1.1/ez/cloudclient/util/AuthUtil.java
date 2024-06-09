package ez.cloudclient.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class AuthUtil {

    static JTextField textfield1;

    public void auth() {
        JFrame frame = new JFrame("CloudClient Auth");
        frame.setVisible(true);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        textfield1 = new JTextField("Status: Loading...", 10);
        textfield1.setVisible(true);
        String acces = null;
        try {
            String sURL = "https://cloudclient.glitch.me/?name=" + Minecraft.getMinecraft().getSession().getUsername();
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootobj = root.getAsJsonObject();
            acces = rootobj.get("allowed").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setTitle("Connecting");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.setTitle("Loading");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (acces != null && acces.equalsIgnoreCase("true")) {
            frame.setTitle("Done");
        } else {
            frame.setTitle("No access");
            Minecraft.getMinecraft().shutdownMinecraftApplet();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
            frame.setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
