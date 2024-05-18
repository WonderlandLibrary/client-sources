package net.minecraft.src;

public class BlockLeavesBase extends Block
{
    protected boolean graphicsLevel;
    
    protected BlockLeavesBase(final int par1, final Material par2Material, final boolean par3) {
        super(par1, par2Material);
        this.graphicsLevel = par3;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return (this.graphicsLevel || var6 != this.blockID) && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
}
