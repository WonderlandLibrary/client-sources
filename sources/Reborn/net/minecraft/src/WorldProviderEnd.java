package net.minecraft.src;

public class WorldProviderEnd extends WorldProvider
{
    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5f, 0.0f);
        this.dimensionId = 1;
        this.hasNoSky = true;
    }
    
    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderEnd(this.worldObj, this.worldObj.getSeed());
    }
    
    @Override
    public float calculateCelestialAngle(final long par1, final float par3) {
        return 0.0f;
    }
    
    @Override
    public float[] calcSunriseSunsetColors(final float par1, final float par2) {
        return null;
    }
    
    @Override
    public Vec3 getFogColor(final float par1, final float par2) {
        final int var3 = 10518688;
        float var4 = MathHelper.cos(par1 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        float var5 = (var3 >> 16 & 0xFF) / 255.0f;
        float var6 = (var3 >> 8 & 0xFF) / 255.0f;
        float var7 = (var3 & 0xFF) / 255.0f;
        var5 *= var4 * 0.0f + 0.15f;
        var6 *= var4 * 0.0f + 0.15f;
        var7 *= var4 * 0.0f + 0.15f;
        return this.worldObj.getWorldVec3Pool().getVecFromPool(var5, var6, var7);
    }
    
    @Override
    public boolean isSkyColored() {
        return false;
    }
    
    @Override
    public boolean canRespawnHere() {
        return false;
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return false;
    }
    
    @Override
    public float getCloudHeight() {
        return 8.0f;
    }
    
    @Override
    public boolean canCoordinateBeSpawn(final int par1, final int par2) {
        final int var3 = this.worldObj.getFirstUncoveredBlock(par1, par2);
        return var3 != 0 && Block.blocksList[var3].blockMaterial.blocksMovement();
    }
    
    @Override
    public ChunkCoordinates getEntrancePortalLocation() {
        return new ChunkCoordinates(100, 50, 0);
    }
    
    @Override
    public int getAverageGroundLevel() {
        return 50;
    }
    
    @Override
    public boolean doesXZShowFog(final int par1, final int par2) {
        return true;
    }
    
    @Override
    public String getDimensionName() {
        return "The End";
    }
}
