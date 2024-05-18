// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.ItemSlowEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Mod(displayName = "No Slowdown")
public class NoSlowdown extends Module
{
    @Op(name = "Vanilla")
    public boolean vanilla;
    public static boolean wasOnground;
    
    static {
        NoSlowdown.wasOnground = true;
    }
    
    @EventTarget
    private void onItemUse(final ItemSlowEvent event) {
        event.setCancelled(true);
    }
    
    @EventTarget(4)
    private void onUpdate(final UpdateEvent event) {
        if (!this.vanilla && ClientUtils.player().isBlocking() && (ClientUtils.player().motionX != 0.0 || ClientUtils.player().motionZ != 0.0) && NoSlowdown.wasOnground) {
            if (event.getState() == Event.State.PRE) {
                ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
            else if (event.getState() == Event.State.POST) {
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
            }
        }
        if (!new Speed().getInstance().isEnabled() || !((Speed)new Speed().getInstance()).bhop.getValue()) {
            NoSlowdown.wasOnground = ClientUtils.player().onGround;
        }
    }
}
