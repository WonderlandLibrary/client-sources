// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import java.net.URLConnection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.EventTarget;
import java.util.Iterator;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.PacketReceiveEvent;
import java.util.UUID;
import java.util.ArrayList;
import me.chrest.client.module.Module;

@Mod(displayName = "AntiVanish")
public class AntiVanish extends Module
{
    private ArrayList<UUID> vanished;
    
    public AntiVanish() {
        (this.vanished = new ArrayList<UUID>()).clear();
    }
    
    @EventTarget
    private void onReceive(final PacketReceiveEvent event) {
        if (ClientUtils.world() != null && event.getPacket() instanceof S38PacketPlayerListItem) {
            final S38PacketPlayerListItem listItem = (S38PacketPlayerListItem)event.getPacket();
            if (listItem.func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
                for (final S38PacketPlayerListItem.AddPlayerData data : listItem.func_179767_a()) {
                    if (ClientUtils.mc().getNetHandler().func_175102_a(data.getField_179964_d().getId()) == null && !this.checkList(data.getField_179964_d().getId())) {
                        ClientUtils.sendMessage("§c" + this.getName(data.getField_179964_d().getId()) + "is vanished.");
                    }
                }
            }
        }
    }
    
    @EventTarget(0)
    private void onUpdate(final UpdateEvent event) {
        event.getState();
        if (Event.State.PRE == event.getState()) {
            this.setSuffix(new StringBuilder().append(ClientUtils.player().sendQueue.getPlayerInfoMap().size()).toString());
            for (final UUID uuid : this.vanished) {
                if (ClientUtils.mc().getNetHandler().func_175102_a(uuid) != null) {
                    ClientUtils.sendMessage("§c" + this.getName(uuid) + "is no longer vanished.");
                    this.vanished.remove(uuid);
                }
            }
        }
    }
    
    public String getName(final UUID uuid) {
        try {
            final URL url = new URL("https://namemc.com/profile/" + uuid.toString());
            final URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String name = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("<title>")) {
                    name = line.split("§")[0].trim().replaceAll("<title>", "").replaceAll("</title>", "").replaceAll("\u2013 Minecraft Profile \u2013 NameMC", "").replaceAll("\u00e2\u20ac\u201c Minecraft Profile \u00e2\u20ac\u201c NameMC", "");
                }
            }
            reader.close();
            return name;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "(Failed) " + uuid;
        }
    }
    
    public void onEnable() {
        this.vanished.clear();
        super.enable();
    }
    
    public void onDisable() {
        this.vanished.clear();
        super.disable();
    }
    
    private boolean checkList(final UUID uuid) {
        if (this.vanished.contains(uuid)) {
            this.vanished.remove(uuid);
            return true;
        }
        this.vanished.add(uuid);
        return false;
    }
}
