package tk.rektsky.Utils;

import tk.rektsky.*;
import tk.rektsky.Main.*;
import net.minecraft.client.renderer.texture.*;
import java.nio.file.*;
import javax.imageio.*;
import java.io.*;
import java.net.*;
import java.awt.image.*;
import net.arikia.dev.drpc.*;

public class DiscordManager
{
    private static final long created;
    public static boolean running;
    public static DiscordUser discordUser;
    
    public static void startUp() {
        DiscordManager.running = true;
        final DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> {
            DiscordManager.discordUser = discordUser;
            updateRPC("RektSky Client B2  |  Public Beta", "[" + Client.role + "] " + Client.userName + " IGN:" + Auth.ign);
            return;
        }).build();
        DiscordRPC.discordInitialize("797038611098370108", handlers, true);
        new Thread("DiscordRPC") {
            @Override
            public void run() {
                while (DiscordManager.running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }
    
    public static DynamicTexture getAvatar() {
        if (DiscordManager.discordUser == null) {
            return null;
        }
        try {
            final URL url = new URL("https://cdn.discordapp.com/avatars/" + DiscordManager.discordUser.userId + "/" + DiscordManager.discordUser.avatar + ".png?size=64");
            File avatarFile = new File("RektSky/avatars/");
            System.out.println(url.toString());
            avatarFile.mkdirs();
            avatarFile = new File("RektSky/avatars/" + DiscordManager.discordUser.userId + ".png");
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.101 Safari/537.36");
            connection.connect();
            Files.copy(connection.getInputStream(), Paths.get("RektSky/avatars/" + DiscordManager.discordUser.userId + ".png", new String[0]), StandardCopyOption.REPLACE_EXISTING);
            final BufferedImage image = ImageIO.read(new FileInputStream("RektSky/avatars/" + DiscordManager.discordUser.userId + ".png"));
            final DynamicTexture dynamicTexture = new DynamicTexture(image);
            dynamicTexture.updateDynamicTexture();
            return dynamicTexture;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void updateRPC(final String firstLine, final String secondLine) {
        final DiscordRichPresence rpc = new DiscordRichPresence.Builder(secondLine).setBigImage("icon", "Rekt Client").setSmallImage(Auth.role, Auth.role).setDetails(firstLine).setStartTimestamps(DiscordManager.created).build();
        DiscordRPC.discordUpdatePresence(rpc);
    }
    
    public static void shutdownRPC() {
        DiscordManager.running = false;
        DiscordRPC.discordShutdown();
    }
    
    static {
        created = System.currentTimeMillis();
        DiscordManager.running = true;
        DiscordManager.discordUser = null;
    }
}
