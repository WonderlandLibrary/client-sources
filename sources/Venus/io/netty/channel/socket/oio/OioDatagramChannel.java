/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.oio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.oio.AbstractOioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.DefaultOioDatagramChannelConfig;
import io.netty.channel.socket.oio.OioDatagramChannelConfig;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.NotYetConnectedException;
import java.util.List;
import java.util.Locale;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OioDatagramChannel
extends AbstractOioMessageChannel
implements DatagramChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    private final MulticastSocket socket;
    private final OioDatagramChannelConfig config;
    private final java.net.DatagramPacket tmpPacket = new java.net.DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);

    private static MulticastSocket newSocket() {
        try {
            return new MulticastSocket(null);
        } catch (Exception exception) {
            throw new ChannelException("failed to create a new socket", exception);
        }
    }

    public OioDatagramChannel() {
        this(OioDatagramChannel.newSocket());
    }

    public OioDatagramChannel(MulticastSocket multicastSocket) {
        super(null);
        boolean bl = false;
        try {
            multicastSocket.setSoTimeout(1000);
            multicastSocket.setBroadcast(true);
            bl = true;
        } catch (SocketException socketException) {
            throw new ChannelException("Failed to configure the datagram socket timeout.", socketException);
        } finally {
            if (!bl) {
                multicastSocket.close();
            }
        }
        this.socket = multicastSocket;
        this.config = new DefaultOioDatagramChannelConfig(this, multicastSocket);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public DatagramChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    @Override
    public boolean isActive() {
        return this.isOpen() && (this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) != false && this.isRegistered() || this.socket.isBound());
    }

    @Override
    public boolean isConnected() {
        return this.socket.isConnected();
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
        this.socket.bind(socketAddress);
    }

    @Override
    public InetSocketAddress localAddress() {
        return (InetSocketAddress)super.localAddress();
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            this.socket.bind(socketAddress2);
        }
        boolean bl = false;
        try {
            this.socket.connect(socketAddress);
            bl = true;
        } finally {
            if (!bl) {
                try {
                    this.socket.close();
                } catch (Throwable throwable) {
                    logger.warn("Failed to close a socket.", throwable);
                }
            }
        }
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.socket.disconnect();
    }

    @Override
    protected void doClose() throws Exception {
        this.socket.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        DatagramChannelConfig datagramChannelConfig = this.config();
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        ByteBuf byteBuf = datagramChannelConfig.getAllocator().heapBuffer(handle.guess());
        boolean bl = true;
        try {
            this.tmpPacket.setAddress(null);
            this.tmpPacket.setData(byteBuf.array(), byteBuf.arrayOffset(), byteBuf.capacity());
            this.socket.receive(this.tmpPacket);
            InetSocketAddress inetSocketAddress = (InetSocketAddress)this.tmpPacket.getSocketAddress();
            handle.lastBytesRead(this.tmpPacket.getLength());
            list.add(new DatagramPacket(byteBuf.writerIndex(handle.lastBytesRead()), this.localAddress(), inetSocketAddress));
            bl = false;
            int n = 1;
            return n;
        } catch (SocketTimeoutException socketTimeoutException) {
            int n = 0;
            return n;
        } catch (SocketException socketException) {
            if (!socketException.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
                throw socketException;
            }
            int n = -1;
            return n;
        } catch (Throwable throwable) {
            PlatformDependent.throwException(throwable);
            int n = -1;
            return n;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object;
        while ((object = channelOutboundBuffer.current()) != null) {
            ByteBuf byteBuf;
            SocketAddress socketAddress;
            if (object instanceof AddressedEnvelope) {
                AddressedEnvelope addressedEnvelope = (AddressedEnvelope)object;
                socketAddress = (SocketAddress)addressedEnvelope.recipient();
                byteBuf = (ByteBuf)addressedEnvelope.content();
            } else {
                byteBuf = (ByteBuf)object;
                socketAddress = null;
            }
            int n = byteBuf.readableBytes();
            try {
                if (socketAddress != null) {
                    this.tmpPacket.setSocketAddress(socketAddress);
                } else {
                    if (!this.isConnected()) {
                        throw new NotYetConnectedException();
                    }
                    this.tmpPacket.setAddress(null);
                }
                if (byteBuf.hasArray()) {
                    this.tmpPacket.setData(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), n);
                } else {
                    byte[] byArray = new byte[n];
                    byteBuf.getBytes(byteBuf.readerIndex(), byArray);
                    this.tmpPacket.setData(byArray);
                }
                this.socket.send(this.tmpPacket);
                channelOutboundBuffer.remove();
            } catch (Exception exception) {
                channelOutboundBuffer.remove(exception);
            }
        }
    }

    @Override
    protected Object filterOutboundMessage(Object object) {
        AddressedEnvelope addressedEnvelope;
        if (object instanceof DatagramPacket || object instanceof ByteBuf) {
            return object;
        }
        if (object instanceof AddressedEnvelope && (addressedEnvelope = (AddressedEnvelope)object).content() instanceof ByteBuf) {
            return object;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return this.joinGroup(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        this.ensureBound();
        try {
            this.socket.joinGroup(inetAddress);
            channelPromise.setSuccess();
        } catch (IOException iOException) {
            channelPromise.setFailure(iOException);
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return this.joinGroup(inetSocketAddress, networkInterface, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        this.ensureBound();
        try {
            this.socket.joinGroup(inetSocketAddress, networkInterface);
            channelPromise.setSuccess();
        } catch (IOException iOException) {
            channelPromise.setFailure(iOException);
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }

    private void ensureBound() {
        if (!this.isActive()) {
            throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
        }
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress) {
        return this.leaveGroup(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            this.socket.leaveGroup(inetAddress);
            channelPromise.setSuccess();
        } catch (IOException iOException) {
            channelPromise.setFailure(iOException);
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return this.leaveGroup(inetSocketAddress, networkInterface, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        try {
            this.socket.leaveGroup(inetSocketAddress, networkInterface);
            channelPromise.setSuccess();
        } catch (IOException iOException) {
            channelPromise.setFailure(iOException);
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2) {
        return this.newFailedFuture(new UnsupportedOperationException());
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise) {
        channelPromise.setFailure(new UnsupportedOperationException());
        return channelPromise;
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

