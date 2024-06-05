package net.minecraft.src;

final class CreativeTabSearch extends CreativeTabs
{
    CreativeTabSearch(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.compass.itemID;
    }
}
