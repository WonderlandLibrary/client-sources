package net.minecraft.src;

public class BiomeCacheBlock
{
    public float[] temperatureValues;
    public float[] rainfallValues;
    public BiomeGenBase[] biomes;
    public int xPosition;
    public int zPosition;
    public long lastAccessTime;
    final BiomeCache theBiomeCache;
    
    public BiomeCacheBlock(final BiomeCache par1BiomeCache, final int par2, final int par3) {
        this.theBiomeCache = par1BiomeCache;
        this.temperatureValues = new float[256];
        this.rainfallValues = new float[256];
        this.biomes = new BiomeGenBase[256];
        this.xPosition = par2;
        this.zPosition = par3;
        BiomeCache.getChunkManager(par1BiomeCache).getTemperatures(this.temperatureValues, par2 << 4, par3 << 4, 16, 16);
        BiomeCache.getChunkManager(par1BiomeCache).getRainfall(this.rainfallValues, par2 << 4, par3 << 4, 16, 16);
        BiomeCache.getChunkManager(par1BiomeCache).getBiomeGenAt(this.biomes, par2 << 4, par3 << 4, 16, 16, false);
    }
    
    public BiomeGenBase getBiomeGenAt(final int par1, final int par2) {
        return this.biomes[(par1 & 0xF) | (par2 & 0xF) << 4];
    }
}
