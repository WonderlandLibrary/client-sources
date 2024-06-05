package net.minecraft.src;

import java.util.*;

public class WorldGenTrees extends WorldGenerator
{
    private final int minTreeHeight;
    private final boolean vinesGrow;
    private final int metaWood;
    private final int metaLeaves;
    
    public WorldGenTrees(final boolean par1) {
        this(par1, 4, 0, 0, false);
    }
    
    public WorldGenTrees(final boolean par1, final int par2, final int par3, final int par4, final boolean par5) {
        super(par1);
        this.minTreeHeight = par2;
        this.metaWood = par3;
        this.metaLeaves = par4;
        this.vinesGrow = par5;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final int var6 = par2Random.nextInt(3) + this.minTreeHeight;
        boolean var7 = true;
        if (par4 < 1 || par4 + var6 + 1 > 256) {
            return false;
        }
        for (int var8 = par4; var8 <= par4 + 1 + var6; ++var8) {
            byte var9 = 1;
            if (var8 == par4) {
                var9 = 0;
            }
            if (var8 >= par4 + 1 + var6 - 2) {
                var9 = 2;
            }
            for (int var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10) {
                for (int var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11) {
                    if (var8 >= 0 && var8 < 256) {
                        final int var12 = par1World.getBlockId(var10, var8, var11);
                        if (var12 != 0 && var12 != Block.leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != Block.wood.blockID) {
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
            this.setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
            final byte var9 = 3;
            final byte var13 = 0;
            for (int var11 = par4 - var9 + var6; var11 <= par4 + var6; ++var11) {
                final int var12 = var11 - (par4 + var6);
                for (int var14 = var13 + 1 - var12 / 2, var15 = par3 - var14; var15 <= par3 + var14; ++var15) {
                    final int var16 = var15 - par3;
                    for (int var17 = par5 - var14; var17 <= par5 + var14; ++var17) {
                        final int var18 = var17 - par5;
                        if (Math.abs(var16) != var14 || Math.abs(var18) != var14 || (par2Random.nextInt(2) != 0 && var12 != 0)) {
                            final int var19 = par1World.getBlockId(var15, var11, var17);
                            if (var19 == 0 || var19 == Block.leaves.blockID) {
                                this.setBlockAndMetadata(par1World, var15, var11, var17, Block.leaves.blockID, this.metaLeaves);
                            }
                        }
                    }
                }
            }
            for (int var11 = 0; var11 < var6; ++var11) {
                final int var12 = par1World.getBlockId(par3, par4 + var11, par5);
                if (var12 == 0 || var12 == Block.leaves.blockID) {
                    this.setBlockAndMetadata(par1World, par3, par4 + var11, par5, Block.wood.blockID, this.metaWood);
                    if (this.vinesGrow && var11 > 0) {
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var11, par5)) {
                            this.setBlockAndMetadata(par1World, par3 - 1, par4 + var11, par5, Block.vine.blockID, 8);
                        }
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var11, par5)) {
                            this.setBlockAndMetadata(par1World, par3 + 1, par4 + var11, par5, Block.vine.blockID, 2);
                        }
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 - 1)) {
                            this.setBlockAndMetadata(par1World, par3, par4 + var11, par5 - 1, Block.vine.blockID, 1);
                        }
                        if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 + 1)) {
                            this.setBlockAndMetadata(par1World, par3, par4 + var11, par5 + 1, Block.vine.blockID, 4);
                        }
                    }
                }
            }
            if (this.vinesGrow) {
                for (int var11 = par4 - 3 + var6; var11 <= par4 + var6; ++var11) {
                    final int var12 = var11 - (par4 + var6);
                    for (int var14 = 2 - var12 / 2, var15 = par3 - var14; var15 <= par3 + var14; ++var15) {
                        for (int var16 = par5 - var14; var16 <= par5 + var14; ++var16) {
                            if (par1World.getBlockId(var15, var11, var16) == Block.leaves.blockID) {
                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var15 - 1, var11, var16) == 0) {
                                    this.growVines(par1World, var15 - 1, var11, var16, 8);
                                }
                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var15 + 1, var11, var16) == 0) {
                                    this.growVines(par1World, var15 + 1, var11, var16, 2);
                                }
                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var15, var11, var16 - 1) == 0) {
                                    this.growVines(par1World, var15, var11, var16 - 1, 1);
                                }
                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(var15, var11, var16 + 1) == 0) {
                                    this.growVines(par1World, var15, var11, var16 + 1, 4);
                                }
                            }
                        }
                    }
                }
                if (par2Random.nextInt(5) == 0 && var6 > 5) {
                    for (int var11 = 0; var11 < 2; ++var11) {
                        for (int var12 = 0; var12 < 4; ++var12) {
                            if (par2Random.nextInt(4 - var11) == 0) {
                                final int var14 = par2Random.nextInt(3);
                                this.setBlockAndMetadata(par1World, par3 + Direction.offsetX[Direction.rotateOpposite[var12]], par4 + var6 - 5 + var11, par5 + Direction.offsetZ[Direction.rotateOpposite[var12]], Block.cocoaPlant.blockID, var14 << 2 | var12);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void growVines(final World par1World, final int par2, int par3, final int par4, final int par5) {
        this.setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
        int var6 = 4;
        while (true) {
            --par3;
            if (par1World.getBlockId(par2, par3, par4) != 0 || var6 <= 0) {
                break;
            }
            this.setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
            --var6;
        }
    }
}
