/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BooleanSupplier;
import javax.annotation.concurrent.GuardedBy;

@Beta
@GwtIncompatible
public final class Monitor {
    private final boolean fair;
    private final ReentrantLock lock;
    @GuardedBy(value="lock")
    private Guard activeGuards = null;

    public Monitor() {
        this(false);
    }

    public Monitor(boolean bl) {
        this.fair = bl;
        this.lock = new ReentrantLock(bl);
    }

    public Guard newGuard(BooleanSupplier booleanSupplier) {
        Preconditions.checkNotNull(booleanSupplier, "isSatisfied");
        return new Guard(this, this, booleanSupplier){
            final BooleanSupplier val$isSatisfied;
            final Monitor this$0;
            {
                this.this$0 = monitor;
                this.val$isSatisfied = booleanSupplier;
                super(monitor2);
            }

            @Override
            public boolean isSatisfied() {
                return this.val$isSatisfied.getAsBoolean();
            }
        };
    }

    public void enter() {
        this.lock.lock();
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enter(long l, TimeUnit timeUnit) {
        long l2 = Monitor.toSafeNanos(l, timeUnit);
        ReentrantLock reentrantLock = this.lock;
        if (!this.fair && reentrantLock.tryLock()) {
            return false;
        }
        boolean bl = Thread.interrupted();
        try {
            long l3 = System.nanoTime();
            long l4 = l2;
            while (true) {
                try {
                    boolean bl2 = reentrantLock.tryLock(l4, TimeUnit.NANOSECONDS);
                    return bl2;
                } catch (InterruptedException interruptedException) {
                    bl = true;
                    l4 = Monitor.remainingNanos(l3, l2);
                    continue;
                }
                break;
            }
        } finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean enterInterruptibly(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(l, timeUnit);
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        boolean bl = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lockInterruptibly();
        boolean bl2 = false;
        try {
            if (!guard.isSatisfied()) {
                this.await(guard, bl);
            }
            bl2 = true;
        } finally {
            if (!bl2) {
                this.leave();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        boolean bl = reentrantLock.isHeldByCurrentThread();
        reentrantLock.lock();
        boolean bl2 = false;
        try {
            if (!guard.isSatisfied()) {
                this.awaitUninterruptibly(guard, bl);
            }
            bl2 = true;
        } finally {
            if (!bl2) {
                this.leave();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean enterWhen(Guard guard, long l, TimeUnit timeUnit) throws InterruptedException {
        long l2;
        boolean bl;
        ReentrantLock reentrantLock;
        long l3;
        block19: {
            block18: {
                l3 = Monitor.toSafeNanos(l, timeUnit);
                if (guard.monitor != this) {
                    throw new IllegalMonitorStateException();
                }
                reentrantLock = this.lock;
                bl = reentrantLock.isHeldByCurrentThread();
                l2 = 0L;
                if (this.fair) break block18;
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                if (reentrantLock.tryLock()) break block19;
            }
            l2 = Monitor.initNanoTime(l3);
            if (!reentrantLock.tryLock(l, timeUnit)) {
                return true;
            }
        }
        boolean bl2 = false;
        boolean bl3 = true;
        try {
            bl2 = guard.isSatisfied() || this.awaitNanos(guard, l2 == 0L ? l3 : Monitor.remainingNanos(l2, l3), bl);
            bl3 = false;
            boolean bl4 = bl2;
            return bl4;
        } finally {
            if (!bl2) {
                try {
                    if (bl3 && !bl) {
                        this.signalNextWaiter();
                    }
                } finally {
                    reentrantLock.unlock();
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean enterWhenUninterruptibly(Guard guard, long l, TimeUnit timeUnit) {
        long l2 = Monitor.toSafeNanos(l, timeUnit);
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        long l3 = 0L;
        boolean bl = reentrantLock.isHeldByCurrentThread();
        boolean bl2 = Thread.interrupted();
        if (this.fair || !reentrantLock.tryLock()) {
            l3 = Monitor.initNanoTime(l2);
            long l4 = l2;
            while (true) {
                try {
                    if (!reentrantLock.tryLock(l4, TimeUnit.NANOSECONDS)) {
                        boolean bl3 = false;
                        return bl3;
                    }
                } catch (InterruptedException interruptedException) {
                    bl2 = true;
                    l4 = Monitor.remainingNanos(l3, l2);
                    continue;
                }
                break;
            }
        }
        boolean bl4 = false;
        while (true) {
            try {
                if (guard.isSatisfied()) {
                    bl4 = true;
                } else {
                    long l5;
                    if (l3 == 0L) {
                        l3 = Monitor.initNanoTime(l2);
                        l5 = l2;
                    } else {
                        l5 = Monitor.remainingNanos(l3, l2);
                    }
                    bl4 = this.awaitNanos(guard, l5, bl);
                }
                boolean bl5 = bl4;
                return bl5;
            } catch (InterruptedException interruptedException) {
                bl2 = true;
                bl = false;
                continue;
            }
            break;
        }
        finally {
            if (!bl4) {
                reentrantLock.unlock();
            }
        }
        finally {
            if (bl2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean enterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        boolean bl = false;
        try {
            boolean bl2 = bl = guard.isSatisfied();
            return bl2;
        } finally {
            if (!bl) {
                reentrantLock.unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        boolean bl = false;
        try {
            boolean bl2 = bl = guard.isSatisfied();
            return bl2;
        } finally {
            if (!bl) {
                reentrantLock.unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean enterIf(Guard guard, long l, TimeUnit timeUnit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        if (!this.enter(l, timeUnit)) {
            return true;
        }
        boolean bl = false;
        try {
            boolean bl2 = bl = guard.isSatisfied();
            return bl2;
        } finally {
            if (!bl) {
                this.lock.unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean enterIfInterruptibly(Guard guard, long l, TimeUnit timeUnit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        if (!reentrantLock.tryLock(l, timeUnit)) {
            return true;
        }
        boolean bl = false;
        try {
            boolean bl2 = bl = guard.isSatisfied();
            return bl2;
        } finally {
            if (!bl) {
                reentrantLock.unlock();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock reentrantLock = this.lock;
        if (!reentrantLock.tryLock()) {
            return true;
        }
        boolean bl = false;
        try {
            boolean bl2 = bl = guard.isSatisfied();
            return bl2;
        } finally {
            if (!bl) {
                reentrantLock.unlock();
            }
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (!guard.isSatisfied()) {
            this.await(guard, true);
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (!guard.isSatisfied()) {
            this.awaitUninterruptibly(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long l, TimeUnit timeUnit) throws InterruptedException {
        long l2 = Monitor.toSafeNanos(l, timeUnit);
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return false;
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this.awaitNanos(guard, l2, true);
    }

    public boolean waitForUninterruptibly(Guard guard, long l, TimeUnit timeUnit) {
        long l2 = Monitor.toSafeNanos(l, timeUnit);
        if (!(guard.monitor == this & this.lock.isHeldByCurrentThread())) {
            throw new IllegalMonitorStateException();
        }
        if (guard.isSatisfied()) {
            return false;
        }
        boolean bl = true;
        long l3 = Monitor.initNanoTime(l2);
        boolean bl2 = Thread.interrupted();
        try {
            long l4 = l2;
            while (true) {
                try {
                    boolean bl3 = this.awaitNanos(guard, l4, bl);
                    return bl3;
                } catch (InterruptedException interruptedException) {
                    block12: {
                        bl2 = true;
                        if (!guard.isSatisfied()) break block12;
                        boolean bl4 = true;
                        if (bl2) {
                            Thread.currentThread().interrupt();
                        }
                        return bl4;
                    }
                    bl = false;
                    l4 = Monitor.remainingNanos(l3, l2);
                    continue;
                }
                break;
            }
        } finally {
            if (bl2) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void leave() {
        ReentrantLock reentrantLock = this.lock;
        try {
            if (reentrantLock.getHoldCount() == 1) {
                this.signalNextWaiter();
            }
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread2) {
        return this.lock.hasQueuedThread(thread2);
    }

    public boolean hasWaiters(Guard guard) {
        return this.getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            int n = guard.waiterCount;
            return n;
        } finally {
            this.lock.unlock();
        }
    }

    private static long toSafeNanos(long l, TimeUnit timeUnit) {
        long l2 = timeUnit.toNanos(l);
        return l2 <= 0L ? 0L : (l2 > 0x5FFFFFFFFFFFFFFDL ? 0x5FFFFFFFFFFFFFFDL : l2);
    }

    private static long initNanoTime(long l) {
        if (l <= 0L) {
            return 0L;
        }
        long l2 = System.nanoTime();
        return l2 == 0L ? 1L : l2;
    }

    private static long remainingNanos(long l, long l2) {
        return l2 <= 0L ? 0L : l2 - (System.nanoTime() - l);
    }

    @GuardedBy(value="lock")
    private void signalNextWaiter() {
        Guard guard = this.activeGuards;
        while (guard != null) {
            if (this.isSatisfied(guard)) {
                guard.condition.signal();
                break;
            }
            guard = guard.next;
        }
    }

    @GuardedBy(value="lock")
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable throwable) {
            this.signalAllWaiters();
            throw Throwables.propagate(throwable);
        }
    }

    @GuardedBy(value="lock")
    private void signalAllWaiters() {
        Guard guard = this.activeGuards;
        while (guard != null) {
            guard.condition.signalAll();
            guard = guard.next;
        }
    }

    @GuardedBy(value="lock")
    private void beginWaitingFor(Guard guard) {
        int n;
        if ((n = guard.waiterCount++) == 0) {
            guard.next = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy(value="lock")
    private void endWaitingFor(Guard guard) {
        int n;
        if ((n = --guard.waiterCount) == 0) {
            Guard guard2 = this.activeGuards;
            Guard guard3 = null;
            while (true) {
                if (guard2 == guard) {
                    if (guard3 == null) {
                        this.activeGuards = guard2.next;
                    } else {
                        guard3.next = guard2.next;
                    }
                    guard2.next = null;
                    break;
                }
                guard3 = guard2;
                guard2 = guard2.next;
            }
        }
    }

    @GuardedBy(value="lock")
    private void await(Guard guard, boolean bl) throws InterruptedException {
        if (bl) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        try {
            do {
                guard.condition.await();
            } while (!guard.isSatisfied());
        } finally {
            this.endWaitingFor(guard);
        }
    }

    @GuardedBy(value="lock")
    private void awaitUninterruptibly(Guard guard, boolean bl) {
        if (bl) {
            this.signalNextWaiter();
        }
        this.beginWaitingFor(guard);
        try {
            do {
                guard.condition.awaitUninterruptibly();
            } while (!guard.isSatisfied());
        } finally {
            this.endWaitingFor(guard);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GuardedBy(value="lock")
    private boolean awaitNanos(Guard guard, long l, boolean bl) throws InterruptedException {
        boolean bl2 = true;
        try {
            do {
                if (l <= 0L) {
                    boolean bl3 = false;
                    return bl3;
                }
                if (bl2) {
                    if (bl) {
                        this.signalNextWaiter();
                    }
                    this.beginWaitingFor(guard);
                    bl2 = false;
                }
                l = guard.condition.awaitNanos(l);
            } while (!guard.isSatisfied());
            boolean bl4 = true;
            return bl4;
        } finally {
            if (!bl2) {
                this.endWaitingFor(guard);
            }
        }
    }

    static ReentrantLock access$000(Monitor monitor) {
        return monitor.lock;
    }

    @Beta
    public static abstract class Guard {
        @Weak
        final Monitor monitor;
        final Condition condition;
        @GuardedBy(value="monitor.lock")
        int waiterCount = 0;
        @GuardedBy(value="monitor.lock")
        Guard next;

        protected Guard(Monitor monitor) {
            this.monitor = Preconditions.checkNotNull(monitor, "monitor");
            this.condition = Monitor.access$000(monitor).newCondition();
        }

        public abstract boolean isSatisfied();
    }
}

