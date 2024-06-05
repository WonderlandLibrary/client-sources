package net.minecraft.world.gen.layer;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings;

public abstract class GenLayer {
   protected long baseSeed;
   private static final String __OBFID = "CL_00000559";
   private long worldGenSeed;
   protected GenLayer parent;
   private long chunkSeed;

   protected static boolean isBiomeOceanic(int var0) {
      return var0 == BiomeGenBase.ocean.biomeID || var0 == BiomeGenBase.deepOcean.biomeID || var0 == BiomeGenBase.frozenOcean.biomeID;
   }

   public abstract int[] getInts(int var1, int var2, int var3, int var4);

   public static GenLayer[] func_180781_a(long var0, WorldType var2, String var3) {
      GenLayerIsland var4 = new GenLayerIsland(1L);
      GenLayerFuzzyZoom var5 = new GenLayerFuzzyZoom(2000L, var4);
      GenLayerAddIsland var6 = new GenLayerAddIsland(1L, var5);
      GenLayerZoom var7 = new GenLayerZoom(2001L, var6);
      var6 = new GenLayerAddIsland(2L, var7);
      var6 = new GenLayerAddIsland(50L, var6);
      var6 = new GenLayerAddIsland(70L, var6);
      GenLayerRemoveTooMuchOcean var8 = new GenLayerRemoveTooMuchOcean(2L, var6);
      GenLayerAddSnow var9 = new GenLayerAddSnow(2L, var8);
      var6 = new GenLayerAddIsland(3L, var9);
      GenLayerEdge var10 = new GenLayerEdge(2L, var6, GenLayerEdge.Mode.COOL_WARM);
      var10 = new GenLayerEdge(2L, var10, GenLayerEdge.Mode.HEAT_ICE);
      var10 = new GenLayerEdge(3L, var10, GenLayerEdge.Mode.SPECIAL);
      var7 = new GenLayerZoom(2002L, var10);
      var7 = new GenLayerZoom(2003L, var7);
      var6 = new GenLayerAddIsland(4L, var7);
      GenLayerAddMushroomIsland var11 = new GenLayerAddMushroomIsland(5L, var6);
      GenLayerDeepOcean var12 = new GenLayerDeepOcean(4L, var11);
      GenLayer var13 = GenLayerZoom.magnify(1000L, var12, 0);
      ChunkProviderSettings var14 = null;
      int var15 = 4;
      int var16 = var15;
      if (var2 == WorldType.CUSTOMIZED && var3.length() > 0) {
         var14 = ChunkProviderSettings.Factory.func_177865_a(var3).func_177864_b();
         var15 = var14.field_177780_G;
         var16 = var14.field_177788_H;
      }

      if (var2 == WorldType.LARGE_BIOMES) {
         var15 = 6;
      }

      GenLayer var17 = GenLayerZoom.magnify(1000L, var13, 0);
      GenLayerRiverInit var18 = new GenLayerRiverInit(100L, var17);
      GenLayerBiome var19 = new GenLayerBiome(200L, var13, var2, var3);
      GenLayer var20 = GenLayerZoom.magnify(1000L, var19, 2);
      GenLayerBiomeEdge var21 = new GenLayerBiomeEdge(1000L, var20);
      GenLayer var22 = GenLayerZoom.magnify(1000L, var18, 2);
      GenLayerHills var23 = new GenLayerHills(1000L, var21, var22);
      var17 = GenLayerZoom.magnify(1000L, var18, 2);
      var17 = GenLayerZoom.magnify(1000L, var17, var16);
      GenLayerRiver var24 = new GenLayerRiver(1L, var17);
      GenLayerSmooth var25 = new GenLayerSmooth(1000L, var24);
      Object var26 = new GenLayerRareBiome(1001L, var23);

      for(int var27 = 0; var27 < var15; ++var27) {
         var26 = new GenLayerZoom((long)(1000 + var27), (GenLayer)var26);
         if (var27 == 0) {
            var26 = new GenLayerAddIsland(3L, (GenLayer)var26);
         }

         if (var27 == 1 || var15 == 1) {
            var26 = new GenLayerShore(1000L, (GenLayer)var26);
         }
      }

      GenLayerSmooth var30 = new GenLayerSmooth(1000L, (GenLayer)var26);
      GenLayerRiverMix var28 = new GenLayerRiverMix(100L, var30, var25);
      GenLayerVoronoiZoom var29 = new GenLayerVoronoiZoom(10L, var28);
      var28.initWorldGenSeed(var0);
      var29.initWorldGenSeed(var0);
      return new GenLayer[]{var28, var29, var28};
   }

   protected static boolean biomesEqualOrMesaPlateau(int var0, int var1) {
      if (var0 == var1) {
         return true;
      } else if (var0 != BiomeGenBase.mesaPlateau_F.biomeID && var0 != BiomeGenBase.mesaPlateau.biomeID) {
         BiomeGenBase var2 = BiomeGenBase.getBiome(var0);
         BiomeGenBase var3 = BiomeGenBase.getBiome(var1);

         try {
            return var2 != null && var3 != null ? var2.isEqualTo(var3) : false;
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.makeCrashReport(var7, "Comparing biomes");
            CrashReportCategory var6 = var5.makeCategory("Biomes being compared");
            var6.addCrashSection("Biome A ID", var0);
            var6.addCrashSection("Biome B ID", var1);
            var6.addCrashSectionCallable("Biome A", new Callable(var2) {
               private static final String __OBFID = "CL_00000560";
               private final BiomeGenBase val$var2;

               public Object call() throws Exception {
                  return this.call();
               }

               public String call() {
                  return String.valueOf(this.val$var2);
               }

               {
                  this.val$var2 = var1;
               }
            });
            var6.addCrashSectionCallable("Biome B", new Callable(var3) {
               private final BiomeGenBase val$var3;
               private static final String __OBFID = "CL_00000561";

               {
                  this.val$var3 = var1;
               }

               public String call() {
                  return String.valueOf(this.val$var3);
               }

               public Object call() throws Exception {
                  return this.call();
               }
            });
            throw new ReportedException(var5);
         }
      } else {
         return var1 == BiomeGenBase.mesaPlateau_F.biomeID || var1 == BiomeGenBase.mesaPlateau.biomeID;
      }
   }

   public void initChunkSeed(long var1, long var3) {
      this.chunkSeed = this.worldGenSeed;
      this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
      this.chunkSeed += var1;
      this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
      this.chunkSeed += var3;
      this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
      this.chunkSeed += var1;
      this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
      this.chunkSeed += var3;
   }

   public void initWorldGenSeed(long var1) {
      this.worldGenSeed = var1;
      if (this.parent != null) {
         this.parent.initWorldGenSeed(var1);
      }

      this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
      this.worldGenSeed += this.baseSeed;
      this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
      this.worldGenSeed += this.baseSeed;
      this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
      this.worldGenSeed += this.baseSeed;
   }

   protected int selectRandom(int... var1) {
      return var1[this.nextInt(var1.length)];
   }

   public GenLayer(long var1) {
      this.baseSeed = var1;
      this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
      this.baseSeed += var1;
      this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
      this.baseSeed += var1;
      this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
      this.baseSeed += var1;
   }

   protected int nextInt(int var1) {
      int var2 = (int)((this.chunkSeed >> 24) % (long)var1);
      if (var2 < 0) {
         var2 += var1;
      }

      this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
      this.chunkSeed += this.worldGenSeed;
      return var2;
   }

   protected int selectModeOrRandom(int var1, int var2, int var3, int var4) {
      return var2 == var3 && var3 == var4 ? var2 : (var1 == var2 && var1 == var3 ? var1 : (var1 == var2 && var1 == var4 ? var1 : (var1 == var3 && var1 == var4 ? var1 : (var1 == var2 && var3 != var4 ? var1 : (var1 == var3 && var2 != var4 ? var1 : (var1 == var4 && var2 != var3 ? var1 : (var2 == var3 && var1 != var4 ? var2 : (var2 == var4 && var1 != var3 ? var2 : (var3 == var4 && var1 != var2 ? var3 : this.selectRandom(var1, var2, var3, var4))))))))));
   }
}
