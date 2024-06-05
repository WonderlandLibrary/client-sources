package com.boomer.client.module.modules.combat;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.world.PacketEvent;
import com.boomer.client.module.Module;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

import java.awt.*;

public class AntiVelocity extends Module {

    public AntiVelocity() {
        super("AntiVelocity", Category.COMBAT, new Color(120, 120, 150, 255).getRGB());
        setDescription("Cancels velocity packets");
        setRenderlabel("Anti Velocity");

    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (!event.isSending()) {
            if ((event.getPacket() instanceof S12PacketEntityVelocity)) {
                event.setCanceled(true);
            }
            if (event.getPacket() instanceof S27PacketExplosion) {
                event.setCanceled(true);
            }
        }
    }
}
