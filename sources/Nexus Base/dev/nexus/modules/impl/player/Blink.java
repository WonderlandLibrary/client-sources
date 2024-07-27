package dev.nexus.modules.impl.player;

import dev.nexus.events.bus.Listener;
import dev.nexus.events.bus.annotations.EventLink;
import dev.nexus.events.impl.EventPacketSend;
import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import net.minecraft.network.Packet;

import java.util.ArrayList;

public class Blink extends Module {
    public Blink() {
        super("Blink", 0, ModuleCategory.PLAYER);
    }
    private final ArrayList<Packet<?>> packets = new ArrayList<>();

    @EventLink
    public final Listener<EventPacketSend> onPacket = event -> {
        if(mc.theWorld == null)
            return;
        event.cancel();
        packets.add(event.getPacket());
    };

    @Override
    public void onEnable() {
        packets.clear();
    }

    @Override
    public void onDisable() {
        for (Packet<?> packet : packets) {
            mc.getNetHandler().addToSendQueue(packet);
        }
        packets.clear();
    }
}
