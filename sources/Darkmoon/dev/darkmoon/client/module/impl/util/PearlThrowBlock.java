package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.packet.EventSendPacket;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;

@ModuleAnnotation(name = "PearlThrow", category = Category.UTIL)
public class PearlThrowBlock extends Module {
    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (mc.player.getHeldItemMainhand().getItem() == Items.ENDER_PEARL) {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                CPacketPlayerTryUseItemOnBlock pac = (CPacketPlayerTryUseItemOnBlock) event.getPacket();
                ((CPacketPlayerTryUseItemOnBlock)pac).hand = EnumHand.OFF_HAND;
            }
        }
    }
}