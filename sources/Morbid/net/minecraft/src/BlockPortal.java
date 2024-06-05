package net.minecraft.src;

import java.util.*;

public class BlockPortal extends BlockBreakable
{
    public BlockPortal(final int par1) {
        super(par1, "portal", Material.portal, false);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        if (par1World.provider.isSurfaceWorld() && par5Random.nextInt(2000) < par1World.difficultySetting) {
            int var6;
            for (var6 = par3; !par1World.doesBlockHaveSolidTopSurface(par2, var6, par4) && var6 > 0; --var6) {}
            if (var6 > 0 && !par1World.isBlockNormalCube(par2, var6 + 1, par4)) {
                final Entity var7 = ItemMonsterPlacer.spawnCreature(par1World, 57, par2 + 0.5, var6 + 1.1, par4 + 0.5);
                if (var7 != null) {
                    var7.timeUntilPortal = var7.getPortalCooldown();
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (par1IBlockAccess.getBlockId(par2 - 1, par3, par4) != this.blockID && par1IBlockAccess.getBlockId(par2 + 1, par3, par4) != this.blockID) {
            final float var5 = 0.125f;
            final float var6 = 0.5f;
            this.setBlockBounds(0.5f - var5, 0.0f, 0.5f - var6, 0.5f + var5, 1.0f, 0.5f + var6);
        }
        else {
            final float var5 = 0.5f;
            final float var6 = 0.125f;
            this.setBlockBounds(0.5f - var5, 0.0f, 0.5f - var6, 0.5f + var5, 1.0f, 0.5f + var6);
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean tryToCreatePortal(final World par1World, int par2, final int par3, int par4) {
        byte var5 = 0;
        byte var6 = 0;
        if (par1World.getBlockId(par2 - 1, par3, par4) == Block.obsidian.blockID || par1World.getBlockId(par2 + 1, par3, par4) == Block.obsidian.blockID) {
            var5 = 1;
        }
        if (par1World.getBlockId(par2, par3, par4 - 1) == Block.obsidian.blockID || par1World.getBlockId(par2, par3, par4 + 1) == Block.obsidian.blockID) {
            var6 = 1;
        }
        if (var5 == var6) {
            return false;
        }
        if (par1World.getBlockId(par2 - var5, par3, par4 - var6) == 0) {
            par2 -= var5;
            par4 -= var6;
        }
        for (int var7 = -1; var7 <= 2; ++var7) {
            for (int var8 = -1; var8 <= 3; ++var8) {
                final boolean var9 = var7 == -1 || var7 == 2 || var8 == -1 || var8 == 3;
                if ((var7 != -1 && var7 != 2) || (var8 != -1 && var8 != 3)) {
                    final int var10 = par1World.getBlockId(par2 + var5 * var7, par3 + var8, par4 + var6 * var7);
                    if (var9) {
                        if (var10 != Block.obsidian.blockID) {
                            return false;
                        }
                    }
                    else if (var10 != 0 && var10 != Block.fire.blockID) {
                        return false;
                    }
                }
            }
        }
        for (int var7 = 0; var7 < 2; ++var7) {
            for (int var8 = 0; var8 < 3; ++var8) {
                par1World.setBlock(par2 + var5 * var7, par3 + var8, par4 + var6 * var7, Block.portal.blockID, 0, 2);
            }
        }
        return true;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        byte var6 = 0;
        byte var7 = 1;
        if (par1World.getBlockId(par2 - 1, par3, par4) == this.blockID || par1World.getBlockId(par2 + 1, par3, par4) == this.blockID) {
            var6 = 1;
            var7 = 0;
        }
        int var8;
        for (var8 = par3; par1World.getBlockId(par2, var8 - 1, par4) == this.blockID; --var8) {}
        if (par1World.getBlockId(par2, var8 - 1, par4) != Block.obsidian.blockID) {
            par1World.setBlockToAir(par2, par3, par4);
        }
        else {
            int var9;
            for (var9 = 1; var9 < 4 && par1World.getBlockId(par2, var8 + var9, par4) == this.blockID; ++var9) {}
            if (var9 == 3 && par1World.getBlockId(par2, var8 + var9, par4) == Block.obsidian.blockID) {
                final boolean var10 = par1World.getBlockId(par2 - 1, par3, par4) == this.blockID || par1World.getBlockId(par2 + 1, par3, par4) == this.blockID;
                final boolean var11 = par1World.getBlockId(par2, par3, par4 - 1) == this.blockID || par1World.getBlockId(par2, par3, par4 + 1) == this.blockID;
                if (var10 && var11) {
                    par1World.setBlockToAir(par2, par3, par4);
                }
                else if ((par1World.getBlockId(par2 + var6, par3, par4 + var7) != Block.obsidian.blockID || par1World.getBlockId(par2 - var6, par3, par4 - var7) != this.blockID) && (par1World.getBlockId(par2 - var6, par3, par4 - var7) != Block.obsidian.blockID || par1World.getBlockId(par2 + var6, par3, par4 + var7) != this.blockID)) {
                    par1World.setBlockToAir(par2, par3, par4);
                }
            }
            else {
                par1World.setBlockToAir(par2, par3, par4);
            }
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (par1IBlockAccess.getBlockId(par2, par3, par4) == this.blockID) {
            return false;
        }
        final boolean var6 = par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == this.blockID && par1IBlockAccess.getBlockId(par2 - 2, par3, par4) != this.blockID;
        final boolean var7 = par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == this.blockID && par1IBlockAccess.getBlockId(par2 + 2, par3, par4) != this.blockID;
        final boolean var8 = par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == this.blockID && par1IBlockAccess.getBlockId(par2, par3, par4 - 2) != this.blockID;
        final boolean var9 = par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == this.blockID && par1IBlockAccess.getBlockId(par2, par3, par4 + 2) != this.blockID;
        final boolean var10 = var6 || var7;
        final boolean var11 = var8 || var9;
        return (var10 && par5 == 4) || (var10 && par5 == 5) || (var11 && par5 == 2) || (var11 && par5 == 3);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World par1World, final int par2, final int par3, final int par4, final Entity par5Entity) {
        if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null) {
            par5Entity.setInPortal();
        }
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par5Random.nextInt(100) == 0) {
            par1World.playSound(par2 + 0.5, par3 + 0.5, par4 + 0.5, "portal.portal", 0.5f, par5Random.nextFloat() * 0.4f + 0.8f, false);
        }
        for (int var6 = 0; var6 < 4; ++var6) {
            double var7 = par2 + par5Random.nextFloat();
            final double var8 = par3 + par5Random.nextFloat();
            double var9 = par4 + par5Random.nextFloat();
            double var10 = 0.0;
            double var11 = 0.0;
            double var12 = 0.0;
            final int var13 = par5Random.nextInt(2) * 2 - 1;
            var10 = (par5Random.nextFloat() - 0.5) * 0.5;
            var11 = (par5Random.nextFloat() - 0.5) * 0.5;
            var12 = (par5Random.nextFloat() - 0.5) * 0.5;
            if (par1World.getBlockId(par2 - 1, par3, par4) != this.blockID && par1World.getBlockId(par2 + 1, par3, par4) != this.blockID) {
                var7 = par2 + 0.5 + 0.25 * var13;
                var10 = par5Random.nextFloat() * 2.0f * var13;
            }
            else {
                var9 = par4 + 0.5 + 0.25 * var13;
                var12 = par5Random.nextFloat() * 2.0f * var13;
            }
            par1World.spawnParticle("portal", var7, var8, var9, var10, var11, var12);
        }
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return 0;
    }
}
