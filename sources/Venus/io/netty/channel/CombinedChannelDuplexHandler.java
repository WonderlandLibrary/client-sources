/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundInvoker;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPipelineException;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;

public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler>
extends ChannelDuplexHandler {
    private static final InternalLogger logger;
    private DelegatingChannelHandlerContext inboundCtx;
    private DelegatingChannelHandlerContext outboundCtx;
    private volatile boolean handlerAdded;
    private I inboundHandler;
    private O outboundHandler;
    static final boolean $assertionsDisabled;

    protected CombinedChannelDuplexHandler() {
        this.ensureNotSharable();
    }

    public CombinedChannelDuplexHandler(I i, O o) {
        this.ensureNotSharable();
        this.init(i, o);
    }

    protected final void init(I i, O o) {
        this.validate(i, o);
        this.inboundHandler = i;
        this.outboundHandler = o;
    }

    private void validate(I i, O o) {
        if (this.inboundHandler != null) {
            throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
        }
        if (i == null) {
            throw new NullPointerException("inboundHandler");
        }
        if (o == null) {
            throw new NullPointerException("outboundHandler");
        }
        if (i instanceof ChannelOutboundHandler) {
            throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
        }
        if (o instanceof ChannelInboundHandler) {
            throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
        }
    }

    protected final I inboundHandler() {
        return this.inboundHandler;
    }

    protected final O outboundHandler() {
        return this.outboundHandler;
    }

    private void checkAdded() {
        if (!this.handlerAdded) {
            throw new IllegalStateException("handler not added to pipeline yet");
        }
    }

    public final void removeInboundHandler() {
        this.checkAdded();
        this.inboundCtx.remove();
    }

    public final void removeOutboundHandler() {
        this.checkAdded();
        this.outboundCtx.remove();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.inboundHandler == null) {
            throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
        }
        this.outboundCtx = new DelegatingChannelHandlerContext(channelHandlerContext, (ChannelHandler)this.outboundHandler);
        this.inboundCtx = new DelegatingChannelHandlerContext(this, channelHandlerContext, (ChannelHandler)this.inboundHandler){
            final CombinedChannelDuplexHandler this$0;
            {
                this.this$0 = combinedChannelDuplexHandler;
                super(channelHandlerContext, channelHandler);
            }

            @Override
            public ChannelHandlerContext fireExceptionCaught(Throwable throwable) {
                if (!CombinedChannelDuplexHandler.access$000((CombinedChannelDuplexHandler)this.this$0).removed) {
                    try {
                        CombinedChannelDuplexHandler.access$100(this.this$0).exceptionCaught(CombinedChannelDuplexHandler.access$000(this.this$0), throwable);
                    } catch (Throwable throwable2) {
                        if (CombinedChannelDuplexHandler.access$200().isDebugEnabled()) {
                            CombinedChannelDuplexHandler.access$200().debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", (Object)ThrowableUtil.stackTraceToString(throwable2), (Object)throwable);
                        } else if (CombinedChannelDuplexHandler.access$200().isWarnEnabled()) {
                            CombinedChannelDuplexHandler.access$200().warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", (Object)throwable2, (Object)throwable);
                        }
                    }
                } else {
                    super.fireExceptionCaught(throwable);
                }
                return this;
            }

            @Override
            public ChannelInboundInvoker fireExceptionCaught(Throwable throwable) {
                return this.fireExceptionCaught(throwable);
            }
        };
        this.handlerAdded = true;
        try {
            this.inboundHandler.handlerAdded(this.inboundCtx);
        } finally {
            this.outboundHandler.handlerAdded(this.outboundCtx);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.inboundCtx.remove();
        } finally {
            this.outboundCtx.remove();
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelRegistered(this.inboundCtx);
        } else {
            this.inboundCtx.fireChannelRegistered();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelUnregistered(this.inboundCtx);
        } else {
            this.inboundCtx.fireChannelUnregistered();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelActive(this.inboundCtx);
        } else {
            this.inboundCtx.fireChannelActive();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelInactive(this.inboundCtx);
        } else {
            this.inboundCtx.fireChannelInactive();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.exceptionCaught(this.inboundCtx, throwable);
        } else {
            this.inboundCtx.fireExceptionCaught(throwable);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.userEventTriggered(this.inboundCtx, object);
        } else {
            this.inboundCtx.fireUserEventTriggered(object);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelRead(this.inboundCtx, object);
        } else {
            this.inboundCtx.fireChannelRead(object);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelReadComplete(this.inboundCtx);
        } else {
            this.inboundCtx.fireChannelReadComplete();
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.inboundCtx)) {
            throw new AssertionError();
        }
        if (!this.inboundCtx.removed) {
            this.inboundHandler.channelWritabilityChanged(this.inboundCtx);
        } else {
            this.inboundCtx.fireChannelWritabilityChanged();
        }
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.bind(this.outboundCtx, socketAddress, channelPromise);
        } else {
            this.outboundCtx.bind(socketAddress, channelPromise);
        }
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.connect(this.outboundCtx, socketAddress, socketAddress2, channelPromise);
        } else {
            this.outboundCtx.connect(socketAddress2, channelPromise);
        }
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.disconnect(this.outboundCtx, channelPromise);
        } else {
            this.outboundCtx.disconnect(channelPromise);
        }
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.close(this.outboundCtx, channelPromise);
        } else {
            this.outboundCtx.close(channelPromise);
        }
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.deregister(this.outboundCtx, channelPromise);
        } else {
            this.outboundCtx.deregister(channelPromise);
        }
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.read(this.outboundCtx);
        } else {
            this.outboundCtx.read();
        }
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.write(this.outboundCtx, object, channelPromise);
        } else {
            this.outboundCtx.write(object, channelPromise);
        }
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!$assertionsDisabled && channelHandlerContext != DelegatingChannelHandlerContext.access$300(this.outboundCtx)) {
            throw new AssertionError();
        }
        if (!this.outboundCtx.removed) {
            this.outboundHandler.flush(this.outboundCtx);
        } else {
            this.outboundCtx.flush();
        }
    }

    static DelegatingChannelHandlerContext access$000(CombinedChannelDuplexHandler combinedChannelDuplexHandler) {
        return combinedChannelDuplexHandler.outboundCtx;
    }

    static ChannelOutboundHandler access$100(CombinedChannelDuplexHandler combinedChannelDuplexHandler) {
        return combinedChannelDuplexHandler.outboundHandler;
    }

    static InternalLogger access$200() {
        return logger;
    }

    static {
        $assertionsDisabled = !CombinedChannelDuplexHandler.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(CombinedChannelDuplexHandler.class);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class DelegatingChannelHandlerContext
    implements ChannelHandlerContext {
        private final ChannelHandlerContext ctx;
        private final ChannelHandler handler;
        boolean removed;

        DelegatingChannelHandlerContext(ChannelHandlerContext channelHandlerContext, ChannelHandler channelHandler) {
            this.ctx = channelHandlerContext;
            this.handler = channelHandler;
        }

        @Override
        public Channel channel() {
            return this.ctx.channel();
        }

        @Override
        public EventExecutor executor() {
            return this.ctx.executor();
        }

        @Override
        public String name() {
            return this.ctx.name();
        }

        @Override
        public ChannelHandler handler() {
            return this.ctx.handler();
        }

        @Override
        public boolean isRemoved() {
            return this.removed || this.ctx.isRemoved();
        }

        @Override
        public ChannelHandlerContext fireChannelRegistered() {
            this.ctx.fireChannelRegistered();
            return this;
        }

        @Override
        public ChannelHandlerContext fireChannelUnregistered() {
            this.ctx.fireChannelUnregistered();
            return this;
        }

        @Override
        public ChannelHandlerContext fireChannelActive() {
            this.ctx.fireChannelActive();
            return this;
        }

        @Override
        public ChannelHandlerContext fireChannelInactive() {
            this.ctx.fireChannelInactive();
            return this;
        }

        @Override
        public ChannelHandlerContext fireExceptionCaught(Throwable throwable) {
            this.ctx.fireExceptionCaught(throwable);
            return this;
        }

        @Override
        public ChannelHandlerContext fireUserEventTriggered(Object object) {
            this.ctx.fireUserEventTriggered(object);
            return this;
        }

        @Override
        public ChannelHandlerContext fireChannelRead(Object object) {
            this.ctx.fireChannelRead(object);
            return this;
        }

        @Override
        public ChannelHandlerContext fireChannelReadComplete() {
            this.ctx.fireChannelReadComplete();
            return this;
        }

        @Override
        public ChannelHandlerContext fireChannelWritabilityChanged() {
            this.ctx.fireChannelWritabilityChanged();
            return this;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress) {
            return this.ctx.bind(socketAddress);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress) {
            return this.ctx.connect(socketAddress);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
            return this.ctx.connect(socketAddress, socketAddress2);
        }

        @Override
        public ChannelFuture disconnect() {
            return this.ctx.disconnect();
        }

        @Override
        public ChannelFuture close() {
            return this.ctx.close();
        }

        @Override
        public ChannelFuture deregister() {
            return this.ctx.deregister();
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return this.ctx.bind(socketAddress, channelPromise);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return this.ctx.connect(socketAddress, channelPromise);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            return this.ctx.connect(socketAddress, socketAddress2, channelPromise);
        }

        @Override
        public ChannelFuture disconnect(ChannelPromise channelPromise) {
            return this.ctx.disconnect(channelPromise);
        }

        @Override
        public ChannelFuture close(ChannelPromise channelPromise) {
            return this.ctx.close(channelPromise);
        }

        @Override
        public ChannelFuture deregister(ChannelPromise channelPromise) {
            return this.ctx.deregister(channelPromise);
        }

        @Override
        public ChannelHandlerContext read() {
            this.ctx.read();
            return this;
        }

        @Override
        public ChannelFuture write(Object object) {
            return this.ctx.write(object);
        }

        @Override
        public ChannelFuture write(Object object, ChannelPromise channelPromise) {
            return this.ctx.write(object, channelPromise);
        }

        @Override
        public ChannelHandlerContext flush() {
            this.ctx.flush();
            return this;
        }

        @Override
        public ChannelFuture writeAndFlush(Object object, ChannelPromise channelPromise) {
            return this.ctx.writeAndFlush(object, channelPromise);
        }

        @Override
        public ChannelFuture writeAndFlush(Object object) {
            return this.ctx.writeAndFlush(object);
        }

        @Override
        public ChannelPipeline pipeline() {
            return this.ctx.pipeline();
        }

        @Override
        public ByteBufAllocator alloc() {
            return this.ctx.alloc();
        }

        @Override
        public ChannelPromise newPromise() {
            return this.ctx.newPromise();
        }

        @Override
        public ChannelProgressivePromise newProgressivePromise() {
            return this.ctx.newProgressivePromise();
        }

        @Override
        public ChannelFuture newSucceededFuture() {
            return this.ctx.newSucceededFuture();
        }

        @Override
        public ChannelFuture newFailedFuture(Throwable throwable) {
            return this.ctx.newFailedFuture(throwable);
        }

        @Override
        public ChannelPromise voidPromise() {
            return this.ctx.voidPromise();
        }

        @Override
        public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
            return this.ctx.channel().attr(attributeKey);
        }

        @Override
        public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
            return this.ctx.channel().hasAttr(attributeKey);
        }

        final void remove() {
            EventExecutor eventExecutor = this.executor();
            if (eventExecutor.inEventLoop()) {
                this.remove0();
            } else {
                eventExecutor.execute(new Runnable(this){
                    final DelegatingChannelHandlerContext this$0;
                    {
                        this.this$0 = delegatingChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        DelegatingChannelHandlerContext.access$400(this.this$0);
                    }
                });
            }
        }

        private void remove0() {
            if (!this.removed) {
                this.removed = true;
                try {
                    this.handler.handlerRemoved(this);
                } catch (Throwable throwable) {
                    this.fireExceptionCaught(new ChannelPipelineException(this.handler.getClass().getName() + ".handlerRemoved() has thrown an exception.", throwable));
                }
            }
        }

        @Override
        public ChannelInboundInvoker fireChannelWritabilityChanged() {
            return this.fireChannelWritabilityChanged();
        }

        @Override
        public ChannelInboundInvoker fireChannelReadComplete() {
            return this.fireChannelReadComplete();
        }

        @Override
        public ChannelInboundInvoker fireChannelRead(Object object) {
            return this.fireChannelRead(object);
        }

        @Override
        public ChannelInboundInvoker fireUserEventTriggered(Object object) {
            return this.fireUserEventTriggered(object);
        }

        @Override
        public ChannelInboundInvoker fireExceptionCaught(Throwable throwable) {
            return this.fireExceptionCaught(throwable);
        }

        @Override
        public ChannelInboundInvoker fireChannelInactive() {
            return this.fireChannelInactive();
        }

        @Override
        public ChannelInboundInvoker fireChannelActive() {
            return this.fireChannelActive();
        }

        @Override
        public ChannelInboundInvoker fireChannelUnregistered() {
            return this.fireChannelUnregistered();
        }

        @Override
        public ChannelInboundInvoker fireChannelRegistered() {
            return this.fireChannelRegistered();
        }

        @Override
        public ChannelOutboundInvoker flush() {
            return this.flush();
        }

        @Override
        public ChannelOutboundInvoker read() {
            return this.read();
        }

        static ChannelHandlerContext access$300(DelegatingChannelHandlerContext delegatingChannelHandlerContext) {
            return delegatingChannelHandlerContext.ctx;
        }

        static void access$400(DelegatingChannelHandlerContext delegatingChannelHandlerContext) {
            delegatingChannelHandlerContext.remove0();
        }
    }
}

