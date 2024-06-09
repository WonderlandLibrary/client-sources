package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator
{
    private NoiseGeneratorSimplex[] HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000536";
    
    public NoiseGeneratorPerlin(final Random p_i45470_1_, final int p_i45470_2_) {
        this.Â = p_i45470_2_;
        this.HorizonCode_Horizon_È = new NoiseGeneratorSimplex[p_i45470_2_];
        for (int var3 = 0; var3 < p_i45470_2_; ++var3) {
            this.HorizonCode_Horizon_È[var3] = new NoiseGeneratorSimplex(p_i45470_1_);
        }
    }
    
    public double HorizonCode_Horizon_È(final double p_151601_1_, final double p_151601_3_) {
        double var5 = 0.0;
        double var6 = 1.0;
        for (int var7 = 0; var7 < this.Â; ++var7) {
            var5 += this.HorizonCode_Horizon_È[var7].HorizonCode_Horizon_È(p_151601_1_ * var6, p_151601_3_ * var6) / var6;
            var6 /= 2.0;
        }
        return var5;
    }
    
    public double[] HorizonCode_Horizon_È(final double[] p_151599_1_, final double p_151599_2_, final double p_151599_4_, final int p_151599_6_, final int p_151599_7_, final double p_151599_8_, final double p_151599_10_, final double p_151599_12_) {
        return this.HorizonCode_Horizon_È(p_151599_1_, p_151599_2_, p_151599_4_, p_151599_6_, p_151599_7_, p_151599_8_, p_151599_10_, p_151599_12_, 0.5);
    }
    
    public double[] HorizonCode_Horizon_È(double[] p_151600_1_, final double p_151600_2_, final double p_151600_4_, final int p_151600_6_, final int p_151600_7_, final double p_151600_8_, final double p_151600_10_, final double p_151600_12_, final double p_151600_14_) {
        if (p_151600_1_ != null && p_151600_1_.length >= p_151600_6_ * p_151600_7_) {
            for (int var16 = 0; var16 < p_151600_1_.length; ++var16) {
                p_151600_1_[var16] = 0.0;
            }
        }
        else {
            p_151600_1_ = new double[p_151600_6_ * p_151600_7_];
        }
        double var17 = 1.0;
        double var18 = 1.0;
        for (int var19 = 0; var19 < this.Â; ++var19) {
            this.HorizonCode_Horizon_È[var19].HorizonCode_Horizon_È(p_151600_1_, p_151600_2_, p_151600_4_, p_151600_6_, p_151600_7_, p_151600_8_ * var18 * var17, p_151600_10_ * var18 * var17, 0.55 / var17);
            var18 *= p_151600_12_;
            var17 *= p_151600_14_;
        }
        return p_151600_1_;
    }
}
