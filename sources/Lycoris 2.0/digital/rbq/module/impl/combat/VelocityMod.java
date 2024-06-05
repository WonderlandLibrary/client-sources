/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.combat;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import digital.rbq.annotations.Label;
import digital.rbq.events.packet.ReceivePacketEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;

@Label(value="Velocity")
@Category(value=ModuleCategory.COMBAT)
public final class VelocityMod
extends Module {
    @Listener(value=ReceivePacketEvent.class)
    public final void onReceivePacket(ReceivePacketEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity || packet instanceof S27PacketExplosion) {
            event.setCancelled();
        }
    }
}

