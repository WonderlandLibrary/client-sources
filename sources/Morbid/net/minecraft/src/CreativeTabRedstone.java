package net.minecraft.src;

final class CreativeTabRedstone extends CreativeTabs
{
    CreativeTabRedstone(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.redstone.itemID;
    }
}
