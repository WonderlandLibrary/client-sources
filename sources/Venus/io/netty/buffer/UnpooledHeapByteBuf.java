/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.HeapByteBufUtil;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
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

public class UnpooledHeapByteBuf
extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    byte[] array;
    private ByteBuffer tmpNioBuf;

    public UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, int n, int n2) {
        super(n2);
        ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        if (n > n2) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", n, n2));
        }
        this.alloc = byteBufAllocator;
        this.setArray(this.allocateArray(n));
        this.setIndex(0, 0);
    }

    protected UnpooledHeapByteBuf(ByteBufAllocator byteBufAllocator, byte[] byArray, int n) {
        super(n);
        ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        ObjectUtil.checkNotNull(byArray, "initialArray");
        if (byArray.length > n) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", byArray.length, n));
        }
        this.alloc = byteBufAllocator;
        this.setArray(byArray);
        this.setIndex(0, byArray.length);
    }

    byte[] allocateArray(int n) {
        return new byte[n];
    }

    void freeArray(byte[] byArray) {
    }

    private void setArray(byte[] byArray) {
        this.array = byArray;
        this.tmpNioBuf = null;
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
    public boolean isDirect() {
        return true;
    }

    @Override
    public int capacity() {
        return this.array.length;
    }

    @Override
    public ByteBuf capacity(int n) {
        this.checkNewCapacity(n);
        int n2 = this.array.length;
        byte[] byArray = this.array;
        if (n > n2) {
            byte[] byArray2 = this.allocateArray(n);
            System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
            this.setArray(byArray2);
            this.freeArray(byArray);
        } else if (n < n2) {
            byte[] byArray3 = this.allocateArray(n);
            int n3 = this.readerIndex();
            if (n3 < n) {
                int n4 = this.writerIndex();
                if (n4 > n) {
                    n4 = n;
                    this.writerIndex(n4);
                }
                System.arraycopy(byArray, n3, byArray3, n3, n4 - n3);
            } else {
                this.setIndex(n, n);
            }
            this.setArray(byArray3);
            this.freeArray(byArray);
        }
        return this;
    }

    @Override
    public boolean hasArray() {
        return false;
    }

    @Override
    public byte[] array() {
        this.ensureAccessible();
        return this.array;
    }

    @Override
    public int arrayOffset() {
        return 1;
    }

    @Override
    public boolean hasMemoryAddress() {
        return true;
    }

    @Override
    public long memoryAddress() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(this.array, n, byteBuf.memoryAddress() + (long)n2, (long)n3);
        } else if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        } else {
            byteBuf.setBytes(n2, this.array, n, n3);
        }
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byArray.length);
        System.arraycopy(this.array, n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.checkIndex(n, byteBuffer.remaining());
        byteBuffer.put(this.array, n, byteBuffer.remaining());
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.ensureAccessible();
        outputStream.write(this.array, n, n2);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        this.ensureAccessible();
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.ensureAccessible();
        return this.getBytes(n, fileChannel, l, n2, false);
    }

    private int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : ByteBuffer.wrap(this.array);
        return gatheringByteChannel.write((ByteBuffer)byteBuffer.clear().position(n).limit(n + n2));
    }

    private int getBytes(int n, FileChannel fileChannel, long l, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : ByteBuffer.wrap(this.array);
        return fileChannel.write((ByteBuffer)byteBuffer.clear().position(n).limit(n + n2), l);
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
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(byteBuf.memoryAddress() + (long)n2, this.array, n, (long)n3);
        } else if (byteBuf.hasArray()) {
            this.setBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        } else {
            byteBuf.getBytes(n2, this.array, n, n3);
        }
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byArray.length);
        System.arraycopy(byArray, n2, this.array, n, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        this.ensureAccessible();
        byteBuffer.get(this.array, n, byteBuffer.remaining());
        return this;
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        this.ensureAccessible();
        return inputStream.read(this.array, n, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.ensureAccessible();
        try {
            return scatteringByteChannel.read((ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2));
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.ensureAccessible();
        try {
            return fileChannel.read((ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2), l);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public int nioBufferCount() {
        return 0;
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        this.ensureAccessible();
        return ByteBuffer.wrap(this.array, n, n2).slice();
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return new ByteBuffer[]{this.nioBuffer(n, n2)};
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }

    @Override
    public byte getByte(int n) {
        this.ensureAccessible();
        return this._getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        return HeapByteBufUtil.getByte(this.array, n);
    }

    @Override
    public short getShort(int n) {
        this.ensureAccessible();
        return this._getShort(n);
    }

    @Override
    protected short _getShort(int n) {
        return HeapByteBufUtil.getShort(this.array, n);
    }

    @Override
    public short getShortLE(int n) {
        this.ensureAccessible();
        return this._getShortLE(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return HeapByteBufUtil.getShortLE(this.array, n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.ensureAccessible();
        return this._getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return HeapByteBufUtil.getUnsignedMedium(this.array, n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        this.ensureAccessible();
        return this._getUnsignedMediumLE(n);
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return HeapByteBufUtil.getUnsignedMediumLE(this.array, n);
    }

    @Override
    public int getInt(int n) {
        this.ensureAccessible();
        return this._getInt(n);
    }

    @Override
    protected int _getInt(int n) {
        return HeapByteBufUtil.getInt(this.array, n);
    }

    @Override
    public int getIntLE(int n) {
        this.ensureAccessible();
        return this._getIntLE(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return HeapByteBufUtil.getIntLE(this.array, n);
    }

    @Override
    public long getLong(int n) {
        this.ensureAccessible();
        return this._getLong(n);
    }

    @Override
    protected long _getLong(int n) {
        return HeapByteBufUtil.getLong(this.array, n);
    }

    @Override
    public long getLongLE(int n) {
        this.ensureAccessible();
        return this._getLongLE(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return HeapByteBufUtil.getLongLE(this.array, n);
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.ensureAccessible();
        this._setByte(n, n2);
        return this;
    }

    @Override
    protected void _setByte(int n, int n2) {
        HeapByteBufUtil.setByte(this.array, n, n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.ensureAccessible();
        this._setShort(n, n2);
        return this;
    }

    @Override
    protected void _setShort(int n, int n2) {
        HeapByteBufUtil.setShort(this.array, n, n2);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        this.ensureAccessible();
        this._setShortLE(n, n2);
        return this;
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        HeapByteBufUtil.setShortLE(this.array, n, n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.ensureAccessible();
        this._setMedium(n, n2);
        return this;
    }

    @Override
    protected void _setMedium(int n, int n2) {
        HeapByteBufUtil.setMedium(this.array, n, n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        this.ensureAccessible();
        this._setMediumLE(n, n2);
        return this;
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        HeapByteBufUtil.setMediumLE(this.array, n, n2);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.ensureAccessible();
        this._setInt(n, n2);
        return this;
    }

    @Override
    protected void _setInt(int n, int n2) {
        HeapByteBufUtil.setInt(this.array, n, n2);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        this.ensureAccessible();
        this._setIntLE(n, n2);
        return this;
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        HeapByteBufUtil.setIntLE(this.array, n, n2);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.ensureAccessible();
        this._setLong(n, l);
        return this;
    }

    @Override
    protected void _setLong(int n, long l) {
        HeapByteBufUtil.setLong(this.array, n, l);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        this.ensureAccessible();
        this._setLongLE(n, l);
        return this;
    }

    @Override
    protected void _setLongLE(int n, long l) {
        HeapByteBufUtil.setLongLE(this.array, n, l);
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        this.checkIndex(n, n2);
        byte[] byArray = new byte[n2];
        System.arraycopy(this.array, n, byArray, 0, n2);
        return new UnpooledHeapByteBuf(this.alloc(), byArray, this.maxCapacity());
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer == null) {
            this.tmpNioBuf = byteBuffer = ByteBuffer.wrap(this.array);
        }
        return byteBuffer;
    }

    @Override
    protected void deallocate() {
        this.freeArray(this.array);
        this.array = EmptyArrays.EMPTY_BYTES;
    }

    @Override
    public ByteBuf unwrap() {
        return null;
    }
}

