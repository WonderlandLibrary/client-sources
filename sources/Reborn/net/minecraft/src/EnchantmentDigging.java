package net.minecraft.src;

public class EnchantmentDigging extends Enchantment
{
    protected EnchantmentDigging(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.digger);
        this.setName("digging");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 1 + 10 * (par1 - 1);
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return super.getMinEnchantability(par1) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 5;
    }
    
    @Override
    public boolean canApply(final ItemStack par1ItemStack) {
        return par1ItemStack.getItem().itemID == Item.shears.itemID || super.canApply(par1ItemStack);
    }
}
