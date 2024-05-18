// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import me.chrest.event.Event;
import me.chrest.utils.ClientUtils;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.EventTarget;
import me.chrest.event.events.ItemSlowEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "No Slowdown")
public class NoSlowdown extends Module
{
    @Option.Op(name = "Vanilla")
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
