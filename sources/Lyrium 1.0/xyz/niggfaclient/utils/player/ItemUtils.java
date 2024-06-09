// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.player;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.item.ItemTool;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.client.Minecraft;

public class ItemUtils
{
    public static boolean isBetterArmor(final int slot, final int[] armorType) {
        if (Minecraft.getMinecraft().thePlayer.inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int invIndex = 0;
            int finalCurrentIndex = -1;
            int finalInvIndex = -1;
            final int j = armorType.length;
            for (final int armor : armorType) {
                if (Item.getIdFromItem(Minecraft.getMinecraft().thePlayer.inventory.armorInventory[slot].getItem()) == armor) {
                    finalCurrentIndex = currentIndex;
                }
                else {
                    ++currentIndex;
                }
            }
            for (final int armor : armorType) {
                if (getItem(armor) != -1) {
                    finalInvIndex = invIndex;
                }
                else {
                    ++invIndex;
                }
            }
            if (finalInvIndex > -1) {
                return finalInvIndex < finalCurrentIndex;
            }
        }
        return false;
    }
    
    public static int getItem(final int id) {
        for (int i = 9; i < 45; ++i) {
            final ItemStack item = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id) {
                return i;
            }
        }
        return -1;
    }
    
    public static float getItemDamage(final ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemSword) {
            double damage = 4.0 + ((ItemSword)itemStack.getItem()).getDamageVsEntity();
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25;
            return (float)damage;
        }
        if (itemStack.getItem() instanceof ItemTool) {
            double damage = 1.0 + ((ItemTool)itemStack.getItem()).getToolMaterial().getDamageVsEntity();
            damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f;
            return (float)damage;
        }
        return 1.0f;
    }
    
    public static void updateTool(final BlockPos pos) {
        final Minecraft mc = Minecraft.getMinecraft();
        final Block block = mc.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0f;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
                strength = itemStack.getStrVsBlock(block);
                bestItemIndex = i;
            }
        }
        if (bestItemIndex != -1) {
            mc.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }
    
    public static int getBestSword() {
        final int originalSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (byte slot = 0; slot < 9; ++slot) {
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (itemStack != null && itemStack.getItem() instanceof ItemSword) {
                final float damage = getItemDamage(itemStack);
                if (damage > weaponDamage) {
                    weaponDamage = damage;
                    weaponSlot = slot;
                }
            }
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }
}
