package net.minecraft.src;

public class WorldProviderHell extends WorldProvider
{
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 1.0f, 0.0f);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }
    
    @Override
    public Vec3 getFogColor(final float par1, final float par2) {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
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
        return new ChunkProviderHell(this.worldObj, this.worldObj.getSeed());
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int par1, final int par2) {
        return false;
    }
    
    @Override
    public float calculateCelestialAngle(final long par1, final float par3) {
        return 0.5f;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public boolean doesXZShowFog(final int par1, final int par2) {
        return true;
    }
    
    @Override
    public String getDimensionName() {
        return "Nether";
    }
}
