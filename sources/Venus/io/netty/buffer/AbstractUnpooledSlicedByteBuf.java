/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractDerivedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.DuplicatedByteBuf;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.MathUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

abstract class AbstractUnpooledSlicedByteBuf
extends AbstractDerivedByteBuf {
    private final ByteBuf buffer;
    private final int adjustment;

    AbstractUnpooledSlicedByteBuf(ByteBuf byteBuf, int n, int n2) {
        super(n2);
        AbstractUnpooledSlicedByteBuf.checkSliceOutOfBounds(n, n2, byteBuf);
        if (byteBuf instanceof AbstractUnpooledSlicedByteBuf) {
            this.buffer = ((AbstractUnpooledSlicedByteBuf)byteBuf).buffer;
            this.adjustment = ((AbstractUnpooledSlicedByteBuf)byteBuf).adjustment + n;
        } else if (byteBuf instanceof DuplicatedByteBuf) {
            this.buffer = byteBuf.unwrap();
            this.adjustment = n;
        } else {
            this.buffer = byteBuf;
            this.adjustment = n;
        }
        this.initLength(n2);
        this.writerIndex(n2);
    }

    void initLength(int n) {
    }

    int length() {
        return this.capacity();
    }

    @Override
    public ByteBuf unwrap() {
        return this.buffer;
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.unwrap().alloc();
    }

    @Override
    @Deprecated
    public ByteOrder order() {
        return this.unwrap().order();
    }

    @Override
    public boolean isDirect() {
        return this.unwrap().isDirect();
    }

    @Override
    public ByteBuf capacity(int n) {
        throw new UnsupportedOperationException("sliced buffer");
    }

    @Override
    public boolean hasArray() {
        return this.unwrap().hasArray();
    }

    @Override
    public byte[] array() {
        return this.unwrap().array();
    }

    @Override
    public int arrayOffset() {
        return this.idx(this.unwrap().arrayOffset());
    }

    @Override
    public boolean hasMemoryAddress() {
        return this.unwrap().hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return this.unwrap().memoryAddress() + (long)this.adjustment;
    }

    @Override
    public byte getByte(int n) {
        this.checkIndex0(n, 1);
        return this.unwrap().getByte(this.idx(n));
    }

    @Override
    protected byte _getByte(int n) {
        return this.unwrap().getByte(this.idx(n));
    }

    @Override
    public short getShort(int n) {
        this.checkIndex0(n, 2);
        return this.unwrap().getShort(this.idx(n));
    }

    @Override
    protected short _getShort(int n) {
        return this.unwrap().getShort(this.idx(n));
    }

    @Override
    public short getShortLE(int n) {
        this.checkIndex0(n, 2);
        return this.unwrap().getShortLE(this.idx(n));
    }

    @Override
    protected short _getShortLE(int n) {
        return this.unwrap().getShortLE(this.idx(n));
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.checkIndex0(n, 3);
        return this.unwrap().getUnsignedMedium(this.idx(n));
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return this.unwrap().getUnsignedMedium(this.idx(n));
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        this.checkIndex0(n, 3);
        return this.unwrap().getUnsignedMediumLE(this.idx(n));
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.unwrap().getUnsignedMediumLE(this.idx(n));
    }

    @Override
    public int getInt(int n) {
        this.checkIndex0(n, 4);
        return this.unwrap().getInt(this.idx(n));
    }

    @Override
    protected int _getInt(int n) {
        return this.unwrap().getInt(this.idx(n));
    }

    @Override
    public int getIntLE(int n) {
        this.checkIndex0(n, 4);
        return this.unwrap().getIntLE(this.idx(n));
    }

    @Override
    protected int _getIntLE(int n) {
        return this.unwrap().getIntLE(this.idx(n));
    }

    @Override
    public long getLong(int n) {
        this.checkIndex0(n, 8);
        return this.unwrap().getLong(this.idx(n));
    }

    @Override
    protected long _getLong(int n) {
        return this.unwrap().getLong(this.idx(n));
    }

    @Override
    public long getLongLE(int n) {
        this.checkIndex0(n, 8);
        return this.unwrap().getLongLE(this.idx(n));
    }

    @Override
    protected long _getLongLE(int n) {
        return this.unwrap().getLongLE(this.idx(n));
    }

    @Override
    public ByteBuf duplicate() {
        return this.unwrap().duplicate().setIndex(this.idx(this.readerIndex()), this.idx(this.writerIndex()));
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        this.checkIndex0(n, n2);
        return this.unwrap().copy(this.idx(n), n2);
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        this.checkIndex0(n, n2);
        return this.unwrap().slice(this.idx(n), n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkIndex0(n, n3);
        this.unwrap().getBytes(this.idx(n), byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkIndex0(n, n3);
        this.unwrap().getBytes(this.idx(n), byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.checkIndex0(n, byteBuffer.remaining());
        this.unwrap().getBytes(this.idx(n), byteBuffer);
        return this;
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.checkIndex0(n, 1);
        this.unwrap().setByte(this.idx(n), n2);
        return this;
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        this.checkIndex0(n, n2);
        return this.unwrap().getCharSequence(this.idx(n), n2, charset);
    }

    @Override
    protected void _setByte(int n, int n2) {
        this.unwrap().setByte(this.idx(n), n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.checkIndex0(n, 2);
        this.unwrap().setShort(this.idx(n), n2);
        return this;
    }

    @Override
    protected void _setShort(int n, int n2) {
        this.unwrap().setShort(this.idx(n), n2);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        this.checkIndex0(n, 2);
        this.unwrap().setShortLE(this.idx(n), n2);
        return this;
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        this.unwrap().setShortLE(this.idx(n), n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.checkIndex0(n, 3);
        this.unwrap().setMedium(this.idx(n), n2);
        return this;
    }

    @Override
    protected void _setMedium(int n, int n2) {
        this.unwrap().setMedium(this.idx(n), n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        this.checkIndex0(n, 3);
        this.unwrap().setMediumLE(this.idx(n), n2);
        return this;
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        this.unwrap().setMediumLE(this.idx(n), n2);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.checkIndex0(n, 4);
        this.unwrap().setInt(this.idx(n), n2);
        return this;
    }

    @Override
    protected void _setInt(int n, int n2) {
        this.unwrap().setInt(this.idx(n), n2);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        this.checkIndex0(n, 4);
        this.unwrap().setIntLE(this.idx(n), n2);
        return this;
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        this.unwrap().setIntLE(this.idx(n), n2);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.checkIndex0(n, 8);
        this.unwrap().setLong(this.idx(n), l);
        return this;
    }

    @Override
    protected void _setLong(int n, long l) {
        this.unwrap().setLong(this.idx(n), l);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        this.checkIndex0(n, 8);
        this.unwrap().setLongLE(this.idx(n), l);
        return this;
    }

    @Override
    protected void _setLongLE(int n, long l) {
        this.unwrap().setLongLE(this.idx(n), l);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkIndex0(n, n3);
        this.unwrap().setBytes(this.idx(n), byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkIndex0(n, n3);
        this.unwrap().setBytes(this.idx(n), byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        this.checkIndex0(n, byteBuffer.remaining());
        this.unwrap().setBytes(this.idx(n), byteBuffer);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.checkIndex0(n, n2);
        this.unwrap().getBytes(this.idx(n), outputStream, n2);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        this.checkIndex0(n, n2);
        return this.unwrap().getBytes(this.idx(n), gatheringByteChannel, n2);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.checkIndex0(n, n2);
        return this.unwrap().getBytes(this.idx(n), fileChannel, l, n2);
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        this.checkIndex0(n, n2);
        return this.unwrap().setBytes(this.idx(n), inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.checkIndex0(n, n2);
        return this.unwrap().setBytes(this.idx(n), scatteringByteChannel, n2);
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.checkIndex0(n, n2);
        return this.unwrap().setBytes(this.idx(n), fileChannel, l, n2);
    }

    @Override
    public int nioBufferCount() {
        return this.unwrap().nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        this.checkIndex0(n, n2);
        return this.unwrap().nioBuffer(this.idx(n), n2);
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        this.checkIndex0(n, n2);
        return this.unwrap().nioBuffers(this.idx(n), n2);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        this.checkIndex0(n, n2);
        int n3 = this.unwrap().forEachByte(this.idx(n), n2, byteProcessor);
        if (n3 >= this.adjustment) {
            return n3 - this.adjustment;
        }
        return 1;
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        this.checkIndex0(n, n2);
        int n3 = this.unwrap().forEachByteDesc(this.idx(n), n2, byteProcessor);
        if (n3 >= this.adjustment) {
            return n3 - this.adjustment;
        }
        return 1;
    }

    final int idx(int n) {
        return n + this.adjustment;
    }

    static void checkSliceOutOfBounds(int n, int n2, ByteBuf byteBuf) {
        if (MathUtil.isOutOfBounds(n, n2, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException(byteBuf + ".slice(" + n + ", " + n2 + ')');
        }
    }
}

