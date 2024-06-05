package net.minecraft.src;

public abstract class WorldProvider
{
    public World worldObj;
    public WorldType terrainType;
    public String field_82913_c;
    public WorldChunkManager worldChunkMgr;
    public boolean isHellWorld;
    public boolean hasNoSky;
    public float[] lightBrightnessTable;
    public int dimensionId;
    private float[] colorsSunriseSunset;
    
    public WorldProvider() {
        this.isHellWorld = false;
        this.hasNoSky = false;
        this.lightBrightnessTable = new float[16];
        this.dimensionId = 0;
        this.colorsSunriseSunset = new float[4];
    }
    
    public final void registerWorld(final World par1World) {
        this.worldObj = par1World;
        this.terrainType = par1World.getWorldInfo().getTerrainType();
        this.field_82913_c = par1World.getWorldInfo().getGeneratorOptions();
        this.registerWorldChunkManager();
        this.generateLightBrightnessTable();
    }
    
    protected void generateLightBrightnessTable() {
        final float var1 = 0.0f;
        for (int var2 = 0; var2 <= 15; ++var2) {
            final float var3 = 1.0f - var2 / 15.0f;
            this.lightBrightnessTable[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
        }
    }
    
    protected void registerWorldChunkManager() {
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT) {
            final FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.biomeList[var1.getBiome()], 0.5f, 0.5f);
        }
        else {
            this.worldChunkMgr = new WorldChunkManager(this.worldObj);
        }
    }
    
    public IChunkProvider createChunkGenerator() {
        return (this.terrainType == WorldType.FLAT) ? new ChunkProviderFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.field_82913_c) : new ChunkProviderGenerate(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
    }
    
    public boolean canCoordinateBeSpawn(final int par1, final int par2) {
        final int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
        return var3 == Block.grass.blockID;
    }
    
    public float calculateCelestialAngle(final long par1, final float par3) {
        final int var4 = (int)(par1 % 24000L);
        float var5 = (var4 + par3) / 24000.0f - 0.25f;
        if (var5 < 0.0f) {
            ++var5;
        }
        if (var5 > 1.0f) {
            --var5;
        }
        final float var6 = var5;
        var5 = 1.0f - (float)((Math.cos(var5 * 3.141592653589793) + 1.0) / 2.0);
        var5 = var6 + (var5 - var6) / 3.0f;
        return var5;
    }
    
    public int getMoonPhase(final long par1) {
        return (int)(par1 / 24000L) % 8;
    }
    
    public boolean isSurfaceWorld() {
        return true;
    }
    
    public float[] calcSunriseSunsetColors(final float par1, final float par2) {
        final float var3 = 0.4f;
        final float var4 = MathHelper.cos(par1 * 3.1415927f * 2.0f) - 0.0f;
        final float var5 = -0.0f;
        if (var4 >= var5 - var3 && var4 <= var5 + var3) {
            final float var6 = (var4 - var5) / var3 * 0.5f + 0.5f;
            float var7 = 1.0f - (1.0f - MathHelper.sin(var6 * 3.1415927f)) * 0.99f;
            var7 *= var7;
            this.colorsSunriseSunset[0] = var6 * 0.3f + 0.7f;
            this.colorsSunriseSunset[1] = var6 * var6 * 0.7f + 0.2f;
            this.colorsSunriseSunset[2] = var6 * var6 * 0.0f + 0.2f;
            this.colorsSunriseSunset[3] = var7;
            return this.colorsSunriseSunset;
        }
        return null;
    }
    
    public Vec3 getFogColor(final float par1, final float par2) {
        float var3 = MathHelper.cos(par1 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = 0.7529412f;
        float var5 = 0.84705883f;
        float var6 = 1.0f;
        var4 *= var3 * 0.94f + 0.06f;
        var5 *= var3 * 0.94f + 0.06f;
        var6 *= var3 * 0.91f + 0.09f;
        return this.worldObj.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
    }
    
    public boolean canRespawnHere() {
        return true;
    }
    
    public static WorldProvider getProviderForDimension(final int par0) {
        return (par0 == -1) ? new WorldProviderHell() : ((par0 == 0) ? new WorldProviderSurface() : ((par0 == 1) ? new WorldProviderEnd() : null));
    }
    
    public float getCloudHeight() {
        return 128.0f;
    }
    
    public boolean isSkyColored() {
        return true;
    }
    
    public ChunkCoordinates getEntrancePortalLocation() {
        return null;
    }
    
    public int getAverageGroundLevel() {
        return (this.terrainType == WorldType.FLAT) ? 4 : 64;
    }
    
    public boolean getWorldHasVoidParticles() {
        return this.terrainType != WorldType.FLAT && !this.hasNoSky;
    }
    
    public double getVoidFogYFactor() {
        return (this.terrainType == WorldType.FLAT) ? 1.0 : 0.03125;
    }
    
    public boolean doesXZShowFog(final int par1, final int par2) {
        return false;
    }
    
    public abstract String getDimensionName();
}
