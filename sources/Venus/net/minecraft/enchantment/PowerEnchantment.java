/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class PowerEnchantment
extends Enchantment {
    public PowerEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.BOW, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 1 + (n - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}

