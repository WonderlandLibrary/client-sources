/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.AbstractEpollStreamChannel;
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollDomainSocketChannelConfig;
import io.netty.channel.epoll.EpollRecvByteAllocatorHandle;
import io.netty.channel.epoll.LinuxSocket;
import io.netty.channel.epoll.Native;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.DomainSocketChannel;
import io.netty.channel.unix.DomainSocketChannelConfig;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.PeerCredentials;
import java.io.IOException;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EpollDomainSocketChannel
extends AbstractEpollStreamChannel
implements DomainSocketChannel {
    private final EpollDomainSocketChannelConfig config = new EpollDomainSocketChannelConfig(this);
    private volatile DomainSocketAddress local;
    private volatile DomainSocketAddress remote;

    public EpollDomainSocketChannel() {
        super(LinuxSocket.newSocketDomain(), false);
    }

    EpollDomainSocketChannel(Channel channel, FileDescriptor fileDescriptor) {
        super(channel, new LinuxSocket(fileDescriptor.intValue()));
    }

    public EpollDomainSocketChannel(int n) {
        super(n);
    }

    public EpollDomainSocketChannel(Channel channel, LinuxSocket linuxSocket) {
        super(channel, linuxSocket);
    }

    public EpollDomainSocketChannel(int n, boolean bl) {
        super(new LinuxSocket(n), bl);
    }

    @Override
    protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new EpollDomainUnsafe(this, null);
    }

    @Override
    protected DomainSocketAddress localAddress0() {
        return this.local;
    }

    @Override
    protected DomainSocketAddress remoteAddress0() {
        return this.remote;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.socket.bind(socketAddress);
        this.local = (DomainSocketAddress)socketAddress;
    }

    @Override
    public EpollDomainSocketChannelConfig config() {
        return this.config;
    }

    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (super.doConnect(socketAddress, socketAddress2)) {
            this.local = (DomainSocketAddress)socketAddress2;
            this.remote = (DomainSocketAddress)socketAddress;
            return false;
        }
        return true;
    }

    @Override
    public DomainSocketAddress remoteAddress() {
        return (DomainSocketAddress)super.remoteAddress();
    }

    @Override
    public DomainSocketAddress localAddress() {
        return (DomainSocketAddress)super.localAddress();
    }

    @Override
    protected int doWriteSingle(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object = channelOutboundBuffer.current();
        if (object instanceof FileDescriptor && this.socket.sendFd(((FileDescriptor)object).intValue()) > 0) {
            channelOutboundBuffer.remove();
            return 0;
        }
        return super.doWriteSingle(channelOutboundBuffer);
    }

    @Override
    protected Object filterOutboundMessage(Object object) {
        if (object instanceof FileDescriptor) {
            return object;
        }
        return super.filterOutboundMessage(object);
    }

    public PeerCredentials peerCredentials() throws IOException {
        return this.socket.getPeerCredentials();
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
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress0();
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress0();
    }

    @Override
    public EpollChannelConfig config() {
        return this.config();
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    @Override
    public DomainSocketChannelConfig config() {
        return this.config();
    }

    private final class EpollDomainUnsafe
    extends AbstractEpollStreamChannel.EpollStreamUnsafe {
        final EpollDomainSocketChannel this$0;

        private EpollDomainUnsafe(EpollDomainSocketChannel epollDomainSocketChannel) {
            this.this$0 = epollDomainSocketChannel;
            super(epollDomainSocketChannel);
        }

        @Override
        void epollInReady() {
            switch (1.$SwitchMap$io$netty$channel$unix$DomainSocketReadMode[this.this$0.config().getReadMode().ordinal()]) {
                case 1: {
                    super.epollInReady();
                    break;
                }
                case 2: {
                    this.epollInReadFd();
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void epollInReadFd() {
            if (this.this$0.socket.isInputShutdown()) {
                this.clearEpollIn0();
                return;
            }
            EpollDomainSocketChannelConfig epollDomainSocketChannelConfig = this.this$0.config();
            EpollRecvByteAllocatorHandle epollRecvByteAllocatorHandle = this.recvBufAllocHandle();
            epollRecvByteAllocatorHandle.edgeTriggered(this.this$0.isFlagSet(Native.EPOLLET));
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            epollRecvByteAllocatorHandle.reset(epollDomainSocketChannelConfig);
            this.epollInBefore();
            try {
                block10: while (true) {
                    epollRecvByteAllocatorHandle.lastBytesRead(this.this$0.socket.recvFd());
                    switch (epollRecvByteAllocatorHandle.lastBytesRead()) {
                        case 0: {
                            break block10;
                        }
                        case -1: {
                            this.close(this.voidPromise());
                            return;
                        }
                        default: {
                            epollRecvByteAllocatorHandle.incMessagesRead(1);
                            this.readPending = false;
                            channelPipeline.fireChannelRead(new FileDescriptor(epollRecvByteAllocatorHandle.lastBytesRead()));
                            if (epollRecvByteAllocatorHandle.continueReading()) continue block10;
                        }
                    }
                    break;
                }
                epollRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
            } catch (Throwable throwable) {
                epollRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                channelPipeline.fireExceptionCaught(throwable);
            } finally {
                this.epollInFinally(epollDomainSocketChannelConfig);
            }
        }

        EpollDomainUnsafe(EpollDomainSocketChannel epollDomainSocketChannel, 1 var2_2) {
            this(epollDomainSocketChannel);
        }
    }
}

