/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;
import org.apache.commons.lang3.concurrent.CircuitBreakingException;

public class ThresholdCircuitBreaker
extends AbstractCircuitBreaker<Long> {
    private static final long INITIAL_COUNT = 0L;
    private final long threshold;
    private final AtomicLong used = new AtomicLong(0L);

    public ThresholdCircuitBreaker(long l) {
        this.threshold = l;
    }

    public long getThreshold() {
        return this.threshold;
    }

    @Override
    public boolean checkState() throws CircuitBreakingException {
        return this.isOpen();
    }

    @Override
    public void close() {
        super.close();
        this.used.set(0L);
    }

    @Override
    public boolean incrementAndCheckState(Long l) throws CircuitBreakingException {
        long l2;
        if (this.threshold == 0L) {
            this.open();
        }
        if ((l2 = this.used.addAndGet(l)) > this.threshold) {
            this.open();
        }
        return this.checkState();
    }

    @Override
    public boolean incrementAndCheckState(Object object) {
        return this.incrementAndCheckState((Long)object);
    }
}

