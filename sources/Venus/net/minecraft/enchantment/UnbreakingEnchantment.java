/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class UnbreakingEnchantment
extends Enchantment {
    protected UnbreakingEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.BREAKABLE, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 5 + (n - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return super.getMinEnchantability(n) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return itemStack.isDamageable() ? true : super.canApply(itemStack);
    }

    public static boolean negateDamage(ItemStack itemStack, int n, Random random2) {
        if (itemStack.getItem() instanceof ArmorItem && random2.nextFloat() < 0.6f) {
            return true;
        }
        return random2.nextInt(n + 1) > 0;
    }
}

