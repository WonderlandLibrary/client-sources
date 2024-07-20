/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.EpollDatagramChannelConfig;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollRecvByteAllocatorHandle;
import io.netty.channel.epoll.IovArray;
import io.netty.channel.epoll.Native;
import io.netty.channel.epoll.NativeDatagramPacketArray;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.DatagramSocketAddress;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.List;

public final class EpollDatagramChannel
extends AbstractEpollChannel
implements DatagramChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(true);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName(DatagramPacket.class) + ", " + StringUtil.simpleClassName(AddressedEnvelope.class) + '<' + StringUtil.simpleClassName(ByteBuf.class) + ", " + StringUtil.simpleClassName(InetSocketAddress.class) + ">, " + StringUtil.simpleClassName(ByteBuf.class) + ')';
    private volatile InetSocketAddress local;
    private volatile InetSocketAddress remote;
    private volatile boolean connected;
    private final EpollDatagramChannelConfig config;

    public EpollDatagramChannel() {
        super(Socket.newSocketDgram(), Native.EPOLLIN);
        this.config = new EpollDatagramChannelConfig(this);
    }

    @Deprecated
    public EpollDatagramChannel(FileDescriptor fd) {
        this(new Socket(fd.intValue()));
    }

    public EpollDatagramChannel(Socket fd) {
        super(null, fd, Native.EPOLLIN, true);
        this.local = fd.localAddress();
        this.config = new EpollDatagramChannelConfig(this);
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
        return this.fd().isOpen() && (this.config.getActiveOnOpen() && this.isRegistered() || this.active);
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public ChannelFuture joinGroup(InetAddress multicastAddress) {
        return this.joinGroup(multicastAddress, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            return this.joinGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
            return promise;
        }
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return this.joinGroup(multicastAddress, networkInterface, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        return this.joinGroup(multicastAddress.getAddress(), networkInterface, null, promise);
    }

    @Override
    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return this.joinGroup(multicastAddress, networkInterface, source, this.newPromise());
    }

    @Override
    public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
        return promise;
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress multicastAddress) {
        return this.leaveGroup(multicastAddress, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
        try {
            return this.leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), null, promise);
        } catch (SocketException e) {
            promise.setFailure(e);
            return promise;
        }
    }

    @Override
    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
        return this.leaveGroup(multicastAddress, networkInterface, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
        return this.leaveGroup(multicastAddress.getAddress(), networkInterface, null, promise);
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
        return this.leaveGroup(multicastAddress, networkInterface, source, this.newPromise());
    }

    @Override
    public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
        return promise;
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
        return this.block(multicastAddress, networkInterface, sourceToBlock, this.newPromise());
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
        if (multicastAddress == null) {
            throw new NullPointerException("multicastAddress");
        }
        if (sourceToBlock == null) {
            throw new NullPointerException("sourceToBlock");
        }
        if (networkInterface == null) {
            throw new NullPointerException("networkInterface");
        }
        promise.setFailure(new UnsupportedOperationException("Multicast not supported"));
        return promise;
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
        return this.block(multicastAddress, sourceToBlock, this.newPromise());
    }

    @Override
    public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
        try {
            return this.block(multicastAddress, NetworkInterface.getByInetAddress(this.localAddress().getAddress()), sourceToBlock, promise);
        } catch (Throwable e) {
            promise.setFailure(e);
            return promise;
        }
    }

    @Override
    protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new EpollDatagramChannelUnsafe();
    }

    @Override
    protected InetSocketAddress localAddress0() {
        return this.local;
    }

    @Override
    protected InetSocketAddress remoteAddress0() {
        return this.remote;
    }

    @Override
    protected void doBind(SocketAddress localAddress) throws Exception {
        InetSocketAddress addr = (InetSocketAddress)localAddress;
        EpollDatagramChannel.checkResolvable(addr);
        this.fd().bind(addr);
        this.local = this.fd().localAddress();
        this.active = true;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer in) throws Exception {
        block2: while (true) {
            Object msg;
            if ((msg = in.current()) == null) {
                this.clearFlag(Native.EPOLLOUT);
                break;
            }
            try {
                NativeDatagramPacketArray array;
                int cnt;
                if (Native.IS_SUPPORTING_SENDMMSG && in.size() > 1 && (cnt = (array = NativeDatagramPacketArray.getInstance(in)).count()) >= 1) {
                    int offset = 0;
                    NativeDatagramPacketArray.NativeDatagramPacket[] packets = array.packets();
                    while (true) {
                        if (cnt <= 0) continue block2;
                        int send = Native.sendmmsg(this.fd().intValue(), packets, offset, cnt);
                        if (send == 0) {
                            this.setFlag(Native.EPOLLOUT);
                            return;
                        }
                        for (int i = 0; i < send; ++i) {
                            in.remove();
                        }
                        cnt -= send;
                        offset += send;
                    }
                }
                boolean done = false;
                for (int i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
                    if (!this.doWriteMessage(msg)) continue;
                    done = true;
                    break;
                }
                if (done) {
                    in.remove();
                    continue;
                }
                this.setFlag(Native.EPOLLOUT);
            } catch (IOException e) {
                in.remove(e);
                continue;
            }
            break;
        }
    }

    private boolean doWriteMessage(Object msg) throws Exception {
        int writtenBytes;
        InetSocketAddress remoteAddress;
        ByteBuf data;
        if (msg instanceof AddressedEnvelope) {
            AddressedEnvelope envelope = (AddressedEnvelope)msg;
            data = (ByteBuf)envelope.content();
            remoteAddress = (InetSocketAddress)envelope.recipient();
        } else {
            data = (ByteBuf)msg;
            remoteAddress = null;
        }
        int dataLen = data.readableBytes();
        if (dataLen == 0) {
            return true;
        }
        if (remoteAddress == null && (remoteAddress = this.remote) == null) {
            throw new NotYetConnectedException();
        }
        if (data.hasMemoryAddress()) {
            long memoryAddress = data.memoryAddress();
            writtenBytes = this.fd().sendToAddress(memoryAddress, data.readerIndex(), data.writerIndex(), remoteAddress.getAddress(), remoteAddress.getPort());
        } else if (data instanceof CompositeByteBuf) {
            IovArray array = ((EpollEventLoop)this.eventLoop()).cleanArray();
            array.add(data);
            int cnt = array.count();
            assert (cnt != 0);
            writtenBytes = this.fd().sendToAddresses(array.memoryAddress(0), cnt, remoteAddress.getAddress(), remoteAddress.getPort());
        } else {
            ByteBuffer nioData = data.internalNioBuffer(data.readerIndex(), data.readableBytes());
            writtenBytes = this.fd().sendTo(nioData, nioData.position(), nioData.limit(), remoteAddress.getAddress(), remoteAddress.getPort());
        }
        return writtenBytes > 0;
    }

    @Override
    protected Object filterOutboundMessage(Object msg) {
        AddressedEnvelope e;
        if (msg instanceof DatagramPacket) {
            CompositeByteBuf comp;
            DatagramPacket packet = (DatagramPacket)msg;
            ByteBuf content = (ByteBuf)packet.content();
            if (content.hasMemoryAddress()) {
                return msg;
            }
            if (content.isDirect() && content instanceof CompositeByteBuf && (comp = (CompositeByteBuf)content).isDirect() && comp.nioBufferCount() <= Native.IOV_MAX) {
                return msg;
            }
            return new DatagramPacket(this.newDirectBuffer(packet, content), (InetSocketAddress)packet.recipient());
        }
        if (msg instanceof ByteBuf) {
            ByteBuf buf = (ByteBuf)msg;
            if (!(buf.hasMemoryAddress() || !PlatformDependent.hasUnsafe() && buf.isDirect())) {
                if (buf instanceof CompositeByteBuf) {
                    CompositeByteBuf comp = (CompositeByteBuf)buf;
                    if (!comp.isDirect() || comp.nioBufferCount() > Native.IOV_MAX) {
                        buf = this.newDirectBuffer(buf);
                        assert (buf.hasMemoryAddress());
                    }
                } else {
                    buf = this.newDirectBuffer(buf);
                    assert (buf.hasMemoryAddress());
                }
            }
            return buf;
        }
        if (msg instanceof AddressedEnvelope && (e = (AddressedEnvelope)msg).content() instanceof ByteBuf && (e.recipient() == null || e.recipient() instanceof InetSocketAddress)) {
            CompositeByteBuf comp;
            ByteBuf content = (ByteBuf)e.content();
            if (content.hasMemoryAddress()) {
                return e;
            }
            if (content instanceof CompositeByteBuf && (comp = (CompositeByteBuf)content).isDirect() && comp.nioBufferCount() <= Native.IOV_MAX) {
                return e;
            }
            return new DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>(this.newDirectBuffer(e, content), (InetSocketAddress)e.recipient());
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg) + EXPECTED_TYPES);
    }

    @Override
    public EpollDatagramChannelConfig config() {
        return this.config;
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.connected = false;
    }

    final class EpollDatagramChannelUnsafe
    extends AbstractEpollChannel.AbstractEpollUnsafe {
        private final List<Object> readBuf = new ArrayList<Object>();

        EpollDatagramChannelUnsafe() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void connect(SocketAddress remote, SocketAddress local, ChannelPromise channelPromise) {
            boolean success = false;
            try {
                try {
                    boolean wasActive = EpollDatagramChannel.this.isActive();
                    InetSocketAddress remoteAddress = (InetSocketAddress)remote;
                    if (local != null) {
                        InetSocketAddress localAddress = (InetSocketAddress)local;
                        EpollDatagramChannel.this.doBind(localAddress);
                    }
                    AbstractEpollChannel.checkResolvable(remoteAddress);
                    EpollDatagramChannel.this.remote = remoteAddress;
                    EpollDatagramChannel.this.local = EpollDatagramChannel.this.fd().localAddress();
                    success = true;
                    channelPromise.trySuccess();
                    if (!wasActive && EpollDatagramChannel.this.isActive()) {
                        EpollDatagramChannel.this.pipeline().fireChannelActive();
                    }
                } finally {
                    if (!success) {
                        EpollDatagramChannel.this.doClose();
                    } else {
                        EpollDatagramChannel.this.connected = true;
                    }
                }
            } catch (Throwable cause) {
                channelPromise.tryFailure(cause);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void epollInReady() {
            assert (EpollDatagramChannel.this.eventLoop().inEventLoop());
            EpollDatagramChannelConfig config = EpollDatagramChannel.this.config();
            if (EpollDatagramChannel.this.shouldBreakEpollInReady(config)) {
                this.clearEpollIn0();
                return;
            }
            EpollRecvByteAllocatorHandle allocHandle = this.recvBufAllocHandle();
            allocHandle.edgeTriggered(EpollDatagramChannel.this.isFlagSet(Native.EPOLLET));
            ChannelPipeline pipeline = EpollDatagramChannel.this.pipeline();
            ByteBufAllocator allocator = config.getAllocator();
            allocHandle.reset(config);
            this.epollInBefore();
            Throwable exception = null;
            try {
                ByteBuf data = null;
                try {
                    do {
                        DatagramSocketAddress remoteAddress;
                        data = allocHandle.allocate(allocator);
                        allocHandle.attemptedBytesRead(data.writableBytes());
                        if (data.hasMemoryAddress()) {
                            remoteAddress = EpollDatagramChannel.this.fd().recvFromAddress(data.memoryAddress(), data.writerIndex(), data.capacity());
                        } else {
                            ByteBuffer nioData = data.internalNioBuffer(data.writerIndex(), data.writableBytes());
                            remoteAddress = EpollDatagramChannel.this.fd().recvFrom(nioData, nioData.position(), nioData.limit());
                        }
                        if (remoteAddress == null) {
                            allocHandle.lastBytesRead(-1);
                            data.release();
                            data = null;
                            break;
                        }
                        allocHandle.incMessagesRead(1);
                        allocHandle.lastBytesRead(remoteAddress.receivedAmount());
                        data.writerIndex(data.writerIndex() + allocHandle.lastBytesRead());
                        this.readBuf.add(new DatagramPacket(data, (InetSocketAddress)this.localAddress(), remoteAddress));
                        data = null;
                    } while (allocHandle.continueReading());
                } catch (Throwable t) {
                    if (data != null) {
                        data.release();
                    }
                    exception = t;
                }
                int size = this.readBuf.size();
                for (int i = 0; i < size; ++i) {
                    this.readPending = false;
                    pipeline.fireChannelRead(this.readBuf.get(i));
                }
                this.readBuf.clear();
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                if (exception != null) {
                    pipeline.fireExceptionCaught(exception);
                }
            } finally {
                this.epollInFinally(config);
            }
        }
    }
}

