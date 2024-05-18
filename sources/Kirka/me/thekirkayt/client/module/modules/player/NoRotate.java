/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketReceiveEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@Module.Mod(displayName="NoRotate")
public class NoRotate
extends Module {
    @EventTarget
    private void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
            packet.field_148936_d = ClientUtils.yaw();
            packet.field_148937_e = ClientUtils.pitch();
        }
    }
}

