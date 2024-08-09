/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractReferenceCountedByteBuf
extends AbstractByteBuf {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
    private volatile int refCnt;

    protected AbstractReferenceCountedByteBuf(int n) {
        super(n);
        refCntUpdater.set(this, 1);
    }

    @Override
    public int refCnt() {
        return this.refCnt;
    }

    protected final void setRefCnt(int n) {
        refCntUpdater.set(this, n);
    }

    @Override
    public ByteBuf retain() {
        return this.retain0(1);
    }

    @Override
    public ByteBuf retain(int n) {
        return this.retain0(ObjectUtil.checkPositive(n, "increment"));
    }

    private ByteBuf retain0(int n) {
        int n2 = refCntUpdater.getAndAdd(this, n);
        if (n2 <= 0 || n2 + n < n2) {
            refCntUpdater.getAndAdd(this, -n);
            throw new IllegalReferenceCountException(n2, n);
        }
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
        return this.release0(1);
    }

    @Override
    public boolean release(int n) {
        return this.release0(ObjectUtil.checkPositive(n, "decrement"));
    }

    private boolean release0(int n) {
        int n2 = refCntUpdater.getAndAdd(this, -n);
        if (n2 == n) {
            this.deallocate();
            return false;
        }
        if (n2 < n || n2 - n > n2) {
            refCntUpdater.getAndAdd(this, n);
            throw new IllegalReferenceCountException(n2, -n);
        }
        return true;
    }

    protected abstract void deallocate();

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
}

