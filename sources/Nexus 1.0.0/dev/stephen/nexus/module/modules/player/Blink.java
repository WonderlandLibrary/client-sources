package dev.stephen.nexus.module.modules.player;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import net.minecraft.network.packet.Packet;

import java.util.ArrayList;

public class Blink extends Module {

    public Blink() {
        super("Blink", "Holds packets", 0, ModuleCategory.PLAYER);
    }

    public final ArrayList<Packet<?>> packets = new ArrayList<>();

    @Override
    public void onEnable() {
        packets.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (isNull()) {
            packets.clear();
        } else {
            for (Packet<?> packet : packets) {
                mc.getNetworkHandler().sendPacket(packet);
            }
        }
        super.onDisable();
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (mc.player == null || mc.world == null) return;

        if (event.getOrder() == TransferOrder.RECEIVE) {
            return;
        }

        Packet<?> packet = event.getPacket();

        event.cancel();
        packets.add(packet);
    };
}
