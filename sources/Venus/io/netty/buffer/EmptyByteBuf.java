/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EmptyByteBuf
extends ByteBuf {
    static final int EMPTY_BYTE_BUF_HASH_CODE = 1;
    private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocateDirect(0);
    private static final long EMPTY_BYTE_BUFFER_ADDRESS;
    private final ByteBufAllocator alloc;
    private final ByteOrder order;
    private final String str;
    private EmptyByteBuf swapped;

    public EmptyByteBuf(ByteBufAllocator byteBufAllocator) {
        this(byteBufAllocator, ByteOrder.BIG_ENDIAN);
    }

    private EmptyByteBuf(ByteBufAllocator byteBufAllocator, ByteOrder byteOrder) {
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        this.alloc = byteBufAllocator;
        this.order = byteOrder;
        this.str = StringUtil.simpleClassName(this) + (byteOrder == ByteOrder.BIG_ENDIAN ? "BE" : "LE");
    }

    @Override
    public int capacity() {
        return 1;
    }

    @Override
    public ByteBuf capacity(int n) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    @Override
    public ByteOrder order() {
        return this.order;
    }

    @Override
    public ByteBuf unwrap() {
        return null;
    }

    @Override
    public ByteBuf asReadOnly() {
        return Unpooled.unmodifiableBuffer((ByteBuf)this);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public boolean isDirect() {
        return false;
    }

    @Override
    public int maxCapacity() {
        return 1;
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        EmptyByteBuf emptyByteBuf = this.swapped;
        if (emptyByteBuf != null) {
            return emptyByteBuf;
        }
        this.swapped = emptyByteBuf = new EmptyByteBuf(this.alloc(), byteOrder);
        return emptyByteBuf;
    }

    @Override
    public int readerIndex() {
        return 1;
    }

    @Override
    public ByteBuf readerIndex(int n) {
        return this.checkIndex(n);
    }

    @Override
    public int writerIndex() {
        return 1;
    }

    @Override
    public ByteBuf writerIndex(int n) {
        return this.checkIndex(n);
    }

    @Override
    public ByteBuf setIndex(int n, int n2) {
        this.checkIndex(n);
        this.checkIndex(n2);
        return this;
    }

    @Override
    public int readableBytes() {
        return 1;
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
    public boolean isReadable() {
        return true;
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public ByteBuf clear() {
        return this;
    }

    @Override
    public ByteBuf markReaderIndex() {
        return this;
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return this;
    }

    @Override
    public ByteBuf markWriterIndex() {
        return this;
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return this;
    }

    @Override
    public ByteBuf discardReadBytes() {
        return this;
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return this;
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("minWritableBytes: " + n + " (expected: >= 0)");
        }
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        if (n < 0) {
            throw new IllegalArgumentException("minWritableBytes: " + n + " (expected: >= 0)");
        }
        if (n == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean getBoolean(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public byte getByte(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public short getUnsignedByte(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public short getShort(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public short getShortLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getUnsignedShort(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getUnsignedShortLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getMedium(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getMediumLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getUnsignedMedium(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getInt(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int getIntLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long getUnsignedInt(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long getUnsignedIntLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long getLong(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long getLongLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public char getChar(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public float getFloat(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public double getDouble(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        return this.checkIndex(n, byteBuf.writableBytes());
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.checkIndex(n, n3);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray) {
        return this.checkIndex(n, byArray.length);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        return this.checkIndex(n, n3);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        return this.checkIndex(n, byteBuffer.remaining());
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        this.checkIndex(n, n2);
        return null;
    }

    @Override
    public ByteBuf setBoolean(int n, boolean bl) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setChar(int n, int n2) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setFloat(int n, float f) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setDouble(int n, double d) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return this.checkIndex(n, n3);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray) {
        return this.checkIndex(n, byArray.length);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        return this.checkIndex(n, n3);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        return this.checkIndex(n, byteBuffer.remaining());
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public boolean readBoolean() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public byte readByte() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public short readUnsignedByte() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public short readShort() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public short readShortLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readUnsignedShort() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readUnsignedShortLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readMedium() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readMediumLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readUnsignedMedium() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readUnsignedMediumLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readInt() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int readIntLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long readUnsignedInt() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long readUnsignedIntLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long readLong() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public long readLongLE() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public char readChar() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public float readFloat() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public double readDouble() {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf readBytes(int n) {
        return this.checkLength(n);
    }

    @Override
    public ByteBuf readSlice(int n) {
        return this.checkLength(n);
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.checkLength(n);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        return this.checkLength(byteBuf.writableBytes());
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        return this.checkLength(n);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        return this.checkLength(n2);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray) {
        return this.checkLength(byArray.length);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        return this.checkLength(n2);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return this.checkLength(byteBuffer.remaining());
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) {
        return this.checkLength(n);
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) {
        this.checkLength(n);
        return 1;
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) {
        this.checkLength(n);
        return 1;
    }

    @Override
    public CharSequence readCharSequence(int n, Charset charset) {
        this.checkLength(n);
        return null;
    }

    @Override
    public ByteBuf skipBytes(int n) {
        return this.checkLength(n);
    }

    @Override
    public ByteBuf writeBoolean(boolean bl) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeByte(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeShort(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeShortLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeMedium(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeMediumLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeInt(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeIntLE(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeLong(long l) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeChar(int n) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeFloat(float f) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeDouble(double d) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return this.checkLength(byteBuf.readableBytes());
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        return this.checkLength(n);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        return this.checkLength(n2);
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray) {
        return this.checkLength(byArray.length);
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        return this.checkLength(n2);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return this.checkLength(byteBuffer.remaining());
    }

    @Override
    public int writeBytes(InputStream inputStream, int n) {
        this.checkLength(n);
        return 1;
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) {
        this.checkLength(n);
        return 1;
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int n) {
        this.checkLength(n);
        return 1;
    }

    @Override
    public ByteBuf writeZero(int n) {
        return this.checkLength(n);
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int indexOf(int n, int n2, byte by) {
        this.checkIndex(n);
        this.checkIndex(n2);
        return 1;
    }

    @Override
    public int bytesBefore(byte by) {
        return 1;
    }

    @Override
    public int bytesBefore(int n, byte by) {
        this.checkLength(n);
        return 1;
    }

    @Override
    public int bytesBefore(int n, int n2, byte by) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return 1;
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return 1;
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        this.checkIndex(n, n2);
        return 1;
    }

    @Override
    public ByteBuf copy() {
        return this;
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public ByteBuf slice() {
        return this;
    }

    @Override
    public ByteBuf retainedSlice() {
        return this;
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.checkIndex(n, n2);
    }

    @Override
    public ByteBuf duplicate() {
        return this;
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this;
    }

    @Override
    public int nioBufferCount() {
        return 0;
    }

    @Override
    public ByteBuffer nioBuffer() {
        return EMPTY_BYTE_BUFFER;
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return this.nioBuffer();
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return new ByteBuffer[]{EMPTY_BYTE_BUFFER};
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        this.checkIndex(n, n2);
        return this.nioBuffers();
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        return EMPTY_BYTE_BUFFER;
    }

    @Override
    public boolean hasArray() {
        return false;
    }

    @Override
    public byte[] array() {
        return EmptyArrays.EMPTY_BYTES;
    }

    @Override
    public int arrayOffset() {
        return 1;
    }

    @Override
    public boolean hasMemoryAddress() {
        return EMPTY_BYTE_BUFFER_ADDRESS != 0L;
    }

    @Override
    public long memoryAddress() {
        if (this.hasMemoryAddress()) {
            return EMPTY_BYTE_BUFFER_ADDRESS;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString(Charset charset) {
        return "";
    }

    @Override
    public String toString(int n, int n2, Charset charset) {
        this.checkIndex(n, n2);
        return this.toString(charset);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ByteBuf && !((ByteBuf)object).isReadable();
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return byteBuf.isReadable() ? -1 : 0;
    }

    @Override
    public String toString() {
        return this.str;
    }

    @Override
    public boolean isReadable(int n) {
        return true;
    }

    @Override
    public boolean isWritable(int n) {
        return true;
    }

    @Override
    public int refCnt() {
        return 0;
    }

    @Override
    public ByteBuf retain() {
        return this;
    }

    @Override
    public ByteBuf retain(int n) {
        return this;
    }

    @Override
    public ByteBuf touch() {
        return this;
    }

    @Override
    public ByteBuf touch(Object object) {
        return this;
    }

    @Override
    public boolean release() {
        return true;
    }

    @Override
    public boolean release(int n) {
        return true;
    }

    private ByteBuf checkIndex(int n) {
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    private ByteBuf checkIndex(int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("length: " + n2);
        }
        if (n != 0 || n2 != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
    }

    private ByteBuf checkLength(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("length: " + n + " (expected: >= 0)");
        }
        if (n != 0) {
            throw new IndexOutOfBoundsException();
        }
        return this;
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
        long l = 0L;
        try {
            if (PlatformDependent.hasUnsafe()) {
                l = PlatformDependent.directBufferAddress(EMPTY_BYTE_BUFFER);
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        EMPTY_BYTE_BUFFER_ADDRESS = l;
    }
}

