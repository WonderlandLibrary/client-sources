/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.AbstractChannel;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueDatagramChannelConfig;
import io.netty.channel.kqueue.KQueueEventLoop;
import io.netty.channel.kqueue.KQueueRecvByteAllocatorHandle;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.DatagramSocketAddress;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.UnixChannelUtil;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class KQueueDatagramChannel
extends AbstractKQueueChannel
implements DatagramChannel {
    private static final ChannelMetadata METADATA;
    private static final String EXPECTED_TYPES;
    private volatile boolean connected;
    private final KQueueDatagramChannelConfig config = new KQueueDatagramChannelConfig(this);
    static final boolean $assertionsDisabled;

    public KQueueDatagramChannel() {
        super(null, BsdSocket.newSocketDgram(), false);
    }

    public KQueueDatagramChannel(int n) {
        this(new BsdSocket(n), true);
    }

    KQueueDatagramChannel(BsdSocket bsdSocket, boolean bl) {
        super(null, bsdSocket, bl);
    }

    @Override
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress)super.remoteAddress();
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
    public boolean isActive() {
        return this.socket.isOpen() && (this.config.getActiveOnOpen() && this.isRegistered() || this.active);
    }

    @Override
    public boolean isConnected() {
        return this.connected;
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

    @Override
    public ChannelFuture joinGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        channelPromise.setFailure(new UnsupportedOperationException("Multicast not supported"));
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

    @Override
    public ChannelFuture leaveGroup(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        channelPromise.setFailure(new UnsupportedOperationException("Multicast not supported"));
        return channelPromise;
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2) {
        return this.block(inetAddress, networkInterface, inetAddress2, this.newPromise());
    }

    @Override
    public ChannelFuture block(InetAddress inetAddress, NetworkInterface networkInterface, InetAddress inetAddress2, ChannelPromise channelPromise) {
        if (inetAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (inetAddress2 == null) {
            throw new NullPointerException("sourceToBlock");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        channelPromise.setFailure(new UnsupportedOperationException("Multicast not supported"));
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
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
            return channelPromise;
        }
    }

    @Override
    protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
        return new KQueueDatagramChannelUnsafe(this);
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        super.doBind(socketAddress);
        this.active = true;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            Object object;
            if ((object = channelOutboundBuffer.current()) == null) {
                this.writeFilter(true);
                break;
            }
            try {
                boolean bl = false;
                for (int i = this.config().getWriteSpinCount(); i > 0; --i) {
                    if (!this.doWriteMessage(object)) continue;
                    bl = true;
                    break;
                }
                if (bl) {
                    channelOutboundBuffer.remove();
                    continue;
                }
                this.writeFilter(false);
            } catch (IOException iOException) {
                channelOutboundBuffer.remove(iOException);
                continue;
            }
            break;
        }
    }

    private boolean doWriteMessage(Object object) throws Exception {
        long l;
        InetSocketAddress inetSocketAddress;
        ByteBuf byteBuf;
        if (object instanceof AddressedEnvelope) {
            AddressedEnvelope addressedEnvelope = (AddressedEnvelope)object;
            byteBuf = (ByteBuf)addressedEnvelope.content();
            inetSocketAddress = (InetSocketAddress)addressedEnvelope.recipient();
        } else {
            byteBuf = (ByteBuf)object;
            inetSocketAddress = null;
        }
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return false;
        }
        if (byteBuf.hasMemoryAddress()) {
            long l2 = byteBuf.memoryAddress();
            l = inetSocketAddress == null ? (long)this.socket.writeAddress(l2, byteBuf.readerIndex(), byteBuf.writerIndex()) : (long)this.socket.sendToAddress(l2, byteBuf.readerIndex(), byteBuf.writerIndex(), inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        } else if (byteBuf.nioBufferCount() > 1) {
            IovArray iovArray = ((KQueueEventLoop)this.eventLoop()).cleanArray();
            iovArray.add(byteBuf);
            int n2 = iovArray.count();
            if (!$assertionsDisabled && n2 == 0) {
                throw new AssertionError();
            }
            l = inetSocketAddress == null ? this.socket.writevAddresses(iovArray.memoryAddress(0), n2) : (long)this.socket.sendToAddresses(iovArray.memoryAddress(0), n2, inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        } else {
            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
            l = inetSocketAddress == null ? (long)this.socket.write(byteBuffer, byteBuffer.position(), byteBuffer.limit()) : (long)this.socket.sendTo(byteBuffer, byteBuffer.position(), byteBuffer.limit(), inetSocketAddress.getAddress(), inetSocketAddress.getPort());
        }
        return l > 0L;
    }

    @Override
    protected Object filterOutboundMessage(Object object) {
        AddressedEnvelope<ByteBuf, InetSocketAddress> addressedEnvelope;
        if (object instanceof DatagramPacket) {
            DatagramPacket datagramPacket = (DatagramPacket)object;
            ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
            return UnixChannelUtil.isBufferCopyNeededForWrite(byteBuf) ? new DatagramPacket(this.newDirectBuffer(datagramPacket, byteBuf), (InetSocketAddress)datagramPacket.recipient()) : object;
        }
        if (object instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf)object;
            return UnixChannelUtil.isBufferCopyNeededForWrite(byteBuf) ? this.newDirectBuffer(byteBuf) : byteBuf;
        }
        if (object instanceof AddressedEnvelope && (addressedEnvelope = (AddressedEnvelope<ByteBuf, InetSocketAddress>)object).content() instanceof ByteBuf && (addressedEnvelope.recipient() == null || addressedEnvelope.recipient() instanceof InetSocketAddress)) {
            ByteBuf byteBuf = (ByteBuf)addressedEnvelope.content();
            return UnixChannelUtil.isBufferCopyNeededForWrite(byteBuf) ? new DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>(this.newDirectBuffer(addressedEnvelope, byteBuf), (InetSocketAddress)addressedEnvelope.recipient()) : addressedEnvelope;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(object) + EXPECTED_TYPES);
    }

    @Override
    public KQueueDatagramChannelConfig config() {
        return this.config;
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.socket.disconnect();
        this.active = false;
        this.connected = false;
    }

    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (super.doConnect(socketAddress, socketAddress2)) {
            this.connected = true;
            return false;
        }
        return true;
    }

    @Override
    protected void doClose() throws Exception {
        super.doClose();
        this.connected = false;
    }

    @Override
    public KQueueChannelConfig config() {
        return this.config();
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
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
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    @Override
    public DatagramChannelConfig config() {
        return this.config();
    }

    static {
        $assertionsDisabled = !KQueueDatagramChannel.class.desiredAssertionStatus();
        METADATA = new ChannelMetadata(true);
        EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    }

    final class KQueueDatagramChannelUnsafe
    extends AbstractKQueueChannel.AbstractKQueueUnsafe {
        static final boolean $assertionsDisabled = !KQueueDatagramChannel.class.desiredAssertionStatus();
        final KQueueDatagramChannel this$0;

        KQueueDatagramChannelUnsafe(KQueueDatagramChannel kQueueDatagramChannel) {
            this.this$0 = kQueueDatagramChannel;
            super(kQueueDatagramChannel);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void readReady(KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle) {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            KQueueDatagramChannelConfig kQueueDatagramChannelConfig = this.this$0.config();
            if (this.this$0.shouldBreakReadReady(kQueueDatagramChannelConfig)) {
                this.clearReadFilter0();
                return;
            }
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            ByteBufAllocator byteBufAllocator = kQueueDatagramChannelConfig.getAllocator();
            kQueueRecvByteAllocatorHandle.reset(kQueueDatagramChannelConfig);
            this.readReadyBefore();
            Throwable throwable = null;
            try {
                ByteBuf byteBuf = null;
                try {
                    do {
                        DatagramSocketAddress datagramSocketAddress;
                        byteBuf = kQueueRecvByteAllocatorHandle.allocate(byteBufAllocator);
                        kQueueRecvByteAllocatorHandle.attemptedBytesRead(byteBuf.writableBytes());
                        if (byteBuf.hasMemoryAddress()) {
                            datagramSocketAddress = this.this$0.socket.recvFromAddress(byteBuf.memoryAddress(), byteBuf.writerIndex(), byteBuf.capacity());
                        } else {
                            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(byteBuf.writerIndex(), byteBuf.writableBytes());
                            datagramSocketAddress = this.this$0.socket.recvFrom(byteBuffer, byteBuffer.position(), byteBuffer.limit());
                        }
                        if (datagramSocketAddress == null) {
                            kQueueRecvByteAllocatorHandle.lastBytesRead(-1);
                            byteBuf.release();
                            byteBuf = null;
                            break;
                        }
                        kQueueRecvByteAllocatorHandle.incMessagesRead(1);
                        kQueueRecvByteAllocatorHandle.lastBytesRead(datagramSocketAddress.receivedAmount());
                        byteBuf.writerIndex(byteBuf.writerIndex() + kQueueRecvByteAllocatorHandle.lastBytesRead());
                        this.readPending = false;
                        channelPipeline.fireChannelRead(new DatagramPacket(byteBuf, (InetSocketAddress)this.localAddress(), datagramSocketAddress));
                        byteBuf = null;
                    } while (kQueueRecvByteAllocatorHandle.continueReading());
                } catch (Throwable throwable2) {
                    if (byteBuf != null) {
                        byteBuf.release();
                    }
                    throwable = throwable2;
                }
                kQueueRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                if (throwable != null) {
                    channelPipeline.fireExceptionCaught(throwable);
                }
            } finally {
                this.readReadyFinally(kQueueDatagramChannelConfig);
            }
        }
    }
}

