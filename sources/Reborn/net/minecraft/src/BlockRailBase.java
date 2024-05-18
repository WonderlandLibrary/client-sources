package net.minecraft.src;

import java.util.*;

public abstract class BlockRailBase extends Block
{
    protected final boolean isPowered;
    
    public static final boolean isRailBlockAt(final World par0World, final int par1, final int par2, final int par3) {
        return isRailBlock(par0World.getBlockId(par1, par2, par3));
    }
    
    public static final boolean isRailBlock(final int par0) {
        return par0 == Block.rail.blockID || par0 == Block.railPowered.blockID || par0 == Block.railDetector.blockID || par0 == Block.railActivator.blockID;
    }
    
    protected BlockRailBase(final int par1, final boolean par2) {
        super(par1, Material.circuits);
        this.isPowered = par2;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    public boolean isPowered() {
        return this.isPowered;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World par1World, final int par2, final int par3, final int par4, final Vec3 par5Vec3, final Vec3 par6Vec3) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if (var5 >= 2 && var5 <= 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.625f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        }
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 9;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        if (!par1World.isRemote) {
            this.refreshTrackShape(par1World, par2, par3, par4, true);
            if (this.isPowered) {
                this.onNeighborBlockChange(par1World, par2, par3, par4, this.blockID);
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            int var7;
            final int var6 = var7 = par1World.getBlockMetadata(par2, par3, par4);
            if (this.isPowered) {
                var7 = (var6 & 0x7);
            }
            boolean var8 = false;
            if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
                var8 = true;
            }
            if (var7 == 2 && !par1World.doesBlockHaveSolidTopSurface(par2 + 1, par3, par4)) {
                var8 = true;
            }
            if (var7 == 3 && !par1World.doesBlockHaveSolidTopSurface(par2 - 1, par3, par4)) {
                var8 = true;
            }
            if (var7 == 4 && !par1World.doesBlockHaveSolidTopSurface(par2, par3, par4 - 1)) {
                var8 = true;
            }
            if (var7 == 5 && !par1World.doesBlockHaveSolidTopSurface(par2, par3, par4 + 1)) {
                var8 = true;
            }
            if (var8) {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
            else {
                this.func_94358_a(par1World, par2, par3, par4, var6, var7, par5);
            }
        }
    }
    
    protected void func_94358_a(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
    }
    
    protected void refreshTrackShape(final World par1World, final int par2, final int par3, final int par4, final boolean par5) {
        if (!par1World.isRemote) {
            new BlockBaseRailLogic(this, par1World, par2, par3, par4).func_94511_a(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4), par5);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 0;
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        int var7 = par6;
        if (this.isPowered) {
            var7 = (par6 & 0x7);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        if (var7 == 2 || var7 == 3 || var7 == 4 || var7 == 5) {
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, par5);
        }
        if (this.isPowered) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par5);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, par5);
        }
    }
}
