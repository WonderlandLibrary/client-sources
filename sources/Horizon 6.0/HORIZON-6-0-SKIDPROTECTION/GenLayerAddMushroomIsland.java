package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerAddMushroomIsland extends GenLayer
{
    private static final String Ý = "CL_00000552";
    
    public GenLayerAddMushroomIsland(final long p_i2120_1_, final GenLayer p_i2120_3_) {
        super(p_i2120_1_);
        this.HorizonCode_Horizon_È = p_i2120_3_;
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
                final int var13 = var9[var12 + 0 + (var11 + 0) * var7];
                final int var14 = var9[var12 + 2 + (var11 + 0) * var7];
                final int var15 = var9[var12 + 0 + (var11 + 2) * var7];
                final int var16 = var9[var12 + 2 + (var11 + 2) * var7];
                final int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                this.HorizonCode_Horizon_È(var12 + areaX, (long)(var11 + areaY));
                if (var17 == 0 && var13 == 0 && var14 == 0 && var15 == 0 && var16 == 0 && this.HorizonCode_Horizon_È(100) == 0) {
                    var10[var12 + var11 * areaWidth] = BiomeGenBase.Ê.ÇªÔ;
                }
                else {
                    var10[var12 + var11 * areaWidth] = var17;
                }
            }
        }
        return var10;
    }
}
