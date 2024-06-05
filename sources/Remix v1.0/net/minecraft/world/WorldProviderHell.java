package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.border.*;

public class WorldProviderHell extends WorldProvider
{
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 0.0f);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }
    
    @Override
    public Vec3 getFogColor(final float p_76562_1_, final float p_76562_2_) {
        return new Vec3(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
    }
    
    @Override
    protected void generateLightBrightnessTable() {
        final float var1 = 0.1f;
        for (int var2 = 0; var2 <= 15; ++var2) {
            final float var3 = 1.0f - var2 / 15.0f;
            this.lightBrightnessTable[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
        }
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int x, final int z) {
        return false;
    }
    
    @Override
    public float calculateCelestialAngle(final long p_76563_1_, final float p_76563_3_) {
        return 0.5f;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public boolean doesXZShowFog(final int p_76568_1_, final int p_76568_2_) {
        return true;
    }
    
    @Override
    public String getDimensionName() {
        return "Nether";
    }
    
    @Override
    public String getInternalNameSuffix() {
        return "_nether";
    }
    
    @Override
    public WorldBorder getWorldBorder() {
        return new WorldBorder() {
            @Override
            public double getCenterX() {
                return super.getCenterX() / 8.0;
            }
            
            @Override
            public double getCenterZ() {
                return super.getCenterZ() / 8.0;
            }
        };
    }
}
