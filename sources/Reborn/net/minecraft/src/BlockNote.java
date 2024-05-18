package net.minecraft.src;

public class BlockNote extends BlockContainer
{
    public BlockNote(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
        final TileEntityNote var7 = (TileEntityNote)par1World.getBlockTileEntity(par2, par3, par4);
        if (var7 != null && var7.previousRedstoneState != var6) {
            if (var6) {
                var7.triggerNote(par1World, par2, par3, par4);
            }
            var7.previousRedstoneState = var6;
        }
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityNote var10 = (TileEntityNote)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 != null) {
            var10.changePitch();
            var10.triggerNote(par1World, par2, par3, par4);
        }
        return true;
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        if (!par1World.isRemote) {
            final TileEntityNote var6 = (TileEntityNote)par1World.getBlockTileEntity(par2, par3, par4);
            if (var6 != null) {
                var6.triggerNote(par1World, par2, par3, par4);
            }
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityNote();
    }
    
    @Override
    public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final float var7 = (float)Math.pow(2.0, (par6 - 12) / 12.0);
        String var8 = "harp";
        if (par5 == 1) {
            var8 = "bd";
        }
        if (par5 == 2) {
            var8 = "snare";
        }
        if (par5 == 3) {
            var8 = "hat";
        }
        if (par5 == 4) {
            var8 = "bassattack";
        }
        par1World.playSoundEffect(par2 + 0.5, par3 + 0.5, par4 + 0.5, "note." + var8, 3.0f, var7);
        par1World.spawnParticle("note", par2 + 0.5, par3 + 1.2, par4 + 0.5, par6 / 24.0, 0.0, 0.0);
        return true;
    }
}
