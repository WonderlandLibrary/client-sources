package net.minecraft.src;

import java.util.*;

public class BlockTorch extends Block
{
    protected BlockTorch(final int par1) {
        super(par1, Material.circuits);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
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
        return 2;
    }
    
    private boolean canPlaceTorchOn(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.doesBlockHaveSolidTopSurface(par2, par3, par4)) {
            return true;
        }
        final int var5 = par1World.getBlockId(par2, par3, par4);
        return var5 == Block.fence.blockID || var5 == Block.netherFence.blockID || var5 == Block.glass.blockID || var5 == Block.cobblestoneWall.blockID;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) || par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) || par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) || par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) || this.canPlaceTorchOn(par1World, par2, par3 - 1, par4);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        int var10 = par9;
        if (par5 == 1 && this.canPlaceTorchOn(par1World, par2, par3 - 1, par4)) {
            var10 = 5;
        }
        if (par5 == 2 && par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true)) {
            var10 = 4;
        }
        if (par5 == 3 && par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true)) {
            var10 = 3;
        }
        if (par5 == 4 && par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true)) {
            var10 = 2;
        }
        if (par5 == 5 && par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true)) {
            var10 = 1;
        }
        return var10;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
            this.onBlockAdded(par1World, par2, par3, par4);
        }
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
            if (par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true)) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
            }
            else if (par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true)) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
            }
            else if (par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true)) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
            }
            else if (par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true)) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
            }
            else if (this.canPlaceTorchOn(par1World, par2, par3 - 1, par4)) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
            }
        }
        this.dropTorchIfCantStay(par1World, par2, par3, par4);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.func_94397_d(par1World, par2, par3, par4, par5);
    }
    
    protected boolean func_94397_d(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!this.dropTorchIfCantStay(par1World, par2, par3, par4)) {
            return true;
        }
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        boolean var7 = false;
        if (!par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true) && var6 == 1) {
            var7 = true;
        }
        if (!par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true) && var6 == 2) {
            var7 = true;
        }
        if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true) && var6 == 3) {
            var7 = true;
        }
        if (!par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true) && var6 == 4) {
            var7 = true;
        }
        if (!this.canPlaceTorchOn(par1World, par2, par3 - 1, par4) && var6 == 5) {
            var7 = true;
        }
        if (var7) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
            return true;
        }
        return false;
    }
    
    protected boolean dropTorchIfCantStay(final World par1World, final int par2, final int par3, final int par4) {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
            if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
                this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                par1World.setBlockToAir(par2, par3, par4);
            }
            return false;
        }
        return true;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World par1World, final int par2, final int par3, final int par4, final Vec3 par5Vec3, final Vec3 par6Vec3) {
        final int var7 = par1World.getBlockMetadata(par2, par3, par4) & 0x7;
        float var8 = 0.15f;
        if (var7 == 1) {
            this.setBlockBounds(0.0f, 0.2f, 0.5f - var8, var8 * 2.0f, 0.8f, 0.5f + var8);
        }
        else if (var7 == 2) {
            this.setBlockBounds(1.0f - var8 * 2.0f, 0.2f, 0.5f - var8, 1.0f, 0.8f, 0.5f + var8);
        }
        else if (var7 == 3) {
            this.setBlockBounds(0.5f - var8, 0.2f, 0.0f, 0.5f + var8, 0.8f, var8 * 2.0f);
        }
        else if (var7 == 4) {
            this.setBlockBounds(0.5f - var8, 0.2f, 1.0f - var8 * 2.0f, 0.5f + var8, 0.8f, 1.0f);
        }
        else {
            var8 = 0.1f;
            this.setBlockBounds(0.5f - var8, 0.0f, 0.5f - var8, 0.5f + var8, 0.6f, 0.5f + var8);
        }
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        final double var7 = par2 + 0.5f;
        final double var8 = par3 + 0.7f;
        final double var9 = par4 + 0.5f;
        final double var10 = 0.2199999988079071;
        final double var11 = 0.27000001072883606;
        if (var6 == 1) {
            par1World.spawnParticle("smoke", var7 - var11, var8 + var10, var9, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7 - var11, var8 + var10, var9, 0.0, 0.0, 0.0);
        }
        else if (var6 == 2) {
            par1World.spawnParticle("smoke", var7 + var11, var8 + var10, var9, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7 + var11, var8 + var10, var9, 0.0, 0.0, 0.0);
        }
        else if (var6 == 3) {
            par1World.spawnParticle("smoke", var7, var8 + var10, var9 - var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var8 + var10, var9 - var11, 0.0, 0.0, 0.0);
        }
        else if (var6 == 4) {
            par1World.spawnParticle("smoke", var7, var8 + var10, var9 + var11, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var8 + var10, var9 + var11, 0.0, 0.0, 0.0);
        }
        else {
            par1World.spawnParticle("smoke", var7, var8, var9, 0.0, 0.0, 0.0);
            par1World.spawnParticle("flame", var7, var8, var9, 0.0, 0.0, 0.0);
        }
    }
}
