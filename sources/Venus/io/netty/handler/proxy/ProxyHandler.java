/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.PendingWriteQueue;
import io.netty.handler.proxy.ProxyConnectException;
import io.netty.handler.proxy.ProxyConnectionEvent;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ConnectionPendingException;
import java.util.concurrent.TimeUnit;

public abstract class ProxyHandler
extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ProxyHandler.class);
    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 10000L;
    static final String AUTH_NONE = "none";
    private final SocketAddress proxyAddress;
    private volatile SocketAddress destinationAddress;
    private volatile long connectTimeoutMillis = 10000L;
    private volatile ChannelHandlerContext ctx;
    private PendingWriteQueue pendingWrites;
    private boolean finished;
    private boolean suppressChannelReadComplete;
    private boolean flushedPrematurely;
    private final LazyChannelPromise connectPromise = new LazyChannelPromise(this, null);
    private ScheduledFuture<?> connectTimeoutFuture;
    private final ChannelFutureListener writeListener = new ChannelFutureListener(this){
        final ProxyHandler this$0;
        {
            this.this$0 = proxyHandler;
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                ProxyHandler.access$100(this.this$0, channelFuture.cause());
            }
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };

    protected ProxyHandler(SocketAddress socketAddress) {
        if (socketAddress == null) {
            throw new NullPointerException("proxyAddress");
        }
        this.proxyAddress = socketAddress;
    }

    public abstract String protocol();

    public abstract String authScheme();

    public final <T extends SocketAddress> T proxyAddress() {
        return (T)this.proxyAddress;
    }

    public final <T extends SocketAddress> T destinationAddress() {
        return (T)this.destinationAddress;
    }

    public final boolean isConnected() {
        return this.connectPromise.isSuccess();
    }

    public final Future<Channel> connectFuture() {
        return this.connectPromise;
    }

    public final long connectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    public final void setConnectTimeoutMillis(long l) {
        if (l <= 0L) {
            l = 0L;
        }
        this.connectTimeoutMillis = l;
    }

    @Override
    public final void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        this.addCodec(channelHandlerContext);
        if (channelHandlerContext.channel().isActive()) {
            this.sendInitialMessage(channelHandlerContext);
        }
    }

    protected abstract void addCodec(ChannelHandlerContext var1) throws Exception;

    protected abstract void removeEncoder(ChannelHandlerContext var1) throws Exception;

    protected abstract void removeDecoder(ChannelHandlerContext var1) throws Exception;

    @Override
    public final void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (this.destinationAddress != null) {
            channelPromise.setFailure(new ConnectionPendingException());
            return;
        }
        this.destinationAddress = socketAddress;
        channelHandlerContext.connect(this.proxyAddress, socketAddress2, channelPromise);
    }

    @Override
    public final void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.sendInitialMessage(channelHandlerContext);
        channelHandlerContext.fireChannelActive();
    }

    private void sendInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        Object object;
        long l = this.connectTimeoutMillis;
        if (l > 0L) {
            this.connectTimeoutFuture = channelHandlerContext.executor().schedule(new Runnable(this){
                final ProxyHandler this$0;
                {
                    this.this$0 = proxyHandler;
                }

                @Override
                public void run() {
                    if (!ProxyHandler.access$200(this.this$0).isDone()) {
                        ProxyHandler.access$100(this.this$0, new ProxyConnectException(this.this$0.exceptionMessage("timeout")));
                    }
                }
            }, l, TimeUnit.MILLISECONDS);
        }
        if ((object = this.newInitialMessage(channelHandlerContext)) != null) {
            this.sendToProxyServer(object);
        }
        ProxyHandler.readIfNeeded(channelHandlerContext);
    }

    protected abstract Object newInitialMessage(ChannelHandlerContext var1) throws Exception;

    protected final void sendToProxyServer(Object object) {
        this.ctx.writeAndFlush(object).addListener(this.writeListener);
    }

    @Override
    public final void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.finished) {
            channelHandlerContext.fireChannelInactive();
        } else {
            this.setConnectFailure(new ProxyConnectException(this.exceptionMessage("disconnected")));
        }
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (this.finished) {
            channelHandlerContext.fireExceptionCaught(throwable);
        } else {
            this.setConnectFailure(throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (this.finished) {
            this.suppressChannelReadComplete = false;
            channelHandlerContext.fireChannelRead(object);
        } else {
            this.suppressChannelReadComplete = true;
            Throwable throwable = null;
            try {
                boolean bl = this.handleResponse(channelHandlerContext, object);
                if (bl) {
                    this.setConnectSuccess();
                }
            } catch (Throwable throwable2) {
                throwable = throwable2;
            } finally {
                ReferenceCountUtil.release(object);
                if (throwable != null) {
                    this.setConnectFailure(throwable);
                }
            }
        }
    }

    protected abstract boolean handleResponse(ChannelHandlerContext var1, Object var2) throws Exception;

    private void setConnectSuccess() {
        this.finished = true;
        this.cancelConnectTimeoutFuture();
        if (!this.connectPromise.isDone()) {
            boolean bl = true;
            bl &= this.safeRemoveEncoder();
            this.ctx.fireUserEventTriggered(new ProxyConnectionEvent(this.protocol(), this.authScheme(), this.proxyAddress, this.destinationAddress));
            if (bl &= this.safeRemoveDecoder()) {
                this.writePendingWrites();
                if (this.flushedPrematurely) {
                    this.ctx.flush();
                }
                this.connectPromise.trySuccess(this.ctx.channel());
            } else {
                ProxyConnectException proxyConnectException = new ProxyConnectException("failed to remove all codec handlers added by the proxy handler; bug?");
                this.failPendingWritesAndClose(proxyConnectException);
            }
        }
    }

    private boolean safeRemoveDecoder() {
        try {
            this.removeDecoder(this.ctx);
            return true;
        } catch (Exception exception) {
            logger.warn("Failed to remove proxy decoders:", exception);
            return true;
        }
    }

    private boolean safeRemoveEncoder() {
        try {
            this.removeEncoder(this.ctx);
            return true;
        } catch (Exception exception) {
            logger.warn("Failed to remove proxy encoders:", exception);
            return true;
        }
    }

    private void setConnectFailure(Throwable throwable) {
        this.finished = true;
        this.cancelConnectTimeoutFuture();
        if (!this.connectPromise.isDone()) {
            if (!(throwable instanceof ProxyConnectException)) {
                throwable = new ProxyConnectException(this.exceptionMessage(throwable.toString()), throwable);
            }
            this.safeRemoveDecoder();
            this.safeRemoveEncoder();
            this.failPendingWritesAndClose(throwable);
        }
    }

    private void failPendingWritesAndClose(Throwable throwable) {
        this.failPendingWrites(throwable);
        this.connectPromise.tryFailure(throwable);
        this.ctx.fireExceptionCaught(throwable);
        this.ctx.close();
    }

    private void cancelConnectTimeoutFuture() {
        if (this.connectTimeoutFuture != null) {
            this.connectTimeoutFuture.cancel(false);
            this.connectTimeoutFuture = null;
        }
    }

    protected final String exceptionMessage(String string) {
        if (string == null) {
            string = "";
        }
        StringBuilder stringBuilder = new StringBuilder(128 + string.length()).append(this.protocol()).append(", ").append(this.authScheme()).append(", ").append(this.proxyAddress).append(" => ").append(this.destinationAddress);
        if (!string.isEmpty()) {
            stringBuilder.append(", ").append(string);
        }
        return stringBuilder.toString();
    }

    @Override
    public final void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.suppressChannelReadComplete) {
            this.suppressChannelReadComplete = false;
            ProxyHandler.readIfNeeded(channelHandlerContext);
        } else {
            channelHandlerContext.fireChannelReadComplete();
        }
    }

    @Override
    public final void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (this.finished) {
            this.writePendingWrites();
            channelHandlerContext.write(object, channelPromise);
        } else {
            this.addPendingWrite(channelHandlerContext, object, channelPromise);
        }
    }

    @Override
    public final void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.finished) {
            this.writePendingWrites();
            channelHandlerContext.flush();
        } else {
            this.flushedPrematurely = true;
        }
    }

    private static void readIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (!channelHandlerContext.channel().config().isAutoRead()) {
            channelHandlerContext.read();
        }
    }

    private void writePendingWrites() {
        if (this.pendingWrites != null) {
            this.pendingWrites.removeAndWriteAll();
            this.pendingWrites = null;
        }
    }

    private void failPendingWrites(Throwable throwable) {
        if (this.pendingWrites != null) {
            this.pendingWrites.removeAndFailAll(throwable);
            this.pendingWrites = null;
        }
    }

    private void addPendingWrite(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) {
        PendingWriteQueue pendingWriteQueue = this.pendingWrites;
        if (pendingWriteQueue == null) {
            this.pendingWrites = pendingWriteQueue = new PendingWriteQueue(channelHandlerContext);
        }
        pendingWriteQueue.add(object, channelPromise);
    }

    static void access$100(ProxyHandler proxyHandler, Throwable throwable) {
        proxyHandler.setConnectFailure(throwable);
    }

    static LazyChannelPromise access$200(ProxyHandler proxyHandler) {
        return proxyHandler.connectPromise;
    }

    static ChannelHandlerContext access$300(ProxyHandler proxyHandler) {
        return proxyHandler.ctx;
    }

    private final class LazyChannelPromise
    extends DefaultPromise<Channel> {
        final ProxyHandler this$0;

        private LazyChannelPromise(ProxyHandler proxyHandler) {
            this.this$0 = proxyHandler;
        }

        @Override
        protected EventExecutor executor() {
            if (ProxyHandler.access$300(this.this$0) == null) {
                throw new IllegalStateException();
            }
            return ProxyHandler.access$300(this.this$0).executor();
        }

        LazyChannelPromise(ProxyHandler proxyHandler, 1 var2_2) {
            this(proxyHandler);
        }
    }
}

