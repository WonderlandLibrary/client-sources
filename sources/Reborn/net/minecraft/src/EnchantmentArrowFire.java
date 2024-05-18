package net.minecraft.src;

public class EnchantmentArrowFire extends Enchantment
{
    public EnchantmentArrowFire(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.bow);
        this.setName("arrowFire");
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
