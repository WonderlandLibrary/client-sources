/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractReferenceCounted
implements ReferenceCounted {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCounted> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCounted.class, "refCnt");
    private volatile int refCnt = 1;

    @Override
    public final int refCnt() {
        return this.refCnt;
    }

    protected final void setRefCnt(int n) {
        refCntUpdater.set(this, n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain0(1);
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain0(ObjectUtil.checkPositive(n, "increment"));
    }

    private ReferenceCounted retain0(int n) {
        int n2 = refCntUpdater.getAndAdd(this, n);
        if (n2 <= 0 || n2 + n < n2) {
            refCntUpdater.getAndAdd(this, -n);
            throw new IllegalReferenceCountException(n2, n);
        }
        return this;
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch(null);
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
}

