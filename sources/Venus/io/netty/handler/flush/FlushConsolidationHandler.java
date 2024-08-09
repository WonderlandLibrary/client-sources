/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.flush;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.Future;

public class FlushConsolidationHandler
extends ChannelDuplexHandler {
    private final int explicitFlushAfterFlushes;
    private final boolean consolidateWhenNoReadInProgress;
    private final Runnable flushTask;
    private int flushPendingCount;
    private boolean readInProgress;
    private ChannelHandlerContext ctx;
    private Future<?> nextScheduledFlush;

    public FlushConsolidationHandler() {
        this(256, false);
    }

    public FlushConsolidationHandler(int n) {
        this(n, false);
    }

    public FlushConsolidationHandler(int n, boolean bl) {
        if (n <= 0) {
            throw new IllegalArgumentException("explicitFlushAfterFlushes: " + n + " (expected: > 0)");
        }
        this.explicitFlushAfterFlushes = n;
        this.consolidateWhenNoReadInProgress = bl;
        this.flushTask = bl ? new Runnable(this){
            final FlushConsolidationHandler this$0;
            {
                this.this$0 = flushConsolidationHandler;
            }

            @Override
            public void run() {
                if (FlushConsolidationHandler.access$000(this.this$0) > 0 && !FlushConsolidationHandler.access$100(this.this$0)) {
                    FlushConsolidationHandler.access$002(this.this$0, 0);
                    FlushConsolidationHandler.access$200(this.this$0).flush();
                    FlushConsolidationHandler.access$302(this.this$0, null);
                }
            }
        } : null;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.readInProgress) {
            if (++this.flushPendingCount == this.explicitFlushAfterFlushes) {
                this.flushNow(channelHandlerContext);
            }
        } else if (this.consolidateWhenNoReadInProgress) {
            if (++this.flushPendingCount == this.explicitFlushAfterFlushes) {
                this.flushNow(channelHandlerContext);
            } else {
                this.scheduleFlush(channelHandlerContext);
            }
        } else {
            this.flushNow(channelHandlerContext);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.resetReadAndFlushIfNeeded(channelHandlerContext);
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        this.readInProgress = true;
        channelHandlerContext.fireChannelRead(object);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        this.resetReadAndFlushIfNeeded(channelHandlerContext);
        channelHandlerContext.fireExceptionCaught(throwable);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.resetReadAndFlushIfNeeded(channelHandlerContext);
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.resetReadAndFlushIfNeeded(channelHandlerContext);
        channelHandlerContext.close(channelPromise);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!channelHandlerContext.channel().isWritable()) {
            this.flushIfNeeded(channelHandlerContext);
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.flushIfNeeded(channelHandlerContext);
    }

    private void resetReadAndFlushIfNeeded(ChannelHandlerContext channelHandlerContext) {
        this.readInProgress = false;
        this.flushIfNeeded(channelHandlerContext);
    }

    private void flushIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (this.flushPendingCount > 0) {
            this.flushNow(channelHandlerContext);
        }
    }

    private void flushNow(ChannelHandlerContext channelHandlerContext) {
        this.cancelScheduledFlush();
        this.flushPendingCount = 0;
        channelHandlerContext.flush();
    }

    private void scheduleFlush(ChannelHandlerContext channelHandlerContext) {
        if (this.nextScheduledFlush == null) {
            this.nextScheduledFlush = channelHandlerContext.channel().eventLoop().submit(this.flushTask);
        }
    }

    private void cancelScheduledFlush() {
        if (this.nextScheduledFlush != null) {
            this.nextScheduledFlush.cancel(false);
            this.nextScheduledFlush = null;
        }
    }

    static int access$000(FlushConsolidationHandler flushConsolidationHandler) {
        return flushConsolidationHandler.flushPendingCount;
    }

    static boolean access$100(FlushConsolidationHandler flushConsolidationHandler) {
        return flushConsolidationHandler.readInProgress;
    }

    static int access$002(FlushConsolidationHandler flushConsolidationHandler, int n) {
        flushConsolidationHandler.flushPendingCount = n;
        return flushConsolidationHandler.flushPendingCount;
    }

    static ChannelHandlerContext access$200(FlushConsolidationHandler flushConsolidationHandler) {
        return flushConsolidationHandler.ctx;
    }

    static Future access$302(FlushConsolidationHandler flushConsolidationHandler, Future future) {
        flushConsolidationHandler.nextScheduledFlush = future;
        return flushConsolidationHandler.nextScheduledFlush;
    }
}

