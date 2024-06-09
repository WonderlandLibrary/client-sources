package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerAddSnow extends GenLayer
{
    private static final String Ý = "CL_00000553";
    
    public GenLayerAddSnow(final long p_i2121_1_, final GenLayer p_i2121_3_) {
        super(p_i2121_1_);
        this.HorizonCode_Horizon_È = p_i2121_3_;
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int var5 = areaX - 1;
        final int var6 = areaY - 1;
        final int var7 = areaWidth + 2;
        final int var8 = areaHeight + 2;
        final int[] var9 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5, var6, var7, var8);
        final int[] var10 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var11 = 0; var11 < areaHeight; ++var11) {
            for (int var12 = 0; var12 < areaWidth; ++var12) {
                final int var13 = var9[var12 + 1 + (var11 + 1) * var7];
                this.HorizonCode_Horizon_È(var12 + areaX, (long)(var11 + areaY));
                if (var13 == 0) {
                    var10[var12 + var11 * areaWidth] = 0;
                }
                else {
                    final int var14 = this.HorizonCode_Horizon_È(6);
                    byte var15;
                    if (var14 == 0) {
                        var15 = 4;
                    }
                    else if (var14 <= 1) {
                        var15 = 3;
                    }
                    else {
                        var15 = 1;
                    }
                    var10[var12 + var11 * areaWidth] = var15;
                }
            }
        }
        return var10;
    }
}
