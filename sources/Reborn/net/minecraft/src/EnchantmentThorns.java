package net.minecraft.src;

import java.util.*;

public class EnchantmentThorns extends Enchantment
{
    public EnchantmentThorns(final int par1, final int par2) {
        super(par1, par2, EnumEnchantmentType.armor_torso);
        this.setName("thorns");
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
        return 3;
    }
    
    @Override
    public boolean canApply(final ItemStack par1ItemStack) {
        return par1ItemStack.getItem() instanceof ItemArmor || super.canApply(par1ItemStack);
    }
    
    public static boolean func_92094_a(final int par0, final Random par1Random) {
        return par0 > 0 && par1Random.nextFloat() < 0.15f * par0;
    }
    
    public static int func_92095_b(final int par0, final Random par1Random) {
        return (par0 > 10) ? (par0 - 10) : (1 + par1Random.nextInt(4));
    }
    
    public static void func_92096_a(final Entity par0Entity, final EntityLiving par1EntityLiving, final Random par2Random) {
        final int var3 = EnchantmentHelper.func_92098_i(par1EntityLiving);
        final ItemStack var4 = EnchantmentHelper.func_92099_a(Enchantment.thorns, par1EntityLiving);
        if (func_92094_a(var3, par2Random)) {
            par0Entity.attackEntityFrom(DamageSource.causeThornsDamage(par1EntityLiving), func_92095_b(var3, par2Random));
            par0Entity.playSound("damage.thorns", 0.5f, 1.0f);
            if (var4 != null) {
                var4.damageItem(3, par1EntityLiving);
            }
        }
        else if (var4 != null) {
            var4.damageItem(1, par1EntityLiving);
        }
    }
}
