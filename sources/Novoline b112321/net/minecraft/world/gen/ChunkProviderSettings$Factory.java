package net.minecraft.world.gen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.qd;
import net.minecraft.world.gen.ChunkProviderSettings;

public class ChunkProviderSettings$Factory {
   static final Gson JSON_ADAPTER = (new GsonBuilder()).registerTypeAdapter(ChunkProviderSettings$Factory.class, new qd()).create();
   public float coordinateScale = 684.412F;
   public float heightScale = 684.412F;
   public float upperLimitScale = 512.0F;
   public float lowerLimitScale = 512.0F;
   public float depthNoiseScaleX = 200.0F;
   public float depthNoiseScaleZ = 200.0F;
   public float depthNoiseScaleExponent = 0.5F;
   public float mainNoiseScaleX = 80.0F;
   public float mainNoiseScaleY = 160.0F;
   public float mainNoiseScaleZ = 80.0F;
   public float baseSize = 8.5F;
   public float stretchY = 12.0F;
   public float biomeDepthWeight = 1.0F;
   public float biomeDepthOffset = 0.0F;
   public float biomeScaleWeight = 1.0F;
   public float biomeScaleOffset = 0.0F;
   public int seaLevel = 63;
   public boolean useCaves = true;
   public boolean useDungeons = true;
   public int dungeonChance = 8;
   public boolean useStrongholds = true;
   public boolean useVillages = true;
   public boolean useMineShafts = true;
   public boolean useTemples = true;
   public boolean useMonuments = true;
   public boolean useRavines = true;
   public boolean useWaterLakes = true;
   public int waterLakeChance = 4;
   public boolean useLavaLakes = true;
   public int lavaLakeChance = 80;
   public boolean useLavaOceans = false;
   public int fixedBiome = -1;
   public int biomeSize = 4;
   public int riverSize = 4;
   public int dirtSize = 33;
   public int dirtCount = 10;
   public int dirtMinHeight = 0;
   public int dirtMaxHeight = 256;
   public int gravelSize = 33;
   public int gravelCount = 8;
   public int gravelMinHeight = 0;
   public int gravelMaxHeight = 256;
   public int graniteSize = 33;
   public int graniteCount = 10;
   public int graniteMinHeight = 0;
   public int graniteMaxHeight = 80;
   public int dioriteSize = 33;
   public int dioriteCount = 10;
   public int dioriteMinHeight = 0;
   public int dioriteMaxHeight = 80;
   public int andesiteSize = 33;
   public int andesiteCount = 10;
   public int andesiteMinHeight = 0;
   public int andesiteMaxHeight = 80;
   public int coalSize = 17;
   public int coalCount = 20;
   public int coalMinHeight = 0;
   public int coalMaxHeight = 128;
   public int ironSize = 9;
   public int ironCount = 20;
   public int ironMinHeight = 0;
   public int ironMaxHeight = 64;
   public int goldSize = 9;
   public int goldCount = 2;
   public int goldMinHeight = 0;
   public int goldMaxHeight = 32;
   public int redstoneSize = 8;
   public int redstoneCount = 8;
   public int redstoneMinHeight = 0;
   public int redstoneMaxHeight = 16;
   public int diamondSize = 8;
   public int diamondCount = 1;
   public int diamondMinHeight = 0;
   public int diamondMaxHeight = 16;
   public int lapisSize = 7;
   public int lapisCount = 1;
   public int lapisCenterHeight = 16;
   public int lapisSpread = 16;

   public static ChunkProviderSettings$Factory jsonToFactory(String var0) {
      if(var0.isEmpty()) {
         return new ChunkProviderSettings$Factory();
      } else {
         try {
            return (ChunkProviderSettings$Factory)JSON_ADAPTER.fromJson(var0, ChunkProviderSettings$Factory.class);
         } catch (Exception var2) {
            return new ChunkProviderSettings$Factory();
         }
      }
   }

   public String toString() {
      return JSON_ADAPTER.toJson(this);
   }

   public ChunkProviderSettings$Factory() {
      this.func_177863_a();
   }

   public void func_177863_a() {
      this.coordinateScale = 684.412F;
      this.heightScale = 684.412F;
      this.upperLimitScale = 512.0F;
      this.lowerLimitScale = 512.0F;
      this.depthNoiseScaleX = 200.0F;
      this.depthNoiseScaleZ = 200.0F;
      this.depthNoiseScaleExponent = 0.5F;
      this.mainNoiseScaleX = 80.0F;
      this.mainNoiseScaleY = 160.0F;
      this.mainNoiseScaleZ = 80.0F;
      this.baseSize = 8.5F;
      this.stretchY = 12.0F;
      this.biomeDepthWeight = 1.0F;
      this.biomeDepthOffset = 0.0F;
      this.biomeScaleWeight = 1.0F;
      this.biomeScaleOffset = 0.0F;
      this.seaLevel = 63;
      this.useCaves = true;
      this.useDungeons = true;
      this.dungeonChance = 8;
      this.useStrongholds = true;
      this.useVillages = true;
      this.useMineShafts = true;
      this.useTemples = true;
      this.useMonuments = true;
      this.useRavines = true;
      this.useWaterLakes = true;
      this.waterLakeChance = 4;
      this.useLavaLakes = true;
      this.lavaLakeChance = 80;
      this.useLavaOceans = false;
      this.fixedBiome = -1;
      this.biomeSize = 4;
      this.riverSize = 4;
      this.dirtSize = 33;
      this.dirtCount = 10;
      this.dirtMinHeight = 0;
      this.dirtMaxHeight = 256;
      this.gravelSize = 33;
      this.gravelCount = 8;
      this.gravelMinHeight = 0;
      this.gravelMaxHeight = 256;
      this.graniteSize = 33;
      this.graniteCount = 10;
      this.graniteMinHeight = 0;
      this.graniteMaxHeight = 80;
      this.dioriteSize = 33;
      this.dioriteCount = 10;
      this.dioriteMinHeight = 0;
      this.dioriteMaxHeight = 80;
      this.andesiteSize = 33;
      this.andesiteCount = 10;
      this.andesiteMinHeight = 0;
      this.andesiteMaxHeight = 80;
      this.coalSize = 17;
      this.coalCount = 20;
      this.coalMinHeight = 0;
      this.coalMaxHeight = 128;
      this.ironSize = 9;
      this.ironCount = 20;
      this.ironMinHeight = 0;
      this.ironMaxHeight = 64;
      this.goldSize = 9;
      this.goldCount = 2;
      this.goldMinHeight = 0;
      this.goldMaxHeight = 32;
      this.redstoneSize = 8;
      this.redstoneCount = 8;
      this.redstoneMinHeight = 0;
      this.redstoneMaxHeight = 16;
      this.diamondSize = 8;
      this.diamondCount = 1;
      this.diamondMinHeight = 0;
      this.diamondMaxHeight = 16;
      this.lapisSize = 7;
      this.lapisCount = 1;
      this.lapisCenterHeight = 16;
      this.lapisSpread = 16;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(this.getClass() != var1.getClass()) {
         return false;
      } else {
         ChunkProviderSettings$Factory var2 = (ChunkProviderSettings$Factory)var1;
         return this.andesiteCount == var2.andesiteCount && this.andesiteMaxHeight == var2.andesiteMaxHeight && this.andesiteMinHeight == var2.andesiteMinHeight && this.andesiteSize == var2.andesiteSize && Float.compare(var2.baseSize, this.baseSize) == 0 && Float.compare(var2.biomeDepthOffset, this.biomeDepthOffset) == 0 && Float.compare(var2.biomeDepthWeight, this.biomeDepthWeight) == 0 && Float.compare(var2.biomeScaleOffset, this.biomeScaleOffset) == 0 && Float.compare(var2.biomeScaleWeight, this.biomeScaleWeight) == 0 && this.biomeSize == var2.biomeSize && this.coalCount == var2.coalCount && this.coalMaxHeight == var2.coalMaxHeight && this.coalMinHeight == var2.coalMinHeight && this.coalSize == var2.coalSize && Float.compare(var2.coordinateScale, this.coordinateScale) == 0 && Float.compare(var2.depthNoiseScaleExponent, this.depthNoiseScaleExponent) == 0 && Float.compare(var2.depthNoiseScaleX, this.depthNoiseScaleX) == 0 && Float.compare(var2.depthNoiseScaleZ, this.depthNoiseScaleZ) == 0 && this.diamondCount == var2.diamondCount && this.diamondMaxHeight == var2.diamondMaxHeight && this.diamondMinHeight == var2.diamondMinHeight && this.diamondSize == var2.diamondSize && this.dioriteCount == var2.dioriteCount && this.dioriteMaxHeight == var2.dioriteMaxHeight && this.dioriteMinHeight == var2.dioriteMinHeight && this.dioriteSize == var2.dioriteSize && this.dirtCount == var2.dirtCount && this.dirtMaxHeight == var2.dirtMaxHeight && this.dirtMinHeight == var2.dirtMinHeight && this.dirtSize == var2.dirtSize && this.dungeonChance == var2.dungeonChance && this.fixedBiome == var2.fixedBiome && this.goldCount == var2.goldCount && this.goldMaxHeight == var2.goldMaxHeight && this.goldMinHeight == var2.goldMinHeight && this.goldSize == var2.goldSize && this.graniteCount == var2.graniteCount && this.graniteMaxHeight == var2.graniteMaxHeight && this.graniteMinHeight == var2.graniteMinHeight && this.graniteSize == var2.graniteSize && this.gravelCount == var2.gravelCount && this.gravelMaxHeight == var2.gravelMaxHeight && this.gravelMinHeight == var2.gravelMinHeight && this.gravelSize == var2.gravelSize && Float.compare(var2.heightScale, this.heightScale) == 0 && this.ironCount == var2.ironCount && this.ironMaxHeight == var2.ironMaxHeight && this.ironMinHeight == var2.ironMinHeight && this.ironSize == var2.ironSize && this.lapisCenterHeight == var2.lapisCenterHeight && this.lapisCount == var2.lapisCount && this.lapisSize == var2.lapisSize && this.lapisSpread == var2.lapisSpread && this.lavaLakeChance == var2.lavaLakeChance && Float.compare(var2.lowerLimitScale, this.lowerLimitScale) == 0 && Float.compare(var2.mainNoiseScaleX, this.mainNoiseScaleX) == 0 && Float.compare(var2.mainNoiseScaleY, this.mainNoiseScaleY) == 0 && Float.compare(var2.mainNoiseScaleZ, this.mainNoiseScaleZ) == 0 && this.redstoneCount == var2.redstoneCount && this.redstoneMaxHeight == var2.redstoneMaxHeight && this.redstoneMinHeight == var2.redstoneMinHeight && this.redstoneSize == var2.redstoneSize && this.riverSize == var2.riverSize && this.seaLevel == var2.seaLevel && Float.compare(var2.stretchY, this.stretchY) == 0 && Float.compare(var2.upperLimitScale, this.upperLimitScale) == 0 && this.useCaves == var2.useCaves && this.useDungeons == var2.useDungeons && this.useLavaLakes == var2.useLavaLakes && this.useLavaOceans == var2.useLavaOceans && this.useMineShafts == var2.useMineShafts && this.useRavines == var2.useRavines && this.useStrongholds == var2.useStrongholds && this.useTemples == var2.useTemples && this.useMonuments == var2.useMonuments && this.useVillages == var2.useVillages && this.useWaterLakes == var2.useWaterLakes && this.waterLakeChance == var2.waterLakeChance;
      }
   }

   public int hashCode() {
      int var1 = this.coordinateScale != 0.0F?Float.floatToIntBits(this.coordinateScale):0;
      var1 = 31 * var1 + (this.heightScale != 0.0F?Float.floatToIntBits(this.heightScale):0);
      var1 = 31 * var1 + (this.upperLimitScale != 0.0F?Float.floatToIntBits(this.upperLimitScale):0);
      var1 = 31 * var1 + (this.lowerLimitScale != 0.0F?Float.floatToIntBits(this.lowerLimitScale):0);
      var1 = 31 * var1 + (this.depthNoiseScaleX != 0.0F?Float.floatToIntBits(this.depthNoiseScaleX):0);
      var1 = 31 * var1 + (this.depthNoiseScaleZ != 0.0F?Float.floatToIntBits(this.depthNoiseScaleZ):0);
      var1 = 31 * var1 + (this.depthNoiseScaleExponent != 0.0F?Float.floatToIntBits(this.depthNoiseScaleExponent):0);
      var1 = 31 * var1 + (this.mainNoiseScaleX != 0.0F?Float.floatToIntBits(this.mainNoiseScaleX):0);
      var1 = 31 * var1 + (this.mainNoiseScaleY != 0.0F?Float.floatToIntBits(this.mainNoiseScaleY):0);
      var1 = 31 * var1 + (this.mainNoiseScaleZ != 0.0F?Float.floatToIntBits(this.mainNoiseScaleZ):0);
      var1 = 31 * var1 + (this.baseSize != 0.0F?Float.floatToIntBits(this.baseSize):0);
      var1 = 31 * var1 + (this.stretchY != 0.0F?Float.floatToIntBits(this.stretchY):0);
      var1 = 31 * var1 + (this.biomeDepthWeight != 0.0F?Float.floatToIntBits(this.biomeDepthWeight):0);
      var1 = 31 * var1 + (this.biomeDepthOffset != 0.0F?Float.floatToIntBits(this.biomeDepthOffset):0);
      var1 = 31 * var1 + (this.biomeScaleWeight != 0.0F?Float.floatToIntBits(this.biomeScaleWeight):0);
      var1 = 31 * var1 + (this.biomeScaleOffset != 0.0F?Float.floatToIntBits(this.biomeScaleOffset):0);
      var1 = 31 * var1 + this.seaLevel;
      var1 = 31 * var1 + (this.useCaves?1:0);
      var1 = 31 * var1 + (this.useDungeons?1:0);
      var1 = 31 * var1 + this.dungeonChance;
      var1 = 31 * var1 + (this.useStrongholds?1:0);
      var1 = 31 * var1 + (this.useVillages?1:0);
      var1 = 31 * var1 + (this.useMineShafts?1:0);
      var1 = 31 * var1 + (this.useTemples?1:0);
      var1 = 31 * var1 + (this.useMonuments?1:0);
      var1 = 31 * var1 + (this.useRavines?1:0);
      var1 = 31 * var1 + (this.useWaterLakes?1:0);
      var1 = 31 * var1 + this.waterLakeChance;
      var1 = 31 * var1 + (this.useLavaLakes?1:0);
      var1 = 31 * var1 + this.lavaLakeChance;
      var1 = 31 * var1 + (this.useLavaOceans?1:0);
      var1 = 31 * var1 + this.fixedBiome;
      var1 = 31 * var1 + this.biomeSize;
      var1 = 31 * var1 + this.riverSize;
      var1 = 31 * var1 + this.dirtSize;
      var1 = 31 * var1 + this.dirtCount;
      var1 = 31 * var1 + this.dirtMinHeight;
      var1 = 31 * var1 + this.dirtMaxHeight;
      var1 = 31 * var1 + this.gravelSize;
      var1 = 31 * var1 + this.gravelCount;
      var1 = 31 * var1 + this.gravelMinHeight;
      var1 = 31 * var1 + this.gravelMaxHeight;
      var1 = 31 * var1 + this.graniteSize;
      var1 = 31 * var1 + this.graniteCount;
      var1 = 31 * var1 + this.graniteMinHeight;
      var1 = 31 * var1 + this.graniteMaxHeight;
      var1 = 31 * var1 + this.dioriteSize;
      var1 = 31 * var1 + this.dioriteCount;
      var1 = 31 * var1 + this.dioriteMinHeight;
      var1 = 31 * var1 + this.dioriteMaxHeight;
      var1 = 31 * var1 + this.andesiteSize;
      var1 = 31 * var1 + this.andesiteCount;
      var1 = 31 * var1 + this.andesiteMinHeight;
      var1 = 31 * var1 + this.andesiteMaxHeight;
      var1 = 31 * var1 + this.coalSize;
      var1 = 31 * var1 + this.coalCount;
      var1 = 31 * var1 + this.coalMinHeight;
      var1 = 31 * var1 + this.coalMaxHeight;
      var1 = 31 * var1 + this.ironSize;
      var1 = 31 * var1 + this.ironCount;
      var1 = 31 * var1 + this.ironMinHeight;
      var1 = 31 * var1 + this.ironMaxHeight;
      var1 = 31 * var1 + this.goldSize;
      var1 = 31 * var1 + this.goldCount;
      var1 = 31 * var1 + this.goldMinHeight;
      var1 = 31 * var1 + this.goldMaxHeight;
      var1 = 31 * var1 + this.redstoneSize;
      var1 = 31 * var1 + this.redstoneCount;
      var1 = 31 * var1 + this.redstoneMinHeight;
      var1 = 31 * var1 + this.redstoneMaxHeight;
      var1 = 31 * var1 + this.diamondSize;
      var1 = 31 * var1 + this.diamondCount;
      var1 = 31 * var1 + this.diamondMinHeight;
      var1 = 31 * var1 + this.diamondMaxHeight;
      var1 = 31 * var1 + this.lapisSize;
      var1 = 31 * var1 + this.lapisCount;
      var1 = 31 * var1 + this.lapisCenterHeight;
      var1 = 31 * var1 + this.lapisSpread;
      return var1;
   }

   public ChunkProviderSettings func_177864_b() {
      return new ChunkProviderSettings(this);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
