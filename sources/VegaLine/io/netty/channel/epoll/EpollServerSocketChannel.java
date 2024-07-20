/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.EventLoop;
import io.netty.channel.epoll.AbstractEpollServerChannel;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollServerSocketChannelConfig;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.epoll.Native;
import io.netty.channel.epoll.TcpMd5Util;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.channel.unix.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class EpollServerSocketChannel
extends AbstractEpollServerChannel
implements ServerSocketChannel {
    private final EpollServerSocketChannelConfig config;
    private volatile InetSocketAddress local;
    private volatile Collection<InetAddress> tcpMd5SigAddresses = Collections.emptyList();

    public EpollServerSocketChannel() {
        super(Socket.newSocketStream(), false);
        this.config = new EpollServerSocketChannelConfig(this);
    }

    @Deprecated
    public EpollServerSocketChannel(FileDescriptor fd) {
        this(new Socket(fd.intValue()));
    }

    @Deprecated
    public EpollServerSocketChannel(Socket fd) {
        super(fd);
        this.local = fd.localAddress();
        this.config = new EpollServerSocketChannelConfig(this);
    }

    public EpollServerSocketChannel(Socket fd, boolean active) {
        super(fd, active);
        this.local = fd.localAddress();
        this.config = new EpollServerSocketChannelConfig(this);
    }

    @Override
    protected boolean isCompatible(EventLoop loop) {
        return loop instanceof EpollEventLoop;
    }

    @Override
    protected void doBind(SocketAddress localAddress) throws Exception {
        InetSocketAddress addr = (InetSocketAddress)localAddress;
        EpollServerSocketChannel.checkResolvable(addr);
        this.fd().bind(addr);
        this.local = this.fd().localAddress();
        if (Native.IS_SUPPORTING_TCP_FASTOPEN && this.config.getTcpFastopen() > 0) {
            Native.setTcpFastopen(this.fd().intValue(), this.config.getTcpFastopen());
        }
        this.fd().listen(this.config.getBacklog());
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
    public EpollServerSocketChannelConfig config() {
        return this.config;
    }

    @Override
    protected InetSocketAddress localAddress0() {
        return this.local;
    }

    @Override
    protected Channel newChildChannel(int fd, byte[] address, int offset, int len) throws Exception {
        return new EpollSocketChannel(this, new Socket(fd), NativeInetAddress.address(address, offset, len));
    }

    Collection<InetAddress> tcpMd5SigAddresses() {
        return this.tcpMd5SigAddresses;
    }

    void setTcpMd5Sig(Map<InetAddress, byte[]> keys) throws IOException {
        this.tcpMd5SigAddresses = TcpMd5Util.newTcpMd5Sigs(this, this.tcpMd5SigAddresses, keys);
    }
}

