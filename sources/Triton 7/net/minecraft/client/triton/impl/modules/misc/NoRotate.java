// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketReceiveEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@Mod(displayName = "No Rotate")
public class NoRotate extends Module
{
    @EventTarget
    private void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
            packet.field_148936_d = ClientUtils.yaw();
            packet.field_148937_e = ClientUtils.pitch();
        }
    }
}
