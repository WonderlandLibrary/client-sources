/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.world.chunk.Chunk;

public interface IChunkProvider {
    @Nullable
    public Chunk getLoadedChunk(int var1, int var2);

    public Chunk provideChunk(int var1, int var2);

    public boolean unloadQueuedChunks();

    public String makeString();

    public boolean func_191062_e(int var1, int var2);
}

