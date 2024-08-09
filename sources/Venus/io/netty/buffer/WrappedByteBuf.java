/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
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
class WrappedByteBuf
extends ByteBuf {
    protected final ByteBuf buf;

    protected WrappedByteBuf(ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("buf");
        }
        this.buf = byteBuf;
    }

    @Override
    public final boolean hasMemoryAddress() {
        return this.buf.hasMemoryAddress();
    }

    @Override
    public final long memoryAddress() {
        return this.buf.memoryAddress();
    }

    @Override
    public final int capacity() {
        return this.buf.capacity();
    }

    @Override
    public ByteBuf capacity(int n) {
        this.buf.capacity(n);
        return this;
    }

    @Override
    public final int maxCapacity() {
        return this.buf.maxCapacity();
    }

    @Override
    public final ByteBufAllocator alloc() {
        return this.buf.alloc();
    }

    @Override
    public final ByteOrder order() {
        return this.buf.order();
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        return this.buf.order(byteOrder);
    }

    @Override
    public final ByteBuf unwrap() {
        return this.buf;
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.buf.asReadOnly();
    }

    @Override
    public boolean isReadOnly() {
        return this.buf.isReadOnly();
    }

    @Override
    public final boolean isDirect() {
        return this.buf.isDirect();
    }

    @Override
    public final int readerIndex() {
        return this.buf.readerIndex();
    }

    @Override
    public final ByteBuf readerIndex(int n) {
        this.buf.readerIndex(n);
        return this;
    }

    @Override
    public final int writerIndex() {
        return this.buf.writerIndex();
    }

    @Override
    public final ByteBuf writerIndex(int n) {
        this.buf.writerIndex(n);
        return this;
    }

    @Override
    public ByteBuf setIndex(int n, int n2) {
        this.buf.setIndex(n, n2);
        return this;
    }

    @Override
    public final int readableBytes() {
        return this.buf.readableBytes();
    }

    @Override
    public final int writableBytes() {
        return this.buf.writableBytes();
    }

    @Override
    public final int maxWritableBytes() {
        return this.buf.maxWritableBytes();
    }

    @Override
    public final boolean isReadable() {
        return this.buf.isReadable();
    }

    @Override
    public final boolean isWritable() {
        return this.buf.isWritable();
    }

    @Override
    public final ByteBuf clear() {
        this.buf.clear();
        return this;
    }

    @Override
    public final ByteBuf markReaderIndex() {
        this.buf.markReaderIndex();
        return this;
    }

    @Override
    public final ByteBuf resetReaderIndex() {
        this.buf.resetReaderIndex();
        return this;
    }

    @Override
    public final ByteBuf markWriterIndex() {
        this.buf.markWriterIndex();
        return this;
    }

    @Override
    public final ByteBuf resetWriterIndex() {
        this.buf.resetWriterIndex();
        return this;
    }

    @Override
    public ByteBuf discardReadBytes() {
        this.buf.discardReadBytes();
        return this;
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        this.buf.discardSomeReadBytes();
        return this;
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        this.buf.ensureWritable(n);
        return this;
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        return this.buf.ensureWritable(n, bl);
    }

    @Override
    public boolean getBoolean(int n) {
        return this.buf.getBoolean(n);
    }

    @Override
    public byte getByte(int n) {
        return this.buf.getByte(n);
    }

    @Override
    public short getUnsignedByte(int n) {
        return this.buf.getUnsignedByte(n);
    }

    @Override
    public short getShort(int n) {
        return this.buf.getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        return this.buf.getShortLE(n);
    }

    @Override
    public int getUnsignedShort(int n) {
        return this.buf.getUnsignedShort(n);
    }

    @Override
    public int getUnsignedShortLE(int n) {
        return this.buf.getUnsignedShortLE(n);
    }

    @Override
    public int getMedium(int n) {
        return this.buf.getMedium(n);
    }

    @Override
    public int getMediumLE(int n) {
        return this.buf.getMediumLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        return this.buf.getUnsignedMedium(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        return this.buf.getUnsignedMediumLE(n);
    }

    @Override
    public int getInt(int n) {
        return this.buf.getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        return this.buf.getIntLE(n);
    }

    @Override
    public long getUnsignedInt(int n) {
        return this.buf.getUnsignedInt(n);
    }

    @Override
    public long getUnsignedIntLE(int n) {
        return this.buf.getUnsignedIntLE(n);
    }

    @Override
    public long getLong(int n) {
        return this.buf.getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        return this.buf.getLongLE(n);
    }

    @Override
    public char getChar(int n) {
        return this.buf.getChar(n);
    }

    @Override
    public float getFloat(int n) {
        return this.buf.getFloat(n);
    }

    @Override
    public double getDouble(int n) {
        return this.buf.getDouble(n);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        this.buf.getBytes(n, byteBuf);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        this.buf.getBytes(n, byteBuf, n2);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.buf.getBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray) {
        this.buf.getBytes(n, byArray);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.buf.getBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.buf.getBytes(n, byteBuffer);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.buf.getBytes(n, outputStream, n2);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.buf.getBytes(n, gatheringByteChannel, n2);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.buf.getBytes(n, fileChannel, l, n2);
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        return this.buf.getCharSequence(n, n2, charset);
    }

    @Override
    public ByteBuf setBoolean(int n, boolean bl) {
        this.buf.setBoolean(n, bl);
        return this;
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.buf.setByte(n, n2);
        return this;
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.buf.setShort(n, n2);
        return this;
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        this.buf.setShortLE(n, n2);
        return this;
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.buf.setMedium(n, n2);
        return this;
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        this.buf.setMediumLE(n, n2);
        return this;
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.buf.setInt(n, n2);
        return this;
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        this.buf.setIntLE(n, n2);
        return this;
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.buf.setLong(n, l);
        return this;
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        this.buf.setLongLE(n, l);
        return this;
    }

    @Override
    public ByteBuf setChar(int n, int n2) {
        this.buf.setChar(n, n2);
        return this;
    }

    @Override
    public ByteBuf setFloat(int n, float f) {
        this.buf.setFloat(n, f);
        return this;
    }

    @Override
    public ByteBuf setDouble(int n, double d) {
        this.buf.setDouble(n, d);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        this.buf.setBytes(n, byteBuf);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        this.buf.setBytes(n, byteBuf, n2);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.buf.setBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray) {
        this.buf.setBytes(n, byArray);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.buf.setBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        this.buf.setBytes(n, byteBuffer);
        return this;
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return this.buf.setBytes(n, inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        return this.buf.setBytes(n, scatteringByteChannel, n2);
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.buf.setBytes(n, fileChannel, l, n2);
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        this.buf.setZero(n, n2);
        return this;
    }

    @Override
    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        return this.buf.setCharSequence(n, charSequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return this.buf.readBoolean();
    }

    @Override
    public byte readByte() {
        return this.buf.readByte();
    }

    @Override
    public short readUnsignedByte() {
        return this.buf.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return this.buf.readShort();
    }

    @Override
    public short readShortLE() {
        return this.buf.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return this.buf.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return this.buf.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return this.buf.readMedium();
    }

    @Override
    public int readMediumLE() {
        return this.buf.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return this.buf.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return this.buf.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return this.buf.readInt();
    }

    @Override
    public int readIntLE() {
        return this.buf.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return this.buf.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return this.buf.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return this.buf.readLong();
    }

    @Override
    public long readLongLE() {
        return this.buf.readLongLE();
    }

    @Override
    public char readChar() {
        return this.buf.readChar();
    }

    @Override
    public float readFloat() {
        return this.buf.readFloat();
    }

    @Override
    public double readDouble() {
        return this.buf.readDouble();
    }

    @Override
    public ByteBuf readBytes(int n) {
        return this.buf.readBytes(n);
    }

    @Override
    public ByteBuf readSlice(int n) {
        return this.buf.readSlice(n);
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.buf.readRetainedSlice(n);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        this.buf.readBytes(byteBuf);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        this.buf.readBytes(byteBuf, n);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        this.buf.readBytes(byteBuf, n, n2);
        return this;
    }

    @Override
    public ByteBuf readBytes(byte[] byArray) {
        this.buf.readBytes(byArray);
        return this;
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        this.buf.readBytes(byArray, n, n2);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        this.buf.readBytes(byteBuffer);
        return this;
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        this.buf.readBytes(outputStream, n);
        return this;
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        return this.buf.readBytes(gatheringByteChannel, n);
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.buf.readBytes(fileChannel, l, n);
    }

    @Override
    public CharSequence readCharSequence(int n, Charset charset) {
        return this.buf.readCharSequence(n, charset);
    }

    @Override
    public ByteBuf skipBytes(int n) {
        this.buf.skipBytes(n);
        return this;
    }

    @Override
    public ByteBuf writeBoolean(boolean bl) {
        this.buf.writeBoolean(bl);
        return this;
    }

    @Override
    public ByteBuf writeByte(int n) {
        this.buf.writeByte(n);
        return this;
    }

    @Override
    public ByteBuf writeShort(int n) {
        this.buf.writeShort(n);
        return this;
    }

    @Override
    public ByteBuf writeShortLE(int n) {
        this.buf.writeShortLE(n);
        return this;
    }

    @Override
    public ByteBuf writeMedium(int n) {
        this.buf.writeMedium(n);
        return this;
    }

    @Override
    public ByteBuf writeMediumLE(int n) {
        this.buf.writeMediumLE(n);
        return this;
    }

    @Override
    public ByteBuf writeInt(int n) {
        this.buf.writeInt(n);
        return this;
    }

    @Override
    public ByteBuf writeIntLE(int n) {
        this.buf.writeIntLE(n);
        return this;
    }

    @Override
    public ByteBuf writeLong(long l) {
        this.buf.writeLong(l);
        return this;
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        this.buf.writeLongLE(l);
        return this;
    }

    @Override
    public ByteBuf writeChar(int n) {
        this.buf.writeChar(n);
        return this;
    }

    @Override
    public ByteBuf writeFloat(float f) {
        this.buf.writeFloat(f);
        return this;
    }

    @Override
    public ByteBuf writeDouble(double d) {
        this.buf.writeDouble(d);
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        this.buf.writeBytes(byteBuf);
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        this.buf.writeBytes(byteBuf, n);
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        this.buf.writeBytes(byteBuf, n, n2);
        return this;
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray) {
        this.buf.writeBytes(byArray);
        return this;
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        this.buf.writeBytes(byArray, n, n2);
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.buf.writeBytes(byteBuffer);
        return this;
    }

    @Override
    public int writeBytes(InputStream inputStream, int n) throws IOException {
        return this.buf.writeBytes(inputStream, n);
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) throws IOException {
        return this.buf.writeBytes(scatteringByteChannel, n);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.buf.writeBytes(fileChannel, l, n);
    }

    @Override
    public ByteBuf writeZero(int n) {
        this.buf.writeZero(n);
        return this;
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.buf.writeCharSequence(charSequence, charset);
    }

    @Override
    public int indexOf(int n, int n2, byte by) {
        return this.buf.indexOf(n, n2, by);
    }

    @Override
    public int bytesBefore(byte by) {
        return this.buf.bytesBefore(by);
    }

    @Override
    public int bytesBefore(int n, byte by) {
        return this.buf.bytesBefore(n, by);
    }

    @Override
    public int bytesBefore(int n, int n2, byte by) {
        return this.buf.bytesBefore(n, n2, by);
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return this.buf.forEachByte(byteProcessor);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return this.buf.forEachByte(n, n2, byteProcessor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.buf.forEachByteDesc(byteProcessor);
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return this.buf.forEachByteDesc(n, n2, byteProcessor);
    }

    @Override
    public ByteBuf copy() {
        return this.buf.copy();
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return this.buf.copy(n, n2);
    }

    @Override
    public ByteBuf slice() {
        return this.buf.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.buf.retainedSlice();
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return this.buf.slice(n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.buf.retainedSlice(n, n2);
    }

    @Override
    public ByteBuf duplicate() {
        return this.buf.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.buf.retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return this.buf.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return this.buf.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return this.buf.nioBuffer(n, n2);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return this.buf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return this.buf.nioBuffers(n, n2);
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        return this.buf.internalNioBuffer(n, n2);
    }

    @Override
    public boolean hasArray() {
        return this.buf.hasArray();
    }

    @Override
    public byte[] array() {
        return this.buf.array();
    }

    @Override
    public int arrayOffset() {
        return this.buf.arrayOffset();
    }

    @Override
    public String toString(Charset charset) {
        return this.buf.toString(charset);
    }

    @Override
    public String toString(int n, int n2, Charset charset) {
        return this.buf.toString(n, n2, charset);
    }

    @Override
    public int hashCode() {
        return this.buf.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return this.buf.equals(object);
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return this.buf.compareTo(byteBuf);
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.buf.toString() + ')';
    }

    @Override
    public ByteBuf retain(int n) {
        this.buf.retain(n);
        return this;
    }

    @Override
    public ByteBuf retain() {
        this.buf.retain();
        return this;
    }

    @Override
    public ByteBuf touch() {
        this.buf.touch();
        return this;
    }

    @Override
    public ByteBuf touch(Object object) {
        this.buf.touch(object);
        return this;
    }

    @Override
    public final boolean isReadable(int n) {
        return this.buf.isReadable(n);
    }

    @Override
    public final boolean isWritable(int n) {
        return this.buf.isWritable(n);
    }

    @Override
    public final int refCnt() {
        return this.buf.refCnt();
    }

    @Override
    public boolean release() {
        return this.buf.release();
    }

    @Override
    public boolean release(int n) {
        return this.buf.release(n);
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
}

