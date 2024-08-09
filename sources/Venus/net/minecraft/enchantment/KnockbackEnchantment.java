/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class KnockbackEnchantment
extends Enchantment {
    protected KnockbackEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.WEAPON, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 5 + 20 * (n - 1);
    }

    @Override
    public int getMaxEnchantability(int n) {
        return super.getMinEnchantability(n) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}

