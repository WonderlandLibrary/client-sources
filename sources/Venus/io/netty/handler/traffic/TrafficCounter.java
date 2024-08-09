/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.traffic;

import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TrafficCounter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
    private final AtomicLong currentWrittenBytes = new AtomicLong();
    private final AtomicLong currentReadBytes = new AtomicLong();
    private long writingTime;
    private long readingTime;
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    private long lastCumulativeTime;
    private long lastWriteThroughput;
    private long lastReadThroughput;
    final AtomicLong lastTime = new AtomicLong();
    private volatile long lastWrittenBytes;
    private volatile long lastReadBytes;
    private volatile long lastWritingTime;
    private volatile long lastReadingTime;
    private final AtomicLong realWrittenBytes = new AtomicLong();
    private long realWriteThroughput;
    final AtomicLong checkInterval = new AtomicLong(1000L);
    final String name;
    final AbstractTrafficShapingHandler trafficShapingHandler;
    final ScheduledExecutorService executor;
    Runnable monitor;
    volatile ScheduledFuture<?> scheduledFuture;
    volatile boolean monitorActive;

    public static long milliSecondFromNano() {
        return System.nanoTime() / 1000000L;
    }

    public synchronized void start() {
        if (this.monitorActive) {
            return;
        }
        this.lastTime.set(TrafficCounter.milliSecondFromNano());
        long l = this.checkInterval.get();
        if (l > 0L && this.executor != null) {
            this.monitorActive = true;
            this.monitor = new TrafficMonitoringTask(this, null);
            this.scheduledFuture = this.executor.schedule(this.monitor, l, TimeUnit.MILLISECONDS);
        }
    }

    public synchronized void stop() {
        if (!this.monitorActive) {
            return;
        }
        this.monitorActive = false;
        this.resetAccounting(TrafficCounter.milliSecondFromNano());
        if (this.trafficShapingHandler != null) {
            this.trafficShapingHandler.doAccounting(this);
        }
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
    }

    synchronized void resetAccounting(long l) {
        long l2 = l - this.lastTime.getAndSet(l);
        if (l2 == 0L) {
            return;
        }
        if (logger.isDebugEnabled() && l2 > this.checkInterval() << 1) {
            logger.debug("Acct schedule not ok: " + l2 + " > 2*" + this.checkInterval() + " from " + this.name);
        }
        this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
        this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
        this.lastReadThroughput = this.lastReadBytes * 1000L / l2;
        this.lastWriteThroughput = this.lastWrittenBytes * 1000L / l2;
        this.realWriteThroughput = this.realWrittenBytes.getAndSet(0L) * 1000L / l2;
        this.lastWritingTime = Math.max(this.lastWritingTime, this.writingTime);
        this.lastReadingTime = Math.max(this.lastReadingTime, this.readingTime);
    }

    public TrafficCounter(ScheduledExecutorService scheduledExecutorService, String string, long l) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        this.trafficShapingHandler = null;
        this.executor = scheduledExecutorService;
        this.name = string;
        this.init(l);
    }

    public TrafficCounter(AbstractTrafficShapingHandler abstractTrafficShapingHandler, ScheduledExecutorService scheduledExecutorService, String string, long l) {
        if (abstractTrafficShapingHandler == null) {
            throw new IllegalArgumentException("trafficShapingHandler");
        }
        if (string == null) {
            throw new NullPointerException("name");
        }
        this.trafficShapingHandler = abstractTrafficShapingHandler;
        this.executor = scheduledExecutorService;
        this.name = string;
        this.init(l);
    }

    private void init(long l) {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.readingTime = this.writingTime = TrafficCounter.milliSecondFromNano();
        this.lastWritingTime = this.writingTime;
        this.lastReadingTime = this.writingTime;
        this.configure(l);
    }

    public void configure(long l) {
        long l2 = l / 10L * 10L;
        if (this.checkInterval.getAndSet(l2) != l2) {
            if (l2 <= 0L) {
                this.stop();
                this.lastTime.set(TrafficCounter.milliSecondFromNano());
            } else {
                this.start();
            }
        }
    }

    void bytesRecvFlowControl(long l) {
        this.currentReadBytes.addAndGet(l);
        this.cumulativeReadBytes.addAndGet(l);
    }

    void bytesWriteFlowControl(long l) {
        this.currentWrittenBytes.addAndGet(l);
        this.cumulativeWrittenBytes.addAndGet(l);
    }

    void bytesRealWriteFlowControl(long l) {
        this.realWrittenBytes.addAndGet(l);
    }

    public long checkInterval() {
        return this.checkInterval.get();
    }

    public long lastReadThroughput() {
        return this.lastReadThroughput;
    }

    public long lastWriteThroughput() {
        return this.lastWriteThroughput;
    }

    public long lastReadBytes() {
        return this.lastReadBytes;
    }

    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }

    public long currentReadBytes() {
        return this.currentReadBytes.get();
    }

    public long currentWrittenBytes() {
        return this.currentWrittenBytes.get();
    }

    public long lastTime() {
        return this.lastTime.get();
    }

    public long cumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    public long cumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public long lastCumulativeTime() {
        return this.lastCumulativeTime;
    }

    public AtomicLong getRealWrittenBytes() {
        return this.realWrittenBytes;
    }

    public long getRealWriteThroughput() {
        return this.realWriteThroughput;
    }

    public void resetCumulativeTime() {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.cumulativeReadBytes.set(0L);
        this.cumulativeWrittenBytes.set(0L);
    }

    public String name() {
        return this.name;
    }

    @Deprecated
    public long readTimeToWait(long l, long l2, long l3) {
        return this.readTimeToWait(l, l2, l3, TrafficCounter.milliSecondFromNano());
    }

    public long readTimeToWait(long l, long l2, long l3, long l4) {
        this.bytesRecvFlowControl(l);
        if (l == 0L || l2 == 0L) {
            return 0L;
        }
        long l5 = this.lastTime.get();
        long l6 = this.currentReadBytes.get();
        long l7 = this.readingTime;
        long l8 = this.lastReadBytes;
        long l9 = l4 - l5;
        long l10 = Math.max(this.lastReadingTime - l5, 0L);
        if (l9 > 10L) {
            long l11 = l6 * 1000L / l2 - l9 + l10;
            if (l11 > 10L) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Time: " + l11 + ':' + l6 + ':' + l9 + ':' + l10);
                }
                if (l11 > l3 && l4 + l11 - l7 > l3) {
                    l11 = l3;
                }
                this.readingTime = Math.max(l7, l4 + l11);
                return l11;
            }
            this.readingTime = Math.max(l7, l4);
            return 0L;
        }
        long l12 = l6 + l8;
        long l13 = l9 + this.checkInterval.get();
        long l14 = l12 * 1000L / l2 - l13 + l10;
        if (l14 > 10L) {
            if (logger.isDebugEnabled()) {
                logger.debug("Time: " + l14 + ':' + l12 + ':' + l13 + ':' + l10);
            }
            if (l14 > l3 && l4 + l14 - l7 > l3) {
                l14 = l3;
            }
            this.readingTime = Math.max(l7, l4 + l14);
            return l14;
        }
        this.readingTime = Math.max(l7, l4);
        return 0L;
    }

    @Deprecated
    public long writeTimeToWait(long l, long l2, long l3) {
        return this.writeTimeToWait(l, l2, l3, TrafficCounter.milliSecondFromNano());
    }

    public long writeTimeToWait(long l, long l2, long l3, long l4) {
        this.bytesWriteFlowControl(l);
        if (l == 0L || l2 == 0L) {
            return 0L;
        }
        long l5 = this.lastTime.get();
        long l6 = this.currentWrittenBytes.get();
        long l7 = this.lastWrittenBytes;
        long l8 = this.writingTime;
        long l9 = Math.max(this.lastWritingTime - l5, 0L);
        long l10 = l4 - l5;
        if (l10 > 10L) {
            long l11 = l6 * 1000L / l2 - l10 + l9;
            if (l11 > 10L) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Time: " + l11 + ':' + l6 + ':' + l10 + ':' + l9);
                }
                if (l11 > l3 && l4 + l11 - l8 > l3) {
                    l11 = l3;
                }
                this.writingTime = Math.max(l8, l4 + l11);
                return l11;
            }
            this.writingTime = Math.max(l8, l4);
            return 0L;
        }
        long l12 = l6 + l7;
        long l13 = l10 + this.checkInterval.get();
        long l14 = l12 * 1000L / l2 - l13 + l9;
        if (l14 > 10L) {
            if (logger.isDebugEnabled()) {
                logger.debug("Time: " + l14 + ':' + l12 + ':' + l13 + ':' + l9);
            }
            if (l14 > l3 && l4 + l14 - l8 > l3) {
                l14 = l3;
            }
            this.writingTime = Math.max(l8, l4 + l14);
            return l14;
        }
        this.writingTime = Math.max(l8, l4);
        return 0L;
    }

    public String toString() {
        return new StringBuilder(165).append("Monitor ").append(this.name).append(" Current Speed Read: ").append(this.lastReadThroughput >> 10).append(" KB/s, ").append("Asked Write: ").append(this.lastWriteThroughput >> 10).append(" KB/s, ").append("Real Write: ").append(this.realWriteThroughput >> 10).append(" KB/s, ").append("Current Read: ").append(this.currentReadBytes.get() >> 10).append(" KB, ").append("Current asked Write: ").append(this.currentWrittenBytes.get() >> 10).append(" KB, ").append("Current real Write: ").append(this.realWrittenBytes.get() >> 10).append(" KB").toString();
    }

    private final class TrafficMonitoringTask
    implements Runnable {
        final TrafficCounter this$0;

        private TrafficMonitoringTask(TrafficCounter trafficCounter) {
            this.this$0 = trafficCounter;
        }

        @Override
        public void run() {
            if (!this.this$0.monitorActive) {
                return;
            }
            this.this$0.resetAccounting(TrafficCounter.milliSecondFromNano());
            if (this.this$0.trafficShapingHandler != null) {
                this.this$0.trafficShapingHandler.doAccounting(this.this$0);
            }
            this.this$0.scheduledFuture = this.this$0.executor.schedule(this, this.this$0.checkInterval.get(), TimeUnit.MILLISECONDS);
        }

        TrafficMonitoringTask(TrafficCounter trafficCounter, 1 var2_2) {
            this(trafficCounter);
        }
    }
}

