package HORIZON-6-0-SKIDPROTECTION;

import java.util.concurrent.Callable;

public abstract class GenLayer
{
    private long Ý;
    protected GenLayer HorizonCode_Horizon_È;
    private long Ø­áŒŠá;
    protected long Â;
    private static final String Âµá€ = "CL_00000559";
    
    public static GenLayer[] HorizonCode_Horizon_È(final long p_180781_0_, final WorldType p_180781_2_, final String p_180781_3_) {
        final GenLayerIsland var4 = new GenLayerIsland(1L);
        final GenLayerFuzzyZoom var5 = new GenLayerFuzzyZoom(2000L, var4);
        GenLayerAddIsland var6 = new GenLayerAddIsland(1L, var5);
        GenLayerZoom var7 = new GenLayerZoom(2001L, var6);
        var6 = new GenLayerAddIsland(2L, var7);
        var6 = new GenLayerAddIsland(50L, var6);
        var6 = new GenLayerAddIsland(70L, var6);
        final GenLayerRemoveTooMuchOcean var8 = new GenLayerRemoveTooMuchOcean(2L, var6);
        final GenLayerAddSnow var9 = new GenLayerAddSnow(2L, var8);
        var6 = new GenLayerAddIsland(3L, var9);
        GenLayerEdge var10 = new GenLayerEdge(2L, var6, GenLayerEdge.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        var10 = new GenLayerEdge(2L, var10, GenLayerEdge.HorizonCode_Horizon_È.Â);
        var10 = new GenLayerEdge(3L, var10, GenLayerEdge.HorizonCode_Horizon_È.Ý);
        var7 = new GenLayerZoom(2002L, var10);
        var7 = new GenLayerZoom(2003L, var7);
        var6 = new GenLayerAddIsland(4L, var7);
        final GenLayerAddMushroomIsland var11 = new GenLayerAddMushroomIsland(5L, var6);
        final GenLayerDeepOcean var12 = new GenLayerDeepOcean(4L, var11);
        final GenLayer var13 = GenLayerZoom.HorizonCode_Horizon_È(1000L, var12, 0);
        ChunkProviderSettings var14 = null;
        int var16;
        int var15 = var16 = 4;
        if (p_180781_2_ == WorldType.à && p_180781_3_.length() > 0) {
            var14 = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180781_3_).Â();
            var15 = var14.ÇŽÕ;
            var16 = var14.É;
        }
        if (p_180781_2_ == WorldType.Âµá€) {
            var15 = 6;
        }
        GenLayer var17 = GenLayerZoom.HorizonCode_Horizon_È(1000L, var13, 0);
        final GenLayerRiverInit var18 = new GenLayerRiverInit(100L, var17);
        final GenLayerBiome var19 = new GenLayerBiome(200L, var13, p_180781_2_, p_180781_3_);
        final GenLayer var20 = GenLayerZoom.HorizonCode_Horizon_È(1000L, var19, 2);
        final GenLayerBiomeEdge var21 = new GenLayerBiomeEdge(1000L, var20);
        final GenLayer var22 = GenLayerZoom.HorizonCode_Horizon_È(1000L, var18, 2);
        final GenLayerHills var23 = new GenLayerHills(1000L, var21, var22);
        var17 = GenLayerZoom.HorizonCode_Horizon_È(1000L, var18, 2);
        var17 = GenLayerZoom.HorizonCode_Horizon_È(1000L, var17, var16);
        final GenLayerRiver var24 = new GenLayerRiver(1L, var17);
        final GenLayerSmooth var25 = new GenLayerSmooth(1000L, var24);
        Object var26 = new GenLayerRareBiome(1001L, var23);
        for (int var27 = 0; var27 < var15; ++var27) {
            var26 = new GenLayerZoom(1000 + var27, (GenLayer)var26);
            if (var27 == 0) {
                var26 = new GenLayerAddIsland(3L, (GenLayer)var26);
            }
            if (var27 == 1 || var15 == 1) {
                var26 = new GenLayerShore(1000L, (GenLayer)var26);
            }
        }
        final GenLayerSmooth var28 = new GenLayerSmooth(1000L, (GenLayer)var26);
        final GenLayerRiverMix var29 = new GenLayerRiverMix(100L, var28, var25);
        final GenLayerVoronoiZoom var30 = new GenLayerVoronoiZoom(10L, var29);
        var29.HorizonCode_Horizon_È(p_180781_0_);
        var30.HorizonCode_Horizon_È(p_180781_0_);
        return new GenLayer[] { var29, var30, var29 };
    }
    
    public GenLayer(final long p_i2125_1_) {
        this.Â = p_i2125_1_;
        this.Â *= this.Â * 6364136223846793005L + 1442695040888963407L;
        this.Â += p_i2125_1_;
        this.Â *= this.Â * 6364136223846793005L + 1442695040888963407L;
        this.Â += p_i2125_1_;
        this.Â *= this.Â * 6364136223846793005L + 1442695040888963407L;
        this.Â += p_i2125_1_;
    }
    
    public void HorizonCode_Horizon_È(final long p_75905_1_) {
        this.Ý = p_75905_1_;
        if (this.HorizonCode_Horizon_È != null) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_75905_1_);
        }
        this.Ý *= this.Ý * 6364136223846793005L + 1442695040888963407L;
        this.Ý += this.Â;
        this.Ý *= this.Ý * 6364136223846793005L + 1442695040888963407L;
        this.Ý += this.Â;
        this.Ý *= this.Ý * 6364136223846793005L + 1442695040888963407L;
        this.Ý += this.Â;
    }
    
    public void HorizonCode_Horizon_È(final long p_75903_1_, final long p_75903_3_) {
        this.Ø­áŒŠá = this.Ý;
        this.Ø­áŒŠá *= this.Ø­áŒŠá * 6364136223846793005L + 1442695040888963407L;
        this.Ø­áŒŠá += p_75903_1_;
        this.Ø­áŒŠá *= this.Ø­áŒŠá * 6364136223846793005L + 1442695040888963407L;
        this.Ø­áŒŠá += p_75903_3_;
        this.Ø­áŒŠá *= this.Ø­áŒŠá * 6364136223846793005L + 1442695040888963407L;
        this.Ø­áŒŠá += p_75903_1_;
        this.Ø­áŒŠá *= this.Ø­áŒŠá * 6364136223846793005L + 1442695040888963407L;
        this.Ø­áŒŠá += p_75903_3_;
    }
    
    protected int HorizonCode_Horizon_È(final int p_75902_1_) {
        int var2 = (int)((this.Ø­áŒŠá >> 24) % p_75902_1_);
        if (var2 < 0) {
            var2 += p_75902_1_;
        }
        this.Ø­áŒŠá *= this.Ø­áŒŠá * 6364136223846793005L + 1442695040888963407L;
        this.Ø­áŒŠá += this.Ý;
        return var2;
    }
    
    public abstract int[] HorizonCode_Horizon_È(final int p0, final int p1, final int p2, final int p3);
    
    protected static boolean HorizonCode_Horizon_È(final int biomeIDA, final int biomeIDB) {
        if (biomeIDA == biomeIDB) {
            return true;
        }
        if (biomeIDA != BiomeGenBase.Ï.ÇªÔ && biomeIDA != BiomeGenBase.Ô.ÇªÔ) {
            final BiomeGenBase var2 = BiomeGenBase.Âµá€(biomeIDA);
            final BiomeGenBase var3 = BiomeGenBase.Âµá€(biomeIDB);
            try {
                return var2 != null && var3 != null && var2.HorizonCode_Horizon_È(var3);
            }
            catch (Throwable var5) {
                final CrashReport var4 = CrashReport.HorizonCode_Horizon_È(var5, "Comparing biomes");
                final CrashReportCategory var6 = var4.HorizonCode_Horizon_È("Biomes being compared");
                var6.HorizonCode_Horizon_È("Biome A ID", biomeIDA);
                var6.HorizonCode_Horizon_È("Biome B ID", biomeIDB);
                var6.HorizonCode_Horizon_È("Biome A", new Callable() {
                    private static final String HorizonCode_Horizon_È = "CL_00000560";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(var2);
                    }
                });
                var6.HorizonCode_Horizon_È("Biome B", new Callable() {
                    private static final String HorizonCode_Horizon_È = "CL_00000561";
                    
                    public String HorizonCode_Horizon_È() {
                        return String.valueOf(var3);
                    }
                });
                throw new ReportedException(var4);
            }
        }
        return biomeIDB == BiomeGenBase.Ï.ÇªÔ || biomeIDB == BiomeGenBase.Ô.ÇªÔ;
    }
    
    protected static boolean Â(final int p_151618_0_) {
        return p_151618_0_ == BiomeGenBase.£à.ÇªÔ || p_151618_0_ == BiomeGenBase.¥à.ÇªÔ || p_151618_0_ == BiomeGenBase.ŠÄ.ÇªÔ;
    }
    
    protected int HorizonCode_Horizon_È(final int... p_151619_1_) {
        return p_151619_1_[this.HorizonCode_Horizon_È(p_151619_1_.length)];
    }
    
    protected int Â(final int p_151617_1_, final int p_151617_2_, final int p_151617_3_, final int p_151617_4_) {
        return (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_) ? p_151617_2_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_) ? p_151617_1_ : ((p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_) ? p_151617_1_ : ((p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_) ? p_151617_2_ : ((p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_) ? p_151617_2_ : ((p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_) ? p_151617_3_ : this.HorizonCode_Horizon_È(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ }))))))))));
    }
}
