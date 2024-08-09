/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.lighting.WorldLightManager;

public abstract class AbstractChunkProvider
implements IChunkLightProvider,
AutoCloseable {
    @Nullable
    public Chunk getChunk(int n, int n2, boolean bl) {
        return (Chunk)this.getChunk(n, n2, ChunkStatus.FULL, bl);
    }

    @Nullable
    public Chunk getChunkNow(int n, int n2) {
        return this.getChunk(n, n2, true);
    }

    @Override
    @Nullable
    public IBlockReader getChunkForLight(int n, int n2) {
        return this.getChunk(n, n2, ChunkStatus.EMPTY, true);
    }

    public boolean chunkExists(int n, int n2) {
        return this.getChunk(n, n2, ChunkStatus.FULL, true) != null;
    }

    @Nullable
    public abstract IChunk getChunk(int var1, int var2, ChunkStatus var3, boolean var4);

    public abstract String makeString();

    @Override
    public void close() throws IOException {
    }

    public abstract WorldLightManager getLightManager();

    public void setAllowedSpawnTypes(boolean bl, boolean bl2) {
    }

    public void forceChunk(ChunkPos chunkPos, boolean bl) {
    }

    public boolean isChunkLoaded(Entity entity2) {
        return false;
    }

    public boolean isChunkLoaded(ChunkPos chunkPos) {
        return false;
    }

    public boolean canTick(BlockPos blockPos) {
        return false;
    }
}

