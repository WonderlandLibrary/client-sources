/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
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

public class UnpooledDirectByteBuf
extends AbstractReferenceCountedByteBuf {
    private final ByteBufAllocator alloc;
    private ByteBuffer buffer;
    private ByteBuffer tmpNioBuf;
    private int capacity;
    private boolean doNotFree;

    public UnpooledDirectByteBuf(ByteBufAllocator byteBufAllocator, int n, int n2) {
        super(n2);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        if (n < 0) {
            throw new IllegalArgumentException("initialCapacity: " + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("maxCapacity: " + n2);
        }
        if (n > n2) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", n, n2));
        }
        this.alloc = byteBufAllocator;
        this.setByteBuffer(ByteBuffer.allocateDirect(n));
    }

    protected UnpooledDirectByteBuf(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int n) {
        super(n);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        if (byteBuffer == null) {
            throw new NullPointerException("initialBuffer");
        }
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("initialBuffer is not a direct buffer.");
        }
        if (byteBuffer.isReadOnly()) {
            throw new IllegalArgumentException("initialBuffer is a read-only buffer.");
        }
        int n2 = byteBuffer.remaining();
        if (n2 > n) {
            throw new IllegalArgumentException(String.format("initialCapacity(%d) > maxCapacity(%d)", n2, n));
        }
        this.alloc = byteBufAllocator;
        this.doNotFree = true;
        this.setByteBuffer(byteBuffer.slice().order(ByteOrder.BIG_ENDIAN));
        this.writerIndex(n2);
    }

    protected ByteBuffer allocateDirect(int n) {
        return ByteBuffer.allocateDirect(n);
    }

    protected void freeDirect(ByteBuffer byteBuffer) {
        PlatformDependent.freeDirectBuffer(byteBuffer);
    }

    private void setByteBuffer(ByteBuffer byteBuffer) {
        ByteBuffer byteBuffer2 = this.buffer;
        if (byteBuffer2 != null) {
            if (this.doNotFree) {
                this.doNotFree = false;
            } else {
                this.freeDirect(byteBuffer2);
            }
        }
        this.buffer = byteBuffer;
        this.tmpNioBuf = null;
        this.capacity = byteBuffer.remaining();
    }

    @Override
    public boolean isDirect() {
        return false;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public ByteBuf capacity(int n) {
        this.checkNewCapacity(n);
        int n2 = this.readerIndex();
        int n3 = this.writerIndex();
        int n4 = this.capacity;
        if (n > n4) {
            ByteBuffer byteBuffer = this.buffer;
            ByteBuffer byteBuffer2 = this.allocateDirect(n);
            byteBuffer.position(0).limit(byteBuffer.capacity());
            byteBuffer2.position(0).limit(byteBuffer.capacity());
            byteBuffer2.put(byteBuffer);
            byteBuffer2.clear();
            this.setByteBuffer(byteBuffer2);
        } else if (n < n4) {
            ByteBuffer byteBuffer = this.buffer;
            ByteBuffer byteBuffer3 = this.allocateDirect(n);
            if (n2 < n) {
                if (n3 > n) {
                    n3 = n;
                    this.writerIndex(n3);
                }
                byteBuffer.position(n2).limit(n3);
                byteBuffer3.position(n2).limit(n3);
                byteBuffer3.put(byteBuffer);
                byteBuffer3.clear();
            } else {
                this.setIndex(n, n);
            }
            this.setByteBuffer(byteBuffer3);
        }
        return this;
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
    public boolean hasArray() {
        return true;
    }

    @Override
    public byte[] array() {
        throw new UnsupportedOperationException("direct buffer");
    }

    @Override
    public int arrayOffset() {
        throw new UnsupportedOperationException("direct buffer");
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
    public byte getByte(int n) {
        this.ensureAccessible();
        return this._getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        return this.buffer.get(n);
    }

    @Override
    public short getShort(int n) {
        this.ensureAccessible();
        return this._getShort(n);
    }

    @Override
    protected short _getShort(int n) {
        return this.buffer.getShort(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return ByteBufUtil.swapShort(this.buffer.getShort(n));
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.ensureAccessible();
        return this._getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return (this.getByte(n) & 0xFF) << 16 | (this.getByte(n + 1) & 0xFF) << 8 | this.getByte(n + 2) & 0xFF;
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.getByte(n) & 0xFF | (this.getByte(n + 1) & 0xFF) << 8 | (this.getByte(n + 2) & 0xFF) << 16;
    }

    @Override
    public int getInt(int n) {
        this.ensureAccessible();
        return this._getInt(n);
    }

    @Override
    protected int _getInt(int n) {
        return this.buffer.getInt(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return ByteBufUtil.swapInt(this.buffer.getInt(n));
    }

    @Override
    public long getLong(int n) {
        this.ensureAccessible();
        return this._getLong(n);
    }

    @Override
    protected long _getLong(int n) {
        return this.buffer.getLong(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return ByteBufUtil.swapLong(this.buffer.getLong(n));
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.hasArray()) {
            this.getBytes(n, byteBuf.array(), byteBuf.arrayOffset() + n2, n3);
        } else if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(n2, n3)) {
                int n4 = byteBuffer.remaining();
                this.getBytes(n, byteBuffer);
                n += n4;
            }
        } else {
            byteBuf.setBytes(n2, this, n, n3);
        }
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.getBytes(n, byArray, n2, n3, false);
        return this;
    }

    private void getBytes(int n, byte[] byArray, int n2, int n3, boolean bl) {
        this.checkDstIndex(n, n3, n2, byArray.length);
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : this.buffer.duplicate();
        byteBuffer.clear().position(n).limit(n + n3);
        byteBuffer.get(byArray, n2, n3);
    }

    @Override
    public ByteBuf readBytes(byte[] byArray, int n, int n2) {
        this.checkReadableBytes(n2);
        this.getBytes(this.readerIndex, byArray, n, n2, true);
        this.readerIndex += n2;
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        this.getBytes(n, byteBuffer, false);
        return this;
    }

    private void getBytes(int n, ByteBuffer byteBuffer, boolean bl) {
        this.checkIndex(n, byteBuffer.remaining());
        ByteBuffer byteBuffer2 = bl ? this.internalNioBuffer() : this.buffer.duplicate();
        byteBuffer2.clear().position(n).limit(n + byteBuffer.remaining());
        byteBuffer.put(byteBuffer2);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        int n = byteBuffer.remaining();
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, byteBuffer, true);
        this.readerIndex += n;
        return this;
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.ensureAccessible();
        this._setByte(n, n2);
        return this;
    }

    @Override
    protected void _setByte(int n, int n2) {
        this.buffer.put(n, (byte)n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.ensureAccessible();
        this._setShort(n, n2);
        return this;
    }

    @Override
    protected void _setShort(int n, int n2) {
        this.buffer.putShort(n, (short)n2);
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        this.buffer.putShort(n, ByteBufUtil.swapShort((short)n2));
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.ensureAccessible();
        this._setMedium(n, n2);
        return this;
    }

    @Override
    protected void _setMedium(int n, int n2) {
        this.setByte(n, (byte)(n2 >>> 16));
        this.setByte(n + 1, (byte)(n2 >>> 8));
        this.setByte(n + 2, (byte)n2);
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        this.setByte(n, (byte)n2);
        this.setByte(n + 1, (byte)(n2 >>> 8));
        this.setByte(n + 2, (byte)(n2 >>> 16));
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.ensureAccessible();
        this._setInt(n, n2);
        return this;
    }

    @Override
    protected void _setInt(int n, int n2) {
        this.buffer.putInt(n, n2);
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        this.buffer.putInt(n, ByteBufUtil.swapInt(n2));
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.ensureAccessible();
        this._setLong(n, l);
        return this;
    }

    @Override
    protected void _setLong(int n, long l) {
        this.buffer.putLong(n, l);
    }

    @Override
    protected void _setLongLE(int n, long l) {
        this.buffer.putLong(n, ByteBufUtil.swapLong(l));
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (byteBuf.nioBufferCount() > 0) {
            for (ByteBuffer byteBuffer : byteBuf.nioBuffers(n2, n3)) {
                int n4 = byteBuffer.remaining();
                this.setBytes(n, byteBuffer);
                n += n4;
            }
        } else {
            byteBuf.getBytes(n2, this, n, n3);
        }
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byArray.length);
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n3);
        byteBuffer.put(byArray, n2, n3);
        return this;
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        this.ensureAccessible();
        ByteBuffer byteBuffer2 = this.internalNioBuffer();
        if (byteBuffer == byteBuffer2) {
            byteBuffer = byteBuffer.duplicate();
        }
        byteBuffer2.clear().position(n).limit(n + byteBuffer.remaining());
        byteBuffer2.put(byteBuffer);
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.getBytes(n, outputStream, n2, false);
        return this;
    }

    private void getBytes(int n, OutputStream outputStream, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return;
        }
        ByteBufUtil.readBytes(this.alloc(), bl ? this.internalNioBuffer() : this.buffer.duplicate(), n, n2, outputStream);
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        this.checkReadableBytes(n);
        this.getBytes(this.readerIndex, outputStream, n, true);
        this.readerIndex += n;
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return this.getBytes(n, gatheringByteChannel, n2, false);
    }

    private int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : this.buffer.duplicate();
        byteBuffer.clear().position(n).limit(n + n2);
        return gatheringByteChannel.write(byteBuffer);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return this.getBytes(n, fileChannel, l, n2, false);
    }

    private int getBytes(int n, FileChannel fileChannel, long l, int n2, boolean bl) throws IOException {
        this.ensureAccessible();
        if (n2 == 0) {
            return 1;
        }
        ByteBuffer byteBuffer = bl ? this.internalNioBuffer() : this.buffer.duplicate();
        byteBuffer.clear().position(n).limit(n + n2);
        return fileChannel.write(byteBuffer, l);
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
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        this.ensureAccessible();
        if (this.buffer.hasArray()) {
            return inputStream.read(this.buffer.array(), this.buffer.arrayOffset() + n, n2);
        }
        byte[] byArray = new byte[n2];
        int n3 = inputStream.read(byArray);
        if (n3 <= 0) {
            return n3;
        }
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n);
        byteBuffer.put(byArray, 0, n3);
        return n3;
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n2);
        try {
            return scatteringByteChannel.read(this.tmpNioBuf);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.ensureAccessible();
        ByteBuffer byteBuffer = this.internalNioBuffer();
        byteBuffer.clear().position(n).limit(n + n2);
        try {
            return fileChannel.read(this.tmpNioBuf, l);
        } catch (ClosedChannelException closedChannelException) {
            return 1;
        }
    }

    @Override
    public int nioBufferCount() {
        return 0;
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return new ByteBuffer[]{this.nioBuffer(n, n2)};
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        ByteBuffer byteBuffer;
        this.ensureAccessible();
        try {
            byteBuffer = (ByteBuffer)this.buffer.duplicate().clear().position(n).limit(n + n2);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IndexOutOfBoundsException("Too many bytes to read - Need " + (n + n2));
        }
        return this.alloc().directBuffer(n2, this.maxCapacity()).writeBytes(byteBuffer);
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return (ByteBuffer)this.internalNioBuffer().clear().position(n).limit(n + n2);
    }

    private ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer == null) {
            this.tmpNioBuf = byteBuffer = this.buffer.duplicate();
        }
        return byteBuffer;
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        this.checkIndex(n, n2);
        return ((ByteBuffer)this.buffer.duplicate().position(n).limit(n + n2)).slice();
    }

    @Override
    protected void deallocate() {
        ByteBuffer byteBuffer = this.buffer;
        if (byteBuffer == null) {
            return;
        }
        this.buffer = null;
        if (!this.doNotFree) {
            this.freeDirect(byteBuffer);
        }
    }

    @Override
    public ByteBuf unwrap() {
        return null;
    }
}

