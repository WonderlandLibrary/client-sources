package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerBiomeEdge extends GenLayer
{
    private static final String Ý = "CL_00000554";
    
    public GenLayerBiomeEdge(final long p_i45475_1_, final GenLayer p_i45475_3_) {
        super(p_i45475_1_);
        this.HorizonCode_Horizon_È = p_i45475_3_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        final int[] var6 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var7 = 0; var7 < areaHeight; ++var7) {
            for (int var8 = 0; var8 < areaWidth; ++var8) {
                this.HorizonCode_Horizon_È(var8 + areaX, (long)(var7 + areaY));
                final int var9 = var5[var8 + 1 + (var7 + 1) * (areaWidth + 2)];
                if (!this.HorizonCode_Horizon_È(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.¥Æ.ÇªÔ, BiomeGenBase.á€.ÇªÔ) && !this.Â(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.Ï.ÇªÔ, BiomeGenBase.ÇŽá€.ÇªÔ) && !this.Â(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.Ô.ÇªÔ, BiomeGenBase.ÇŽá€.ÇªÔ) && !this.Â(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.Ø­Âµ.ÇªÔ, BiomeGenBase.µÕ.ÇªÔ)) {
                    if (var9 == BiomeGenBase.ˆà.ÇªÔ) {
                        final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                        final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                        final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                        final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                        if (var10 != BiomeGenBase.ŒÏ.ÇªÔ && var11 != BiomeGenBase.ŒÏ.ÇªÔ && var12 != BiomeGenBase.ŒÏ.ÇªÔ && var13 != BiomeGenBase.ŒÏ.ÇªÔ) {
                            var6[var8 + var7 * areaWidth] = var9;
                        }
                        else {
                            var6[var8 + var7 * areaWidth] = BiomeGenBase.Ñ¢Â.ÇªÔ;
                        }
                    }
                    else if (var9 == BiomeGenBase.Æ.ÇªÔ) {
                        final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                        final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                        final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                        final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                        if (var10 != BiomeGenBase.ˆà.ÇªÔ && var11 != BiomeGenBase.ˆà.ÇªÔ && var12 != BiomeGenBase.ˆà.ÇªÔ && var13 != BiomeGenBase.ˆà.ÇªÔ && var10 != BiomeGenBase.áŒŠ.ÇªÔ && var11 != BiomeGenBase.áŒŠ.ÇªÔ && var12 != BiomeGenBase.áŒŠ.ÇªÔ && var13 != BiomeGenBase.áŒŠ.ÇªÔ && var10 != BiomeGenBase.ŒÏ.ÇªÔ && var11 != BiomeGenBase.ŒÏ.ÇªÔ && var12 != BiomeGenBase.ŒÏ.ÇªÔ && var13 != BiomeGenBase.ŒÏ.ÇªÔ) {
                            if (var10 != BiomeGenBase.Õ.ÇªÔ && var13 != BiomeGenBase.Õ.ÇªÔ && var11 != BiomeGenBase.Õ.ÇªÔ && var12 != BiomeGenBase.Õ.ÇªÔ) {
                                var6[var8 + var7 * areaWidth] = var9;
                            }
                            else {
                                var6[var8 + var7 * areaWidth] = BiomeGenBase.ŠÂµà.ÇªÔ;
                            }
                        }
                        else {
                            var6[var8 + var7 * areaWidth] = BiomeGenBase.µà.ÇªÔ;
                        }
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = var9;
                    }
                }
            }
        }
        return var6;
    }
    
    private boolean HorizonCode_Horizon_È(final int[] p_151636_1_, final int[] p_151636_2_, final int p_151636_3_, final int p_151636_4_, final int p_151636_5_, final int p_151636_6_, final int p_151636_7_, final int p_151636_8_) {
        if (!GenLayer.HorizonCode_Horizon_È(p_151636_6_, p_151636_7_)) {
            return false;
        }
        final int var9 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
        final int var10 = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        final int var11 = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        final int var12 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];
        if (this.Â(var9, p_151636_7_) && this.Â(var10, p_151636_7_) && this.Â(var11, p_151636_7_) && this.Â(var12, p_151636_7_)) {
            p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_6_;
        }
        else {
            p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_8_;
        }
        return true;
    }
    
    private boolean Â(final int[] p_151635_1_, final int[] p_151635_2_, final int p_151635_3_, final int p_151635_4_, final int p_151635_5_, final int p_151635_6_, final int p_151635_7_, final int p_151635_8_) {
        if (p_151635_6_ != p_151635_7_) {
            return false;
        }
        final int var9 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
        final int var10 = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        final int var11 = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        final int var12 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];
        if (GenLayer.HorizonCode_Horizon_È(var9, p_151635_7_) && GenLayer.HorizonCode_Horizon_È(var10, p_151635_7_) && GenLayer.HorizonCode_Horizon_È(var11, p_151635_7_) && GenLayer.HorizonCode_Horizon_È(var12, p_151635_7_)) {
            p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_6_;
        }
        else {
            p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_8_;
        }
        return true;
    }
    
    private boolean Â(final int p_151634_1_, final int p_151634_2_) {
        if (GenLayer.HorizonCode_Horizon_È(p_151634_1_, p_151634_2_)) {
            return true;
        }
        final BiomeGenBase var3 = BiomeGenBase.Âµá€(p_151634_1_);
        final BiomeGenBase var4 = BiomeGenBase.Âµá€(p_151634_2_);
        if (var3 != null && var4 != null) {
            final BiomeGenBase.Ø­áŒŠá var5 = var3.ˆÏ­();
            final BiomeGenBase.Ø­áŒŠá var6 = var4.ˆÏ­();
            return var5 == var6 || var5 == BiomeGenBase.Ø­áŒŠá.Ý || var6 == BiomeGenBase.Ø­áŒŠá.Ý;
        }
        return false;
    }
}
