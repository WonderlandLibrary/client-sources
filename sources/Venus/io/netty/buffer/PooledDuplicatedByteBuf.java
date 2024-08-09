/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractPooledDerivedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledSlicedByteBuf;
import io.netty.util.ByteProcessor;
import io.netty.util.Recycler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

final class PooledDuplicatedByteBuf
extends AbstractPooledDerivedByteBuf {
    private static final Recycler<PooledDuplicatedByteBuf> RECYCLER = new Recycler<PooledDuplicatedByteBuf>(){

        @Override
        protected PooledDuplicatedByteBuf newObject(Recycler.Handle<PooledDuplicatedByteBuf> handle) {
            return new PooledDuplicatedByteBuf(handle, null);
        }

        @Override
        protected Object newObject(Recycler.Handle handle) {
            return this.newObject(handle);
        }
    };

    static PooledDuplicatedByteBuf newInstance(AbstractByteBuf abstractByteBuf, ByteBuf byteBuf, int n, int n2) {
        PooledDuplicatedByteBuf pooledDuplicatedByteBuf = RECYCLER.get();
        pooledDuplicatedByteBuf.init(abstractByteBuf, byteBuf, n, n2, abstractByteBuf.maxCapacity());
        pooledDuplicatedByteBuf.markReaderIndex();
        pooledDuplicatedByteBuf.markWriterIndex();
        return pooledDuplicatedByteBuf;
    }

    private PooledDuplicatedByteBuf(Recycler.Handle<PooledDuplicatedByteBuf> handle) {
        super(handle);
    }

    @Override
    public int capacity() {
        return this.unwrap().capacity();
    }

    @Override
    public ByteBuf capacity(int n) {
        this.unwrap().capacity(n);
        return this;
    }

    @Override
    public int arrayOffset() {
        return this.unwrap().arrayOffset();
    }

    @Override
    public long memoryAddress() {
        return this.unwrap().memoryAddress();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return this.unwrap().nioBuffer(n, n2);
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return this.unwrap().nioBuffers(n, n2);
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return this.unwrap().copy(n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return PooledSlicedByteBuf.newInstance(this.unwrap(), this, n, n2);
    }

    @Override
    public ByteBuf duplicate() {
        return this.duplicate0().setIndex(this.readerIndex(), this.writerIndex());
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.readerIndex(), this.writerIndex());
    }

    @Override
    public byte getByte(int n) {
        return this.unwrap().getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        return this.unwrap()._getByte(n);
    }

    @Override
    public short getShort(int n) {
        return this.unwrap().getShort(n);
    }

    @Override
    protected short _getShort(int n) {
        return this.unwrap()._getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        return this.unwrap().getShortLE(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return this.unwrap()._getShortLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        return this.unwrap().getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return this.unwrap()._getUnsignedMedium(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        return this.unwrap().getUnsignedMediumLE(n);
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.unwrap()._getUnsignedMediumLE(n);
    }

    @Override
    public int getInt(int n) {
        return this.unwrap().getInt(n);
    }

    @Override
    protected int _getInt(int n) {
        return this.unwrap()._getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        return this.unwrap().getIntLE(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return this.unwrap()._getIntLE(n);
    }

    @Override
    public long getLong(int n) {
        return this.unwrap().getLong(n);
    }

    @Override
    protected long _getLong(int n) {
        return this.unwrap()._getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        return this.unwrap().getLongLE(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return this.unwrap()._getLongLE(n);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.unwrap().getBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.unwrap().getBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.unwrap().getBytes(n, byteBuffer);
        return this;
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.unwrap().setByte(n, n2);
        return this;
    }

    @Override
    protected void _setByte(int n, int n2) {
        this.unwrap()._setByte(n, n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.unwrap().setShort(n, n2);
        return this;
    }

    @Override
    protected void _setShort(int n, int n2) {
        this.unwrap()._setShort(n, n2);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        this.unwrap().setShortLE(n, n2);
        return this;
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        this.unwrap()._setShortLE(n, n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.unwrap().setMedium(n, n2);
        return this;
    }

    @Override
    protected void _setMedium(int n, int n2) {
        this.unwrap()._setMedium(n, n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        this.unwrap().setMediumLE(n, n2);
        return this;
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        this.unwrap()._setMediumLE(n, n2);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.unwrap().setInt(n, n2);
        return this;
    }

    @Override
    protected void _setInt(int n, int n2) {
        this.unwrap()._setInt(n, n2);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        this.unwrap().setIntLE(n, n2);
        return this;
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        this.unwrap()._setIntLE(n, n2);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.unwrap().setLong(n, l);
        return this;
    }

    @Override
    protected void _setLong(int n, long l) {
        this.unwrap()._setLong(n, l);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        this.unwrap().setLongLE(n, l);
        return this;
    }

    @Override
    protected void _setLongLE(int n, long l) {
        this.unwrap().setLongLE(n, l);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.unwrap().setBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.unwrap().setBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        this.unwrap().setBytes(n, byteBuffer);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.unwrap().getBytes(n, outputStream, n2);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.unwrap().getBytes(n, gatheringByteChannel, n2);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.unwrap().getBytes(n, fileChannel, l, n2);
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return this.unwrap().setBytes(n, inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        return this.unwrap().setBytes(n, scatteringByteChannel, n2);
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.unwrap().setBytes(n, fileChannel, l, n2);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return this.unwrap().forEachByte(n, n2, byteProcessor);
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return this.unwrap().forEachByteDesc(n, n2, byteProcessor);
    }

    PooledDuplicatedByteBuf(Recycler.Handle handle, 1 var2_2) {
        this(handle);
    }
}

