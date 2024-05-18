package net.minecraft.src;

public class BlockWorkbench extends Block
{
    private Icon workbenchIconTop;
    private Icon workbenchIconFront;
    
    protected BlockWorkbench(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.workbenchIconTop : ((par1 == 0) ? Block.planks.getBlockTextureFromSide(par1) : ((par1 != 2 && par1 != 4) ? this.blockIcon : this.workbenchIconFront));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("workbench_side");
        this.workbenchIconTop = par1IconRegister.registerIcon("workbench_top");
        this.workbenchIconFront = par1IconRegister.registerIcon("workbench_front");
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        par5EntityPlayer.displayGUIWorkbench(par2, par3, par4);
        return true;
    }
}
