// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import me.chrest.event.events.PacketSendEvent;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockLiquid;
import me.chrest.event.events.BoundingBoxEvent;
import me.chrest.event.EventTarget;
import net.minecraft.block.material.Material;
import me.chrest.utils.ClientUtils;
import me.chrest.utils.LiquidUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;

@Mod(displayName = "Jesus")
public class Jesus extends Module
{
    public static boolean shouldOffsetPacket;
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        final Event.State state = event.getState();
        event.getState();
        if (state == Event.State.PRE) {
            if (LiquidUtils.isInLiquid() && ClientUtils.mc().thePlayer.isInsideOfMaterial(Material.air) && !ClientUtils.mc().thePlayer.isSneaking()) {
                ClientUtils.mc().thePlayer.motionY = 0.085;
            }
            if (!LiquidUtils.isOnLiquid() || LiquidUtils.isInLiquid() || !this.shouldSetBoundingBox()) {
                Jesus.shouldOffsetPacket = false;
            }
        }
    }
    
    @EventTarget
    private void onBoundingBox(final BoundingBoxEvent event) {
        if (!LiquidUtils.isInLiquid() && event.getBlock() instanceof BlockLiquid && ClientUtils.world().getBlockState(event.getBlockPos()).getBlock() instanceof BlockLiquid && (int)ClientUtils.world().getBlockState(event.getBlockPos()).getValue(BlockLiquid.LEVEL) == 0 && this.shouldSetBoundingBox() && event.getBlockPos().getY() + 1 <= ClientUtils.mc().thePlayer.boundingBox.minY) {
            event.setBoundingBox(new AxisAlignedBB(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ(), event.getBlockPos().getX() + 1, event.getBlockPos().getY() + 1, event.getBlockPos().getZ() + 1));
        }
    }
    
    @EventTarget
    private void onPacketSend(final PacketSendEvent event) {
        final Event.State state = event.getState();
        event.getState();
        if (state == Event.State.PRE && event.getPacket() instanceof C03PacketPlayer && LiquidUtils.isOnLiquid()) {
            final C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
            final boolean bl = Jesus.shouldOffsetPacket = !Jesus.shouldOffsetPacket;
            if (Jesus.shouldOffsetPacket) {
                final C03PacketPlayer c03PacketPlayer = packet;
                c03PacketPlayer.y -= 1.0E-6;
            }
        }
    }
    
    private boolean shouldSetBoundingBox() {
        return !ClientUtils.mc().thePlayer.isSneaking() && ClientUtils.mc().thePlayer.fallDistance < 4.0f;
    }
    
    public void onDisable() {
        Jesus.shouldOffsetPacket = false;
        super.disable();
    }
}
