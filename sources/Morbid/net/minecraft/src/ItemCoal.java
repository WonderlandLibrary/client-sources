package net.minecraft.src;

import java.util.*;

public class ItemCoal extends Item
{
    public ItemCoal(final int par1) {
        super(par1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return (par1ItemStack.getItemDamage() == 1) ? "item.charcoal" : "item.coal";
    }
    
    @Override
    public void getSubItems(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }
}
