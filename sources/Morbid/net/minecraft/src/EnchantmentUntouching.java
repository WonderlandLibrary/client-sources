package net.minecraft.src;

public class EnchantmentUntouching extends Enchantment
{
    protected EnchantmentUntouching(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.digger);
        this.setName("untouching");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 15;
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return super.getMinEnchantability(par1) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 1;
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment par1Enchantment) {
        return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != EnchantmentUntouching.fortune.effectId;
    }
    
    @Override
    public boolean canApply(final ItemStack par1ItemStack) {
        return par1ItemStack.getItem().itemID == Item.shears.itemID || super.canApply(par1ItemStack);
    }
}
