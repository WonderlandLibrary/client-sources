/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundInvoker;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.DefaultChannelProgressivePromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.FailedChannelFuture;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.SucceededChannelFuture;
import io.netty.channel.VoidChannelPromise;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.Recycler;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakHint;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.OrderedEventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PromiseNotificationUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AbstractChannelHandlerContext
extends DefaultAttributeMap
implements ChannelHandlerContext,
ResourceLeakHint {
    private static final InternalLogger logger;
    volatile AbstractChannelHandlerContext next;
    volatile AbstractChannelHandlerContext prev;
    private static final AtomicIntegerFieldUpdater<AbstractChannelHandlerContext> HANDLER_STATE_UPDATER;
    private static final int ADD_PENDING = 1;
    private static final int ADD_COMPLETE = 2;
    private static final int REMOVE_COMPLETE = 3;
    private static final int INIT = 0;
    private final boolean inbound;
    private final boolean outbound;
    private final DefaultChannelPipeline pipeline;
    private final String name;
    private final boolean ordered;
    final EventExecutor executor;
    private ChannelFuture succeededFuture;
    private Runnable invokeChannelReadCompleteTask;
    private Runnable invokeReadTask;
    private Runnable invokeChannelWritableStateChangedTask;
    private Runnable invokeFlushTask;
    private volatile int handlerState = 0;
    static final boolean $assertionsDisabled;

    AbstractChannelHandlerContext(DefaultChannelPipeline defaultChannelPipeline, EventExecutor eventExecutor, String string, boolean bl, boolean bl2) {
        this.name = ObjectUtil.checkNotNull(string, "name");
        this.pipeline = defaultChannelPipeline;
        this.executor = eventExecutor;
        this.inbound = bl;
        this.outbound = bl2;
        this.ordered = eventExecutor == null || eventExecutor instanceof OrderedEventExecutor;
    }

    @Override
    public Channel channel() {
        return this.pipeline.channel();
    }

    @Override
    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.channel().config().getAllocator();
    }

    @Override
    public EventExecutor executor() {
        if (this.executor == null) {
            return this.channel().eventLoop();
        }
        return this.executor;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public ChannelHandlerContext fireChannelRegistered() {
        AbstractChannelHandlerContext.invokeChannelRegistered(this.findContextInbound());
        return this;
    }

    static void invokeChannelRegistered(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelRegistered();
        } else {
            eventExecutor.execute(new Runnable(abstractChannelHandlerContext){
                final AbstractChannelHandlerContext val$next;
                {
                    this.val$next = abstractChannelHandlerContext;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$000(this.val$next);
                }
            });
        }
    }

    private void invokeChannelRegistered() {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelRegistered(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelRegistered();
        }
    }

    @Override
    public ChannelHandlerContext fireChannelUnregistered() {
        AbstractChannelHandlerContext.invokeChannelUnregistered(this.findContextInbound());
        return this;
    }

    static void invokeChannelUnregistered(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelUnregistered();
        } else {
            eventExecutor.execute(new Runnable(abstractChannelHandlerContext){
                final AbstractChannelHandlerContext val$next;
                {
                    this.val$next = abstractChannelHandlerContext;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$100(this.val$next);
                }
            });
        }
    }

    private void invokeChannelUnregistered() {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelUnregistered(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelUnregistered();
        }
    }

    @Override
    public ChannelHandlerContext fireChannelActive() {
        AbstractChannelHandlerContext.invokeChannelActive(this.findContextInbound());
        return this;
    }

    static void invokeChannelActive(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelActive();
        } else {
            eventExecutor.execute(new Runnable(abstractChannelHandlerContext){
                final AbstractChannelHandlerContext val$next;
                {
                    this.val$next = abstractChannelHandlerContext;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$200(this.val$next);
                }
            });
        }
    }

    private void invokeChannelActive() {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelActive(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelActive();
        }
    }

    @Override
    public ChannelHandlerContext fireChannelInactive() {
        AbstractChannelHandlerContext.invokeChannelInactive(this.findContextInbound());
        return this;
    }

    static void invokeChannelInactive(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelInactive();
        } else {
            eventExecutor.execute(new Runnable(abstractChannelHandlerContext){
                final AbstractChannelHandlerContext val$next;
                {
                    this.val$next = abstractChannelHandlerContext;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$300(this.val$next);
                }
            });
        }
    }

    private void invokeChannelInactive() {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelInactive(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelInactive();
        }
    }

    @Override
    public ChannelHandlerContext fireExceptionCaught(Throwable throwable) {
        AbstractChannelHandlerContext.invokeExceptionCaught(this.next, throwable);
        return this;
    }

    static void invokeExceptionCaught(AbstractChannelHandlerContext abstractChannelHandlerContext, Throwable throwable) {
        block4: {
            ObjectUtil.checkNotNull(throwable, "cause");
            EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
            if (eventExecutor.inEventLoop()) {
                abstractChannelHandlerContext.invokeExceptionCaught(throwable);
            } else {
                try {
                    eventExecutor.execute(new Runnable(abstractChannelHandlerContext, throwable){
                        final AbstractChannelHandlerContext val$next;
                        final Throwable val$cause;
                        {
                            this.val$next = abstractChannelHandlerContext;
                            this.val$cause = throwable;
                        }

                        @Override
                        public void run() {
                            AbstractChannelHandlerContext.access$400(this.val$next, this.val$cause);
                        }
                    });
                } catch (Throwable throwable2) {
                    if (!logger.isWarnEnabled()) break block4;
                    logger.warn("Failed to submit an exceptionCaught() event.", throwable2);
                    logger.warn("The exceptionCaught() event that was failed to submit was:", throwable);
                }
            }
        }
    }

    private void invokeExceptionCaught(Throwable throwable) {
        if (this.invokeHandler()) {
            try {
                this.handler().exceptionCaught(this, throwable);
            } catch (Throwable throwable2) {
                if (logger.isDebugEnabled()) {
                    logger.debug("An exception {}was thrown by a user handler's exceptionCaught() method while handling the following exception:", (Object)ThrowableUtil.stackTraceToString(throwable2), (Object)throwable);
                } else if (logger.isWarnEnabled()) {
                    logger.warn("An exception '{}' [enable DEBUG level for full stacktrace] was thrown by a user handler's exceptionCaught() method while handling the following exception:", (Object)throwable2, (Object)throwable);
                }
            }
        } else {
            this.fireExceptionCaught(throwable);
        }
    }

    @Override
    public ChannelHandlerContext fireUserEventTriggered(Object object) {
        AbstractChannelHandlerContext.invokeUserEventTriggered(this.findContextInbound(), object);
        return this;
    }

    static void invokeUserEventTriggered(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object) {
        ObjectUtil.checkNotNull(object, "event");
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeUserEventTriggered(object);
        } else {
            eventExecutor.execute(new Runnable(abstractChannelHandlerContext, object){
                final AbstractChannelHandlerContext val$next;
                final Object val$event;
                {
                    this.val$next = abstractChannelHandlerContext;
                    this.val$event = object;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$500(this.val$next, this.val$event);
                }
            });
        }
    }

    private void invokeUserEventTriggered(Object object) {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).userEventTriggered(this, object);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireUserEventTriggered(object);
        }
    }

    @Override
    public ChannelHandlerContext fireChannelRead(Object object) {
        AbstractChannelHandlerContext.invokeChannelRead(this.findContextInbound(), object);
        return this;
    }

    static void invokeChannelRead(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object) {
        Object object2 = abstractChannelHandlerContext.pipeline.touch(ObjectUtil.checkNotNull(object, "msg"), abstractChannelHandlerContext);
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelRead(object2);
        } else {
            eventExecutor.execute(new Runnable(abstractChannelHandlerContext, object2){
                final AbstractChannelHandlerContext val$next;
                final Object val$m;
                {
                    this.val$next = abstractChannelHandlerContext;
                    this.val$m = object;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$600(this.val$next, this.val$m);
                }
            });
        }
    }

    private void invokeChannelRead(Object object) {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelRead(this, object);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelRead(object);
        }
    }

    @Override
    public ChannelHandlerContext fireChannelReadComplete() {
        AbstractChannelHandlerContext.invokeChannelReadComplete(this.findContextInbound());
        return this;
    }

    static void invokeChannelReadComplete(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelReadComplete();
        } else {
            Runnable runnable = abstractChannelHandlerContext.invokeChannelReadCompleteTask;
            if (runnable == null) {
                abstractChannelHandlerContext.invokeChannelReadCompleteTask = runnable = new Runnable(abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$next;
                    {
                        this.val$next = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$700(this.val$next);
                    }
                };
            }
            eventExecutor.execute(runnable);
        }
    }

    private void invokeChannelReadComplete() {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelReadComplete(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelReadComplete();
        }
    }

    @Override
    public ChannelHandlerContext fireChannelWritabilityChanged() {
        AbstractChannelHandlerContext.invokeChannelWritabilityChanged(this.findContextInbound());
        return this;
    }

    static void invokeChannelWritabilityChanged(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeChannelWritabilityChanged();
        } else {
            Runnable runnable = abstractChannelHandlerContext.invokeChannelWritableStateChangedTask;
            if (runnable == null) {
                abstractChannelHandlerContext.invokeChannelWritableStateChangedTask = runnable = new Runnable(abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$next;
                    {
                        this.val$next = abstractChannelHandlerContext;
                    }

                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$800(this.val$next);
                    }
                };
            }
            eventExecutor.execute(runnable);
        }
    }

    private void invokeChannelWritabilityChanged() {
        if (this.invokeHandler()) {
            try {
                ((ChannelInboundHandler)this.handler()).channelWritabilityChanged(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.fireChannelWritabilityChanged();
        }
    }

    @Override
    public ChannelFuture bind(SocketAddress socketAddress) {
        return this.bind(socketAddress, this.newPromise());
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress) {
        return this.connect(socketAddress, this.newPromise());
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.connect(socketAddress, socketAddress2, this.newPromise());
    }

    @Override
    public ChannelFuture disconnect() {
        return this.disconnect(this.newPromise());
    }

    @Override
    public ChannelFuture close() {
        return this.close(this.newPromise());
    }

    @Override
    public ChannelFuture deregister() {
        return this.deregister(this.newPromise());
    }

    @Override
    public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("localAddress");
        }
        if (this.isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeBind(socketAddress, channelPromise);
        } else {
            AbstractChannelHandlerContext.safeExecute(eventExecutor, new Runnable(this, abstractChannelHandlerContext, socketAddress, channelPromise){
                final AbstractChannelHandlerContext val$next;
                final SocketAddress val$localAddress;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                {
                    this.this$0 = abstractChannelHandlerContext;
                    this.val$next = abstractChannelHandlerContext2;
                    this.val$localAddress = socketAddress;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$900(this.val$next, this.val$localAddress, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    private void invokeBind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            try {
                ((ChannelOutboundHandler)this.handler()).bind(this, socketAddress, channelPromise);
            } catch (Throwable throwable) {
                AbstractChannelHandlerContext.notifyOutboundHandlerException(throwable, channelPromise);
            }
        } else {
            this.bind(socketAddress, channelPromise);
        }
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.connect(socketAddress, null, channelPromise);
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        if (socketAddress == null) {
            throw new NullPointerException("remoteAddress");
        }
        if (this.isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeConnect(socketAddress, socketAddress2, channelPromise);
        } else {
            AbstractChannelHandlerContext.safeExecute(eventExecutor, new Runnable(this, abstractChannelHandlerContext, socketAddress, socketAddress2, channelPromise){
                final AbstractChannelHandlerContext val$next;
                final SocketAddress val$remoteAddress;
                final SocketAddress val$localAddress;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                {
                    this.this$0 = abstractChannelHandlerContext;
                    this.val$next = abstractChannelHandlerContext2;
                    this.val$remoteAddress = socketAddress;
                    this.val$localAddress = socketAddress2;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1000(this.val$next, this.val$remoteAddress, this.val$localAddress, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    private void invokeConnect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            try {
                ((ChannelOutboundHandler)this.handler()).connect(this, socketAddress, socketAddress2, channelPromise);
            } catch (Throwable throwable) {
                AbstractChannelHandlerContext.notifyOutboundHandlerException(throwable, channelPromise);
            }
        } else {
            this.connect(socketAddress, socketAddress2, channelPromise);
        }
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise channelPromise) {
        if (this.isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            if (!this.channel().metadata().hasDisconnect()) {
                abstractChannelHandlerContext.invokeClose(channelPromise);
            } else {
                abstractChannelHandlerContext.invokeDisconnect(channelPromise);
            }
        } else {
            AbstractChannelHandlerContext.safeExecute(eventExecutor, new Runnable(this, abstractChannelHandlerContext, channelPromise){
                final AbstractChannelHandlerContext val$next;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                {
                    this.this$0 = abstractChannelHandlerContext;
                    this.val$next = abstractChannelHandlerContext2;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    if (!this.this$0.channel().metadata().hasDisconnect()) {
                        AbstractChannelHandlerContext.access$1100(this.val$next, this.val$promise);
                    } else {
                        AbstractChannelHandlerContext.access$1200(this.val$next, this.val$promise);
                    }
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    private void invokeDisconnect(ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            try {
                ((ChannelOutboundHandler)this.handler()).disconnect(this, channelPromise);
            } catch (Throwable throwable) {
                AbstractChannelHandlerContext.notifyOutboundHandlerException(throwable, channelPromise);
            }
        } else {
            this.disconnect(channelPromise);
        }
    }

    @Override
    public ChannelFuture close(ChannelPromise channelPromise) {
        if (this.isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeClose(channelPromise);
        } else {
            AbstractChannelHandlerContext.safeExecute(eventExecutor, new Runnable(this, abstractChannelHandlerContext, channelPromise){
                final AbstractChannelHandlerContext val$next;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                {
                    this.this$0 = abstractChannelHandlerContext;
                    this.val$next = abstractChannelHandlerContext2;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1100(this.val$next, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    private void invokeClose(ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            try {
                ((ChannelOutboundHandler)this.handler()).close(this, channelPromise);
            } catch (Throwable throwable) {
                AbstractChannelHandlerContext.notifyOutboundHandlerException(throwable, channelPromise);
            }
        } else {
            this.close(channelPromise);
        }
    }

    @Override
    public ChannelFuture deregister(ChannelPromise channelPromise) {
        if (this.isNotValidPromise(channelPromise, false)) {
            return channelPromise;
        }
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeDeregister(channelPromise);
        } else {
            AbstractChannelHandlerContext.safeExecute(eventExecutor, new Runnable(this, abstractChannelHandlerContext, channelPromise){
                final AbstractChannelHandlerContext val$next;
                final ChannelPromise val$promise;
                final AbstractChannelHandlerContext this$0;
                {
                    this.this$0 = abstractChannelHandlerContext;
                    this.val$next = abstractChannelHandlerContext2;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    AbstractChannelHandlerContext.access$1300(this.val$next, this.val$promise);
                }
            }, channelPromise, null);
        }
        return channelPromise;
    }

    private void invokeDeregister(ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            try {
                ((ChannelOutboundHandler)this.handler()).deregister(this, channelPromise);
            } catch (Throwable throwable) {
                AbstractChannelHandlerContext.notifyOutboundHandlerException(throwable, channelPromise);
            }
        } else {
            this.deregister(channelPromise);
        }
    }

    @Override
    public ChannelHandlerContext read() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeRead();
        } else {
            Runnable runnable = abstractChannelHandlerContext.invokeReadTask;
            if (runnable == null) {
                abstractChannelHandlerContext.invokeReadTask = runnable = new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$next;
                    final AbstractChannelHandlerContext this$0;
                    {
                        this.this$0 = abstractChannelHandlerContext;
                        this.val$next = abstractChannelHandlerContext2;
                    }

                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$1400(this.val$next);
                    }
                };
            }
            eventExecutor.execute(runnable);
        }
        return this;
    }

    private void invokeRead() {
        if (this.invokeHandler()) {
            try {
                ((ChannelOutboundHandler)this.handler()).read(this);
            } catch (Throwable throwable) {
                this.notifyHandlerException(throwable);
            }
        } else {
            this.read();
        }
    }

    @Override
    public ChannelFuture write(Object object) {
        return this.write(object, this.newPromise());
    }

    @Override
    public ChannelFuture write(Object object, ChannelPromise channelPromise) {
        if (object == null) {
            throw new NullPointerException("msg");
        }
        try {
            if (this.isNotValidPromise(channelPromise, true)) {
                ReferenceCountUtil.release(object);
                return channelPromise;
            }
        } catch (RuntimeException runtimeException) {
            ReferenceCountUtil.release(object);
            throw runtimeException;
        }
        this.write(object, false, channelPromise);
        return channelPromise;
    }

    private void invokeWrite(Object object, ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            this.invokeWrite0(object, channelPromise);
        } else {
            this.write(object, channelPromise);
        }
    }

    private void invokeWrite0(Object object, ChannelPromise channelPromise) {
        try {
            ((ChannelOutboundHandler)this.handler()).write(this, object, channelPromise);
        } catch (Throwable throwable) {
            AbstractChannelHandlerContext.notifyOutboundHandlerException(throwable, channelPromise);
        }
    }

    @Override
    public ChannelHandlerContext flush() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            abstractChannelHandlerContext.invokeFlush();
        } else {
            Runnable runnable = abstractChannelHandlerContext.invokeFlushTask;
            if (runnable == null) {
                abstractChannelHandlerContext.invokeFlushTask = runnable = new Runnable(this, abstractChannelHandlerContext){
                    final AbstractChannelHandlerContext val$next;
                    final AbstractChannelHandlerContext this$0;
                    {
                        this.this$0 = abstractChannelHandlerContext;
                        this.val$next = abstractChannelHandlerContext2;
                    }

                    @Override
                    public void run() {
                        AbstractChannelHandlerContext.access$1500(this.val$next);
                    }
                };
            }
            AbstractChannelHandlerContext.safeExecute(eventExecutor, runnable, this.channel().voidPromise(), null);
        }
        return this;
    }

    private void invokeFlush() {
        if (this.invokeHandler()) {
            this.invokeFlush0();
        } else {
            this.flush();
        }
    }

    private void invokeFlush0() {
        try {
            ((ChannelOutboundHandler)this.handler()).flush(this);
        } catch (Throwable throwable) {
            this.notifyHandlerException(throwable);
        }
    }

    @Override
    public ChannelFuture writeAndFlush(Object object, ChannelPromise channelPromise) {
        if (object == null) {
            throw new NullPointerException("msg");
        }
        if (this.isNotValidPromise(channelPromise, true)) {
            ReferenceCountUtil.release(object);
            return channelPromise;
        }
        this.write(object, true, channelPromise);
        return channelPromise;
    }

    private void invokeWriteAndFlush(Object object, ChannelPromise channelPromise) {
        if (this.invokeHandler()) {
            this.invokeWrite0(object, channelPromise);
            this.invokeFlush0();
        } else {
            this.writeAndFlush(object, channelPromise);
        }
    }

    private void write(Object object, boolean bl, ChannelPromise channelPromise) {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this.findContextOutbound();
        Object object2 = this.pipeline.touch(object, abstractChannelHandlerContext);
        EventExecutor eventExecutor = abstractChannelHandlerContext.executor();
        if (eventExecutor.inEventLoop()) {
            if (bl) {
                abstractChannelHandlerContext.invokeWriteAndFlush(object2, channelPromise);
            } else {
                abstractChannelHandlerContext.invokeWrite(object2, channelPromise);
            }
        } else {
            AbstractWriteTask abstractWriteTask = bl ? WriteAndFlushTask.access$1600(abstractChannelHandlerContext, object2, channelPromise) : WriteTask.access$1700(abstractChannelHandlerContext, object2, channelPromise);
            AbstractChannelHandlerContext.safeExecute(eventExecutor, abstractWriteTask, channelPromise, object2);
        }
    }

    @Override
    public ChannelFuture writeAndFlush(Object object) {
        return this.writeAndFlush(object, this.newPromise());
    }

    private static void notifyOutboundHandlerException(Throwable throwable, ChannelPromise channelPromise) {
        PromiseNotificationUtil.tryFailure(channelPromise, throwable, channelPromise instanceof VoidChannelPromise ? null : logger);
    }

    private void notifyHandlerException(Throwable throwable) {
        if (AbstractChannelHandlerContext.inExceptionCaught(throwable)) {
            if (logger.isWarnEnabled()) {
                logger.warn("An exception was thrown by a user handler while handling an exceptionCaught event", throwable);
            }
            return;
        }
        this.invokeExceptionCaught(throwable);
    }

    private static boolean inExceptionCaught(Throwable throwable) {
        do {
            StackTraceElement[] stackTraceElementArray;
            if ((stackTraceElementArray = throwable.getStackTrace()) == null) continue;
            for (StackTraceElement stackTraceElement : stackTraceElementArray) {
                if (stackTraceElement == null) break;
                if (!"exceptionCaught".equals(stackTraceElement.getMethodName())) continue;
                return false;
            }
        } while ((throwable = throwable.getCause()) != null);
        return true;
    }

    @Override
    public ChannelPromise newPromise() {
        return new DefaultChannelPromise(this.channel(), this.executor());
    }

    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return new DefaultChannelProgressivePromise(this.channel(), this.executor());
    }

    @Override
    public ChannelFuture newSucceededFuture() {
        ChannelFuture channelFuture = this.succeededFuture;
        if (channelFuture == null) {
            this.succeededFuture = channelFuture = new SucceededChannelFuture(this.channel(), this.executor());
        }
        return channelFuture;
    }

    @Override
    public ChannelFuture newFailedFuture(Throwable throwable) {
        return new FailedChannelFuture(this.channel(), this.executor(), throwable);
    }

    private boolean isNotValidPromise(ChannelPromise channelPromise, boolean bl) {
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        }
        if (channelPromise.isDone()) {
            if (channelPromise.isCancelled()) {
                return false;
            }
            throw new IllegalArgumentException("promise already done: " + channelPromise);
        }
        if (channelPromise.channel() != this.channel()) {
            throw new IllegalArgumentException(String.format("promise.channel does not match: %s (expected: %s)", channelPromise.channel(), this.channel()));
        }
        if (channelPromise.getClass() == DefaultChannelPromise.class) {
            return true;
        }
        if (!bl && channelPromise instanceof VoidChannelPromise) {
            throw new IllegalArgumentException(StringUtil.simpleClassName(VoidChannelPromise.class) + " not allowed for this operation");
        }
        if (channelPromise instanceof AbstractChannel.CloseFuture) {
            throw new IllegalArgumentException(StringUtil.simpleClassName(AbstractChannel.CloseFuture.class) + " not allowed in a pipeline");
        }
        return true;
    }

    private AbstractChannelHandlerContext findContextInbound() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this;
        do {
            abstractChannelHandlerContext = abstractChannelHandlerContext.next;
        } while (!abstractChannelHandlerContext.inbound);
        return abstractChannelHandlerContext;
    }

    private AbstractChannelHandlerContext findContextOutbound() {
        AbstractChannelHandlerContext abstractChannelHandlerContext = this;
        do {
            abstractChannelHandlerContext = abstractChannelHandlerContext.prev;
        } while (!abstractChannelHandlerContext.outbound);
        return abstractChannelHandlerContext;
    }

    @Override
    public ChannelPromise voidPromise() {
        return this.channel().voidPromise();
    }

    final void setRemoved() {
        this.handlerState = 3;
    }

    final void setAddComplete() {
        int n;
        while ((n = this.handlerState) != 3 && !HANDLER_STATE_UPDATER.compareAndSet(this, n, 1)) {
        }
    }

    final void setAddPending() {
        boolean bl = HANDLER_STATE_UPDATER.compareAndSet(this, 0, 0);
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
    }

    private boolean invokeHandler() {
        int n = this.handlerState;
        return n == 2 || !this.ordered && n == 1;
    }

    @Override
    public boolean isRemoved() {
        return this.handlerState == 3;
    }

    @Override
    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        return this.channel().attr(attributeKey);
    }

    @Override
    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        return this.channel().hasAttr(attributeKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void safeExecute(EventExecutor eventExecutor, Runnable runnable, ChannelPromise channelPromise, Object object) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable throwable) {
            try {
                channelPromise.setFailure(throwable);
            } finally {
                if (object != null) {
                    ReferenceCountUtil.release(object);
                }
            }
        }
    }

    @Override
    public String toHintString() {
        return '\'' + this.name + "' will handle the message from this point.";
    }

    public String toString() {
        return StringUtil.simpleClassName(ChannelHandlerContext.class) + '(' + this.name + ", " + this.channel() + ')';
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

    static void access$000(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelRegistered();
    }

    static void access$100(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelUnregistered();
    }

    static void access$200(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelActive();
    }

    static void access$300(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelInactive();
    }

    static void access$400(AbstractChannelHandlerContext abstractChannelHandlerContext, Throwable throwable) {
        abstractChannelHandlerContext.invokeExceptionCaught(throwable);
    }

    static void access$500(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object) {
        abstractChannelHandlerContext.invokeUserEventTriggered(object);
    }

    static void access$600(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object) {
        abstractChannelHandlerContext.invokeChannelRead(object);
    }

    static void access$700(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelReadComplete();
    }

    static void access$800(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeChannelWritabilityChanged();
    }

    static void access$900(AbstractChannelHandlerContext abstractChannelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeBind(socketAddress, channelPromise);
    }

    static void access$1000(AbstractChannelHandlerContext abstractChannelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeConnect(socketAddress, socketAddress2, channelPromise);
    }

    static void access$1100(AbstractChannelHandlerContext abstractChannelHandlerContext, ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeClose(channelPromise);
    }

    static void access$1200(AbstractChannelHandlerContext abstractChannelHandlerContext, ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeDisconnect(channelPromise);
    }

    static void access$1300(AbstractChannelHandlerContext abstractChannelHandlerContext, ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeDeregister(channelPromise);
    }

    static void access$1400(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeRead();
    }

    static void access$1500(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        abstractChannelHandlerContext.invokeFlush();
    }

    static DefaultChannelPipeline access$1800(AbstractChannelHandlerContext abstractChannelHandlerContext) {
        return abstractChannelHandlerContext.pipeline;
    }

    static void access$1900(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
        abstractChannelHandlerContext.invokeWrite(object, channelPromise);
    }

    static {
        $assertionsDisabled = !AbstractChannelHandlerContext.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(AbstractChannelHandlerContext.class);
        HANDLER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AbstractChannelHandlerContext.class, "handlerState");
    }

    static final class WriteAndFlushTask
    extends AbstractWriteTask {
        private static final Recycler<WriteAndFlushTask> RECYCLER = new Recycler<WriteAndFlushTask>(){

            @Override
            protected WriteAndFlushTask newObject(Recycler.Handle<WriteAndFlushTask> handle) {
                return new WriteAndFlushTask(handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };

        private static WriteAndFlushTask newInstance(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            WriteAndFlushTask writeAndFlushTask = RECYCLER.get();
            WriteAndFlushTask.init(writeAndFlushTask, abstractChannelHandlerContext, object, channelPromise);
            return writeAndFlushTask;
        }

        private WriteAndFlushTask(Recycler.Handle<WriteAndFlushTask> handle) {
            super(handle, null);
        }

        @Override
        public void write(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            super.write(abstractChannelHandlerContext, object, channelPromise);
            AbstractChannelHandlerContext.access$1500(abstractChannelHandlerContext);
        }

        static WriteAndFlushTask access$1600(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            return WriteAndFlushTask.newInstance(abstractChannelHandlerContext, object, channelPromise);
        }

        WriteAndFlushTask(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }

    static final class WriteTask
    extends AbstractWriteTask
    implements SingleThreadEventLoop.NonWakeupRunnable {
        private static final Recycler<WriteTask> RECYCLER = new Recycler<WriteTask>(){

            @Override
            protected WriteTask newObject(Recycler.Handle<WriteTask> handle) {
                return new WriteTask(handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };

        private static WriteTask newInstance(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            WriteTask writeTask = RECYCLER.get();
            WriteTask.init(writeTask, abstractChannelHandlerContext, object, channelPromise);
            return writeTask;
        }

        private WriteTask(Recycler.Handle<WriteTask> handle) {
            super(handle, null);
        }

        static WriteTask access$1700(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            return WriteTask.newInstance(abstractChannelHandlerContext, object, channelPromise);
        }

        WriteTask(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }

    static abstract class AbstractWriteTask
    implements Runnable {
        private static final boolean ESTIMATE_TASK_SIZE_ON_SUBMIT = SystemPropertyUtil.getBoolean("io.netty.transport.estimateSizeOnSubmit", true);
        private static final int WRITE_TASK_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.writeTaskSizeOverhead", 48);
        private final Recycler.Handle<AbstractWriteTask> handle;
        private AbstractChannelHandlerContext ctx;
        private Object msg;
        private ChannelPromise promise;
        private int size;

        private AbstractWriteTask(Recycler.Handle<? extends AbstractWriteTask> handle) {
            this.handle = handle;
        }

        protected static void init(AbstractWriteTask abstractWriteTask, AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            abstractWriteTask.ctx = abstractChannelHandlerContext;
            abstractWriteTask.msg = object;
            abstractWriteTask.promise = channelPromise;
            if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
                abstractWriteTask.size = AbstractChannelHandlerContext.access$1800(abstractChannelHandlerContext).estimatorHandle().size(object) + WRITE_TASK_OVERHEAD;
                AbstractChannelHandlerContext.access$1800(abstractChannelHandlerContext).incrementPendingOutboundBytes(abstractWriteTask.size);
            } else {
                abstractWriteTask.size = 0;
            }
        }

        @Override
        public final void run() {
            try {
                if (ESTIMATE_TASK_SIZE_ON_SUBMIT) {
                    AbstractChannelHandlerContext.access$1800(this.ctx).decrementPendingOutboundBytes(this.size);
                }
                this.write(this.ctx, this.msg, this.promise);
            } finally {
                this.ctx = null;
                this.msg = null;
                this.promise = null;
                this.handle.recycle(this);
            }
        }

        protected void write(AbstractChannelHandlerContext abstractChannelHandlerContext, Object object, ChannelPromise channelPromise) {
            AbstractChannelHandlerContext.access$1900(abstractChannelHandlerContext, object, channelPromise);
        }

        AbstractWriteTask(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }
}

