package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenMesa;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerShore extends GenLayer {
   public GenLayerShore(long var1, GenLayer var3) {
      super(var1);
      this.parent = var3;
   }

   public int[] getInts(int var1, int var2, int var3, int var4) {
      int[] var5 = this.parent.getInts(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
      int[] var6 = IntCache.getIntCache(var3 * var4);

      for(int var7 = 0; var7 < var4; ++var7) {
         for(int var8 = 0; var8 < var3; ++var8) {
            this.initChunkSeed((long)(var8 + var1), (long)(var7 + var2));
            int var9 = var5[var8 + 1 + (var7 + 1) * (var3 + 2)];
            BiomeGenBase var10 = BiomeGenBase.getBiome(var9);
            if(var9 == BiomeGenBase.mushroomIsland.biomeID) {
               int var17 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
               int var20 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
               int var23 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
               int var26 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
               if(var17 != BiomeGenBase.ocean.biomeID && var20 != BiomeGenBase.ocean.biomeID && var23 != BiomeGenBase.ocean.biomeID && var26 != BiomeGenBase.ocean.biomeID) {
                  var6[var8 + var7 * var3] = var9;
               } else {
                  var6[var8 + var7 * var3] = BiomeGenBase.mushroomIslandShore.biomeID;
               }
            } else if(var10.getBiomeClass() == BiomeGenJungle.class) {
               int var16 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
               int var19 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
               int var22 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
               int var25 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
               if(this.func_151631_c(var16) && this.func_151631_c(var19) && this.func_151631_c(var22) && this.func_151631_c(var25)) {
                  if(!isBiomeOceanic(var16) && !isBiomeOceanic(var19) && !isBiomeOceanic(var22) && !isBiomeOceanic(var25)) {
                     var6[var8 + var7 * var3] = var9;
                  } else {
                     var6[var8 + var7 * var3] = BiomeGenBase.beach.biomeID;
                  }
               } else {
                  var6[var8 + var7 * var3] = BiomeGenBase.jungleEdge.biomeID;
               }
            } else if(var9 != BiomeGenBase.extremeHills.biomeID && var9 != BiomeGenBase.extremeHillsPlus.biomeID && var9 != BiomeGenBase.extremeHillsEdge.biomeID) {
               if(var10.isSnowyBiome()) {
                  this.func_151632_a(var5, var6, var8, var7, var3, var9, BiomeGenBase.coldBeach.biomeID);
               } else if(var9 != BiomeGenBase.mesa.biomeID && var9 != BiomeGenBase.mesaPlateau_F.biomeID) {
                  if(var9 != BiomeGenBase.ocean.biomeID && var9 != BiomeGenBase.deepOcean.biomeID && var9 != BiomeGenBase.river.biomeID && var9 != BiomeGenBase.swampland.biomeID) {
                     int var15 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
                     int var18 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
                     int var21 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
                     int var24 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
                     if(!isBiomeOceanic(var15) && !isBiomeOceanic(var18) && !isBiomeOceanic(var21) && !isBiomeOceanic(var24)) {
                        var6[var8 + var7 * var3] = var9;
                     } else {
                        var6[var8 + var7 * var3] = BiomeGenBase.beach.biomeID;
                     }
                  } else {
                     var6[var8 + var7 * var3] = var9;
                  }
               } else {
                  int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
                  int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
                  int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
                  int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
                  if(!isBiomeOceanic(var11) && !isBiomeOceanic(var12) && !isBiomeOceanic(var13) && !isBiomeOceanic(var14)) {
                     if(this.func_151633_d(var11) && this.func_151633_d(var12) && this.func_151633_d(var13) && this.func_151633_d(var14)) {
                        var6[var8 + var7 * var3] = var9;
                     } else {
                        var6[var8 + var7 * var3] = BiomeGenBase.desert.biomeID;
                     }
                  } else {
                     var6[var8 + var7 * var3] = var9;
                  }
               }
            } else {
               this.func_151632_a(var5, var6, var8, var7, var3, var9, BiomeGenBase.stoneBeach.biomeID);
            }
         }
      }

      return var6;
   }

   private void func_151632_a(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7) {
      if(isBiomeOceanic(var6)) {
         var2[var3 + var4 * var5] = var6;
      } else {
         int var8 = var1[var3 + 1 + (var4 + 1 - 1) * (var5 + 2)];
         int var9 = var1[var3 + 1 + 1 + (var4 + 1) * (var5 + 2)];
         int var10 = var1[var3 + 1 - 1 + (var4 + 1) * (var5 + 2)];
         int var11 = var1[var3 + 1 + (var4 + 1 + 1) * (var5 + 2)];
         if(!isBiomeOceanic(var8) && !isBiomeOceanic(var9) && !isBiomeOceanic(var10) && !isBiomeOceanic(var11)) {
            var2[var3 + var4 * var5] = var6;
         } else {
            var2[var3 + var4 * var5] = var7;
         }
      }

   }

   private boolean func_151631_c(int var1) {
      return BiomeGenBase.getBiome(var1) != null && BiomeGenBase.getBiome(var1).getBiomeClass() == BiomeGenJungle.class || var1 == BiomeGenBase.jungleEdge.biomeID || var1 == BiomeGenBase.jungle.biomeID || var1 == BiomeGenBase.jungleHills.biomeID || var1 == BiomeGenBase.forest.biomeID || var1 == BiomeGenBase.taiga.biomeID || isBiomeOceanic(var1);
   }

   private boolean func_151633_d(int var1) {
      return BiomeGenBase.getBiome(var1) instanceof BiomeGenMesa;
   }
}
