package net.minecraft.src;

public class BlockBreakable extends Block
{
    private boolean localFlag;
    private String breakableBlockIcon;
    
    protected BlockBreakable(final int par1, final String par2Str, final Material par3Material, final boolean par4) {
        super(par1, par3Material);
        this.localFlag = par4;
        this.breakableBlockIcon = par2Str;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return (this.localFlag || var6 != this.blockID) && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.breakableBlockIcon);
    }
}
