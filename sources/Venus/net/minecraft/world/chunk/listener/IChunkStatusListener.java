/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.listener;

import javax.annotation.Nullable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;

public interface IChunkStatusListener {
    public void start(ChunkPos var1);

    public void statusChanged(ChunkPos var1, @Nullable ChunkStatus var2);

    public void stop();
}

