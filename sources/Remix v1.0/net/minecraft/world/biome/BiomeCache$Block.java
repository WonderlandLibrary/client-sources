package net.minecraft.world.biome;

public class Block
{
    public float[] rainfallValues;
    public BiomeGenBase[] biomes;
    public int xPosition;
    public int zPosition;
    public long lastAccessTime;
    
    public Block(final int p_i1972_2_, final int p_i1972_3_) {
        this.rainfallValues = new float[256];
        this.biomes = new BiomeGenBase[256];
        this.xPosition = p_i1972_2_;
        this.zPosition = p_i1972_3_;
        BiomeCache.access$000(BiomeCache.this).getRainfall(this.rainfallValues, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16);
        BiomeCache.access$000(BiomeCache.this).getBiomeGenAt(this.biomes, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16, false);
    }
    
    public BiomeGenBase getBiomeGenAt(final int p_76885_1_, final int p_76885_2_) {
        return this.biomes[(p_76885_1_ & 0xF) | (p_76885_2_ & 0xF) << 4];
    }
}
