package net.minecraft.src;

final class CreativeTabMaterial extends CreativeTabs
{
    CreativeTabMaterial(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.stick.itemID;
    }
}
