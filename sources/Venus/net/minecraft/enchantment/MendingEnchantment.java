/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class MendingEnchantment
extends Enchantment {
    public MendingEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.BREAKABLE, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return n * 25;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 50;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}

