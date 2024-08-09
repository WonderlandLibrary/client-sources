/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.WeightedRandom;

public class EnchantmentData
extends WeightedRandom.Item {
    public final Enchantment enchantment;
    public final int enchantmentLevel;

    public EnchantmentData(Enchantment enchantment, int n) {
        super(enchantment.getRarity().getWeight());
        this.enchantment = enchantment;
        this.enchantmentLevel = n;
    }
}

