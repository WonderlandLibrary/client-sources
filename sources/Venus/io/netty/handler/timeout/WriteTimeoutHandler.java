/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.timeout;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.WriteTimeoutException;
import io.netty.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WriteTimeoutHandler
extends ChannelOutboundHandlerAdapter {
    private static final long MIN_TIMEOUT_NANOS;
    private final long timeoutNanos;
    private WriteTimeoutTask lastTask;
    private boolean closed;
    static final boolean $assertionsDisabled;

    public WriteTimeoutHandler(int n) {
        this(n, TimeUnit.SECONDS);
    }

    public WriteTimeoutHandler(long l, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        this.timeoutNanos = l <= 0L ? 0L : Math.max(timeUnit.toNanos(l), MIN_TIMEOUT_NANOS);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (this.timeoutNanos > 0L) {
            channelPromise = channelPromise.unvoid();
            this.scheduleTimeout(channelHandlerContext, channelPromise);
        }
        channelHandlerContext.write(object, channelPromise);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        WriteTimeoutTask writeTimeoutTask = this.lastTask;
        this.lastTask = null;
        while (writeTimeoutTask != null) {
            writeTimeoutTask.scheduledFuture.cancel(false);
            WriteTimeoutTask writeTimeoutTask2 = writeTimeoutTask.prev;
            writeTimeoutTask.prev = null;
            writeTimeoutTask.next = null;
            writeTimeoutTask = writeTimeoutTask2;
        }
    }

    private void scheduleTimeout(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        WriteTimeoutTask writeTimeoutTask = new WriteTimeoutTask(this, channelHandlerContext, channelPromise);
        writeTimeoutTask.scheduledFuture = channelHandlerContext.executor().schedule(writeTimeoutTask, this.timeoutNanos, TimeUnit.NANOSECONDS);
        if (!writeTimeoutTask.scheduledFuture.isDone()) {
            this.addWriteTimeoutTask(writeTimeoutTask);
            channelPromise.addListener(writeTimeoutTask);
        }
    }

    private void addWriteTimeoutTask(WriteTimeoutTask writeTimeoutTask) {
        if (this.lastTask != null) {
            this.lastTask.next = writeTimeoutTask;
            writeTimeoutTask.prev = this.lastTask;
        }
        this.lastTask = writeTimeoutTask;
    }

    private void removeWriteTimeoutTask(WriteTimeoutTask writeTimeoutTask) {
        if (writeTimeoutTask == this.lastTask) {
            if (!$assertionsDisabled && writeTimeoutTask.next != null) {
                throw new AssertionError();
            }
            this.lastTask = this.lastTask.prev;
            if (this.lastTask != null) {
                this.lastTask.next = null;
            }
        } else {
            if (writeTimeoutTask.prev == null && writeTimeoutTask.next == null) {
                return;
            }
            if (writeTimeoutTask.prev == null) {
                writeTimeoutTask.next.prev = null;
            } else {
                writeTimeoutTask.prev.next = writeTimeoutTask.next;
                writeTimeoutTask.next.prev = writeTimeoutTask.prev;
            }
        }
        writeTimeoutTask.prev = null;
        writeTimeoutTask.next = null;
    }

    protected void writeTimedOut(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.closed) {
            channelHandlerContext.fireExceptionCaught(WriteTimeoutException.INSTANCE);
            channelHandlerContext.close();
            this.closed = true;
        }
    }

    static void access$000(WriteTimeoutHandler writeTimeoutHandler, WriteTimeoutTask writeTimeoutTask) {
        writeTimeoutHandler.removeWriteTimeoutTask(writeTimeoutTask);
    }

    static {
        $assertionsDisabled = !WriteTimeoutHandler.class.desiredAssertionStatus();
        MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    }

    private final class WriteTimeoutTask
    implements Runnable,
    ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;
        WriteTimeoutTask prev;
        WriteTimeoutTask next;
        ScheduledFuture<?> scheduledFuture;
        final WriteTimeoutHandler this$0;

        WriteTimeoutTask(WriteTimeoutHandler writeTimeoutHandler, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            this.this$0 = writeTimeoutHandler;
            this.ctx = channelHandlerContext;
            this.promise = channelPromise;
        }

        @Override
        public void run() {
            if (!this.promise.isDone()) {
                try {
                    this.this$0.writeTimedOut(this.ctx);
                } catch (Throwable throwable) {
                    this.ctx.fireExceptionCaught(throwable);
                }
            }
            WriteTimeoutHandler.access$000(this.this$0, this);
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            this.scheduledFuture.cancel(false);
            WriteTimeoutHandler.access$000(this.this$0, this);
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    }
}

