/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledDuplicatedByteBuf;
import io.netty.buffer.PooledSlicedByteBuf;
import io.netty.buffer.SimpleLeakAwareByteBuf;
import io.netty.buffer.UnpooledDuplicatedByteBuf;
import io.netty.buffer.UnpooledSlicedByteBuf;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCounted;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AbstractPooledDerivedByteBuf
extends AbstractReferenceCountedByteBuf {
    private final Recycler.Handle<AbstractPooledDerivedByteBuf> recyclerHandle;
    private AbstractByteBuf rootParent;
    private ByteBuf parent;
    static final boolean $assertionsDisabled = !AbstractPooledDerivedByteBuf.class.desiredAssertionStatus();

    AbstractPooledDerivedByteBuf(Recycler.Handle<? extends AbstractPooledDerivedByteBuf> handle) {
        super(0);
        this.recyclerHandle = handle;
    }

    final void parent(ByteBuf byteBuf) {
        if (!$assertionsDisabled && !(byteBuf instanceof SimpleLeakAwareByteBuf)) {
            throw new AssertionError();
        }
        this.parent = byteBuf;
    }

    @Override
    public final AbstractByteBuf unwrap() {
        return this.rootParent;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final <U extends AbstractPooledDerivedByteBuf> U init(AbstractByteBuf abstractByteBuf, ByteBuf byteBuf, int n, int n2, int n3) {
        byteBuf.retain();
        this.parent = byteBuf;
        this.rootParent = abstractByteBuf;
        try {
            this.maxCapacity(n3);
            this.setIndex0(n, n2);
            this.setRefCnt(1);
            AbstractPooledDerivedByteBuf abstractPooledDerivedByteBuf = this;
            byteBuf = null;
            AbstractPooledDerivedByteBuf abstractPooledDerivedByteBuf2 = abstractPooledDerivedByteBuf;
            return (U)abstractPooledDerivedByteBuf2;
        } finally {
            if (byteBuf != null) {
                this.rootParent = null;
                this.parent = null;
                byteBuf.release();
            }
        }
    }

    @Override
    protected final void deallocate() {
        ByteBuf byteBuf = this.parent;
        this.recyclerHandle.recycle(this);
        byteBuf.release();
    }

    @Override
    public final ByteBufAllocator alloc() {
        return this.unwrap().alloc();
    }

    @Override
    @Deprecated
    public final ByteOrder order() {
        return this.unwrap().order();
    }

    @Override
    public boolean isReadOnly() {
        return this.unwrap().isReadOnly();
    }

    @Override
    public final boolean isDirect() {
        return this.unwrap().isDirect();
    }

    @Override
    public boolean hasArray() {
        return this.unwrap().hasArray();
    }

    @Override
    public byte[] array() {
        return this.unwrap().array();
    }

    @Override
    public boolean hasMemoryAddress() {
        return this.unwrap().hasMemoryAddress();
    }

    @Override
    public final int nioBufferCount() {
        return this.unwrap().nioBufferCount();
    }

    @Override
    public final ByteBuffer internalNioBuffer(int n, int n2) {
        return this.nioBuffer(n, n2);
    }

    @Override
    public final ByteBuf retainedSlice() {
        int n = this.readerIndex();
        return this.retainedSlice(n, this.writerIndex() - n);
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        this.ensureAccessible();
        return new PooledNonRetainedSlicedByteBuf(this, this.unwrap(), n, n2);
    }

    final ByteBuf duplicate0() {
        this.ensureAccessible();
        return new PooledNonRetainedDuplicateByteBuf(this, this.unwrap());
    }

    @Override
    public ByteBuf unwrap() {
        return this.unwrap();
    }

    private static final class PooledNonRetainedSlicedByteBuf
    extends UnpooledSlicedByteBuf {
        private final ReferenceCounted referenceCountDelegate;

        PooledNonRetainedSlicedByteBuf(ReferenceCounted referenceCounted, AbstractByteBuf abstractByteBuf, int n, int n2) {
            super(abstractByteBuf, n, n2);
            this.referenceCountDelegate = referenceCounted;
        }

        @Override
        int refCnt0() {
            return this.referenceCountDelegate.refCnt();
        }

        @Override
        ByteBuf retain0() {
            this.referenceCountDelegate.retain();
            return this;
        }

        @Override
        ByteBuf retain0(int n) {
            this.referenceCountDelegate.retain(n);
            return this;
        }

        @Override
        ByteBuf touch0() {
            this.referenceCountDelegate.touch();
            return this;
        }

        @Override
        ByteBuf touch0(Object object) {
            this.referenceCountDelegate.touch(object);
            return this;
        }

        @Override
        boolean release0() {
            return this.referenceCountDelegate.release();
        }

        @Override
        boolean release0(int n) {
            return this.referenceCountDelegate.release(n);
        }

        @Override
        public ByteBuf duplicate() {
            this.ensureAccessible();
            return new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this.unwrap()).setIndex(this.idx(this.readerIndex()), this.idx(this.writerIndex()));
        }

        @Override
        public ByteBuf retainedDuplicate() {
            return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.idx(this.readerIndex()), this.idx(this.writerIndex()));
        }

        @Override
        public ByteBuf slice(int n, int n2) {
            this.checkIndex(n, n2);
            return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, this.unwrap(), this.idx(n), n2);
        }

        @Override
        public ByteBuf retainedSlice() {
            return this.retainedSlice(0, this.capacity());
        }

        @Override
        public ByteBuf retainedSlice(int n, int n2) {
            return PooledSlicedByteBuf.newInstance(this.unwrap(), this, this.idx(n), n2);
        }
    }

    private static final class PooledNonRetainedDuplicateByteBuf
    extends UnpooledDuplicatedByteBuf {
        private final ReferenceCounted referenceCountDelegate;

        PooledNonRetainedDuplicateByteBuf(ReferenceCounted referenceCounted, AbstractByteBuf abstractByteBuf) {
            super(abstractByteBuf);
            this.referenceCountDelegate = referenceCounted;
        }

        @Override
        int refCnt0() {
            return this.referenceCountDelegate.refCnt();
        }

        @Override
        ByteBuf retain0() {
            this.referenceCountDelegate.retain();
            return this;
        }

        @Override
        ByteBuf retain0(int n) {
            this.referenceCountDelegate.retain(n);
            return this;
        }

        @Override
        ByteBuf touch0() {
            this.referenceCountDelegate.touch();
            return this;
        }

        @Override
        ByteBuf touch0(Object object) {
            this.referenceCountDelegate.touch(object);
            return this;
        }

        @Override
        boolean release0() {
            return this.referenceCountDelegate.release();
        }

        @Override
        boolean release0(int n) {
            return this.referenceCountDelegate.release(n);
        }

        @Override
        public ByteBuf duplicate() {
            this.ensureAccessible();
            return new PooledNonRetainedDuplicateByteBuf(this.referenceCountDelegate, this);
        }

        @Override
        public ByteBuf retainedDuplicate() {
            return PooledDuplicatedByteBuf.newInstance(this.unwrap(), this, this.readerIndex(), this.writerIndex());
        }

        @Override
        public ByteBuf slice(int n, int n2) {
            this.checkIndex(n, n2);
            return new PooledNonRetainedSlicedByteBuf(this.referenceCountDelegate, this.unwrap(), n, n2);
        }

        @Override
        public ByteBuf retainedSlice() {
            return this.retainedSlice(this.readerIndex(), this.capacity());
        }

        @Override
        public ByteBuf retainedSlice(int n, int n2) {
            return PooledSlicedByteBuf.newInstance(this.unwrap(), this, n, n2);
        }
    }
}

