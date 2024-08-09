/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.HeapByteBufUtil;
import io.netty.buffer.PooledByteBuf;
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

class PooledHeapByteBuf
extends PooledByteBuf<byte[]> {
    private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>(){

        @Override
        protected PooledHeapByteBuf newObject(Recycler.Handle<PooledHeapByteBuf> handle) {
            return new PooledHeapByteBuf((Recycler.Handle<? extends PooledHeapByteBuf>)handle, 0);
        }

        @Override
        protected Object newObject(Recycler.Handle handle) {
            return this.newObject(handle);
        }
    };

    static PooledHeapByteBuf newInstance(int n) {
        PooledHeapByteBuf pooledHeapByteBuf = RECYCLER.get();
        pooledHeapByteBuf.reuse(n);
        return pooledHeapByteBuf;
    }

    PooledHeapByteBuf(Recycler.Handle<? extends PooledHeapByteBuf> handle, int n) {
        super(handle, n);
    }

    @Override
    public final boolean isDirect() {
        return true;
    }

    @Override
    protected byte _getByte(int n) {
        return HeapByteBufUtil.getByte((byte[])this.memory, this.idx(n));
    }

    @Override
    protected short _getShort(int n) {
        return HeapByteBufUtil.getShort((byte[])this.memory, this.idx(n));
    }

    @Override
    protected short _getShortLE(int n) {
        return HeapByteBufUtil.getShortLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return HeapByteBufUtil.getUnsignedMedium((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return HeapByteBufUtil.getUnsignedMediumLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getInt(int n) {
        return HeapByteBufUtil.getInt((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getIntLE(int n) {
        return HeapByteBufUtil.getIntLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected long _getLong(int n) {
        return HeapByteBufUtil.getLong((byte[])this.memory, this.idx(n));
    }

    @Override
    protected long _getLongLE(int n) {
        return HeapByteBufUtil.getLongLE((byte[])this.memory, this.idx(n));
    }

    @Override
    public final ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory((byte[])this.memory, this.idx(n), byteBuf.memoryAddress() + (long)n2, (long)n3);
        } else if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        } else {
            byteBuf.setBytes(n2, (byte[])this.memory, this.idx(n), n3);
        }
        return this;
    }

    @Override
    public final ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byArray.length);
        System.arraycopy(this.memory, this.idx(n), byArray, n2, n3);
        return this;
    }

    @Override
    public final ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.checkIndex(n, byteBuffer.remaining());
        byteBuffer.put((byte[])this.memory, this.idx(n), byteBuffer.remaining());
        return this;
    }

    @Override
    public final ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.checkIndex(n, n2);
        outputStream.write((byte[])this.memory, this.idx(n), n2);
        return this;
    }

    @Override
    public final int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }

    private int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2, boolean bl) throws IOException {
        this.checkIndex(n, n2);
        n = this.idx(n);
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : ByteBuffer.wrap((byte[])this.memory);
        return gatheringByteChannel.write((ByteBuffer)byteBuffer.clear().position(n).limit(n + n2));
    }

    @Override
    public final int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.getBytes(n, fileChannel, l, n2, false);
    }

    private int getBytes(int n, FileChannel fileChannel, long l, int n2, boolean bl) throws IOException {
        this.checkIndex(n, n2);
        n = this.idx(n);
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : ByteBuffer.wrap((byte[])this.memory);
        return fileChannel.write((ByteBuffer)byteBuffer.clear().position(n).limit(n + n2), l);
    }

    @Override
    public final int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        this.checkReadableBytes(n);
        int n2 = this.getBytes(this.readerIndex, gatheringByteChannel, n, true);
        this.readerIndex += n2;
        return n2;
    }

    @Override
    public final int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        this.checkReadableBytes(n);
        int n2 = this.getBytes(this.readerIndex, fileChannel, l, n, true);
        this.readerIndex += n2;
        return n2;
    }

    @Override
    protected void _setByte(int n, int n2) {
        HeapByteBufUtil.setByte((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setShort(int n, int n2) {
        HeapByteBufUtil.setShort((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        HeapByteBufUtil.setShortLE((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setMedium(int n, int n2) {
        HeapByteBufUtil.setMedium((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        HeapByteBufUtil.setMediumLE((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setInt(int n, int n2) {
        HeapByteBufUtil.setInt((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        HeapByteBufUtil.setIntLE((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setLong(int n, long l) {
        HeapByteBufUtil.setLong((byte[])this.memory, this.idx(n), l);
    }

    @Override
    protected void _setLongLE(int n, long l) {
        HeapByteBufUtil.setLongLE((byte[])this.memory, this.idx(n), l);
    }

    @Override
    public final ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + (long)n2, (byte[])this.memory, this.idx(n), (long)n3);
        } else if (byteBuf.hasArray()) {
            this.setBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        } else {
            byteBuf.getBytes(n2, (byte[])this.memory, this.idx(n), n3);
        }
        return this;
    }

    @Override
    public final ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byArray.length);
        System.arraycopy(byArray, n2, this.memory, this.idx(n), n3);
        return this;
    }

    @Override
    public final ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        int n2 = byteBuffer.remaining();
        this.checkIndex(n, n2);
        byteBuffer.get((byte[])this.memory, this.idx(n), n2);
        return this;
    }

    @Override
    public final int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        this.checkIndex(n, n2);
        return inputStream.read((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    public final int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.checkIndex(n, n2);
        n = this.idx(n);
        try {
            return scatteringByteChannel.read((ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2));
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public final int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.checkIndex(n, n2);
        n = this.idx(n);
        try {
            return fileChannel.read((ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2), l);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public final ByteBuf copy(int n, int n2) {
        this.checkIndex(n, n2);
        ByteBuf byteBuf = this.alloc().heapBuffer(n2, this.maxCapacity());
        byteBuf.writeBytes((byte[])this.memory, this.idx(n), n2);
        return byteBuf;
    }

    @Override
    public final int nioBufferCount() {
        return 0;
    }

    @Override
    public final ByteBuffer[] nioBuffers(int n, int n2) {
        return new ByteBuffer[]{this.nioBuffer(n, n2)};
    }

    @Override
    public final ByteBuffer nioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        n = this.idx(n);
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])this.memory, n, n2);
        return byteBuffer.slice();
    }

    @Override
    public final ByteBuffer internalNioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        n = this.idx(n);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }

    @Override
    public final boolean hasArray() {
        return false;
    }

    @Override
    public final byte[] array() {
        this.ensureAccessible();
        return (byte[])this.memory;
    }

    @Override
    public final int arrayOffset() {
        return this.offset;
    }

    @Override
    public final boolean hasMemoryAddress() {
        return true;
    }

    @Override
    public final long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final ByteBuffer newInternalNioBuffer(byte[] byArray) {
        return ByteBuffer.wrap(byArray);
    }

    @Override
    protected ByteBuffer newInternalNioBuffer(Object object) {
        return this.newInternalNioBuffer((byte[])object);
    }
}

