/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledDuplicatedByteBuf;
import io.netty.buffer.UnpooledSlicedByteBuf;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public abstract class AbstractByteBuf
extends ByteBuf {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractByteBuf.class);
    private static final String PROP_MODE = "io.netty.buffer.bytebuf.checkAccessible";
    private static final boolean checkAccessible = SystemPropertyUtil.getBoolean("io.netty.buffer.bytebuf.checkAccessible", true);
    static final ResourceLeakDetector<ByteBuf> leakDetector;
    int readerIndex;
    int writerIndex;
    private int markedReaderIndex;
    private int markedWriterIndex;
    private int maxCapacity;

    protected AbstractByteBuf(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("maxCapacity: " + n + " (expected: >= 0)");
        }
        this.maxCapacity = n;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public ByteBuf asReadOnly() {
        if (this.isReadOnly()) {
            return this;
        }
        return Unpooled.unmodifiableBuffer((ByteBuf)this);
    }

    @Override
    public int maxCapacity() {
        return this.maxCapacity;
    }

    protected final void maxCapacity(int n) {
        this.maxCapacity = n;
    }

    @Override
    public int readerIndex() {
        return this.readerIndex;
    }

    @Override
    public ByteBuf readerIndex(int n) {
        if (n < 0 || n > this.writerIndex) {
            throw new IndexOutOfBoundsException(String.format("readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", n, this.writerIndex));
        }
        this.readerIndex = n;
        return this;
    }

    @Override
    public int writerIndex() {
        return this.writerIndex;
    }

    @Override
    public ByteBuf writerIndex(int n) {
        if (n < this.readerIndex || n > this.capacity()) {
            throw new IndexOutOfBoundsException(String.format("writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))", n, this.readerIndex, this.capacity()));
        }
        this.writerIndex = n;
        return this;
    }

    @Override
    public ByteBuf setIndex(int n, int n2) {
        if (n < 0 || n > n2 || n2 > this.capacity()) {
            throw new IndexOutOfBoundsException(String.format("readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))", n, n2, this.capacity()));
        }
        this.setIndex0(n, n2);
        return this;
    }

    @Override
    public ByteBuf clear() {
        this.writerIndex = 0;
        this.readerIndex = 0;
        return this;
    }

    @Override
    public boolean isReadable() {
        return this.writerIndex > this.readerIndex;
    }

    @Override
    public boolean isReadable(int n) {
        return this.writerIndex - this.readerIndex >= n;
    }

    @Override
    public boolean isWritable() {
        return this.capacity() > this.writerIndex;
    }

    @Override
    public boolean isWritable(int n) {
        return this.capacity() - this.writerIndex >= n;
    }

    @Override
    public int readableBytes() {
        return this.writerIndex - this.readerIndex;
    }

    @Override
    public int writableBytes() {
        return this.capacity() - this.writerIndex;
    }

    @Override
    public int maxWritableBytes() {
        return this.maxCapacity() - this.writerIndex;
    }

    @Override
    public ByteBuf markReaderIndex() {
        this.markedReaderIndex = this.readerIndex;
        return this;
    }

    @Override
    public ByteBuf resetReaderIndex() {
        this.readerIndex(this.markedReaderIndex);
        return this;
    }

    @Override
    public ByteBuf markWriterIndex() {
        this.markedWriterIndex = this.writerIndex;
        return this;
    }

    @Override
    public ByteBuf resetWriterIndex() {
        this.writerIndex(this.markedWriterIndex);
        return this;
    }

    @Override
    public ByteBuf discardReadBytes() {
        this.ensureAccessible();
        if (this.readerIndex == 0) {
            return this;
        }
        if (this.readerIndex != this.writerIndex) {
            this.setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
            this.writerIndex -= this.readerIndex;
            this.adjustMarkers(this.readerIndex);
            this.readerIndex = 0;
        } else {
            this.adjustMarkers(this.readerIndex);
            this.readerIndex = 0;
            this.writerIndex = 0;
        }
        return this;
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        this.ensureAccessible();
        if (this.readerIndex == 0) {
            return this;
        }
        if (this.readerIndex == this.writerIndex) {
            this.adjustMarkers(this.readerIndex);
            this.readerIndex = 0;
            this.writerIndex = 0;
            return this;
        }
        if (this.readerIndex >= this.capacity() >>> 1) {
            this.setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
            this.writerIndex -= this.readerIndex;
            this.adjustMarkers(this.readerIndex);
            this.readerIndex = 0;
        }
        return this;
    }

    protected final void adjustMarkers(int n) {
        int n2 = this.markedReaderIndex;
        if (n2 <= n) {
            this.markedReaderIndex = 0;
            int n3 = this.markedWriterIndex;
            this.markedWriterIndex = n3 <= n ? 0 : n3 - n;
        } else {
            this.markedReaderIndex = n2 - n;
            this.markedWriterIndex -= n;
        }
    }

    @Override
    public ByteBuf ensureWritable(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", n));
        }
        this.ensureWritable0(n);
        return this;
    }

    final void ensureWritable0(int n) {
        this.ensureAccessible();
        if (n <= this.writableBytes()) {
            return;
        }
        if (n > this.maxCapacity - this.writerIndex) {
            throw new IndexOutOfBoundsException(String.format("writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s", this.writerIndex, n, this.maxCapacity, this));
        }
        int n2 = this.alloc().calculateNewCapacity(this.writerIndex + n, this.maxCapacity);
        this.capacity(n2);
    }

    @Override
    public int ensureWritable(int n, boolean bl) {
        int n2;
        this.ensureAccessible();
        if (n < 0) {
            throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", n));
        }
        if (n <= this.writableBytes()) {
            return 1;
        }
        int n3 = this.maxCapacity();
        if (n > n3 - (n2 = this.writerIndex())) {
            if (!bl || this.capacity() == n3) {
                return 0;
            }
            this.capacity(n3);
            return 0;
        }
        int n4 = this.alloc().calculateNewCapacity(n2 + n, n3);
        this.capacity(n4);
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
        return this.newSwappedByteBuf();
    }

    protected SwappedByteBuf newSwappedByteBuf() {
        return new SwappedByteBuf(this);
    }

    @Override
    public byte getByte(int n) {
        this.checkIndex(n);
        return this._getByte(n);
    }

    protected abstract byte _getByte(int var1);

    @Override
    public boolean getBoolean(int n) {
        return this.getByte(n) != 0;
    }

    @Override
    public short getUnsignedByte(int n) {
        return (short)(this.getByte(n) & 0xFF);
    }

    @Override
    public short getShort(int n) {
        this.checkIndex(n, 2);
        return this._getShort(n);
    }

    protected abstract short _getShort(int var1);

    @Override
    public short getShortLE(int n) {
        this.checkIndex(n, 2);
        return this._getShortLE(n);
    }

    protected abstract short _getShortLE(int var1);

    @Override
    public int getUnsignedShort(int n) {
        return this.getShort(n) & 0xFFFF;
    }

    @Override
    public int getUnsignedShortLE(int n) {
        return this.getShortLE(n) & 0xFFFF;
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.checkIndex(n, 3);
        return this._getUnsignedMedium(n);
    }

    protected abstract int _getUnsignedMedium(int var1);

    @Override
    public int getUnsignedMediumLE(int n) {
        this.checkIndex(n, 3);
        return this._getUnsignedMediumLE(n);
    }

    protected abstract int _getUnsignedMediumLE(int var1);

    @Override
    public int getMedium(int n) {
        int n2 = this.getUnsignedMedium(n);
        if ((n2 & 0x800000) != 0) {
            n2 |= 0xFF000000;
        }
        return n2;
    }

    @Override
    public int getMediumLE(int n) {
        int n2 = this.getUnsignedMediumLE(n);
        if ((n2 & 0x800000) != 0) {
            n2 |= 0xFF000000;
        }
        return n2;
    }

    @Override
    public int getInt(int n) {
        this.checkIndex(n, 4);
        return this._getInt(n);
    }

    protected abstract int _getInt(int var1);

    @Override
    public int getIntLE(int n) {
        this.checkIndex(n, 4);
        return this._getIntLE(n);
    }

    protected abstract int _getIntLE(int var1);

    @Override
    public long getUnsignedInt(int n) {
        return (long)this.getInt(n) & 0xFFFFFFFFL;
    }

    @Override
    public long getUnsignedIntLE(int n) {
        return (long)this.getIntLE(n) & 0xFFFFFFFFL;
    }

    @Override
    public long getLong(int n) {
        this.checkIndex(n, 8);
        return this._getLong(n);
    }

    protected abstract long _getLong(int var1);

    @Override
    public long getLongLE(int n) {
        this.checkIndex(n, 8);
        return this._getLongLE(n);
    }

    protected abstract long _getLongLE(int var1);

    @Override
    public char getChar(int n) {
        return (char)this.getShort(n);
    }

    @Override
    public float getFloat(int n) {
        return Float.intBitsToFloat(this.getInt(n));
    }

    @Override
    public double getDouble(int n) {
        return Double.longBitsToDouble(this.getLong(n));
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray) {
        this.getBytes(n, byArray, 0, byArray.length);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf) {
        this.getBytes(n, byteBuf, byteBuf.writableBytes());
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        this.getBytes(n, byteBuf, byteBuf.writerIndex(), n2);
        byteBuf.writerIndex(byteBuf.writerIndex() + n2);
        return this;
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        return this.toString(n, n2, charset);
    }

    @Override
    public CharSequence readCharSequence(int n, Charset charset) {
        CharSequence charSequence = this.getCharSequence(this.readerIndex, n, charset);
        this.readerIndex += n;
        return charSequence;
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.checkIndex(n);
        this._setByte(n, n2);
        return this;
    }

    protected abstract void _setByte(int var1, int var2);

    @Override
    public ByteBuf setBoolean(int n, boolean bl) {
        this.setByte(n, bl ? 1 : 0);
        return this;
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.checkIndex(n, 2);
        this._setShort(n, n2);
        return this;
    }

    protected abstract void _setShort(int var1, int var2);

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        this.checkIndex(n, 2);
        this._setShortLE(n, n2);
        return this;
    }

    protected abstract void _setShortLE(int var1, int var2);

    @Override
    public ByteBuf setChar(int n, int n2) {
        this.setShort(n, n2);
        return this;
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.checkIndex(n, 3);
        this._setMedium(n, n2);
        return this;
    }

    protected abstract void _setMedium(int var1, int var2);

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        this.checkIndex(n, 3);
        this._setMediumLE(n, n2);
        return this;
    }

    protected abstract void _setMediumLE(int var1, int var2);

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.checkIndex(n, 4);
        this._setInt(n, n2);
        return this;
    }

    protected abstract void _setInt(int var1, int var2);

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        this.checkIndex(n, 4);
        this._setIntLE(n, n2);
        return this;
    }

    protected abstract void _setIntLE(int var1, int var2);

    @Override
    public ByteBuf setFloat(int n, float f) {
        this.setInt(n, Float.floatToRawIntBits(f));
        return this;
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.checkIndex(n, 8);
        this._setLong(n, l);
        return this;
    }

    protected abstract void _setLong(int var1, long var2);

    @Override
    public ByteBuf setLongLE(int n, long l) {
        this.checkIndex(n, 8);
        this._setLongLE(n, l);
        return this;
    }

    protected abstract void _setLongLE(int var1, long var2);

    @Override
    public ByteBuf setDouble(int n, double d) {
        this.setLong(n, Double.doubleToRawLongBits(d));
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray) {
        this.setBytes(n, byArray, 0, byArray.length);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf) {
        this.setBytes(n, byteBuf, byteBuf.readableBytes());
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        this.checkIndex(n, n2);
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (n2 > byteBuf.readableBytes()) {
            throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", n2, byteBuf.readableBytes(), byteBuf));
        }
        this.setBytes(n, byteBuf, byteBuf.readerIndex(), n2);
        byteBuf.readerIndex(byteBuf.readerIndex() + n2);
        return this;
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        int n3;
        if (n2 == 0) {
            return this;
        }
        this.checkIndex(n, n2);
        int n4 = n2 >>> 3;
        int n5 = n2 & 7;
        for (n3 = n4; n3 > 0; --n3) {
            this._setLong(n, 0L);
            n += 8;
        }
        if (n5 == 4) {
            this._setInt(n, 0);
        } else if (n5 < 4) {
            for (n3 = n5; n3 > 0; --n3) {
                this._setByte(n, 0);
                ++n;
            }
        } else {
            this._setInt(n, 0);
            n += 4;
            for (n3 = n5 - 4; n3 > 0; --n3) {
                this._setByte(n, 0);
                ++n;
            }
        }
        return this;
    }

    @Override
    public int setCharSequence(int n, CharSequence charSequence, Charset charset) {
        return this.setCharSequence0(n, charSequence, charset, false);
    }

    private int setCharSequence0(int n, CharSequence charSequence, Charset charset, boolean bl) {
        if (charset.equals(CharsetUtil.UTF_8)) {
            int n2 = ByteBufUtil.utf8MaxBytes(charSequence);
            if (bl) {
                this.ensureWritable0(n2);
                this.checkIndex0(n, n2);
            } else {
                this.checkIndex(n, n2);
            }
            return ByteBufUtil.writeUtf8(this, n, charSequence, charSequence.length());
        }
        if (charset.equals(CharsetUtil.US_ASCII) || charset.equals(CharsetUtil.ISO_8859_1)) {
            int n3 = charSequence.length();
            if (bl) {
                this.ensureWritable0(n3);
                this.checkIndex0(n, n3);
            } else {
                this.checkIndex(n, n3);
            }
            return ByteBufUtil.writeAscii(this, n, charSequence, n3);
        }
        byte[] byArray = charSequence.toString().getBytes(charset);
        if (bl) {
            this.ensureWritable0(byArray.length);
        }
        this.setBytes(n, byArray);
        return byArray.length;
    }

    @Override
    public byte readByte() {
        this.checkReadableBytes0(1);
        int n = this.readerIndex;
        byte by = this._getByte(n);
        this.readerIndex = n + 1;
        return by;
    }

    @Override
    public boolean readBoolean() {
        return this.readByte() != 0;
    }

    @Override
    public short readUnsignedByte() {
        return (short)(this.readByte() & 0xFF);
    }

    @Override
    public short readShort() {
        this.checkReadableBytes0(2);
        short s = this._getShort(this.readerIndex);
        this.readerIndex += 2;
        return s;
    }

    @Override
    public short readShortLE() {
        this.checkReadableBytes0(2);
        short s = this._getShortLE(this.readerIndex);
        this.readerIndex += 2;
        return s;
    }

    @Override
    public int readUnsignedShort() {
        return this.readShort() & 0xFFFF;
    }

    @Override
    public int readUnsignedShortLE() {
        return this.readShortLE() & 0xFFFF;
    }

    @Override
    public int readMedium() {
        int n = this.readUnsignedMedium();
        if ((n & 0x800000) != 0) {
            n |= 0xFF000000;
        }
        return n;
    }

    @Override
    public int readMediumLE() {
        int n = this.readUnsignedMediumLE();
        if ((n & 0x800000) != 0) {
            n |= 0xFF000000;
        }
        return n;
    }

    @Override
    public int readUnsignedMedium() {
        this.checkReadableBytes0(3);
        int n = this._getUnsignedMedium(this.readerIndex);
        this.readerIndex += 3;
        return n;
    }

    @Override
    public int readUnsignedMediumLE() {
        this.checkReadableBytes0(3);
        int n = this._getUnsignedMediumLE(this.readerIndex);
        this.readerIndex += 3;
        return n;
    }

    @Override
    public int readInt() {
        this.checkReadableBytes0(4);
        int n = this._getInt(this.readerIndex);
        this.readerIndex += 4;
        return n;
    }

    @Override
    public int readIntLE() {
        this.checkReadableBytes0(4);
        int n = this._getIntLE(this.readerIndex);
        this.readerIndex += 4;
        return n;
    }

    @Override
    public long readUnsignedInt() {
        return (long)this.readInt() & 0xFFFFFFFFL;
    }

    @Override
    public long readUnsignedIntLE() {
        return (long)this.readIntLE() & 0xFFFFFFFFL;
    }

    @Override
    public long readLong() {
        this.checkReadableBytes0(8);
        long l = this._getLong(this.readerIndex);
        this.readerIndex += 8;
        return l;
    }

    @Override
    public long readLongLE() {
        this.checkReadableBytes0(8);
        long l = this._getLongLE(this.readerIndex);
        this.readerIndex += 8;
        return l;
    }

    @Override
    public char readChar() {
        return (char)this.readShort();
    }

    @Override
    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public ByteBuf readBytes(int n) {
        this.checkReadableBytes(n);
        if (n == 0) {
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBuf byteBuf = this.alloc().buffer(n, this.maxCapacity);
        byteBuf.writeBytes(this, this.readerIndex, n);
        this.readerIndex += n;
        return byteBuf;
    }

    @Override
    public ByteBuf readSlice(int n) {
        this.checkReadableBytes(n);
        ByteBuf byteBuf = this.slice(this.readerIndex, n);
        this.readerIndex += n;
        return byteBuf;
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        this.checkReadableBytes(n);
        ByteBuf byteBuf = this.retainedSlice(this.readerIndex, n);
        this.readerIndex += n;
        return byteBuf;
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, byArray, n, n2);
        this.readerIndex += n2;
        return this;
    }

    @Override
    public ByteBuf readBytes(byte[] byArray) {
        this.readBytes(byArray, 0, byArray.length);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        this.readBytes(byteBuf, byteBuf.writableBytes());
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n) {
        if (n > byteBuf.writableBytes()) {
            throw new IndexOutOfBoundsException(String.format("length(%d) exceeds dst.writableBytes(%d) where dst is: %s", n, byteBuf.writableBytes(), byteBuf));
        }
        this.readBytes(byteBuf, byteBuf.writerIndex(), n);
        byteBuf.writerIndex(byteBuf.writerIndex() + n);
        return this;
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, byteBuf, n, n2);
        this.readerIndex += n2;
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
    public int readBytes(GatheringByteChannel gatheringByteChannel, int n) throws IOException {
        this.checkReadableBytes(n);
        int n2 = this.getBytes(this.readerIndex, gatheringByteChannel, n);
        this.readerIndex += n2;
        return n2;
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int n) throws IOException {
        this.checkReadableBytes(n);
        int n2 = this.getBytes(this.readerIndex, fileChannel, l, n);
        this.readerIndex += n2;
        return n2;
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, outputStream, n);
        this.readerIndex += n;
        return this;
    }

    @Override
    public ByteBuf skipBytes(int n) {
        this.checkReadableBytes(n);
        this.readerIndex += n;
        return this;
    }

    @Override
    public ByteBuf writeBoolean(boolean bl) {
        this.writeByte(bl ? 1 : 0);
        return this;
    }

    @Override
    public ByteBuf writeByte(int n) {
        this.ensureWritable0(1);
        this._setByte(this.writerIndex++, n);
        return this;
    }

    @Override
    public ByteBuf writeShort(int n) {
        this.ensureWritable0(2);
        this._setShort(this.writerIndex, n);
        this.writerIndex += 2;
        return this;
    }

    @Override
    public ByteBuf writeShortLE(int n) {
        this.ensureWritable0(2);
        this._setShortLE(this.writerIndex, n);
        this.writerIndex += 2;
        return this;
    }

    @Override
    public ByteBuf writeMedium(int n) {
        this.ensureWritable0(3);
        this._setMedium(this.writerIndex, n);
        this.writerIndex += 3;
        return this;
    }

    @Override
    public ByteBuf writeMediumLE(int n) {
        this.ensureWritable0(3);
        this._setMediumLE(this.writerIndex, n);
        this.writerIndex += 3;
        return this;
    }

    @Override
    public ByteBuf writeInt(int n) {
        this.ensureWritable0(4);
        this._setInt(this.writerIndex, n);
        this.writerIndex += 4;
        return this;
    }

    @Override
    public ByteBuf writeIntLE(int n) {
        this.ensureWritable0(4);
        this._setIntLE(this.writerIndex, n);
        this.writerIndex += 4;
        return this;
    }

    @Override
    public ByteBuf writeLong(long l) {
        this.ensureWritable0(8);
        this._setLong(this.writerIndex, l);
        this.writerIndex += 8;
        return this;
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        this.ensureWritable0(8);
        this._setLongLE(this.writerIndex, l);
        this.writerIndex += 8;
        return this;
    }

    @Override
    public ByteBuf writeChar(int n) {
        this.writeShort(n);
        return this;
    }

    @Override
    public ByteBuf writeFloat(float f) {
        this.writeInt(Float.floatToRawIntBits(f));
        return this;
    }

    @Override
    public ByteBuf writeDouble(double d) {
        this.writeLong(Double.doubleToRawLongBits(d));
        return this;
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray, int n, int n2) {
        this.ensureWritable(n2);
        this.setBytes(this.writerIndex, byArray, n, n2);
        this.writerIndex += n2;
        return this;
    }

    @Override
    public ByteBuf writeBytes(byte[] byArray) {
        this.writeBytes(byArray, 0, byArray.length);
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        this.writeBytes(byteBuf, byteBuf.readableBytes());
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n) {
        if (n > byteBuf.readableBytes()) {
            throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", n, byteBuf.readableBytes(), byteBuf));
        }
        this.writeBytes(byteBuf, byteBuf.readerIndex(), n);
        byteBuf.readerIndex(byteBuf.readerIndex() + n);
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        this.ensureWritable(n2);
        this.setBytes(this.writerIndex, byteBuf, n, n2);
        this.writerIndex += n2;
        return this;
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        int n = byteBuffer.remaining();
        this.ensureWritable0(n);
        this.setBytes(this.writerIndex, byteBuffer);
        this.writerIndex += n;
        return this;
    }

    @Override
    public int writeBytes(InputStream inputStream, int n) throws IOException {
        this.ensureWritable(n);
        int n2 = this.setBytes(this.writerIndex, inputStream, n);
        if (n2 > 0) {
            this.writerIndex += n2;
        }
        return n2;
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int n) throws IOException {
        this.ensureWritable(n);
        int n2 = this.setBytes(this.writerIndex, scatteringByteChannel, n);
        if (n2 > 0) {
            this.writerIndex += n2;
        }
        return n2;
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int n) throws IOException {
        this.ensureWritable(n);
        int n2 = this.setBytes(this.writerIndex, fileChannel, l, n);
        if (n2 > 0) {
            this.writerIndex += n2;
        }
        return n2;
    }

    @Override
    public ByteBuf writeZero(int n) {
        int n2;
        if (n == 0) {
            return this;
        }
        this.ensureWritable(n);
        int n3 = this.writerIndex;
        this.checkIndex0(n3, n);
        int n4 = n >>> 3;
        int n5 = n & 7;
        for (n2 = n4; n2 > 0; --n2) {
            this._setLong(n3, 0L);
            n3 += 8;
        }
        if (n5 == 4) {
            this._setInt(n3, 0);
            n3 += 4;
        } else if (n5 < 4) {
            for (n2 = n5; n2 > 0; --n2) {
                this._setByte(n3, 0);
                ++n3;
            }
        } else {
            this._setInt(n3, 0);
            n3 += 4;
            for (n2 = n5 - 4; n2 > 0; --n2) {
                this._setByte(n3, 0);
                ++n3;
            }
        }
        this.writerIndex = n3;
        return this;
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        int n = this.setCharSequence0(this.writerIndex, charSequence, charset, true);
        this.writerIndex += n;
        return n;
    }

    @Override
    public ByteBuf copy() {
        return this.copy(this.readerIndex, this.readableBytes());
    }

    @Override
    public ByteBuf duplicate() {
        this.ensureAccessible();
        return new UnpooledDuplicatedByteBuf(this);
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.duplicate().retain();
    }

    @Override
    public ByteBuf slice() {
        return this.slice(this.readerIndex, this.readableBytes());
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.slice().retain();
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        this.ensureAccessible();
        return new UnpooledSlicedByteBuf(this, n, n2);
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.slice(n, n2).retain();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return this.nioBuffer(this.readerIndex, this.readableBytes());
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return this.nioBuffers(this.readerIndex, this.readableBytes());
    }

    @Override
    public String toString(Charset charset) {
        return this.toString(this.readerIndex, this.readableBytes(), charset);
    }

    @Override
    public String toString(int n, int n2, Charset charset) {
        return ByteBufUtil.decodeString(this, n, n2, charset);
    }

    @Override
    public int indexOf(int n, int n2, byte by) {
        return ByteBufUtil.indexOf(this, n, n2, by);
    }

    @Override
    public int bytesBefore(byte by) {
        return this.bytesBefore(this.readerIndex(), this.readableBytes(), by);
    }

    @Override
    public int bytesBefore(int n, byte by) {
        this.checkReadableBytes(n);
        return this.bytesBefore(this.readerIndex(), n, by);
    }

    @Override
    public int bytesBefore(int n, int n2, byte by) {
        int n3 = this.indexOf(n, n + n2, by);
        if (n3 < 0) {
            return 1;
        }
        return n3 - n;
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        this.ensureAccessible();
        try {
            return this.forEachByteAsc0(this.readerIndex, this.writerIndex, byteProcessor);
        } catch (Exception exception) {
            PlatformDependent.throwException(exception);
            return 1;
        }
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        this.checkIndex(n, n2);
        try {
            return this.forEachByteAsc0(n, n + n2, byteProcessor);
        } catch (Exception exception) {
            PlatformDependent.throwException(exception);
            return 1;
        }
    }

    private int forEachByteAsc0(int n, int n2, ByteProcessor byteProcessor) throws Exception {
        while (n < n2) {
            if (!byteProcessor.process(this._getByte(n))) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        this.ensureAccessible();
        try {
            return this.forEachByteDesc0(this.writerIndex - 1, this.readerIndex, byteProcessor);
        } catch (Exception exception) {
            PlatformDependent.throwException(exception);
            return 1;
        }
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        this.checkIndex(n, n2);
        try {
            return this.forEachByteDesc0(n + n2 - 1, n, byteProcessor);
        } catch (Exception exception) {
            PlatformDependent.throwException(exception);
            return 1;
        }
    }

    private int forEachByteDesc0(int n, int n2, ByteProcessor byteProcessor) throws Exception {
        while (n >= n2) {
            if (!byteProcessor.process(this._getByte(n))) {
                return n;
            }
            --n;
        }
        return 1;
    }

    @Override
    public int hashCode() {
        return ByteBufUtil.hashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof ByteBuf && ByteBufUtil.equals(this, (ByteBuf)object);
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return ByteBufUtil.compare(this, byteBuf);
    }

    @Override
    public String toString() {
        ByteBuf byteBuf;
        if (this.refCnt() == 0) {
            return StringUtil.simpleClassName(this) + "(freed)";
        }
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(ridx: ").append(this.readerIndex).append(", widx: ").append(this.writerIndex).append(", cap: ").append(this.capacity());
        if (this.maxCapacity != Integer.MAX_VALUE) {
            stringBuilder.append('/').append(this.maxCapacity);
        }
        if ((byteBuf = this.unwrap()) != null) {
            stringBuilder.append(", unwrapped: ").append(byteBuf);
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    protected final void checkIndex(int n) {
        this.checkIndex(n, 1);
    }

    protected final void checkIndex(int n, int n2) {
        this.ensureAccessible();
        this.checkIndex0(n, n2);
    }

    final void checkIndex0(int n, int n2) {
        if (MathUtil.isOutOfBounds(n, n2, this.capacity())) {
            throw new IndexOutOfBoundsException(String.format("index: %d, length: %d (expected: range(0, %d))", n, n2, this.capacity()));
        }
    }

    protected final void checkSrcIndex(int n, int n2, int n3, int n4) {
        this.checkIndex(n, n2);
        if (MathUtil.isOutOfBounds(n3, n2, n4)) {
            throw new IndexOutOfBoundsException(String.format("srcIndex: %d, length: %d (expected: range(0, %d))", n3, n2, n4));
        }
    }

    protected final void checkDstIndex(int n, int n2, int n3, int n4) {
        this.checkIndex(n, n2);
        if (MathUtil.isOutOfBounds(n3, n2, n4)) {
            throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", n3, n2, n4));
        }
    }

    protected final void checkReadableBytes(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("minimumReadableBytes: " + n + " (expected: >= 0)");
        }
        this.checkReadableBytes0(n);
    }

    protected final void checkNewCapacity(int n) {
        this.ensureAccessible();
        if (n < 0 || n > this.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + n + " (expected: 0-" + this.maxCapacity() + ')');
        }
    }

    private void checkReadableBytes0(int n) {
        this.ensureAccessible();
        if (this.readerIndex > this.writerIndex - n) {
            throw new IndexOutOfBoundsException(String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", this.readerIndex, n, this.writerIndex, this));
        }
    }

    protected final void ensureAccessible() {
        if (checkAccessible && this.refCnt() == 0) {
            throw new IllegalReferenceCountException(0);
        }
    }

    final void setIndex0(int n, int n2) {
        this.readerIndex = n;
        this.writerIndex = n2;
    }

    final void discardMarks() {
        this.markedWriterIndex = 0;
        this.markedReaderIndex = 0;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ByteBuf)object);
    }

    static {
        if (logger.isDebugEnabled()) {
            logger.debug("-D{}: {}", (Object)PROP_MODE, (Object)checkAccessible);
        }
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ByteBuf.class);
    }
}

