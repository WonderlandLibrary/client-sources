/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.listener;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.listener.IChunkStatusListener;

public class ChainedChunkStatusListener
implements IChunkStatusListener {
    private final IChunkStatusListener delegate;
    private final DelegatedTaskExecutor<Runnable> executor;

    public ChainedChunkStatusListener(IChunkStatusListener iChunkStatusListener, Executor executor) {
        this.delegate = iChunkStatusListener;
        this.executor = DelegatedTaskExecutor.create(executor, "progressListener");
    }

    @Override
    public void start(ChunkPos chunkPos) {
        this.executor.enqueue(() -> this.lambda$start$0(chunkPos));
    }

    @Override
    public void statusChanged(ChunkPos chunkPos, @Nullable ChunkStatus chunkStatus) {
        this.executor.enqueue(() -> this.lambda$statusChanged$1(chunkPos, chunkStatus));
    }

    @Override
    public void stop() {
        this.executor.enqueue(this.delegate::stop);
    }

    private void lambda$statusChanged$1(ChunkPos chunkPos, ChunkStatus chunkStatus) {
        this.delegate.statusChanged(chunkPos, chunkStatus);
    }

    private void lambda$start$0(ChunkPos chunkPos) {
        this.delegate.start(chunkPos);
    }
}

