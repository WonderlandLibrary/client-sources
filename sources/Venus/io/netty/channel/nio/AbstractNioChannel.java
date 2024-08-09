/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoop;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractNioChannel
extends AbstractChannel {
    private static final InternalLogger logger;
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION;
    private final SelectableChannel ch;
    protected final int readInterestOp;
    volatile SelectionKey selectionKey;
    boolean readPending;
    private final Runnable clearReadPendingRunnable = new Runnable(this){
        final AbstractNioChannel this$0;
        {
            this.this$0 = abstractNioChannel;
        }

        @Override
        public void run() {
            AbstractNioChannel.access$000(this.this$0);
        }
    };
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    static final boolean $assertionsDisabled;

    protected AbstractNioChannel(Channel channel, SelectableChannel selectableChannel, int n) {
        super(channel);
        this.ch = selectableChannel;
        this.readInterestOp = n;
        try {
            selectableChannel.configureBlocking(true);
        } catch (IOException iOException) {
            block4: {
                try {
                    selectableChannel.close();
                } catch (IOException iOException2) {
                    if (!logger.isWarnEnabled()) break block4;
                    logger.warn("Failed to close a partially initialized socket.", iOException2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", iOException);
        }
    }

    @Override
    public boolean isOpen() {
        return this.ch.isOpen();
    }

    @Override
    public NioUnsafe unsafe() {
        return (NioUnsafe)super.unsafe();
    }

    protected SelectableChannel javaChannel() {
        return this.ch;
    }

    @Override
    public NioEventLoop eventLoop() {
        return (NioEventLoop)super.eventLoop();
    }

    protected SelectionKey selectionKey() {
        if (!$assertionsDisabled && this.selectionKey == null) {
            throw new AssertionError();
        }
        return this.selectionKey;
    }

    @Deprecated
    protected boolean isReadPending() {
        return this.readPending;
    }

    @Deprecated
    protected void setReadPending(boolean bl) {
        if (this.isRegistered()) {
            NioEventLoop nioEventLoop = this.eventLoop();
            if (nioEventLoop.inEventLoop()) {
                this.setReadPending0(bl);
            } else {
                nioEventLoop.execute(new Runnable(this, bl){
                    final boolean val$readPending;
                    final AbstractNioChannel this$0;
                    {
                        this.this$0 = abstractNioChannel;
                        this.val$readPending = bl;
                    }

                    @Override
                    public void run() {
                        AbstractNioChannel.access$100(this.this$0, this.val$readPending);
                    }
                });
            }
        } else {
            this.readPending = bl;
        }
    }

    protected final void clearReadPending() {
        if (this.isRegistered()) {
            NioEventLoop nioEventLoop = this.eventLoop();
            if (nioEventLoop.inEventLoop()) {
                this.clearReadPending0();
            } else {
                nioEventLoop.execute(this.clearReadPendingRunnable);
            }
        } else {
            this.readPending = false;
        }
    }

    private void setReadPending0(boolean bl) {
        this.readPending = bl;
        if (!bl) {
            ((AbstractNioUnsafe)this.unsafe()).removeReadOp();
        }
    }

    private void clearReadPending0() {
        this.readPending = false;
        ((AbstractNioUnsafe)this.unsafe()).removeReadOp();
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof NioEventLoop;
    }

    @Override
    protected void doRegister() throws Exception {
        boolean bl = false;
        while (true) {
            try {
                this.selectionKey = this.javaChannel().register(this.eventLoop().unwrappedSelector(), 0, this);
                return;
            } catch (CancelledKeyException cancelledKeyException) {
                if (!bl) {
                    this.eventLoop().selectNow();
                    bl = true;
                    continue;
                }
                throw cancelledKeyException;
            }
            break;
        }
    }

    @Override
    protected void doDeregister() throws Exception {
        this.eventLoop().cancel(this.selectionKey());
    }

    @Override
    protected void doBeginRead() throws Exception {
        SelectionKey selectionKey = this.selectionKey;
        if (!selectionKey.isValid()) {
            return;
        }
        this.readPending = true;
        int n = selectionKey.interestOps();
        if ((n & this.readInterestOp) == 0) {
            selectionKey.interestOps(n | this.readInterestOp);
        }
    }

    protected abstract boolean doConnect(SocketAddress var1, SocketAddress var2) throws Exception;

    protected abstract void doFinishConnect() throws Exception;

    protected final ByteBuf newDirectBuffer(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (n == 0) {
            ReferenceCountUtil.safeRelease(byteBuf);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator byteBufAllocator = this.alloc();
        if (byteBufAllocator.isDirectBufferPooled()) {
            ByteBuf byteBuf2 = byteBufAllocator.directBuffer(n);
            byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), n);
            ReferenceCountUtil.safeRelease(byteBuf);
            return byteBuf2;
        }
        ByteBuf byteBuf3 = ByteBufUtil.threadLocalDirectBuffer();
        if (byteBuf3 != null) {
            byteBuf3.writeBytes(byteBuf, byteBuf.readerIndex(), n);
            ReferenceCountUtil.safeRelease(byteBuf);
            return byteBuf3;
        }
        return byteBuf;
    }

    protected final ByteBuf newDirectBuffer(ReferenceCounted referenceCounted, ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (n == 0) {
            ReferenceCountUtil.safeRelease(referenceCounted);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator byteBufAllocator = this.alloc();
        if (byteBufAllocator.isDirectBufferPooled()) {
            ByteBuf byteBuf2 = byteBufAllocator.directBuffer(n);
            byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), n);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return byteBuf2;
        }
        ByteBuf byteBuf3 = ByteBufUtil.threadLocalDirectBuffer();
        if (byteBuf3 != null) {
            byteBuf3.writeBytes(byteBuf, byteBuf.readerIndex(), n);
            ReferenceCountUtil.safeRelease(referenceCounted);
            return byteBuf3;
        }
        if (referenceCounted != byteBuf) {
            byteBuf.retain();
            ReferenceCountUtil.safeRelease(referenceCounted);
        }
        return byteBuf;
    }

    @Override
    protected void doClose() throws Exception {
        ScheduledFuture<?> scheduledFuture;
        ChannelPromise channelPromise = this.connectPromise;
        if (channelPromise != null) {
            channelPromise.tryFailure(DO_CLOSE_CLOSED_CHANNEL_EXCEPTION);
            this.connectPromise = null;
        }
        if ((scheduledFuture = this.connectTimeoutFuture) != null) {
            scheduledFuture.cancel(false);
            this.connectTimeoutFuture = null;
        }
    }

    @Override
    public Channel.Unsafe unsafe() {
        return this.unsafe();
    }

    @Override
    public EventLoop eventLoop() {
        return this.eventLoop();
    }

    static void access$000(AbstractNioChannel abstractNioChannel) {
        abstractNioChannel.clearReadPending0();
    }

    static void access$100(AbstractNioChannel abstractNioChannel, boolean bl) {
        abstractNioChannel.setReadPending0(bl);
    }

    static ChannelPromise access$200(AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.connectPromise;
    }

    static ChannelPromise access$202(AbstractNioChannel abstractNioChannel, ChannelPromise channelPromise) {
        abstractNioChannel.connectPromise = channelPromise;
        return abstractNioChannel.connectPromise;
    }

    static SocketAddress access$302(AbstractNioChannel abstractNioChannel, SocketAddress socketAddress) {
        abstractNioChannel.requestedRemoteAddress = socketAddress;
        return abstractNioChannel.requestedRemoteAddress;
    }

    static ScheduledFuture access$402(AbstractNioChannel abstractNioChannel, ScheduledFuture scheduledFuture) {
        abstractNioChannel.connectTimeoutFuture = scheduledFuture;
        return abstractNioChannel.connectTimeoutFuture;
    }

    static ScheduledFuture access$400(AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.connectTimeoutFuture;
    }

    static SocketAddress access$300(AbstractNioChannel abstractNioChannel) {
        return abstractNioChannel.requestedRemoteAddress;
    }

    static {
        $assertionsDisabled = !AbstractNioChannel.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
        DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractNioChannel.class, "doClose()");
    }

    protected abstract class AbstractNioUnsafe
    extends AbstractChannel.AbstractUnsafe
    implements NioUnsafe {
        static final boolean $assertionsDisabled = !AbstractNioChannel.class.desiredAssertionStatus();
        final AbstractNioChannel this$0;

        protected AbstractNioUnsafe(AbstractNioChannel abstractNioChannel) {
            this.this$0 = abstractNioChannel;
            super(abstractNioChannel);
        }

        protected final void removeReadOp() {
            SelectionKey selectionKey = this.this$0.selectionKey();
            if (!selectionKey.isValid()) {
                return;
            }
            int n = selectionKey.interestOps();
            if ((n & this.this$0.readInterestOp) != 0) {
                selectionKey.interestOps(n & ~this.this$0.readInterestOp);
            }
        }

        @Override
        public final SelectableChannel ch() {
            return this.this$0.javaChannel();
        }

        @Override
        public final void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            try {
                if (AbstractNioChannel.access$200(this.this$0) != null) {
                    throw new ConnectionPendingException();
                }
                boolean bl = this.this$0.isActive();
                if (this.this$0.doConnect(socketAddress, socketAddress2)) {
                    this.fulfillConnectPromise(channelPromise, bl);
                } else {
                    AbstractNioChannel.access$202(this.this$0, channelPromise);
                    AbstractNioChannel.access$302(this.this$0, socketAddress);
                    int n = this.this$0.config().getConnectTimeoutMillis();
                    if (n > 0) {
                        AbstractNioChannel.access$402(this.this$0, this.this$0.eventLoop().schedule(new Runnable(this, socketAddress){
                            final SocketAddress val$remoteAddress;
                            final AbstractNioUnsafe this$1;
                            {
                                this.this$1 = abstractNioUnsafe;
                                this.val$remoteAddress = socketAddress;
                            }

                            @Override
                            public void run() {
                                ChannelPromise channelPromise = AbstractNioChannel.access$200(this.this$1.this$0);
                                ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + this.val$remoteAddress);
                                if (channelPromise != null && channelPromise.tryFailure(connectTimeoutException)) {
                                    this.this$1.close(this.this$1.voidPromise());
                                }
                            }
                        }, (long)n, TimeUnit.MILLISECONDS));
                    }
                    channelPromise.addListener(new ChannelFutureListener(this){
                        final AbstractNioUnsafe this$1;
                        {
                            this.this$1 = abstractNioUnsafe;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isCancelled()) {
                                if (AbstractNioChannel.access$400(this.this$1.this$0) != null) {
                                    AbstractNioChannel.access$400(this.this$1.this$0).cancel(false);
                                }
                                AbstractNioChannel.access$202(this.this$1.this$0, null);
                                this.this$1.close(this.this$1.voidPromise());
                            }
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
            } catch (Throwable throwable) {
                channelPromise.tryFailure(this.annotateConnectException(throwable, socketAddress));
                this.closeIfClosed();
            }
        }

        private void fulfillConnectPromise(ChannelPromise channelPromise, boolean bl) {
            if (channelPromise == null) {
                return;
            }
            boolean bl2 = this.this$0.isActive();
            boolean bl3 = channelPromise.trySuccess();
            if (!bl && bl2) {
                this.this$0.pipeline().fireChannelActive();
            }
            if (!bl3) {
                this.close(this.voidPromise());
            }
        }

        private void fulfillConnectPromise(ChannelPromise channelPromise, Throwable throwable) {
            if (channelPromise == null) {
                return;
            }
            channelPromise.tryFailure(throwable);
            this.closeIfClosed();
        }

        @Override
        public final void finishConnect() {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            try {
                boolean bl = this.this$0.isActive();
                this.this$0.doFinishConnect();
                this.fulfillConnectPromise(AbstractNioChannel.access$200(this.this$0), bl);
            } catch (Throwable throwable) {
                this.fulfillConnectPromise(AbstractNioChannel.access$200(this.this$0), this.annotateConnectException(throwable, AbstractNioChannel.access$300(this.this$0)));
            } finally {
                if (AbstractNioChannel.access$400(this.this$0) != null) {
                    AbstractNioChannel.access$400(this.this$0).cancel(false);
                }
                AbstractNioChannel.access$202(this.this$0, null);
            }
        }

        @Override
        protected final void flush0() {
            if (!this.isFlushPending()) {
                super.flush0();
            }
        }

        @Override
        public final void forceFlush() {
            super.flush0();
        }

        private boolean isFlushPending() {
            SelectionKey selectionKey = this.this$0.selectionKey();
            return selectionKey.isValid() && (selectionKey.interestOps() & 4) != 0;
        }
    }

    public static interface NioUnsafe
    extends Channel.Unsafe {
        public SelectableChannel ch();

        public void finishConnect();

        public void read();

        public void forceFlush();
    }
}

