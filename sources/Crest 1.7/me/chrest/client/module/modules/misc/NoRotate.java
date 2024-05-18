// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import me.chrest.event.EventTarget;
import me.chrest.utils.ClientUtils;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import me.chrest.event.events.PacketReceiveEvent;
import me.chrest.client.module.Module;

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
