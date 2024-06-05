package net.minecraft.src;

public class BlockBeacon extends BlockContainer
{
    private Icon theIcon;
    
    public BlockBeacon(final int par1) {
        super(par1, Material.glass);
        this.setHardness(3.0f);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityBeacon();
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (par1World.isRemote) {
            return true;
        }
        final TileEntityBeacon var10 = (TileEntityBeacon)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIBeacon(var10);
        }
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 34;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("beacon");
    }
    
    public Icon getBeaconIcon() {
        return this.theIcon;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
        if (par6ItemStack.hasDisplayName()) {
            ((TileEntityBeacon)par1World.getBlockTileEntity(par2, par3, par4)).func_94047_a(par6ItemStack.getDisplayName());
        }
    }
}
