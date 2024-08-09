/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.oio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.socket.oio.DefaultOioServerSocketChannelConfig;
import io.netty.channel.socket.oio.OioServerSocketChannelConfig;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OioServerSocketChannel
extends AbstractOioMessageChannel
implements ServerSocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioServerSocketChannel.class);
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 1);
    final ServerSocket socket;
    final Lock shutdownLock = new ReentrantLock();
    private final OioServerSocketChannelConfig config;

    private static ServerSocket newServerSocket() {
        try {
            return new ServerSocket();
        } catch (IOException iOException) {
            throw new ChannelException("failed to create a server socket", iOException);
        }
    }

    public OioServerSocketChannel() {
        this(OioServerSocketChannel.newServerSocket());
    }

    public OioServerSocketChannel(ServerSocket serverSocket) {
        super(null);
        if (serverSocket == null) {
            throw new NullPointerException("socket");
        }
        boolean bl = false;
        try {
            serverSocket.setSoTimeout(1000);
            bl = true;
        } catch (IOException iOException) {
            throw new ChannelException("Failed to set the server socket timeout.", iOException);
        } finally {
            block12: {
                if (!bl) {
                    try {
                        serverSocket.close();
                    } catch (IOException iOException) {
                        if (!logger.isWarnEnabled()) break block12;
                        logger.warn("Failed to close a partially initialized socket.", iOException);
                    }
                }
            }
        }
        this.socket = serverSocket;
        this.config = new DefaultOioServerSocketChannelConfig(this, serverSocket);
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public OioServerSocketChannelConfig config() {
        return this.config;
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    @Override
    public boolean isActive() {
        return this.isOpen() && this.socket.isBound();
    }

    @Override
    protected SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress(this.socket);
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.socket.bind(socketAddress, this.config.getBacklog());
    }

    @Override
    protected void doClose() throws Exception {
        this.socket.close();
    }

    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        if (this.socket.isClosed()) {
            return 1;
        }
        try {
            Socket socket = this.socket.accept();
            try {
                list.add(new OioSocketChannel((Channel)this, socket));
                return 1;
            } catch (Throwable throwable) {
                logger.warn("Failed to create a new channel from an accepted socket.", throwable);
                try {
                    socket.close();
                } catch (Throwable throwable2) {
                    logger.warn("Failed to close a socket.", throwable2);
                }
            }
        } catch (SocketTimeoutException socketTimeoutException) {
            // empty catch block
        }
        return 1;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object filterOutboundMessage(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }

    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    protected void setReadPending(boolean bl) {
        super.setReadPending(bl);
    }

    final void clearReadPending0() {
        super.clearReadPending();
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
    public ChannelConfig config() {
        return this.config();
    }

    @Override
    public ServerSocketChannelConfig config() {
        return this.config();
    }
}

