/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.AbstractEpollStreamChannel;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannelConfig;
import io.netty.channel.epoll.EpollTcpInfo;
import io.netty.channel.epoll.Native;
import io.netty.channel.epoll.TcpMd5Util;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.Socket;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.AlreadyConnectedException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;

public final class EpollSocketChannel
extends AbstractEpollStreamChannel
implements SocketChannel {
    private final EpollSocketChannelConfig config;
    private volatile InetSocketAddress local;
    private volatile InetSocketAddress remote;
    private InetSocketAddress requestedRemote;
    private volatile Collection<InetAddress> tcpMd5SigAddresses = Collections.emptyList();

    EpollSocketChannel(Channel parent, Socket fd, InetSocketAddress remote) {
        super(parent, fd);
        this.config = new EpollSocketChannelConfig(this);
        this.remote = remote;
        this.local = fd.localAddress();
        if (parent instanceof EpollServerSocketChannel) {
            this.tcpMd5SigAddresses = ((EpollServerSocketChannel)parent).tcpMd5SigAddresses();
        }
    }

    public EpollSocketChannel() {
        super(Socket.newSocketStream(), false);
        this.config = new EpollSocketChannelConfig(this);
    }

    @Deprecated
    public EpollSocketChannel(FileDescriptor fd) {
        super(fd);
        this.remote = this.fd().remoteAddress();
        this.local = this.fd().localAddress();
        this.config = new EpollSocketChannelConfig(this);
    }

    public EpollSocketChannel(Socket fd, boolean active) {
        super(fd, active);
        this.remote = fd.remoteAddress();
        this.local = fd.localAddress();
        this.config = new EpollSocketChannelConfig(this);
    }

    public EpollTcpInfo tcpInfo() {
        return this.tcpInfo(new EpollTcpInfo());
    }

    public EpollTcpInfo tcpInfo(EpollTcpInfo info) {
        try {
            Native.tcpInfo(this.fd().intValue(), info);
            return info;
        } catch (IOException e) {
            throw new ChannelException(e);
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
    protected SocketAddress localAddress0() {
        return this.local;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.remote;
    }

    @Override
    protected void doBind(SocketAddress local) throws Exception {
        InetSocketAddress localAddress = (InetSocketAddress)local;
        this.fd().bind(localAddress);
        this.local = this.fd().localAddress();
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
        return new EpollSocketChannelUnsafe();
    }

    private static InetSocketAddress computeRemoteAddr(InetSocketAddress remoteAddr, InetSocketAddress osRemoteAddr) {
        if (osRemoteAddr != null) {
            if (PlatformDependent.javaVersion() >= 7) {
                try {
                    return new InetSocketAddress(InetAddress.getByAddress(remoteAddr.getHostString(), osRemoteAddr.getAddress().getAddress()), osRemoteAddr.getPort());
                } catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            return osRemoteAddr;
        }
        return remoteAddr;
    }

    @Override
    protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        if (localAddress != null) {
            EpollSocketChannel.checkResolvable((InetSocketAddress)localAddress);
        }
        InetSocketAddress remoteAddr = (InetSocketAddress)remoteAddress;
        EpollSocketChannel.checkResolvable(remoteAddr);
        if (this.remote != null) {
            throw new AlreadyConnectedException();
        }
        boolean connected = super.doConnect(remoteAddress, localAddress);
        if (connected) {
            this.remote = EpollSocketChannel.computeRemoteAddr(remoteAddr, this.fd().remoteAddress());
        } else {
            this.requestedRemote = remoteAddr;
        }
        this.local = this.fd().localAddress();
        return connected;
    }

    void setTcpMd5Sig(Map<InetAddress, byte[]> keys) throws IOException {
        this.tcpMd5SigAddresses = TcpMd5Util.newTcpMd5Sigs(this, this.tcpMd5SigAddresses, keys);
    }

    private final class EpollSocketChannelUnsafe
    extends AbstractEpollStreamChannel.EpollStreamUnsafe {
        private EpollSocketChannelUnsafe() {
        }

        @Override
        protected Executor prepareToClose() {
            try {
                if (EpollSocketChannel.this.isOpen() && EpollSocketChannel.this.config().getSoLinger() > 0) {
                    ((EpollEventLoop)EpollSocketChannel.this.eventLoop()).remove(EpollSocketChannel.this);
                    return GlobalEventExecutor.INSTANCE;
                }
            } catch (Throwable throwable) {
                // empty catch block
            }
            return null;
        }

        @Override
        boolean doFinishConnect() throws Exception {
            if (super.doFinishConnect()) {
                EpollSocketChannel.this.remote = EpollSocketChannel.computeRemoteAddr(EpollSocketChannel.this.requestedRemote, EpollSocketChannel.this.fd().remoteAddress());
                EpollSocketChannel.this.requestedRemote = null;
                return true;
            }
            return false;
        }
    }
}

