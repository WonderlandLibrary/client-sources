/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat.AntiKnockBack;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.AntiKnockBack.AntiKnockBack Mode;
import me.thekirkayt.event.events.PacketReceiveEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class NCP
extends AntiKnockBack Mode {
    public NCP(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onPacketReceiveEvent(PacketReceiveEvent event) {
        if (super.onPacketReceiveEvent(event) && event.getPacket() instanceof S12PacketEntityVelocity) {
            event.setCancelled(true);
        }
        return true;
    }
}

