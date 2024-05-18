package HORIZON-6-0-SKIDPROTECTION;

public class GenLayerBiome extends GenLayer
{
    private BiomeGenBase[] Ý;
    private BiomeGenBase[] Ø­áŒŠá;
    private BiomeGenBase[] Âµá€;
    private BiomeGenBase[] Ó;
    private final ChunkProviderSettings à;
    private static final String Ø = "CL_00000555";
    
    public GenLayerBiome(final long p_i45560_1_, final GenLayer p_i45560_3_, final WorldType p_i45560_4_, final String p_i45560_5_) {
        super(p_i45560_1_);
        this.Ý = new BiomeGenBase[] { BiomeGenBase.ˆà, BiomeGenBase.ˆà, BiomeGenBase.ˆà, BiomeGenBase.Ï­à, BiomeGenBase.Ï­à, BiomeGenBase.µà };
        this.Ø­áŒŠá = new BiomeGenBase[] { BiomeGenBase.Ø­à, BiomeGenBase.ˆáŠ, BiomeGenBase.¥Æ, BiomeGenBase.µà, BiomeGenBase.È, BiomeGenBase.Æ };
        this.Âµá€ = new BiomeGenBase[] { BiomeGenBase.Ø­à, BiomeGenBase.¥Æ, BiomeGenBase.µÕ, BiomeGenBase.µà };
        this.Ó = new BiomeGenBase[] { BiomeGenBase.ŒÏ, BiomeGenBase.ŒÏ, BiomeGenBase.ŒÏ, BiomeGenBase.áŒŠ };
        this.HorizonCode_Horizon_È = p_i45560_3_;
        if (p_i45560_4_ == WorldType.áŒŠÆ) {
            this.Ý = new BiomeGenBase[] { BiomeGenBase.ˆà, BiomeGenBase.Ø­à, BiomeGenBase.¥Æ, BiomeGenBase.Æ, BiomeGenBase.µà, BiomeGenBase.µÕ };
            this.à = null;
        }
        else if (p_i45560_4_ == WorldType.à) {
            this.à = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i45560_5_).Â();
        }
        else {
            this.à = null;
        }
    }
    
    @Override
    public int[] HorizonCode_Horizon_È(final int areaX, final int areaY, final int areaWidth, final int areaHeight) {
        final int[] var5 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(areaX, areaY, areaWidth, areaHeight);
        final int[] var6 = IntCache.HorizonCode_Horizon_È(areaWidth * areaHeight);
        for (int var7 = 0; var7 < areaHeight; ++var7) {
            for (int var8 = 0; var8 < areaWidth; ++var8) {
                this.HorizonCode_Horizon_È(var8 + areaX, (long)(var7 + areaY));
                int var9 = var5[var8 + var7 * areaWidth];
                final int var10 = (var9 & 0xF00) >> 8;
                var9 &= 0xFFFFF0FF;
                if (this.à != null && this.à.ˆá >= 0) {
                    var6[var8 + var7 * areaWidth] = this.à.ˆá;
                }
                else if (GenLayer.Â(var9)) {
                    var6[var8 + var7 * areaWidth] = var9;
                }
                else if (var9 == BiomeGenBase.Ê.ÇªÔ) {
                    var6[var8 + var7 * areaWidth] = var9;
                }
                else if (var9 == 1) {
                    if (var10 > 0) {
                        if (this.HorizonCode_Horizon_È(3) == 0) {
                            var6[var8 + var7 * areaWidth] = BiomeGenBase.Ô.ÇªÔ;
                        }
                        else {
                            var6[var8 + var7 * areaWidth] = BiomeGenBase.Ï.ÇªÔ;
                        }
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = this.Ý[this.HorizonCode_Horizon_È(this.Ý.length)].ÇªÔ;
                    }
                }
                else if (var9 == 2) {
                    if (var10 > 0) {
                        var6[var8 + var7 * areaWidth] = BiomeGenBase.Õ.ÇªÔ;
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = this.Ø­áŒŠá[this.HorizonCode_Horizon_È(this.Ø­áŒŠá.length)].ÇªÔ;
                    }
                }
                else if (var9 == 3) {
                    if (var10 > 0) {
                        var6[var8 + var7 * areaWidth] = BiomeGenBase.Ø­Âµ.ÇªÔ;
                    }
                    else {
                        var6[var8 + var7 * areaWidth] = this.Âµá€[this.HorizonCode_Horizon_È(this.Âµá€.length)].ÇªÔ;
                    }
                }
                else if (var9 == 4) {
                    var6[var8 + var7 * areaWidth] = this.Ó[this.HorizonCode_Horizon_È(this.Ó.length)].ÇªÔ;
                }
                else {
                    var6[var8 + var7 * areaWidth] = BiomeGenBase.Ê.ÇªÔ;
                }
            }
        }
        return var6;
    }
}
