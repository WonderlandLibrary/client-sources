package net.minecraft.src;

final class CreativeTabBrewing extends CreativeTabs
{
    CreativeTabBrewing(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.potion.itemID;
    }
}
