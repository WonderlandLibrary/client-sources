/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.RecyclableArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.Collections;

final class FixedCompositeByteBuf
extends AbstractReferenceCountedByteBuf {
    private static final ByteBuf[] EMPTY = new ByteBuf[]{Unpooled.EMPTY_BUFFER};
    private final int nioBufferCount;
    private final int capacity;
    private final ByteBufAllocator allocator;
    private final ByteOrder order;
    private final Object[] buffers;
    private final boolean direct;

    FixedCompositeByteBuf(ByteBufAllocator byteBufAllocator, ByteBuf ... byteBufArray) {
        super(Integer.MAX_VALUE);
        if (byteBufArray.length == 0) {
            this.buffers = EMPTY;
            this.order = ByteOrder.BIG_ENDIAN;
            this.nioBufferCount = 1;
            this.capacity = 0;
            this.direct = false;
        } else {
            ByteBuf byteBuf = byteBufArray[0];
            this.buffers = new Object[byteBufArray.length];
            this.buffers[0] = byteBuf;
            boolean bl = true;
            int n = byteBuf.nioBufferCount();
            int n2 = byteBuf.readableBytes();
            this.order = byteBuf.order();
            for (int i = 1; i < byteBufArray.length; ++i) {
                byteBuf = byteBufArray[i];
                if (byteBufArray[i].order() != this.order) {
                    throw new IllegalArgumentException("All ByteBufs need to have same ByteOrder");
                }
                n += byteBuf.nioBufferCount();
                n2 += byteBuf.readableBytes();
                if (!byteBuf.isDirect()) {
                    bl = false;
                }
                this.buffers[i] = byteBuf;
            }
            this.nioBufferCount = n;
            this.capacity = n2;
            this.direct = bl;
        }
        this.setIndex(0, this.capacity());
        this.allocator = byteBufAllocator;
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
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int maxCapacity() {
        return this.capacity;
    }

    @Override
    public ByteBuf capacity(int n) {
        throw new ReadOnlyBufferException();
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.allocator;
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
    public boolean isDirect() {
        return this.direct;
    }

    private Component findComponent(int n) {
        int n2 = 0;
        for (int i = 0; i < this.buffers.length; ++i) {
            boolean bl;
            ByteBuf byteBuf;
            Component component = null;
            Object object = this.buffers[i];
            if (object instanceof ByteBuf) {
                byteBuf = (ByteBuf)object;
                bl = true;
            } else {
                component = (Component)object;
                byteBuf = Component.access$000(component);
                bl = false;
            }
            if (n >= (n2 += byteBuf.readableBytes())) continue;
            if (bl) {
                component = new Component(i, n2 - byteBuf.readableBytes(), byteBuf);
                this.buffers[i] = component;
            }
            return component;
        }
        throw new IllegalStateException();
    }

    private ByteBuf buffer(int n) {
        Object object = this.buffers[n];
        if (object instanceof ByteBuf) {
            return (ByteBuf)object;
        }
        return Component.access$000((Component)object);
    }

    @Override
    public byte getByte(int n) {
        return this._getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        Component component = this.findComponent(n);
        return Component.access$000(component).getByte(n - Component.access$100(component));
    }

    @Override
    protected short _getShort(int n) {
        Component component = this.findComponent(n);
        if (n + 2 <= Component.access$200(component)) {
            return Component.access$000(component).getShort(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (short)((this._getByte(n) & 0xFF) << 8 | this._getByte(n + 1) & 0xFF);
        }
        return (short)(this._getByte(n) & 0xFF | (this._getByte(n + 1) & 0xFF) << 8);
    }

    @Override
    protected short _getShortLE(int n) {
        Component component = this.findComponent(n);
        if (n + 2 <= Component.access$200(component)) {
            return Component.access$000(component).getShortLE(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (short)(this._getByte(n) & 0xFF | (this._getByte(n + 1) & 0xFF) << 8);
        }
        return (short)((this._getByte(n) & 0xFF) << 8 | this._getByte(n + 1) & 0xFF);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        Component component = this.findComponent(n);
        if (n + 3 <= Component.access$200(component)) {
            return Component.access$000(component).getUnsignedMedium(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(n) & 0xFFFF) << 8 | this._getByte(n + 2) & 0xFF;
        }
        return this._getShort(n) & 0xFFFF | (this._getByte(n + 2) & 0xFF) << 16;
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        Component component = this.findComponent(n);
        if (n + 3 <= Component.access$200(component)) {
            return Component.access$000(component).getUnsignedMediumLE(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return this._getShortLE(n) & 0xFFFF | (this._getByte(n + 2) & 0xFF) << 16;
        }
        return (this._getShortLE(n) & 0xFFFF) << 8 | this._getByte(n + 2) & 0xFF;
    }

    @Override
    protected int _getInt(int n) {
        Component component = this.findComponent(n);
        if (n + 4 <= Component.access$200(component)) {
            return Component.access$000(component).getInt(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(n) & 0xFFFF) << 16 | this._getShort(n + 2) & 0xFFFF;
        }
        return this._getShort(n) & 0xFFFF | (this._getShort(n + 2) & 0xFFFF) << 16;
    }

    @Override
    protected int _getIntLE(int n) {
        Component component = this.findComponent(n);
        if (n + 4 <= Component.access$200(component)) {
            return Component.access$000(component).getIntLE(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return this._getShortLE(n) & 0xFFFF | (this._getShortLE(n + 2) & 0xFFFF) << 16;
        }
        return (this._getShortLE(n) & 0xFFFF) << 16 | this._getShortLE(n + 2) & 0xFFFF;
    }

    @Override
    protected long _getLong(int n) {
        Component component = this.findComponent(n);
        if (n + 8 <= Component.access$200(component)) {
            return Component.access$000(component).getLong(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return ((long)this._getInt(n) & 0xFFFFFFFFL) << 32 | (long)this._getInt(n + 4) & 0xFFFFFFFFL;
        }
        return (long)this._getInt(n) & 0xFFFFFFFFL | ((long)this._getInt(n + 4) & 0xFFFFFFFFL) << 32;
    }

    @Override
    protected long _getLongLE(int n) {
        Component component = this.findComponent(n);
        if (n + 8 <= Component.access$200(component)) {
            return Component.access$000(component).getLongLE(n - Component.access$100(component));
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (long)this._getIntLE(n) & 0xFFFFFFFFL | ((long)this._getIntLE(n + 4) & 0xFFFFFFFFL) << 32;
        }
        return ((long)this._getIntLE(n) & 0xFFFFFFFFL) << 32 | (long)this._getIntLE(n + 4) & 0xFFFFFFFFL;
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byArray.length);
        if (n3 == 0) {
            return this;
        }
        Component component = this.findComponent(n);
        int n4 = Component.access$300(component);
        int n5 = Component.access$100(component);
        ByteBuf byteBuf = Component.access$000(component);
        while (true) {
            int n6 = Math.min(n3, byteBuf.readableBytes() - (n - n5));
            byteBuf.getBytes(n - n5, byArray, n2, n6);
            n += n6;
            n2 += n6;
            n5 += byteBuf.readableBytes();
            if ((n3 -= n6) <= 0) break;
            byteBuf = this.buffer(++n4);
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        int n2 = byteBuffer.limit();
        int n3 = byteBuffer.remaining();
        this.checkIndex(n, n3);
        if (n3 == 0) {
            return this;
        }
        try {
            Component component = this.findComponent(n);
            int n4 = Component.access$300(component);
            int n5 = Component.access$100(component);
            ByteBuf byteBuf = Component.access$000(component);
            while (true) {
                int n6 = Math.min(n3, byteBuf.readableBytes() - (n - n5));
                byteBuffer.limit(byteBuffer.position() + n6);
                byteBuf.getBytes(n - n5, byteBuffer);
                n += n6;
                n5 += byteBuf.readableBytes();
                if ((n3 -= n6) <= 0) {
                    break;
                }
                byteBuf = this.buffer(++n4);
            }
        } finally {
            byteBuffer.limit(n2);
        }
        return this;
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (n3 == 0) {
            return this;
        }
        Component component = this.findComponent(n);
        int n4 = Component.access$300(component);
        int n5 = Component.access$100(component);
        ByteBuf byteBuf2 = Component.access$000(component);
        while (true) {
            int n6 = Math.min(n3, byteBuf2.readableBytes() - (n - n5));
            byteBuf2.getBytes(n - n5, byteBuf, n2, n6);
            n += n6;
            n2 += n6;
            n5 += byteBuf2.readableBytes();
            if ((n3 -= n6) <= 0) break;
            byteBuf2 = this.buffer(++n4);
        }
        return this;
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        int n3 = this.nioBufferCount();
        if (n3 == 1) {
            return gatheringByteChannel.write(this.internalNioBuffer(n, n2));
        }
        long l = gatheringByteChannel.write(this.nioBuffers(n, n2));
        if (l > Integer.MAX_VALUE) {
            return 0;
        }
        return (int)l;
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        int n3 = this.nioBufferCount();
        if (n3 == 1) {
            return fileChannel.write(this.internalNioBuffer(n, n2), l);
        }
        long l2 = 0L;
        for (ByteBuffer byteBuffer : this.nioBuffers(n, n2)) {
            l2 += (long)fileChannel.write(byteBuffer, l + l2);
        }
        if (l2 > Integer.MAX_VALUE) {
            return 0;
        }
        return (int)l2;
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return this;
        }
        Component component = this.findComponent(n);
        int n3 = Component.access$300(component);
        int n4 = Component.access$100(component);
        ByteBuf byteBuf = Component.access$000(component);
        while (true) {
            int n5 = Math.min(n2, byteBuf.readableBytes() - (n - n4));
            byteBuf.getBytes(n - n4, outputStream, n5);
            n += n5;
            n4 += byteBuf.readableBytes();
            if ((n2 -= n5) <= 0) break;
            byteBuf = this.buffer(++n3);
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuf copy(int n, int n2) {
        this.checkIndex(n, n2);
        boolean bl = true;
        ByteBuf byteBuf = this.alloc().buffer(n2);
        try {
            byteBuf.writeBytes(this, n, n2);
            bl = false;
            ByteBuf byteBuf2 = byteBuf;
            return byteBuf2;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    @Override
    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        Comparable<ByteBuf> comparable;
        this.checkIndex(n, n2);
        if (this.buffers.length == 1 && ((ByteBuf)(comparable = this.buffer(0))).nioBufferCount() == 1) {
            return ((ByteBuf)comparable).nioBuffer(n, n2);
        }
        comparable = ByteBuffer.allocate(n2).order(this.order());
        ByteBuffer[] byteBufferArray = this.nioBuffers(n, n2);
        for (int i = 0; i < byteBufferArray.length; ++i) {
            ((ByteBuffer)comparable).put(byteBufferArray[i]);
        }
        ((Buffer)((Object)comparable)).flip();
        return comparable;
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        if (this.buffers.length == 1) {
            return this.buffer(0).internalNioBuffer(n, n2);
        }
        throw new UnsupportedOperationException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return EmptyArrays.EMPTY_BYTE_BUFFERS;
        }
        RecyclableArrayList recyclableArrayList = RecyclableArrayList.newInstance(this.buffers.length);
        try {
            Component component = this.findComponent(n);
            int n3 = Component.access$300(component);
            int n4 = Component.access$100(component);
            ByteBuf byteBuf = Component.access$000(component);
            while (true) {
                int n5 = Math.min(n2, byteBuf.readableBytes() - (n - n4));
                switch (byteBuf.nioBufferCount()) {
                    case 0: {
                        throw new UnsupportedOperationException();
                    }
                    case 1: {
                        recyclableArrayList.add(byteBuf.nioBuffer(n - n4, n5));
                        break;
                    }
                    default: {
                        Collections.addAll(recyclableArrayList, byteBuf.nioBuffers(n - n4, n5));
                    }
                }
                n += n5;
                n4 += byteBuf.readableBytes();
                if ((n2 -= n5) <= 0) break;
                byteBuf = this.buffer(++n3);
            }
            ByteBuffer[] byteBufferArray = recyclableArrayList.toArray(new ByteBuffer[recyclableArrayList.size()]);
            return byteBufferArray;
        } finally {
            recyclableArrayList.recycle();
        }
    }

    @Override
    public boolean hasArray() {
        switch (this.buffers.length) {
            case 0: {
                return false;
            }
            case 1: {
                return this.buffer(0).hasArray();
            }
        }
        return true;
    }

    @Override
    public byte[] array() {
        switch (this.buffers.length) {
            case 0: {
                return EmptyArrays.EMPTY_BYTES;
            }
            case 1: {
                return this.buffer(0).array();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public int arrayOffset() {
        switch (this.buffers.length) {
            case 0: {
                return 1;
            }
            case 1: {
                return this.buffer(0).arrayOffset();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMemoryAddress() {
        switch (this.buffers.length) {
            case 0: {
                return Unpooled.EMPTY_BUFFER.hasMemoryAddress();
            }
            case 1: {
                return this.buffer(0).hasMemoryAddress();
            }
        }
        return true;
    }

    @Override
    public long memoryAddress() {
        switch (this.buffers.length) {
            case 0: {
                return Unpooled.EMPTY_BUFFER.memoryAddress();
            }
            case 1: {
                return this.buffer(0).memoryAddress();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    protected void deallocate() {
        for (int i = 0; i < this.buffers.length; ++i) {
            this.buffer(i).release();
        }
    }

    @Override
    public String toString() {
        String string = super.toString();
        string = string.substring(0, string.length() - 1);
        return string + ", components=" + this.buffers.length + ')';
    }

    private static final class Component {
        private final int index;
        private final int offset;
        private final ByteBuf buf;
        private final int endOffset;

        Component(int n, int n2, ByteBuf byteBuf) {
            this.index = n;
            this.offset = n2;
            this.endOffset = n2 + byteBuf.readableBytes();
            this.buf = byteBuf;
        }

        static ByteBuf access$000(Component component) {
            return component.buf;
        }

        static int access$100(Component component) {
            return component.offset;
        }

        static int access$200(Component component) {
            return component.endOffset;
        }

        static int access$300(Component component) {
            return component.index;
        }
    }
}

