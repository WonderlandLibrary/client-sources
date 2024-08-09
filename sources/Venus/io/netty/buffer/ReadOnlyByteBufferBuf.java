/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

class ReadOnlyByteBufferBuf
extends AbstractReferenceCountedByteBuf {
    protected final ByteBuffer buffer;
    private final ByteBufAllocator allocator;
    private ByteBuffer tmpNioBuf;

    ReadOnlyByteBufferBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer) {
        super(byteBuffer.remaining());
        if (!byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("must be a readonly buffer: " + StringUtil.simpleClassName(byteBuffer));
        }
        this.allocator = byteBufAllocator;
        this.buffer = byteBuffer.slice().order(ByteOrder.BIG_ENDIAN);
        this.writerIndex(this.buffer.limit());
    }

    @Override
    protected void deallocate() {
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public boolean isWritable(int n) {
        return true;
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        return 0;
    }

    @Override
    public byte getByte(int n) {
        this.ensureAccessible();
        return this._getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        return this.buffer.get(n);
    }

    @Override
    public short getShort(int n) {
        this.ensureAccessible();
        return this._getShort(n);
    }

    @Override
    protected short _getShort(int n) {
        return this.buffer.getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        this.ensureAccessible();
        return this._getShortLE(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return ByteBufUtil.swapShort(this.buffer.getShort(n));
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.ensureAccessible();
        return this._getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return (this.getByte(n) & 0xFF) << 16 | (this.getByte(n + 1) & 0xFF) << 8 | this.getByte(n + 2) & 0xFF;
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        this.ensureAccessible();
        return this._getUnsignedMediumLE(n);
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.getByte(n) & 0xFF | (this.getByte(n + 1) & 0xFF) << 8 | (this.getByte(n + 2) & 0xFF) << 16;
    }

    @Override
    public int getInt(int n) {
        this.ensureAccessible();
        return this._getInt(n);
    }

    @Override
    protected int _getInt(int n) {
        return this.buffer.getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        this.ensureAccessible();
        return this._getIntLE(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return ByteBufUtil.swapInt(this.buffer.getInt(n));
    }

    @Override
    public long getLong(int n) {
        this.ensureAccessible();
        return this._getLong(n);
    }

    @Override
    protected long _getLong(int n) {
        return this.buffer.getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        this.ensureAccessible();
        return this._getLongLE(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return ByteBufUtil.swapLong(this.buffer.getLong(n));
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        } else if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(n2, n3)) {
                int n4 = byteBuffer.remaining();
                this.getBytes(n, byteBuffer);
                n += n4;
            }
        } else {
            byteBuf.setBytes(n2, this, n, n3);
        }
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byArray.length);
        if (n2 < 0 || n2 > byArray.length - n3) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n2, n3, byArray.length));
        }
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n3);
        byteBuffer.get(byArray, n2, n3);
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
    public ByteBuf setByte(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setByte(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setShort(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setMedium(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setInt(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setLong(int n, long l) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        throw new ReadOnlyBufferException();
    }

    @Override
    protected void _setLongLE(int n, long l) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int capacity() {
        return this.maxCapacity();
    }

    @Override
    public ByteBuf capacity(int n) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.allocator;
    }

    @Override
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public ByteBuf unwrap() {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return this.buffer.isReadOnly();
    }

    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return this;
        }
        if (this.buffer.hasArray()) {
            outputStream.write(this.buffer.array(), n + this.buffer.arrayOffset(), n2);
        } else {
            byte[] byArray = new byte[n2];
            ByteBuffer byteBuffer = this.internalNioBuffer();
            byteBuffer.clear().position(n);
            byteBuffer.get(byArray);
            outputStream.write(byArray);
        }
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n2);
        return gatheringByteChannel.write(byteBuffer);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n2);
        return fileChannel.write(byteBuffer, l);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        throw new ReadOnlyBufferException();
    }

    protected final ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer == null) {
            this.tmpNioBuf = byteBuffer = this.buffer.duplicate();
        }
        return byteBuffer;
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        ByteBuffer byteBuffer;
        this.ensureAccessible();
        try {
            byteBuffer = (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (n + n2));
        }
        ByteBuf byteBuf = byteBuffer.isDirect() ? this.alloc().directBuffer(n2) : this.alloc().heapBuffer(n2);
        byteBuf.writeBytes(byteBuffer);
        return byteBuf;
    }

    @Override
    public int nioBufferCount() {
        return 0;
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return new ByteBuffer[]{this.nioBuffer(n, n2)};
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return (ByteBuffer)this.buffer.duplicate().position(n).limit(n + n2);
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        this.ensureAccessible();
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }

    @Override
    public boolean hasArray() {
        return this.buffer.hasArray();
    }

    @Override
    public byte[] array() {
        return this.buffer.array();
    }

    @Override
    public int arrayOffset() {
        return this.buffer.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return true;
    }

    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }
}

