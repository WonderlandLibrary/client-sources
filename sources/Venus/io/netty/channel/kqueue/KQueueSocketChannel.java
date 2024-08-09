/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.AbstractKQueueStreamChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueEventLoop;
import io.netty.channel.kqueue.KQueueSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class KQueueSocketChannel
extends AbstractKQueueStreamChannel
implements SocketChannel {
    private final KQueueSocketChannelConfig config = new KQueueSocketChannelConfig(this);

    public KQueueSocketChannel() {
        super(null, BsdSocket.newSocketStream(), false);
    }

    public KQueueSocketChannel(int n) {
        super(new BsdSocket(n));
    }

    KQueueSocketChannel(Channel channel, BsdSocket bsdSocket, InetSocketAddress inetSocketAddress) {
        super(channel, bsdSocket, inetSocketAddress);
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
    public KQueueSocketChannelConfig config() {
        return this.config;
    }

    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }

    @Override
    protected AbstractKQueueChannel.AbstractKQueueUnsafe newUnsafe() {
        return new KQueueSocketChannelUnsafe(this, null);
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
    public Channel parent() {
        return this.parent();
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
    public SocketChannelConfig config() {
        return this.config();
    }

    private final class KQueueSocketChannelUnsafe
    extends AbstractKQueueStreamChannel.KQueueStreamUnsafe {
        final KQueueSocketChannel this$0;

        private KQueueSocketChannelUnsafe(KQueueSocketChannel kQueueSocketChannel) {
            this.this$0 = kQueueSocketChannel;
            super(kQueueSocketChannel);
        }

        @Override
        protected Executor prepareToClose() {
            try {
                if (this.this$0.isOpen() && this.this$0.config().getSoLinger() > 0) {
                    ((KQueueEventLoop)this.this$0.eventLoop()).remove(this.this$0);
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }

        KQueueSocketChannelUnsafe(KQueueSocketChannel kQueueSocketChannel, 1 var2_2) {
            this(kQueueSocketChannel);
        }
    }
}

