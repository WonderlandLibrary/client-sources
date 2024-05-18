package net.minecraft.src;

import java.util.*;

public class BlockRedstoneWire extends Block
{
    private boolean wiresProvidePower;
    private Set blocksNeedingUpdate;
    private Icon field_94413_c;
    private Icon field_94410_cO;
    private Icon field_94411_cP;
    private Icon field_94412_cQ;
    
    public BlockRedstoneWire(final int par1) {
        super(par1, Material.circuits);
        this.wiresProvidePower = true;
        this.blocksNeedingUpdate = new HashSet();
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
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
    public int getRenderType() {
        return 5;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return 8388608;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || par1World.getBlockId(par2, par3 - 1, par4) == Block.glowStone.blockID;
    }
    
    private void updateAndPropagateCurrentStrength(final World par1World, final int par2, final int par3, final int par4) {
        this.calculateCurrentChanges(par1World, par2, par3, par4, par2, par3, par4);
        final ArrayList var5 = new ArrayList(this.blocksNeedingUpdate);
        this.blocksNeedingUpdate.clear();
        for (int var6 = 0; var6 < var5.size(); ++var6) {
            final ChunkPosition var7 = var5.get(var6);
            par1World.notifyBlocksOfNeighborChange(var7.x, var7.y, var7.z, this.blockID);
        }
    }
    
    private void calculateCurrentChanges(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final int var8 = par1World.getBlockMetadata(par2, par3, par4);
        final byte var9 = 0;
        int var10 = this.getMaxCurrentStrength(par1World, par5, par6, par7, var9);
        this.wiresProvidePower = false;
        final int var11 = par1World.getStrongestIndirectPower(par2, par3, par4);
        this.wiresProvidePower = true;
        if (var11 > 0 && var11 > var10 - 1) {
            var10 = var11;
        }
        int var12 = 0;
        for (int var13 = 0; var13 < 4; ++var13) {
            int var14 = par2;
            int var15 = par4;
            if (var13 == 0) {
                var14 = par2 - 1;
            }
            if (var13 == 1) {
                ++var14;
            }
            if (var13 == 2) {
                var15 = par4 - 1;
            }
            if (var13 == 3) {
                ++var15;
            }
            if (var14 != par5 || var15 != par7) {
                var12 = this.getMaxCurrentStrength(par1World, var14, par3, var15, var12);
            }
            if (par1World.isBlockNormalCube(var14, par3, var15) && !par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
                if ((var14 != par5 || var15 != par7) && par3 >= par6) {
                    var12 = this.getMaxCurrentStrength(par1World, var14, par3 + 1, var15, var12);
                }
            }
            else if (!par1World.isBlockNormalCube(var14, par3, var15) && (var14 != par5 || var15 != par7) && par3 <= par6) {
                var12 = this.getMaxCurrentStrength(par1World, var14, par3 - 1, var15, var12);
            }
        }
        if (var12 > var10) {
            var10 = var12 - 1;
        }
        else if (var10 > 0) {
            --var10;
        }
        else {
            var10 = 0;
        }
        if (var11 > var10 - 1) {
            var10 = var11;
        }
        if (var8 != var10) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var10, 2);
            this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4));
            this.blocksNeedingUpdate.add(new ChunkPosition(par2 - 1, par3, par4));
            this.blocksNeedingUpdate.add(new ChunkPosition(par2 + 1, par3, par4));
            this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3 - 1, par4));
            this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3 + 1, par4));
            this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 - 1));
            this.blocksNeedingUpdate.add(new ChunkPosition(par2, par3, par4 + 1));
        }
    }
    
    private void notifyWireNeighborsOfNeighborChange(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        if (!par1World.isRemote) {
            this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);
            if (par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
            }
            if (par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
            }
            if (par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
            }
            if (par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
            }
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        if (!par1World.isRemote) {
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3, par4);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 - 1);
            this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3, par4 + 1);
            if (par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 + 1, par4);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 - 1, par3 - 1, par4);
            }
            if (par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 + 1, par4);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2 + 1, par3 - 1, par4);
            }
            if (par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 - 1);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 - 1);
            }
            if (par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 + 1, par4 + 1);
            }
            else {
                this.notifyWireNeighborsOfNeighborChange(par1World, par2, par3 - 1, par4 + 1);
            }
        }
    }
    
    private int getMaxCurrentStrength(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (par1World.getBlockId(par2, par3, par4) != this.blockID) {
            return par5;
        }
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        return (var6 > par5) ? var6 : par5;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            final boolean var6 = this.canPlaceBlockAt(par1World, par2, par3, par4);
            if (var6) {
                this.updateAndPropagateCurrentStrength(par1World, par2, par3, par4);
            }
            else {
                this.dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
            super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.redstone.itemID;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return this.wiresProvidePower ? this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5) : 0;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (!this.wiresProvidePower) {
            return 0;
        }
        final int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if (var6 == 0) {
            return 0;
        }
        if (par5 == 1) {
            return var6;
        }
        boolean var7 = isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3, par4, 1) || (!par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 - 1, par4, -1));
        boolean var8 = isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3, par4, 3) || (!par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 - 1, par4, -1));
        boolean var9 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 - 1, 2) || (!par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 - 1, -1));
        boolean var10 = isPoweredOrRepeater(par1IBlockAccess, par2, par3, par4 + 1, 0) || (!par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 - 1, par4 + 1, -1));
        if (!par1IBlockAccess.isBlockNormalCube(par2, par3 + 1, par4)) {
            if (par1IBlockAccess.isBlockNormalCube(par2 - 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 - 1, par3 + 1, par4, -1)) {
                var7 = true;
            }
            if (par1IBlockAccess.isBlockNormalCube(par2 + 1, par3, par4) && isPoweredOrRepeater(par1IBlockAccess, par2 + 1, par3 + 1, par4, -1)) {
                var8 = true;
            }
            if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 - 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 - 1, -1)) {
                var9 = true;
            }
            if (par1IBlockAccess.isBlockNormalCube(par2, par3, par4 + 1) && isPoweredOrRepeater(par1IBlockAccess, par2, par3 + 1, par4 + 1, -1)) {
                var10 = true;
            }
        }
        return (!var9 && !var8 && !var7 && !var10 && par5 >= 2 && par5 <= 5) ? var6 : ((par5 == 2 && var9 && !var7 && !var8) ? var6 : ((par5 == 3 && var10 && !var7 && !var8) ? var6 : ((par5 == 4 && var7 && !var9 && !var10) ? var6 : ((par5 == 5 && var8 && !var9 && !var10) ? var6 : 0))));
    }
    
    @Override
    public boolean canProvidePower() {
        return this.wiresProvidePower;
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        if (var6 > 0) {
            final double var7 = par2 + 0.5 + (par5Random.nextFloat() - 0.5) * 0.2;
            final double var8 = par3 + 0.0625f;
            final double var9 = par4 + 0.5 + (par5Random.nextFloat() - 0.5) * 0.2;
            final float var10 = var6 / 15.0f;
            float var11 = var10 * 0.6f + 0.4f;
            if (var6 == 0) {
                var11 = 0.0f;
            }
            float var12 = var10 * var10 * 0.7f - 0.5f;
            float var13 = var10 * var10 * 0.6f - 0.7f;
            if (var12 < 0.0f) {
                var12 = 0.0f;
            }
            if (var13 < 0.0f) {
                var13 = 0.0f;
            }
            par1World.spawnParticle("reddust", var7, var8, var9, var11, var12, var13);
        }
    }
    
    public static boolean isPowerProviderOrWire(final IBlockAccess par0IBlockAccess, final int par1, final int par2, final int par3, final int par4) {
        final int var5 = par0IBlockAccess.getBlockId(par1, par2, par3);
        if (var5 == Block.redstoneWire.blockID) {
            return true;
        }
        if (var5 == 0) {
            return false;
        }
        if (!Block.redstoneRepeaterIdle.func_94487_f(var5)) {
            return Block.blocksList[var5].canProvidePower() && par4 != -1;
        }
        final int var6 = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
        return par4 == (var6 & 0x3) || par4 == Direction.rotateOpposite[var6 & 0x3];
    }
    
    public static boolean isPoweredOrRepeater(final IBlockAccess par0IBlockAccess, final int par1, final int par2, final int par3, final int par4) {
        if (isPowerProviderOrWire(par0IBlockAccess, par1, par2, par3, par4)) {
            return true;
        }
        final int var5 = par0IBlockAccess.getBlockId(par1, par2, par3);
        if (var5 == Block.redstoneRepeaterActive.blockID) {
            final int var6 = par0IBlockAccess.getBlockMetadata(par1, par2, par3);
            return par4 == (var6 & 0x3);
        }
        return false;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.redstone.itemID;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.field_94413_c = par1IconRegister.registerIcon("redstoneDust_cross");
        this.field_94410_cO = par1IconRegister.registerIcon("redstoneDust_line");
        this.field_94411_cP = par1IconRegister.registerIcon("redstoneDust_cross_overlay");
        this.field_94412_cQ = par1IconRegister.registerIcon("redstoneDust_line_overlay");
        this.blockIcon = this.field_94413_c;
    }
    
    public static Icon func_94409_b(final String par0Str) {
        return (par0Str == "redstoneDust_cross") ? Block.redstoneWire.field_94413_c : ((par0Str == "redstoneDust_line") ? Block.redstoneWire.field_94410_cO : ((par0Str == "redstoneDust_cross_overlay") ? Block.redstoneWire.field_94411_cP : ((par0Str == "redstoneDust_line_overlay") ? Block.redstoneWire.field_94412_cQ : null)));
    }
}
