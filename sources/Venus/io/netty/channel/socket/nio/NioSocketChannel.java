/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NioSocketChannel
extends AbstractNioByteChannel
implements SocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioSocketChannel.class);
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private final SocketChannelConfig config;

    private static java.nio.channels.SocketChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openSocketChannel();
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a socket.", iOException);
        }
    }

    public NioSocketChannel() {
        this(DEFAULT_SELECTOR_PROVIDER);
    }

    public NioSocketChannel(SelectorProvider selectorProvider) {
        this(NioSocketChannel.newSocket(selectorProvider));
    }

    public NioSocketChannel(java.nio.channels.SocketChannel socketChannel) {
        this(null, socketChannel);
    }

    public NioSocketChannel(Channel channel, java.nio.channels.SocketChannel socketChannel) {
        super(channel, socketChannel);
        this.config = new NioSocketChannelConfig(this, this, socketChannel.socket(), null);
    }

    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }

    @Override
    public SocketChannelConfig config() {
        return this.config;
    }

    @Override
    protected java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel)super.javaChannel();
    }

    @Override
    public boolean isActive() {
        java.nio.channels.SocketChannel socketChannel = this.javaChannel();
        return socketChannel.isOpen() && socketChannel.isConnected();
    }

    @Override
    public boolean isOutputShutdown() {
        return this.javaChannel().socket().isOutputShutdown() || !this.isActive();
    }

    @Override
    public boolean isInputShutdown() {
        return this.javaChannel().socket().isInputShutdown() || !this.isActive();
    }

    @Override
    public boolean isShutdown() {
        Socket socket = this.javaChannel().socket();
        return socket.isInputShutdown() && socket.isOutputShutdown() || !this.isActive();
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }

    @Override
    protected final void doShutdownOutput() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            this.javaChannel().shutdownOutput();
        } else {
            this.javaChannel().socket().shutdownOutput();
        }
    }

    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdownOutput(ChannelPromise channelPromise) {
        NioEventLoop nioEventLoop = this.eventLoop();
        if (nioEventLoop.inEventLoop()) {
            ((AbstractChannel.AbstractUnsafe)((Object)this.unsafe())).shutdownOutput(channelPromise);
        } else {
            nioEventLoop.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final NioSocketChannel this$0;
                {
                    this.this$0 = nioSocketChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    ((AbstractChannel.AbstractUnsafe)((Object)this.this$0.unsafe())).shutdownOutput(this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture shutdownInput() {
        return this.shutdownInput(this.newPromise());
    }

    @Override
    protected boolean isInputShutdown0() {
        return this.isInputShutdown();
    }

    @Override
    public ChannelFuture shutdownInput(ChannelPromise channelPromise) {
        NioEventLoop nioEventLoop = this.eventLoop();
        if (nioEventLoop.inEventLoop()) {
            this.shutdownInput0(channelPromise);
        } else {
            nioEventLoop.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final NioSocketChannel this$0;
                {
                    this.this$0 = nioSocketChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    NioSocketChannel.access$100(this.this$0, this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture shutdown() {
        return this.shutdown(this.newPromise());
    }

    @Override
    public ChannelFuture shutdown(ChannelPromise channelPromise) {
        ChannelFuture channelFuture = this.shutdownOutput();
        if (channelFuture.isDone()) {
            this.shutdownOutputDone(channelFuture, channelPromise);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this, channelPromise){
                final ChannelPromise val$promise;
                final NioSocketChannel this$0;
                {
                    this.this$0 = nioSocketChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    NioSocketChannel.access$200(this.this$0, channelFuture, this.val$promise);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        return channelPromise;
    }

    private void shutdownOutputDone(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        ChannelFuture channelFuture2 = this.shutdownInput();
        if (channelFuture2.isDone()) {
            NioSocketChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
        } else {
            channelFuture2.addListener(new ChannelFutureListener(this, channelFuture, channelPromise){
                final ChannelFuture val$shutdownOutputFuture;
                final ChannelPromise val$promise;
                final NioSocketChannel this$0;
                {
                    this.this$0 = nioSocketChannel;
                    this.val$shutdownOutputFuture = channelFuture;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    NioSocketChannel.access$300(this.val$shutdownOutputFuture, channelFuture, this.val$promise);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
    }

    private static void shutdownDone(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        Throwable throwable = channelFuture.cause();
        Throwable throwable2 = channelFuture2.cause();
        if (throwable != null) {
            if (throwable2 != null) {
                logger.debug("Exception suppressed because a previous exception occurred.", throwable2);
            }
            channelPromise.setFailure(throwable);
        } else if (throwable2 != null) {
            channelPromise.setFailure(throwable2);
        } else {
            channelPromise.setSuccess();
        }
    }

    private void shutdownInput0(ChannelPromise channelPromise) {
        try {
            this.shutdownInput0();
            channelPromise.setSuccess();
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
    }

    private void shutdownInput0() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            this.javaChannel().shutdownInput();
        } else {
            this.javaChannel().socket().shutdownInput();
        }
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.doBind0(socketAddress);
    }

    private void doBind0(SocketAddress socketAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            SocketUtils.bind(this.javaChannel(), socketAddress);
        } else {
            SocketUtils.bind(this.javaChannel().socket(), socketAddress);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.doBind0(socketAddress2);
        }
        boolean bl = false;
        try {
            boolean bl2 = SocketUtils.connect(this.javaChannel(), socketAddress);
            if (!bl2) {
                this.selectionKey().interestOps(8);
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
    protected void doFinishConnect() throws Exception {
        if (!this.javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected void doClose() throws Exception {
        super.doClose();
        this.javaChannel().close();
    }

    @Override
    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        handle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes(this.javaChannel(), handle.attemptedBytesRead());
    }

    @Override
    protected int doWriteBytes(ByteBuf byteBuf) throws Exception {
        int n = byteBuf.readableBytes();
        return byteBuf.readBytes(this.javaChannel(), n);
    }

    @Override
    protected long doWriteFileRegion(FileRegion fileRegion) throws Exception {
        long l = fileRegion.transferred();
        return fileRegion.transferTo(this.javaChannel(), l);
    }

    private void adjustMaxBytesPerGatheringWrite(int n, int n2, int n3) {
        if (n == n2) {
            if (n << 1 > n3) {
                ((NioSocketChannelConfig)this.config).setMaxBytesPerGatheringWrite(n << 1);
            }
        } else if (n > 4096 && n2 < n >>> 1) {
            ((NioSocketChannelConfig)this.config).setMaxBytesPerGatheringWrite(n >>> 1);
        }
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        java.nio.channels.SocketChannel socketChannel = this.javaChannel();
        int n = this.config().getWriteSpinCount();
        do {
            if (channelOutboundBuffer.isEmpty()) {
                this.clearOpWrite();
                return;
            }
            int n2 = ((NioSocketChannelConfig)this.config).getMaxBytesPerGatheringWrite();
            ByteBuffer[] byteBufferArray = channelOutboundBuffer.nioBuffers(1024, n2);
            int n3 = channelOutboundBuffer.nioBufferCount();
            switch (n3) {
                case 0: {
                    n -= this.doWrite0(channelOutboundBuffer);
                    break;
                }
                case 1: {
                    ByteBuffer byteBuffer = byteBufferArray[0];
                    int n4 = byteBuffer.remaining();
                    int n5 = socketChannel.write(byteBuffer);
                    if (n5 <= 0) {
                        this.incompleteWrite(false);
                        return;
                    }
                    this.adjustMaxBytesPerGatheringWrite(n4, n5, n2);
                    channelOutboundBuffer.removeBytes(n5);
                    --n;
                    break;
                }
                default: {
                    long l = channelOutboundBuffer.nioBufferSize();
                    long l2 = socketChannel.write(byteBufferArray, 0, n3);
                    if (l2 <= 0L) {
                        this.incompleteWrite(false);
                        return;
                    }
                    this.adjustMaxBytesPerGatheringWrite((int)l, (int)l2, n2);
                    channelOutboundBuffer.removeBytes(l2);
                    --n;
                    break;
                }
            }
        } while (n > 0);
        this.incompleteWrite(n < 0);
    }

    @Override
    protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioSocketChannelUnsafe(this, null);
    }

    @Override
    protected SelectableChannel javaChannel() {
        return this.javaChannel();
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
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

    @Override
    public ChannelConfig config() {
        return this.config();
    }

    static void access$100(NioSocketChannel nioSocketChannel, ChannelPromise channelPromise) {
        nioSocketChannel.shutdownInput0(channelPromise);
    }

    static void access$200(NioSocketChannel nioSocketChannel, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        nioSocketChannel.shutdownOutputDone(channelFuture, channelPromise);
    }

    static void access$300(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        NioSocketChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
    }

    static void access$500(NioSocketChannel nioSocketChannel) throws Exception {
        nioSocketChannel.doDeregister();
    }

    static void access$600(NioSocketChannel nioSocketChannel) {
        nioSocketChannel.clearReadPending();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class NioSocketChannelConfig
    extends DefaultSocketChannelConfig {
        private volatile int maxBytesPerGatheringWrite;
        final NioSocketChannel this$0;

        private NioSocketChannelConfig(NioSocketChannel nioSocketChannel, NioSocketChannel nioSocketChannel2, Socket socket) {
            this.this$0 = nioSocketChannel;
            super(nioSocketChannel2, socket);
            this.maxBytesPerGatheringWrite = Integer.MAX_VALUE;
            this.calculateMaxBytesPerGatheringWrite();
        }

        @Override
        protected void autoReadCleared() {
            NioSocketChannel.access$600(this.this$0);
        }

        @Override
        public NioSocketChannelConfig setSendBufferSize(int n) {
            super.setSendBufferSize(n);
            this.calculateMaxBytesPerGatheringWrite();
            return this;
        }

        void setMaxBytesPerGatheringWrite(int n) {
            this.maxBytesPerGatheringWrite = n;
        }

        int getMaxBytesPerGatheringWrite() {
            return this.maxBytesPerGatheringWrite;
        }

        private void calculateMaxBytesPerGatheringWrite() {
            int n = this.getSendBufferSize() << 1;
            if (n > 0) {
                this.setMaxBytesPerGatheringWrite(this.getSendBufferSize() << 1);
            }
        }

        @Override
        public SocketChannelConfig setSendBufferSize(int n) {
            return this.setSendBufferSize(n);
        }

        NioSocketChannelConfig(NioSocketChannel nioSocketChannel, NioSocketChannel nioSocketChannel2, Socket socket, 1 var4_4) {
            this(nioSocketChannel, nioSocketChannel2, socket);
        }
    }

    private final class NioSocketChannelUnsafe
    extends AbstractNioByteChannel.NioByteUnsafe {
        final NioSocketChannel this$0;

        private NioSocketChannelUnsafe(NioSocketChannel nioSocketChannel) {
            this.this$0 = nioSocketChannel;
            super(nioSocketChannel);
        }

        @Override
        protected Executor prepareToClose() {
            try {
                if (this.this$0.javaChannel().isOpen() && this.this$0.config().getSoLinger() > 0) {
                    NioSocketChannel.access$500(this.this$0);
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }

        NioSocketChannelUnsafe(NioSocketChannel nioSocketChannel, 1 var2_2) {
            this(nioSocketChannel);
        }
    }
}

