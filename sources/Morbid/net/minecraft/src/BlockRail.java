package net.minecraft.src;

public class BlockRail extends BlockRailBase
{
    private Icon theIcon;
    
    protected BlockRail(final int par1) {
        super(par1, false);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par2 >= 6) ? this.theIcon : this.blockIcon;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("rail_turn");
    }
    
    @Override
    protected void func_94358_a(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par7 > 0 && Block.blocksList[par7].canProvidePower() && new BlockBaseRailLogic(this, par1World, par2, par3, par4).getNumberOfAdjacentTracks() == 3) {
            this.refreshTrackShape(par1World, par2, par3, par4, false);
        }
    }
}
