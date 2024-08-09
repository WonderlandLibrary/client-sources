/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.listener;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.chunk.listener.LoggingChunkStatusListener;

public class TrackingChunkStatusListener
implements IChunkStatusListener {
    private final LoggingChunkStatusListener loggingListener;
    private final Long2ObjectOpenHashMap<ChunkStatus> statuses;
    private ChunkPos center = new ChunkPos(0, 0);
    private final int diameter;
    private final int positionOffset;
    private final int field_219531_f;
    private boolean tracking;

    public TrackingChunkStatusListener(int n) {
        this.loggingListener = new LoggingChunkStatusListener(n);
        this.diameter = n * 2 + 1;
        this.positionOffset = n + ChunkStatus.maxDistance();
        this.field_219531_f = this.positionOffset * 2 + 1;
        this.statuses = new Long2ObjectOpenHashMap();
    }

    @Override
    public void start(ChunkPos chunkPos) {
        if (this.tracking) {
            this.loggingListener.start(chunkPos);
            this.center = chunkPos;
        }
    }

    @Override
    public void statusChanged(ChunkPos chunkPos, @Nullable ChunkStatus chunkStatus) {
        if (this.tracking) {
            this.loggingListener.statusChanged(chunkPos, chunkStatus);
            if (chunkStatus == null) {
                this.statuses.remove(chunkPos.asLong());
            } else {
                this.statuses.put(chunkPos.asLong(), chunkStatus);
            }
        }
    }

    public void startTracking() {
        this.tracking = true;
        this.statuses.clear();
    }

    @Override
    public void stop() {
        this.tracking = false;
        this.loggingListener.stop();
    }

    public int getDiameter() {
        return this.diameter;
    }

    public int func_219523_d() {
        return this.field_219531_f;
    }

    public int getPercentDone() {
        return this.loggingListener.getPercentDone();
    }

    @Nullable
    public ChunkStatus getStatus(int n, int n2) {
        return this.statuses.get(ChunkPos.asLong(n + this.center.x - this.positionOffset, n2 + this.center.z - this.positionOffset));
    }
}

