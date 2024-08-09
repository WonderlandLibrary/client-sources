/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.nio;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DefaultServerSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NioServerSocketChannel
extends AbstractNioMessageChannel
implements ServerSocketChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
    private final ServerSocketChannelConfig config = new NioServerSocketChannelConfig(this, this, this.javaChannel().socket(), null);

    private static java.nio.channels.ServerSocketChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openServerSocketChannel();
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a server socket.", iOException);
        }
    }

    public NioServerSocketChannel() {
        this(NioServerSocketChannel.newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioServerSocketChannel(SelectorProvider selectorProvider) {
        this(NioServerSocketChannel.newSocket(selectorProvider));
    }

    public NioServerSocketChannel(java.nio.channels.ServerSocketChannel serverSocketChannel) {
        super(null, serverSocketChannel, 16);
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
    public ServerSocketChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isActive() {
        return this.javaChannel().socket().isBound();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override
    protected java.nio.channels.ServerSocketChannel javaChannel() {
        return (java.nio.channels.ServerSocketChannel)super.javaChannel();
    }

    @Override
    protected SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress(this.javaChannel().socket());
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            this.javaChannel().bind(socketAddress, this.config.getBacklog());
        } else {
            this.javaChannel().socket().bind(socketAddress, this.config.getBacklog());
        }
    }

    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }

    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        SocketChannel socketChannel = SocketUtils.accept(this.javaChannel());
        try {
            if (socketChannel != null) {
                list.add(new NioSocketChannel((Channel)this, socketChannel));
                return 1;
            }
        } catch (Throwable throwable) {
            logger.warn("Failed to create a new channel from an accepted socket.", throwable);
            try {
                socketChannel.close();
            } catch (Throwable throwable2) {
                logger.warn("Failed to close a socket.", throwable2);
            }
        }
        return 1;
    }

    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doFinishConnect() throws Exception {
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
    protected boolean doWriteMessage(Object object, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final Object filterOutboundMessage(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean closeOnReadError(Throwable throwable) {
        return super.closeOnReadError(throwable);
    }

    @Override
    protected SelectableChannel javaChannel() {
        return this.javaChannel();
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

    static void access$100(NioServerSocketChannel nioServerSocketChannel) {
        nioServerSocketChannel.clearReadPending();
    }

    private final class NioServerSocketChannelConfig
    extends DefaultServerSocketChannelConfig {
        final NioServerSocketChannel this$0;

        private NioServerSocketChannelConfig(NioServerSocketChannel nioServerSocketChannel, NioServerSocketChannel nioServerSocketChannel2, ServerSocket serverSocket) {
            this.this$0 = nioServerSocketChannel;
            super(nioServerSocketChannel2, serverSocket);
        }

        @Override
        protected void autoReadCleared() {
            NioServerSocketChannel.access$100(this.this$0);
        }

        NioServerSocketChannelConfig(NioServerSocketChannel nioServerSocketChannel, NioServerSocketChannel nioServerSocketChannel2, ServerSocket serverSocket, 1 var4_4) {
            this(nioServerSocketChannel, nioServerSocketChannel2, serverSocket);
        }
    }
}

