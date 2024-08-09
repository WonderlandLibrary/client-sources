/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.util.internal.ObjectUtil;

public final class PreferHeapByteBufAllocator
implements ByteBufAllocator {
    private final ByteBufAllocator allocator;

    public PreferHeapByteBufAllocator(ByteBufAllocator byteBufAllocator) {
        this.allocator = ObjectUtil.checkNotNull(byteBufAllocator, "allocator");
    }

    @Override
    public ByteBuf buffer() {
        return this.allocator.heapBuffer();
    }

    @Override
    public ByteBuf buffer(int n) {
        return this.allocator.heapBuffer(n);
    }

    @Override
    public ByteBuf buffer(int n, int n2) {
        return this.allocator.heapBuffer(n, n2);
    }

    @Override
    public ByteBuf ioBuffer() {
        return this.allocator.heapBuffer();
    }

    @Override
    public ByteBuf ioBuffer(int n) {
        return this.allocator.heapBuffer(n);
    }

    @Override
    public ByteBuf ioBuffer(int n, int n2) {
        return this.allocator.heapBuffer(n, n2);
    }

    @Override
    public ByteBuf heapBuffer() {
        return this.allocator.heapBuffer();
    }

    @Override
    public ByteBuf heapBuffer(int n) {
        return this.allocator.heapBuffer(n);
    }

    @Override
    public ByteBuf heapBuffer(int n, int n2) {
        return this.allocator.heapBuffer(n, n2);
    }

    @Override
    public ByteBuf directBuffer() {
        return this.allocator.directBuffer();
    }

    @Override
    public ByteBuf directBuffer(int n) {
        return this.allocator.directBuffer(n);
    }

    @Override
    public ByteBuf directBuffer(int n, int n2) {
        return this.allocator.directBuffer(n, n2);
    }

    @Override
    public CompositeByteBuf compositeBuffer() {
        return this.allocator.compositeHeapBuffer();
    }

    @Override
    public CompositeByteBuf compositeBuffer(int n) {
        return this.allocator.compositeHeapBuffer(n);
    }

    @Override
    public CompositeByteBuf compositeHeapBuffer() {
        return this.allocator.compositeHeapBuffer();
    }

    @Override
    public CompositeByteBuf compositeHeapBuffer(int n) {
        return this.allocator.compositeHeapBuffer(n);
    }

    @Override
    public CompositeByteBuf compositeDirectBuffer() {
        return this.allocator.compositeDirectBuffer();
    }

    @Override
    public CompositeByteBuf compositeDirectBuffer(int n) {
        return this.allocator.compositeDirectBuffer(n);
    }

    @Override
    public boolean isDirectBufferPooled() {
        return this.allocator.isDirectBufferPooled();
    }

    @Override
    public int calculateNewCapacity(int n, int n2) {
        return this.allocator.calculateNewCapacity(n, n2);
    }
}

