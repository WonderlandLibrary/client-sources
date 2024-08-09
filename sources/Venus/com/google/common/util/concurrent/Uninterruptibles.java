/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
@GwtCompatible(emulated=true)
public final class Uninterruptibles {
    @GwtIncompatible
    public static void awaitUninterruptibly(CountDownLatch countDownLatch) {
        boolean bl = false;
        while (true) {
            try {
                countDownLatch.await();
                return;
            } catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
        finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static boolean awaitUninterruptibly(CountDownLatch countDownLatch, long l, TimeUnit timeUnit) {
        boolean bl = false;
        try {
            long l2 = timeUnit.toNanos(l);
            long l3 = System.nanoTime() + l2;
            while (true) {
                try {
                    boolean bl2 = countDownLatch.await(l2, TimeUnit.NANOSECONDS);
                    return bl2;
                } catch (InterruptedException interruptedException) {
                    bl = true;
                    l2 = l3 - System.nanoTime();
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

    @GwtIncompatible
    public static void joinUninterruptibly(Thread thread2) {
        boolean bl = false;
        while (true) {
            try {
                thread2.join();
                return;
            } catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
        finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @CanIgnoreReturnValue
    public static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        boolean bl = false;
        while (true) {
            try {
                V v = future.get();
                return v;
            } catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
        finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @CanIgnoreReturnValue
    @GwtIncompatible
    public static <V> V getUninterruptibly(Future<V> future, long l, TimeUnit timeUnit) throws ExecutionException, TimeoutException {
        boolean bl = false;
        try {
            long l2 = timeUnit.toNanos(l);
            long l3 = System.nanoTime() + l2;
            while (true) {
                V v;
                try {
                    v = future.get(l2, TimeUnit.NANOSECONDS);
                } catch (InterruptedException interruptedException) {
                    bl = true;
                    l2 = l3 - System.nanoTime();
                    continue;
                }
                return v;
            }
        } finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static void joinUninterruptibly(Thread thread2, long l, TimeUnit timeUnit) {
        Preconditions.checkNotNull(thread2);
        boolean bl = false;
        try {
            long l2 = timeUnit.toNanos(l);
            long l3 = System.nanoTime() + l2;
            while (true) {
                try {
                    TimeUnit.NANOSECONDS.timedJoin(thread2, l2);
                    return;
                } catch (InterruptedException interruptedException) {
                    bl = true;
                    l2 = l3 - System.nanoTime();
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

    @GwtIncompatible
    public static <E> E takeUninterruptibly(BlockingQueue<E> blockingQueue) {
        boolean bl = false;
        while (true) {
            try {
                E e = blockingQueue.take();
                return e;
            } catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
        finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static <E> void putUninterruptibly(BlockingQueue<E> blockingQueue, E e) {
        boolean bl = false;
        while (true) {
            try {
                blockingQueue.put(e);
                return;
            } catch (InterruptedException interruptedException) {
                bl = true;
                continue;
            }
            break;
        }
        finally {
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @GwtIncompatible
    public static void sleepUninterruptibly(long l, TimeUnit timeUnit) {
        boolean bl = false;
        try {
            long l2 = timeUnit.toNanos(l);
            long l3 = System.nanoTime() + l2;
            while (true) {
                try {
                    TimeUnit.NANOSECONDS.sleep(l2);
                    return;
                } catch (InterruptedException interruptedException) {
                    bl = true;
                    l2 = l3 - System.nanoTime();
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

    @GwtIncompatible
    public static boolean tryAcquireUninterruptibly(Semaphore semaphore, long l, TimeUnit timeUnit) {
        return Uninterruptibles.tryAcquireUninterruptibly(semaphore, 1, l, timeUnit);
    }

    @GwtIncompatible
    public static boolean tryAcquireUninterruptibly(Semaphore semaphore, int n, long l, TimeUnit timeUnit) {
        boolean bl = false;
        try {
            long l2 = timeUnit.toNanos(l);
            long l3 = System.nanoTime() + l2;
            while (true) {
                try {
                    boolean bl2 = semaphore.tryAcquire(n, l2, TimeUnit.NANOSECONDS);
                    return bl2;
                } catch (InterruptedException interruptedException) {
                    bl = true;
                    l2 = l3 - System.nanoTime();
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

    private Uninterruptibles() {
    }
}

