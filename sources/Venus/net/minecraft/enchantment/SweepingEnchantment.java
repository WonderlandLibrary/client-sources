/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class SweepingEnchantment
extends Enchantment {
    public SweepingEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.WEAPON, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 5 + (n - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    public static float getSweepingDamageRatio(int n) {
        return 1.0f - 1.0f / (float)(n + 1);
    }
}

