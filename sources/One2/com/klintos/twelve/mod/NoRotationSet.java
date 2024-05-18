// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import com.klintos.twelve.mod.events.EventPacketRecieve;

public class NoRotationSet extends Mod
{
    public NoRotationSet() {
        super("NoRotationSet", 0, ModCategory.MISC);
    }
    
    @EventTarget
    public void onPacketRecieve(final EventPacketRecieve event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook)event.getPacket();
            if (NoRotationSet.mc.thePlayer != null && NoRotationSet.mc.thePlayer.rotationYaw != -180.0f && NoRotationSet.mc.thePlayer.rotationPitch != 0.0f) {
                poslook.field_148936_d = NoRotationSet.mc.thePlayer.rotationYaw;
                poslook.field_148937_e = NoRotationSet.mc.thePlayer.rotationPitch;
            }
        }
    }
}
