/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class LoyaltyEnchantment
extends Enchantment {
    public LoyaltyEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.TRIDENT, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 5 + n * 7;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment);
    }
}

