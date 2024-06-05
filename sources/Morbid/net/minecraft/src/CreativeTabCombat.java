package net.minecraft.src;

import java.util.*;

final class CreativeTabCombat extends CreativeTabs
{
    CreativeTabCombat(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.swordGold.itemID;
    }
    
    @Override
    public void displayAllReleventItems(final List par1List) {
        super.displayAllReleventItems(par1List);
        this.func_92116_a(par1List, EnumEnchantmentType.armor, EnumEnchantmentType.armor_feet, EnumEnchantmentType.armor_head, EnumEnchantmentType.armor_legs, EnumEnchantmentType.armor_torso, EnumEnchantmentType.bow, EnumEnchantmentType.weapon);
    }
}
