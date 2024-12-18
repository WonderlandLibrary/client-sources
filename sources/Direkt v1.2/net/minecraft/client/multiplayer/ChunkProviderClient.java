package net.minecraft.client.multiplayer;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Objects;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderClient implements IChunkProvider {
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * The completely empty chunk used by ChunkProviderClient when chunkMapping doesn't contain the requested coordinates.
	 */
	private final Chunk blankChunk;
	private final Long2ObjectMap<Chunk> chunkMapping = new Long2ObjectOpenHashMap<Chunk>(8192) {
		@Override
		protected void rehash(int p_rehash_1_) {
			if (p_rehash_1_ > this.key.length) {
				super.rehash(p_rehash_1_);
			}
		}
	};

	/** Reference to the World object. */
	private final World worldObj;

	public ChunkProviderClient(World worldIn) {
		this.blankChunk = new EmptyChunk(worldIn, 0, 0);
		this.worldObj = worldIn;
	}

	/**
	 * Unload chunk from ChunkProviderClient's hashmap. Called in response to a Packet50PreChunk with its mode field set to false
	 */
	public void unloadChunk(int x, int z) {
		Chunk chunk = this.provideChunk(x, z);

		if (!chunk.isEmpty()) {
			chunk.onChunkUnload();
		}

		this.chunkMapping.remove(ChunkPos.chunkXZ2Int(x, z));
	}

	@Override
	@Nullable
	public Chunk getLoadedChunk(int x, int z) {
		return this.chunkMapping.get(ChunkPos.chunkXZ2Int(x, z));
	}

	/**
	 * loads or generates the chunk at the chunk location specified
	 */
	public Chunk loadChunk(int chunkX, int chunkZ) {
		Chunk chunk = new Chunk(this.worldObj, chunkX, chunkZ);
		this.chunkMapping.put(ChunkPos.chunkXZ2Int(chunkX, chunkZ), chunk);
		chunk.setChunkLoaded(true);
		return chunk;
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		return Objects.firstNonNull(this.getLoadedChunk(x, z), this.blankChunk);
	}

	/**
	 * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
	 */
	@Override
	public boolean unloadQueuedChunks() {
		long i = System.currentTimeMillis();

		for (Chunk chunk : this.chunkMapping.values()) {
			chunk.onTick((System.currentTimeMillis() - i) > 5L);
		}

		if ((System.currentTimeMillis() - i) > 100L) {
			LOGGER.info("Warning: Clientside chunk ticking took {} ms", new Object[] { Long.valueOf(System.currentTimeMillis() - i) });
		}

		return false;
	}

	/**
	 * Converts the instance data to a readable string.
	 */
	@Override
	public String makeString() {
		return "MultiplayerChunkCache: " + this.chunkMapping.size() + ", " + this.chunkMapping.size();
	}
}
