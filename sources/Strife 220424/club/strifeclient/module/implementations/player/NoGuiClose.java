package club.strifeclient.module.implementations.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.networking.PacketInboundEvent;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import net.minecraft.network.play.server.S2EPacketCloseWindow;


@ModuleInfo(name = "NoGuiClose", description = "Prevents the server from closing your open container.", category = Category.PLAYER)
public final class NoGuiClose extends Module {
    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundEventListener = e -> {
        if (e.getPacket() instanceof S2EPacketCloseWindow)
            e.setCancelled(true);
    };
}
