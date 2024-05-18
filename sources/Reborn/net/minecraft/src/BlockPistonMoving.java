package net.minecraft.src;

import java.util.*;

public class BlockPistonMoving extends BlockContainer
{
    public BlockPistonMoving(final int par1) {
        super(par1, Material.piston);
        this.setHardness(-1.0f);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return null;
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final TileEntity var7 = par1World.getBlockTileEntity(par2, par3, par4);
        if (var7 instanceof TileEntityPiston) {
            ((TileEntityPiston)var7).clearPistonTileEntity();
        }
        else {
            super.breakBlock(par1World, par2, par3, par4, par5, par6);
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return -1;
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
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        if (!par1World.isRemote && par1World.getBlockTileEntity(par2, par3, par4) == null) {
            par1World.setBlockToAir(par2, par3, par4);
            return true;
        }
        return false;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (!par1World.isRemote) {
            final TileEntityPiston var8 = this.getTileEntityAtLocation(par1World, par2, par3, par4);
            if (var8 != null) {
                Block.blocksList[var8.getStoredBlockID()].dropBlockAsItem(par1World, par2, par3, par4, var8.getBlockMetadata(), 0);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (par1World.isRemote || par1World.getBlockTileEntity(par2, par3, par4) == null) {}
    }
    
    public static TileEntity getTileEntity(final int par0, final int par1, final int par2, final boolean par3, final boolean par4) {
        return new TileEntityPiston(par0, par1, par2, par3, par4);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final TileEntityPiston var5 = this.getTileEntityAtLocation(par1World, par2, par3, par4);
        if (var5 == null) {
            return null;
        }
        float var6 = var5.getProgress(0.0f);
        if (var5.isExtending()) {
            var6 = 1.0f - var6;
        }
        return this.getAxisAlignedBB(par1World, par2, par3, par4, var5.getStoredBlockID(), var6, var5.getPistonOrientation());
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final TileEntityPiston var5 = this.getTileEntityAtLocation(par1IBlockAccess, par2, par3, par4);
        if (var5 != null) {
            final Block var6 = Block.blocksList[var5.getStoredBlockID()];
            if (var6 == null || var6 == this) {
                return;
            }
            var6.setBlockBoundsBasedOnState(par1IBlockAccess, par2, par3, par4);
            float var7 = var5.getProgress(0.0f);
            if (var5.isExtending()) {
                var7 = 1.0f - var7;
            }
            final int var8 = var5.getPistonOrientation();
            this.minX = var6.getBlockBoundsMinX() - Facing.offsetsXForSide[var8] * var7;
            this.minY = var6.getBlockBoundsMinY() - Facing.offsetsYForSide[var8] * var7;
            this.minZ = var6.getBlockBoundsMinZ() - Facing.offsetsZForSide[var8] * var7;
            this.maxX = var6.getBlockBoundsMaxX() - Facing.offsetsXForSide[var8] * var7;
            this.maxY = var6.getBlockBoundsMaxY() - Facing.offsetsYForSide[var8] * var7;
            this.maxZ = var6.getBlockBoundsMaxZ() - Facing.offsetsZForSide[var8] * var7;
        }
    }
    
    public AxisAlignedBB getAxisAlignedBB(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        if (par5 == 0 || par5 == this.blockID) {
            return null;
        }
        final AxisAlignedBB var8 = Block.blocksList[par5].getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
        if (var8 == null) {
            return null;
        }
        if (Facing.offsetsXForSide[par7] < 0) {
            final AxisAlignedBB axisAlignedBB = var8;
            axisAlignedBB.minX -= Facing.offsetsXForSide[par7] * par6;
        }
        else {
            final AxisAlignedBB axisAlignedBB2 = var8;
            axisAlignedBB2.maxX -= Facing.offsetsXForSide[par7] * par6;
        }
        if (Facing.offsetsYForSide[par7] < 0) {
            final AxisAlignedBB axisAlignedBB3 = var8;
            axisAlignedBB3.minY -= Facing.offsetsYForSide[par7] * par6;
        }
        else {
            final AxisAlignedBB axisAlignedBB4 = var8;
            axisAlignedBB4.maxY -= Facing.offsetsYForSide[par7] * par6;
        }
        if (Facing.offsetsZForSide[par7] < 0) {
            final AxisAlignedBB axisAlignedBB5 = var8;
            axisAlignedBB5.minZ -= Facing.offsetsZForSide[par7] * par6;
        }
        else {
            final AxisAlignedBB axisAlignedBB6 = var8;
            axisAlignedBB6.maxZ -= Facing.offsetsZForSide[par7] * par6;
        }
        return var8;
    }
    
    private TileEntityPiston getTileEntityAtLocation(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final TileEntity var5 = par1IBlockAccess.getBlockTileEntity(par2, par3, par4);
        return (var5 instanceof TileEntityPiston) ? ((TileEntityPiston)var5) : null;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return 0;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("piston_top");
    }
}
