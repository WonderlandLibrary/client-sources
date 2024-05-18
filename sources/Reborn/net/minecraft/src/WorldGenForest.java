package net.minecraft.src;

import java.util.*;

public class WorldGenForest extends WorldGenerator
{
    public WorldGenForest(final boolean par1) {
        super(par1);
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final int var6 = par2Random.nextInt(3) + 5;
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
                        if (var12 != 0 && var12 != Block.leaves.blockID) {
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
            for (int var13 = par4 - 3 + var6; var13 <= par4 + var6; ++var13) {
                final int var10 = var13 - (par4 + var6);
                for (int var11 = 1 - var10 / 2, var12 = par3 - var11; var12 <= par3 + var11; ++var12) {
                    final int var14 = var12 - par3;
                    for (int var15 = par5 - var11; var15 <= par5 + var11; ++var15) {
                        final int var16 = var15 - par5;
                        if (Math.abs(var14) != var11 || Math.abs(var16) != var11 || (par2Random.nextInt(2) != 0 && var10 != 0)) {
                            final int var17 = par1World.getBlockId(var12, var13, var15);
                            if (var17 == 0 || var17 == Block.leaves.blockID) {
                                this.setBlockAndMetadata(par1World, var12, var13, var15, Block.leaves.blockID, 2);
                            }
                        }
                    }
                }
            }
            for (int var13 = 0; var13 < var6; ++var13) {
                final int var10 = par1World.getBlockId(par3, par4 + var13, par5);
                if (var10 == 0 || var10 == Block.leaves.blockID) {
                    this.setBlockAndMetadata(par1World, par3, par4 + var13, par5, Block.wood.blockID, 2);
                }
            }
            return true;
        }
        return false;
    }
}
