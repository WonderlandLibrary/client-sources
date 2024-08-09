/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.barchart.udt.TypeUDT
 *  com.barchart.udt.nio.ChannelUDT
 *  com.barchart.udt.nio.SocketChannelUDT
 */
package io.netty.channel.udt.nio;

import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ChannelUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.udt.DefaultUdtChannelConfig;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtChannelConfig;
import io.netty.channel.udt.UdtMessage;
import io.netty.channel.udt.nio.NioUdtProvider;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class NioUdtMessageConnectorChannel
extends AbstractNioMessageChannel
implements UdtChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtMessageConnectorChannel.class);
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private final UdtChannelConfig config;

    public NioUdtMessageConnectorChannel() {
        this(TypeUDT.DATAGRAM);
    }

    public NioUdtMessageConnectorChannel(Channel channel, SocketChannelUDT socketChannelUDT) {
        super(channel, (SelectableChannel)socketChannelUDT, 1);
        try {
            socketChannelUDT.configureBlocking(true);
            switch (2.$SwitchMap$com$barchart$udt$StatusUDT[socketChannelUDT.socketUDT().status().ordinal()]) {
                case 1: 
                case 2: {
                    this.config = new DefaultUdtChannelConfig(this, (ChannelUDT)socketChannelUDT, true);
                    break;
                }
                default: {
                    this.config = new DefaultUdtChannelConfig(this, (ChannelUDT)socketChannelUDT, false);
                    break;
                }
            }
        } catch (Exception exception) {
            block7: {
                try {
                    socketChannelUDT.close();
                } catch (Exception exception2) {
                    if (!logger.isWarnEnabled()) break block7;
                    logger.warn("Failed to close channel.", exception2);
                }
            }
            throw new ChannelException("Failed to configure channel.", exception);
        }
    }

    public NioUdtMessageConnectorChannel(SocketChannelUDT socketChannelUDT) {
        this(null, socketChannelUDT);
    }

    public NioUdtMessageConnectorChannel(TypeUDT typeUDT) {
        this(NioUdtProvider.newConnectorChannelUDT(typeUDT));
    }

    @Override
    public UdtChannelConfig config() {
        return this.config;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        NioUdtMessageConnectorChannel.privilegedBind(this.javaChannel(), socketAddress);
    }

    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        this.doBind(socketAddress2 != null ? socketAddress2 : new InetSocketAddress(0));
        boolean bl = false;
        try {
            boolean bl2 = SocketUtils.connect((SocketChannel)this.javaChannel(), socketAddress);
            if (!bl2) {
                this.selectionKey().interestOps(this.selectionKey().interestOps() | 8);
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
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected void doFinishConnect() throws Exception {
        if (!this.javaChannel().finishConnect()) {
            throw new Error("Provider error: failed to finish connect. Provider library should be upgraded.");
        }
        this.selectionKey().interestOps(this.selectionKey().interestOps() & 0xFFFFFFF7);
    }

    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        int n = this.config.getReceiveBufferSize();
        ByteBuf byteBuf = this.config.getAllocator().directBuffer(n);
        int n2 = byteBuf.writeBytes((ScatteringByteChannel)this.javaChannel(), n);
        if (n2 <= 0) {
            byteBuf.release();
            return 1;
        }
        if (n2 >= n) {
            this.javaChannel().close();
            throw new ChannelException("Invalid config : increase receive buffer size to avoid message truncation");
        }
        list.add(new UdtMessage(byteBuf));
        return 0;
    }

    @Override
    protected boolean doWriteMessage(Object object, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        UdtMessage udtMessage = (UdtMessage)object;
        ByteBuf byteBuf = udtMessage.content();
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return false;
        }
        long l = byteBuf.nioBufferCount() == 1 ? (long)this.javaChannel().write(byteBuf.nioBuffer()) : this.javaChannel().write(byteBuf.nioBuffers());
        if (l > 0L && l != (long)n) {
            throw new Error("Provider error: failed to write message. Provider library should be upgraded.");
        }
        return l > 0L;
    }

    @Override
    public boolean isActive() {
        SocketChannelUDT socketChannelUDT = this.javaChannel();
        return socketChannelUDT.isOpen() && socketChannelUDT.isConnectFinished();
    }

    protected SocketChannelUDT javaChannel() {
        return (SocketChannelUDT)super.javaChannel();
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.javaChannel().socket().getLocalSocketAddress();
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.javaChannel().socket().getRemoteSocketAddress();
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }

    private static void privilegedBind(SocketChannelUDT socketChannelUDT, SocketAddress socketAddress) throws IOException {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>(socketChannelUDT, socketAddress){
                final SocketChannelUDT val$socketChannel;
                final SocketAddress val$localAddress;
                {
                    this.val$socketChannel = socketChannelUDT;
                    this.val$localAddress = socketAddress;
                }

                @Override
                public Void run() throws IOException {
                    this.val$socketChannel.bind(this.val$localAddress);
                    return null;
                }

                @Override
                public Object run() throws Exception {
                    return this.run();
                }
            });
        } catch (PrivilegedActionException privilegedActionException) {
            throw (IOException)privilegedActionException.getCause();
        }
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
}

