/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.NCP Speed;
import me.thekirkayt.client.module.modules.speed.NCP Speed.Speed;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.ItemSlowEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Module.Mod(displayName="NoSlowdown")
public class NoSlowdown
extends Module {
    @Option.Op
    public boolean ncp;
    public static boolean wasOnground = true;

    @EventTarget
    private void onItemUse(ItemSlowEvent event) {
        event.setCancelled(true);
    }

    @EventTarget(value=4)
    private void onUpdate(UpdateEvent event) {
        if (!this.ncp && ClientUtils.player().isBlocking() && (ClientUtils.player().motionX != 0.0 || ClientUtils.player().motionZ != 0.0) && wasOnground) {
            if (event.getState() == Event.State.PRE) {
                ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            } else if (event.getState() == Event.State.POST) {
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
            }
        }
        if (!new NCP Speed().getInstance().isEnabled() || !((Boolean)((NCP Speed)new NCP Speed().getInstance()).latest.getValue()).booleanValue()) {
            wasOnground = ClientUtils.player().onGround;
        }
    }
}

