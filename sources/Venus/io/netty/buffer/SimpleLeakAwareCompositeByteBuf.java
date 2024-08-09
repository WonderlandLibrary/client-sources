/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.SimpleLeakAwareByteBuf;
import io.netty.buffer.WrappedCompositeByteBuf;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.ObjectUtil;
import java.nio.ByteOrder;

class SimpleLeakAwareCompositeByteBuf
extends WrappedCompositeByteBuf {
    final ResourceLeakTracker<ByteBuf> leak;
    static final boolean $assertionsDisabled = !SimpleLeakAwareCompositeByteBuf.class.desiredAssertionStatus();

    SimpleLeakAwareCompositeByteBuf(CompositeByteBuf compositeByteBuf, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        super(compositeByteBuf);
        this.leak = ObjectUtil.checkNotNull(resourceLeakTracker, "leak");
    }

    @Override
    public boolean release() {
        ByteBuf byteBuf = this.unwrap();
        if (super.release()) {
            this.closeLeak(byteBuf);
            return false;
        }
        return true;
    }

    @Override
    public boolean release(int n) {
        ByteBuf byteBuf = this.unwrap();
        if (super.release(n)) {
            this.closeLeak(byteBuf);
            return false;
        }
        return true;
    }

    private void closeLeak(ByteBuf byteBuf) {
        boolean bl = this.leak.close(byteBuf);
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        if (this.order() == byteOrder) {
            return this;
        }
        return this.newLeakAwareByteBuf(super.order(byteOrder));
    }

    @Override
    public ByteBuf slice() {
        return this.newLeakAwareByteBuf(super.slice());
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.newLeakAwareByteBuf(super.retainedSlice());
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return this.newLeakAwareByteBuf(super.slice(n, n2));
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.newLeakAwareByteBuf(super.retainedSlice(n, n2));
    }

    @Override
    public ByteBuf duplicate() {
        return this.newLeakAwareByteBuf(super.duplicate());
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.newLeakAwareByteBuf(super.retainedDuplicate());
    }

    @Override
    public ByteBuf readSlice(int n) {
        return this.newLeakAwareByteBuf(super.readSlice(n));
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.newLeakAwareByteBuf(super.readRetainedSlice(n));
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.newLeakAwareByteBuf(super.asReadOnly());
    }

    private SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf) {
        return this.newLeakAwareByteBuf(byteBuf, this.unwrap(), this.leak);
    }

    protected SimpleLeakAwareByteBuf newLeakAwareByteBuf(ByteBuf byteBuf, ByteBuf byteBuf2, ResourceLeakTracker<ByteBuf> resourceLeakTracker) {
        return new SimpleLeakAwareByteBuf(byteBuf, byteBuf2, resourceLeakTracker);
    }
}

