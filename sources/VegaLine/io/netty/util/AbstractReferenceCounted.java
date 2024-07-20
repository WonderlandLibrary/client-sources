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

    protected final void setRefCnt(int refCnt) {
        this.refCnt = refCnt;
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain0(1);
    }

    @Override
    public ReferenceCounted retain(int increment) {
        return this.retain0(ObjectUtil.checkPositive(increment, "increment"));
    }

    private ReferenceCounted retain0(int increment) {
        int nextCnt;
        int refCnt;
        do {
            if ((nextCnt = (refCnt = this.refCnt) + increment) > increment) continue;
            throw new IllegalReferenceCountException(refCnt, increment);
        } while (!refCntUpdater.compareAndSet(this, refCnt, nextCnt));
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
    public boolean release(int decrement) {
        return this.release0(ObjectUtil.checkPositive(decrement, "decrement"));
    }

    private boolean release0(int decrement) {
        int refCnt;
        do {
            if ((refCnt = this.refCnt) >= decrement) continue;
            throw new IllegalReferenceCountException(refCnt, -decrement);
        } while (!refCntUpdater.compareAndSet(this, refCnt, refCnt - decrement));
        if (refCnt == decrement) {
            this.deallocate();
            return true;
        }
        return false;
    }

    protected abstract void deallocate();
}

