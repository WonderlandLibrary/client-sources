/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class RiptideEnchantment
extends Enchantment {
    public RiptideEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.TRIDENT, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 10 + n * 7;
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
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.LOYALTY && enchantment != Enchantments.CHANNELING;
    }
}

