/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PoolChunk;
import io.netty.buffer.PoolThreadCache;
import io.netty.buffer.PooledByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.UnsafeByteBufUtil;
import io.netty.buffer.UnsafeDirectSwappedByteBuf;
import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

final class PooledUnsafeDirectByteBuf
extends PooledByteBuf<ByteBuffer> {
    private static final Recycler<PooledUnsafeDirectByteBuf> RECYCLER = new Recycler<PooledUnsafeDirectByteBuf>(){

        @Override
        protected PooledUnsafeDirectByteBuf newObject(Recycler.Handle<PooledUnsafeDirectByteBuf> handle) {
            return new PooledUnsafeDirectByteBuf(handle, 0, null);
        }

        @Override
        protected Object newObject(Recycler.Handle handle) {
            return this.newObject(handle);
        }
    };
    private long memoryAddress;

    static PooledUnsafeDirectByteBuf newInstance(int n) {
        PooledUnsafeDirectByteBuf pooledUnsafeDirectByteBuf = RECYCLER.get();
        pooledUnsafeDirectByteBuf.reuse(n);
        return pooledUnsafeDirectByteBuf;
    }

    private PooledUnsafeDirectByteBuf(Recycler.Handle<PooledUnsafeDirectByteBuf> handle, int n) {
        super(handle, n);
    }

    @Override
    void init(PoolChunk<ByteBuffer> poolChunk, long l, int n, int n2, int n3, PoolThreadCache poolThreadCache) {
        super.init(poolChunk, l, n, n2, n3, poolThreadCache);
        this.initMemoryAddress();
    }

    @Override
    void initUnpooled(PoolChunk<ByteBuffer> poolChunk, int n) {
        super.initUnpooled(poolChunk, n);
        this.initMemoryAddress();
    }

    private void initMemoryAddress() {
        this.memoryAddress = PlatformDependent.directBufferAddress((ByteBuffer)this.memory) + (long)this.offset;
    }

    @Override
    protected ByteBuffer newInternalNioBuffer(ByteBuffer byteBuffer) {
        return byteBuffer.duplicate();
    }

    @Override
    public boolean isDirect() {
        return false;
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
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        UnsafeByteBufUtil.getBytes(this, this.addr(n), n, outputStream, n2);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }

    private int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2, boolean bl) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : ((ByteBuffer)this.memory).duplicate();
        n = this.idx(n);
        byteBuffer.clear().position(n).limit(n + n2);
        return gatheringByteChannel.write(byteBuffer);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.getBytes(n, fileChannel, l, n2, false);
    }

    private int getBytes(int n, FileChannel fileChannel, long l, int n2, boolean bl) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : ((ByteBuffer)this.memory).duplicate();
        n = this.idx(n);
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
    protected void _setByte(int n, int n2) {
        UnsafeByteBufUtil.setByte(this.addr(n), (byte)n2);
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
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return UnsafeByteBufUtil.setBytes(this, this.addr(n), n, inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.checkIndex(n, n2);
        ByteBuffer byteBuffer = this.internalNioBuffer();
        n = this.idx(n);
        byteBuffer.clear().position(n).limit(n + n2);
        try {
            return scatteringByteChannel.read(byteBuffer);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.checkIndex(n, n2);
        ByteBuffer byteBuffer = this.internalNioBuffer();
        n = this.idx(n);
        byteBuffer.clear().position(n).limit(n + n2);
        try {
            return fileChannel.read(byteBuffer, l);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return UnsafeByteBufUtil.copy(this, this.addr(n), n, n2);
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
        this.checkIndex(n, n2);
        n = this.idx(n);
        return ((ByteBuffer)((ByteBuffer)this.memory).duplicate().position(n).limit(n + n2)).slice();
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        n = this.idx(n);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
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

    private long addr(int n) {
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

    @Override
    protected ByteBuffer newInternalNioBuffer(Object object) {
        return this.newInternalNioBuffer((ByteBuffer)object);
    }

    PooledUnsafeDirectByteBuf(Recycler.Handle handle, int n, 1 var3_3) {
        this(handle, n);
    }
}

