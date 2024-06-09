package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;
import java.util.Random;

public class MapGenCaves extends MapGenBase
{
    private static final String Ø­áŒŠá = "CL_00000393";
    
    protected void HorizonCode_Horizon_È(final long p_180703_1_, final int p_180703_3_, final int p_180703_4_, final ChunkPrimer p_180703_5_, final double p_180703_6_, final double p_180703_8_, final double p_180703_10_) {
        this.HorizonCode_Horizon_È(p_180703_1_, p_180703_3_, p_180703_4_, p_180703_5_, p_180703_6_, p_180703_8_, p_180703_10_, 1.0f + this.Â.nextFloat() * 6.0f, 0.0f, 0.0f, -1, -1, 0.5);
    }
    
    protected void HorizonCode_Horizon_È(final long p_180702_1_, final int p_180702_3_, final int p_180702_4_, final ChunkPrimer p_180702_5_, double p_180702_6_, double p_180702_8_, double p_180702_10_, final float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, final double p_180702_17_) {
        final double var19 = p_180702_3_ * 16 + 8;
        final double var20 = p_180702_4_ * 16 + 8;
        float var21 = 0.0f;
        float var22 = 0.0f;
        final Random var23 = new Random(p_180702_1_);
        if (p_180702_16_ <= 0) {
            final int var24 = this.HorizonCode_Horizon_È * 16 - 16;
            p_180702_16_ = var24 - var23.nextInt(var24 / 4);
        }
        boolean var25 = false;
        if (p_180702_15_ == -1) {
            p_180702_15_ = p_180702_16_ / 2;
            var25 = true;
        }
        final int var26 = var23.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;
        final boolean var27 = var23.nextInt(6) == 0;
        while (p_180702_15_ < p_180702_16_) {
            final double var28 = 1.5 + MathHelper.HorizonCode_Horizon_È(p_180702_15_ * 3.1415927f / p_180702_16_) * p_180702_12_ * 1.0f;
            final double var29 = var28 * p_180702_17_;
            final float var30 = MathHelper.Â(p_180702_14_);
            final float var31 = MathHelper.HorizonCode_Horizon_È(p_180702_14_);
            p_180702_6_ += MathHelper.Â(p_180702_13_) * var30;
            p_180702_8_ += var31;
            p_180702_10_ += MathHelper.HorizonCode_Horizon_È(p_180702_13_) * var30;
            if (var27) {
                p_180702_14_ *= 0.92f;
            }
            else {
                p_180702_14_ *= 0.7f;
            }
            p_180702_14_ += var22 * 0.1f;
            p_180702_13_ += var21 * 0.1f;
            var22 *= 0.9f;
            var21 *= 0.75f;
            var22 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 2.0f;
            var21 += (var23.nextFloat() - var23.nextFloat()) * var23.nextFloat() * 4.0f;
            if (!var25 && p_180702_15_ == var26 && p_180702_12_ > 1.0f && p_180702_16_ > 0) {
                this.HorizonCode_Horizon_È(var23.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, var23.nextFloat() * 0.5f + 0.5f, p_180702_13_ - 1.5707964f, p_180702_14_ / 3.0f, p_180702_15_, p_180702_16_, 1.0);
                this.HorizonCode_Horizon_È(var23.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, var23.nextFloat() * 0.5f + 0.5f, p_180702_13_ + 1.5707964f, p_180702_14_ / 3.0f, p_180702_15_, p_180702_16_, 1.0);
                return;
            }
            if (var25 || var23.nextInt(4) != 0) {
                final double var32 = p_180702_6_ - var19;
                final double var33 = p_180702_10_ - var20;
                final double var34 = p_180702_16_ - p_180702_15_;
                final double var35 = p_180702_12_ + 2.0f + 16.0f;
                if (var32 * var32 + var33 * var33 - var34 * var34 > var35 * var35) {
                    return;
                }
                if (p_180702_6_ >= var19 - 16.0 - var28 * 2.0 && p_180702_10_ >= var20 - 16.0 - var28 * 2.0 && p_180702_6_ <= var19 + 16.0 + var28 * 2.0 && p_180702_10_ <= var20 + 16.0 + var28 * 2.0) {
                    int var36 = MathHelper.Ý(p_180702_6_ - var28) - p_180702_3_ * 16 - 1;
                    int var37 = MathHelper.Ý(p_180702_6_ + var28) - p_180702_3_ * 16 + 1;
                    int var38 = MathHelper.Ý(p_180702_8_ - var29) - 1;
                    int var39 = MathHelper.Ý(p_180702_8_ + var29) + 1;
                    int var40 = MathHelper.Ý(p_180702_10_ - var28) - p_180702_4_ * 16 - 1;
                    int var41 = MathHelper.Ý(p_180702_10_ + var28) - p_180702_4_ * 16 + 1;
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
                                    final IBlockState var46 = p_180702_5_.HorizonCode_Horizon_È(var43, var45, var44);
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
                            final double var47 = (var43 + p_180702_3_ * 16 + 0.5 - p_180702_6_) / var28;
                            for (int var48 = var40; var48 < var41; ++var48) {
                                final double var49 = (var48 + p_180702_4_ * 16 + 0.5 - p_180702_10_) / var28;
                                boolean var50 = false;
                                if (var47 * var47 + var49 * var49 < 1.0) {
                                    for (int var51 = var39; var51 > var38; --var51) {
                                        final double var52 = (var51 - 1 + 0.5 - p_180702_8_) / var29;
                                        if (var52 > -0.7 && var47 * var47 + var52 * var52 + var49 * var49 < 1.0) {
                                            final IBlockState var53 = p_180702_5_.HorizonCode_Horizon_È(var43, var51, var48);
                                            final IBlockState var54 = (IBlockState)Objects.firstNonNull((Object)p_180702_5_.HorizonCode_Horizon_È(var43, var51 + 1, var48), (Object)Blocks.Â.¥à());
                                            if (var53.Ý() == Blocks.Ø­áŒŠá || var53.Ý() == Blocks.Œáƒ) {
                                                var50 = true;
                                            }
                                            if (this.HorizonCode_Horizon_È(var53, var54)) {
                                                if (var51 - 1 < 10) {
                                                    p_180702_5_.HorizonCode_Horizon_È(var43, var51, var48, Blocks.ˆÏ­.¥à());
                                                }
                                                else {
                                                    p_180702_5_.HorizonCode_Horizon_È(var43, var51, var48, Blocks.Â.¥à());
                                                    if (var54.Ý() == Blocks.£á) {
                                                        p_180702_5_.HorizonCode_Horizon_È(var43, var51 + 1, var48, (var54.HorizonCode_Horizon_È(BlockSand.Õ) == BlockSand.HorizonCode_Horizon_È.Â) ? Blocks.áˆºÛ.¥à() : Blocks.ŒÏ.¥à());
                                                    }
                                                    if (var50 && p_180702_5_.HorizonCode_Horizon_È(var43, var51 - 1, var48).Ý() == Blocks.Âµá€) {
                                                        p_180702_5_.HorizonCode_Horizon_È(var43, var51 - 1, var48, this.Ý.Ý(new BlockPos(var43 + p_180702_3_ * 16, 0, var48 + p_180702_4_ * 16)).Ï­Ï­Ï.Ý().¥à());
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
            ++p_180702_15_;
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final IBlockState p_175793_1_, final IBlockState p_175793_2_) {
        return p_175793_1_.Ý() == Blocks.Ý || p_175793_1_.Ý() == Blocks.Âµá€ || p_175793_1_.Ý() == Blocks.Ø­áŒŠá || p_175793_1_.Ý() == Blocks.Ø­ || p_175793_1_.Ý() == Blocks.Ø­Â || p_175793_1_.Ý() == Blocks.ŒÏ || p_175793_1_.Ý() == Blocks.áˆºÛ || p_175793_1_.Ý() == Blocks.Œáƒ || p_175793_1_.Ý() == Blocks.áŒŠá€ || ((p_175793_1_.Ý() == Blocks.£á || p_175793_1_.Ý() == Blocks.Å) && p_175793_2_.Ý().Ó() != Material.Ø);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final World worldIn, final int p_180701_2_, final int p_180701_3_, final int p_180701_4_, final int p_180701_5_, final ChunkPrimer p_180701_6_) {
        int var7 = this.Â.nextInt(this.Â.nextInt(this.Â.nextInt(15) + 1) + 1);
        if (this.Â.nextInt(7) != 0) {
            var7 = 0;
        }
        for (int var8 = 0; var8 < var7; ++var8) {
            final double var9 = p_180701_2_ * 16 + this.Â.nextInt(16);
            final double var10 = this.Â.nextInt(this.Â.nextInt(120) + 8);
            final double var11 = p_180701_3_ * 16 + this.Â.nextInt(16);
            int var12 = 1;
            if (this.Â.nextInt(4) == 0) {
                this.HorizonCode_Horizon_È(this.Â.nextLong(), p_180701_4_, p_180701_5_, p_180701_6_, var9, var10, var11);
                var12 += this.Â.nextInt(4);
            }
            for (int var13 = 0; var13 < var12; ++var13) {
                final float var14 = this.Â.nextFloat() * 3.1415927f * 2.0f;
                final float var15 = (this.Â.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float var16 = this.Â.nextFloat() * 2.0f + this.Â.nextFloat();
                if (this.Â.nextInt(10) == 0) {
                    var16 *= this.Â.nextFloat() * this.Â.nextFloat() * 3.0f + 1.0f;
                }
                this.HorizonCode_Horizon_È(this.Â.nextLong(), p_180701_4_, p_180701_5_, p_180701_6_, var9, var10, var11, var16, var14, var15, 0, 0, 1.0);
            }
        }
    }
}
