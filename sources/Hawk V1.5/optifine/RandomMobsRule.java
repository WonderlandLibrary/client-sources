package optifine;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;

public class RandomMobsRule {
   private int[] weights = null;
   public int sumAllWeights = 1;
   private ResourceLocation[] resourceLocations = null;
   private ResourceLocation baseResLoc = null;
   private int[] skins = null;
   public int[] sumWeights = null;
   private RangeListInt heights = null;
   private BiomeGenBase[] biomes = null;

   public ResourceLocation getTextureLocation(ResourceLocation var1, int var2) {
      int var3 = 0;
      if (this.weights == null) {
         var3 = var2 % this.resourceLocations.length;
      } else {
         int var4 = var2 % this.sumAllWeights;

         for(int var5 = 0; var5 < this.sumWeights.length; ++var5) {
            if (this.sumWeights[var5] > var4) {
               var3 = var5;
               break;
            }
         }
      }

      return this.resourceLocations[var3];
   }

   public boolean matches(EntityLiving var1) {
      return !Matches.biome(var1.spawnBiome, this.biomes) ? false : (this.heights != null && var1.spawnPosition != null ? this.heights.isInRange(var1.spawnPosition.getY()) : true);
   }

   public boolean isValid(String var1) {
      this.resourceLocations = new ResourceLocation[this.skins.length];
      ResourceLocation var2 = RandomMobs.getMcpatcherLocation(this.baseResLoc);
      if (var2 == null) {
         Config.warn(String.valueOf((new StringBuilder("Invalid path: ")).append(this.baseResLoc.getResourcePath())));
         return false;
      } else {
         int var3;
         int var4;
         for(var3 = 0; var3 < this.resourceLocations.length; ++var3) {
            var4 = this.skins[var3];
            if (var4 <= 1) {
               this.resourceLocations[var3] = this.baseResLoc;
            } else {
               ResourceLocation var5 = RandomMobs.getLocationIndexed(var2, var4);
               if (var5 == null) {
                  Config.warn(String.valueOf((new StringBuilder("Invalid path: ")).append(this.baseResLoc.getResourcePath())));
                  return false;
               }

               if (!Config.hasResource(var5)) {
                  Config.warn(String.valueOf((new StringBuilder("Texture not found: ")).append(var5.getResourcePath())));
                  return false;
               }

               this.resourceLocations[var3] = var5;
            }
         }

         if (this.weights != null) {
            int[] var7;
            if (this.weights.length > this.resourceLocations.length) {
               Config.warn(String.valueOf((new StringBuilder("More weights defined than skins, trimming weights: ")).append(var1)));
               var7 = new int[this.resourceLocations.length];
               System.arraycopy(this.weights, 0, var7, 0, var7.length);
               this.weights = var7;
            }

            if (this.weights.length < this.resourceLocations.length) {
               Config.warn(String.valueOf((new StringBuilder("Less weights defined than skins, expanding weights: ")).append(var1)));
               var7 = new int[this.resourceLocations.length];
               System.arraycopy(this.weights, 0, var7, 0, this.weights.length);
               var4 = MathUtils.getAverage(this.weights);

               for(int var6 = this.weights.length; var6 < var7.length; ++var6) {
                  var7[var6] = var4;
               }

               this.weights = var7;
            }

            this.sumWeights = new int[this.weights.length];
            var3 = 0;

            for(var4 = 0; var4 < this.weights.length; ++var4) {
               if (this.weights[var4] < 0) {
                  Config.warn(String.valueOf((new StringBuilder("Invalid weight: ")).append(this.weights[var4])));
                  return false;
               }

               var3 += this.weights[var4];
               this.sumWeights[var4] = var3;
            }

            this.sumAllWeights = var3;
            if (this.sumAllWeights <= 0) {
               Config.warn(String.valueOf((new StringBuilder("Invalid sum of all weights: ")).append(var3)));
               this.sumAllWeights = 1;
            }
         }

         return true;
      }
   }

   public RandomMobsRule(ResourceLocation var1, int[] var2, int[] var3, BiomeGenBase[] var4, RangeListInt var5) {
      this.baseResLoc = var1;
      this.skins = var2;
      this.weights = var3;
      this.biomes = var4;
      this.heights = var5;
   }
}
