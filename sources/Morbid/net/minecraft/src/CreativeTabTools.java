package net.minecraft.src;

import java.util.*;

final class CreativeTabTools extends CreativeTabs
{
    CreativeTabTools(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.axeIron.itemID;
    }
    
    @Override
    public void displayAllReleventItems(final List par1List) {
        super.displayAllReleventItems(par1List);
        this.func_92116_a(par1List, EnumEnchantmentType.digger);
    }
}
