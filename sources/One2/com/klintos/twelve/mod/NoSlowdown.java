// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import com.klintos.twelve.mod.events.EventPostUpdate;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.item.ItemPotion;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class NoSlowdown extends Mod
{
    public NoSlowdown() {
        super("NoSlowdown", 0, ModCategory.COMBAT);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        if (NoSlowdown.mc.thePlayer.isUsingItem() && !(NoSlowdown.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion)) {
            NoSlowdown.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
    
    @EventTarget
    public void onPostUpdate(final EventPostUpdate event) {
        if (NoSlowdown.mc.thePlayer.isUsingItem() && !(NoSlowdown.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion)) {
            NoSlowdown.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, -1, NoSlowdown.mc.thePlayer.inventory.getCurrentItem(), -1.0f, -1.0f, -1.0f));
        }
    }
    
    @Override
    public void onDisable() {
        if (NoSlowdown.mc.thePlayer.isUsingItem() && !(NoSlowdown.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion)) {
            NoSlowdown.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
