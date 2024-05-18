// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Combat;

import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.client.network.badlion.Events.EventPacketTake;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.NumValue;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Velocity extends Mod
{
    private NumValue Velocity;
    
    public Velocity() {
        super("Velocity", Category.COMBAT);
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
        if (event.packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.packet;
            if (this.mc.theWorld.getEntityByID(packet.func_149412_c()) == this.mc.thePlayer) {
                event.setCancelled(true);
            }
        }
    }
}
