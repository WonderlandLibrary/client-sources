package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator
{
    private int[] Ø­áŒŠá;
    public double HorizonCode_Horizon_È;
    public double Â;
    public double Ý;
    private static final double[] Âµá€;
    private static final double[] Ó;
    private static final double[] à;
    private static final double[] Ø;
    private static final double[] áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000534";
    
    static {
        Âµá€ = new double[] { 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0 };
        Ó = new double[] { 1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0 };
        à = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0 };
        Ø = new double[] { 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0 };
        áŒŠÆ = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0 };
    }
    
    public NoiseGeneratorImproved() {
        this(new Random());
    }
    
    public NoiseGeneratorImproved(final Random p_i45469_1_) {
        this.Ø­áŒŠá = new int[512];
        this.HorizonCode_Horizon_È = p_i45469_1_.nextDouble() * 256.0;
        this.Â = p_i45469_1_.nextDouble() * 256.0;
        this.Ý = p_i45469_1_.nextDouble() * 256.0;
        for (int var2 = 0; var2 < 256; this.Ø­áŒŠá[var2] = var2++) {}
        for (int var2 = 0; var2 < 256; ++var2) {
            final int var3 = p_i45469_1_.nextInt(256 - var2) + var2;
            final int var4 = this.Ø­áŒŠá[var2];
            this.Ø­áŒŠá[var2] = this.Ø­áŒŠá[var3];
            this.Ø­áŒŠá[var3] = var4;
            this.Ø­áŒŠá[var2 + 256] = this.Ø­áŒŠá[var2];
        }
    }
    
    public final double HorizonCode_Horizon_È(final double p_76311_1_, final double p_76311_3_, final double p_76311_5_) {
        return p_76311_3_ + p_76311_1_ * (p_76311_5_ - p_76311_3_);
    }
    
    public final double HorizonCode_Horizon_È(final int p_76309_1_, final double p_76309_2_, final double p_76309_4_) {
        final int var6 = p_76309_1_ & 0xF;
        return NoiseGeneratorImproved.Ø[var6] * p_76309_2_ + NoiseGeneratorImproved.áŒŠÆ[var6] * p_76309_4_;
    }
    
    public final double HorizonCode_Horizon_È(final int p_76310_1_, final double p_76310_2_, final double p_76310_4_, final double p_76310_6_) {
        final int var8 = p_76310_1_ & 0xF;
        return NoiseGeneratorImproved.Âµá€[var8] * p_76310_2_ + NoiseGeneratorImproved.Ó[var8] * p_76310_4_ + NoiseGeneratorImproved.à[var8] * p_76310_6_;
    }
    
    public void HorizonCode_Horizon_È(final double[] p_76308_1_, final double p_76308_2_, final double p_76308_4_, final double p_76308_6_, final int p_76308_8_, final int p_76308_9_, final int p_76308_10_, final double p_76308_11_, final double p_76308_13_, final double p_76308_15_, final double p_76308_17_) {
        if (p_76308_9_ == 1) {
            final boolean var64 = false;
            final boolean var65 = false;
            final boolean var66 = false;
            final boolean var67 = false;
            double var68 = 0.0;
            double var69 = 0.0;
            int var70 = 0;
            final double var71 = 1.0 / p_76308_17_;
            for (int var72 = 0; var72 < p_76308_8_; ++var72) {
                double var73 = p_76308_2_ + var72 * p_76308_11_ + this.HorizonCode_Horizon_È;
                int var74 = (int)var73;
                if (var73 < var74) {
                    --var74;
                }
                final int var75 = var74 & 0xFF;
                var73 -= var74;
                final double var76 = var73 * var73 * var73 * (var73 * (var73 * 6.0 - 15.0) + 10.0);
                for (int var77 = 0; var77 < p_76308_10_; ++var77) {
                    double var78 = p_76308_6_ + var77 * p_76308_15_ + this.Ý;
                    int var79 = (int)var78;
                    if (var78 < var79) {
                        --var79;
                    }
                    final int var80 = var79 & 0xFF;
                    var78 -= var79;
                    final double var81 = var78 * var78 * var78 * (var78 * (var78 * 6.0 - 15.0) + 10.0);
                    final int var82 = this.Ø­áŒŠá[var75] + 0;
                    final int var83 = this.Ø­áŒŠá[var82] + var80;
                    final int var84 = this.Ø­áŒŠá[var75 + 1] + 0;
                    final int var85 = this.Ø­áŒŠá[var84] + var80;
                    var68 = this.HorizonCode_Horizon_È(var76, this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var83], var73, var78), this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var85], var73 - 1.0, 0.0, var78));
                    var69 = this.HorizonCode_Horizon_È(var76, this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var83 + 1], var73, 0.0, var78 - 1.0), this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var85 + 1], var73 - 1.0, 0.0, var78 - 1.0));
                    final double var86 = this.HorizonCode_Horizon_È(var81, var68, var69);
                    final int n;
                    final int var87 = n = var70++;
                    p_76308_1_[n] += var86 * var71;
                }
            }
        }
        else {
            int var82 = 0;
            final double var88 = 1.0 / p_76308_17_;
            int var85 = -1;
            final boolean var89 = false;
            final boolean var90 = false;
            final boolean var91 = false;
            final boolean var92 = false;
            final boolean var93 = false;
            final boolean var94 = false;
            double var95 = 0.0;
            double var73 = 0.0;
            double var96 = 0.0;
            double var76 = 0.0;
            for (int var77 = 0; var77 < p_76308_8_; ++var77) {
                double var78 = p_76308_2_ + var77 * p_76308_11_ + this.HorizonCode_Horizon_È;
                int var79 = (int)var78;
                if (var78 < var79) {
                    --var79;
                }
                final int var80 = var79 & 0xFF;
                var78 -= var79;
                final double var81 = var78 * var78 * var78 * (var78 * (var78 * 6.0 - 15.0) + 10.0);
                for (int var97 = 0; var97 < p_76308_10_; ++var97) {
                    double var98 = p_76308_6_ + var97 * p_76308_15_ + this.Ý;
                    int var99 = (int)var98;
                    if (var98 < var99) {
                        --var99;
                    }
                    final int var100 = var99 & 0xFF;
                    var98 -= var99;
                    final double var101 = var98 * var98 * var98 * (var98 * (var98 * 6.0 - 15.0) + 10.0);
                    for (int var102 = 0; var102 < p_76308_9_; ++var102) {
                        double var103 = p_76308_4_ + var102 * p_76308_13_ + this.Â;
                        int var104 = (int)var103;
                        if (var103 < var104) {
                            --var104;
                        }
                        final int var105 = var104 & 0xFF;
                        var103 -= var104;
                        final double var106 = var103 * var103 * var103 * (var103 * (var103 * 6.0 - 15.0) + 10.0);
                        if (var102 == 0 || var105 != var85) {
                            var85 = var105;
                            final int var107 = this.Ø­áŒŠá[var80] + var105;
                            final int var108 = this.Ø­áŒŠá[var107] + var100;
                            final int var109 = this.Ø­áŒŠá[var107 + 1] + var100;
                            final int var110 = this.Ø­áŒŠá[var80 + 1] + var105;
                            final int var70 = this.Ø­áŒŠá[var110] + var100;
                            final int var111 = this.Ø­áŒŠá[var110 + 1] + var100;
                            var95 = this.HorizonCode_Horizon_È(var81, this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var108], var78, var103, var98), this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var70], var78 - 1.0, var103, var98));
                            var73 = this.HorizonCode_Horizon_È(var81, this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var109], var78, var103 - 1.0, var98), this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var111], var78 - 1.0, var103 - 1.0, var98));
                            var96 = this.HorizonCode_Horizon_È(var81, this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var108 + 1], var78, var103, var98 - 1.0), this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var70 + 1], var78 - 1.0, var103, var98 - 1.0));
                            var76 = this.HorizonCode_Horizon_È(var81, this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var109 + 1], var78, var103 - 1.0, var98 - 1.0), this.HorizonCode_Horizon_È(this.Ø­áŒŠá[var111 + 1], var78 - 1.0, var103 - 1.0, var98 - 1.0));
                        }
                        final double var112 = this.HorizonCode_Horizon_È(var106, var95, var73);
                        final double var113 = this.HorizonCode_Horizon_È(var106, var96, var76);
                        final double var114 = this.HorizonCode_Horizon_È(var101, var112, var113);
                        final int n2;
                        final int var87 = n2 = var82++;
                        p_76308_1_[n2] += var114 * var88;
                    }
                }
            }
        }
    }
}
