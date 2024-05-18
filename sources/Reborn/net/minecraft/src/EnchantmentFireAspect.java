package net.minecraft.src;

public class EnchantmentFireAspect extends Enchantment
{
    protected EnchantmentFireAspect(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.weapon);
        this.setName("fire");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 10 + 20 * (par1 - 1);
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return super.getMinEnchantability(par1) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 2;
    }
}
