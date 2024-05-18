package net.minecraft.src;

import java.util.*;

public class BlockDetectorRail extends BlockRailBase
{
    private Icon[] iconArray;
    
    public BlockDetectorRail(final int par1) {
        super(par1, true);
        this.setTickRandomly(true);
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 20;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (!par1World.isRemote) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if ((var6 & 0x8) == 0x0) {
                this.setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, var6);
            }
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            if ((var6 & 0x8) != 0x0) {
                this.setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, var6);
            }
        }
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return ((par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) != 0x0) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return ((par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) == 0x0) ? 0 : ((par5 == 1) ? 15 : 0);
    }
    
    private void setStateIfMinecartInteractsWithRail(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final boolean var6 = (par5 & 0x8) != 0x0;
        boolean var7 = false;
        final float var8 = 0.125f;
        final List var9 = par1World.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(par2 + var8, par3, par4 + var8, par2 + 1 - var8, par3 + 1 - var8, par4 + 1 - var8));
        if (!var9.isEmpty()) {
            var7 = true;
        }
        if (var7 && !var6) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 0x8, 3);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
        }
        if (!var7 && var6) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 & 0x7, 3);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
        }
        if (var7) {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
        par1World.func_96440_m(par2, par3, par4, this.blockID);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if ((par1World.getBlockMetadata(par2, par3, par4) & 0x8) > 0) {
            final float var6 = 0.125f;
            final List var7 = par1World.selectEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(par2 + var6, par3, par4 + var6, par2 + 1 - var6, par3 + 1 - var6, par4 + 1 - var6), IEntitySelector.selectInventories);
            if (var7.size() > 0) {
                return Container.calcRedstoneFromInventory(var7.get(0));
            }
        }
        return 0;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        (this.iconArray = new Icon[2])[0] = par1IconRegister.registerIcon("detectorRail");
        this.iconArray[1] = par1IconRegister.registerIcon("detectorRail_on");
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return ((par2 & 0x8) != 0x0) ? this.iconArray[1] : this.iconArray[0];
    }
}
