package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentData extends WeightedRandom.Item
{
    public final Enchantment enchantmentobj;
    public final int enchantmentLevel;
    
    public EnchantmentData(final Enchantment p_i1930_1_, final int p_i1930_2_) {
        super(p_i1930_1_.getWeight());
        this.enchantmentobj = p_i1930_1_;
        this.enchantmentLevel = p_i1930_2_;
    }
}
