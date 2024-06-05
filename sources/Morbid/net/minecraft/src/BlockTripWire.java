package net.minecraft.src;

import java.util.*;

public class BlockTripWire extends Block
{
    public BlockTripWire(final int par1) {
        super(par1, Material.circuits);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.setTickRandomly(true);
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 10;
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
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public int getRenderType() {
        return 30;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.silk.itemID;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.silk.itemID;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        final boolean var7 = (var6 & 0x2) == 0x2;
        final boolean var8 = !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
        if (var7 != var8) {
            this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final boolean var6 = (var5 & 0x4) == 0x4;
        final boolean var7 = (var5 & 0x2) == 0x2;
        if (!var7) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
        }
        else if (!var6) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) ? 0 : 2;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 3);
        this.func_72149_e(par1World, par2, par3, par4, var5);
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.func_72149_e(par1World, par2, par3, par4, par6 | 0x1);
    }
    
    @Override
    public void onBlockHarvested(final World par1World, final int par2, final int par3, final int par4, final int par5, final EntityPlayer par6EntityPlayer) {
        if (!par1World.isRemote && par6EntityPlayer.getCurrentEquippedItem() != null && par6EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 0x8, 4);
        }
    }
    
    private void func_72149_e(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < 2; ++var6) {
            int var7 = 1;
            while (var7 < 42) {
                final int var8 = par2 + Direction.offsetX[var6] * var7;
                final int var9 = par4 + Direction.offsetZ[var6] * var7;
                final int var10 = par1World.getBlockId(var8, par3, var9);
                if (var10 == Block.tripWireSource.blockID) {
                    final int var11 = par1World.getBlockMetadata(var8, par3, var9) & 0x3;
                    if (var11 == Direction.rotateOpposite[var6]) {
                        Block.tripWireSource.func_72143_a(par1World, var8, par3, var9, var10, par1World.getBlockMetadata(var8, par3, var9), true, var7, par5);
                        break;
                    }
                    break;
                }
                else {
                    if (var10 != Block.tripWire.blockID) {
                        break;
                    }
                    ++var7;
                }
            }
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (!par1World.isRemote && (par1World.getBlockMetadata(par2, par3, par4) & 0x1) != 0x1) {
            this.updateTripWireState(par1World, par2, par3, par4);
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote && (par1World.getBlockMetadata(par2, par3, par4) & 0x1) == 0x1) {
            this.updateTripWireState(par1World, par2, par3, par4);
        }
    }
    
    private void updateTripWireState(final World par1World, final int par2, final int par3, final int par4) {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final boolean var6 = (var5 & 0x1) == 0x1;
        boolean var7 = false;
        final List var8 = par1World.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ));
        if (!var8.isEmpty()) {
            for (final Entity var10 : var8) {
                if (!var10.doesEntityNotTriggerPressurePlate()) {
                    var7 = true;
                    break;
                }
            }
        }
        if (var7 && !var6) {
            var5 |= 0x1;
        }
        if (!var7 && var6) {
            var5 &= 0xFFFFFFFE;
        }
        if (var7 != var6) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 3);
            this.func_72149_e(par1World, par2, par3, par4, var5);
        }
        if (var7) {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }
    
    public static boolean func_72148_a(final IBlockAccess par0IBlockAccess, final int par1, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1 + Direction.offsetX[par5];
        final int var7 = par3 + Direction.offsetZ[par5];
        final int var8 = par0IBlockAccess.getBlockId(var6, par2, var7);
        final boolean var9 = (par4 & 0x2) == 0x2;
        if (var8 == Block.tripWireSource.blockID) {
            final int var10 = par0IBlockAccess.getBlockMetadata(var6, par2, var7);
            final int var11 = var10 & 0x3;
            return var11 == Direction.rotateOpposite[par5];
        }
        if (var8 == Block.tripWire.blockID) {
            final int var10 = par0IBlockAccess.getBlockMetadata(var6, par2, var7);
            final boolean var12 = (var10 & 0x2) == 0x2;
            return var9 == var12;
        }
        return false;
    }
}
