package net.minecraft.src;

import java.util.*;

public class BlockEndPortal extends BlockContainer
{
    public static boolean bossDefeated;
    
    static {
        BlockEndPortal.bossDefeated = false;
    }
    
    protected BlockEndPortal(final int par1, final Material par2Material) {
        super(par1, par2Material);
        this.setLightValue(1.0f);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityEndPortal();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final float var5 = 0.0625f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var5, 1.0f);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return par5 == 0 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
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
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && !par1World.isRemote) {
            par5Entity.travelToDimension(1);
        }
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final double var6 = par2 + par5Random.nextFloat();
        final double var7 = par3 + 0.8f;
        final double var8 = par4 + par5Random.nextFloat();
        final double var9 = 0.0;
        final double var10 = 0.0;
        final double var11 = 0.0;
        par1World.spawnParticle("smoke", var6, var7, var8, var9, var10, var11);
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        if (!BlockEndPortal.bossDefeated && par1World.provider.dimensionId != 0) {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return 0;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("portal");
    }
}
