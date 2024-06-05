/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.java_websocket.client.WebSocketClient
 *  org.java_websocket.handshake.ServerHandshake
 */
package cc.diablo.manager;

import cc.diablo.Main;
import cc.diablo.helpers.render.ChatHelper;
import java.net.URI;
import java.net.URISyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class IRCClient
extends WebSocketClient {
    public static char SPLIT = '\u0000';

    public IRCClient() throws URISyntaxException {
        super(new URI("ws://66.135.1.39:1337"));
        this.setAttachment(Main.username);
        this.addHeader("name", (String)this.getAttachment());
        this.addHeader("uid", Main.uid);
    }

    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("IRC Connected");
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7bIRC\u00a77] \u00a7fConnected"));
    }

    public void onMessage(String s) {
        System.out.println(s);
        if (s.contains(Character.toString(SPLIT))) {
            String[] split = s.split(Character.toString(SPLIT));
            if (split.length != 3) {
                return;
            }
            String username = split[0];
            String uid = split[1];
            String message = split[2];
            uid = uid.replace("(", "\u00a77(\u00a7b").replace(")", "\u00a77)");
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatHelper.translateColorCodes("\u00a77[\u00a7bIRC\u00a77] \u00a7b" + username + uid + " \u00a7f: " + message)));
        } else {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7bIRC\u00a77] " + s));
        }
    }

    public void onClose(int i, String s, boolean b) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a7bIRC\u00a77] \u00a7fDisconnected"));
    }

    public void onError(Exception e) {
        e.printStackTrace();
    }
}

