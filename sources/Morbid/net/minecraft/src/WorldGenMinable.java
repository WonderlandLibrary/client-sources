package net.minecraft.src;

import java.util.*;

public class WorldGenMinable extends WorldGenerator
{
    private int minableBlockId;
    private int numberOfBlocks;
    private int field_94523_c;
    
    public WorldGenMinable(final int par1, final int par2) {
        this(par1, par2, Block.stone.blockID);
    }
    
    public WorldGenMinable(final int par1, final int par2, final int par3) {
        this.minableBlockId = par1;
        this.numberOfBlocks = par2;
        this.field_94523_c = par3;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final float var6 = par2Random.nextFloat() * 3.1415927f;
        final double var7 = par3 + 8 + MathHelper.sin(var6) * this.numberOfBlocks / 8.0f;
        final double var8 = par3 + 8 - MathHelper.sin(var6) * this.numberOfBlocks / 8.0f;
        final double var9 = par5 + 8 + MathHelper.cos(var6) * this.numberOfBlocks / 8.0f;
        final double var10 = par5 + 8 - MathHelper.cos(var6) * this.numberOfBlocks / 8.0f;
        final double var11 = par4 + par2Random.nextInt(3) - 2;
        final double var12 = par4 + par2Random.nextInt(3) - 2;
        for (int var13 = 0; var13 <= this.numberOfBlocks; ++var13) {
            final double var14 = var7 + (var8 - var7) * var13 / this.numberOfBlocks;
            final double var15 = var11 + (var12 - var11) * var13 / this.numberOfBlocks;
            final double var16 = var9 + (var10 - var9) * var13 / this.numberOfBlocks;
            final double var17 = par2Random.nextDouble() * this.numberOfBlocks / 16.0;
            final double var18 = (MathHelper.sin(var13 * 3.1415927f / this.numberOfBlocks) + 1.0f) * var17 + 1.0;
            final double var19 = (MathHelper.sin(var13 * 3.1415927f / this.numberOfBlocks) + 1.0f) * var17 + 1.0;
            final int var20 = MathHelper.floor_double(var14 - var18 / 2.0);
            final int var21 = MathHelper.floor_double(var15 - var19 / 2.0);
            final int var22 = MathHelper.floor_double(var16 - var18 / 2.0);
            final int var23 = MathHelper.floor_double(var14 + var18 / 2.0);
            final int var24 = MathHelper.floor_double(var15 + var19 / 2.0);
            final int var25 = MathHelper.floor_double(var16 + var18 / 2.0);
            for (int var26 = var20; var26 <= var23; ++var26) {
                final double var27 = (var26 + 0.5 - var14) / (var18 / 2.0);
                if (var27 * var27 < 1.0) {
                    for (int var28 = var21; var28 <= var24; ++var28) {
                        final double var29 = (var28 + 0.5 - var15) / (var19 / 2.0);
                        if (var27 * var27 + var29 * var29 < 1.0) {
                            for (int var30 = var22; var30 <= var25; ++var30) {
                                final double var31 = (var30 + 0.5 - var16) / (var18 / 2.0);
                                if (var27 * var27 + var29 * var29 + var31 * var31 < 1.0 && par1World.getBlockId(var26, var28, var30) == this.field_94523_c) {
                                    par1World.setBlock(var26, var28, var30, this.minableBlockId, 0, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
