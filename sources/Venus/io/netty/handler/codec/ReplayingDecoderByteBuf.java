/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCounted;
import io.netty.util.Signal;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ReplayingDecoderByteBuf
extends ByteBuf {
    private static final Signal REPLAY = ReplayingDecoder.REPLAY;
    private ByteBuf buffer;
    private boolean terminated;
    private SwappedByteBuf swapped;
    static final ReplayingDecoderByteBuf EMPTY_BUFFER = new ReplayingDecoderByteBuf(Unpooled.EMPTY_BUFFER);

    ReplayingDecoderByteBuf() {
    }

    ReplayingDecoderByteBuf(ByteBuf byteBuf) {
        this.setCumulation(byteBuf);
    }

    void setCumulation(ByteBuf byteBuf) {
        this.buffer = byteBuf;
    }

    void terminate() {
        this.terminated = true;
    }

    @Override
    public int capacity() {
        if (this.terminated) {
            return this.buffer.capacity();
        }
        return 0;
    }

    @Override
    public ByteBuf capacity(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int maxCapacity() {
        return this.capacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.buffer.alloc();
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer((ByteBuf)this);
    }

    @Override
    public boolean isDirect() {
        return this.buffer.isDirect();
    }

    @Override
    public boolean hasArray() {
        return true;
    }

    @Override
    public byte[] array() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException();
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
    public ByteBuf clear() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf copy() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        this.checkIndex(n, n2);
        return this.buffer.copy(n, n2);
    }

    @Override
    public ByteBuf discardReadBytes() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf duplicate() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public boolean getBoolean(int n) {
        this.checkIndex(n, 1);
        return this.buffer.getBoolean(n);
    }

    @Override
    public byte getByte(int n) {
        this.checkIndex(n, 1);
        return this.buffer.getByte(n);
    }

    @Override
    public short getUnsignedByte(int n) {
        this.checkIndex(n, 1);
        return this.buffer.getUnsignedByte(n);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkIndex(n, n3);
        this.buffer.getBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray) {
        this.checkIndex(n, byArray.length);
        this.buffer.getBytes(n, byArray);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkIndex(n, n3);
        this.buffer.getBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int getInt(int n) {
        this.checkIndex(n, 4);
        return this.buffer.getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        this.checkIndex(n, 4);
        return this.buffer.getIntLE(n);
    }

    @Override
    public long getUnsignedInt(int n) {
        this.checkIndex(n, 4);
        return this.buffer.getUnsignedInt(n);
    }

    @Override
    public long getUnsignedIntLE(int n) {
        this.checkIndex(n, 4);
        return this.buffer.getUnsignedIntLE(n);
    }

    @Override
    public long getLong(int n) {
        this.checkIndex(n, 8);
        return this.buffer.getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        this.checkIndex(n, 8);
        return this.buffer.getLongLE(n);
    }

    @Override
    public int getMedium(int n) {
        this.checkIndex(n, 3);
        return this.buffer.getMedium(n);
    }

    @Override
    public int getMediumLE(int n) {
        this.checkIndex(n, 3);
        return this.buffer.getMediumLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.checkIndex(n, 3);
        return this.buffer.getUnsignedMedium(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        this.checkIndex(n, 3);
        return this.buffer.getUnsignedMediumLE(n);
    }

    @Override
    public short getShort(int n) {
        this.checkIndex(n, 2);
        return this.buffer.getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        this.checkIndex(n, 2);
        return this.buffer.getShortLE(n);
    }

    @Override
    public int getUnsignedShort(int n) {
        this.checkIndex(n, 2);
        return this.buffer.getUnsignedShort(n);
    }

    @Override
    public int getUnsignedShortLE(int n) {
        this.checkIndex(n, 2);
        return this.buffer.getUnsignedShortLE(n);
    }

    @Override
    public char getChar(int n) {
        this.checkIndex(n, 2);
        return this.buffer.getChar(n);
    }

    @Override
    public float getFloat(int n) {
        this.checkIndex(n, 4);
        return this.buffer.getFloat(n);
    }

    @Override
    public double getDouble(int n) {
        this.checkIndex(n, 8);
        return this.buffer.getDouble(n);
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        this.checkIndex(n, n2);
        return this.buffer.getCharSequence(n, n2, charset);
    }

    @Override
    public int hashCode() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int indexOf(int n, int n2, byte by) {
        if (n == n2) {
            return 1;
        }
        if (Math.max(n, n2) > this.buffer.writerIndex()) {
            throw REPLAY;
        }
        return this.buffer.indexOf(n, n2, by);
    }

    @Override
    public int bytesBefore(byte by) {
        int n = this.buffer.bytesBefore(by);
        if (n < 0) {
            throw REPLAY;
        }
        return n;
    }

    @Override
    public int bytesBefore(int n, byte by) {
        return this.bytesBefore(this.buffer.readerIndex(), n, by);
    }

    @Override
    public int bytesBefore(int n, int n2, byte by) {
        int n3 = this.buffer.writerIndex();
        if (n >= n3) {
            throw REPLAY;
        }
        if (n <= n3 - n2) {
            return this.buffer.bytesBefore(n, n2, by);
        }
        int n4 = this.buffer.bytesBefore(n, n3 - n, by);
        if (n4 < 0) {
            throw REPLAY;
        }
        return n4;
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        int n = this.buffer.forEachByte(byteProcessor);
        if (n < 0) {
            throw REPLAY;
        }
        return n;
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        int n3 = this.buffer.writerIndex();
        if (n >= n3) {
            throw REPLAY;
        }
        if (n <= n3 - n2) {
            return this.buffer.forEachByte(n, n2, byteProcessor);
        }
        int n4 = this.buffer.forEachByte(n, n3 - n, byteProcessor);
        if (n4 < 0) {
            throw REPLAY;
        }
        return n4;
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        if (this.terminated) {
            return this.buffer.forEachByteDesc(byteProcessor);
        }
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        if (n + n2 > this.buffer.writerIndex()) {
            throw REPLAY;
        }
        return this.buffer.forEachByteDesc(n, n2, byteProcessor);
    }

    @Override
    public ByteBuf markReaderIndex() {
        this.buffer.markReaderIndex();
        return this;
    }

    @Override
    public ByteBuf markWriterIndex() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteOrder order() {
        return this.buffer.order();
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        SwappedByteBuf swappedByteBuf = this.swapped;
        if (swappedByteBuf == null) {
            this.swapped = swappedByteBuf = new SwappedByteBuf(this);
        }
        return swappedByteBuf;
    }

    @Override
    public boolean isReadable() {
        return this.terminated ? this.buffer.isReadable() : true;
    }

    @Override
    public boolean isReadable(int n) {
        return this.terminated ? this.buffer.isReadable(n) : true;
    }

    @Override
    public int readableBytes() {
        if (this.terminated) {
            return this.buffer.readableBytes();
        }
        return Integer.MAX_VALUE - this.buffer.readerIndex();
    }

    @Override
    public boolean readBoolean() {
        this.checkReadableBytes(1);
        return this.buffer.readBoolean();
    }

    @Override
    public byte readByte() {
        this.checkReadableBytes(1);
        return this.buffer.readByte();
    }

    @Override
    public short readUnsignedByte() {
        this.checkReadableBytes(1);
        return this.buffer.readUnsignedByte();
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        this.checkReadableBytes(n2);
        this.buffer.readBytes(byArray, n, n2);
        return this;
    }

    @Override
    public ByteBuf readBytes(byte[] byArray) {
        this.checkReadableBytes(byArray.length);
        this.buffer.readBytes(byArray);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        this.checkReadableBytes(n2);
        this.buffer.readBytes(byteBuf, n, n2);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        this.checkReadableBytes(byteBuf.writableBytes());
        this.buffer.readBytes(byteBuf);
        return this;
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf readBytes(int n) {
        this.checkReadableBytes(n);
        return this.buffer.readBytes(n);
    }

    @Override
    public ByteBuf readSlice(int n) {
        this.checkReadableBytes(n);
        return this.buffer.readSlice(n);
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        this.checkReadableBytes(n);
        return this.buffer.readRetainedSlice(n);
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int readerIndex() {
        return this.buffer.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int n) {
        this.buffer.readerIndex(n);
        return this;
    }

    @Override
    public int readInt() {
        this.checkReadableBytes(4);
        return this.buffer.readInt();
    }

    @Override
    public int readIntLE() {
        this.checkReadableBytes(4);
        return this.buffer.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        this.checkReadableBytes(4);
        return this.buffer.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        this.checkReadableBytes(4);
        return this.buffer.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        this.checkReadableBytes(8);
        return this.buffer.readLong();
    }

    @Override
    public long readLongLE() {
        this.checkReadableBytes(8);
        return this.buffer.readLongLE();
    }

    @Override
    public int readMedium() {
        this.checkReadableBytes(3);
        return this.buffer.readMedium();
    }

    @Override
    public int readMediumLE() {
        this.checkReadableBytes(3);
        return this.buffer.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        this.checkReadableBytes(3);
        return this.buffer.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        this.checkReadableBytes(3);
        return this.buffer.readUnsignedMediumLE();
    }

    @Override
    public short readShort() {
        this.checkReadableBytes(2);
        return this.buffer.readShort();
    }

    @Override
    public short readShortLE() {
        this.checkReadableBytes(2);
        return this.buffer.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        this.checkReadableBytes(2);
        return this.buffer.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        this.checkReadableBytes(2);
        return this.buffer.readUnsignedShortLE();
    }

    @Override
    public char readChar() {
        this.checkReadableBytes(2);
        return this.buffer.readChar();
    }

    @Override
    public float readFloat() {
        this.checkReadableBytes(4);
        return this.buffer.readFloat();
    }

    @Override
    public double readDouble() {
        this.checkReadableBytes(8);
        return this.buffer.readDouble();
    }

    @Override
    public CharSequence readCharSequence(int n, Charset charset) {
        this.checkReadableBytes(n);
        return this.buffer.readCharSequence(n, charset);
    }

    @Override
    public ByteBuf resetReaderIndex() {
        this.buffer.resetReaderIndex();
        return this;
    }

    @Override
    public ByteBuf resetWriterIndex() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBoolean(int n, boolean bl) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setIndex(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setChar(int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setFloat(int n, float f) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf setDouble(int n, double d) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf skipBytes(int n) {
        this.checkReadableBytes(n);
        this.buffer.skipBytes(n);
        return this;
    }

    @Override
    public ByteBuf slice() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf retainedSlice() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        this.checkIndex(n, n2);
        return this.buffer.slice(n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        this.checkIndex(n, n2);
        return this.buffer.slice(n, n2);
    }

    @Override
    public int nioBufferCount() {
        return this.buffer.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return this.buffer.nioBuffer(n, n2);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        this.checkIndex(n, n2);
        return this.buffer.nioBuffers(n, n2);
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return this.buffer.internalNioBuffer(n, n2);
    }

    @Override
    public String toString(int n, int n2, Charset charset) {
        this.checkIndex(n, n2);
        return this.buffer.toString(n, n2, charset);
    }

    @Override
    public String toString(Charset charset) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + "ridx=" + this.readerIndex() + ", widx=" + this.writerIndex() + ')';
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
    public int writableBytes() {
        return 1;
    }

    @Override
    public int maxWritableBytes() {
        return 1;
    }

    @Override
    public ByteBuf writeBoolean(boolean bl) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeByte(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int writeBytes(InputStream inputStream, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeInt(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeIntLE(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeLong(long l) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeMedium(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeMediumLE(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeZero(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int writerIndex() {
        return this.buffer.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeShort(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeShortLE(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeChar(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeFloat(float f) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf writeDouble(double d) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        throw ReplayingDecoderByteBuf.reject();
    }

    private void checkIndex(int n, int n2) {
        if (n + n2 > this.buffer.writerIndex()) {
            throw REPLAY;
        }
    }

    private void checkReadableBytes(int n) {
        if (this.buffer.readableBytes() < n) {
            throw REPLAY;
        }
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public int refCnt() {
        return this.buffer.refCnt();
    }

    @Override
    public ByteBuf retain() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf retain(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf touch() {
        this.buffer.touch();
        return this;
    }

    @Override
    public ByteBuf touch(Object object) {
        this.buffer.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public boolean release(int n) {
        throw ReplayingDecoderByteBuf.reject();
    }

    @Override
    public ByteBuf unwrap() {
        throw ReplayingDecoderByteBuf.reject();
    }

    private static UnsupportedOperationException reject() {
        return new UnsupportedOperationException("not a replayable operation");
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ByteBuf)object);
    }

    static {
        EMPTY_BUFFER.terminate();
    }
}

