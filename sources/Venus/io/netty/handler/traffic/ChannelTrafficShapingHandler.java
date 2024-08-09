/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;
import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

public class ChannelTrafficShapingHandler
extends AbstractTrafficShapingHandler {
    private final ArrayDeque<ToSend> messagesQueue = new ArrayDeque();
    private long queueSize;

    public ChannelTrafficShapingHandler(long l, long l2, long l3, long l4) {
        super(l, l2, l3, l4);
    }

    public ChannelTrafficShapingHandler(long l, long l2, long l3) {
        super(l, l2, l3);
    }

    public ChannelTrafficShapingHandler(long l, long l2) {
        super(l, l2);
    }

    public ChannelTrafficShapingHandler(long l) {
        super(l);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        TrafficCounter trafficCounter = new TrafficCounter(this, channelHandlerContext.executor(), "ChannelTC" + channelHandlerContext.channel().hashCode(), this.checkInterval);
        this.setTrafficCounter(trafficCounter);
        trafficCounter.start();
        super.handlerAdded(channelHandlerContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.trafficCounter.stop();
        ChannelTrafficShapingHandler channelTrafficShapingHandler = this;
        synchronized (channelTrafficShapingHandler) {
            if (channelHandlerContext.channel().isActive()) {
                for (ToSend toSend : this.messagesQueue) {
                    long l = this.calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(l);
                    this.queueSize -= l;
                    channelHandlerContext.write(toSend.toSend, toSend.promise);
                }
            } else {
                for (ToSend toSend : this.messagesQueue) {
                    if (!(toSend.toSend instanceof ByteBuf)) continue;
                    ((ByteBuf)toSend.toSend).release();
                }
            }
            this.messagesQueue.clear();
        }
        this.releaseWriteSuspended(channelHandlerContext);
        this.releaseReadSuspended(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    void submitWrite(ChannelHandlerContext channelHandlerContext, Object object, long l, long l2, long l3, ChannelPromise channelPromise) {
        ToSend toSend;
        ChannelTrafficShapingHandler channelTrafficShapingHandler = this;
        synchronized (channelTrafficShapingHandler) {
            if (l2 == 0L && this.messagesQueue.isEmpty()) {
                this.trafficCounter.bytesRealWriteFlowControl(l);
                channelHandlerContext.write(object, channelPromise);
                return;
            }
            toSend = new ToSend(l2 + l3, object, channelPromise, null);
            this.messagesQueue.addLast(toSend);
            this.queueSize += l;
            this.checkWriteSuspend(channelHandlerContext, l2, this.queueSize);
        }
        long l4 = toSend.relativeTimeAction;
        channelHandlerContext.executor().schedule(new Runnable(this, channelHandlerContext, l4){
            final ChannelHandlerContext val$ctx;
            final long val$futureNow;
            final ChannelTrafficShapingHandler this$0;
            {
                this.this$0 = channelTrafficShapingHandler;
                this.val$ctx = channelHandlerContext;
                this.val$futureNow = l;
            }

            @Override
            public void run() {
                ChannelTrafficShapingHandler.access$100(this.this$0, this.val$ctx, this.val$futureNow);
            }
        }, l2, TimeUnit.MILLISECONDS);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendAllValid(ChannelHandlerContext channelHandlerContext, long l) {
        ChannelTrafficShapingHandler channelTrafficShapingHandler = this;
        synchronized (channelTrafficShapingHandler) {
            ToSend toSend = this.messagesQueue.pollFirst();
            while (toSend != null) {
                if (toSend.relativeTimeAction <= l) {
                    long l2 = this.calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(l2);
                    this.queueSize -= l2;
                } else {
                    this.messagesQueue.addFirst(toSend);
                    break;
                }
                channelHandlerContext.write(toSend.toSend, toSend.promise);
                toSend = this.messagesQueue.pollFirst();
            }
            if (this.messagesQueue.isEmpty()) {
                this.releaseWriteSuspended(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }

    public long queueSize() {
        return this.queueSize;
    }

    static void access$100(ChannelTrafficShapingHandler channelTrafficShapingHandler, ChannelHandlerContext channelHandlerContext, long l) {
        channelTrafficShapingHandler.sendAllValid(channelHandlerContext, l);
    }

    private static final class ToSend {
        final long relativeTimeAction;
        final Object toSend;
        final ChannelPromise promise;

        private ToSend(long l, Object object, ChannelPromise channelPromise) {
            this.relativeTimeAction = l;
            this.toSend = object;
            this.promise = channelPromise;
        }

        ToSend(long l, Object object, ChannelPromise channelPromise, 1 var5_4) {
            this(l, object, channelPromise);
        }
    }
}

