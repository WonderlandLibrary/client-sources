// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.network.badlion.Events.EventPacket;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.network.badlion.Events.EventEntityCollision;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Jesus extends Mod
{
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
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
    public void onBoundingBox(final EventEntityCollision event) {
        if (event.getBlock() instanceof BlockLiquid && event.getEntity() == this.mc.thePlayer) {
            event.setBoundingBox(new AxisAlignedBB(event.getLocation().getX(), event.getLocation().getY(), event.getLocation().getZ(), event.getLocation().getX() + 1.0, event.getLocation().getY() + 1.0, event.getLocation().getZ() + 1.0));
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        if (EntityPlayerSP.isOnLiquid() && !this.mc.thePlayer.onGround) {
            this.mc.thePlayer.onGround = true;
            this.mc.thePlayer.motionY = 0.015;
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacket event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            if (EntityPlayerSP.isOnLiquid()) {
                final C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
                packet.setY((this.mc.thePlayer.ticksExisted % 2 == 0) ? (packet.getY() + 0.01) : (packet.getY() - 0.01));
            }
        }
    }
}
