/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.item.crafting.Ingredient;

public interface IItemTier {
    public int getMaxUses();

    public float getEfficiency();

    public float getAttackDamage();

    public int getHarvestLevel();

    public int getEnchantability();

    public Ingredient getRepairMaterial();
}

