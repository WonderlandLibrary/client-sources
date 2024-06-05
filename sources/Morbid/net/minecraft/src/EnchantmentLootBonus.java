package net.minecraft.src;

public class EnchantmentLootBonus extends Enchantment
{
    protected EnchantmentLootBonus(final int par1, final int par2, final EnumEnchantmentType par3EnumEnchantmentType) {
        super(par1, par2, par3EnumEnchantmentType);
        this.setName("lootBonus");
        if (par3EnumEnchantmentType == EnumEnchantmentType.digger) {
            this.setName("lootBonusDigger");
        }
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 15 + (par1 - 1) * 9;
    }
    
    @Override
    public int getMaxEnchantability(final int par1) {
        return super.getMinEnchantability(par1) + 50;
    }
    
    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment par1Enchantment) {
        return super.canApplyTogether(par1Enchantment) && par1Enchantment.effectId != EnchantmentLootBonus.silkTouch.effectId;
    }
}
