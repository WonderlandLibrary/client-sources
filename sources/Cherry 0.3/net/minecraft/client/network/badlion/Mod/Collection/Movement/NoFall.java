// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.network.badlion.Events.PacketSendEvent;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class NoFall extends Mod
{
    public NoFall() {
        super("Nofall", Category.OTHER);
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
    public void onSendPacket(final PacketSendEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            final C03PacketPlayer packet = (C03PacketPlayer)e.getPacket();
            packet.field_149474_g = true;
        }
    }
}
