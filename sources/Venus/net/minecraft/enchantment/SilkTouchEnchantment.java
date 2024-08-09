/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class SilkTouchEnchantment
extends Enchantment {
    protected SilkTouchEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.DIGGER, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 0;
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
    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.FORTUNE;
    }
}

