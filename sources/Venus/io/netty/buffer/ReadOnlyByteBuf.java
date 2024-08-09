/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractDerivedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.DuplicatedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

@Deprecated
public class ReadOnlyByteBuf
extends AbstractDerivedByteBuf {
    private final ByteBuf buffer;

    public ReadOnlyByteBuf(ByteBuf byteBuf) {
        super(byteBuf.maxCapacity());
        this.buffer = byteBuf instanceof ReadOnlyByteBuf || byteBuf instanceof DuplicatedByteBuf ? byteBuf.unwrap() : byteBuf;
        this.setIndex(byteBuf.readerIndex(), byteBuf.writerIndex());
    }

    @Override
    public boolean isReadOnly() {
        return false;
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
    public int ensureWritable(int n, boolean bl) {
        return 0;
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        throw new ReadOnlyBufferException();
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
    public boolean hasArray() {
        return true;
    }

    @Override
    public byte[] array() {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int arrayOffset() {
        throw new ReadOnlyBufferException();
    }

    @Override
    public boolean hasMemoryAddress() {
        return this.unwrap().hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return this.unwrap().memoryAddress();
    }

    @Override
    public ByteBuf discardReadBytes() {
        throw new ReadOnlyBufferException();
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
    public int setBytes(int n, InputStream inputStream, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) {
        throw new ReadOnlyBufferException();
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
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.unwrap().getBytes(n, outputStream, n2);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.unwrap().getBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.unwrap().getBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.unwrap().getBytes(n, byteBuffer);
        return this;
    }

    @Override
    public ByteBuf duplicate() {
        return new ReadOnlyByteBuf(this);
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return this.unwrap().copy(n, n2);
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return Unpooled.unmodifiableBuffer(this.unwrap().slice(n, n2));
    }

    @Override
    public byte getByte(int n) {
        return this.unwrap().getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        return this.unwrap().getByte(n);
    }

    @Override
    public short getShort(int n) {
        return this.unwrap().getShort(n);
    }

    @Override
    protected short _getShort(int n) {
        return this.unwrap().getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        return this.unwrap().getShortLE(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return this.unwrap().getShortLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        return this.unwrap().getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return this.unwrap().getUnsignedMedium(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        return this.unwrap().getUnsignedMediumLE(n);
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.unwrap().getUnsignedMediumLE(n);
    }

    @Override
    public int getInt(int n) {
        return this.unwrap().getInt(n);
    }

    @Override
    protected int _getInt(int n) {
        return this.unwrap().getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        return this.unwrap().getIntLE(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return this.unwrap().getIntLE(n);
    }

    @Override
    public long getLong(int n) {
        return this.unwrap().getLong(n);
    }

    @Override
    protected long _getLong(int n) {
        return this.unwrap().getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        return this.unwrap().getLongLE(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return this.unwrap().getLongLE(n);
    }

    @Override
    public int nioBufferCount() {
        return this.unwrap().nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return this.unwrap().nioBuffer(n, n2).asReadOnlyBuffer();
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return this.unwrap().nioBuffers(n, n2);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return this.unwrap().forEachByte(n, n2, byteProcessor);
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return this.unwrap().forEachByteDesc(n, n2, byteProcessor);
    }

    @Override
    public int capacity() {
        return this.unwrap().capacity();
    }

    @Override
    public ByteBuf capacity(int n) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBuf asReadOnly() {
        return this;
    }
}

