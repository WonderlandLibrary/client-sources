package net.minecraft.src;

public class EnchantmentArrowDamage extends Enchantment
{
    public EnchantmentArrowDamage(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowDamage");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 1 + (par1 - 1) * 10;
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return this.getMinEnchantability(par1) + 15;
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
}
