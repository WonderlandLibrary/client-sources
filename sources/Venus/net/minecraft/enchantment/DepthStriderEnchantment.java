/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class DepthStriderEnchantment
extends Enchantment {
    public DepthStriderEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.ARMOR_FEET, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return n * 10;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.FROST_WALKER;
    }
}

