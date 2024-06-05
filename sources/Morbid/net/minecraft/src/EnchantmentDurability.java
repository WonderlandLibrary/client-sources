package net.minecraft.src;

import java.util.*;

public class EnchantmentDurability extends Enchantment
{
    protected EnchantmentDurability(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.digger);
        this.setName("durability");
    }
    
    @Override
    public int getMinEnchantability(final int par1) {
        return 5 + (par1 - 1) * 8;
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
    public boolean canApply(final ItemStack par1ItemStack) {
        return par1ItemStack.isItemStackDamageable() || super.canApply(par1ItemStack);
    }
    
    public static boolean negateDamage(final ItemStack par0ItemStack, final int par1, final Random par2Random) {
        return (!(par0ItemStack.getItem() instanceof ItemArmor) || par2Random.nextFloat() >= 0.6f) && par2Random.nextInt(par1 + 1) > 0;
    }
}
