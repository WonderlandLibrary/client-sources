package net.minecraft.src;

import java.util.*;

public abstract class BlockBasePressurePlate extends Block
{
    private String pressurePlateIconName;
    
    protected BlockBasePressurePlate(final int par1, final String par2Str, final Material par3Material) {
        super(par1, par3Material);
        this.pressurePlateIconName = par2Str;
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
        this.func_94353_c_(this.getMetaFromWeight(15));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.func_94353_c_(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    protected void func_94353_c_(final int par1) {
        final boolean var2 = this.getPowerSupply(par1) > 0;
        final float var3 = 0.0625f;
        if (var2) {
            this.setBlockBounds(var3, 0.0f, var3, 1.0f - var3, 0.03125f, 1.0f - var3);
        }
        else {
            this.setBlockBounds(var3, 0.0f, var3, 1.0f - var3, 0.0625f, 1.0f - var3);
        }
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 20;
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
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || BlockFence.isIdAFence(par1World.getBlockId(par2, par3 - 1, par4));
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        boolean var6 = false;
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !BlockFence.isIdAFence(par1World.getBlockId(par2, par3 - 1, par4))) {
            var6 = true;
        }
        if (var6) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            final int var6 = this.getPowerSupply(par1World.getBlockMetadata(par2, par3, par4));
            if (var6 > 0) {
                this.setStateIfMobInteractsWithPlate(par1World, par2, par3, par4, var6);
            }
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (!par1World.isRemote) {
            final int var6 = this.getPowerSupply(par1World.getBlockMetadata(par2, par3, par4));
            if (var6 == 0) {
                this.setStateIfMobInteractsWithPlate(par1World, par2, par3, par4, var6);
            }
        }
    }
    
    protected void setStateIfMobInteractsWithPlate(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = this.getPlateState(par1World, par2, par3, par4);
        final boolean var7 = par5 > 0;
        final boolean var8 = var6 > 0;
        if (par5 != var6) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, this.getMetaFromWeight(var6), 2);
            this.func_94354_b_(par1World, par2, par3, par4);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
        }
        if (!var8 && var7) {
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.1, par4 + 0.5, "random.click", 0.3f, 0.5f);
        }
        else if (var8 && !var7) {
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.1, par4 + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (var8) {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }
    
    protected AxisAlignedBB getSensitiveAABB(final int par1, final int par2, final int par3) {
        final float var4 = 0.125f;
        return AxisAlignedBB.getAABBPool().getAABB(par1 + var4, par2, par3 + var4, par1 + 1 - var4, par2 + 0.25, par3 + 1 - var4);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (this.getPowerSupply(par6) > 0) {
            this.func_94354_b_(par1World, par2, par3, par4);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    protected void func_94354_b_(final World par1World, final int par2, final int par3, final int par4) {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return this.getPowerSupply(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return (par5 == 1) ? this.getPowerSupply(par1IBlockAccess.getBlockMetadata(par2, par3, par4)) : 0;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.5f;
        final float var2 = 0.125f;
        final float var3 = 0.5f;
        this.setBlockBounds(0.5f - var1, 0.5f - var2, 0.5f - var3, 0.5f + var1, 0.5f + var2, 0.5f + var3);
    }
    
    @Override
    public int getMobilityFlag() {
        return 1;
    }
    
    protected abstract int getPlateState(final World p0, final int p1, final int p2, final int p3);
    
    protected abstract int getPowerSupply(final int p0);
    
    protected abstract int getMetaFromWeight(final int p0);
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.pressurePlateIconName);
    }
}
