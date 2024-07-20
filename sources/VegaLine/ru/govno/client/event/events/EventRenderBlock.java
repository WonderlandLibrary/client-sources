/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.w3c.dom.events.EventTarget;
import ru.govno.client.event.types.EventCancellable;

public class EventRenderBlock
extends EventCancellable {
    private final IBlockState state;
    private final BlockPos pos;
    private final IBlockAccess access;
    private final BufferBuilder bufferBuilder;

    public EventRenderBlock(IBlockState state, BlockPos pos, IBlockAccess access, BufferBuilder bufferBuilder) {
        this.state = state;
        this.pos = pos;
        this.access = access;
        this.bufferBuilder = bufferBuilder;
    }

    public IBlockState getState() {
        return this.state;
    }

    public BufferBuilder getBufferBuilder() {
        return this.bufferBuilder;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public IBlockAccess getAccess() {
        return this.access;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public EventTarget getTarget() {
        return null;
    }

    @Override
    public EventTarget getCurrentTarget() {
        return null;
    }

    @Override
    public short getEventPhase() {
        return 0;
    }

    @Override
    public boolean getBubbles() {
        return false;
    }

    @Override
    public boolean getCancelable() {
        return false;
    }

    @Override
    public long getTimeStamp() {
        return 0L;
    }

    @Override
    public void stopPropagation() {
    }

    @Override
    public void preventDefault() {
    }

    @Override
    public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
    }
}

