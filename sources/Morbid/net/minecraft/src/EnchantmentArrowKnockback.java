package net.minecraft.src;

public class EnchantmentArrowKnockback extends Enchantment
{
    public EnchantmentArrowKnockback(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowKnockback");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 12 + (par1 - 1) * 20;
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return this.getMinEnchantability(par1) + 25;
    }
    
    @Override
    public int getMaxLevel() {
        return 2;
    }
}
