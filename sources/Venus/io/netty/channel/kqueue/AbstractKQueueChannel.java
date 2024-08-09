/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueEventLoop;
import io.netty.channel.kqueue.KQueueRecvByteAllocatorHandle;
import io.netty.channel.kqueue.Native;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.UnixChannel;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.UnresolvedAddressException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AbstractKQueueChannel
extends AbstractChannel
implements UnixChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    final BsdSocket socket;
    private boolean readFilterEnabled = true;
    private boolean writeFilterEnabled;
    boolean readReadyRunnablePending;
    boolean inputClosedSeenErrorOnRead;
    long jniSelfPtr;
    protected volatile boolean active;
    private volatile SocketAddress local;
    private volatile SocketAddress remote;

    AbstractKQueueChannel(Channel channel, BsdSocket bsdSocket, boolean bl) {
        super(channel);
        this.socket = ObjectUtil.checkNotNull(bsdSocket, "fd");
        this.active = bl;
        if (bl) {
            this.local = bsdSocket.localAddress();
            this.remote = bsdSocket.remoteAddress();
        }
    }

    AbstractKQueueChannel(Channel channel, BsdSocket bsdSocket, SocketAddress socketAddress) {
        super(channel);
        this.socket = ObjectUtil.checkNotNull(bsdSocket, "fd");
        this.active = true;
        this.remote = socketAddress;
        this.local = bsdSocket.localAddress();
    }

    static boolean isSoErrorZero(BsdSocket bsdSocket) {
        try {
            return bsdSocket.getSoError() == 0;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    public final FileDescriptor fd() {
        return this.socket;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    protected void doClose() throws Exception {
        this.active = false;
        this.inputClosedSeenErrorOnRead = true;
        try {
            if (this.isRegistered()) {
                EventLoop eventLoop = this.eventLoop();
                if (eventLoop.inEventLoop()) {
                    this.doDeregister();
                } else {
                    eventLoop.execute(new Runnable(this){
                        final AbstractKQueueChannel this$0;
                        {
                            this.this$0 = abstractKQueueChannel;
                        }

                        @Override
                        public void run() {
                            try {
                                this.this$0.doDeregister();
                            } catch (Throwable throwable) {
                                this.this$0.pipeline().fireExceptionCaught(throwable);
                            }
                        }
                    });
                }
            }
        } finally {
            this.socket.close();
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof KQueueEventLoop;
    }

    @Override
    public boolean isOpen() {
        return this.socket.isOpen();
    }

    @Override
    protected void doDeregister() throws Exception {
        this.readFilter(true);
        this.writeFilter(true);
        this.evSet0(Native.EVFILT_SOCK, Native.EV_DELETE, 0);
        ((KQueueEventLoop)this.eventLoop()).remove(this);
        this.readFilterEnabled = true;
    }

    @Override
    protected final void doBeginRead() throws Exception {
        AbstractKQueueUnsafe abstractKQueueUnsafe = (AbstractKQueueUnsafe)this.unsafe();
        abstractKQueueUnsafe.readPending = true;
        this.readFilter(false);
        if (abstractKQueueUnsafe.maybeMoreDataToRead) {
            abstractKQueueUnsafe.executeReadReadyRunnable(this.config());
        }
    }

    @Override
    protected void doRegister() throws Exception {
        this.readReadyRunnablePending = false;
        if (this.writeFilterEnabled) {
            this.evSet0(Native.EVFILT_WRITE, Native.EV_ADD_CLEAR_ENABLE);
        }
        if (this.readFilterEnabled) {
            this.evSet0(Native.EVFILT_READ, Native.EV_ADD_CLEAR_ENABLE);
        }
        this.evSet0(Native.EVFILT_SOCK, Native.EV_ADD, Native.NOTE_RDHUP);
    }

    @Override
    protected abstract AbstractKQueueUnsafe newUnsafe();

    @Override
    public abstract KQueueChannelConfig config();

    protected final ByteBuf newDirectBuffer(ByteBuf byteBuf) {
        return this.newDirectBuffer(byteBuf, byteBuf);
    }

    protected final ByteBuf newDirectBuffer(Object object, ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (n == 0) {
            ReferenceCountUtil.release(object);
            return Unpooled.EMPTY_BUFFER;
        }
        ByteBufAllocator byteBufAllocator = this.alloc();
        if (byteBufAllocator.isDirectBufferPooled()) {
            return AbstractKQueueChannel.newDirectBuffer0(object, byteBuf, byteBufAllocator, n);
        }
        ByteBuf byteBuf2 = ByteBufUtil.threadLocalDirectBuffer();
        if (byteBuf2 == null) {
            return AbstractKQueueChannel.newDirectBuffer0(object, byteBuf, byteBufAllocator, n);
        }
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), n);
        ReferenceCountUtil.safeRelease(object);
        return byteBuf2;
    }

    private static ByteBuf newDirectBuffer0(Object object, ByteBuf byteBuf, ByteBufAllocator byteBufAllocator, int n) {
        ByteBuf byteBuf2 = byteBufAllocator.directBuffer(n);
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), n);
        ReferenceCountUtil.safeRelease(object);
        return byteBuf2;
    }

    protected static void checkResolvable(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress.isUnresolved()) {
            throw new UnresolvedAddressException();
        }
    }

    protected final int doReadBytes(ByteBuf byteBuf) throws Exception {
        int n;
        int n2 = byteBuf.writerIndex();
        this.unsafe().recvBufAllocHandle().attemptedBytesRead(byteBuf.writableBytes());
        if (byteBuf.hasMemoryAddress()) {
            n = this.socket.readAddress(byteBuf.memoryAddress(), n2, byteBuf.capacity());
        } else {
            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(n2, byteBuf.writableBytes());
            n = this.socket.read(byteBuffer, byteBuffer.position(), byteBuffer.limit());
        }
        if (n > 0) {
            byteBuf.writerIndex(n2 + n);
        }
        return n;
    }

    protected final int doWriteBytes(ChannelOutboundBuffer channelOutboundBuffer, ByteBuf byteBuf) throws Exception {
        if (byteBuf.hasMemoryAddress()) {
            int n = this.socket.writeAddress(byteBuf.memoryAddress(), byteBuf.readerIndex(), byteBuf.writerIndex());
            if (n > 0) {
                channelOutboundBuffer.removeBytes(n);
                return 0;
            }
        } else {
            ByteBuffer byteBuffer = byteBuf.nioBufferCount() == 1 ? byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes()) : byteBuf.nioBuffer();
            int n = this.socket.write(byteBuffer, byteBuffer.position(), byteBuffer.limit());
            if (n > 0) {
                byteBuffer.position(byteBuffer.position() + n);
                channelOutboundBuffer.removeBytes(n);
                return 0;
            }
        }
        return 0;
    }

    final boolean shouldBreakReadReady(ChannelConfig channelConfig) {
        return this.socket.isInputShutdown() && (this.inputClosedSeenErrorOnRead || !AbstractKQueueChannel.isAllowHalfClosure(channelConfig));
    }

    private static boolean isAllowHalfClosure(ChannelConfig channelConfig) {
        return channelConfig instanceof SocketChannelConfig && ((SocketChannelConfig)channelConfig).isAllowHalfClosure();
    }

    final void clearReadFilter() {
        if (this.isRegistered()) {
            EventLoop eventLoop = this.eventLoop();
            AbstractKQueueUnsafe abstractKQueueUnsafe = (AbstractKQueueUnsafe)this.unsafe();
            if (eventLoop.inEventLoop()) {
                abstractKQueueUnsafe.clearReadFilter0();
            } else {
                eventLoop.execute(new Runnable(this, abstractKQueueUnsafe){
                    final AbstractKQueueUnsafe val$unsafe;
                    final AbstractKQueueChannel this$0;
                    {
                        this.this$0 = abstractKQueueChannel;
                        this.val$unsafe = abstractKQueueUnsafe;
                    }

                    @Override
                    public void run() {
                        if (!this.val$unsafe.readPending && !this.this$0.config().isAutoRead()) {
                            this.val$unsafe.clearReadFilter0();
                        }
                    }
                });
            }
        } else {
            this.readFilterEnabled = false;
        }
    }

    void readFilter(boolean bl) throws IOException {
        if (this.readFilterEnabled != bl) {
            this.readFilterEnabled = bl;
            this.evSet(Native.EVFILT_READ, bl ? Native.EV_ADD_CLEAR_ENABLE : Native.EV_DELETE_DISABLE);
        }
    }

    void writeFilter(boolean bl) throws IOException {
        if (this.writeFilterEnabled != bl) {
            this.writeFilterEnabled = bl;
            this.evSet(Native.EVFILT_WRITE, bl ? Native.EV_ADD_CLEAR_ENABLE : Native.EV_DELETE_DISABLE);
        }
    }

    private void evSet(short s, short s2) {
        if (this.isOpen() && this.isRegistered()) {
            this.evSet0(s, s2);
        }
    }

    private void evSet0(short s, short s2) {
        this.evSet0(s, s2, 0);
    }

    private void evSet0(short s, short s2, int n) {
        ((KQueueEventLoop)this.eventLoop()).evSet(this, s, s2, n);
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        if (socketAddress instanceof InetSocketAddress) {
            AbstractKQueueChannel.checkResolvable((InetSocketAddress)socketAddress);
        }
        this.socket.bind(socketAddress);
        this.local = this.socket.localAddress();
    }

    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        boolean bl;
        InetSocketAddress inetSocketAddress;
        if (socketAddress2 instanceof InetSocketAddress) {
            AbstractKQueueChannel.checkResolvable((InetSocketAddress)socketAddress2);
        }
        InetSocketAddress inetSocketAddress2 = inetSocketAddress = socketAddress instanceof InetSocketAddress ? (InetSocketAddress)socketAddress : null;
        if (inetSocketAddress != null) {
            AbstractKQueueChannel.checkResolvable(inetSocketAddress);
        }
        if (this.remote != null) {
            throw new AlreadyConnectedException();
        }
        if (socketAddress2 != null) {
            this.socket.bind(socketAddress2);
        }
        if (bl = this.doConnect0(socketAddress)) {
            this.remote = inetSocketAddress == null ? socketAddress : UnixChannelUtil.computeRemoteAddr(inetSocketAddress, this.socket.remoteAddress());
        }
        this.local = this.socket.localAddress();
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean doConnect0(SocketAddress socketAddress) throws Exception {
        boolean bl = false;
        try {
            boolean bl2 = this.socket.connect(socketAddress);
            if (!bl2) {
                this.writeFilter(false);
            }
            bl = true;
            boolean bl3 = bl2;
            return bl3;
        } finally {
            if (!bl) {
                this.doClose();
            }
        }
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.local;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.remote;
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    @Override
    public ChannelConfig config() {
        return this.config();
    }

    static ChannelPromise access$000(AbstractKQueueChannel abstractKQueueChannel) {
        return abstractKQueueChannel.connectPromise;
    }

    static ChannelPromise access$002(AbstractKQueueChannel abstractKQueueChannel, ChannelPromise channelPromise) {
        abstractKQueueChannel.connectPromise = channelPromise;
        return abstractKQueueChannel.connectPromise;
    }

    static boolean access$100(ChannelConfig channelConfig) {
        return AbstractKQueueChannel.isAllowHalfClosure(channelConfig);
    }

    static boolean access$200(AbstractKQueueChannel abstractKQueueChannel) {
        return abstractKQueueChannel.writeFilterEnabled;
    }

    static SocketAddress access$302(AbstractKQueueChannel abstractKQueueChannel, SocketAddress socketAddress) {
        abstractKQueueChannel.requestedRemoteAddress = socketAddress;
        return abstractKQueueChannel.requestedRemoteAddress;
    }

    static ScheduledFuture access$402(AbstractKQueueChannel abstractKQueueChannel, ScheduledFuture scheduledFuture) {
        abstractKQueueChannel.connectTimeoutFuture = scheduledFuture;
        return abstractKQueueChannel.connectTimeoutFuture;
    }

    static ScheduledFuture access$400(AbstractKQueueChannel abstractKQueueChannel) {
        return abstractKQueueChannel.connectTimeoutFuture;
    }

    static SocketAddress access$300(AbstractKQueueChannel abstractKQueueChannel) {
        return abstractKQueueChannel.requestedRemoteAddress;
    }

    static SocketAddress access$502(AbstractKQueueChannel abstractKQueueChannel, SocketAddress socketAddress) {
        abstractKQueueChannel.remote = socketAddress;
        return abstractKQueueChannel.remote;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    abstract class AbstractKQueueUnsafe
    extends AbstractChannel.AbstractUnsafe {
        boolean readPending;
        boolean maybeMoreDataToRead;
        private KQueueRecvByteAllocatorHandle allocHandle;
        private final Runnable readReadyRunnable;
        static final boolean $assertionsDisabled = !AbstractKQueueChannel.class.desiredAssertionStatus();
        final AbstractKQueueChannel this$0;

        AbstractKQueueUnsafe(AbstractKQueueChannel abstractKQueueChannel) {
            this.this$0 = abstractKQueueChannel;
            super(abstractKQueueChannel);
            this.readReadyRunnable = new Runnable(this){
                final AbstractKQueueUnsafe this$1;
                {
                    this.this$1 = abstractKQueueUnsafe;
                }

                @Override
                public void run() {
                    this.this$1.this$0.readReadyRunnablePending = false;
                    this.this$1.readReady(this.this$1.recvBufAllocHandle());
                }
            };
        }

        final void readReady(long l) {
            KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle = this.recvBufAllocHandle();
            kQueueRecvByteAllocatorHandle.numberBytesPending(l);
            this.readReady(kQueueRecvByteAllocatorHandle);
        }

        abstract void readReady(KQueueRecvByteAllocatorHandle var1);

        final void readReadyBefore() {
            this.maybeMoreDataToRead = false;
        }

        final void readReadyFinally(ChannelConfig channelConfig) {
            this.maybeMoreDataToRead = this.allocHandle.maybeMoreDataToRead();
            if (!this.readPending && !channelConfig.isAutoRead()) {
                this.clearReadFilter0();
            } else if (this.readPending && this.maybeMoreDataToRead) {
                this.executeReadReadyRunnable(channelConfig);
            }
        }

        final boolean failConnectPromise(Throwable throwable) {
            if (AbstractKQueueChannel.access$000(this.this$0) != null) {
                ChannelPromise channelPromise = AbstractKQueueChannel.access$000(this.this$0);
                AbstractKQueueChannel.access$002(this.this$0, null);
                if (channelPromise.tryFailure(throwable instanceof ConnectException ? throwable : new ConnectException("failed to connect").initCause(throwable))) {
                    this.closeIfClosed();
                    return false;
                }
            }
            return true;
        }

        final void writeReady() {
            if (AbstractKQueueChannel.access$000(this.this$0) != null) {
                this.finishConnect();
            } else if (!this.this$0.socket.isOutputShutdown()) {
                super.flush0();
            }
        }

        void shutdownInput(boolean bl) {
            if (bl && AbstractKQueueChannel.access$000(this.this$0) != null) {
                this.finishConnect();
            }
            if (!this.this$0.socket.isInputShutdown()) {
                if (AbstractKQueueChannel.access$100(this.this$0.config())) {
                    try {
                        this.this$0.socket.shutdown(true, true);
                    } catch (IOException iOException) {
                        this.fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    } catch (NotYetConnectedException notYetConnectedException) {
                        // empty catch block
                    }
                    this.this$0.pipeline().fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                } else {
                    this.close(this.voidPromise());
                }
            } else if (!bl) {
                this.this$0.inputClosedSeenErrorOnRead = true;
                this.this$0.pipeline().fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
            }
        }

        final void readEOF() {
            KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle = this.recvBufAllocHandle();
            kQueueRecvByteAllocatorHandle.readEOF();
            if (this.this$0.isActive()) {
                this.readReady(kQueueRecvByteAllocatorHandle);
            } else {
                this.shutdownInput(false);
            }
        }

        @Override
        public KQueueRecvByteAllocatorHandle recvBufAllocHandle() {
            if (this.allocHandle == null) {
                this.allocHandle = new KQueueRecvByteAllocatorHandle((RecvByteBufAllocator.ExtendedHandle)super.recvBufAllocHandle());
            }
            return this.allocHandle;
        }

        @Override
        protected final void flush0() {
            if (!AbstractKQueueChannel.access$200(this.this$0)) {
                super.flush0();
            }
        }

        final void executeReadReadyRunnable(ChannelConfig channelConfig) {
            if (this.this$0.readReadyRunnablePending || !this.this$0.isActive() || this.this$0.shouldBreakReadReady(channelConfig)) {
                return;
            }
            this.this$0.readReadyRunnablePending = true;
            this.this$0.eventLoop().execute(this.readReadyRunnable);
        }

        protected final void clearReadFilter0() {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            try {
                this.readPending = false;
                this.this$0.readFilter(true);
            } catch (IOException iOException) {
                this.this$0.pipeline().fireExceptionCaught(iOException);
                this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
            }
        }

        private void fireEventAndClose(Object object) {
            this.this$0.pipeline().fireUserEventTriggered(object);
            this.close(this.voidPromise());
        }

        @Override
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            try {
                if (AbstractKQueueChannel.access$000(this.this$0) != null) {
                    throw new ConnectionPendingException();
                }
                boolean bl = this.this$0.isActive();
                if (this.this$0.doConnect(socketAddress, socketAddress2)) {
                    this.fulfillConnectPromise(channelPromise, bl);
                } else {
                    AbstractKQueueChannel.access$002(this.this$0, channelPromise);
                    AbstractKQueueChannel.access$302(this.this$0, socketAddress);
                    int n = this.this$0.config().getConnectTimeoutMillis();
                    if (n > 0) {
                        AbstractKQueueChannel.access$402(this.this$0, this.this$0.eventLoop().schedule(new Runnable(this, socketAddress){
                            final SocketAddress val$remoteAddress;
                            final AbstractKQueueUnsafe this$1;
                            {
                                this.this$1 = abstractKQueueUnsafe;
                                this.val$remoteAddress = socketAddress;
                            }

                            @Override
                            public void run() {
                                ChannelPromise channelPromise = AbstractKQueueChannel.access$000(this.this$1.this$0);
                                ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + this.val$remoteAddress);
                                if (channelPromise != null && channelPromise.tryFailure(connectTimeoutException)) {
                                    this.this$1.close(this.this$1.voidPromise());
                                }
                            }
                        }, (long)n, TimeUnit.MILLISECONDS));
                    }
                    channelPromise.addListener(new ChannelFutureListener(this){
                        final AbstractKQueueUnsafe this$1;
                        {
                            this.this$1 = abstractKQueueUnsafe;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isCancelled()) {
                                if (AbstractKQueueChannel.access$400(this.this$1.this$0) != null) {
                                    AbstractKQueueChannel.access$400(this.this$1.this$0).cancel(false);
                                }
                                AbstractKQueueChannel.access$002(this.this$1.this$0, null);
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
                this.closeIfClosed();
                channelPromise.tryFailure(this.annotateConnectException(throwable, socketAddress));
            }
        }

        private void fulfillConnectPromise(ChannelPromise channelPromise, boolean bl) {
            if (channelPromise == null) {
                return;
            }
            this.this$0.active = true;
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

        private void finishConnect() {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            boolean bl = false;
            try {
                boolean bl2 = this.this$0.isActive();
                if (!this.doFinishConnect()) {
                    bl = true;
                    return;
                }
                this.fulfillConnectPromise(AbstractKQueueChannel.access$000(this.this$0), bl2);
            } catch (Throwable throwable) {
                this.fulfillConnectPromise(AbstractKQueueChannel.access$000(this.this$0), this.annotateConnectException(throwable, AbstractKQueueChannel.access$300(this.this$0)));
            } finally {
                if (!bl) {
                    if (AbstractKQueueChannel.access$400(this.this$0) != null) {
                        AbstractKQueueChannel.access$400(this.this$0).cancel(false);
                    }
                    AbstractKQueueChannel.access$002(this.this$0, null);
                }
            }
        }

        private boolean doFinishConnect() throws Exception {
            if (this.this$0.socket.finishConnect()) {
                this.this$0.writeFilter(true);
                if (AbstractKQueueChannel.access$300(this.this$0) instanceof InetSocketAddress) {
                    AbstractKQueueChannel.access$502(this.this$0, UnixChannelUtil.computeRemoteAddr((InetSocketAddress)AbstractKQueueChannel.access$300(this.this$0), this.this$0.socket.remoteAddress()));
                }
                AbstractKQueueChannel.access$302(this.this$0, null);
                return false;
            }
            this.this$0.writeFilter(false);
            return true;
        }

        @Override
        public RecvByteBufAllocator.Handle recvBufAllocHandle() {
            return this.recvBufAllocHandle();
        }
    }
}

