/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import winter.event.EventListener;
import winter.event.events.PacketEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class NoFall
extends Module {
    public NoFall() {
        super("NoFall", Module.Category.Movement, -17937);
        this.setBind(0);
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
            if (this.mc.thePlayer.fallDistance > 3.0f) {
                packet.field_149474_g = true;
            }
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.mc.thePlayer.fallDistance > 3.0f && event.isPre()) {
            event.ground(true);
        }
    }
}

