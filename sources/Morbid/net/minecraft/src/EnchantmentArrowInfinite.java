package net.minecraft.src;

public class EnchantmentArrowInfinite extends Enchantment
{
    public EnchantmentArrowInfinite(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowInfinite");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 20;
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
}
