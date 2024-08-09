package dev.darkmoon.client.utility.player;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;

import java.util.Iterator;

public class InventoryUtility implements Utility {
    public static boolean hasItemWithEnchantment(ItemStack itemStack) {
        if (itemStack.isEmpty() || itemStack.getItem() != Items.TOTEM_OF_UNDYING) {
            return false;
        }
        return itemStack.isItemEnchanted();
    }
    public static boolean doesHotbarHaveAxe() {
        for (int i = 0; i < 9; ++i) {
            mc.player.inventory.getStackInSlot(i);
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemAxe) {
                return true;
            }
        }
        return false;
    }
    public static int getElytra() {
        Iterator var0 = mc.player.getArmorInventoryList().iterator();

        while(var0.hasNext()) {
            ItemStack stack = (ItemStack)var0.next();
            if (stack.getItem() == Items.ELYTRA) {
                return -2;
            }
        }

        int slot = -1;

        for(int i = 0; i < 36; ++i) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == Items.ELYTRA) {
                slot = i;
                break;
            }
        }

        if (slot < 9 && slot != -1) {
            slot += 36;
        }

        return slot;
    }
    public static void swapElytraToChestplate() {
        for (ItemStack stack : mc.player.inventory.armorInventory) {
            if (stack.getItem() == Items.ELYTRA) {

                int slot = getSlotWithArmor() < 9 ? getSlotWithArmor() + 36 : getSlotWithArmor();
                if (getSlotWithArmor() != -1) {
                    mc.playerController.windowClick(0, slot, 1, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                }
            }
        }
    }
    public static
    int getSlotWithArmor() {
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack = mc.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() == Items.DIAMOND_CHESTPLATE || itemStack.getItem() == Items.GOLDEN_CHESTPLATE || itemStack.getItem() == Items.LEATHER_CHESTPLATE || itemStack.getItem() == Items.CHAINMAIL_CHESTPLATE || itemStack.getItem() == Items.IRON_CHESTPLATE) {
                return i < 9 ? i + 36 : i;
            }
        }
        return -1;
    }
    public static int getAxe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemAxe) {
                return i;
            }
        }
        return 1;
    }
    public static int getFireworks() {
        for(int i = 0; i < 9; ++i) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemFirework) {
                return i;
            }
        }

        return -1;
    }
    public static int getItemSlot(Item item, boolean isHotbar) {
        for(int i = 0; i < (isHotbar ? 9 : 45); ++i) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
        }

        return -1;
    }

    public static int getItemSlot(Item input) {
        if (input == mc.player.getHeldItemOffhand().getItem()) return -2;
        for (int i = 36; i >= 0; i--) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
}
