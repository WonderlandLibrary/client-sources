/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.barchart.udt.TypeUDT
 *  com.barchart.udt.nio.ChannelUDT
 *  com.barchart.udt.nio.ServerSocketChannelUDT
 *  com.barchart.udt.nio.SocketChannelUDT
 */
package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ChannelUDT;
import com.barchart.udt.nio.ServerSocketChannelUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtServerChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtServerChannel;
import io.netty.channel.udt.UdtServerChannelConfig;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public abstract class NioUdtAcceptorChannel
extends AbstractNioMessageChannel
implements UdtServerChannel {
    protected static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtAcceptorChannel.class);
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private final UdtServerChannelConfig config;

    protected NioUdtAcceptorChannel(ServerSocketChannelUDT serverSocketChannelUDT) {
        super(null, (SelectableChannel)serverSocketChannelUDT, 16);
        try {
            serverSocketChannelUDT.configureBlocking(true);
            this.config = new DefaultUdtServerChannelConfig(this, (ChannelUDT)serverSocketChannelUDT, true);
        } catch (Exception exception) {
            block4: {
                try {
                    serverSocketChannelUDT.close();
                } catch (Exception exception2) {
                    if (!logger.isWarnEnabled()) break block4;
                    logger.warn("Failed to close channel.", exception2);
                }
            }
            throw new ChannelException("Failed to configure channel.", exception);
        }
    }

    protected NioUdtAcceptorChannel(TypeUDT typeUDT) {
        this(NioUdtProvider.newAcceptorChannelUDT(typeUDT));
    }

    @Override
    public UdtServerChannelConfig config() {
        return this.config;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.javaChannel().socket().bind(socketAddress, this.config.getBacklog());
    }

    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }

    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doFinishConnect() throws Exception {
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
    public boolean isActive() {
        return this.javaChannel().socket().isBound();
    }

    protected ServerSocketChannelUDT javaChannel() {
        return (ServerSocketChannelUDT)super.javaChannel();
    }

    @Override
    protected SocketAddress localAddress0() {
        return SocketUtils.localSocketAddress((ServerSocket)this.javaChannel().socket());
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return null;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return null;
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        SocketChannelUDT socketChannelUDT = (SocketChannelUDT)SocketUtils.accept((ServerSocketChannel)this.javaChannel());
        if (socketChannelUDT == null) {
            return 1;
        }
        list.add(this.newConnectorChannel(socketChannelUDT));
        return 0;
    }

    protected abstract UdtChannel newConnectorChannel(SocketChannelUDT var1);

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

    @Override
    public UdtChannelConfig config() {
        return this.config();
    }
}

