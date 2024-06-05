package com.boomer.client.module.modules.other;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.game.TickEvent;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.Printer;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

import java.awt.*;
import java.util.*;

/**
 * @author Xen for BoomerWare
 * @since 8/11/2019
 **/
public class VanishDetector extends Module {
    private Set<UUID> vanished = new HashSet<>();
    private HashMap<UUID, String> uuids = new HashMap<>();

    public VanishDetector() {
        super("VanishDetector", Category.OTHER, new Color(0xB5FFEE).getRGB());
        setDescription("no vanish niggas");
        setLabel("Vanish Detector");
    }

    @Handler
    public void onTick(TickEvent event) {
        if (mc.getNetHandler() != null) {
            mc.getNetHandler().getRealPlayerInfoMap().values().forEach(info -> {
                if (info.getGameProfile().getName() != null) uuids.put(info.getGameProfile().getId(), info.getGameProfile().getName());
            });
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S38PacketPlayerListItem) {
            S38PacketPlayerListItem packet = (S38PacketPlayerListItem) event.getPacket();
            if (packet.func_179768_b() == S38PacketPlayerListItem.Action.UPDATE_LATENCY) {
                packet.func_179767_a().forEach(data -> {
                    if (mc.getNetHandler().getPlayerInfo(data.getProfile().getId()) == null) {
                        if (!vanished.contains(data.getProfile().getId()))
                            Printer.print(getName(data.getProfile().getId()) + " is now vanished.");
                        vanished.add(data.getProfile().getId());
                    }
                });
            }
        }
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if(vanished != null) {
            vanished.forEach(uuid -> {
                if (mc.getNetHandler().getPlayerInfo(uuid) != null)
                    Printer.print(getName(uuid) + " is no longer vanished.");
                    vanished.remove(uuid);
            });
        }
    }

    public String getName(UUID uuid) {
        if (uuids.containsKey(uuid)) {
            return uuids.get(uuid);
        }
        return "undefined - " + uuid.toString();
    }


}
