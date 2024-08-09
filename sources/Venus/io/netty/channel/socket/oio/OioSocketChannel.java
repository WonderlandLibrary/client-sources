/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.socket.oio.DefaultOioSocketChannelConfig;
import io.netty.channel.socket.oio.OioSocketChannelConfig;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OioSocketChannel
extends OioByteStreamChannel
implements SocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
    private final Socket socket;
    private final OioSocketChannelConfig config;

    public OioSocketChannel() {
        this(new Socket());
    }

    public OioSocketChannel(Socket socket) {
        this(null, socket);
    }

    public OioSocketChannel(Channel channel, Socket socket) {
        super(channel);
        this.socket = socket;
        this.config = new DefaultOioSocketChannelConfig(this, socket);
        boolean bl = false;
        try {
            if (socket.isConnected()) {
                this.activate(socket.getInputStream(), socket.getOutputStream());
            }
            socket.setSoTimeout(1000);
            bl = true;
        } catch (Exception exception) {
            throw new ChannelException("failed to initialize a socket", exception);
        } finally {
            if (!bl) {
                try {
                    socket.close();
                } catch (IOException iOException) {
                    logger.warn("Failed to close a socket.", iOException);
                }
            }
        }
    }

    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }

    @Override
    public OioSocketChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    @Override
    public boolean isActive() {
        return !this.socket.isClosed() && this.socket.isConnected();
    }

    @Override
    public boolean isOutputShutdown() {
        return this.socket.isOutputShutdown() || !this.isActive();
    }

    @Override
    public boolean isInputShutdown() {
        return this.socket.isInputShutdown() || !this.isActive();
    }

    @Override
    public boolean isShutdown() {
        return this.socket.isInputShutdown() && this.socket.isOutputShutdown() || !this.isActive();
    }

    @Override
    protected final void doShutdownOutput() throws Exception {
        this.shutdownOutput0();
    }

    @Override
    public ChannelFuture shutdownOutput() {
        return this.shutdownOutput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdownInput() {
        return this.shutdownInput(this.newPromise());
    }

    @Override
    public ChannelFuture shutdown() {
        return this.shutdown(this.newPromise());
    }

    @Override
    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        if (this.socket.isClosed()) {
            return 1;
        }
        try {
            return super.doReadBytes(byteBuf);
        } catch (SocketTimeoutException socketTimeoutException) {
            return 1;
        }
    }

    @Override
    public ChannelFuture shutdownOutput(ChannelPromise channelPromise) {
        EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.shutdownOutput0(channelPromise);
        } else {
            eventLoop.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final OioSocketChannel this$0;
                {
                    this.this$0 = oioSocketChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    OioSocketChannel.access$000(this.this$0, this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    private void shutdownOutput0(ChannelPromise channelPromise) {
        try {
            this.shutdownOutput0();
            channelPromise.setSuccess();
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
    }

    private void shutdownOutput0() throws IOException {
        this.socket.shutdownOutput();
    }

    @Override
    public ChannelFuture shutdownInput(ChannelPromise channelPromise) {
        EventLoop eventLoop = this.eventLoop();
        if (eventLoop.inEventLoop()) {
            this.shutdownInput0(channelPromise);
        } else {
            eventLoop.execute(new Runnable(this, channelPromise){
                final ChannelPromise val$promise;
                final OioSocketChannel this$0;
                {
                    this.this$0 = oioSocketChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    OioSocketChannel.access$100(this.this$0, this.val$promise);
                }
            });
        }
        return channelPromise;
    }

    private void shutdownInput0(ChannelPromise channelPromise) {
        try {
            this.socket.shutdownInput();
            channelPromise.setSuccess();
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
    }

    @Override
    public ChannelFuture shutdown(ChannelPromise channelPromise) {
        ChannelFuture channelFuture = this.shutdownOutput();
        if (channelFuture.isDone()) {
            this.shutdownOutputDone(channelFuture, channelPromise);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this, channelPromise){
                final ChannelPromise val$promise;
                final OioSocketChannel this$0;
                {
                    this.this$0 = oioSocketChannel;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    OioSocketChannel.access$200(this.this$0, channelFuture, this.val$promise);
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
            OioSocketChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
        } else {
            channelFuture2.addListener(new ChannelFutureListener(this, channelFuture, channelPromise){
                final ChannelFuture val$shutdownOutputFuture;
                final ChannelPromise val$promise;
                final OioSocketChannel this$0;
                {
                    this.this$0 = oioSocketChannel;
                    this.val$shutdownOutputFuture = channelFuture;
                    this.val$promise = channelPromise;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    OioSocketChannel.access$300(this.val$shutdownOutputFuture, channelFuture, this.val$promise);
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

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.socket.getLocalSocketAddress();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.socket.getRemoteSocketAddress();
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        SocketUtils.bind(this.socket, socketAddress);
    }

    @Override
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            SocketUtils.bind(this.socket, socketAddress2);
        }
        boolean bl = false;
        try {
            SocketUtils.connect(this.socket, socketAddress, this.config().getConnectTimeoutMillis());
            this.activate(this.socket.getInputStream(), this.socket.getOutputStream());
            bl = true;
        } catch (SocketTimeoutException socketTimeoutException) {
            ConnectTimeoutException connectTimeoutException = new ConnectTimeoutException("connection timed out: " + socketAddress);
            connectTimeoutException.setStackTrace(socketTimeoutException.getStackTrace());
            throw connectTimeoutException;
        } finally {
            if (!bl) {
                this.doClose();
            }
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected void doClose() throws Exception {
        this.socket.close();
    }

    protected boolean checkInputShutdown() {
        if (this.isInputShutdown()) {
            try {
                Thread.sleep(this.config().getSoTimeout());
            } catch (Throwable throwable) {
                // empty catch block
            }
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    protected void setReadPending(boolean bl) {
        super.setReadPending(bl);
    }

    final void clearReadPending0() {
        this.clearReadPending();
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

    @Override
    public SocketChannelConfig config() {
        return this.config();
    }

    static void access$000(OioSocketChannel oioSocketChannel, ChannelPromise channelPromise) {
        oioSocketChannel.shutdownOutput0(channelPromise);
    }

    static void access$100(OioSocketChannel oioSocketChannel, ChannelPromise channelPromise) {
        oioSocketChannel.shutdownInput0(channelPromise);
    }

    static void access$200(OioSocketChannel oioSocketChannel, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        oioSocketChannel.shutdownOutputDone(channelFuture, channelPromise);
    }

    static void access$300(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        OioSocketChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
    }
}

