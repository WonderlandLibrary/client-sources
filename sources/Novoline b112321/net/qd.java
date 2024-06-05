package net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.ChunkProviderSettings$Factory;

public class qd implements JsonDeserializer, JsonSerializer {
   public ChunkProviderSettings$Factory a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject var4 = var1.getAsJsonObject();
      ChunkProviderSettings$Factory var5 = new ChunkProviderSettings$Factory();

      try {
         var5.coordinateScale = JsonUtils.getFloat(var4, "coordinateScale", var5.coordinateScale);
         var5.heightScale = JsonUtils.getFloat(var4, "heightScale", var5.heightScale);
         var5.lowerLimitScale = JsonUtils.getFloat(var4, "lowerLimitScale", var5.lowerLimitScale);
         var5.upperLimitScale = JsonUtils.getFloat(var4, "upperLimitScale", var5.upperLimitScale);
         var5.depthNoiseScaleX = JsonUtils.getFloat(var4, "depthNoiseScaleX", var5.depthNoiseScaleX);
         var5.depthNoiseScaleZ = JsonUtils.getFloat(var4, "depthNoiseScaleZ", var5.depthNoiseScaleZ);
         var5.depthNoiseScaleExponent = JsonUtils.getFloat(var4, "depthNoiseScaleExponent", var5.depthNoiseScaleExponent);
         var5.mainNoiseScaleX = JsonUtils.getFloat(var4, "mainNoiseScaleX", var5.mainNoiseScaleX);
         var5.mainNoiseScaleY = JsonUtils.getFloat(var4, "mainNoiseScaleY", var5.mainNoiseScaleY);
         var5.mainNoiseScaleZ = JsonUtils.getFloat(var4, "mainNoiseScaleZ", var5.mainNoiseScaleZ);
         var5.baseSize = JsonUtils.getFloat(var4, "baseSize", var5.baseSize);
         var5.stretchY = JsonUtils.getFloat(var4, "stretchY", var5.stretchY);
         var5.biomeDepthWeight = JsonUtils.getFloat(var4, "biomeDepthWeight", var5.biomeDepthWeight);
         var5.biomeDepthOffset = JsonUtils.getFloat(var4, "biomeDepthOffset", var5.biomeDepthOffset);
         var5.biomeScaleWeight = JsonUtils.getFloat(var4, "biomeScaleWeight", var5.biomeScaleWeight);
         var5.biomeScaleOffset = JsonUtils.getFloat(var4, "biomeScaleOffset", var5.biomeScaleOffset);
         var5.seaLevel = JsonUtils.getInt(var4, "seaLevel", var5.seaLevel);
         var5.useCaves = JsonUtils.getBoolean(var4, "useCaves", var5.useCaves);
         var5.useDungeons = JsonUtils.getBoolean(var4, "useDungeons", var5.useDungeons);
         var5.dungeonChance = JsonUtils.getInt(var4, "dungeonChance", var5.dungeonChance);
         var5.useStrongholds = JsonUtils.getBoolean(var4, "useStrongholds", var5.useStrongholds);
         var5.useVillages = JsonUtils.getBoolean(var4, "useVillages", var5.useVillages);
         var5.useMineShafts = JsonUtils.getBoolean(var4, "useMineShafts", var5.useMineShafts);
         var5.useTemples = JsonUtils.getBoolean(var4, "useTemples", var5.useTemples);
         var5.useMonuments = JsonUtils.getBoolean(var4, "useMonuments", var5.useMonuments);
         var5.useRavines = JsonUtils.getBoolean(var4, "useRavines", var5.useRavines);
         var5.useWaterLakes = JsonUtils.getBoolean(var4, "useWaterLakes", var5.useWaterLakes);
         var5.waterLakeChance = JsonUtils.getInt(var4, "waterLakeChance", var5.waterLakeChance);
         var5.useLavaLakes = JsonUtils.getBoolean(var4, "useLavaLakes", var5.useLavaLakes);
         var5.lavaLakeChance = JsonUtils.getInt(var4, "lavaLakeChance", var5.lavaLakeChance);
         var5.useLavaOceans = JsonUtils.getBoolean(var4, "useLavaOceans", var5.useLavaOceans);
         var5.fixedBiome = JsonUtils.getInt(var4, "fixedBiome", var5.fixedBiome);
         if(var5.fixedBiome < 38 && var5.fixedBiome >= -1) {
            if(var5.fixedBiome >= BiomeGenBase.hell.biomeID) {
               var5.fixedBiome += 2;
            }
         } else {
            var5.fixedBiome = -1;
         }

         var5.biomeSize = JsonUtils.getInt(var4, "biomeSize", var5.biomeSize);
         var5.riverSize = JsonUtils.getInt(var4, "riverSize", var5.riverSize);
         var5.dirtSize = JsonUtils.getInt(var4, "dirtSize", var5.dirtSize);
         var5.dirtCount = JsonUtils.getInt(var4, "dirtCount", var5.dirtCount);
         var5.dirtMinHeight = JsonUtils.getInt(var4, "dirtMinHeight", var5.dirtMinHeight);
         var5.dirtMaxHeight = JsonUtils.getInt(var4, "dirtMaxHeight", var5.dirtMaxHeight);
         var5.gravelSize = JsonUtils.getInt(var4, "gravelSize", var5.gravelSize);
         var5.gravelCount = JsonUtils.getInt(var4, "gravelCount", var5.gravelCount);
         var5.gravelMinHeight = JsonUtils.getInt(var4, "gravelMinHeight", var5.gravelMinHeight);
         var5.gravelMaxHeight = JsonUtils.getInt(var4, "gravelMaxHeight", var5.gravelMaxHeight);
         var5.graniteSize = JsonUtils.getInt(var4, "graniteSize", var5.graniteSize);
         var5.graniteCount = JsonUtils.getInt(var4, "graniteCount", var5.graniteCount);
         var5.graniteMinHeight = JsonUtils.getInt(var4, "graniteMinHeight", var5.graniteMinHeight);
         var5.graniteMaxHeight = JsonUtils.getInt(var4, "graniteMaxHeight", var5.graniteMaxHeight);
         var5.dioriteSize = JsonUtils.getInt(var4, "dioriteSize", var5.dioriteSize);
         var5.dioriteCount = JsonUtils.getInt(var4, "dioriteCount", var5.dioriteCount);
         var5.dioriteMinHeight = JsonUtils.getInt(var4, "dioriteMinHeight", var5.dioriteMinHeight);
         var5.dioriteMaxHeight = JsonUtils.getInt(var4, "dioriteMaxHeight", var5.dioriteMaxHeight);
         var5.andesiteSize = JsonUtils.getInt(var4, "andesiteSize", var5.andesiteSize);
         var5.andesiteCount = JsonUtils.getInt(var4, "andesiteCount", var5.andesiteCount);
         var5.andesiteMinHeight = JsonUtils.getInt(var4, "andesiteMinHeight", var5.andesiteMinHeight);
         var5.andesiteMaxHeight = JsonUtils.getInt(var4, "andesiteMaxHeight", var5.andesiteMaxHeight);
         var5.coalSize = JsonUtils.getInt(var4, "coalSize", var5.coalSize);
         var5.coalCount = JsonUtils.getInt(var4, "coalCount", var5.coalCount);
         var5.coalMinHeight = JsonUtils.getInt(var4, "coalMinHeight", var5.coalMinHeight);
         var5.coalMaxHeight = JsonUtils.getInt(var4, "coalMaxHeight", var5.coalMaxHeight);
         var5.ironSize = JsonUtils.getInt(var4, "ironSize", var5.ironSize);
         var5.ironCount = JsonUtils.getInt(var4, "ironCount", var5.ironCount);
         var5.ironMinHeight = JsonUtils.getInt(var4, "ironMinHeight", var5.ironMinHeight);
         var5.ironMaxHeight = JsonUtils.getInt(var4, "ironMaxHeight", var5.ironMaxHeight);
         var5.goldSize = JsonUtils.getInt(var4, "goldSize", var5.goldSize);
         var5.goldCount = JsonUtils.getInt(var4, "goldCount", var5.goldCount);
         var5.goldMinHeight = JsonUtils.getInt(var4, "goldMinHeight", var5.goldMinHeight);
         var5.goldMaxHeight = JsonUtils.getInt(var4, "goldMaxHeight", var5.goldMaxHeight);
         var5.redstoneSize = JsonUtils.getInt(var4, "redstoneSize", var5.redstoneSize);
         var5.redstoneCount = JsonUtils.getInt(var4, "redstoneCount", var5.redstoneCount);
         var5.redstoneMinHeight = JsonUtils.getInt(var4, "redstoneMinHeight", var5.redstoneMinHeight);
         var5.redstoneMaxHeight = JsonUtils.getInt(var4, "redstoneMaxHeight", var5.redstoneMaxHeight);
         var5.diamondSize = JsonUtils.getInt(var4, "diamondSize", var5.diamondSize);
         var5.diamondCount = JsonUtils.getInt(var4, "diamondCount", var5.diamondCount);
         var5.diamondMinHeight = JsonUtils.getInt(var4, "diamondMinHeight", var5.diamondMinHeight);
         var5.diamondMaxHeight = JsonUtils.getInt(var4, "diamondMaxHeight", var5.diamondMaxHeight);
         var5.lapisSize = JsonUtils.getInt(var4, "lapisSize", var5.lapisSize);
         var5.lapisCount = JsonUtils.getInt(var4, "lapisCount", var5.lapisCount);
         var5.lapisCenterHeight = JsonUtils.getInt(var4, "lapisCenterHeight", var5.lapisCenterHeight);
         var5.lapisSpread = JsonUtils.getInt(var4, "lapisSpread", var5.lapisSpread);
      } catch (Exception var7) {
         ;
      }

      return var5;
   }

   public JsonElement a(ChunkProviderSettings$Factory var1, Type var2, JsonSerializationContext var3) {
      JsonObject var4 = new JsonObject();
      var4.addProperty("coordinateScale", Float.valueOf(var1.coordinateScale));
      var4.addProperty("heightScale", Float.valueOf(var1.heightScale));
      var4.addProperty("lowerLimitScale", Float.valueOf(var1.lowerLimitScale));
      var4.addProperty("upperLimitScale", Float.valueOf(var1.upperLimitScale));
      var4.addProperty("depthNoiseScaleX", Float.valueOf(var1.depthNoiseScaleX));
      var4.addProperty("depthNoiseScaleZ", Float.valueOf(var1.depthNoiseScaleZ));
      var4.addProperty("depthNoiseScaleExponent", Float.valueOf(var1.depthNoiseScaleExponent));
      var4.addProperty("mainNoiseScaleX", Float.valueOf(var1.mainNoiseScaleX));
      var4.addProperty("mainNoiseScaleY", Float.valueOf(var1.mainNoiseScaleY));
      var4.addProperty("mainNoiseScaleZ", Float.valueOf(var1.mainNoiseScaleZ));
      var4.addProperty("baseSize", Float.valueOf(var1.baseSize));
      var4.addProperty("stretchY", Float.valueOf(var1.stretchY));
      var4.addProperty("biomeDepthWeight", Float.valueOf(var1.biomeDepthWeight));
      var4.addProperty("biomeDepthOffset", Float.valueOf(var1.biomeDepthOffset));
      var4.addProperty("biomeScaleWeight", Float.valueOf(var1.biomeScaleWeight));
      var4.addProperty("biomeScaleOffset", Float.valueOf(var1.biomeScaleOffset));
      var4.addProperty("seaLevel", Integer.valueOf(var1.seaLevel));
      var4.addProperty("useCaves", Boolean.valueOf(var1.useCaves));
      var4.addProperty("useDungeons", Boolean.valueOf(var1.useDungeons));
      var4.addProperty("dungeonChance", Integer.valueOf(var1.dungeonChance));
      var4.addProperty("useStrongholds", Boolean.valueOf(var1.useStrongholds));
      var4.addProperty("useVillages", Boolean.valueOf(var1.useVillages));
      var4.addProperty("useMineShafts", Boolean.valueOf(var1.useMineShafts));
      var4.addProperty("useTemples", Boolean.valueOf(var1.useTemples));
      var4.addProperty("useMonuments", Boolean.valueOf(var1.useMonuments));
      var4.addProperty("useRavines", Boolean.valueOf(var1.useRavines));
      var4.addProperty("useWaterLakes", Boolean.valueOf(var1.useWaterLakes));
      var4.addProperty("waterLakeChance", Integer.valueOf(var1.waterLakeChance));
      var4.addProperty("useLavaLakes", Boolean.valueOf(var1.useLavaLakes));
      var4.addProperty("lavaLakeChance", Integer.valueOf(var1.lavaLakeChance));
      var4.addProperty("useLavaOceans", Boolean.valueOf(var1.useLavaOceans));
      var4.addProperty("fixedBiome", Integer.valueOf(var1.fixedBiome));
      var4.addProperty("biomeSize", Integer.valueOf(var1.biomeSize));
      var4.addProperty("riverSize", Integer.valueOf(var1.riverSize));
      var4.addProperty("dirtSize", Integer.valueOf(var1.dirtSize));
      var4.addProperty("dirtCount", Integer.valueOf(var1.dirtCount));
      var4.addProperty("dirtMinHeight", Integer.valueOf(var1.dirtMinHeight));
      var4.addProperty("dirtMaxHeight", Integer.valueOf(var1.dirtMaxHeight));
      var4.addProperty("gravelSize", Integer.valueOf(var1.gravelSize));
      var4.addProperty("gravelCount", Integer.valueOf(var1.gravelCount));
      var4.addProperty("gravelMinHeight", Integer.valueOf(var1.gravelMinHeight));
      var4.addProperty("gravelMaxHeight", Integer.valueOf(var1.gravelMaxHeight));
      var4.addProperty("graniteSize", Integer.valueOf(var1.graniteSize));
      var4.addProperty("graniteCount", Integer.valueOf(var1.graniteCount));
      var4.addProperty("graniteMinHeight", Integer.valueOf(var1.graniteMinHeight));
      var4.addProperty("graniteMaxHeight", Integer.valueOf(var1.graniteMaxHeight));
      var4.addProperty("dioriteSize", Integer.valueOf(var1.dioriteSize));
      var4.addProperty("dioriteCount", Integer.valueOf(var1.dioriteCount));
      var4.addProperty("dioriteMinHeight", Integer.valueOf(var1.dioriteMinHeight));
      var4.addProperty("dioriteMaxHeight", Integer.valueOf(var1.dioriteMaxHeight));
      var4.addProperty("andesiteSize", Integer.valueOf(var1.andesiteSize));
      var4.addProperty("andesiteCount", Integer.valueOf(var1.andesiteCount));
      var4.addProperty("andesiteMinHeight", Integer.valueOf(var1.andesiteMinHeight));
      var4.addProperty("andesiteMaxHeight", Integer.valueOf(var1.andesiteMaxHeight));
      var4.addProperty("coalSize", Integer.valueOf(var1.coalSize));
      var4.addProperty("coalCount", Integer.valueOf(var1.coalCount));
      var4.addProperty("coalMinHeight", Integer.valueOf(var1.coalMinHeight));
      var4.addProperty("coalMaxHeight", Integer.valueOf(var1.coalMaxHeight));
      var4.addProperty("ironSize", Integer.valueOf(var1.ironSize));
      var4.addProperty("ironCount", Integer.valueOf(var1.ironCount));
      var4.addProperty("ironMinHeight", Integer.valueOf(var1.ironMinHeight));
      var4.addProperty("ironMaxHeight", Integer.valueOf(var1.ironMaxHeight));
      var4.addProperty("goldSize", Integer.valueOf(var1.goldSize));
      var4.addProperty("goldCount", Integer.valueOf(var1.goldCount));
      var4.addProperty("goldMinHeight", Integer.valueOf(var1.goldMinHeight));
      var4.addProperty("goldMaxHeight", Integer.valueOf(var1.goldMaxHeight));
      var4.addProperty("redstoneSize", Integer.valueOf(var1.redstoneSize));
      var4.addProperty("redstoneCount", Integer.valueOf(var1.redstoneCount));
      var4.addProperty("redstoneMinHeight", Integer.valueOf(var1.redstoneMinHeight));
      var4.addProperty("redstoneMaxHeight", Integer.valueOf(var1.redstoneMaxHeight));
      var4.addProperty("diamondSize", Integer.valueOf(var1.diamondSize));
      var4.addProperty("diamondCount", Integer.valueOf(var1.diamondCount));
      var4.addProperty("diamondMinHeight", Integer.valueOf(var1.diamondMinHeight));
      var4.addProperty("diamondMaxHeight", Integer.valueOf(var1.diamondMaxHeight));
      var4.addProperty("lapisSize", Integer.valueOf(var1.lapisSize));
      var4.addProperty("lapisCount", Integer.valueOf(var1.lapisCount));
      var4.addProperty("lapisCenterHeight", Integer.valueOf(var1.lapisCenterHeight));
      var4.addProperty("lapisSpread", Integer.valueOf(var1.lapisSpread));
      return var4;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
