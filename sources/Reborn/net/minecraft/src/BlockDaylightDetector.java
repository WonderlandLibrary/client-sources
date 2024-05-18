package net.minecraft.src;

import java.util.*;

public class BlockDaylightDetector extends BlockContainer
{
    private Icon[] iconArray;
    
    public BlockDaylightDetector(final int par1) {
        super(par1, Material.wood);
        this.iconArray = new Icon[2];
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return par1IBlockAccess.getBlockMetadata(par2, par3, par4);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
    }
    
    public void updateLightLevel(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.provider.hasNoSky) {
            final int var5 = par1World.getBlockMetadata(par2, par3, par4);
            int var6 = par1World.getSavedLightValue(EnumSkyBlock.Sky, par2, par3, par4) - par1World.skylightSubtracted;
            float var7 = par1World.getCelestialAngleRadians(1.0f);
            if (var7 < 3.1415927f) {
                var7 += (0.0f - var7) * 0.2f;
            }
            else {
                var7 += (6.2831855f - var7) * 0.2f;
            }
            var6 = Math.round(var6 * MathHelper.cos(var7));
            if (var6 < 0) {
                var6 = 0;
            }
            if (var6 > 15) {
                var6 = 15;
            }
            if (var5 != var6) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 3);
            }
        }
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.iconArray[0] : this.iconArray[1];
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.iconArray[0] = par1IconRegister.registerIcon("daylightDetector_top");
        this.iconArray[1] = par1IconRegister.registerIcon("daylightDetector_side");
    }
}
