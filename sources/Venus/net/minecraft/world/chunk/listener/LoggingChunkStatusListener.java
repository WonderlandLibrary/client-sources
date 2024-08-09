/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.listener;

import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingChunkStatusListener
implements IChunkStatusListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int totalChunks;
    private int loadedChunks;
    private long startTime;
    private long nextLogTime = Long.MAX_VALUE;

    public LoggingChunkStatusListener(int n) {
        int n2 = n * 2 + 1;
        this.totalChunks = n2 * n2;
    }

    @Override
    public void start(ChunkPos chunkPos) {
        this.startTime = this.nextLogTime = Util.milliTime();
    }

    @Override
    public void statusChanged(ChunkPos chunkPos, @Nullable ChunkStatus chunkStatus) {
        if (chunkStatus == ChunkStatus.FULL) {
            ++this.loadedChunks;
        }
        int n = this.getPercentDone();
        if (Util.milliTime() > this.nextLogTime) {
            this.nextLogTime += 500L;
            LOGGER.info(new TranslationTextComponent("menu.preparingSpawn", MathHelper.clamp(n, 0, 100)).getString());
        }
    }

    @Override
    public void stop() {
        LOGGER.info("Time elapsed: {} ms", (Object)(Util.milliTime() - this.startTime));
        this.nextLogTime = Long.MAX_VALUE;
    }

    public int getPercentDone() {
        return MathHelper.floor((float)this.loadedChunks * 100.0f / (float)this.totalChunks);
    }
}

