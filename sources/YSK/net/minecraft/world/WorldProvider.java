package net.minecraft.world;

import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.border.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.biome.*;
import net.minecraft.init.*;

public abstract class WorldProvider
{
    protected boolean isHellWorld;
    private String generatorSettings;
    private WorldType terrainType;
    protected final float[] lightBrightnessTable;
    private final float[] colorsSunriseSunset;
    protected WorldChunkManager worldChunkMgr;
    protected World worldObj;
    protected int dimensionId;
    protected boolean hasNoSky;
    public static final float[] moonPhaseFactors;
    
    public BlockPos getSpawnCoordinate() {
        return null;
    }
    
    public float[] calcSunriseSunsetColors(final float n, final float n2) {
        final float n3 = 0.4f;
        final float n4 = MathHelper.cos(n * 3.1415927f * 2.0f) - 0.0f;
        final float n5 = -0.0f;
        if (n4 >= n5 - n3 && n4 <= n5 + n3) {
            final float n6 = (n4 - n5) / n3 * 0.5f + 0.5f;
            final float n7 = 1.0f - (1.0f - MathHelper.sin(n6 * 3.1415927f)) * 0.99f;
            final float n8 = n7 * n7;
            this.colorsSunriseSunset["".length()] = n6 * 0.3f + 0.7f;
            this.colorsSunriseSunset[" ".length()] = n6 * n6 * 0.7f + 0.2f;
            this.colorsSunriseSunset["  ".length()] = n6 * n6 * 0.0f + 0.2f;
            this.colorsSunriseSunset["   ".length()] = n8;
            return this.colorsSunriseSunset;
        }
        return null;
    }
    
    public boolean isSurfaceWorld() {
        return " ".length() != 0;
    }
    
    public Vec3 getFogColor(final float n, final float n2) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(n * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        return new Vec3(0.7529412f * (clamp_float * 0.94f + 0.06f), 0.84705883f * (clamp_float * 0.94f + 0.06f), 1.0f * (clamp_float * 0.91f + 0.09f));
    }
    
    public boolean doesWaterVaporize() {
        return this.isHellWorld;
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void generateLightBrightnessTable() {
        final float n = 0.0f;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i <= (0x41 ^ 0x4E)) {
            final float n2 = 1.0f - i / 15.0f;
            this.lightBrightnessTable[i] = (1.0f - n2) / (n2 * 3.0f + 1.0f) * (1.0f - n) + n;
            ++i;
        }
    }
    
    public int getAverageGroundLevel() {
        int n;
        if (this.terrainType == WorldType.FLAT) {
            n = (0x50 ^ 0x54);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            n = this.worldObj.func_181545_F() + " ".length();
        }
        return n;
    }
    
    public abstract String getDimensionName();
    
    public boolean canRespawnHere() {
        return " ".length() != 0;
    }
    
    public int getDimensionId() {
        return this.dimensionId;
    }
    
    public IChunkProvider createChunkGenerator() {
        IChunkProvider chunkProvider;
        if (this.terrainType == WorldType.FLAT) {
            chunkProvider = new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (this.terrainType == WorldType.DEBUG_WORLD) {
            chunkProvider = new ChunkProviderDebug(this.worldObj);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (this.terrainType == WorldType.CUSTOMIZED) {
            chunkProvider = new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings);
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            chunkProvider = new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings);
        }
        return chunkProvider;
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.worldChunkMgr;
    }
    
    public int getMoonPhase(final long n) {
        return (int)(n / 24000L % 8L + 8L) % (0x78 ^ 0x70);
    }
    
    public boolean getHasNoSky() {
        return this.hasNoSky;
    }
    
    public static WorldProvider getProviderForDimension(final int n) {
        WorldProvider worldProvider;
        if (n == -" ".length()) {
            worldProvider = new WorldProviderHell();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (n == 0) {
            worldProvider = new WorldProviderSurface();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else if (n == " ".length()) {
            worldProvider = new WorldProviderEnd();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            worldProvider = null;
        }
        return worldProvider;
    }
    
    public boolean isSkyColored() {
        return " ".length() != 0;
    }
    
    public final void registerWorld(final World worldObj) {
        this.worldObj = worldObj;
        this.terrainType = worldObj.getWorldInfo().getTerrainType();
        this.generatorSettings = worldObj.getWorldInfo().getGeneratorOptions();
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }
    
    public WorldBorder getWorldBorder() {
        return new WorldBorder();
    }
    
    public float[] getLightBrightnessTable() {
        return this.lightBrightnessTable;
    }
    
    public double getVoidFogYFactor() {
        double n;
        if (this.terrainType == WorldType.FLAT) {
            n = 1.0;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = 0.03125;
        }
        return n;
    }
    
    protected void registerWorldChunkManager() {
        final WorldType terrainType = this.worldObj.getWorldInfo().getTerrainType();
        if (terrainType == WorldType.FLAT) {
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.getBiomeFromBiomeList(FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions()).getBiome(), BiomeGenBase.field_180279_ad), 0.5f);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (terrainType == WorldType.DEBUG_WORLD) {
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.plains, 0.0f);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            this.worldChunkMgr = new WorldChunkManager(this.worldObj);
        }
    }
    
    public WorldProvider() {
        this.lightBrightnessTable = new float[0x81 ^ 0x91];
        this.colorsSunriseSunset = new float[0x52 ^ 0x56];
    }
    
    public float getCloudHeight() {
        return 128.0f;
    }
    
    static {
        final float[] moonPhaseFactors2 = new float[0x94 ^ 0x9C];
        moonPhaseFactors2["".length()] = 1.0f;
        moonPhaseFactors2[" ".length()] = 0.75f;
        moonPhaseFactors2["  ".length()] = 0.5f;
        moonPhaseFactors2["   ".length()] = 0.25f;
        moonPhaseFactors2[0x5 ^ 0x1] = 0.0f;
        moonPhaseFactors2[0xA0 ^ 0xA5] = 0.25f;
        moonPhaseFactors2[0xBD ^ 0xBB] = 0.5f;
        moonPhaseFactors2[0x5E ^ 0x59] = 0.75f;
        moonPhaseFactors = moonPhaseFactors2;
    }
    
    public boolean doesXZShowFog(final int n, final int n2) {
        return "".length() != 0;
    }
    
    public float calculateCelestialAngle(final long n, final float n2) {
        float n3 = ((int)(n % 24000L) + n2) / 24000.0f - 0.25f;
        if (n3 < 0.0f) {
            ++n3;
        }
        if (n3 > 1.0f) {
            --n3;
        }
        final float n4 = 1.0f - (float)((Math.cos(n3 * 3.141592653589793) + 1.0) / 2.0);
        return n4 + (n4 - n4) / 3.0f;
    }
    
    public boolean canCoordinateBeSpawn(final int n, final int n2) {
        if (this.worldObj.getGroundAboveSeaLevel(new BlockPos(n, "".length(), n2)) == Blocks.grass) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public abstract String getInternalNameSuffix();
}
