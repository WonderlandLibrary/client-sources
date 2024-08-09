/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.handler.traffic.TrafficCounter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.TimeUnit;

public abstract class AbstractTrafficShapingHandler
extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractTrafficShapingHandler.class);
    public static final long DEFAULT_CHECK_INTERVAL = 1000L;
    public static final long DEFAULT_MAX_TIME = 15000L;
    static final long DEFAULT_MAX_SIZE = 0x400000L;
    static final long MINIMAL_WAIT = 10L;
    protected TrafficCounter trafficCounter;
    private volatile long writeLimit;
    private volatile long readLimit;
    protected volatile long maxTime = 15000L;
    protected volatile long checkInterval = 1000L;
    static final AttributeKey<Boolean> READ_SUSPENDED = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".READ_SUSPENDED");
    static final AttributeKey<Runnable> REOPEN_TASK = AttributeKey.valueOf(AbstractTrafficShapingHandler.class.getName() + ".REOPEN_TASK");
    volatile long maxWriteDelay = 4000L;
    volatile long maxWriteSize = 0x400000L;
    final int userDefinedWritabilityIndex;
    static final int CHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 1;
    static final int GLOBAL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 2;
    static final int GLOBALCHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 3;

    void setTrafficCounter(TrafficCounter trafficCounter) {
        this.trafficCounter = trafficCounter;
    }

    protected int userDefinedWritabilityIndex() {
        return 0;
    }

    protected AbstractTrafficShapingHandler(long l, long l2, long l3, long l4) {
        if (l4 <= 0L) {
            throw new IllegalArgumentException("maxTime must be positive");
        }
        this.userDefinedWritabilityIndex = this.userDefinedWritabilityIndex();
        this.writeLimit = l;
        this.readLimit = l2;
        this.checkInterval = l3;
        this.maxTime = l4;
    }

    protected AbstractTrafficShapingHandler(long l, long l2, long l3) {
        this(l, l2, l3, 15000L);
    }

    protected AbstractTrafficShapingHandler(long l, long l2) {
        this(l, l2, 1000L, 15000L);
    }

    protected AbstractTrafficShapingHandler() {
        this(0L, 0L, 1000L, 15000L);
    }

    protected AbstractTrafficShapingHandler(long l) {
        this(0L, 0L, l, 15000L);
    }

    public void configure(long l, long l2, long l3) {
        this.configure(l, l2);
        this.configure(l3);
    }

    public void configure(long l, long l2) {
        this.writeLimit = l;
        this.readLimit = l2;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public void configure(long l) {
        this.checkInterval = l;
        if (this.trafficCounter != null) {
            this.trafficCounter.configure(this.checkInterval);
        }
    }

    public long getWriteLimit() {
        return this.writeLimit;
    }

    public void setWriteLimit(long l) {
        this.writeLimit = l;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getReadLimit() {
        return this.readLimit;
    }

    public void setReadLimit(long l) {
        this.readLimit = l;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getCheckInterval() {
        return this.checkInterval;
    }

    public void setCheckInterval(long l) {
        this.checkInterval = l;
        if (this.trafficCounter != null) {
            this.trafficCounter.configure(l);
        }
    }

    public void setMaxTimeWait(long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException("maxTime must be positive");
        }
        this.maxTime = l;
    }

    public long getMaxTimeWait() {
        return this.maxTime;
    }

    public long getMaxWriteDelay() {
        return this.maxWriteDelay;
    }

    public void setMaxWriteDelay(long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException("maxWriteDelay must be positive");
        }
        this.maxWriteDelay = l;
    }

    public long getMaxWriteSize() {
        return this.maxWriteSize;
    }

    public void setMaxWriteSize(long l) {
        this.maxWriteSize = l;
    }

    protected void doAccounting(TrafficCounter trafficCounter) {
    }

    void releaseReadSuspended(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        channel.attr(READ_SUSPENDED).set(false);
        channel.config().setAutoRead(true);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        long l = this.calculateSize(object);
        long l2 = TrafficCounter.milliSecondFromNano();
        if (l > 0L) {
            long l3 = this.trafficCounter.readTimeToWait(l, this.readLimit, this.maxTime, l2);
            if ((l3 = this.checkWaitReadTime(channelHandlerContext, l3, l2)) >= 10L) {
                Channel channel = channelHandlerContext.channel();
                ChannelConfig channelConfig = channel.config();
                if (logger.isDebugEnabled()) {
                    logger.debug("Read suspend: " + l3 + ':' + channelConfig.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(channelHandlerContext));
                }
                if (channelConfig.isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(channelHandlerContext)) {
                    channelConfig.setAutoRead(false);
                    channel.attr(READ_SUSPENDED).set(true);
                    Attribute<Runnable> attribute = channel.attr(REOPEN_TASK);
                    Runnable runnable = attribute.get();
                    if (runnable == null) {
                        runnable = new ReopenReadTimerTask(channelHandlerContext);
                        attribute.set(runnable);
                    }
                    channelHandlerContext.executor().schedule(runnable, l3, TimeUnit.MILLISECONDS);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Suspend final status => " + channelConfig.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(channelHandlerContext) + " will reopened at: " + l3);
                    }
                }
            }
        }
        this.informReadOperation(channelHandlerContext, l2);
        channelHandlerContext.fireChannelRead(object);
    }

    long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long l, long l2) {
        return l;
    }

    void informReadOperation(ChannelHandlerContext channelHandlerContext, long l) {
    }

    protected static boolean isHandlerActive(ChannelHandlerContext channelHandlerContext) {
        Boolean bl = channelHandlerContext.channel().attr(READ_SUSPENDED).get();
        return bl == null || Boolean.FALSE.equals(bl);
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) {
        if (AbstractTrafficShapingHandler.isHandlerActive(channelHandlerContext)) {
            channelHandlerContext.read();
        }
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        long l;
        long l2 = this.calculateSize(object);
        long l3 = TrafficCounter.milliSecondFromNano();
        if (l2 > 0L && (l = this.trafficCounter.writeTimeToWait(l2, this.writeLimit, this.maxTime, l3)) >= 10L) {
            if (logger.isDebugEnabled()) {
                logger.debug("Write suspend: " + l + ':' + channelHandlerContext.channel().config().isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(channelHandlerContext));
            }
            this.submitWrite(channelHandlerContext, object, l2, l, l3, channelPromise);
            return;
        }
        this.submitWrite(channelHandlerContext, object, l2, 0L, l3, channelPromise);
    }

    @Deprecated
    protected void submitWrite(ChannelHandlerContext channelHandlerContext, Object object, long l, ChannelPromise channelPromise) {
        this.submitWrite(channelHandlerContext, object, this.calculateSize(object), l, TrafficCounter.milliSecondFromNano(), channelPromise);
    }

    abstract void submitWrite(ChannelHandlerContext var1, Object var2, long var3, long var5, long var7, ChannelPromise var9);

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.setUserDefinedWritability(channelHandlerContext, false);
        super.channelRegistered(channelHandlerContext);
    }

    void setUserDefinedWritability(ChannelHandlerContext channelHandlerContext, boolean bl) {
        ChannelOutboundBuffer channelOutboundBuffer = channelHandlerContext.channel().unsafe().outboundBuffer();
        if (channelOutboundBuffer != null) {
            channelOutboundBuffer.setUserDefinedWritability(this.userDefinedWritabilityIndex, bl);
        }
    }

    void checkWriteSuspend(ChannelHandlerContext channelHandlerContext, long l, long l2) {
        if (l2 > this.maxWriteSize || l > this.maxWriteDelay) {
            this.setUserDefinedWritability(channelHandlerContext, true);
        }
    }

    void releaseWriteSuspended(ChannelHandlerContext channelHandlerContext) {
        this.setUserDefinedWritability(channelHandlerContext, false);
    }

    public TrafficCounter trafficCounter() {
        return this.trafficCounter;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(290).append("TrafficShaping with Write Limit: ").append(this.writeLimit).append(" Read Limit: ").append(this.readLimit).append(" CheckInterval: ").append(this.checkInterval).append(" maxDelay: ").append(this.maxWriteDelay).append(" maxSize: ").append(this.maxWriteSize).append(" and Counter: ");
        if (this.trafficCounter != null) {
            stringBuilder.append(this.trafficCounter);
        } else {
            stringBuilder.append("none");
        }
        return stringBuilder.toString();
    }

    protected long calculateSize(Object object) {
        if (object instanceof ByteBuf) {
            return ((ByteBuf)object).readableBytes();
        }
        if (object instanceof ByteBufHolder) {
            return ((ByteBufHolder)object).content().readableBytes();
        }
        return -1L;
    }

    static InternalLogger access$000() {
        return logger;
    }

    static final class ReopenReadTimerTask
    implements Runnable {
        final ChannelHandlerContext ctx;

        ReopenReadTimerTask(ChannelHandlerContext channelHandlerContext) {
            this.ctx = channelHandlerContext;
        }

        @Override
        public void run() {
            Channel channel = this.ctx.channel();
            ChannelConfig channelConfig = channel.config();
            if (!channelConfig.isAutoRead() && AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                    AbstractTrafficShapingHandler.access$000().debug("Not unsuspend: " + channelConfig.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                }
                channel.attr(READ_SUSPENDED).set(false);
            } else {
                if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                    if (channelConfig.isAutoRead() && !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                        AbstractTrafficShapingHandler.access$000().debug("Unsuspend: " + channelConfig.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    } else {
                        AbstractTrafficShapingHandler.access$000().debug("Normal unsuspend: " + channelConfig.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    }
                }
                channel.attr(READ_SUSPENDED).set(false);
                channelConfig.setAutoRead(true);
                channel.read();
            }
            if (AbstractTrafficShapingHandler.access$000().isDebugEnabled()) {
                AbstractTrafficShapingHandler.access$000().debug("Unsuspend final status => " + channelConfig.isAutoRead() + ':' + AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
            }
        }
    }
}

