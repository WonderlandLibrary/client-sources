package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerRareBiome extends GenLayer
{
    private static final String Ý = "CL_00000562";
    
    public GenLayerRareBiome(final long p_i45478_1_, final GenLayer p_i45478_3_) {
        super(p_i45478_1_);
        this.HorizonCode_Horizon_È = p_i45478_3_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        final int[] var6 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var7 = 0; var7 < areaHeight; ++var7) {
            for (int var8 = 0; var8 < areaWidth; ++var8) {
                this.HorizonCode_Horizon_È(var8 + areaX, (long)(var7 + areaY));
                final int var9 = var5[var8 + 1 + (var7 + 1) * (areaWidth + 2)];
                if (this.HorizonCode_Horizon_È(57) == 0) {
                    if (var9 == BiomeGenBase.µà.ÇªÔ) {
                        var6[var8 + var7 * areaWidth] = BiomeGenBase.µà.ÇªÔ + 128;
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = var9;
                    }
                }
                else {
                    var6[var8 + var7 * areaWidth] = var9;
                }
            }
        }
        return var6;
    }
}
