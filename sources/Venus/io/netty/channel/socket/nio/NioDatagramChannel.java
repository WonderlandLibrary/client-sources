/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioMessageChannel;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.channel.socket.nio.NioDatagramChannelConfig;
import io.netty.channel.socket.nio.ProtocolFamilyConverter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.MembershipKey;
import java.nio.channels.SelectableChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class NioDatagramChannel
extends AbstractNioMessageChannel
implements DatagramChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(SocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    private final DatagramChannelConfig config;
    private Map<InetAddress, List<MembershipKey>> memberships;

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openDatagramChannel();
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a socket.", iOException);
        }
    }

    private static java.nio.channels.DatagramChannel newSocket(SelectorProvider selectorProvider, InternetProtocolFamily internetProtocolFamily) {
        if (internetProtocolFamily == null) {
            return NioDatagramChannel.newSocket(selectorProvider);
        }
        NioDatagramChannel.checkJavaVersion();
        try {
            return selectorProvider.openDatagramChannel(ProtocolFamilyConverter.convert(internetProtocolFamily));
        } catch (IOException iOException) {
            throw new ChannelException("Failed to open a socket.", iOException);
        }
    }

    private static void checkJavaVersion() {
        if (PlatformDependent.javaVersion() < 7) {
            throw new UnsupportedOperationException("Only supported on java 7+.");
        }
    }

    public NioDatagramChannel() {
        this(NioDatagramChannel.newSocket(DEFAULT_SELECTOR_PROVIDER));
    }

    public NioDatagramChannel(SelectorProvider selectorProvider) {
        this(NioDatagramChannel.newSocket(selectorProvider));
    }

    public NioDatagramChannel(InternetProtocolFamily internetProtocolFamily) {
        this(NioDatagramChannel.newSocket(DEFAULT_SELECTOR_PROVIDER, internetProtocolFamily));
    }

    public NioDatagramChannel(SelectorProvider selectorProvider, InternetProtocolFamily internetProtocolFamily) {
        this(NioDatagramChannel.newSocket(selectorProvider, internetProtocolFamily));
    }

    public NioDatagramChannel(java.nio.channels.DatagramChannel datagramChannel) {
        super(null, datagramChannel, 1);
        this.config = new NioDatagramChannelConfig(this, datagramChannel);
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
    public boolean isActive() {
        java.nio.channels.DatagramChannel datagramChannel = this.javaChannel();
        return datagramChannel.isOpen() && (this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION) != false && this.isRegistered() || datagramChannel.socket().isBound());
    }

    @Override
    public boolean isConnected() {
        return this.javaChannel().isConnected();
    }

    @Override
    protected java.nio.channels.DatagramChannel javaChannel() {
        return (java.nio.channels.DatagramChannel)super.javaChannel();
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
            this.javaChannel().socket().bind(socketAddress);
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
            this.javaChannel().connect(socketAddress);
            bl = true;
            boolean bl2 = true;
            return bl2;
        } finally {
            if (!bl) {
                this.doClose();
            }
        }
    }

    @Override
    protected void doFinishConnect() throws Exception {
        throw new Error();
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.javaChannel().disconnect();
    }

    @Override
    protected void doClose() throws Exception {
        this.javaChannel().close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected int doReadMessages(List<Object> list) throws Exception {
        int n;
        java.nio.channels.DatagramChannel datagramChannel = this.javaChannel();
        DatagramChannelConfig datagramChannelConfig = this.config();
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        ByteBuf byteBuf = handle.allocate(datagramChannelConfig.getAllocator());
        handle.attemptedBytesRead(byteBuf.writableBytes());
        boolean bl = true;
        try {
            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(byteBuf.writerIndex(), byteBuf.writableBytes());
            n = byteBuffer.position();
            InetSocketAddress inetSocketAddress = (InetSocketAddress)datagramChannel.receive(byteBuffer);
            if (inetSocketAddress == null) {
                int n2 = 0;
                return n2;
            }
            handle.lastBytesRead(byteBuffer.position() - n);
            list.add(new DatagramPacket(byteBuf.writerIndex(byteBuf.writerIndex() + handle.lastBytesRead()), this.localAddress(), inetSocketAddress));
            bl = false;
            int n3 = 1;
            return n3;
        } catch (Throwable throwable) {
            PlatformDependent.throwException(throwable);
            n = -1;
            return n;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    @Override
    protected boolean doWriteMessage(Object object, ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
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
        if (n == 0) {
            return false;
        }
        ByteBuffer byteBuffer = byteBuf.nioBufferCount() == 1 ? byteBuf.internalNioBuffer(byteBuf.readerIndex(), n) : byteBuf.nioBuffer(byteBuf.readerIndex(), n);
        int n2 = socketAddress != null ? this.javaChannel().send(byteBuffer, socketAddress) : this.javaChannel().write(byteBuffer);
        return n2 > 0;
    }

    @Override
    protected Object filterOutboundMessage(Object object) {
        AddressedEnvelope addressedEnvelope;
        if (object instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket)object;
            ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
            if (NioDatagramChannel.isSingleDirectBuffer(byteBuf)) {
                return datagramPacket;
            }
            return new DatagramPacket(this.newDirectBuffer(datagramPacket, byteBuf), (InetSocketAddress)datagramPacket.recipient());
        }
        if (object instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf)object;
            if (NioDatagramChannel.isSingleDirectBuffer(byteBuf)) {
                return byteBuf;
            }
            return this.newDirectBuffer(byteBuf);
        }
        if (object instanceof AddressedEnvelope && (addressedEnvelope = (AddressedEnvelope)object).content() instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf)addressedEnvelope.content();
            if (NioDatagramChannel.isSingleDirectBuffer(byteBuf)) {
                return addressedEnvelope;
            }
            return new DefaultAddressedEnvelope(this.newDirectBuffer(addressedEnvelope, byteBuf), addressedEnvelope.recipient());
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    private static boolean isSingleDirectBuffer(ByteBuf byteBuf) {
        return byteBuf.isDirect() && byteBuf.nioBufferCount() == 1;
    }

    @Override
    protected boolean continueOnWriteError() {
        return false;
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
    public ChannelFuture joinGroup(InetAddress inetAddress) {
        return this.joinGroup(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return this.joinGroup(inetAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, channelPromise);
        } catch (SocketException socketException) {
            channelPromise.setFailure(socketException);
            return channelPromise;
        }
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return this.joinGroup(inetSocketAddress, networkInterface, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        return this.joinGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.joinGroup(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        NioDatagramChannel.checkJavaVersion();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        try {
            MembershipKey membershipKey = inetAddress2 == null ? this.javaChannel().join(inetAddress, networkInterface) : this.javaChannel().join(inetAddress, networkInterface, inetAddress2);
            NioDatagramChannel nioDatagramChannel = this;
            synchronized (nioDatagramChannel) {
                List<MembershipKey> list = null;
                if (this.memberships == null) {
                    this.memberships = new HashMap<InetAddress, List<MembershipKey>>();
                } else {
                    list = this.memberships.get(inetAddress);
                }
                if (list == null) {
                    list = new ArrayList<MembershipKey>();
                    this.memberships.put(inetAddress, list);
                }
                list.add(membershipKey);
            }
            channelPromise.setSuccess();
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress) {
        return this.leaveGroup(inetAddress, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, ChannelPromise channelPromise) {
        try {
            return this.leaveGroup(inetAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, channelPromise);
        } catch (SocketException socketException) {
            channelPromise.setFailure(socketException);
            return channelPromise;
        }
    }

    @Override
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface) {
        return this.leaveGroup(inetSocketAddress, networkInterface, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetSocketAddress inetSocketAddress, NetworkInterface networkInterface, ChannelPromise channelPromise) {
        return this.leaveGroup(inetSocketAddress.getAddress(), networkInterface, null, channelPromise);
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.leaveGroup(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        NioDatagramChannel.checkJavaVersion();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        NioDatagramChannel nioDatagramChannel = this;
        synchronized (nioDatagramChannel) {
            List<MembershipKey> list;
            if (this.memberships != null && (list = this.memberships.get(inetAddress)) != null) {
                Iterator<MembershipKey> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    MembershipKey membershipKey = iterator2.next();
                    if (!networkInterface.equals(membershipKey.networkInterface()) || (inetAddress2 != null || membershipKey.sourceAddress() != null) && (inetAddress2 == null || !inetAddress2.equals(membershipKey.sourceAddress()))) continue;
                    membershipKey.drop();
                    iterator2.remove();
                }
                if (list.isEmpty()) {
                    this.memberships.remove(inetAddress);
                }
            }
        }
        channelPromise.setSuccess();
        return channelPromise;
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.block(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        NioDatagramChannel.checkJavaVersion();
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (inetAddress2 == null) {
            throw new NullPointerException("sourceToBlock");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        NioDatagramChannel nioDatagramChannel = this;
        synchronized (nioDatagramChannel) {
            if (this.memberships != null) {
                List<MembershipKey> list = this.memberships.get(inetAddress);
                for (MembershipKey membershipKey : list) {
                    if (!networkInterface.equals(membershipKey.networkInterface())) continue;
                    try {
                        membershipKey.block(inetAddress2);
                    } catch (IOException iOException) {
                        channelPromise.setFailure(iOException);
                    }
                }
            }
        }
        channelPromise.setSuccess();
        return channelPromise;
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2) {
        return this.block(inetAddress, inetAddress2, this.newPromise());
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, InetAddress inetAddress2, ChannelPromise channelPromise) {
        try {
            return this.block(inetAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), inetAddress2, channelPromise);
        } catch (SocketException socketException) {
            channelPromise.setFailure(socketException);
            return channelPromise;
        }
    }

    @Override
    @Deprecated
    protected void setReadPending(boolean bl) {
        super.setReadPending(bl);
    }

    void clearReadPending0() {
        this.clearReadPending();
    }

    @Override
    protected boolean closeOnReadError(Throwable throwable) {
        if (throwable instanceof SocketException) {
            return true;
        }
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
}

