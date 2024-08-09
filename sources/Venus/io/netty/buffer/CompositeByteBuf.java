/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CompositeByteBuf
extends AbstractReferenceCountedByteBuf
implements Iterable<ByteBuf> {
    private static final ByteBuffer EMPTY_NIO_BUFFER;
    private static final Iterator<ByteBuf> EMPTY_ITERATOR;
    private final ByteBufAllocator alloc;
    private final boolean direct;
    private final ComponentList components;
    private final int maxNumComponents;
    private boolean freed;
    static final boolean $assertionsDisabled;

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean bl, int n) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        this.alloc = byteBufAllocator;
        this.direct = bl;
        this.maxNumComponents = n;
        this.components = CompositeByteBuf.newList(n);
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean bl, int n, ByteBuf ... byteBufArray) {
        this(byteBufAllocator, bl, n, byteBufArray, 0, byteBufArray.length);
    }

    CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean bl, int n, ByteBuf[] byteBufArray, int n2, int n3) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        if (n < 2) {
            throw new IllegalArgumentException("maxNumComponents: " + n + " (expected: >= 2)");
        }
        this.alloc = byteBufAllocator;
        this.direct = bl;
        this.maxNumComponents = n;
        this.components = CompositeByteBuf.newList(n);
        this.addComponents0(false, 0, byteBufArray, n2, n3);
        this.consolidateIfNeeded();
        this.setIndex(0, this.capacity());
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean bl, int n, Iterable<ByteBuf> iterable) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        }
        if (n < 2) {
            throw new IllegalArgumentException("maxNumComponents: " + n + " (expected: >= 2)");
        }
        this.alloc = byteBufAllocator;
        this.direct = bl;
        this.maxNumComponents = n;
        this.components = CompositeByteBuf.newList(n);
        this.addComponents0(false, 0, iterable);
        this.consolidateIfNeeded();
        this.setIndex(0, this.capacity());
    }

    private static ComponentList newList(int n) {
        return new ComponentList(Math.min(16, n));
    }

    CompositeByteBuf(ByteBufAllocator byteBufAllocator) {
        super(Integer.MAX_VALUE);
        this.alloc = byteBufAllocator;
        this.direct = false;
        this.maxNumComponents = 0;
        this.components = null;
    }

    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        return this.addComponent(false, byteBuf);
    }

    public CompositeByteBuf addComponents(ByteBuf ... byteBufArray) {
        return this.addComponents(false, byteBufArray);
    }

    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        return this.addComponents(false, iterable);
    }

    public CompositeByteBuf addComponent(int n, ByteBuf byteBuf) {
        return this.addComponent(false, n, byteBuf);
    }

    public CompositeByteBuf addComponent(boolean bl, ByteBuf byteBuf) {
        ObjectUtil.checkNotNull(byteBuf, "buffer");
        this.addComponent0(bl, this.components.size(), byteBuf);
        this.consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponents(boolean bl, ByteBuf ... byteBufArray) {
        this.addComponents0(bl, this.components.size(), byteBufArray, 0, byteBufArray.length);
        this.consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponents(boolean bl, Iterable<ByteBuf> iterable) {
        this.addComponents0(bl, this.components.size(), iterable);
        this.consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponent(boolean bl, int n, ByteBuf byteBuf) {
        ObjectUtil.checkNotNull(byteBuf, "buffer");
        this.addComponent0(bl, n, byteBuf);
        this.consolidateIfNeeded();
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int addComponent0(boolean bl, int n, ByteBuf byteBuf) {
        if (!$assertionsDisabled && byteBuf == null) {
            throw new AssertionError();
        }
        boolean bl2 = false;
        try {
            this.checkComponentIndex(n);
            int n2 = byteBuf.readableBytes();
            Component component = new Component(byteBuf.order(ByteOrder.BIG_ENDIAN).slice());
            if (n == this.components.size()) {
                bl2 = this.components.add(component);
                if (n == 0) {
                    component.endOffset = n2;
                } else {
                    Component component2 = (Component)this.components.get(n - 1);
                    component.offset = component2.endOffset;
                    component.endOffset = component.offset + n2;
                }
            } else {
                this.components.add(n, component);
                bl2 = true;
                if (n2 != 0) {
                    this.updateComponentOffsets(n);
                }
            }
            if (bl) {
                this.writerIndex(this.writerIndex() + byteBuf.readableBytes());
            }
            int n3 = n;
            return n3;
        } finally {
            if (!bl2) {
                byteBuf.release();
            }
        }
    }

    public CompositeByteBuf addComponents(int n, ByteBuf ... byteBufArray) {
        this.addComponents0(false, n, byteBufArray, 0, byteBufArray.length);
        this.consolidateIfNeeded();
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int addComponents0(boolean bl, int n, ByteBuf[] byteBufArray, int n2, int n3) {
        int n4;
        ObjectUtil.checkNotNull(byteBufArray, "buffers");
        try {
            ByteBuf byteBuf;
            this.checkComponentIndex(n);
            while (n4 < n3 && (byteBuf = byteBufArray[n4++]) != null) {
                int n5;
                if ((n = this.addComponent0(bl, n, byteBuf) + 1) <= (n5 = this.components.size())) continue;
                n = n5;
            }
            int n6 = n;
            return n6;
        } finally {
            for (n4 = n2; n4 < n3; ++n4) {
                ByteBuf byteBuf = byteBufArray[n4];
                if (byteBuf == null) continue;
                try {
                    byteBuf.release();
                    continue;
                } catch (Throwable throwable) {}
            }
        }
    }

    public CompositeByteBuf addComponents(int n, Iterable<ByteBuf> iterable) {
        this.addComponents0(false, n, iterable);
        this.consolidateIfNeeded();
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int addComponents0(boolean bl, int n, Iterable<ByteBuf> iterable) {
        ArrayList<ByteBuf> arrayList;
        if (iterable instanceof ByteBuf) {
            return this.addComponent0(bl, n, (ByteBuf)((Object)iterable));
        }
        ObjectUtil.checkNotNull(iterable, "buffers");
        if (!(iterable instanceof Collection)) {
            arrayList = new ArrayList<ByteBuf>();
            try {
                for (ByteBuf byteBuf : iterable) {
                    arrayList.add(byteBuf);
                }
                iterable = arrayList;
            } finally {
                if (iterable != arrayList) {
                    for (ByteBuf byteBuf : iterable) {
                        if (byteBuf == null) continue;
                        try {
                            byteBuf.release();
                        } catch (Throwable throwable) {}
                    }
                }
            }
        }
        arrayList = (ArrayList<ByteBuf>)iterable;
        return this.addComponents0(bl, n, arrayList.toArray(new ByteBuf[arrayList.size()]), 0, arrayList.size());
    }

    private void consolidateIfNeeded() {
        int n = this.components.size();
        if (n > this.maxNumComponents) {
            int n2 = ((Component)this.components.get((int)(n - 1))).endOffset;
            ByteBuf byteBuf = this.allocBuffer(n2);
            for (int i = 0; i < n; ++i) {
                Component component = (Component)this.components.get(i);
                ByteBuf byteBuf2 = component.buf;
                byteBuf.writeBytes(byteBuf2);
                component.freeIfNecessary();
            }
            Component component = new Component(byteBuf);
            component.endOffset = component.length;
            this.components.clear();
            this.components.add(component);
        }
    }

    private void checkComponentIndex(int n) {
        this.ensureAccessible();
        if (n < 0 || n > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", n, this.components.size()));
        }
    }

    private void checkComponentIndex(int n, int n2) {
        this.ensureAccessible();
        if (n < 0 || n + n2 > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", n, n2, this.components.size()));
        }
    }

    private void updateComponentOffsets(int n) {
        int n2 = this.components.size();
        if (n2 <= n) {
            return;
        }
        Component component = (Component)this.components.get(n);
        if (n == 0) {
            component.offset = 0;
            component.endOffset = component.length;
            ++n;
        }
        for (int i = n; i < n2; ++i) {
            Component component2 = (Component)this.components.get(i - 1);
            Component component3 = (Component)this.components.get(i);
            component3.offset = component2.endOffset;
            component3.endOffset = component3.offset + component3.length;
        }
    }

    public CompositeByteBuf removeComponent(int n) {
        this.checkComponentIndex(n);
        Component component = (Component)this.components.remove(n);
        component.freeIfNecessary();
        if (component.length > 0) {
            this.updateComponentOffsets(n);
        }
        return this;
    }

    public CompositeByteBuf removeComponents(int n, int n2) {
        this.checkComponentIndex(n, n2);
        if (n2 == 0) {
            return this;
        }
        int n3 = n + n2;
        boolean bl = false;
        for (int i = n; i < n3; ++i) {
            Component component = (Component)this.components.get(i);
            if (component.length > 0) {
                bl = true;
            }
            component.freeIfNecessary();
        }
        this.components.removeRange(n, n3);
        if (bl) {
            this.updateComponentOffsets(n);
        }
        return this;
    }

    @Override
    public Iterator<ByteBuf> iterator() {
        this.ensureAccessible();
        if (this.components.isEmpty()) {
            return EMPTY_ITERATOR;
        }
        return new CompositeByteBufIterator(this, null);
    }

    public List<ByteBuf> decompose(int n, int n2) {
        int n3;
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return Collections.emptyList();
        }
        int n4 = this.toComponentIndex(n);
        ArrayList<ByteBuf> arrayList = new ArrayList<ByteBuf>(this.components.size());
        Component component = (Component)this.components.get(n4);
        ByteBuf byteBuf = component.buf.duplicate();
        byteBuf.readerIndex(n - component.offset);
        ByteBuf byteBuf2 = byteBuf;
        int n5 = n2;
        do {
            if (n5 <= (n3 = byteBuf2.readableBytes())) {
                byteBuf2.writerIndex(byteBuf2.readerIndex() + n5);
                arrayList.add(byteBuf2);
                break;
            }
            arrayList.add(byteBuf2);
            byteBuf2 = ((Component)this.components.get((int)(++n4))).buf.duplicate();
        } while ((n5 -= n3) > 0);
        for (n3 = 0; n3 < arrayList.size(); ++n3) {
            arrayList.set(n3, ((ByteBuf)arrayList.get(n3)).slice());
        }
        return arrayList;
    }

    @Override
    public boolean isDirect() {
        int n = this.components.size();
        if (n == 0) {
            return true;
        }
        for (int i = 0; i < n; ++i) {
            if (((Component)this.components.get((int)i)).buf.isDirect()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasArray() {
        switch (this.components.size()) {
            case 0: {
                return false;
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.hasArray();
            }
        }
        return true;
    }

    @Override
    public byte[] array() {
        switch (this.components.size()) {
            case 0: {
                return EmptyArrays.EMPTY_BYTES;
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.array();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public int arrayOffset() {
        switch (this.components.size()) {
            case 0: {
                return 1;
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.arrayOffset();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasMemoryAddress() {
        switch (this.components.size()) {
            case 0: {
                return Unpooled.EMPTY_BUFFER.hasMemoryAddress();
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.hasMemoryAddress();
            }
        }
        return true;
    }

    @Override
    public long memoryAddress() {
        switch (this.components.size()) {
            case 0: {
                return Unpooled.EMPTY_BUFFER.memoryAddress();
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.memoryAddress();
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public int capacity() {
        int n = this.components.size();
        if (n == 0) {
            return 1;
        }
        return ((Component)this.components.get((int)(n - 1))).endOffset;
    }

    @Override
    public CompositeByteBuf capacity(int n) {
        this.checkNewCapacity(n);
        int n2 = this.capacity();
        if (n > n2) {
            int n3 = n - n2;
            int n4 = this.components.size();
            if (n4 < this.maxNumComponents) {
                ByteBuf byteBuf = this.allocBuffer(n3);
                byteBuf.setIndex(0, n3);
                this.addComponent0(false, this.components.size(), byteBuf);
            } else {
                ByteBuf byteBuf = this.allocBuffer(n3);
                byteBuf.setIndex(0, n3);
                this.addComponent0(false, this.components.size(), byteBuf);
                this.consolidateIfNeeded();
            }
        } else if (n < n2) {
            int n5 = n2 - n;
            ListIterator<Component> listIterator2 = this.components.listIterator(this.components.size());
            while (listIterator2.hasPrevious()) {
                Component component = (Component)listIterator2.previous();
                if (n5 >= component.length) {
                    n5 -= component.length;
                    listIterator2.remove();
                    continue;
                }
                Component component2 = new Component(component.buf.slice(0, component.length - n5));
                component2.offset = component.offset;
                component2.endOffset = component2.offset + component2.length;
                listIterator2.set(component2);
                break;
            }
            if (this.readerIndex() > n) {
                this.setIndex(n, n);
            } else if (this.writerIndex() > n) {
                this.writerIndex(n);
            }
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

    public int numComponents() {
        return this.components.size();
    }

    public int maxNumComponents() {
        return this.maxNumComponents;
    }

    public int toComponentIndex(int n) {
        this.checkIndex(n);
        int n2 = 0;
        int n3 = this.components.size();
        while (n2 <= n3) {
            int n4 = n2 + n3 >>> 1;
            Component component = (Component)this.components.get(n4);
            if (n >= component.endOffset) {
                n2 = n4 + 1;
                continue;
            }
            if (n < component.offset) {
                n3 = n4 - 1;
                continue;
            }
            return n4;
        }
        throw new Error("should not reach here");
    }

    public int toByteIndex(int n) {
        this.checkComponentIndex(n);
        return ((Component)this.components.get((int)n)).offset;
    }

    @Override
    public byte getByte(int n) {
        return this._getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        Component component = this.findComponent(n);
        return component.buf.getByte(n - component.offset);
    }

    @Override
    protected short _getShort(int n) {
        Component component = this.findComponent(n);
        if (n + 2 <= component.endOffset) {
            return component.buf.getShort(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (short)((this._getByte(n) & 0xFF) << 8 | this._getByte(n + 1) & 0xFF);
        }
        return (short)(this._getByte(n) & 0xFF | (this._getByte(n + 1) & 0xFF) << 8);
    }

    @Override
    protected short _getShortLE(int n) {
        Component component = this.findComponent(n);
        if (n + 2 <= component.endOffset) {
            return component.buf.getShortLE(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (short)(this._getByte(n) & 0xFF | (this._getByte(n + 1) & 0xFF) << 8);
        }
        return (short)((this._getByte(n) & 0xFF) << 8 | this._getByte(n + 1) & 0xFF);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        Component component = this.findComponent(n);
        if (n + 3 <= component.endOffset) {
            return component.buf.getUnsignedMedium(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(n) & 0xFFFF) << 8 | this._getByte(n + 2) & 0xFF;
        }
        return this._getShort(n) & 0xFFFF | (this._getByte(n + 2) & 0xFF) << 16;
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        Component component = this.findComponent(n);
        if (n + 3 <= component.endOffset) {
            return component.buf.getUnsignedMediumLE(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return this._getShortLE(n) & 0xFFFF | (this._getByte(n + 2) & 0xFF) << 16;
        }
        return (this._getShortLE(n) & 0xFFFF) << 8 | this._getByte(n + 2) & 0xFF;
    }

    @Override
    protected int _getInt(int n) {
        Component component = this.findComponent(n);
        if (n + 4 <= component.endOffset) {
            return component.buf.getInt(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (this._getShort(n) & 0xFFFF) << 16 | this._getShort(n + 2) & 0xFFFF;
        }
        return this._getShort(n) & 0xFFFF | (this._getShort(n + 2) & 0xFFFF) << 16;
    }

    @Override
    protected int _getIntLE(int n) {
        Component component = this.findComponent(n);
        if (n + 4 <= component.endOffset) {
            return component.buf.getIntLE(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return this._getShortLE(n) & 0xFFFF | (this._getShortLE(n + 2) & 0xFFFF) << 16;
        }
        return (this._getShortLE(n) & 0xFFFF) << 16 | this._getShortLE(n + 2) & 0xFFFF;
    }

    @Override
    protected long _getLong(int n) {
        Component component = this.findComponent(n);
        if (n + 8 <= component.endOffset) {
            return component.buf.getLong(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return ((long)this._getInt(n) & 0xFFFFFFFFL) << 32 | (long)this._getInt(n + 4) & 0xFFFFFFFFL;
        }
        return (long)this._getInt(n) & 0xFFFFFFFFL | ((long)this._getInt(n + 4) & 0xFFFFFFFFL) << 32;
    }

    @Override
    protected long _getLongLE(int n) {
        Component component = this.findComponent(n);
        if (n + 8 <= component.endOffset) {
            return component.buf.getLongLE(n - component.offset);
        }
        if (this.order() == ByteOrder.BIG_ENDIAN) {
            return (long)this._getIntLE(n) & 0xFFFFFFFFL | ((long)this._getIntLE(n + 4) & 0xFFFFFFFFL) << 32;
        }
        return ((long)this._getIntLE(n) & 0xFFFFFFFFL) << 32 | (long)this._getIntLE(n + 4) & 0xFFFFFFFFL;
    }

    @Override
    public CompositeByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byArray.length);
        if (n3 == 0) {
            return this;
        }
        int n4 = this.toComponentIndex(n);
        while (n3 > 0) {
            Component component = (Component)this.components.get(n4);
            ByteBuf byteBuf = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n3, byteBuf.capacity() - (n - n5));
            byteBuf.getBytes(n - n5, byArray, n2, n6);
            n += n6;
            n2 += n6;
            n3 -= n6;
            ++n4;
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CompositeByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        int n2 = byteBuffer.limit();
        int n3 = byteBuffer.remaining();
        this.checkIndex(n, n3);
        if (n3 == 0) {
            return this;
        }
        int n4 = this.toComponentIndex(n);
        try {
            while (n3 > 0) {
                Component component = (Component)this.components.get(n4);
                ByteBuf byteBuf = component.buf;
                int n5 = component.offset;
                int n6 = Math.min(n3, byteBuf.capacity() - (n - n5));
                byteBuffer.limit(byteBuffer.position() + n6);
                byteBuf.getBytes(n - n5, byteBuffer);
                n += n6;
                n3 -= n6;
                ++n4;
            }
        } finally {
            byteBuffer.limit(n2);
        }
        return this;
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkDstIndex(n, n3, n2, byteBuf.capacity());
        if (n3 == 0) {
            return this;
        }
        int n4 = this.toComponentIndex(n);
        while (n3 > 0) {
            Component component = (Component)this.components.get(n4);
            ByteBuf byteBuf2 = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n3, byteBuf2.capacity() - (n - n5));
            byteBuf2.getBytes(n - n5, byteBuf, n2, n6);
            n += n6;
            n2 += n6;
            n3 -= n6;
            ++n4;
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
    public CompositeByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return this;
        }
        int n3 = this.toComponentIndex(n);
        while (n2 > 0) {
            Component component = (Component)this.components.get(n3);
            ByteBuf byteBuf = component.buf;
            int n4 = component.offset;
            int n5 = Math.min(n2, byteBuf.capacity() - (n - n4));
            byteBuf.getBytes(n - n4, outputStream, n5);
            n += n5;
            n2 -= n5;
            ++n3;
        }
        return this;
    }

    @Override
    public CompositeByteBuf setByte(int n, int n2) {
        Component component = this.findComponent(n);
        component.buf.setByte(n - component.offset, n2);
        return this;
    }

    @Override
    protected void _setByte(int n, int n2) {
        this.setByte(n, n2);
    }

    @Override
    public CompositeByteBuf setShort(int n, int n2) {
        return (CompositeByteBuf)super.setShort(n, n2);
    }

    @Override
    protected void _setShort(int n, int n2) {
        Component component = this.findComponent(n);
        if (n + 2 <= component.endOffset) {
            component.buf.setShort(n - component.offset, n2);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setByte(n, (byte)(n2 >>> 8));
            this._setByte(n + 1, (byte)n2);
        } else {
            this._setByte(n, (byte)n2);
            this._setByte(n + 1, (byte)(n2 >>> 8));
        }
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        Component component = this.findComponent(n);
        if (n + 2 <= component.endOffset) {
            component.buf.setShortLE(n - component.offset, n2);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setByte(n, (byte)n2);
            this._setByte(n + 1, (byte)(n2 >>> 8));
        } else {
            this._setByte(n, (byte)(n2 >>> 8));
            this._setByte(n + 1, (byte)n2);
        }
    }

    @Override
    public CompositeByteBuf setMedium(int n, int n2) {
        return (CompositeByteBuf)super.setMedium(n, n2);
    }

    @Override
    protected void _setMedium(int n, int n2) {
        Component component = this.findComponent(n);
        if (n + 3 <= component.endOffset) {
            component.buf.setMedium(n - component.offset, n2);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShort(n, (short)(n2 >> 8));
            this._setByte(n + 2, (byte)n2);
        } else {
            this._setShort(n, (short)n2);
            this._setByte(n + 2, (byte)(n2 >>> 16));
        }
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        Component component = this.findComponent(n);
        if (n + 3 <= component.endOffset) {
            component.buf.setMediumLE(n - component.offset, n2);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShortLE(n, (short)n2);
            this._setByte(n + 2, (byte)(n2 >>> 16));
        } else {
            this._setShortLE(n, (short)(n2 >> 8));
            this._setByte(n + 2, (byte)n2);
        }
    }

    @Override
    public CompositeByteBuf setInt(int n, int n2) {
        return (CompositeByteBuf)super.setInt(n, n2);
    }

    @Override
    protected void _setInt(int n, int n2) {
        Component component = this.findComponent(n);
        if (n + 4 <= component.endOffset) {
            component.buf.setInt(n - component.offset, n2);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShort(n, (short)(n2 >>> 16));
            this._setShort(n + 2, (short)n2);
        } else {
            this._setShort(n, (short)n2);
            this._setShort(n + 2, (short)(n2 >>> 16));
        }
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        Component component = this.findComponent(n);
        if (n + 4 <= component.endOffset) {
            component.buf.setIntLE(n - component.offset, n2);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setShortLE(n, (short)n2);
            this._setShortLE(n + 2, (short)(n2 >>> 16));
        } else {
            this._setShortLE(n, (short)(n2 >>> 16));
            this._setShortLE(n + 2, (short)n2);
        }
    }

    @Override
    public CompositeByteBuf setLong(int n, long l) {
        return (CompositeByteBuf)super.setLong(n, l);
    }

    @Override
    protected void _setLong(int n, long l) {
        Component component = this.findComponent(n);
        if (n + 8 <= component.endOffset) {
            component.buf.setLong(n - component.offset, l);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setInt(n, (int)(l >>> 32));
            this._setInt(n + 4, (int)l);
        } else {
            this._setInt(n, (int)l);
            this._setInt(n + 4, (int)(l >>> 32));
        }
    }

    @Override
    protected void _setLongLE(int n, long l) {
        Component component = this.findComponent(n);
        if (n + 8 <= component.endOffset) {
            component.buf.setLongLE(n - component.offset, l);
        } else if (this.order() == ByteOrder.BIG_ENDIAN) {
            this._setIntLE(n, (int)l);
            this._setIntLE(n + 4, (int)(l >>> 32));
        } else {
            this._setIntLE(n, (int)(l >>> 32));
            this._setIntLE(n + 4, (int)l);
        }
    }

    @Override
    public CompositeByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byArray.length);
        if (n3 == 0) {
            return this;
        }
        int n4 = this.toComponentIndex(n);
        while (n3 > 0) {
            Component component = (Component)this.components.get(n4);
            ByteBuf byteBuf = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n3, byteBuf.capacity() - (n - n5));
            byteBuf.setBytes(n - n5, byArray, n2, n6);
            n += n6;
            n2 += n6;
            n3 -= n6;
            ++n4;
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CompositeByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        int n2 = byteBuffer.limit();
        int n3 = byteBuffer.remaining();
        this.checkIndex(n, n3);
        if (n3 == 0) {
            return this;
        }
        int n4 = this.toComponentIndex(n);
        try {
            while (n3 > 0) {
                Component component = (Component)this.components.get(n4);
                ByteBuf byteBuf = component.buf;
                int n5 = component.offset;
                int n6 = Math.min(n3, byteBuf.capacity() - (n - n5));
                byteBuffer.limit(byteBuffer.position() + n6);
                byteBuf.setBytes(n - n5, byteBuffer);
                n += n6;
                n3 -= n6;
                ++n4;
            }
        } finally {
            byteBuffer.limit(n2);
        }
        return this;
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        this.checkSrcIndex(n, n3, n2, byteBuf.capacity());
        if (n3 == 0) {
            return this;
        }
        int n4 = this.toComponentIndex(n);
        while (n3 > 0) {
            Component component = (Component)this.components.get(n4);
            ByteBuf byteBuf2 = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n3, byteBuf2.capacity() - (n - n5));
            byteBuf2.setBytes(n - n5, byteBuf, n2, n6);
            n += n6;
            n2 += n6;
            n3 -= n6;
            ++n4;
        }
        return this;
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return inputStream.read(EmptyArrays.EMPTY_BYTES);
        }
        int n3 = this.toComponentIndex(n);
        int n4 = 0;
        do {
            Component component = (Component)this.components.get(n3);
            ByteBuf byteBuf = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n2, byteBuf.capacity() - (n - n5));
            if (n6 == 0) {
                ++n3;
                continue;
            }
            int n7 = byteBuf.setBytes(n - n5, inputStream, n6);
            if (n7 < 0) {
                if (n4 != 0) break;
                return 1;
            }
            if (n7 == n6) {
                n += n6;
                n2 -= n6;
                n4 += n6;
                ++n3;
                continue;
            }
            n += n7;
            n2 -= n7;
            n4 += n7;
        } while (n2 > 0);
        return n4;
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return scatteringByteChannel.read(EMPTY_NIO_BUFFER);
        }
        int n3 = this.toComponentIndex(n);
        int n4 = 0;
        do {
            Component component = (Component)this.components.get(n3);
            ByteBuf byteBuf = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n2, byteBuf.capacity() - (n - n5));
            if (n6 == 0) {
                ++n3;
                continue;
            }
            int n7 = byteBuf.setBytes(n - n5, scatteringByteChannel, n6);
            if (n7 == 0) break;
            if (n7 < 0) {
                if (n4 != 0) break;
                return 1;
            }
            if (n7 == n6) {
                n += n6;
                n2 -= n6;
                n4 += n6;
                ++n3;
                continue;
            }
            n += n7;
            n2 -= n7;
            n4 += n7;
        } while (n2 > 0);
        return n4;
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return fileChannel.read(EMPTY_NIO_BUFFER, l);
        }
        int n3 = this.toComponentIndex(n);
        int n4 = 0;
        do {
            Component component = (Component)this.components.get(n3);
            ByteBuf byteBuf = component.buf;
            int n5 = component.offset;
            int n6 = Math.min(n2, byteBuf.capacity() - (n - n5));
            if (n6 == 0) {
                ++n3;
                continue;
            }
            int n7 = byteBuf.setBytes(n - n5, fileChannel, l + (long)n4, n6);
            if (n7 == 0) break;
            if (n7 < 0) {
                if (n4 != 0) break;
                return 1;
            }
            if (n7 == n6) {
                n += n6;
                n2 -= n6;
                n4 += n6;
                ++n3;
                continue;
            }
            n += n7;
            n2 -= n7;
            n4 += n7;
        } while (n2 > 0);
        return n4;
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        this.checkIndex(n, n2);
        ByteBuf byteBuf = this.allocBuffer(n2);
        if (n2 != 0) {
            this.copyTo(n, n2, this.toComponentIndex(n), byteBuf);
        }
        return byteBuf;
    }

    private void copyTo(int n, int n2, int n3, ByteBuf byteBuf) {
        int n4 = 0;
        int n5 = n3;
        while (n2 > 0) {
            Component component = (Component)this.components.get(n5);
            ByteBuf byteBuf2 = component.buf;
            int n6 = component.offset;
            int n7 = Math.min(n2, byteBuf2.capacity() - (n - n6));
            byteBuf2.getBytes(n - n6, byteBuf, n4, n7);
            n += n7;
            n4 += n7;
            n2 -= n7;
            ++n5;
        }
        byteBuf.writerIndex(byteBuf.capacity());
    }

    public ByteBuf component(int n) {
        return this.internalComponent(n).duplicate();
    }

    public ByteBuf componentAtOffset(int n) {
        return this.internalComponentAtOffset(n).duplicate();
    }

    public ByteBuf internalComponent(int n) {
        this.checkComponentIndex(n);
        return ((Component)this.components.get((int)n)).buf;
    }

    public ByteBuf internalComponentAtOffset(int n) {
        return this.findComponent((int)n).buf;
    }

    private Component findComponent(int n) {
        this.checkIndex(n);
        int n2 = 0;
        int n3 = this.components.size();
        while (n2 <= n3) {
            int n4 = n2 + n3 >>> 1;
            Component component = (Component)this.components.get(n4);
            if (n >= component.endOffset) {
                n2 = n4 + 1;
                continue;
            }
            if (n < component.offset) {
                n3 = n4 - 1;
                continue;
            }
            if (!$assertionsDisabled && component.length == 0) {
                throw new AssertionError();
            }
            return component;
        }
        throw new Error("should not reach here");
    }

    @Override
    public int nioBufferCount() {
        switch (this.components.size()) {
            case 0: {
                return 0;
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.nioBufferCount();
            }
        }
        int n = 0;
        int n2 = this.components.size();
        for (int i = 0; i < n2; ++i) {
            Component component = (Component)this.components.get(i);
            n += component.buf.nioBufferCount();
        }
        return n;
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        switch (this.components.size()) {
            case 0: {
                return EMPTY_NIO_BUFFER;
            }
            case 1: {
                return ((Component)this.components.get((int)0)).buf.internalNioBuffer(n, n2);
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        ByteBuffer[] byteBufferArray;
        Comparable<ByteBuffer> comparable;
        this.checkIndex(n, n2);
        switch (this.components.size()) {
            case 0: {
                return EMPTY_NIO_BUFFER;
            }
            case 1: {
                comparable = ((Component)this.components.get((int)0)).buf;
                if (((ByteBuf)comparable).nioBufferCount() != 1) break;
                return ((Component)this.components.get((int)0)).buf.nioBuffer(n, n2);
            }
        }
        comparable = ByteBuffer.allocate(n2).order(this.order());
        for (ByteBuffer byteBuffer : byteBufferArray = this.nioBuffers(n, n2)) {
            ((ByteBuffer)comparable).put(byteBuffer);
        }
        ((Buffer)((Object)comparable)).flip();
        return comparable;
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        this.checkIndex(n, n2);
        if (n2 == 0) {
            return new ByteBuffer[]{EMPTY_NIO_BUFFER};
        }
        ArrayList<ByteBuffer> arrayList = new ArrayList<ByteBuffer>(this.components.size());
        int n3 = this.toComponentIndex(n);
        while (n2 > 0) {
            Component component = (Component)this.components.get(n3);
            ByteBuf byteBuf = component.buf;
            int n4 = component.offset;
            int n5 = Math.min(n2, byteBuf.capacity() - (n - n4));
            switch (byteBuf.nioBufferCount()) {
                case 0: {
                    throw new UnsupportedOperationException();
                }
                case 1: {
                    arrayList.add(byteBuf.nioBuffer(n - n4, n5));
                    break;
                }
                default: {
                    Collections.addAll(arrayList, byteBuf.nioBuffers(n - n4, n5));
                }
            }
            n += n5;
            n2 -= n5;
            ++n3;
        }
        return arrayList.toArray(new ByteBuffer[arrayList.size()]);
    }

    public CompositeByteBuf consolidate() {
        this.ensureAccessible();
        int n = this.numComponents();
        if (n <= 1) {
            return this;
        }
        Component component = (Component)this.components.get(n - 1);
        int n2 = component.endOffset;
        ByteBuf byteBuf = this.allocBuffer(n2);
        for (int i = 0; i < n; ++i) {
            Component component2 = (Component)this.components.get(i);
            ByteBuf byteBuf2 = component2.buf;
            byteBuf.writeBytes(byteBuf2);
            component2.freeIfNecessary();
        }
        this.components.clear();
        this.components.add(new Component(byteBuf));
        this.updateComponentOffsets(0);
        return this;
    }

    public CompositeByteBuf consolidate(int n, int n2) {
        this.checkComponentIndex(n, n2);
        if (n2 <= 1) {
            return this;
        }
        int n3 = n + n2;
        Component component = (Component)this.components.get(n3 - 1);
        int n4 = component.endOffset - ((Component)this.components.get((int)n)).offset;
        ByteBuf byteBuf = this.allocBuffer(n4);
        for (int i = n; i < n3; ++i) {
            Component component2 = (Component)this.components.get(i);
            ByteBuf byteBuf2 = component2.buf;
            byteBuf.writeBytes(byteBuf2);
            component2.freeIfNecessary();
        }
        this.components.removeRange(n + 1, n3);
        this.components.set(n, new Component(byteBuf));
        this.updateComponentOffsets(n);
        return this;
    }

    public CompositeByteBuf discardReadComponents() {
        this.ensureAccessible();
        int n = this.readerIndex();
        if (n == 0) {
            return this;
        }
        int n2 = this.writerIndex();
        if (n == n2 && n2 == this.capacity()) {
            int n3 = this.components.size();
            for (int i = 0; i < n3; ++i) {
                ((Component)this.components.get(i)).freeIfNecessary();
            }
            this.components.clear();
            this.setIndex(0, 0);
            this.adjustMarkers(n);
            return this;
        }
        int n4 = this.toComponentIndex(n);
        for (int i = 0; i < n4; ++i) {
            ((Component)this.components.get(i)).freeIfNecessary();
        }
        this.components.removeRange(0, n4);
        Component component = (Component)this.components.get(0);
        int n5 = component.offset;
        this.updateComponentOffsets(0);
        this.setIndex(n - n5, n2 - n5);
        this.adjustMarkers(n5);
        return this;
    }

    @Override
    public CompositeByteBuf discardReadBytes() {
        this.ensureAccessible();
        int n = this.readerIndex();
        if (n == 0) {
            return this;
        }
        int n2 = this.writerIndex();
        if (n == n2 && n2 == this.capacity()) {
            int n3 = this.components.size();
            for (int i = 0; i < n3; ++i) {
                ((Component)this.components.get(i)).freeIfNecessary();
            }
            this.components.clear();
            this.setIndex(0, 0);
            this.adjustMarkers(n);
            return this;
        }
        int n4 = this.toComponentIndex(n);
        for (int i = 0; i < n4; ++i) {
            ((Component)this.components.get(i)).freeIfNecessary();
        }
        Component component = (Component)this.components.get(n4);
        int n5 = n - component.offset;
        if (n5 == component.length) {
            ++n4;
        } else {
            Component component2 = new Component(component.buf.slice(n5, component.length - n5));
            this.components.set(n4, component2);
        }
        this.components.removeRange(0, n4);
        this.updateComponentOffsets(0);
        this.setIndex(0, n2 - n);
        this.adjustMarkers(n);
        return this;
    }

    private ByteBuf allocBuffer(int n) {
        return this.direct ? this.alloc().directBuffer(n) : this.alloc().heapBuffer(n);
    }

    @Override
    public String toString() {
        String string = super.toString();
        string = string.substring(0, string.length() - 1);
        return string + ", components=" + this.components.size() + ')';
    }

    @Override
    public CompositeByteBuf readerIndex(int n) {
        return (CompositeByteBuf)super.readerIndex(n);
    }

    @Override
    public CompositeByteBuf writerIndex(int n) {
        return (CompositeByteBuf)super.writerIndex(n);
    }

    @Override
    public CompositeByteBuf setIndex(int n, int n2) {
        return (CompositeByteBuf)super.setIndex(n, n2);
    }

    @Override
    public CompositeByteBuf clear() {
        return (CompositeByteBuf)super.clear();
    }

    @Override
    public CompositeByteBuf markReaderIndex() {
        return (CompositeByteBuf)super.markReaderIndex();
    }

    @Override
    public CompositeByteBuf resetReaderIndex() {
        return (CompositeByteBuf)super.resetReaderIndex();
    }

    @Override
    public CompositeByteBuf markWriterIndex() {
        return (CompositeByteBuf)super.markWriterIndex();
    }

    @Override
    public CompositeByteBuf resetWriterIndex() {
        return (CompositeByteBuf)super.resetWriterIndex();
    }

    @Override
    public CompositeByteBuf ensureWritable(int n) {
        return (CompositeByteBuf)super.ensureWritable(n);
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuf byteBuf) {
        return (CompositeByteBuf)super.getBytes(n, byteBuf);
    }

    @Override
    public CompositeByteBuf getBytes(int n, ByteBuf byteBuf, int n2) {
        return (CompositeByteBuf)super.getBytes(n, byteBuf, n2);
    }

    @Override
    public CompositeByteBuf getBytes(int n, byte[] byArray) {
        return (CompositeByteBuf)super.getBytes(n, byArray);
    }

    @Override
    public CompositeByteBuf setBoolean(int n, boolean bl) {
        return (CompositeByteBuf)super.setBoolean(n, bl);
    }

    @Override
    public CompositeByteBuf setChar(int n, int n2) {
        return (CompositeByteBuf)super.setChar(n, n2);
    }

    @Override
    public CompositeByteBuf setFloat(int n, float f) {
        return (CompositeByteBuf)super.setFloat(n, f);
    }

    @Override
    public CompositeByteBuf setDouble(int n, double d) {
        return (CompositeByteBuf)super.setDouble(n, d);
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuf byteBuf) {
        return (CompositeByteBuf)super.setBytes(n, byteBuf);
    }

    @Override
    public CompositeByteBuf setBytes(int n, ByteBuf byteBuf, int n2) {
        return (CompositeByteBuf)super.setBytes(n, byteBuf, n2);
    }

    @Override
    public CompositeByteBuf setBytes(int n, byte[] byArray) {
        return (CompositeByteBuf)super.setBytes(n, byArray);
    }

    @Override
    public CompositeByteBuf setZero(int n, int n2) {
        return (CompositeByteBuf)super.setZero(n, n2);
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        return (CompositeByteBuf)super.readBytes(byteBuf);
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int n) {
        return (CompositeByteBuf)super.readBytes(byteBuf, n);
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuf byteBuf, int n, int n2) {
        return (CompositeByteBuf)super.readBytes(byteBuf, n, n2);
    }

    @Override
    public CompositeByteBuf readBytes(byte[] byArray) {
        return (CompositeByteBuf)super.readBytes(byArray);
    }

    @Override
    public CompositeByteBuf readBytes(byte[] byArray, int n, int n2) {
        return (CompositeByteBuf)super.readBytes(byArray, n, n2);
    }

    @Override
    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        return (CompositeByteBuf)super.readBytes(byteBuffer);
    }

    @Override
    public CompositeByteBuf readBytes(OutputStream outputStream, int n) throws IOException {
        return (CompositeByteBuf)super.readBytes(outputStream, n);
    }

    @Override
    public CompositeByteBuf skipBytes(int n) {
        return (CompositeByteBuf)super.skipBytes(n);
    }

    @Override
    public CompositeByteBuf writeBoolean(boolean bl) {
        return (CompositeByteBuf)super.writeBoolean(bl);
    }

    @Override
    public CompositeByteBuf writeByte(int n) {
        return (CompositeByteBuf)super.writeByte(n);
    }

    @Override
    public CompositeByteBuf writeShort(int n) {
        return (CompositeByteBuf)super.writeShort(n);
    }

    @Override
    public CompositeByteBuf writeMedium(int n) {
        return (CompositeByteBuf)super.writeMedium(n);
    }

    @Override
    public CompositeByteBuf writeInt(int n) {
        return (CompositeByteBuf)super.writeInt(n);
    }

    @Override
    public CompositeByteBuf writeLong(long l) {
        return (CompositeByteBuf)super.writeLong(l);
    }

    @Override
    public CompositeByteBuf writeChar(int n) {
        return (CompositeByteBuf)super.writeChar(n);
    }

    @Override
    public CompositeByteBuf writeFloat(float f) {
        return (CompositeByteBuf)super.writeFloat(f);
    }

    @Override
    public CompositeByteBuf writeDouble(double d) {
        return (CompositeByteBuf)super.writeDouble(d);
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        return (CompositeByteBuf)super.writeBytes(byteBuf);
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int n) {
        return (CompositeByteBuf)super.writeBytes(byteBuf, n);
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int n, int n2) {
        return (CompositeByteBuf)super.writeBytes(byteBuf, n, n2);
    }

    @Override
    public CompositeByteBuf writeBytes(byte[] byArray) {
        return (CompositeByteBuf)super.writeBytes(byArray);
    }

    @Override
    public CompositeByteBuf writeBytes(byte[] byArray, int n, int n2) {
        return (CompositeByteBuf)super.writeBytes(byArray, n, n2);
    }

    @Override
    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        return (CompositeByteBuf)super.writeBytes(byteBuffer);
    }

    @Override
    public CompositeByteBuf writeZero(int n) {
        return (CompositeByteBuf)super.writeZero(n);
    }

    @Override
    public CompositeByteBuf retain(int n) {
        return (CompositeByteBuf)super.retain(n);
    }

    @Override
    public CompositeByteBuf retain() {
        return (CompositeByteBuf)super.retain();
    }

    @Override
    public CompositeByteBuf touch() {
        return this;
    }

    @Override
    public CompositeByteBuf touch(Object object) {
        return this;
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return this.nioBuffers(this.readerIndex(), this.readableBytes());
    }

    @Override
    public CompositeByteBuf discardSomeReadBytes() {
        return this.discardReadComponents();
    }

    @Override
    protected void deallocate() {
        if (this.freed) {
            return;
        }
        this.freed = true;
        int n = this.components.size();
        for (int i = 0; i < n; ++i) {
            ((Component)this.components.get(i)).freeIfNecessary();
        }
    }

    @Override
    public ByteBuf unwrap() {
        return null;
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

    static ComponentList access$100(CompositeByteBuf compositeByteBuf) {
        return compositeByteBuf.components;
    }

    static {
        $assertionsDisabled = !CompositeByteBuf.class.desiredAssertionStatus();
        EMPTY_NIO_BUFFER = Unpooled.EMPTY_BUFFER.nioBuffer();
        EMPTY_ITERATOR = Collections.emptyList().iterator();
    }

    private static final class ComponentList
    extends ArrayList<Component> {
        ComponentList(int n) {
            super(n);
        }

        @Override
        public void removeRange(int n, int n2) {
            super.removeRange(n, n2);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class CompositeByteBufIterator
    implements Iterator<ByteBuf> {
        private final int size;
        private int index;
        final CompositeByteBuf this$0;

        private CompositeByteBufIterator(CompositeByteBuf compositeByteBuf) {
            this.this$0 = compositeByteBuf;
            this.size = CompositeByteBuf.access$100(this.this$0).size();
        }

        @Override
        public boolean hasNext() {
            return this.size > this.index;
        }

        @Override
        public ByteBuf next() {
            if (this.size != CompositeByteBuf.access$100(this.this$0).size()) {
                throw new ConcurrentModificationException();
            }
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            try {
                return ((Component)CompositeByteBuf.access$100((CompositeByteBuf)this.this$0).get((int)this.index++)).buf;
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Read-Only");
        }

        @Override
        public Object next() {
            return this.next();
        }

        CompositeByteBufIterator(CompositeByteBuf compositeByteBuf, 1 var2_2) {
            this(compositeByteBuf);
        }
    }

    private static final class Component {
        final ByteBuf buf;
        final int length;
        int offset;
        int endOffset;

        Component(ByteBuf byteBuf) {
            this.buf = byteBuf;
            this.length = byteBuf.readableBytes();
        }

        void freeIfNecessary() {
            this.buf.release();
        }
    }
}

