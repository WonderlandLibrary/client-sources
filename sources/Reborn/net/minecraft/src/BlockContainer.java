package net.minecraft.src;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    protected BlockContainer(final int par1, final Material par2Material) {
        super(par1, par2Material);
        this.isBlockContainer = true;
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }
    
    @Override
    public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        final TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
        return var7 != null && var7.receiveClientEvent(par5, par6);
    }
}
