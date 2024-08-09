/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class WrappedCompositeByteBuf
extends CompositeByteBuf {
    private final CompositeByteBuf wrapped;

    WrappedCompositeByteBuf(CompositeByteBuf compositeByteBuf) {
        super(compositeByteBuf.alloc());
        this.wrapped = compositeByteBuf;
    }

    @Override
    public boolean release() {
        return this.wrapped.release();
    }

    @Override
    public boolean release(int n) {
        return this.wrapped.release(n);
    }

    @Override
    public final int maxCapacity() {
        return this.wrapped.maxCapacity();
    }

    @Override
    public final int readerIndex() {
        return this.wrapped.readerIndex();
    }

    @Override
    public final int writerIndex() {
        return this.wrapped.writerIndex();
    }

    @Override
    public final boolean isReadable() {
        return this.wrapped.isReadable();
    }

    @Override
    public final boolean isReadable(int n) {
        return this.wrapped.isReadable(n);
    }

    @Override
    public final boolean isWritable() {
        return this.wrapped.isWritable();
    }

    @Override
    public final boolean isWritable(int n) {
        return this.wrapped.isWritable(n);
    }

    @Override
    public final int readableBytes() {
        return this.wrapped.readableBytes();
    }

    @Override
    public final int writableBytes() {
        return this.wrapped.writableBytes();
    }

    @Override
    public final int maxWritableBytes() {
        return this.wrapped.maxWritableBytes();
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        return this.wrapped.ensureWritable(n, bl);
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        return this.wrapped.order(byteOrder);
    }

    @Override
    public boolean getBoolean(int n) {
        return this.wrapped.getBoolean(n);
    }

    @Override
    public short getUnsignedByte(int n) {
        return this.wrapped.getUnsignedByte(n);
    }

    @Override
    public short getShort(int n) {
        return this.wrapped.getShort(n);
    }

    @Override
    public short getShortLE(int n) {
        return this.wrapped.getShortLE(n);
    }

    @Override
    public int getUnsignedShort(int n) {
        return this.wrapped.getUnsignedShort(n);
    }

    @Override
    public int getUnsignedShortLE(int n) {
        return this.wrapped.getUnsignedShortLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        return this.wrapped.getUnsignedMedium(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        return this.wrapped.getUnsignedMediumLE(n);
    }

    @Override
    public int getMedium(int n) {
        return this.wrapped.getMedium(n);
    }

    @Override
    public int getMediumLE(int n) {
        return this.wrapped.getMediumLE(n);
    }

    @Override
    public int getInt(int n) {
        return this.wrapped.getInt(n);
    }

    @Override
    public int getIntLE(int n) {
        return this.wrapped.getIntLE(n);
    }

    @Override
    public long getUnsignedInt(int n) {
        return this.wrapped.getUnsignedInt(n);
    }

    @Override
    public long getUnsignedIntLE(int n) {
        return this.wrapped.getUnsignedIntLE(n);
    }

    @Override
    public long getLong(int n) {
        return this.wrapped.getLong(n);
    }

    @Override
    public long getLongLE(int n) {
        return this.wrapped.getLongLE(n);
    }

    @Override
    public char getChar(int n) {
        return this.wrapped.getChar(n);
    }

    @Override
    public float getFloat(int n) {
        return this.wrapped.getFloat(n);
    }

    @Override
    public double getDouble(int n) {
        return this.wrapped.getDouble(n);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        return this.wrapped.setShortLE(n, n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        return this.wrapped.setMediumLE(n, n2);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        return this.wrapped.setIntLE(n, n2);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        return this.wrapped.setLongLE(n, l);
    }

    @Override
    public byte readByte() {
        return this.wrapped.readByte();
    }

    @Override
    public boolean readBoolean() {
        return this.wrapped.readBoolean();
    }

    @Override
    public short readUnsignedByte() {
        return this.wrapped.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return this.wrapped.readShort();
    }

    @Override
    public short readShortLE() {
        return this.wrapped.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return this.wrapped.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return this.wrapped.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return this.wrapped.readMedium();
    }

    @Override
    public int readMediumLE() {
        return this.wrapped.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return this.wrapped.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return this.wrapped.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return this.wrapped.readInt();
    }

    @Override
    public int readIntLE() {
        return this.wrapped.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return this.wrapped.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return this.wrapped.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return this.wrapped.readLong();
    }

    @Override
    public long readLongLE() {
        return this.wrapped.readLongLE();
    }

    @Override
    public char readChar() {
        return this.wrapped.readChar();
    }

    @Override
    public float readFloat() {
        return this.wrapped.readFloat();
    }

    @Override
    public double readDouble() {
        return this.wrapped.readDouble();
    }

    @Override
    public ByteBuf readBytes(int n) {
        return this.wrapped.readBytes(n);
    }

    @Override
    public ByteBuf slice() {
        return this.wrapped.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.wrapped.retainedSlice();
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return this.wrapped.slice(n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.wrapped.retainedSlice(n, n2);
    }

    @Override
    public ByteBuffer nioBuffer() {
        return this.wrapped.nioBuffer();
    }

    @Override
    public String toString(Charset charset) {
        return this.wrapped.toString(charset);
    }

    @Override
    public String toString(int n, int n2, Charset charset) {
        return this.wrapped.toString(n, n2, charset);
    }

    @Override
    public int indexOf(int n, int n2, byte by) {
        return this.wrapped.indexOf(n, n2, by);
    }

    @Override
    public int bytesBefore(byte by) {
        return this.wrapped.bytesBefore(by);
    }

    @Override
    public int bytesBefore(int n, byte by) {
        return this.wrapped.bytesBefore(n, by);
    }

    @Override
    public int bytesBefore(int n, int n2, byte by) {
        return this.wrapped.bytesBefore(n, n2, by);
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return this.wrapped.forEachByte(byteProcessor);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return this.wrapped.forEachByte(n, n2, byteProcessor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return this.wrapped.forEachByteDesc(byteProcessor);
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return this.wrapped.forEachByteDesc(n, n2, byteProcessor);
    }

    @Override
    public final int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public final boolean equals(Object object) {
        return this.wrapped.equals(object);
    }

    @Override
    public final int compareTo(ByteBuf byteBuf) {
        return this.wrapped.compareTo(byteBuf);
    }

    @Override
    public final int refCnt() {
        return this.wrapped.refCnt();
    }

    @Override
    public ByteBuf duplicate() {
        return this.wrapped.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.wrapped.retainedDuplicate();
    }

    @Override
    public ByteBuf readSlice(int n) {
        return this.wrapped.readSlice(n);
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.wrapped.readRetainedSlice(n);
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        return this.wrapped.readBytes(gatheringByteChannel, n);
    }

    @Override
    public ByteBuf writeShortLE(int n) {
        return this.wrapped.writeShortLE(n);
    }

    @Override
    public ByteBuf writeMediumLE(int n) {
        return this.wrapped.writeMediumLE(n);
    }

    @Override
    public ByteBuf writeIntLE(int n) {
        return this.wrapped.writeIntLE(n);
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        return this.wrapped.writeLongLE(l);
    }

    @Override
    public int writeBytes(InputStream inputStream, int n) throws IOException {
        return this.wrapped.writeBytes(inputStream, n);
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) throws IOException {
        return this.wrapped.writeBytes(scatteringByteChannel, n);
    }

    @Override
    public ByteBuf copy() {
        return this.wrapped.copy();
    }

    @Override
    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        this.wrapped.addComponent(byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf addComponents(ByteBuf ... byteBufArray) {
        this.wrapped.addComponents(byteBufArray);
        return this;
    }

    @Override
    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        this.wrapped.addComponents(iterable);
        return this;
    }

    @Override
    public CompositeByteBuf addComponent(int n, ByteBuf byteBuf) {
        this.wrapped.addComponent(n, byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf addComponents(int n, ByteBuf ... byteBufArray) {
        this.wrapped.addComponents(n, byteBufArray);
        return this;
    }

    @Override
    public CompositeByteBuf addComponents(int n, Iterable<ByteBuf> iterable) {
        this.wrapped.addComponents(n, iterable);
        return this;
    }

    @Override
    public CompositeByteBuf addComponent(boolean bl, ByteBuf byteBuf) {
        this.wrapped.addComponent(bl, byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf addComponents(boolean bl, ByteBuf ... byteBufArray) {
        this.wrapped.addComponents(bl, byteBufArray);
        return this;
    }

    @Override
    public CompositeByteBuf addComponents(boolean bl, Iterable<ByteBuf> iterable) {
        this.wrapped.addComponents(bl, iterable);
        return this;
    }

    @Override
    public CompositeByteBuf addComponent(boolean bl, int n, ByteBuf byteBuf) {
        this.wrapped.addComponent(bl, n, byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf removeComponent(int n) {
        this.wrapped.removeComponent(n);
        return this;
    }

    @Override
    public CompositeByteBuf removeComponents(int n, int n2) {
        this.wrapped.removeComponents(n, n2);
        return this;
    }

    @Override
    public Iterator<ByteBuf> iterator() {
        return this.wrapped.iterator();
    }

    @Override
    public List<ByteBuf> decompose(int n, int n2) {
        return this.wrapped.decompose(n, n2);
    }

    @Override
    public final boolean isDirect() {
        return this.wrapped.isDirect();
    }

    @Override
    public final boolean hasArray() {
        return this.wrapped.hasArray();
    }

    @Override
    public final byte[] array() {
        return this.wrapped.array();
    }

    @Override
    public final int arrayOffset() {
        return this.wrapped.arrayOffset();
    }

    @Override
    public final boolean hasMemoryAddress() {
        return this.wrapped.hasMemoryAddress();
    }

    @Override
    public final long memoryAddress() {
        return this.wrapped.memoryAddress();
    }

    @Override
    public final int capacity() {
        return this.wrapped.capacity();
    }

    @Override
    public CompositeByteBuf capacity(int n) {
        this.wrapped.capacity(n);
        return this;
    }

    @Override
    public final ByteBufAllocator alloc() {
        return this.wrapped.alloc();
    }

    @Override
    public final ByteOrder order() {
        return this.wrapped.order();
    }

    @Override
    public final int numComponents() {
        return this.wrapped.numComponents();
    }

    @Override
    public final int maxNumComponents() {
        return this.wrapped.maxNumComponents();
    }

    @Override
    public final int toComponentIndex(int n) {
        return this.wrapped.toComponentIndex(n);
    }

    @Override
    public final int toByteIndex(int n) {
        return this.wrapped.toByteIndex(n);
    }

    @Override
    public byte getByte(int n) {
        return this.wrapped.getByte(n);
    }

    @Override
    protected final byte _getByte(int n) {
        return this.wrapped._getByte(n);
    }

    @Override
    protected final short _getShort(int n) {
        return this.wrapped._getShort(n);
    }

    @Override
    protected final short _getShortLE(int n) {
        return this.wrapped._getShortLE(n);
    }

    @Override
    protected final int _getUnsignedMedium(int n) {
        return this.wrapped._getUnsignedMedium(n);
    }

    @Override
    protected final int _getUnsignedMediumLE(int n) {
        return this.wrapped._getUnsignedMediumLE(n);
    }

    @Override
    protected final int _getInt(int n) {
        return this.wrapped._getInt(n);
    }

    @Override
    protected final int _getIntLE(int n) {
        return this.wrapped._getIntLE(n);
    }

    @Override
    protected final long _getLong(int n) {
        return this.wrapped._getLong(n);
    }

    @Override
    protected final long _getLongLE(int n) {
        return this.wrapped._getLongLE(n);
    }

    @Override
    public CompositeByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.wrapped.getBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.wrapped.getBytes(n, byteBuffer);
        return this;
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.wrapped.getBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.wrapped.getBytes(n, gatheringByteChannel, n2);
    }

    @Override
    public CompositeByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.wrapped.getBytes(n, outputStream, n2);
        return this;
    }

    @Override
    public CompositeByteBuf setByte(int n, int n2) {
        this.wrapped.setByte(n, n2);
        return this;
    }

    @Override
    protected final void _setByte(int n, int n2) {
        this.wrapped._setByte(n, n2);
    }

    @Override
    public CompositeByteBuf setShort(int n, int n2) {
        this.wrapped.setShort(n, n2);
        return this;
    }

    @Override
    protected final void _setShort(int n, int n2) {
        this.wrapped._setShort(n, n2);
    }

    @Override
    protected final void _setShortLE(int n, int n2) {
        this.wrapped._setShortLE(n, n2);
    }

    @Override
    public CompositeByteBuf setMedium(int n, int n2) {
        this.wrapped.setMedium(n, n2);
        return this;
    }

    @Override
    protected final void _setMedium(int n, int n2) {
        this.wrapped._setMedium(n, n2);
    }

    @Override
    protected final void _setMediumLE(int n, int n2) {
        this.wrapped._setMediumLE(n, n2);
    }

    @Override
    public CompositeByteBuf setInt(int n, int n2) {
        this.wrapped.setInt(n, n2);
        return this;
    }

    @Override
    protected final void _setInt(int n, int n2) {
        this.wrapped._setInt(n, n2);
    }

    @Override
    protected final void _setIntLE(int n, int n2) {
        this.wrapped._setIntLE(n, n2);
    }

    @Override
    public CompositeByteBuf setLong(int n, long l) {
        this.wrapped.setLong(n, l);
        return this;
    }

    @Override
    protected final void _setLong(int n, long l) {
        this.wrapped._setLong(n, l);
    }

    @Override
    protected final void _setLongLE(int n, long l) {
        this.wrapped._setLongLE(n, l);
    }

    @Override
    public CompositeByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.wrapped.setBytes(n, byArray, n2, n3);
        return this;
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        this.wrapped.setBytes(n, byteBuffer);
        return this;
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.wrapped.setBytes(n, byteBuf, n2, n3);
        return this;
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return this.wrapped.setBytes(n, inputStream, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        return this.wrapped.setBytes(n, scatteringByteChannel, n2);
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return this.wrapped.copy(n, n2);
    }

    @Override
    public final ByteBuf component(int n) {
        return this.wrapped.component(n);
    }

    @Override
    public final ByteBuf componentAtOffset(int n) {
        return this.wrapped.componentAtOffset(n);
    }

    @Override
    public final ByteBuf internalComponent(int n) {
        return this.wrapped.internalComponent(n);
    }

    @Override
    public final ByteBuf internalComponentAtOffset(int n) {
        return this.wrapped.internalComponentAtOffset(n);
    }

    @Override
    public int nioBufferCount() {
        return this.wrapped.nioBufferCount();
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        return this.wrapped.internalNioBuffer(n, n2);
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return this.wrapped.nioBuffer(n, n2);
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return this.wrapped.nioBuffers(n, n2);
    }

    @Override
    public CompositeByteBuf consolidate() {
        this.wrapped.consolidate();
        return this;
    }

    @Override
    public CompositeByteBuf consolidate(int n, int n2) {
        this.wrapped.consolidate(n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf discardReadComponents() {
        this.wrapped.discardReadComponents();
        return this;
    }

    @Override
    public CompositeByteBuf discardReadBytes() {
        this.wrapped.discardReadBytes();
        return this;
    }

    @Override
    public final String toString() {
        return this.wrapped.toString();
    }

    @Override
    public final CompositeByteBuf readerIndex(int n) {
        this.wrapped.readerIndex(n);
        return this;
    }

    @Override
    public final CompositeByteBuf writerIndex(int n) {
        this.wrapped.writerIndex(n);
        return this;
    }

    @Override
    public final CompositeByteBuf setIndex(int n, int n2) {
        this.wrapped.setIndex(n, n2);
        return this;
    }

    @Override
    public final CompositeByteBuf clear() {
        this.wrapped.clear();
        return this;
    }

    @Override
    public final CompositeByteBuf markReaderIndex() {
        this.wrapped.markReaderIndex();
        return this;
    }

    @Override
    public final CompositeByteBuf resetReaderIndex() {
        this.wrapped.resetReaderIndex();
        return this;
    }

    @Override
    public final CompositeByteBuf markWriterIndex() {
        this.wrapped.markWriterIndex();
        return this;
    }

    @Override
    public final CompositeByteBuf resetWriterIndex() {
        this.wrapped.resetWriterIndex();
        return this;
    }

    @Override
    public CompositeByteBuf ensureWritable(int n) {
        this.wrapped.ensureWritable(n);
        return this;
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuf byteBuf) {
        this.wrapped.getBytes(n, byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        this.wrapped.getBytes(n, byteBuf, n2);
        return this;
    }

    @Override
    public CompositeByteBuf getBytes(int n, byte[] byArray) {
        this.wrapped.getBytes(n, byArray);
        return this;
    }

    @Override
    public CompositeByteBuf setBoolean(int n, boolean bl) {
        this.wrapped.setBoolean(n, bl);
        return this;
    }

    @Override
    public CompositeByteBuf setChar(int n, int n2) {
        this.wrapped.setChar(n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf setFloat(int n, float f) {
        this.wrapped.setFloat(n, f);
        return this;
    }

    @Override
    public CompositeByteBuf setDouble(int n, double d) {
        this.wrapped.setDouble(n, d);
        return this;
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuf byteBuf) {
        this.wrapped.setBytes(n, byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        this.wrapped.setBytes(n, byteBuf, n2);
        return this;
    }

    @Override
    public CompositeByteBuf setBytes(int n, byte[] byArray) {
        this.wrapped.setBytes(n, byArray);
        return this;
    }

    @Override
    public CompositeByteBuf setZero(int n, int n2) {
        this.wrapped.setZero(n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        this.wrapped.readBytes(byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int n) {
        this.wrapped.readBytes(byteBuf, n);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        this.wrapped.readBytes(byteBuf, n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(byte[] byArray) {
        this.wrapped.readBytes(byArray);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(byte[] byArray, int n, int n2) {
        this.wrapped.readBytes(byArray, n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        this.wrapped.readBytes(byteBuffer);
        return this;
    }

    @Override
    public CompositeByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        this.wrapped.readBytes(outputStream, n);
        return this;
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.wrapped.getBytes(n, fileChannel, l, n2);
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.wrapped.setBytes(n, fileChannel, l, n2);
    }

    @Override
    public boolean isReadOnly() {
        return this.wrapped.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.wrapped.asReadOnly();
    }

    @Override
    protected SwappedByteBuf newSwappedByteBuf() {
        return this.wrapped.newSwappedByteBuf();
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        return this.wrapped.getCharSequence(n, n2, charset);
    }

    @Override
    public CharSequence readCharSequence(int n, Charset charset) {
        return this.wrapped.readCharSequence(n, charset);
    }

    @Override
    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        return this.wrapped.setCharSequence(n, charSequence, charset);
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.wrapped.readBytes(fileChannel, l, n);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int n) throws IOException {
        return this.wrapped.writeBytes(fileChannel, l, n);
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return this.wrapped.writeCharSequence(charSequence, charset);
    }

    @Override
    public CompositeByteBuf skipBytes(int n) {
        this.wrapped.skipBytes(n);
        return this;
    }

    @Override
    public CompositeByteBuf writeBoolean(boolean bl) {
        this.wrapped.writeBoolean(bl);
        return this;
    }

    @Override
    public CompositeByteBuf writeByte(int n) {
        this.wrapped.writeByte(n);
        return this;
    }

    @Override
    public CompositeByteBuf writeShort(int n) {
        this.wrapped.writeShort(n);
        return this;
    }

    @Override
    public CompositeByteBuf writeMedium(int n) {
        this.wrapped.writeMedium(n);
        return this;
    }

    @Override
    public CompositeByteBuf writeInt(int n) {
        this.wrapped.writeInt(n);
        return this;
    }

    @Override
    public CompositeByteBuf writeLong(long l) {
        this.wrapped.writeLong(l);
        return this;
    }

    @Override
    public CompositeByteBuf writeChar(int n) {
        this.wrapped.writeChar(n);
        return this;
    }

    @Override
    public CompositeByteBuf writeFloat(float f) {
        this.wrapped.writeFloat(f);
        return this;
    }

    @Override
    public CompositeByteBuf writeDouble(double d) {
        this.wrapped.writeDouble(d);
        return this;
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        this.wrapped.writeBytes(byteBuf);
        return this;
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int n) {
        this.wrapped.writeBytes(byteBuf, n);
        return this;
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        this.wrapped.writeBytes(byteBuf, n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf writeBytes(byte[] byArray) {
        this.wrapped.writeBytes(byArray);
        return this;
    }

    @Override
    public CompositeByteBuf writeBytes(byte[] byArray, int n, int n2) {
        this.wrapped.writeBytes(byArray, n, n2);
        return this;
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        this.wrapped.writeBytes(byteBuffer);
        return this;
    }

    @Override
    public CompositeByteBuf writeZero(int n) {
        this.wrapped.writeZero(n);
        return this;
    }

    @Override
    public CompositeByteBuf retain(int n) {
        this.wrapped.retain(n);
        return this;
    }

    @Override
    public CompositeByteBuf retain() {
        this.wrapped.retain();
        return this;
    }

    @Override
    public CompositeByteBuf touch() {
        this.wrapped.touch();
        return this;
    }

    @Override
    public CompositeByteBuf touch(Object object) {
        this.wrapped.touch(object);
        return this;
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return this.wrapped.nioBuffers();
    }

    @Override
    public CompositeByteBuf discardSomeReadBytes() {
        this.wrapped.discardSomeReadBytes();
        return this;
    }

    @Override
    public final void deallocate() {
        this.wrapped.deallocate();
    }

    @Override
    public final ByteBuf unwrap() {
        return this.wrapped;
    }

    @Override
    public ByteBuf touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBuf touch() {
        return this.touch();
    }

    @Override
    public ByteBuf retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBuf retain() {
        return this.retain();
    }

    @Override
    public ByteBuf writeZero(int n) {
        return this.writeZero(n);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return this.writeBytes(byteBuffer);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        return this.writeBytes(byteBuf, n, n2);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        return this.writeBytes(byteBuf, n);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return this.writeBytes(byteBuf);
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray) {
        return this.writeBytes(byArray);
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        return this.writeBytes(byArray, n, n2);
    }

    @Override
    public ByteBuf writeDouble(double d) {
        return this.writeDouble(d);
    }

    @Override
    public ByteBuf writeFloat(float f) {
        return this.writeFloat(f);
    }

    @Override
    public ByteBuf writeChar(int n) {
        return this.writeChar(n);
    }

    @Override
    public ByteBuf writeLong(long l) {
        return this.writeLong(l);
    }

    @Override
    public ByteBuf writeInt(int n) {
        return this.writeInt(n);
    }

    @Override
    public ByteBuf writeMedium(int n) {
        return this.writeMedium(n);
    }

    @Override
    public ByteBuf writeShort(int n) {
        return this.writeShort(n);
    }

    @Override
    public ByteBuf writeByte(int n) {
        return this.writeByte(n);
    }

    @Override
    public ByteBuf writeBoolean(boolean bl) {
        return this.writeBoolean(bl);
    }

    @Override
    public ByteBuf skipBytes(int n) {
        return this.skipBytes(n);
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        return this.readBytes(outputStream, n);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return this.readBytes(byteBuffer);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        return this.readBytes(byteBuf, n, n2);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        return this.readBytes(byteBuf, n);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        return this.readBytes(byteBuf);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray) {
        return this.readBytes(byArray);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        return this.readBytes(byArray, n, n2);
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        return this.setZero(n, n2);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        return this.setBytes(n, byteBuf, n2);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        return this.setBytes(n, byteBuf);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray) {
        return this.setBytes(n, byArray);
    }

    @Override
    public ByteBuf setDouble(int n, double d) {
        return this.setDouble(n, d);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        return this.setLong(n, l);
    }

    @Override
    public ByteBuf setFloat(int n, float f) {
        return this.setFloat(n, f);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        return this.setInt(n, n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        return this.setMedium(n, n2);
    }

    @Override
    public ByteBuf setChar(int n, int n2) {
        return this.setChar(n, n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        return this.setShort(n, n2);
    }

    @Override
    public ByteBuf setBoolean(int n, boolean bl) {
        return this.setBoolean(n, bl);
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        return this.setByte(n, n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        return this.getBytes(n, byteBuf, n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        return this.getBytes(n, byteBuf);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray) {
        return this.getBytes(n, byArray);
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        return this.ensureWritable(n);
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return this.discardSomeReadBytes();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return this.discardReadBytes();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return this.resetWriterIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return this.markWriterIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return this.resetReaderIndex();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return this.markReaderIndex();
    }

    @Override
    public ByteBuf clear() {
        return this.clear();
    }

    @Override
    public ByteBuf setIndex(int n, int n2) {
        return this.setIndex(n, n2);
    }

    @Override
    public ByteBuf writerIndex(int n) {
        return this.writerIndex(n);
    }

    @Override
    public ByteBuf readerIndex(int n) {
        return this.readerIndex(n);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        return this.setBytes(n, byteBuffer);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        return this.setBytes(n, byArray, n2, n3);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.setBytes(n, byteBuf, n2, n3);
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        return this.getBytes(n, outputStream, n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        return this.getBytes(n, byteBuffer);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        return this.getBytes(n, byArray, n2, n3);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.getBytes(n, byteBuf, n2, n3);
    }

    @Override
    public ByteBuf capacity(int n) {
        return this.capacity(n);
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

