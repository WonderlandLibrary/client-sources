package net.minecraft.src;

import java.util.*;

public class WorldGenTaiga2 extends WorldGenerator
{
    public WorldGenTaiga2(final boolean par1) {
        super(par1);
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final int var6 = par2Random.nextInt(4) + 6;
        final int var7 = 1 + par2Random.nextInt(2);
        final int var8 = var6 - var7;
        final int var9 = 2 + par2Random.nextInt(2);
        boolean var10 = true;
        if (par4 < 1 || par4 + var6 + 1 > 256) {
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
                    if (var11 >= 0 && var11 < 256) {
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
        if ((var11 == Block.grass.blockID || var11 == Block.dirt.blockID) && par4 < 256 - var6 - 1) {
            this.setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
            int var13 = par2Random.nextInt(2);
            int var14 = 1;
            byte var17 = 0;
            for (int var16 = 0; var16 <= var8; ++var16) {
                final int var18 = par4 + var6 - var16;
                for (int var19 = par3 - var13; var19 <= par3 + var13; ++var19) {
                    final int var20 = var19 - par3;
                    for (int var21 = par5 - var13; var21 <= par5 + var13; ++var21) {
                        final int var22 = var21 - par5;
                        if ((Math.abs(var20) != var13 || Math.abs(var22) != var13 || var13 <= 0) && !Block.opaqueCubeLookup[par1World.getBlockId(var19, var18, var21)]) {
                            this.setBlockAndMetadata(par1World, var19, var18, var21, Block.leaves.blockID, 1);
                        }
                    }
                }
                if (var13 >= var14) {
                    var13 = var17;
                    var17 = 1;
                    if (++var14 > var9) {
                        var14 = var9;
                    }
                }
                else {
                    ++var13;
                }
            }
            int var16;
            for (var16 = par2Random.nextInt(3), int var18 = 0; var18 < var6 - var16; ++var18) {
                final int var19 = par1World.getBlockId(par3, par4 + var18, par5);
                if (var19 == 0 || var19 == Block.leaves.blockID) {
                    this.setBlockAndMetadata(par1World, par3, par4 + var18, par5, Block.wood.blockID, 1);
                }
            }
            return true;
        }
        return false;
    }
}
