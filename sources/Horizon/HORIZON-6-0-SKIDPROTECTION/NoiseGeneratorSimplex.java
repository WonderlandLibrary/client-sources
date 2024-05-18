package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class NoiseGeneratorSimplex
{
    private static int[][] Âµá€;
    public static final double HorizonCode_Horizon_È;
    private int[] Ó;
    public double Â;
    public double Ý;
    public double Ø­áŒŠá;
    private static final double à;
    private static final double Ø;
    private static final String áŒŠÆ = "CL_00000537";
    
    static {
        NoiseGeneratorSimplex.Âµá€ = new int[][] { { 1, 1, 0 }, { -1, 1, 0 }, { 1, -1, 0 }, { -1, -1, 0 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
        HorizonCode_Horizon_È = Math.sqrt(3.0);
        à = 0.5 * (NoiseGeneratorSimplex.HorizonCode_Horizon_È - 1.0);
        Ø = (3.0 - NoiseGeneratorSimplex.HorizonCode_Horizon_È) / 6.0;
    }
    
    public NoiseGeneratorSimplex() {
        this(new Random());
    }
    
    public NoiseGeneratorSimplex(final Random p_i45471_1_) {
        this.Ó = new int[512];
        this.Â = p_i45471_1_.nextDouble() * 256.0;
        this.Ý = p_i45471_1_.nextDouble() * 256.0;
        this.Ø­áŒŠá = p_i45471_1_.nextDouble() * 256.0;
        for (int var2 = 0; var2 < 256; this.Ó[var2] = var2++) {}
        for (int var2 = 0; var2 < 256; ++var2) {
            final int var3 = p_i45471_1_.nextInt(256 - var2) + var2;
            final int var4 = this.Ó[var2];
            this.Ó[var2] = this.Ó[var3];
            this.Ó[var3] = var4;
            this.Ó[var2 + 256] = this.Ó[var2];
        }
    }
    
    private static int HorizonCode_Horizon_È(final double p_151607_0_) {
        return (p_151607_0_ > 0.0) ? ((int)p_151607_0_) : ((int)p_151607_0_ - 1);
    }
    
    private static double HorizonCode_Horizon_È(final int[] p_151604_0_, final double p_151604_1_, final double p_151604_3_) {
        return p_151604_0_[0] * p_151604_1_ + p_151604_0_[1] * p_151604_3_;
    }
    
    public double HorizonCode_Horizon_È(final double p_151605_1_, final double p_151605_3_) {
        final double var11 = 0.5 * (NoiseGeneratorSimplex.HorizonCode_Horizon_È - 1.0);
        final double var12 = (p_151605_1_ + p_151605_3_) * var11;
        final int var13 = HorizonCode_Horizon_È(p_151605_1_ + var12);
        final int var14 = HorizonCode_Horizon_È(p_151605_3_ + var12);
        final double var15 = (3.0 - NoiseGeneratorSimplex.HorizonCode_Horizon_È) / 6.0;
        final double var16 = (var13 + var14) * var15;
        final double var17 = var13 - var16;
        final double var18 = var14 - var16;
        final double var19 = p_151605_1_ - var17;
        final double var20 = p_151605_3_ - var18;
        byte var21;
        byte var22;
        if (var19 > var20) {
            var21 = 1;
            var22 = 0;
        }
        else {
            var21 = 0;
            var22 = 1;
        }
        final double var23 = var19 - var21 + var15;
        final double var24 = var20 - var22 + var15;
        final double var25 = var19 - 1.0 + 2.0 * var15;
        final double var26 = var20 - 1.0 + 2.0 * var15;
        final int var27 = var13 & 0xFF;
        final int var28 = var14 & 0xFF;
        final int var29 = this.Ó[var27 + this.Ó[var28]] % 12;
        final int var30 = this.Ó[var27 + var21 + this.Ó[var28 + var22]] % 12;
        final int var31 = this.Ó[var27 + 1 + this.Ó[var28 + 1]] % 12;
        double var32 = 0.5 - var19 * var19 - var20 * var20;
        double var33;
        if (var32 < 0.0) {
            var33 = 0.0;
        }
        else {
            var32 *= var32;
            var33 = var32 * var32 * HorizonCode_Horizon_È(NoiseGeneratorSimplex.Âµá€[var29], var19, var20);
        }
        double var34 = 0.5 - var23 * var23 - var24 * var24;
        double var35;
        if (var34 < 0.0) {
            var35 = 0.0;
        }
        else {
            var34 *= var34;
            var35 = var34 * var34 * HorizonCode_Horizon_È(NoiseGeneratorSimplex.Âµá€[var30], var23, var24);
        }
        double var36 = 0.5 - var25 * var25 - var26 * var26;
        double var37;
        if (var36 < 0.0) {
            var37 = 0.0;
        }
        else {
            var36 *= var36;
            var37 = var36 * var36 * HorizonCode_Horizon_È(NoiseGeneratorSimplex.Âµá€[var31], var25, var26);
        }
        return 70.0 * (var33 + var35 + var37);
    }
    
    public void HorizonCode_Horizon_È(final double[] p_151606_1_, final double p_151606_2_, final double p_151606_4_, final int p_151606_6_, final int p_151606_7_, final double p_151606_8_, final double p_151606_10_, final double p_151606_12_) {
        int var14 = 0;
        for (int var15 = 0; var15 < p_151606_7_; ++var15) {
            final double var16 = (p_151606_4_ + var15) * p_151606_10_ + this.Ý;
            for (int var17 = 0; var17 < p_151606_6_; ++var17) {
                final double var18 = (p_151606_2_ + var17) * p_151606_8_ + this.Â;
                final double var19 = (var18 + var16) * NoiseGeneratorSimplex.à;
                final int var20 = HorizonCode_Horizon_È(var18 + var19);
                final int var21 = HorizonCode_Horizon_È(var16 + var19);
                final double var22 = (var20 + var21) * NoiseGeneratorSimplex.Ø;
                final double var23 = var20 - var22;
                final double var24 = var21 - var22;
                final double var25 = var18 - var23;
                final double var26 = var16 - var24;
                byte var27;
                byte var28;
                if (var25 > var26) {
                    var27 = 1;
                    var28 = 0;
                }
                else {
                    var27 = 0;
                    var28 = 1;
                }
                final double var29 = var25 - var27 + NoiseGeneratorSimplex.Ø;
                final double var30 = var26 - var28 + NoiseGeneratorSimplex.Ø;
                final double var31 = var25 - 1.0 + 2.0 * NoiseGeneratorSimplex.Ø;
                final double var32 = var26 - 1.0 + 2.0 * NoiseGeneratorSimplex.Ø;
                final int var33 = var20 & 0xFF;
                final int var34 = var21 & 0xFF;
                final int var35 = this.Ó[var33 + this.Ó[var34]] % 12;
                final int var36 = this.Ó[var33 + var27 + this.Ó[var34 + var28]] % 12;
                final int var37 = this.Ó[var33 + 1 + this.Ó[var34 + 1]] % 12;
                double var38 = 0.5 - var25 * var25 - var26 * var26;
                double var39;
                if (var38 < 0.0) {
                    var39 = 0.0;
                }
                else {
                    var38 *= var38;
                    var39 = var38 * var38 * HorizonCode_Horizon_È(NoiseGeneratorSimplex.Âµá€[var35], var25, var26);
                }
                double var40 = 0.5 - var29 * var29 - var30 * var30;
                double var41;
                if (var40 < 0.0) {
                    var41 = 0.0;
                }
                else {
                    var40 *= var40;
                    var41 = var40 * var40 * HorizonCode_Horizon_È(NoiseGeneratorSimplex.Âµá€[var36], var29, var30);
                }
                double var42 = 0.5 - var31 * var31 - var32 * var32;
                double var43;
                if (var42 < 0.0) {
                    var43 = 0.0;
                }
                else {
                    var42 *= var42;
                    var43 = var42 * var42 * HorizonCode_Horizon_È(NoiseGeneratorSimplex.Âµá€[var37], var31, var32);
                }
                final int n;
                final int var44 = n = var14++;
                p_151606_1_[n] += 70.0 * (var39 + var41 + var43) * p_151606_12_;
            }
        }
    }
}
