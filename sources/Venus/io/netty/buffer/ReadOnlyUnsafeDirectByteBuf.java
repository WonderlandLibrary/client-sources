/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ReadOnlyByteBufferBuf;
import io.netty.buffer.UnsafeByteBufUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;

final class ReadOnlyUnsafeDirectByteBuf
extends ReadOnlyByteBufferBuf {
    private final long memoryAddress;

    ReadOnlyUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer) {
        super(byteBufAllocator, byteBuffer);
        this.memoryAddress = PlatformDependent.directBufferAddress(this.buffer);
    }

    @Override
    protected byte _getByte(int n) {
        return UnsafeByteBufUtil.getByte(this.addr(n));
    }

    @Override
    protected short _getShort(int n) {
        return UnsafeByteBufUtil.getShort(this.addr(n));
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return UnsafeByteBufUtil.getUnsignedMedium(this.addr(n));
    }

    @Override
    protected int _getInt(int n) {
        return UnsafeByteBufUtil.getInt(this.addr(n));
    }

    @Override
    protected long _getLong(int n) {
        return UnsafeByteBufUtil.getLong(this.addr(n));
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkIndex(n, n3);
        if (byteBuf == null) {
            throw new NullPointerException("dst");
        }
        if (n2 < 0 || n2 > byteBuf.capacity() - n3) {
            throw new IndexOutOfBoundsException("dstIndex: " + n2);
        }
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.addr(n), byteBuf.memoryAddress() + (long)n2, n3);
        } else if (byteBuf.hasArray()) {
            PlatformDependent.copyMemory(this.addr(n), byteBuf.array(), byteBuf.arrayOffset() + n2, (long)n3);
        } else {
            byteBuf.setBytes(n2, this, n, n3);
        }
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkIndex(n, n3);
        if (byArray == null) {
            throw new NullPointerException("dst");
        }
        if (n2 < 0 || n2 > byArray.length - n3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n2, n3, byArray.length));
        }
        if (n3 != 0) {
            PlatformDependent.copyMemory(this.addr(n), byArray, n2, (long)n3);
        }
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.checkIndex(n);
        if (byteBuffer == null) {
            throw new NullPointerException("dst");
        }
        int n2 = Math.min(this.capacity() - n, byteBuffer.remaining());
        ByteBuffer byteBuffer2 = this.internalNioBuffer();
        byteBuffer2.clear().position(n).limit(n + n2);
        byteBuffer.put(byteBuffer2);
        return this;
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        this.checkIndex(n, n2);
        ByteBuf byteBuf = this.alloc().directBuffer(n2, this.maxCapacity());
        if (n2 != 0) {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(this.addr(n), byteBuf.memoryAddress(), n2);
                byteBuf.setIndex(0, n2);
            } else {
                byteBuf.writeBytes(this, n, n2);
            }
        }
        return byteBuf;
    }

    @Override
    public boolean hasMemoryAddress() {
        return false;
    }

    @Override
    public long memoryAddress() {
        return this.memoryAddress;
    }

    private long addr(int n) {
        return this.memoryAddress + (long)n;
    }
}

