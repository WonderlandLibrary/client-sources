package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerIsland extends GenLayer
{
    private static final String Ý = "CL_00000558";
    
    public GenLayerIsland(final long p_i2124_1_) {
        super(p_i2124_1_);
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var6 = 0; var6 < areaHeight; ++var6) {
            for (int var7 = 0; var7 < areaWidth; ++var7) {
                this.HorizonCode_Horizon_È(areaX + var7, (long)(areaY + var6));
                var5[var7 + var6 * areaWidth] = ((this.HorizonCode_Horizon_È(10) == 0) ? 1 : 0);
            }
        }
        if (areaX > -areaWidth && areaX <= 0 && areaY > -areaHeight && areaY <= 0) {
            var5[-areaX + -areaY * areaWidth] = 1;
        }
        return var5;
    }
}
