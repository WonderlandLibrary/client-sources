package net.minecraft.src;

import java.util.*;

public class MapGenCaves extends MapGenBase
{
    protected void generateLargeCaveNode(final long par1, final int par3, final int par4, final byte[] par5ArrayOfByte, final double par6, final double par8, final double par10) {
        this.generateCaveNode(par1, par3, par4, par5ArrayOfByte, par6, par8, par10, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }
    
    protected void generateCaveNode(final long par1, final int par3, final int par4, final byte[] par5ArrayOfByte, double par6, double par8, double par10, final float par12, float par13, float par14, int par15, int par16, final double par17) {
        final double var19 = par3 * 16 + 8;
        final double var20 = par4 * 16 + 8;
        float var21 = 0.0f;
        float var22 = 0.0f;
        final Random var23 = new Random(par1);
        if (par16 <= 0) {
            final int var24 = this.range * 16 - 16;
            par16 = var24 - var23.nextInt(var24 / 4);
        }
        boolean var25 = false;
        if (par15 == -1) {
            par15 = par16 / 2;
            var25 = true;
        }
        final int var26 = var23.nextInt(par16 / 2) + par16 / 4;
        final boolean var27 = var23.nextInt(6) == 0;
        while (par15 < par16) {
            final double var28 = 1.5 + MathHelper.sin(par15 * 3.1415927f / par16) * par12 * 1.0f;
            final double var29 = var28 * par17;
            final float var30 = MathHelper.cos(par14);
            final float var31 = MathHelper.sin(par14);
            par6 += MathHelper.cos(par13) * var30;
            par8 += var31;
            par10 += MathHelper.sin(par13) * var30;
            if (var27) {
                par14 *= 0.92f;
            }
            else {
                par14 *= 0.7f;
            }
            par14 += var22 * 0.1f;
            par13 += var21 * 0.1f;
            var22 *= 0.9f;
            var21 *= 0.75f;
            var22 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 2.0f;
            var21 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 4.0f;
            if (!var25 && par15 == var26 && par12 > 1.0f && par16 > 0) {
                this.generateCaveNode(var23.nextLong(), par3, par4, par5ArrayOfByte, par6, par8, par10, var23.nextFloat() * 0.5f + 0.5f, par13 - 1.5707964f, par14 / 3.0f, par15, par16, 1.0);
                this.generateCaveNode(var23.nextLong(), par3, par4, par5ArrayOfByte, par6, par8, par10, var23.nextFloat() * 0.5f + 0.5f, par13 + 1.5707964f, par14 / 3.0f, par15, par16, 1.0);
                return;
            }
            if (var25 || var23.nextInt(4) != 0) {
                final double var32 = par6 - var19;
                final double var33 = par10 - var20;
                final double var34 = par16 - par15;
                final double var35 = par12 + 2.0f + 16.0f;
                if (var32 * var32 + var33 * var33 - var34 * var34 > var35 * var35) {
                    return;
                }
                if (par6 >= var19 - 16.0 - var28 * 2.0 && par10 >= var20 - 16.0 - var28 * 2.0 && par6 <= var19 + 16.0 + var28 * 2.0 && par10 <= var20 + 16.0 + var28 * 2.0) {
                    int var36 = MathHelper.floor_double(par6 - var28) - par3 * 16 - 1;
                    int var37 = MathHelper.floor_double(par6 + var28) - par3 * 16 + 1;
                    int var38 = MathHelper.floor_double(par8 - var29) - 1;
                    int var39 = MathHelper.floor_double(par8 + var29) + 1;
                    int var40 = MathHelper.floor_double(par10 - var28) - par4 * 16 - 1;
                    int var41 = MathHelper.floor_double(par10 + var28) - par4 * 16 + 1;
                    if (var36 < 0) {
                        var36 = 0;
                    }
                    if (var37 > 16) {
                        var37 = 16;
                    }
                    if (var38 < 1) {
                        var38 = 1;
                    }
                    if (var39 > 120) {
                        var39 = 120;
                    }
                    if (var40 < 0) {
                        var40 = 0;
                    }
                    if (var41 > 16) {
                        var41 = 16;
                    }
                    boolean var42 = false;
                    for (int var43 = var36; !var42 && var43 < var37; ++var43) {
                        for (int var44 = var40; !var42 && var44 < var41; ++var44) {
                            for (int var45 = var39 + 1; !var42 && var45 >= var38 - 1; --var45) {
                                final int var46 = (var43 * 16 + var44) * 128 + var45;
                                if (var45 >= 0 && var45 < 128) {
                                    if (par5ArrayOfByte[var46] == Block.waterMoving.blockID || par5ArrayOfByte[var46] == Block.waterStill.blockID) {
                                        var42 = true;
                                    }
                                    if (var45 != var38 - 1 && var43 != var36 && var43 != var37 - 1 && var44 != var40 && var44 != var41 - 1) {
                                        var45 = var38;
                                    }
                                }
                            }
                        }
                    }
                    if (!var42) {
                        for (int var43 = var36; var43 < var37; ++var43) {
                            final double var47 = (var43 + par3 * 16 + 0.5 - par6) / var28;
                            for (int var46 = var40; var46 < var41; ++var46) {
                                final double var48 = (var46 + par4 * 16 + 0.5 - par10) / var28;
                                int var49 = (var43 * 16 + var46) * 128 + var39;
                                boolean var50 = false;
                                if (var47 * var47 + var48 * var48 < 1.0) {
                                    for (int var51 = var39 - 1; var51 >= var38; --var51) {
                                        final double var52 = (var51 + 0.5 - par8) / var29;
                                        if (var52 > -0.7 && var47 * var47 + var52 * var52 + var48 * var48 < 1.0) {
                                            final byte var53 = par5ArrayOfByte[var49];
                                            if (var53 == Block.grass.blockID) {
                                                var50 = true;
                                            }
                                            if (var53 == Block.stone.blockID || var53 == Block.dirt.blockID || var53 == Block.grass.blockID) {
                                                if (var51 < 10) {
                                                    par5ArrayOfByte[var49] = (byte)Block.lavaMoving.blockID;
                                                }
                                                else {
                                                    par5ArrayOfByte[var49] = 0;
                                                    if (var50 && par5ArrayOfByte[var49 - 1] == Block.dirt.blockID) {
                                                        par5ArrayOfByte[var49 - 1] = this.worldObj.getBiomeGenForCoords(var43 + par3 * 16, var46 + par4 * 16).topBlock;
                                                    }
                                                }
                                            }
                                        }
                                        --var49;
                                    }
                                }
                            }
                        }
                        if (var25) {
                            break;
                        }
                    }
                }
            }
            ++par15;
        }
    }
    
    @Override
    protected void recursiveGenerate(final World par1World, final int par2, final int par3, final int par4, final int par5, final byte[] par6ArrayOfByte) {
        int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(40) + 1) + 1);
        if (this.rand.nextInt(15) != 0) {
            var7 = 0;
        }
        for (int var8 = 0; var8 < var7; ++var8) {
            final double var9 = par2 * 16 + this.rand.nextInt(16);
            final double var10 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            final double var11 = par3 * 16 + this.rand.nextInt(16);
            int var12 = 1;
            if (this.rand.nextInt(4) == 0) {
                this.generateLargeCaveNode(this.rand.nextLong(), par4, par5, par6ArrayOfByte, var9, var10, var11);
                var12 += this.rand.nextInt(4);
            }
            for (int var13 = 0; var13 < var12; ++var13) {
                final float var14 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var15 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float var16 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                if (this.rand.nextInt(10) == 0) {
                    var16 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0f + 1.0f;
                }
                this.generateCaveNode(this.rand.nextLong(), par4, par5, par6ArrayOfByte, var9, var10, var11, var16, var14, var15, 0, 0, 1.0);
            }
        }
    }
}
