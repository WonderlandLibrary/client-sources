/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.AbstractEpollStreamChannel;
import io.netty.channel.epoll.EpollChannelConfig;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannelConfig;
import io.netty.channel.epoll.EpollTcpInfo;
import io.netty.channel.epoll.LinuxSocket;
import io.netty.channel.epoll.TcpMd5Util;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EpollSocketChannel
extends AbstractEpollStreamChannel
implements SocketChannel {
    private final EpollSocketChannelConfig config;
    private volatile Collection<InetAddress> tcpMd5SigAddresses = Collections.emptyList();

    public EpollSocketChannel() {
        super(LinuxSocket.newSocketStream(), false);
        this.config = new EpollSocketChannelConfig(this);
    }

    public EpollSocketChannel(int n) {
        super(n);
        this.config = new EpollSocketChannelConfig(this);
    }

    EpollSocketChannel(LinuxSocket linuxSocket, boolean bl) {
        super(linuxSocket, bl);
        this.config = new EpollSocketChannelConfig(this);
    }

    EpollSocketChannel(Channel channel, LinuxSocket linuxSocket, InetSocketAddress inetSocketAddress) {
        super(channel, linuxSocket, inetSocketAddress);
        this.config = new EpollSocketChannelConfig(this);
        if (channel instanceof EpollServerSocketChannel) {
            this.tcpMd5SigAddresses = ((EpollServerSocketChannel)channel).tcpMd5SigAddresses();
        }
    }

    public EpollTcpInfo tcpInfo() {
        return this.tcpInfo(new EpollTcpInfo());
    }

    public EpollTcpInfo tcpInfo(EpollTcpInfo epollTcpInfo) {
        try {
            this.socket.getTcpInfo(epollTcpInfo);
            return epollTcpInfo;
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
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
    public EpollSocketChannelConfig config() {
        return this.config;
    }

    @Override
    public ServerSocketChannel parent() {
        return (ServerSocketChannel)super.parent();
    }

    @Override
    protected AbstractEpollChannel.AbstractEpollUnsafe newUnsafe() {
        return new EpollSocketChannelUnsafe(this, null);
    }

    void setTcpMd5Sig(Map<InetAddress, byte[]> map) throws IOException {
        this.tcpMd5SigAddresses = TcpMd5Util.newTcpMd5Sigs(this, this.tcpMd5SigAddresses, map);
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
    public EpollChannelConfig config() {
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

    private final class EpollSocketChannelUnsafe
    extends AbstractEpollStreamChannel.EpollStreamUnsafe {
        final EpollSocketChannel this$0;

        private EpollSocketChannelUnsafe(EpollSocketChannel epollSocketChannel) {
            this.this$0 = epollSocketChannel;
            super(epollSocketChannel);
        }

        @Override
        protected Executor prepareToClose() {
            try {
                if (this.this$0.isOpen() && this.this$0.config().getSoLinger() > 0) {
                    ((EpollEventLoop)this.this$0.eventLoop()).remove(this.this$0);
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }

        EpollSocketChannelUnsafe(EpollSocketChannel epollSocketChannel, 1 var2_2) {
            this(epollSocketChannel);
        }
    }
}

