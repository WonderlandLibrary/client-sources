/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractPooledDerivedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.WrappedByteBuf;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.ObjectUtil;
import java.nio.ByteOrder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class SimpleLeakAwareByteBuf
extends WrappedByteBuf {
    private final ByteBuf trackedByteBuf;
    final ResourceLeakTracker<ByteBuf> leak;
    static final boolean $assertionsDisabled = !SimpleLeakAwareByteBuf.class.desiredAssertionStatus();

    SimpleLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        super(byteBuf);
        this.trackedByteBuf = ObjectUtil.checkNotNull(byteBuf2, "trackedByteBuf");
        this.leak = ObjectUtil.checkNotNull(resourceLeakTracker, "leak");
    }

    SimpleLeakAwareByteBuf(ByteBuf byteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        this(byteBuf, byteBuf, resourceLeakTracker);
    }

    @Override
    public ByteBuf slice() {
        return this.newSharedLeakAwareByteBuf(super.slice());
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.unwrappedDerived(super.retainedSlice());
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.unwrappedDerived(super.retainedSlice(n, n2));
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.unwrappedDerived(super.retainedDuplicate());
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.unwrappedDerived(super.readRetainedSlice(n));
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return this.newSharedLeakAwareByteBuf(super.slice(n, n2));
    }

    @Override
    public ByteBuf duplicate() {
        return this.newSharedLeakAwareByteBuf(super.duplicate());
    }

    @Override
    public ByteBuf readSlice(int n) {
        return this.newSharedLeakAwareByteBuf(super.readSlice(n));
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.newSharedLeakAwareByteBuf(super.asReadOnly());
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
        if (super.release()) {
            this.closeLeak();
            return false;
        }
        return true;
    }

    @Override
    public boolean release(int n) {
        if (super.release(n)) {
            this.closeLeak();
            return false;
        }
        return true;
    }

    private void closeLeak() {
        boolean bl = this.leak.close(this.trackedByteBuf);
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        if (this.order() == byteOrder) {
            return this;
        }
        return this.newSharedLeakAwareByteBuf(super.order(byteOrder));
    }

    private ByteBuf unwrappedDerived(ByteBuf byteBuf) {
        ByteBuf byteBuf2 = SimpleLeakAwareByteBuf.unwrapSwapped(byteBuf);
        if (byteBuf2 instanceof AbstractPooledDerivedByteBuf) {
            ((AbstractPooledDerivedByteBuf)byteBuf2).parent(this);
            ResourceLeakTracker<ByteBuf> resourceLeakTracker = AbstractByteBuf.leakDetector.track(byteBuf);
            if (resourceLeakTracker == null) {
                return byteBuf;
            }
            return this.newLeakAwareByteBuf(byteBuf, resourceLeakTracker);
        }
        return this.newSharedLeakAwareByteBuf(byteBuf);
    }

    private static ByteBuf unwrapSwapped(ByteBuf byteBuf) {
        if (byteBuf instanceof SwappedByteBuf) {
            while ((byteBuf = byteBuf.unwrap()) instanceof SwappedByteBuf) {
            }
            return byteBuf;
        }
        return byteBuf;
    }

    private SimpleLeakAwareByteBuf newSharedLeakAwareByteBuf(ByteBuf byteBuf) {
        return this.newLeakAwareByteBuf(byteBuf, this.trackedByteBuf, this.leak);
    }

    private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return this.newLeakAwareByteBuf(byteBuf, byteBuf, resourceLeakTracker);
    }

    protected SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return new SimpleLeakAwareByteBuf(byteBuf, byteBuf2, resourceLeakTracker);
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }
}

