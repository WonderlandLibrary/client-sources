/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.AbstractKQueueStreamChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueDomainSocketChannelConfig;
import io.netty.channel.kqueue.KQueueRecvByteAllocatorHandle;
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
public final class KQueueDomainSocketChannel
extends AbstractKQueueStreamChannel
implements DomainSocketChannel {
    private final KQueueDomainSocketChannelConfig config = new KQueueDomainSocketChannelConfig(this);
    private volatile DomainSocketAddress local;
    private volatile DomainSocketAddress remote;

    public KQueueDomainSocketChannel() {
        super(null, BsdSocket.newSocketDomain(), false);
    }

    public KQueueDomainSocketChannel(int n) {
        this(null, new BsdSocket(n));
    }

    KQueueDomainSocketChannel(Channel channel, BsdSocket bsdSocket) {
        super(channel, bsdSocket, true);
    }

    @Override
    protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
        return new KQueueDomainUnsafe(this, null);
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
    public KQueueDomainSocketChannelConfig config() {
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
    public KQueueChannelConfig config() {
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

    private final class KQueueDomainUnsafe
    extends AbstractKQueueStreamChannel.KQueueStreamUnsafe {
        final KQueueDomainSocketChannel this$0;

        private KQueueDomainUnsafe(KQueueDomainSocketChannel kQueueDomainSocketChannel) {
            this.this$0 = kQueueDomainSocketChannel;
            super(kQueueDomainSocketChannel);
        }

        @Override
        void readReady(KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle) {
            switch (1.$SwitchMap$io$netty$channel$unix$DomainSocketReadMode[this.this$0.config().getReadMode().ordinal()]) {
                case 1: {
                    super.readReady(kQueueRecvByteAllocatorHandle);
                    break;
                }
                case 2: {
                    this.readReadyFd();
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
        private void readReadyFd() {
            if (this.this$0.socket.isInputShutdown()) {
                super.clearReadFilter0();
                return;
            }
            KQueueDomainSocketChannelConfig kQueueDomainSocketChannelConfig = this.this$0.config();
            KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle = this.recvBufAllocHandle();
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            kQueueRecvByteAllocatorHandle.reset(kQueueDomainSocketChannelConfig);
            this.readReadyBefore();
            try {
                block10: while (true) {
                    int n = this.this$0.socket.recvFd();
                    switch (n) {
                        case 0: {
                            kQueueRecvByteAllocatorHandle.lastBytesRead(0);
                            break block10;
                        }
                        case -1: {
                            kQueueRecvByteAllocatorHandle.lastBytesRead(-1);
                            this.close(this.voidPromise());
                            return;
                        }
                        default: {
                            kQueueRecvByteAllocatorHandle.lastBytesRead(1);
                            kQueueRecvByteAllocatorHandle.incMessagesRead(1);
                            this.readPending = false;
                            channelPipeline.fireChannelRead(new FileDescriptor(n));
                            if (kQueueRecvByteAllocatorHandle.continueReading()) continue block10;
                        }
                    }
                    break;
                }
                kQueueRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
            } catch (Throwable throwable) {
                kQueueRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                channelPipeline.fireExceptionCaught(throwable);
            } finally {
                this.readReadyFinally(kQueueDomainSocketChannelConfig);
            }
        }

        KQueueDomainUnsafe(KQueueDomainSocketChannel kQueueDomainSocketChannel, 1 var2_2) {
            this(kQueueDomainSocketChannel);
        }
    }
}

