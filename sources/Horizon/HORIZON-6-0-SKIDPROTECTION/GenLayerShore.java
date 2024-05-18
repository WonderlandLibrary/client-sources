package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerShore extends GenLayer
{
    private static final String Ý = "CL_00000568";
    
    public GenLayerShore(final long p_i2130_1_, final GenLayer p_i2130_3_) {
        super(p_i2130_1_);
        this.HorizonCode_Horizon_È = p_i2130_3_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        final int[] var6 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var7 = 0; var7 < areaHeight; ++var7) {
            for (int var8 = 0; var8 < areaWidth; ++var8) {
                this.HorizonCode_Horizon_È(var8 + areaX, (long)(var7 + areaY));
                final int var9 = var5[var8 + 1 + (var7 + 1) * (areaWidth + 2)];
                final BiomeGenBase var10 = BiomeGenBase.Âµá€(var9);
                if (var9 == BiomeGenBase.Ê.ÇªÔ) {
                    final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                    final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                    final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                    final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                    if (var11 != BiomeGenBase.£à.ÇªÔ && var12 != BiomeGenBase.£à.ÇªÔ && var13 != BiomeGenBase.£à.ÇªÔ && var14 != BiomeGenBase.£à.ÇªÔ) {
                        var6[var8 + var7 * areaWidth] = var9;
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = BiomeGenBase.ÇŽÉ.ÇªÔ;
                    }
                }
                else if (var10 != null && var10.á() == BiomeGenJungle.class) {
                    final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                    final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                    final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                    final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                    if (this.Ý(var11) && this.Ý(var12) && this.Ý(var13) && this.Ý(var14)) {
                        if (!GenLayer.Â(var11) && !GenLayer.Â(var12) && !GenLayer.Â(var13) && !GenLayer.Â(var14)) {
                            var6[var8 + var7 * areaWidth] = var9;
                        }
                        else {
                            var6[var8 + var7 * areaWidth] = BiomeGenBase.ˆá.ÇªÔ;
                        }
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = BiomeGenBase.ŠÂµà.ÇªÔ;
                    }
                }
                else if (var9 != BiomeGenBase.¥Æ.ÇªÔ && var9 != BiomeGenBase.Ñ¢Â.ÇªÔ && var9 != BiomeGenBase.á€.ÇªÔ) {
                    if (var10 != null && var10.áˆºÑ¢Õ()) {
                        this.HorizonCode_Horizon_È(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.Ç.ÇªÔ);
                    }
                    else if (var9 != BiomeGenBase.ÇŽá€.ÇªÔ && var9 != BiomeGenBase.Ï.ÇªÔ) {
                        if (var9 != BiomeGenBase.£à.ÇªÔ && var9 != BiomeGenBase.¥à.ÇªÔ && var9 != BiomeGenBase.Šáƒ.ÇªÔ && var9 != BiomeGenBase.Æ.ÇªÔ) {
                            final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                            final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                            final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                            final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                            if (!GenLayer.Â(var11) && !GenLayer.Â(var12) && !GenLayer.Â(var13) && !GenLayer.Â(var14)) {
                                var6[var8 + var7 * areaWidth] = var9;
                            }
                            else {
                                var6[var8 + var7 * areaWidth] = BiomeGenBase.ˆá.ÇªÔ;
                            }
                        }
                        else {
                            var6[var8 + var7 * areaWidth] = var9;
                        }
                    }
                    else {
                        final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                        final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                        final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                        final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                        if (!GenLayer.Â(var11) && !GenLayer.Â(var12) && !GenLayer.Â(var13) && !GenLayer.Â(var14)) {
                            if (this.Ø­áŒŠá(var11) && this.Ø­áŒŠá(var12) && this.Ø­áŒŠá(var13) && this.Ø­áŒŠá(var14)) {
                                var6[var8 + var7 * areaWidth] = var9;
                            }
                            else {
                                var6[var8 + var7 * areaWidth] = BiomeGenBase.ˆà.ÇªÔ;
                            }
                        }
                        else {
                            var6[var8 + var7 * areaWidth] = var9;
                        }
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.Âµà.ÇªÔ);
                }
            }
        }
        return var6;
    }
    
    private void HorizonCode_Horizon_È(final int[] p_151632_1_, final int[] p_151632_2_, final int p_151632_3_, final int p_151632_4_, final int p_151632_5_, final int p_151632_6_, final int p_151632_7_) {
        if (GenLayer.Â(p_151632_6_)) {
            p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
        }
        else {
            final int var8 = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
            final int var9 = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            final int var10 = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
            final int var11 = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
            if (!GenLayer.Â(var8) && !GenLayer.Â(var9) && !GenLayer.Â(var10) && !GenLayer.Â(var11)) {
                p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
            }
            else {
                p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_7_;
            }
        }
    }
    
    private boolean Ý(final int p_151631_1_) {
        return (BiomeGenBase.Âµá€(p_151631_1_) != null && BiomeGenBase.Âµá€(p_151631_1_).á() == BiomeGenJungle.class) || p_151631_1_ == BiomeGenBase.ŠÂµà.ÇªÔ || p_151631_1_ == BiomeGenBase.Õ.ÇªÔ || p_151631_1_ == BiomeGenBase.à¢.ÇªÔ || p_151631_1_ == BiomeGenBase.Ø­à.ÇªÔ || p_151631_1_ == BiomeGenBase.µÕ.ÇªÔ || GenLayer.Â(p_151631_1_);
    }
    
    private boolean Ø­áŒŠá(final int p_151633_1_) {
        return BiomeGenBase.Âµá€(p_151633_1_) instanceof BiomeGenMesa;
    }
}
