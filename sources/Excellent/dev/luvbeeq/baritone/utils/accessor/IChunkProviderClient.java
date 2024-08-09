package dev.luvbeeq.baritone.utils.accessor;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.world.chunk.Chunk;

public interface IChunkProviderClient {

    Long2ObjectMap<Chunk> loadedChunks();
}
