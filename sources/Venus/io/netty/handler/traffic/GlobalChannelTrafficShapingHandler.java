/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.handler.traffic.GlobalChannelTrafficCounter;
import io.netty.handler.traffic.TrafficCounter;
import io.netty.util.Attribute;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
public class GlobalChannelTrafficShapingHandler
extends AbstractTrafficShapingHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalChannelTrafficShapingHandler.class);
    final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    private final AtomicLong queuesSize = new AtomicLong();
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    volatile long maxGlobalWriteSize = 0x19000000L;
    private volatile long writeChannelLimit;
    private volatile long readChannelLimit;
    private static final float DEFAULT_DEVIATION = 0.1f;
    private static final float MAX_DEVIATION = 0.4f;
    private static final float DEFAULT_SLOWDOWN = 0.4f;
    private static final float DEFAULT_ACCELERATION = -0.1f;
    private volatile float maxDeviation;
    private volatile float accelerationFactor;
    private volatile float slowDownFactor;
    private volatile boolean readDeviationActive;
    private volatile boolean writeDeviationActive;

    void createGlobalTrafficCounter(ScheduledExecutorService scheduledExecutorService) {
        this.setMaxDeviation(0.1f, 0.4f, -0.1f);
        if (scheduledExecutorService == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
        GlobalChannelTrafficCounter globalChannelTrafficCounter = new GlobalChannelTrafficCounter(this, scheduledExecutorService, "GlobalChannelTC", this.checkInterval);
        this.setTrafficCounter(globalChannelTrafficCounter);
        ((TrafficCounter)globalChannelTrafficCounter).start();
    }

    @Override
    protected int userDefinedWritabilityIndex() {
        return 0;
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l, long l2, long l3, long l4, long l5, long l6) {
        super(l, l2, l5, l6);
        this.createGlobalTrafficCounter(scheduledExecutorService);
        this.writeChannelLimit = l3;
        this.readChannelLimit = l4;
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l, long l2, long l3, long l4, long l5) {
        super(l, l2, l5);
        this.writeChannelLimit = l3;
        this.readChannelLimit = l4;
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l, long l2, long l3, long l4) {
        super(l, l2);
        this.writeChannelLimit = l3;
        this.readChannelLimit = l4;
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l) {
        super(l);
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalChannelTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService) {
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public float maxDeviation() {
        return this.maxDeviation;
    }

    public float accelerationFactor() {
        return this.accelerationFactor;
    }

    public float slowDownFactor() {
        return this.slowDownFactor;
    }

    public void setMaxDeviation(float f, float f2, float f3) {
        if (f > 0.4f) {
            throw new IllegalArgumentException("maxDeviation must be <= 0.4");
        }
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("slowDownFactor must be >= 0");
        }
        if (f3 > 0.0f) {
            throw new IllegalArgumentException("accelerationFactor must be <= 0");
        }
        this.maxDeviation = f;
        this.accelerationFactor = 1.0f + f3;
        this.slowDownFactor = 1.0f + f2;
    }

    private void computeDeviationCumulativeBytes() {
        long l = 0L;
        long l2 = 0L;
        long l3 = Long.MAX_VALUE;
        long l4 = Long.MAX_VALUE;
        for (PerChannel perChannel : this.channelQueues.values()) {
            long l5 = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
            if (l < l5) {
                l = l5;
            }
            if (l3 > l5) {
                l3 = l5;
            }
            if (l2 < (l5 = perChannel.channelTrafficCounter.cumulativeReadBytes())) {
                l2 = l5;
            }
            if (l4 <= l5) continue;
            l4 = l5;
        }
        boolean bl = this.channelQueues.size() > 1;
        this.readDeviationActive = bl && l4 < l2 / 2L;
        this.writeDeviationActive = bl && l3 < l / 2L;
        this.cumulativeWrittenBytes.set(l);
        this.cumulativeReadBytes.set(l2);
    }

    @Override
    protected void doAccounting(TrafficCounter trafficCounter) {
        this.computeDeviationCumulativeBytes();
        super.doAccounting(trafficCounter);
    }

    private long computeBalancedWait(float f, float f2, long l) {
        if (f2 == 0.0f) {
            return l;
        }
        float f3 = f / f2;
        if (f3 > this.maxDeviation) {
            if (f3 < 1.0f - this.maxDeviation) {
                return l;
            }
            f3 = this.slowDownFactor;
            if (l < 10L) {
                l = 10L;
            }
        } else {
            f3 = this.accelerationFactor;
        }
        return (long)((float)l * f3);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException("maxGlobalWriteSize must be positive");
        }
        this.maxGlobalWriteSize = l;
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public void configureChannel(long l, long l2) {
        this.writeChannelLimit = l;
        this.readChannelLimit = l2;
        long l3 = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(l3);
        }
    }

    public long getWriteChannelLimit() {
        return this.writeChannelLimit;
    }

    public void setWriteChannelLimit(long l) {
        this.writeChannelLimit = l;
        long l2 = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(l2);
        }
    }

    public long getReadChannelLimit() {
        return this.readChannelLimit;
    }

    public void setReadChannelLimit(long l) {
        this.readChannelLimit = l;
        long l2 = TrafficCounter.milliSecondFromNano();
        for (PerChannel perChannel : this.channelQueues.values()) {
            perChannel.channelTrafficCounter.resetAccounting(l2);
        }
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private PerChannel getOrSetPerChannel(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        Integer n = channel.hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel == null) {
            perChannel = new PerChannel();
            perChannel.messagesQueue = new ArrayDeque();
            perChannel.channelTrafficCounter = new TrafficCounter(this, null, "ChannelTC" + channelHandlerContext.channel().hashCode(), this.checkInterval);
            perChannel.queueSize = 0L;
            perChannel.lastWriteTimestamp = perChannel.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
            this.channelQueues.put(n, perChannel);
        }
        return perChannel;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.getOrSetPerChannel(channelHandlerContext);
        this.trafficCounter.resetCumulativeTime();
        super.handlerAdded(channelHandlerContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.trafficCounter.resetCumulativeTime();
        Channel channel = channelHandlerContext.channel();
        Integer n = channel.hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.remove(n);
        if (perChannel != null) {
            PerChannel perChannel2 = perChannel;
            synchronized (perChannel2) {
                if (channel.isActive()) {
                    for (ToSend toSend : perChannel.messagesQueue) {
                        long l = this.calculateSize(toSend.toSend);
                        this.trafficCounter.bytesRealWriteFlowControl(l);
                        perChannel.channelTrafficCounter.bytesRealWriteFlowControl(l);
                        perChannel.queueSize -= l;
                        this.queuesSize.addAndGet(-l);
                        channelHandlerContext.write(toSend.toSend, toSend.promise);
                    }
                } else {
                    this.queuesSize.addAndGet(-perChannel.queueSize);
                    for (ToSend toSend : perChannel.messagesQueue) {
                        if (!(toSend.toSend instanceof ByteBuf)) continue;
                        ((ByteBuf)toSend.toSend).release();
                    }
                }
                perChannel.messagesQueue.clear();
            }
        }
        this.releaseWriteSuspended(channelHandlerContext);
        this.releaseReadSuspended(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        long l = this.calculateSize(object);
        long l2 = TrafficCounter.milliSecondFromNano();
        if (l > 0L) {
            long l3 = this.trafficCounter.readTimeToWait(l, this.getReadLimit(), this.maxTime, l2);
            Integer n = channelHandlerContext.channel().hashCode();
            PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
            long l4 = 0L;
            if (perChannel != null) {
                l4 = perChannel.channelTrafficCounter.readTimeToWait(l, this.readChannelLimit, this.maxTime, l2);
                if (this.readDeviationActive) {
                    long l5 = perChannel.channelTrafficCounter.cumulativeReadBytes();
                    long l6 = this.cumulativeReadBytes.get();
                    if (l5 <= 0L) {
                        l5 = 0L;
                    }
                    if (l6 < l5) {
                        l6 = l5;
                    }
                    l4 = this.computeBalancedWait(l5, l6, l4);
                }
            }
            if (l4 < l3) {
                l4 = l3;
            }
            if ((l4 = this.checkWaitReadTime(channelHandlerContext, l4, l2)) >= 10L) {
                Channel channel = channelHandlerContext.channel();
                ChannelConfig channelConfig = channel.config();
                if (logger.isDebugEnabled()) {
                    logger.debug("Read Suspend: " + l4 + ':' + channelConfig.isAutoRead() + ':' + GlobalChannelTrafficShapingHandler.isHandlerActive(channelHandlerContext));
                }
                if (channelConfig.isAutoRead() && GlobalChannelTrafficShapingHandler.isHandlerActive(channelHandlerContext)) {
                    channelConfig.setAutoRead(false);
                    channel.attr(READ_SUSPENDED).set(true);
                    Attribute<Runnable> attribute = channel.attr(REOPEN_TASK);
                    Runnable runnable = (Runnable)attribute.get();
                    if (runnable == null) {
                        runnable = new AbstractTrafficShapingHandler.ReopenReadTimerTask(channelHandlerContext);
                        attribute.set(runnable);
                    }
                    channelHandlerContext.executor().schedule(runnable, l4, TimeUnit.MILLISECONDS);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Suspend final status => " + channelConfig.isAutoRead() + ':' + GlobalChannelTrafficShapingHandler.isHandlerActive(channelHandlerContext) + " will reopened at: " + l4);
                    }
                }
            }
        }
        this.informReadOperation(channelHandlerContext, l2);
        channelHandlerContext.fireChannelRead(object);
    }

    @Override
    protected long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long l, long l2) {
        Integer n = channelHandlerContext.channel().hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel != null && l > this.maxTime && l2 + l - perChannel.lastReadTimestamp > this.maxTime) {
            l = this.maxTime;
        }
        return l;
    }

    @Override
    protected void informReadOperation(ChannelHandlerContext channelHandlerContext, long l) {
        Integer n = channelHandlerContext.channel().hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel != null) {
            perChannel.lastReadTimestamp = l;
        }
    }

    protected long maximumCumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    protected long maximumCumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public Collection<TrafficCounter> channelTrafficCounters() {
        return new AbstractCollection<TrafficCounter>(this){
            final GlobalChannelTrafficShapingHandler this$0;
            {
                this.this$0 = globalChannelTrafficShapingHandler;
            }

            @Override
            public Iterator<TrafficCounter> iterator() {
                return new Iterator<TrafficCounter>(this){
                    final Iterator<PerChannel> iter;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.iter = this.this$1.this$0.channelQueues.values().iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.iter.hasNext();
                    }

                    @Override
                    public TrafficCounter next() {
                        return this.iter.next().channelTrafficCounter;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public Object next() {
                        return this.next();
                    }
                };
            }

            @Override
            public int size() {
                return this.this$0.channelQueues.size();
            }
        };
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        long l = this.calculateSize(object);
        long l2 = TrafficCounter.milliSecondFromNano();
        if (l > 0L) {
            long l3 = this.trafficCounter.writeTimeToWait(l, this.getWriteLimit(), this.maxTime, l2);
            Integer n = channelHandlerContext.channel().hashCode();
            PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
            long l4 = 0L;
            if (perChannel != null) {
                l4 = perChannel.channelTrafficCounter.writeTimeToWait(l, this.writeChannelLimit, this.maxTime, l2);
                if (this.writeDeviationActive) {
                    long l5 = perChannel.channelTrafficCounter.cumulativeWrittenBytes();
                    long l6 = this.cumulativeWrittenBytes.get();
                    if (l5 <= 0L) {
                        l5 = 0L;
                    }
                    if (l6 < l5) {
                        l6 = l5;
                    }
                    l4 = this.computeBalancedWait(l5, l6, l4);
                }
            }
            if (l4 < l3) {
                l4 = l3;
            }
            if (l4 >= 10L) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Write suspend: " + l4 + ':' + channelHandlerContext.channel().config().isAutoRead() + ':' + GlobalChannelTrafficShapingHandler.isHandlerActive(channelHandlerContext));
                }
                this.submitWrite(channelHandlerContext, object, l, l4, l2, channelPromise);
                return;
            }
        }
        this.submitWrite(channelHandlerContext, object, l, 0L, l2, channelPromise);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void submitWrite(ChannelHandlerContext channelHandlerContext, Object object, long l, long l2, long l3, ChannelPromise channelPromise) {
        ToSend toSend;
        Channel channel = channelHandlerContext.channel();
        Integer n = channel.hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel == null) {
            perChannel = this.getOrSetPerChannel(channelHandlerContext);
        }
        long l4 = l2;
        boolean bl = false;
        PerChannel perChannel2 = perChannel;
        synchronized (perChannel2) {
            if (l2 == 0L && perChannel.messagesQueue.isEmpty()) {
                this.trafficCounter.bytesRealWriteFlowControl(l);
                perChannel.channelTrafficCounter.bytesRealWriteFlowControl(l);
                channelHandlerContext.write(object, channelPromise);
                perChannel.lastWriteTimestamp = l3;
                return;
            }
            if (l4 > this.maxTime && l3 + l4 - perChannel.lastWriteTimestamp > this.maxTime) {
                l4 = this.maxTime;
            }
            toSend = new ToSend(l4 + l3, object, l, channelPromise, null);
            perChannel.messagesQueue.addLast(toSend);
            perChannel.queueSize += l;
            this.queuesSize.addAndGet(l);
            this.checkWriteSuspend(channelHandlerContext, l4, perChannel.queueSize);
            if (this.queuesSize.get() > this.maxGlobalWriteSize) {
                bl = true;
            }
        }
        if (bl) {
            this.setUserDefinedWritability(channelHandlerContext, true);
        }
        long l5 = toSend.relativeTimeAction;
        PerChannel perChannel3 = perChannel;
        channelHandlerContext.executor().schedule(new Runnable(this, channelHandlerContext, perChannel3, l5){
            final ChannelHandlerContext val$ctx;
            final PerChannel val$forSchedule;
            final long val$futureNow;
            final GlobalChannelTrafficShapingHandler this$0;
            {
                this.this$0 = globalChannelTrafficShapingHandler;
                this.val$ctx = channelHandlerContext;
                this.val$forSchedule = perChannel;
                this.val$futureNow = l;
            }

            @Override
            public void run() {
                GlobalChannelTrafficShapingHandler.access$100(this.this$0, this.val$ctx, this.val$forSchedule, this.val$futureNow);
            }
        }, l4, TimeUnit.MILLISECONDS);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendAllValid(ChannelHandlerContext channelHandlerContext, PerChannel perChannel, long l) {
        PerChannel perChannel2 = perChannel;
        synchronized (perChannel2) {
            ToSend toSend = perChannel.messagesQueue.pollFirst();
            while (toSend != null) {
                long l2;
                if (toSend.relativeTimeAction <= l) {
                    l2 = toSend.size;
                    this.trafficCounter.bytesRealWriteFlowControl(l2);
                    perChannel.channelTrafficCounter.bytesRealWriteFlowControl(l2);
                    perChannel.queueSize -= l2;
                } else {
                    perChannel.messagesQueue.addFirst(toSend);
                    break;
                }
                this.queuesSize.addAndGet(-l2);
                channelHandlerContext.write(toSend.toSend, toSend.promise);
                perChannel.lastWriteTimestamp = l;
                toSend = perChannel.messagesQueue.pollFirst();
            }
            if (perChannel.messagesQueue.isEmpty()) {
                this.releaseWriteSuspended(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }

    @Override
    public String toString() {
        return new StringBuilder(340).append(super.toString()).append(" Write Channel Limit: ").append(this.writeChannelLimit).append(" Read Channel Limit: ").append(this.readChannelLimit).toString();
    }

    static void access$100(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler, ChannelHandlerContext channelHandlerContext, PerChannel perChannel, long l) {
        globalChannelTrafficShapingHandler.sendAllValid(channelHandlerContext, perChannel, l);
    }

    private static final class ToSend {
        final long relativeTimeAction;
        final Object toSend;
        final ChannelPromise promise;
        final long size;

        private ToSend(long l, Object object, long l2, ChannelPromise channelPromise) {
            this.relativeTimeAction = l;
            this.toSend = object;
            this.size = l2;
            this.promise = channelPromise;
        }

        ToSend(long l, Object object, long l2, ChannelPromise channelPromise, 1 var7_5) {
            this(l, object, l2, channelPromise);
        }
    }

    static final class PerChannel {
        ArrayDeque<ToSend> messagesQueue;
        TrafficCounter channelTrafficCounter;
        long queueSize;
        long lastWriteTimestamp;
        long lastReadTimestamp;

        PerChannel() {
        }
    }
}

