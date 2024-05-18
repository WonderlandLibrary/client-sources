/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRareBiome;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayer {
    private long worldGenSeed;
    protected GenLayer parent;
    private long chunkSeed;
    protected long baseSeed;
    private static final String __OBFID = "CL_00000559";

    public static GenLayer[] func_180781_a(long p_180781_0_, WorldType p_180781_2_, String p_180781_3_) {
        int var6;
        GenLayerIsland var4 = new GenLayerIsland(1);
        GenLayerFuzzyZoom var13 = new GenLayerFuzzyZoom(2000, var4);
        GenLayerAddIsland var14 = new GenLayerAddIsland(1, var13);
        GenLayerZoom var15 = new GenLayerZoom(2001, var14);
        var14 = new GenLayerAddIsland(2, var15);
        var14 = new GenLayerAddIsland(50, var14);
        var14 = new GenLayerAddIsland(70, var14);
        GenLayerRemoveTooMuchOcean var16 = new GenLayerRemoveTooMuchOcean(2, var14);
        GenLayerAddSnow var17 = new GenLayerAddSnow(2, var16);
        var14 = new GenLayerAddIsland(3, var17);
        GenLayerEdge var18 = new GenLayerEdge(2, var14, GenLayerEdge.Mode.COOL_WARM);
        var18 = new GenLayerEdge(2, var18, GenLayerEdge.Mode.HEAT_ICE);
        var18 = new GenLayerEdge(3, var18, GenLayerEdge.Mode.SPECIAL);
        var15 = new GenLayerZoom(2002, var18);
        var15 = new GenLayerZoom(2003, var15);
        var14 = new GenLayerAddIsland(4, var15);
        GenLayerAddMushroomIsland var19 = new GenLayerAddMushroomIsland(5, var14);
        GenLayerDeepOcean var20 = new GenLayerDeepOcean(4, var19);
        GenLayer var21 = GenLayerZoom.magnify(1000, var20, 0);
        ChunkProviderSettings var5 = null;
        int var7 = var6 = 4;
        if (p_180781_2_ == WorldType.CUSTOMIZED && p_180781_3_.length() > 0) {
            var5 = ChunkProviderSettings.Factory.func_177865_a(p_180781_3_).func_177864_b();
            var6 = var5.field_177780_G;
            var7 = var5.field_177788_H;
        }
        if (p_180781_2_ == WorldType.LARGE_BIOMES) {
            var6 = 6;
        }
        GenLayer var8 = GenLayerZoom.magnify(1000, var21, 0);
        GenLayerRiverInit var22 = new GenLayerRiverInit(100, var8);
        GenLayerBiome var9 = new GenLayerBiome(200, var21, p_180781_2_, p_180781_3_);
        GenLayer var25 = GenLayerZoom.magnify(1000, var9, 2);
        GenLayerBiomeEdge var26 = new GenLayerBiomeEdge(1000, var25);
        GenLayer var10 = GenLayerZoom.magnify(1000, var22, 2);
        GenLayerHills var27 = new GenLayerHills(1000, var26, var10);
        var8 = GenLayerZoom.magnify(1000, var22, 2);
        var8 = GenLayerZoom.magnify(1000, var8, var7);
        GenLayerRiver var23 = new GenLayerRiver(1, var8);
        GenLayerSmooth var24 = new GenLayerSmooth(1000, var23);
        GenLayer var28 = new GenLayerRareBiome(1001, var27);
        int var11 = 0;
        while (var11 < var6) {
            var28 = new GenLayerZoom(1000 + var11, var28);
            if (var11 == 0) {
                var28 = new GenLayerAddIsland(3, var28);
            }
            if (var11 == 1 || var6 == 1) {
                var28 = new GenLayerShore(1000, var28);
            }
            ++var11;
        }
        GenLayerSmooth var29 = new GenLayerSmooth(1000, var28);
        GenLayerRiverMix var30 = new GenLayerRiverMix(100, var29, var24);
        GenLayerVoronoiZoom var12 = new GenLayerVoronoiZoom(10, var30);
        var30.initWorldGenSeed(p_180781_0_);
        var12.initWorldGenSeed(p_180781_0_);
        return new GenLayer[]{var30, var12, var30};
    }

    public GenLayer(long p_i2125_1_) {
        this.baseSeed = p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += p_i2125_1_;
    }

    public void initWorldGenSeed(long p_75905_1_) {
        this.worldGenSeed = p_75905_1_;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(p_75905_1_);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }

    public void initChunkSeed(long p_75903_1_, long p_75903_3_) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_1_;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += p_75903_3_;
    }

    protected int nextInt(int p_75902_1_) {
        int var2 = (int)((this.chunkSeed >> 24) % (long)p_75902_1_);
        if (var2 < 0) {
            var2 += p_75902_1_;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }

    public abstract int[] getInts(int var1, int var2, int var3, int var4);

    protected static boolean biomesEqualOrMesaPlateau(int biomeIDA, int biomeIDB) {
        if (biomeIDA == biomeIDB) {
            return true;
        }
        if (biomeIDA != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDA != BiomeGenBase.mesaPlateau.biomeID) {
            BiomeGenBase var2 = BiomeGenBase.getBiome(biomeIDA);
            BiomeGenBase var3 = BiomeGenBase.getBiome(biomeIDB);
            try {
                return var2 != null && var3 != null ? var2.isEqualTo(var3) : false;
            }
            catch (Throwable var7) {
                CrashReport var5 = CrashReport.makeCrashReport(var7, "Comparing biomes");
                CrashReportCategory var6 = var5.makeCategory("Biomes being compared");
                var6.addCrashSection("Biome A ID", biomeIDA);
                var6.addCrashSection("Biome B ID", biomeIDB);
                var6.addCrashSectionCallable("Biome A", new Callable(){
                    private static final String __OBFID = "CL_00000560";

                    public String call() {
                        return String.valueOf(BiomeGenBase.this);
                    }
                });
                var6.addCrashSectionCallable("Biome B", new Callable(){
                    private static final String __OBFID = "CL_00000561";

                    public String call() {
                        return String.valueOf(BiomeGenBase.this);
                    }
                });
                throw new ReportedException(var5);
            }
        }
        if (biomeIDB != BiomeGenBase.mesaPlateau_F.biomeID && biomeIDB != BiomeGenBase.mesaPlateau.biomeID) {
            return false;
        }
        return true;
    }

    protected static boolean isBiomeOceanic(int p_151618_0_) {
        if (p_151618_0_ != BiomeGenBase.ocean.biomeID && p_151618_0_ != BiomeGenBase.deepOcean.biomeID && p_151618_0_ != BiomeGenBase.frozenOcean.biomeID) {
            return false;
        }
        return true;
    }

    protected /* varargs */ int selectRandom(int ... p_151619_1_) {
        return p_151619_1_[this.nextInt(p_151619_1_.length)];
    }

    protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
        return p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_ ? p_151617_2_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_ ? p_151617_1_ : (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_ ? p_151617_2_ : (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_ ? p_151617_2_ : (p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.selectRandom(p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_))))))))));
    }

}

