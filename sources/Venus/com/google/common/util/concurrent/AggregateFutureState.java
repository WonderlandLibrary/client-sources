/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible(emulated=true)
abstract class AggregateFutureState {
    private volatile Set<Throwable> seenExceptions = null;
    private volatile int remaining;
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log;

    AggregateFutureState(int n) {
        this.remaining = n;
    }

    final Set<Throwable> getOrInitSeenExceptions() {
        Set<Throwable> set = this.seenExceptions;
        if (set == null) {
            set = Sets.newConcurrentHashSet();
            this.addInitialException(set);
            ATOMIC_HELPER.compareAndSetSeenExceptions(this, null, set);
            set = this.seenExceptions;
        }
        return set;
    }

    abstract void addInitialException(Set<Throwable> var1);

    final int decrementRemainingAndGet() {
        return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
    }

    static Set access$200(AggregateFutureState aggregateFutureState) {
        return aggregateFutureState.seenExceptions;
    }

    static Set access$202(AggregateFutureState aggregateFutureState, Set set) {
        aggregateFutureState.seenExceptions = set;
        return aggregateFutureState.seenExceptions;
    }

    static int access$310(AggregateFutureState aggregateFutureState) {
        return aggregateFutureState.remaining--;
    }

    static int access$300(AggregateFutureState aggregateFutureState) {
        return aggregateFutureState.remaining;
    }

    static {
        AtomicHelper atomicHelper;
        log = Logger.getLogger(AggregateFutureState.class.getName());
        try {
            atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
        } catch (Throwable throwable) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", throwable);
            atomicHelper = new SynchronizedAtomicHelper(null);
        }
        ATOMIC_HELPER = atomicHelper;
    }

    private static final class SynchronizedAtomicHelper
    extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super(null);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2) {
            AggregateFutureState aggregateFutureState2 = aggregateFutureState;
            synchronized (aggregateFutureState2) {
                if (AggregateFutureState.access$200(aggregateFutureState) == set) {
                    AggregateFutureState.access$202(aggregateFutureState, set2);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        int decrementAndGetRemainingCount(AggregateFutureState aggregateFutureState) {
            AggregateFutureState aggregateFutureState2 = aggregateFutureState;
            synchronized (aggregateFutureState2) {
                AggregateFutureState.access$310(aggregateFutureState);
                return AggregateFutureState.access$300(aggregateFutureState);
            }
        }

        SynchronizedAtomicHelper(1 var1_1) {
            this();
        }
    }

    private static final class SafeAtomicHelper
    extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AggregateFutureState, Set<Throwable>> seenExceptionsUpdater;
        final AtomicIntegerFieldUpdater<AggregateFutureState> remainingCountUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater) {
            super(null);
            this.seenExceptionsUpdater = atomicReferenceFieldUpdater;
            this.remainingCountUpdater = atomicIntegerFieldUpdater;
        }

        @Override
        void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2) {
            this.seenExceptionsUpdater.compareAndSet(aggregateFutureState, set, set2);
        }

        @Override
        int decrementAndGetRemainingCount(AggregateFutureState aggregateFutureState) {
            return this.remainingCountUpdater.decrementAndGet(aggregateFutureState);
        }
    }

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract void compareAndSetSeenExceptions(AggregateFutureState var1, Set<Throwable> var2, Set<Throwable> var3);

        abstract int decrementAndGetRemainingCount(AggregateFutureState var1);

        AtomicHelper(1 var1_1) {
            this();
        }
    }
}

