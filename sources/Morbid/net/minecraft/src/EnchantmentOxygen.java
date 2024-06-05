package net.minecraft.src;

public class EnchantmentOxygen extends Enchantment
{
    public EnchantmentOxygen(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.armor_head);
        this.setName("oxygen");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 10 * par1;
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return this.getMinEnchantability(par1) + 30;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
}
