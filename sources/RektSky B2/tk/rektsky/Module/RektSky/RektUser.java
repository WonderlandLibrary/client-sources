package tk.rektsky.Module.RektSky;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;
import tk.rektsky.Main.*;
import java.net.*;
import java.io.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;

public class RektUser extends Module
{
    public static Map<String, String> userCache;
    
    public RektUser() {
        super("RektUser", "Display RektSky's usernames in chat.", Category.REKTSKY);
        this.toggle();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PacketReceiveEvent) {
            final Packet p = ((PacketReceiveEvent)e).getPacket();
            if (p instanceof S02PacketChat) {
                final S02PacketChat packet = (S02PacketChat)p;
                final String originalMessage = packet.chatComponent.getFormattedText();
                if (originalMessage.contains("Reeker")) {
                    ((PacketReceiveEvent)e).setCanceled(true);
                    final int endIndex = originalMessage.indexOf("Reeker") + 16;
                    try {
                        final String msg5;
                        final int n;
                        String playerName;
                        String userName;
                        final Iterator<String> iterator;
                        String ign;
                        final URL url;
                        URL hwidUrl;
                        HttpURLConnection connection;
                        final BufferedReader bufferedReader;
                        BufferedReader input;
                        StringBuilder content;
                        String line;
                        final Object o;
                        final Throwable t2;
                        String verified;
                        String msg;
                        String color;
                        String cache;
                        String msg2;
                        String msg3;
                        String msg4;
                        new Thread(() -> {
                            try {
                                playerName = msg5.substring(msg5.indexOf("Reeker"), n);
                                userName = "";
                                RektUser.userCache.keySet().iterator();
                                while (iterator.hasNext()) {
                                    ign = iterator.next();
                                    if (ign.equals(playerName)) {
                                        userName = RektUser.userCache.get(ign);
                                    }
                                }
                                if (userName.equals("")) {
                                    new URL("http://RektClientBot.blapplejuice.repl.co/player?hwid=" + HwidUtil.getHwid() + "&ign=" + playerName);
                                    hwidUrl = url;
                                    connection = (HttpURLConnection)hwidUrl.openConnection();
                                    connection.setRequestMethod("GET");
                                    connection.setConnectTimeout(3000);
                                    connection.setReadTimeout(3000);
                                    try {
                                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                        input = bufferedReader;
                                        try {
                                            content = new StringBuilder();
                                            while (true) {
                                                line = input.readLine();
                                                if (o != null) {
                                                    content.append(line);
                                                    content.append(System.lineSeparator());
                                                }
                                                else {
                                                    break;
                                                }
                                            }
                                        }
                                        catch (Throwable t) {
                                            throw t;
                                        }
                                        finally {
                                            if (input != null) {
                                                if (t2 != null) {
                                                    try {
                                                        input.close();
                                                    }
                                                    catch (Throwable t3) {
                                                        t2.addSuppressed(t3);
                                                    }
                                                }
                                                else {
                                                    input.close();
                                                }
                                            }
                                        }
                                    }
                                    finally {
                                        connection.disconnect();
                                    }
                                    verified = content.toString();
                                    userName = verified.split(":::::")[1];
                                    RektUser.userCache.put(playerName, userName);
                                }
                                msg = "";
                                color = "";
                                cache = msg5.split(playerName)[0];
                                if (cache.contains("§")) {
                                    color = "§" + cache.substring(cache.indexOf("§"), cache.indexOf("§") + 1);
                                }
                                msg2 = msg + msg5.split(playerName)[0];
                                msg3 = msg2 + playerName + ChatFormatting.BLUE + " (" + userName + ") " + color;
                                msg4 = msg3 + msg5.split(playerName)[1];
                                Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(msg4));
                            }
                            catch (Exception ex) {
                                Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(msg5));
                            }
                        }).start();
                    }
                    catch (Exception ex2) {
                        Minecraft.getMinecraft().thePlayer.sendMessage(new ChatComponentText(originalMessage));
                    }
                }
            }
        }
    }
    
    static {
        RektUser.userCache = new HashMap<String, String>();
    }
}
