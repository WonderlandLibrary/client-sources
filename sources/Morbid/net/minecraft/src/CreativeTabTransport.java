package net.minecraft.src;

final class CreativeTabTransport extends CreativeTabs
{
    CreativeTabTransport(final int par1, final String par2Str) {
        super(par1, par2Str);
    }
    
    @Override
    public int getTabIconItemIndex() {
        return Block.railPowered.blockID;
    }
}
