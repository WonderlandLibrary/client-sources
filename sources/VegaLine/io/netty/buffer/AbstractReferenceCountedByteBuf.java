/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public abstract class AbstractReferenceCountedByteBuf
extends AbstractByteBuf {
    private static final AtomicIntegerFieldUpdater<AbstractReferenceCountedByteBuf> refCntUpdater = AtomicIntegerFieldUpdater.newUpdater(AbstractReferenceCountedByteBuf.class, "refCnt");
    private volatile int refCnt = 1;

    protected AbstractReferenceCountedByteBuf(int maxCapacity) {
        super(maxCapacity);
    }

    @Override
    public int refCnt() {
        return this.refCnt;
    }

    protected final void setRefCnt(int refCnt) {
        this.refCnt = refCnt;
    }

    @Override
    public ByteBuf retain() {
        return this.retain0(1);
    }

    @Override
    public ByteBuf retain(int increment) {
        return this.retain0(ObjectUtil.checkPositive(increment, "increment"));
    }

    private ByteBuf retain0(int increment) {
        int nextCnt;
        int refCnt;
        do {
            if ((nextCnt = (refCnt = this.refCnt) + increment) > increment) continue;
            throw new IllegalReferenceCountException(refCnt, increment);
        } while (!refCntUpdater.compareAndSet(this, refCnt, nextCnt));
        return this;
    }

    @Override
    public ByteBuf touch() {
        return this;
    }

    @Override
    public ByteBuf touch(Object hint) {
        return this;
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

