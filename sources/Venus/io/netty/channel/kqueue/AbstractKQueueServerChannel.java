/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.ServerChannel;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueEventLoop;
import io.netty.channel.kqueue.KQueueRecvByteAllocatorHandle;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractKQueueServerChannel
extends AbstractKQueueChannel
implements ServerChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);

    AbstractKQueueServerChannel(BsdSocket bsdSocket) {
        this(bsdSocket, AbstractKQueueServerChannel.isSoErrorZero(bsdSocket));
    }

    AbstractKQueueServerChannel(BsdSocket bsdSocket, boolean bl) {
        super(null, bsdSocket, bl);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof KQueueEventLoop;
    }

    @Override
    protected InetSocketAddress remoteAddress0() {
        return null;
    }

    @Override
    protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
        return new KQueueServerSocketUnsafe(this);
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object filterOutboundMessage(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    abstract Channel newChildChannel(int var1, byte[] var2, int var3, int var4) throws Exception;

    @Override
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress0();
    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    final class KQueueServerSocketUnsafe
    extends AbstractKQueueChannel.AbstractKQueueUnsafe {
        private final byte[] acceptedAddress;
        static final boolean $assertionsDisabled = !AbstractKQueueServerChannel.class.desiredAssertionStatus();
        final AbstractKQueueServerChannel this$0;

        KQueueServerSocketUnsafe(AbstractKQueueServerChannel abstractKQueueServerChannel) {
            this.this$0 = abstractKQueueServerChannel;
            super(abstractKQueueServerChannel);
            this.acceptedAddress = new byte[26];
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void readReady(KQueueRecvByteAllocatorHandle kQueueRecvByteAllocatorHandle) {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            KQueueChannelConfig kQueueChannelConfig = this.this$0.config();
            if (this.this$0.shouldBreakReadReady(kQueueChannelConfig)) {
                this.clearReadFilter0();
                return;
            }
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            kQueueRecvByteAllocatorHandle.reset(kQueueChannelConfig);
            kQueueRecvByteAllocatorHandle.attemptedBytesRead(1);
            this.readReadyBefore();
            Throwable throwable = null;
            try {
                try {
                    do {
                        int n;
                        if ((n = this.this$0.socket.accept(this.acceptedAddress)) == -1) {
                            kQueueRecvByteAllocatorHandle.lastBytesRead(-1);
                            break;
                        }
                        kQueueRecvByteAllocatorHandle.lastBytesRead(1);
                        kQueueRecvByteAllocatorHandle.incMessagesRead(1);
                        this.readPending = false;
                        channelPipeline.fireChannelRead(this.this$0.newChildChannel(n, this.acceptedAddress, 1, this.acceptedAddress[0]));
                    } while (kQueueRecvByteAllocatorHandle.continueReading());
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                }
                kQueueRecvByteAllocatorHandle.readComplete();
                channelPipeline.fireChannelReadComplete();
                if (throwable != null) {
                    channelPipeline.fireExceptionCaught(throwable);
                }
            } finally {
                this.readReadyFinally(kQueueChannelConfig);
            }
        }
    }
}

