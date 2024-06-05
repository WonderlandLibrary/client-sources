package net.minecraft.src;

import java.util.*;

public class WorldGenBigMushroom extends WorldGenerator
{
    private int mushroomType;
    
    public WorldGenBigMushroom(final int par1) {
        super(true);
        this.mushroomType = -1;
        this.mushroomType = par1;
    }
    
    public WorldGenBigMushroom() {
        super(false);
        this.mushroomType = -1;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        int var6 = par2Random.nextInt(2);
        if (this.mushroomType >= 0) {
            var6 = this.mushroomType;
        }
        final int var7 = par2Random.nextInt(3) + 4;
        boolean var8 = true;
        if (par4 < 1 || par4 + var7 + 1 >= 256) {
            return false;
        }
        for (int var9 = par4; var9 <= par4 + 1 + var7; ++var9) {
            byte var10 = 3;
            if (var9 <= par4 + 3) {
                var10 = 0;
            }
            for (int var11 = par3 - var10; var11 <= par3 + var10 && var8; ++var11) {
                for (int var12 = par5 - var10; var12 <= par5 + var10 && var8; ++var12) {
                    if (var9 >= 0 && var9 < 256) {
                        final int var13 = par1World.getBlockId(var11, var9, var12);
                        if (var13 != 0 && var13 != Block.leaves.blockID) {
                            var8 = false;
                        }
                    }
                    else {
                        var8 = false;
                    }
                }
            }
        }
        if (!var8) {
            return false;
        }
        int var9 = par1World.getBlockId(par3, par4 - 1, par5);
        if (var9 != Block.dirt.blockID && var9 != Block.grass.blockID && var9 != Block.mycelium.blockID) {
            return false;
        }
        int var14 = par4 + var7;
        if (var6 == 1) {
            var14 = par4 + var7 - 3;
        }
        for (int var11 = var14; var11 <= par4 + var7; ++var11) {
            int var12 = 1;
            if (var11 < par4 + var7) {
                ++var12;
            }
            if (var6 == 0) {
                var12 = 3;
            }
            for (int var13 = par3 - var12; var13 <= par3 + var12; ++var13) {
                for (int var15 = par5 - var12; var15 <= par5 + var12; ++var15) {
                    int var16 = 5;
                    if (var13 == par3 - var12) {
                        --var16;
                    }
                    if (var13 == par3 + var12) {
                        ++var16;
                    }
                    if (var15 == par5 - var12) {
                        var16 -= 3;
                    }
                    if (var15 == par5 + var12) {
                        var16 += 3;
                    }
                    if (var6 == 0 || var11 < par4 + var7) {
                        if (var13 == par3 - var12 || var13 == par3 + var12) {
                            if (var15 == par5 - var12) {
                                continue;
                            }
                            if (var15 == par5 + var12) {
                                continue;
                            }
                        }
                        if (var13 == par3 - (var12 - 1) && var15 == par5 - var12) {
                            var16 = 1;
                        }
                        if (var13 == par3 - var12 && var15 == par5 - (var12 - 1)) {
                            var16 = 1;
                        }
                        if (var13 == par3 + (var12 - 1) && var15 == par5 - var12) {
                            var16 = 3;
                        }
                        if (var13 == par3 + var12 && var15 == par5 - (var12 - 1)) {
                            var16 = 3;
                        }
                        if (var13 == par3 - (var12 - 1) && var15 == par5 + var12) {
                            var16 = 7;
                        }
                        if (var13 == par3 - var12 && var15 == par5 + (var12 - 1)) {
                            var16 = 7;
                        }
                        if (var13 == par3 + (var12 - 1) && var15 == par5 + var12) {
                            var16 = 9;
                        }
                        if (var13 == par3 + var12 && var15 == par5 + (var12 - 1)) {
                            var16 = 9;
                        }
                    }
                    if (var16 == 5 && var11 < par4 + var7) {
                        var16 = 0;
                    }
                    if ((var16 != 0 || par4 >= par4 + var7 - 1) && !Block.opaqueCubeLookup[par1World.getBlockId(var13, var11, var15)]) {
                        this.setBlockAndMetadata(par1World, var13, var11, var15, Block.mushroomCapBrown.blockID + var6, var16);
                    }
                }
            }
        }
        for (int var11 = 0; var11 < var7; ++var11) {
            final int var12 = par1World.getBlockId(par3, par4 + var11, par5);
            if (!Block.opaqueCubeLookup[var12]) {
                this.setBlockAndMetadata(par1World, par3, par4 + var11, par5, Block.mushroomCapBrown.blockID + var6, 10);
            }
        }
        return true;
    }
}
