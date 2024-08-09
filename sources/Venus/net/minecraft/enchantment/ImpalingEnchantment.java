/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.inventory.EquipmentSlotType;

public class ImpalingEnchantment
extends Enchantment {
    public ImpalingEnchantment(Enchantment.Rarity rarity, EquipmentSlotType ... equipmentSlotTypeArray) {
        super(rarity, EnchantmentType.TRIDENT, equipmentSlotTypeArray);
    }

    @Override
    public int getMinEnchantability(int n) {
        return 1 + (n - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int n) {
        return this.getMinEnchantability(n) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public float calcDamageByCreature(int n, CreatureAttribute creatureAttribute) {
        return creatureAttribute == CreatureAttribute.WATER ? (float)n * 2.5f : 0.0f;
    }
}

