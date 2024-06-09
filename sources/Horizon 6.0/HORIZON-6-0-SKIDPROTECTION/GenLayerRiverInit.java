package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerRiverInit extends GenLayer
{
    private static final String Ý = "CL_00000565";
    
    public GenLayerRiverInit(final long p_i2127_1_, final GenLayer p_i2127_3_) {
        super(p_i2127_1_);
        this.HorizonCode_Horizon_È = p_i2127_3_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(areaX, areaY, areaWidth, areaHeight);
        final int[] var6 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var7 = 0; var7 < areaHeight; ++var7) {
            for (int var8 = 0; var8 < areaWidth; ++var8) {
                this.HorizonCode_Horizon_È(var8 + areaX, (long)(var7 + areaY));
                var6[var8 + var7 * areaWidth] = ((var5[var8 + var7 * areaWidth] > 0) ? (this.HorizonCode_Horizon_È(299999) + 2) : 0);
            }
        }
        return var6;
    }
}
