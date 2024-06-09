/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.module.Module;

public class Velocity
extends Module {
    public Velocity() {
        super("Velocity", Module.Category.Combat, -5357415);
        this.setBind(49);
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        this.mode(" 0.0%");
        if ((event.isOutgoing() || event.isIncoming()) && event.getPacket() instanceof S12PacketEntityVelocity) {
            event.setCancelled(true);
        }
    }
}

