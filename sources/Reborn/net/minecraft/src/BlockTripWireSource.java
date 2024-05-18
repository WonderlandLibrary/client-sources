package net.minecraft.src;

import java.util.*;

public class BlockTripWireSource extends Block
{
    public BlockTripWireSource(final int par1) {
        super(par1, Material.circuits);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(true);
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
        return 29;
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 10;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return (par5 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) || (par5 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) || (par5 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) || (par5 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4));
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) || par1World.isBlockNormalCube(par2 + 1, par3, par4) || par1World.isBlockNormalCube(par2, par3, par4 - 1) || par1World.isBlockNormalCube(par2, par3, par4 + 1);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        byte var10 = 0;
        if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true)) {
            var10 = 2;
        }
        if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true)) {
            var10 = 0;
        }
        if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true)) {
            var10 = 1;
        }
        if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true)) {
            var10 = 3;
        }
        return var10;
    }
    
    @Override
    public void onPostBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.func_72143_a(par1World, par2, par3, par4, this.blockID, par5, false, -1, 0);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (par5 != this.blockID && this.func_72144_l(par1World, par2, par3, par4)) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final int var7 = var6 & 0x3;
            boolean var8 = false;
            if (!par1World.isBlockNormalCube(par2 - 1, par3, par4) && var7 == 3) {
                var8 = true;
            }
            if (!par1World.isBlockNormalCube(par2 + 1, par3, par4) && var7 == 1) {
                var8 = true;
            }
            if (!par1World.isBlockNormalCube(par2, par3, par4 - 1) && var7 == 0) {
                var8 = true;
            }
            if (!par1World.isBlockNormalCube(par2, par3, par4 + 1) && var7 == 2) {
                var8 = true;
            }
            if (var8) {
                this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
    
    public void func_72143_a(final World par1World, final int par2, final int par3, final int par4, final int par5, int par6, final boolean par7, final int par8, final int par9) {
        final int var10 = par6 & 0x3;
        final boolean var11 = (par6 & 0x4) == 0x4;
        final boolean var12 = (par6 & 0x8) == 0x8;
        boolean var13 = par5 == Block.tripWireSource.blockID;
        boolean var14 = false;
        final boolean var15 = !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
        final int var16 = Direction.offsetX[var10];
        final int var17 = Direction.offsetZ[var10];
        int var18 = 0;
        final int[] var19 = new int[42];
        int var20 = 1;
        while (var20 < 42) {
            final int var21 = par2 + var16 * var20;
            final int var22 = par4 + var17 * var20;
            final int var23 = par1World.getBlockId(var21, par3, var22);
            if (var23 == Block.tripWireSource.blockID) {
                final int var24 = par1World.getBlockMetadata(var21, par3, var22);
                if ((var24 & 0x3) == Direction.rotateOpposite[var10]) {
                    var18 = var20;
                    break;
                }
                break;
            }
            else {
                if (var23 != Block.tripWire.blockID && var20 != par8) {
                    var19[var20] = -1;
                    var13 = false;
                }
                else {
                    final int var24 = (var20 == par8) ? par9 : par1World.getBlockMetadata(var21, par3, var22);
                    final boolean var25 = (var24 & 0x8) != 0x8;
                    final boolean var26 = (var24 & 0x1) == 0x1;
                    final boolean var27 = (var24 & 0x2) == 0x2;
                    var13 &= (var27 == var15);
                    var14 |= (var25 && var26);
                    var19[var20] = var24;
                    if (var20 == par8) {
                        par1World.scheduleBlockUpdate(par2, par3, par4, par5, this.tickRate(par1World));
                        var13 &= var25;
                    }
                }
                ++var20;
            }
        }
        var13 &= (var18 > 1);
        var14 &= var13;
        var20 = ((var13 ? 4 : 0) | (var14 ? 8 : 0));
        par6 = (var10 | var20);
        if (var18 > 0) {
            final int var21 = par2 + var16 * var18;
            final int var22 = par4 + var17 * var18;
            final int var23 = Direction.rotateOpposite[var10];
            par1World.setBlockMetadataWithNotify(var21, par3, var22, var23 | var20, 3);
            this.notifyNeighborOfChange(par1World, var21, par3, var22, var23);
            this.playSoundEffect(par1World, var21, par3, var22, var13, var14, var11, var12);
        }
        this.playSoundEffect(par1World, par2, par3, par4, var13, var14, var11, var12);
        if (par5 > 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par6, 3);
            if (par7) {
                this.notifyNeighborOfChange(par1World, par2, par3, par4, var10);
            }
        }
        if (var11 != var13) {
            for (int var21 = 1; var21 < var18; ++var21) {
                final int var22 = par2 + var16 * var21;
                final int var23 = par4 + var17 * var21;
                int var24 = var19[var21];
                if (var24 >= 0) {
                    if (var13) {
                        var24 |= 0x4;
                    }
                    else {
                        var24 &= 0xFFFFFFFB;
                    }
                    par1World.setBlockMetadataWithNotify(var22, par3, var23, var24, 3);
                }
            }
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        this.func_72143_a(par1World, par2, par3, par4, this.blockID, par1World.getBlockMetadata(par2, par3, par4), true, -1, 0);
    }
    
    private void playSoundEffect(final World par1World, final int par2, final int par3, final int par4, final boolean par5, final boolean par6, final boolean par7, final boolean par8) {
        if (par6 && !par8) {
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.1, par4 + 0.5, "random.click", 0.4f, 0.6f);
        }
        else if (!par6 && par8) {
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.1, par4 + 0.5, "random.click", 0.4f, 0.5f);
        }
        else if (par5 && !par7) {
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.1, par4 + 0.5, "random.click", 0.4f, 0.7f);
        }
        else if (!par5 && par7) {
            par1World.playSoundEffect(par2 + 0.5, par3 + 0.1, par4 + 0.5, "random.bowhit", 0.4f, 1.2f / (par1World.rand.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    private void notifyNeighborOfChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        if (par5 == 3) {
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
        }
        else if (par5 == 1) {
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
        }
        else if (par5 == 0) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
        }
        else if (par5 == 2) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        }
    }
    
    private boolean func_72144_l(final World par1World, final int par2, final int par3, final int par4) {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
            return false;
        }
        return true;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x3;
        final float var6 = 0.1875f;
        if (var5 == 3) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var6, var6 * 2.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 1) {
            this.setBlockBounds(1.0f - var6 * 2.0f, 0.2f, 0.5f - var6, 1.0f, 0.8f, 0.5f + var6);
        }
        else if (var5 == 0) {
            this.setBlockBounds(0.5f - var6, 0.2f, 0.0f, 0.5f + var6, 0.8f, var6 * 2.0f);
        }
        else if (var5 == 2) {
            this.setBlockBounds(0.5f - var6, 0.2f, 1.0f - var6 * 2.0f, 0.5f + var6, 0.8f, 1.0f);
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final boolean var7 = (par6 & 0x4) == 0x4;
        final boolean var8 = (par6 & 0x8) == 0x8;
        if (var7 || var8) {
            this.func_72143_a(par1World, par2, par3, par4, 0, par6, false, -1, 0);
        }
        if (var8) {
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
            final int var9 = par6 & 0x3;
            if (var9 == 3) {
                par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            }
            else if (var9 == 1) {
                par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            }
            else if (var9 == 0) {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            }
            else if (var9 == 2) {
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return ((par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 0x8) == 0x8) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if ((var6 & 0x8) != 0x8) {
            return 0;
        }
        final int var7 = var6 & 0x3;
        return (var7 == 2 && par5 == 2) ? 15 : ((var7 == 0 && par5 == 3) ? 15 : ((var7 == 1 && par5 == 4) ? 15 : ((var7 == 3 && par5 == 5) ? 15 : 0)));
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
}
