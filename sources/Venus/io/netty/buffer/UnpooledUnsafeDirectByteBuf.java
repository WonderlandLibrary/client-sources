/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.UnsafeByteBufUtil;
import io.netty.buffer.UnsafeDirectSwappedByteBuf;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class UnpooledUnsafeDirectByteBuf
extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    private ByteBuffer tmpNioBuf;
    private int capacity;
    private boolean doNotFree;
    ByteBuffer buffer;
    long memoryAddress;

    public UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, int n, int n2) {
        super(n2);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("maxCapacity: " + n2);
        }
        if (n > n2) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", n, n2));
        }
        this.alloc = byteBufAllocator;
        this.setByteBuffer(this.allocateDirect(n), true);
    }

    protected UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int n) {
        this(byteBufAllocator, byteBuffer.slice(), n, false);
    }

    UnpooledUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int n, boolean bl) {
        super(n);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        if (byteBuffer == null) {
            throw new NullPointerException("initialBuffer");
        }
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        }
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
        int n2 = byteBuffer.remaining();
        if (n2 > n) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", n2, n));
        }
        this.alloc = byteBufAllocator;
        this.doNotFree = !bl;
        this.setByteBuffer(byteBuffer.order(ByteOrder.BIG_ENDIAN), true);
        this.writerIndex(n2);
    }

    protected ByteBuffer allocateDirect(int n) {
        return ByteBuffer.allocateDirect(n);
    }

    protected void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectBuffer(byteBuffer);
    }

    final void setByteBuffer(ByteBuffer byteBuffer, boolean bl) {
        ByteBuffer byteBuffer2;
        if (bl && (byteBuffer2 = this.buffer) != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            } else {
                this.freeDirect(byteBuffer2);
            }
        }
        this.buffer = byteBuffer;
        this.memoryAddress = PlatformDependent.directBufferAddress(byteBuffer);
        this.tmpNioBuf = null;
        this.capacity = byteBuffer.remaining();
    }

    @Override
    public boolean isDirect() {
        return false;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public ByteBuf capacity(int n) {
        this.checkNewCapacity(n);
        int n2 = this.readerIndex();
        int n3 = this.writerIndex();
        int n4 = this.capacity;
        if (n > n4) {
            ByteBuffer byteBuffer = this.buffer;
            ByteBuffer byteBuffer2 = this.allocateDirect(n);
            byteBuffer.position(0).limit(byteBuffer.capacity());
            byteBuffer2.position(0).limit(byteBuffer.capacity());
            byteBuffer2.put(byteBuffer);
            byteBuffer2.clear();
            this.setByteBuffer(byteBuffer2, false);
        } else if (n < n4) {
            ByteBuffer byteBuffer = this.buffer;
            ByteBuffer byteBuffer3 = this.allocateDirect(n);
            if (n2 < n) {
                if (n3 > n) {
                    n3 = n;
                    this.writerIndex(n3);
                }
                byteBuffer.position(n2).limit(n3);
                byteBuffer3.position(n2).limit(n3);
                byteBuffer3.put(byteBuffer);
                byteBuffer3.clear();
            } else {
                this.setIndex(n, n);
            }
            this.setByteBuffer(byteBuffer3, false);
        }
        return this;
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    @Override
    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public boolean hasArray() {
        return true;
    }

    @Override
    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
    }

    @Override
    public boolean hasMemoryAddress() {
        return false;
    }

    @Override
    public long memoryAddress() {
        this.ensureAccessible();
        return this.memoryAddress;
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
    protected short _getShortLE(int n) {
        return UnsafeByteBufUtil.getShortLE(this.addr(n));
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return UnsafeByteBufUtil.getUnsignedMedium(this.addr(n));
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return UnsafeByteBufUtil.getUnsignedMediumLE(this.addr(n));
    }

    @Override
    protected int _getInt(int n) {
        return UnsafeByteBufUtil.getInt(this.addr(n));
    }

    @Override
    protected int _getIntLE(int n) {
        return UnsafeByteBufUtil.getIntLE(this.addr(n));
    }

    @Override
    protected long _getLong(int n) {
        return UnsafeByteBufUtil.getLong(this.addr(n));
    }

    @Override
    protected long _getLongLE(int n) {
        return UnsafeByteBufUtil.getLongLE(this.addr(n));
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf)this, this.addr(n), n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        UnsafeByteBufUtil.getBytes((AbstractByteBuf)this, this.addr(n), n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        UnsafeByteBufUtil.getBytes(this, this.addr(n), n, byteBuffer);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int n = byteBuffer.remaining();
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, byteBuffer);
        this.readerIndex += n;
        return this;
    }

    @Override
    protected void _setByte(int n, int n2) {
        UnsafeByteBufUtil.setByte(this.addr(n), n2);
    }

    @Override
    protected void _setShort(int n, int n2) {
        UnsafeByteBufUtil.setShort(this.addr(n), n2);
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        UnsafeByteBufUtil.setShortLE(this.addr(n), n2);
    }

    @Override
    protected void _setMedium(int n, int n2) {
        UnsafeByteBufUtil.setMedium(this.addr(n), n2);
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        UnsafeByteBufUtil.setMediumLE(this.addr(n), n2);
    }

    @Override
    protected void _setInt(int n, int n2) {
        UnsafeByteBufUtil.setInt(this.addr(n), n2);
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        UnsafeByteBufUtil.setIntLE(this.addr(n), n2);
    }

    @Override
    protected void _setLong(int n, long l) {
        UnsafeByteBufUtil.setLong(this.addr(n), l);
    }

    @Override
    protected void _setLongLE(int n, long l) {
        UnsafeByteBufUtil.setLongLE(this.addr(n), l);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf)this, this.addr(n), n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        UnsafeByteBufUtil.setBytes((AbstractByteBuf)this, this.addr(n), n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        UnsafeByteBufUtil.setBytes(this, this.addr(n), n, byteBuffer);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        UnsafeByteBufUtil.getBytes(this, this.addr(n), n, outputStream, n2);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }

    private int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : this.buffer.duplicate();
        byteBuffer.clear().position(n).limit(n + n2);
        return gatheringByteChannel.write(byteBuffer);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.getBytes(n, fileChannel, l, n2, false);
    }

    private int getBytes(int n, FileChannel fileChannel, long l, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : this.buffer.duplicate();
        byteBuffer.clear().position(n).limit(n + n2);
        return fileChannel.write(byteBuffer, l);
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        this.checkReadableBytes(n);
        int n2 = this.getBytes(this.readerIndex, gatheringByteChannel, n, true);
        this.readerIndex += n2;
        return n2;
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        this.checkReadableBytes(n);
        int n2 = this.getBytes(this.readerIndex, fileChannel, l, n, true);
        this.readerIndex += n2;
        return n2;
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return UnsafeByteBufUtil.setBytes(this, this.addr(n), n, inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n2);
        try {
            return scatteringByteChannel.read(byteBuffer);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n2);
        try {
            return fileChannel.read(byteBuffer, l);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
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
    public ByteBuf copy(int n, int n2) {
        return UnsafeByteBufUtil.copy(this, this.addr(n), n, n2);
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer == null) {
            this.tmpNioBuf = byteBuffer = this.buffer.duplicate();
        }
        return byteBuffer;
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return ((ByteBuffer)this.buffer.duplicate().position(n).limit(n + n2)).slice();
    }

    @Override
    protected void deallocate() {
        ByteBuffer byteBuffer = this.buffer;
        if (byteBuffer == null) {
            return;
        }
        this.buffer = null;
        if (!this.doNotFree) {
            this.freeDirect(byteBuffer);
        }
    }

    @Override
    public ByteBuf unwrap() {
        return null;
    }

    long addr(int n) {
        return this.memoryAddress + (long)n;
    }

    @Override
    protected SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeDirectSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        this.checkIndex(n, n2);
        UnsafeByteBufUtil.setZero(this.addr(n), n2);
        return this;
    }

    @Override
    public ByteBuf writeZero(int n) {
        this.ensureWritable(n);
        int n2 = this.writerIndex;
        UnsafeByteBufUtil.setZero(this.addr(n2), n);
        this.writerIndex = n2 + n;
        return this;
    }
}

