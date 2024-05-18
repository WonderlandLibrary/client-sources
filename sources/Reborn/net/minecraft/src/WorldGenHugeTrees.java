package net.minecraft.src;

import java.util.*;

public class WorldGenHugeTrees extends WorldGenerator
{
    private final int baseHeight;
    private final int woodMetadata;
    private final int leavesMetadata;
    
    public WorldGenHugeTrees(final boolean par1, final int par2, final int par3, final int par4) {
        super(par1);
        this.baseHeight = par2;
        this.woodMetadata = par3;
        this.leavesMetadata = par4;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final int var6 = par2Random.nextInt(3) + this.baseHeight;
        boolean var7 = true;
        if (par4 < 1 || par4 + var6 + 1 > 256) {
            return false;
        }
        for (int var8 = par4; var8 <= par4 + 1 + var6; ++var8) {
            byte var9 = 2;
            if (var8 == par4) {
                var9 = 1;
            }
            if (var8 >= par4 + 1 + var6 - 2) {
                var9 = 2;
            }
            for (int var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10) {
                for (int var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11) {
                    if (var8 >= 0 && var8 < 256) {
                        final int var12 = par1World.getBlockId(var10, var8, var11);
                        if (var12 != 0 && var12 != Block.leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != Block.wood.blockID && var12 != Block.sapling.blockID) {
                            var7 = false;
                        }
                    }
                    else {
                        var7 = false;
                    }
                }
            }
        }
        if (!var7) {
            return false;
        }
        int var8 = par1World.getBlockId(par3, par4 - 1, par5);
        if ((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && par4 < 256 - var6 - 1) {
            par1World.setBlock(par3, par4 - 1, par5, Block.dirt.blockID, 0, 2);
            par1World.setBlock(par3 + 1, par4 - 1, par5, Block.dirt.blockID, 0, 2);
            par1World.setBlock(par3, par4 - 1, par5 + 1, Block.dirt.blockID, 0, 2);
            par1World.setBlock(par3 + 1, par4 - 1, par5 + 1, Block.dirt.blockID, 0, 2);
            this.growLeaves(par1World, par3, par5, par4 + var6, 2, par2Random);
            for (int var13 = par4 + var6 - 2 - par2Random.nextInt(4); var13 > par4 + var6 / 2; var13 -= 2 + par2Random.nextInt(4)) {
                final float var14 = par2Random.nextFloat() * 3.1415927f * 2.0f;
                int var11 = par3 + (int)(0.5f + MathHelper.cos(var14) * 4.0f);
                int var12 = par5 + (int)(0.5f + MathHelper.sin(var14) * 4.0f);
                this.growLeaves(par1World, var11, var12, var13, 0, par2Random);
                for (int var15 = 0; var15 < 5; ++var15) {
                    var11 = par3 + (int)(1.5f + MathHelper.cos(var14) * var15);
                    var12 = par5 + (int)(1.5f + MathHelper.sin(var14) * var15);
                    this.setBlockAndMetadata(par1World, var11, var13 - 3 + var15 / 2, var12, Block.wood.blockID, this.woodMetadata);
                }
            }
            for (int var10 = 0; var10 < var6; ++var10) {
                int var11 = par1World.getBlockId(par3, par4 + var10, par5);
                if (var11 == 0 || var11 == Block.leaves.blockID) {
                    this.setBlockAndMetadata(par1World, par3, par4 + var10, par5, Block.wood.blockID, this.woodMetadata);
                    if (var10 > 0) {
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var10, par5)) {
                            this.setBlockAndMetadata(par1World, par3 - 1, par4 + var10, par5, Block.vine.blockID, 8);
                        }
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var10, par5 - 1)) {
                            this.setBlockAndMetadata(par1World, par3, par4 + var10, par5 - 1, Block.vine.blockID, 1);
                        }
                    }
                }
                if (var10 < var6 - 1) {
                    var11 = par1World.getBlockId(par3 + 1, par4 + var10, par5);
                    if (var11 == 0 || var11 == Block.leaves.blockID) {
                        this.setBlockAndMetadata(par1World, par3 + 1, par4 + var10, par5, Block.wood.blockID, this.woodMetadata);
                        if (var10 > 0) {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + var10, par5)) {
                                this.setBlockAndMetadata(par1World, par3 + 2, par4 + var10, par5, Block.vine.blockID, 2);
                            }
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var10, par5 - 1)) {
                                this.setBlockAndMetadata(par1World, par3 + 1, par4 + var10, par5 - 1, Block.vine.blockID, 1);
                            }
                        }
                    }
                    var11 = par1World.getBlockId(par3 + 1, par4 + var10, par5 + 1);
                    if (var11 == 0 || var11 == Block.leaves.blockID) {
                        this.setBlockAndMetadata(par1World, par3 + 1, par4 + var10, par5 + 1, Block.wood.blockID, this.woodMetadata);
                        if (var10 > 0) {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + var10, par5 + 1)) {
                                this.setBlockAndMetadata(par1World, par3 + 2, par4 + var10, par5 + 1, Block.vine.blockID, 2);
                            }
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var10, par5 + 2)) {
                                this.setBlockAndMetadata(par1World, par3 + 1, par4 + var10, par5 + 2, Block.vine.blockID, 4);
                            }
                        }
                    }
                    var11 = par1World.getBlockId(par3, par4 + var10, par5 + 1);
                    if (var11 == 0 || var11 == Block.leaves.blockID) {
                        this.setBlockAndMetadata(par1World, par3, par4 + var10, par5 + 1, Block.wood.blockID, this.woodMetadata);
                        if (var10 > 0) {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var10, par5 + 1)) {
                                this.setBlockAndMetadata(par1World, par3 - 1, par4 + var10, par5 + 1, Block.vine.blockID, 8);
                            }
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var10, par5 + 2)) {
                                this.setBlockAndMetadata(par1World, par3, par4 + var10, par5 + 2, Block.vine.blockID, 4);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void growLeaves(final World par1World, final int par2, final int par3, final int par4, final int par5, final Random par6Random) {
        final byte var7 = 2;
        for (int var8 = par4 - var7; var8 <= par4; ++var8) {
            final int var9 = var8 - par4;
            for (int var10 = par5 + 1 - var9, var11 = par2 - var10; var11 <= par2 + var10 + 1; ++var11) {
                final int var12 = var11 - par2;
                for (int var13 = par3 - var10; var13 <= par3 + var10 + 1; ++var13) {
                    final int var14 = var13 - par3;
                    if ((var12 >= 0 || var14 >= 0 || var12 * var12 + var14 * var14 <= var10 * var10) && ((var12 <= 0 && var14 <= 0) || var12 * var12 + var14 * var14 <= (var10 + 1) * (var10 + 1)) && (par6Random.nextInt(4) != 0 || var12 * var12 + var14 * var14 <= (var10 - 1) * (var10 - 1))) {
                        final int var15 = par1World.getBlockId(var11, var8, var13);
                        if (var15 == 0 || var15 == Block.leaves.blockID) {
                            this.setBlockAndMetadata(par1World, var11, var8, var13, Block.leaves.blockID, this.leavesMetadata);
                        }
                    }
                }
            }
        }
    }
}
