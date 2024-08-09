/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.timeout;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class IdleStateHandler
extends ChannelDuplexHandler {
    private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    private final ChannelFutureListener writeListener = new ChannelFutureListener(this){
        final IdleStateHandler this$0;
        {
            this.this$0 = idleStateHandler;
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            IdleStateHandler.access$002(this.this$0, this.this$0.ticksInNanos());
            IdleStateHandler.access$102(this.this$0, IdleStateHandler.access$202(this.this$0, true));
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    private final boolean observeOutput;
    private final long readerIdleTimeNanos;
    private final long writerIdleTimeNanos;
    private final long allIdleTimeNanos;
    private ScheduledFuture<?> readerIdleTimeout;
    private long lastReadTime;
    private boolean firstReaderIdleEvent = true;
    private ScheduledFuture<?> writerIdleTimeout;
    private long lastWriteTime;
    private boolean firstWriterIdleEvent = true;
    private ScheduledFuture<?> allIdleTimeout;
    private boolean firstAllIdleEvent = true;
    private byte state;
    private boolean reading;
    private long lastChangeCheckTimeStamp;
    private int lastMessageHashCode;
    private long lastPendingWriteBytes;

    public IdleStateHandler(int n, int n2, int n3) {
        this(n, n2, n3, TimeUnit.SECONDS);
    }

    public IdleStateHandler(long l, long l2, long l3, TimeUnit timeUnit) {
        this(false, l, l2, l3, timeUnit);
    }

    public IdleStateHandler(boolean bl, long l, long l2, long l3, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        this.observeOutput = bl;
        this.readerIdleTimeNanos = l <= 0L ? 0L : Math.max(timeUnit.toNanos(l), MIN_TIMEOUT_NANOS);
        this.writerIdleTimeNanos = l2 <= 0L ? 0L : Math.max(timeUnit.toNanos(l2), MIN_TIMEOUT_NANOS);
        this.allIdleTimeNanos = l3 <= 0L ? 0L : Math.max(timeUnit.toNanos(l3), MIN_TIMEOUT_NANOS);
    }

    public long getReaderIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.readerIdleTimeNanos);
    }

    public long getWriterIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.writerIdleTimeNanos);
    }

    public long getAllIdleTimeInMillis() {
        return TimeUnit.NANOSECONDS.toMillis(this.allIdleTimeNanos);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isRegistered()) {
            this.initialize(channelHandlerContext);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.destroy();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.channel().isActive()) {
            this.initialize(channelHandlerContext);
        }
        super.channelRegistered(channelHandlerContext);
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.initialize(channelHandlerContext);
        super.channelActive(channelHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.destroy();
        super.channelInactive(channelHandlerContext);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (this.readerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) {
            this.reading = true;
            this.firstAllIdleEvent = true;
            this.firstReaderIdleEvent = true;
        }
        channelHandlerContext.fireChannelRead(object);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if ((this.readerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) && this.reading) {
            this.lastReadTime = this.ticksInNanos();
            this.reading = false;
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (this.writerIdleTimeNanos > 0L || this.allIdleTimeNanos > 0L) {
            channelHandlerContext.write(object, channelPromise.unvoid()).addListener(this.writeListener);
        } else {
            channelHandlerContext.write(object, channelPromise);
        }
    }

    private void initialize(ChannelHandlerContext channelHandlerContext) {
        switch (this.state) {
            case 1: 
            case 2: {
                return;
            }
        }
        this.state = 1;
        this.initOutputChanged(channelHandlerContext);
        this.lastReadTime = this.lastWriteTime = this.ticksInNanos();
        if (this.readerIdleTimeNanos > 0L) {
            this.readerIdleTimeout = this.schedule(channelHandlerContext, new ReaderIdleTimeoutTask(this, channelHandlerContext), this.readerIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
        if (this.writerIdleTimeNanos > 0L) {
            this.writerIdleTimeout = this.schedule(channelHandlerContext, new WriterIdleTimeoutTask(this, channelHandlerContext), this.writerIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
        if (this.allIdleTimeNanos > 0L) {
            this.allIdleTimeout = this.schedule(channelHandlerContext, new AllIdleTimeoutTask(this, channelHandlerContext), this.allIdleTimeNanos, TimeUnit.NANOSECONDS);
        }
    }

    long ticksInNanos() {
        return System.nanoTime();
    }

    ScheduledFuture<?> schedule(ChannelHandlerContext channelHandlerContext, Runnable runnable, long l, TimeUnit timeUnit) {
        return channelHandlerContext.executor().schedule(runnable, l, timeUnit);
    }

    private void destroy() {
        this.state = (byte)2;
        if (this.readerIdleTimeout != null) {
            this.readerIdleTimeout.cancel(false);
            this.readerIdleTimeout = null;
        }
        if (this.writerIdleTimeout != null) {
            this.writerIdleTimeout.cancel(false);
            this.writerIdleTimeout = null;
        }
        if (this.allIdleTimeout != null) {
            this.allIdleTimeout.cancel(false);
            this.allIdleTimeout = null;
        }
    }

    protected void channelIdle(ChannelHandlerContext channelHandlerContext, IdleStateEvent idleStateEvent) throws Exception {
        channelHandlerContext.fireUserEventTriggered(idleStateEvent);
    }

    protected IdleStateEvent newIdleStateEvent(IdleState idleState, boolean bl) {
        switch (2.$SwitchMap$io$netty$handler$timeout$IdleState[idleState.ordinal()]) {
            case 1: {
                return bl ? IdleStateEvent.FIRST_ALL_IDLE_STATE_EVENT : IdleStateEvent.ALL_IDLE_STATE_EVENT;
            }
            case 2: {
                return bl ? IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT : IdleStateEvent.READER_IDLE_STATE_EVENT;
            }
            case 3: {
                return bl ? IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT : IdleStateEvent.WRITER_IDLE_STATE_EVENT;
            }
        }
        throw new IllegalArgumentException("Unhandled: state=" + (Object)((Object)idleState) + ", first=" + bl);
    }

    private void initOutputChanged(ChannelHandlerContext channelHandlerContext) {
        Channel channel;
        Channel.Unsafe unsafe;
        ChannelOutboundBuffer channelOutboundBuffer;
        if (this.observeOutput && (channelOutboundBuffer = (unsafe = (channel = channelHandlerContext.channel()).unsafe()).outboundBuffer()) != null) {
            this.lastMessageHashCode = System.identityHashCode(channelOutboundBuffer.current());
            this.lastPendingWriteBytes = channelOutboundBuffer.totalPendingWriteBytes();
        }
    }

    private boolean hasOutputChanged(ChannelHandlerContext channelHandlerContext, boolean bl) {
        if (this.observeOutput) {
            Channel channel;
            Channel.Unsafe unsafe;
            ChannelOutboundBuffer channelOutboundBuffer;
            if (this.lastChangeCheckTimeStamp != this.lastWriteTime) {
                this.lastChangeCheckTimeStamp = this.lastWriteTime;
                if (!bl) {
                    return false;
                }
            }
            if ((channelOutboundBuffer = (unsafe = (channel = channelHandlerContext.channel()).unsafe()).outboundBuffer()) != null) {
                int n = System.identityHashCode(channelOutboundBuffer.current());
                long l = channelOutboundBuffer.totalPendingWriteBytes();
                if (n != this.lastMessageHashCode || l != this.lastPendingWriteBytes) {
                    this.lastMessageHashCode = n;
                    this.lastPendingWriteBytes = l;
                    if (!bl) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static long access$002(IdleStateHandler idleStateHandler, long l) {
        idleStateHandler.lastWriteTime = l;
        return idleStateHandler.lastWriteTime;
    }

    static boolean access$102(IdleStateHandler idleStateHandler, boolean bl) {
        idleStateHandler.firstWriterIdleEvent = bl;
        return idleStateHandler.firstWriterIdleEvent;
    }

    static boolean access$202(IdleStateHandler idleStateHandler, boolean bl) {
        idleStateHandler.firstAllIdleEvent = bl;
        return idleStateHandler.firstAllIdleEvent;
    }

    static long access$300(IdleStateHandler idleStateHandler) {
        return idleStateHandler.readerIdleTimeNanos;
    }

    static boolean access$400(IdleStateHandler idleStateHandler) {
        return idleStateHandler.reading;
    }

    static long access$500(IdleStateHandler idleStateHandler) {
        return idleStateHandler.lastReadTime;
    }

    static ScheduledFuture access$602(IdleStateHandler idleStateHandler, ScheduledFuture scheduledFuture) {
        idleStateHandler.readerIdleTimeout = scheduledFuture;
        return idleStateHandler.readerIdleTimeout;
    }

    static boolean access$700(IdleStateHandler idleStateHandler) {
        return idleStateHandler.firstReaderIdleEvent;
    }

    static boolean access$702(IdleStateHandler idleStateHandler, boolean bl) {
        idleStateHandler.firstReaderIdleEvent = bl;
        return idleStateHandler.firstReaderIdleEvent;
    }

    static long access$000(IdleStateHandler idleStateHandler) {
        return idleStateHandler.lastWriteTime;
    }

    static long access$800(IdleStateHandler idleStateHandler) {
        return idleStateHandler.writerIdleTimeNanos;
    }

    static ScheduledFuture access$902(IdleStateHandler idleStateHandler, ScheduledFuture scheduledFuture) {
        idleStateHandler.writerIdleTimeout = scheduledFuture;
        return idleStateHandler.writerIdleTimeout;
    }

    static boolean access$100(IdleStateHandler idleStateHandler) {
        return idleStateHandler.firstWriterIdleEvent;
    }

    static boolean access$1000(IdleStateHandler idleStateHandler, ChannelHandlerContext channelHandlerContext, boolean bl) {
        return idleStateHandler.hasOutputChanged(channelHandlerContext, bl);
    }

    static long access$1100(IdleStateHandler idleStateHandler) {
        return idleStateHandler.allIdleTimeNanos;
    }

    static ScheduledFuture access$1202(IdleStateHandler idleStateHandler, ScheduledFuture scheduledFuture) {
        idleStateHandler.allIdleTimeout = scheduledFuture;
        return idleStateHandler.allIdleTimeout;
    }

    static boolean access$200(IdleStateHandler idleStateHandler) {
        return idleStateHandler.firstAllIdleEvent;
    }

    private final class AllIdleTimeoutTask
    extends AbstractIdleTask {
        final IdleStateHandler this$0;

        AllIdleTimeoutTask(IdleStateHandler idleStateHandler, ChannelHandlerContext channelHandlerContext) {
            this.this$0 = idleStateHandler;
            super(channelHandlerContext);
        }

        @Override
        protected void run(ChannelHandlerContext channelHandlerContext) {
            long l = IdleStateHandler.access$1100(this.this$0);
            if (!IdleStateHandler.access$400(this.this$0)) {
                l -= this.this$0.ticksInNanos() - Math.max(IdleStateHandler.access$500(this.this$0), IdleStateHandler.access$000(this.this$0));
            }
            if (l <= 0L) {
                IdleStateHandler.access$1202(this.this$0, this.this$0.schedule(channelHandlerContext, this, IdleStateHandler.access$1100(this.this$0), TimeUnit.NANOSECONDS));
                boolean bl = IdleStateHandler.access$200(this.this$0);
                IdleStateHandler.access$202(this.this$0, false);
                try {
                    if (IdleStateHandler.access$1000(this.this$0, channelHandlerContext, bl)) {
                        return;
                    }
                    IdleStateEvent idleStateEvent = this.this$0.newIdleStateEvent(IdleState.ALL_IDLE, bl);
                    this.this$0.channelIdle(channelHandlerContext, idleStateEvent);
                } catch (Throwable throwable) {
                    channelHandlerContext.fireExceptionCaught(throwable);
                }
            } else {
                IdleStateHandler.access$1202(this.this$0, this.this$0.schedule(channelHandlerContext, this, l, TimeUnit.NANOSECONDS));
            }
        }
    }

    private final class WriterIdleTimeoutTask
    extends AbstractIdleTask {
        final IdleStateHandler this$0;

        WriterIdleTimeoutTask(IdleStateHandler idleStateHandler, ChannelHandlerContext channelHandlerContext) {
            this.this$0 = idleStateHandler;
            super(channelHandlerContext);
        }

        @Override
        protected void run(ChannelHandlerContext channelHandlerContext) {
            long l = IdleStateHandler.access$000(this.this$0);
            long l2 = IdleStateHandler.access$800(this.this$0) - (this.this$0.ticksInNanos() - l);
            if (l2 <= 0L) {
                IdleStateHandler.access$902(this.this$0, this.this$0.schedule(channelHandlerContext, this, IdleStateHandler.access$800(this.this$0), TimeUnit.NANOSECONDS));
                boolean bl = IdleStateHandler.access$100(this.this$0);
                IdleStateHandler.access$102(this.this$0, false);
                try {
                    if (IdleStateHandler.access$1000(this.this$0, channelHandlerContext, bl)) {
                        return;
                    }
                    IdleStateEvent idleStateEvent = this.this$0.newIdleStateEvent(IdleState.WRITER_IDLE, bl);
                    this.this$0.channelIdle(channelHandlerContext, idleStateEvent);
                } catch (Throwable throwable) {
                    channelHandlerContext.fireExceptionCaught(throwable);
                }
            } else {
                IdleStateHandler.access$902(this.this$0, this.this$0.schedule(channelHandlerContext, this, l2, TimeUnit.NANOSECONDS));
            }
        }
    }

    private final class ReaderIdleTimeoutTask
    extends AbstractIdleTask {
        final IdleStateHandler this$0;

        ReaderIdleTimeoutTask(IdleStateHandler idleStateHandler, ChannelHandlerContext channelHandlerContext) {
            this.this$0 = idleStateHandler;
            super(channelHandlerContext);
        }

        @Override
        protected void run(ChannelHandlerContext channelHandlerContext) {
            long l = IdleStateHandler.access$300(this.this$0);
            if (!IdleStateHandler.access$400(this.this$0)) {
                l -= this.this$0.ticksInNanos() - IdleStateHandler.access$500(this.this$0);
            }
            if (l <= 0L) {
                IdleStateHandler.access$602(this.this$0, this.this$0.schedule(channelHandlerContext, this, IdleStateHandler.access$300(this.this$0), TimeUnit.NANOSECONDS));
                boolean bl = IdleStateHandler.access$700(this.this$0);
                IdleStateHandler.access$702(this.this$0, false);
                try {
                    IdleStateEvent idleStateEvent = this.this$0.newIdleStateEvent(IdleState.READER_IDLE, bl);
                    this.this$0.channelIdle(channelHandlerContext, idleStateEvent);
                } catch (Throwable throwable) {
                    channelHandlerContext.fireExceptionCaught(throwable);
                }
            } else {
                IdleStateHandler.access$602(this.this$0, this.this$0.schedule(channelHandlerContext, this, l, TimeUnit.NANOSECONDS));
            }
        }
    }

    private static abstract class AbstractIdleTask
    implements Runnable {
        private final ChannelHandlerContext ctx;

        AbstractIdleTask(ChannelHandlerContext channelHandlerContext) {
            this.ctx = channelHandlerContext;
        }

        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            this.run(this.ctx);
        }

        protected abstract void run(ChannelHandlerContext var1);
    }
}

