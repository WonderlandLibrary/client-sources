/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.Event;

public class EventRenderChunk
extends Event {
    public BlockPos blockPos;
    public RenderChunk renderChunk;

    public EventRenderChunk(RenderChunk renderChunk, BlockPos blockPos) {
        this.blockPos = blockPos;
        this.renderChunk = renderChunk;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public RenderChunk getRenderChunk() {
        return this.renderChunk;
    }

    public void setRenderChunk(RenderChunk renderChunk) {
        this.renderChunk = renderChunk;
    }
}

