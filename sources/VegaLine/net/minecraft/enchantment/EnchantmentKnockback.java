/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentKnockback
extends Enchantment {
    protected EnchantmentKnockback(Enchantment.Rarity rarityIn, EntityEquipmentSlot ... slots) {
        super(rarityIn, EnumEnchantmentType.WEAPON, slots);
        this.setName("knockback");
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + 20 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}

