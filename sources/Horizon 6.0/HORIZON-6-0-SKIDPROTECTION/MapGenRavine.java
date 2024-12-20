package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class MapGenRavine extends MapGenBase
{
    private float[] Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000390";
    
    public MapGenRavine() {
        this.Ø­áŒŠá = new float[1024];
    }
    
    protected void HorizonCode_Horizon_È(final long p_180707_1_, final int p_180707_3_, final int p_180707_4_, final ChunkPrimer p_180707_5_, double p_180707_6_, double p_180707_8_, double p_180707_10_, final float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, final double p_180707_17_) {
        final Random var19 = new Random(p_180707_1_);
        final double var20 = p_180707_3_ * 16 + 8;
        final double var21 = p_180707_4_ * 16 + 8;
        float var22 = 0.0f;
        float var23 = 0.0f;
        if (p_180707_16_ <= 0) {
            final int var24 = this.HorizonCode_Horizon_È * 16 - 16;
            p_180707_16_ = var24 - var19.nextInt(var24 / 4);
        }
        boolean var25 = false;
        if (p_180707_15_ == -1) {
            p_180707_15_ = p_180707_16_ / 2;
            var25 = true;
        }
        float var26 = 1.0f;
        for (int var27 = 0; var27 < 256; ++var27) {
            if (var27 == 0 || var19.nextInt(3) == 0) {
                var26 = 1.0f + var19.nextFloat() * var19.nextFloat() * 1.0f;
            }
            this.Ø­áŒŠá[var27] = var26 * var26;
        }
        while (p_180707_15_ < p_180707_16_) {
            double var28 = 1.5 + MathHelper.HorizonCode_Horizon_È(p_180707_15_ * 3.1415927f / p_180707_16_) * p_180707_12_ * 1.0f;
            double var29 = var28 * p_180707_17_;
            var28 *= var19.nextFloat() * 0.25 + 0.75;
            var29 *= var19.nextFloat() * 0.25 + 0.75;
            final float var30 = MathHelper.Â(p_180707_14_);
            final float var31 = MathHelper.HorizonCode_Horizon_È(p_180707_14_);
            p_180707_6_ += MathHelper.Â(p_180707_13_) * var30;
            p_180707_8_ += var31;
            p_180707_10_ += MathHelper.HorizonCode_Horizon_È(p_180707_13_) * var30;
            p_180707_14_ *= 0.7f;
            p_180707_14_ += var23 * 0.05f;
            p_180707_13_ += var22 * 0.05f;
            var23 *= 0.8f;
            var22 *= 0.5f;
            var23 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0f;
            var22 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0f;
            if (var25 || var19.nextInt(4) != 0) {
                final double var32 = p_180707_6_ - var20;
                final double var33 = p_180707_10_ - var21;
                final double var34 = p_180707_16_ - p_180707_15_;
                final double var35 = p_180707_12_ + 2.0f + 16.0f;
                if (var32 * var32 + var33 * var33 - var34 * var34 > var35 * var35) {
                    return;
                }
                if (p_180707_6_ >= var20 - 16.0 - var28 * 2.0 && p_180707_10_ >= var21 - 16.0 - var28 * 2.0 && p_180707_6_ <= var20 + 16.0 + var28 * 2.0 && p_180707_10_ <= var21 + 16.0 + var28 * 2.0) {
                    int var36 = MathHelper.Ý(p_180707_6_ - var28) - p_180707_3_ * 16 - 1;
                    int var37 = MathHelper.Ý(p_180707_6_ + var28) - p_180707_3_ * 16 + 1;
                    int var38 = MathHelper.Ý(p_180707_8_ - var29) - 1;
                    int var39 = MathHelper.Ý(p_180707_8_ + var29) + 1;
                    int var40 = MathHelper.Ý(p_180707_10_ - var28) - p_180707_4_ * 16 - 1;
                    int var41 = MathHelper.Ý(p_180707_10_ + var28) - p_180707_4_ * 16 + 1;
                    if (var36 < 0) {
                        var36 = 0;
                    }
                    if (var37 > 16) {
                        var37 = 16;
                    }
                    if (var38 < 1) {
                        var38 = 1;
                    }
                    if (var39 > 248) {
                        var39 = 248;
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
                                if (var45 >= 0 && var45 < 256) {
                                    final IBlockState var46 = p_180707_5_.HorizonCode_Horizon_È(var43, var45, var44);
                                    if (var46.Ý() == Blocks.áˆºÑ¢Õ || var46.Ý() == Blocks.ÂµÈ) {
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
                            final double var47 = (var43 + p_180707_3_ * 16 + 0.5 - p_180707_6_) / var28;
                            for (int var48 = var40; var48 < var41; ++var48) {
                                final double var49 = (var48 + p_180707_4_ * 16 + 0.5 - p_180707_10_) / var28;
                                boolean var50 = false;
                                if (var47 * var47 + var49 * var49 < 1.0) {
                                    for (int var51 = var39; var51 > var38; --var51) {
                                        final double var52 = (var51 - 1 + 0.5 - p_180707_8_) / var29;
                                        if ((var47 * var47 + var49 * var49) * this.Ø­áŒŠá[var51 - 1] + var52 * var52 / 6.0 < 1.0) {
                                            final IBlockState var53 = p_180707_5_.HorizonCode_Horizon_È(var43, var51, var48);
                                            if (var53.Ý() == Blocks.Ø­áŒŠá) {
                                                var50 = true;
                                            }
                                            if (var53.Ý() == Blocks.Ý || var53.Ý() == Blocks.Âµá€ || var53.Ý() == Blocks.Ø­áŒŠá) {
                                                if (var51 - 1 < 10) {
                                                    p_180707_5_.HorizonCode_Horizon_È(var43, var51, var48, Blocks.á.¥à());
                                                }
                                                else {
                                                    p_180707_5_.HorizonCode_Horizon_È(var43, var51, var48, Blocks.Â.¥à());
                                                    if (var50 && p_180707_5_.HorizonCode_Horizon_È(var43, var51 - 1, var48).Ý() == Blocks.Âµá€) {
                                                        p_180707_5_.HorizonCode_Horizon_È(var43, var51 - 1, var48, this.Ý.Ý(new BlockPos(var43 + p_180707_3_ * 16, 0, var48 + p_180707_4_ * 16)).Ï­Ï­Ï);
                                                    }
                                                }
                                            }
                                        }
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
            ++p_180707_15_;
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final World worldIn, final int p_180701_2_, final int p_180701_3_, final int p_180701_4_, final int p_180701_5_, final ChunkPrimer p_180701_6_) {
        if (this.Â.nextInt(50) == 0) {
            final double var7 = p_180701_2_ * 16 + this.Â.nextInt(16);
            final double var8 = this.Â.nextInt(this.Â.nextInt(40) + 8) + 20;
            final double var9 = p_180701_3_ * 16 + this.Â.nextInt(16);
            final byte var10 = 1;
            for (int var11 = 0; var11 < var10; ++var11) {
                final float var12 = this.Â.nextFloat() * 3.1415927f * 2.0f;
                final float var13 = (this.Â.nextFloat() - 0.5f) * 2.0f / 8.0f;
                final float var14 = (this.Â.nextFloat() * 2.0f + this.Â.nextFloat()) * 2.0f;
                this.HorizonCode_Horizon_È(this.Â.nextLong(), p_180701_4_, p_180701_5_, p_180701_6_, var7, var8, var9, var14, var12, var13, 0, 0, 3.0);
            }
        }
    }
}
