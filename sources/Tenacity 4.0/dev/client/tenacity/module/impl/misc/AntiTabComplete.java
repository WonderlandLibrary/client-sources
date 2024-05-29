package dev.client.tenacity.module.impl.misc;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.event.EventListener;
import dev.event.impl.network.PacketReceiveEvent;
import dev.event.impl.network.PacketSendEvent;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;

@SuppressWarnings("unused")
public final class AntiTabComplete extends Module {

    public AntiTabComplete() {
        super("AntiTabComplete", Category.MISC, "prevents you from tab completing");
    }

    private final EventListener<PacketSendEvent> packetSendEventEventListener = event -> {
        if (event.getPacket() instanceof C14PacketTabComplete) {
            event.cancel();
        }
    };
    private final EventListener<PacketReceiveEvent> packetReceiveEventEventListener = event -> {
        if (event.getPacket() instanceof S3APacketTabComplete) {
            event.cancel();
        }
    };

}
