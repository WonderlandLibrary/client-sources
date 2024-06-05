package net.minecraft.src;

import java.util.*;

public class WorldGenLakes extends WorldGenerator
{
    private int blockIndex;
    
    public WorldGenLakes(final int par1) {
        this.blockIndex = par1;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, int par3, int par4, int par5) {
        for (par3 -= 8, par5 -= 8; par4 > 5 && par1World.isAirBlock(par3, par4, par5); --par4) {}
        if (par4 <= 4) {
            return false;
        }
        par4 -= 4;
        final boolean[] var6 = new boolean[2048];
        for (int var7 = par2Random.nextInt(4) + 4, var8 = 0; var8 < var7; ++var8) {
            final double var9 = par2Random.nextDouble() * 6.0 + 3.0;
            final double var10 = par2Random.nextDouble() * 4.0 + 2.0;
            final double var11 = par2Random.nextDouble() * 6.0 + 3.0;
            final double var12 = par2Random.nextDouble() * (16.0 - var9 - 2.0) + 1.0 + var9 / 2.0;
            final double var13 = par2Random.nextDouble() * (8.0 - var10 - 4.0) + 2.0 + var10 / 2.0;
            final double var14 = par2Random.nextDouble() * (16.0 - var11 - 2.0) + 1.0 + var11 / 2.0;
            for (int var15 = 1; var15 < 15; ++var15) {
                for (int var16 = 1; var16 < 15; ++var16) {
                    for (int var17 = 1; var17 < 7; ++var17) {
                        final double var18 = (var15 - var12) / (var9 / 2.0);
                        final double var19 = (var17 - var13) / (var10 / 2.0);
                        final double var20 = (var16 - var14) / (var11 / 2.0);
                        final double var21 = var18 * var18 + var19 * var19 + var20 * var20;
                        if (var21 < 1.0) {
                            var6[(var15 * 16 + var16) * 8 + var17] = true;
                        }
                    }
                }
            }
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var22 = 0; var22 < 16; ++var22) {
                for (int var23 = 0; var23 < 8; ++var23) {
                    final boolean var24 = !var6[(var8 * 16 + var22) * 8 + var23] && ((var8 < 15 && var6[((var8 + 1) * 16 + var22) * 8 + var23]) || (var8 > 0 && var6[((var8 - 1) * 16 + var22) * 8 + var23]) || (var22 < 15 && var6[(var8 * 16 + var22 + 1) * 8 + var23]) || (var22 > 0 && var6[(var8 * 16 + (var22 - 1)) * 8 + var23]) || (var23 < 7 && var6[(var8 * 16 + var22) * 8 + var23 + 1]) || (var23 > 0 && var6[(var8 * 16 + var22) * 8 + (var23 - 1)]));
                    if (var24) {
                        final Material var25 = par1World.getBlockMaterial(par3 + var8, par4 + var23, par5 + var22);
                        if (var23 >= 4 && var25.isLiquid()) {
                            return false;
                        }
                        if (var23 < 4 && !var25.isSolid() && par1World.getBlockId(par3 + var8, par4 + var23, par5 + var22) != this.blockIndex) {
                            return false;
                        }
                    }
                }
            }
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var22 = 0; var22 < 16; ++var22) {
                for (int var23 = 0; var23 < 8; ++var23) {
                    if (var6[(var8 * 16 + var22) * 8 + var23]) {
                        par1World.setBlock(par3 + var8, par4 + var23, par5 + var22, (var23 >= 4) ? 0 : this.blockIndex, 0, 2);
                    }
                }
            }
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var22 = 0; var22 < 16; ++var22) {
                for (int var23 = 4; var23 < 8; ++var23) {
                    if (var6[(var8 * 16 + var22) * 8 + var23] && par1World.getBlockId(par3 + var8, par4 + var23 - 1, par5 + var22) == Block.dirt.blockID && par1World.getSavedLightValue(EnumSkyBlock.Sky, par3 + var8, par4 + var23, par5 + var22) > 0) {
                        final BiomeGenBase var26 = par1World.getBiomeGenForCoords(par3 + var8, par5 + var22);
                        if (var26.topBlock == Block.mycelium.blockID) {
                            par1World.setBlock(par3 + var8, par4 + var23 - 1, par5 + var22, Block.mycelium.blockID, 0, 2);
                        }
                        else {
                            par1World.setBlock(par3 + var8, par4 + var23 - 1, par5 + var22, Block.grass.blockID, 0, 2);
                        }
                    }
                }
            }
        }
        if (Block.blocksList[this.blockIndex].blockMaterial == Material.lava) {
            for (int var8 = 0; var8 < 16; ++var8) {
                for (int var22 = 0; var22 < 16; ++var22) {
                    for (int var23 = 0; var23 < 8; ++var23) {
                        final boolean var24 = !var6[(var8 * 16 + var22) * 8 + var23] && ((var8 < 15 && var6[((var8 + 1) * 16 + var22) * 8 + var23]) || (var8 > 0 && var6[((var8 - 1) * 16 + var22) * 8 + var23]) || (var22 < 15 && var6[(var8 * 16 + var22 + 1) * 8 + var23]) || (var22 > 0 && var6[(var8 * 16 + (var22 - 1)) * 8 + var23]) || (var23 < 7 && var6[(var8 * 16 + var22) * 8 + var23 + 1]) || (var23 > 0 && var6[(var8 * 16 + var22) * 8 + (var23 - 1)]));
                        if (var24 && (var23 < 4 || par2Random.nextInt(2) != 0) && par1World.getBlockMaterial(par3 + var8, par4 + var23, par5 + var22).isSolid()) {
                            par1World.setBlock(par3 + var8, par4 + var23, par5 + var22, Block.stone.blockID, 0, 2);
                        }
                    }
                }
            }
        }
        if (Block.blocksList[this.blockIndex].blockMaterial == Material.water) {
            for (int var8 = 0; var8 < 16; ++var8) {
                for (int var22 = 0; var22 < 16; ++var22) {
                    final byte var27 = 4;
                    if (par1World.isBlockFreezable(par3 + var8, par4 + var27, par5 + var22)) {
                        par1World.setBlock(par3 + var8, par4 + var27, par5 + var22, Block.ice.blockID, 0, 2);
                    }
                }
            }
        }
        return true;
    }
}
