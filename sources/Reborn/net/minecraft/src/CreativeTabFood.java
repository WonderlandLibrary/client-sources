package net.minecraft.src;

final class CreativeTabFood extends CreativeTabs
{
    CreativeTabFood(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Item.appleRed.itemID;
    }
}
