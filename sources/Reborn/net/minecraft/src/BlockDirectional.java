package net.minecraft.src;

public abstract class BlockDirectional extends Block
{
    protected BlockDirectional(final int par1, final Material par2Material) {
        super(par1, par2Material);
    }
    
    public static int getDirection(final int par0) {
        return par0 & 0x3;
    }
}
