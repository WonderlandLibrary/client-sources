/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.unix.Limits;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

public final class IovArray
implements ChannelOutboundBuffer.MessageProcessor {
    private static final int ADDRESS_SIZE;
    private static final int IOV_SIZE;
    private static final int CAPACITY;
    private final long memoryAddress;
    private int count;
    private long size;
    private long maxBytes = Limits.SSIZE_MAX;
    static final boolean $assertionsDisabled;

    public IovArray() {
        this.memoryAddress = PlatformDependent.allocateMemory(CAPACITY);
    }

    public void clear() {
        this.count = 0;
        this.size = 0L;
    }

    public boolean add(ByteBuf byteBuf) {
        ByteBuffer[] byteBufferArray;
        if (this.count == Limits.IOV_MAX) {
            return true;
        }
        if (byteBuf.hasMemoryAddress() && byteBuf.nioBufferCount() == 1) {
            int n = byteBuf.readableBytes();
            return n == 0 || this.add(byteBuf.memoryAddress(), byteBuf.readerIndex(), n);
        }
        for (ByteBuffer byteBuffer : byteBufferArray = byteBuf.nioBuffers()) {
            int n = byteBuffer.remaining();
            if (n == 0 || this.add(PlatformDependent.directBufferAddress(byteBuffer), byteBuffer.position(), n) && this.count != Limits.IOV_MAX) continue;
            return true;
        }
        return false;
    }

    private boolean add(long l, int n, int n2) {
        long l2 = this.memoryAddress(this.count);
        long l3 = l2 + (long)ADDRESS_SIZE;
        if (this.maxBytes - (long)n2 < this.size && this.count > 0) {
            return true;
        }
        this.size += (long)n2;
        ++this.count;
        if (ADDRESS_SIZE == 8) {
            PlatformDependent.putLong(l2, l + (long)n);
            PlatformDependent.putLong(l3, n2);
        } else {
            if (!$assertionsDisabled && ADDRESS_SIZE != 4) {
                throw new AssertionError();
            }
            PlatformDependent.putInt(l2, (int)l + n);
            PlatformDependent.putInt(l3, n2);
        }
        return false;
    }

    public int count() {
        return this.count;
    }

    public long size() {
        return this.size;
    }

    public void maxBytes(long l) {
        this.maxBytes = Math.min(Limits.SSIZE_MAX, ObjectUtil.checkPositive(l, "maxBytes"));
    }

    public long maxBytes() {
        return this.maxBytes;
    }

    public long memoryAddress(int n) {
        return this.memoryAddress + (long)(IOV_SIZE * n);
    }

    public void release() {
        PlatformDependent.freeMemory(this.memoryAddress);
    }

    @Override
    public boolean processMessage(Object object) throws Exception {
        return object instanceof ByteBuf && this.add((ByteBuf)object);
    }

    static {
        $assertionsDisabled = !IovArray.class.desiredAssertionStatus();
        ADDRESS_SIZE = PlatformDependent.addressSize();
        IOV_SIZE = 2 * ADDRESS_SIZE;
        CAPACITY = Limits.IOV_MAX * IOV_SIZE;
    }
}

