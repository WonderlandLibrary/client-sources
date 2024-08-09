/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

class UnpooledUnsafeNoCleanerDirectByteBuf
extends UnpooledUnsafeDirectByteBuf {
    UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator byteBufAllocator, int n, int n2) {
        super(byteBufAllocator, n, n2);
    }

    @Override
    protected ByteBuffer allocateDirect(int n) {
        return PlatformDependent.allocateDirectNoCleaner(n);
    }

    ByteBuffer reallocateDirect(ByteBuffer byteBuffer, int n) {
        return PlatformDependent.reallocateDirectNoCleaner(byteBuffer, n);
    }

    @Override
    protected void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectNoCleaner(byteBuffer);
    }

    @Override
    public ByteBuf capacity(int n) {
        this.checkNewCapacity(n);
        int n2 = this.capacity();
        if (n == n2) {
            return this;
        }
        ByteBuffer byteBuffer = this.reallocateDirect(this.buffer, n);
        if (n < n2) {
            if (this.readerIndex() < n) {
                if (this.writerIndex() > n) {
                    this.writerIndex(n);
                }
            } else {
                this.setIndex(n, n);
            }
        }
        this.setByteBuffer(byteBuffer, true);
        return this;
    }
}

