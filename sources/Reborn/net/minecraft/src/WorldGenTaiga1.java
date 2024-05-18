package net.minecraft.src;

import java.util.*;

public class WorldGenTaiga1 extends WorldGenerator
{
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final int var6 = par2Random.nextInt(5) + 7;
        final int var7 = var6 - par2Random.nextInt(2) - 3;
        final int var8 = var6 - var7;
        final int var9 = 1 + par2Random.nextInt(var8 + 1);
        boolean var10 = true;
        if (par4 < 1 || par4 + var6 + 1 > 128) {
            return false;
        }
        for (int var11 = par4; var11 <= par4 + 1 + var6 && var10; ++var11) {
            final boolean var12 = true;
            int var13;
            if (var11 - par4 < var7) {
                var13 = 0;
            }
            else {
                var13 = var9;
            }
            for (int var14 = par3 - var13; var14 <= par3 + var13 && var10; ++var14) {
                for (int var15 = par5 - var13; var15 <= par5 + var13 && var10; ++var15) {
                    if (var11 >= 0 && var11 < 128) {
                        final int var16 = par1World.getBlockId(var14, var11, var15);
                        if (var16 != 0 && var16 != Block.leaves.blockID) {
                            var10 = false;
                        }
                    }
                    else {
                        var10 = false;
                    }
                }
            }
        }
        if (!var10) {
            return false;
        }
        int var11 = par1World.getBlockId(par3, par4 - 1, par5);
        if ((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && par4 < 128 - var6 - 1) {
            this.setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
            int var13 = 0;
            for (int var14 = par4 + var6; var14 >= par4 + var7; --var14) {
                for (int var15 = par3 - var13; var15 <= par3 + var13; ++var15) {
                    final int var16 = var15 - par3;
                    for (int var17 = par5 - var13; var17 <= par5 + var13; ++var17) {
                        final int var18 = var17 - par5;
                        if ((Math.abs(var16) != var13 || Math.abs(var18) != var13 || var13 <= 0) && !Block.opaqueCubeLookup[par1World.getBlockId(var15, var14, var17)]) {
                            this.setBlockAndMetadata(par1World, var15, var14, var17, Block.leaves.blockID, 1);
                        }
                    }
                }
                if (var13 >= 1 && var14 == par4 + var7 + 1) {
                    --var13;
                }
                else if (var13 < var9) {
                    ++var13;
                }
            }
            for (int var14 = 0; var14 < var6 - 1; ++var14) {
                final int var15 = par1World.getBlockId(par3, par4 + var14, par5);
                if (var15 == 0 || var15 == Block.leaves.blockID) {
                    this.setBlockAndMetadata(par1World, par3, par4 + var14, par5, Block.wood.blockID, 1);
                }
            }
            return true;
        }
        return false;
    }
}
