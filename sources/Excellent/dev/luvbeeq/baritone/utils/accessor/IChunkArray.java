package dev.luvbeeq.baritone.utils.accessor;

import net.minecraft.world.chunk.Chunk;

import java.util.concurrent.atomic.AtomicReferenceArray;

public interface IChunkArray {
    void copyFrom(IChunkArray other);

    AtomicReferenceArray<Chunk> getChunks();

    int centerX();

    int centerZ();

    int viewDistance();
}
