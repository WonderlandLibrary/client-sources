package me.teus.eclipse.modules.impl.combat;

import me.teus.eclipse.events.packet.EventReceivePacket;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.ModeValue;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import xyz.lemon.event.bus.Listener;

@Info(name = "Velocity", displayName = "Velocity", category = Category.COMBAT)
public class Velocity extends Module {
    public Listener<EventReceivePacket> eventReceivePacketListener = event -> {
        EventReceivePacket e = (EventReceivePacket) event;
        if (e.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) e.getPacket();
            if (packet.getEntityID() == mc.thePlayer.getEntityId()) {
                e.setCancelled(true);
            }
        } else if (e.getPacket() instanceof S27PacketExplosion) {
            S27PacketExplosion packet = (S27PacketExplosion) e.getPacket();
            e.setCancelled(true);
        }
    };
}
