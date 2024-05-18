package net.minecraft.world.biome;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.server.*;
import com.google.common.collect.*;

public class BiomeCache
{
    private LongHashMap cacheMap;
    private final WorldChunkManager chunkManager;
    private List<Block> cache;
    private long lastCleanupTime;
    
    public void cleanupCache() {
        final long currentTimeMillis = MinecraftServer.getCurrentTimeMillis();
        final long n = currentTimeMillis - this.lastCleanupTime;
        if (n > 7500L || n < 0L) {
            this.lastCleanupTime = currentTimeMillis;
            int i = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (i < this.cache.size()) {
                final Block block = this.cache.get(i);
                final long n2 = currentTimeMillis - block.lastAccessTime;
                if (n2 > 30000L || n2 < 0L) {
                    this.cache.remove(i--);
                    this.cacheMap.remove((block.xPosition & 0xFFFFFFFFL) | (block.zPosition & 0xFFFFFFFFL) << (0x7D ^ 0x5D));
                }
                ++i;
            }
        }
    }
    
    public BiomeCache(final WorldChunkManager chunkManager) {
        this.cacheMap = new LongHashMap();
        this.cache = (List<Block>)Lists.newArrayList();
        this.chunkManager = chunkManager;
    }
    
    public BiomeGenBase[] getCachedBiomes(final int n, final int n2) {
        return this.getBiomeCacheBlock(n, n2).biomes;
    }
    
    public BiomeGenBase func_180284_a(final int n, final int n2, final BiomeGenBase biomeGenBase) {
        final BiomeGenBase biomeGen = this.getBiomeCacheBlock(n, n2).getBiomeGenAt(n, n2);
        BiomeGenBase biomeGenBase2;
        if (biomeGen == null) {
            biomeGenBase2 = biomeGenBase;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            biomeGenBase2 = biomeGen;
        }
        return biomeGenBase2;
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Block getBiomeCacheBlock(int n, int n2) {
        n >>= (0x77 ^ 0x73);
        n2 >>= (0x66 ^ 0x62);
        final long n3 = (n & 0xFFFFFFFFL) | (n2 & 0xFFFFFFFFL) << (0x75 ^ 0x55);
        Block block = (Block)this.cacheMap.getValueByKey(n3);
        if (block == null) {
            block = new Block(n, n2);
            this.cacheMap.add(n3, block);
            this.cache.add(block);
        }
        block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
        return block;
    }
    
    static WorldChunkManager access$0(final BiomeCache biomeCache) {
        return biomeCache.chunkManager;
    }
    
    public class Block
    {
        public int xPosition;
        public int zPosition;
        public BiomeGenBase[] biomes;
        public float[] rainfallValues;
        public long lastAccessTime;
        final BiomeCache this$0;
        
        public Block(final BiomeCache this$0, final int xPosition, final int zPosition) {
            this.this$0 = this$0;
            this.rainfallValues = new float[2 + 52 + 125 + 77];
            this.biomes = new BiomeGenBase[34 + 155 - 132 + 199];
            this.xPosition = xPosition;
            this.zPosition = zPosition;
            BiomeCache.access$0(this$0).getRainfall(this.rainfallValues, xPosition << (0x29 ^ 0x2D), zPosition << (0x27 ^ 0x23), 0x10 ^ 0x0, 0x4D ^ 0x5D);
            BiomeCache.access$0(this$0).getBiomeGenAt(this.biomes, xPosition << (0x75 ^ 0x71), zPosition << (0x66 ^ 0x62), 0x18 ^ 0x8, 0x74 ^ 0x64, "".length() != 0);
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
                if (0 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public BiomeGenBase getBiomeGenAt(final int n, final int n2) {
            return this.biomes[(n & (0x3 ^ 0xC)) | (n2 & (0x52 ^ 0x5D)) << (0xA1 ^ 0xA5)];
        }
    }
}
