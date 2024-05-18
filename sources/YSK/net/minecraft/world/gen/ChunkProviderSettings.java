package net.minecraft.world.gen;

import java.lang.reflect.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;
import com.google.gson.*;

public class ChunkProviderSettings
{
    public final int lapisSize;
    public final boolean useMonuments;
    public final int graniteCount;
    public final int ironMaxHeight;
    public final int coalSize;
    public final int goldSize;
    public final int dioriteMaxHeight;
    public final int dirtMaxHeight;
    public final boolean useCaves;
    public final int goldMinHeight;
    public final int lavaLakeChance;
    public final int andesiteMaxHeight;
    public final int ironMinHeight;
    public final int diamondSize;
    public final boolean useDungeons;
    public final int goldCount;
    public final int lapisCenterHeight;
    public final int ironCount;
    public final float depthNoiseScaleZ;
    public final int seaLevel;
    public final boolean useStrongholds;
    public final int diamondCount;
    public final boolean useRavines;
    public final int gravelMaxHeight;
    public final int dioriteMinHeight;
    public final int dirtCount;
    public final int lapisSpread;
    public final float mainNoiseScaleZ;
    public final int redstoneCount;
    public final int gravelCount;
    public final int graniteMaxHeight;
    public final int gravelMinHeight;
    public final float depthNoiseScaleX;
    public final float upperLimitScale;
    public final int redstoneSize;
    public final float biomeDepthWeight;
    public final boolean useLavaLakes;
    public final float baseSize;
    public final int coalCount;
    public final int gravelSize;
    public final int diamondMaxHeight;
    public final int graniteMinHeight;
    public final int dirtMinHeight;
    public final int waterLakeChance;
    public final int riverSize;
    public final float lowerLimitScale;
    public final int dirtSize;
    public final int andesiteSize;
    public final int coalMinHeight;
    public final float depthNoiseScaleExponent;
    public final int ironSize;
    public final float biomeScaleWeight;
    public final boolean useWaterLakes;
    public final boolean useLavaOceans;
    public final float mainNoiseScaleY;
    public final float biomeDepthOffSet;
    public final float mainNoiseScaleX;
    public final boolean useTemples;
    public final boolean useVillages;
    public final int graniteSize;
    public final int goldMaxHeight;
    public final int lapisCount;
    public final int dungeonChance;
    public final int andesiteMinHeight;
    public final float heightScale;
    public final int coalMaxHeight;
    public final float coordinateScale;
    public final int dioriteCount;
    public final int diamondMinHeight;
    public final int dioriteSize;
    public final float stretchY;
    public final float biomeScaleOffset;
    public final int biomeSize;
    public final int redstoneMaxHeight;
    public final int andesiteCount;
    public final int redstoneMinHeight;
    public final int fixedBiome;
    public final boolean useMineShafts;
    
    ChunkProviderSettings(final Factory factory, final ChunkProviderSettings chunkProviderSettings) {
        this(factory);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private ChunkProviderSettings(final Factory factory) {
        this.coordinateScale = factory.coordinateScale;
        this.heightScale = factory.heightScale;
        this.upperLimitScale = factory.upperLimitScale;
        this.lowerLimitScale = factory.lowerLimitScale;
        this.depthNoiseScaleX = factory.depthNoiseScaleX;
        this.depthNoiseScaleZ = factory.depthNoiseScaleZ;
        this.depthNoiseScaleExponent = factory.depthNoiseScaleExponent;
        this.mainNoiseScaleX = factory.mainNoiseScaleX;
        this.mainNoiseScaleY = factory.mainNoiseScaleY;
        this.mainNoiseScaleZ = factory.mainNoiseScaleZ;
        this.baseSize = factory.baseSize;
        this.stretchY = factory.stretchY;
        this.biomeDepthWeight = factory.biomeDepthWeight;
        this.biomeDepthOffSet = factory.biomeDepthOffset;
        this.biomeScaleWeight = factory.biomeScaleWeight;
        this.biomeScaleOffset = factory.biomeScaleOffset;
        this.seaLevel = factory.seaLevel;
        this.useCaves = factory.useCaves;
        this.useDungeons = factory.useDungeons;
        this.dungeonChance = factory.dungeonChance;
        this.useStrongholds = factory.useStrongholds;
        this.useVillages = factory.useVillages;
        this.useMineShafts = factory.useMineShafts;
        this.useTemples = factory.useTemples;
        this.useMonuments = factory.useMonuments;
        this.useRavines = factory.useRavines;
        this.useWaterLakes = factory.useWaterLakes;
        this.waterLakeChance = factory.waterLakeChance;
        this.useLavaLakes = factory.useLavaLakes;
        this.lavaLakeChance = factory.lavaLakeChance;
        this.useLavaOceans = factory.useLavaOceans;
        this.fixedBiome = factory.fixedBiome;
        this.biomeSize = factory.biomeSize;
        this.riverSize = factory.riverSize;
        this.dirtSize = factory.dirtSize;
        this.dirtCount = factory.dirtCount;
        this.dirtMinHeight = factory.dirtMinHeight;
        this.dirtMaxHeight = factory.dirtMaxHeight;
        this.gravelSize = factory.gravelSize;
        this.gravelCount = factory.gravelCount;
        this.gravelMinHeight = factory.gravelMinHeight;
        this.gravelMaxHeight = factory.gravelMaxHeight;
        this.graniteSize = factory.graniteSize;
        this.graniteCount = factory.graniteCount;
        this.graniteMinHeight = factory.graniteMinHeight;
        this.graniteMaxHeight = factory.graniteMaxHeight;
        this.dioriteSize = factory.dioriteSize;
        this.dioriteCount = factory.dioriteCount;
        this.dioriteMinHeight = factory.dioriteMinHeight;
        this.dioriteMaxHeight = factory.dioriteMaxHeight;
        this.andesiteSize = factory.andesiteSize;
        this.andesiteCount = factory.andesiteCount;
        this.andesiteMinHeight = factory.andesiteMinHeight;
        this.andesiteMaxHeight = factory.andesiteMaxHeight;
        this.coalSize = factory.coalSize;
        this.coalCount = factory.coalCount;
        this.coalMinHeight = factory.coalMinHeight;
        this.coalMaxHeight = factory.coalMaxHeight;
        this.ironSize = factory.ironSize;
        this.ironCount = factory.ironCount;
        this.ironMinHeight = factory.ironMinHeight;
        this.ironMaxHeight = factory.ironMaxHeight;
        this.goldSize = factory.goldSize;
        this.goldCount = factory.goldCount;
        this.goldMinHeight = factory.goldMinHeight;
        this.goldMaxHeight = factory.goldMaxHeight;
        this.redstoneSize = factory.redstoneSize;
        this.redstoneCount = factory.redstoneCount;
        this.redstoneMinHeight = factory.redstoneMinHeight;
        this.redstoneMaxHeight = factory.redstoneMaxHeight;
        this.diamondSize = factory.diamondSize;
        this.diamondCount = factory.diamondCount;
        this.diamondMinHeight = factory.diamondMinHeight;
        this.diamondMaxHeight = factory.diamondMaxHeight;
        this.lapisSize = factory.lapisSize;
        this.lapisCount = factory.lapisCount;
        this.lapisCenterHeight = factory.lapisCenterHeight;
        this.lapisSpread = factory.lapisSpread;
    }
    
    public static class Serializer implements JsonDeserializer<Factory>, JsonSerializer<Factory>
    {
        private static final String[] I;
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((Factory)o, type, jsonSerializationContext);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
        }
        
        public Factory deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final Factory factory = new Factory();
            try {
                factory.coordinateScale = JsonUtils.getFloat(asJsonObject, Serializer.I["".length()], factory.coordinateScale);
                factory.heightScale = JsonUtils.getFloat(asJsonObject, Serializer.I[" ".length()], factory.heightScale);
                factory.lowerLimitScale = JsonUtils.getFloat(asJsonObject, Serializer.I["  ".length()], factory.lowerLimitScale);
                factory.upperLimitScale = JsonUtils.getFloat(asJsonObject, Serializer.I["   ".length()], factory.upperLimitScale);
                factory.depthNoiseScaleX = JsonUtils.getFloat(asJsonObject, Serializer.I[0xD ^ 0x9], factory.depthNoiseScaleX);
                factory.depthNoiseScaleZ = JsonUtils.getFloat(asJsonObject, Serializer.I[0x5 ^ 0x0], factory.depthNoiseScaleZ);
                factory.depthNoiseScaleExponent = JsonUtils.getFloat(asJsonObject, Serializer.I[0x49 ^ 0x4F], factory.depthNoiseScaleExponent);
                factory.mainNoiseScaleX = JsonUtils.getFloat(asJsonObject, Serializer.I[0x8F ^ 0x88], factory.mainNoiseScaleX);
                factory.mainNoiseScaleY = JsonUtils.getFloat(asJsonObject, Serializer.I[0x47 ^ 0x4F], factory.mainNoiseScaleY);
                factory.mainNoiseScaleZ = JsonUtils.getFloat(asJsonObject, Serializer.I[0xA7 ^ 0xAE], factory.mainNoiseScaleZ);
                factory.baseSize = JsonUtils.getFloat(asJsonObject, Serializer.I[0x90 ^ 0x9A], factory.baseSize);
                factory.stretchY = JsonUtils.getFloat(asJsonObject, Serializer.I[0x7F ^ 0x74], factory.stretchY);
                factory.biomeDepthWeight = JsonUtils.getFloat(asJsonObject, Serializer.I[0x83 ^ 0x8F], factory.biomeDepthWeight);
                factory.biomeDepthOffset = JsonUtils.getFloat(asJsonObject, Serializer.I[0x22 ^ 0x2F], factory.biomeDepthOffset);
                factory.biomeScaleWeight = JsonUtils.getFloat(asJsonObject, Serializer.I[0xC ^ 0x2], factory.biomeScaleWeight);
                factory.biomeScaleOffset = JsonUtils.getFloat(asJsonObject, Serializer.I[0x87 ^ 0x88], factory.biomeScaleOffset);
                factory.seaLevel = JsonUtils.getInt(asJsonObject, Serializer.I[0x4A ^ 0x5A], factory.seaLevel);
                factory.useCaves = JsonUtils.getBoolean(asJsonObject, Serializer.I[0xB1 ^ 0xA0], factory.useCaves);
                factory.useDungeons = JsonUtils.getBoolean(asJsonObject, Serializer.I[0xE ^ 0x1C], factory.useDungeons);
                factory.dungeonChance = JsonUtils.getInt(asJsonObject, Serializer.I[0xB ^ 0x18], factory.dungeonChance);
                factory.useStrongholds = JsonUtils.getBoolean(asJsonObject, Serializer.I[0x1E ^ 0xA], factory.useStrongholds);
                factory.useVillages = JsonUtils.getBoolean(asJsonObject, Serializer.I[0x8A ^ 0x9F], factory.useVillages);
                factory.useMineShafts = JsonUtils.getBoolean(asJsonObject, Serializer.I[0x5E ^ 0x48], factory.useMineShafts);
                factory.useTemples = JsonUtils.getBoolean(asJsonObject, Serializer.I[0x44 ^ 0x53], factory.useTemples);
                factory.useMonuments = JsonUtils.getBoolean(asJsonObject, Serializer.I[0xD8 ^ 0xC0], factory.useMonuments);
                factory.useRavines = JsonUtils.getBoolean(asJsonObject, Serializer.I[0xD9 ^ 0xC0], factory.useRavines);
                factory.useWaterLakes = JsonUtils.getBoolean(asJsonObject, Serializer.I[0x2D ^ 0x37], factory.useWaterLakes);
                factory.waterLakeChance = JsonUtils.getInt(asJsonObject, Serializer.I[0x14 ^ 0xF], factory.waterLakeChance);
                factory.useLavaLakes = JsonUtils.getBoolean(asJsonObject, Serializer.I[0xAA ^ 0xB6], factory.useLavaLakes);
                factory.lavaLakeChance = JsonUtils.getInt(asJsonObject, Serializer.I[0x6A ^ 0x77], factory.lavaLakeChance);
                factory.useLavaOceans = JsonUtils.getBoolean(asJsonObject, Serializer.I[0xBE ^ 0xA0], factory.useLavaOceans);
                factory.fixedBiome = JsonUtils.getInt(asJsonObject, Serializer.I[0x67 ^ 0x78], factory.fixedBiome);
                if (factory.fixedBiome < (0x98 ^ 0xBE) && factory.fixedBiome >= -" ".length()) {
                    if (factory.fixedBiome >= BiomeGenBase.hell.biomeID) {
                        final Factory factory2 = factory;
                        factory2.fixedBiome += "  ".length();
                        "".length();
                        if (4 == 2) {
                            throw null;
                        }
                    }
                }
                else {
                    factory.fixedBiome = -" ".length();
                }
                factory.biomeSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x7 ^ 0x27], factory.biomeSize);
                factory.riverSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x6F ^ 0x4E], factory.riverSize);
                factory.dirtSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x92 ^ 0xB0], factory.dirtSize);
                factory.dirtCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x76 ^ 0x55], factory.dirtCount);
                factory.dirtMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x9D ^ 0xB9], factory.dirtMinHeight);
                factory.dirtMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x87 ^ 0xA2], factory.dirtMaxHeight);
                factory.gravelSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x17 ^ 0x31], factory.gravelSize);
                factory.gravelCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x5F ^ 0x78], factory.gravelCount);
                factory.gravelMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x88 ^ 0xA0], factory.gravelMinHeight);
                factory.gravelMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x41 ^ 0x68], factory.gravelMaxHeight);
                factory.graniteSize = JsonUtils.getInt(asJsonObject, Serializer.I[0xA4 ^ 0x8E], factory.graniteSize);
                factory.graniteCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x32 ^ 0x19], factory.graniteCount);
                factory.graniteMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x75 ^ 0x59], factory.graniteMinHeight);
                factory.graniteMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x2D ^ 0x0], factory.graniteMaxHeight);
                factory.dioriteSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x39 ^ 0x17], factory.dioriteSize);
                factory.dioriteCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x39 ^ 0x16], factory.dioriteCount);
                factory.dioriteMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x21 ^ 0x11], factory.dioriteMinHeight);
                factory.dioriteMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x95 ^ 0xA4], factory.dioriteMaxHeight);
                factory.andesiteSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x91 ^ 0xA3], factory.andesiteSize);
                factory.andesiteCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x1D ^ 0x2E], factory.andesiteCount);
                factory.andesiteMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x3A ^ 0xE], factory.andesiteMinHeight);
                factory.andesiteMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0xC ^ 0x39], factory.andesiteMaxHeight);
                factory.coalSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x6 ^ 0x30], factory.coalSize);
                factory.coalCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x86 ^ 0xB1], factory.coalCount);
                factory.coalMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x5F ^ 0x67], factory.coalMinHeight);
                factory.coalMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x35 ^ 0xC], factory.coalMaxHeight);
                factory.ironSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x64 ^ 0x5E], factory.ironSize);
                factory.ironCount = JsonUtils.getInt(asJsonObject, Serializer.I[0xB0 ^ 0x8B], factory.ironCount);
                factory.ironMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x94 ^ 0xA8], factory.ironMinHeight);
                factory.ironMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0xAF ^ 0x92], factory.ironMaxHeight);
                factory.goldSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x6F ^ 0x51], factory.goldSize);
                factory.goldCount = JsonUtils.getInt(asJsonObject, Serializer.I[0xA3 ^ 0x9C], factory.goldCount);
                factory.goldMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0xE5 ^ 0xA5], factory.goldMinHeight);
                factory.goldMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x4D ^ 0xC], factory.goldMaxHeight);
                factory.redstoneSize = JsonUtils.getInt(asJsonObject, Serializer.I[0xD7 ^ 0x95], factory.redstoneSize);
                factory.redstoneCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x74 ^ 0x37], factory.redstoneCount);
                factory.redstoneMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x23 ^ 0x67], factory.redstoneMinHeight);
                factory.redstoneMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x80 ^ 0xC5], factory.redstoneMaxHeight);
                factory.diamondSize = JsonUtils.getInt(asJsonObject, Serializer.I[0xC2 ^ 0x84], factory.diamondSize);
                factory.diamondCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x62 ^ 0x25], factory.diamondCount);
                factory.diamondMinHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x4D ^ 0x5], factory.diamondMinHeight);
                factory.diamondMaxHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0x60 ^ 0x29], factory.diamondMaxHeight);
                factory.lapisSize = JsonUtils.getInt(asJsonObject, Serializer.I[0x79 ^ 0x33], factory.lapisSize);
                factory.lapisCount = JsonUtils.getInt(asJsonObject, Serializer.I[0x46 ^ 0xD], factory.lapisCount);
                factory.lapisCenterHeight = JsonUtils.getInt(asJsonObject, Serializer.I[0xD6 ^ 0x9A], factory.lapisCenterHeight);
                factory.lapisSpread = JsonUtils.getInt(asJsonObject, Serializer.I[0x4E ^ 0x3], factory.lapisSpread);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            catch (Exception ex) {}
            return factory;
        }
        
        public JsonElement serialize(final Factory factory, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(Serializer.I[0xCD ^ 0x83], (Number)factory.coordinateScale);
            jsonObject.addProperty(Serializer.I[0x74 ^ 0x3B], (Number)factory.heightScale);
            jsonObject.addProperty(Serializer.I[0xE4 ^ 0xB4], (Number)factory.lowerLimitScale);
            jsonObject.addProperty(Serializer.I[0x6A ^ 0x3B], (Number)factory.upperLimitScale);
            jsonObject.addProperty(Serializer.I[0x1 ^ 0x53], (Number)factory.depthNoiseScaleX);
            jsonObject.addProperty(Serializer.I[0xF9 ^ 0xAA], (Number)factory.depthNoiseScaleZ);
            jsonObject.addProperty(Serializer.I[0x11 ^ 0x45], (Number)factory.depthNoiseScaleExponent);
            jsonObject.addProperty(Serializer.I[0x96 ^ 0xC3], (Number)factory.mainNoiseScaleX);
            jsonObject.addProperty(Serializer.I[0x5C ^ 0xA], (Number)factory.mainNoiseScaleY);
            jsonObject.addProperty(Serializer.I[0xF0 ^ 0xA7], (Number)factory.mainNoiseScaleZ);
            jsonObject.addProperty(Serializer.I[0x22 ^ 0x7A], (Number)factory.baseSize);
            jsonObject.addProperty(Serializer.I[0x4 ^ 0x5D], (Number)factory.stretchY);
            jsonObject.addProperty(Serializer.I[0x28 ^ 0x72], (Number)factory.biomeDepthWeight);
            jsonObject.addProperty(Serializer.I[0x47 ^ 0x1C], (Number)factory.biomeDepthOffset);
            jsonObject.addProperty(Serializer.I[0x25 ^ 0x79], (Number)factory.biomeScaleWeight);
            jsonObject.addProperty(Serializer.I[0xFE ^ 0xA3], (Number)factory.biomeScaleOffset);
            jsonObject.addProperty(Serializer.I[0xE ^ 0x50], (Number)factory.seaLevel);
            jsonObject.addProperty(Serializer.I[0x1A ^ 0x45], factory.useCaves);
            jsonObject.addProperty(Serializer.I[0x4F ^ 0x2F], factory.useDungeons);
            jsonObject.addProperty(Serializer.I[0x7B ^ 0x1A], (Number)factory.dungeonChance);
            jsonObject.addProperty(Serializer.I[0x6A ^ 0x8], factory.useStrongholds);
            jsonObject.addProperty(Serializer.I[0x69 ^ 0xA], factory.useVillages);
            jsonObject.addProperty(Serializer.I[0x5B ^ 0x3F], factory.useMineShafts);
            jsonObject.addProperty(Serializer.I[0x76 ^ 0x13], factory.useTemples);
            jsonObject.addProperty(Serializer.I[0xE0 ^ 0x86], factory.useMonuments);
            jsonObject.addProperty(Serializer.I[0x26 ^ 0x41], factory.useRavines);
            jsonObject.addProperty(Serializer.I[0xE7 ^ 0x8F], factory.useWaterLakes);
            jsonObject.addProperty(Serializer.I[0x26 ^ 0x4F], (Number)factory.waterLakeChance);
            jsonObject.addProperty(Serializer.I[0xFD ^ 0x97], factory.useLavaLakes);
            jsonObject.addProperty(Serializer.I[0x33 ^ 0x58], (Number)factory.lavaLakeChance);
            jsonObject.addProperty(Serializer.I[0xD1 ^ 0xBD], factory.useLavaOceans);
            jsonObject.addProperty(Serializer.I[0x52 ^ 0x3F], (Number)factory.fixedBiome);
            jsonObject.addProperty(Serializer.I[0xF0 ^ 0x9E], (Number)factory.biomeSize);
            jsonObject.addProperty(Serializer.I[0xAB ^ 0xC4], (Number)factory.riverSize);
            jsonObject.addProperty(Serializer.I[0xDE ^ 0xAE], (Number)factory.dirtSize);
            jsonObject.addProperty(Serializer.I[0x17 ^ 0x66], (Number)factory.dirtCount);
            jsonObject.addProperty(Serializer.I[0x30 ^ 0x42], (Number)factory.dirtMinHeight);
            jsonObject.addProperty(Serializer.I[0x1A ^ 0x69], (Number)factory.dirtMaxHeight);
            jsonObject.addProperty(Serializer.I[0xC3 ^ 0xB7], (Number)factory.gravelSize);
            jsonObject.addProperty(Serializer.I[0x6 ^ 0x73], (Number)factory.gravelCount);
            jsonObject.addProperty(Serializer.I[0xFA ^ 0x8C], (Number)factory.gravelMinHeight);
            jsonObject.addProperty(Serializer.I[0xD0 ^ 0xA7], (Number)factory.gravelMaxHeight);
            jsonObject.addProperty(Serializer.I[0x7D ^ 0x5], (Number)factory.graniteSize);
            jsonObject.addProperty(Serializer.I[0xCF ^ 0xB6], (Number)factory.graniteCount);
            jsonObject.addProperty(Serializer.I[0x6D ^ 0x17], (Number)factory.graniteMinHeight);
            jsonObject.addProperty(Serializer.I[0xFE ^ 0x85], (Number)factory.graniteMaxHeight);
            jsonObject.addProperty(Serializer.I[0x12 ^ 0x6E], (Number)factory.dioriteSize);
            jsonObject.addProperty(Serializer.I[0xFB ^ 0x86], (Number)factory.dioriteCount);
            jsonObject.addProperty(Serializer.I[0x1E ^ 0x60], (Number)factory.dioriteMinHeight);
            jsonObject.addProperty(Serializer.I[43 + 4 - 3 + 83], (Number)factory.dioriteMaxHeight);
            jsonObject.addProperty(Serializer.I[103 + 64 - 71 + 32], (Number)factory.andesiteSize);
            jsonObject.addProperty(Serializer.I[123 + 34 - 134 + 106], (Number)factory.andesiteCount);
            jsonObject.addProperty(Serializer.I[81 + 42 - 25 + 32], (Number)factory.andesiteMinHeight);
            jsonObject.addProperty(Serializer.I[66 + 6 + 20 + 39], (Number)factory.andesiteMaxHeight);
            jsonObject.addProperty(Serializer.I[108 + 3 - 15 + 36], (Number)factory.coalSize);
            jsonObject.addProperty(Serializer.I[125 + 2 - 45 + 51], (Number)factory.coalCount);
            jsonObject.addProperty(Serializer.I[66 + 35 - 90 + 123], (Number)factory.coalMinHeight);
            jsonObject.addProperty(Serializer.I[133 + 97 - 147 + 52], (Number)factory.coalMaxHeight);
            jsonObject.addProperty(Serializer.I[33 + 115 - 24 + 12], (Number)factory.ironSize);
            jsonObject.addProperty(Serializer.I[106 + 12 + 15 + 4], (Number)factory.ironCount);
            jsonObject.addProperty(Serializer.I[36 + 104 - 138 + 136], (Number)factory.ironMinHeight);
            jsonObject.addProperty(Serializer.I[57 + 46 - 15 + 51], (Number)factory.ironMaxHeight);
            jsonObject.addProperty(Serializer.I[65 + 111 - 117 + 81], (Number)factory.goldSize);
            jsonObject.addProperty(Serializer.I[64 + 77 - 48 + 48], (Number)factory.goldCount);
            jsonObject.addProperty(Serializer.I[13 + 76 - 75 + 128], (Number)factory.goldMinHeight);
            jsonObject.addProperty(Serializer.I[9 + 42 + 80 + 12], (Number)factory.goldMaxHeight);
            jsonObject.addProperty(Serializer.I[41 + 82 - 48 + 69], (Number)factory.redstoneSize);
            jsonObject.addProperty(Serializer.I[130 + 49 - 91 + 57], (Number)factory.redstoneCount);
            jsonObject.addProperty(Serializer.I[5 + 39 + 62 + 40], (Number)factory.redstoneMinHeight);
            jsonObject.addProperty(Serializer.I[107 + 121 - 193 + 112], (Number)factory.redstoneMaxHeight);
            jsonObject.addProperty(Serializer.I[137 + 113 - 137 + 35], (Number)factory.diamondSize);
            jsonObject.addProperty(Serializer.I[113 + 10 - 86 + 112], (Number)factory.diamondCount);
            jsonObject.addProperty(Serializer.I[24 + 129 - 59 + 56], (Number)factory.diamondMinHeight);
            jsonObject.addProperty(Serializer.I[70 + 12 + 7 + 62], (Number)factory.diamondMaxHeight);
            jsonObject.addProperty(Serializer.I[85 + 46 - 13 + 34], (Number)factory.lapisSize);
            jsonObject.addProperty(Serializer.I[152 + 31 - 112 + 82], (Number)factory.lapisCount);
            jsonObject.addProperty(Serializer.I[2 + 52 - 35 + 135], (Number)factory.lapisCenterHeight);
            jsonObject.addProperty(Serializer.I[103 + 97 - 126 + 81], (Number)factory.lapisSpread);
            return (JsonElement)jsonObject;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        private static void I() {
            (I = new String[27 + 123 - 117 + 123])["".length()] = I("*\u0017\u0007\u0005\u0017 \u0016\t\u0003\u0016\u001a\u001b\t\u001b\u0016", "Ixhws");
            Serializer.I[" ".length()] = I("=5/\u0011\u0002!\u0003%\u0017\u00060", "UPFvj");
            Serializer.I["  ".length()] = I(")\f\u0012\b\u0011\t\n\b\u0004\u0017\u0016\u0000\u0004\u0001\u0006", "Ecemc");
            Serializer.I["   ".length()] = I("\u0016%4\u00148/<)\u0018>06%\u001d/", "cUDqJ");
            Serializer.I[0x6F ^ 0x6B] = I("4&6;!\u001e,/<,\u0003 '#,\b", "PCFOI");
            Serializer.I[0x69 ^ 0x6C] = I("-\u0004\u0012#\u0005\u0007\u000e\u000b$\b\u001a\u0002\u0003;\b\u0013", "IabWm");
            Serializer.I[0x6D ^ 0x6B] = I("\u0000)\u0001\u0005\n*#\u0018\u0002\u00077/\u0010\u001d\u0007!4\u0001\u001e\f\u0001\"\u0005", "dLqqb");
            Serializer.I[0xC7 ^ 0xC0] = I("\u001c\u001b\f::\u001e\u0013\u00161'\u0012\u001b\t1,", "qzeTt");
            Serializer.I[0x69 ^ 0x61] = I("\u0015\r\"6&\u0017\u00058=;\u001b\r'=1", "xlKXh");
            Serializer.I[0x3 ^ 0xA] = I("*\u0002:\u0014\r(\n \u001f\u0010$\u0002?\u001f\u0019", "GcSzC");
            Serializer.I[0x27 ^ 0x2D] = I("\u0010\u0013\u0014\"\u0004\u001b\b\u0002", "rrgGW");
            Serializer.I[0x32 ^ 0x39] = I("\t\u0003\u0000\u000f:\u0019\u001f+", "zwrjN");
            Serializer.I[0x42 ^ 0x4E] = I(".!\u0005/\n\b-\u001a6\u0007\u001b-\u0003%\u00078", "LHjBo");
            Serializer.I[0xD ^ 0x0] = I("\u000e$\u001e:\u0011((\u0001#\u001c#+\u0017$\u0011\u0018", "lMqWt");
            Serializer.I[0x83 ^ 0x8D] = I("4\u001b7+1\u0005\u00119*1\u0001\u00171!<\"", "VrXFT");
            Serializer.I[0x3C ^ 0x33] = I("\u0018\u001a:8\u0011)\u001049\u00115\u00153&\u0011\u000e", "zsUUt");
            Serializer.I[0xBA ^ 0xAA] = I("*6(;)/6%", "YSIwL");
            Serializer.I[0x37 ^ 0x26] = I("7\u0011\t'\u001b4\u0007\u001f", "Bbldz");
            Serializer.I[0xB1 ^ 0xA3] = I(";\u0002\"'\" \u0016\"\f9=", "NqGcW");
            Serializer.I[0xB9 ^ 0xAA] = I("\u0010\u0005\b32\u001b\u001e%<6\u001a\u0013\u0003", "tpfTW");
            Serializer.I[0x7C ^ 0x68] = I("'%1;\u0005 9:\u000f\u0019=:0\u001b", "RVThq");
            Serializer.I[0x3B ^ 0x2E] = I("\u0018\u0001\b\u00130\u0001\u001e\f\"<\u001e", "mrmEY");
            Serializer.I[0x64 ^ 0x72] = I("\u0002\u0014\b8#\u0019\u0002>\u001d+\u0011\u0013\u001e", "wgmuJ");
            Serializer.I[0xA1 ^ 0xB6] = I("\u001e\u001a\u001c0(\u0006\u0019\u0015\u0001>", "kiydM");
            Serializer.I[0xC ^ 0x14] = I(";& ,\u0004  (\u0004\u0005:&", "NUEak");
            Serializer.I[0x5C ^ 0x45] = I(" \u00181=5#\u0002:\n'", "UkToT");
            Serializer.I[0x5F ^ 0x45] = I("\u001a\n\u001f/-\u001b\u001c\b4-\u0004\u001c\t", "oyzxL");
            Serializer.I[0xD8 ^ 0xC3] = I("0\u0016\u00186\u0007\u000b\u0016\u000766/\u0016\u00020\u0010", "GwlSu");
            Serializer.I[0x5A ^ 0x46] = I("\u0019\n\u000f\u0003\r\u001a\u0018&.\u0007\t\n", "lyjOl");
            Serializer.I[0xD ^ 0x10] = I("\u000e\u000b\u0004 \b\u0003\u0001\u0017\u0002,\u0003\u0004\u0011$", "bjrAD");
            Serializer.I[0x6F ^ 0x71] = I("\u00196\u0007>\u0019\u001a$-\u0011\u001d\r+\u0011", "lEbrx");
            Serializer.I[0x48 ^ 0x57] = I("%\n\u001c-1\u0001\n\u000b%0", "CcdHU");
            Serializer.I[0x43 ^ 0x63] = I("*#\r\u000e?\u001b#\u0018\u0006", "HJbcZ");
            Serializer.I[0x55 ^ 0x74] = I("*\u0004%*\u0001\u000b\u0004)*", "XmSOs");
            Serializer.I[0x41 ^ 0x63] = I("\u0000\u000f!3\u0007\r\u001c6", "dfSGT");
            Serializer.I[0x2E ^ 0xD] = I("\u001c\u000e32\u0016\u0017\u0012/2", "xgAFU");
            Serializer.I[0x49 ^ 0x6D] = I("(\u0007\u001a\u0000$%\u0000 \u0011\u0000+\u0006\u001c", "Lnhti");
            Serializer.I[0x98 ^ 0xBD] = I("\u00029\u001b,\u001f\u0007(!=;\u00018\u001d", "fPiXR");
            Serializer.I[0x8 ^ 0x2E] = I("?=\t\u0013\u001d4\u001c\u0001\u001f\u001d", "XOhex");
            Serializer.I[0xA1 ^ 0x86] = I(",\u0003\u0015;\u0000'2\u001b8\u000b?", "KqtMe");
            Serializer.I[0x8F ^ 0xA7] = I("2:%.\n9\u0005-6'0!#0\u001b", "UHDXo");
            Serializer.I[0x99 ^ 0xB0] = I("\f1;</\u0007\u000e;2\u0002\u000e*=\">", "kCZJJ");
            Serializer.I[0x63 ^ 0x49] = I(".\u0015\u001b8?=\u0002)?,,", "IgzVV");
            Serializer.I[0x24 ^ 0xF] = I("+;\u00119\f8,38\u0010\"=", "LIpWe");
            Serializer.I[0x8A ^ 0xA6] = I("!\n\u0018\f\u001c2\u001d4\u000b\u001b\u000e\u001d\u0010\u0005\u001d2", "Fxybu");
            Serializer.I[0xA8 ^ 0x85] = I("\u0003 \u0006\u0004\u0011\u00107*\u000b\u0000,7\u000e\r\u0010\u0010", "dRgjx");
            Serializer.I[0x85 ^ 0xAB] = I(".\u001a$ (>\u0016\u0018;;/", "JsKRA");
            Serializer.I[0xBA ^ 0x95] = I("\u000b>\u0004(\u0006\u001b2(5\u001a\u0001#", "oWkZo");
            Serializer.I[0x57 ^ 0x67] = I("\u001e1\u00061\u0005\u000e=$*\u00022=\u0000$\u0004\u000e", "zXiCl");
            Serializer.I[0xB5 ^ 0x84] = I("'\u000b;\u0019\u00067\u0007\u0019\n\u0017\u000b\u0007=\f\u00077", "CbTko");
            Serializer.I[0x46 ^ 0x74] = I("*<2\u001c\n\"&3*\u001017", "KRVyy");
            Serializer.I[0xAE ^ 0x9D] = I("\"\u001b+$\u0000*\u0001*\u0002\u001c6\u001b;", "CuOAs");
            Serializer.I[0x25 ^ 0x11] = I("9\u0006!\u0001\u001f1\u001c )\u00056  \r\u000b0\u001c", "XhEdl");
            Serializer.I[0x2C ^ 0x19] = I("\u0014\n\u0011+9\u001c\u0010\u0010\u0003+\r,\u0010'-\u001d\u0010", "uduNJ");
            Serializer.I[0x8F ^ 0xB9] = I("3#\u0014\u001c\u001196\u0010", "PLupB");
            Serializer.I[0xA7 ^ 0x90] = I("\u00119\u0003\u0018!\u001d#\f\u0000", "rVbtb");
            Serializer.I[0x77 ^ 0x4F] = I("\u0013-\u000e-\u0005\u0019,'$!\u0017*\u001b", "pBoAH");
            Serializer.I[0x58 ^ 0x61] = I(",*\u000b\u0018\n.=\"\u0011.(-\u001e", "OEjtG");
            Serializer.I[0x41 ^ 0x7B] = I("\u001a#7)\u0018\u001a+=", "sQXGK");
            Serializer.I[0x4F ^ 0x74] = I("\u0004\";?.\u0002%:%", "mPTQm");
            Serializer.I[0x29 ^ 0x15] = I(" $\u001a\f< 8=\u0007\u0018.>\u0001", "IVubq");
            Serializer.I[0x91 ^ 0xAC] = I(",\u001e;\u001b\u0002$\u0014\u001c\u0010&\"\u0004 ", "ElTuO");
            Serializer.I[0x2B ^ 0x15] = I(">=&\u001e10(/", "YRJzb");
            Serializer.I[0x69 ^ 0x56] = I("\u0011>;5\"\u0019$9%", "vQWQa");
            Serializer.I[0x43 ^ 0x3] = I("\u0012\u000b\u001e5(\u001c\n:4\f\u0012\f\u0006", "udrQe");
            Serializer.I[0x3C ^ 0x7D] = I("&?/\f; (\u000b\r\u001f&87", "APChv");
            Serializer.I[0x52 ^ 0x10] = I("\u0018<!\u001a!\u00057 :<\u0010<", "jYEiU");
            Serializer.I[0x21 ^ 0x62] = I(">5\u0006&3#>\u0007\u0016(9>\u0016", "LPbUG");
            Serializer.I[0x2F ^ 0x6B] = I("0\u0006=&\u0016-\r<\u0018\u000b,+<<\u0005*\u0017", "BcYUb");
            Serializer.I[0x61 ^ 0x24] = I("1\u000f6\u0010\u0005,\u00047.\u0010;\"7\n\u0016+\u001e", "CjRcq");
            Serializer.I[0x7B ^ 0x3D] = I("\r=\u000f\b\u001c\u00070=\f\t\f", "iTnes");
            Serializer.I[0x3A ^ 0x7D] = I("\u00113\u0017\u000b=\u001b>5\t'\u001b.", "uZvfR");
            Serializer.I[0x4B ^ 0x3] = I(")>\u00008)#3,<(\u00052\b2.9", "MWaUF");
            Serializer.I[0x7D ^ 0x34] = I("3\u00049\u0005?9\t\u0015\t(\u001f\b1\u000f8#", "WmXhP");
            Serializer.I[0x15 ^ 0x5F] = I("\u000b1\"/)49(#", "gPRFZ");
            Serializer.I[0xD1 ^ 0x9A] = I("\u0003\u0003\n\r\u0011,\r\u000f\n\u0016", "obzdb");
            Serializer.I[0x5C ^ 0x10] = I("\u0015;*\u0013\u0015:?4\u000e\u0003\u000b\u0012?\u0013\u0001\u0011.", "yZZzf");
            Serializer.I[0x15 ^ 0x58] = I("\u0014\n)\u000e\u0001+\u001b+\u0002\u0013\u001c", "xkYgr");
            Serializer.I[0xDC ^ 0x92] = I("5#<\u00160?\"2\u00101\u0005/2\b1", "VLSdT");
            Serializer.I[0xE ^ 0x41] = I("\u0000\u001c\u000b\",\u001c*\u0001$(\r", "hybED");
            Serializer.I[0x5F ^ 0xF] = I("\u0003\u0015.\u000b%#\u00134\u0007#<\u00198\u00022", "ozYnW");
            Serializer.I[0x70 ^ 0x21] = I("8\u001d\u0004\u00018\u0001\u0004\u0019\r>\u001e\u000e\u0015\b/", "MmtdJ");
            Serializer.I[0xE ^ 0x5C] = I("(\u0001<28\u0002\u000b%55\u001f\u0007-*5\u0014", "LdLFP");
            Serializer.I[0xED ^ 0xBE] = I("\f\b;2#&\u0002\"5.;\u000e**.2", "hmKFK");
            Serializer.I[0xED ^ 0xB9] = I("\u0005$(\u001c!/.1\u001b,2\"9\u0004,$9(\u0007'\u0004/,", "aAXhI");
            Serializer.I[0xEE ^ 0xBB] = I("\u001e\n\n:7\u001c\u0002\u00101*\u0010\n\u000f1!", "skcTy");
            Serializer.I[0x18 ^ 0x4E] = I(":\u000f,\f-8\u00076\u000704\u000f)\u0007:", "WnEbc");
            Serializer.I[0xD2 ^ 0x85] = I("\u001d\"\u001f+\u0007\u001f*\u0005 \u001a\u0013\"\u001a \u0013", "pCvEI");
            Serializer.I[0xA ^ 0x52] = I("\u000e\n\u00192:\u0005\u0011\u000f", "lkjWi");
            Serializer.I[0x6F ^ 0x36] = I("1\u0019\u00193;!\u00052", "BmkVO");
            Serializer.I[0x24 ^ 0x7E] = I("\u0012\u0005\u0019/\u00074\t\u00066\n'\t\u001f%\n\u0004", "plvBb");
            Serializer.I[0xC0 ^ 0x9B] = I("4\u000b5+/\u0012\u0007*2\"\u0019\u0004<5/\"", "VbZFJ");
            Serializer.I[0x32 ^ 0x6E] = I("0!?=\u0011\u0001+1<\u0011\u0005-97\u001c&", "RHPPt");
            Serializer.I[0xF2 ^ 0xAF] = I("\u0015\u0001 \u0018?$\u000b.\u0019?8\u000e)\u0006?\u0003", "whOuZ");
            Serializer.I[0xF3 ^ 0xAD] = I("?=5\n7:=8", "LXTFR");
            Serializer.I[0x8 ^ 0x57] = I("\u0017>\u00122.\u0014(\u0004", "bMwqO");
            Serializer.I[0x39 ^ 0x59] = I("\u00141$<\u001a\u000f%$\u0017\u0001\u0012", "aBAxo");
            Serializer.I[0x1F ^ 0x7E] = I("\u0017\u0000 \u000f\u001c\u001c\u001b\r\u0000\u0018\u001d\u0016+", "suNhy");
            Serializer.I[0xA ^ 0x68] = I(",\u0001=>\u0017+\u001d6\n\u000b6\u001e<\u001e", "YrXmc");
            Serializer.I[0x6B ^ 0x8] = I("\u0006'+\u0011>\u001f8/ 2\u0000", "sTNGW");
            Serializer.I[0x3D ^ 0x59] = I("\u0019\u0017\u000f:\u000b\u0002\u00019\u001f\u0003\n\u0010\u0019", "ldjwb");
            Serializer.I[0xF6 ^ 0x93] = I("\u0006;\u000f7,\u001e8\u0006\u0006:", "sHjcI");
            Serializer.I[0xFB ^ 0x9D] = I("\u0019\u001d&\u001a*\u0002\u001b.2+\u0018\u001d", "lnCWE");
            Serializer.I[0xE7 ^ 0x80] = I("\u0000900,\u0003#;\u0007>", "uJUbM");
            Serializer.I[0x33 ^ 0x5B] = I("\u0002'51&\u00031\"*&\u001c1#", "wTPfG");
            Serializer.I[0x7B ^ 0x12] = I("#'.\f\u001b\u0018'1\f*<'4\n\f", "TFZii");
            Serializer.I[0xE ^ 0x64] = I("\u0007*\u0016(5\u00048?\u0005?\u0017*", "rYsdT");
            Serializer.I[0x2D ^ 0x46] = I("\u0015\u0015\u0018\u0003\u0018\u0018\u001f\u000b!<\u0018\u001a\r\u0007", "ytnbT");
            Serializer.I[0x2 ^ 0x6E] = I("\u0017;7 \r\u0014)\u001d\u000f\t\u0003&!", "bHRll");
            Serializer.I[0xD0 ^ 0xBD] = I("(\u000f \u0015\u0007\f\u000f7\u001d\u0006", "NfXpc");
            Serializer.I[0x69 ^ 0x7] = I("7#<!\u0011\u0006#))", "UJSLt");
            Serializer.I[0x1D ^ 0x72] = I("\u0019:%\u000498:)\u0004", "kSSaK");
            Serializer.I[0xEF ^ 0x9F] = I("\u001d\u0006\u0016&\u0000\u0010\u0015\u0001", "yodRS");
            Serializer.I[0xF0 ^ 0x81] = I("\u0016\u0001\u0002\"\u0016\u001d\u001d\u001e\"", "rhpVU");
            Serializer.I[0xA ^ 0x78] = I("\u00079\u0011\r+\n>+\u001c\u000f\u00048\u0017", "cPcyf");
            Serializer.I[0x3A ^ 0x49] = I("\b=\u000b\u0003\u001f\r,1\u0012;\u000b<\r", "lTywR");
            Serializer.I[0xC6 ^ 0xB2] = I("\u0010\u0002\u000f\u001c#\u001b#\u0007\u0010#", "wpnjF");
            Serializer.I[0xED ^ 0x98] = I("4\u001b15\u0004?*?6\u000f'", "SiPCa");
            Serializer.I[0x63 ^ 0x15] = I("(:(\u000f\b#\u0005 \u0017%*!.\u0011\u0019", "OHIym");
            Serializer.I[0x77 ^ 0x0] = I(")47\u001c#\"\u000b7\u0012\u000e+/1\u00022", "NFVjF");
            Serializer.I[0x7B ^ 0x3] = I("?\b\u000b6$,\u001f917=", "XzjXM");
            Serializer.I[0xBC ^ 0xC5] = I("5\b\n?\u0010&\u001f(>\f<\u000e", "RzkQy");
            Serializer.I[0xD8 ^ 0xA2] = I("\"3\u0003\u0019\u00011$/\u001e\u0006\r$\u000b\u0010\u00001", "EAbwh");
            Serializer.I[0xF7 ^ 0x8C] = I("+\u0000+><8\u0017\u00071-\u0004\u0017#7=8", "LrJPU");
            Serializer.I[0xFA ^ 0x86] = I("\u001e\u0011\r+&\u000e\u001d105\u001f", "zxbYO");
            Serializer.I[0x62 ^ 0x1F] = I("\u00108\u0015$#\u0000499?\u001a%", "tQzVJ");
            Serializer.I[0x22 ^ 0x5C] = I("\u0001:#\u001c!\u00116\u0001\u0007&-6%\t \u0011", "eSLnH");
            Serializer.I[43 + 4 + 77 + 3] = I("#9*\u0016\u001135\b\u0005\u0000\u000f5,\u0003\u00103", "GPEdx");
            Serializer.I[48 + 59 - 8 + 29] = I("\u0005\n\u0000\u000b0\r\u0010\u0001=*\u001e\u0001", "dddnC");
            Serializer.I[65 + 117 - 136 + 83] = I("\u001b-+\f'\u00137**;\u000f-;", "zCOiT");
            Serializer.I[1 + 7 + 96 + 26] = I("\u001b\f\u0017&\u0019\u0013\u0016\u0016\u000e\u0003\u0014*\u0016*\r\u0012\u0016", "zbsCj");
            Serializer.I[27 + 34 + 63 + 7] = I("+$\u0017-\u001c#>\u0016\u0005\u000e2\u0002\u0016!\b\">", "JJsHo");
            Serializer.I[50 + 28 - 41 + 95] = I("\u0019\f+*\u0019\u0013\u0019/", "zcJFJ");
            Serializer.I[21 + 117 - 33 + 28] = I(":.\u00194\u001b64\u0016,", "YAxXX");
            Serializer.I[86 + 62 - 17 + 3] = I("\u0004\b,;5\u000e\t\u00052\u0011\u0000\u000f9", "ggMWx");
            Serializer.I[72 + 15 - 86 + 134] = I("'.9>5%9\u00107\u0011#),", "DAXRx");
            Serializer.I[91 + 29 - 106 + 122] = I("?\u0016\u001e\u00046?\u001e\u0014", "Vdqje");
            Serializer.I[41 + 51 + 4 + 41] = I("\n6\t\u0016\u0016\f1\b\f", "cDfxU");
            Serializer.I[44 + 5 + 68 + 21] = I("\r+ \u0017\n\r7\u0007\u001c.\u00031;", "dYOyG");
            Serializer.I[81 + 80 - 134 + 112] = I("\u0011\n?=#\u0019\u0000\u00186\u0007\u001f\u0010$", "xxPSn");
            Serializer.I[114 + 115 - 162 + 73] = I("?\u0016\u00196&1\u0003\u0010", "XyuRu");
            Serializer.I[115 + 24 - 31 + 33] = I(")\u001c\u0016\u00054!\u0006\u0014\u0015", "Nszaw");
            Serializer.I[10 + 92 + 35 + 5] = I("6?\u0002\r\u00188>&\f<68\u001a", "QPniU");
            Serializer.I[76 + 124 - 157 + 100] = I("\"\"9/\u0004$5\u001d. \"%!", "EMUKI");
            Serializer.I[108 + 91 - 83 + 28] = I("\u0010 =\u0016$\r+<69\u0018 ", "bEYeP");
            Serializer.I[136 + 65 - 169 + 113] = I(">\u00132><#\u00183\u000e'9\u0018\"", "LvVMH");
            Serializer.I[83 + 35 - 21 + 49] = I("\u001b32=:\u000683\u0003'\u0007\u001e3')\u0001\"", "iVVNN");
            Serializer.I[6 + 66 + 54 + 21] = I("\u0005\u0013(\u001c\u001f\u0018\u0018)\"\n\u000f>)\u0006\f\u001f\u0002", "wvLok");
            Serializer.I[69 + 96 - 62 + 45] = I("\u0002\u0004+,\u0016\b\t\u0019(\u0003\u0003", "fmJAy");
            Serializer.I[86 + 81 - 33 + 15] = I("/\f\u00025\u0002%\u0001 7\u0018%\u0011", "KecXm");
            Serializer.I[131 + 149 - 205 + 75] = I("\u0005\b\u0011 :\u000f\u0005=$;)\u0004\u0019*=\u0015", "aapMU");
            Serializer.I[27 + 90 + 12 + 22] = I("<\u0010\u000f\u0005?6\u001d#\t(\u0010\u001c\u0007\u000f8,", "XynhP");
            Serializer.I[125 + 86 - 139 + 80] = I("93'\u0007\u0003\u0006;-\u000b", "URWnp");
            Serializer.I[129 + 85 - 208 + 147] = I("\u0007\u0014\u001c\u001a#(\u001a\u0019\u001d$", "kulsP");
            Serializer.I[142 + 124 - 207 + 95] = I("\u00166\u0003,\u000492\u001d1\u0012\b\u001f\u0016,\u0010\u0012#", "zWsEw");
            Serializer.I[152 + 103 - 111 + 11] = I("\u000f\r;-10\u001c9!#\u0007", "clKDB");
        }
    }
    
    public static class Factory
    {
        public float biomeDepthOffset;
        public int dirtCount;
        public boolean useLavaOceans;
        public int waterLakeChance;
        public int dungeonChance;
        public int dioriteMinHeight;
        public float biomeScaleWeight;
        public boolean useCaves;
        public boolean useDungeons;
        public int dioriteSize;
        public int dirtSize;
        public boolean useRavines;
        public boolean useMonuments;
        public int diamondSize;
        public int biomeSize;
        public float stretchY;
        public boolean useStrongholds;
        public boolean useVillages;
        public float coordinateScale;
        public int ironCount;
        public boolean useMineShafts;
        public int diamondMinHeight;
        public float biomeDepthWeight;
        public int lapisCount;
        public int andesiteCount;
        public int graniteMinHeight;
        public int graniteCount;
        public float mainNoiseScaleZ;
        public int goldMinHeight;
        public int coalCount;
        public int gravelCount;
        public int redstoneCount;
        public int lapisCenterHeight;
        public int goldSize;
        public int dirtMaxHeight;
        public int gravelMaxHeight;
        public int riverSize;
        public float depthNoiseScaleExponent;
        public int lapisSize;
        public float mainNoiseScaleX;
        public int diamondMaxHeight;
        public int goldMaxHeight;
        public float depthNoiseScaleX;
        public int dioriteCount;
        public boolean useLavaLakes;
        public int goldCount;
        public float lowerLimitScale;
        public int diamondCount;
        public float baseSize;
        public float heightScale;
        public int ironSize;
        public int andesiteSize;
        public int coalSize;
        public float upperLimitScale;
        public int redstoneMaxHeight;
        public int graniteSize;
        public int redstoneSize;
        public int gravelMinHeight;
        public int redstoneMinHeight;
        static final Gson JSON_ADAPTER;
        public float mainNoiseScaleY;
        public int lavaLakeChance;
        public int dirtMinHeight;
        public int lapisSpread;
        public int gravelSize;
        public boolean useWaterLakes;
        public int dioriteMaxHeight;
        public int ironMaxHeight;
        public float depthNoiseScaleZ;
        public int coalMaxHeight;
        public int coalMinHeight;
        public float biomeScaleOffset;
        public int andesiteMinHeight;
        public int fixedBiome;
        public int ironMinHeight;
        public int seaLevel;
        public boolean useTemples;
        public int andesiteMaxHeight;
        public int graniteMaxHeight;
        
        @Override
        public int hashCode() {
            int n;
            if (this.coordinateScale != 0.0f) {
                n = Float.floatToIntBits(this.coordinateScale);
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = (0x29 ^ 0x36) * n;
            int n3;
            if (this.heightScale != 0.0f) {
                n3 = Float.floatToIntBits(this.heightScale);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = (0x1C ^ 0x3) * (n2 + n3);
            int n5;
            if (this.upperLimitScale != 0.0f) {
                n5 = Float.floatToIntBits(this.upperLimitScale);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            final int n6 = (0xD ^ 0x12) * (n4 + n5);
            int n7;
            if (this.lowerLimitScale != 0.0f) {
                n7 = Float.floatToIntBits(this.lowerLimitScale);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                n7 = "".length();
            }
            final int n8 = (0x96 ^ 0x89) * (n6 + n7);
            int n9;
            if (this.depthNoiseScaleX != 0.0f) {
                n9 = Float.floatToIntBits(this.depthNoiseScaleX);
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            else {
                n9 = "".length();
            }
            final int n10 = (0x9F ^ 0x80) * (n8 + n9);
            int n11;
            if (this.depthNoiseScaleZ != 0.0f) {
                n11 = Float.floatToIntBits(this.depthNoiseScaleZ);
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else {
                n11 = "".length();
            }
            final int n12 = (0x1C ^ 0x3) * (n10 + n11);
            int n13;
            if (this.depthNoiseScaleExponent != 0.0f) {
                n13 = Float.floatToIntBits(this.depthNoiseScaleExponent);
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                n13 = "".length();
            }
            final int n14 = (0x99 ^ 0x86) * (n12 + n13);
            int n15;
            if (this.mainNoiseScaleX != 0.0f) {
                n15 = Float.floatToIntBits(this.mainNoiseScaleX);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                n15 = "".length();
            }
            final int n16 = (0x1D ^ 0x2) * (n14 + n15);
            int n17;
            if (this.mainNoiseScaleY != 0.0f) {
                n17 = Float.floatToIntBits(this.mainNoiseScaleY);
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else {
                n17 = "".length();
            }
            final int n18 = (0xA1 ^ 0xBE) * (n16 + n17);
            int n19;
            if (this.mainNoiseScaleZ != 0.0f) {
                n19 = Float.floatToIntBits(this.mainNoiseScaleZ);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                n19 = "".length();
            }
            final int n20 = (0x91 ^ 0x8E) * (n18 + n19);
            int n21;
            if (this.baseSize != 0.0f) {
                n21 = Float.floatToIntBits(this.baseSize);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n21 = "".length();
            }
            final int n22 = (0x5C ^ 0x43) * (n20 + n21);
            int n23;
            if (this.stretchY != 0.0f) {
                n23 = Float.floatToIntBits(this.stretchY);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n23 = "".length();
            }
            final int n24 = (0x31 ^ 0x2E) * (n22 + n23);
            int n25;
            if (this.biomeDepthWeight != 0.0f) {
                n25 = Float.floatToIntBits(this.biomeDepthWeight);
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            else {
                n25 = "".length();
            }
            final int n26 = (0x19 ^ 0x6) * (n24 + n25);
            int n27;
            if (this.biomeDepthOffset != 0.0f) {
                n27 = Float.floatToIntBits(this.biomeDepthOffset);
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                n27 = "".length();
            }
            final int n28 = (0x4E ^ 0x51) * (n26 + n27);
            int n29;
            if (this.biomeScaleWeight != 0.0f) {
                n29 = Float.floatToIntBits(this.biomeScaleWeight);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                n29 = "".length();
            }
            final int n30 = (0x3A ^ 0x25) * (n28 + n29);
            int n31;
            if (this.biomeScaleOffset != 0.0f) {
                n31 = Float.floatToIntBits(this.biomeScaleOffset);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                n31 = "".length();
            }
            final int n32 = (0x90 ^ 0x8F) * ((0x38 ^ 0x27) * (n30 + n31) + this.seaLevel);
            int n33;
            if (this.useCaves) {
                n33 = " ".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                n33 = "".length();
            }
            final int n34 = (0x2E ^ 0x31) * (n32 + n33);
            int n35;
            if (this.useDungeons) {
                n35 = " ".length();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                n35 = "".length();
            }
            final int n36 = (0x9B ^ 0x84) * ((0x31 ^ 0x2E) * (n34 + n35) + this.dungeonChance);
            int n37;
            if (this.useStrongholds) {
                n37 = " ".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                n37 = "".length();
            }
            final int n38 = (0x3E ^ 0x21) * (n36 + n37);
            int n39;
            if (this.useVillages) {
                n39 = " ".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                n39 = "".length();
            }
            final int n40 = (0xBC ^ 0xA3) * (n38 + n39);
            int n41;
            if (this.useMineShafts) {
                n41 = " ".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n41 = "".length();
            }
            final int n42 = (0x81 ^ 0x9E) * (n40 + n41);
            int n43;
            if (this.useTemples) {
                n43 = " ".length();
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                n43 = "".length();
            }
            final int n44 = (0xB ^ 0x14) * (n42 + n43);
            int n45;
            if (this.useMonuments) {
                n45 = " ".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                n45 = "".length();
            }
            final int n46 = (0x15 ^ 0xA) * (n44 + n45);
            int n47;
            if (this.useRavines) {
                n47 = " ".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                n47 = "".length();
            }
            final int n48 = (0x63 ^ 0x7C) * (n46 + n47);
            int n49;
            if (this.useWaterLakes) {
                n49 = " ".length();
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else {
                n49 = "".length();
            }
            final int n50 = (0x16 ^ 0x9) * ((0x30 ^ 0x2F) * (n48 + n49) + this.waterLakeChance);
            int n51;
            if (this.useLavaLakes) {
                n51 = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n51 = "".length();
            }
            final int n52 = (0xD8 ^ 0xC7) * ((0x7A ^ 0x65) * (n50 + n51) + this.lavaLakeChance);
            int n53;
            if (this.useLavaOceans) {
                n53 = " ".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n53 = "".length();
            }
            return (0x61 ^ 0x7E) * ((0x91 ^ 0x8E) * ((0xA5 ^ 0xBA) * ((0x18 ^ 0x7) * ((0x3E ^ 0x21) * ((0x76 ^ 0x69) * ((0x6D ^ 0x72) * ((0x5 ^ 0x1A) * ((0x3E ^ 0x21) * ((0xD ^ 0x12) * ((0xA6 ^ 0xB9) * ((0xAD ^ 0xB2) * ((0xA1 ^ 0xBE) * ((0x41 ^ 0x5E) * ((0x8C ^ 0x93) * ((0x16 ^ 0x9) * ((0x6A ^ 0x75) * ((0x68 ^ 0x77) * ((0xA9 ^ 0xB6) * ((0x74 ^ 0x6B) * ((0x90 ^ 0x8F) * ((0x5A ^ 0x45) * ((0xB8 ^ 0xA7) * ((0x4B ^ 0x54) * ((0xAE ^ 0xB1) * ((0x8D ^ 0x92) * ((0x8E ^ 0x91) * ((0x39 ^ 0x26) * ((0x69 ^ 0x76) * ((0x8 ^ 0x17) * ((0xDF ^ 0xC0) * ((0x9 ^ 0x16) * ((0x66 ^ 0x79) * ((0xF ^ 0x10) * ((0xBD ^ 0xA2) * ((0x44 ^ 0x5B) * ((0x68 ^ 0x77) * ((0x77 ^ 0x68) * ((0x21 ^ 0x3E) * ((0x25 ^ 0x3A) * ((0x7 ^ 0x18) * ((0x5 ^ 0x1A) * ((0x22 ^ 0x3D) * ((0x9E ^ 0x81) * ((0x89 ^ 0x96) * ((0x3B ^ 0x24) * ((0x11 ^ 0xE) * (n52 + n53) + this.fixedBiome) + this.biomeSize) + this.riverSize) + this.dirtSize) + this.dirtCount) + this.dirtMinHeight) + this.dirtMaxHeight) + this.gravelSize) + this.gravelCount) + this.gravelMinHeight) + this.gravelMaxHeight) + this.graniteSize) + this.graniteCount) + this.graniteMinHeight) + this.graniteMaxHeight) + this.dioriteSize) + this.dioriteCount) + this.dioriteMinHeight) + this.dioriteMaxHeight) + this.andesiteSize) + this.andesiteCount) + this.andesiteMinHeight) + this.andesiteMaxHeight) + this.coalSize) + this.coalCount) + this.coalMinHeight) + this.coalMaxHeight) + this.ironSize) + this.ironCount) + this.ironMinHeight) + this.ironMaxHeight) + this.goldSize) + this.goldCount) + this.goldMinHeight) + this.goldMaxHeight) + this.redstoneSize) + this.redstoneCount) + this.redstoneMinHeight) + this.redstoneMaxHeight) + this.diamondSize) + this.diamondCount) + this.diamondMinHeight) + this.diamondMaxHeight) + this.lapisSize) + this.lapisCount) + this.lapisCenterHeight) + this.lapisSpread;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return " ".length() != 0;
            }
            if (o != null && this.getClass() == o.getClass()) {
                final Factory factory = (Factory)o;
                int n;
                if (this.andesiteCount != factory.andesiteCount) {
                    n = "".length();
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
                else if (this.andesiteMaxHeight != factory.andesiteMaxHeight) {
                    n = "".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else if (this.andesiteMinHeight != factory.andesiteMinHeight) {
                    n = "".length();
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                else if (this.andesiteSize != factory.andesiteSize) {
                    n = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.baseSize, this.baseSize) != 0) {
                    n = "".length();
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.biomeDepthOffset, this.biomeDepthOffset) != 0) {
                    n = "".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.biomeDepthWeight, this.biomeDepthWeight) != 0) {
                    n = "".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.biomeScaleOffset, this.biomeScaleOffset) != 0) {
                    n = "".length();
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.biomeScaleWeight, this.biomeScaleWeight) != 0) {
                    n = "".length();
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else if (this.biomeSize != factory.biomeSize) {
                    n = "".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else if (this.coalCount != factory.coalCount) {
                    n = "".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else if (this.coalMaxHeight != factory.coalMaxHeight) {
                    n = "".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else if (this.coalMinHeight != factory.coalMinHeight) {
                    n = "".length();
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else if (this.coalSize != factory.coalSize) {
                    n = "".length();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.coordinateScale, this.coordinateScale) != 0) {
                    n = "".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) != 0) {
                    n = "".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.depthNoiseScaleX, this.depthNoiseScaleX) != 0) {
                    n = "".length();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.depthNoiseScaleZ, this.depthNoiseScaleZ) != 0) {
                    n = "".length();
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
                else if (this.diamondCount != factory.diamondCount) {
                    n = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (this.diamondMaxHeight != factory.diamondMaxHeight) {
                    n = "".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (this.diamondMinHeight != factory.diamondMinHeight) {
                    n = "".length();
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                }
                else if (this.diamondSize != factory.diamondSize) {
                    n = "".length();
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                }
                else if (this.dioriteCount != factory.dioriteCount) {
                    n = "".length();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else if (this.dioriteMaxHeight != factory.dioriteMaxHeight) {
                    n = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (this.dioriteMinHeight != factory.dioriteMinHeight) {
                    n = "".length();
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
                else if (this.dioriteSize != factory.dioriteSize) {
                    n = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (this.dirtCount != factory.dirtCount) {
                    n = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (this.dirtMaxHeight != factory.dirtMaxHeight) {
                    n = "".length();
                    "".length();
                    if (0 == -1) {
                        throw null;
                    }
                }
                else if (this.dirtMinHeight != factory.dirtMinHeight) {
                    n = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (this.dirtSize != factory.dirtSize) {
                    n = "".length();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                }
                else if (this.dungeonChance != factory.dungeonChance) {
                    n = "".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (this.fixedBiome != factory.fixedBiome) {
                    n = "".length();
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                else if (this.goldCount != factory.goldCount) {
                    n = "".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else if (this.goldMaxHeight != factory.goldMaxHeight) {
                    n = "".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else if (this.goldMinHeight != factory.goldMinHeight) {
                    n = "".length();
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
                else if (this.goldSize != factory.goldSize) {
                    n = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (this.graniteCount != factory.graniteCount) {
                    n = "".length();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (this.graniteMaxHeight != factory.graniteMaxHeight) {
                    n = "".length();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else if (this.graniteMinHeight != factory.graniteMinHeight) {
                    n = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (this.graniteSize != factory.graniteSize) {
                    n = "".length();
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else if (this.gravelCount != factory.gravelCount) {
                    n = "".length();
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                else if (this.gravelMaxHeight != factory.gravelMaxHeight) {
                    n = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (this.gravelMinHeight != factory.gravelMinHeight) {
                    n = "".length();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else if (this.gravelSize != factory.gravelSize) {
                    n = "".length();
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.heightScale, this.heightScale) != 0) {
                    n = "".length();
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                }
                else if (this.ironCount != factory.ironCount) {
                    n = "".length();
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
                else if (this.ironMaxHeight != factory.ironMaxHeight) {
                    n = "".length();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else if (this.ironMinHeight != factory.ironMinHeight) {
                    n = "".length();
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else if (this.ironSize != factory.ironSize) {
                    n = "".length();
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else if (this.lapisCenterHeight != factory.lapisCenterHeight) {
                    n = "".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else if (this.lapisCount != factory.lapisCount) {
                    n = "".length();
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                else if (this.lapisSize != factory.lapisSize) {
                    n = "".length();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else if (this.lapisSpread != factory.lapisSpread) {
                    n = "".length();
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                else if (this.lavaLakeChance != factory.lavaLakeChance) {
                    n = "".length();
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.lowerLimitScale, this.lowerLimitScale) != 0) {
                    n = "".length();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.mainNoiseScaleX, this.mainNoiseScaleX) != 0) {
                    n = "".length();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.mainNoiseScaleY, this.mainNoiseScaleY) != 0) {
                    n = "".length();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.mainNoiseScaleZ, this.mainNoiseScaleZ) != 0) {
                    n = "".length();
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else if (this.redstoneCount != factory.redstoneCount) {
                    n = "".length();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else if (this.redstoneMaxHeight != factory.redstoneMaxHeight) {
                    n = "".length();
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                }
                else if (this.redstoneMinHeight != factory.redstoneMinHeight) {
                    n = "".length();
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                }
                else if (this.redstoneSize != factory.redstoneSize) {
                    n = "".length();
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                }
                else if (this.riverSize != factory.riverSize) {
                    n = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (this.seaLevel != factory.seaLevel) {
                    n = "".length();
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.stretchY, this.stretchY) != 0) {
                    n = "".length();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                else if (Float.compare(factory.upperLimitScale, this.upperLimitScale) != 0) {
                    n = "".length();
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else if (this.useCaves != factory.useCaves) {
                    n = "".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                else if (this.useDungeons != factory.useDungeons) {
                    n = "".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else if (this.useLavaLakes != factory.useLavaLakes) {
                    n = "".length();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else if (this.useLavaOceans != factory.useLavaOceans) {
                    n = "".length();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else if (this.useMineShafts != factory.useMineShafts) {
                    n = "".length();
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                }
                else if (this.useRavines != factory.useRavines) {
                    n = "".length();
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else if (this.useStrongholds != factory.useStrongholds) {
                    n = "".length();
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                }
                else if (this.useTemples != factory.useTemples) {
                    n = "".length();
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else if (this.useMonuments != factory.useMonuments) {
                    n = "".length();
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
                else if (this.useVillages != factory.useVillages) {
                    n = "".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (this.useWaterLakes != factory.useWaterLakes) {
                    n = "".length();
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else if (this.waterLakeChance == factory.waterLakeChance) {
                    n = " ".length();
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                return n != 0;
            }
            return "".length() != 0;
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
            this.seaLevel = (0x47 ^ 0x78);
            this.useCaves = (" ".length() != 0);
            this.useDungeons = (" ".length() != 0);
            this.dungeonChance = (0x65 ^ 0x6D);
            this.useStrongholds = (" ".length() != 0);
            this.useVillages = (" ".length() != 0);
            this.useMineShafts = (" ".length() != 0);
            this.useTemples = (" ".length() != 0);
            this.useMonuments = (" ".length() != 0);
            this.useRavines = (" ".length() != 0);
            this.useWaterLakes = (" ".length() != 0);
            this.waterLakeChance = (0x74 ^ 0x70);
            this.useLavaLakes = (" ".length() != 0);
            this.lavaLakeChance = (0x2B ^ 0x7B);
            this.useLavaOceans = ("".length() != 0);
            this.fixedBiome = -" ".length();
            this.biomeSize = (0x37 ^ 0x33);
            this.riverSize = (0xBD ^ 0xB9);
            this.dirtSize = (0xBD ^ 0x9C);
            this.dirtCount = (0xA1 ^ 0xAB);
            this.dirtMinHeight = "".length();
            this.dirtMaxHeight = 79 + 232 - 201 + 146;
            this.gravelSize = (0x2B ^ 0xA);
            this.gravelCount = (0xBA ^ 0xB2);
            this.gravelMinHeight = "".length();
            this.gravelMaxHeight = 110 + 95 - 60 + 111;
            this.graniteSize = (0xB9 ^ 0x98);
            this.graniteCount = (0xB4 ^ 0xBE);
            this.graniteMinHeight = "".length();
            this.graniteMaxHeight = (0x3A ^ 0x6A);
            this.dioriteSize = (0x72 ^ 0x53);
            this.dioriteCount = (0x11 ^ 0x1B);
            this.dioriteMinHeight = "".length();
            this.dioriteMaxHeight = (0x9 ^ 0x59);
            this.andesiteSize = (0x9F ^ 0xBE);
            this.andesiteCount = (0xCB ^ 0xC1);
            this.andesiteMinHeight = "".length();
            this.andesiteMaxHeight = (0xEC ^ 0xBC);
            this.coalSize = (0x25 ^ 0x34);
            this.coalCount = (0x3F ^ 0x2B);
            this.coalMinHeight = "".length();
            this.coalMaxHeight = 57 + 120 - 176 + 127;
            this.ironSize = (0x15 ^ 0x1C);
            this.ironCount = (0x6C ^ 0x78);
            this.ironMinHeight = "".length();
            this.ironMaxHeight = (0x60 ^ 0x20);
            this.goldSize = (0x3F ^ 0x36);
            this.goldCount = "  ".length();
            this.goldMinHeight = "".length();
            this.goldMaxHeight = (0x6B ^ 0x4B);
            this.redstoneSize = (0xD ^ 0x5);
            this.redstoneCount = (0x1B ^ 0x13);
            this.redstoneMinHeight = "".length();
            this.redstoneMaxHeight = (0x4E ^ 0x5E);
            this.diamondSize = (0x5F ^ 0x57);
            this.diamondCount = " ".length();
            this.diamondMinHeight = "".length();
            this.diamondMaxHeight = (0x9E ^ 0x8E);
            this.lapisSize = (0x66 ^ 0x61);
            this.lapisCount = " ".length();
            this.lapisCenterHeight = (0x75 ^ 0x65);
            this.lapisSpread = (0x59 ^ 0x49);
        }
        
        @Override
        public String toString() {
            return Factory.JSON_ADAPTER.toJson((Object)this);
        }
        
        public static Factory jsonToFactory(final String s) {
            if (s.length() == 0) {
                return new Factory();
            }
            try {
                return (Factory)Factory.JSON_ADAPTER.fromJson(s, (Class)Factory.class);
            }
            catch (Exception ex) {
                return new Factory();
            }
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            JSON_ADAPTER = new GsonBuilder().registerTypeAdapter((Type)Factory.class, (Object)new Serializer()).create();
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
            this.seaLevel = (0x75 ^ 0x4A);
            this.useCaves = (" ".length() != 0);
            this.useDungeons = (" ".length() != 0);
            this.dungeonChance = (0x18 ^ 0x10);
            this.useStrongholds = (" ".length() != 0);
            this.useVillages = (" ".length() != 0);
            this.useMineShafts = (" ".length() != 0);
            this.useTemples = (" ".length() != 0);
            this.useMonuments = (" ".length() != 0);
            this.useRavines = (" ".length() != 0);
            this.useWaterLakes = (" ".length() != 0);
            this.waterLakeChance = (0x26 ^ 0x22);
            this.useLavaLakes = (" ".length() != 0);
            this.lavaLakeChance = (0xF1 ^ 0xA1);
            this.useLavaOceans = ("".length() != 0);
            this.fixedBiome = -" ".length();
            this.biomeSize = (0x41 ^ 0x45);
            this.riverSize = (0xA1 ^ 0xA5);
            this.dirtSize = (0x1E ^ 0x3F);
            this.dirtCount = (0x6B ^ 0x61);
            this.dirtMinHeight = "".length();
            this.dirtMaxHeight = 25 + 133 - 105 + 203;
            this.gravelSize = (0x5E ^ 0x7F);
            this.gravelCount = (0x57 ^ 0x5F);
            this.gravelMinHeight = "".length();
            this.gravelMaxHeight = 247 + 215 - 271 + 65;
            this.graniteSize = (0x4B ^ 0x6A);
            this.graniteCount = (0x69 ^ 0x63);
            this.graniteMinHeight = "".length();
            this.graniteMaxHeight = (0x68 ^ 0x38);
            this.dioriteSize = (0x7F ^ 0x5E);
            this.dioriteCount = (0x89 ^ 0x83);
            this.dioriteMinHeight = "".length();
            this.dioriteMaxHeight = (0xE2 ^ 0xB2);
            this.andesiteSize = (0x39 ^ 0x18);
            this.andesiteCount = (0x31 ^ 0x3B);
            this.andesiteMinHeight = "".length();
            this.andesiteMaxHeight = (0x92 ^ 0xC2);
            this.coalSize = (0x80 ^ 0x91);
            this.coalCount = (0x57 ^ 0x43);
            this.coalMinHeight = "".length();
            this.coalMaxHeight = 33 + 105 - 10 + 0;
            this.ironSize = (0xA3 ^ 0xAA);
            this.ironCount = (0x24 ^ 0x30);
            this.ironMinHeight = "".length();
            this.ironMaxHeight = (0x7 ^ 0x47);
            this.goldSize = (0xB3 ^ 0xBA);
            this.goldCount = "  ".length();
            this.goldMinHeight = "".length();
            this.goldMaxHeight = (0x4F ^ 0x6F);
            this.redstoneSize = (0x86 ^ 0x8E);
            this.redstoneCount = (0xB8 ^ 0xB0);
            this.redstoneMinHeight = "".length();
            this.redstoneMaxHeight = (0x16 ^ 0x6);
            this.diamondSize = (0xA7 ^ 0xAF);
            this.diamondCount = " ".length();
            this.diamondMinHeight = "".length();
            this.diamondMaxHeight = (0x93 ^ 0x83);
            this.lapisSize = (0x32 ^ 0x35);
            this.lapisCount = " ".length();
            this.lapisCenterHeight = (0x4E ^ 0x5E);
            this.lapisSpread = (0x3B ^ 0x2B);
            this.func_177863_a();
        }
        
        public ChunkProviderSettings func_177864_b() {
            return new ChunkProviderSettings(this, null);
        }
    }
}
