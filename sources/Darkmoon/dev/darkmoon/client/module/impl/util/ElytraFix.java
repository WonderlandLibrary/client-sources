package dev.darkmoon.client.module.impl.util;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@ModuleAnnotation(name = "ElytraFix", category = Category.UTIL)
public class ElytraFix extends Module {
    public static long delay;

    @EventTarget
    public void onUpdate(EventUpdate event) {
        ItemStack stack = mc.player.inventory.getItemStack();
        if (stack.getItem() instanceof ItemArmor && System.currentTimeMillis() > delay) {
            ItemArmor ia = (ItemArmor) stack.getItem();
            if (ia.armorType == EntityEquipmentSlot.CHEST
                    && mc.player.inventory.armorItemInSlot(2).getItem() == Items.ELYTRA) {
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                int nullSlot = findNullSlot();
                boolean needDrop = nullSlot == 999;
                if (needDrop) {
                    nullSlot = 9;
                }
                mc.playerController.windowClick(0, nullSlot, 1, ClickType.PICKUP, mc.player);
                if (needDrop) {
                    mc.playerController.windowClick(0, -999, 1, ClickType.PICKUP, mc.player);
                }
                delay = System.currentTimeMillis() + 300;
            }
        }
    }

    public static int findNullSlot() {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemAir) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return 999;
    }
}
