// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Other;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.client.network.badlion.Events.EventPacketTake;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class NoRotate extends Mod
{
    public NoRotate() {
        super("NoRotate", Category.OTHER);
    }
    
    @Override
    public void onEnable() {
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onPacketTake(final EventPacketTake event) {
        if (event.packet instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.packet;
            packet.yaw = this.mc.thePlayer.rotationYaw;
            packet.pitch = this.mc.thePlayer.rotationPitch;
        }
    }
}
