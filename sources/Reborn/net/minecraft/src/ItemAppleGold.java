package net.minecraft.src;

import java.util.*;

public class ItemAppleGold extends ItemFood
{
    public ItemAppleGold(final int par1, final int par2, final float par3, final boolean par4) {
        super(par1, par2, par3, par4);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return par1ItemStack.getItemDamage() > 0;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        return (par1ItemStack.getItemDamage() == 0) ? EnumRarity.rare : EnumRarity.epic;
    }
    
    @Override
    protected void onFoodEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getItemDamage() > 0) {
            if (!par2World.isRemote) {
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 3));
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
                par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
            }
        }
        else {
            super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        }
    }
    
    @Override
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
}
