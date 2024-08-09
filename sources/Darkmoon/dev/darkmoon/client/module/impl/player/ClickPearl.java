package dev.darkmoon.client.module.impl.player;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.input.EventMouse;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;

@ModuleAnnotation(name = "ClickPearl", category = Category.PLAYER)
public class ClickPearl extends Module {

    @EventTarget
    public void onMouse(EventMouse event) {
        if (event.getButton() == 2 && !mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL)) {
            int hotbarPearls = InventoryUtility.getItemSlot(Items.ENDER_PEARL, true);
            if (hotbarPearls != -1) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(hotbarPearls));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            } else {
                int invPearls = InventoryUtility.getItemSlot(Items.ENDER_PEARL, false);
                if (invPearls != -1) {
                    mc.playerController.pickItem(invPearls);
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                    mc.playerController.pickItem(invPearls);
                }
            }
        }
    }
}

