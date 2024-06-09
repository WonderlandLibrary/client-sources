package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;
import java.util.Random;

public class MathHelper
{
    public static final float HorizonCode_Horizon_È;
    private static final int à = 12;
    private static final int Ø = 4095;
    private static final int áŒŠÆ = 4096;
    public static final float Â = 3.1415927f;
    public static final float Ý = 6.2831855f;
    public static final float Ø­áŒŠá = 1.5707964f;
    private static final float áˆºÑ¢Õ = 6.2831855f;
    private static final float ÂµÈ = 360.0f;
    private static final float á = 651.8986f;
    private static final float ˆÏ­ = 11.377778f;
    public static final float Âµá€ = 0.017453292f;
    private static final float[] £á;
    public static boolean Ó;
    private static final float[] Å;
    private static final int[] £à;
    private static final String µà = "CL_00001496";
    
    static {
        HorizonCode_Horizon_È = Ý(2.0f);
        £á = new float[4096];
        MathHelper.Ó = false;
        Å = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathHelper.Å[i] = (float)Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
        £à = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
        for (int i = 0; i < 4096; ++i) {
            MathHelper.£á[i] = (float)Math.sin((i + 0.5f) / 4096.0f * 6.2831855f);
        }
        for (int i = 0; i < 360; i += 90) {
            MathHelper.£á[(int)(i * 11.377778f) & 0xFFF] = (float)Math.sin(i * 0.017453292f);
        }
    }
    
    public static float HorizonCode_Horizon_È(final float p_76126_0_) {
        return MathHelper.Ó ? MathHelper.£á[(int)(p_76126_0_ * 651.8986f) & 0xFFF] : MathHelper.Å[(int)(p_76126_0_ * 10430.378f) & 0xFFFF];
    }
    
    public static float Â(final float p_76134_0_) {
        return MathHelper.Ó ? MathHelper.£á[(int)((p_76134_0_ + 1.5707964f) * 651.8986f) & 0xFFF] : MathHelper.Å[(int)(p_76134_0_ * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static float Ý(final float p_76129_0_) {
        return (float)Math.sqrt(p_76129_0_);
    }
    
    public static float HorizonCode_Horizon_È(final double p_76133_0_) {
        return (float)Math.sqrt(p_76133_0_);
    }
    
    public static int Ø­áŒŠá(final float p_76141_0_) {
        final int var1 = (int)p_76141_0_;
        return (p_76141_0_ < var1) ? (var1 - 1) : var1;
    }
    
    public static int Â(final double p_76140_0_) {
        return (int)(p_76140_0_ + 1024.0) - 1024;
    }
    
    public static int Ý(final double p_76128_0_) {
        final int var2 = (int)p_76128_0_;
        return (p_76128_0_ < var2) ? (var2 - 1) : var2;
    }
    
    public static long Ø­áŒŠá(final double p_76124_0_) {
        final long var2 = (long)p_76124_0_;
        return (p_76124_0_ < var2) ? (var2 - 1L) : var2;
    }
    
    public static int Âµá€(final double p_154353_0_) {
        return (int)((p_154353_0_ >= 0.0) ? p_154353_0_ : (-p_154353_0_ + 1.0));
    }
    
    public static float Âµá€(final float p_76135_0_) {
        return (p_76135_0_ >= 0.0f) ? p_76135_0_ : (-p_76135_0_);
    }
    
    public static int HorizonCode_Horizon_È(final int p_76130_0_) {
        return (p_76130_0_ >= 0) ? p_76130_0_ : (-p_76130_0_);
    }
    
    public static int Ó(final float p_76123_0_) {
        final int var1 = (int)p_76123_0_;
        return (p_76123_0_ > var1) ? (var1 + 1) : var1;
    }
    
    public static int Ó(final double p_76143_0_) {
        final int var2 = (int)p_76143_0_;
        return (p_76143_0_ > var2) ? (var2 + 1) : var2;
    }
    
    public static int HorizonCode_Horizon_È(final int p_76125_0_, final int p_76125_1_, final int p_76125_2_) {
        return (p_76125_0_ < p_76125_1_) ? p_76125_1_ : ((p_76125_0_ > p_76125_2_) ? p_76125_2_ : p_76125_0_);
    }
    
    public static float HorizonCode_Horizon_È(final float p_76131_0_, final float p_76131_1_, final float p_76131_2_) {
        return (p_76131_0_ < p_76131_1_) ? p_76131_1_ : ((p_76131_0_ > p_76131_2_) ? p_76131_2_ : p_76131_0_);
    }
    
    public static double HorizonCode_Horizon_È(final double p_151237_0_, final double p_151237_2_, final double p_151237_4_) {
        return (p_151237_0_ < p_151237_2_) ? p_151237_2_ : ((p_151237_0_ > p_151237_4_) ? p_151237_4_ : p_151237_0_);
    }
    
    public static double Â(final double p_151238_0_, final double p_151238_2_, final double p_151238_4_) {
        return (p_151238_4_ < 0.0) ? p_151238_0_ : ((p_151238_4_ > 1.0) ? p_151238_2_ : (p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_));
    }
    
    public static double HorizonCode_Horizon_È(double p_76132_0_, double p_76132_2_) {
        if (p_76132_0_ < 0.0) {
            p_76132_0_ = -p_76132_0_;
        }
        if (p_76132_2_ < 0.0) {
            p_76132_2_ = -p_76132_2_;
        }
        return (p_76132_0_ > p_76132_2_) ? p_76132_0_ : p_76132_2_;
    }
    
    public static int HorizonCode_Horizon_È(final int p_76137_0_, final int p_76137_1_) {
        return (p_76137_0_ < 0) ? (-((-p_76137_0_ - 1) / p_76137_1_) - 1) : (p_76137_0_ / p_76137_1_);
    }
    
    public static int HorizonCode_Horizon_È(final Random p_76136_0_, final int p_76136_1_, final int p_76136_2_) {
        return (p_76136_1_ >= p_76136_2_) ? p_76136_1_ : (p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_);
    }
    
    public static float HorizonCode_Horizon_È(final Random p_151240_0_, final float p_151240_1_, final float p_151240_2_) {
        return (p_151240_1_ >= p_151240_2_) ? p_151240_1_ : (p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_);
    }
    
    public static double HorizonCode_Horizon_È(final Random p_82716_0_, final double p_82716_1_, final double p_82716_3_) {
        return (p_82716_1_ >= p_82716_3_) ? p_82716_1_ : (p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_);
    }
    
    public static double HorizonCode_Horizon_È(final long[] p_76127_0_) {
        long var1 = 0L;
        for (final long var4 : p_76127_0_) {
            var1 += var4;
        }
        return var1 / p_76127_0_.length;
    }
    
    public static boolean HorizonCode_Horizon_È(final float p_180185_0_, final float p_180185_1_) {
        return Âµá€(p_180185_1_ - p_180185_0_) < 1.0E-5f;
    }
    
    public static int Â(final int p_180184_0_, final int p_180184_1_) {
        return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
    }
    
    public static float à(float p_76142_0_) {
        p_76142_0_ %= 360.0f;
        if (p_76142_0_ >= 180.0f) {
            p_76142_0_ -= 360.0f;
        }
        if (p_76142_0_ < -180.0f) {
            p_76142_0_ += 360.0f;
        }
        return p_76142_0_;
    }
    
    public static double à(double p_76138_0_) {
        p_76138_0_ %= 360.0;
        if (p_76138_0_ >= 180.0) {
            p_76138_0_ -= 360.0;
        }
        if (p_76138_0_ < -180.0) {
            p_76138_0_ += 360.0;
        }
        return p_76138_0_;
    }
    
    public static int HorizonCode_Horizon_È(final String p_82715_0_, final int p_82715_1_) {
        try {
            return Integer.parseInt(p_82715_0_);
        }
        catch (Throwable var3) {
            return p_82715_1_;
        }
    }
    
    public static int HorizonCode_Horizon_È(final String p_82714_0_, final int p_82714_1_, final int p_82714_2_) {
        return Math.max(p_82714_2_, HorizonCode_Horizon_È(p_82714_0_, p_82714_1_));
    }
    
    public static double HorizonCode_Horizon_È(final String p_82712_0_, final double p_82712_1_) {
        try {
            return Double.parseDouble(p_82712_0_);
        }
        catch (Throwable var4) {
            return p_82712_1_;
        }
    }
    
    public static double HorizonCode_Horizon_È(final String p_82713_0_, final double p_82713_1_, final double p_82713_3_) {
        return Math.max(p_82713_3_, HorizonCode_Horizon_È(p_82713_0_, p_82713_1_));
    }
    
    public static int Â(final int p_151236_0_) {
        int var1 = p_151236_0_ - 1;
        var1 |= var1 >> 1;
        var1 |= var1 >> 2;
        var1 |= var1 >> 4;
        var1 |= var1 >> 8;
        var1 |= var1 >> 16;
        return var1 + 1;
    }
    
    private static boolean Ø­áŒŠá(final int p_151235_0_) {
        return p_151235_0_ != 0 && (p_151235_0_ & p_151235_0_ - 1) == 0x0;
    }
    
    private static int Âµá€(int p_151241_0_) {
        p_151241_0_ = (Ø­áŒŠá(p_151241_0_) ? p_151241_0_ : Â(p_151241_0_));
        return MathHelper.£à[(int)(p_151241_0_ * 125613361L >> 27) & 0x1F];
    }
    
    public static int Ý(final int p_151239_0_) {
        return Âµá€(p_151239_0_) - (Ø­áŒŠá(p_151239_0_) ? 0 : 1);
    }
    
    public static int Ý(final int p_154354_0_, int p_154354_1_) {
        if (p_154354_1_ == 0) {
            return 0;
        }
        if (p_154354_0_ == 0) {
            return p_154354_1_;
        }
        if (p_154354_0_ < 0) {
            p_154354_1_ *= -1;
        }
        final int var2 = p_154354_0_ % p_154354_1_;
        return (var2 == 0) ? p_154354_0_ : (p_154354_0_ + p_154354_1_ - var2);
    }
    
    public static int Â(final float p_180183_0_, final float p_180183_1_, final float p_180183_2_) {
        return Â(Ø­áŒŠá(p_180183_0_ * 255.0f), Ø­áŒŠá(p_180183_1_ * 255.0f), Ø­áŒŠá(p_180183_2_ * 255.0f));
    }
    
    public static int Â(final int p_180181_0_, final int p_180181_1_, final int p_180181_2_) {
        int var3 = (p_180181_0_ << 8) + p_180181_1_;
        var3 = (var3 << 8) + p_180181_2_;
        return var3;
    }
    
    public static int Ø­áŒŠá(final int p_180188_0_, final int p_180188_1_) {
        final int var2 = (p_180188_0_ & 0xFF0000) >> 16;
        final int var3 = (p_180188_1_ & 0xFF0000) >> 16;
        final int var4 = (p_180188_0_ & 0xFF00) >> 8;
        final int var5 = (p_180188_1_ & 0xFF00) >> 8;
        final int var6 = (p_180188_0_ & 0xFF) >> 0;
        final int var7 = (p_180188_1_ & 0xFF) >> 0;
        final int var8 = (int)(var2 * var3 / 255.0f);
        final int var9 = (int)(var4 * var5 / 255.0f);
        final int var10 = (int)(var6 * var7 / 255.0f);
        return (p_180188_0_ & 0xFF000000) | var8 << 16 | var9 << 8 | var10;
    }
    
    public static long HorizonCode_Horizon_È(final Vec3i pos) {
        return Ý(pos.HorizonCode_Horizon_È(), pos.Â(), pos.Ý());
    }
    
    public static long Ý(final int x, final int y, final int z) {
        long var3 = x * 3129871 ^ z * 116129781L ^ y;
        var3 = var3 * var3 * 42317861L + var3 * 11L;
        return var3;
    }
    
    public static UUID HorizonCode_Horizon_È(final Random p_180182_0_) {
        final long var1 = (p_180182_0_.nextLong() & 0xFFFFFFFFFFFF0FFFL) | 0x4000L;
        final long var2 = (p_180182_0_.nextLong() & 0x3FFFFFFFFFFFFFFFL) | Long.MIN_VALUE;
        return new UUID(var1, var2);
    }
}
