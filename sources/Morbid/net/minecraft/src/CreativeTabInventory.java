package net.minecraft.src;

final class CreativeTabInventory extends CreativeTabs
{
    CreativeTabInventory(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Block.chest.blockID;
    }
}
