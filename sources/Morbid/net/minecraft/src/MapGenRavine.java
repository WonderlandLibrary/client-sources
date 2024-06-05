package net.minecraft.src;

import java.util.*;

public class MapGenRavine extends MapGenBase
{
    private float[] field_75046_d;
    
    public MapGenRavine() {
        this.field_75046_d = new float[1024];
    }
    
    protected void generateRavine(final long par1, final int par3, final int par4, final byte[] par5ArrayOfByte, double par6, double par8, double par10, final float par12, float par13, float par14, int par15, int par16, final double par17) {
        final Random var19 = new Random(par1);
        final double var20 = par3 * 16 + 8;
        final double var21 = par4 * 16 + 8;
        float var22 = 0.0f;
        float var23 = 0.0f;
        if (par16 <= 0) {
            final int var24 = this.range * 16 - 16;
            par16 = var24 - var19.nextInt(var24 / 4);
        }
        boolean var25 = false;
        if (par15 == -1) {
            par15 = par16 / 2;
            var25 = true;
        }
        float var26 = 1.0f;
        for (int var27 = 0; var27 < 128; ++var27) {
            if (var27 == 0 || var19.nextInt(3) == 0) {
                var26 = 1.0f + var19.nextFloat() * var19.nextFloat() * 1.0f;
            }
            this.field_75046_d[var27] = var26 * var26;
        }
        while (par15 < par16) {
            double var28 = 1.5 + MathHelper.sin(par15 * 3.1415927f / par16) * par12 * 1.0f;
            double var29 = var28 * par17;
            var28 *= var19.nextFloat() * 0.25 + 0.75;
            var29 *= var19.nextFloat() * 0.25 + 0.75;
            final float var30 = MathHelper.cos(par14);
            final float var31 = MathHelper.sin(par14);
            par6 += MathHelper.cos(par13) * var30;
            par8 += var31;
            par10 += MathHelper.sin(par13) * var30;
            par14 *= 0.7f;
            par14 += var23 * 0.05f;
            par13 += var22 * 0.05f;
            var23 *= 0.8f;
            var22 *= 0.5f;
            var23 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0f;
            var22 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0f;
            if (var25 || var19.nextInt(4) != 0) {
                final double var32 = par6 - var20;
                final double var33 = par10 - var21;
                final double var34 = par16 - par15;
                final double var35 = par12 + 2.0f + 16.0f;
                if (var32 * var32 + var33 * var33 - var34 * var34 > var35 * var35) {
                    return;
                }
                if (par6 >= var20 - 16.0 - var28 * 2.0 && par10 >= var21 - 16.0 - var28 * 2.0 && par6 <= var20 + 16.0 + var28 * 2.0 && par10 <= var21 + 16.0 + var28 * 2.0) {
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
                                        if ((var47 * var47 + var48 * var48) * this.field_75046_d[var51] + var52 * var52 / 6.0 < 1.0) {
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
        if (this.rand.nextInt(50) == 0) {
            final double var7 = par2 * 16 + this.rand.nextInt(16);
            final double var8 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
            final double var9 = par3 * 16 + this.rand.nextInt(16);
            final byte var10 = 1;
            for (int var11 = 0; var11 < var10; ++var11) {
                final float var12 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var13 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                final float var14 = (this.rand.nextFloat() * 2.0f + this.rand.nextFloat()) * 2.0f;
                this.generateRavine(this.rand.nextLong(), par4, par5, par6ArrayOfByte, var7, var8, var9, var14, var12, var13, 0, 0, 3.0);
            }
        }
    }
}
