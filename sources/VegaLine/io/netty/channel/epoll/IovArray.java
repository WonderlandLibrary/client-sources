/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.epoll.Native;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

final class IovArray
implements ChannelOutboundBuffer.MessageProcessor {
    private static final int ADDRESS_SIZE = PlatformDependent.addressSize();
    private static final int IOV_SIZE = 2 * ADDRESS_SIZE;
    private static final int CAPACITY = Native.IOV_MAX * IOV_SIZE;
    private final long memoryAddress = PlatformDependent.allocateMemory(CAPACITY);
    private int count;
    private long size;

    IovArray() {
    }

    void clear() {
        this.count = 0;
        this.size = 0L;
    }

    boolean add(ByteBuf buf) {
        if (this.count == Native.IOV_MAX) {
            return false;
        }
        int len = buf.readableBytes();
        if (len == 0) {
            return true;
        }
        long addr = buf.memoryAddress();
        int offset = buf.readerIndex();
        return this.add(addr, offset, len);
    }

    private boolean add(long addr, int offset, int len) {
        if (len == 0) {
            return true;
        }
        long baseOffset = this.memoryAddress(this.count++);
        long lengthOffset = baseOffset + (long)ADDRESS_SIZE;
        if (Native.SSIZE_MAX - (long)len < this.size) {
            return false;
        }
        this.size += (long)len;
        if (ADDRESS_SIZE == 8) {
            PlatformDependent.putLong(baseOffset, addr + (long)offset);
            PlatformDependent.putLong(lengthOffset, len);
        } else {
            assert (ADDRESS_SIZE == 4);
            PlatformDependent.putInt(baseOffset, (int)addr + offset);
            PlatformDependent.putInt(lengthOffset, len);
        }
        return true;
    }

    boolean add(CompositeByteBuf buf) {
        ByteBuffer[] buffers = buf.nioBuffers();
        if (this.count + buffers.length >= Native.IOV_MAX) {
            return false;
        }
        for (ByteBuffer nioBuffer : buffers) {
            long addr;
            int offset = nioBuffer.position();
            int len = nioBuffer.limit() - nioBuffer.position();
            if (len == 0 || this.add(addr = PlatformDependent.directBufferAddress(nioBuffer), offset, len)) continue;
            return false;
        }
        return true;
    }

    long processWritten(int index, long written) {
        long baseOffset = this.memoryAddress(index);
        long lengthOffset = baseOffset + (long)ADDRESS_SIZE;
        if (ADDRESS_SIZE == 8) {
            long len = PlatformDependent.getLong(lengthOffset);
            if (len > written) {
                long offset = PlatformDependent.getLong(baseOffset);
                PlatformDependent.putLong(baseOffset, offset + written);
                PlatformDependent.putLong(lengthOffset, len - written);
                return -1L;
            }
            return len;
        }
        assert (ADDRESS_SIZE == 4);
        long len = PlatformDependent.getInt(lengthOffset);
        if (len > written) {
            int offset = PlatformDependent.getInt(baseOffset);
            PlatformDependent.putInt(baseOffset, (int)((long)offset + written));
            PlatformDependent.putInt(lengthOffset, (int)(len - written));
            return -1L;
        }
        return len;
    }

    int count() {
        return this.count;
    }

    long size() {
        return this.size;
    }

    long memoryAddress(int offset) {
        return this.memoryAddress + (long)(IOV_SIZE * offset);
    }

    void release() {
        PlatformDependent.freeMemory(this.memoryAddress);
    }

    @Override
    public boolean processMessage(Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            if (msg instanceof CompositeByteBuf) {
                return this.add((CompositeByteBuf)msg);
            }
            return this.add((ByteBuf)msg);
        }
        return false;
    }
}

