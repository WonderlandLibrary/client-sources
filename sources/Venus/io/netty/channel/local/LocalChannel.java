/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.local;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.PreferHeapByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannelRegistry;
import io.netty.channel.local.LocalServerChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.ConnectException;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LocalChannel
extends AbstractChannel {
    private static final InternalLogger logger;
    private static final AtomicReferenceFieldUpdater<LocalChannel, Future> FINISH_READ_FUTURE_UPDATER;
    private static final ChannelMetadata METADATA;
    private static final int MAX_READER_STACK_DEPTH = 8;
    private static final ClosedChannelException DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION;
    private final ChannelConfig config = new DefaultChannelConfig(this);
    final Queue<Object> inboundBuffer = PlatformDependent.newSpscQueue();
    private final Runnable readTask = new Runnable(this){
        final LocalChannel this$0;
        {
            this.this$0 = localChannel;
        }

        @Override
        public void run() {
            if (!this.this$0.inboundBuffer.isEmpty()) {
                LocalChannel.access$000(this.this$0);
            }
        }
    };
    private final Runnable shutdownHook = new Runnable(this){
        final LocalChannel this$0;
        {
            this.this$0 = localChannel;
        }

        @Override
        public void run() {
            this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
        }
    };
    private volatile State state;
    private volatile LocalChannel peer;
    private volatile LocalAddress localAddress;
    private volatile LocalAddress remoteAddress;
    private volatile ChannelPromise connectPromise;
    private volatile boolean readInProgress;
    private volatile boolean writeInProgress;
    private volatile Future<?> finishReadFuture;
    static final boolean $assertionsDisabled;

    public LocalChannel() {
        super(null);
        this.config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
    }

    protected LocalChannel(LocalServerChannel localServerChannel, LocalChannel localChannel) {
        super(localServerChannel);
        this.config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
        this.peer = localChannel;
        this.localAddress = localServerChannel.localAddress();
        this.remoteAddress = localChannel.localAddress();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public ChannelConfig config() {
        return this.config;
    }

    @Override
    public LocalServerChannel parent() {
        return (LocalServerChannel)super.parent();
    }

    @Override
    public LocalAddress localAddress() {
        return (LocalAddress)super.localAddress();
    }

    @Override
    public LocalAddress remoteAddress() {
        return (LocalAddress)super.remoteAddress();
    }

    @Override
    public boolean isOpen() {
        return this.state != State.CLOSED;
    }

    @Override
    public boolean isActive() {
        return this.state == State.CONNECTED;
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new LocalUnsafe(this, null);
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress;
    }

    @Override
    protected void doRegister() throws Exception {
        if (this.peer != null && this.parent() != null) {
            LocalChannel localChannel = this.peer;
            this.state = State.CONNECTED;
            localChannel.remoteAddress = this.parent() == null ? null : this.parent().localAddress();
            localChannel.state = State.CONNECTED;
            localChannel.eventLoop().execute(new Runnable(this, localChannel){
                final LocalChannel val$peer;
                final LocalChannel this$0;
                {
                    this.this$0 = localChannel;
                    this.val$peer = localChannel2;
                }

                @Override
                public void run() {
                    ChannelPromise channelPromise = LocalChannel.access$200(this.val$peer);
                    if (channelPromise != null && channelPromise.trySuccess()) {
                        this.val$peer.pipeline().fireChannelActive();
                    }
                }
            });
        }
        ((SingleThreadEventExecutor)((Object)this.eventLoop())).addShutdownHook(this.shutdownHook);
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, socketAddress);
        this.state = State.BOUND;
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doClose() throws Exception {
        LocalChannel localChannel = this.peer;
        State state = this.state;
        try {
            Object object;
            if (state != State.CLOSED) {
                if (this.localAddress != null) {
                    if (this.parent() == null) {
                        LocalChannelRegistry.unregister(this.localAddress);
                    }
                    this.localAddress = null;
                }
                this.state = State.CLOSED;
                if (this.writeInProgress && localChannel != null) {
                    this.finishPeerRead(localChannel);
                }
                if ((object = this.connectPromise) != null) {
                    object.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
                    this.connectPromise = null;
                }
            }
            if (localChannel != null) {
                this.peer = null;
                object = localChannel.eventLoop();
                boolean bl = localChannel.isActive();
                try {
                    object.execute(new Runnable(this, localChannel, bl){
                        final LocalChannel val$peer;
                        final boolean val$peerIsActive;
                        final LocalChannel this$0;
                        {
                            this.this$0 = localChannel;
                            this.val$peer = localChannel2;
                            this.val$peerIsActive = bl;
                        }

                        @Override
                        public void run() {
                            LocalChannel.access$300(this.val$peer, this.val$peerIsActive);
                        }
                    });
                } catch (Throwable throwable) {
                    logger.warn("Releasing Inbound Queues for channels {}-{} because exception occurred!", this, localChannel, throwable);
                    if (object.inEventLoop()) {
                        localChannel.releaseInboundBuffers();
                    } else {
                        localChannel.close();
                    }
                    PlatformDependent.throwException(throwable);
                }
            }
        } finally {
            if (state != null && state != State.CLOSED) {
                this.releaseInboundBuffers();
            }
        }
    }

    private void tryClose(boolean bl) {
        if (bl) {
            this.unsafe().close(this.unsafe().voidPromise());
        } else {
            this.releaseInboundBuffers();
        }
    }

    @Override
    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor)((Object)this.eventLoop())).removeShutdownHook(this.shutdownHook);
    }

    private void readInbound() {
        Object object;
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        handle.reset(this.config());
        ChannelPipeline channelPipeline = this.pipeline();
        while ((object = this.inboundBuffer.poll()) != null) {
            channelPipeline.fireChannelRead(object);
            if (handle.continueReading()) continue;
        }
        channelPipeline.fireChannelReadComplete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doBeginRead() throws Exception {
        if (this.readInProgress) {
            return;
        }
        Queue<Object> queue = this.inboundBuffer;
        if (queue.isEmpty()) {
            this.readInProgress = true;
            return;
        }
        InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
        Integer n = internalThreadLocalMap.localChannelReaderStackDepth();
        if (n < 8) {
            internalThreadLocalMap.setLocalChannelReaderStackDepth(n + 1);
            try {
                this.readInbound();
            } finally {
                internalThreadLocalMap.setLocalChannelReaderStackDepth(n);
            }
        }
        try {
            this.eventLoop().execute(this.readTask);
        } catch (Throwable throwable) {
            logger.warn("Closing Local channels {}-{} because exception occurred!", this, this.peer, throwable);
            this.close();
            this.peer.close();
            PlatformDependent.throwException(throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        LocalChannel localChannel;
        block13: {
            switch (6.$SwitchMap$io$netty$channel$local$LocalChannel$State[this.state.ordinal()]) {
                case 1: 
                case 2: {
                    throw new NotYetConnectedException();
                }
                case 3: {
                    throw DO_WRITE_CLOSED_CHANNEL_EXCEPTION;
                }
            }
            localChannel = this.peer;
            this.writeInProgress = true;
            block9: while (true) {
                while (true) {
                    Object object;
                    if ((object = channelOutboundBuffer.current()) == null) {
                        break block13;
                    }
                    try {
                        if (localChannel.state == State.CONNECTED) {
                            localChannel.inboundBuffer.add(ReferenceCountUtil.retain(object));
                            channelOutboundBuffer.remove();
                            continue block9;
                        }
                        channelOutboundBuffer.remove(DO_WRITE_CLOSED_CHANNEL_EXCEPTION);
                        continue block9;
                    } catch (Throwable throwable) {
                        channelOutboundBuffer.remove(throwable);
                        continue;
                    }
                    break;
                }
            }
            finally {
                this.writeInProgress = false;
            }
        }
        this.finishPeerRead(localChannel);
    }

    private void finishPeerRead(LocalChannel localChannel) {
        if (localChannel.eventLoop() == this.eventLoop() && !localChannel.writeInProgress) {
            this.finishPeerRead0(localChannel);
        } else {
            this.runFinishPeerReadTask(localChannel);
        }
    }

    private void runFinishPeerReadTask(LocalChannel localChannel) {
        Runnable runnable = new Runnable(this, localChannel){
            final LocalChannel val$peer;
            final LocalChannel this$0;
            {
                this.this$0 = localChannel;
                this.val$peer = localChannel2;
            }

            @Override
            public void run() {
                LocalChannel.access$400(this.this$0, this.val$peer);
            }
        };
        try {
            if (localChannel.writeInProgress) {
                localChannel.finishReadFuture = localChannel.eventLoop().submit(runnable);
            } else {
                localChannel.eventLoop().execute(runnable);
            }
        } catch (Throwable throwable) {
            logger.warn("Closing Local channels {}-{} because exception occurred!", this, localChannel, throwable);
            this.close();
            localChannel.close();
            PlatformDependent.throwException(throwable);
        }
    }

    private void releaseInboundBuffers() {
        Object object;
        if (!$assertionsDisabled && this.eventLoop() != null && !this.eventLoop().inEventLoop()) {
            throw new AssertionError();
        }
        this.readInProgress = false;
        Queue<Object> queue = this.inboundBuffer;
        while ((object = queue.poll()) != null) {
            ReferenceCountUtil.release(object);
        }
    }

    private void finishPeerRead0(LocalChannel localChannel) {
        Future<?> future = localChannel.finishReadFuture;
        if (future != null) {
            if (!future.isDone()) {
                this.runFinishPeerReadTask(localChannel);
                return;
            }
            FINISH_READ_FUTURE_UPDATER.compareAndSet(localChannel, future, null);
        }
        if (localChannel.readInProgress && !localChannel.inboundBuffer.isEmpty()) {
            localChannel.readInProgress = false;
            localChannel.readInbound();
        }
    }

    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }

    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }

    @Override
    public Channel parent() {
        return this.parent();
    }

    static void access$000(LocalChannel localChannel) {
        localChannel.readInbound();
    }

    static ChannelPromise access$200(LocalChannel localChannel) {
        return localChannel.connectPromise;
    }

    static void access$300(LocalChannel localChannel, boolean bl) {
        localChannel.tryClose(bl);
    }

    static void access$400(LocalChannel localChannel, LocalChannel localChannel2) {
        localChannel.finishPeerRead0(localChannel2);
    }

    static State access$500(LocalChannel localChannel) {
        return localChannel.state;
    }

    static ChannelPromise access$202(LocalChannel localChannel, ChannelPromise channelPromise) {
        localChannel.connectPromise = channelPromise;
        return localChannel.connectPromise;
    }

    static LocalChannel access$602(LocalChannel localChannel, LocalChannel localChannel2) {
        localChannel.peer = localChannel2;
        return localChannel.peer;
    }

    static {
        $assertionsDisabled = !LocalChannel.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(LocalChannel.class);
        FINISH_READ_FUTURE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(LocalChannel.class, Future.class, "finishReadFuture");
        METADATA = new ChannelMetadata(false);
        DO_WRITE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doWrite(...)");
        DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), LocalChannel.class, "doClose()");
    }

    private class LocalUnsafe
    extends AbstractChannel.AbstractUnsafe {
        final LocalChannel this$0;

        private LocalUnsafe(LocalChannel localChannel) {
            this.this$0 = localChannel;
            super(localChannel);
        }

        @Override
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            Channel channel;
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            if (LocalChannel.access$500(this.this$0) == State.CONNECTED) {
                AlreadyConnectedException alreadyConnectedException = new AlreadyConnectedException();
                this.safeSetFailure(channelPromise, alreadyConnectedException);
                this.this$0.pipeline().fireExceptionCaught(alreadyConnectedException);
                return;
            }
            if (LocalChannel.access$200(this.this$0) != null) {
                throw new ConnectionPendingException();
            }
            LocalChannel.access$202(this.this$0, channelPromise);
            if (LocalChannel.access$500(this.this$0) != State.BOUND && socketAddress2 == null) {
                socketAddress2 = new LocalAddress(this.this$0);
            }
            if (socketAddress2 != null) {
                try {
                    this.this$0.doBind(socketAddress2);
                } catch (Throwable throwable) {
                    this.safeSetFailure(channelPromise, throwable);
                    this.close(this.voidPromise());
                    return;
                }
            }
            if (!((channel = LocalChannelRegistry.get(socketAddress)) instanceof LocalServerChannel)) {
                ConnectException connectException = new ConnectException("connection refused: " + socketAddress);
                this.safeSetFailure(channelPromise, connectException);
                this.close(this.voidPromise());
                return;
            }
            LocalServerChannel localServerChannel = (LocalServerChannel)channel;
            LocalChannel.access$602(this.this$0, localServerChannel.serve(this.this$0));
        }

        LocalUnsafe(LocalChannel localChannel, 1 var2_2) {
            this(localChannel);
        }
    }

    private static enum State {
        OPEN,
        BOUND,
        CONNECTED,
        CLOSED;

    }
}

