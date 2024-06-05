package net.minecraft.src;

import java.util.*;

final class CreativeTabMisc extends CreativeTabs
{
    CreativeTabMisc(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.bucketLava.itemID;
    }
    
    @Override
    public void displayAllReleventItems(final List par1List) {
        super.displayAllReleventItems(par1List);
        this.func_92116_a(par1List, EnumEnchantmentType.all);
    }
}
