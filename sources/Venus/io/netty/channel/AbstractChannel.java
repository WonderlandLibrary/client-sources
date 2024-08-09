/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelId;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.VoidChannelPromise;
import io.netty.channel.socket.ChannelOutputShutdownEvent;
import io.netty.channel.socket.ChannelOutputShutdownException;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractChannel
extends DefaultAttributeMap
implements Channel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractChannel.class);
    private static final ClosedChannelException FLUSH0_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "flush0()");
    private static final ClosedChannelException ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "ensureOpen(...)");
    private static final ClosedChannelException CLOSE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "close(...)");
    private static final ClosedChannelException WRITE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractUnsafe.class, "write(...)");
    private static final NotYetConnectedException FLUSH0_NOT_YET_CONNECTED_EXCEPTION = ThrowableUtil.unknownStackTrace(new NotYetConnectedException(), AbstractUnsafe.class, "flush0()");
    private final Channel parent;
    private final ChannelId id;
    private final Channel.Unsafe unsafe;
    private final DefaultChannelPipeline pipeline;
    private final VoidChannelPromise unsafeVoidPromise = new VoidChannelPromise(this, false);
    private final CloseFuture closeFuture = new CloseFuture(this);
    private volatile SocketAddress localAddress;
    private volatile SocketAddress remoteAddress;
    private volatile EventLoop eventLoop;
    private volatile boolean registered;
    private boolean closeInitiated;
    private boolean strValActive;
    private String strVal;

    protected AbstractChannel(Channel channel) {
        this.parent = channel;
        this.id = this.newId();
        this.unsafe = this.newUnsafe();
        this.pipeline = this.newChannelPipeline();
    }

    protected AbstractChannel(Channel channel, ChannelId channelId) {
        this.parent = channel;
        this.id = channelId;
        this.unsafe = this.newUnsafe();
        this.pipeline = this.newChannelPipeline();
    }

    @Override
    public final ChannelId id() {
        return this.id;
    }

    protected ChannelId newId() {
        return DefaultChannelId.newInstance();
    }

    protected DefaultChannelPipeline newChannelPipeline() {
        return new DefaultChannelPipeline(this);
    }

    @Override
    public boolean isWritable() {
        ChannelOutboundBuffer channelOutboundBuffer = this.unsafe.outboundBuffer();
        return channelOutboundBuffer != null && channelOutboundBuffer.isWritable();
    }

    @Override
    public long bytesBeforeUnwritable() {
        ChannelOutboundBuffer channelOutboundBuffer = this.unsafe.outboundBuffer();
        return channelOutboundBuffer != null ? channelOutboundBuffer.bytesBeforeUnwritable() : 0L;
    }

    @Override
    public long bytesBeforeWritable() {
        ChannelOutboundBuffer channelOutboundBuffer = this.unsafe.outboundBuffer();
        return channelOutboundBuffer != null ? channelOutboundBuffer.bytesBeforeWritable() : Long.MAX_VALUE;
    }

    @Override
    public Channel parent() {
        return this.parent;
    }

    @Override
    public ChannelPipeline pipeline() {
        return this.pipeline;
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.config().getAllocator();
    }

    @Override
    public EventLoop eventLoop() {
        EventLoop eventLoop = this.eventLoop;
        if (eventLoop == null) {
            throw new IllegalStateException("channel not registered to an event loop");
        }
        return eventLoop;
    }

    @Override
    public SocketAddress localAddress() {
        SocketAddress socketAddress = this.localAddress;
        if (socketAddress == null) {
            try {
                this.localAddress = socketAddress = this.unsafe().localAddress();
            } catch (Throwable throwable) {
                return null;
            }
        }
        return socketAddress;
    }

    @Deprecated
    protected void invalidateLocalAddress() {
        this.localAddress = null;
    }

    @Override
    public SocketAddress remoteAddress() {
        SocketAddress socketAddress = this.remoteAddress;
        if (socketAddress == null) {
            try {
                this.remoteAddress = socketAddress = this.unsafe().remoteAddress();
            } catch (Throwable throwable) {
                return null;
            }
        }
        return socketAddress;
    }

    @Deprecated
    protected void invalidateRemoteAddress() {
        this.remoteAddress = null;
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public ChannelFuture bind(SocketAddress socketAddress) {
        return this.pipeline.bind(socketAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress) {
        return this.pipeline.connect(socketAddress);
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
        return this.pipeline.connect(socketAddress, socketAddress2);
    }

    @Override
    public ChannelFuture disconnect() {
        return this.pipeline.disconnect();
    }

    @Override
    public ChannelFuture close() {
        return this.pipeline.close();
    }

    @Override
    public ChannelFuture deregister() {
        return this.pipeline.deregister();
    }

    @Override
    public Channel flush() {
        this.pipeline.flush();
        return this;
    }

    @Override
    public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.pipeline.bind(socketAddress, channelPromise);
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
        return this.pipeline.connect(socketAddress, channelPromise);
    }

    @Override
    public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
        return this.pipeline.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public ChannelFuture disconnect(ChannelPromise channelPromise) {
        return this.pipeline.disconnect(channelPromise);
    }

    @Override
    public ChannelFuture close(ChannelPromise channelPromise) {
        return this.pipeline.close(channelPromise);
    }

    @Override
    public ChannelFuture deregister(ChannelPromise channelPromise) {
        return this.pipeline.deregister(channelPromise);
    }

    @Override
    public Channel read() {
        this.pipeline.read();
        return this;
    }

    @Override
    public ChannelFuture write(Object object) {
        return this.pipeline.write(object);
    }

    @Override
    public ChannelFuture write(Object object, ChannelPromise channelPromise) {
        return this.pipeline.write(object, channelPromise);
    }

    @Override
    public ChannelFuture writeAndFlush(Object object) {
        return this.pipeline.writeAndFlush(object);
    }

    @Override
    public ChannelFuture writeAndFlush(Object object, ChannelPromise channelPromise) {
        return this.pipeline.writeAndFlush(object, channelPromise);
    }

    @Override
    public ChannelPromise newPromise() {
        return this.pipeline.newPromise();
    }

    @Override
    public ChannelProgressivePromise newProgressivePromise() {
        return this.pipeline.newProgressivePromise();
    }

    @Override
    public ChannelFuture newSucceededFuture() {
        return this.pipeline.newSucceededFuture();
    }

    @Override
    public ChannelFuture newFailedFuture(Throwable throwable) {
        return this.pipeline.newFailedFuture(throwable);
    }

    @Override
    public ChannelFuture closeFuture() {
        return this.closeFuture;
    }

    @Override
    public Channel.Unsafe unsafe() {
        return this.unsafe;
    }

    protected abstract AbstractUnsafe newUnsafe();

    public final int hashCode() {
        return this.id.hashCode();
    }

    public final boolean equals(Object object) {
        return this == object;
    }

    @Override
    public final int compareTo(Channel channel) {
        if (this == channel) {
            return 1;
        }
        return this.id().compareTo(channel.id());
    }

    public String toString() {
        boolean bl = this.isActive();
        if (this.strValActive == bl && this.strVal != null) {
            return this.strVal;
        }
        SocketAddress socketAddress = this.remoteAddress();
        SocketAddress socketAddress2 = this.localAddress();
        if (socketAddress != null) {
            StringBuilder stringBuilder = new StringBuilder(96).append("[id: 0x").append(this.id.asShortText()).append(", L:").append(socketAddress2).append(bl ? " - " : " ! ").append("R:").append(socketAddress).append(']');
            this.strVal = stringBuilder.toString();
        } else if (socketAddress2 != null) {
            StringBuilder stringBuilder = new StringBuilder(64).append("[id: 0x").append(this.id.asShortText()).append(", L:").append(socketAddress2).append(']');
            this.strVal = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder(16).append("[id: 0x").append(this.id.asShortText()).append(']');
            this.strVal = stringBuilder.toString();
        }
        this.strValActive = bl;
        return this.strVal;
    }

    @Override
    public final ChannelPromise voidPromise() {
        return this.pipeline.voidPromise();
    }

    protected abstract boolean isCompatible(EventLoop var1);

    protected abstract SocketAddress localAddress0();

    protected abstract SocketAddress remoteAddress0();

    protected void doRegister() throws Exception {
    }

    protected abstract void doBind(SocketAddress var1) throws Exception;

    protected abstract void doDisconnect() throws Exception;

    protected abstract void doClose() throws Exception;

    protected void doShutdownOutput() throws Exception {
        this.doClose();
    }

    protected void doDeregister() throws Exception {
    }

    protected abstract void doBeginRead() throws Exception;

    protected abstract void doWrite(ChannelOutboundBuffer var1) throws Exception;

    protected Object filterOutboundMessage(Object object) throws Exception {
        return object;
    }

    @Override
    public ChannelOutboundInvoker flush() {
        return this.flush();
    }

    @Override
    public ChannelOutboundInvoker read() {
        return this.read();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Channel)object);
    }

    static boolean access$000(AbstractChannel abstractChannel) {
        return abstractChannel.registered;
    }

    static EventLoop access$100(AbstractChannel abstractChannel) {
        return abstractChannel.eventLoop;
    }

    static EventLoop access$102(AbstractChannel abstractChannel, EventLoop eventLoop) {
        abstractChannel.eventLoop = eventLoop;
        return abstractChannel.eventLoop;
    }

    static InternalLogger access$300() {
        return logger;
    }

    static CloseFuture access$400(AbstractChannel abstractChannel) {
        return abstractChannel.closeFuture;
    }

    static boolean access$002(AbstractChannel abstractChannel, boolean bl) {
        abstractChannel.registered = bl;
        return abstractChannel.registered;
    }

    static DefaultChannelPipeline access$500(AbstractChannel abstractChannel) {
        return abstractChannel.pipeline;
    }

    static ClosedChannelException access$600() {
        return CLOSE_CLOSED_CHANNEL_EXCEPTION;
    }

    static boolean access$800(AbstractChannel abstractChannel) {
        return abstractChannel.closeInitiated;
    }

    static boolean access$802(AbstractChannel abstractChannel, boolean bl) {
        abstractChannel.closeInitiated = bl;
        return abstractChannel.closeInitiated;
    }

    static ClosedChannelException access$1200() {
        return WRITE_CLOSED_CHANNEL_EXCEPTION;
    }

    static NotYetConnectedException access$1300() {
        return FLUSH0_NOT_YET_CONNECTED_EXCEPTION;
    }

    static ClosedChannelException access$1400() {
        return FLUSH0_CLOSED_CHANNEL_EXCEPTION;
    }

    static VoidChannelPromise access$1500(AbstractChannel abstractChannel) {
        return abstractChannel.unsafeVoidPromise;
    }

    static ClosedChannelException access$1600() {
        return ENSURE_OPEN_CLOSED_CHANNEL_EXCEPTION;
    }

    private static final class AnnotatedSocketException
    extends SocketException {
        private static final long serialVersionUID = 3896743275010454039L;

        AnnotatedSocketException(SocketException socketException, SocketAddress socketAddress) {
            super(socketException.getMessage() + ": " + socketAddress);
            this.initCause(socketException);
            this.setStackTrace(socketException.getStackTrace());
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    private static final class AnnotatedNoRouteToHostException
    extends NoRouteToHostException {
        private static final long serialVersionUID = -6801433937592080623L;

        AnnotatedNoRouteToHostException(NoRouteToHostException noRouteToHostException, SocketAddress socketAddress) {
            super(noRouteToHostException.getMessage() + ": " + socketAddress);
            this.initCause(noRouteToHostException);
            this.setStackTrace(noRouteToHostException.getStackTrace());
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    private static final class AnnotatedConnectException
    extends ConnectException {
        private static final long serialVersionUID = 3901958112696433556L;

        AnnotatedConnectException(ConnectException connectException, SocketAddress socketAddress) {
            super(connectException.getMessage() + ": " + socketAddress);
            this.initCause(connectException);
            this.setStackTrace(connectException.getStackTrace());
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class CloseFuture
    extends DefaultChannelPromise {
        CloseFuture(AbstractChannel abstractChannel) {
            super(abstractChannel);
        }

        @Override
        public ChannelPromise setSuccess() {
            throw new IllegalStateException();
        }

        @Override
        public ChannelPromise setFailure(Throwable throwable) {
            throw new IllegalStateException();
        }

        @Override
        public boolean trySuccess() {
            throw new IllegalStateException();
        }

        @Override
        public boolean tryFailure(Throwable throwable) {
            throw new IllegalStateException();
        }

        boolean setClosed() {
            return super.trySuccess();
        }

        @Override
        public Promise setFailure(Throwable throwable) {
            return this.setFailure(throwable);
        }
    }

    protected abstract class AbstractUnsafe
    implements Channel.Unsafe {
        private volatile ChannelOutboundBuffer outboundBuffer;
        private RecvByteBufAllocator.Handle recvHandle;
        private boolean inFlush0;
        private boolean neverRegistered;
        static final boolean $assertionsDisabled = !AbstractChannel.class.desiredAssertionStatus();
        final AbstractChannel this$0;

        protected AbstractUnsafe(AbstractChannel abstractChannel) {
            this.this$0 = abstractChannel;
            this.outboundBuffer = new ChannelOutboundBuffer(this.this$0);
            this.neverRegistered = true;
        }

        private void assertEventLoop() {
            if (!$assertionsDisabled && AbstractChannel.access$000(this.this$0) && !AbstractChannel.access$100(this.this$0).inEventLoop()) {
                throw new AssertionError();
            }
        }

        @Override
        public RecvByteBufAllocator.Handle recvBufAllocHandle() {
            if (this.recvHandle == null) {
                this.recvHandle = this.this$0.config().getRecvByteBufAllocator().newHandle();
            }
            return this.recvHandle;
        }

        @Override
        public final ChannelOutboundBuffer outboundBuffer() {
            return this.outboundBuffer;
        }

        @Override
        public final SocketAddress localAddress() {
            return this.this$0.localAddress0();
        }

        @Override
        public final SocketAddress remoteAddress() {
            return this.this$0.remoteAddress0();
        }

        @Override
        public final void register(EventLoop eventLoop, ChannelPromise channelPromise) {
            if (eventLoop == null) {
                throw new NullPointerException("eventLoop");
            }
            if (this.this$0.isRegistered()) {
                channelPromise.setFailure(new IllegalStateException("registered to an event loop already"));
                return;
            }
            if (!this.this$0.isCompatible(eventLoop)) {
                channelPromise.setFailure(new IllegalStateException("incompatible event loop type: " + eventLoop.getClass().getName()));
                return;
            }
            AbstractChannel.access$102(this.this$0, eventLoop);
            if (eventLoop.inEventLoop()) {
                this.register0(channelPromise);
            } else {
                try {
                    eventLoop.execute(new Runnable(this, channelPromise){
                        final ChannelPromise val$promise;
                        final AbstractUnsafe this$1;
                        {
                            this.this$1 = abstractUnsafe;
                            this.val$promise = channelPromise;
                        }

                        @Override
                        public void run() {
                            AbstractUnsafe.access$200(this.this$1, this.val$promise);
                        }
                    });
                } catch (Throwable throwable) {
                    AbstractChannel.access$300().warn("Force-closing a channel whose registration task was not accepted by an event loop: {}", (Object)this.this$0, (Object)throwable);
                    this.closeForcibly();
                    AbstractChannel.access$400(this.this$0).setClosed();
                    this.safeSetFailure(channelPromise, throwable);
                }
            }
        }

        private void register0(ChannelPromise channelPromise) {
            try {
                if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                    return;
                }
                boolean bl = this.neverRegistered;
                this.this$0.doRegister();
                this.neverRegistered = false;
                AbstractChannel.access$002(this.this$0, true);
                AbstractChannel.access$500(this.this$0).invokeHandlerAddedIfNeeded();
                this.safeSetSuccess(channelPromise);
                AbstractChannel.access$500(this.this$0).fireChannelRegistered();
                if (this.this$0.isActive()) {
                    if (bl) {
                        AbstractChannel.access$500(this.this$0).fireChannelActive();
                    } else if (this.this$0.config().isAutoRead()) {
                        this.beginRead();
                    }
                }
            } catch (Throwable throwable) {
                this.closeForcibly();
                AbstractChannel.access$400(this.this$0).setClosed();
                this.safeSetFailure(channelPromise, throwable);
            }
        }

        @Override
        public final void bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            this.assertEventLoop();
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            if (Boolean.TRUE.equals(this.this$0.config().getOption(ChannelOption.SO_BROADCAST)) && socketAddress instanceof InetSocketAddress && !((InetSocketAddress)socketAddress).getAddress().isAnyLocalAddress() && !PlatformDependent.isWindows() && !PlatformDependent.maybeSuperUser()) {
                AbstractChannel.access$300().warn("A non-root user can't receive a broadcast packet if the socket is not bound to a wildcard address; binding to a non-wildcard address (" + socketAddress + ") anyway as requested.");
            }
            boolean bl = this.this$0.isActive();
            try {
                this.this$0.doBind(socketAddress);
            } catch (Throwable throwable) {
                this.safeSetFailure(channelPromise, throwable);
                this.closeIfClosed();
                return;
            }
            if (!bl && this.this$0.isActive()) {
                this.invokeLater(new Runnable(this){
                    final AbstractUnsafe this$1;
                    {
                        this.this$1 = abstractUnsafe;
                    }

                    @Override
                    public void run() {
                        AbstractChannel.access$500(this.this$1.this$0).fireChannelActive();
                    }
                });
            }
            this.safeSetSuccess(channelPromise);
        }

        @Override
        public final void disconnect(ChannelPromise channelPromise) {
            this.assertEventLoop();
            if (!channelPromise.setUncancellable()) {
                return;
            }
            boolean bl = this.this$0.isActive();
            try {
                this.this$0.doDisconnect();
            } catch (Throwable throwable) {
                this.safeSetFailure(channelPromise, throwable);
                this.closeIfClosed();
                return;
            }
            if (bl && !this.this$0.isActive()) {
                this.invokeLater(new Runnable(this){
                    final AbstractUnsafe this$1;
                    {
                        this.this$1 = abstractUnsafe;
                    }

                    @Override
                    public void run() {
                        AbstractChannel.access$500(this.this$1.this$0).fireChannelInactive();
                    }
                });
            }
            this.safeSetSuccess(channelPromise);
            this.closeIfClosed();
        }

        @Override
        public final void close(ChannelPromise channelPromise) {
            this.assertEventLoop();
            this.close(channelPromise, AbstractChannel.access$600(), AbstractChannel.access$600(), false);
        }

        public final void shutdownOutput(ChannelPromise channelPromise) {
            this.assertEventLoop();
            this.shutdownOutput(channelPromise, null);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void shutdownOutput(ChannelPromise channelPromise, Throwable throwable) {
            if (!channelPromise.setUncancellable()) {
                return;
            }
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            if (channelOutboundBuffer == null) {
                channelPromise.setFailure(AbstractChannel.access$600());
                return;
            }
            this.outboundBuffer = null;
            ChannelOutputShutdownException channelOutputShutdownException = throwable == null ? new ChannelOutputShutdownException("Channel output shutdown") : new ChannelOutputShutdownException("Channel output shutdown", throwable);
            Executor executor = this.prepareToClose();
            if (executor != null) {
                executor.execute(new Runnable(this, channelPromise, channelOutboundBuffer, channelOutputShutdownException){
                    final ChannelPromise val$promise;
                    final ChannelOutboundBuffer val$outboundBuffer;
                    final Throwable val$shutdownCause;
                    final AbstractUnsafe this$1;
                    {
                        this.this$1 = abstractUnsafe;
                        this.val$promise = channelPromise;
                        this.val$outboundBuffer = channelOutboundBuffer;
                        this.val$shutdownCause = throwable;
                    }

                    @Override
                    public void run() {
                        try {
                            this.this$1.this$0.doShutdownOutput();
                            this.val$promise.setSuccess();
                        } catch (Throwable throwable) {
                            try {
                                this.val$promise.setFailure(throwable);
                            } catch (Throwable throwable2) {
                                this.this$1.this$0.eventLoop().execute(new Runnable(this){
                                    final 4 this$2;
                                    {
                                        this.this$2 = var1_1;
                                    }

                                    @Override
                                    public void run() {
                                        AbstractUnsafe.access$700(this.this$2.this$1, AbstractChannel.access$500(this.this$2.this$1.this$0), this.this$2.val$outboundBuffer, this.this$2.val$shutdownCause);
                                    }
                                });
                                throw throwable2;
                            }
                            this.this$1.this$0.eventLoop().execute(new /* invalid duplicate definition of identical inner class */);
                        }
                        this.this$1.this$0.eventLoop().execute(new /* invalid duplicate definition of identical inner class */);
                    }
                });
            } else {
                try {
                    this.this$0.doShutdownOutput();
                    channelPromise.setSuccess();
                } catch (Throwable throwable2) {
                    channelPromise.setFailure(throwable2);
                } finally {
                    this.closeOutboundBufferForShutdown(AbstractChannel.access$500(this.this$0), channelOutboundBuffer, channelOutputShutdownException);
                }
            }
        }

        private void closeOutboundBufferForShutdown(ChannelPipeline channelPipeline, ChannelOutboundBuffer channelOutboundBuffer, Throwable throwable) {
            channelOutboundBuffer.failFlushed(throwable, true);
            channelOutboundBuffer.close(throwable, false);
            channelPipeline.fireUserEventTriggered(ChannelOutputShutdownEvent.INSTANCE);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void close(ChannelPromise channelPromise, Throwable throwable, ClosedChannelException closedChannelException, boolean bl) {
            if (!channelPromise.setUncancellable()) {
                return;
            }
            if (AbstractChannel.access$800(this.this$0)) {
                if (AbstractChannel.access$400(this.this$0).isDone()) {
                    this.safeSetSuccess(channelPromise);
                } else if (!(channelPromise instanceof VoidChannelPromise)) {
                    AbstractChannel.access$400(this.this$0).addListener((GenericFutureListener)new ChannelFutureListener(this, channelPromise){
                        final ChannelPromise val$promise;
                        final AbstractUnsafe this$1;
                        {
                            this.this$1 = abstractUnsafe;
                            this.val$promise = channelPromise;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            this.val$promise.setSuccess();
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
                return;
            }
            AbstractChannel.access$802(this.this$0, true);
            boolean bl2 = this.this$0.isActive();
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            this.outboundBuffer = null;
            Executor executor = this.prepareToClose();
            if (executor != null) {
                executor.execute(new Runnable(this, channelPromise, channelOutboundBuffer, throwable, bl, closedChannelException, bl2){
                    final ChannelPromise val$promise;
                    final ChannelOutboundBuffer val$outboundBuffer;
                    final Throwable val$cause;
                    final boolean val$notify;
                    final ClosedChannelException val$closeCause;
                    final boolean val$wasActive;
                    final AbstractUnsafe this$1;
                    {
                        this.this$1 = abstractUnsafe;
                        this.val$promise = channelPromise;
                        this.val$outboundBuffer = channelOutboundBuffer;
                        this.val$cause = throwable;
                        this.val$notify = bl;
                        this.val$closeCause = closedChannelException;
                        this.val$wasActive = bl2;
                    }

                    @Override
                    public void run() {
                        try {
                            AbstractUnsafe.access$900(this.this$1, this.val$promise);
                        } catch (Throwable throwable) {
                            AbstractUnsafe.access$1100(this.this$1, new Runnable(this){
                                final 6 this$2;
                                {
                                    this.this$2 = var1_1;
                                }

                                @Override
                                public void run() {
                                    if (this.this$2.val$outboundBuffer != null) {
                                        this.this$2.val$outboundBuffer.failFlushed(this.this$2.val$cause, this.this$2.val$notify);
                                        this.this$2.val$outboundBuffer.close(this.this$2.val$closeCause);
                                    }
                                    AbstractUnsafe.access$1000(this.this$2.this$1, this.this$2.val$wasActive);
                                }
                            });
                            throw throwable;
                        }
                        AbstractUnsafe.access$1100(this.this$1, new /* invalid duplicate definition of identical inner class */);
                    }
                });
            } else {
                try {
                    this.doClose0(channelPromise);
                } finally {
                    if (channelOutboundBuffer != null) {
                        channelOutboundBuffer.failFlushed(throwable, bl);
                        channelOutboundBuffer.close(closedChannelException);
                    }
                }
                if (this.inFlush0) {
                    this.invokeLater(new Runnable(this, bl2){
                        final boolean val$wasActive;
                        final AbstractUnsafe this$1;
                        {
                            this.this$1 = abstractUnsafe;
                            this.val$wasActive = bl;
                        }

                        @Override
                        public void run() {
                            AbstractUnsafe.access$1000(this.this$1, this.val$wasActive);
                        }
                    });
                } else {
                    this.fireChannelInactiveAndDeregister(bl2);
                }
            }
        }

        private void doClose0(ChannelPromise channelPromise) {
            try {
                this.this$0.doClose();
                AbstractChannel.access$400(this.this$0).setClosed();
                this.safeSetSuccess(channelPromise);
            } catch (Throwable throwable) {
                AbstractChannel.access$400(this.this$0).setClosed();
                this.safeSetFailure(channelPromise, throwable);
            }
        }

        private void fireChannelInactiveAndDeregister(boolean bl) {
            this.deregister(this.voidPromise(), bl && !this.this$0.isActive());
        }

        @Override
        public final void closeForcibly() {
            this.assertEventLoop();
            try {
                this.this$0.doClose();
            } catch (Exception exception) {
                AbstractChannel.access$300().warn("Failed to close a channel.", exception);
            }
        }

        @Override
        public final void deregister(ChannelPromise channelPromise) {
            this.assertEventLoop();
            this.deregister(channelPromise, false);
        }

        private void deregister(ChannelPromise channelPromise, boolean bl) {
            if (!channelPromise.setUncancellable()) {
                return;
            }
            if (!AbstractChannel.access$000(this.this$0)) {
                this.safeSetSuccess(channelPromise);
                return;
            }
            this.invokeLater(new Runnable(this, bl, channelPromise){
                final boolean val$fireChannelInactive;
                final ChannelPromise val$promise;
                final AbstractUnsafe this$1;
                {
                    this.this$1 = abstractUnsafe;
                    this.val$fireChannelInactive = bl;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    try {
                        this.this$1.this$0.doDeregister();
                    } catch (Throwable throwable) {
                        AbstractChannel.access$300().warn("Unexpected exception occurred while deregistering a channel.", throwable);
                    } finally {
                        if (this.val$fireChannelInactive) {
                            AbstractChannel.access$500(this.this$1.this$0).fireChannelInactive();
                        }
                        if (AbstractChannel.access$000(this.this$1.this$0)) {
                            AbstractChannel.access$002(this.this$1.this$0, false);
                            AbstractChannel.access$500(this.this$1.this$0).fireChannelUnregistered();
                        }
                        this.this$1.safeSetSuccess(this.val$promise);
                    }
                }
            });
        }

        @Override
        public final void beginRead() {
            this.assertEventLoop();
            if (!this.this$0.isActive()) {
                return;
            }
            try {
                this.this$0.doBeginRead();
            } catch (Exception exception) {
                this.invokeLater(new Runnable(this, exception){
                    final Exception val$e;
                    final AbstractUnsafe this$1;
                    {
                        this.this$1 = abstractUnsafe;
                        this.val$e = exception;
                    }

                    @Override
                    public void run() {
                        AbstractChannel.access$500(this.this$1.this$0).fireExceptionCaught(this.val$e);
                    }
                });
                this.close(this.voidPromise());
            }
        }

        @Override
        public final void write(Object object, ChannelPromise channelPromise) {
            int n;
            this.assertEventLoop();
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            if (channelOutboundBuffer == null) {
                this.safeSetFailure(channelPromise, AbstractChannel.access$1200());
                ReferenceCountUtil.release(object);
                return;
            }
            try {
                object = this.this$0.filterOutboundMessage(object);
                n = AbstractChannel.access$500(this.this$0).estimatorHandle().size(object);
                if (n < 0) {
                    n = 0;
                }
            } catch (Throwable throwable) {
                this.safeSetFailure(channelPromise, throwable);
                ReferenceCountUtil.release(object);
                return;
            }
            channelOutboundBuffer.addMessage(object, n, channelPromise);
        }

        @Override
        public final void flush() {
            this.assertEventLoop();
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            if (channelOutboundBuffer == null) {
                return;
            }
            channelOutboundBuffer.addFlush();
            this.flush0();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected void flush0() {
            if (this.inFlush0) {
                return;
            }
            ChannelOutboundBuffer channelOutboundBuffer = this.outboundBuffer;
            if (channelOutboundBuffer == null || channelOutboundBuffer.isEmpty()) {
                return;
            }
            this.inFlush0 = true;
            if (!this.this$0.isActive()) {
                try {
                    if (this.this$0.isOpen()) {
                        channelOutboundBuffer.failFlushed(AbstractChannel.access$1300(), false);
                    } else {
                        channelOutboundBuffer.failFlushed(AbstractChannel.access$1400(), true);
                    }
                } finally {
                    this.inFlush0 = false;
                }
                return;
            }
            try {
                this.this$0.doWrite(channelOutboundBuffer);
            } catch (Throwable throwable) {
                if (throwable instanceof IOException && this.this$0.config().isAutoClose()) {
                    this.close(this.voidPromise(), throwable, AbstractChannel.access$1400(), false);
                } else {
                    try {
                        this.shutdownOutput(this.voidPromise(), throwable);
                    } catch (Throwable throwable2) {
                        this.close(this.voidPromise(), throwable2, AbstractChannel.access$1400(), false);
                    }
                }
            } finally {
                this.inFlush0 = false;
            }
        }

        @Override
        public final ChannelPromise voidPromise() {
            this.assertEventLoop();
            return AbstractChannel.access$1500(this.this$0);
        }

        protected final boolean ensureOpen(ChannelPromise channelPromise) {
            if (this.this$0.isOpen()) {
                return false;
            }
            this.safeSetFailure(channelPromise, AbstractChannel.access$1600());
            return true;
        }

        protected final void safeSetSuccess(ChannelPromise channelPromise) {
            if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.trySuccess()) {
                AbstractChannel.access$300().warn("Failed to mark a promise as success because it is done already: {}", (Object)channelPromise);
            }
        }

        protected final void safeSetFailure(ChannelPromise channelPromise, Throwable throwable) {
            if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(throwable)) {
                AbstractChannel.access$300().warn("Failed to mark a promise as failure because it's done already: {}", (Object)channelPromise, (Object)throwable);
            }
        }

        protected final void closeIfClosed() {
            if (this.this$0.isOpen()) {
                return;
            }
            this.close(this.voidPromise());
        }

        private void invokeLater(Runnable runnable) {
            try {
                this.this$0.eventLoop().execute(runnable);
            } catch (RejectedExecutionException rejectedExecutionException) {
                AbstractChannel.access$300().warn("Can't invoke task later as EventLoop rejected it", rejectedExecutionException);
            }
        }

        protected final Throwable annotateConnectException(Throwable throwable, SocketAddress socketAddress) {
            if (throwable instanceof ConnectException) {
                return new AnnotatedConnectException((ConnectException)throwable, socketAddress);
            }
            if (throwable instanceof NoRouteToHostException) {
                return new AnnotatedNoRouteToHostException((NoRouteToHostException)throwable, socketAddress);
            }
            if (throwable instanceof SocketException) {
                return new AnnotatedSocketException((SocketException)throwable, socketAddress);
            }
            return throwable;
        }

        protected Executor prepareToClose() {
            return null;
        }

        static void access$200(AbstractUnsafe abstractUnsafe, ChannelPromise channelPromise) {
            abstractUnsafe.register0(channelPromise);
        }

        static void access$700(AbstractUnsafe abstractUnsafe, ChannelPipeline channelPipeline, ChannelOutboundBuffer channelOutboundBuffer, Throwable throwable) {
            abstractUnsafe.closeOutboundBufferForShutdown(channelPipeline, channelOutboundBuffer, throwable);
        }

        static void access$900(AbstractUnsafe abstractUnsafe, ChannelPromise channelPromise) {
            abstractUnsafe.doClose0(channelPromise);
        }

        static void access$1000(AbstractUnsafe abstractUnsafe, boolean bl) {
            abstractUnsafe.fireChannelInactiveAndDeregister(bl);
        }

        static void access$1100(AbstractUnsafe abstractUnsafe, Runnable runnable) {
            abstractUnsafe.invokeLater(runnable);
        }
    }
}

