package net.minecraft.src;

public class BlockJukeBox extends BlockContainer
{
    private Icon theIcon;
    
    protected BlockJukeBox(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.theIcon : this.blockIcon;
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
            return false;
        }
        this.ejectRecord(par1World, par2, par3, par4);
        return true;
    }
    
    public void insertRecord(final World par1World, final int par2, final int par3, final int par4, final ItemStack par5ItemStack) {
        if (!par1World.isRemote) {
            final TileEntityRecordPlayer var6 = (TileEntityRecordPlayer)par1World.getBlockTileEntity(par2, par3, par4);
            if (var6 != null) {
                var6.func_96098_a(par5ItemStack.copy());
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
            }
        }
    }
    
    public void ejectRecord(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.isRemote) {
            final TileEntityRecordPlayer var5 = (TileEntityRecordPlayer)par1World.getBlockTileEntity(par2, par3, par4);
            if (var5 != null) {
                final ItemStack var6 = var5.func_96097_a();
                if (var6 != null) {
                    par1World.playAuxSFX(1005, par2, par3, par4, 0);
                    par1World.playRecord(null, par2, par3, par4);
                    var5.func_96098_a(null);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
                    final float var7 = 0.7f;
                    final double var8 = par1World.rand.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final double var9 = par1World.rand.nextFloat() * var7 + (1.0f - var7) * 0.2 + 0.6;
                    final double var10 = par1World.rand.nextFloat() * var7 + (1.0f - var7) * 0.5;
                    final ItemStack var11 = var6.copy();
                    final EntityItem var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, var11);
                    var12.delayBeforeCanPickup = 10;
                    par1World.spawnEntityInWorld(var12);
                }
            }
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.ejectRecord(par1World, par2, par3, par4);
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (!par1World.isRemote) {
            super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityRecordPlayer();
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("musicBlock");
        this.theIcon = par1IconRegister.registerIcon("jukebox_top");
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final ItemStack var6 = ((TileEntityRecordPlayer)par1World.getBlockTileEntity(par2, par3, par4)).func_96097_a();
        return (var6 == null) ? 0 : (var6.itemID + 1 - Item.record13.itemID);
    }
}
