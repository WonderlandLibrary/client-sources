package net.minecraft.enchantment;

import net.minecraft.util.*;

public class EnchantmentArrowKnockback extends Enchantment
{
    public EnchantmentArrowKnockback(final int p_i45775_1_, final ResourceLocation p_i45775_2_, final int p_i45775_3_) {
        super(p_i45775_1_, p_i45775_2_, p_i45775_3_, EnumEnchantmentType.BOW);
        this.setName("arrowKnockback");
    }
    
    @Override
    public int getMinEnchantability(final int p_77321_1_) {
        return 12 + (p_77321_1_ - 1) * 20;
    }
    
    @Override
    public int getMaxEnchantability(final int p_77317_1_) {
        return this.getMinEnchantability(p_77317_1_) + 25;
    }
    
    @Override
    public int getMaxLevel() {
        return 2;
    }
}
