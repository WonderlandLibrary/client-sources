/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import com.mojang.authlib.GameProfile;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.event.EventManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketReceiveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.utils.ChatMessage;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.util.EnumChatFormatting;

@Com(names={"vanish", "vanished", "getvanished"})
public class Vanish
extends Command {
    private List<GameProfile> vanishedPlayers = new ArrayList<GameProfile>();

    public Vanish() {
        EventManager.register(this);
    }

    @EventTarget
    public void onReceivePacket(PacketReceiveEvent event) {
        S38PacketPlayerListItem packet;
        if (event.getPacket() instanceof S38PacketPlayerListItem && (packet = (S38PacketPlayerListItem)event.getPacket()).func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
            for (Object o : packet.func_179767_a()) {
                S38PacketPlayerListItem.AddPlayerData data = (S38PacketPlayerListItem.AddPlayerData)o;
                ClientUtils.mc();
                if (Minecraft.getNetHandler().func_175102_a(data.field_179964_d.getId()) != null || this.vanishedPlayers.contains((Object)data.field_179964_d)) continue;
                this.vanishedPlayers.add(data.field_179964_d);
            }
        }
    }

    @EventTarget
    public void onTick(TickEvent event) {
        if (ClientUtils.player() != null) {
            for (GameProfile profile : this.vanishedPlayers) {
                ClientUtils.mc();
                if (Minecraft.getNetHandler().func_175102_a(profile.getId()) == null) continue;
                this.vanishedPlayers.remove((Object)profile);
            }
        } else {
            this.vanishedPlayers.clear();
        }
    }

    @Override
    public void runCommand(String[] args) {
        if (this.vanishedPlayers.isEmpty()) {
            ClientUtils.sendMessage("No vanished players");
        } else {
            ClientUtils.sendMessage("Vanished Players:");
            for (GameProfile profile : this.vanishedPlayers) {
                if (profile == null) continue;
                this.printName(profile.getId());
            }
        }
    }

    public void printName(final UUID uuid) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    String line;
                    URL url = new URL("https://namemc.com/profile/" + uuid.toString());
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String name = null;
                    while ((line = reader.readLine()) != null) {
                        if (!line.contains("<title>")) continue;
                        name = line.split("\u00a7")[0].trim().replaceAll("<title>", "").replaceAll("</title>", "").replaceAll("\u2013 Minecraft Profile \u2013 NameMC", "").replaceAll("\u00e2\u20ac\u201c Minecraft Profile \u00e2\u20ac\u201c NameMC", "");
                    }
                    reader.close();
                    new ChatMessage.ChatMessageBuilder(false, false).appendText(name).setColor(EnumChatFormatting.GRAY).setColor(EnumChatFormatting.ITALIC).build().displayClientSided();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    new ChatMessage.ChatMessageBuilder(false, false).appendText(uuid.toString()).setColor(EnumChatFormatting.GRAY).setColor(EnumChatFormatting.ITALIC).build().displayClientSided();
                }
            }
        }).start();
    }

    @Override
    public String getHelp() {
        return "Vanish - vanish <vanished, getvanished>.";
    }

}

