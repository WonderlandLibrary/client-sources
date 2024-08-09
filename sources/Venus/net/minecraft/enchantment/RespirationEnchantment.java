/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class RespirationEnchantment
extends Enchantment {
    public RespirationEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.ARMOR_HEAD, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 10 * n;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 30;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}

