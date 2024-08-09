/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.PlatformDependent;
import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
public class GlobalTrafficShapingHandler
extends AbstractTrafficShapingHandler {
    private final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    private final AtomicLong queuesSize = new AtomicLong();
    long maxGlobalWriteSize = 0x19000000L;

    void createGlobalTrafficCounter(ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService == null) {
            throw new NullPointerException("executor");
        }
        TrafficCounter trafficCounter = new TrafficCounter(this, scheduledExecutorService, "GlobalTC", this.checkInterval);
        this.setTrafficCounter(trafficCounter);
        trafficCounter.start();
    }

    @Override
    protected int userDefinedWritabilityIndex() {
        return 1;
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l, long l2, long l3, long l4) {
        super(l, l2, l3, l4);
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l, long l2, long l3) {
        super(l, l2, l3);
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l, long l2) {
        super(l, l2);
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long l) {
        super(l);
        this.createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(EventExecutor eventExecutor) {
        this.createGlobalTrafficCounter(eventExecutor);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long l) {
        this.maxGlobalWriteSize = l;
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private PerChannel getOrSetPerChannel(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        Integer n = channel.hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel == null) {
            perChannel = new PerChannel(null);
            perChannel.messagesQueue = new ArrayDeque();
            perChannel.queueSize = 0L;
            perChannel.lastWriteTimestamp = perChannel.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
            this.channelQueues.put(n, perChannel);
        }
        return perChannel;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.getOrSetPerChannel(channelHandlerContext);
        super.handlerAdded(channelHandlerContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
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
    long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long l, long l2) {
        Integer n = channelHandlerContext.channel().hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel != null && l > this.maxTime && l2 + l - perChannel.lastReadTimestamp > this.maxTime) {
            l = this.maxTime;
        }
        return l;
    }

    @Override
    void informReadOperation(ChannelHandlerContext channelHandlerContext, long l) {
        Integer n = channelHandlerContext.channel().hashCode();
        PerChannel perChannel = (PerChannel)this.channelQueues.get(n);
        if (perChannel != null) {
            perChannel.lastReadTimestamp = l;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void submitWrite(ChannelHandlerContext channelHandlerContext, Object object, long l, long l2, long l3, ChannelPromise channelPromise) {
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
            final GlobalTrafficShapingHandler this$0;
            {
                this.this$0 = globalTrafficShapingHandler;
                this.val$ctx = channelHandlerContext;
                this.val$forSchedule = perChannel;
                this.val$futureNow = l;
            }

            @Override
            public void run() {
                GlobalTrafficShapingHandler.access$200(this.this$0, this.val$ctx, this.val$forSchedule, this.val$futureNow);
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

    static void access$200(GlobalTrafficShapingHandler globalTrafficShapingHandler, ChannelHandlerContext channelHandlerContext, PerChannel perChannel, long l) {
        globalTrafficShapingHandler.sendAllValid(channelHandlerContext, perChannel, l);
    }

    private static final class ToSend {
        final long relativeTimeAction;
        final Object toSend;
        final long size;
        final ChannelPromise promise;

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

    private static final class PerChannel {
        ArrayDeque<ToSend> messagesQueue;
        long queueSize;
        long lastWriteTimestamp;
        long lastReadTimestamp;

        private PerChannel() {
        }

        PerChannel(1 var1_1) {
            this();
        }
    }
}

