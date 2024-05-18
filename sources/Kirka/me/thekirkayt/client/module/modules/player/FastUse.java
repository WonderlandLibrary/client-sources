/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.player;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Module.Mod(displayName="FastUse")
public class FastUse
extends Module {
    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE && ClientUtils.player().getItemInUseDuration() == 16 && !(ClientUtils.player().getItemInUse().getItem() instanceof ItemBow) && !(ClientUtils.player().getItemInUse().getItem() instanceof ItemSword)) {
            for (int i = 0; i < 17; ++i) {
                ClientUtils.packet(new C03PacketPlayer(true));
            }
            ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}

