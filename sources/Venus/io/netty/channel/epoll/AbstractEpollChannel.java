/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

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
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollRecvByteAllocatorHandle;
import io.netty.channel.epoll.LinuxSocket;
import io.netty.channel.epoll.Native;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.channel.unix.UnixChannel;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.UnresolvedAddressException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AbstractEpollChannel
extends AbstractChannel
implements UnixChannel {
    private static final ClosedChannelException DO_CLOSE_CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractEpollChannel.class, "doClose()");
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private final int readFlag;
    final LinuxSocket socket;
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    private volatile SocketAddress local;
    private volatile SocketAddress remote;
    protected int flags = Native.EPOLLET;
    boolean inputClosedSeenErrorOnRead;
    boolean epollInReadyRunnablePending;
    protected volatile boolean active;

    AbstractEpollChannel(LinuxSocket linuxSocket, int n) {
        this(null, linuxSocket, n, false);
    }

    AbstractEpollChannel(Channel channel, LinuxSocket linuxSocket, int n, boolean bl) {
        super(channel);
        this.socket = ObjectUtil.checkNotNull(linuxSocket, "fd");
        this.readFlag = n;
        this.flags |= n;
        this.active = bl;
        if (bl) {
            this.local = linuxSocket.localAddress();
            this.remote = linuxSocket.remoteAddress();
        }
    }

    AbstractEpollChannel(Channel channel, LinuxSocket linuxSocket, int n, SocketAddress socketAddress) {
        super(channel);
        this.socket = ObjectUtil.checkNotNull(linuxSocket, "fd");
        this.readFlag = n;
        this.flags |= n;
        this.active = true;
        this.remote = socketAddress;
        this.local = linuxSocket.localAddress();
    }

    static boolean isSoErrorZero(Socket socket) {
        try {
            return socket.getSoError() == 0;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    void setFlag(int n) throws IOException {
        if (!this.isFlagSet(n)) {
            this.flags |= n;
            this.modifyEvents();
        }
    }

    void clearFlag(int n) throws IOException {
        if (this.isFlagSet(n)) {
            this.flags &= ~n;
            this.modifyEvents();
        }
    }

    boolean isFlagSet(int n) {
        return (this.flags & n) != 0;
    }

    @Override
    public final FileDescriptor fd() {
        return this.socket;
    }

    @Override
    public abstract EpollChannelConfig config();

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doClose() throws Exception {
        this.active = false;
        this.inputClosedSeenErrorOnRead = true;
        try {
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
            if (this.isRegistered()) {
                EventLoop eventLoop = this.eventLoop();
                if (eventLoop.inEventLoop()) {
                    this.doDeregister();
                } else {
                    eventLoop.execute(new Runnable(this){
                        final AbstractEpollChannel this$0;
                        {
                            this.this$0 = abstractEpollChannel;
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
        return eventLoop instanceof EpollEventLoop;
    }

    @Override
    public boolean isOpen() {
        return this.socket.isOpen();
    }

    @Override
    protected void doDeregister() throws Exception {
        ((EpollEventLoop)this.eventLoop()).remove(this);
    }

    @Override
    protected final void doBeginRead() throws Exception {
        AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollUnsafe)this.unsafe();
        abstractEpollUnsafe.readPending = true;
        this.setFlag(this.readFlag);
        if (abstractEpollUnsafe.maybeMoreDataToRead) {
            abstractEpollUnsafe.executeEpollInReadyRunnable(this.config());
        }
    }

    final boolean shouldBreakEpollInReady(ChannelConfig channelConfig) {
        return this.socket.isInputShutdown() && (this.inputClosedSeenErrorOnRead || !AbstractEpollChannel.isAllowHalfClosure(channelConfig));
    }

    private static boolean isAllowHalfClosure(ChannelConfig channelConfig) {
        return channelConfig instanceof SocketChannelConfig && ((SocketChannelConfig)channelConfig).isAllowHalfClosure();
    }

    final void clearEpollIn() {
        if (this.isRegistered()) {
            EventLoop eventLoop = this.eventLoop();
            AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollUnsafe)this.unsafe();
            if (eventLoop.inEventLoop()) {
                abstractEpollUnsafe.clearEpollIn0();
            } else {
                eventLoop.execute(new Runnable(this, abstractEpollUnsafe){
                    final AbstractEpollUnsafe val$unsafe;
                    final AbstractEpollChannel this$0;
                    {
                        this.this$0 = abstractEpollChannel;
                        this.val$unsafe = abstractEpollUnsafe;
                    }

                    @Override
                    public void run() {
                        if (!this.val$unsafe.readPending && !this.this$0.config().isAutoRead()) {
                            this.val$unsafe.clearEpollIn0();
                        }
                    }
                });
            }
        } else {
            this.flags &= ~this.readFlag;
        }
    }

    private void modifyEvents() throws IOException {
        if (this.isOpen() && this.isRegistered()) {
            ((EpollEventLoop)this.eventLoop()).modify(this);
        }
    }

    @Override
    protected void doRegister() throws Exception {
        this.epollInReadyRunnablePending = false;
        ((EpollEventLoop)this.eventLoop()).add(this);
    }

    @Override
    protected abstract AbstractEpollUnsafe newUnsafe();

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
            return AbstractEpollChannel.newDirectBuffer0(object, byteBuf, byteBufAllocator, n);
        }
        ByteBuf byteBuf2 = ByteBufUtil.threadLocalDirectBuffer();
        if (byteBuf2 == null) {
            return AbstractEpollChannel.newDirectBuffer0(object, byteBuf, byteBufAllocator, n);
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

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        if (socketAddress instanceof InetSocketAddress) {
            AbstractEpollChannel.checkResolvable((InetSocketAddress)socketAddress);
        }
        this.socket.bind(socketAddress);
        this.local = this.socket.localAddress();
    }

    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        boolean bl;
        InetSocketAddress inetSocketAddress;
        if (socketAddress2 instanceof InetSocketAddress) {
            AbstractEpollChannel.checkResolvable((InetSocketAddress)socketAddress2);
        }
        InetSocketAddress inetSocketAddress2 = inetSocketAddress = socketAddress instanceof InetSocketAddress ? (InetSocketAddress)socketAddress : null;
        if (inetSocketAddress != null) {
            AbstractEpollChannel.checkResolvable(inetSocketAddress);
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
                this.setFlag(Native.EPOLLOUT);
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

    static boolean access$000(ChannelConfig channelConfig) {
        return AbstractEpollChannel.isAllowHalfClosure(channelConfig);
    }

    static ChannelPromise access$100(AbstractEpollChannel abstractEpollChannel) {
        return abstractEpollChannel.connectPromise;
    }

    static int access$200(AbstractEpollChannel abstractEpollChannel) {
        return abstractEpollChannel.readFlag;
    }

    static ChannelPromise access$102(AbstractEpollChannel abstractEpollChannel, ChannelPromise channelPromise) {
        abstractEpollChannel.connectPromise = channelPromise;
        return abstractEpollChannel.connectPromise;
    }

    static SocketAddress access$302(AbstractEpollChannel abstractEpollChannel, SocketAddress socketAddress) {
        abstractEpollChannel.requestedRemoteAddress = socketAddress;
        return abstractEpollChannel.requestedRemoteAddress;
    }

    static ScheduledFuture access$402(AbstractEpollChannel abstractEpollChannel, ScheduledFuture scheduledFuture) {
        abstractEpollChannel.connectTimeoutFuture = scheduledFuture;
        return abstractEpollChannel.connectTimeoutFuture;
    }

    static ScheduledFuture access$400(AbstractEpollChannel abstractEpollChannel) {
        return abstractEpollChannel.connectTimeoutFuture;
    }

    static SocketAddress access$300(AbstractEpollChannel abstractEpollChannel) {
        return abstractEpollChannel.requestedRemoteAddress;
    }

    static SocketAddress access$502(AbstractEpollChannel abstractEpollChannel, SocketAddress socketAddress) {
        abstractEpollChannel.remote = socketAddress;
        return abstractEpollChannel.remote;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected abstract class AbstractEpollUnsafe
    extends AbstractChannel.AbstractUnsafe {
        boolean readPending;
        boolean maybeMoreDataToRead;
        private EpollRecvByteAllocatorHandle allocHandle;
        private final Runnable epollInReadyRunnable;
        static final boolean $assertionsDisabled = !AbstractEpollChannel.class.desiredAssertionStatus();
        final AbstractEpollChannel this$0;

        protected AbstractEpollUnsafe(AbstractEpollChannel abstractEpollChannel) {
            this.this$0 = abstractEpollChannel;
            super(abstractEpollChannel);
            this.epollInReadyRunnable = new Runnable(this){
                final AbstractEpollUnsafe this$1;
                {
                    this.this$1 = abstractEpollUnsafe;
                }

                @Override
                public void run() {
                    this.this$1.this$0.epollInReadyRunnablePending = false;
                    this.this$1.epollInReady();
                }
            };
        }

        abstract void epollInReady();

        final void epollInBefore() {
            this.maybeMoreDataToRead = false;
        }

        final void epollInFinally(ChannelConfig channelConfig) {
            boolean bl = this.maybeMoreDataToRead = this.allocHandle.isEdgeTriggered() && this.allocHandle.maybeMoreDataToRead();
            if (!this.readPending && !channelConfig.isAutoRead()) {
                this.this$0.clearEpollIn();
            } else if (this.readPending && this.maybeMoreDataToRead) {
                this.executeEpollInReadyRunnable(channelConfig);
            }
        }

        final void executeEpollInReadyRunnable(ChannelConfig channelConfig) {
            if (this.this$0.epollInReadyRunnablePending || !this.this$0.isActive() || this.this$0.shouldBreakEpollInReady(channelConfig)) {
                return;
            }
            this.this$0.epollInReadyRunnablePending = true;
            this.this$0.eventLoop().execute(this.epollInReadyRunnable);
        }

        final void epollRdHupReady() {
            this.recvBufAllocHandle().receivedRdHup();
            if (this.this$0.isActive()) {
                this.epollInReady();
            } else {
                this.shutdownInput(false);
            }
            this.clearEpollRdHup();
        }

        private void clearEpollRdHup() {
            try {
                this.this$0.clearFlag(Native.EPOLLRDHUP);
            } catch (IOException iOException) {
                this.this$0.pipeline().fireExceptionCaught(iOException);
                this.close(this.voidPromise());
            }
        }

        void shutdownInput(boolean bl) {
            if (!this.this$0.socket.isInputShutdown()) {
                if (AbstractEpollChannel.access$000(this.this$0.config())) {
                    try {
                        this.this$0.socket.shutdown(true, true);
                    } catch (IOException iOException) {
                        this.fireEventAndClose(ChannelInputShutdownEvent.INSTANCE);
                        return;
                    } catch (NotYetConnectedException notYetConnectedException) {
                        // empty catch block
                    }
                    this.this$0.clearEpollIn();
                    this.this$0.pipeline().fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
                } else {
                    this.close(this.voidPromise());
                }
            } else if (!bl) {
                this.this$0.inputClosedSeenErrorOnRead = true;
                this.this$0.pipeline().fireUserEventTriggered(ChannelInputShutdownReadComplete.INSTANCE);
            }
        }

        private void fireEventAndClose(Object object) {
            this.this$0.pipeline().fireUserEventTriggered(object);
            this.close(this.voidPromise());
        }

        @Override
        public EpollRecvByteAllocatorHandle recvBufAllocHandle() {
            if (this.allocHandle == null) {
                this.allocHandle = this.newEpollHandle((RecvByteBufAllocator.ExtendedHandle)super.recvBufAllocHandle());
            }
            return this.allocHandle;
        }

        EpollRecvByteAllocatorHandle newEpollHandle(RecvByteBufAllocator.ExtendedHandle extendedHandle) {
            return new EpollRecvByteAllocatorHandle(extendedHandle);
        }

        @Override
        protected final void flush0() {
            if (!this.this$0.isFlagSet(Native.EPOLLOUT)) {
                super.flush0();
            }
        }

        final void epollOutReady() {
            if (AbstractEpollChannel.access$100(this.this$0) != null) {
                this.finishConnect();
            } else if (!this.this$0.socket.isOutputShutdown()) {
                super.flush0();
            }
        }

        protected final void clearEpollIn0() {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            try {
                this.readPending = false;
                this.this$0.clearFlag(AbstractEpollChannel.access$200(this.this$0));
            } catch (IOException iOException) {
                this.this$0.pipeline().fireExceptionCaught(iOException);
                this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
            }
        }

        @Override
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            try {
                if (AbstractEpollChannel.access$100(this.this$0) != null) {
                    throw new ConnectionPendingException();
                }
                boolean bl = this.this$0.isActive();
                if (this.this$0.doConnect(socketAddress, socketAddress2)) {
                    this.fulfillConnectPromise(channelPromise, bl);
                } else {
                    AbstractEpollChannel.access$102(this.this$0, channelPromise);
                    AbstractEpollChannel.access$302(this.this$0, socketAddress);
                    int n = this.this$0.config().getConnectTimeoutMillis();
                    if (n > 0) {
                        AbstractEpollChannel.access$402(this.this$0, this.this$0.eventLoop().schedule(new Runnable(this, socketAddress){
                            final SocketAddress val$remoteAddress;
                            final AbstractEpollUnsafe this$1;
                            {
                                this.this$1 = abstractEpollUnsafe;
                                this.val$remoteAddress = socketAddress;
                            }

                            @Override
                            public void run() {
                                ChannelPromise channelPromise = AbstractEpollChannel.access$100(this.this$1.this$0);
                                ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + this.val$remoteAddress);
                                if (channelPromise != null && channelPromise.tryFailure(connectTimeoutException)) {
                                    this.this$1.close(this.this$1.voidPromise());
                                }
                            }
                        }, (long)n, TimeUnit.MILLISECONDS));
                    }
                    channelPromise.addListener(new ChannelFutureListener(this){
                        final AbstractEpollUnsafe this$1;
                        {
                            this.this$1 = abstractEpollUnsafe;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isCancelled()) {
                                if (AbstractEpollChannel.access$400(this.this$1.this$0) != null) {
                                    AbstractEpollChannel.access$400(this.this$1.this$0).cancel(false);
                                }
                                AbstractEpollChannel.access$102(this.this$1.this$0, null);
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
                this.fulfillConnectPromise(AbstractEpollChannel.access$100(this.this$0), bl2);
            } catch (Throwable throwable) {
                this.fulfillConnectPromise(AbstractEpollChannel.access$100(this.this$0), this.annotateConnectException(throwable, AbstractEpollChannel.access$300(this.this$0)));
            } finally {
                if (!bl) {
                    if (AbstractEpollChannel.access$400(this.this$0) != null) {
                        AbstractEpollChannel.access$400(this.this$0).cancel(false);
                    }
                    AbstractEpollChannel.access$102(this.this$0, null);
                }
            }
        }

        private boolean doFinishConnect() throws Exception {
            if (this.this$0.socket.finishConnect()) {
                this.this$0.clearFlag(Native.EPOLLOUT);
                if (AbstractEpollChannel.access$300(this.this$0) instanceof InetSocketAddress) {
                    AbstractEpollChannel.access$502(this.this$0, UnixChannelUtil.computeRemoteAddr((InetSocketAddress)AbstractEpollChannel.access$300(this.this$0), this.this$0.socket.remoteAddress()));
                }
                AbstractEpollChannel.access$302(this.this$0, null);
                return false;
            }
            this.this$0.setFlag(Native.EPOLLOUT);
            return true;
        }

        @Override
        public RecvByteBufAllocator.Handle recvBufAllocHandle() {
            return this.recvBufAllocHandle();
        }
    }
}

