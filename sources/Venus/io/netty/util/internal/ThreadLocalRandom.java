/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadLocalRandom
extends Random {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
    private static final AtomicLong seedUniquifier = new AtomicLong();
    private static volatile long initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0L);
    private static final Thread seedGeneratorThread;
    private static final BlockingQueue<Long> seedQueue;
    private static final long seedGeneratorStartTime;
    private static volatile long seedGeneratorEndTime;
    private static final long multiplier = 25214903917L;
    private static final long addend = 11L;
    private static final long mask = 0xFFFFFFFFFFFFL;
    private long rnd;
    boolean initialized = true;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private static final long serialVersionUID = -5851777807851030925L;

    public static void setInitialSeedUniquifier(long l) {
        initialSeedUniquifier = l;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long getInitialSeedUniquifier() {
        long l = initialSeedUniquifier;
        if (l != 0L) {
            return l;
        }
        Class<ThreadLocalRandom> clazz = ThreadLocalRandom.class;
        synchronized (ThreadLocalRandom.class) {
            boolean bl;
            block10: {
                long l2;
                l = initialSeedUniquifier;
                if (l != 0L) {
                    // ** MonitorExit[var2_1] (shouldn't be in output)
                    return l;
                }
                long l3 = 3L;
                long l4 = seedGeneratorStartTime + TimeUnit.SECONDS.toNanos(3L);
                bl = false;
                do {
                    l2 = l4 - System.nanoTime();
                    try {
                        Long l5 = l2 <= 0L ? (Long)seedQueue.poll() : seedQueue.poll(l2, TimeUnit.NANOSECONDS);
                        if (l5 == null) continue;
                        l = l5;
                    } catch (InterruptedException interruptedException) {
                        bl = true;
                        logger.warn("Failed to generate a seed from SecureRandom due to an InterruptedException.");
                    }
                    break block10;
                } while (l2 > 0L);
                seedGeneratorThread.interrupt();
                logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entropy?", (Object)3L);
            }
            l ^= 0x3255ECDC33BAE119L;
            initialSeedUniquifier = l ^= Long.reverse(System.nanoTime());
            if (bl) {
                Thread.currentThread().interrupt();
                seedGeneratorThread.interrupt();
            }
            if (seedGeneratorEndTime == 0L) {
                seedGeneratorEndTime = System.nanoTime();
            }
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return l;
        }
    }

    private static long newSeed() {
        long l;
        long l2;
        long l3;
        while (!seedUniquifier.compareAndSet(l3, l2 = (l = (l3 = seedUniquifier.get()) != 0L ? l3 : ThreadLocalRandom.getInitialSeedUniquifier()) * 181783497276652981L)) {
        }
        if (l3 == 0L && logger.isDebugEnabled()) {
            if (seedGeneratorEndTime != 0L) {
                logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", l, TimeUnit.NANOSECONDS.toMillis(seedGeneratorEndTime - seedGeneratorStartTime)));
            } else {
                logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x", l));
            }
        }
        return l2 ^ System.nanoTime();
    }

    private static long mix64(long l) {
        l = (l ^ l >>> 33) * -49064778989728563L;
        l = (l ^ l >>> 33) * -4265267296055464877L;
        return l ^ l >>> 33;
    }

    ThreadLocalRandom() {
        super(ThreadLocalRandom.newSeed());
    }

    public static ThreadLocalRandom current() {
        return InternalThreadLocalMap.get().random();
    }

    @Override
    public void setSeed(long l) {
        if (this.initialized) {
            throw new UnsupportedOperationException();
        }
        this.rnd = (l ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
    }

    @Override
    protected int next(int n) {
        this.rnd = this.rnd * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
        return (int)(this.rnd >>> 48 - n);
    }

    public int nextInt(int n, int n2) {
        if (n >= n2) {
            throw new IllegalArgumentException();
        }
        return this.nextInt(n2 - n) + n;
    }

    public long nextLong(long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException("n must be positive");
        }
        long l2 = 0L;
        while (l >= Integer.MAX_VALUE) {
            long l3;
            int n = this.next(2);
            long l4 = l >>> 1;
            long l5 = l3 = (n & 2) == 0 ? l4 : l - l4;
            if ((n & 1) == 0) {
                l2 += l - l3;
            }
            l = l3;
        }
        return l2 + (long)this.nextInt((int)l);
    }

    public long nextLong(long l, long l2) {
        if (l >= l2) {
            throw new IllegalArgumentException();
        }
        return this.nextLong(l2 - l) + l;
    }

    public double nextDouble(double d) {
        if (d <= 0.0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return this.nextDouble() * d;
    }

    public double nextDouble(double d, double d2) {
        if (d >= d2) {
            throw new IllegalArgumentException();
        }
        return this.nextDouble() * (d2 - d) + d;
    }

    static long access$002(long l) {
        seedGeneratorEndTime = l;
        return seedGeneratorEndTime;
    }

    static BlockingQueue access$100() {
        return seedQueue;
    }

    static InternalLogger access$200() {
        return logger;
    }

    static {
        if (initialSeedUniquifier == 0L) {
            boolean bl = SystemPropertyUtil.getBoolean("java.util.secureRandomSeed", false);
            if (bl) {
                seedQueue = new LinkedBlockingQueue<Long>();
                seedGeneratorStartTime = System.nanoTime();
                seedGeneratorThread = new Thread("initialSeedUniquifierGenerator"){

                    @Override
                    public void run() {
                        SecureRandom secureRandom = new SecureRandom();
                        byte[] byArray = secureRandom.generateSeed(8);
                        ThreadLocalRandom.access$002(System.nanoTime());
                        long l = ((long)byArray[0] & 0xFFL) << 56 | ((long)byArray[1] & 0xFFL) << 48 | ((long)byArray[2] & 0xFFL) << 40 | ((long)byArray[3] & 0xFFL) << 32 | ((long)byArray[4] & 0xFFL) << 24 | ((long)byArray[5] & 0xFFL) << 16 | ((long)byArray[6] & 0xFFL) << 8 | (long)byArray[7] & 0xFFL;
                        ThreadLocalRandom.access$100().add(l);
                    }
                };
                seedGeneratorThread.setDaemon(false);
                seedGeneratorThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

                    @Override
                    public void uncaughtException(Thread thread2, Throwable throwable) {
                        ThreadLocalRandom.access$200().debug("An exception has been raised by {}", (Object)thread2.getName(), (Object)throwable);
                    }
                });
                seedGeneratorThread.start();
            } else {
                initialSeedUniquifier = ThreadLocalRandom.mix64(System.currentTimeMillis()) ^ ThreadLocalRandom.mix64(System.nanoTime());
                seedGeneratorThread = null;
                seedQueue = null;
                seedGeneratorStartTime = 0L;
            }
        } else {
            seedGeneratorThread = null;
            seedQueue = null;
            seedGeneratorStartTime = 0L;
        }
    }
}

