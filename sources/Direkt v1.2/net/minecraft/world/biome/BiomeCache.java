package net.minecraft.world.biome;

import java.util.List;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.server.MinecraftServer;

public class BiomeCache {
	/** Reference to the WorldChunkManager */
	private final BiomeProvider chunkManager;

	/** The last time this BiomeCache was cleaned, in milliseconds. */
	private long lastCleanupTime;
	private final Long2ObjectMap<BiomeCache.Block> cacheMap = new Long2ObjectOpenHashMap(4096);
	private final List<BiomeCache.Block> cache = Lists.<BiomeCache.Block> newArrayList();

	public BiomeCache(BiomeProvider chunkManagerIn) {
		this.chunkManager = chunkManagerIn;
	}

	/**
	 * Returns a biome cache block at location specified.
	 */
	public BiomeCache.Block getBiomeCacheBlock(int x, int z) {
		x = x >> 4;
		z = z >> 4;
		long i = (x & 4294967295L) | ((z & 4294967295L) << 32);
		BiomeCache.Block biomecache$block = this.cacheMap.get(i);

		if (biomecache$block == null) {
			biomecache$block = new BiomeCache.Block(x, z);
			this.cacheMap.put(i, biomecache$block);
			this.cache.add(biomecache$block);
		}

		biomecache$block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
		return biomecache$block;
	}

	public Biome getBiome(int x, int z, Biome defaultValue) {
		Biome biome = this.getBiomeCacheBlock(x, z).getBiomeGenAt(x, z);
		return biome == null ? defaultValue : biome;
	}

	/**
	 * Removes BiomeCacheBlocks from this cache that haven't been accessed in at least 30 seconds.
	 */
	public void cleanupCache() {
		long i = MinecraftServer.getCurrentTimeMillis();
		long j = i - this.lastCleanupTime;

		if ((j > 7500L) || (j < 0L)) {
			this.lastCleanupTime = i;

			for (int k = 0; k < this.cache.size(); ++k) {
				BiomeCache.Block biomecache$block = this.cache.get(k);
				long l = i - biomecache$block.lastAccessTime;

				if ((l > 30000L) || (l < 0L)) {
					this.cache.remove(k--);
					long i1 = (biomecache$block.xPosition & 4294967295L) | ((biomecache$block.zPosition & 4294967295L) << 32);
					this.cacheMap.remove(i1);
				}
			}
		}
	}

	/**
	 * Returns the array of cached biome types in the BiomeCacheBlock at the given location.
	 */
	public Biome[] getCachedBiomes(int x, int z) {
		return this.getBiomeCacheBlock(x, z).biomes;
	}

	public class Block {
		public Biome[] biomes = new Biome[256];
		public int xPosition;
		public int zPosition;
		public long lastAccessTime;

		public Block(int x, int z) {
			this.xPosition = x;
			this.zPosition = z;
			BiomeCache.this.chunkManager.getBiomeGenAt(this.biomes, x << 4, z << 4, 16, 16, false);
		}

		public Biome getBiomeGenAt(int x, int z) {
			return this.biomes[(x & 15) | ((z & 15) << 4)];
		}
	}
}
