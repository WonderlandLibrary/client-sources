/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.kqueue.AbstractKQueueServerChannel;
import io.netty.channel.kqueue.BsdSocket;
import io.netty.channel.kqueue.KQueueChannelConfig;
import io.netty.channel.kqueue.KQueueEventLoop;
import io.netty.channel.kqueue.KQueueServerSocketChannelConfig;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.ServerSocketChannelConfig;
import io.netty.channel.unix.NativeInetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class KQueueServerSocketChannel
extends AbstractKQueueServerChannel
implements ServerSocketChannel {
    private final KQueueServerSocketChannelConfig config = new KQueueServerSocketChannelConfig(this);

    public KQueueServerSocketChannel() {
        super(BsdSocket.newSocketStream(), false);
    }

    public KQueueServerSocketChannel(int n) {
        this(new BsdSocket(n));
    }

    KQueueServerSocketChannel(BsdSocket bsdSocket) {
        super(bsdSocket);
    }

    KQueueServerSocketChannel(BsdSocket bsdSocket, boolean bl) {
        super(bsdSocket, bl);
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof KQueueEventLoop;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        super.doBind(socketAddress);
        this.socket.listen(this.config.getBacklog());
        this.active = true;
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
    public KQueueServerSocketChannelConfig config() {
        return this.config;
    }

    @Override
    protected Channel newChildChannel(int n, byte[] byArray, int n2, int n3) throws Exception {
        return new KQueueSocketChannel((Channel)this, new BsdSocket(n), NativeInetAddress.address(byArray, n2, n3));
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
    public KQueueChannelConfig config() {
        return this.config();
    }

    @Override
    public ServerSocketChannelConfig config() {
        return this.config();
    }
}

