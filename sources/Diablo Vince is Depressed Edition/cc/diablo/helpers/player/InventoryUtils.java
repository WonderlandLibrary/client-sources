/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.player;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InventoryUtils {
    public static void switchToSlot(int slot) {
        Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot - 1;
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C09PacketHeldItemChange(slot - 1));
    }

    public static void shiftClick(int slot, int windowId) {
        Minecraft.getMinecraft().playerController.windowClick(windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
    }

    public static void shiftClick(int slot) {
        InventoryUtils.shiftClick(slot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }

    public static void drop(int slot, int windowId) {
        Minecraft.getMinecraft().playerController.windowClick(windowId, slot, 1, 4, Minecraft.getMinecraft().thePlayer);
    }

    public static void drop(int slot) {
        InventoryUtils.shiftClick(slot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }

    public static void click(int slot, int windowId) {
        Minecraft.getMinecraft().playerController.windowClick(windowId, slot, 0, 0, Minecraft.getMinecraft().thePlayer);
    }

    public static void click(int slot) {
        InventoryUtils.shiftClick(slot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }

    public static void swap(int slot1, int hotbarSlot, int windowId) {
        if (hotbarSlot == slot1 - 36) {
            return;
        }
        Minecraft.getMinecraft().playerController.windowClick(windowId, slot1, hotbarSlot, 2, Minecraft.getMinecraft().thePlayer);
    }

    public static void swap(int slot1, int hotbarSlot) {
        InventoryUtils.swap(slot1, hotbarSlot, Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId);
    }

    public static float getDamage(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemSword) {
            double damage = 4.0 + (double)((ItemSword)itemStack.getItem()).getDamageVsEntity();
            return (float)(damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25);
        }
        if (itemStack.getItem() instanceof ItemTool) {
            double damage = 1.0 + (double)((ItemTool)itemStack.getItem()).getToolMaterial().getDamageVsEntity();
            return (float)(damage += (double)((float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f));
        }
        return 1.0f;
    }

    public static boolean isBestPickaxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemPickaxe)) {
            return false;
        }
        float value = InventoryUtils.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(InventoryUtils.getToolEffect(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemPickaxe)) continue;
            return false;
        }
        return true;
    }

    public static boolean isBestShovel(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemSpade)) {
            return false;
        }
        float value = InventoryUtils.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(InventoryUtils.getToolEffect(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemSpade)) continue;
            return false;
        }
        return true;
    }

    public static boolean isBestAxe(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemAxe)) {
            return false;
        }
        float value = InventoryUtils.getToolEffect(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(InventoryUtils.getToolEffect(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()) > value) || !(is.getItem() instanceof ItemAxe) || InventoryUtils.isBestWeapon(stack)) continue;
            return false;
        }
        return true;
    }

    public static float getToolEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ItemTool)) {
            return 0.0f;
        }
        String name = item.getUnlocalizedName();
        ItemTool tool = (ItemTool)item;
        float value = 1.0f;
        if (item instanceof ItemPickaxe) {
            value = tool.getStrVsBlock(stack, Blocks.stone);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else if (item instanceof ItemSpade) {
            value = tool.getStrVsBlock(stack, Blocks.dirt);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else if (item instanceof ItemAxe) {
            value = tool.getStrVsBlock(stack, Blocks.log);
            if (name.toLowerCase().contains("gold")) {
                value -= 5.0f;
            }
        } else {
            return 1.0f;
        }
        value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075);
        value = (float)((double)value + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100.0);
        return value;
    }

    public static boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion)stack.getItem();
            if (potion.getEffects(stack) == null) {
                return true;
            }
            for (PotionEffect o : potion.getEffects(stack)) {
                PotionEffect effect = o;
                if (effect.getPotionID() != Potion.poison.getId() && effect.getPotionID() != Potion.harm.getId() && effect.getPotionID() != Potion.moveSlowdown.getId() && effect.getPotionID() != Potion.weakness.getId()) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isBestWeapon(ItemStack stack) {
        float damage = InventoryUtils.getDamage(stack);
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !(InventoryUtils.getDamage(is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack()) > damage) || !(is.getItem() instanceof ItemSword)) continue;
            return false;
        }
        return stack.getItem() instanceof ItemSword;
    }

    public static Slot getBestSwordSlot() {
        Slot bestSword = null;
        for (int i = 9; i < 45; ++i) {
            Slot slot;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i)).getStack().getItem() instanceof ItemSword) || !InventoryUtils.isBestWeapon(slot.getStack())) continue;
            bestSword = slot;
        }
        return bestSword;
    }

    public static Slot getBestPickaxeSlot() {
        Slot bestPickaxe = null;
        for (int i = 9; i < 45; ++i) {
            Slot slot;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i)).getStack().getItem() instanceof ItemPickaxe) || !InventoryUtils.isBestPickaxe(slot.getStack())) continue;
            bestPickaxe = slot;
        }
        return bestPickaxe;
    }

    public static Slot getBestAxeSlot() {
        Slot bestAxe = null;
        for (int i = 9; i < 45; ++i) {
            Slot slot;
            if (!Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((slot = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i)).getStack().getItem() instanceof ItemAxe) || !InventoryUtils.isBestAxe(slot.getStack())) continue;
            bestAxe = slot;
        }
        return bestAxe;
    }

    public static boolean isInventoryFull() {
        ItemStack[] arrayOfItemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
        int i = Minecraft.getMinecraft().thePlayer.inventory.mainInventory.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            ItemStack stack = arrayOfItemStack[b];
            if (stack != null && stack.getItem() != null) continue;
            return false;
        }
        return true;
    }
}

