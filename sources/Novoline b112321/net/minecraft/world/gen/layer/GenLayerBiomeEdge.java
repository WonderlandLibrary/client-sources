package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase$TempCategory;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeEdge extends GenLayer {
   public GenLayerBiomeEdge(long var1, GenLayer var3) {
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
            if(!this.replaceBiomeEdgeIfNecessary(var5, var6, var8, var7, var3, var9, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) && !this.replaceBiomeEdge(var5, var6, var8, var7, var3, var9, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID) && !this.replaceBiomeEdge(var5, var6, var8, var7, var3, var9, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID) && !this.replaceBiomeEdge(var5, var6, var8, var7, var3, var9, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID)) {
               if(var9 == BiomeGenBase.desert.biomeID) {
                  int var14 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
                  int var15 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
                  int var16 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
                  int var17 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
                  if(var14 != BiomeGenBase.icePlains.biomeID && var15 != BiomeGenBase.icePlains.biomeID && var16 != BiomeGenBase.icePlains.biomeID && var17 != BiomeGenBase.icePlains.biomeID) {
                     var6[var8 + var7 * var3] = var9;
                  } else {
                     var6[var8 + var7 * var3] = BiomeGenBase.extremeHillsPlus.biomeID;
                  }
               } else if(var9 == BiomeGenBase.swampland.biomeID) {
                  int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (var3 + 2)];
                  int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (var3 + 2)];
                  int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (var3 + 2)];
                  int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (var3 + 2)];
                  if(var10 != BiomeGenBase.desert.biomeID && var11 != BiomeGenBase.desert.biomeID && var12 != BiomeGenBase.desert.biomeID && var13 != BiomeGenBase.desert.biomeID && var10 != BiomeGenBase.coldTaiga.biomeID && var11 != BiomeGenBase.coldTaiga.biomeID && var12 != BiomeGenBase.coldTaiga.biomeID && var13 != BiomeGenBase.coldTaiga.biomeID && var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID) {
                     if(var10 != BiomeGenBase.jungle.biomeID && var13 != BiomeGenBase.jungle.biomeID && var11 != BiomeGenBase.jungle.biomeID && var12 != BiomeGenBase.jungle.biomeID) {
                        var6[var8 + var7 * var3] = var9;
                     } else {
                        var6[var8 + var7 * var3] = BiomeGenBase.jungleEdge.biomeID;
                     }
                  } else {
                     var6[var8 + var7 * var3] = BiomeGenBase.plains.biomeID;
                  }
               } else {
                  var6[var8 + var7 * var3] = var9;
               }
            }
         }
      }

      return var6;
   }

   private boolean replaceBiomeEdgeIfNecessary(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if(!biomesEqualOrMesaPlateau(var6, var7)) {
         return false;
      } else {
         int var9 = var1[var3 + 1 + (var4 + 1 - 1) * (var5 + 2)];
         int var10 = var1[var3 + 1 + 1 + (var4 + 1) * (var5 + 2)];
         int var11 = var1[var3 + 1 - 1 + (var4 + 1) * (var5 + 2)];
         int var12 = var1[var3 + 1 + (var4 + 1 + 1) * (var5 + 2)];
         if(this.canBiomesBeNeighbors(var9, var7) && this.canBiomesBeNeighbors(var10, var7) && this.canBiomesBeNeighbors(var11, var7) && this.canBiomesBeNeighbors(var12, var7)) {
            var2[var3 + var4 * var5] = var6;
         } else {
            var2[var3 + var4 * var5] = var8;
         }

         return true;
      }
   }

   private boolean replaceBiomeEdge(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if(var6 != var7) {
         return false;
      } else {
         int var9 = var1[var3 + 1 + (var4 + 1 - 1) * (var5 + 2)];
         int var10 = var1[var3 + 1 + 1 + (var4 + 1) * (var5 + 2)];
         int var11 = var1[var3 + 1 - 1 + (var4 + 1) * (var5 + 2)];
         int var12 = var1[var3 + 1 + (var4 + 1 + 1) * (var5 + 2)];
         if(biomesEqualOrMesaPlateau(var9, var7) && biomesEqualOrMesaPlateau(var10, var7) && biomesEqualOrMesaPlateau(var11, var7) && biomesEqualOrMesaPlateau(var12, var7)) {
            var2[var3 + var4 * var5] = var6;
         } else {
            var2[var3 + var4 * var5] = var8;
         }

         return true;
      }
   }

   private boolean canBiomesBeNeighbors(int var1, int var2) {
      if(biomesEqualOrMesaPlateau(var1, var2)) {
         return true;
      } else {
         BiomeGenBase var3 = BiomeGenBase.getBiome(var1);
         BiomeGenBase var4 = BiomeGenBase.getBiome(var2);
         BiomeGenBase$TempCategory var5 = var3.getTempCategory();
         BiomeGenBase$TempCategory var6 = var4.getTempCategory();
         return var5 == var6 || var5 == BiomeGenBase$TempCategory.MEDIUM || var6 == BiomeGenBase$TempCategory.MEDIUM;
      }
   }
}
