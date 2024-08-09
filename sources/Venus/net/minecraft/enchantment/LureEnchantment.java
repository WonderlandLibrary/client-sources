/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class LureEnchantment
extends Enchantment {
    protected LureEnchantment(Enchantment.Rarity rarity, EnchantmentType enchantmentType, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, enchantmentType, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 15 + (n - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return super.getMinEnchantability(n) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}

