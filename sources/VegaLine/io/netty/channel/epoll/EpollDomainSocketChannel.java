/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.AbstractEpollStreamChannel;
import io.netty.channel.epoll.EpollDomainSocketChannelConfig;
import io.netty.channel.epoll.EpollRecvByteAllocatorHandle;
import io.netty.channel.epoll.Native;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.DomainSocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.PeerCredentials;
import io.netty.channel.unix.Socket;
import java.io.IOException;
import java.net.SocketAddress;

public final class EpollDomainSocketChannel
extends AbstractEpollStreamChannel
implements DomainSocketChannel {
    private final EpollDomainSocketChannelConfig config = new EpollDomainSocketChannelConfig(this);
    private volatile DomainSocketAddress local;
    private volatile DomainSocketAddress remote;

    public EpollDomainSocketChannel() {
        super(Socket.newSocketDomain(), false);
    }

    @Deprecated
    public EpollDomainSocketChannel(Channel parent, FileDescriptor fd) {
        super(parent, new Socket(fd.intValue()));
    }

    @Deprecated
    public EpollDomainSocketChannel(FileDescriptor fd) {
        super(fd);
    }

    public EpollDomainSocketChannel(Channel parent, Socket fd) {
        super(parent, fd);
    }

    public EpollDomainSocketChannel(Socket fd, boolean active) {
        super(fd, active);
    }

    @Override
    protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new EpollDomainUnsafe();
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
    protected void doBind(SocketAddress localAddress) throws Exception {
        this.fd().bind(localAddress);
        this.local = (DomainSocketAddress)localAddress;
    }

    @Override
    public EpollDomainSocketChannelConfig config() {
        return this.config;
    }

    @Override
    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (super.doConnect(remoteAddress, localAddress)) {
            this.local = (DomainSocketAddress)localAddress;
            this.remote = (DomainSocketAddress)remoteAddress;
            return true;
        }
        return false;
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
    protected boolean doWriteSingle(ChannelOutboundBuffer in, int writeSpinCount) throws Exception {
        Object msg = in.current();
        if (msg instanceof FileDescriptor && Native.sendFd(this.fd().intValue(), ((FileDescriptor)msg).intValue()) > 0) {
            in.remove();
            return true;
        }
        return super.doWriteSingle(in, writeSpinCount);
    }

    @Override
    protected Object filterOutboundMessage(Object msg) {
        if (msg instanceof FileDescriptor) {
            return msg;
        }
        return super.filterOutboundMessage(msg);
    }

    public PeerCredentials peerCredentials() throws IOException {
        return this.fd().getPeerCredentials();
    }

    private final class EpollDomainUnsafe
    extends AbstractEpollStreamChannel.EpollStreamUnsafe {
        private EpollDomainUnsafe() {
        }

        @Override
        void epollInReady() {
            switch (EpollDomainSocketChannel.this.config().getReadMode()) {
                case BYTES: {
                    super.epollInReady();
                    break;
                }
                case FILE_DESCRIPTORS: {
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
            if (EpollDomainSocketChannel.this.fd().isInputShutdown()) {
                this.clearEpollIn0();
                return;
            }
            EpollDomainSocketChannelConfig config = EpollDomainSocketChannel.this.config();
            EpollRecvByteAllocatorHandle allocHandle = this.recvBufAllocHandle();
            allocHandle.edgeTriggered(EpollDomainSocketChannel.this.isFlagSet(Native.EPOLLET));
            ChannelPipeline pipeline = EpollDomainSocketChannel.this.pipeline();
            allocHandle.reset(config);
            this.epollInBefore();
            try {
                block10: while (true) {
                    allocHandle.lastBytesRead(Native.recvFd(EpollDomainSocketChannel.this.fd().intValue()));
                    switch (allocHandle.lastBytesRead()) {
                        case 0: {
                            break block10;
                        }
                        case -1: {
                            this.close(this.voidPromise());
                            return;
                        }
                        default: {
                            allocHandle.incMessagesRead(1);
                            this.readPending = false;
                            pipeline.fireChannelRead(new FileDescriptor(allocHandle.lastBytesRead()));
                            if (allocHandle.continueReading()) continue block10;
                        }
                    }
                    break;
                }
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
            } catch (Throwable t) {
                allocHandle.readComplete();
                pipeline.fireChannelReadComplete();
                pipeline.fireExceptionCaught(t);
            } finally {
                this.epollInFinally(config);
            }
        }
    }
}

