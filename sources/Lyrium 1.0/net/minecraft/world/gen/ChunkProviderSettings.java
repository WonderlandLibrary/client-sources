// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.world.gen;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.JsonUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

public class ChunkProviderSettings
{
    public final float coordinateScale;
    public final float heightScale;
    public final float upperLimitScale;
    public final float lowerLimitScale;
    public final float depthNoiseScaleX;
    public final float depthNoiseScaleZ;
    public final float depthNoiseScaleExponent;
    public final float mainNoiseScaleX;
    public final float mainNoiseScaleY;
    public final float mainNoiseScaleZ;
    public final float baseSize;
    public final float stretchY;
    public final float biomeDepthWeight;
    public final float biomeDepthOffSet;
    public final float biomeScaleWeight;
    public final float biomeScaleOffset;
    public final int seaLevel;
    public final boolean useCaves;
    public final boolean useDungeons;
    public final int dungeonChance;
    public final boolean useStrongholds;
    public final boolean useVillages;
    public final boolean useMineShafts;
    public final boolean useTemples;
    public final boolean useMonuments;
    public final boolean useRavines;
    public final boolean useWaterLakes;
    public final int waterLakeChance;
    public final boolean useLavaLakes;
    public final int lavaLakeChance;
    public final boolean useLavaOceans;
    public final int fixedBiome;
    public final int biomeSize;
    public final int riverSize;
    public final int dirtSize;
    public final int dirtCount;
    public final int dirtMinHeight;
    public final int dirtMaxHeight;
    public final int gravelSize;
    public final int gravelCount;
    public final int gravelMinHeight;
    public final int gravelMaxHeight;
    public final int graniteSize;
    public final int graniteCount;
    public final int graniteMinHeight;
    public final int graniteMaxHeight;
    public final int dioriteSize;
    public final int dioriteCount;
    public final int dioriteMinHeight;
    public final int dioriteMaxHeight;
    public final int andesiteSize;
    public final int andesiteCount;
    public final int andesiteMinHeight;
    public final int andesiteMaxHeight;
    public final int coalSize;
    public final int coalCount;
    public final int coalMinHeight;
    public final int coalMaxHeight;
    public final int ironSize;
    public final int ironCount;
    public final int ironMinHeight;
    public final int ironMaxHeight;
    public final int goldSize;
    public final int goldCount;
    public final int goldMinHeight;
    public final int goldMaxHeight;
    public final int redstoneSize;
    public final int redstoneCount;
    public final int redstoneMinHeight;
    public final int redstoneMaxHeight;
    public final int diamondSize;
    public final int diamondCount;
    public final int diamondMinHeight;
    public final int diamondMaxHeight;
    public final int lapisSize;
    public final int lapisCount;
    public final int lapisCenterHeight;
    public final int lapisSpread;
    
    private ChunkProviderSettings(final Factory settingsFactory) {
        this.coordinateScale = settingsFactory.coordinateScale;
        this.heightScale = settingsFactory.heightScale;
        this.upperLimitScale = settingsFactory.upperLimitScale;
        this.lowerLimitScale = settingsFactory.lowerLimitScale;
        this.depthNoiseScaleX = settingsFactory.depthNoiseScaleX;
        this.depthNoiseScaleZ = settingsFactory.depthNoiseScaleZ;
        this.depthNoiseScaleExponent = settingsFactory.depthNoiseScaleExponent;
        this.mainNoiseScaleX = settingsFactory.mainNoiseScaleX;
        this.mainNoiseScaleY = settingsFactory.mainNoiseScaleY;
        this.mainNoiseScaleZ = settingsFactory.mainNoiseScaleZ;
        this.baseSize = settingsFactory.baseSize;
        this.stretchY = settingsFactory.stretchY;
        this.biomeDepthWeight = settingsFactory.biomeDepthWeight;
        this.biomeDepthOffSet = settingsFactory.biomeDepthOffset;
        this.biomeScaleWeight = settingsFactory.biomeScaleWeight;
        this.biomeScaleOffset = settingsFactory.biomeScaleOffset;
        this.seaLevel = settingsFactory.seaLevel;
        this.useCaves = settingsFactory.useCaves;
        this.useDungeons = settingsFactory.useDungeons;
        this.dungeonChance = settingsFactory.dungeonChance;
        this.useStrongholds = settingsFactory.useStrongholds;
        this.useVillages = settingsFactory.useVillages;
        this.useMineShafts = settingsFactory.useMineShafts;
        this.useTemples = settingsFactory.useTemples;
        this.useMonuments = settingsFactory.useMonuments;
        this.useRavines = settingsFactory.useRavines;
        this.useWaterLakes = settingsFactory.useWaterLakes;
        this.waterLakeChance = settingsFactory.waterLakeChance;
        this.useLavaLakes = settingsFactory.useLavaLakes;
        this.lavaLakeChance = settingsFactory.lavaLakeChance;
        this.useLavaOceans = settingsFactory.useLavaOceans;
        this.fixedBiome = settingsFactory.fixedBiome;
        this.biomeSize = settingsFactory.biomeSize;
        this.riverSize = settingsFactory.riverSize;
        this.dirtSize = settingsFactory.dirtSize;
        this.dirtCount = settingsFactory.dirtCount;
        this.dirtMinHeight = settingsFactory.dirtMinHeight;
        this.dirtMaxHeight = settingsFactory.dirtMaxHeight;
        this.gravelSize = settingsFactory.gravelSize;
        this.gravelCount = settingsFactory.gravelCount;
        this.gravelMinHeight = settingsFactory.gravelMinHeight;
        this.gravelMaxHeight = settingsFactory.gravelMaxHeight;
        this.graniteSize = settingsFactory.graniteSize;
        this.graniteCount = settingsFactory.graniteCount;
        this.graniteMinHeight = settingsFactory.graniteMinHeight;
        this.graniteMaxHeight = settingsFactory.graniteMaxHeight;
        this.dioriteSize = settingsFactory.dioriteSize;
        this.dioriteCount = settingsFactory.dioriteCount;
        this.dioriteMinHeight = settingsFactory.dioriteMinHeight;
        this.dioriteMaxHeight = settingsFactory.dioriteMaxHeight;
        this.andesiteSize = settingsFactory.andesiteSize;
        this.andesiteCount = settingsFactory.andesiteCount;
        this.andesiteMinHeight = settingsFactory.andesiteMinHeight;
        this.andesiteMaxHeight = settingsFactory.andesiteMaxHeight;
        this.coalSize = settingsFactory.coalSize;
        this.coalCount = settingsFactory.coalCount;
        this.coalMinHeight = settingsFactory.coalMinHeight;
        this.coalMaxHeight = settingsFactory.coalMaxHeight;
        this.ironSize = settingsFactory.ironSize;
        this.ironCount = settingsFactory.ironCount;
        this.ironMinHeight = settingsFactory.ironMinHeight;
        this.ironMaxHeight = settingsFactory.ironMaxHeight;
        this.goldSize = settingsFactory.goldSize;
        this.goldCount = settingsFactory.goldCount;
        this.goldMinHeight = settingsFactory.goldMinHeight;
        this.goldMaxHeight = settingsFactory.goldMaxHeight;
        this.redstoneSize = settingsFactory.redstoneSize;
        this.redstoneCount = settingsFactory.redstoneCount;
        this.redstoneMinHeight = settingsFactory.redstoneMinHeight;
        this.redstoneMaxHeight = settingsFactory.redstoneMaxHeight;
        this.diamondSize = settingsFactory.diamondSize;
        this.diamondCount = settingsFactory.diamondCount;
        this.diamondMinHeight = settingsFactory.diamondMinHeight;
        this.diamondMaxHeight = settingsFactory.diamondMaxHeight;
        this.lapisSize = settingsFactory.lapisSize;
        this.lapisCount = settingsFactory.lapisCount;
        this.lapisCenterHeight = settingsFactory.lapisCenterHeight;
        this.lapisSpread = settingsFactory.lapisSpread;
    }
    
    public static class Factory
    {
        static final Gson JSON_ADAPTER;
        public float coordinateScale;
        public float heightScale;
        public float upperLimitScale;
        public float lowerLimitScale;
        public float depthNoiseScaleX;
        public float depthNoiseScaleZ;
        public float depthNoiseScaleExponent;
        public float mainNoiseScaleX;
        public float mainNoiseScaleY;
        public float mainNoiseScaleZ;
        public float baseSize;
        public float stretchY;
        public float biomeDepthWeight;
        public float biomeDepthOffset;
        public float biomeScaleWeight;
        public float biomeScaleOffset;
        public int seaLevel;
        public boolean useCaves;
        public boolean useDungeons;
        public int dungeonChance;
        public boolean useStrongholds;
        public boolean useVillages;
        public boolean useMineShafts;
        public boolean useTemples;
        public boolean useMonuments;
        public boolean useRavines;
        public boolean useWaterLakes;
        public int waterLakeChance;
        public boolean useLavaLakes;
        public int lavaLakeChance;
        public boolean useLavaOceans;
        public int fixedBiome;
        public int biomeSize;
        public int riverSize;
        public int dirtSize;
        public int dirtCount;
        public int dirtMinHeight;
        public int dirtMaxHeight;
        public int gravelSize;
        public int gravelCount;
        public int gravelMinHeight;
        public int gravelMaxHeight;
        public int graniteSize;
        public int graniteCount;
        public int graniteMinHeight;
        public int graniteMaxHeight;
        public int dioriteSize;
        public int dioriteCount;
        public int dioriteMinHeight;
        public int dioriteMaxHeight;
        public int andesiteSize;
        public int andesiteCount;
        public int andesiteMinHeight;
        public int andesiteMaxHeight;
        public int coalSize;
        public int coalCount;
        public int coalMinHeight;
        public int coalMaxHeight;
        public int ironSize;
        public int ironCount;
        public int ironMinHeight;
        public int ironMaxHeight;
        public int goldSize;
        public int goldCount;
        public int goldMinHeight;
        public int goldMaxHeight;
        public int redstoneSize;
        public int redstoneCount;
        public int redstoneMinHeight;
        public int redstoneMaxHeight;
        public int diamondSize;
        public int diamondCount;
        public int diamondMinHeight;
        public int diamondMaxHeight;
        public int lapisSize;
        public int lapisCount;
        public int lapisCenterHeight;
        public int lapisSpread;
        
        public static Factory jsonToFactory(final String p_177865_0_) {
            if (p_177865_0_.length() == 0) {
                return new Factory();
            }
            try {
                return Factory.JSON_ADAPTER.fromJson(p_177865_0_, Factory.class);
            }
            catch (Exception var2) {
                return new Factory();
            }
        }
        
        @Override
        public String toString() {
            return Factory.JSON_ADAPTER.toJson(this);
        }
        
        public Factory() {
            this.coordinateScale = 684.412f;
            this.heightScale = 684.412f;
            this.upperLimitScale = 512.0f;
            this.lowerLimitScale = 512.0f;
            this.depthNoiseScaleX = 200.0f;
            this.depthNoiseScaleZ = 200.0f;
            this.depthNoiseScaleExponent = 0.5f;
            this.mainNoiseScaleX = 80.0f;
            this.mainNoiseScaleY = 160.0f;
            this.mainNoiseScaleZ = 80.0f;
            this.baseSize = 8.5f;
            this.stretchY = 12.0f;
            this.biomeDepthWeight = 1.0f;
            this.biomeDepthOffset = 0.0f;
            this.biomeScaleWeight = 1.0f;
            this.biomeScaleOffset = 0.0f;
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
            this.func_177863_a();
        }
        
        public void func_177863_a() {
            this.coordinateScale = 684.412f;
            this.heightScale = 684.412f;
            this.upperLimitScale = 512.0f;
            this.lowerLimitScale = 512.0f;
            this.depthNoiseScaleX = 200.0f;
            this.depthNoiseScaleZ = 200.0f;
            this.depthNoiseScaleExponent = 0.5f;
            this.mainNoiseScaleX = 80.0f;
            this.mainNoiseScaleY = 160.0f;
            this.mainNoiseScaleZ = 80.0f;
            this.baseSize = 8.5f;
            this.stretchY = 12.0f;
            this.biomeDepthWeight = 1.0f;
            this.biomeDepthOffset = 0.0f;
            this.biomeScaleWeight = 1.0f;
            this.biomeScaleOffset = 0.0f;
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
        
        @Override
        public boolean equals(final Object p_equals_1_) {
            if (this == p_equals_1_) {
                return true;
            }
            if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
                final Factory chunkprovidersettingsfactory = (Factory)p_equals_1_;
                return this.andesiteCount == chunkprovidersettingsfactory.andesiteCount && this.andesiteMaxHeight == chunkprovidersettingsfactory.andesiteMaxHeight && this.andesiteMinHeight == chunkprovidersettingsfactory.andesiteMinHeight && this.andesiteSize == chunkprovidersettingsfactory.andesiteSize && Float.compare(chunkprovidersettingsfactory.baseSize, this.baseSize) == 0 && Float.compare(chunkprovidersettingsfactory.biomeDepthOffset, this.biomeDepthOffset) == 0 && Float.compare(chunkprovidersettingsfactory.biomeDepthWeight, this.biomeDepthWeight) == 0 && Float.compare(chunkprovidersettingsfactory.biomeScaleOffset, this.biomeScaleOffset) == 0 && Float.compare(chunkprovidersettingsfactory.biomeScaleWeight, this.biomeScaleWeight) == 0 && this.biomeSize == chunkprovidersettingsfactory.biomeSize && this.coalCount == chunkprovidersettingsfactory.coalCount && this.coalMaxHeight == chunkprovidersettingsfactory.coalMaxHeight && this.coalMinHeight == chunkprovidersettingsfactory.coalMinHeight && this.coalSize == chunkprovidersettingsfactory.coalSize && Float.compare(chunkprovidersettingsfactory.coordinateScale, this.coordinateScale) == 0 && Float.compare(chunkprovidersettingsfactory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) == 0 && Float.compare(chunkprovidersettingsfactory.depthNoiseScaleX, this.depthNoiseScaleX) == 0 && Float.compare(chunkprovidersettingsfactory.depthNoiseScaleZ, this.depthNoiseScaleZ) == 0 && this.diamondCount == chunkprovidersettingsfactory.diamondCount && this.diamondMaxHeight == chunkprovidersettingsfactory.diamondMaxHeight && this.diamondMinHeight == chunkprovidersettingsfactory.diamondMinHeight && this.diamondSize == chunkprovidersettingsfactory.diamondSize && this.dioriteCount == chunkprovidersettingsfactory.dioriteCount && this.dioriteMaxHeight == chunkprovidersettingsfactory.dioriteMaxHeight && this.dioriteMinHeight == chunkprovidersettingsfactory.dioriteMinHeight && this.dioriteSize == chunkprovidersettingsfactory.dioriteSize && this.dirtCount == chunkprovidersettingsfactory.dirtCount && this.dirtMaxHeight == chunkprovidersettingsfactory.dirtMaxHeight && this.dirtMinHeight == chunkprovidersettingsfactory.dirtMinHeight && this.dirtSize == chunkprovidersettingsfactory.dirtSize && this.dungeonChance == chunkprovidersettingsfactory.dungeonChance && this.fixedBiome == chunkprovidersettingsfactory.fixedBiome && this.goldCount == chunkprovidersettingsfactory.goldCount && this.goldMaxHeight == chunkprovidersettingsfactory.goldMaxHeight && this.goldMinHeight == chunkprovidersettingsfactory.goldMinHeight && this.goldSize == chunkprovidersettingsfactory.goldSize && this.graniteCount == chunkprovidersettingsfactory.graniteCount && this.graniteMaxHeight == chunkprovidersettingsfactory.graniteMaxHeight && this.graniteMinHeight == chunkprovidersettingsfactory.graniteMinHeight && this.graniteSize == chunkprovidersettingsfactory.graniteSize && this.gravelCount == chunkprovidersettingsfactory.gravelCount && this.gravelMaxHeight == chunkprovidersettingsfactory.gravelMaxHeight && this.gravelMinHeight == chunkprovidersettingsfactory.gravelMinHeight && this.gravelSize == chunkprovidersettingsfactory.gravelSize && Float.compare(chunkprovidersettingsfactory.heightScale, this.heightScale) == 0 && this.ironCount == chunkprovidersettingsfactory.ironCount && this.ironMaxHeight == chunkprovidersettingsfactory.ironMaxHeight && this.ironMinHeight == chunkprovidersettingsfactory.ironMinHeight && this.ironSize == chunkprovidersettingsfactory.ironSize && this.lapisCenterHeight == chunkprovidersettingsfactory.lapisCenterHeight && this.lapisCount == chunkprovidersettingsfactory.lapisCount && this.lapisSize == chunkprovidersettingsfactory.lapisSize && this.lapisSpread == chunkprovidersettingsfactory.lapisSpread && this.lavaLakeChance == chunkprovidersettingsfactory.lavaLakeChance && Float.compare(chunkprovidersettingsfactory.lowerLimitScale, this.lowerLimitScale) == 0 && Float.compare(chunkprovidersettingsfactory.mainNoiseScaleX, this.mainNoiseScaleX) == 0 && Float.compare(chunkprovidersettingsfactory.mainNoiseScaleY, this.mainNoiseScaleY) == 0 && Float.compare(chunkprovidersettingsfactory.mainNoiseScaleZ, this.mainNoiseScaleZ) == 0 && this.redstoneCount == chunkprovidersettingsfactory.redstoneCount && this.redstoneMaxHeight == chunkprovidersettingsfactory.redstoneMaxHeight && this.redstoneMinHeight == chunkprovidersettingsfactory.redstoneMinHeight && this.redstoneSize == chunkprovidersettingsfactory.redstoneSize && this.riverSize == chunkprovidersettingsfactory.riverSize && this.seaLevel == chunkprovidersettingsfactory.seaLevel && Float.compare(chunkprovidersettingsfactory.stretchY, this.stretchY) == 0 && Float.compare(chunkprovidersettingsfactory.upperLimitScale, this.upperLimitScale) == 0 && this.useCaves == chunkprovidersettingsfactory.useCaves && this.useDungeons == chunkprovidersettingsfactory.useDungeons && this.useLavaLakes == chunkprovidersettingsfactory.useLavaLakes && this.useLavaOceans == chunkprovidersettingsfactory.useLavaOceans && this.useMineShafts == chunkprovidersettingsfactory.useMineShafts && this.useRavines == chunkprovidersettingsfactory.useRavines && this.useStrongholds == chunkprovidersettingsfactory.useStrongholds && this.useTemples == chunkprovidersettingsfactory.useTemples && this.useMonuments == chunkprovidersettingsfactory.useMonuments && this.useVillages == chunkprovidersettingsfactory.useVillages && this.useWaterLakes == chunkprovidersettingsfactory.useWaterLakes && this.waterLakeChance == chunkprovidersettingsfactory.waterLakeChance;
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            int i = (this.coordinateScale != 0.0f) ? Float.floatToIntBits(this.coordinateScale) : 0;
            i = 31 * i + ((this.heightScale != 0.0f) ? Float.floatToIntBits(this.heightScale) : 0);
            i = 31 * i + ((this.upperLimitScale != 0.0f) ? Float.floatToIntBits(this.upperLimitScale) : 0);
            i = 31 * i + ((this.lowerLimitScale != 0.0f) ? Float.floatToIntBits(this.lowerLimitScale) : 0);
            i = 31 * i + ((this.depthNoiseScaleX != 0.0f) ? Float.floatToIntBits(this.depthNoiseScaleX) : 0);
            i = 31 * i + ((this.depthNoiseScaleZ != 0.0f) ? Float.floatToIntBits(this.depthNoiseScaleZ) : 0);
            i = 31 * i + ((this.depthNoiseScaleExponent != 0.0f) ? Float.floatToIntBits(this.depthNoiseScaleExponent) : 0);
            i = 31 * i + ((this.mainNoiseScaleX != 0.0f) ? Float.floatToIntBits(this.mainNoiseScaleX) : 0);
            i = 31 * i + ((this.mainNoiseScaleY != 0.0f) ? Float.floatToIntBits(this.mainNoiseScaleY) : 0);
            i = 31 * i + ((this.mainNoiseScaleZ != 0.0f) ? Float.floatToIntBits(this.mainNoiseScaleZ) : 0);
            i = 31 * i + ((this.baseSize != 0.0f) ? Float.floatToIntBits(this.baseSize) : 0);
            i = 31 * i + ((this.stretchY != 0.0f) ? Float.floatToIntBits(this.stretchY) : 0);
            i = 31 * i + ((this.biomeDepthWeight != 0.0f) ? Float.floatToIntBits(this.biomeDepthWeight) : 0);
            i = 31 * i + ((this.biomeDepthOffset != 0.0f) ? Float.floatToIntBits(this.biomeDepthOffset) : 0);
            i = 31 * i + ((this.biomeScaleWeight != 0.0f) ? Float.floatToIntBits(this.biomeScaleWeight) : 0);
            i = 31 * i + ((this.biomeScaleOffset != 0.0f) ? Float.floatToIntBits(this.biomeScaleOffset) : 0);
            i = 31 * i + this.seaLevel;
            i = 31 * i + (this.useCaves ? 1 : 0);
            i = 31 * i + (this.useDungeons ? 1 : 0);
            i = 31 * i + this.dungeonChance;
            i = 31 * i + (this.useStrongholds ? 1 : 0);
            i = 31 * i + (this.useVillages ? 1 : 0);
            i = 31 * i + (this.useMineShafts ? 1 : 0);
            i = 31 * i + (this.useTemples ? 1 : 0);
            i = 31 * i + (this.useMonuments ? 1 : 0);
            i = 31 * i + (this.useRavines ? 1 : 0);
            i = 31 * i + (this.useWaterLakes ? 1 : 0);
            i = 31 * i + this.waterLakeChance;
            i = 31 * i + (this.useLavaLakes ? 1 : 0);
            i = 31 * i + this.lavaLakeChance;
            i = 31 * i + (this.useLavaOceans ? 1 : 0);
            i = 31 * i + this.fixedBiome;
            i = 31 * i + this.biomeSize;
            i = 31 * i + this.riverSize;
            i = 31 * i + this.dirtSize;
            i = 31 * i + this.dirtCount;
            i = 31 * i + this.dirtMinHeight;
            i = 31 * i + this.dirtMaxHeight;
            i = 31 * i + this.gravelSize;
            i = 31 * i + this.gravelCount;
            i = 31 * i + this.gravelMinHeight;
            i = 31 * i + this.gravelMaxHeight;
            i = 31 * i + this.graniteSize;
            i = 31 * i + this.graniteCount;
            i = 31 * i + this.graniteMinHeight;
            i = 31 * i + this.graniteMaxHeight;
            i = 31 * i + this.dioriteSize;
            i = 31 * i + this.dioriteCount;
            i = 31 * i + this.dioriteMinHeight;
            i = 31 * i + this.dioriteMaxHeight;
            i = 31 * i + this.andesiteSize;
            i = 31 * i + this.andesiteCount;
            i = 31 * i + this.andesiteMinHeight;
            i = 31 * i + this.andesiteMaxHeight;
            i = 31 * i + this.coalSize;
            i = 31 * i + this.coalCount;
            i = 31 * i + this.coalMinHeight;
            i = 31 * i + this.coalMaxHeight;
            i = 31 * i + this.ironSize;
            i = 31 * i + this.ironCount;
            i = 31 * i + this.ironMinHeight;
            i = 31 * i + this.ironMaxHeight;
            i = 31 * i + this.goldSize;
            i = 31 * i + this.goldCount;
            i = 31 * i + this.goldMinHeight;
            i = 31 * i + this.goldMaxHeight;
            i = 31 * i + this.redstoneSize;
            i = 31 * i + this.redstoneCount;
            i = 31 * i + this.redstoneMinHeight;
            i = 31 * i + this.redstoneMaxHeight;
            i = 31 * i + this.diamondSize;
            i = 31 * i + this.diamondCount;
            i = 31 * i + this.diamondMinHeight;
            i = 31 * i + this.diamondMaxHeight;
            i = 31 * i + this.lapisSize;
            i = 31 * i + this.lapisCount;
            i = 31 * i + this.lapisCenterHeight;
            i = 31 * i + this.lapisSpread;
            return i;
        }
        
        public ChunkProviderSettings func_177864_b() {
            return new ChunkProviderSettings(this, null);
        }
        
        static {
            JSON_ADAPTER = new GsonBuilder().registerTypeAdapter(Factory.class, new Serializer()).create();
        }
    }
    
    public static class Serializer implements JsonDeserializer<Factory>, JsonSerializer<Factory>
    {
        @Override
        public Factory deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            final JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            final Factory chunkprovidersettingsfactory = new Factory();
            try {
                chunkprovidersettingsfactory.coordinateScale = JsonUtils.getFloat(jsonobject, "coordinateScale", chunkprovidersettingsfactory.coordinateScale);
                chunkprovidersettingsfactory.heightScale = JsonUtils.getFloat(jsonobject, "heightScale", chunkprovidersettingsfactory.heightScale);
                chunkprovidersettingsfactory.lowerLimitScale = JsonUtils.getFloat(jsonobject, "lowerLimitScale", chunkprovidersettingsfactory.lowerLimitScale);
                chunkprovidersettingsfactory.upperLimitScale = JsonUtils.getFloat(jsonobject, "upperLimitScale", chunkprovidersettingsfactory.upperLimitScale);
                chunkprovidersettingsfactory.depthNoiseScaleX = JsonUtils.getFloat(jsonobject, "depthNoiseScaleX", chunkprovidersettingsfactory.depthNoiseScaleX);
                chunkprovidersettingsfactory.depthNoiseScaleZ = JsonUtils.getFloat(jsonobject, "depthNoiseScaleZ", chunkprovidersettingsfactory.depthNoiseScaleZ);
                chunkprovidersettingsfactory.depthNoiseScaleExponent = JsonUtils.getFloat(jsonobject, "depthNoiseScaleExponent", chunkprovidersettingsfactory.depthNoiseScaleExponent);
                chunkprovidersettingsfactory.mainNoiseScaleX = JsonUtils.getFloat(jsonobject, "mainNoiseScaleX", chunkprovidersettingsfactory.mainNoiseScaleX);
                chunkprovidersettingsfactory.mainNoiseScaleY = JsonUtils.getFloat(jsonobject, "mainNoiseScaleY", chunkprovidersettingsfactory.mainNoiseScaleY);
                chunkprovidersettingsfactory.mainNoiseScaleZ = JsonUtils.getFloat(jsonobject, "mainNoiseScaleZ", chunkprovidersettingsfactory.mainNoiseScaleZ);
                chunkprovidersettingsfactory.baseSize = JsonUtils.getFloat(jsonobject, "baseSize", chunkprovidersettingsfactory.baseSize);
                chunkprovidersettingsfactory.stretchY = JsonUtils.getFloat(jsonobject, "stretchY", chunkprovidersettingsfactory.stretchY);
                chunkprovidersettingsfactory.biomeDepthWeight = JsonUtils.getFloat(jsonobject, "biomeDepthWeight", chunkprovidersettingsfactory.biomeDepthWeight);
                chunkprovidersettingsfactory.biomeDepthOffset = JsonUtils.getFloat(jsonobject, "biomeDepthOffset", chunkprovidersettingsfactory.biomeDepthOffset);
                chunkprovidersettingsfactory.biomeScaleWeight = JsonUtils.getFloat(jsonobject, "biomeScaleWeight", chunkprovidersettingsfactory.biomeScaleWeight);
                chunkprovidersettingsfactory.biomeScaleOffset = JsonUtils.getFloat(jsonobject, "biomeScaleOffset", chunkprovidersettingsfactory.biomeScaleOffset);
                chunkprovidersettingsfactory.seaLevel = JsonUtils.getInt(jsonobject, "seaLevel", chunkprovidersettingsfactory.seaLevel);
                chunkprovidersettingsfactory.useCaves = JsonUtils.getBoolean(jsonobject, "useCaves", chunkprovidersettingsfactory.useCaves);
                chunkprovidersettingsfactory.useDungeons = JsonUtils.getBoolean(jsonobject, "useDungeons", chunkprovidersettingsfactory.useDungeons);
                chunkprovidersettingsfactory.dungeonChance = JsonUtils.getInt(jsonobject, "dungeonChance", chunkprovidersettingsfactory.dungeonChance);
                chunkprovidersettingsfactory.useStrongholds = JsonUtils.getBoolean(jsonobject, "useStrongholds", chunkprovidersettingsfactory.useStrongholds);
                chunkprovidersettingsfactory.useVillages = JsonUtils.getBoolean(jsonobject, "useVillages", chunkprovidersettingsfactory.useVillages);
                chunkprovidersettingsfactory.useMineShafts = JsonUtils.getBoolean(jsonobject, "useMineShafts", chunkprovidersettingsfactory.useMineShafts);
                chunkprovidersettingsfactory.useTemples = JsonUtils.getBoolean(jsonobject, "useTemples", chunkprovidersettingsfactory.useTemples);
                chunkprovidersettingsfactory.useMonuments = JsonUtils.getBoolean(jsonobject, "useMonuments", chunkprovidersettingsfactory.useMonuments);
                chunkprovidersettingsfactory.useRavines = JsonUtils.getBoolean(jsonobject, "useRavines", chunkprovidersettingsfactory.useRavines);
                chunkprovidersettingsfactory.useWaterLakes = JsonUtils.getBoolean(jsonobject, "useWaterLakes", chunkprovidersettingsfactory.useWaterLakes);
                chunkprovidersettingsfactory.waterLakeChance = JsonUtils.getInt(jsonobject, "waterLakeChance", chunkprovidersettingsfactory.waterLakeChance);
                chunkprovidersettingsfactory.useLavaLakes = JsonUtils.getBoolean(jsonobject, "useLavaLakes", chunkprovidersettingsfactory.useLavaLakes);
                chunkprovidersettingsfactory.lavaLakeChance = JsonUtils.getInt(jsonobject, "lavaLakeChance", chunkprovidersettingsfactory.lavaLakeChance);
                chunkprovidersettingsfactory.useLavaOceans = JsonUtils.getBoolean(jsonobject, "useLavaOceans", chunkprovidersettingsfactory.useLavaOceans);
                chunkprovidersettingsfactory.fixedBiome = JsonUtils.getInt(jsonobject, "fixedBiome", chunkprovidersettingsfactory.fixedBiome);
                if (chunkprovidersettingsfactory.fixedBiome < 38 && chunkprovidersettingsfactory.fixedBiome >= -1) {
                    if (chunkprovidersettingsfactory.fixedBiome >= BiomeGenBase.hell.biomeID) {
                        final Factory factory = chunkprovidersettingsfactory;
                        factory.fixedBiome += 2;
                    }
                }
                else {
                    chunkprovidersettingsfactory.fixedBiome = -1;
                }
                chunkprovidersettingsfactory.biomeSize = JsonUtils.getInt(jsonobject, "biomeSize", chunkprovidersettingsfactory.biomeSize);
                chunkprovidersettingsfactory.riverSize = JsonUtils.getInt(jsonobject, "riverSize", chunkprovidersettingsfactory.riverSize);
                chunkprovidersettingsfactory.dirtSize = JsonUtils.getInt(jsonobject, "dirtSize", chunkprovidersettingsfactory.dirtSize);
                chunkprovidersettingsfactory.dirtCount = JsonUtils.getInt(jsonobject, "dirtCount", chunkprovidersettingsfactory.dirtCount);
                chunkprovidersettingsfactory.dirtMinHeight = JsonUtils.getInt(jsonobject, "dirtMinHeight", chunkprovidersettingsfactory.dirtMinHeight);
                chunkprovidersettingsfactory.dirtMaxHeight = JsonUtils.getInt(jsonobject, "dirtMaxHeight", chunkprovidersettingsfactory.dirtMaxHeight);
                chunkprovidersettingsfactory.gravelSize = JsonUtils.getInt(jsonobject, "gravelSize", chunkprovidersettingsfactory.gravelSize);
                chunkprovidersettingsfactory.gravelCount = JsonUtils.getInt(jsonobject, "gravelCount", chunkprovidersettingsfactory.gravelCount);
                chunkprovidersettingsfactory.gravelMinHeight = JsonUtils.getInt(jsonobject, "gravelMinHeight", chunkprovidersettingsfactory.gravelMinHeight);
                chunkprovidersettingsfactory.gravelMaxHeight = JsonUtils.getInt(jsonobject, "gravelMaxHeight", chunkprovidersettingsfactory.gravelMaxHeight);
                chunkprovidersettingsfactory.graniteSize = JsonUtils.getInt(jsonobject, "graniteSize", chunkprovidersettingsfactory.graniteSize);
                chunkprovidersettingsfactory.graniteCount = JsonUtils.getInt(jsonobject, "graniteCount", chunkprovidersettingsfactory.graniteCount);
                chunkprovidersettingsfactory.graniteMinHeight = JsonUtils.getInt(jsonobject, "graniteMinHeight", chunkprovidersettingsfactory.graniteMinHeight);
                chunkprovidersettingsfactory.graniteMaxHeight = JsonUtils.getInt(jsonobject, "graniteMaxHeight", chunkprovidersettingsfactory.graniteMaxHeight);
                chunkprovidersettingsfactory.dioriteSize = JsonUtils.getInt(jsonobject, "dioriteSize", chunkprovidersettingsfactory.dioriteSize);
                chunkprovidersettingsfactory.dioriteCount = JsonUtils.getInt(jsonobject, "dioriteCount", chunkprovidersettingsfactory.dioriteCount);
                chunkprovidersettingsfactory.dioriteMinHeight = JsonUtils.getInt(jsonobject, "dioriteMinHeight", chunkprovidersettingsfactory.dioriteMinHeight);
                chunkprovidersettingsfactory.dioriteMaxHeight = JsonUtils.getInt(jsonobject, "dioriteMaxHeight", chunkprovidersettingsfactory.dioriteMaxHeight);
                chunkprovidersettingsfactory.andesiteSize = JsonUtils.getInt(jsonobject, "andesiteSize", chunkprovidersettingsfactory.andesiteSize);
                chunkprovidersettingsfactory.andesiteCount = JsonUtils.getInt(jsonobject, "andesiteCount", chunkprovidersettingsfactory.andesiteCount);
                chunkprovidersettingsfactory.andesiteMinHeight = JsonUtils.getInt(jsonobject, "andesiteMinHeight", chunkprovidersettingsfactory.andesiteMinHeight);
                chunkprovidersettingsfactory.andesiteMaxHeight = JsonUtils.getInt(jsonobject, "andesiteMaxHeight", chunkprovidersettingsfactory.andesiteMaxHeight);
                chunkprovidersettingsfactory.coalSize = JsonUtils.getInt(jsonobject, "coalSize", chunkprovidersettingsfactory.coalSize);
                chunkprovidersettingsfactory.coalCount = JsonUtils.getInt(jsonobject, "coalCount", chunkprovidersettingsfactory.coalCount);
                chunkprovidersettingsfactory.coalMinHeight = JsonUtils.getInt(jsonobject, "coalMinHeight", chunkprovidersettingsfactory.coalMinHeight);
                chunkprovidersettingsfactory.coalMaxHeight = JsonUtils.getInt(jsonobject, "coalMaxHeight", chunkprovidersettingsfactory.coalMaxHeight);
                chunkprovidersettingsfactory.ironSize = JsonUtils.getInt(jsonobject, "ironSize", chunkprovidersettingsfactory.ironSize);
                chunkprovidersettingsfactory.ironCount = JsonUtils.getInt(jsonobject, "ironCount", chunkprovidersettingsfactory.ironCount);
                chunkprovidersettingsfactory.ironMinHeight = JsonUtils.getInt(jsonobject, "ironMinHeight", chunkprovidersettingsfactory.ironMinHeight);
                chunkprovidersettingsfactory.ironMaxHeight = JsonUtils.getInt(jsonobject, "ironMaxHeight", chunkprovidersettingsfactory.ironMaxHeight);
                chunkprovidersettingsfactory.goldSize = JsonUtils.getInt(jsonobject, "goldSize", chunkprovidersettingsfactory.goldSize);
                chunkprovidersettingsfactory.goldCount = JsonUtils.getInt(jsonobject, "goldCount", chunkprovidersettingsfactory.goldCount);
                chunkprovidersettingsfactory.goldMinHeight = JsonUtils.getInt(jsonobject, "goldMinHeight", chunkprovidersettingsfactory.goldMinHeight);
                chunkprovidersettingsfactory.goldMaxHeight = JsonUtils.getInt(jsonobject, "goldMaxHeight", chunkprovidersettingsfactory.goldMaxHeight);
                chunkprovidersettingsfactory.redstoneSize = JsonUtils.getInt(jsonobject, "redstoneSize", chunkprovidersettingsfactory.redstoneSize);
                chunkprovidersettingsfactory.redstoneCount = JsonUtils.getInt(jsonobject, "redstoneCount", chunkprovidersettingsfactory.redstoneCount);
                chunkprovidersettingsfactory.redstoneMinHeight = JsonUtils.getInt(jsonobject, "redstoneMinHeight", chunkprovidersettingsfactory.redstoneMinHeight);
                chunkprovidersettingsfactory.redstoneMaxHeight = JsonUtils.getInt(jsonobject, "redstoneMaxHeight", chunkprovidersettingsfactory.redstoneMaxHeight);
                chunkprovidersettingsfactory.diamondSize = JsonUtils.getInt(jsonobject, "diamondSize", chunkprovidersettingsfactory.diamondSize);
                chunkprovidersettingsfactory.diamondCount = JsonUtils.getInt(jsonobject, "diamondCount", chunkprovidersettingsfactory.diamondCount);
                chunkprovidersettingsfactory.diamondMinHeight = JsonUtils.getInt(jsonobject, "diamondMinHeight", chunkprovidersettingsfactory.diamondMinHeight);
                chunkprovidersettingsfactory.diamondMaxHeight = JsonUtils.getInt(jsonobject, "diamondMaxHeight", chunkprovidersettingsfactory.diamondMaxHeight);
                chunkprovidersettingsfactory.lapisSize = JsonUtils.getInt(jsonobject, "lapisSize", chunkprovidersettingsfactory.lapisSize);
                chunkprovidersettingsfactory.lapisCount = JsonUtils.getInt(jsonobject, "lapisCount", chunkprovidersettingsfactory.lapisCount);
                chunkprovidersettingsfactory.lapisCenterHeight = JsonUtils.getInt(jsonobject, "lapisCenterHeight", chunkprovidersettingsfactory.lapisCenterHeight);
                chunkprovidersettingsfactory.lapisSpread = JsonUtils.getInt(jsonobject, "lapisSpread", chunkprovidersettingsfactory.lapisSpread);
            }
            catch (Exception ex) {}
            return chunkprovidersettingsfactory;
        }
        
        @Override
        public JsonElement serialize(final Factory p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            final JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("coordinateScale", p_serialize_1_.coordinateScale);
            jsonobject.addProperty("heightScale", p_serialize_1_.heightScale);
            jsonobject.addProperty("lowerLimitScale", p_serialize_1_.lowerLimitScale);
            jsonobject.addProperty("upperLimitScale", p_serialize_1_.upperLimitScale);
            jsonobject.addProperty("depthNoiseScaleX", p_serialize_1_.depthNoiseScaleX);
            jsonobject.addProperty("depthNoiseScaleZ", p_serialize_1_.depthNoiseScaleZ);
            jsonobject.addProperty("depthNoiseScaleExponent", p_serialize_1_.depthNoiseScaleExponent);
            jsonobject.addProperty("mainNoiseScaleX", p_serialize_1_.mainNoiseScaleX);
            jsonobject.addProperty("mainNoiseScaleY", p_serialize_1_.mainNoiseScaleY);
            jsonobject.addProperty("mainNoiseScaleZ", p_serialize_1_.mainNoiseScaleZ);
            jsonobject.addProperty("baseSize", p_serialize_1_.baseSize);
            jsonobject.addProperty("stretchY", p_serialize_1_.stretchY);
            jsonobject.addProperty("biomeDepthWeight", p_serialize_1_.biomeDepthWeight);
            jsonobject.addProperty("biomeDepthOffset", p_serialize_1_.biomeDepthOffset);
            jsonobject.addProperty("biomeScaleWeight", p_serialize_1_.biomeScaleWeight);
            jsonobject.addProperty("biomeScaleOffset", p_serialize_1_.biomeScaleOffset);
            jsonobject.addProperty("seaLevel", p_serialize_1_.seaLevel);
            jsonobject.addProperty("useCaves", p_serialize_1_.useCaves);
            jsonobject.addProperty("useDungeons", p_serialize_1_.useDungeons);
            jsonobject.addProperty("dungeonChance", p_serialize_1_.dungeonChance);
            jsonobject.addProperty("useStrongholds", p_serialize_1_.useStrongholds);
            jsonobject.addProperty("useVillages", p_serialize_1_.useVillages);
            jsonobject.addProperty("useMineShafts", p_serialize_1_.useMineShafts);
            jsonobject.addProperty("useTemples", p_serialize_1_.useTemples);
            jsonobject.addProperty("useMonuments", p_serialize_1_.useMonuments);
            jsonobject.addProperty("useRavines", p_serialize_1_.useRavines);
            jsonobject.addProperty("useWaterLakes", p_serialize_1_.useWaterLakes);
            jsonobject.addProperty("waterLakeChance", p_serialize_1_.waterLakeChance);
            jsonobject.addProperty("useLavaLakes", p_serialize_1_.useLavaLakes);
            jsonobject.addProperty("lavaLakeChance", p_serialize_1_.lavaLakeChance);
            jsonobject.addProperty("useLavaOceans", p_serialize_1_.useLavaOceans);
            jsonobject.addProperty("fixedBiome", p_serialize_1_.fixedBiome);
            jsonobject.addProperty("biomeSize", p_serialize_1_.biomeSize);
            jsonobject.addProperty("riverSize", p_serialize_1_.riverSize);
            jsonobject.addProperty("dirtSize", p_serialize_1_.dirtSize);
            jsonobject.addProperty("dirtCount", p_serialize_1_.dirtCount);
            jsonobject.addProperty("dirtMinHeight", p_serialize_1_.dirtMinHeight);
            jsonobject.addProperty("dirtMaxHeight", p_serialize_1_.dirtMaxHeight);
            jsonobject.addProperty("gravelSize", p_serialize_1_.gravelSize);
            jsonobject.addProperty("gravelCount", p_serialize_1_.gravelCount);
            jsonobject.addProperty("gravelMinHeight", p_serialize_1_.gravelMinHeight);
            jsonobject.addProperty("gravelMaxHeight", p_serialize_1_.gravelMaxHeight);
            jsonobject.addProperty("graniteSize", p_serialize_1_.graniteSize);
            jsonobject.addProperty("graniteCount", p_serialize_1_.graniteCount);
            jsonobject.addProperty("graniteMinHeight", p_serialize_1_.graniteMinHeight);
            jsonobject.addProperty("graniteMaxHeight", p_serialize_1_.graniteMaxHeight);
            jsonobject.addProperty("dioriteSize", p_serialize_1_.dioriteSize);
            jsonobject.addProperty("dioriteCount", p_serialize_1_.dioriteCount);
            jsonobject.addProperty("dioriteMinHeight", p_serialize_1_.dioriteMinHeight);
            jsonobject.addProperty("dioriteMaxHeight", p_serialize_1_.dioriteMaxHeight);
            jsonobject.addProperty("andesiteSize", p_serialize_1_.andesiteSize);
            jsonobject.addProperty("andesiteCount", p_serialize_1_.andesiteCount);
            jsonobject.addProperty("andesiteMinHeight", p_serialize_1_.andesiteMinHeight);
            jsonobject.addProperty("andesiteMaxHeight", p_serialize_1_.andesiteMaxHeight);
            jsonobject.addProperty("coalSize", p_serialize_1_.coalSize);
            jsonobject.addProperty("coalCount", p_serialize_1_.coalCount);
            jsonobject.addProperty("coalMinHeight", p_serialize_1_.coalMinHeight);
            jsonobject.addProperty("coalMaxHeight", p_serialize_1_.coalMaxHeight);
            jsonobject.addProperty("ironSize", p_serialize_1_.ironSize);
            jsonobject.addProperty("ironCount", p_serialize_1_.ironCount);
            jsonobject.addProperty("ironMinHeight", p_serialize_1_.ironMinHeight);
            jsonobject.addProperty("ironMaxHeight", p_serialize_1_.ironMaxHeight);
            jsonobject.addProperty("goldSize", p_serialize_1_.goldSize);
            jsonobject.addProperty("goldCount", p_serialize_1_.goldCount);
            jsonobject.addProperty("goldMinHeight", p_serialize_1_.goldMinHeight);
            jsonobject.addProperty("goldMaxHeight", p_serialize_1_.goldMaxHeight);
            jsonobject.addProperty("redstoneSize", p_serialize_1_.redstoneSize);
            jsonobject.addProperty("redstoneCount", p_serialize_1_.redstoneCount);
            jsonobject.addProperty("redstoneMinHeight", p_serialize_1_.redstoneMinHeight);
            jsonobject.addProperty("redstoneMaxHeight", p_serialize_1_.redstoneMaxHeight);
            jsonobject.addProperty("diamondSize", p_serialize_1_.diamondSize);
            jsonobject.addProperty("diamondCount", p_serialize_1_.diamondCount);
            jsonobject.addProperty("diamondMinHeight", p_serialize_1_.diamondMinHeight);
            jsonobject.addProperty("diamondMaxHeight", p_serialize_1_.diamondMaxHeight);
            jsonobject.addProperty("lapisSize", p_serialize_1_.lapisSize);
            jsonobject.addProperty("lapisCount", p_serialize_1_.lapisCount);
            jsonobject.addProperty("lapisCenterHeight", p_serialize_1_.lapisCenterHeight);
            jsonobject.addProperty("lapisSpread", p_serialize_1_.lapisSpread);
            return jsonobject;
        }
    }
}
