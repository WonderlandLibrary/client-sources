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
    UnpooledUnsafeNoCleanerDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        super(alloc, initialCapacity, maxCapacity);
    }

    @Override
    protected ByteBuffer allocateDirect(int initialCapacity) {
        return PlatformDependent.allocateDirectNoCleaner(initialCapacity);
    }

    ByteBuffer reallocateDirect(ByteBuffer oldBuffer, int initialCapacity) {
        return PlatformDependent.reallocateDirectNoCleaner(oldBuffer, initialCapacity);
    }

    @Override
    protected void freeDirect(ByteBuffer buffer) {
        PlatformDependent.freeDirectNoCleaner(buffer);
    }

    @Override
    public ByteBuf capacity(int newCapacity) {
        this.checkNewCapacity(newCapacity);
        int readerIndex = this.readerIndex();
        int writerIndex = this.writerIndex();
        int oldCapacity = this.capacity();
        if (newCapacity > oldCapacity) {
            ByteBuffer oldBuffer = this.buffer;
            ByteBuffer newBuffer = this.reallocateDirect(oldBuffer, newCapacity);
            this.setByteBuffer(newBuffer, false);
        } else if (newCapacity < oldCapacity) {
            ByteBuffer oldBuffer = this.buffer;
            ByteBuffer newBuffer = this.allocateDirect(newCapacity);
            if (readerIndex < newCapacity) {
                if (writerIndex > newCapacity) {
                    writerIndex = newCapacity;
                    this.writerIndex(writerIndex);
                }
                oldBuffer.position(readerIndex).limit(writerIndex);
                newBuffer.position(readerIndex).limit(writerIndex);
                newBuffer.put(oldBuffer);
                newBuffer.clear();
            } else {
                this.setIndex(newCapacity, newCapacity);
            }
            this.setByteBuffer(newBuffer, true);
        }
        return this;
    }
}

