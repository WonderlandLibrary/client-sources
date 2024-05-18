// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.player;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Mod(displayName = "Fast Use")
public class FastUse extends Module
{
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE && ClientUtils.player().getItemInUseDuration() == 16 && !(ClientUtils.player().getItemInUse().getItem() instanceof ItemBow)) {
            for (int i = 0; i < 17; ++i) {
                ClientUtils.packet(new C03PacketPlayer(true));
            }
            ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
