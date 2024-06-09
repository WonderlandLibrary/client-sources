package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerEdge extends GenLayer
{
    private final HorizonCode_Horizon_È Ý;
    private static final String Ø­áŒŠá = "CL_00000547";
    
    public GenLayerEdge(final long p_i45474_1_, final GenLayer p_i45474_3_, final HorizonCode_Horizon_È p_i45474_4_) {
        super(p_i45474_1_);
        this.HorizonCode_Horizon_È = p_i45474_3_;
        this.Ý = p_i45474_4_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        switch (Â.HorizonCode_Horizon_È[this.Ý.ordinal()]) {
            default: {
                return this.Ý(areaX, areaY, areaWidth, areaHeight);
            }
            case 2: {
                return this.Ø­áŒŠá(areaX, areaY, areaWidth, areaHeight);
            }
            case 3: {
                return this.Âµá€(areaX, areaY, areaWidth, areaHeight);
            }
        }
    }
    
    private int[] Ý(final int p_151626_1_, final int p_151626_2_, final int p_151626_3_, final int p_151626_4_) {
        final int var5 = p_151626_1_ - 1;
        final int var6 = p_151626_2_ - 1;
        final int var7 = 1 + p_151626_3_ + 1;
        final int var8 = 1 + p_151626_4_ + 1;
        final int[] var9 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5, var6, var7, var8);
        final int[] var10 = IntCache.HorizonCode_Horizon_È(p_151626_3_ * p_151626_4_);
        for (int var11 = 0; var11 < p_151626_4_; ++var11) {
            for (int var12 = 0; var12 < p_151626_3_; ++var12) {
                this.HorizonCode_Horizon_È(var12 + p_151626_1_, (long)(var11 + p_151626_2_));
                int var13 = var9[var12 + 1 + (var11 + 1) * var7];
                if (var13 == 1) {
                    final int var14 = var9[var12 + 1 + (var11 + 1 - 1) * var7];
                    final int var15 = var9[var12 + 1 + 1 + (var11 + 1) * var7];
                    final int var16 = var9[var12 + 1 - 1 + (var11 + 1) * var7];
                    final int var17 = var9[var12 + 1 + (var11 + 1 + 1) * var7];
                    final boolean var18 = var14 == 3 || var15 == 3 || var16 == 3 || var17 == 3;
                    final boolean var19 = var14 == 4 || var15 == 4 || var16 == 4 || var17 == 4;
                    if (var18 || var19) {
                        var13 = 2;
                    }
                }
                var10[var12 + var11 * p_151626_3_] = var13;
            }
        }
        return var10;
    }
    
    private int[] Ø­áŒŠá(final int p_151624_1_, final int p_151624_2_, final int p_151624_3_, final int p_151624_4_) {
        final int var5 = p_151624_1_ - 1;
        final int var6 = p_151624_2_ - 1;
        final int var7 = 1 + p_151624_3_ + 1;
        final int var8 = 1 + p_151624_4_ + 1;
        final int[] var9 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5, var6, var7, var8);
        final int[] var10 = IntCache.HorizonCode_Horizon_È(p_151624_3_ * p_151624_4_);
        for (int var11 = 0; var11 < p_151624_4_; ++var11) {
            for (int var12 = 0; var12 < p_151624_3_; ++var12) {
                int var13 = var9[var12 + 1 + (var11 + 1) * var7];
                if (var13 == 4) {
                    final int var14 = var9[var12 + 1 + (var11 + 1 - 1) * var7];
                    final int var15 = var9[var12 + 1 + 1 + (var11 + 1) * var7];
                    final int var16 = var9[var12 + 1 - 1 + (var11 + 1) * var7];
                    final int var17 = var9[var12 + 1 + (var11 + 1 + 1) * var7];
                    final boolean var18 = var14 == 2 || var15 == 2 || var16 == 2 || var17 == 2;
                    final boolean var19 = var14 == 1 || var15 == 1 || var16 == 1 || var17 == 1;
                    if (var19 || var18) {
                        var13 = 3;
                    }
                }
                var10[var12 + var11 * p_151624_3_] = var13;
            }
        }
        return var10;
    }
    
    private int[] Âµá€(final int p_151625_1_, final int p_151625_2_, final int p_151625_3_, final int p_151625_4_) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_151625_1_, p_151625_2_, p_151625_3_, p_151625_4_);
        final int[] var6 = IntCache.HorizonCode_Horizon_È(p_151625_3_ * p_151625_4_);
        for (int var7 = 0; var7 < p_151625_4_; ++var7) {
            for (int var8 = 0; var8 < p_151625_3_; ++var8) {
                this.HorizonCode_Horizon_È(var8 + p_151625_1_, (long)(var7 + p_151625_2_));
                int var9 = var5[var8 + var7 * p_151625_3_];
                if (var9 != 0 && this.HorizonCode_Horizon_È(13) == 0) {
                    var9 |= (1 + this.HorizonCode_Horizon_È(15) << 8 & 0xF00);
                }
                var6[var8 + var7 * p_151625_3_] = var9;
            }
        }
        return var6;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("COOL_WARM", 0, "COOL_WARM", 0), 
        Â("HEAT_ICE", 1, "HEAT_ICE", 1), 
        Ý("SPECIAL", 2, "SPECIAL", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000549";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45473_1_, final int p_i45473_2_) {
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00000548";
        
        static {
            HorizonCode_Horizon_È = new int[GenLayerEdge.HorizonCode_Horizon_È.values().length];
            try {
                GenLayerEdge.Â.HorizonCode_Horizon_È[GenLayerEdge.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                GenLayerEdge.Â.HorizonCode_Horizon_È[GenLayerEdge.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                GenLayerEdge.Â.HorizonCode_Horizon_È[GenLayerEdge.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
