package dev.darkmoon.client.module.impl.player;

import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

@ModuleAnnotation(name = "ElytraSwap", category = Category.PLAYER)
public class ElytraSwap extends Module {
    private int getChestPlateSlot() {
        Item[] items = {Items.DIAMOND_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.LEATHER_CHESTPLATE};
        for (Item item : items) {
            int slot = InventoryUtility.getItemSlot(item);
            if (slot != -1) {
                return slot;
            }
        }
        return -1;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (mc.player.inventory.getStackInSlot(38).getItem().equals(Items.ELYTRA)) {
            int slot = getChestPlateSlot();
            if (slot != -1) {
                mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
            } else {
                ChatUtility.addChatMessage("Нагрудник не найден!");
            }
        } else {
            int elytraSlot = InventoryUtility.getItemSlot(Items.ELYTRA);
            if (elytraSlot != -1) {
                mc.playerController.windowClick(0, elytraSlot, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, elytraSlot, 1, ClickType.PICKUP, mc.player);
            } else {
                ChatUtility.addChatMessage("Элитра не найдена!");
            }
        }
        toggle();
    }
}
