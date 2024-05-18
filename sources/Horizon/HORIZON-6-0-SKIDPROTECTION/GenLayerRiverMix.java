package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerRiverMix extends GenLayer
{
    private GenLayer Ý;
    private GenLayer Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000567";
    
    public GenLayerRiverMix(final long p_i2129_1_, final GenLayer p_i2129_3_, final GenLayer p_i2129_4_) {
        super(p_i2129_1_);
        this.Ý = p_i2129_3_;
        this.Ø­áŒŠá = p_i2129_4_;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final long p_75905_1_) {
        this.Ý.HorizonCode_Horizon_È(p_75905_1_);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(p_75905_1_);
        super.HorizonCode_Horizon_È(p_75905_1_);
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.Ý.HorizonCode_Horizon_È(areaX, areaY, areaWidth, areaHeight);
        final int[] var6 = this.Ø­áŒŠá.HorizonCode_Horizon_È(areaX, areaY, areaWidth, areaHeight);
        final int[] var7 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var8 = 0; var8 < areaWidth * areaHeight; ++var8) {
            if (var5[var8] != BiomeGenBase.£à.ÇªÔ && var5[var8] != BiomeGenBase.¥à.ÇªÔ) {
                if (var6[var8] == BiomeGenBase.Šáƒ.ÇªÔ) {
                    if (var5[var8] == BiomeGenBase.ŒÏ.ÇªÔ) {
                        var7[var8] = BiomeGenBase.Ñ¢á.ÇªÔ;
                    }
                    else if (var5[var8] != BiomeGenBase.Ê.ÇªÔ && var5[var8] != BiomeGenBase.ÇŽÉ.ÇªÔ) {
                        var7[var8] = (var6[var8] & 0xFF);
                    }
                    else {
                        var7[var8] = BiomeGenBase.ÇŽÉ.ÇªÔ;
                    }
                }
                else {
                    var7[var8] = var5[var8];
                }
            }
            else {
                var7[var8] = var5[var8];
            }
        }
        return var7;
    }
}
