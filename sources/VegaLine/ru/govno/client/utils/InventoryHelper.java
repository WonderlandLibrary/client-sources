/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class InventoryHelper {
    static Minecraft mc = Minecraft.getMinecraft();

    public static boolean doesHotbarHaveAxe() {
        for (int i = 0; i < 9; ++i) {
            Minecraft.player.inventory.getStackInSlot(i);
            if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemAxe)) continue;
            return true;
        }
        return false;
    }

    public static boolean doesHotbarHaveBlock() {
        for (int i = 0; i < 9; ++i) {
            if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock)) continue;
            return true;
        }
        return false;
    }

    public static int getAxe() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof ItemAxe)) continue;
            return i;
        }
        return 1;
    }

    public static int findWaterBucket() {
        for (int i = 0; i < 9; ++i) {
            Minecraft.player.inventory.getStackInSlot(i);
            if (Minecraft.player.inventory.getStackInSlot(i).getItem() != Items.WATER_BUCKET) continue;
            return i;
        }
        return -1;
    }

    public static int getSwordAtHotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof ItemSword)) continue;
            return i;
        }
        return -1;
    }

    public static int getTotemAtHotbar() {
        for (int i = 0; i < 45; ++i) {
            ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            if (itemStack.getItem() != Items.TOTEM) continue;
            return i;
        }
        return -1;
    }

    public static boolean isBestArmor(ItemStack stack, int type2) {
        float prot = InventoryHelper.getProtection(stack);
        String armorType = "";
        if (type2 == 1) {
            armorType = "helmet";
        } else if (type2 == 2) {
            armorType = "chestplate";
        } else if (type2 == 3) {
            armorType = "leggings";
        } else if (type2 == 4) {
            armorType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(armorType)) {
            return false;
        }
        for (int i = 5; i < 45; ++i) {
            if (!Minecraft.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack is = Minecraft.player.inventoryContainer.getSlot(i).getStack();
            if (!(InventoryHelper.getProtection(is) > prot) || !is.getUnlocalizedName().contains(armorType)) continue;
            return false;
        }
        return true;
    }

    public static float getProtection(ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor)stack.getItem();
            prot = (float)((double)prot + ((double)armor.damageReduceAmount + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(0)), stack)) * 0.0075));
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(3)), stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(1)), stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(7)), stack) / 100.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(34)), stack) / 50.0);
            prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(4)), stack) / 100.0);
        }
        return prot;
    }
}

